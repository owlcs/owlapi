package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractDataRangeFillerRestrictionElementHandler extends AbstractDataRestrictionElementHandler<OWLDataRange> {

    public AbstractDataRangeFillerRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) {
        setFiller(handler.getOWLObject());
    }
}
