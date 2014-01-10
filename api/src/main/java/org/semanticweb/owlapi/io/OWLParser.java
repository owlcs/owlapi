/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.io;

import java.io.IOException;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/** An {@code OWLParser} parses an ontology document and adds the axioms
 * of the parsed ontology to an existing ontology.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 14-Nov-2006 */
public interface OWLParser {
    /** Sets the {@code OWLOntologyManager} which should be used to load imports
     * etc.
     * 
     * @param owlOntologyManager
     *            The ontology manager to be set
     * @deprecated Each {@code OWLOntology} contains a reference to its manager.
     *             This method is no longer necessary. Parsers will obtain the
     *             manager from the ontology in the various parse methods i.e.
     *             {@link #parse(OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     *             ,
     *             {@link #parse(OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     *             ,
     *             {@link #parse(OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology, org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration)} */
    @Deprecated
    void setOWLOntologyManager(OWLOntologyManager owlOntologyManager);

    /** Parses the ontology with a concrete representation at {@code documentIRI}
     * and adds its axioms to {@code ontology}. Implementors of this method should
     * load any imported ontologies with the ontology's manager's
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration) makeLoadImportRequest()}
     * method.
     * 
     * @param documentIRI
     *            the IRI of the ontology to parse
     * @param ontology
     *            the ontology to which the parsed ontology's axioms are added
     * @return the format of the parsed ontology, never {@code null}
     * @throws OWLParserException
     *             if there was a problem parsing the ontology
     * @throws IOException
     *             if there was an IOException during parsing
     * @throws OWLOntologyChangeException
     *             if there was a problem updating {@code ontology}
     * @throws UnloadableImportException
     *             if parsing the ontology prompted the loading of an unloadable import */
    OWLOntologyFormat parse(IRI documentIRI, OWLOntology ontology)
            throws OWLParserException, IOException, OWLOntologyChangeException,
            UnloadableImportException;

    /** Parses the ontology with a concrete representation in {@code documentSource}
     * and adds its axioms to {@code ontology}. Implementors of this method should
     * load any imported ontologies with the ontology's manager's
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration) makeLoadImportRequest()}
     * method.
     * 
     * @param documentSource
     *            the source of a concrete representation of the ontology to parse
     * @param ontology
     *            the ontology to which the parsed ontology's axioms are added
     * @return the format of the parsed ontology, never {@code null}
     * @throws OWLParserException
     *             if there was a problem parsing the ontology
     * @throws IOException
     *             if there was an IOException during parsing
     * @throws OWLOntologyChangeException
     *             if there was a problem updating {@code ontology}
     * @throws UnloadableImportException
     *             if parsing the ontology prompted the loading of an unloadable import */
    OWLOntologyFormat
            parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology)
                    throws OWLParserException, IOException, OWLOntologyChangeException,
                    UnloadableImportException;

    /** Parses the ontology with a concrete representation in {@code documentSource}
     * and adds its axioms to {@code ontology}. Implementors of this method should
     * load any imported ontologies with the ontology's manager's
     * {@link OWLOntologyManager#makeLoadImportRequest(org.semanticweb.owlapi.model.OWLImportsDeclaration, OWLOntologyLoaderConfiguration) makeLoadImportRequest()}
     * method.
     * 
     * @param documentSource
     *            the source of a concrete representation of the ontology to parse
     * @param ontology
     *            the ontology to which the parsed ontology's axioms are added
     * @param configuration
     *            a configuration object providing options to the parser
     * @return the format of the parsed ontology, never {@code null}
     * @throws OWLParserException
     *             if there was a problem parsing the ontology
     * @throws IOException
     *             if there was an IOException during parsing
     * @throws OWLOntologyChangeException
     *             if there was a problem updating {@code ontology}
     * @throws UnloadableImportException
     *             if parsing the ontology prompted the loading of an unloadable import */
    OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology, OWLOntologyLoaderConfiguration configuration)
            throws OWLParserException, IOException, OWLOntologyChangeException,
            UnloadableImportException;
}
