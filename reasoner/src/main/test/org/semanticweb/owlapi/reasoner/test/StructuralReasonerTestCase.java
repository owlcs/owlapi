package org.semanticweb.owlapi.reasoner.test;

import org.semanticweb.owlapi.api.test.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Jul-2010
 */
public class StructuralReasonerTestCase extends AbstractOWLAPITestCase {

    public void testClassHierarchy() {
        OWLClass clsX = getOWLClass("X");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsAp = getOWLClass("Ap");
        OWLClass clsB = getOWLClass("B");
        OWLOntology ont = getOWLOntology("ont");
        OWLOntologyManager man = ont.getOWLOntologyManager();
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(getFactory().getOWLThing(), clsX));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsB, clsA));
        man.addAxiom(ont, getFactory().getOWLEquivalentClassesAxiom(clsA, clsAp));
        
        StructuralReasoner reasoner = new StructuralReasoner(ont, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
        testClassHierarchy(reasoner);

        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLThing()));
        testClassHierarchy(reasoner);

        man.removeAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLThing()));
        testClassHierarchy(reasoner);

    }

    private void testClassHierarchy(StructuralReasoner reasoner) {
        OWLClass clsX = getOWLClass("X");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsAp = getOWLClass("Ap");
        OWLClass clsB = getOWLClass("B");

        NodeSet<OWLClass> subsOfA = reasoner.getSubClasses(clsA, true);
        assertTrue(subsOfA.getNodes().size() == 1);
        assertTrue(subsOfA.containsEntity(clsB));

        NodeSet<OWLClass> subsOfAp = reasoner.getSubClasses(clsAp, true);
        assertTrue(subsOfAp.getNodes().size() == 1);
        assertTrue(subsOfAp.containsEntity(clsB));


        Node<OWLClass> topNode = reasoner.getTopClassNode();
        NodeSet<OWLClass> subsOfTop = reasoner.getSubClasses(topNode.getRepresentativeElement(), true);
        assertTrue(subsOfTop.getNodes().size() == 1);
        assertTrue(subsOfTop.containsEntity(clsA));

        NodeSet<OWLClass> descOfTop = reasoner.getSubClasses(topNode.getRepresentativeElement(), false);
        assertTrue(descOfTop.getNodes().size() == 3);
        assertTrue(descOfTop.containsEntity(clsA));
        assertTrue(descOfTop.containsEntity(clsB));
        assertTrue(descOfTop.containsEntity(getFactory().getOWLNothing()));

        NodeSet<OWLClass> supersOfTop = reasoner.getSuperClasses(getFactory().getOWLThing(), false);
        assertTrue(supersOfTop.isEmpty());


        NodeSet<OWLClass> supersOfA = reasoner.getSuperClasses(clsA, false);
        assertTrue(supersOfA.isTopSingleton());
        assertTrue(supersOfA.getNodes().size() == 1);
        assertTrue(supersOfA.containsEntity(getFactory().getOWLThing()));

//        NodeSet<OWLClass> supersOfBottom = reasoner.getSuperClasses(getFactory().getOWLNothing(), true);
//        assertTrue(supersOfBottom.getNodes().size() == 1);
//        assertTrue(supersOfBottom.containsEntity(clsB));

        Node<OWLClass> equivsOfTop = reasoner.getEquivalentClasses(getFactory().getOWLThing());
        assertTrue(equivsOfTop.getEntities().size() == 2);
        assertTrue(equivsOfTop.getEntities().contains(clsX));
    }

}
