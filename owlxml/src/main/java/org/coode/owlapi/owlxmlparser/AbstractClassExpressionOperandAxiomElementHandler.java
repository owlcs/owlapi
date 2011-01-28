package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractClassExpressionOperandAxiomElementHandler extends AbstractOperandAxiomElementHandler<OWLClassExpression> {

    public AbstractClassExpressionOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) {
        addOperand(handler.getOWLObject());
    }
}
