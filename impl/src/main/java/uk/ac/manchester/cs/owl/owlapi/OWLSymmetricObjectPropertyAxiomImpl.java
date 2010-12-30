package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLSymmetricObjectPropertyAxiomImpl extends OWLObjectPropertyCharacteristicAxiomImpl implements OWLSymmetricObjectPropertyAxiom {

    public OWLSymmetricObjectPropertyAxiomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, annotations);
    }


    public Set<OWLSubObjectPropertyOfAxiom> asSubPropertyAxioms() {
        Set<OWLSubObjectPropertyOfAxiom> result = new HashSet<OWLSubObjectPropertyOfAxiom>(5);
        result.add(getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getProperty(), getProperty().getInverseProperty().getSimplified()));
        result.add(getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getProperty().getInverseProperty().getSimplified(), getProperty()));
        return result;
    }

    public OWLSymmetricObjectPropertyAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(getProperty(), mergeAnnos(annotations));
    }

    public OWLSymmetricObjectPropertyAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(getProperty());
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLSymmetricObjectPropertyAxiom;
        }
        return false;
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.SYMMETRIC_OBJECT_PROPERTY;
    }
}
