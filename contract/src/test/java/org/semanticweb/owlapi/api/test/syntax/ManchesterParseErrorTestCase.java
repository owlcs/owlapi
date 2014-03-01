package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

@SuppressWarnings("javadoc")
public class ManchesterParseErrorTestCase {

    @Test(expected = ParserException.class)
    public void shouldNotParse() {
        parse("p some rdfs:Literal");
        String text1 = "p some Litera";
        parse(text1);
    }

    @Test(expected = ParserException.class)
    public void shouldNotParseToo() {
        parse("p some rdfs:Literal");
        String text1 = "p some Literal";
        parse(text1);
    }

    private static OWLClassExpression parse(String text) {
        OWLDataFactory factory = Factory.getFactory();
        StupidEntityChecker checker = new StupidEntityChecker(factory);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, text);
        parser.setOWLEntityChecker(checker);
        return parser.parseClassExpression();
    }

    /**
     * A very stupid entity checker that only understands that "p" is a property
     * and rdfs:Literal is a datatype. He is an extreme simplification of the
     * entity checker that runs when Protege is set to render entities as
     * qnames.
     * 
     * @author tredmond
     */
    private static class StupidEntityChecker implements OWLEntityChecker {

        private final OWLDataFactory factory;

        public StupidEntityChecker(OWLDataFactory factory) {
            this.factory = factory;
        }

        @Override
        public OWLClass getOWLClass(String name) {
            return null;
        }

        @Override
        public OWLObjectProperty getOWLObjectProperty(String name) {
            return null;
        }

        @Override
        public OWLDataProperty getOWLDataProperty(String name) {
            if (name != null && name.equals("p")) {
                return factory
                        .getOWLDataProperty(IRI("http://protege.org/Test.owl#p"));
            } else {
                return null;
            }
        }

        @Override
        public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
            return null;
        }

        @Override
        public OWLNamedIndividual getOWLIndividual(String name) {
            return null;
        }

        @Override
        public OWLDatatype getOWLDatatype(String name) {
            if (name != null && name.equals("rdfs:Literal")) {
                return factory.getTopDatatype();
            } else {
                return null;
            }
        }
    }
}
