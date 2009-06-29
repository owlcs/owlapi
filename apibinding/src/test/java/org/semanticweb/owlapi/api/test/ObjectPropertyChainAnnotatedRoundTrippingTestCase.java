package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
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
 * Date: 01-Jun-2009
 */
public class ObjectPropertyChainAnnotatedRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        OWLObjectProperty propA = getOWLObjectProperty("propA");
        OWLObjectProperty propB = getOWLObjectProperty("propB");
        OWLObjectProperty propC = getOWLObjectProperty("propC");
        OWLObjectProperty propD = getOWLObjectProperty("propD");
        List<OWLObjectProperty> props = new ArrayList<OWLObjectProperty>();
        props.add(propA);
        props.add(propB);
        props.add(propC);
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        OWLAnnotationProperty annoPropA = getOWLAnnotationProperty("annoPropA");
        OWLAnnotationProperty annoPropB = getOWLAnnotationProperty("annoPropB");
        annos.add(getFactory().getOWLAnnotation(annoPropA, getFactory().getOWLStringLiteral("Test", "en")));
        annos.add(getFactory().getOWLAnnotation(annoPropB, getFactory().getOWLStringLiteral("Test", null)));
        OWLAxiom ax = getFactory().getOWLSubPropertyChainOfAxiom(props, propD, annos);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax);
        return axioms;
    }
}
