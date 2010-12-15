package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Oct-2009
 */
public class SWRLRuleElementHandler extends AbstractOWLAxiomElementHandler {

    private Set<SWRLAtom> body = null;

    private Set<SWRLAtom> head = null;

    public SWRLRuleElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getSWRLRule(body, head, getAnnotations());
    }

    @Override
    public void handleChild(SWRLAtomListElementHandler handler) throws OWLXMLParserException {
        if(body == null) {
            body = new HashSet<SWRLAtom>(handler.getOWLObject());
        }
        else if(head == null) {
            head = new HashSet<SWRLAtom>(handler.getOWLObject());
        }
    }
}
