package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLInverseObjectPropertyElementHandler extends AbstractOWLObjectPropertyElementHandler {

    private OWLObjectPropertyExpression inverse;

    public OWLInverseObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        inverse = handler.getOWLObject();
    }


    @Override
	protected void endObjectPropertyElement() throws OWLXMLParserException {
        if(inverse == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), OWLXMLVocabulary.OBJECT_INVERSE_OF.getShortName());
        }
        setOWLObjectPropertyExpression(getOWLDataFactory().getOWLObjectInverseOf(inverse));
    }
}
