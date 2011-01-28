package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLObjectPropertyAssertionAxiomElementHandler extends AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLIndividual> {

    public AbstractOWLObjectPropertyAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void handleChild(OWLAnonymousIndividualElementHandler handler) throws OWLXMLParserException {
        if (getSubject() == null) {
            setSubject(handler.getOWLObject());
        }
        else if (getObject() == null) {
            setObject(handler.getOWLObject());
        }
    }

    @Override
	public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        if (getSubject() == null) {
            setSubject(handler.getOWLObject());
        }
        else if (getObject() == null) {
            setObject(handler.getOWLObject());
        }
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
