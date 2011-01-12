package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2007<br><br>
 */
public class TPAllValuesFromHandler extends TriplePredicateHandler {

    public TPAllValuesFromHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ALL_VALUES_FROM.getIRI());
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        OWLRDFConsumer consumer = getConsumer();
        consumer.addOWLRestriction(subject, false);
        IRI propIRI = consumer.getResourceObject(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI(), false);
        if (propIRI != null && (!consumer.isAnonymousNode(object) || consumer.getClassExpressionIfTranslated(object) != null)) {
            // The filler is either a datatype or named class
            if (consumer.isObjectPropertyOnly(propIRI)) {
                consumer.addClassExpression(object, false);
                consumer.addTriple(subject, predicate, object);
                consumer.translateClassExpression(subject);
                return true;
            }
            else if (consumer.isDataPropertyOnly(propIRI)) {

            }

        }
        return false;
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
