package org.coode.owlapi.owlxmlparser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Oct-2009
 */
public class SWRLBuiltInAtomElementHandler extends SWRLAtomElementHandler {

    private IRI iri;

    private List<SWRLDArgument> args = new ArrayList<SWRLDArgument>();

    public SWRLBuiltInAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void attribute(String localName, String value) throws OWLParserException {
        iri = getIRIFromAttribute(localName, value);
    }

    @Override
    public void handleChild(SWRLVariableElementHandler handler) throws OWLXMLParserException {
        args.add(handler.getOWLObject());
    }

    @Override
    public void handleChild(OWLLiteralElementHandler handler) throws OWLXMLParserException {
        args.add(getOWLDataFactory().getSWRLLiteralArgument(handler.getOWLObject()));
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        setAtom(getOWLDataFactory().getSWRLBuiltInAtom(iri, args));
        getParentHandler().handleChild(this);
    }
}
