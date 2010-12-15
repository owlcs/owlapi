package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractDataRestrictionElementHandler<F extends OWLObject> extends AbstractRestrictionElementHandler<OWLDataPropertyExpression, F> {

    public AbstractDataRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLDataPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
