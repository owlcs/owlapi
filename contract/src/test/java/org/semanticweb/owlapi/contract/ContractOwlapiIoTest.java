package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.DefaultOntologyFormat;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.FileDocumentTarget;
import org.semanticweb.owlapi.io.IOProperties;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyInputSourceException;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLOntologyStorageIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.io.OWLParserIOException;
import org.semanticweb.owlapi.io.OWLParserSAXException;
import org.semanticweb.owlapi.io.OWLParserURISyntaxException;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.OntologyIRIMappingNotFoundException;
import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFOntologyHeaderStatus;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.io.WriterDocumentTarget;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.io.ZipDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.xml.sax.SAXException;

@SuppressWarnings({ "unused", "javadoc", "resource" })
public class ContractOwlapiIoTest {

    @Test
    public void shouldTestAbstractOWLParser() throws Exception {
        AbstractOWLParser testSubject0 = new AbstractOWLParser() {

            @Override
            public OWLOntologyFormat parse(
                    OWLOntologyDocumentSource documentSource,
                    OWLOntology ontology) throws OWLParserException,
                    IOException, OWLOntologyChangeException,
                    UnloadableImportException {
                return null;
            }

            @Override
            public OWLOntologyFormat parse(
                    OWLOntologyDocumentSource documentSource,
                    OWLOntology ontology,
                    OWLOntologyLoaderConfiguration configuration)
                    throws OWLParserException, IOException,
                    OWLOntologyChangeException, UnloadableImportException {
                return null;
            }
        };
        OWLOntologyFormat result0 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        String result2 = testSubject0.toString();
        OWLOntologyFormat result3 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result4 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
    }

    @Test
    public void shouldTestAbstractOWLRenderer() throws Exception {
        AbstractOWLRenderer testSubject0 = new AbstractOWLRenderer() {

            @Override
            public void render(OWLOntology ontology, Writer writer)
                    throws OWLRendererException {}
        };
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        String result0 = testSubject0.toString();
    }

    public void shouldTestDefaultOntologyFormat() throws Exception {
        DefaultOntologyFormat testSubject0 = new DefaultOntologyFormat();
        String result0 = testSubject0.toString();
        RDFParserMetaData result1 = testSubject0.getOntologyLoaderMetaData();
        OWLOntologyLoaderMetaData result2 = testSubject0
                .getOntologyLoaderMetaData();
        boolean result3 = testSubject0.isAddMissingTypes();
        boolean result4 = RDFOntologyFormat.isMissingType(
                Utils.mockOWLEntity(), Utils.getMockOntology());
        testSubject0.setAddMissingTypes(false);
        testSubject0.addError(mock(RDFResourceParseError.class));
        String result5 = testSubject0.getPrefix("");
        IRI result6 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result7 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result8 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result9 = testSubject0.containsPrefixMapping("");
        String result10 = testSubject0.getDefaultPrefix();
        String result11 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result12 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result13 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result14 = testSubject0
                .asPrefixOWLOntologyFormat();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestFileDocumentSource() throws Exception {
        FileDocumentSource testSubject0 = new FileDocumentSource(
                mock(File.class));
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        Reader result2 = testSubject0.getReader();
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
        String result5 = testSubject0.toString();
    }

    public void shouldTestFileDocumentTarget() throws Exception {
        FileDocumentTarget testSubject0 = new FileDocumentTarget(
                mock(File.class));
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestIOProperties() throws Exception {
        IOProperties testSubject0 = IOProperties.getInstance();
        IOProperties result0 = IOProperties.getInstance();
        boolean result1 = testSubject0.isConnectionAcceptHTTPCompression();
        int result2 = testSubject0.getConnectionTimeout();
        testSubject0.setConnectionTimeout(0);
        testSubject0.setConnectionAcceptHTTPCompression(false);
        String result3 = testSubject0.toString();
    }

    public void shouldTestIRIDocumentSource() throws Exception {
        IRIDocumentSource testSubject0 = new IRIDocumentSource(IRI("urn:aFake"));
        String result0 = testSubject0.toString();
        InputStream result1 = testSubject0.getInputStream();
        boolean result2 = testSubject0.isReaderAvailable();
        Reader result3 = testSubject0.getReader();
        boolean result4 = testSubject0.isInputStreamAvailable();
        IRI result5 = testSubject0.getDocumentIRI();
    }

    @Test
    public void shouldTestOntologyIRIMappingNotFoundException()
            throws Exception {
        OntologyIRIMappingNotFoundException testSubject0 = new OntologyIRIMappingNotFoundException(
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getOntologyIRI();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLFunctionalSyntaxOntologyFormat() throws Exception {
        OWLFunctionalSyntaxOntologyFormat testSubject0 = new OWLFunctionalSyntaxOntologyFormat();
        String result0 = testSubject0.toString();
        String result1 = testSubject0.getPrefix("");
        IRI result2 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result3 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result4 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result5 = testSubject0.containsPrefixMapping("");
        String result6 = testSubject0.getDefaultPrefix();
        String result7 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result8 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result9 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result10 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result11 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestInterfaceOWLObjectRenderer() throws Exception {
        OWLObjectRenderer testSubject0 = mock(OWLObjectRenderer.class);
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
    }

    @Test
    public void shouldTestOWLOntologyCreationIOException() throws Exception {
        OWLOntologyCreationIOException testSubject0 = new OWLOntologyCreationIOException(
                mock(IOException.class));
        IOException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.getMessage();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyDocumentSource() throws Exception {
        OWLOntologyDocumentSource testSubject0 = mock(OWLOntologyDocumentSource.class);
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        Reader result2 = testSubject0.getReader();
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyDocumentTarget() throws Exception {
        OWLOntologyDocumentTarget testSubject0 = mock(OWLOntologyDocumentTarget.class);
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
    }

    @Test
    public void shouldTestOWLOntologyInputSourceException() throws Exception {
        OWLOntologyInputSourceException testSubject0 = new OWLOntologyInputSourceException();
        OWLOntologyInputSourceException testSubject1 = new OWLOntologyInputSourceException(
                new RuntimeException());
        OWLOntologyInputSourceException testSubject2 = new OWLOntologyInputSourceException(
                "");
        OWLOntologyInputSourceException testSubject3 = new OWLOntologyInputSourceException(
                "", new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyLoaderMetaData() throws Exception {
        OWLOntologyLoaderMetaData testSubject0 = mock(OWLOntologyLoaderMetaData.class);
    }

    @Test
    public void shouldTestOWLOntologyStorageIOException() throws Exception {
        OWLOntologyStorageIOException testSubject0 = new OWLOntologyStorageIOException(
                mock(IOException.class));
        IOException result0 = testSubject0.getIOException();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLParser() throws Exception {
        OWLParser testSubject0 = mock(OWLParser.class);
        OWLOntologyFormat result0 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result2 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
    }

    @Test
    public void shouldTestOWLParserException() throws Exception {
        OWLParserException testSubject0 = new OWLParserException();
        OWLParserException testSubject1 = new OWLParserException("");
        OWLParserException testSubject2 = new OWLParserException("",
                new RuntimeException());
        OWLParserException testSubject3 = new OWLParserException(
                new RuntimeException());
        OWLParserException testSubject4 = new OWLParserException("", 0, 0);
        OWLParserException testSubject5 = new OWLParserException(
                new RuntimeException(), 0, 0);
        OWLParserException testSubject6 = new OWLParserException("",
                new RuntimeException(), 0, 0);
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLParserFactory() throws Exception {
        OWLParserFactory testSubject0 = mock(OWLParserFactory.class);
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
    }

    public void shouldTestOWLParserFactoryRegistry() throws Exception {
        OWLParserFactoryRegistry testSubject0 = OWLParserFactoryRegistry
                .getInstance();
        OWLParserFactoryRegistry result0 = OWLParserFactoryRegistry
                .getInstance();
        testSubject0.clearParserFactories();
        List<OWLParserFactory> result1 = testSubject0.getParserFactories();
        testSubject0.registerParserFactory(mock(OWLParserFactory.class));
        testSubject0.unregisterParserFactory(mock(OWLParserFactory.class));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLParserIOException() throws Exception {
        OWLParserIOException testSubject0 = new OWLParserIOException(
                mock(IOException.class));
        IOException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.getMessage();
        int result3 = testSubject0.getLineNumber();
        int result4 = testSubject0.getColumnNumber();
        String result7 = testSubject0.toString();
        String result8 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLParserSAXException() throws Exception {
        OWLParserSAXException testSubject0 = new OWLParserSAXException(
                mock(SAXException.class));
        SAXException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.getMessage();
        int result3 = testSubject0.getLineNumber();
        int result4 = testSubject0.getColumnNumber();
        String result7 = testSubject0.toString();
        String result8 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLParserURISyntaxException() throws Exception {
        OWLParserURISyntaxException testSubject0 = new OWLParserURISyntaxException(
                mock(URISyntaxException.class), 0, 0);
        URISyntaxException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.getMessage();
        int result3 = testSubject0.getLineNumber();
        int result4 = testSubject0.getColumnNumber();
        String result7 = testSubject0.toString();
        String result8 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLRenderer() throws Exception {
        OWLRenderer testSubject0 = mock(OWLRenderer.class);
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
    }

    @Test
    public void shouldTestOWLRendererException() throws Exception {
        OWLRendererException testSubject0 = new OWLRendererException("");
        OWLRendererException testSubject1 = new OWLRendererException("",
                new RuntimeException());
        OWLRendererException testSubject2 = new OWLRendererException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLRendererIOException() throws Exception {
        OWLRendererIOException testSubject0 = new OWLRendererIOException(
                mock(IOException.class));
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLOntologyFormat() throws Exception {
        OWLXMLOntologyFormat testSubject0 = new OWLXMLOntologyFormat();
        String result0 = testSubject0.toString();
        String result1 = testSubject0.getPrefix("");
        IRI result2 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result3 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result4 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result5 = testSubject0.containsPrefixMapping("");
        String result6 = testSubject0.getDefaultPrefix();
        String result7 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result8 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result9 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result10 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result11 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestRDFLiteral() throws Exception {
        RDFLiteral testSubject0 = new RDFLiteral(mock(OWLLiteral.class));
        String result0 = testSubject0.toString();
        OWLLiteral result1 = testSubject0.getLiteral();
        boolean result2 = testSubject0.isLiteral();
    }

    @Test
    public void shouldTestRDFNode() throws Exception {
        RDFNode testSubject0 = new RDFNode() {

            @Override
            public boolean isLiteral() {
                return false;
            }
        };
        boolean result0 = testSubject0.isLiteral();
        String result1 = testSubject0.toString();
    }

    public void shouldTestRDFOntologyFormat() throws Exception {
        RDFOntologyFormat testSubject0 = new RDFOntologyFormat() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        RDFParserMetaData result0 = testSubject0.getOntologyLoaderMetaData();
        OWLOntologyLoaderMetaData result1 = testSubject0
                .getOntologyLoaderMetaData();
        boolean result2 = testSubject0.isAddMissingTypes();
        boolean result3 = RDFOntologyFormat.isMissingType(
                Utils.mockOWLEntity(), Utils.getMockOntology());
        testSubject0.setAddMissingTypes(false);
        testSubject0.addError(mock(RDFResourceParseError.class));
        String result4 = testSubject0.getPrefix("");
        IRI result5 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result6 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result7 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result8 = testSubject0.containsPrefixMapping("");
        String result9 = testSubject0.getDefaultPrefix();
        String result10 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result11 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result12 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result13 = testSubject0
                .asPrefixOWLOntologyFormat();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
        String result14 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFOntologyHeaderStatus() throws Exception {
        RDFOntologyHeaderStatus testSubject0 = RDFOntologyHeaderStatus.PARSED_MULTIPLE_HEADERS;
        RDFOntologyHeaderStatus[] result0 = RDFOntologyHeaderStatus.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestRDFParserMetaData() throws Exception {
        RDFParserMetaData testSubject0 = new RDFParserMetaData(
                mock(RDFOntologyHeaderStatus.class), 0,
                Utils.mockSet(mock(RDFTriple.class)));
        int result0 = testSubject0.getTripleCount();
        RDFOntologyHeaderStatus result1 = testSubject0.getHeaderState();
        Set<RDFTriple> result2 = testSubject0.getUnparsedTriples();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFResource() throws Exception {
        RDFResource testSubject0 = new RDFResource(IRI("urn:aFake"));
        RDFResource testSubject1 = new RDFResource(IRI("urn:aFake"), false);
        String result0 = testSubject0.toString();
        IRI result1 = testSubject0.getResource();
        boolean result2 = testSubject0.isAnonymous();
        boolean result3 = testSubject0.isLiteral();
    }

    @Test
    public void shouldTestRDFResourceParseError() throws Exception {
        RDFResourceParseError testSubject0 = new RDFResourceParseError(
                Utils.mockOWLEntity(), mock(RDFNode.class),
                Utils.mockSet(mock(RDFTriple.class)));
        OWLEntity result0 = testSubject0.getParserGeneratedErrorEntity();
        RDFNode result1 = testSubject0.getMainNode();
        Set<RDFTriple> result2 = testSubject0.getMainNodeTriples();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFTriple() throws Exception {
        RDFTriple testSubject0 = new RDFTriple(IRI("urn:aFake"), false,
                IRI("urn:aFake"), false, IRI("urn:aFake"), false);
        RDFTriple testSubject1 = new RDFTriple(IRI("urn:aFake"), false,
                IRI("urn:aFake"), false, mock(OWLLiteral.class));
        RDFTriple testSubject2 = new RDFTriple(mock(RDFResource.class),
                mock(RDFResource.class), mock(RDFNode.class));
        String result0 = testSubject0.toString();
        RDFNode result1 = testSubject0.getObject();
        RDFResource result2 = testSubject0.getSubject();
        RDFResource result3 = testSubject0.getPredicate();
    }

    public void shouldTestRDFXMLOntologyFormat() throws Exception {
        RDFXMLOntologyFormat testSubject0 = new RDFXMLOntologyFormat();
        String result0 = testSubject0.toString();
        RDFParserMetaData result1 = testSubject0.getOntologyLoaderMetaData();
        OWLOntologyLoaderMetaData result2 = testSubject0
                .getOntologyLoaderMetaData();
        boolean result3 = testSubject0.isAddMissingTypes();
        boolean result4 = RDFOntologyFormat.isMissingType(
                Utils.mockOWLEntity(), Utils.getMockOntology());
        testSubject0.setAddMissingTypes(false);
        testSubject0.addError(mock(RDFResourceParseError.class));
        String result5 = testSubject0.getPrefix("");
        IRI result6 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result7 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result8 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result9 = testSubject0.containsPrefixMapping("");
        String result10 = testSubject0.getDefaultPrefix();
        String result11 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result12 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result13 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result14 = testSubject0
                .asPrefixOWLOntologyFormat();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestReaderDocumentSource() throws Exception {
        ReaderDocumentSource testSubject0 = new ReaderDocumentSource(
                mock(Reader.class), IRI("urn:aFake"));
        ReaderDocumentSource testSubject1 = new ReaderDocumentSource(
                mock(Reader.class));
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        Reader result2 = testSubject0.getReader();
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
        IRI result5 = ReaderDocumentSource.getNextDocumentIRI();
        String result6 = testSubject0.toString();
    }

    public void shouldTestStreamDocumentSource() throws Exception {
        StreamDocumentSource testSubject0 = new StreamDocumentSource(
                mock(InputStream.class));
        StreamDocumentSource testSubject1 = new StreamDocumentSource(
                mock(InputStream.class), IRI("urn:aFake"));
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        Reader result2 = testSubject0.getReader();
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
        IRI result5 = StreamDocumentSource.getNextDocumentIRI();
        String result6 = testSubject0.toString();
    }

    public void shouldTestStreamDocumentTarget() throws Exception {
        StreamDocumentTarget testSubject0 = new StreamDocumentTarget(
                mock(OutputStream.class));
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        String result6 = testSubject0.toString();
    }

    public void shouldTestStringDocumentSource() throws Exception {
        StringDocumentSource testSubject0 = new StringDocumentSource("");
        StringDocumentSource testSubject1 = new StringDocumentSource("",
                IRI("urn:aFake"));
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        Reader result2 = testSubject0.getReader();
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
        IRI result5 = StringDocumentSource.getNextDocumentIRI();
        String result6 = testSubject0.toString();
    }

    public void shouldTestStringDocumentTarget() throws Exception {
        StringDocumentTarget testSubject0 = new StringDocumentTarget();
        String result0 = testSubject0.toString();
        OutputStream result1 = testSubject0.getOutputStream();
        IRI result2 = testSubject0.getDocumentIRI();
        boolean result3 = testSubject0.isWriterAvailable();
        Writer result4 = testSubject0.getWriter();
        boolean result5 = testSubject0.isOutputStreamAvailable();
        boolean result6 = testSubject0.isDocumentIRIAvailable();
    }

    public void shouldTestSystemOutDocumentTarget() throws Exception {
        SystemOutDocumentTarget testSubject0 = new SystemOutDocumentTarget();
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        String result6 = testSubject0.toString();
    }

    public void shouldTestToStringRenderer() throws Exception {
        ToStringRenderer testSubject0 = ToStringRenderer.getInstance();
        ToStringRenderer result0 = ToStringRenderer.getInstance();
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        testSubject0.setRenderer(mock(OWLObjectRenderer.class));
        String result2 = testSubject0.getRendering(mock(OWLObject.class));
        String result3 = testSubject0.toString();
    }

    public void shouldTestWriterDocumentTarget() throws Exception {
        WriterDocumentTarget testSubject0 = new WriterDocumentTarget(
                mock(Writer.class));
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestXMLUtils() throws Exception {
        XMLUtils testSubject0 = new XMLUtils();
        boolean result0 = XMLUtils.isXMLNameStartCharacter(0);
        boolean result1 = XMLUtils.isXMLNameChar(0);
        boolean result2 = XMLUtils.isNCNameStartChar(0);
        boolean result3 = XMLUtils.isNCNameChar(0);
        boolean result4 = XMLUtils.isNCName(mock(CharSequence.class));
        boolean result5 = XMLUtils.isQName(mock(CharSequence.class));
        boolean result6 = XMLUtils.hasNCNameSuffix(mock(CharSequence.class));
        int result7 = XMLUtils.getNCNameSuffixIndex(mock(CharSequence.class));
        String result8 = XMLUtils.getNCNameSuffix(mock(CharSequence.class));
        String result9 = XMLUtils.getNCNamePrefix(mock(CharSequence.class));
        String result10 = XMLUtils.escapeXML(mock(CharSequence.class));
        String result11 = testSubject0.toString();
    }

    public void shouldTestZipDocumentTarget() throws Exception {
        ZipDocumentTarget testSubject0 = new ZipDocumentTarget(mock(File.class));
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        String result6 = testSubject0.toString();
    }
}
