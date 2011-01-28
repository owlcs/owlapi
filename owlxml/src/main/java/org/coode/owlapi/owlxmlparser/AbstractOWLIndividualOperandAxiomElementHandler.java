package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLIndividualOperandAxiomElementHandler extends AbstractOperandAxiomElementHandler<OWLIndividual> {

    public AbstractOWLIndividualOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLIndividualElementHandler handler) {
        addOperand(handler.getOWLObject());
    }

    @Override
	public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException {
        addOperand(handler.getOWLObject());
    }
}
