package org.coode.owlapi.owlxmlparser;

import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLSubObjectPropertyOfAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLObjectPropertyExpression subProperty;

    private List<OWLObjectPropertyExpression> propertyList;

    private OWLObjectPropertyExpression superProperty;


    public OWLSubObjectPropertyOfAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        if (subProperty == null && propertyList == null) {
            subProperty = handler.getOWLObject();
        } else if (superProperty == null) {
            superProperty = handler.getOWLObject();
        } else {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected two object property expression elements");
        }
    }


    @Override
	public void handleChild(OWLSubObjectPropertyChainElementHandler handler) {
        propertyList = handler.getOWLObject();
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (subProperty != null) {
            return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, getAnnotations());
        } else {
            return getOWLDataFactory().getOWLSubPropertyChainOfAxiom(propertyList, superProperty, getAnnotations());
        }
    }
}
