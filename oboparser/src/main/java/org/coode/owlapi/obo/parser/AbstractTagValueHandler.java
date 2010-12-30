package org.coode.owlapi.obo.parser;

import java.util.StringTokenizer;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.XSDVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public abstract class AbstractTagValueHandler implements TagValueHandler {

    //private Logger logger = Logger.getLogger(AbstractTagValueHandler.class.getName());

    private String tag;

    private OBOConsumer consumer;


    public AbstractTagValueHandler(String tag, OBOConsumer consumer) {
        this.tag = tag;
        this.consumer = consumer;
    }


    public String getTag() {
        return tag;
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return consumer.getOWLOntologyManager();
    }


    public OWLOntology getOntology() {
        return consumer.getOntology();
    }


    public void applyChange(OWLOntologyChange change) {
        consumer.getOWLOntologyManager().applyChange(change);
    }


    public OBOConsumer getConsumer() {
        return consumer;
    }


    public OWLDataFactory getDataFactory() {
        return consumer.getOWLOntologyManager().getOWLDataFactory();
    }


    public IRI getIRIFromValue(String s) {
        return consumer.getIRI(s);
    }


    public OWLClass getClassFromId(String s) {
        return getDataFactory().getOWLClass(getIRIFromValue(s));
    }


    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(getIRIFromValue(consumer.getCurrentId()));
    }


    protected OWLClass getOWLClass(String id) {
        return getDataFactory().getOWLClass(getIRIFromValue(id));
    }


    protected OWLObjectProperty getOWLObjectProperty(String id) {
        return getDataFactory().getOWLObjectProperty(getIRIFromValue(id));
    }


    protected OWLClassExpression getOWLClassOrRestriction(String termList) {
        StringTokenizer tok = new StringTokenizer(termList, " ", false);
        String id0 = null;
        String id1 = null;
        id0 = tok.nextToken();
        if (tok.hasMoreTokens()) {
            id1 = tok.nextToken();
        }
        if (id1 == null) {
            return getDataFactory().getOWLClass(getIRIFromValue(id0));
        } else {
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(getIRIFromValue(id0));
            OWLClass filler = getDataFactory().getOWLClass(getIRIFromValue(id1));
            return getDataFactory().getOWLObjectSomeValuesFrom(prop, filler);
        }
    }


    protected OWLLiteral getBooleanConstant(Boolean b) {
        if (b) {
            return getDataFactory().getOWLLiteral("true",
                    getDataFactory().getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
        } else {
            return getDataFactory().getOWLLiteral("false",
                    getDataFactory().getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI()));
        }
    }


    protected void addAnnotation(String id, String uriID, OWLLiteral value) {
        IRI subject = getIRIFromValue(id);
        OWLAnnotationProperty annotationProperty = getDataFactory().getOWLAnnotationProperty(getIRIFromValue(uriID));
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(annotationProperty, subject, value);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
