package org.semanticweb.owlapi6.model;

import java.util.Optional;

/**
 * Value visitor for AxiomType
 *
 * @param <T> type to return
 * @author ignazio
 */
public interface AxiomTypeVisitorEx<T> {

    /**
     * @param type axiom type to visit
     * @return return value
     */
    default Optional<T> visit(AxiomType<?> type) {
        if (type.equals(AxiomType.DECLARATION)) {
            return visitDeclarationAxiom();
        }
        if (type.equals(AxiomType.EQUIVALENT_CLASSES)) {
            return visitEquivalentClassesAxiom();
        }
        if (type.equals(AxiomType.SUBCLASS_OF)) {
            return visitSubClassOfAxiom();
        }
        if (type.equals(AxiomType.DISJOINT_CLASSES)) {
            return visitDisjointClassesAxiom();
        }
        if (type.equals(AxiomType.DISJOINT_UNION)) {
            return visitDisjointUnionAxiom();
        }
        if (type.equals(AxiomType.CLASS_ASSERTION)) {
            return visitClassAssertionAxiom();
        }
        if (type.equals(AxiomType.SAME_INDIVIDUAL)) {
            return visitSameIndividualAxiom();
        }
        if (type.equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
            return visitDifferentIndividualsAxiom();
        }
        if (type.equals(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
            return visitObjectPropertyAssertionAxiom();
        }
        if (type.equals(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
            return visitNegativeObjectPropertyAssertionAxiom();
        }
        if (type.equals(AxiomType.DATA_PROPERTY_ASSERTION)) {
            return visitDataPropertyAssertionAxiom();
        }
        if (type.equals(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION)) {
            return visitNegativeDataPropertyAssertionAxiom();
        }
        if (type.equals(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            return visitEquivalentObjectPropertiesAxiom();
        }
        if (type.equals(AxiomType.SUB_OBJECT_PROPERTY)) {
            return visitSubObjectPropertyOfAxiom();
        }
        if (type.equals(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
            return visitInverseObjectPropertiesAxiom();
        }
        if (type.equals(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
            return visitFunctionalObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
            return visitInverseFunctionalObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
            return visitSymmetricObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
            return visitAsymmetricObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
            return visitTransitiveObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
            return visitReflexiveObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
            return visitIrreflexiveObjectPropertyAxiom();
        }
        if (type.equals(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            return visitObjectPropertyDomainAxiom();
        }
        if (type.equals(AxiomType.OBJECT_PROPERTY_RANGE)) {
            return visitObjectPropertyRangeAxiom();
        }
        if (type.equals(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            return visitDisjointObjectPropertiesAxiom();
        }
        if (type.equals(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            return visitSubPropertyChainOfAxiom();
        }
        if (type.equals(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            return visitEquivalentDataPropertiesAxiom();
        }
        if (type.equals(AxiomType.SUB_DATA_PROPERTY)) {
            return visitSubDataPropertyOfAxiom();
        }
        if (type.equals(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            return visitFunctionalDataPropertyAxiom();
        }
        if (type.equals(AxiomType.DATA_PROPERTY_DOMAIN)) {
            return visitDataPropertyDomainAxiom();
        }
        if (type.equals(AxiomType.DATA_PROPERTY_RANGE)) {
            return visitDataPropertyRangeAxiom();
        }
        if (type.equals(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            return visitDisjointDataPropertiesAxiom();
        }
        if (type.equals(AxiomType.DATATYPE_DEFINITION)) {
            return visitDatatypeDefinitionAxiom();
        }
        if (type.equals(AxiomType.HAS_KEY)) {
            return visitHasKeyAxiom();
        }
        if (type.equals(AxiomType.SWRL_RULE)) {
            return visitSWRLRule();
        }
        if (type.equals(AxiomType.ANNOTATION_ASSERTION)) {
            return visitAnnotationAssertionAxiom();
        }
        if (type.equals(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            return visitSubAnnotationPropertyOfAxiom();
        }
        if (type.equals(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            return visitAnnotationPropertyRangeAxiom();
        }
        if (type.equals(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            return visitAnnotationPropertyDomainAxiom();
        }
        return doDefault();
    }

    /**
     * @return default value if no values are matched
     */
    default Optional<T> doDefault() {
        return Optional.empty();
    }

    /** @return value for DeclarationAxiom */
    default Optional<T> visitDeclarationAxiom() {
        return doDefault();
    }

    /** @return value for EquivalentClassesAxiom */
    default Optional<T> visitEquivalentClassesAxiom() {
        return doDefault();
    }

    /** @return value for SubClassOfAxiom */
    default Optional<T> visitSubClassOfAxiom() {
        return doDefault();
    }

    /** @return value for DisjointClassesAxiom */
    default Optional<T> visitDisjointClassesAxiom() {
        return doDefault();
    }

    /** @return value for DisjointUnionAxiom */
    default Optional<T> visitDisjointUnionAxiom() {
        return doDefault();
    }

    /** @return value for ClassAssertionAxiom */
    default Optional<T> visitClassAssertionAxiom() {
        return doDefault();
    }

    /** @return value for SameIndividualAxiom */
    default Optional<T> visitSameIndividualAxiom() {
        return doDefault();
    }

    /** @return value for DifferentIndividualsAxiom */
    default Optional<T> visitDifferentIndividualsAxiom() {
        return doDefault();
    }

    /** @return value for ObjectPropertyAssertionAxiom */
    default Optional<T> visitObjectPropertyAssertionAxiom() {
        return doDefault();
    }

    /** @return value for NegativeObjectPropertyAssertionAxiom */
    default Optional<T> visitNegativeObjectPropertyAssertionAxiom() {
        return doDefault();
    }

    /** @return value for DataPropertyAssertionAxiom */
    default Optional<T> visitDataPropertyAssertionAxiom() {
        return doDefault();
    }

    /** @return value for NegativeDataPropertyAssertionAxiom */
    default Optional<T> visitNegativeDataPropertyAssertionAxiom() {
        return doDefault();
    }

    /** @return value for EquivalentObjectPropertiesAxiom */
    default Optional<T> visitEquivalentObjectPropertiesAxiom() {
        return doDefault();
    }

    /** @return value for SubObjectPropertyOfAxiom */
    default Optional<T> visitSubObjectPropertyOfAxiom() {
        return doDefault();
    }

    /** @return value for InverseObjectPropertiesAxiom */
    default Optional<T> visitInverseObjectPropertiesAxiom() {
        return doDefault();
    }

    /** @return value for FunctionalObjectPropertyAxiom */
    default Optional<T> visitFunctionalObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for InverseFunctionalObjectPropertyAxiom */
    default Optional<T> visitInverseFunctionalObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for SymmetricObjectPropertyAxiom */
    default Optional<T> visitSymmetricObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for AsymmetricObjectPropertyAxiom */
    default Optional<T> visitAsymmetricObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for TransitiveObjectPropertyAxiom */
    default Optional<T> visitTransitiveObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for ReflexiveObjectPropertyAxiom */
    default Optional<T> visitReflexiveObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for IrreflexiveObjectPropertyAxiom */
    default Optional<T> visitIrreflexiveObjectPropertyAxiom() {
        return doDefault();
    }

    /** @return value for ObjectPropertyDomainAxiom */
    default Optional<T> visitObjectPropertyDomainAxiom() {
        return doDefault();
    }

    /** @return value for ObjectPropertyRangeAxiom */
    default Optional<T> visitObjectPropertyRangeAxiom() {
        return doDefault();
    }

    /** @return value for DisjointObjectPropertiesAxiom */
    default Optional<T> visitDisjointObjectPropertiesAxiom() {
        return doDefault();
    }

    /** @return value for SubPropertyChainOfAxiom */
    default Optional<T> visitSubPropertyChainOfAxiom() {
        return doDefault();
    }

    /** @return value for EquivalentDataPropertiesAxiom */
    default Optional<T> visitEquivalentDataPropertiesAxiom() {
        return doDefault();
    }

    /** @return value for SubDataPropertyOfAxiom */
    default Optional<T> visitSubDataPropertyOfAxiom() {
        return doDefault();
    }

    /** @return value for FunctionalDataPropertyAxiom */
    default Optional<T> visitFunctionalDataPropertyAxiom() {
        return doDefault();
    }

    /** @return value for DataPropertyDomainAxiom */
    default Optional<T> visitDataPropertyDomainAxiom() {
        return doDefault();
    }

    /** @return value for DataPropertyRangeAxiom */
    default Optional<T> visitDataPropertyRangeAxiom() {
        return doDefault();
    }

    /** @return value for DisjointDataPropertiesAxiom */
    default Optional<T> visitDisjointDataPropertiesAxiom() {
        return doDefault();
    }

    /** @return value for DatatypeDefinitionAxiom */
    default Optional<T> visitDatatypeDefinitionAxiom() {
        return doDefault();
    }

    /** @return value for HasKeyAxiom */
    default Optional<T> visitHasKeyAxiom() {
        return doDefault();
    }

    /** @return value for SWRLRule */
    default Optional<T> visitSWRLRule() {
        return doDefault();
    }

    /** @return value for AnnotationAssertionAxiom */
    default Optional<T> visitAnnotationAssertionAxiom() {
        return doDefault();
    }

    /** @return value for SubAnnotationPropertyOfAxiom */
    default Optional<T> visitSubAnnotationPropertyOfAxiom() {
        return doDefault();
    }

    /** @return value for AnnotationPropertyRangeAxiom */
    default Optional<T> visitAnnotationPropertyRangeAxiom() {
        return doDefault();
    }

    /** @return value for AnnotationPropertyDomainAxiom */
    default Optional<T> visitAnnotationPropertyDomainAxiom() {
        return doDefault();
    }
}
