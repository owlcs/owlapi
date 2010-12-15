package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.SAXException;

/**
 * Receives notifications about triples generated during the parsing process.
 */
public interface RDFConsumer {
    /**
     * Called when model parsing is started.
     *
     * @param physicalURI           physical URI of the model
     */
    void startModel(String physicalURI) throws SAXException;
    /**
     * Called when model parsing is finished.
     */
    void endModel() throws SAXException;
    /**
     * Called when a statement with resource value is added to the model.
     *
     * @param subject               URI of the subject resource
     * @param predicate             URI of the predicate resource
     * @param object                URI of the object resource
     */
    void statementWithResourceValue(String subject,String predicate,String object) throws SAXException;
    /**
     * Called when a statement with literal value is added to the model.
     *
     * @param subject               URI of the subject resource
     * @param predicate             URI of the predicate resource
     * @param object                literal object value
     * @param language              the language
     * @param datatype              the URI of the literal's datatype (may be <code>null</code>)
     */
    void statementWithLiteralValue(String subject,String predicate,String object,String language,String datatype) throws SAXException;
    /**
     * Receives the logical URI of the model.
     *
     * @param logicalURI            logical URI of the model
     */
    void logicalURI(String logicalURI) throws SAXException;
    /**
     * Receives the notification that the model being parsed includes another model with supplied URIs.
     *
     * @param logicalURI            logical URI of the model
     * @param physicalURI           physical URI of the model
     */
    void includeModel(String logicalURI,String physicalURI) throws SAXException;
    /**
     * Receives the notification that the attribute and its value has been parsed.
     *
     * @param key                   the key of the attribute
     * @param value                 the value of the attribute
     */
    void addModelAttribte(String key,String value) throws SAXException;
}

