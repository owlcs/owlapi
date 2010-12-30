package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLSubPropertyAxiomImpl<P extends OWLPropertyExpression> extends OWLPropertyAxiomImpl implements OWLSubPropertyAxiom<P> {

    private P subProperty;

    private P superProperty;


    public OWLSubPropertyAxiomImpl(OWLDataFactory dataFactory, P subProperty, P superProperty, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subProperty = subProperty;
        this.superProperty = superProperty;
    }


    public P getSubProperty() {
        return subProperty;
    }


    public P getSuperProperty() {
        return superProperty;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLSubPropertyAxiom)) {
                return false;
            }
            OWLSubPropertyAxiom<?> other = (OWLSubPropertyAxiom<?>) obj;
            return other.getSubProperty().equals(subProperty) && other.getSuperProperty().equals(superProperty);
        }
        return false;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLSubPropertyAxiom<?> other = (OWLSubPropertyAxiom<?>) object;
        int diff = subProperty.compareTo(other.getSubProperty());
        if (diff != 0) {
            return diff;
        }
        return superProperty.compareTo(other.getSuperProperty());
    }
}
