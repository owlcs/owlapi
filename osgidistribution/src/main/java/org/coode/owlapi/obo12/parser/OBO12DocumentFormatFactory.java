package org.coode.owlapi6.obo12.parser;

import org.semanticweb.owlapi6.formats.OWLDocumentFormatFactoryImpl;

@SuppressWarnings("javadoc")
public class OBO12DocumentFormatFactory extends OWLDocumentFormatFactoryImpl {

    /**
     * Default constructor.
     */
    public OBO12DocumentFormatFactory() {
        super(new OBO12DocumentFormat());
    }
}
