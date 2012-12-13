package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Set;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 24/10/2012
 * </p>
 * An optimised implementation of OWLDatatype for OWL2Datatypes.
 */
public class OWL2DatatypeImpl implements OWLDatatype {

    // NOTE:  This class did extend OWLObjectImpl but this created a circular dependency and caused initialisation
    // problems with static methods.
    
    private static final long serialVersionUID = 30402L;
    private static final EnumMap<OWL2Datatype, OWLDatatype> instanceMap;

    static {
        final EnumMap<OWL2Datatype, OWLDatatype> map = new EnumMap<OWL2Datatype, OWLDatatype>(OWL2Datatype.class);
        for (OWL2Datatype datatype : OWL2Datatype.values()) {
            map.put(datatype, new OWL2DatatypeImpl(datatype));
        }
        instanceMap = map;
    }

    /**
     * Creates an instance of {@code OWLDatatypeImplForOWL2Datatype} for the specified {@link OWL2Datatype}
     * @param owl2Datatype The datatype.  Not {@code null}.
     * @throws NullPointerException if {@code owl2Datatype} is {@code null}.
     */
    private OWL2DatatypeImpl(OWL2Datatype owl2Datatype) {
        if(owl2Datatype == null) {
            throw new NullPointerException("owl2Datatype must not be null");
        }
        this.owl2Datatype = owl2Datatype;
    }

    /**
     * A factory method which gets an instance of {@link OWLDatatype} for an instance of {@link OWL2Datatype}
     * specified by the {@code owl2Datatype} parameter.
     * @param owl2Datatype The datatype to be retrieved.
     * @return A {@link OWLDatatype} that has the same IRI as the IRI returned by {@code owl2Datatype#getIRI()}.
     */
    public static OWLDatatype getDatatype(OWL2Datatype owl2Datatype) {
        return instanceMap.get(owl2Datatype);
    }

    private final OWL2Datatype owl2Datatype;

    /**
     * Gets the built in datatype information if this datatype is a built in
     * datatype.  This method should only be called if the isBuiltIn() method
     * returns <code>true</code>
     * @return The OWLDatatypeVocabulary that describes this built in datatype
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *          if this datatype is not a built in datatype.
     */
    @Override
    public OWL2Datatype getBuiltInDatatype() {
        return owl2Datatype;
    }

    /**
     * Determines if this datatype has the IRI <code>xsd:string</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:string</code>, otherwise <code>false</code>.
     */
    @Override
    public boolean isString() {
        return owl2Datatype == OWL2Datatype.XSD_STRING;
    }

    /**
     * Determines if this datatype has the IRI <code>xsd:integer</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:integer</code>, otherwise <code>false</code>.
     */
    @Override
    public boolean isInteger() {
        return owl2Datatype == OWL2Datatype.XSD_INTEGER;
    }

    /**
     * Determines if this datatype has the IRI <code>xsd:float</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:float</code>, otherwise <code>false</code>.
     */
    @Override
    public boolean isFloat() {
        return owl2Datatype == OWL2Datatype.XSD_FLOAT;
    }

    /**
     * Determines if this datatype has the IRI <code>xsd:double</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:double</code>, otherwise <code>false</code>.
     */
    @Override
    public boolean isDouble() {
        return owl2Datatype == OWL2Datatype.XSD_DOUBLE;
    }

    /**
     * Determines if this datatype has the IRI <code>xsd:boolean</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:boolean</code>, otherwise <code>false</code>.
     */
    @Override
    public boolean isBoolean() {
        return owl2Datatype == OWL2Datatype.XSD_BOOLEAN;
    }

    /**
     * Determines if this datatype has the IRI <code>rdf:PlainLiteral</code>
     * @return <code>true</code> if this datatype has the IRI <code>rdf:PlainLiteral</code> otherwise
     *         <code>false</code>
     */
    @Override
    public boolean isRDFPlainLiteral() {
        return owl2Datatype == OWL2Datatype.RDF_PLAIN_LITERAL;
    }

    /**
     * Determines if this data range is a datatype (int, float, ...)
     * @return <code>true</code> if this datarange is a datatype, or
     *         <code>false</code> if it is not a datatype and is some other
     *         data range such as a data range restriction, data oneOf or
     *         data complementOf.
     */
    @Override
    public boolean isDatatype() {
        return true;
    }

    /**
     * Determines if this data range is the top data type.
     * @return <code>true</code> if this data range is the top datatype otherwise
     *         <code>false</code>
     */
    @Override
    public boolean isTopDatatype() {
        return owl2Datatype == OWL2Datatype.RDFS_LITERAL;
    }

    /**
     * If this data range is a datatype then this method may be used to obtain it as a datatype (rather than
     * using an explicit cast).
     * @return This data range as an {@link org.semanticweb.owlapi.model.OWLDatatype}
     */
    @Override
    public OWLDatatype asOWLDatatype() {
        return this;
    }

    /**
     * Gets the type of this data range
     * @return The data range type
     */
    @Override
    public DataRangeType getDataRangeType() {
        return DataRangeType.DATATYPE;
    }

    @Override
    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(OWLDataRangeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    /**
     * Gets the entity type for this entity
     * @return The entity type
     */
    @Override
    public EntityType<?> getEntityType() {
        return EntityType.DATATYPE;
    }

    /**
     * Gets an entity that has the same IRI as this entity but is of the specified type.
     * @param entityType The type of the entity to obtain.  This entity is not affected in any way.
     * @return An entity that has the same IRI as this entity and is of the specified type
     */
    @Override
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType) {
        return OWLObjectImpl.getOWLDataFactory().getOWLEntity(entityType, OWL2Datatype.RDF_PLAIN_LITERAL.getIRI());
    }

    /**
     * Tests to see if this entity is of the specified type
     * @param entityType The entity type
     * @return <code>true</code> if this entity is of the specified type, otherwise <code>false</code>.
     */
    @Override
    public boolean isType(EntityType<?> entityType) {
        return entityType == EntityType.DATATYPE;
    }

    /**
     * Gets the annotations for this entity.  These are deemed to be annotations in annotation assertion
     * axioms that have a subject that is an IRI that is equal to the IRI of this entity.
     * @param ontology The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation assertion whose subject is an
     *         IRI corresponding to the IRI of this entity.
     */
    @Override
    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified
     * annotation property.
     * @param ontology The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of <code>OWLAnnotation</code> objects that have the specified
     *         URI.
     */
    @Override
    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology, OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty, Collections.singleton(ontology));
    }

    /**
     * @param ontology the ontology to use
     * @return the annotation assertion axioms about this entity in the provided ontology
     */
    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this, Collections.singleton(ontology));
    }

    /**
     * Determines if this entity is a built in entity. The entity is a built in entity if it is:
     * <ul>
     * <li>a class and the URI corresponds to owl:Thing or owl:Nothing</li>
     * <li>an object property and the URI corresponds to owl:topObjectProperty or owl:bottomObjectProperty</li>
     * <li>a data property and the URI corresponds to owl:topDataProperty or owl:bottomDataProperty</li>
     * <li>a datatype and the IRI is rdfs:Literal or is in the OWL 2 datatype map or is rdf:PlainLiteral</li>
     * <li>an annotation property and the URI is in the set of built in annotation property URIs</li>
     * </ul>
     * @return <code>true</code> if this entity is a built in entity, or <code>false</code>
     *         if this entity is not a builtin entity.
     */
    @Override
    public boolean isBuiltIn() {
        return true;
    }

    /**
     * A convenience method that determines if this entity is an OWLClass
     * @return <code>true</code> if this entity is an OWLClass, otherwise <code>false</code>
     */
    @Override
    public boolean isOWLClass() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLClass (in order to
     * avoid explicit casting).
     * @return The entity as an OWLClass.
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *          if this entity is not an OWLClass (check with the
     *          isOWLClass method first).
     */
    @Override
    public OWLClass asOWLClass() {
        throw new RuntimeException("Not an OWLClass");
    }

    /**
     * A convenience method that determines if this entity is an OWLObjectProperty
     * @return <code>true</code> if this entity is an OWLObjectProperty, otherwise <code>false</code>
     */
    @Override
    public boolean isOWLObjectProperty() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLObjectProperty (in order to
     * avoid explicit casting).
     * @return The entity as an OWLObjectProperty.
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *          if this entity is not an OWLObjectProperty (check with the
     *          isOWLObjectProperty method first).
     */
    @Override
    public OWLObjectProperty asOWLObjectProperty() {
        throw new RuntimeException("Not an OWLClass");
    }

    /**
     * A convenience method that determines if this entity is an OWLDataProperty
     * @return <code>true</code> if this entity is an OWLDataProperty, otherwise <code>false</code>
     */
    @Override
    public boolean isOWLDataProperty() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLDataProperty (in order to
     * avoid explicit casting).
     * @return The entity as an OWLDataProperty.
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *          if this entity is not an OWLDataProperty (check with the
     *          isOWLDataProperty method first).
     */
    @Override
    public OWLDataProperty asOWLDataProperty() {
        throw new RuntimeException("Not an OWLDataProperty");
    }

    /**
     * A convenience method that determines if this entity is an OWLNamedIndividual
     * @return <code>true</code> if this entity is an OWLNamedIndividual, otherwise <code>false</code>
     */
    @Override
    public boolean isOWLNamedIndividual() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLNamedIndividual (in order to
     * avoid explicit casting).
     * @return The entity as an OWLNamedIndividual.
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *          if this entity is not an OWLIndividual (check with the
     *          isOWLIndividual method first).
     */
    @Override
    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new RuntimeException("Not an OWLNamedIndividual");
    }

    /**
     * A convenience method that determines if this entity is an OWLDatatype
     * @return <code>true</code> if this entity is an OWLDatatype, otherwise <code>false</code>
     */
    @Override
    public boolean isOWLDatatype() {
        return true;
    }

    /**
     * A convenience method that determines if this entity is an OWLAnnotationProperty
     * @return <code>true</code> if this entity is an OWLAnnotationProperty, otherwise <code>false</code>
     */
    @Override
    public boolean isOWLAnnotationProperty() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLAnnotationProperty (in order to
     * avoid explicit casting).
     * @return The entity as an OWLAnnotationProperty.
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *          if this entity is not an OWLAnnotationProperty
     */
    @Override
    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new RuntimeException("Not an OWLAnnotationProperty");
    }

    /**
     * Returns a string representation that can be used as the ID of this entity.  This is the toString
     * representation of the IRI
     * @return A string representing the toString of the IRI of this entity.
     */
    @Override
    public String toStringID() {
        return owl2Datatype.getIRI().toString();
    }

    /**
     * Gets the axioms in the specified ontology that contain this entity in their signature.
     * @param ontology The ontology that will be searched for axioms
     * @return The axioms in the specified ontology whose signature contains this entity.
     */
    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
        return ontology.getReferencingAxioms(this);
    }

    /**
     * Gets the axioms in the specified ontology and possibly its imports closure that contain this entity in their
     * signature.
     * @param ontology The ontology that will be searched for axioms
     * @param includeImports If <code>true</code> then axioms in the imports closure will also be returned, if
     * <code>false</code> then only the axioms in the specified ontology will be returned.
     * @return The axioms in the specified ontology whose signature contains this entity.
     */
    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology, boolean includeImports) {
        return ontology.getReferencingAxioms(this, true);
    }

    @Override
    public void accept(OWLEntityVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLEntityVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    /**
     * Gets the IRI of this object
     * @return The IRI of this object
     */
    @Override
    public IRI getIRI() {
        return owl2Datatype.getIRI();
    }

    @Override
    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLDatatype)) {
            return false;
        }
        OWLDatatype other = (OWLDatatype) obj;
        return owl2Datatype.getIRI().equals(other.getIRI());
    }

    /**
     * Gets the signature of this object
     * @return A set of entities that correspond to the
     *         signature of this object. The set is a copy, changes are not reflected back.
     */
    @Override
    public Set<OWLEntity> getSignature() {
        return Collections.<OWLEntity>singleton(this);
    }

    /**
     * Gets the anonymous individuals occurring in this object, as collected by an
     * OWLEntityCollector. The set is a copy, changes are not reflected back.
     * @return A set of anonymous individuals.
     */
    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return Collections.emptySet();
    }

    /**
     * A convenience method that obtains the classes
     * that are in the signature of this object
     * @return A set containing the classes that are in the signature
     *         of this object. The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection
     *         and changes are not reflected by the signature.
     */
    @Override
    public Set<OWLClass> getClassesInSignature() {
        return Collections.emptySet();
    }

    /**
     * A convenience method that obtains the data properties
     * that are in the signature of this object
     * @return A set containing the data properties that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection
     *         and changes are not reflected by the signature.
     */
    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return Collections.emptySet();
    }

    /**
     * A convenience method that obtains the object properties
     * that are in the signature of this object
     * @return A set containing the object properties that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection
     *         and changes are not reflected by the signature.
     */
    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return Collections.emptySet();
    }

    /**
     * A convenience method that obtains the individuals
     * that are in the signature of this object
     * @return A set containing the individuals that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection
     *         and changes are not reflected by the signature.
     */
    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return Collections.emptySet();
    }

    /**
     * A convenience method that obtains the datatypes
     * that are in the signature of this object
     * @return A set containing the datatypes that are in the signature
     *         of this object.The set is a subset of the signature, and
     *         is not backed by the signature; it is a modifiable collection
     *         and changes are not reflected by the signature.
     */
    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return Collections.emptySet();
    }

    /**
     * Gets all of the nested (includes top level) class expressions that are used in this object
     * @return A set of {@link org.semanticweb.owlapi.model.OWLClassExpression}s that represent the nested class
     *         expressions used in this object.
     */
    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        return Collections.emptySet();
    }

    /**
     * Determines if this object is either, owl:Thing (the top class), owl:topObjectProperty (the top object property)
     * , owl:topDataProperty (the top data property) or rdfs:Literal (the top datatype).
     * @return <code>true</code> if this object corresponds to one of the above entities.
     */
    @Override
    public boolean isTopEntity() {
        return owl2Datatype == OWL2Datatype.RDF_PLAIN_LITERAL;
    }

    /**
     * Determines if this object is either, owl:Nothing (the bottom class), owl:bottomObjectProperty (the bottom object
     * property)
     * , owl:bottomDataProperty (the bottom data property).
     * @return <code>true</code> if this object corresponds to one of the above entities.
     */
    @Override
    public boolean isBottomEntity() {
        return false;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this object.
     */
    @Override
    public int compareTo(OWLObject o) {
        if(!(o instanceof OWLDatatype)) {
            OWLObjectTypeIndexProvider provider = new OWLObjectTypeIndexProvider();
            return provider.getTypeIndex(o);
        }
        OWLDatatype other = (OWLDatatype) o;
        return getIRI().compareTo(other.getIRI());
    }

    @Override
    public int hashCode() {
        return HashCode.hashCode(this);
    }
}
