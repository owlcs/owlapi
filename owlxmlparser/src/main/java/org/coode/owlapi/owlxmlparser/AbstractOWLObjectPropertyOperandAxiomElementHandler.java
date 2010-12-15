package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLObjectPropertyOperandAxiomElementHandler extends AbstractOperandAxiomElementHandler<OWLObjectPropertyExpression> {

    public AbstractOWLObjectPropertyOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        addOperand(handler.getOWLObject());
    }
}
