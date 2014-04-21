/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

import javax.annotation.Nonnull;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public interface RioRDFOntologyFormatFactory extends OWLOntologyFormatFactory {

    @Nonnull
    @Override
    RioRDFOntologyFormat createFormat();

    /**
     * @return RDFFormat for this format
     */
    RDFFormat getRioFormat();
}
