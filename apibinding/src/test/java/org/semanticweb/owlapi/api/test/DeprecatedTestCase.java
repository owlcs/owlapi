package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 10-Dec-2009
 */
public class DeprecatedTestCase extends AbstractFileTestCase {

    public void testAnnotationAssertionsPresent() {
        OWLOntology ont = createOntology();
        OWLClass cls = getOWLClass("http://www.semanticweb.org/owlapi/test#ClsA");
        for(OWLAnnotation anno : cls.getAnnotations(ont)) {
            assertTrue(anno.isDeprecatedIRIAnnotation());
        }
        OWLDataProperty prop = getOWLDataProperty("http://www.semanticweb.org/owlapi/test#prop");
        for(OWLAnnotation anno : prop.getAnnotations(ont)) {
            assertTrue(anno.isDeprecatedIRIAnnotation());
        }
    }

    protected String getFileName() {
        return "Deprecated.rdf";
    }
}
