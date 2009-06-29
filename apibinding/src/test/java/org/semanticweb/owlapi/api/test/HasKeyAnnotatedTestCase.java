package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Set;
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
 * Date: 28-May-2009
 */
public class HasKeyAnnotatedTestCase extends AbstractAxiomsRoundTrippingTestCase {


    protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnnotationProperty ap = getFactory().getOWLAnnotationProperty(IRI.create("http://annotation.com/annos#prop"));
        OWLLiteral val = getFactory().getOWLStringLiteral("Test", null);
        OWLAnnotation anno = getFactory().getOWLAnnotation(ap, val);
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        annos.add(anno);
        OWLClassExpression ce = getOWLClass("A");
        OWLObjectProperty p1 = getOWLObjectProperty("p1");
        OWLObjectProperty p2 = getOWLObjectProperty("p2");
        OWLObjectProperty p3 = getOWLObjectProperty("p3");

        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        props.add(p1);
        props.add(p2);
        props.add(p3);
        OWLHasKeyAxiom ax = getFactory().getOWLHasKeyAxiom(ce, props, annos);
        Set<OWLAxiom> axs = new HashSet<OWLAxiom>();
        axs.add(ax);
        axs.add(getFactory().getOWLDeclarationAxiom(ap));
        axs.add(getFactory().getOWLDeclarationAxiom(p1));
        axs.add(getFactory().getOWLDeclarationAxiom(p2));
        axs.add(getFactory().getOWLDeclarationAxiom(p3));
        return axs;
    }
}
