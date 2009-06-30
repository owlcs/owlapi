package uk.ac.manchester.owl.owlapi.tutorial;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

/*
 * Copyright (C) 2007, University of Manchester
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
 * <p>A visitor that simply collects any subclass axioms that have the given class
 * as the subclass.</p>
 * <p/>
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 24-April-2007<br>
 * <br>
 */
public class SubClassCollector extends OWLAxiomVisitorAdapter {
    /* Collected axioms */
    private Set<OWLSubClassOfAxiom> axioms;

    /* Class to look for */
    private OWLClass clazz;

    public SubClassCollector(OWLClass clazz) {
        axioms = new HashSet<OWLSubClassOfAxiom>();
        this.clazz = clazz;
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        if (axiom.getSubClass().equals(clazz)) {
            axioms.add(axiom);
        }
    }

    public Set<OWLSubClassOfAxiom> getAxioms() {
        return axioms;
    }
}
