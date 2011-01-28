package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectMinCardinalityElementHandler extends AbstractOWLObjectCardinalityElementHandler {

    public OWLObjectMinCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	protected OWLClassExpression createCardinalityRestriction() {
        return getOWLDataFactory().getOWLObjectMinCardinality(getCardinality(), getProperty(), getFiller()
        );
    }
}
