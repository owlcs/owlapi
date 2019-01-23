package org.semanticweb.owlapi6.api.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.TestFiles;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;

/**
 * Created by vincent on 20.08.15.
 */
public class DeclareAnnotatedEntitiesTestCase extends TestBase {

    @Test
    public void shouldDeclareAllDatatypes() throws Exception {
        OWLOntology ontology = loadOntologyFromString(TestFiles.declareDatatypes, new OWLXMLDocumentFormat());
        Set<OWLDeclarationAxiom> declarations = asSet(ontology.axioms(AxiomType.DECLARATION));
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms = asSet(
            ontology.axioms(AxiomType.ANNOTATION_ASSERTION));
        OWLOntology ontology2 = m1.createOntology();
        ontology2.add(annotationAssertionAxioms);
        OWLOntology o3 = roundTrip(ontology2, new RDFXMLDocumentFormat());
        Set<OWLDeclarationAxiom> reloadedDeclarations = asSet(o3.axioms(AxiomType.DECLARATION));
        assertEquals(declarations, reloadedDeclarations);
    }
}
