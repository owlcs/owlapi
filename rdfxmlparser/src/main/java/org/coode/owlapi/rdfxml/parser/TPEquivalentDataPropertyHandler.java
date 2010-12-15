package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPEquivalentDataPropertyHandler extends TriplePredicateHandler {

    public TPEquivalentDataPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_EQUIVALENT_DATA_PROPERTIES.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
        properties.add(translateDataProperty(subject));
        properties.add(translateDataProperty(object));
        addAxiom(getDataFactory().getOWLEquivalentDataPropertiesAxiom(properties, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}

