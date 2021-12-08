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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;

class OntologyIDTestCase extends TestBase {

    static final String TEST_ONTOLOGY_IRI_STRING = "http://test.it/check1";
    IRI iri1 = iri();
    IRI iri2 = iri();

    @Test
    void shouldFindSameHashCode() {
        assertEquals(iri1.hashCode(), iri2.hashCode());
        assertEquals(iri1, iri2);
    }

    @Test
    void shouldFindSameHashCodeForIDs() {
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = new OWLOntologyID(optional(iri1), emptyOptional(IRI.class));
        OWLOntologyID id2 = new OWLOntologyID(optional(iri2), emptyOptional(IRI.class));
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void shouldFindSameHashCodeForIDs2() {
        assertEquals(iri1.hashCode(), iri2.hashCode());
        OWLOntologyID id1 = new OWLOntologyID(optional(iri1), emptyOptional(IRI.class));
        OWLOntologyID id2 = new OWLOntologyID(optional(iri2), emptyOptional(IRI.class));
        assertEquals(id1.hashCode(), id2.hashCode());
        assertEquals(id1, id2);
    }

    @Test
    void testUnequalIdsUnequal() {
        OWLOntologyID id1 =
            new OWLOntologyID(optional(iri("http://www.w3.org/", "foo")), emptyOptional(IRI.class));
        OWLOntologyID id2 =
            new OWLOntologyID(optional(iri("http://www.w3.org/", "bar")), emptyOptional(IRI.class));
        assertNotEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1, id2);
    }

    // this is an experiment, if the manager were to keep all versions of an
    // ontology together in a multimap or something
    @Test
    void testVersionedIDComparisons() {
        assertEquals(iri1.hashCode(), iri2.hashCode());
        assertEquals(iri1, iri2);
        OWLOntologyID unversionedID = new OWLOntologyID(optional(iri1), emptyOptional(IRI.class));
        IRI version1IRI = iri(TEST_ONTOLOGY_IRI_STRING + "/", "version1");
        OWLOntologyID version1ID = new OWLOntologyID(optional(iri2), optional(version1IRI));
        assertEquals(unversionedID.getOntologyIRI(), version1ID.getOntologyIRI());
        assertNotEquals(unversionedID.getVersionIRI(), version1ID.getVersionIRI());
        assertNotEquals(unversionedID.hashCode(), version1ID.hashCode());
        assertNotEquals(unversionedID, version1ID);
        OWLOntologyID duplicateVersion1ID =
            new OWLOntologyID(optional(iri(TEST_ONTOLOGY_IRI_STRING, "")), optional(version1IRI));
        assertEquals(version1ID, duplicateVersion1ID);
        OWLOntologyID differentBasedVersion1ID = new OWLOntologyID(
            optional(iri(TEST_ONTOLOGY_IRI_STRING + "-of-doom", "")), optional(version1IRI));
        assertNotEquals(version1ID, differentBasedVersion1ID);
        IRI version2IRI = iri(TEST_ONTOLOGY_IRI_STRING + "/", "version2");
        IRI iri3 = iri();
        OWLOntologyID version2ID = new OWLOntologyID(optional(iri3), optional(version2IRI));
        assertNotEquals(version1ID.hashCode(), version2ID.hashCode());
        assertNotEquals(version1ID, version2ID);
    }

    protected IRI iri() {
        return iri(TEST_ONTOLOGY_IRI_STRING, "");
    }
}
