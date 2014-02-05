/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 *
 */
public interface RioRDFOntologyFormatFactory extends OWLOntologyFormatFactory
{
    RioRDFOntologyFormat getNewFormat();
    
    RDFFormat getRioFormat();
}
