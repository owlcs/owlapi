package org.coode.owlapi.rdfxml.parser;

import java.io.IOException;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.rdf.syntax.RDFParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class RDFXMLParser extends AbstractOWLParser {

    private OWLOntologyManager owlOntologyManager;

    private OWLRDFConsumer consumer;


    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        try {
            final RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
            if (owlOntologyManager == null) {
                throw new OWLRuntimeException("Cannot parse because OWLOntologyManager is null!");
            }
            final RDFParser parser = new RDFParser() {
                @Override
				public void startPrefixMapping(String prefix, String IRI) throws SAXException {
                    super.startPrefixMapping(prefix, IRI);
                    format.setPrefix(prefix, IRI);
                }


                @Override
				public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws SAXException {
                    super.startElement(namespaceIRI, localName, qName, atts);
                    String value = atts.getValue(XMLNS, "base");
                    if (value != null) {
//                        consumer.setXMLBase(value);
                    }
                }
            };
            IRIProvider prov = new IRIProvider() {
                public IRI getIRI(String s) {
                    return parser.getIRI(s);
                }
            };
            consumer = new OWLRDFConsumer(owlOntologyManager, ontology, new AnonymousNodeChecker() {
                public boolean isAnonymousNode(IRI IRI) {
                    return parser.isAnonymousNodeIRI(IRI.getFragment());
                }

                public boolean isAnonymousSharedNode(String iri) {
                    return parser.isAnonymousNodeID(iri);
                }

                public boolean isAnonymousNode(String IRI) {
                    return parser.isAnonymousNodeIRI(IRI);
                }
            }, configuration);
            consumer.setIRIProvider(prov);
            consumer.setOntologyFormat(format);
            InputSource is = getInputSource(documentSource);
            parser.parse(is, consumer);
            return format;
        }
        catch (TranslatedOntologyChangeException e) {
            throw e.getCause();
        }
        catch (TranslatedUnloadedImportException e) {
            throw e.getCause();
        }
        catch (SAXException e) {
            throw new OWLRDFXMLParserSAXException(e);
        }
    }

    @Override
	public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }
}
