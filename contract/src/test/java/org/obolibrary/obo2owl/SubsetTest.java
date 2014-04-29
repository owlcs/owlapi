package org.obolibrary.obo2owl;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
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
        Set<OWLAnnotationAssertionAxiom> aas = ontology
                .getAnnotationAssertionAxioms(subj);
        boolean ok = false;
        for (OWLAnnotationAssertionAxiom aa : aas) {
            if (aa.getProperty()
                    .getIRI()
                    .toString()
                    .equals("http://www.geneontology.org/formats/oboInOwl#inSubset")) {
                ok = true;
            }
        }
        assertTrue(ok);
    }
}
