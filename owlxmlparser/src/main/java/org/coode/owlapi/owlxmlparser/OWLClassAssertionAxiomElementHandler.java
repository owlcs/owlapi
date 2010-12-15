package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLClassAssertionAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLIndividual individual;

    private OWLClassExpression classExpression;

    public OWLClassAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) {
        classExpression = handler.getOWLObject();
    }


    @Override
	public void handleChild(OWLIndividualElementHandler handler) {
        individual = handler.getOWLObject();
    }

    @Override
	public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException {
        individual = handler.getOWLObject();
    }

    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (individual == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "individual element");
        }
        if (classExpression == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "classExpression kind element");
        }
        return getOWLDataFactory().getOWLClassAssertionAxiom(classExpression, individual, getAnnotations());
    }
}
