package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Jul-2010
 */
public class SKOSDataTripleHandler extends AbstractLiteralTripleHandler {

    private IRI iri;

    public SKOSDataTripleHandler(OWLRDFConsumer consumer, SKOSVocabulary v) {
        super(consumer);
        iri = v.getIRI();
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
        OWLIndividual subj = getDataFactory().getOWLNamedIndividual(subject);
        OWLDataProperty prop = getDataFactory().getOWLDataProperty(predicate);
        addAxiom(getDataFactory().getOWLDataPropertyAssertionAxiom(prop, subj, object));
    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        return predicate.equals(iri);
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return true;
    }
}
