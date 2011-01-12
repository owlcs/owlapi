package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Nov-2006<br><br>
 */
public class OWLSubPropertyChainAxiomImpl extends OWLPropertyAxiomImpl implements OWLSubPropertyChainOfAxiom {

    private List<OWLObjectPropertyExpression> propertyChain;

    private OWLObjectPropertyExpression superProperty;


    public OWLSubPropertyChainAxiomImpl(OWLDataFactory dataFactory, List<? extends OWLObjectPropertyExpression> propertyChain, OWLObjectPropertyExpression superProperty, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.propertyChain = new ArrayList<OWLObjectPropertyExpression>(propertyChain);
        this.superProperty = superProperty;
    }

    public OWLSubPropertyChainOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubPropertyChainOfAxiom(getPropertyChain(), getSuperProperty(), mergeAnnos(annotations));
    }

    public OWLSubPropertyChainOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubPropertyChainOfAxiom(getPropertyChain(), getSuperProperty());
    }

    public List<OWLObjectPropertyExpression> getPropertyChain() {
        return new ArrayList<OWLObjectPropertyExpression>(propertyChain);
    }


    public OWLObjectPropertyExpression getSuperProperty() {
        return superProperty;
    }


    public boolean isEncodingOfTransitiveProperty() {
        if (propertyChain.size() == 2) {
            return superProperty.equals(propertyChain.get(0)) && superProperty.equals(propertyChain.get(1));
        }
        else {
            return false;
        }
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLSubPropertyChainOfAxiom)) {
            return false;
        }
        OWLSubPropertyChainOfAxiom other = (OWLSubPropertyChainOfAxiom) obj;
        return other.getPropertyChain().equals(getPropertyChain()) && other.getSuperProperty().equals(superProperty);
    }


    public AxiomType<?> getAxiomType() {
        return AxiomType.SUB_PROPERTY_CHAIN_OF;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLSubPropertyChainOfAxiom other = (OWLSubPropertyChainOfAxiom) object;

        for (int i = 0; i < propertyChain.size() && i < other.getPropertyChain().size(); i++) {
            int diff = propertyChain.get(i).compareTo(other.getPropertyChain().get(i));
            if (diff != 0) {
                return diff;
            }
            i++;
        }
        int diff = propertyChain.size() - other.getPropertyChain().size();
        if (diff != 0) {
            return diff;
        }
        return superProperty.compareTo(other.getSuperProperty());
    }
}
