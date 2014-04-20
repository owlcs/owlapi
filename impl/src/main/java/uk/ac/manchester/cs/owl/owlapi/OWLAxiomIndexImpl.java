package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Search.*;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomIndex;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.search.Filters;

import javax.annotation.Nonnull;

/**
 * @author ignazio
 * @since 4.0.0
 */
public abstract class OWLAxiomIndexImpl extends OWLObjectImpl implements
        OWLAxiomIndex {

    private static final long serialVersionUID = 40000L;
    protected Internals ints = new Internals();

    @Nonnull
    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(@Nonnull OWLEntity entity) {
        return getAxioms(OWLDeclarationAxiom.class, entity, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            @Nonnull OWLAnnotationSubject subject) {
        return getAxioms(OWLAnnotationAssertionAxiom.class,
                OWLAnnotationSubject.class, subject, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
            @Nonnull OWLDatatype datatype) {
        Set<OWLDatatypeDefinitionAxiom> toReturn = new HashSet<OWLDatatypeDefinitionAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.datatypeDefFilter,
                datatype)) {
            toReturn.add((OWLDatatypeDefinitionAxiom) ax);
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom>
            getSubAnnotationPropertyOfAxioms(@Nonnull OWLAnnotationProperty subProperty) {
        Set<OWLSubAnnotationPropertyOfAxiom> toReturn = new HashSet<OWLSubAnnotationPropertyOfAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.subAnnotationWithSub,
                subProperty)) {
            toReturn.add((OWLSubAnnotationPropertyOfAxiom) ax);
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationPropertyDomainAxiom>
            getAnnotationPropertyDomainAxioms(@Nonnull OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyDomainAxiom> toReturn = new HashSet<OWLAnnotationPropertyDomainAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.apDomainFilter, property)) {
            toReturn.add((OWLAnnotationPropertyDomainAxiom) ax);
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationPropertyRangeAxiom>
            getAnnotationPropertyRangeAxioms(@Nonnull OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyRangeAxiom> toReturn = new HashSet<OWLAnnotationPropertyRangeAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.apRangeFilter, property)) {
            toReturn.add((OWLAnnotationPropertyRangeAxiom) ax);
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(@Nonnull OWLClass cls) {
        return getAxioms(OWLSubClassOfAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(@Nonnull OWLClass cls) {
        return getAxioms(OWLSubClassOfAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUPER_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
            @Nonnull OWLClass cls) {
        return getAxioms(OWLEquivalentClassesAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(@Nonnull OWLClass cls) {
        return getAxioms(OWLDisjointClassesAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(@Nonnull OWLClass owlClass) {
        return getAxioms(OWLDisjointUnionAxiom.class, OWLClass.class, owlClass,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(@Nonnull OWLClass cls) {
        return getAxioms(OWLHasKeyAxiom.class, OWLClass.class, cls, EXCLUDED,
                IN_SUB_POSITION);
    }

    // Object properties
    @Nonnull
    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSubProperty(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLSubObjectPropertyOfAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSuperProperty(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLSubObjectPropertyOfAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUPER_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
            @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLObjectPropertyDomainAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
            @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLObjectPropertyRangeAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLInverseObjectPropertiesAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertiesAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLEquivalentObjectPropertiesAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertiesAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLDisjointObjectPropertiesAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLFunctionalObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLInverseFunctionalObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLSymmetricObjectPropertyAxiom>
            getSymmetricObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLSymmetricObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLAsymmetricObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLReflexiveObjectPropertyAxiom>
            getReflexiveObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLReflexiveObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexiveObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLIrreflexiveObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLTransitiveObjectPropertyAxiom>
            getTransitiveObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property) {
        return getAxioms(OWLTransitiveObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
            @Nonnull OWLDataPropertyExpression property) {
        return getAxioms(OWLFunctionalDataPropertyAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSubProperty(@Nonnull OWLDataProperty lhsProperty) {
        return getAxioms(OWLSubDataPropertyOfAxiom.class,
                OWLDataPropertyExpression.class, lhsProperty, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSuperProperty(
                    @Nonnull OWLDataPropertyExpression property) {
        return getAxioms(OWLSubDataPropertyOfAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUPER_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
            @Nonnull OWLDataProperty property) {
        return getAxioms(OWLDataPropertyDomainAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
            @Nonnull OWLDataProperty property) {
        return getAxioms(OWLDataPropertyRangeAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertiesAxioms(@Nonnull OWLDataProperty property) {
        return getAxioms(OWLEquivalentDataPropertiesAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
            @Nonnull OWLDataProperty property) {
        return getAxioms(OWLDisjointDataPropertiesAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            @Nonnull OWLIndividual individual) {
        return getAxioms(OWLClassAssertionAxiom.class, OWLIndividual.class,
                individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            @Nonnull OWLClassExpression type) {
        return getAxioms(OWLClassAssertionAxiom.class,
                OWLClassExpression.class, type, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
            @Nonnull OWLIndividual individual) {
        return getAxioms(OWLDataPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionAxioms(@Nonnull OWLIndividual individual) {
        return getAxioms(OWLObjectPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxioms(@Nonnull OWLIndividual individual) {
        return getAxioms(OWLNegativeObjectPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxioms(@Nonnull OWLIndividual individual) {
        return getAxioms(OWLNegativeDataPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
            @Nonnull OWLIndividual individual) {
        return getAxioms(OWLSameIndividualAxiom.class, OWLIndividual.class,
                individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Nonnull
    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
            @Nonnull OWLIndividual individual) {
        return getAxioms(OWLDifferentIndividualsAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }
}
