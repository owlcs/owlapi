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

import org.coode.suggestor.api.FillerSuggestor;
import org.coode.suggestor.api.PropertySuggestor;
import org.coode.suggestor.impl.SuggestorFactory;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FillerSuggestorTests extends AbstractSuggestorTest {

    private OWLClass ca, cb, cc, cd, ce, ca1, cb1, cc1;
    private OWLObjectProperty oa, ob, ob1;
    private OWLDataProperty da;

    private void createEntities() {

        ca = createClass("ca");
        ca1 = createClass("ca1");
        cb = createClass("cb");
        cb1 = createClass("cb1");
        cc = createClass("cc");
        cc1 = createClass("cc1");
        cd = createClass("cd");
        ce = createClass("ce");

        oa = createObjectProperty("oa");
        ob = createObjectProperty("ob");
        ob1 = createObjectProperty("ob1");

        da = createDataProperty("da");
    }

    /*
     * ca -> oa some cb     // "redundant"
     * ca -> oa some cb1
     * ca -> ob1 some cb
     * ca -> da some integer
     * cd == oa some cb
     * cd -> oa some ce
     * cb1 -> cb
     * cc1 -> cc
     * ca1 -> ca
     * ob1 -> ob
     */
    private OWLOntology createModelA() throws Exception{
OWLOntology ont=createOntology();
        createEntities();

        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cb1, cb)));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca1, ca)));
        changes.add(new AddAxiom(ont, df.getOWLSubObjectPropertyOfAxiom(ob1, ob)));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectSomeValuesFrom(oa, cb))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLDataSomeValuesFrom(da, df.getIntegerOWLDatatype()))));
        changes.add(new AddAxiom(ont, df.getOWLEquivalentClassesAxiom(cd, df.getOWLObjectSomeValuesFrom(oa, cb))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cd, df.getOWLObjectSomeValuesFrom(oa, ce))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectSomeValuesFrom(oa, cb1))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectSomeValuesFrom(ob1, cb))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cc1, cc)));

        mngr.applyChanges(changes);
        return ont;
    }

    public void testIsCurrentFiller() throws Exception{

       OWLOntology ont= createModelA();



            OWLReasoner r = ((OWLReasonerFactory) Class.forName(DEFAULT_REASONER_FACTORY).newInstance()).createNonBufferingReasoner(ont);


            SuggestorFactory fac = new SuggestorFactory(r);
           PropertySuggestor ps = fac.getPropertySuggestor();
           FillerSuggestor fs = fac.getFillerSuggestor();


        OWLDataFactory f=fs.getReasoner().getRootOntology().getOWLOntologyManager().getOWLDataFactory();
        System.out.println("FillerSuggestorTests.testIsCurrentFiller()\n"+
        fs.getReasoner().getRootOntology().getAxioms().toString().replace(",", ",\n\t"));

        System.out.println("FillerSuggestorTests.testIsCurrentFiller() subclasses of thing \n"+fs.getReasoner().getSubClasses(f.getOWLThing(), true));
        System.out.println("FillerSuggestorTests.testIsCurrentFiller() subclasses of thing \n"+fs.getReasoner().getSubClasses(f.getOWLThing(), false));
        //TODO add a generic reasoner to implement these tests
       // OWLReasoner test=new JFactFactory().createReasoner(fs.getReasoner().getRootOntology());
//        System.out.println("FillerSuggestorTests.testIsCurrentFiller() subclasses of thing test \n"+test.getSubClasses(f.getOWLThing(), true));
//        System.out.println("FillerSuggestorTests.testIsCurrentFiller() subclasses of thing test\n"+test.getSubClasses(f.getOWLThing(), false));
        assertTrue(fs.isCurrent(ca, oa, cb1, true));
        assertFalse(fs.isCurrent(ca, oa, cb, true));
        assertFalse(fs.isCurrent(ca, oa, df.getOWLObjectIntersectionOf(cb, df.getOWLObjectSomeValuesFrom(ob, cc)), true));
        assertFalse(fs.isCurrent(ca, oa, cc, true));
        assertTrue(fs.isCurrent(ca, oa, ce, true)); // from interaction with d
        assertTrue(fs.isCurrent(ca, da, df.getIntegerOWLDatatype(), true));
        assertFalse(fs.isCurrent(ca, da, df.getOWLDatatypeRestriction(df.getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral(2)), true));
        assertFalse(fs.isCurrent(ca, oa, df.getOWLThing(), true));

        assertTrue(fs.isCurrent(ca, oa, cb1));
        assertTrue(fs.isCurrent(ca, oa, cb));
        assertFalse(fs.isCurrent(ca, oa, df.getOWLObjectIntersectionOf(cb, df.getOWLObjectSomeValuesFrom(ob, cc))));
        assertFalse(fs.isCurrent(ca, oa, cc));
        assertTrue(fs.isCurrent(ca, oa, df.getOWLThing()));
        assertTrue(fs.isCurrent(ca, da, df.getIntegerOWLDatatype()));
        assertTrue(fs.isCurrent(ca, da, df.getTopDatatype()));

        // inherited
        assertTrue(fs.isCurrent(ca1, oa, cb1, true));
    }

    /*
     * ca -> not(oa some cb)
     * cb1 -> cb
     * cc1 -> cc
     */
    private OWLOntology createModelB() throws Exception{
OWLOntology ont=createOntology();
        createEntities();

        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectComplementOf(df.getOWLObjectSomeValuesFrom(oa, cb)))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cb1, cb)));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cc1, cc)));

        mngr.applyChanges(changes);
        return ont;
    }

    public void testIsPossibleFiller() throws Exception{



            OWLOntology ont = createModelB();


            OWLReasoner r = ((OWLReasonerFactory) Class.forName(DEFAULT_REASONER_FACTORY).newInstance()).createNonBufferingReasoner(ont);


            SuggestorFactory fac = new SuggestorFactory(r);
           PropertySuggestor ps = fac.getPropertySuggestor();
           FillerSuggestor fs = fac.getFillerSuggestor();

        assertTrue(fs.isPossible(ca, oa, ca));
        assertFalse(fs.isPossible(ca, oa, cb));
        assertTrue(fs.isPossible(ca, oa, cc));
        assertTrue(fs.isPossible(ca, oa, cc1));
        assertTrue(fs.isPossible(ca, oa, df.getOWLThing()));
    }

    /*
     * ca -> oa some cb
     * ca -> ob some cc
     * ca -> ob some cd
     * ob -> oa
     * cd -> cc
     */
    public void testGetCurrentFillers()throws Exception{

        OWLOntology ont = createOntology();


        OWLReasoner r = ((OWLReasonerFactory) Class.forName(DEFAULT_REASONER_FACTORY).newInstance()).createNonBufferingReasoner(ont);


        SuggestorFactory fac = new SuggestorFactory(r);
       PropertySuggestor ps = fac.getPropertySuggestor();
       FillerSuggestor fs = fac.getFillerSuggestor();

        createEntities();

        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectSomeValuesFrom(oa, cb))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectSomeValuesFrom(ob, cc))));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(ca, df.getOWLObjectSomeValuesFrom(ob, cd))));
        changes.add(new AddAxiom(ont, df.getOWLSubObjectPropertyOfAxiom(ob, oa)));
        changes.add(new AddAxiom(ont, df.getOWLSubClassOfAxiom(cd, cc)));

        mngr.applyChanges(changes);

        NodeSet<OWLClass> all = fs.getCurrentNamedFillers(ca, oa, false);

        assertTrue(all.containsEntity(cb));
        assertTrue(all.containsEntity(cc));
        assertTrue(all.containsEntity(cd));
        assertTrue(all.containsEntity(df.getOWLThing()));
        assertEquals(4, all.getNodes().size());

        NodeSet<OWLClass> direct = fs.getCurrentNamedFillers(ca, oa, true);

        assertTrue(direct.containsEntity(cb));
        assertFalse(direct.containsEntity(cc)); // as cd is more specific
        assertTrue(direct.containsEntity(cd));
        assertFalse(direct.containsEntity(df.getOWLThing())); // as more specific properties have been found
        assertEquals(2, direct.getNodes().size());
    }

    public void testGetPossibleFillers()throws Exception{

        OWLOntology ont = createModelA();


        OWLReasoner r = ((OWLReasonerFactory) Class.forName(DEFAULT_REASONER_FACTORY).newInstance()).createNonBufferingReasoner(ont);


        SuggestorFactory fac = new SuggestorFactory(r);
       PropertySuggestor ps = fac.getPropertySuggestor();
       FillerSuggestor fs = fac.getFillerSuggestor();



        NodeSet<OWLClass> pSuccessorsA = fs.getPossibleNamedFillers(ca, oa, null, false);

        assertEquals(8, pSuccessorsA.getNodes().size());
        assertTrue(pSuccessorsA.containsEntity(ca));
        assertTrue(pSuccessorsA.containsEntity(ca1));
        assertTrue(pSuccessorsA.containsEntity(cb));
        assertTrue(pSuccessorsA.containsEntity(cb1));
        assertTrue(pSuccessorsA.containsEntity(cc));
        assertTrue(pSuccessorsA.containsEntity(cc1));
        assertTrue(pSuccessorsA.containsEntity(cd));
        assertTrue(pSuccessorsA.containsEntity(ce));

        NodeSet<OWLClass> pSuccessorsADirect = fs.getPossibleNamedFillers(ca, oa, null, true);

        assertEquals(4, pSuccessorsADirect.getNodes().size());
        // not ca as it is a sub of cd
        assertTrue(pSuccessorsADirect.containsEntity(cb));
        assertTrue(pSuccessorsADirect.containsEntity(cc));
        assertTrue(pSuccessorsADirect.containsEntity(cd));
        assertTrue(pSuccessorsADirect.containsEntity(ce));
    }
}