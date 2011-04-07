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

import java.util.HashSet;
import java.util.Set;

import org.coode.xml.IllegalElementNameException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-Dec-2009
 */
public class NoQNameTestCase extends AbstractAxiomsRoundTrippingTestCase {


    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLNamedIndividual indA = getFactory().getOWLNamedIndividual(IRI.create("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395A"));
        OWLNamedIndividual indB = getFactory().getOWLNamedIndividual(IRI.create("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395B"));
        OWLObjectProperty property = getFactory().getOWLObjectProperty(IRI.create("http://example.com/place/123"));
        axioms.add(getFactory().getOWLObjectPropertyAssertionAxiom(property, indA, indB));
        return axioms;
    }

    @Override
    public void testRDFXML() throws Exception {
        try {
            super.testRDFXML();
            fail("Expected an exception specifying that a QName could not be generated");
        }
        catch (OWLOntologyStorageException e) {
            if (e.getCause() instanceof IllegalElementNameException) {
                System.out.println("Caught IllegalElementNameException as expected: " + e.getMessage());
            }
            else {
                throw e;
            }
        }
    }
}
