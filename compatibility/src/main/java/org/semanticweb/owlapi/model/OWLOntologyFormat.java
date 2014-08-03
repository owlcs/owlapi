package org.semanticweb.owlapi.model;

import java.io.Serializable;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;

/** @deprecated this class is here only to help with backwards compatibility */
@Deprecated
@SuppressWarnings("javadoc")
public interface OWLOntologyFormat {

    boolean isAddMissingTypes();

    void setAddMissingTypes(boolean addMissingTypes);

    void setParameter(Serializable key, Serializable value);

    Serializable getParameter(Serializable key, Serializable defaultValue);

    boolean isPrefixOWLOntologyFormat();

    PrefixDocumentFormat asPrefixOWLOntologyFormat();

    OWLOntologyLoaderMetaData getOntologyLoaderMetaData();

    void setOntologyLoaderMetaData(OWLOntologyLoaderMetaData loaderMetaData);

    String getKey();

    boolean isTextual();
}
