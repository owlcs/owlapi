package org.semanticweb.owlapi.api.test.syntax.manchester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class ManchesterSyntaxParserTest extends TestBase {

    public static Stream<Arguments> data() {
        return Stream.of(
        //@formatter:off
            Arguments.of( "hasAge exactly 1 xsd:int",  DataExactCardinality(1, hasAge, OWL2Datatype.XSD_INT)),
            Arguments.of( "hasAge exactly 1",          DataExactCardinality(1, hasAge)),
            Arguments.of( "hasAge min 1 xsd:int",      DataMinCardinality(1, hasAge, OWL2Datatype.XSD_INT)),
            Arguments.of( "hasAge min 1",              DataMinCardinality(1, hasAge)),
            Arguments.of( "hasAge max 1 xsd:int",      DataMaxCardinality(1, hasAge, OWL2Datatype.XSD_INT)),
            Arguments.of( "hasAge max 1",              DataMaxCardinality(1, hasAge)));
            //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("data")
    void testParseDataCardinalityExpression(String input, Object expected) {
        OWLOntology ont = createAnon();
        ont.addAxiom(Declaration(hasAge));
        ManchesterOWLSyntaxClassExpressionParser parser =
            new ManchesterOWLSyntaxClassExpressionParser(df, checker(m));
        assertEquals(expected, parser.parse(input));
    }

    protected OWLEntityChecker checker(OWLOntologyManager manager) {
        BidirectionalShortFormProviderAdapter adapter = new BidirectionalShortFormProviderAdapter(
            asList(manager.ontologies()), new SimpleShortFormProvider());
        return new ShortFormEntityChecker(adapter);
    }
}
