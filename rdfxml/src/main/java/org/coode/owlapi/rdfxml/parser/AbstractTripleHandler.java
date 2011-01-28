package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class AbstractTripleHandler {

    private OWLRDFConsumer consumer;


    public AbstractTripleHandler(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLRDFConsumer getConsumer() {
        return consumer;
    }



    protected Set<OWLAnnotation> getPendingAnnotations() {
        return consumer.getPendingAnnotations();
    }

    protected void consumeTriple(IRI subject, IRI predicate, IRI object) {
        consumer.consumeTriple(subject, predicate, object);
    }

    protected void consumeTriple(IRI subject, IRI predicate, OWLLiteral object) {
        consumer.consumeTriple(subject, predicate, object);
    }


    protected boolean isAnonymous(IRI IRI) {
        return consumer.isAnonymousNode(IRI);
    }

    protected boolean isStrict() {
        return consumer.getConfiguration().isStrict();
    }

    protected boolean isObjectPropertyOnly(IRI iri) {
        return consumer.isObjectPropertyOnly(iri);
    }

    protected boolean isDataPropertyOnly(IRI iri) {
        return consumer.isDataPropertyOnly(iri);
    }


    protected boolean isAnnotationPropertyOnly(IRI iri) {
        return consumer.isAnnotationPropertyOnly(iri);
    }

    protected void addAxiom(OWLAxiom axiom) {
        consumer.addAxiom(axiom);
    }


    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }


    protected OWLClassExpression translateClassExpression(IRI IRI) {
        return consumer.translateClassExpression(IRI);
    }


    protected OWLObjectPropertyExpression translateObjectProperty(IRI IRI) {
        return consumer.translateObjectPropertyExpression(IRI);
    }


    protected OWLDataPropertyExpression translateDataProperty(IRI IRI) {
        return consumer.translateDataPropertyExpression(IRI);
    }


    protected OWLDataRange translateDataRange(IRI IRI) {
        return consumer.translateDataRange(IRI);
    }


    protected OWLIndividual translateIndividual(IRI IRI) {
        return consumer.translateIndividual(IRI);
    }
}
