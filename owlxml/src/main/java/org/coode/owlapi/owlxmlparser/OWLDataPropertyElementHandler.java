package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public class OWLDataPropertyElementHandler extends AbstractOWLElementHandler<OWLDataPropertyExpression> {

    private OWLDataPropertyExpression prop;

    private IRI iri;

    public OWLDataPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public OWLDataPropertyExpression getOWLObject() {
        return prop;
    }

    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    final public void endElement() throws OWLParserException, UnloadableImportException {
        prop = getOWLDataFactory().getOWLDataProperty(iri);
        getParentHandler().handleChild(this);
    }

}
