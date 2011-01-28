package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class OWLAnnotationPropertyDomainElementHandler extends AbstractOWLAxiomElementHandler {

    private IRI domain;

    private OWLAnnotationProperty property;

    public OWLAnnotationPropertyDomainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractIRIElementHandler handler) throws OWLXMLParserException {
        domain = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLAnnotationPropertyElementHandler handler) throws OWLXMLParserException {
        property = handler.getOWLObject();
    }

    @Override
    protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if(property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected annotation property element");
        }
        if(domain == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected iri element");
        }
        return getOWLDataFactory().getOWLAnnotationPropertyDomainAxiom(property, domain, getAnnotations());
    }
}
