package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Oct-2009
 */
public class SWRLVariableElementHandler extends AbstractOWLElementHandler<SWRLVariable> {

    protected SWRLVariableElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    private IRI iri;


    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }

    @Override
    public void attribute(String localName, String value) throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    public SWRLVariable getOWLObject() throws OWLXMLParserException {
        if (iri != null) {
            return getOWLDataFactory().getSWRLVariable(iri);
        }
        return null;

    }
}
