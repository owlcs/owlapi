package org.semanticweb.owlapi6.documents;

import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.io.OWLParser;
import org.semanticweb.owlapi6.io.OWLParserFactory;
import org.semanticweb.owlapi6.model.OWLDocumentFormatFactory;

/**
 * Generic parser factory.
 *
 * @author ignazio
 */
public abstract class OWLParserFactoryImpl implements OWLParserFactory {

    private final OWLDocumentFormatFactory format;

    protected OWLParserFactoryImpl(OWLDocumentFormatFactory format) {
        this.format = format;
    }

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return format;
    }

    @Override
    public final OWLParser get() {
        return createParser();
    }

    @Override
    @Nullable
    public final String getDefaultMIMEType() {
        return format.getDefaultMIMEType();
    }

    @Override
    public final List<String> getMIMETypes() {
        return format.getMIMETypes();
    }

    @Override
    public final boolean handlesMimeType(String mimeType) {
        return format.handlesMimeType(mimeType);
    }
}
