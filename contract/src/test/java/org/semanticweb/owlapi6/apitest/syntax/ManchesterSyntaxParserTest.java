package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.expression.OWLEntityChecker;
import org.semanticweb.owlapi6.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi6.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.utility.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi6.utility.SimpleShortFormProvider;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

class ManchesterSyntaxParserTest extends TestBase {

    static Stream<Arguments> data() {
        return Stream.of(
        //@formatter:off
            of("hasAge exactly 1 xsd:int",  DataExactCardinality(1, DATAPROPS.hasAge, OWL2Datatype.XSD_INT)),
            of("hasAge exactly 1",          DataExactCardinality(1, DATAPROPS.hasAge)),
            of("hasAge min 1 xsd:int",      DataMinCardinality  (1, DATAPROPS.hasAge, OWL2Datatype.XSD_INT)),
            of("hasAge min 1",              DataMinCardinality  (1, DATAPROPS.hasAge)),
            of("hasAge max 1 xsd:int",      DataMaxCardinality  (1, DATAPROPS.hasAge, OWL2Datatype.XSD_INT)),
            of("hasAge max 1",              DataMaxCardinality  (1, DATAPROPS.hasAge)));
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("data")
    void testParseDataCardinalityExpression(String input, OWLDataCardinalityRestriction expected) {
        ManchesterOWLSyntaxClassExpressionParser parser =
            new ManchesterOWLSyntaxClassExpressionParser(df,
                checker(o(Declaration(DATAPROPS.hasAge))));
        assertEquals(expected, parser.parse(input));


        assertTrue(TopDatatype().isTopDatatype());
        if (expected.getFiller().isTopDatatype()) {
            assertEquals(TopDatatype(), expected.getFiller());
            assertFalse(expected.isQualified());
        } else {
            assertTrue(expected.isQualified());
        }
    }

    protected OWLEntityChecker checker(OWLOntology o) {
        return new ShortFormEntityChecker(
            new BidirectionalShortFormProviderAdapter(l(o), new SimpleShortFormProvider()));
    }
}
