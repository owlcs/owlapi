package org.semanticweb.owlapi.api.test.syntax.manchester;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class ManchesterSyntaxParserTest extends TestBase {

    @Parameters
    public static Collection<Object[]> data() {
        OWLDataFactory datafactory = OWLManager.getOWLDataFactory();
        OWLDataProperty hasAge =
                        datafactory.getOWLDataProperty(IRI.create("http://example.org/hasAge"));
        return Arrays.asList(
            //@formatter:off
            new Object[] { "hasAge exactly 1 xsd:int",  datafactory.getOWLDataExactCardinality(1, hasAge, OWL2Datatype.XSD_INT) },
            new Object[] { "hasAge exactly 1",          datafactory.getOWLDataExactCardinality(1, hasAge) }, 
            new Object[] { "hasAge min 1 xsd:int",      datafactory.getOWLDataMinCardinality(1, hasAge, OWL2Datatype.XSD_INT) }, 
            new Object[] { "hasAge min 1",              datafactory.getOWLDataMinCardinality(1, hasAge) }, 
            new Object[] { "hasAge max 1 xsd:int",      datafactory.getOWLDataMaxCardinality(1, hasAge, OWL2Datatype.XSD_INT) }, 
            new Object[] { "hasAge max 1",              datafactory.getOWLDataMaxCardinality(1, hasAge) });
            //@formatter:on
    }

    private String input;
    private Object expected;

    public ManchesterSyntaxParserTest(String input, Object expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testParseDataCardinalityExpression() throws OWLOntologyCreationException {
        OWLDataProperty hasAge = df.getOWLDataProperty(IRI.create("http://example.org/hasAge"));
        OWLOntology ont = m.createOntology();
        m.addAxiom(ont, df.getOWLDeclarationAxiom(hasAge));
        ManchesterOWLSyntaxClassExpressionParser parser =
                        new ManchesterOWLSyntaxClassExpressionParser(df, checker(m));
        assertEquals(expected, parser.parse(input));
    }

    protected OWLEntityChecker checker(OWLOntologyManager manager) {
        BidirectionalShortFormProviderAdapter adapter = new BidirectionalShortFormProviderAdapter(
                        asList(manager.ontologies()), new SimpleShortFormProvider());
        OWLEntityChecker checker = new ShortFormEntityChecker(adapter);
        return checker;
    }
}
