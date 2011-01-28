package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectComplementOfElementHandler extends AbstractClassExpressionElementHandler {

    private OWLClassExpression operand;


    public OWLObjectComplementOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) {
        operand = handler.getOWLObject();
    }


    @Override
	protected void endClassExpressionElement() throws OWLXMLParserException {
        if (operand == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "class expression element");
        }
        setClassExpression(getOWLDataFactory().getOWLObjectComplementOf(operand));
    }
}
