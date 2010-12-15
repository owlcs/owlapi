package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectHasValueElementHandler extends AbstractObjectRestrictionElementHandler<OWLIndividual> {

    public OWLObjectHasValueElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(OWLIndividualElementHandler handler) {
        setFiller(handler.getOWLObject());
    }


    @Override
	protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLObjectHasValue(getProperty(), getFiller());
    }

}
