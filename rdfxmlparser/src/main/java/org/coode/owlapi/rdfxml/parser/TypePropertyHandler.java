package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Feb-2007<br><br>
 */
public class TypePropertyHandler extends BuiltInTypeHandler {

    private static final Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    public TypePropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDF_PROPERTY.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // We need to consume this triple
        consumeTriple(subject, predicate, object);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Usage of rdf vocabulary: " + subject + " -> " + predicate + " -> " + object);
        }
    }
}
