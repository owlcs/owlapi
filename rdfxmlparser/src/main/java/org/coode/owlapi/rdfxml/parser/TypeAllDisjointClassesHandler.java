package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Apr-2008<br><br>
 */
public class TypeAllDisjointClassesHandler extends BuiltInTypeHandler {


    public TypeAllDisjointClassesHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES.getIRI());
    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS, false) != null;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        IRI listNode = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS.getIRI(), true);
        if (listNode != null) {
            Set<OWLClassExpression> desc = getConsumer().translateToClassExpressionSet(listNode);
            Set<OWLAnnotation> annotations = getConsumer().translateAnnotations(subject);
            addAxiom(getDataFactory().getOWLDisjointClassesAxiom(desc, annotations));
            consumeTriple(subject, predicate, object);
        }
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }
}
