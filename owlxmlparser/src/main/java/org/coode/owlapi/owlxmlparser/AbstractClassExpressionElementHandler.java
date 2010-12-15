package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public abstract class AbstractClassExpressionElementHandler extends AbstractOWLElementHandler<OWLClassExpression> {

    private OWLClassExpression desc;

    public AbstractClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
        endClassExpressionElement();
        getParentHandler().handleChild(this);
    }

    protected abstract void endClassExpressionElement() throws OWLXMLParserException;

    protected void setClassExpression(OWLClassExpression desc) {
        this.desc = desc;
    }

    final public OWLClassExpression getOWLObject() {
        return desc;
    }
}
