package org.semanticweb.owlapi.metrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.NamedConjunctChecker;


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


    @Override
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


    @Override
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


    @Override
	protected void disposeMetric() {
    }
}
