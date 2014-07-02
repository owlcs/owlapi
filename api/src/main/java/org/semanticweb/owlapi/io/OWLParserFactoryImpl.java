package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Generic parser factory.
 * 
 * @author ignazio
 * @param <T>
 *        type to build
 */
public abstract class OWLParserFactoryImpl implements OWLParserFactory {

    private static final long serialVersionUID = 40000L;
    private final List<String> mimeTypes;

    protected OWLParserFactoryImpl() {
        this(new ArrayList<String>());
    }

    protected OWLParserFactoryImpl(List<String> mimeTypes) {
        this.mimeTypes = Collections
                .unmodifiableList(new ArrayList<>(mimeTypes));
    }

    @Override
    public final OWLParser get() {
        return createParser();
    }

    @Nullable
    @Override
    public final String getDefaultMIMEType() {
        if (mimeTypes.isEmpty()) {
            return null;
        } else {
            return mimeTypes.get(0);
        }
    }

    @Override
    public final List<String> getMIMETypes() {
        if (mimeTypes.isEmpty()) {
            return CollectionFactory.emptyList();
        } else {
            return mimeTypes;
        }
    }

    @Override
    public final boolean handlesMimeType(String mimeType) {
        return mimeTypes.contains(mimeType);
    }
}
