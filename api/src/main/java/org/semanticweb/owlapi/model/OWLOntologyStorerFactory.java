/**
 * 
 */
package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
public interface OWLOntologyStorerFactory
{
    /**
     * Creates a storer
     * @return The storer created by this storer factory.
     */
    OWLOntologyStorer createStorer();
    
    /**
     * 
     * @return The ontology format used by this OWLOntologyStorerFactory
     */
    OWLOntologyFormatFactory getFormatFactory();
}
