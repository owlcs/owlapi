package org.semanticweb.owlapi.formats;

import java.util.List;

import org.openrdf.rio.RDFFormat;

/**
 * An abstract implementation of the RioRDFOntologyFormatFactory interface that
 * uses the Rio RDFFormat class to provide information for common methods
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public abstract class AbstractRioRDFOntologyFormatFactory extends
        AbstractOWLOntologyFormatFactory implements RioRDFOntologyFormatFactory {

    private static final long serialVersionUID = 40000L;

    @Override
    public String getKey() {
        return createFormat().getKey();
    }

    @SuppressWarnings("null")
    @Override
    public List<String> getMIMETypes() {
        return getRioFormat().getMIMETypes();
    }

    @Override
    public RDFFormat getRioFormat() {
        return createFormat().getRioFormat();
    }
}
