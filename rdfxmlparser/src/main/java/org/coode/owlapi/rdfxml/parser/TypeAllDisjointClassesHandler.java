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
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        IRI listNode = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS.getIRI(), true);
        if (listNode != null) {
            Set<OWLClassExpression> desc = getConsumer().translateToClassExpressionSet(listNode);
            Set<OWLAnnotation> annotations = getConsumer().translateAnnotations(subject);
            addAxiom(getDataFactory().getOWLDisjointClassesAxiom(desc, annotations));
        }
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }
}
