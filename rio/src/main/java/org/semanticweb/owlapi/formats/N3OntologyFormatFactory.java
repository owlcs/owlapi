/**
 * 
 */
package org.semanticweb.owlapi.formats;

import javax.annotation.Nonnull;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class N3OntologyFormatFactory extends
        AbstractRioRDFOntologyFormatFactory implements
        RioRDFOntologyFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public RioRDFOntologyFormat createFormat() {
        return new N3OntologyFormat();
    }
}
