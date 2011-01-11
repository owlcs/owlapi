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
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
    	inferTypes(subject, object);
        return super.canHandle(subject, predicate, object) && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(subject, object);
    }



    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        inferTypes(subject, object);
        return !isSubjectOrObjectAnonymous(subject, object) && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(subject, object);
    }

	public void inferTypes(IRI subject, IRI object) {
		if(getConsumer().isClassExpression(object)) {
            getConsumer().addClassExpression(subject, false);
        }
        else if(getConsumer().isDataRange(object)) {
            getConsumer().addDataRange(subject, false);
        }
        else if(getConsumer().isClassExpression(subject)) {
            getConsumer().addClassExpression(object, false);
        }
        else if(getConsumer().isDataRange(subject)) {
            getConsumer().addDataRange(object, false);
        }
	}


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if(getConsumer().isDataRange(subject) && getConsumer().isDataRange(object)) {
            OWLDatatype datatype = getDataFactory().getOWLDatatype(subject);
            OWLDataRange dataRange = getConsumer().translateDataRange(object);
            addAxiom(getDataFactory().getOWLDatatypeDefinitionAxiom(datatype, dataRange, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if(getConsumer().isClassExpression(subject) && getConsumer().isClassExpression(object)) {
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(translateClassExpression(subject));
            operands.add(translateClassExpression(object));
            addAxiom(getDataFactory().getOWLEquivalentClassesAxiom(operands, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        // TODO: LOG ERROR


    }
}
