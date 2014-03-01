package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.coode.owl.krssparser.KRSSOWLParser;
import org.coode.owl.krssparser.KRSSOWLParserFactory;
import org.coode.owl.krssparser.KRSSOntologyFormat;
import org.coode.owl.krssparser.KRSSParserConstants;
import org.coode.owl.krssparser.NameResolverStrategy;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractKrssparserTest {

    public void shouldTestKRSSOntologyFormat() throws Exception {
        KRSSOntologyFormat testSubject0 = new KRSSOntologyFormat();
        String result0 = testSubject0.toString();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestKRSSOWLParser() throws Exception {
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
    public void shouldTestKRSSOWLParserFactory() throws Exception {
        KRSSOWLParserFactory testSubject0 = new KRSSOWLParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceKRSSParserConstants() throws Exception {
        KRSSParserConstants testSubject0 = mock(KRSSParserConstants.class);
    }

    @Test
    public void shouldTestNameResolverStrategy() throws Exception {
        NameResolverStrategy testSubject0 = NameResolverStrategy.ADAPTIVE;
        NameResolverStrategy[] result0 = NameResolverStrategy.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }
}
