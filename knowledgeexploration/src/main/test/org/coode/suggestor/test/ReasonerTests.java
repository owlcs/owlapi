/*
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * Author: Nick Drummond
 * http://www.cs.man.ac.uk/~drummond/
 * Bio Health Informatics Group
 * The University Of Manchester
 */
package org.coode.suggestor.test;

import junit.framework.TestCase;
import org.coode.suggestor.util.ReasonerHelper;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;

import java.util.Arrays;

public class ReasonerTests extends TestCase {

    public static final String JFACT_FACTORY =        "uk.ac.manchester.cs.jfact.JFactFactory";
//    public static final String HERMIT_FACTORY =       "org.semanticweb.HermiT.Reasoner$ReasonerFactory";
//    public static final String PELLET_FACTORY =       "com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory";

    public void testReasoner() throws Exception{



            OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
            OWLOntology ont = mngr.createOntology();

            final OWLReasonerFactory fac = (OWLReasonerFactory) Class.forName(JFACT_FACTORY).newInstance();
            OWLReasoner r = fac.createNonBufferingReasoner(ont);

            OWLDataFactory df = mngr.getOWLDataFactory();

            OWLClass a = df.getOWLClass(IRI.create("http://example.com/a"));
            OWLClass b = df.getOWLClass(IRI.create("http://example.com/b"));
            OWLClass c = df.getOWLClass(IRI.create("http://example.com/c"));
            OWLObjectProperty p = df.getOWLObjectProperty(IRI.create("http://example.com/p"));

            mngr.applyChange(new AddAxiom(ont, df.getOWLSubClassOfAxiom(a, df.getOWLObjectSomeValuesFrom(p, b))));
            mngr.applyChange(new AddAxiom(ont, df.getOWLSubClassOfAxiom(c, df.getOWLThing())));

            NodeSet<OWLClass> subs = r.getSubClasses(df.getOWLObjectSomeValuesFrom(p, b), true);

            assertEquals(1, subs.getNodes().size());
            assertTrue(subs.containsEntity(a));

            subs = r.getSubClasses(df.getOWLObjectSomeValuesFrom(p, df.getOWLThing()), true);

            assertEquals(1, subs.getNodes().size());
            assertTrue(subs.containsEntity(a));

            subs = r.getSubClasses(df.getOWLObjectSomeValuesFrom(df.getOWLTopObjectProperty(), df.getOWLThing()), true);

            assertEquals(3, subs.getNodes().size());
            assertTrue(subs.containsEntity(a));
            assertTrue(subs.containsEntity(b));
            assertTrue(subs.containsEntity(c));
        
    }

//    public void testReasoner2(){
//        try {
//            OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
//            OWLOntology ont = mngr.createOntology();
//            OWLDataFactory df = mngr.getOWLDataFactory();
//
//            final OWLReasonerFactory fac = (OWLReasonerFactory) Class.forName(HERMIT_FACTORY).newInstance();
//            OWLReasoner r = fac.createNonBufferingReasoner(ont);
//
//            OWLClass a = df.getOWLClass(IRI.create("http://example.com/a"));
//
//            mngr.applyChange(new AddAxiom(ont, df.getOWLSubClassOfAxiom(a, df.getOWLThing())));
//
//            r.getSubClasses(df.getOWLDataMinCardinality(1, df.getOWLTopDataProperty()), true);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//
//    public void testReasoner3(){
//        try {
//            OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
//            OWLOntology ont = mngr.createOntology();
//            OWLDataFactory df = mngr.getOWLDataFactory();
//
//            final OWLReasonerFactory fac = (OWLReasonerFactory) Class.forName(HERMIT_FACTORY).newInstance();
//            OWLReasoner r = fac.createNonBufferingReasoner(ont);
//
//            OWLDataProperty p = df.getOWLDataProperty(IRI.create("http://example.com/p"));
//
//            // just so p is known in the ontology
//            mngr.applyChange(new AddAxiom(ont, df.getOWLFunctionalDataPropertyAxiom(p)));
//
//            assertTrue(r.isEntailed(df.getOWLSubClassOfAxiom(df.getOWLDataSomeValuesFrom(p, df.getIntegerOWLDatatype()),
//                                                             df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()))));
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }

    public void testReasoner4() throws Exception{

            OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
            OWLOntology ont = mngr.createOntology();

            final OWLReasonerFactory fac = (OWLReasonerFactory) Class.forName(JFACT_FACTORY).newInstance();
            OWLReasoner r = fac.createNonBufferingReasoner(ont);

            assertFalse(r.getTopDataPropertyNode().getEntities().isEmpty());
       
    }


    // Hermit and pellet only return named object properties
    // JFact includes all inverses
//    public void testInverseObjPropsReturnedByGetSubProperties(){
//        try {
//            OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
//            OWLOntology ont = mngr.createOntology();
//            OWLDataFactory df = mngr.getOWLDataFactory();
//
//            final OWLReasonerFactory fac = (OWLReasonerFactory) Class.forName(PELLET_FACTORY).newInstance();
//            OWLReasoner reasoner = fac.createNonBufferingReasoner(ont);
//
//            OWLObjectProperty p = df.getOWLObjectProperty(IRI.create("http://example.com/p"));
//            OWLObjectProperty q = df.getOWLObjectProperty(IRI.create("http://example.com/q"));
//            OWLObjectProperty r = df.getOWLObjectProperty(IRI.create("http://example.com/r"));
//
//            mngr.applyChanges(Arrays.asList(new AddAxiom(ont, df.getOWLFunctionalObjectPropertyAxiom(p)),
//                                            new AddAxiom(ont, df.getOWLFunctionalObjectPropertyAxiom(q)),
//                                            new AddAxiom(ont, df.getOWLFunctionalObjectPropertyAxiom(df.getOWLObjectInverseOf(r)))));
//
//
//            final NodeSet<OWLObjectPropertyExpression> subs = reasoner.getSubObjectProperties(df.getOWLTopObjectProperty(), true);
//
//            System.out.println(subs);
//
//            assertEquals(6, subs.getNodes().size());
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
}