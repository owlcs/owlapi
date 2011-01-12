package org.coode.owlapi.owlxmlparser;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserSAXException;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public class OWLXMLParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        try {
            System.setProperty("entityExpansionLimit", "100000000");
            OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            InputSource isrc = getInputSource(documentSource);
            OWLXMLParserHandler handler = new OWLXMLParserHandler(getOWLOntologyManager(), ontology, configuration);
            parser.parse(isrc, handler);
            Map<String, String> prefix2NamespaceMap = handler.getPrefixName2PrefixMap();
            for(String prefix : prefix2NamespaceMap.keySet()) {
                format.setPrefix(prefix, prefix2NamespaceMap.get(prefix));
            }
            return format;
        }
        catch (ParserConfigurationException e) {
            // What the hell should be do here?  In serious trouble if this happens
            throw new OWLRuntimeException(e);
        }
        catch (TranslatedOWLParserException e) {
            throw e.getParserException();
        }
        catch (TranslatedUnloadableImportException e) {
            throw e.getUnloadableImportException();
        }
        catch (SAXException e) {
            // General exception
            throw new OWLParserSAXException(e);
        }
    }
}
