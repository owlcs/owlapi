package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-May-2009
 */
public class OWLAnonymousIndividualElementHandler extends AbstractOWLElementHandler<OWLAnonymousIndividual> {

    private OWLAnonymousIndividual ind;

    public OWLAnonymousIndividualElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    public OWLAnonymousIndividual getOWLObject() throws OWLXMLParserException {
        return ind;
    }

    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        if(localName.equals(OWLXMLVocabulary.NODE_ID.getShortName())) {
            ind = getOWLDataFactory().getOWLAnonymousIndividual(value.trim());
        }
        else {
            super.attribute(localName, value);
        }
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }
}
