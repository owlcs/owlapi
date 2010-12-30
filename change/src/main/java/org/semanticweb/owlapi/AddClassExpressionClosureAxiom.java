package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jul-2007<br><br>
 * <p/>
 * This composite change adds a 'closure' axiom to an ontology for
 * a given class and object property.  In this case, a closure axiom
 * is defined for a given class, A, and object property, P, to be a subclass axiom,
 * whose subclass is class A, and whose superclass is a universal restriction
 * along the property, P, whose filler is the union of any other existential
 * (including hasValue restrictions - i.e. nominals) restriction fillers that are the
 * superclasses of class A.
 * <p/>
 * This code is based on the tutorial examples by Sean Bechhofer (see the tutoral module).
 */
public class AddClassExpressionClosureAxiom extends AbstractCompositeOntologyChange {

    private OWLClass cls;

    private OWLObjectPropertyExpression property;

    private Set<OWLOntology> ontologies;

    private OWLOntology targetOntology;

    private List<OWLOntologyChange> changes;


    /**
     * Creates a composite change that will add a closure axiom for a given class along a
     * specified property.
     *
     * @param dataFactory    The data factory that should be used to create the necessary objects
     * @param cls            The class for which the closure axiom will be generated
     * @param property       The property that the closure axiom will act along
     * @param ontologies     The ontologies that will be examined for subclass axioms
     * @param targetOntology The target ontology that changes will be applied to.
     */
    public AddClassExpressionClosureAxiom(OWLDataFactory dataFactory, OWLClass cls,
                                           OWLObjectPropertyExpression property, Set<OWLOntology> ontologies,
                                           OWLOntology targetOntology) {
        super(dataFactory);
        this.cls = cls;
        this.property = property;
        this.ontologies = ontologies;
        this.targetOntology = targetOntology;
        generateChanges();
    }


    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        // We collect all of the fillers for existential restrictions along
        // the target property and all of the fillers for hasValue restrictions
        // as nominals
        FillerCollector collector = new FillerCollector();
        for (OWLOntology ont : ontologies) {
            for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(cls)) {
                ax.getSuperClass().accept(collector);
            }
        }
        Set<OWLClassExpression> fillers = collector.getFillers();
        if (fillers.isEmpty()) {
            return;
        }
        OWLClassExpression closureAxiomFiller = getDataFactory().getOWLObjectUnionOf(fillers);
        OWLClassExpression closureAxiomDesc = getDataFactory().getOWLObjectAllValuesFrom(property, closureAxiomFiller);
        changes.add(new AddAxiom(targetOntology, getDataFactory().getOWLSubClassOfAxiom(cls, closureAxiomDesc)));
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }


    private class FillerCollector extends OWLClassExpressionVisitorAdapter {

        private Set<OWLClassExpression> fillers;


        public FillerCollector() {
            fillers = new HashSet<OWLClassExpression>();
        }


        public Set<OWLClassExpression> getFillers() {
            return fillers;
        }


//        public void reset() {
//            fillers.clear();
//        }


        @Override
		public void visit(OWLObjectSomeValuesFrom desc) {
            if (desc.getProperty().equals(property)) {
                fillers.add(desc.getFiller());
            }
        }


        @Override
		public void visit(OWLObjectHasValue desc) {
            if (desc.getProperty().equals(property)) {
                fillers.add(getDataFactory().getOWLObjectOneOf(CollectionFactory.createSet(desc.getValue())));
            }
        }
    }
}
