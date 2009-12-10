package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.List;
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Aug-2007<br><br>
 * <p/>
 * Counts the number of "hidden" GCIs in an ontology imports
 * closure.  A GCI is regarded to be a "hidden" GCI if it is
 * essentially introduce via an equivalent class axiom and a
 * subclass axioms where the LHS of the subclass axiom is nameed.
 * For example, A equivalentTo p some C, A subClassOf B results
 * in a "hidden" GCI.
 */
public class HiddenGCICount extends IntegerValuedMetric {


    public HiddenGCICount(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    protected void disposeMetric() {
    }


    protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange()) {
                if (chg.getAxiom() instanceof OWLEquivalentClassesAxiom ||
                        chg.getAxiom() instanceof OWLSubClassOfAxiom) {
                    return true;
                }
            }
        }
        return false;
    }


    protected Integer recomputeMetric() {
        Set<OWLClass> processed = new HashSet<OWLClass>();
        Set<OWLClass> result = new HashSet<OWLClass>();
        for (OWLOntology ont : getOntologies()) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                if (!processed.contains(cls)) {
                    processed.add(cls);
                } else {
                    continue;
                }
                boolean foundEquivalentClassesAxiom = false;
                boolean foundSubClassAxiom = false;
                for (OWLOntology o : getOntologies()) {
                    if (!foundEquivalentClassesAxiom) {
                        foundEquivalentClassesAxiom = !o.getEquivalentClassesAxioms(cls).isEmpty();
                    }
                    if (!foundSubClassAxiom) {
                        foundSubClassAxiom = !o.getSubClassAxiomsForSubClass(cls).isEmpty();
                    }
                    if (foundSubClassAxiom && foundEquivalentClassesAxiom) {
                        result.add(cls);
                        break;
                    }
                }
            }
        }
        return result.size();
    }


    public String getName() {
        return "Hidden GCI Count";
    }
}
