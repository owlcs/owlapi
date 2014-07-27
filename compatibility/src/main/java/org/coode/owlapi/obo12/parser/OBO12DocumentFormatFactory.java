package org.coode.owlapi.obo12.parser;

import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.util.OWLDocumentFormatFactoryImpl;

@SuppressWarnings("javadoc")
public class OBO12DocumentFormatFactory extends OWLDocumentFormatFactoryImpl {

    private static final long serialVersionUID = 40000L;

    @Override
    public OWLDocumentFormat get() {
        return createFormat();
    }

    @Override
    public String getKey() {
        return "OBO 1.2 Format";
    }

    @Override
    public OWLDocumentFormat createFormat() {
        return new OBO12DocumentFormat();
    }
}
