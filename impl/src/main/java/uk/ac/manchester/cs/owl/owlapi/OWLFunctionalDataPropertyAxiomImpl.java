package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLFunctionalDataPropertyAxiomImpl extends OWLDataPropertyCharacteristicAxiomImpl implements OWLFunctionalDataPropertyAxiom {

    public OWLFunctionalDataPropertyAxiomImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, annotations);
    }

    public OWLFunctionalDataPropertyAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(getProperty());
    }

    public OWLFunctionalDataPropertyAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(getProperty(), mergeAnnos(annotations));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLFunctionalDataPropertyAxiom;
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
        return AxiomType.FUNCTIONAL_DATA_PROPERTY;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return getProperty().compareTo(((OWLFunctionalDataPropertyAxiom) object).getProperty());
    }


    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        OWLDataFactory df = getOWLDataFactory();
        return df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLDataMaxCardinality(1, getProperty()));
    }
}
