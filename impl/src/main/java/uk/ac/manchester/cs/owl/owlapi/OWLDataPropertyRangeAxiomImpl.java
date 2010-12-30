package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataPropertyRangeAxiomImpl extends OWLPropertyRangeAxiomImpl<OWLDataPropertyExpression, OWLDataRange> implements OWLDataPropertyRangeAxiom {

    public OWLDataPropertyRangeAxiomImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, OWLDataRange range, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, range, annotations);
    }

    public OWLDataPropertyRangeAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDataPropertyRangeAxiom(getProperty(), getRange());
    }

    public OWLAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDataPropertyRangeAxiom(getProperty(), getRange(), mergeAnnos(annotations));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLDataPropertyRangeAxiom;
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
        return AxiomType.DATA_PROPERTY_RANGE;
    }


    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        OWLDataFactory df = getOWLDataFactory();
        OWLClassExpression sup = df.getOWLDataAllValuesFrom(getProperty(), getRange());
        return df.getOWLSubClassOfAxiom(df.getOWLThing(), sup);
    }
}
