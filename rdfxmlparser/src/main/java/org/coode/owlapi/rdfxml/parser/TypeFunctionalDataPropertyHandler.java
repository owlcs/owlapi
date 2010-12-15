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
public class TypeFunctionalDataPropertyHandler extends BuiltInTypeHandler {

    public TypeFunctionalDataPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_FUNCTIONAL_DATA_PROPERTY.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addOWLDataProperty(subject);
        addAxiom(getDataFactory().getOWLFunctionalDataPropertyAxiom(translateDataProperty(subject), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
