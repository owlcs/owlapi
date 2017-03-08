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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.util.OWLOntologyIRIChanger;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings({"javadoc"})
public class ChangeOntologyURITestCase extends TestBase {

    @Test
    public void testChangeURI() throws OWLOntologyCreationException {
        IRI oldIRI = IRI("http://www.semanticweb.org/ontologies/", "ontA");
        IRI newIRI = IRI("http://www.semanticweb.org/ontologies/", "ontB");
        OWLOntology ont = m.createOntology(oldIRI);
        OWLOntology importingOnt =
                        m.createOntology(IRI("http://www.semanticweb.org/ontologies/", "ontC"));
        m.applyChange(new AddImport(importingOnt,
                        df.getOWLImportsDeclaration(get(ont.getOntologyID().getOntologyIRI()))));
        assertTrue(m.contains(oldIRI));
        OWLOntologyIRIChanger changer = new OWLOntologyIRIChanger(m);
        m.applyChanges(changer.getChanges(ont, newIRI));
        assertFalse(m.contains(oldIRI));
        assertTrue(m.contains(newIRI));
        assertTrue(m.ontologies().anyMatch(o -> o.equals(ont)));
        assertTrue(m.directImports(importingOnt).anyMatch(o -> o.equals(ont)));
        OWLOntology ontology = m.getOntology(newIRI);
        assertNotNull("ontology should not be null", ontology);
        assertEquals(ontology, ont);
        assertEquals(ontology.getOntologyID().getOntologyIRI().get(), newIRI);
        assertTrue(m.importsClosure(importingOnt).anyMatch(o -> o.equals(ont)));
        assertNotNull("ontology should not be null", m.getOntologyDocumentIRI(ont));
        // Document IRI will still be the same (in this case the old ont URI)
        assertEquals(m.getOntologyDocumentIRI(ont), oldIRI);
        assertNotNull("ontology format should not be null", ont.getFormat());
    }

    @Test
    public void shouldCheckContents() throws OWLOntologyCreationException {
        m.createOntology(IRI.create("http://www.test.com/", "123"));
        OWLOntologyID anonymousId = m1.createOntology().getOntologyID();
        m.contains(anonymousId);
    }
}
