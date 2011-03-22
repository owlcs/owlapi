package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
public class GTPObjectPropertyAssertionHandler extends AbstractResourceTripleHandler {

    public GTPObjectPropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        if(isStrict()) {
            return isObjectPropertyStrict(predicate);
        }
        else {
            // Handle annotation assertions as annotation assertions only!
            return isObjectPropertyLax(predicate) && !isAnnotationPropertyOnly(predicate);
        }
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(predicate)) {
            consumeTriple(subject, predicate, object);
            addAxiom(getDataFactory().getOWLObjectPropertyAssertionAxiom(translateObjectProperty(predicate), translateIndividual(subject), translateIndividual(object), getPendingAnnotations()));
        }
    }
}
