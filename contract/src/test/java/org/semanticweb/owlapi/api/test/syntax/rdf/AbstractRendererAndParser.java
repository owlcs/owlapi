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
package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Set;

import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 09-May-2007
 */
@SuppressWarnings("javadoc")
public abstract class AbstractRendererAndParser {

    private OWLOntologyManager man;

    @Before
    public void setUp() {
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(
                new RDFXMLParserFactory());
        man.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        man.addOntologyFactory(new ParsableOWLOntologyFactory());
        man.addOntologyStorer(new RDFXMLOntologyStorer());
    }

    protected OWLClass createClass() {
        return Class(TestUtils.createIRI());
    }

    protected OWLAnnotationProperty createAnnotationProperty() {
        return getDataFactory().getOWLAnnotationProperty(TestUtils.createIRI());
    }

    protected OWLObjectProperty createObjectProperty() {
        return man.getOWLDataFactory().getOWLObjectProperty(
                TestUtils.createIRI());
    }

    protected OWLDataProperty createDataProperty() {
        return man.getOWLDataFactory()
                .getOWLDataProperty(TestUtils.createIRI());
    }

    protected OWLIndividual createIndividual() {
        return man.getOWLDataFactory().getOWLNamedIndividual(
                TestUtils.createIRI());
    }

    protected OWLDataFactory getDataFactory() {
        return man.getOWLDataFactory();
    }

    @Test
    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = man
                .createOntology(IRI("http://rdfxmltests/ontology"));
        for (OWLAxiom ax : getAxioms()) {
            man.applyChange(new AddAxiom(ontA, ax));
        }
        StringDocumentTarget target = new StringDocumentTarget();
        man.saveOntology(ontA, target);
        man.removeOntology(ontA);
        OWLOntology ontB = man
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        target.toString()));
        Set<OWLLogicalAxiom> AminusB = ontA.getLogicalAxioms();
        AminusB.removeAll(ontB.getAxioms());
        Set<OWLLogicalAxiom> BminusA = ontB.getLogicalAxioms();
        BminusA.removeAll(ontA.getAxioms());
        StringBuilder msg = new StringBuilder();
        if (AminusB.isEmpty() && BminusA.isEmpty()) {
            msg.append("Ontology save/load roundtripp OK.\n");
        } else {
            msg.append("Ontology save/load roundtripping error.\n");
            msg.append("=> " + AminusB.size()
                    + " axioms lost in roundtripping.\n");
            for (OWLAxiom axiom : AminusB) {
                msg.append(axiom.toString() + "\n");
            }
            msg.append("=> " + BminusA.size()
                    + " axioms added after roundtripping.\n");
            for (OWLAxiom axiom : BminusA) {
                msg.append(axiom.toString() + "\n");
            }
        }
        assertTrue(msg.toString(), AminusB.isEmpty() && BminusA.isEmpty());
    }

    protected abstract Set<OWLAxiom> getAxioms();

    protected abstract String getClassExpression();
}
