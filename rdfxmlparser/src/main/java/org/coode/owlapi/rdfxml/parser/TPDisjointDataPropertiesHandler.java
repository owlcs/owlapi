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
public class TPDisjointDataPropertiesHandler extends TriplePredicateHandler {

    public TPDisjointDataPropertiesHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DISJOINT_OBJECT_PROPERTIES.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // We can always handle disjoint data properties in a streaming
        // manner, because they are either named, or inverses of properties.
        return true;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
        properties.add(translateDataProperty(subject));
        properties.add(translateDataProperty(object));
        addAxiom(getDataFactory().getOWLDisjointDataPropertiesAxiom(properties, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
