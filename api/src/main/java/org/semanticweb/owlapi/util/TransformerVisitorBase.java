package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectType;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * A base class for transformations.
 * 
 * @author ignazio
 *
 * @param <T> transformed type
 */
public class TransformerVisitorBase<T> implements OWLObjectVisitorEx<OWLObject> {

    protected OWLDataFactory df;
    private Predicate<Object> predicate;
    private UnaryOperator<T> transformer;
    private Class<T> witness;
    private EnumMap<OWLObjectType, UnaryOperator<?>> rebuilders = buildRebuilders();

    protected TransformerVisitorBase(Predicate<Object> predicate, UnaryOperator<T> transformer,
        OWLDataFactory df, Class<T> witness) {
        this.predicate = predicate;
        this.transformer = transformer;
        this.df = df;
        this.witness = witness;
    }

    @SuppressWarnings("unchecked")
    protected <Q extends OWLObject> Q t(Q t) {
        return (Q) t.accept(this);
    }

    @SuppressWarnings("unchecked")
    protected OWLFacet t(OWLFacet t) {
        return (OWLFacet) transformer.apply((T) t);
    }

    protected <Q extends OWLObject> List<Q> t(Stream<Q> c) {
        return asList(c.map(this::t));
    }

    protected <Q extends OWLObject> List<Q> t(List<Q> c) {
        return t(c.stream());
    }

    @Nullable
    protected <Q> Q check(Q o) {
        if (witness.isInstance(o)) {
            @SuppressWarnings("unchecked")
            Q transform = (Q) transformer.apply(witness.cast(o));
            if (o instanceof OWLAxiom ? update((OWLAxiom) transform, (OWLAxiom) o) == transform
                : transform != o) {
                return transform;
            }
        }
        return null;
    }

    protected OWLObject visitGeneric(OWLObject node) {
        OWLObject transform = check(node);
        if (transform != null) {
            return transform;
        }
        if (!predicate.test(node)) {
            return node;
        }
        return node instanceof OWLAxiom ? applyToAxiom(node) : apply(node);
    }

    protected OWLAxiom applyToAxiom(OWLObject node) {
        return update(apply((OWLAxiom) node), (OWLAxiom) node);
    }

    @SuppressWarnings({"unchecked"})
    protected <Z extends OWLObject> Z apply(Z node) {
        return ((UnaryOperator<Z>) rebuilders.get(node.type())).apply(node);
    }

    @Override
    public OWLObject doDefault(OWLObject object) {
        return visitGeneric(object);
    }

    protected OWLAxiom update(OWLAxiom transform, OWLAxiom axiom) {
        return !axiom.equals(transform) ? transform : axiom;
    }

    protected void visitId(@SuppressWarnings("unused") OWLOntology ontology) {}

    @SuppressWarnings("unused")
    protected void visitImports(OWLOntology ontology, OWLImportsDeclaration id) {}

    @SuppressWarnings("unused")
    protected void visitAnnotation(OWLOntology ontology, OWLAnnotation a) {}

    @Override
    public OWLObject visit(OWLOntology ontology) {
        AxiomType.AXIOM_TYPES.stream().flatMap(ontology::axioms).forEach(ax -> ax.accept(this));
        ontology.annotations().forEach(a -> visitAnnotation(ontology, a));
        ontology.importsDeclarations().forEach(id -> visitImports(ontology, id));
        visitId(ontology);
        // the ontology object is never modified
        return ontology;
    }

    @Override
    public OWLObject visit(OWLLiteral node) {
        OWLObject transform = check(node);
        if (transform != null) {
            return transform;
        }
        if (!predicate.test(node)) {
            return node;
        }
        // plain literal is a terminal; if the transform did not make a
        // change, then no change is required
        if (node.isRDFPlainLiteral()) {
            return node;
        }
        return df.getOWLLiteral(node.getLiteral(), t(node.getDatatype()));
    }

    @Override
    public OWLObject visit(IRI iri) {
        OWLObject transform = check(iri);
        if (transform != null) {
            return transform;
        }
        // IRI is a terminal; if the transform did not make a change, then
        // no change is required
        return iri;
    }

    @Override
    public OWLObject visit(OWLAnonymousIndividual individual) {
        OWLObject transform = check(individual);
        if (transform != null) {
            return transform;
        }
        // OWLAnonymousIndividual is a terminal; if the transform did not
        // make a change, then no change is required
        return individual;
    }

    private EnumMap<OWLObjectType, UnaryOperator<?>> buildRebuilders() {
        EnumMap<OWLObjectType, UnaryOperator<?>> map = new EnumMap<>(OWLObjectType.class);
        //@formatter:off
            map.put(OWLObjectType.DECLARATION,              (OWLDeclarationAxiom ax)                -> df.getOWLDeclarationAxiom(t(ax.getEntity()), t(ax.annotations())));
            map.put(OWLObjectType.DATATYPE_DEFINITION,      (OWLDatatypeDefinitionAxiom ax)         -> df.getOWLDatatypeDefinitionAxiom(t(ax.getDatatype()), t(ax.getDataRange()), t(ax.annotations())));
            map.put(OWLObjectType.ANNOTATION_ASSERTION,     (OWLAnnotationAssertionAxiom ax)        -> df.getOWLAnnotationAssertionAxiom(t(ax.getSubject()), t(ax.getAnnotation()), t(ax.annotations())));
            map.put(OWLObjectType.SUB_ANNOTATION,           (OWLSubAnnotationPropertyOfAxiom ax)    -> df.getOWLSubAnnotationPropertyOfAxiom(t(ax.getSubProperty()), t(ax.getSuperProperty()), t(ax.annotations())));
            map.put(OWLObjectType.EQUIVALENT_OBJECT,        (OWLEquivalentObjectPropertiesAxiom ax) -> df.getOWLEquivalentObjectPropertiesAxiom(t(ax.properties()), t(ax.annotations())));
            map.put(OWLObjectType.ANNOTATION_DOMAIN,        (OWLAnnotationPropertyDomainAxiom ax)   -> df.getOWLAnnotationPropertyDomainAxiom(t(ax.getProperty()), t(ax.getDomain()), t(ax.annotations())));
            map.put(OWLObjectType.SUB_CLASS,                (OWLSubClassOfAxiom ax)                 -> df.getOWLSubClassOfAxiom(t(ax.getSubClass()), t(ax.getSuperClass()), t(ax.annotations())));
            map.put(OWLObjectType.ANNOTATION_RANGE,         (OWLAnnotationPropertyRangeAxiom ax)    -> df.getOWLAnnotationPropertyRangeAxiom(t(ax.getProperty()), t(ax.getRange()), t(ax.annotations())));
            map.put(OWLObjectType.ASYMMETRIC,               (OWLAsymmetricObjectPropertyAxiom ax)   -> df.getOWLAsymmetricObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.REFLEXIVE,                (OWLReflexiveObjectPropertyAxiom ax)    -> df.getOWLReflexiveObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.DISJOINT_CLASSES,         (OWLDisjointClassesAxiom ax)            -> df.getOWLDisjointClassesAxiom(t(ax.classExpressions()), t(ax.annotations())));
            map.put(OWLObjectType.DATA_DOMAIN,              (OWLDataPropertyDomainAxiom ax)         -> df.getOWLDataPropertyDomainAxiom(t(ax.getProperty()), t(ax.getDomain()), t(ax.annotations())));
            map.put(OWLObjectType.OBJECT_DOMAIN,            (OWLObjectPropertyDomainAxiom ax)       -> df.getOWLObjectPropertyDomainAxiom(t(ax.getProperty()), t(ax.getDomain()),t(ax.annotations())));
            map.put(OWLObjectType.DIFFERENT_INDIVIDUALS,    (OWLDifferentIndividualsAxiom ax)       -> df.getOWLDifferentIndividualsAxiom(t(ax.individuals()), t(ax.annotations())));
            map.put(OWLObjectType.DISJOINT_DATA,            (OWLDisjointDataPropertiesAxiom ax)     -> df.getOWLDisjointDataPropertiesAxiom(t(ax.properties()), t(ax.annotations())));
            map.put(OWLObjectType.DISJOINT_OBJECT,          (OWLDisjointObjectPropertiesAxiom ax)   -> df.getOWLDisjointObjectPropertiesAxiom(t(ax.properties()), t(ax.annotations())));
            map.put(OWLObjectType.OBJECT_RANGE,             (OWLObjectPropertyRangeAxiom ax)        -> df.getOWLObjectPropertyRangeAxiom(t(ax.getProperty()), t(ax.getRange()), t(ax.annotations())));
            map.put(OWLObjectType.OBJECT_ASSERTION,         (OWLObjectPropertyAssertionAxiom ax)    -> df.getOWLObjectPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()), t(ax.getObject()), t(ax.annotations())));
            map.put(OWLObjectType.TRANSITIVE,               (OWLTransitiveObjectPropertyAxiom ax)   -> df.getOWLTransitiveObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.IRREFLEXIVE,              (OWLIrreflexiveObjectPropertyAxiom ax)  -> df.getOWLIrreflexiveObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.SUB_DATA,                 (OWLSubDataPropertyOfAxiom ax)          -> df.getOWLSubDataPropertyOfAxiom(t(ax.getSubProperty()), t(ax.getSuperProperty()), t(ax.annotations())));
            map.put(OWLObjectType.SAME_INDIVIDUAL,          (OWLSameIndividualAxiom ax)             -> df.getOWLSameIndividualAxiom(t(ax.individuals()), t(ax.annotations())));
            map.put(OWLObjectType.SUB_PROPERTY_CHAIN,       (OWLSubPropertyChainOfAxiom ax)         -> df.getOWLSubPropertyChainOfAxiom(t(ax.getPropertyChain()), t(ax.getSuperProperty()), t(ax.annotations())));
            map.put(OWLObjectType.INVERSE,                  (OWLInverseObjectPropertiesAxiom ax)    -> df.getOWLInverseObjectPropertiesAxiom(t(ax.getFirstProperty()), t(ax.getSecondProperty()), t(ax.annotations())));
            map.put(OWLObjectType.HASKEY,                   (OWLHasKeyAxiom ax)                     -> df.getOWLHasKeyAxiom(t(ax.getClassExpression()), t(ax.propertyExpressions()), t(ax.annotations())));
            map.put(OWLObjectType.DATA_ASSERTION,           (OWLDataPropertyAssertionAxiom ax)      -> df.getOWLDataPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()), t(ax.getObject()), t(ax.annotations())));
            map.put(OWLObjectType.SUB_OBJECT,               (OWLSubObjectPropertyOfAxiom ax)        -> df.getOWLSubObjectPropertyOfAxiom(t(ax.getSubProperty()), t(ax.getSuperProperty()), t(ax.annotations())));
            map.put(OWLObjectType.FUNCTIONAL_OBJECT,        (OWLFunctionalObjectPropertyAxiom ax)   -> df.getOWLFunctionalObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.SYMMETRIC,                (OWLSymmetricObjectPropertyAxiom ax)    -> df.getOWLSymmetricObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.DISJOINT_UNION,           (OWLDisjointUnionAxiom ax)              -> df.getOWLDisjointUnionAxiom(t(ax.getOWLClass()), t(ax.classExpressions()),t(ax.annotations())));
            map.put(OWLObjectType.DATA_RANGE,               (OWLDataPropertyRangeAxiom ax)          -> df.getOWLDataPropertyRangeAxiom(t(ax.getProperty()), t(ax.getRange()), t(ax    .annotations())));
            map.put(OWLObjectType.FUNCTIONAL_DATA,          (OWLFunctionalDataPropertyAxiom ax)     -> df.getOWLFunctionalDataPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.EQUIVALENT_DATA,          (OWLEquivalentDataPropertiesAxiom ax)   -> df.getOWLEquivalentDataPropertiesAxiom(t(ax.properties()), t(ax.annotations())));
            map.put(OWLObjectType.CLASS_ASSERTION,          (OWLClassAssertionAxiom ax)             -> df.getOWLClassAssertionAxiom(t(ax.getClassExpression()), t(ax.getIndividual()), t(ax.annotations())));
            map.put(OWLObjectType.EQUIVALENT_CLASSES,       (OWLEquivalentClassesAxiom ax)          -> df.getOWLEquivalentClassesAxiom(t(ax.classExpressions()), t(ax.annotations())));
            map.put(OWLObjectType.NEGATIVE_OBJECT_ASSERTION,(OWLNegativeObjectPropertyAssertionAxiom ax) -> df.getOWLNegativeObjectPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()), t(ax.getObject()), t(ax.annotations())));
            map.put(OWLObjectType.NEGATIVE_DATA_ASSERTION,  (OWLNegativeDataPropertyAssertionAxiom ax)   -> df.getOWLNegativeDataPropertyAssertionAxiom(t(ax.getProperty()), t(ax.getSubject()), t(ax.getObject()), t(ax.annotations())));
            map.put(OWLObjectType.INVERSE_FUNCTIONAL,       (OWLInverseFunctionalObjectPropertyAxiom ax) -> df.getOWLInverseFunctionalObjectPropertyAxiom(t(ax.getProperty()), t(ax.annotations())));
            map.put(OWLObjectType.SOME_DATA,                (OWLDataSomeValuesFrom ce)              -> df.getOWLDataSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.FORALL_DATA,              (OWLDataAllValuesFrom ce)               -> df.getOWLDataAllValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.HASVALUE_DATA,            (OWLDataHasValue ce)                    -> df.getOWLDataHasValue(t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.MIN_DATA,                 (OWLDataMinCardinality ce)              -> df.getOWLDataMinCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.EXACT_DATA,               (OWLDataExactCardinality ce)            -> df.getOWLDataExactCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.MAX_DATA,                 (OWLDataMaxCardinality ce)              -> df.getOWLDataMaxCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.DATATYPE,                 (OWLDatatype node)                      -> df.getOWLDatatype(t(node.getIRI())));
            map.put(OWLObjectType.NOT_DATA,                 (OWLDataComplementOf node)              -> df.getOWLDataComplementOf(t(node.getDataRange())));
            map.put(OWLObjectType.ONEOF_DATA,               (OWLDataOneOf node)                     -> df.getOWLDataOneOf(t(node.values())));
            map.put(OWLObjectType.AND_DATA,                 (OWLDataIntersectionOf node)            -> df.getOWLDataIntersectionOf(t(node.operands())));
            map.put(OWLObjectType.OR_DATA,                  (OWLDataUnionOf node)                   -> df.getOWLDataUnionOf(t(node.operands())));
            map.put(OWLObjectType.DATATYPE_RESTRICTION,     (OWLDatatypeRestriction node)           -> df.getOWLDatatypeRestriction(t(node.getDatatype()), t(node.facetRestrictions())));
            map.put(OWLObjectType.FACET_RESTRICTION,        (OWLFacetRestriction node)              -> df.getOWLFacetRestriction(t(node.getFacet()), t(node.getFacetValue())));
            map.put(OWLObjectType.OBJECT_PROPERTY,          (OWLObjectProperty property)            -> df.getOWLObjectProperty(t(property.getIRI())));
            map.put(OWLObjectType.INVERSE_OBJECT,           (OWLObjectInverseOf property)           -> df.getOWLObjectInverseOf(t(property.getNamedProperty())));
            map.put(OWLObjectType.DATA_PROPERTY,            (OWLDataProperty property)              -> df.getOWLDataProperty(t(property.getIRI())));
            map.put(OWLObjectType.ANNOTATION_PROPERTY,      (OWLAnnotationProperty property)        -> df.getOWLAnnotationProperty(t(property.getIRI())));
            map.put(OWLObjectType.NAMED_INDIVIDUAL,         (OWLNamedIndividual individual)         -> df.getOWLNamedIndividual(t(individual.getIRI())));
            map.put(OWLObjectType.ANNOTATION,               (OWLAnnotation node)                    -> df.getOWLAnnotation(t(node.getProperty()), t(node.getValue()), t(node.annotations())));
            map.put(OWLObjectType.CLASS,                    (OWLClass ce)                           -> df.getOWLClass(t(ce.getIRI())));
            map.put(OWLObjectType.AND_OBJECT,               (OWLObjectIntersectionOf ce)            -> df.getOWLObjectIntersectionOf(t(ce.operands())));
            map.put(OWLObjectType.OR_OBJECT,                (OWLObjectUnionOf ce)                   -> df.getOWLObjectUnionOf(t(ce.operands())));
            map.put(OWLObjectType.NOT_OBJECT,               (OWLObjectComplementOf ce)              -> df.getOWLObjectComplementOf(t(ce.getOperand())));
            map.put(OWLObjectType.SOME_OBJECT,              (OWLObjectSomeValuesFrom ce)            -> df.getOWLObjectSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.FORALL_OBJECT,            (OWLObjectAllValuesFrom ce)             -> df.getOWLObjectAllValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.HASVALUE_OBJECT,          (OWLObjectHasValue ce)                  -> df.getOWLObjectHasValue(t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.MIN_OBJECT,               (OWLObjectMinCardinality ce)            -> df.getOWLObjectMinCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.EXACT_OBJECT,             (OWLObjectExactCardinality ce)          -> df.getOWLObjectExactCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.MAX_OBJECT,               (OWLObjectMaxCardinality ce)            -> df.getOWLObjectMaxCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller())));
            map.put(OWLObjectType.HASSELF_OBJECT,           (OWLObjectHasSelf ce)                   -> df.getOWLObjectHasSelf(t(ce.getProperty())));
            map.put(OWLObjectType.ONEOF_OBJECT,             (OWLObjectOneOf ce)                     -> df.getOWLObjectOneOf(t(ce.individuals())));
            map.put(OWLObjectType.SWRL_RULE,                (SWRLRule ax)                           -> df.getSWRLRule(t(ax.body()), t(ax.head()), t(ax.annotations())));
            map.put(OWLObjectType.SWRL_DIFFERENT_INDIVIDUAL,(SWRLDifferentIndividualsAtom node)     -> df.getSWRLDifferentIndividualsAtom(t(node.getFirstArgument()), t(node.getSecondArgument())));
            map.put(OWLObjectType.SWRL_CLASS,               (SWRLClassAtom node)                    -> df.getSWRLClassAtom(t(node.getPredicate()), t(node.getArgument())));
            map.put(OWLObjectType.SWRL_DATA_RANGE,          (SWRLDataRangeAtom node)                -> df.getSWRLDataRangeAtom(t(node.getPredicate()), t(node.getArgument())));
            map.put(OWLObjectType.SWRL_OBJECT_PROPERTY,     (SWRLObjectPropertyAtom node)           -> df.getSWRLObjectPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()), t(node.getSecondArgument())));
            map.put(OWLObjectType.SWRL_DATA_PROPERTY,       (SWRLDataPropertyAtom node)             -> df.getSWRLDataPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()), t(node.getSecondArgument())));
            map.put(OWLObjectType.SWRL_BUILTIN,             (SWRLBuiltInAtom node)                  -> df.getSWRLBuiltInAtom(t(node.getPredicate()), t(node.getArguments())));
            map.put(OWLObjectType.SWRL_VARIABLE,            (SWRLVariable node)                     -> df.getSWRLVariable(t(node.getIRI())));
            map.put(OWLObjectType.SWRL_INDIVIDUAL,          (SWRLIndividualArgument node)           -> df.getSWRLIndividualArgument(t(node.getIndividual())));
            map.put(OWLObjectType.SWRL_LITERAL,             (SWRLLiteralArgument node)              -> df.getSWRLLiteralArgument(t(node.getLiteral())));
            map.put(OWLObjectType.SWRL_SAME_INDIVIDUAL,     (SWRLSameIndividualAtom node)           -> df.getSWRLSameIndividualAtom(t(node.getFirstArgument()), t(node.getSecondArgument())));
            //@formatter:on
        return map;
    }
}
