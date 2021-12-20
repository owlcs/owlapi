package org.semanticweb.owlapi6.apitest.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;

/**
 * Created by vincent on 20.08.15.
 */
class DeclareAnnotatedEntitiesTestCase extends TestBase {

    @Test
    void shouldDeclareAllDatatypes() {
        OWLOntology ontology = loadFrom(TestFiles.declareDatatypes, new OWLXMLDocumentFormat());
        Set<OWLDeclarationAxiom> declarations = asSet(ontology.axioms(AxiomType.DECLARATION));
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms =
            asSet(ontology.axioms(AxiomType.ANNOTATION_ASSERTION));
        OWLOntology ontology2 = createAnon();
        ontology2.add(annotationAssertionAxioms);
        OWLOntology o3 = roundTrip(ontology2, new RDFXMLDocumentFormat());
        Set<OWLDeclarationAxiom> reloadedDeclarations = asSet(o3.axioms(AxiomType.DECLARATION));
        assertEquals(declarations, reloadedDeclarations);
    }

    @Test
    void shouldRoundtripUndeclaredAnnotationPropertiesTurtle() {
        OWLOntology o = loadFrom(TestFiles.DECLARED_ANNOTATIONS, new TurtleDocumentFormat());
        OWLOntology o1 = loadFrom(TestFiles.UNDECLARED_ANNOTATIONS, new TurtleDocumentFormat());
        // this declaration was excluded on purpose - add it to be able to use equal
        o1.add(Declaration(AnnotationProperty(iri(OBO_IN_OWL, "source"))));
        equal(o, o1);
    }

    @Test
    void shouldRoundtripUndeclaredAnnotationPropertiesRioTurtle() {
        OWLOntology o = loadFrom(TestFiles.DECLARED_ANNOTATIONS, new RioTurtleDocumentFormat());
        OWLOntology o1 = loadFrom(TestFiles.UNDECLARED_ANNOTATIONS, new RioTurtleDocumentFormat());
        // this declaration was excluded on purpose - add it to be able to use equal
        o1.add(Declaration(AnnotationProperty(iri(OBO_IN_OWL, "source"))));
        equal(o, o1);
    }
}
