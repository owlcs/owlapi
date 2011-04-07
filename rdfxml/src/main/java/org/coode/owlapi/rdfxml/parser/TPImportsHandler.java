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

package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class TPImportsHandler extends TriplePredicateHandler {


    public TPImportsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_IMPORTS.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

        consumeTriple(subject, predicate, object);
        getConsumer().addOntology(subject);
        getConsumer().addOntology(object);
        OWLImportsDeclaration importsDeclaration = getDataFactory().getOWLImportsDeclaration(object);
        getConsumer().addImport(importsDeclaration);

        if (!getConsumer().getConfiguration().isIgnoredImport(object)) {
            OWLOntologyManager man = getConsumer().getOWLOntologyManager();
            man.makeLoadImportRequest(importsDeclaration, getConsumer().getConfiguration());


            OWLOntology importedOntology = man.getImportedOntology(importsDeclaration);
            if (importedOntology != null) {
                OWLOntologyFormat importedOntologyFormat = man.getOntologyFormat(importedOntology);
                if (importedOntologyFormat instanceof RDFOntologyFormat) {
                    if (importedOntology.isAnonymous()) {
                        OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy missingOntologyHeaderStrategy = getConsumer().getConfiguration().getMissingOntologyHeaderStrategy();
                        boolean includeGraph = missingOntologyHeaderStrategy.equals(OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy.INCLUDE_GRAPH);

                        if (includeGraph) {
                            // We should have just included the triples rather than imported them. So,
                            // we remove the imports statement, add the axioms from the imported ontology to
                            // out importing ontology and remove the imported ontology.
                            // WHO EVER THOUGHT THAT THIS WAS A GOOD IDEA?
                            man.applyChange(new RemoveImport(getConsumer().getOntology(), importsDeclaration));

                            for (OWLImportsDeclaration decl : importedOntology.getImportsDeclarations()) {
                                man.applyChange(new AddImport(getConsumer().getOntology(), decl));
                            }
                            for (OWLAnnotation anno : importedOntology.getAnnotations()) {
                                man.applyChange(new AddOntologyAnnotation(getConsumer().getOntology(), anno));
                            }
                            for (OWLAxiom ax : importedOntology.getAxioms()) {
                                getConsumer().addAxiom(ax);
                            }
                            man.removeOntology(importedOntology);
                        }

                    }
                }
            }

            getConsumer().importsClosureChanged();

        }

    }
}
