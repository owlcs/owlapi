package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 24-Mar-2009
 */
public class OWLDatatypeDefinitionAxiomImpl extends OWLAxiomImpl implements OWLDatatypeDefinitionAxiom {

    private OWLDatatype datatype;

    private OWLDataRange dataRange;


    public OWLDatatypeDefinitionAxiomImpl(OWLDataFactory dataFactory, OWLDatatype datatype, OWLDataRange dataRange, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.datatype = datatype;
        this.dataRange = dataRange;
    }

    public OWLAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(getDatatype(), getDataRange());
    }

    public OWLDatatypeDefinitionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(getDatatype(), getDataRange(), mergeAnnos(annotations));
    }

    public OWLDatatype getDatatype() {
        return datatype;
    }


    public OWLDataRange getDataRange() {
        return dataRange;
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isLogicalAxiom() {
        return true;
    }

    public boolean isAnnotationAxiom() {
        return false;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.DATATYPE_DEFINITION;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) object;
        int diff = getDatatype().compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return getDataRange().compareTo(other.getDataRange());
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLDatatypeDefinitionAxiom)) {
            return false;
        }
        OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) obj;
        return datatype.equals(other.getDatatype()) && dataRange.equals(other.getDataRange());
    }
}
