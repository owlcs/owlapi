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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 */
public class AverageAssertedNamedSuperclassCount extends DoubleValuedMetric {


    public AverageAssertedNamedSuperclassCount(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
    }


    public String getName() {
        return "Average number of named superclasses";
    }


    @Override
	public Double recomputeMetric() {
        int total = 0;
        int count = 0;
        Set<OWLClass> processedClasses = new HashSet<OWLClass>();
        for (OWLOntology ont : getOntologies()) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                if (!processedClasses.contains(cls)) {
                    count++;
                    int prevTotal = total;
                    processedClasses.add(cls);
                    for (OWLClassExpression desc : cls.getSuperClasses(ont)) {
                        if (!desc.isAnonymous()) {
                            total++;
                        }
                    }
                    if (prevTotal == total) {
                        total++;
                    }

                }
            }
        }
        return ((double) total) / count;
    }


    @Override
	protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange() && chg.getAxiom() instanceof OWLSubClassOfAxiom) {
                return true;
            }
        }
        return false;
    }


    @Override
	protected void disposeMetric() {
    }
}
