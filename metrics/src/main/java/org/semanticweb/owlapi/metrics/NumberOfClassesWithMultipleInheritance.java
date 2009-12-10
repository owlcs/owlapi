package org.semanticweb.owlapi.metrics;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.NamedConjunctChecker;

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
 * Date: 27-Jul-2007<br><br>
 */
public class NumberOfClassesWithMultipleInheritance extends IntegerValuedMetric {


    public NumberOfClassesWithMultipleInheritance(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    public String getName() {
        return "Number of classes with asserted multiple inheritance";
    }


    public Integer recomputeMetric() {
        Set<OWLClass> processed = new HashSet<OWLClass>();
        Set<OWLClass> clses = new HashSet<OWLClass>();
        NamedConjunctChecker checker = new NamedConjunctChecker();
        for (OWLOntology ont : getOntologies()) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                if (processed.contains(cls)) {
                    continue;
                }
                processed.add(cls);
                int count = 0;
                for (OWLClassExpression sup : cls.getSubClasses(getOntologies())) {
                    if (checker.hasNamedConjunct(sup)) {
                        count++;
                    }
                    if (count > 1) {
                        clses.add(cls);
                        break;
                    }
                }
            }
        }
        return clses.size();
    }


    protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange()) {
                if (change.getAxiom() instanceof OWLSubClassOfAxiom) {
                    return true;
                }
            }
        }
        return false;
    }


    protected void disposeMetric() {
    }
}
