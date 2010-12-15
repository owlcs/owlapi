package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-May-2009
 */
public class LegacyEntityAnnotationElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLEntity entity;

    private OWLAnnotation annotation;

    public LegacyEntityAnnotationElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        OWLAnnotationAssertionAxiom toReturn = getOWLDataFactory().getOWLAnnotationAssertionAxiom(annotation.getProperty(), entity.getIRI(), annotation.getValue());
        annotation = null;
        entity = null;
		return toReturn;
    }

    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject().asOWLClass();
    }

    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject().asOWLDataProperty();
    }

    @Override
	public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject();
    }

    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        entity = handler.getOWLObject().asOWLObjectProperty();
    }

    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if (entity == null) {
            super.handleChild(handler);
        }
        else {
            annotation = handler.getOWLObject();
        }
    }
}

