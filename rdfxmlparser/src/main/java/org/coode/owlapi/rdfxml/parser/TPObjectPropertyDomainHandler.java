package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPObjectPropertyDomainHandler extends TriplePredicateHandler {

    public TPObjectPropertyDomainHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_OBJECT_PROPERTY_DOMAIN.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // Can only handle when domain is named if we are streaming
        // prop rdfs:domain desc
        return !isAnonymous(object);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        OWLObjectPropertyExpression prop = translateObjectProperty(subject);
        OWLClassExpression domain = translateClassExpression(subject);
        addAxiom(getDataFactory().getOWLObjectPropertyDomainAxiom(prop, domain, getPendingAnnotations()));
    }
}
