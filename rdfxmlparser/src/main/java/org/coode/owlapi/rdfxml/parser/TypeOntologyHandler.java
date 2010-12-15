package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class TypeOntologyHandler extends BuiltInTypeHandler {

    public TypeOntologyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ONTOLOGY.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        if(!isAnonymous(subject) && getConsumer().getOntologies().isEmpty()) {
            // Set IRI?
            OWLOntologyID id = new OWLOntologyID(subject);
            getConsumer().applyChange(new SetOntologyID(getConsumer().getOntology(), id));
        }
        getConsumer().addOntology(subject);
    }
}
