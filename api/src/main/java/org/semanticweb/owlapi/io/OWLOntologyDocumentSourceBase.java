package org.semanticweb.owlapi.io;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Base class for OWLOntologyDocumentSource.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public abstract class OWLOntologyDocumentSourceBase implements
        OWLOntologyDocumentSource {

    private static final AtomicLong COUNTER = new AtomicLong();

    /**
     * @param prefix
     *        prefix for result
     * @return a fresh IRI
     */
    @Nonnull
    public static IRI getNextDocumentIRI(String prefix) {
        return IRI.create(prefix + COUNTER.incrementAndGet());
    }

    private final OWLOntologyFormat format;
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

    @Override
    public OWLOntologyFormat getFormat() {
        return format;
    }

    @Override
    public boolean isFormatKnown() {
        return format != null;
    }

    @Override
    public String getMIMEType() {
        return mimeType;
    }

    @Override
    public boolean isMIMETypeKnown() {
        return mimeType != null && !mimeType.isEmpty();
    }
}
