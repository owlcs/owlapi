package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class TypeAllDifferentHandler extends BuiltInTypeHandler {

    public TypeAllDifferentHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ALL_DIFFERENT.getIRI());
    }


    
    
    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object)&& getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS, false) != null;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        IRI listNode = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS.getIRI(), true);
        if (listNode != null) {
        	Set<OWLIndividual> inds = getConsumer().translateToIndividualSet(listNode);
            addAxiom(getDataFactory().getOWLDifferentIndividualsAxiom(inds, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }
}
