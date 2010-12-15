package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLSubDataPropertyOfAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLDataPropertyExpression subProperty;

    private OWLDataPropertyExpression superProperty;


    public OWLSubDataPropertyOfAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        if (subProperty == null) {
            subProperty = handler.getOWLObject();
        }
        else if (superProperty == null) {
            superProperty = handler.getOWLObject();
        }
        else {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "two data property expression elements");
        }
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(subProperty, superProperty, getAnnotations());
    }
}
