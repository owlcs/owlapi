package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 * <br>
 * owl:inverseOf is used in both property expressions AND axioms.
 */
public class TPInverseOfHandler extends TriplePredicateHandler {

    private boolean axiomParsingMode = false;

    public TPInverseOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_INVERSE_OF.getIRI());
    }

    public boolean isAxiomParsingMode() {
        return axiomParsingMode;
    }

    public void setAxiomParsingMode(boolean axiomParsingMode) {
        this.axiomParsingMode = axiomParsingMode;
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addObjectProperty(subject, false);
        getConsumer().addObjectProperty(object, false);
        return false;
    }


    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object);
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // Only do axiom translation
        if (axiomParsingMode && getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object)) {
            addAxiom(getDataFactory().getOWLInverseObjectPropertiesAxiom(
                    translateObjectProperty(subject),
                    translateObjectProperty(object), getPendingAnnotations()
            ));
            consumeTriple(subject, predicate, object);
        }
    }
}
