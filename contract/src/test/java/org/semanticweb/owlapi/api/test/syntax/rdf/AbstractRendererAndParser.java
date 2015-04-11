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
package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLStorerFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public abstract class AbstractRendererAndParser extends TestBase {

    @Before
    public void setUpManager() {
        m.getOntologyFactories().add(
                new OWLOntologyFactoryImpl(new OWLOntologyBuilder() {
                    @Nonnull
                    @Override
                    public OWLOntology createOWLOntology(@Nonnull OWLOntologyManager manager,
                                                         @Nonnull OWLOntologyID ontologyID) {
                        return new OWLOntologyImpl(manager, ontologyID);
                    }
                }));
        m.getOntologyStorers().add(new RDFXMLStorerFactory());
    }

    @Test
    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = m.createOntology(IRI("http://rdfxmltests/ontology"));
        for (OWLAxiom ax : getAxioms()) {
            assert ax != null;
            m.applyChange(new AddAxiom(ontA, ax));
        }
        OWLOntology ontB = roundTrip(ontA);
        Set<OWLLogicalAxiom> aMinusB = ontA.getLogicalAxioms();
        aMinusB.removeAll(ontB.getAxioms());
        Set<OWLLogicalAxiom> bMinusA = ontB.getLogicalAxioms();
        bMinusA.removeAll(ontA.getAxioms());
        StringBuilder msg = new StringBuilder();
        if (aMinusB.isEmpty() && bMinusA.isEmpty()) {
            msg.append("Ontology save/load roundtrip OK.\n");
        } else {
            msg.append("Ontology save/load roundtripping error.\n");
            msg.append("=> ").append(aMinusB.size())
                    .append(" axioms lost in roundtripping.\n");
            for (OWLAxiom axiom : aMinusB) {
                msg.append(axiom + "\n");
            }
            msg.append("=> ").append(bMinusA.size())
                    .append(" axioms added after roundtripping.\n");
            for (OWLAxiom axiom : bMinusA) {
                msg.append(axiom + "\n");
            }
        }
        assertTrue(msg.toString(), aMinusB.isEmpty() && bMinusA.isEmpty());
    }

    protected abstract Set<OWLAxiom> getAxioms();
}
