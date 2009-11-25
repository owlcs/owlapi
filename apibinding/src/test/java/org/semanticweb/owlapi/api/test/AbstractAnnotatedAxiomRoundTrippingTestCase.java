package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLEntityCollector;

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
public abstract class AbstractAnnotatedAxiomRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    final protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLLiteral lit = getFactory().getOWLStringLiteral("Test", null);
        OWLAnnotation anno1 = getFactory().getOWLAnnotation(prop, lit);
        OWLAnnotationProperty prop2 = getOWLAnnotationProperty("prop2");
        OWLAnnotation anno2 = getFactory().getOWLAnnotation(prop2, lit);
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        annos.add(anno1);
        annos.add(anno2);
        OWLAxiom ax = getMainAxiom(annos);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax.getAnnotatedAxiom(annos));
        OWLEntityCollector entityCollector = new OWLEntityCollector();
        ax.accept(entityCollector);
        for(OWLEntity ent : entityCollector.getObjects()) {
            axioms.add(getFactory().getOWLDeclarationAxiom(ent));
        }
        return axioms;
    }

    protected abstract OWLAxiom getMainAxiom(Set<OWLAnnotation> annos);
}
