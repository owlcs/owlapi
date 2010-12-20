package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class OWLSubAnnotationPropertyOfElementHandler extends AbstractOWLAxiomElementHandler {

    OWLAnnotationProperty subProperty = null;

    OWLAnnotationProperty superProperty = null;

    public OWLSubAnnotationPropertyOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        if(subProperty == null) {
            subProperty = handler.getOWLObject();
        }
        else if(superProperty == null) {
            superProperty = handler.getOWLObject();
        }
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if(subProperty == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "AnnotationProperty for sub property");
        }
        if(superProperty == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "AnnotationProperty for super property");
        }
        return getOWLDataFactory().getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty, getAnnotations());
    }
}
