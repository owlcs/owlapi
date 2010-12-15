package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLDataHasValueElementHandler extends AbstractDataRestrictionElementHandler<OWLLiteral> {


    public OWLDataHasValueElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException {
        setFiller(handler.getOWLObject());
    }


    @Override
	protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLDataHasValue(getProperty(), getFiller());
    }
}
