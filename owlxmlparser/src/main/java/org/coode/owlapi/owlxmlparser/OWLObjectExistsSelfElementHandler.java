package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectExistsSelfElementHandler extends AbstractClassExpressionElementHandler {

    private OWLObjectPropertyExpression property;

    public OWLObjectExistsSelfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }


    @Override
	protected void endClassExpressionElement() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Was expecting object property expression element");
        }
        setClassExpression(getOWLDataFactory().getOWLObjectHasSelf(property));
    }


}
