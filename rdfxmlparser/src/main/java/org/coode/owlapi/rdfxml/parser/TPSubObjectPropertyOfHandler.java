package org.coode.owlapi.rdfxml.parser;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPSubObjectPropertyOfHandler extends TriplePredicateHandler {

    public TPSubObjectPropertyOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_SUB_OBJECT_PROPERTY_OF.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // If the subject is anonymous, it *might* be a property chain - we
        // can't handle these in a streaming manner really
        return !isAnonymous(subject);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (isAnonymous(subject) && getConsumer().hasPredicateObject(subject,
                OWLRDFVocabulary.RDF_TYPE.getIRI(),
                OWLRDFVocabulary.RDF_LIST.getIRI())) {
            // Property chain!
            OptimisedListTranslator<OWLObjectPropertyExpression> translator = new OptimisedListTranslator<OWLObjectPropertyExpression>(
                    getConsumer(),
                    new OWLObjectPropertyExpressionListItemTranslator(getConsumer()));
            List<OWLObjectPropertyExpression> props = translator.translateList(subject);
            addAxiom(getDataFactory().getOWLSubPropertyChainOfAxiom(props,
                    translateObjectProperty(object), getPendingAnnotations()));
        } else {
            addAxiom(getDataFactory().getOWLSubObjectPropertyOfAxiom(translateObjectProperty(subject),
                    translateObjectProperty(object), getPendingAnnotations()));
        }
        consumeTriple(subject, predicate, object);
    }
}
