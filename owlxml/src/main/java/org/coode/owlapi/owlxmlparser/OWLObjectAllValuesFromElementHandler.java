package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectAllValuesFromElementHandler extends AbstractClassExpressionFillerRestriction {

    public static final String ELEMENT_NAME = OWLXMLVocabulary.OBJECT_SOME_VALUES_FROM.toString();

    public OWLObjectAllValuesFromElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	protected OWLClassExpression createRestriction() {
        return getOWLDataFactory().getOWLObjectAllValuesFrom(getProperty(), getFiller());
    }
}
