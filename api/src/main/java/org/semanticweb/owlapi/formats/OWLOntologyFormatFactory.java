/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.semanticweb.owlapi.model.OWLOntologyFormat;
import java.util.List;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public interface OWLOntologyFormatFactory
{
    
    /**
     * Returns a new mutable instance of the OWLOntologyFormat that this class is factory for.
     * 
     * NOTE: If OWLOntologyFormat is changed in the future to be immutable by design instead of
     * mutable, these factories will be unnecessary.
     * 
     * @return A new mutable instance of the OWLOntologyFormat that this class is a factory for.
     */
    OWLOntologyFormat getNewFormat();
    
    /**
     * Returns the key for the OWLOntologyFormat that this class is a factory for without
     * necessarily creating an instance of the OWLOntologyFormat.
     * 
     * @return The key for the OWLOntologyFormat.
     */
    String getKey();
    
    /**
     * Returns the default MIME Type for the OWLOntologyFormat that this class is a factory for.
     * 
     * @return The default MIME Type for the OWLOntologyFormat that this class is a factory for or
     *         null if no MIME Types are specified.
     */
    String getDefaultMIMEType();
    
    /**
     * Returns a sorted list of MIMETypes for the OWLOntologyFormat that this class is a factory
     * for.
     * 
     * If this list is not empty, the first element in the returned list must be the default
     * MIMEType.
     * 
     * @return A list of strings containing the known MIME types for this format.
     */
    List<String> getMIMETypes();
    
    /**
     * Determines whether either getDefaultMIMEType() equals the given mimeType or getMIMETypes()
     * contains the given mimeType.
     * 
     * @param mimeType The MIME type to match against.
     * @return True if the given MIME type matches this format.
     */
    boolean handlesMimeType(String mimeType);
    
    /**
     * Returns true if this format can be represented using textual characters. Returns false if
     * this format must be dealt with using binary methods.
     * 
     * @return True if this format is textual, and false if it is a binary format.
     */
    boolean isTextual();
}
