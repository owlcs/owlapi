package org.semanticweb.owlapi.model;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 02-Jan-2007<br><br>
 *
 * Represents the concrete representation format of
 * an ontology.  The equality of an ontology format
 * is defined by the equals and hashCode method (not
 * its identity).
 */
public abstract class OWLOntologyFormat {

    private Map<Object, Object> paramaterMap;

    private OWLOntologyLoaderMetaData loaderMetaData = new NullLoaderMetaData();

    private Map<Object, Object> getParameterMap() {
        if(paramaterMap == null) {
            paramaterMap = new HashMap<Object, Object>();
        }
        return paramaterMap;
    }

    public void setParameter(Object key, Object value) {
        getParameterMap().put(key, value);
    }

    public Object getParameter(Object key, Object defaultValue) {
        Object val = getParameterMap().get(key);
        if(val != null) {
            return val;
        }
        else {
            return defaultValue;
        }
    }

    /**
     * Determines if this format is an instance of a format that uses prefixes to shorted IRIs
     * @return <code>true</code> if this format is an instance of {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}
     * other wise <code>false</code>.
     */
    public boolean isPrefixOWLOntologyFormat() {
        return this instanceof PrefixOWLOntologyFormat;
    }

    /**
     * If this format is an instance of {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat} then this method
     * will obtain it as a {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}
     * @return This format as a more specific {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}.
     * @throws ClassCastException if this format is not an instance of {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}
     */
    public PrefixOWLOntologyFormat asPrefixOWLOntologyFormat() {
        return (PrefixOWLOntologyFormat) this;
    }

    /**
     * If this format describes an ontology that was loaded from some ontology document (rather than created programmatically)
     * there may be some meta data about the loading process.  Subclasses of <code>OWLOntologyFormat</code> will provide
     * accessors etc. to details pertaining to the meta data about loading.
     * @return An object containing the meta data about loading.  Not <code>null</code>.
     */
    public OWLOntologyLoaderMetaData getOntologyLoaderMetaData() {
        return loaderMetaData;
    }

    /**
     * Sets the meta data for the ontology loader.
     * @param loaderMetaData The metadata. Must not be <code>null</code>.
     * @throws NullPointerException if the <code>loaderMetaData</code> is <code>null</code>.
     */
    public void setOntologyLoaderMetaData(OWLOntologyLoaderMetaData loaderMetaData) {
        this.loaderMetaData = loaderMetaData;
    }

    @Override
	public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass().equals(getClass()));
    }


    @Override
	public int hashCode() {
        return getClass().hashCode();
    }

    private static class NullLoaderMetaData implements OWLOntologyLoaderMetaData {

    }
}

