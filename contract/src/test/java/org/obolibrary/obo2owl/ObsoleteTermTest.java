package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

@SuppressWarnings({ "javadoc", "null" })
public class ObsoleteTermTest extends OboFormatTestBasics {

    @Test
    public void testConvert() {
        // PARSE TEST FILE
        OWLOntology ontology = convert(parseOBOFile("obsolete_term_test.obo"));
        // TEST CONTENTS OF OWL ONTOLOGY
        OWLAnnotationSubject subj = IRI
                .create("http://purl.obolibrary.org/obo/XX_0000034");
        Set<OWLAnnotationAssertionAxiom> aas = ontology
                .getAnnotationAssertionAxioms(subj);
        boolean okDeprecated = false;
        for (OWLAnnotationAssertionAxiom aa : aas) {
            if (aa.getProperty().getIRI()
                    .equals(OWLRDFVocabulary.OWL_DEPRECATED.getIRI())) {
                OWLLiteral v = (OWLLiteral) aa.getValue();
                if (v.isBoolean()) {
                    if (v.parseBoolean()) {
                        okDeprecated = true;
                    }
                }
            }
        }
        assertTrue(okDeprecated);
        // CONVERT TO OWL FILE
        writeOWL(ontology, new RDFXMLDocumentFormat());
        // CONVERT BACK TO OBO
        OBODoc obodoc = convert(ontology);
        Frame tf = obodoc.getTermFrame("XX:0000034");
        Clause c = tf.getClause(OboFormatTag.TAG_IS_OBSELETE);
        Object v = c.getValue();
        assertEquals("true", v); // should this be a Boolean object? TODO
    }
}
