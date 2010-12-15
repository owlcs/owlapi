package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPEquivalentClassHandler extends TriplePredicateHandler {

    public TPEquivalentClassHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_EQUIVALENT_CLASS.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // Can handle when streaming if the subject or object are named
        boolean named = (getConsumer().isClass(subject)) && !isSubjectOrObjectAnonymous(subject, object);
        return named || getConsumer().getClassExpressionIfTranslated(subject) != null && getConsumer().getClassExpressionIfTranslated(object) != null;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // Can handle because the IRIs can easily be translated to classes
        if(getConsumer().isDataRange(object) || getConsumer().isDataRange(subject)) {
            OWLDatatype datatype = getDataFactory().getOWLDatatype(subject);
            OWLDataRange dataRange = getConsumer().translateDataRange(object);
            addAxiom(getDataFactory().getOWLDatatypeDefinitionAxiom(datatype, dataRange, getPendingAnnotations()));
        }
        else {
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(translateClassExpression(subject));
            operands.add(translateClassExpression(object));
            addAxiom(getDataFactory().getOWLEquivalentClassesAxiom(operands, getPendingAnnotations()));
        }
        consumeTriple(subject, predicate, object);

    }
}
