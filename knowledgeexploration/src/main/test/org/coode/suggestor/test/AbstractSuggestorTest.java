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
import org.coode.suggestor.api.FillerSuggestor;
import org.coode.suggestor.api.PropertySuggestor;
import org.coode.suggestor.impl.SuggestorFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

public abstract class AbstractSuggestorTest extends TestCase {

    protected static final String HERMIT_FACTORY =       "org.semanticweb.HermiT.Reasoner$ReasonerFactory";
    protected static final String FACTPLUSPLUS_FACTORY = "uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory";
    protected static final String JFACT_FACTORY =        "uk.ac.manchester.cs.jfact.JFactFactory";
    protected static final String PELLET_FACTORY =       "com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory";

    public static final String DEFAULT_REASONER_FACTORY = JFACT_FACTORY;


    private static final String BASE = "http://example.com#";

    protected OWLOntologyManager mngr;


    protected OWLDataFactory df;


    @Override
    protected void setUp() throws Exception {
        mngr = OWLManager.createOWLOntologyManager();
        df = mngr.getOWLDataFactory();


    }

    protected OWLOntology createOntology() throws OWLOntologyCreationException {
        return mngr.createOntology();
    }


    @Override
    protected void tearDown() throws Exception {
        mngr = null;
        df = null;
    }

    protected OWLObjectProperty createObjectProperty(String name) {
        return df.getOWLObjectProperty(IRI.create(BASE + name));
    }

    protected OWLDataProperty createDataProperty(String name) {
        return df.getOWLDataProperty(IRI.create(BASE + name));
    }

    protected OWLClass createClass(String name) {
        return df.getOWLClass(IRI.create(BASE + name));
    }

    protected OWLAnnotationProperty createAnnotationProperty(String name) {
        return df.getOWLAnnotationProperty(IRI.create(BASE + name));
    }
}
