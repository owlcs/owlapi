package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLDataPropertyOperandAxiomElementHandler extends AbstractOperandAxiomElementHandler<OWLDataPropertyExpression> {

    public AbstractOWLDataPropertyOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) {
        addOperand(handler.getOWLObject());
    }
}
