package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Oct-2009
 */
public class SWRLClassAtomElementHandler extends SWRLAtomElementHandler {

    private OWLClassExpression ce;

    private SWRLIArgument arg;

    public SWRLClassAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler) throws OWLXMLParserException {
        arg = handler.getOWLObject();
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        ce = handler.getOWLObject();
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        arg = getOWLDataFactory().getSWRLIndividualArgument(handler.getOWLObject());
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLClassAtom(ce, arg));
        getParentHandler().handleChild(this);
    }
}
