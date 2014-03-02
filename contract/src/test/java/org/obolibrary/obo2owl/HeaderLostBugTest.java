package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class HeaderLostBugTest extends OboFormatTestBasics {

    /**
     * During the conversion of the rdfxml formatfile the ontology header tags
     * are lost. The possible reason is that the RDFXMLOntologyFormat format
     * writes the annotation assertion axioms as annotations.
     * 
     * @throws Exception
     */
    @Test
    public void testHeaderLog() throws Exception {
        OWLOntology ontology = convertOBOFile("header_lost_bug.obo");
        IRI ontologyIRI = IRI.create("http://purl.obolibrary.org/obo/test.owl");
        Set<OWLAnnotation> ontAnns = ontology.getAnnotations();
        Set<OWLAnnotationAssertionAxiom> axioms = ontology
                .getAnnotationAssertionAxioms(ontologyIRI);
        // two tags in the header of the obo file are translated as annotation
        // assertions, so the axioms
        // should have two axioms in count.
        assertEquals(2, ontAnns.size());
        assertEquals(0, axioms.size());
    }

    @Override
    protected OWLOntology convertOBOFile(String fn) throws Exception {
        OWLOntology ontology = convert(parseOBOFile(fn));
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology,
                new RDFXMLOntologyFormat(), target);
        OWLOntology in = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(target));
        return in;
    }
}
