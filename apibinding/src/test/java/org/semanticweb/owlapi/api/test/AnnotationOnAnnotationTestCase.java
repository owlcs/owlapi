package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;

import java.util.Set;
import java.util.HashSet;
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
 * Date: 02-Jul-2009
 */
public class AnnotationOnAnnotationTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLAnnotation annoOuterOuter1 = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myOuterOuterLabel1"), getFactory().getOWLTypedLiteral("Outer Outer label 1"));
        OWLAnnotation annoOuterOuter2 = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myOuterOuterLabel2"), getFactory().getOWLTypedLiteral("Outer Outer label 2"));
        Set<OWLAnnotation> outerOuterAnnos = new HashSet<OWLAnnotation>();
        outerOuterAnnos.add(annoOuterOuter1);
        outerOuterAnnos.add(annoOuterOuter2);
        OWLAnnotation annoOuter = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myOuterLabel"), getFactory().getOWLTypedLiteral("Outer label"), outerOuterAnnos);
        OWLAnnotation annoInner = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myLabel"), getFactory().getOWLTypedLiteral("Label"), Collections.singleton(annoOuter));
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(annoInner));
        axioms.add(ax);
        return axioms;
    }
}
