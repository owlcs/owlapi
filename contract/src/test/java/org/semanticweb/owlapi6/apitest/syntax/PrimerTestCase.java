package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.profiles.OWL2DLProfile;

public class PrimerTestCase extends TestBase {

    private static final String URN_PRIMER = "urn:primer#";
    private static final String NS = "http://example.com/owl/families/";
    protected OWLOntology func = loadOntologyFromString(TestFiles.FUNCTIONAL,
        df.getIRI(URN_PRIMER, "functional"), new FunctionalSyntaxDocumentFormat());
    OWL2DLProfile profile = new OWL2DLProfile();

    @Before
    public void setUpProfile() {
        assertTrue(profile.checkOntology(func).isInProfile());
    }

    @Test
    public void shouldManchBeEquivalent() throws OWLOntologyCreationException {
        OWLOntology manch = loadOntologyFromString(TestFiles.MANCHESTER,
            df.getIRI(URN_PRIMER, "manchester"), new ManchesterSyntaxDocumentFormat());
        assertTrue(profile.checkOntology(manch).getViolations().isEmpty());
        // XXX Manchester OWL Syntax does not support GCIs
        // the input adopts a trick to semantically get around this, by
        // asserting a new named class equivalent to the right hand side of the
        // GCI and subclass of the left hand side
        // Rectifying this to be able to assert equality, and using a different
        // ontology
        // so that the equality test does not skip gcis because of the format
        OWLClass x = df.getOWLClass(NS, "X");
        Set<OWLClassAxiom> axioms = asUnorderedSet(manch.axioms(x));
        manch.remove(axioms);
        OWLClass female = df.getOWLClass(NS, "Female");
        OWLClassExpression oneOf = df.getOWLObjectOneOf(df.getOWLNamedIndividual(NS, "Bill"),
            df.getOWLNamedIndividual(NS, "Mary"), df.getOWLNamedIndividual(NS, "Meg"));
        OWLClass parent = df.getOWLClass(NS, "Parent");
        OWLObjectProperty hasChild = df.getOWLObjectProperty(NS, "hasChild");
        OWLClassExpression superClass =
            df.getOWLObjectIntersectionOf(parent, df.getOWLObjectAllValuesFrom(hasChild, female),
                df.getOWLObjectMaxCardinality(1, hasChild));
        manch.addAxiom(
            df.getOWLSubClassOfAxiom(df.getOWLObjectIntersectionOf(female, oneOf), superClass));
        OWLOntology replacement =
            m.createOntology(manch.axioms(), get(manch.getOntologyID().getOntologyIRI()));
        equal(func, replacement);
    }

    @Test
    public void shouldRDFXMLBeEquivalent() {
        OWLOntology rdf = loadOntologyFromString(TestFiles.RDFXML,
            df.getIRI(URN_PRIMER, "rdfxml"), new RDFXMLDocumentFormat());
        assertTrue(profile.checkOntology(rdf).getViolations().isEmpty());
        equal(func, rdf);
    }

    @Test
    public void shouldOWLXMLBeEquivalent() {
        OWLOntology owl = loadOntologyFromString(TestFiles.OWLXML,
            df.getIRI(URN_PRIMER, "owlxml"), new OWLXMLDocumentFormat());
        assertTrue(profile.checkOntology(owl).getViolations().isEmpty());
        equal(func, owl);
    }

    @Test
    public void shouldTURTLEBeEquivalent() {
        OWLOntology turt = loadOntologyFromString(TestFiles.TURTLE,
            df.getIRI(URN_PRIMER, "turtle"), new TurtleDocumentFormat());
        assertTrue(profile.checkOntology(turt).getViolations().isEmpty());
        // XXX somehow the Turtle parser introduces a tautology: the inverse of
        // inverse(hasParent) is hasParent
        // dropping said tautology to assert equality of the rest of the axioms
        OWLObjectProperty hasParent = df.getOWLObjectProperty(NS, "hasParent");
        turt.remove(
            df.getOWLInverseObjectPropertiesAxiom(df.getOWLObjectInverseOf(hasParent), hasParent));
        equal(func, turt);
    }
}
