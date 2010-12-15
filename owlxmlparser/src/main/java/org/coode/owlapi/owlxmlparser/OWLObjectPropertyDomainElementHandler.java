package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectPropertyDomainElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLClassExpression domain;

    private OWLObjectPropertyExpression property;

    public OWLObjectPropertyDomainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) {
        domain = handler.getOWLObject();
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected object property element");
        }
        if (domain == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected class expression element");
        }
        return getOWLDataFactory().getOWLObjectPropertyDomainAxiom(property, domain, getAnnotations());
    }
}
