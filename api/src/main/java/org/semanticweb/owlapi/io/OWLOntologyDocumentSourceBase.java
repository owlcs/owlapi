package org.semanticweb.owlapi.io;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

import com.google.common.base.Optional;

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

    private final Optional<OWLDocumentFormat> format;
    private final Optional<String> mimeType;
    protected final AtomicBoolean loadable = new AtomicBoolean(true);

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
        this.format = Optional.fromNullable(format);
        mimeType = Optional.fromNullable(mime);
    }

    @Override
    public boolean readerOrInputStreamExists() {
        return loadable.get();
    }

    @Override
    public Optional<OWLDocumentFormat> getFormat() {
        return format;
    }

    @Override
    public Optional<String> getMIMEType() {
        return mimeType;
    }
}
