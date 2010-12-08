package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 16-Oct-2009
 */
public class AnnotationAssertionWithIRITestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass cls = getOWLClass("ClsA");
        axioms.add(getFactory().getOWLDeclarationAxiom(cls));
        IRI object = IRI.create("http://www.semanticweb.org/owlapi#object");
        OWLAnnotationProperty prop = getFactory().getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/owlapi#prop"));
        axioms.add(getFactory().getOWLAnnotationAssertionAxiom(prop, cls.getIRI(), object));
        return axioms;
    }

}
