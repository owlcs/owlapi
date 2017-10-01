package org.semanticweb.owlapi.model;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Interface for MIME type aware objects. These can be instances of format, parser or storer
 * factories. Formats can use annotations for the same purpose, but generic factories cannot use
 * type annotations to change behaviour.
 *
 * @author ignazio
 * @since 4.0.0
 */
public interface MIMETypeAware {

    /**
     * Returns the default MIME Type for the OWLDocumentFormat that this class is a factory for.
     *
     * @return The default MIME Type for the OWLDocumentFormat that this class is a factory for or
     *         null if no MIME Types are specified.
     */
    @Nullable
    String getDefaultMIMEType();

    /**
     * Returns a sorted list of MIMETypes for the OWLDocumentFormat that this class is a factory
     * for. If this list is not empty, the first element in the returned list must be the default
     * MIMEType.
     *
     * @return A list of strings containing the known MIME types for this format.
     */
    default List<String> getMIMETypes() {
        String defaultMIMEType = getDefaultMIMEType();
        if (defaultMIMEType == null || defaultMIMEType.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(getDefaultMIMEType());
    }

    /**
     * Determines whether either getDefaultMIMEType() equals the given mimeType or getMIMETypes()
     * contains the given mimeType.
     *
     * @param mimeType The MIME type to match against.
     * @return True if the given MIME type matches this format.
     */
    default boolean handlesMimeType(String mimeType) {
        String type = stripWeight(mimeType);
        return getMIMETypes().stream().map(MIMETypeAware::stripWeight)
            .anyMatch(m -> m.equals(type));
    }

    /**
     * Utility to reduce a mime format like "text/plain; q=0.1" to "text/plain"
     * 
     * @param mime input
     * @return mime type without weight
     */
    static String stripWeight(String mime) {
        int semiColon = mime.indexOf(';');
        if (semiColon > -1) {
            return mime.substring(0, semiColon).trim();
        }
        return mime.trim();
    }
}
