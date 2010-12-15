package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public abstract class AbstractOWLObjectPropertyElementHandler extends AbstractOWLElementHandler<OWLObjectPropertyExpression> {

    private OWLObjectPropertyExpression property;

    public AbstractOWLObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
        endObjectPropertyElement();
        getParentHandler().handleChild(this);
    }

    protected void setOWLObjectPropertyExpression(OWLObjectPropertyExpression prop) {
        this.property = prop;
    }


    public OWLObjectPropertyExpression getOWLObject() {
        return property;
    }


    protected abstract void endObjectPropertyElement() throws OWLXMLParserException;
}
