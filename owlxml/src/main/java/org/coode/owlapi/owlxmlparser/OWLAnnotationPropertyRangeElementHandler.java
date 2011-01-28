package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class OWLAnnotationPropertyRangeElementHandler extends AbstractOWLAxiomElementHandler {

    private IRI range;

    private OWLAnnotationProperty property;

    public OWLAnnotationPropertyRangeElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractIRIElementHandler handler) throws OWLXMLParserException {
        range = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        property = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if(property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected annotation property element");
        }
        if(range == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected IRI element");
        }
        return getOWLDataFactory().getOWLAnnotationPropertyRangeAxiom(property, range, getAnnotations());
    }
}
