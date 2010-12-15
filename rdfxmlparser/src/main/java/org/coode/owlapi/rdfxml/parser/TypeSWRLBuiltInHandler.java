package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Feb-2007<br><br>
 */
public class TypeSWRLBuiltInHandler extends BuiltInTypeHandler {

    public TypeSWRLBuiltInHandler(OWLRDFConsumer consumer) {
        super(consumer, SWRLVocabulary.BUILT_IN_CLASS.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // Just consume - I don't care about this
        consumeTriple(subject, predicate, object);
    }
}
