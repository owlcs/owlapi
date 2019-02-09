package org.semanticweb.owlapi.api.test.classexpressions;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;

import java.util.Arrays;

import org.semanticweb.owlapi.api.test.baseclasses.AnnotatedAxiomRoundTrippingTestCase;

public class DifferentIndividualsThreeElementsAxiomAnnotatedTestCase
    extends AnnotatedAxiomRoundTrippingTestCase {
    public DifferentIndividualsThreeElementsAxiomAnnotatedTestCase() {
        super(a -> df.getOWLDifferentIndividualsAxiom(Arrays.asList(NamedIndividual(iri("A")),
            NamedIndividual(iri("B")), NamedIndividual(iri("C"))), a));
    }
}
