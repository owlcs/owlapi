package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLPropertyCharacteristicAxiomElementHandler<P extends OWLObject> extends AbstractOWLAxiomElementHandler {

    private P property;

    public AbstractOWLPropertyCharacteristicAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void setProperty(P property) {
        this.property = property;
    }


    protected P getProperty() {
        return property;
    }


    @Override
	final protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if(property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "property element");
        }
        return createPropertyCharacteristicAxiom();
    }

    protected abstract OWLAxiom createPropertyCharacteristicAxiom() throws OWLXMLParserException;
}
