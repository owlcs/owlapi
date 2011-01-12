package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDeclarationAxiomImpl extends OWLAxiomImpl implements OWLDeclarationAxiom {

    private OWLEntity entity;


    public OWLDeclarationAxiomImpl(OWLDataFactory dataFactory, OWLEntity entity, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.entity = entity;
    }


    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return false;
    }

    public OWLDeclarationAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDeclarationAxiom(getEntity());
    }

    public OWLDeclarationAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDeclarationAxiom(getEntity(), mergeAnnos(annotations));
    }

    public OWLEntity getEntity() {
        return entity;
    }


    public Set<OWLAnnotationAssertionAxiom> getEntityAnnotations(OWLOntology ontology) {
        return ontology.getAnnotationAssertionAxioms(getEntity().getIRI());
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (obj instanceof OWLDeclarationAxiom) {
                return ((OWLDeclarationAxiom) obj).getEntity().equals(entity);
            }
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
        return AxiomType.DECLARATION;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return entity.compareTo(((OWLDeclarationAxiom) object).getEntity());
    }
}
