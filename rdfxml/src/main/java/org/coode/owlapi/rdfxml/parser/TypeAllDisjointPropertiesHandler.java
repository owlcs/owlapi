package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Apr-2008<br><br>
 */
public class TypeAllDisjointPropertiesHandler extends BuiltInTypeHandler {


    public TypeAllDisjointPropertiesHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ALL_DISJOINT_PROPERTIES.getIRI());

    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        IRI listNode = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_MEMBERS.getIRI(), true);
        if (getConsumer().isObjectProperty(getConsumer().getFirstResource(listNode, false))) {
            Set<OWLAnnotation> annotations = getConsumer().translateAnnotations(subject);
            List<OWLObjectPropertyExpression> props = getConsumer().translateToObjectPropertyList(listNode);
            getConsumer().addAxiom(getDataFactory().getOWLDisjointObjectPropertiesAxiom(new HashSet<OWLObjectPropertyExpression>(props), annotations));
        } else {
            Set<OWLAnnotation> annotations = getConsumer().translateAnnotations(subject);
            List<OWLDataPropertyExpression> props = getConsumer().translateToDataPropertyList(listNode);
            getConsumer().addAxiom(getDataFactory().getOWLDisjointDataPropertiesAxiom(new HashSet<OWLDataPropertyExpression>(props), annotations));
        }

    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }
}
