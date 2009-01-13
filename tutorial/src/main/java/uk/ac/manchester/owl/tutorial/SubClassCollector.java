package uk.ac.manchester.owl.tutorial;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLSubClassAxiom;
import org.semanticweb.owl.util.OWLAxiomVisitorAdapter;

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
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 24-April-2007<br>
 * <br>
 */
public class SubClassCollector extends OWLAxiomVisitorAdapter {
    /* Collected axioms */
    private Set<OWLSubClassAxiom> axioms;

    /* Class to look for */
    private OWLClass clazz;

    public SubClassCollector(OWLClass clazz) {
        axioms = new HashSet<OWLSubClassAxiom>();
        this.clazz = clazz;
    }

    @Override
    public void visit(OWLSubClassAxiom axiom) {
        if (axiom.getSubClass().equals(clazz)) {
            axioms.add(axiom);
        }
    }

    public Set<OWLSubClassAxiom> getAxioms() {
        return axioms;
    }
}
