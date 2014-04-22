package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Search.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

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

/**
 * @author ignazio
 * @since 4.0.0
 */
public abstract class OWLAxiomIndexImpl extends OWLObjectImpl implements
        OWLAxiomIndex {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    protected Internals ints = new Internals();

    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
        return getAxioms(OWLDeclarationAxiom.class, entity, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            OWLAnnotationSubject subject) {
        return getAxioms(OWLAnnotationAssertionAxiom.class,
                OWLAnnotationSubject.class, subject, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
            OWLDatatype datatype) {
        Set<OWLDatatypeDefinitionAxiom> toReturn = new HashSet<OWLDatatypeDefinitionAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.datatypeDefFilter,
                datatype)) {
            toReturn.add((OWLDatatypeDefinitionAxiom) ax);
        }
        return toReturn;
    }

    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom>
            getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        Set<OWLSubAnnotationPropertyOfAxiom> toReturn = new HashSet<OWLSubAnnotationPropertyOfAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.subAnnotationWithSub,
                subProperty)) {
            toReturn.add((OWLSubAnnotationPropertyOfAxiom) ax);
        }
        return toReturn;
    }

    @Override
    public Set<OWLAnnotationPropertyDomainAxiom>
            getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyDomainAxiom> toReturn = new HashSet<OWLAnnotationPropertyDomainAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.apDomainFilter, property)) {
            toReturn.add((OWLAnnotationPropertyDomainAxiom) ax);
        }
        return toReturn;
    }

    @Override
    public Set<OWLAnnotationPropertyRangeAxiom>
            getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyRangeAxiom> toReturn = new HashSet<OWLAnnotationPropertyRangeAxiom>();
        for (OWLAxiom ax : ints.filterAxioms(Filters.apRangeFilter, property)) {
            toReturn.add((OWLAnnotationPropertyRangeAxiom) ax);
        }
        return toReturn;
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        return getAxioms(OWLSubClassOfAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        return getAxioms(OWLSubClassOfAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUPER_POSITION);
    }

    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
            OWLClass cls) {
        return getAxioms(OWLEquivalentClassesAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        return getAxioms(OWLDisjointClassesAxiom.class, OWLClass.class, cls,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        return getAxioms(OWLDisjointUnionAxiom.class, OWLClass.class, owlClass,
                EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        return getAxioms(OWLHasKeyAxiom.class, OWLClass.class, cls, EXCLUDED,
                IN_SUB_POSITION);
    }

    // Object properties
    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSubProperty(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLSubObjectPropertyOfAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSuperProperty(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLSubObjectPropertyOfAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUPER_POSITION);
    }

    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
            OWLObjectPropertyExpression property) {
        return getAxioms(OWLObjectPropertyDomainAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
            OWLObjectPropertyExpression property) {
        return getAxioms(OWLObjectPropertyRangeAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
            OWLObjectPropertyExpression property) {
        return getAxioms(OWLInverseObjectPropertiesAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertiesAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLEquivalentObjectPropertiesAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertiesAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLDisjointObjectPropertiesAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLFunctionalObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLInverseFunctionalObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLSymmetricObjectPropertyAxiom>
            getSymmetricObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLSymmetricObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLAsymmetricObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLReflexiveObjectPropertyAxiom>
            getReflexiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLReflexiveObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLIrreflexiveObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLTransitiveObjectPropertyAxiom>
            getTransitiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return getAxioms(OWLTransitiveObjectPropertyAxiom.class,
                OWLObjectPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
            OWLDataPropertyExpression property) {
        return getAxioms(OWLFunctionalDataPropertyAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        return getAxioms(OWLSubDataPropertyOfAxiom.class,
                OWLDataPropertyExpression.class, lhsProperty, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSuperProperty(
                    OWLDataPropertyExpression property) {
        return getAxioms(OWLSubDataPropertyOfAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUPER_POSITION);
    }

    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
            OWLDataProperty property) {
        return getAxioms(OWLDataPropertyDomainAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
            OWLDataProperty property) {
        return getAxioms(OWLDataPropertyRangeAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        return getAxioms(OWLEquivalentDataPropertiesAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
            OWLDataProperty property) {
        return getAxioms(OWLDisjointDataPropertiesAxiom.class,
                OWLDataPropertyExpression.class, property, EXCLUDED,
                IN_SUB_POSITION);
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            OWLIndividual individual) {
        return getAxioms(OWLClassAssertionAxiom.class, OWLIndividual.class,
                individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            OWLClassExpression type) {
        return getAxioms(OWLClassAssertionAxiom.class,
                OWLClassExpression.class, type, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
            OWLIndividual individual) {
        return getAxioms(OWLDataPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return getAxioms(OWLObjectPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return getAxioms(OWLNegativeObjectPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        return getAxioms(OWLNegativeDataPropertyAssertionAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
            OWLIndividual individual) {
        return getAxioms(OWLSameIndividualAxiom.class, OWLIndividual.class,
                individual, EXCLUDED, IN_SUB_POSITION);
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
            OWLIndividual individual) {
        return getAxioms(OWLDifferentIndividualsAxiom.class,
                OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }
}
