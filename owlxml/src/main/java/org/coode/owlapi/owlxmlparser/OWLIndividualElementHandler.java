package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public class OWLIndividualElementHandler extends AbstractOWLElementHandler<OWLNamedIndividual> {

    private OWLNamedIndividual individual;

    private IRI name;

    public OWLIndividualElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public OWLNamedIndividual getOWLObject() {
        return individual;
    }


    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        name = getIRIFromAttribute(localName, value);
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
//        URI uri = getNameAttribute();
        individual = getOWLDataFactory().getOWLNamedIndividual(name);
        getParentHandler().handleChild(this);
    }
}
