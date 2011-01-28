package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Apr-2009
 */
public class OWLAnnotationPropertyElementHandler extends AbstractOWLElementHandler<OWLAnnotationProperty> {

    private OWLAnnotationProperty prop;

    private IRI iri;

    public OWLAnnotationPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public OWLAnnotationProperty getOWLObject() {
        return prop;
    }

    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    final public void endElement() throws OWLParserException, UnloadableImportException {
        prop = getOWLDataFactory().getOWLAnnotationProperty(iri);
        getParentHandler().handleChild(this);
    }
}
