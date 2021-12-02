package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;

import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;

class PrimerTestCase extends TestBase {

    static final String URN_PRIMER = "urn:primer#";
    static final String NS = "http://example.com/owl/families/";
    @Nonnull
    OWLOntology func = loadOntologyFromString(TestFiles.FUNCTIONAL, iri(URN_PRIMER, "functional"),
        new FunctionalSyntaxDocumentFormat());
    OWL2DLProfile profile = new OWL2DLProfile();

    @BeforeEach
    void setUpProfile() {
        assertTrue(profile.checkOntology(func).isInProfile());
    }

    @Test
    void shouldManchBeEquivalent() throws OWLOntologyCreationException {
        OWLOntology manch = loadOntologyFromString(TestFiles.MANCHESTER,
            iri(URN_PRIMER, "manchester"), new ManchesterSyntaxDocumentFormat());
        assertTrue(profile.checkOntology(manch).getViolations().isEmpty());
        // XXX Manchester OWL Syntax does not support GCIs
        // the input adopts a trick to semantically get around this, by
        // asserting a new named class equivalent to the right hand side of the
        // GCI and subclass of the left hand side
        // Rectifying this to be able to assert equality, and using a different
        // ontology
        // so that the equality test does not skip gcis because of the format
        OWLClass x = df.getOWLClass(iri(NS, "X"));
        Set<OWLClassAxiom> axioms = manch.getAxioms(x, EXCLUDED);
        manch.getOWLOntologyManager().removeAxioms(manch, axioms);
        OWLClass female = df.getOWLClass(iri(NS, "Female"));
        OWLClassExpression oneOf = df.getOWLObjectOneOf(df.getOWLNamedIndividual(iri(NS, "Bill")),
            df.getOWLNamedIndividual(iri(NS, "Mary")), df.getOWLNamedIndividual(iri(NS, "Meg")));
        OWLClass parent = df.getOWLClass(iri(NS, "Parent"));
        OWLObjectProperty hasChild = df.getOWLObjectProperty(iri(NS, "hasChild"));
        OWLClassExpression superClass =
            df.getOWLObjectIntersectionOf(parent, df.getOWLObjectAllValuesFrom(hasChild, female),
                df.getOWLObjectMaxCardinality(1, hasChild));
        manch.getOWLOntologyManager().addAxiom(manch,
            df.getOWLSubClassOfAxiom(df.getOWLObjectIntersectionOf(female, oneOf), superClass));
        OWLOntology replacement =
            m.createOntology(manch.getAxioms(), manch.getOntologyID().getOntologyIRI().get());
        equal(func, replacement);
    }

    @Test
    void shouldRDFXMLBeEquivalent() {
        OWLOntology rdf = loadOntologyFromString(TestFiles.RDFXML, iri(URN_PRIMER, "rdfxml"),
            new RDFXMLDocumentFormat());
        assertTrue(profile.checkOntology(rdf).getViolations().isEmpty());
        equal(func, rdf);
    }

    @Test
    void shouldOWLXMLBeEquivalent() {
        OWLOntology owl = loadOntologyFromString(TestFiles.OWLXML, iri(URN_PRIMER, "owlxml"),
            new OWLXMLDocumentFormat());
        assertTrue(profile.checkOntology(owl).getViolations().isEmpty());
        equal(func, owl);
    }

    @Test
    void shouldTURTLEBeEquivalent() {
        OWLOntology turt = loadOntologyFromString(TestFiles.TURTLE, iri(URN_PRIMER, "turtle"),
            new TurtleDocumentFormat());
        assertTrue(profile.checkOntology(turt).getViolations().isEmpty());
        // XXX somehow the Turtle parser introduces a tautology: the inverse of
        // inverse(hasParent) is hasParent
        // dropping said tautology to assert equality of the rest of the axioms
        OWLObjectProperty hasParent = df.getOWLObjectProperty(iri(NS, "hasParent"));
        turt.getOWLOntologyManager().removeAxiom(turt,
            df.getOWLInverseObjectPropertiesAxiom(df.getOWLObjectInverseOf(hasParent), hasParent));
        equal(func, turt);
    }
}
