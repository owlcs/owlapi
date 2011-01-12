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
public class TypeTransitivePropertyHandler extends BuiltInTypeHandler {

    public TypeTransitivePropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_TRANSITIVE_PROPERTY.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().handle(subject, predicate, OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI());
        return !isAnonymous(subject);
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        addAxiom(getDataFactory().getOWLTransitiveObjectPropertyAxiom(translateObjectProperty(subject), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
