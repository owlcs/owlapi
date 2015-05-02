package org.semanticweb.owlapi.api.test.baseclasses;

import java.util.Iterator;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

class LiteralFoldingEqualityTester {

    public static boolean equalAxiom(OWLAxiom a, OWLAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLAsymmetricObjectPropertyAxiom
        && b instanceof OWLAsymmetricObjectPropertyAxiom) {
            return equals((OWLAsymmetricObjectPropertyAxiom) a,
            (OWLAsymmetricObjectPropertyAxiom) b);
        } else if (a instanceof OWLClassAssertionAxiom
        && b instanceof OWLClassAssertionAxiom) {
            return equals((OWLClassAssertionAxiom) a,
            (OWLClassAssertionAxiom) b);
        } else if (a instanceof OWLDataPropertyAssertionAxiom
        && b instanceof OWLDataPropertyAssertionAxiom) {
            return equals((OWLDataPropertyAssertionAxiom) a,
            (OWLDataPropertyAssertionAxiom) b);
        } else if (a instanceof OWLDataPropertyDomainAxiom
        && b instanceof OWLDataPropertyDomainAxiom) {
            return equals((OWLDataPropertyDomainAxiom) a,
            (OWLDataPropertyDomainAxiom) b);
        } else if (a instanceof OWLDataPropertyRangeAxiom
        && b instanceof OWLDataPropertyRangeAxiom) {
            return equals((OWLDataPropertyRangeAxiom) a,
            (OWLDataPropertyRangeAxiom) b);
        } else if (a instanceof OWLDeclarationAxiom
        && b instanceof OWLDeclarationAxiom) {
            return equals((OWLDeclarationAxiom) a, (OWLDeclarationAxiom) b);
        } else if (a instanceof OWLDifferentIndividualsAxiom
        && b instanceof OWLDifferentIndividualsAxiom) {
            return equals((OWLDifferentIndividualsAxiom) a,
            (OWLDifferentIndividualsAxiom) b);
        } else if (a instanceof OWLDisjointClassesAxiom
        && b instanceof OWLDisjointClassesAxiom) {
            return equals((OWLDisjointClassesAxiom) a,
            (OWLDisjointClassesAxiom) b);
        } else if (a instanceof OWLDisjointDataPropertiesAxiom
        && b instanceof OWLDisjointDataPropertiesAxiom) {
            return equals((OWLDisjointDataPropertiesAxiom) a,
            (OWLDisjointDataPropertiesAxiom) b);
        } else if (a instanceof OWLDisjointObjectPropertiesAxiom
        && b instanceof OWLDisjointObjectPropertiesAxiom) {
            return equals((OWLDisjointObjectPropertiesAxiom) a,
            (OWLDisjointObjectPropertiesAxiom) b);
        } else if (a instanceof OWLDisjointUnionAxiom
        && b instanceof OWLDisjointUnionAxiom) {
            return equals((OWLDisjointUnionAxiom) a, (OWLDisjointUnionAxiom) b);
        } else if (a instanceof OWLEquivalentClassesAxiom
        && b instanceof OWLEquivalentClassesAxiom) {
            return equals((OWLEquivalentClassesAxiom) a,
            (OWLEquivalentClassesAxiom) b);
        } else if (a instanceof OWLEquivalentDataPropertiesAxiom
        && b instanceof OWLEquivalentDataPropertiesAxiom) {
            return equals((OWLEquivalentDataPropertiesAxiom) a,
            (OWLEquivalentDataPropertiesAxiom) b);
        } else if (a instanceof OWLEquivalentObjectPropertiesAxiom
        && b instanceof OWLEquivalentObjectPropertiesAxiom) {
            return equals((OWLEquivalentObjectPropertiesAxiom) a,
            (OWLEquivalentObjectPropertiesAxiom) b);
        } else if (a instanceof OWLFunctionalDataPropertyAxiom
        && b instanceof OWLFunctionalDataPropertyAxiom) {
            return equals((OWLFunctionalDataPropertyAxiom) a,
            (OWLFunctionalDataPropertyAxiom) b);
        } else if (a instanceof OWLFunctionalObjectPropertyAxiom
        && b instanceof OWLFunctionalObjectPropertyAxiom) {
            return equals((OWLFunctionalObjectPropertyAxiom) a,
            (OWLFunctionalObjectPropertyAxiom) b);
        } else if (a instanceof OWLHasKeyAxiom && b instanceof OWLHasKeyAxiom) {
            return equals((OWLHasKeyAxiom) a, (OWLHasKeyAxiom) b);
        } else if (a instanceof OWLInverseFunctionalObjectPropertyAxiom
        && b instanceof OWLInverseFunctionalObjectPropertyAxiom) {
            return equals((OWLInverseFunctionalObjectPropertyAxiom) a,
            (OWLInverseFunctionalObjectPropertyAxiom) b);
        } else if (a instanceof OWLInverseObjectPropertiesAxiom
        && b instanceof OWLInverseObjectPropertiesAxiom) {
            return equals((OWLInverseObjectPropertiesAxiom) a,
            (OWLInverseObjectPropertiesAxiom) b);
        } else if (a instanceof OWLIrreflexiveObjectPropertyAxiom
        && b instanceof OWLIrreflexiveObjectPropertyAxiom) {
            return equals((OWLIrreflexiveObjectPropertyAxiom) a,
            (OWLIrreflexiveObjectPropertyAxiom) b);
        } else if (a instanceof OWLNegativeDataPropertyAssertionAxiom
        && b instanceof OWLNegativeDataPropertyAssertionAxiom) {
            return equals((OWLNegativeDataPropertyAssertionAxiom) a,
            (OWLNegativeDataPropertyAssertionAxiom) b);
        } else if (a instanceof OWLNegativeObjectPropertyAssertionAxiom
        && b instanceof OWLNegativeObjectPropertyAssertionAxiom) {
            return equals((OWLNegativeObjectPropertyAssertionAxiom) a,
            (OWLNegativeObjectPropertyAssertionAxiom) b);
        } else if (a instanceof OWLObjectPropertyAssertionAxiom
        && b instanceof OWLObjectPropertyAssertionAxiom) {
            return equals((OWLObjectPropertyAssertionAxiom) a,
            (OWLObjectPropertyAssertionAxiom) b);
        } else if (a instanceof OWLSubPropertyChainOfAxiom
        && b instanceof OWLSubPropertyChainOfAxiom) {
            return equals((OWLSubPropertyChainOfAxiom) a,
            (OWLSubPropertyChainOfAxiom) b);
        } else if (a instanceof OWLObjectPropertyDomainAxiom
        && b instanceof OWLObjectPropertyDomainAxiom) {
            return equals((OWLObjectPropertyDomainAxiom) a,
            (OWLObjectPropertyDomainAxiom) b);
        } else if (a instanceof OWLObjectPropertyRangeAxiom
        && b instanceof OWLObjectPropertyRangeAxiom) {
            return equals((OWLObjectPropertyRangeAxiom) a,
            (OWLObjectPropertyRangeAxiom) b);
        } else if (a instanceof OWLReflexiveObjectPropertyAxiom
        && b instanceof OWLReflexiveObjectPropertyAxiom) {
            return equals((OWLReflexiveObjectPropertyAxiom) a,
            (OWLReflexiveObjectPropertyAxiom) b);
        } else if (a instanceof OWLSameIndividualAxiom
        && b instanceof OWLSameIndividualAxiom) {
            return equals((OWLSameIndividualAxiom) a,
            (OWLSameIndividualAxiom) b);
        } else if (a instanceof OWLSubClassOfAxiom
        && b instanceof OWLSubClassOfAxiom) {
            return equals((OWLSubClassOfAxiom) a, (OWLSubClassOfAxiom) b);
        } else if (a instanceof OWLSubDataPropertyOfAxiom
        && b instanceof OWLSubDataPropertyOfAxiom) {
            return equals((OWLSubDataPropertyOfAxiom) a,
            (OWLSubDataPropertyOfAxiom) b);
        } else if (a instanceof OWLSubObjectPropertyOfAxiom
        && b instanceof OWLSubObjectPropertyOfAxiom) {
            return equals((OWLSubObjectPropertyOfAxiom) a,
            (OWLSubObjectPropertyOfAxiom) b);
        } else if (a instanceof OWLSymmetricObjectPropertyAxiom
        && b instanceof OWLSymmetricObjectPropertyAxiom) {
            return equals((OWLSymmetricObjectPropertyAxiom) a,
            (OWLSymmetricObjectPropertyAxiom) b);
        } else if (a instanceof OWLTransitiveObjectPropertyAxiom
        && b instanceof OWLTransitiveObjectPropertyAxiom) {
            return equals((OWLTransitiveObjectPropertyAxiom) a,
            (OWLTransitiveObjectPropertyAxiom) b);
        } else if (a instanceof OWLAnnotationAssertionAxiom
        && b instanceof OWLAnnotationAssertionAxiom) {
            return equals((OWLAnnotationAssertionAxiom) a,
            (OWLAnnotationAssertionAxiom) b);
        } else if (a instanceof OWLAnnotationPropertyDomainAxiom
        && b instanceof OWLAnnotationPropertyDomainAxiom) {
            return equals((OWLAnnotationPropertyDomainAxiom) a,
            (OWLAnnotationPropertyDomainAxiom) b);
        } else if (a instanceof OWLAnnotationPropertyRangeAxiom
        && b instanceof OWLAnnotationPropertyRangeAxiom) {
            return equals((OWLAnnotationPropertyRangeAxiom) a,
            (OWLAnnotationPropertyRangeAxiom) b);
        } else if (a instanceof OWLSubAnnotationPropertyOfAxiom
        && b instanceof OWLSubAnnotationPropertyOfAxiom) {
            return equals((OWLSubAnnotationPropertyOfAxiom) a,
            (OWLSubAnnotationPropertyOfAxiom) b);
        } else if (a instanceof OWLDatatypeDefinitionAxiom
        && b instanceof OWLDatatypeDefinitionAxiom) {
            return equals((OWLDatatypeDefinitionAxiom) a,
            (OWLDatatypeDefinitionAxiom) b);
        } else {
            return false;
        }
    }

    public static boolean equals(IRI a, IRI b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLLiteral a, OWLLiteral b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!a.getLiteral().equals(b.getLiteral())) {
            return false;
        }
        if (!a.getLang().equals(b.getLang())) {
            return false;
        }
        if (a.getDatatype().equals(b.getDatatype())) {
            return true;
        }
        if (a.getDatatype().isRDFPlainLiteral()) {
            if (b.getDatatype().isString() && a.getLang().equals("")) {
                return true;
            }
            if (b.getDatatype().getIRI().equals(OWLRDFVocabulary.RDF_LANG_STRING
            .getIRI()) && !a.getLang().equals("")) {
                return true;
            }
        }
        if (b.getDatatype().isRDFPlainLiteral()) {
            if (a.getDatatype().isString() && b.getLang().equals("")) {
                return true;
            }
            if (a.getDatatype().getIRI().equals(OWLRDFVocabulary.RDF_LANG_STRING
            .getIRI()) && !b.getLang().equals("")) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalLiterals(Iterator<? extends OWLLiteral> ai,
        Iterator<? extends OWLLiteral> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    /*
     * Entity like things
     */
    private static boolean equals(OWLEntity a, OWLEntity b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLClass && b instanceof OWLClass) {
            return equals((OWLClass) a, (OWLClass) b);
        } else if (a instanceof OWLAnnotationProperty
        && b instanceof OWLAnnotationProperty) {
            return equals((OWLAnnotationProperty) a, (OWLAnnotationProperty) b);
        } else if (a instanceof OWLDatatype && b instanceof OWLDatatype) {
            return equals((OWLDatatype) a, (OWLDatatype) b);
        } else if (a instanceof OWLObjectProperty
        && b instanceof OWLObjectProperty) {
            return equals((OWLObjectProperty) a, (OWLObjectProperty) b);
        } else if (a instanceof OWLDataProperty
        && b instanceof OWLDataProperty) {
            return equals((OWLDataProperty) a, (OWLDataProperty) b);
        } else if (a instanceof OWLNamedIndividual
        && b instanceof OWLNamedIndividual) {
            return equals((OWLNamedIndividual) a, (OWLNamedIndividual) b);
        } else {
            return false;
        }
    }

    private static boolean equals(OWLIndividual a, OWLIndividual b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLAnonymousIndividual
        && b instanceof OWLAnonymousIndividual) {
            return equals((OWLAnonymousIndividual) a,
            (OWLAnonymousIndividual) b);
        } else if (a instanceof OWLNamedIndividual
        && b instanceof OWLNamedIndividual) {
            return equals((OWLNamedIndividual) a, (OWLNamedIndividual) b);
        } else {
            return false;
        }
    }

    private static boolean equalIndividuals(
        Iterator<? extends OWLIndividual> ai,
        Iterator<? extends OWLIndividual> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(OWLAnonymousIndividual a,
        OWLAnonymousIndividual b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLNamedIndividual a, OWLNamedIndividual b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLClass a, OWLClass b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLDatatype a, OWLDatatype b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLObjectProperty a, OWLObjectProperty b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLObjectInverseOf a, OWLObjectInverseOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    private static boolean equals(OWLObjectPropertyExpression a,
        OWLObjectPropertyExpression b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLObjectProperty && b instanceof OWLObjectProperty) {
            return equals((OWLObjectProperty) a, (OWLObjectProperty) b);
        } else if (a instanceof OWLObjectInverseOf
        && b instanceof OWLObjectInverseOf) {
            return equals((OWLObjectInverseOf) a, (OWLObjectInverseOf) b);
        } else {
            return false;
        }
    }

    private static boolean equalObjectPropertyExpressions(
        Iterator<? extends OWLObjectPropertyExpression> ai,
        Iterator<? extends OWLObjectPropertyExpression> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(OWLDataProperty a, OWLDataProperty b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    private static boolean equals(OWLDataPropertyExpression a,
        OWLDataPropertyExpression b) {
        return equals(a.asOWLDataProperty(), b.asOWLDataProperty());
    }

    private static boolean equalDataPropertyExpressions(
        Iterator<? extends OWLDataPropertyExpression> ai,
        Iterator<? extends OWLDataPropertyExpression> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(OWLAnnotationProperty a,
        OWLAnnotationProperty b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /*
    * Data Ranges
    */
    private static boolean equals(OWLDataRange a, OWLDataRange b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLDataComplementOf
        && b instanceof OWLDataComplementOf) {
            return equals((OWLDataComplementOf) a, (OWLDataComplementOf) b);
        } else if (a instanceof OWLDataIntersectionOf
        && b instanceof OWLDataIntersectionOf) {
            return equals((OWLDataIntersectionOf) a, (OWLDataIntersectionOf) b);
        } else if (a instanceof OWLDataOneOf && b instanceof OWLDataOneOf) {
            return equals((OWLDataOneOf) a, (OWLDataOneOf) b);
        } else if (a instanceof OWLDatatype && b instanceof OWLDatatype) {
            return equals((OWLDatatype) a, (OWLDatatype) b);
        } else if (a instanceof OWLDatatypeRestriction
        && b instanceof OWLDatatypeRestriction) {
            return equals((OWLDatatypeRestriction) a,
            (OWLDatatypeRestriction) b);
        } else if (a instanceof OWLDataUnionOf && b instanceof OWLDataUnionOf) {
            return equals((OWLDataUnionOf) a, (OWLDataUnionOf) b);
        } else {
            return false;
        }
    }

    private static boolean equalDataRanges(Iterator<? extends OWLDataRange> ai,
        Iterator<? extends OWLDataRange> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(OWLDataComplementOf a, OWLDataComplementOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equals(a.getDataRange(), b.getDataRange());
    }

    public static boolean equals(OWLDataOneOf a, OWLDataOneOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equalLiterals(a.values().iterator(), b.values().iterator());
    }

    public static boolean equals(OWLDataUnionOf a, OWLDataUnionOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equalDataRanges(a.operands().iterator(), b.operands()
        .iterator());
    }

    private static boolean equals(OWLDataIntersectionOf a,
        OWLDataIntersectionOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equalDataRanges(a.operands().iterator(), b.operands()
        .iterator());
    }

    public static boolean equals(OWLDatatypeRestriction a,
        OWLDatatypeRestriction b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getDatatype(), b.getDatatype())) {
            return false;
        }
        return equalFacetRestrictions(a.facetRestrictions().iterator(), b
        .facetRestrictions().iterator());
    }

    public static boolean equals(OWLFacetRestriction a, OWLFacetRestriction b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getFacetValue(), b.getFacetValue())) {
            return false;
        }
        if (!equals(a.getFacet(), b.getFacet())) {
            return false;
        }
        return true;
    }

    public static boolean equalFacetRestrictions(
        Iterator<OWLFacetRestriction> ai, Iterator<OWLFacetRestriction> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    private static boolean equals(OWLFacet a, OWLFacet b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    /*
     *  Annotation related types
     */
    public static boolean equals(OWLAnnotation a, OWLAnnotation b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getValue(), b.getValue())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equalAnnotations(@Nonnull Iterator<OWLAnnotation> ai,
        @Nonnull Iterator<OWLAnnotation> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    private static boolean equals(OWLAnnotationSubject a,
        OWLAnnotationSubject b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof IRI && b instanceof IRI) {
            return equals((IRI) a, (IRI) b);
        } else if (a instanceof OWLAnonymousIndividual
        && b instanceof OWLAnonymousIndividual) {
            return equals((OWLAnonymousIndividual) a,
            (OWLAnonymousIndividual) b);
        } else {
            return false;
        }
    }

    public static boolean equals(OWLAnnotationValue a, OWLAnnotationValue b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.asAnonymousIndividual().isPresent() || a.asIRI().isPresent()) {
            return a.equals(b);
        } else if (a.asLiteral().isPresent() && b.asLiteral().isPresent()) {
            return equals(a.asLiteral().get(), b.asLiteral().get());
        } else {
            return false;
        }
    }

    /* 
     * Axioms
     */
    /* 
     *  Annotation related axioms
     */
    public static boolean equals(OWLAnnotationAssertionAxiom a,
        OWLAnnotationAssertionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getSubject(), b.getSubject())) {
            return false;
        }
        if (!equals(a.getValue(), b.getValue())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLSubAnnotationPropertyOfAxiom a,
        OWLSubAnnotationPropertyOfAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(OWLAnnotationPropertyDomainAxiom a,
        OWLAnnotationPropertyDomainAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!a.getProperty().equals(b.getProperty())) {
            return false;
        }
        if (!equals(a.getDomain(), b.getDomain())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLAnnotationPropertyRangeAxiom a,
        OWLAnnotationPropertyRangeAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!a.getProperty().equals(b.getProperty())) {
            return false;
        }
        if (!equals(a.getRange(), b.getRange())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Declaration Axiom
    public static boolean equals(OWLDeclarationAxiom a, OWLDeclarationAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getEntity(), b.getEntity())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Datatype Definition axiom
    public static boolean equals(OWLDatatypeDefinitionAxiom a,
        OWLDatatypeDefinitionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getDatatype(), b.getDatatype())) {
            return false;
        }
        if (!equals(a.getDataRange(), b.getDataRange())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Individual related Axioms
    public static boolean equals(OWLObjectPropertyAssertionAxiom a,
        OWLObjectPropertyAssertionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getSubject(), b.getSubject())) {
            return false;
        }
        if (!equals(a.getObject(), b.getObject())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLNegativeObjectPropertyAssertionAxiom a,
        OWLNegativeObjectPropertyAssertionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getSubject(), b.getSubject())) {
            return false;
        }
        if (!equals(a.getObject(), b.getObject())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDataPropertyAssertionAxiom a,
        OWLDataPropertyAssertionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSubject(), b.getSubject())) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getObject(), b.getObject())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLNegativeDataPropertyAssertionAxiom a,
        OWLNegativeDataPropertyAssertionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSubject(), b.getSubject())) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getObject(), b.getObject())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLClassAssertionAxiom a,
        OWLClassAssertionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getIndividual(), b.getIndividual())) {
            return false;
        }
        if (!equals(a.getClassExpression(), b.getClassExpression())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLSameIndividualAxiom a,
        OWLSameIndividualAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        Iterator<OWLIndividual> ai = a.individuals().iterator();
        Iterator<OWLIndividual> bi = b.individuals().iterator();
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDifferentIndividualsAxiom a,
        OWLDifferentIndividualsAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        Iterator<OWLIndividual> ai = a.individuals().iterator();
        Iterator<OWLIndividual> bi = b.individuals().iterator();
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Class Axioms
    public static boolean equals(OWLSubClassOfAxiom a, OWLSubClassOfAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSubClass(), b.getSubClass())) {
            return false;
        }
        if (!equals(a.getSuperClass(), b.getSuperClass())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLEquivalentClassesAxiom a,
        OWLEquivalentClassesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalClassExpressions(a.classExpressions().iterator(), b
        .classExpressions().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDisjointClassesAxiom a,
        OWLDisjointClassesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalClassExpressions(a.classExpressions().iterator(), b
        .classExpressions().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDisjointUnionAxiom a,
        OWLDisjointUnionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalClassExpressions(a.classExpressions().iterator(), b
        .classExpressions().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Property Axioms
    // Property characteristics
    public static boolean equals(OWLAsymmetricObjectPropertyAxiom a,
        OWLAsymmetricObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLReflexiveObjectPropertyAxiom a,
        OWLReflexiveObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLIrreflexiveObjectPropertyAxiom a,
        OWLIrreflexiveObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLFunctionalObjectPropertyAxiom a,
        OWLFunctionalObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLInverseFunctionalObjectPropertyAxiom a,
        OWLInverseFunctionalObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLSymmetricObjectPropertyAxiom a,
        OWLSymmetricObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLTransitiveObjectPropertyAxiom a,
        OWLTransitiveObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLFunctionalDataPropertyAxiom a,
        OWLFunctionalDataPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Object Property Axioms
    public static boolean equals(OWLObjectPropertyDomainAxiom a,
        OWLObjectPropertyDomainAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getDomain(), b.getDomain())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLObjectPropertyRangeAxiom a,
        OWLObjectPropertyRangeAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getRange(), b.getRange())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLEquivalentObjectPropertiesAxiom a,
        OWLEquivalentObjectPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalObjectPropertyExpressions(a.properties().iterator(), b
        .properties().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDisjointObjectPropertiesAxiom a,
        OWLDisjointObjectPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalObjectPropertyExpressions(a.properties().iterator(), b
        .properties().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLSubObjectPropertyOfAxiom a,
        OWLSubObjectPropertyOfAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSubProperty(), b.getSubProperty())) {
            return false;
        }
        if (!equals(a.getSuperProperty(), b.getSuperProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLSubPropertyChainOfAxiom a,
        OWLSubPropertyChainOfAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSuperProperty(), b.getSuperProperty())) {
            return false;
        }
        if (!equalObjectPropertyExpressions(a.getPropertyChain().iterator(), b
        .getPropertyChain().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLInverseObjectPropertiesAxiom a,
        OWLInverseObjectPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    // Data Property Axioms
    public static boolean equals(OWLDataPropertyDomainAxiom a,
        OWLDataPropertyDomainAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getDomain(), b.getDomain())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDataPropertyRangeAxiom a,
        OWLDataPropertyRangeAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (!equals(a.getRange(), b.getRange())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLDisjointDataPropertiesAxiom a,
        OWLDisjointDataPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalDataPropertyExpressions(a.properties().iterator(), b
        .properties().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLEquivalentDataPropertiesAxiom a,
        OWLEquivalentDataPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalDataPropertyExpressions(a.properties().iterator(), b
        .properties().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    public static boolean equals(OWLSubDataPropertyOfAxiom a,
        OWLSubDataPropertyOfAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSubProperty(), b.getSubProperty())) {
            return false;
        }
        if (!equals(a.getSuperProperty(), b.getSuperProperty())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    // Has Key Axiom
    public static boolean equals(OWLHasKeyAxiom a, OWLHasKeyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equalDataPropertyExpressions(a.dataPropertyExpressions()
        .iterator(), b.dataPropertyExpressions().iterator())) {
            return false;
        }
        if (!equalObjectPropertyExpressions(a.objectPropertyExpressions()
        .iterator(), b.objectPropertyExpressions().iterator())) {
            return false;
        }
        return equalAnnotations(a.annotations().iterator(), b.annotations()
        .iterator());
    }

    /*
     *  Class Expressions
     */
    private static boolean equals(OWLClassExpression a, OWLClassExpression b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLClass && b instanceof OWLClass) {
            return equals((OWLClass) a, (OWLClass) b);
        } else if (a instanceof OWLObjectIntersectionOf
        && b instanceof OWLObjectIntersectionOf) {
            return equals((OWLObjectIntersectionOf) a,
            (OWLObjectIntersectionOf) b);
        } else if (a instanceof OWLObjectUnionOf
        && b instanceof OWLObjectUnionOf) {
            return equals((OWLObjectUnionOf) a, (OWLObjectUnionOf) b);
        } else if (a instanceof OWLObjectComplementOf
        && b instanceof OWLObjectComplementOf) {
            return equals((OWLObjectComplementOf) a, (OWLObjectComplementOf) b);
        } else if (a instanceof OWLObjectSomeValuesFrom
        && b instanceof OWLObjectSomeValuesFrom) {
            return equals((OWLObjectSomeValuesFrom) a,
            (OWLObjectSomeValuesFrom) b);
        } else if (a instanceof OWLObjectAllValuesFrom
        && b instanceof OWLObjectAllValuesFrom) {
            return equals((OWLObjectAllValuesFrom) a,
            (OWLObjectAllValuesFrom) b);
        } else if (a instanceof OWLObjectHasValue
        && b instanceof OWLObjectHasValue) {
            return equals((OWLObjectHasValue) a, (OWLObjectHasValue) b);
        } else if (a instanceof OWLObjectMinCardinality
        && b instanceof OWLObjectMinCardinality) {
            return equals((OWLObjectMinCardinality) a,
            (OWLObjectMinCardinality) b);
        } else if (a instanceof OWLObjectExactCardinality
        && b instanceof OWLObjectExactCardinality) {
            return equals((OWLObjectExactCardinality) a,
            (OWLObjectExactCardinality) b);
        } else if (a instanceof OWLObjectMaxCardinality
        && b instanceof OWLObjectMaxCardinality) {
            return equals((OWLObjectMaxCardinality) a,
            (OWLObjectMaxCardinality) b);
        } else if (a instanceof OWLObjectHasSelf
        && b instanceof OWLObjectHasSelf) {
            return equals((OWLObjectHasSelf) a, (OWLObjectHasSelf) b);
        } else if (a instanceof OWLObjectOneOf && b instanceof OWLObjectOneOf) {
            return equals((OWLObjectOneOf) a, (OWLObjectOneOf) b);
        } else if (a instanceof OWLDataSomeValuesFrom
        && b instanceof OWLDataSomeValuesFrom) {
            return equals((OWLDataSomeValuesFrom) a, (OWLDataSomeValuesFrom) b);
        } else if (a instanceof OWLDataAllValuesFrom
        && b instanceof OWLDataAllValuesFrom) {
            return equals((OWLDataAllValuesFrom) a, (OWLDataAllValuesFrom) b);
        } else if (a instanceof OWLDataHasValue
        && b instanceof OWLDataHasValue) {
            return equals((OWLDataHasValue) a, (OWLDataHasValue) b);
        } else if (a instanceof OWLDataMinCardinality
        && b instanceof OWLDataMinCardinality) {
            return equals((OWLDataMinCardinality) a, (OWLDataMinCardinality) b);
        } else if (a instanceof OWLDataExactCardinality
        && b instanceof OWLDataExactCardinality) {
            return equals((OWLDataExactCardinality) a,
            (OWLDataExactCardinality) b);
        } else if (a instanceof OWLDataMaxCardinality
        && b instanceof OWLDataMaxCardinality) {
            return equals((OWLDataMaxCardinality) a, (OWLDataMaxCardinality) b);
        } else {
            return false;
        }
    }

    private static boolean equalClassExpressions(
        Iterator<? extends OWLClassExpression> ai,
        Iterator<? extends OWLClassExpression> bi) {
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(OWLObjectIntersectionOf a,
        OWLObjectIntersectionOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equalClassExpressions(a.operands().iterator(), b.operands()
        .iterator());
    }

    public static boolean equals(OWLObjectUnionOf a, OWLObjectUnionOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equalClassExpressions(a.operands().iterator(), b.operands()
        .iterator());
    }

    public static boolean equals(OWLObjectComplementOf a,
        OWLObjectComplementOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equals(a.getOperand(), b.getOperand());
    }

    public static boolean equals(OWLObjectSomeValuesFrom a,
        OWLObjectSomeValuesFrom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLObjectAllValuesFrom a,
        OWLObjectAllValuesFrom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLObjectHasValue a, OWLObjectHasValue b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLObjectMinCardinality a,
        OWLObjectMinCardinality b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (a.getCardinality() != b.getCardinality()) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLObjectExactCardinality a,
        OWLObjectExactCardinality b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (a.getCardinality() != b.getCardinality()) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLObjectMaxCardinality a,
        OWLObjectMaxCardinality b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (a.getCardinality() != b.getCardinality()) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLObjectHasSelf a, OWLObjectHasSelf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return true;
    }

    public static boolean equals(OWLObjectOneOf a, OWLObjectOneOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equalIndividuals(a.individuals().iterator(), b.individuals()
        .iterator());
    }

    public static boolean equals(OWLDataSomeValuesFrom a,
        OWLDataSomeValuesFrom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLDataAllValuesFrom a,
        OWLDataAllValuesFrom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLDataHasValue a, OWLDataHasValue b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLDataMinCardinality a,
        OWLDataMinCardinality b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (a.getCardinality() != b.getCardinality()) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLDataExactCardinality a,
        OWLDataExactCardinality b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (a.getCardinality() != b.getCardinality()) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    public static boolean equals(OWLDataMaxCardinality a,
        OWLDataMaxCardinality b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        if (a.getCardinality() != b.getCardinality()) {
            return false;
        }
        return equals(a.getFiller(), b.getFiller());
    }

    /*
     *  SWRL 
     */
    public static boolean equals(SWRLRule a, SWRLRule b) {
        System.err.println("SWRL Rule literal-folding-equality-not-done");
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLClassAtom a, SWRLClassAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLDataRangeAtom a, SWRLDataRangeAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLObjectPropertyAtom a,
        SWRLObjectPropertyAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLDataPropertyAtom a,
        SWRLDataPropertyAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLBuiltInAtom a, SWRLBuiltInAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLVariable a, SWRLVariable b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLIndividualArgument a,
        SWRLIndividualArgument b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLLiteralArgument a, SWRLLiteralArgument b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLSameIndividualAtom a,
        SWRLSameIndividualAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLDifferentIndividualsAtom a,
        SWRLDifferentIndividualsAtom b) {
        throw new AbstractMethodError();
    }
}
