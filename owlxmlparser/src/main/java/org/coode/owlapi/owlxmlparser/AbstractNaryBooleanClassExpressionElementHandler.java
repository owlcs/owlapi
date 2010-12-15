package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractNaryBooleanClassExpressionElementHandler extends AbstractClassExpressionElementHandler {

    private Set<OWLClassExpression> operands;

    public AbstractNaryBooleanClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<OWLClassExpression>();
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) {
        operands.add(handler.getOWLObject());
    }


    @Override
	protected void endClassExpressionElement() throws OWLXMLParserException {
        if (operands.size() >= 2) {
            setClassExpression(createClassExpression(operands));
        }
        else if(operands.size() == 1) {
            setClassExpression(operands.iterator().next());
        }
        else {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Found zero child elements of an " + getElementName() + " element. At least 2 class expression elements are required as child elements of " + getElementName() + " elements");
        }
    }

    protected abstract OWLClassExpression createClassExpression(Set<OWLClassExpression> operands);

}
