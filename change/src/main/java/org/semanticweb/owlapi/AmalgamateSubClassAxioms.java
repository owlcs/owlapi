package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Aug-2007<br><br>
 * <p/>
 * Given a set of ontologies S, for each ontology, O, in S, this change
 * combines multiple subclass axioms with a common left hand side into
 * one subclass axiom.  For example, given A subClassOf B, A subClassOf C,
 * this change will remove these two axioms and replace them by adding
 * one subclass axiom, A subClassOf (B and C).
 */
public class AmalgamateSubClassAxioms extends AbstractCompositeOntologyChange {

    private List<OWLOntologyChange> changes;


    public AmalgamateSubClassAxioms(Set<OWLOntology> ontologies, OWLDataFactory dataFactory) {
        super(dataFactory);
        changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                Set<OWLSubClassOfAxiom> axioms = ont.getSubClassAxiomsForSubClass(cls);
                if (axioms.size() > 1) {
                    Set<OWLClassExpression> superClasses = new HashSet<OWLClassExpression>();
                    for (OWLSubClassOfAxiom ax : axioms) {
                        changes.add(new RemoveAxiom(ont, ax));
                        superClasses.add(ax.getSuperClass());
                    }
                    OWLClassExpression combinedSuperClass = getDataFactory().getOWLObjectIntersectionOf(superClasses);
                    changes.add(new AddAxiom(ont, getDataFactory().getOWLSubClassOfAxiom(cls, combinedSuperClass)));
                }
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
