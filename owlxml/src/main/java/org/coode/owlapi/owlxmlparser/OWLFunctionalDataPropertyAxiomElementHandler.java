package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLFunctionalDataPropertyAxiomElementHandler extends AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLDataPropertyExpression> {

    public OWLFunctionalDataPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }


    @Override
	protected OWLAxiom createPropertyCharacteristicAxiom() throws OWLXMLParserException {
        if (getProperty() == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected data property element");
        }
        return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(getProperty(), getAnnotations());
    }
}
