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
public class TypeSWRLDataValuedPropertyAtomHandler extends BuiltInTypeHandler {

    public TypeSWRLDataValuedPropertyAtomHandler(OWLRDFConsumer consumer) {
        super(consumer, SWRLVocabulary.DATAVALUED_PROPERTY_ATOM.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        getConsumer().addSWRLDataPropertyAtom(subject);
    }
}
