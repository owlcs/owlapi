package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TypeFunctionalPropertyHandler extends BuiltInTypeHandler {

    public TypeFunctionalPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_FUNCTIONAL_PROPERTY.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return getConsumer().isObjectPropertyOnly(subject) ||
                getConsumer().isDataPropertyOnly(subject);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectPropertyOnly(subject)) {
            addAxiom(getDataFactory().getOWLFunctionalObjectPropertyAxiom(translateObjectProperty(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        } else if (getConsumer().isDataPropertyOnly(subject)) {
            addAxiom(getDataFactory().getOWLFunctionalDataPropertyAxiom(translateDataProperty(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        } else {
            // Assume object property! :(
            // I suppose that we could check where the predicate is used, but I'm losing the will to live!
            addAxiom(getDataFactory().getOWLFunctionalObjectPropertyAxiom(translateObjectProperty(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }

    }
}
