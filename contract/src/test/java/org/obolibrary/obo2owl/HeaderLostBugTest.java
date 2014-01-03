package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class HeaderLostBugTest extends OboFormatTestBasics {
    private File owlFile = null;

    /** During the conversion of the rdfxml formatfile the ontology header tags
     * are lost. The possible reason is that the RDFXMLOntologyFormat format
     * writes the annotation assertion axioms as annotations.
     * 
     * @throws Exception */
    @Test
    public void testHeaderLog() throws Exception {
        convertOBOFile("header_lost_bug.obo");
        assertNotNull(owlFile);
        IRI ontologyIRI = IRI.create(owlFile);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(ontologyIRI);
        Set<OWLAnnotation> ontAnns = ontology.getAnnotations();
        Set<OWLAnnotationAssertionAxiom> axioms = ontology
                .getAnnotationAssertionAxioms(ontologyIRI);
        // two tags in the header of the obo file are translated as annotation
        // assertions, so the axioms
        // should have two axioms in count.
        assertEquals(2, ontAnns.size());
        assertEquals(0, axioms.size());
    }

    private OWLOntology convertOBOFile(String fn) throws Exception {
        OWLOntology ontology = convert(parseOBOFile(fn));
        owlFile = writeOWL(ontology, fn);
        return ontology;
    }
}
