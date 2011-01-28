package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractObjectRestrictionElementHandler<F extends OWLObject> extends AbstractRestrictionElementHandler<OWLObjectPropertyExpression, F> {

    public AbstractObjectRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        setProperty(handler.getOWLObject());
    }
}
