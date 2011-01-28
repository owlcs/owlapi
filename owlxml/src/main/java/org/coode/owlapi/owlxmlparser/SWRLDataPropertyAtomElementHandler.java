package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Oct-2009
 */
public class SWRLDataPropertyAtomElementHandler extends SWRLAtomElementHandler {


    private OWLDataPropertyExpression prop;

    private SWRLIArgument arg0 = null;

    private SWRLDArgument arg1 = null;


    public SWRLDataPropertyAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(OWLDataPropertyElementHandler handler) throws OWLXMLParserException {
        prop = handler.getOWLObject();
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler) throws OWLXMLParserException {
        if (arg0 == null) {
            arg0 = handler.getOWLObject();
        }
        else if(arg1 == null) {
            arg1 = handler.getOWLObject();
        }
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException {
        arg1 = getOWLDataFactory().getSWRLLiteralArgument(handler.getOWLObject());
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLDataPropertyAtom(prop, arg0, arg1));
        getParentHandler().handleChild(this);
    }
}
