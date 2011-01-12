package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class TypeDeprecatedClassHandler extends BuiltInTypeHandler {

    public TypeDeprecatedClassHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DEPRECATED_CLASS.getIRI());
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addClassExpression(subject, false);
        consumeTriple(subject, predicate, object);
        addAxiom(getDataFactory().getDeprecatedOWLAnnotationAssertionAxiom(subject));
    }
}
