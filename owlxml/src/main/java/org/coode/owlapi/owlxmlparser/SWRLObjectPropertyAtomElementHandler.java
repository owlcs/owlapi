package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Oct-2009
 */
public class SWRLObjectPropertyAtomElementHandler extends SWRLAtomElementHandler {

    private OWLObjectPropertyExpression prop;

    private SWRLIArgument arg0 = null;

    private SWRLIArgument arg1 = null;

    public SWRLObjectPropertyAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(AbstractOWLObjectPropertyElementHandler handler) throws OWLXMLParserException {
        prop = handler.getOWLObject();
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler) throws OWLXMLParserException {
        if(arg0 == null) {
            arg0 = handler.getOWLObject();
        }
        else if(arg1 == null) {
            arg1 = handler.getOWLObject();
        }
    }

    @Override
    public void handleChild(OWLIndividualElementHandler handler) throws OWLXMLParserException {
        if(arg0 == null) {
            arg0 = getOWLDataFactory().getSWRLIndividualArgument(handler.getOWLObject());
        }
        else if(arg1 == null) {
            arg1 = getOWLDataFactory().getSWRLIndividualArgument(handler.getOWLObject());
        }
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLObjectPropertyAtom(prop, arg0, arg1));
        getParentHandler().handleChild(this);
    }
}
