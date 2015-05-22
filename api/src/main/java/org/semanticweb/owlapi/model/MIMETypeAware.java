package org.semanticweb.owlapi.model;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Interface for MIME type aware objects. These can be instances of format,
 * parser or storer factories. Formats can use annotations for the same purpose,
 * but generic factories cannot use type annotations to change behaviour.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public interface MIMETypeAware {

    /**
     * Returns the default MIME Type for the OWLOntologyFormat that this class
     * is a factory for.
     * 
     * @return The default MIME Type for the OWLOntologyFormat that this class
     *         is a factory for or null if no MIME Types are specified.
     */
    @Nullable
    String getDefaultMIMEType();

    /**
     * Returns a sorted list of MIMETypes for the OWLOntologyFormat that this
     * class is a factory for. If this list is not empty, the first element in
     * the returned list must be the default MIMEType.
     * 
     * @return A list of strings containing the known MIME types for this
     *         format.
     */
    List<String> getMIMETypes();

    /**
     * Determines whether either getDefaultMIMEType() equals the given mimeType
     * or getMIMETypes() contains the given mimeType.
     * 
     * @param mimeType
     *        The MIME type to match against.
     * @return True if the given MIME type matches this format.
     */
    boolean handlesMimeType(String mimeType);
}
