package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

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

    /**
     * Wrap an input stream to strip BOMs.
     * 
     * @param delegate
     *        delegate to wrap
     * @return wrapped input stream
     */
    @Nonnull
    public static InputStream wrap(@Nonnull InputStream delegate) {
        checkNotNull(delegate, "delegate cannot be null");
        return new BOMInputStream(delegate, ByteOrderMark.UTF_8,
                ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE,
                ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE);
    }

    private final OWLDocumentFormat format;
    private final String mimeType;

    /**
     * Constructs an ontology input source using the specified file.
     * 
     * @param format
     *        ontology format. If null, it is considered unspecified
     * @param mime
     *        mime type. If null or empty, it is considered unspecified.
     */
    public OWLOntologyDocumentSourceBase(@Nullable OWLDocumentFormat format,
            @Nullable String mime) {
        this.format = format;
        mimeType = mime;
    }

    @Override
    public OWLDocumentFormat getFormat() {
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
