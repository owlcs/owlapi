package org.obolibrary.obo2owl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLOntology;

/** @author cjm */
@SuppressWarnings("javadoc")
public class SubsetTest extends OboFormatTestBasics {

    @Test
    public void testConvert() {
        // PARSE TEST FILE
        OWLOntology ontology = convert(parseOBOFile("subset_test.obo"));
        OWLAnnotationSubject subj = IRI
                .create("http://purl.obolibrary.org/obo/GO_0000003");
        OWLAnnotationProperty p = df
                .getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#inSubset");
        boolean ok = ontology.annotationAssertionAxioms(subj).anyMatch(
                a -> a.getProperty().equals(p));
        assertTrue(ok);
    }
}
