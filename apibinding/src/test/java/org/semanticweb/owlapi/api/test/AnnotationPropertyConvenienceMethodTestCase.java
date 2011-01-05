package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Collections;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 05/01/2011
 */
public class AnnotationPropertyConvenienceMethodTestCase extends AbstractOWLAPITestCase {

    public void testGetSuperProperties() {
        OWLOntology ont = getOWLOntology("OntA");

        OWLDataFactory df = ont.getOWLOntologyManager().getOWLDataFactory();
        OWLAnnotationProperty propP = getOWLAnnotationProperty("propP");
        OWLAnnotationProperty propQ = getOWLAnnotationProperty("propQ");
        OWLAnnotationProperty propR = getOWLAnnotationProperty("propR");
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));

        assertTrue(propP.getSuperProperties(ont).contains(propQ));
        assertTrue(propP.getSuperProperties(Collections.singleton(ont)).contains(propQ));
        assertTrue(propP.getSuperProperties(ont, true).contains(propQ));
        assertTrue(propP.getSuperProperties(ont, false).contains(propQ));


        assertTrue(propP.getSuperProperties(ont).contains(propR));
        assertTrue(propP.getSuperProperties(Collections.singleton(ont)).contains(propR));
        assertTrue(propP.getSuperProperties(ont, true).contains(propR));
        assertTrue(propP.getSuperProperties(ont, false).contains(propR));
    }


    public void testGetSubProperties() {
        OWLOntology ont = getOWLOntology("OntA");

        OWLDataFactory df = ont.getOWLOntologyManager().getOWLDataFactory();
        OWLAnnotationProperty propP = getOWLAnnotationProperty("propP");
        OWLAnnotationProperty propQ = getOWLAnnotationProperty("propQ");
        OWLAnnotationProperty propR = getOWLAnnotationProperty("propR");
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));

        assertTrue(propQ.getSubProperties(ont).contains(propP));
        assertTrue(propQ.getSubProperties(Collections.singleton(ont)).contains(propP));
        assertTrue(propQ.getSubProperties(ont, true).contains(propP));
        assertTrue(propQ.getSubProperties(ont, false).contains(propP));

        assertTrue(propR.getSubProperties(ont).contains(propP));
        assertTrue(propR.getSubProperties(Collections.singleton(ont)).contains(propP));
        assertTrue(propR.getSubProperties(ont, true).contains(propP));
        assertTrue(propR.getSubProperties(ont, false).contains(propP));
    }


}
