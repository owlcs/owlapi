package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;
import org.coode.xml.IllegalElementNameException;
import org.semanticweb.owlapi.model.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
