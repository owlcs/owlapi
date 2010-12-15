package org.coode.owlapi.owlxmlparser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Oct-2009
 */
public class SWRLAtomListElementHandler extends AbstractOWLElementHandler<List<SWRLAtom>> {

    private List<SWRLAtom> atoms = new ArrayList<SWRLAtom>();

    protected SWRLAtomListElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
    public void handleChild(SWRLAtomElementHandler handler) throws OWLXMLParserException {
        atoms.add(handler.getOWLObject());
    }

    public List<SWRLAtom> getOWLObject() throws OWLXMLParserException {
        return atoms;
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }
}
