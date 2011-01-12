package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 07-Sep-2008<br><br>
 */
public class TypeAsymmetricPropertyHandler extends BuiltInTypeHandler {

    public TypeAsymmetricPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ASYMMETRIC_PROPERTY.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addObjectProperty(subject, false);
        return !isAnonymous(subject);
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(subject)) {
            addAxiom(getDataFactory().getOWLAsymmetricObjectPropertyAxiom(translateObjectProperty(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

}
