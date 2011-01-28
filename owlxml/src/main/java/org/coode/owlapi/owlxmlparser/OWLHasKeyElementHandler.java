package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 20-May-2009
 */
public class OWLHasKeyElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLClassExpression ce;

    private Set<OWLPropertyExpression> props = new HashSet<OWLPropertyExpression>();

    public OWLHasKeyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        props.clear();
    }

    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        ce = handler.getOWLObject();
    }

    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        props.add(handler.getOWLObject());
    }

    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        props.add(handler.getOWLObject());
    }

    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLHasKeyAxiom(ce, props, getAnnotations());
    }
}
