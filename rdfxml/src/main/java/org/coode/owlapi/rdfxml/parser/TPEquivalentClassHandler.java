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
        return !isStrict() && !isSubjectOrObjectAnonymous(subject, object) && isSubjectAndObjectMatchingClassExpressionOrMatchingDataRange(subject, object);
    }

	@Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if(isStrict()) {
            if(isClassExpressionStrict(subject) && isClassExpressionStrict(object)) {
                translateEquivalentClasses(subject, predicate, object);
            }
            else if(isDataRangeStrict(subject) && isDataRangeStrict(object)) {
                translateEquivalentDataRanges(subject, predicate, object);
            }
        }
        else {
            if(isClassExpressionLax(subject) && isClassExpressionLax(object)) {
                translateEquivalentClasses(subject, predicate, object);
            }
            else if(isDataRangeLax(subject) || isDataRangeLax(object)) {
                translateEquivalentDataRanges(subject, predicate, object);
            }
        }
    }

    private void translateEquivalentDataRanges(IRI subject, IRI predicate, IRI object) {
        OWLDatatype datatype = getDataFactory().getOWLDatatype(subject);
        OWLDataRange dataRange = getConsumer().translateDataRange(object);
        addAxiom(getDataFactory().getOWLDatatypeDefinitionAxiom(datatype, dataRange, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }

    private void translateEquivalentClasses(IRI subject, IRI predicate, IRI object) {
        Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
        operands.add(translateClassExpression(subject));
        operands.add(translateClassExpression(object));
        addAxiom(getDataFactory().getOWLEquivalentClassesAxiom(operands, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
