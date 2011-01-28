package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public abstract class AbstractOWLElementHandler<O> implements OWLElementHandler<O> {

    private OWLXMLParserHandler handler;

    private OWLElementHandler<?> parentHandler;


    private StringBuilder sb;

    private String elementName;


    protected AbstractOWLElementHandler(OWLXMLParserHandler handler) {
        this.handler = handler;
    }

    public OWLOntologyLoaderConfiguration getConfiguration() {
        return handler.getConfiguration();
    }

    public IRI getIRIFromAttribute(String localName, String value) throws OWLParserException {
        if(localName.equals(OWLXMLVocabulary.IRI_ATTRIBUTE.getShortName())) {
            return getIRI(value);
        }
        else if(localName.equals(OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE.getShortName())) {
            return getAbbreviatedIRI(value);
        }
        else if(localName.equals("URI")) {
            // Legacy
            return getIRI(value);
        }
        throw new OWLXMLParserAttributeNotFoundException(getLineNumber(), getColumnNumber(), OWLXMLVocabulary.IRI_ATTRIBUTE.getShortName());
    }


    public IRI getIRIFromElement(String elementLocalName, String textContent) throws OWLParserException {
        if(elementLocalName.equals(OWLXMLVocabulary.IRI_ELEMENT.getShortName())) {
            return handler.getIRI(textContent.trim());
        }
        else if(elementLocalName.equals(OWLXMLVocabulary.ABBREVIATED_IRI_ELEMENT.getShortName())) {
            return handler.getAbbreviatedIRI(textContent.trim());
        }
        throw new OWLXMLParserException(elementLocalName + " is not an IRI element", getLineNumber(), getColumnNumber());
    }


    protected OWLOntologyManager getOWLOntologyManager() throws OWLXMLParserException {
        return handler.getOWLOntologyManager();
    }


    protected OWLOntology getOntology() {
        return handler.getOntology();
    }


    protected OWLDataFactory getOWLDataFactory() {
        return handler.getDataFactory();
    }


    public void setParentHandler(OWLElementHandler<?> handler) {
        this.parentHandler = handler;
    }


    protected OWLElementHandler<?> getParentHandler() {
        return parentHandler;
    }

    @SuppressWarnings("unused")
    public void attribute(String localName, String value) throws OWLParserException {

    }

    protected IRI getIRI(String iri) throws OWLParserException {
        return handler.getIRI(iri);
    }

    protected IRI getAbbreviatedIRI(String abbreviatedIRI) throws OWLParserException {
        return handler.getAbbreviatedIRI(abbreviatedIRI);
    }

//    protected URI getIRI(String uri) throws OWLXMLParserException {
//        return handler.getIRI(uri);
//    }

    protected int getLineNumber() {
        return handler.getLineNumber();
    }

    protected int getColumnNumber() {
        return handler.getColumnNumber();
    }

    // TODO: Make final
    public void startElement(String name) throws OWLXMLParserException {
        sb = null;
        elementName = name;
    }

    protected String getElementName() throws OWLXMLParserException {
        return elementName;
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractOWLAxiomElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractClassExpressionElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractOWLDataRangeHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(AbstractOWLObjectPropertyElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLDataPropertyElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLIndividualElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLLiteralElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLAnnotationElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLSubObjectPropertyChainElementHandler _handler) throws OWLXMLParserException {
    }

    @SuppressWarnings("unused")
    public void handleChild(OWLDatatypeFacetRestrictionElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(OWLAnnotationPropertyElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(OWLAnonymousIndividualElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(AbstractIRIElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(SWRLVariableElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(SWRLAtomElementHandler _handler) throws OWLXMLParserException {
    }
    @SuppressWarnings("unused")
    public void handleChild(SWRLAtomListElementHandler _handler) throws OWLXMLParserException {
    }

    final public void handleChars(char[] chars, int start, int length) {
        if (isTextContentPossible()) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(chars, start, length);
        }
    }


    public String getText() {
        if (sb == null) {
            return "";
        }
        else {
            return sb.toString();
        }
    }


    public boolean isTextContentPossible() {
        return false;
    }
}
