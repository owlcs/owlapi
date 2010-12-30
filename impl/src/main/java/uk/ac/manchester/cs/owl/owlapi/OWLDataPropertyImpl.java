package uk.ac.manchester.cs.owl.owlapi;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNaryPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 26-Oct-2006<br><br>
 */
public class OWLDataPropertyImpl extends OWLPropertyExpressionImpl<OWLDataRange, OWLDataPropertyExpression> implements OWLDataProperty {

    private IRI iri;


    private boolean builtin;


    public OWLDataPropertyImpl(OWLDataFactory dataFactory, IRI iri) {
        super(dataFactory);
        this.iri = iri;
        this.builtin = iri.equals(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI()) || iri.equals(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    }

    @Override
	public boolean isTopEntity() {
        return isOWLTopDataProperty();
    }

    @Override
	public boolean isBottomEntity() {
        return isOWLBottomDataProperty();
    }

    /**
     * Gets the entity type for this entity
     * @return The entity type
     */
    public EntityType<?> getEntityType() {
        return EntityType.DATA_PROPERTY;
    }

    /**
     * Gets an entity that has the same IRI as this entity but is of the specified type.
     * @param entityType The type of the entity to obtain.  This entity is not affected in any way.
     * @return An entity that has the same IRI as this entity and is of the specified type
     */
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType) {
        return getOWLDataFactory().getOWLEntity(entityType, iri);
    }

    /**
     * Tests to see if this entity is of the specified type
     * @param entityType The entity type
     * @return <code>true</code> if this entity is of the specified type, otherwise <code>false</code>.
     */
    public boolean isType(EntityType<?> entityType) {
        return getEntityType().equals(entityType);
    }

    /**
     * Returns a string representation that can be used as the ID of this entity.  This is the toString
     * representation of the IRI
     * @return A string representing the toString of the IRI of this entity.
     */
    public String toStringID() {
        return iri.toString();
    }

    public boolean isDataPropertyExpression() {
        return true;
    }

    public boolean isObjectPropertyExpression() {
        return false;
    }

    public IRI getIRI() {
        return iri;
    }

    public URI getURI() {
        return iri.toURI();
    }


    public boolean isBuiltIn() {
        return builtin;
    }


    public boolean isFunctional(OWLOntology ontology) {
        return ontology.getFunctionalDataPropertyAxioms(this).size() > 0;
    }


    public boolean isFunctional(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isFunctional(ont)) {
                return true;
            }
        }
        return false;
    }


    @Override
	protected Set<? extends OWLNaryPropertyAxiom<OWLDataPropertyExpression>> getDisjointPropertiesAxioms(OWLOntology ontology) {
        return ontology.getDisjointDataPropertiesAxioms(this);
    }


    @Override
	protected Set<? extends OWLPropertyDomainAxiom> getDomainAxioms(OWLOntology ontology) {
        return ontology.getDataPropertyDomainAxioms(this);
    }


    @Override
	protected Set<? extends OWLPropertyRangeAxiom<OWLDataPropertyExpression, OWLDataRange>> getRangeAxioms(OWLOntology ontology) {
        return ontology.getDataPropertyRangeAxioms(this);
    }


    @Override
	protected Set<? extends OWLSubPropertyAxiom<OWLDataPropertyExpression>> getSubPropertyAxioms(OWLOntology ontology) {
        return ontology.getDataSubPropertyAxiomsForSubProperty(this);
    }


    @Override
	protected Set<? extends OWLNaryPropertyAxiom<OWLDataPropertyExpression>> getEquivalentPropertiesAxioms(OWLOntology ontology) {
        return ontology.getEquivalentDataPropertiesAxioms(this);
    }


    @Override
	protected Set<? extends OWLSubPropertyAxiom<OWLDataPropertyExpression>> getSubPropertyAxiomsForRHS(OWLOntology ont) {
        return ont.getDataSubPropertyAxiomsForSuperProperty(this);
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDataProperty)) {
                return false;
            }

            IRI otherIRI = ((OWLDataProperty) obj).getIRI();
            return otherIRI.equals(iri);
        }
        return false;
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }


    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty, Collections.singleton(ontology));
    }


    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLPropertyExpressionVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isAnonymous() {
        return false;
    }


    public OWLDataProperty asOWLDataProperty() {
        return this;
    }


    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass!");
    }


    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not an OWLDatatype!");
    }


    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not an OWLIndividual!");
    }


    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not an OWLObjectProperty!");
    }


    public boolean isOWLClass() {
        return false;
    }


    public boolean isOWLDataProperty() {
        return true;
    }


    public boolean isOWLDatatype() {
        return false;
    }


    public boolean isOWLNamedIndividual() {
        return false;
    }


    public boolean isOWLObjectProperty() {
        return false;
    }

    /**
     * Determines if this is the owl:topObjectProperty
     * @return <code>true</code> if this property is the owl:topObjectProperty otherwise <code>false</code>
     */
    public boolean isOWLTopObjectProperty() {
        return false;
    }

    /**
     * Determines if this is the owl:bottomObjectProperty
     * @return <code>true</code> if this property is the owl:bottomObjectProperty otherwise <code>false</code>
     */
    public boolean isOWLBottomObjectProperty() {
        return false;
    }

    /**
     * Determines if this is the owl:topDataProperty
     * @return <code>true</code> if this property is the owl:topDataProperty otherwise <code>false</code>
     */
    public boolean isOWLTopDataProperty() {
        return iri.equals(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    }

    /**
     * Determines if this is the owl:bottomDataProperty
     * @return <code>true</code> if this property is the owl:bottomDataProperty otherwise <code>false</code>
     */
    public boolean isOWLBottomDataProperty() {
        return iri.equals(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    }

    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new OWLRuntimeException("Not an annotation property");
    }

    public boolean isOWLAnnotationProperty() {
        return false;
    }

    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
        return ontology.getReferencingAxioms(this);
    }

    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
        return ontology.getReferencingAxioms(this, includeImports);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return iri.compareTo(((OWLDataProperty) object).getIRI());
    }
}
