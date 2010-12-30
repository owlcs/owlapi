package uk.ac.manchester.cs.owl.owlapi;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 29-Nov-2006<br><br>
 */
public class OWLInverseObjectPropertiesAxiomImpl extends OWLNaryPropertyAxiomImpl<OWLObjectPropertyExpression> implements OWLInverseObjectPropertiesAxiom {

    private OWLObjectPropertyExpression first;

    private OWLObjectPropertyExpression second;

    public OWLInverseObjectPropertiesAxiomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression first, OWLObjectPropertyExpression second, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, new TreeSet<OWLObjectPropertyExpression>(Arrays.asList(first, second)), annotations);
        this.first = first;
        this.second = second;
    }

    public OWLInverseObjectPropertiesAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(getFirstProperty(), getSecondProperty());
    }

    public OWLInverseObjectPropertiesAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(getFirstProperty(), getSecondProperty(), mergeAnnos(annotations));
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


    public OWLObjectPropertyExpression getFirstProperty() {
        return first;
    }


    public OWLObjectPropertyExpression getSecondProperty() {
        return second;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLInverseObjectPropertiesAxiom;
        }
        return false;
    }


    public AxiomType<?> getAxiomType() {
        return AxiomType.INVERSE_OBJECT_PROPERTIES;
    }


    public Set<OWLSubObjectPropertyOfAxiom> asSubObjectPropertyOfAxioms() {
        Set<OWLSubObjectPropertyOfAxiom> axs = new HashSet<OWLSubObjectPropertyOfAxiom>();
        OWLDataFactory df = getOWLDataFactory();
        axs.add(df.getOWLSubObjectPropertyOfAxiom(first, second.getInverseProperty().getSimplified()));
        axs.add(df.getOWLSubObjectPropertyOfAxiom(second, first.getInverseProperty().getSimplified()));
        return axs;
    }
}
