package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPDisjointObjectPropertiesHandler extends TriplePredicateHandler {

    public TPDisjointObjectPropertiesHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DISJOINT_OBJECT_PROPERTIES.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
        properties.add(translateObjectProperty(subject));
        properties.add(translateObjectProperty(object));
        addAxiom(getDataFactory().getOWLDisjointObjectPropertiesAxiom(properties, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
