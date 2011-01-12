package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
public class GTPDataPropertyAssertionHandler extends AbstractLiteralTripleHandler {

    public GTPDataPropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        return getConsumer().isDataProperty(predicate);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
        addAxiom(getDataFactory().getOWLDataPropertyAssertionAxiom(translateDataProperty(predicate), translateIndividual(subject), object, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
