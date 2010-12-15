package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLSubClassAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLClassExpression subClass;

    private OWLClassExpression supClass;

    public OWLSubClassAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        subClass = null;
        supClass = null;
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        if (subClass == null) {
            subClass = handler.getOWLObject();
        } else if (supClass == null) {
            supClass = handler.getOWLObject();
        } 
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, supClass, getAnnotations());
    }
}
