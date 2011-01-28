package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLDataOneOfElementHandler extends AbstractOWLDataRangeHandler {

    Set<OWLLiteral> constants;

    public OWLDataOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        constants = new HashSet<OWLLiteral>();
    }


    @Override
	public void handleChild(OWLLiteralElementHandler handler) {
        constants.add(handler.getOWLObject());
    }


    @Override
	protected void endDataRangeElement() throws OWLXMLParserException {
        if (constants.isEmpty()) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "data oneOf element");
        }
        setDataRange(getOWLDataFactory().getOWLDataOneOf(constants));
    }
}
