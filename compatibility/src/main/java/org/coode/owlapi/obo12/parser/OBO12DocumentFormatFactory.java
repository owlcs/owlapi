package org.coode.owlapi.obo12.parser;

import org.semanticweb.owlapi.util.OWLDocumentFormatFactoryImpl;

@SuppressWarnings("javadoc")
public class OBO12DocumentFormatFactory extends OWLDocumentFormatFactoryImpl {

    /**
     * Default constructor.
     */
    public OBO12DocumentFormatFactory() {
        super(new OBO12DocumentFormat());
    }
}
