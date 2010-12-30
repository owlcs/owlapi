package uk.ac.manchester.cs.owl.owlapi;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDatatypeImpl extends OWLObjectImpl implements OWLDatatype {

    private IRI iri;

    private boolean top;

    private boolean builtin;

    public OWLDatatypeImpl(OWLDataFactory dataFactory, IRI iri) {
        super(dataFactory);
        this.iri = iri;
        top = iri.equals(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        builtin = top || OWL2Datatype.isBuiltIn(iri) || iri.equals(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
    }

    @Override
	public boolean isTopEntity() {
        return top;
    }

    @Override
	public boolean isBottomEntity() {
        return false;
    }

    /**
     * Determines if this datatype has the IRI <code>rdf:PlainLiteral</code>
     * @return <code>true</code> if this datatype has the IRI <code>rdf:PlainLiteral</code> otherwise <code>false</code>
     */
    public boolean isRDFPlainLiteral() {
        return iri.isPlainLiteral();
    }

    /**
     * Gets the entity type for this entity
     * @return The entity type
     */
    public EntityType<?> getEntityType() {
        return EntityType.DATATYPE;
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

    public IRI getIRI() {
        return iri;
    }

    public boolean isBuiltIn() {
        return builtin;
    }

    public DataRangeType getDataRangeType() {
        return DataRangeType.DATATYPE;
    }

    public OWL2Datatype getBuiltInDatatype() {
        if (!builtin) {
            throw new OWLRuntimeException("Not a built in datatype.  The getBuiltInDatatype() method should only be called on built in datatypes.");
        }
        else {
            return OWL2Datatype.getDatatype(iri);
        }
    }

    public boolean isDouble() {
        return iri.equals(OWL2Datatype.XSD_DOUBLE.getIRI());
    }

    public boolean isFloat() {
        return iri.equals(OWL2Datatype.XSD_FLOAT.getIRI());
    }

    public boolean isInteger() {
        return iri.equals(OWL2Datatype.XSD_INTEGER.getIRI());
    }

    public boolean isString() {
        return iri.equals(OWL2Datatype.XSD_STRING.getIRI());
    }

    public boolean isBoolean() {
        return iri.equals(OWL2Datatype.XSD_BOOLEAN.getIRI());
    }

    public boolean isDatatype() {
        return true;
    }


    public boolean isTopDatatype() {
        return top;
    }


    public URI getURI() {
        return iri.toURI();
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (obj instanceof OWLDatatype) {
                return ((OWLDatatype) obj).getIRI().equals(getIRI());
            }
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


    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass!");
    }


    public OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not a data property!");
    }


    public OWLDatatype asOWLDatatype() {
        return this;
    }


    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new OWLRuntimeException("Not an individual!");
    }


    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not an object property");
    }


    public boolean isOWLClass() {
        return false;
    }


    public boolean isOWLDataProperty() {
        return false;
    }


    public boolean isOWLDatatype() {
        return true;
    }


    public boolean isOWLNamedIndividual() {
        return false;
    }


    public boolean isOWLObjectProperty() {
        return false;
    }

    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new OWLRuntimeException("Not an annotation property");
    }

    public boolean isOWLAnnotationProperty() {
        return false;
    }

    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLDataVisitor visitor) {
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

    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLDataRangeVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return iri.compareTo(((OWLDatatype) object).getIRI());
    }


    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
        return ontology.getReferencingAxioms(this);
    }

    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
        return ontology.getReferencingAxioms(this, includeImports);
    }
}
