package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
public class TPOnPropertyHandler extends TriplePredicateHandler {

    protected static int count = 0;

    public TPOnPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI());
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addRestriction(subject);
        count++;
//        OWLRDFConsumer consumer = getConsumer();
//        IRI obj = consumer.getResourceObject(subject, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getIRI(), false);
//        if (obj != null ) {
//            if (!consumer.isAnonymousNode(object) && (consumer.isObjectPropertyOnly(object) || consumer.isClass(obj))) {
//                if(!isAnonymous(obj)) {
//                    consumer.addOWLObjectProperty(object);
//                    consumer.addTriple(subject, predicate, object);
//                    translateClassExpression(subject);
//                    System.out.println("***** CONSUMED! ****");
//                    return true;
//                }
//    //            if (obj != null) {
//    //                 Our object is either a class or a datatype
//    //                if (consumer.isObjectPropertyOnly(obj) || consumer.isClass(object)) {
//    //                     The object MUST be a named class
//    //                    consumer.addOWLClass(object);
//    //                    consumer.addOWLObjectProperty(obj);
//    //                    eagerConsume = true;
//    //                     In order to translate the restriction we need to add the triple.
//    //                    consumer.addTriple(subject, predicate, object);
//    //                    translateClassExpression(subject);
//    //                    consumer.consumeTriple(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI(), obj);
//    //                    eagerConsume = false;
//    //                    return true;
//    //                }
//                }
//        }
        return false;   
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
