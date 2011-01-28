package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractRestrictionElementHandler<P extends OWLPropertyExpression, F extends OWLObject> extends AbstractClassExpressionElementHandler {

    private P property;

    private F filler;

    protected AbstractRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    protected void setProperty(P prop) {
        property = prop;
    }

    protected P getProperty() {
        return property;
    }


    protected F getFiller() {
        return filler;
    }


    protected void setFiller(F filler) {
        this.filler = filler;
    }


    @Override
	final protected void endClassExpressionElement() throws OWLXMLParserException {
        setClassExpression(createRestriction());
    }


    protected abstract OWLClassExpression createRestriction();
}
