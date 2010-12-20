package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Feb-2007<br><br>
 */
@SuppressWarnings("deprecation")
public class TypeSelfRestrictionHandler extends BuiltInTypeHandler {

    public TypeSelfRestrictionHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_SELF_RESTRICTION.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        getConsumer().addOWLRestriction(subject, false);
        // Patch to new OWL syntax
        getConsumer().addTriple(subject, OWLRDFVocabulary.OWL_HAS_SELF.getIRI(), getDataFactory().getOWLLiteral(true));
    }
}
