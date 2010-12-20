package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class ObjectSomeValuesFromMatcher implements TriplePatternMatcher {

    private IRI onPropertyObject;

    private IRI someValuesFromObject;

    public boolean matches(OWLRDFConsumer consumer, IRI mainNode) {
        onPropertyObject = null;
        someValuesFromObject = null;
        onPropertyObject = consumer.getResourceObject(mainNode, OWLRDFVocabulary.OWL_ON_PROPERTY, false);
        if(consumer.getConfiguration().isStrict()) {
            if(!consumer.isObjectProperty(onPropertyObject)) {
                return false;
            }
        }
        someValuesFromObject = consumer.getResourceObject(mainNode, OWLRDFVocabulary.OWL_SOME_VALUES_FROM, false);
        if(consumer.getConfiguration().isStrict()) {
            if(!consumer.isClassExpression(someValuesFromObject)) {
                return false;
            }
        }
        return onPropertyObject != null && someValuesFromObject != null && consumer.isClassExpression(someValuesFromObject);
    }

    public OWLObjectSomeValuesFrom createObject(OWLRDFConsumer consumer) {
        OWLObjectPropertyExpression property = consumer.translateObjectPropertyExpression(onPropertyObject);
        OWLClassExpression classExpression = consumer.translateClassExpression(someValuesFromObject);
        return consumer.getDataFactory().getOWLObjectSomeValuesFrom(property, classExpression);
    }
}
