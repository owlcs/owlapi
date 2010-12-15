package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataRange;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Apr-2009
 */
public class OWLDataIntersectionOfElementHandler extends AbstractOWLDataRangeHandler {

    private Set<OWLDataRange> dataRanges = new HashSet<OWLDataRange>();

    public OWLDataIntersectionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {
        dataRanges.add(handler.getOWLObject());
    }


    @Override
	protected void endDataRangeElement() throws OWLXMLParserException {
        setDataRange(getOWLDataFactory().getOWLDataIntersectionOf(dataRanges));
    }
}
