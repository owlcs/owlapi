package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLDataComplementOfElementHandler extends AbstractOWLDataRangeHandler {

    private OWLDataRange operand;

    public OWLDataComplementOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) {
        operand = handler.getOWLObject();
    }


    @Override
	protected void endDataRangeElement() throws OWLXMLParserException {
        if (operand == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "data range element");
        }
        setDataRange(getOWLDataFactory().getOWLDataComplementOf(operand));
    }
}
