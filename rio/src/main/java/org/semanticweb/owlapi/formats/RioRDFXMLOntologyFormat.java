package org.semanticweb.owlapi.formats;

import org.openrdf.rio.RDFFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class RioRDFXMLOntologyFormat extends RioRDFOntologyFormat {

    private static final long serialVersionUID = -585049128763071389L;

    /**
     * @param format
     *        RDFFormat for this format
     */
    public RioRDFXMLOntologyFormat(RDFFormat format) {
        super(format);
    }
}
