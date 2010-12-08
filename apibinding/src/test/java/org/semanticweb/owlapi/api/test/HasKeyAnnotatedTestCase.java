package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-May-2009
 */
public class HasKeyAnnotatedTestCase extends AbstractAxiomsRoundTrippingTestCase {


    protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnnotationProperty ap = getFactory().getOWLAnnotationProperty(IRI.create("http://annotation.com/annos#prop"));
        OWLLiteral val = getFactory().getOWLLiteral("Test", "");
        OWLAnnotation anno = getFactory().getOWLAnnotation(ap, val);
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        annos.add(anno);
        OWLClassExpression ce = getOWLClass("A");
        OWLObjectProperty p1 = getOWLObjectProperty("p1");
        OWLObjectProperty p2 = getOWLObjectProperty("p2");
        OWLObjectProperty p3 = getOWLObjectProperty("p3");

        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        props.add(p1);
        props.add(p2);
        props.add(p3);
        OWLHasKeyAxiom ax = getFactory().getOWLHasKeyAxiom(ce, props, annos);
        Set<OWLAxiom> axs = new HashSet<OWLAxiom>();
        axs.add(ax);
        axs.add(getFactory().getOWLDeclarationAxiom(ap));
        axs.add(getFactory().getOWLDeclarationAxiom(p1));
        axs.add(getFactory().getOWLDeclarationAxiom(p2));
        axs.add(getFactory().getOWLDeclarationAxiom(p3));
        return axs;
    }
}
