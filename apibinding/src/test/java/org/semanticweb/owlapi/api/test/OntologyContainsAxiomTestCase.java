package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
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
 * Date: 07-Dec-2009
 */
public class OntologyContainsAxiomTestCase extends AbstractOWLAPITestCase {

    public void testOntologyContainsPlainAxiom() throws Exception {
        OWLAxiom axiom = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"));
        OWLOntology ont = getOWLOntology("testont");
        getManager().addAxiom(ont, axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiomIgnoreAnnotations(axiom));
    }



    public void testOntologyContainsAnnotatedAxiom() throws Exception {
        OWLLiteral annoLiteral = getFactory().getOWLTypedLiteral("value");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");
        OWLAnnotation anno = getFactory().getOWLAnnotation(annoProp, annoLiteral);
        OWLAxiom axiom = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(anno));
        OWLOntology ont = getOWLOntology("testont");
        getManager().addAxiom(ont, axiom);
        assertTrue(ont.containsAxiom(axiom));
        assertTrue(ont.containsAxiomIgnoreAnnotations(axiom));
        assertFalse(ont.containsAxiom(axiom.getAxiomWithoutAnnotations()));
        assertTrue(ont.containsAxiomIgnoreAnnotations(axiom.getAxiomWithoutAnnotations()));
    }
}
