package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-May-2009
 */
public class IRIElementHandler extends AbstractIRIElementHandler {

    public IRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    private IRI iri;

    @Override
	public boolean isTextContentPossible() {
        return true;
    }


    public IRI getOWLObject() throws OWLXMLParserException {
        return iri;
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        String iriText = getText().trim();
        iri = getIRI(iriText);
        getParentHandler().handleChild(this);
    }
}
