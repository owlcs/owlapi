package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.SWRLAtom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Oct-2009
 */
public abstract class SWRLAtomElementHandler extends AbstractOWLElementHandler<SWRLAtom> {

    private SWRLAtom atom;

    public SWRLAtomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    public void setAtom(SWRLAtom atom) {
        this.atom = atom;
    }

    public SWRLAtom getOWLObject() throws OWLXMLParserException {
        return atom;
    }
}
