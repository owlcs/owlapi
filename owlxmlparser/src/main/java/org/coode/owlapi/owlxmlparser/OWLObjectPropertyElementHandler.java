package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectPropertyElementHandler extends AbstractOWLObjectPropertyElementHandler {

    private IRI iri;

    public OWLObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }


    @Override
	protected void endObjectPropertyElement() {
        setOWLObjectPropertyExpression(getOWLDataFactory().getOWLObjectProperty(iri));
    }
}
