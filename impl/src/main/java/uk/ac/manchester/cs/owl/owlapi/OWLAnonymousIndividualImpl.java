package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotationSubjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationSubjectVisitorEx;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLIndividualVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 15-Jan-2009
 */
public class OWLAnonymousIndividualImpl extends OWLIndividualImpl implements OWLAnonymousIndividual {

    private NodeID nodeId;

    public OWLAnonymousIndividualImpl(OWLDataFactory dataFactory, NodeID nodeID) {
        super(dataFactory);
        this.nodeId = nodeID;
    }

    public NodeID getID() {
        return nodeId;
    }

    /**
     * Returns a string representation that can be used as the ID of this individual.  This is the toString
     * representation of the node ID of this individual
     * @return A string representing the toString of the node ID of this entity.
     */
    public String toStringID() {
        return nodeId.getID();
    }

    public boolean isNamed() {
        return false;
    }

    public boolean isAnonymous() {
        return true;
    }

    public OWLAnonymousIndividual asOWLAnonymousIndividual() {
        return this;
    }

    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not a named individual! This method should only be called on named individuals");
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLAnonymousIndividual other = (OWLAnonymousIndividual) object;
        return nodeId.compareTo(other.getID());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public void accept(OWLIndividualVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLIndividualVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationValueVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationSubjectVisitor visitor) {
        visitor.visit(this);
    }

    public <E> E accept(OWLAnnotationSubjectVisitorEx<E> visitor) {
        return visitor.visit(this);
    }

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLAnonymousIndividual)) {
            return false;
        }
        return nodeId.equals(((OWLAnonymousIndividual) obj).getID());
    }
    @Override
    public int hashCode() {
    	return nodeId.hashCode();
    }
}
