package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 */
public class TypeNamedIndividualHandler extends BuiltInTypeHandler {

    public TypeNamedIndividualHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getIRI());
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!isAnonymous(subject)) {
            Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
            OWLNamedIndividual individual = getDataFactory().getOWLNamedIndividual(subject);
            addAxiom(getDataFactory().getOWLDeclarationAxiom(individual, annos));
        }
        getConsumer().addOWLNamedIndividual(subject, true);
    }
}
