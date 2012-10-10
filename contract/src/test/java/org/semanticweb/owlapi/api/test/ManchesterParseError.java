package org.semanticweb.owlapi.api.test;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class ManchesterParseError {

    @Test(expected = ParserException.class)
    public void shouldNotParse() throws ParserException {
        System.out.println("Baseline parses as " + parse("p some rdfs:Literal"));
        String text1 = "p some Litera";
        parse(text1);
    }

    @Test(expected = ParserException.class)
    public void shouldNotParseToo() throws ParserException {
        System.out.println("Baseline parses as " + parse("p some rdfs:Literal"));
        String text1 = "p some Literal";
        parse(text1);
    }
    @Test
    public void shouldParse() throws ParserException {
        System.out.println("Baseline parses as " + parse("p some rdfs:Literal"));
        String text1 = "p some Litera";
        try {
            parse(text1);
            System.out.println("You shouldn't see this - the parse should fail.");
        }
        catch (ParserException reasonableException) {
            System.out.println("This exception explains why \"" + text1 + "\" shouldn't parse:\n\t" + reasonableException.getMessage());
        }
        String text2 = "p some Literal";
        try {
            parse(text2);
            System.out.println("You shouldn't see this - the parse should fail.");
        }
        catch (ParserException reasonableException) {
            System.out.println("This exception explains why \"" + text2 + "\" shouldn't parse:\n\t" + reasonableException.getMessage());
        }
        catch (Throwable t) {
            System.out.println("This exception is not a good explanation of why \" " + text2 + "\" shouldn't parse:\n\t" + t.getMessage());
        }

    }

    private static OWLClassExpression parse(String text) throws ParserException {
        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        StupidEntityChecker checker = new StupidEntityChecker(factory);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(factory, text);
        parser.setOWLEntityChecker(checker);
        return parser.parseClassExpression();
    }

    /**
     * A very stupid entity checker that only understands that "p" is a property and
     * rdfs:Literal is a datatype.  He is an extreme simplification of the entity checker
     * that runs when Protege is set to render entities as qnames.
     * 
     * @author tredmond
     *
     */
    public static class StupidEntityChecker implements OWLEntityChecker {
        private OWLDataFactory factory;

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
                return factory.getOWLDataProperty(IRI.create("http://protege.org/Test.owl#p"));
            }
            else {
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
            }
            else {
                return null;
            }
        }

    }
}
