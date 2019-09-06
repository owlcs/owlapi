package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Arrays;
import java.util.Set;

import org.semanticweb.owlapi.api.test.baseclasses.AnnotatedAxiomRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormatFactory;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TrigDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

public class SameIndividualsThreeElementsAxiomAnnotatedTestCase
    extends AnnotatedAxiomRoundTrippingTestCase {
    public SameIndividualsThreeElementsAxiomAnnotatedTestCase() {
        super(a -> df.getOWLSameIndividualAxiom(Arrays.asList(NamedIndividual(iri("A")),
            NamedIndividual(iri("B")), NamedIndividual(iri("C"))), a));
    }

    @Override
    public void testRDFXML() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RDFXMLDocumentFormatFactory());
    }

    @Override
    public void testRioRDFXML() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RioRDFXMLDocumentFormatFactory());
    }

    @Override
    public void testTrig() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new TrigDocumentFormatFactory());
    }

    @Override
    public void testRDFJSON() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RDFJsonDocumentFormatFactory());
    }

    @Override
    public void testNTriples() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new NTriplesDocumentFormatFactory());
    }

    @Override
    public void roundTripRDFXMLAndFunctionalShouldBeSame() {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        // super.roundTripRDFXMLAndFunctionalShouldBeSame();
    }

    @Override
    public void testJSONLD() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RDFJsonDocumentFormatFactory());
    }

    @Override
    public void testNQuads() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new NQuadsDocumentFormatFactory());
    }

    @Override
    public void testTurtle() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new TurtleDocumentFormatFactory());
    }

    @Override
    public void testRioTurtle() throws Exception {
        // axioms with three or more elements cannot be represented as one axiom in this syntax
        sameIndividualAssertion(createOntology(), new RioTurtleDocumentFormatFactory());
    }

    protected void sameIndividualAssertion(OWLOntology ont, OWLDocumentFormatFactory f)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o =
            loadOntologyFromString(saveOntology(ont, f.createFormat()), f.createFormat());
        assertEquals(3, o.getAxiomCount(AxiomType.SAME_INDIVIDUAL));
        Set<OWLSameIndividualAxiom> axioms = asSet(ont.axioms(AxiomType.SAME_INDIVIDUAL));
        axioms.stream().map(OWLSameIndividualAxiom::splitToAnnotatedPairs)
            .forEach(axs -> axs.forEach(a -> assertTrue(o.containsAxiom(a))));
    }
}
