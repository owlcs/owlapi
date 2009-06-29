package uk.ac.manchester.cs.owl.explanation.ordering;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;

import java.util.*;
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Jan-2008<br><br>
 *
 * Orders an explanation in a flat list, sorting axioms
 * alphabetically.
 */
public class AlphaExplanationOrderer implements ExplanationOrderer {

    private OWLObjectRenderer renderer;


    public AlphaExplanationOrderer(OWLObjectRenderer renderer) {
        this.renderer = renderer;
    }

    public ExplanationTree getOrderedExplanation(OWLAxiom entailment, Set<OWLAxiom> axioms) {
        EntailedAxiomTree root = new EntailedAxiomTree(entailment);
        List<OWLAxiom> sortedAxioms = new ArrayList<OWLAxiom>();
        Collections.sort(sortedAxioms, new Comparator<OWLAxiom>() {

            public int compare(OWLAxiom o1, OWLAxiom o2) {
                return renderer.render(o1).compareTo(renderer.render(o2));
            }
        });
        for(OWLAxiom ax : sortedAxioms) {
            root.addChild(new ExplanationTree(ax));
        }
        return root;
    }
}
