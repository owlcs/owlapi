package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLDataPropertyAssertionAxiomElementHandler extends AbstractOWLAssertionAxiomElementHandler<OWLDataPropertyExpression, OWLLiteral> {

    public AbstractOWLDataPropertyAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException {
        setSubject(handler.getOWLObject());
    }

    @Override
	public void handleChild(OWLIndividualElementHandler handler) {
        setSubject(handler.getOWLObject());
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }


    @Override
	public void handleChild(OWLLiteralElementHandler handler) {
        setObject(handler.getOWLObject());
    }
}
