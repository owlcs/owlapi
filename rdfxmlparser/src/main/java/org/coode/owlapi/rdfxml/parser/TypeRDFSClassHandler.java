package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
public class TypeRDFSClassHandler extends BuiltInTypeHandler {

    public TypeRDFSClassHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_CLASS.getIRI());
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // TODO: Change to rdfs:Class? (See table 5 in the spec)
        getConsumer().addClassExpression(subject, false);
        consumeTriple(subject, predicate, object);
    }
}
