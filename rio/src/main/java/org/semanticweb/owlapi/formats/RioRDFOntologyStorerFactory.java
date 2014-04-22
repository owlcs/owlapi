/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public interface RioRDFOntologyStorerFactory extends OWLOntologyStorerFactory {

    @Nonnull
    @Override
    OWLOntologyFormatFactory getFormatFactory();
}
