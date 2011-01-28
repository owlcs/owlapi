package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 05-Nov-2008<br><br>
 */
public class SKOSClassTripleHandler extends BuiltInTypeHandler {


    public SKOSClassTripleHandler(OWLRDFConsumer consumer, SKOSVocabulary v) {
        super(consumer, v.getIRI());
    }


    @Override  @SuppressWarnings("unused")
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        OWLIndividual ind = getDataFactory().getOWLNamedIndividual(subject);
        OWLClass skosConcept = getDataFactory().getOWLClass(object);
        addAxiom(getDataFactory().getOWLClassAssertionAxiom(skosConcept, ind));
    }
}
