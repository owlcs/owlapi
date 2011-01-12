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
public class TypeIrreflexivePropertyHandler extends BuiltInTypeHandler {

    public TypeIrreflexivePropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_IRREFLEXIVE_PROPERTY.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addObjectProperty(subject, false);
        return !isAnonymous(subject);
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(subject)) {
            addAxiom(getDataFactory().getOWLIrreflexiveObjectPropertyAxiom(translateObjectProperty(subject), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }
}
