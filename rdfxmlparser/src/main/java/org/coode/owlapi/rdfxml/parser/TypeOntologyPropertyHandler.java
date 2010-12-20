package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17/12/2010
 */
public class TypeOntologyPropertyHandler extends BuiltInTypeHandler {

    public TypeOntologyPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ONTOLOGY_PROPERTY.getIRI());
    }


    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        // Add a type triple for an annotation property (Table 6 in Mapping to RDF Graph Spec)
        getConsumer().handle(subject, predicate, OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI());
    }
}
