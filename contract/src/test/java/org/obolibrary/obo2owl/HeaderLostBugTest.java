package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;

@SuppressWarnings("javadoc")
public class HeaderLostBugTest extends OboFormatTestBasics {

    /**
     * During the conversion of the rdfxml formatfile the ontology header tags
     * are lost. The possible reason is that the RDFXMLOntologyFormat format
     * writes the annotation assertion axioms as annotations.
     */
    @Test
    public void testHeaderLog() {
        OWLOntology ontology = convertOBOFile("header_lost_bug.obo");
        IRI ontologyIRI = IRI.create("http://purl.obolibrary.org/obo/test.owl");
        Set<OWLAnnotation> ontAnns = ontology.getAnnotations();
        Set<OWLAnnotationAssertionAxiom> axioms = ontology.getAnnotationAssertionAxioms(ontologyIRI);
        // two tags in the header of the obo file are translated as annotation
        // assertions, so the axioms
        // should have two axioms in count.
        assertEquals(2, ontAnns.size());
        assertEquals(0, axioms.size());
    }

    @Override
    protected OWLOntology convertOBOFile(String fn) {
        OWLOntology ontology = convert(parseOBOFile(fn));
        StringDocumentTarget target = new StringDocumentTarget();
        try {
            ontology.getOWLOntologyManager().saveOntology(ontology, new RDFXMLDocumentFormat(), target);
            return m.loadOntologyFromOntologyDocument(new StringDocumentSource(target));
        } catch (OWLException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
