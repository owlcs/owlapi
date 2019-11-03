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
package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntologyID;

public class OntologyIDTestCase extends TestBase {

    private static final String TEST_ONTOLOGY_IRI_STRING = "http://test.it/check1";

    @Test
    public void shouldFindSameHashCode() {
        IRI iri1 = iri();
        IRI iri2 = iri();
        assertEquals(iri1.hashCode(), iri2.hashCode());
        assertEquals(iri1, iri2);
    }

    @Test
    public void shouldFindSameHashCodeForIDs() {
        IRI iri1 = iri();
        IRI iri2 = iri();
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = df.getOWLOntologyID(iri1);
        OWLOntologyID id2 = df.getOWLOntologyID(iri2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void shouldFindSameHashCodeForIDs2() {
        IRI iri1 = iri();
        IRI iri2 = iri();
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = df.getOWLOntologyID(iri1);
        OWLOntologyID id2 = df.getOWLOntologyID(iri2);
        assertEquals(id1.hashCode(), id2.hashCode());
        assertEquals(id1, id2);
    }

    @Test
    public void testUnequalIdsUnequal() {
        OWLOntologyID id1 = df.getOWLOntologyID(IRI("http://www.w3.org/", "foo"));
        OWLOntologyID id2 = df.getOWLOntologyID(IRI("http://www.w3.org/", "bar"));
        assertNotEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1, id2);
    }

    // this is an experiment, if the manager were to keep all versions of an
    // ontology together in a multimap or something
    @Test
    public void testVersionedIDComparisons() {
        IRI iri1 = iri();
        IRI iri2 = iri();
        assertEquals(iri1.hashCode(), iri2.hashCode());
        assertEquals(iri1, iri2);
        OWLOntologyID unversionedID = df.getOWLOntologyID(iri1);
        IRI version1IRI = IRI(TEST_ONTOLOGY_IRI_STRING + "/", "version1");
        OWLOntologyID version1ID = df.getOWLOntologyID(iri2, version1IRI);
        assertEquals(unversionedID.getOntologyIRI(), version1ID.getOntologyIRI());
        assertNotEquals(unversionedID.getVersionIRI(), version1ID.getVersionIRI());
        assertNotEquals(unversionedID.hashCode(), version1ID.hashCode());
        assertNotEquals(unversionedID, version1ID);
        OWLOntologyID duplicateVersion1ID = df.getOWLOntologyID(iri(), version1IRI);
        assertEquals(version1ID, duplicateVersion1ID);
        OWLOntologyID differentBasedVersion1ID =
            df.getOWLOntologyID(IRI(TEST_ONTOLOGY_IRI_STRING + "-of-doom", ""), version1IRI);
        assertNotEquals("version1 of two base IRIs", version1ID, differentBasedVersion1ID);
        IRI version2IRI = IRI(TEST_ONTOLOGY_IRI_STRING + "/", "version2");
        IRI iri3 = iri();
        OWLOntologyID version2ID = df.getOWLOntologyID(iri3, version2IRI);
        assertNotEquals("version1 vs version2", version1ID.hashCode(), version2ID.hashCode());
        assertNotEquals("version1 vs version2", version1ID, version2ID);
    }

    protected IRI iri() {
        return IRI(TEST_ONTOLOGY_IRI_STRING, "");
    }
}
