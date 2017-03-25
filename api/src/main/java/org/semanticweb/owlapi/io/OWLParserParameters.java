package org.semanticweb.owlapi.io;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * A wrapper for parser specific parameters.
 * 
 * @author ignazio
 * @since 6.0.0
 */
public class OWLParserParameters {
    private final Map<Serializable, Serializable> parameterMap = new HashMap<>();
    private final OWLOntology ontology;
    private final OntologyConfigurator config;
    private final IRI documentIRI;
    private Charset encoding = StandardCharsets.UTF_8;
    @Nullable
    private OWLOntologyLoaderMetaData loaderMetaData = null;

    /**
     * @param o ontology that will be populated with axioms
     * @param c loading config
     * @param iri document iri
     */
    public OWLParserParameters(OWLOntology o, OntologyConfigurator c, IRI iri) {
        ontology = o;
        config = c;
        documentIRI = iri;
    }

    /**
     * @param key key for the new entry
     * @param value value for the new entry
     */
    public void setParameter(Serializable key, Serializable value) {
        parameterMap.put(key, value);
    }

    /**
     * @param key key for the new entry
     * @param defaultValue value for the new entry
     * @param <T> type
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> T getParameter(Serializable key, T defaultValue) {
        Serializable val = parameterMap.get(key);
        if (val == null) {
            return defaultValue;
        }
        return (T) val;
    }

    /**
     * @return all keys in the parameter map
     */
    public Set<Serializable> keys() {
        return parameterMap.keySet();
    }

    /**
     * @param c consumer to apply to all entries
     */
    public void stream(BiConsumer<Serializable, Serializable> c) {
        parameterMap.forEach(c);
    }

    /**
     * @return the loaderMetaData
     */
    @Nullable
    public OWLOntologyLoaderMetaData getLoaderMetaData() {
        return loaderMetaData;
    }

    /**
     * @param metadata the loaderMetaData to set
     * @return this object
     */
    public OWLParserParameters withLoaderMetaData(OWLOntologyLoaderMetaData metadata) {
        loaderMetaData = metadata;
        return this;
    }

    /**
     * @return the ontology
     */
    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * @return the config
     */
    public OntologyConfigurator getConfig() {
        return config;
    }

    /**
     * @return the documentIRI
     */
    public IRI getDocumentIRI() {
        return documentIRI;
    }

    /**
     * @return the encoding
     */
    public Charset getEncoding() {
        return encoding;
    }

    /**
     * @param enc the encoding to set
     * @return this object
     */
    public OWLParserParameters withEncoding(Charset enc) {
        encoding = enc;
        return this;
    }
}
