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
public class TypeFunctionalObjectPropertyHandler extends BuiltInTypeHandler {

    public TypeFunctionalObjectPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return !isAnonymous(subject);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addOWLObjectProperty(subject);
        addAxiom(getDataFactory().getOWLFunctionalObjectPropertyAxiom(translateObjectProperty(subject), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
