package org.semanticweb.owlapi.io;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Base class for OWLOntologyDocumentSource.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public abstract class OWLOntologyDocumentSourceBase implements
        OWLOntologyDocumentSource {

    @Nullable
    private final OWLOntologyFormat format;
    @Nullable
    private final String mimeType;

    /**
     * Constructs an ontology input source using the specified file.
     * 
     * @param format
     *        ontology format. If null, it is considered unspecified
     * @param mime
     *        mime type. If null or empty, it is considered unspecified.
     */
    public OWLOntologyDocumentSourceBase(@Nullable OWLOntologyFormat format,
            @Nullable String mime) {
        this.format = format;
        mimeType = mime;
    }

    @Nullable
    @Override
    public final OWLOntologyFormat getFormat() {
        return format;
    }

    @Override
    public final boolean isFormatKnown() {
        return format != null;
    }

    @Nullable
    @Override
    public final String getMIMEType() {
        return mimeType;
    }

    @Override
    public final boolean isMIMETypeKnown() {
        return mimeType != null && !mimeType.isEmpty();
    }
}
