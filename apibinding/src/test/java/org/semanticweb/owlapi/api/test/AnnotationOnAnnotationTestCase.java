package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class AnnotationOnAnnotationTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLAnnotation annoOuterOuter1 = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myOuterOuterLabel1"), getFactory().getOWLLiteral("Outer Outer label 1"));
        OWLAnnotation annoOuterOuter2 = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myOuterOuterLabel2"), getFactory().getOWLLiteral("Outer Outer label 2"));
        Set<OWLAnnotation> outerOuterAnnos = new HashSet<OWLAnnotation>();
        outerOuterAnnos.add(annoOuterOuter1);
        outerOuterAnnos.add(annoOuterOuter2);
        OWLAnnotation annoOuter = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myOuterLabel"), getFactory().getOWLLiteral("Outer label"), outerOuterAnnos);
        OWLAnnotation annoInner = getFactory().getOWLAnnotation(getOWLAnnotationProperty("myLabel"), getFactory().getOWLLiteral("Label"), Collections.singleton(annoOuter));
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getOWLClass("B"), Collections.singleton(annoInner));
        axioms.add(ax);
        return axioms;
    }
}
