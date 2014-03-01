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
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * An optimised implementation of OWLDatatype for OWL2Datatypes.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 24/10/2012
 */
public class OWL2DatatypeImpl implements OWLDatatype {

    // NOTE: This class did extend OWLObjectImpl but this created a circular
    // dependency and caused initialisation
    // problems with static methods.
    private static final long serialVersionUID = 30406L;
    private static final EnumMap<OWL2Datatype, OWLDatatype> instanceMap;
    static {
        final EnumMap<OWL2Datatype, OWLDatatype> map = new EnumMap<OWL2Datatype, OWLDatatype>(
                OWL2Datatype.class);
        for (OWL2Datatype datatype : OWL2Datatype.values()) {
            map.put(datatype, new OWL2DatatypeImpl(datatype));
        }
        instanceMap = map;
    }

    /**
     * Creates an instance of {@code OWLDatatypeImplForOWL2Datatype} for the
     * specified {@link OWL2Datatype}.
     * 
     * @param owl2Datatype
     *        The datatype. Not {@code null}.
     * @throws NullPointerException
     *         if {@code owl2Datatype} is {@code null}.
     */
    private OWL2DatatypeImpl(OWL2Datatype owl2Datatype) {
        if (owl2Datatype == null) {
            throw new NullPointerException("owl2Datatype must not be null");
        }
        this.owl2Datatype = owl2Datatype;
    }

    /**
     * A factory method which gets an instance of {@link OWLDatatype} for an
     * instance of {@link OWL2Datatype} specified by the {@code owl2Datatype}
     * parameter.
     * 
     * @param owl2Datatype
     *        The datatype to be retrieved.
     * @return A {@link OWLDatatype} that has the same IRI as the IRI returned
     *         by {@code owl2Datatype#getIRI()}.
     */
    public static OWLDatatype getDatatype(OWL2Datatype owl2Datatype) {
        return instanceMap.get(owl2Datatype);
    }

    private final OWL2Datatype owl2Datatype;

    @Override
    public OWL2Datatype getBuiltInDatatype() {
        return owl2Datatype;
    }

    @Override
    public boolean isString() {
        return owl2Datatype == OWL2Datatype.XSD_STRING;
    }

    @Override
    public boolean isInteger() {
        return owl2Datatype == OWL2Datatype.XSD_INTEGER;
    }

    @Override
    public boolean isFloat() {
        return owl2Datatype == OWL2Datatype.XSD_FLOAT;
    }

    @Override
    public boolean isDouble() {
        return owl2Datatype == OWL2Datatype.XSD_DOUBLE;
    }

    @Override
    public boolean isBoolean() {
        return owl2Datatype == OWL2Datatype.XSD_BOOLEAN;
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return owl2Datatype == OWL2Datatype.RDF_PLAIN_LITERAL;
    }

    @Override
    public boolean isDatatype() {
        return true;
    }

    @Override
    public boolean isTopDatatype() {
        return owl2Datatype == OWL2Datatype.RDFS_LITERAL;
    }

    @Override
    public OWLDatatype asOWLDatatype() {
        return this;
    }

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

    @Override
    public EntityType<?> getEntityType() {
        return EntityType.DATATYPE;
    }

    @Override
    public <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType) {
        return OWLObjectImpl.getOWLEntity(entityType,
                OWL2Datatype.RDF_PLAIN_LITERAL.getIRI());
    }

    @Override
    public boolean isType(EntityType<?> entityType) {
        return entityType == EntityType.DATATYPE;
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology) {
        return ImplUtils.getAnnotations(this, Collections.singleton(ontology));
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(OWLOntology ontology,
            OWLAnnotationProperty annotationProperty) {
        return ImplUtils.getAnnotations(this, annotationProperty,
                Collections.singleton(ontology));
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            OWLOntology ontology) {
        return ImplUtils.getAnnotationAxioms(this,
                Collections.singleton(ontology));
    }

    @Override
    public boolean isBuiltIn() {
        return true;
    }

    @Override
    public boolean isOWLClass() {
        return false;
    }

    @Override
    public OWLClass asOWLClass() {
        throw new RuntimeException("Not an OWLClass");
    }

    @Override
    public boolean isOWLObjectProperty() {
        return false;
    }

    @Override
    public OWLObjectProperty asOWLObjectProperty() {
        throw new RuntimeException("Not an OWLObjectProperty");
    }

    @Override
    public boolean isOWLDataProperty() {
        return false;
    }

    @Override
    public OWLDataProperty asOWLDataProperty() {
        throw new RuntimeException("Not an OWLDataProperty");
    }

    @Override
    public boolean isOWLNamedIndividual() {
        return false;
    }

    @Override
    public OWLNamedIndividual asOWLNamedIndividual() {
        throw new RuntimeException("Not an OWLNamedIndividual");
    }

    @Override
    public boolean isOWLDatatype() {
        return true;
    }

    @Override
    public boolean isOWLAnnotationProperty() {
        return false;
    }

    @Override
    public OWLAnnotationProperty asOWLAnnotationProperty() {
        throw new RuntimeException("Not an OWLAnnotationProperty");
    }

    @Override
    public String toStringID() {
        return owl2Datatype.getIRI().toString();
    }

    @Override
    public String toString() {
        return toStringID();
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology) {
        return ontology.getReferencingAxioms(this);
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLOntology ontology,
            boolean includeImports) {
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

    @Override
    public Set<OWLEntity> getSignature() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLEntity> singleton(this));
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return this.equals(owlEntity);
    }

    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLAnonymousIndividual> emptySet());
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLClass> emptySet());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLDataProperty> emptySet());
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLObjectProperty> emptySet());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLNamedIndividual> emptySet());
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .singleton((OWLDatatype) this));
    }

    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        return CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Collections
                        .<OWLClassExpression> emptySet());
    }

    @Override
    public boolean isTopEntity() {
        return owl2Datatype == OWL2Datatype.RDF_PLAIN_LITERAL;
    }

    @Override
    public boolean isBottomEntity() {
        return false;
    }

    @Override
    public int compareTo(OWLObject o) {
        if (!(o instanceof OWLDatatype)) {
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
