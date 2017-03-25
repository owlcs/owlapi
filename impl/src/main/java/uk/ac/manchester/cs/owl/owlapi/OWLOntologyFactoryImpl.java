/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyInputSourceException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.model.UnloadableImportException;

import com.google.common.collect.Sets;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class OWLOntologyFactoryImpl implements OWLOntologyFactory {

    private final Set<String> parsableSchemes = Sets.newHashSet("http", "https", "file", "ftp");
    private final OWLOntologyBuilder ontologyBuilder;

    /**
     * @param ontologyBuilder ontology builder
     */
    @Inject
    public OWLOntologyFactoryImpl(OWLOntologyBuilder ontologyBuilder) {
        this.ontologyBuilder = verifyNotNull(ontologyBuilder);
    }

    @Override
    public boolean canCreateFromDocumentIRI(IRI documentIRI) {
        return true;
    }

    @Override
    public boolean canAttemptLoading(OWLOntologyDocumentSource source) {
        return source.loadingCanBeAttempted(parsableSchemes);
    }

    @Override
    public OWLOntology createOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID,
        IRI documentIRI, OWLOntologyCreationHandler handler) {
        OWLOntology ont = ontologyBuilder.createOWLOntology(manager, ontologyID);
        handler.ontologyCreated(ont);
        handler.setOntologyFormat(ont, new RDFXMLDocumentFormat());
        return ont;
    }

    @Override
    public OWLOntology loadOWLOntology(OWLOntologyManager manager,
        OWLOntologyDocumentSource documentSource, OWLOntologyCreationHandler handler,
        OntologyConfigurator configuration) throws OWLOntologyCreationException {
        // Attempt to parse the ontology by looping through the parsers. If the
        // ontology is parsed successfully then we break out and return the
        // ontology.
        // I think that this is more reliable than selecting a parser based on a
        // file extension
        // for example (perhaps the parser list could be ordered based on most
        // likely parser, which
        // could be determined by file extension).
        Map<OWLParser, OWLParserException> exceptions = new LinkedHashMap<>();
        // Call the super method to create the ontology - this is needed,
        // because
        // we throw an exception if someone tries to create an ontology directly
        OWLOntology existingOntology = null;
        IRI iri = documentSource.getDocumentIRI();
        if (manager.contains(iri)) {
            existingOntology = manager.getOntology(iri);
        }
        OWLOntologyID ontologyID = new OWLOntologyID();
        OWLOntology ont =
            createOWLOntology(manager, ontologyID, documentSource.getDocumentIRI(), handler);
        // Now parse the input into the empty ontology that we created
        // select a parser if the input source has format information and MIME
        // information
        Set<String> bannedParsers = Sets.newHashSet(configuration.getBannedParsers().split(" "));
        for (OWLParserFactory parserFactory : documentSource.filter(manager.getOntologyParsers())) {
            if (!bannedParsers.contains(parserFactory.getClass().getName())) {
                OWLParser parser = parserFactory.createParser();
                try {
                    if (existingOntology == null && !ont.isEmpty()) {
                        // Junk from a previous parse. We should clear the ont
                        manager.removeOntology(ont);
                        ont = createOWLOntology(manager, ontologyID,
                            documentSource.getDocumentIRI(), handler);
                    }
                    handler.setOntologyFormat(ont,
                        documentSource.acceptParser(parser, ont, configuration));
                    return ont;
                } catch (UnloadableImportException e) {
                    // If an import cannot be located, all parsers will fail.
                    // Again,
                    // terminate early
                    // First clean up
                    manager.removeOntology(ont);
                    throw e;
                } catch (OWLParserException e) {
                    if (e.getCause() instanceof IOException
                        || e.getCause() instanceof OWLOntologyInputSourceException) {
                        // For input/output exceptions, we assume that it means
                        // the
                        // source cannot be read regardless of the parsers, so
                        // we
                        // stop
                        // early
                        // First clean up
                        manager.removeOntology(ont);
                        throw new OWLOntologyCreationIOException(e.getCause());
                    }
                    // Record this attempts and continue trying to parse.
                    exceptions.put(parser, e);
                } catch (RuntimeException e) {
                    // Clean up and rethrow
                    exceptions.put(parser, new OWLParserException(e));
                    manager.removeOntology(ont);
                    throw e;
                }
            }
        }
        if (existingOntology == null) {
            manager.removeOntology(ont);
        }
        // We haven't found a parser that could parse the ontology properly.
        // Throw an
        // exception whose message contains the stack traces from all of the
        // parsers
        // that we have tried.
        throw new UnparsableOntologyException(documentSource.getDocumentIRI(), exceptions,
            configuration);
    }
}
