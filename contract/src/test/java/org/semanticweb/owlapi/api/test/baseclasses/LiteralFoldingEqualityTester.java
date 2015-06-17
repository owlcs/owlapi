package org.semanticweb.owlapi.api.test.baseclasses;

import java.util.Iterator;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

class LiteralFoldingEqualityTester {

    public static boolean equalAxiom(@Nullable OWLAxiom a, @Nullable OWLAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLAsymmetricObjectPropertyAxiom && b instanceof OWLAsymmetricObjectPropertyAxiom) {
            return equals((OWLAsymmetricObjectPropertyAxiom) a, (OWLAsymmetricObjectPropertyAxiom) b);
        } else if (a instanceof OWLClassAssertionAxiom && b instanceof OWLClassAssertionAxiom) {
            return equals((OWLClassAssertionAxiom) a, (OWLClassAssertionAxiom) b);
        } else if (a instanceof OWLDataPropertyAssertionAxiom && b instanceof OWLDataPropertyAssertionAxiom) {
            return equals((OWLDataPropertyAssertionAxiom) a, (OWLDataPropertyAssertionAxiom) b);
        } else if (a instanceof OWLDataPropertyDomainAxiom && b instanceof OWLDataPropertyDomainAxiom) {
            return equals((OWLDataPropertyDomainAxiom) a, (OWLDataPropertyDomainAxiom) b);
        } else if (a instanceof OWLDataPropertyRangeAxiom && b instanceof OWLDataPropertyRangeAxiom) {
            return equals((OWLDataPropertyRangeAxiom) a, (OWLDataPropertyRangeAxiom) b);
        } else if (a instanceof OWLDeclarationAxiom && b instanceof OWLDeclarationAxiom) {
            return equals((OWLDeclarationAxiom) a, (OWLDeclarationAxiom) b);
        } else if (a instanceof OWLDifferentIndividualsAxiom && b instanceof OWLDifferentIndividualsAxiom) {
            return equals((OWLDifferentIndividualsAxiom) a, (OWLDifferentIndividualsAxiom) b);
        } else if (a instanceof OWLDisjointClassesAxiom && b instanceof OWLDisjointClassesAxiom) {
            return equals((OWLDisjointClassesAxiom) a, (OWLDisjointClassesAxiom) b);
        } else if (a instanceof OWLDisjointDataPropertiesAxiom && b instanceof OWLDisjointDataPropertiesAxiom) {
            return equals((OWLDisjointDataPropertiesAxiom) a, (OWLDisjointDataPropertiesAxiom) b);
        } else if (a instanceof OWLDisjointObjectPropertiesAxiom && b instanceof OWLDisjointObjectPropertiesAxiom) {
            return equals((OWLDisjointObjectPropertiesAxiom) a, (OWLDisjointObjectPropertiesAxiom) b);
        } else if (a instanceof OWLDisjointUnionAxiom && b instanceof OWLDisjointUnionAxiom) {
            return equals((OWLDisjointUnionAxiom) a, (OWLDisjointUnionAxiom) b);
        } else if (a instanceof OWLEquivalentClassesAxiom && b instanceof OWLEquivalentClassesAxiom) {
            return equals((OWLEquivalentClassesAxiom) a, (OWLEquivalentClassesAxiom) b);
        } else if (a instanceof OWLEquivalentDataPropertiesAxiom && b instanceof OWLEquivalentDataPropertiesAxiom) {
            return equals((OWLEquivalentDataPropertiesAxiom) a, (OWLEquivalentDataPropertiesAxiom) b);
        } else if (a instanceof OWLEquivalentObjectPropertiesAxiom && b instanceof OWLEquivalentObjectPropertiesAxiom) {
            return equals((OWLEquivalentObjectPropertiesAxiom) a, (OWLEquivalentObjectPropertiesAxiom) b);
        } else if (a instanceof OWLFunctionalDataPropertyAxiom && b instanceof OWLFunctionalDataPropertyAxiom) {
            return equals((OWLFunctionalDataPropertyAxiom) a, (OWLFunctionalDataPropertyAxiom) b);
        } else if (a instanceof OWLFunctionalObjectPropertyAxiom && b instanceof OWLFunctionalObjectPropertyAxiom) {
            return equals((OWLFunctionalObjectPropertyAxiom) a, (OWLFunctionalObjectPropertyAxiom) b);
        } else if (a instanceof OWLHasKeyAxiom && b instanceof OWLHasKeyAxiom) {
            return equals((OWLHasKeyAxiom) a, (OWLHasKeyAxiom) b);
        } else if (a instanceof OWLInverseFunctionalObjectPropertyAxiom
            && b instanceof OWLInverseFunctionalObjectPropertyAxiom) {
            return equals((OWLInverseFunctionalObjectPropertyAxiom) a, (OWLInverseFunctionalObjectPropertyAxiom) b);
        } else if (a instanceof OWLInverseObjectPropertiesAxiom && b instanceof OWLInverseObjectPropertiesAxiom) {
            return equals((OWLInverseObjectPropertiesAxiom) a, (OWLInverseObjectPropertiesAxiom) b);
        } else if (a instanceof OWLIrreflexiveObjectPropertyAxiom && b instanceof OWLIrreflexiveObjectPropertyAxiom) {
            return equals((OWLIrreflexiveObjectPropertyAxiom) a, (OWLIrreflexiveObjectPropertyAxiom) b);
        } else if (a instanceof OWLNegativeDataPropertyAssertionAxiom
            && b instanceof OWLNegativeDataPropertyAssertionAxiom) {
            return equals((OWLNegativeDataPropertyAssertionAxiom) a, (OWLNegativeDataPropertyAssertionAxiom) b);
        } else if (a instanceof OWLNegativeObjectPropertyAssertionAxiom
            && b instanceof OWLNegativeObjectPropertyAssertionAxiom) {
            return equals((OWLNegativeObjectPropertyAssertionAxiom) a, (OWLNegativeObjectPropertyAssertionAxiom) b);
        } else if (a instanceof OWLObjectPropertyAssertionAxiom && b instanceof OWLObjectPropertyAssertionAxiom) {
            return equals((OWLObjectPropertyAssertionAxiom) a, (OWLObjectPropertyAssertionAxiom) b);
        } else if (a instanceof OWLSubPropertyChainOfAxiom && b instanceof OWLSubPropertyChainOfAxiom) {
            return equals((OWLSubPropertyChainOfAxiom) a, (OWLSubPropertyChainOfAxiom) b);
        } else if (a instanceof OWLObjectPropertyDomainAxiom && b instanceof OWLObjectPropertyDomainAxiom) {
            return equals((OWLObjectPropertyDomainAxiom) a, (OWLObjectPropertyDomainAxiom) b);
        } else if (a instanceof OWLObjectPropertyRangeAxiom && b instanceof OWLObjectPropertyRangeAxiom) {
            return equals((OWLObjectPropertyRangeAxiom) a, (OWLObjectPropertyRangeAxiom) b);
        } else if (a instanceof OWLReflexiveObjectPropertyAxiom && b instanceof OWLReflexiveObjectPropertyAxiom) {
            return equals((OWLReflexiveObjectPropertyAxiom) a, (OWLReflexiveObjectPropertyAxiom) b);
        } else if (a instanceof OWLSameIndividualAxiom && b instanceof OWLSameIndividualAxiom) {
            return equals((OWLSameIndividualAxiom) a, (OWLSameIndividualAxiom) b);
        } else if (a instanceof OWLSubClassOfAxiom && b instanceof OWLSubClassOfAxiom) {
            return equals((OWLSubClassOfAxiom) a, (OWLSubClassOfAxiom) b);
        } else if (a instanceof OWLSubDataPropertyOfAxiom && b instanceof OWLSubDataPropertyOfAxiom) {
            return equals((OWLSubDataPropertyOfAxiom) a, (OWLSubDataPropertyOfAxiom) b);
        } else if (a instanceof OWLSubObjectPropertyOfAxiom && b instanceof OWLSubObjectPropertyOfAxiom) {
            return equals((OWLSubObjectPropertyOfAxiom) a, (OWLSubObjectPropertyOfAxiom) b);
        } else if (a instanceof OWLSymmetricObjectPropertyAxiom && b instanceof OWLSymmetricObjectPropertyAxiom) {
            return equals((OWLSymmetricObjectPropertyAxiom) a, (OWLSymmetricObjectPropertyAxiom) b);
        } else if (a instanceof OWLTransitiveObjectPropertyAxiom && b instanceof OWLTransitiveObjectPropertyAxiom) {
            return equals((OWLTransitiveObjectPropertyAxiom) a, (OWLTransitiveObjectPropertyAxiom) b);
        } else if (a instanceof OWLAnnotationAssertionAxiom && b instanceof OWLAnnotationAssertionAxiom) {
            return equals((OWLAnnotationAssertionAxiom) a, (OWLAnnotationAssertionAxiom) b);
        } else if (a instanceof OWLAnnotationPropertyDomainAxiom && b instanceof OWLAnnotationPropertyDomainAxiom) {
            return equals((OWLAnnotationPropertyDomainAxiom) a, (OWLAnnotationPropertyDomainAxiom) b);
        } else if (a instanceof OWLAnnotationPropertyRangeAxiom && b instanceof OWLAnnotationPropertyRangeAxiom) {
            return equals((OWLAnnotationPropertyRangeAxiom) a, (OWLAnnotationPropertyRangeAxiom) b);
        } else if (a instanceof OWLSubAnnotationPropertyOfAxiom && b instanceof OWLSubAnnotationPropertyOfAxiom) {
            return equals(a, b);
        } else if (a instanceof OWLDatatypeDefinitionAxiom && b instanceof OWLDatatypeDefinitionAxiom) {
            return equals((OWLDatatypeDefinitionAxiom) a, (OWLDatatypeDefinitionAxiom) b);
        } else {
            return false;
        }
    }

    public static boolean equals(@Nullable IRI a, @Nullable IRI b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    public static boolean equals(@Nullable OWLLiteral a, @Nullable OWLLiteral b) {
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
            if (b.getDatatype().getIRI().equals(OWLRDFVocabulary.RDF_LANG_STRING.getIRI()) && !a.getLang().equals("")) {
                return true;
            }
        }
        if (b.getDatatype().isRDFPlainLiteral()) {
            if (a.getDatatype().isString() && b.getLang().equals("")) {
                return true;
            }
            if (a.getDatatype().getIRI().equals(OWLRDFVocabulary.RDF_LANG_STRING.getIRI()) && !b.getLang().equals("")) {
                return true;
            }
        }
        return false;
    }

    /*
     * Entity like things
     */
    private static boolean equals(@Nullable OWLEntity a, @Nullable OWLEntity b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLClass && b instanceof OWLClass) {
            return equals(a.getIRI(), b.getIRI());
        } else if (a instanceof OWLAnnotationProperty && b instanceof OWLAnnotationProperty) {
            return equals(a.getIRI(), b.getIRI());
        } else if (a instanceof OWLDatatype && b instanceof OWLDatatype) {
            return equals(a.getIRI(), b.getIRI());
        } else if (a.isOWLObjectProperty() && b.isOWLObjectProperty()) {
            return equals(a.getIRI(), b.getIRI());
        } else if (a.isOWLDataProperty() && b.isOWLDataProperty()) {
            return equals(a.getIRI(), b.getIRI());
        } else if (a instanceof OWLNamedIndividual && b instanceof OWLNamedIndividual) {
            return equals(a.getIRI(), b.getIRI());
        } else {
            return false;
        }
    }

    private static boolean equals(@Nullable OWLIndividual a, @Nullable OWLIndividual b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    private static boolean equal(Stream<?> sai, Stream<?> sbi) {
        Iterator<?> ai = sai.iterator();
        Iterator<?> bi = sbi.iterator();
        while (ai.hasNext()) {
            if (!equals(ai.next(), bi.next())) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    private static boolean equals(@Nullable OWLObjectPropertyExpression a, @Nullable OWLObjectPropertyExpression b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.isOWLObjectProperty() && b.isOWLObjectProperty()) {
            return equals(a.asOWLObjectProperty().getIRI(), b.asOWLObjectProperty().getIRI());
        } else if (a instanceof OWLObjectInverseOf && b instanceof OWLObjectInverseOf) {
            return equals(((OWLObjectInverseOf) a).getInverse(), ((OWLObjectInverseOf) b).getInverse());
        } else {
            return false;
        }
    }

    private static boolean equals(OWLDataPropertyExpression a, OWLDataPropertyExpression b) {
        return equals(a.asOWLDataProperty().getIRI(), b.asOWLDataProperty().getIRI());
    }

    /*
    * Data Ranges
    */
    private static boolean equals(@Nullable OWLDataRange a, @Nullable OWLDataRange b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof OWLDataComplementOf && b instanceof OWLDataComplementOf) {
            return equals((OWLDataComplementOf) a, (OWLDataComplementOf) b);
        } else if (a instanceof OWLDataIntersectionOf && b instanceof OWLDataIntersectionOf) {
            return equals((OWLDataIntersectionOf) a, (OWLDataIntersectionOf) b);
        } else if (a instanceof OWLDataOneOf && b instanceof OWLDataOneOf) {
            return equals((OWLDataOneOf) a, (OWLDataOneOf) b);
        } else if (a instanceof OWLDatatype && b instanceof OWLDatatype) {
            return equals(((OWLEntity) a).getIRI(), ((OWLEntity) b).getIRI());
        } else if (a instanceof OWLDatatypeRestriction && b instanceof OWLDatatypeRestriction) {
            return equals((OWLDatatypeRestriction) a, (OWLDatatypeRestriction) b);
        } else if (a instanceof OWLDataUnionOf && b instanceof OWLDataUnionOf) {
            return equals((OWLDataUnionOf) a, (OWLDataUnionOf) b);
        } else {
            return false;
        }
    }

    public static boolean equals(@Nullable OWLDataComplementOf a, @Nullable OWLDataComplementOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equals(a.getDataRange(), b.getDataRange());
    }

    public static boolean equals(@Nullable OWLDataOneOf a, @Nullable OWLDataOneOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equal(a.values(), b.values());
    }

    public static boolean equals(@Nullable OWLDataUnionOf a, @Nullable OWLDataUnionOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equal(a.operands(), b.operands());
    }

    private static boolean equals(@Nullable OWLDataIntersectionOf a, @Nullable OWLDataIntersectionOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equal(a.operands(), b.operands());
    }

    public static boolean equals(@Nullable OWLDatatypeRestriction a, @Nullable OWLDatatypeRestriction b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getDatatype().getIRI(), b.getDatatype().getIRI())) {
            return false;
        }
        return equal(a.facetRestrictions(), b.facetRestrictions());
    }

    public static boolean equals(@Nullable OWLFacetRestriction a, @Nullable OWLFacetRestriction b) {
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

    /*
     *  Annotation related types
     */
    public static boolean equals(@Nullable OWLAnnotation a, @Nullable OWLAnnotation b) {
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
        return equal(a.annotations(), b.annotations());
    }

    private static boolean equals(@Nullable OWLAnnotationSubject a, @Nullable OWLAnnotationSubject b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof IRI && b instanceof IRI) {
            return equals((IRI) a, (IRI) b);
        } else if (a instanceof OWLAnonymousIndividual && b instanceof OWLAnonymousIndividual) {
            return a.equals(b);
        } else {
            return false;
        }
    }

    public static boolean equals(@Nullable OWLAnnotationValue a, @Nullable OWLAnnotationValue b) {
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
    public static boolean equals(@Nullable OWLAnnotationAssertionAxiom a, @Nullable OWLAnnotationAssertionAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLAnnotationPropertyDomainAxiom a,
        @Nullable OWLAnnotationPropertyDomainAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLAnnotationPropertyRangeAxiom a,
        @Nullable OWLAnnotationPropertyRangeAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    // Declaration Axiom
    public static boolean equals(@Nullable OWLDeclarationAxiom a, @Nullable OWLDeclarationAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getEntity(), b.getEntity())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    // Datatype Definition axiom
    public static boolean equals(@Nullable OWLDatatypeDefinitionAxiom a, @Nullable OWLDatatypeDefinitionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getDatatype().getIRI(), b.getDatatype().getIRI())) {
            return false;
        }
        if (!equals(a.getDataRange(), b.getDataRange())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    // Individual related Axioms
    public static boolean equals(@Nullable OWLObjectPropertyAssertionAxiom a,
        @Nullable OWLObjectPropertyAssertionAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLNegativeObjectPropertyAssertionAxiom a,
        @Nullable OWLNegativeObjectPropertyAssertionAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDataPropertyAssertionAxiom a, @Nullable OWLDataPropertyAssertionAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLNegativeDataPropertyAssertionAxiom a,
        @Nullable OWLNegativeDataPropertyAssertionAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLClassAssertionAxiom a, @Nullable OWLClassAssertionAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLSameIndividualAxiom a, @Nullable OWLSameIndividualAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.individuals(), b.individuals())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDifferentIndividualsAxiom a, @Nullable OWLDifferentIndividualsAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.individuals(), b.individuals())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    // Class Axioms
    public static boolean equals(@Nullable OWLSubClassOfAxiom a, @Nullable OWLSubClassOfAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLEquivalentClassesAxiom a, @Nullable OWLEquivalentClassesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.classExpressions(), b.classExpressions())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDisjointClassesAxiom a, @Nullable OWLDisjointClassesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.classExpressions(), b.classExpressions())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDisjointUnionAxiom a, @Nullable OWLDisjointUnionAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.classExpressions(), b.classExpressions())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    // Property Axioms
    // Property characteristics
    public static boolean equals(@Nullable OWLAsymmetricObjectPropertyAxiom a,
        @Nullable OWLAsymmetricObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLReflexiveObjectPropertyAxiom a,
        @Nullable OWLReflexiveObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLIrreflexiveObjectPropertyAxiom a,
        @Nullable OWLIrreflexiveObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLFunctionalObjectPropertyAxiom a,
        @Nullable OWLFunctionalObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLInverseFunctionalObjectPropertyAxiom a,
        @Nullable OWLInverseFunctionalObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLSymmetricObjectPropertyAxiom a,
        @Nullable OWLSymmetricObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLTransitiveObjectPropertyAxiom a,
        @Nullable OWLTransitiveObjectPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLFunctionalDataPropertyAxiom a,
        @Nullable OWLFunctionalDataPropertyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getProperty(), b.getProperty())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    // Object Property Axioms
    public static boolean equals(@Nullable OWLObjectPropertyDomainAxiom a, @Nullable OWLObjectPropertyDomainAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLObjectPropertyRangeAxiom a, @Nullable OWLObjectPropertyRangeAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLEquivalentObjectPropertiesAxiom a,
        @Nullable OWLEquivalentObjectPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.properties(), b.properties())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDisjointObjectPropertiesAxiom a,
        @Nullable OWLDisjointObjectPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.properties(), b.properties())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLSubObjectPropertyOfAxiom a, @Nullable OWLSubObjectPropertyOfAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLSubPropertyChainOfAxiom a, @Nullable OWLSubPropertyChainOfAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equals(a.getSuperProperty(), b.getSuperProperty())) {
            return false;
        }
        if (!equal(a.getPropertyChain().stream(), b.getPropertyChain().stream())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLInverseObjectPropertiesAxiom a,
        @Nullable OWLInverseObjectPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    // Data Property Axioms
    public static boolean equals(@Nullable OWLDataPropertyDomainAxiom a, @Nullable OWLDataPropertyDomainAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDataPropertyRangeAxiom a, @Nullable OWLDataPropertyRangeAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLDisjointDataPropertiesAxiom a,
        @Nullable OWLDisjointDataPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.properties(), b.properties())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLEquivalentDataPropertiesAxiom a,
        @Nullable OWLEquivalentDataPropertiesAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.properties(), b.properties())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    public static boolean equals(@Nullable OWLSubDataPropertyOfAxiom a, @Nullable OWLSubDataPropertyOfAxiom b) {
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
        return equal(a.annotations(), b.annotations());
    }

    // Has Key Axiom
    public static boolean equals(@Nullable OWLHasKeyAxiom a, @Nullable OWLHasKeyAxiom b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (!equal(a.dataPropertyExpressions(), b.dataPropertyExpressions())) {
            return false;
        }
        if (!equal(a.objectPropertyExpressions(), b.objectPropertyExpressions())) {
            return false;
        }
        return equal(a.annotations(), b.annotations());
    }

    /*
     *  Class Expressions
     */
    private static boolean equals(@Nullable OWLClassExpression a, @Nullable OWLClassExpression b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.isOWLClass() && b.isOWLClass()) {
            return equals(a.asOWLClass().getIRI(), b.asOWLClass().getIRI());
        } else if (a instanceof OWLObjectIntersectionOf && b instanceof OWLObjectIntersectionOf) {
            return equals((OWLObjectIntersectionOf) a, (OWLObjectIntersectionOf) b);
        } else if (a instanceof OWLObjectUnionOf && b instanceof OWLObjectUnionOf) {
            return equals((OWLObjectUnionOf) a, (OWLObjectUnionOf) b);
        } else if (a instanceof OWLObjectComplementOf && b instanceof OWLObjectComplementOf) {
            return equals((OWLObjectComplementOf) a, (OWLObjectComplementOf) b);
        } else if (a instanceof OWLObjectSomeValuesFrom && b instanceof OWLObjectSomeValuesFrom) {
            return equals((OWLObjectSomeValuesFrom) a, (OWLObjectSomeValuesFrom) b);
        } else if (a instanceof OWLObjectAllValuesFrom && b instanceof OWLObjectAllValuesFrom) {
            return equals((OWLObjectAllValuesFrom) a, (OWLObjectAllValuesFrom) b);
        } else if (a instanceof OWLObjectHasValue && b instanceof OWLObjectHasValue) {
            return equals((OWLObjectHasValue) a, (OWLObjectHasValue) b);
        } else if (a instanceof OWLObjectMinCardinality && b instanceof OWLObjectMinCardinality) {
            return equals((OWLObjectMinCardinality) a, (OWLObjectMinCardinality) b);
        } else if (a instanceof OWLObjectExactCardinality && b instanceof OWLObjectExactCardinality) {
            return equals((OWLObjectExactCardinality) a, (OWLObjectExactCardinality) b);
        } else if (a instanceof OWLObjectMaxCardinality && b instanceof OWLObjectMaxCardinality) {
            return equals((OWLObjectMaxCardinality) a, (OWLObjectMaxCardinality) b);
        } else if (a instanceof OWLObjectHasSelf && b instanceof OWLObjectHasSelf) {
            return equals((OWLObjectHasSelf) a, (OWLObjectHasSelf) b);
        } else if (a instanceof OWLObjectOneOf && b instanceof OWLObjectOneOf) {
            return equals((OWLObjectOneOf) a, (OWLObjectOneOf) b);
        } else if (a instanceof OWLDataSomeValuesFrom && b instanceof OWLDataSomeValuesFrom) {
            return equals((OWLDataSomeValuesFrom) a, (OWLDataSomeValuesFrom) b);
        } else if (a instanceof OWLDataAllValuesFrom && b instanceof OWLDataAllValuesFrom) {
            return equals((OWLDataAllValuesFrom) a, (OWLDataAllValuesFrom) b);
        } else if (a instanceof OWLDataHasValue && b instanceof OWLDataHasValue) {
            return equals((OWLDataHasValue) a, (OWLDataHasValue) b);
        } else if (a instanceof OWLDataMinCardinality && b instanceof OWLDataMinCardinality) {
            return equals((OWLDataMinCardinality) a, (OWLDataMinCardinality) b);
        } else if (a instanceof OWLDataExactCardinality && b instanceof OWLDataExactCardinality) {
            return equals((OWLDataExactCardinality) a, (OWLDataExactCardinality) b);
        } else if (a instanceof OWLDataMaxCardinality && b instanceof OWLDataMaxCardinality) {
            return equals((OWLDataMaxCardinality) a, (OWLDataMaxCardinality) b);
        } else {
            return false;
        }
    }

    public static boolean equals(@Nullable OWLNaryBooleanClassExpression a, @Nullable OWLNaryBooleanClassExpression b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equal(a.operands(), b.operands());
    }

    public static boolean equals(@Nullable OWLObjectComplementOf a, @Nullable OWLObjectComplementOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equals(a.getOperand(), b.getOperand());
    }

    public static boolean equals(@Nullable OWLObjectSomeValuesFrom a, @Nullable OWLObjectSomeValuesFrom b) {
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

    public static boolean equals(@Nullable OWLObjectAllValuesFrom a, @Nullable OWLObjectAllValuesFrom b) {
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

    public static boolean equals(@Nullable OWLObjectHasValue a, @Nullable OWLObjectHasValue b) {
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

    public static boolean equals(@Nullable OWLObjectCardinalityRestriction a,
        @Nullable OWLObjectCardinalityRestriction b) {
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

    public static boolean equals(@Nullable OWLObjectHasSelf a, @Nullable OWLObjectHasSelf b) {
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

    public static boolean equals(@Nullable OWLObjectOneOf a, @Nullable OWLObjectOneOf b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return equal(a.individuals(), b.individuals());
    }

    public static boolean equals(@Nullable OWLDataSomeValuesFrom a, @Nullable OWLDataSomeValuesFrom b) {
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

    public static boolean equals(@Nullable OWLDataAllValuesFrom a, @Nullable OWLDataAllValuesFrom b) {
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

    public static boolean equals(@Nullable OWLDataHasValue a, @Nullable OWLDataHasValue b) {
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

    public static boolean equals(@Nullable OWLDataCardinalityRestriction a, @Nullable OWLDataCardinalityRestriction b) {
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
    public static boolean equals(@Nullable SWRLRule a, @Nullable SWRLRule b) {
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
    public static boolean equals(SWRLObjectPropertyAtom a, SWRLObjectPropertyAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLDataPropertyAtom a, SWRLDataPropertyAtom b) {
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
    public static boolean equals(SWRLIndividualArgument a, SWRLIndividualArgument b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLLiteralArgument a, SWRLLiteralArgument b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLSameIndividualAtom a, SWRLSameIndividualAtom b) {
        throw new AbstractMethodError();
    }

    @SuppressWarnings("unused")
    public static boolean equals(SWRLDifferentIndividualsAtom a, SWRLDifferentIndividualsAtom b) {
        throw new AbstractMethodError();
    }
}
