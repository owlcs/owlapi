package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2006<br><br>
 */
public class OWLDatatypeElementHandler extends AbstractOWLDataRangeHandler {

    private IRI iri;

    public OWLDatatypeElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
	protected void endDataRangeElement() throws OWLXMLParserException {
        if (iri == null) {
            throw new OWLXMLParserAttributeNotFoundException(getLineNumber(), getColumnNumber(), "IRI");
        }
        setDataRange(getOWLDataFactory().getOWLDatatype(iri));
    }
}
