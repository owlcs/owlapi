package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOperandAxiomElementHandler<O extends OWLObject> extends AbstractOWLAxiomElementHandler {

    private Set<O> operands;

    public AbstractOperandAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<O>();
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        operands.clear();
    }


    protected Set<O> getOperands() {
        return operands;
    }

    protected void addOperand(O operand) {
        operands.add(operand);
    }


}
