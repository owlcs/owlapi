package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author cjm see 5.9.3 and 8.2.2 of spec See
 *         http://code.google.com/p/oboformat/issues/detail?id=13
 */
@SuppressWarnings({ "javadoc" })
public class LogicalDefinitionPropertyViewTest extends OboFormatTestBasics {

    @Test
    public void testConvert() {
        // PARSE TEST FILE
        OWLOntology owlOntology = convert(parseOBOFile("logical-definition-view-relation-test.obo"));
        OWLObjectProperty op = df
                .getOWLObjectProperty("http://purl.obolibrary.org/obo/BFO_0000050");
        boolean ok = owlOntology.axioms(AxiomType.EQUIVALENT_CLASSES).anyMatch(
                eca -> eca
                        .getClassExpressions()
                        .stream()
                        .anyMatch(
                                x -> x instanceof OWLObjectSomeValuesFrom
                                        && x.containsEntityInSignature(op)));
        assertTrue(ok);
        // reverse translation
        OBODoc obodoc = convert(owlOntology);
        Frame fr = obodoc.getTermFrame("X:1");
        Collection<Clause> clauses = fr
                .getClauses(OboFormatTag.TAG_INTERSECTION_OF);
        assertEquals(2, clauses.size());
    }
}
