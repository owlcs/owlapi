package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClassAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.profiles.OWL2DLProfile;

class PrimerTestCase extends TestBase {

    static final String URN_PRIMER = "urn:primer#";
    OWLOntology func = loadFrom(TestFiles.FUNCTIONAL, iri(URN_PRIMER, "functional"),
        new FunctionalSyntaxDocumentFormat());
    OWL2DLProfile profile = new OWL2DLProfile();

    @BeforeEach
    void setUpProfile() {
        assertTrue(profile.checkOntology(func).isInProfile());
    }

    @Test
    void shouldManchBeEquivalent() {
        OWLOntology manch = loadFrom(TestFiles.MANCHESTER, iri(URN_PRIMER, "manchester"),
            new ManchesterSyntaxDocumentFormat());
        assertTrue(profile.checkOntology(manch).getViolations().isEmpty());
        // XXX Manchester OWL Syntax does not support GCIs
        // the input adopts a trick to semantically get around this, by
        // asserting a new named class equivalent to the right hand side of the
        // GCI and subclass of the left hand side
        // Rectifying this to be able to assert equality, and using a different
        // ontology
        // so that the equality test does not skip gcis because of the format
        Set<OWLClassAxiom> axioms = asUnorderedSet(manch.axioms(CLASSES.familyX));
        manch.remove(axioms);
        OWLClassExpression oneOf = ObjectOneOf(INDIVIDUALS.BILL, INDIVIDUALS.MARY, INDIVIDUALS.MEG);
        OWLClassExpression superClass = ObjectIntersectionOf(CLASSES.parent,
            ObjectAllValuesFrom(OBJPROPS.hasChild, CLASSES.female),
            ObjectMaxCardinality(1, OBJPROPS.hasChild, OWLThing()));
        manch.addAxiom(SubClassOf(ObjectIntersectionOf(CLASSES.female, oneOf), superClass));
        OWLOntology replacement = create(manch.getOntologyID().getOntologyIRI().orElse(null));
        replacement.add(manch.axioms());
        equal(func, replacement);
    }

    @Test
    void shouldRDFXMLBeEquivalent() {
        OWLOntology rdf =
            loadFrom(TestFiles.RDFXML, iri(URN_PRIMER, "rdfxml"), new RDFXMLDocumentFormat());
        assertTrue(profile.checkOntology(rdf).getViolations().isEmpty());
        equal(func, rdf);
    }

    @Test
    void shouldOWLXMLBeEquivalent() {
        OWLOntology owl =
            loadFrom(TestFiles.OWLXML, iri(URN_PRIMER, "owlxml"), new OWLXMLDocumentFormat());
        assertTrue(profile.checkOntology(owl).getViolations().isEmpty());
        equal(func, owl);
    }

    @Test
    void shouldTURTLEBeEquivalent() {
        OWLOntology turt =
            loadFrom(TestFiles.TURTLE, iri(URN_PRIMER, "turtle"), new TurtleDocumentFormat());
        assertTrue(profile.checkOntology(turt).getViolations().isEmpty());
        // XXX somehow the Turtle parser introduces a tautology: the inverse of
        // inverse(hasParent) is hasParent
        // dropping said tautology to assert equality of the rest of the axioms
        turt.remove(
            InverseObjectProperties(ObjectInverseOf(OBJPROPS.hasParent), OBJPROPS.hasParent));
        equal(func, turt);
    }
}
