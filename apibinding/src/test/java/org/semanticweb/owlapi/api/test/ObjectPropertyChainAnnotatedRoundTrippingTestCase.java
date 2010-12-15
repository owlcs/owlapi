package org.semanticweb.owlapi.api.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Jun-2009
 */
public class ObjectPropertyChainAnnotatedRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        OWLObjectProperty propA = getOWLObjectProperty("propA");
        OWLObjectProperty propB = getOWLObjectProperty("propB");
        OWLObjectProperty propC = getOWLObjectProperty("propC");
        OWLObjectProperty propD = getOWLObjectProperty("propD");
        List<OWLObjectProperty> props = new ArrayList<OWLObjectProperty>();
        props.add(propA);
        props.add(propB);
        props.add(propC);
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        OWLAnnotationProperty annoPropA = getOWLAnnotationProperty("annoPropA");
        OWLAnnotationProperty annoPropB = getOWLAnnotationProperty("annoPropB");
        annos.add(getFactory().getOWLAnnotation(annoPropA, getFactory().getOWLLiteral("Test", "en")));
        annos.add(getFactory().getOWLAnnotation(annoPropB, getFactory().getOWLLiteral("Test", "")));
        OWLAxiom ax = getFactory().getOWLSubPropertyChainOfAxiom(props, propD, annos);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax);
        return axioms;
    }
}
