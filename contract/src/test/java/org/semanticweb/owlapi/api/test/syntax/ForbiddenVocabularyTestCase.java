package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.profiles.UseOfUndeclaredAnnotationProperty;

@SuppressWarnings("javadoc")
public class ForbiddenVocabularyTestCase {

    @Test
    public void shouldFindViolation() throws Exception {
        String input = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" ><owl:Ontology rdf:about=\"\"/>\n<owl:Class rdf:about=\"http://phenomebrowser.net/cellphenotype.owl#C3PO:000000015\"><rdf:Description rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">Any.</rdf:Description></owl:Class></rdf:RDF>";
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        OWL2DLProfile p = new OWL2DLProfile();
        OWLProfileReport checkOntology = p.checkOntology(o);
        assertEquals(2, checkOntology.getViolations().size());
        OWLProfileViolation v = checkOntology.getViolations().get(0);
        assertTrue(v instanceof UseOfUndeclaredAnnotationProperty
                || v instanceof UseOfReservedVocabularyForAnnotationPropertyIRI);
        v = checkOntology.getViolations().get(1);
        assertTrue(v instanceof UseOfUndeclaredAnnotationProperty
                || v instanceof UseOfReservedVocabularyForAnnotationPropertyIRI);
    }
}
