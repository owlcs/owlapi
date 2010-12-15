package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLAssertionAxiomElementHandler<P extends OWLPropertyExpression, O extends OWLObject> extends AbstractOWLAxiomElementHandler {


    private OWLIndividual subject;

    private P property;

    private O object;


    public AbstractOWLAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public OWLIndividual getSubject() {
        return subject;
    }


    public P getProperty() {
        return property;
    }


    public O getObject() {
        return object;
    }


    public void setSubject(OWLIndividual subject) {
        this.subject = subject;
    }


    public void setProperty(P property) {
        this.property = property;
    }


    public void setObject(O object) {
        this.object = object;
    }
}
