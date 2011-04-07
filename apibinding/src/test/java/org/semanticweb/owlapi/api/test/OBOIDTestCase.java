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

package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
public class OBOIDTestCase extends TestCase {


    public void testID() {
        String id = "go:123";
        IRI iri = OBOVocabulary.ID2IRI(id);
        assertEquals(iri, IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_123"));
    }

    public void testIDEx() {
        String id = "go:X-123_Y";
        IRI iri = OBOVocabulary.ID2IRI(id);
        assertEquals(iri, IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_X-123_Y"));
    }

    public void testIDWithoutIDSpace() {
        String id = "123";
        IRI iri = OBOVocabulary.ID2IRI(id);
        assertEquals(iri, IRI.create(OBOVocabulary.OBO_IRI_BASE + "123"));
    }
    
    public void testIRI() {
        IRI iri = IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_123");
        String id = OBOVocabulary.IRI2ID(iri);
        assertEquals(id, "go:123");
    }

    public void testIRIEx() {
        IRI iri = IRI.create(OBOVocabulary.OBO_IRI_BASE + "go_X-123_Y");
        String id = OBOVocabulary.IRI2ID(iri);
        assertEquals(id, "go:X-123_Y");
    }

    public void testIRIWithoutIDSpace() {
        IRI iri = IRI.create(OBOVocabulary.OBO_IRI_BASE + "123");
        String id = OBOVocabulary.IRI2ID(iri);
        assertEquals(id, "123");
    }
    
}
