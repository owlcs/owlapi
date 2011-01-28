package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Apr-2007<br><br>
 */
public class OWLUnionOfElementHandler extends AbstractOWLElementHandler<OWLClassExpression> {

    public OWLUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        // We simply pass on to our parent, which MUST be an OWLDisjointUnionOf
        getParentHandler().handleChild(handler);
    }

    public void endElement() throws OWLParserException, UnloadableImportException {
    }


    public OWLClassExpression getOWLObject() {
        throw new OWLRuntimeException("getOWLObject should not be called on OWLUnionOfElementHandler");
    }


}
