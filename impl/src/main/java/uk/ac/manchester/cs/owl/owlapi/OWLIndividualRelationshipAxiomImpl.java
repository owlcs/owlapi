package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLIndividualRelationshipAxiomImpl<P extends OWLPropertyExpression, O extends OWLPropertyAssertionObject> extends OWLLogicalAxiomImpl implements OWLPropertyAssertionAxiom<P, O> {

    private OWLIndividual subject;

    private P property;

    private O object;


    public OWLIndividualRelationshipAxiomImpl(OWLDataFactory dataFactory, OWLIndividual subject, P property, O object, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subject = subject;
        this.property = property;
        this.object = object;
    }


    public OWLIndividual getSubject() {
        return subject;
    }


    public P getProperty() {
        return property;
    }


    public O getObject() {
        return object;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLPropertyAssertionAxiom)) {
                return false;
            }
            OWLPropertyAssertionAxiom other = (OWLPropertyAssertionAxiom) obj;
            return other.getSubject().equals(subject) && other.getProperty().equals(property) && other.getObject().equals(object);
        }
        return false;
    }


    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        OWLPropertyAssertionAxiom other = (OWLPropertyAssertionAxiom) object;
        int diff = subject.compareTo(other.getSubject());
        if (diff != 0) {
            return diff;
        }
        diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return object.compareTo(other.getObject());
    }
}
