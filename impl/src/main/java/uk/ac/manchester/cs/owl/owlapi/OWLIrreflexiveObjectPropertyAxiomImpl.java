package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLIrreflexiveObjectPropertyAxiomImpl extends OWLObjectPropertyCharacteristicAxiomImpl implements OWLIrreflexiveObjectPropertyAxiom {

    public OWLIrreflexiveObjectPropertyAxiomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, annotations);
    }

    public OWLIrreflexiveObjectPropertyAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLIrreflexiveObjectPropertyAxiom(getProperty());
    }

    public OWLIrreflexiveObjectPropertyAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLIrreflexiveObjectPropertyAxiom(getProperty(), mergeAnnos(annotations));
    }

    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        return getOWLDataFactory().getOWLSubClassOfAxiom(getOWLDataFactory().getOWLThing(), getOWLDataFactory().getOWLObjectComplementOf(getOWLDataFactory().getOWLObjectHasSelf(getProperty())));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLIrreflexiveObjectPropertyAxiom;
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
        return AxiomType.IRREFLEXIVE_OBJECT_PROPERTY;
    }
}
