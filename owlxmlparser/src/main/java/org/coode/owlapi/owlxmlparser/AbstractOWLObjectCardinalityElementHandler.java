package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLObjectCardinalityElementHandler extends AbstractClassExpressionFillerRestriction
{

    private int cardinality;

    public AbstractOWLObjectCardinalityElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        super.startElement(name);
        setFiller(getOWLDataFactory().getOWLThing());
    }


    @Override
	public void attribute(String localName, String value) throws OWLParserException {
        if(localName.equals("cardinality")) {
            cardinality = Integer.parseInt(value);
        }
    }


    @Override
	protected OWLClassExpression createRestriction() {
        return createCardinalityRestriction();
    }

    protected abstract OWLClassExpression createCardinalityRestriction();

    protected int getCardinality() {
        return cardinality;
    }
}
