package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectPropertyRangeAxiomElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLClassExpression range;

    private OWLObjectPropertyExpression property;

    public OWLObjectPropertyRangeAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) {
        range = handler.getOWLObject();
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        property = handler.getOWLObject();
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        if (property == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), OWLXMLVocabulary.OBJECT_PROPERTY.getShortName());
        }
        if (range == null) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "OWL class expression element");
        }
        return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(property, range, getAnnotations());
    }
}
