/**
 * 
 */
package org.semanticweb.owlapi.formats;

import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyStorerFactory;

import javax.annotation.Nullable;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public interface RioRDFOntologyStorerFactory extends OWLOntologyStorerFactory {

    @Nullable
    @Override
    OWLOntologyFormatFactory getFormatFactory();
}
