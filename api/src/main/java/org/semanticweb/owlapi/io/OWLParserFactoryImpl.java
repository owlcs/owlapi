package org.semanticweb.owlapi.io;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.annotations.SupportsMIMEType;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Generic parser factory.
 * 
 * @author ignazio
 * @param <T>
 *        type to build
 */
public class OWLParserFactoryImpl<T extends OWLParser> implements
        OWLParserFactory {

    private static final long serialVersionUID = 40000L;
    private Class<T> type;

    /**
     * @param type
     *        type to build
     */
    public OWLParserFactoryImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public OWLParser createParser() {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new OWLRuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public Set<OWLOntologyFormatFactory> getSupportedFormats() {
        return createParser().getSupportedFormats();
    }

    @Override
    public OWLParser get() {
        return createParser();
    }

    @Nullable
    @Override
    public String getDefaultMIMEType() {
        SupportsMIMEType annotation = type
                .getAnnotation(SupportsMIMEType.class);
        if (annotation != null) {
            return annotation.defaultMIMEType();
        }
        return null;
    }

    @Override
    public List<String> getMIMETypes() {
        SupportsMIMEType annotation = type
                .getAnnotation(SupportsMIMEType.class);
        if (annotation != null) {
            return Arrays.asList(annotation.supportedMIMEtypes());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean handlesMimeType(String mimeType) {
        return mimeType.equals(getDefaultMIMEType())
                || getMIMETypes().contains(mimeType);
    }
}
