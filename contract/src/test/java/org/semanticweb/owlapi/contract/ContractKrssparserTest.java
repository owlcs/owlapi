package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Set;

import org.coode.owl.krssparser.KRSSOWLParser;
import org.coode.owl.krssparser.KRSSOWLParserFactory;
import org.coode.owl.krssparser.KRSSOntologyFormat;
import org.coode.owl.krssparser.KRSSParser;
import org.coode.owl.krssparser.KRSSParserConstants;
import org.coode.owl.krssparser.KRSSParserTokenManager;
import org.coode.owl.krssparser.NameResolverStrategy;
import org.coode.owl.krssparser.ParseException;
import org.coode.owl.krssparser.Token;
import org.coode.owl.krssparser.TokenMgrError;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractKrssparserTest {
    public void shouldTestKRSSOntologyFormat() throws OWLException {
        KRSSOntologyFormat testSubject0 = new KRSSOntologyFormat();
        String result0 = testSubject0.toString();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestKRSSOWLParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        KRSSOWLParser testSubject0 = new KRSSOWLParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result2 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestKRSSOWLParserFactory() throws OWLException {
        KRSSOWLParserFactory testSubject0 = new KRSSOWLParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    public void shouldTestKRSSParser() throws OWLException, ParseException {
        KRSSParser testSubject0 = new KRSSParser(mock(KRSSParserTokenManager.class));
        KRSSParser testSubject1 = new KRSSParser(mock(Reader.class));
        KRSSParser testSubject2 = new KRSSParser(mock(InputStream.class), "");
        KRSSParser testSubject3 = new KRSSParser(mock(InputStream.class));
        testSubject0.parse();
        IRI result0 = testSubject0.Name();
        OWLAxiom result1 = testSubject0.Instance();
        OWLClassExpression result2 = testSubject0.All();
        IRI result3 = testSubject0.getIRI("");
        testSubject0.setOntology(Utils.getMockOntology(), mock(OWLDataFactory.class));
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(InputStream.class), "");
        testSubject0.ReInit(mock(KRSSParserTokenManager.class));
        OWLAxiom result4 = testSubject0.TBoxStatement();
        OWLAxiom result5 = testSubject0.ABoxStatement();
        OWLAxiom result6 = testSubject0.DefinePrimitiveConcept();
        OWLAxiom result7 = testSubject0.DefineConcept();
        OWLAxiom result8 = testSubject0.DefinePrimitiveRole();
        OWLAxiom result9 = testSubject0.Range();
        OWLAxiom result10 = testSubject0.Transitive();
        OWLClassExpression result11 = testSubject0.ConceptName();
        OWLClassExpression result12 = testSubject0.ConceptExpression();
        OWLObjectProperty result13 = testSubject0.RoleName();
        OWLClassExpression result14 = testSubject0.And();
        OWLClassExpression result15 = testSubject0.Or();
        OWLClassExpression result16 = testSubject0.Not();
        OWLClassExpression result17 = testSubject0.Some();
        OWLClassExpression result18 = testSubject0.AtLeast();
        OWLClassExpression result19 = testSubject0.AtMost();
        OWLClassExpression result20 = testSubject0.Exactly();
        Set<OWLClassExpression> result21 = testSubject0.ConceptSet();
        int result22 = testSubject0.Integer();
        OWLAxiom result23 = testSubject0.Related();
        OWLAxiom result24 = testSubject0.Equal();
        OWLAxiom result25 = testSubject0.Distinct();
        OWLIndividual result26 = testSubject0.IndividualName();
        Token result27 = testSubject0.getNextToken();
        ParseException result28 = testSubject0.generateParseException();
        Token result29 = testSubject0.getToken(0);
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
        testSubject0.setIgnoreAnnotationsAndDeclarations(false);
        String result30 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceKRSSParserConstants() throws OWLException {
        KRSSParserConstants testSubject0 = mock(KRSSParserConstants.class);
    }

    @Test
    public void shouldTestNameResolverStrategy() throws OWLException {
        NameResolverStrategy testSubject0 = NameResolverStrategy.ADAPTIVE;
        NameResolverStrategy[] result0 = NameResolverStrategy.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestParseException() throws OWLException {
        ParseException testSubject0 = new ParseException(mock(Token.class),
                mock(int[][].class), new String[0]);
        ParseException testSubject1 = new ParseException();
        ParseException testSubject2 = new ParseException("");
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestToken() throws OWLException {
        Token testSubject0 = new Token();
        Token testSubject1 = new Token(0);
        Token testSubject2 = new Token(0, "");
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        Token result2 = Token.newToken(0, "");
        Token result3 = Token.newToken(0);
    }

    public void shouldTestTokenMgrError() throws OWLException {
        TokenMgrError testSubject0 = new TokenMgrError();
        TokenMgrError testSubject1 = new TokenMgrError("", 0);
        TokenMgrError testSubject2 = new TokenMgrError(false, 0, 0, 0, "",
                mock(char.class), 0);
        String result0 = testSubject0.getMessage();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getLocalizedMessage();
    }
}
