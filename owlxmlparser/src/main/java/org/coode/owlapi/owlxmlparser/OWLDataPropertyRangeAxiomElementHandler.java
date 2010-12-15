package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLDataPropertyRangeAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLDataPropertyExpression property;

    private OWLDataRange range;

    public OWLDataPropertyRangeAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) {
        range = handler.getOWLObject();
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "data property element");
        }
        if (range == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "data range element");
        }
        return getOWLDataFactory().getOWLDataPropertyRangeAxiom(property, range, getAnnotations());
    }
}
