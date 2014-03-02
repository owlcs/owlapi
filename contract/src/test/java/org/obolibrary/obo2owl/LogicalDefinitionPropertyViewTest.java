package org.obolibrary.obo2owl;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author cjm see 5.9.3 and 8.2.2 of spec See
 *         http://code.google.com/p/oboformat/issues/detail?id=13
 */
@SuppressWarnings("javadoc")
public class LogicalDefinitionPropertyViewTest extends OboFormatTestBasics {

    @Test
    public void testConvert() throws Exception {
        // PARSE TEST FILE
        OWLOntology owlOntology = convert(parseOBOFile("logical-definition-view-relation-test.obo"));
        boolean ok = false;
        for (OWLEquivalentClassesAxiom eca : owlOntology
                .getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
            for (OWLClassExpression x : eca.getClassExpressions()) {
                if (x instanceof OWLObjectSomeValuesFrom) {
                    // fairly weak test - just ensure it's done _something_ here
                    OWLObjectProperty p = (OWLObjectProperty) ((OWLObjectSomeValuesFrom) x)
                            .getProperty();
                    if (p.getIRI()
                            .toString()
                            .equals("http://purl.obolibrary.org/obo/BFO_0000050")) {
                        ok = true;
                    }
                }
            }
        }
        assertTrue(ok);
        // reverse translation
        OBODoc obodoc = this.convert(owlOntology);
        Frame fr = obodoc.getTermFrame("X:1");
        Collection<Clause> clauses = fr
                .getClauses(OboFormatTag.TAG_INTERSECTION_OF);
        assertTrue(clauses.size() == 2);
    }
}
