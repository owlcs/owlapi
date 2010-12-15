package org.semanticweb.owlapi.metrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


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


    @Override
	protected void disposeMetric() {
    }


    @Override
	protected boolean isMetricInvalidated(List<? extends OWLOntologyChange> changes) {
        for (OWLOntologyChange chg : changes) {
            if (chg.isAxiomChange()) {
                if (chg.getAxiom() instanceof OWLEquivalentClassesAxiom || chg.getAxiom() instanceof OWLSubClassOfAxiom) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
	protected Integer recomputeMetric() {
        Set<OWLClass> processed = new HashSet<OWLClass>();
        Set<OWLClass> result = new HashSet<OWLClass>();
        for (OWLOntology ont : getOntologies()) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                if (!processed.contains(cls)) {
                    processed.add(cls);
                }
                else {
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
