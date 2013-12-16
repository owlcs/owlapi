package org.semanticweb.owlapi.util;

import java.io.Serializable;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/** adapter for axiom visitors
 * 
 * @author ignazio
 * @param <K> */
public class OWLAxiomVisitorExAdapter<K> implements OWLAxiomVisitorEx<K>, Serializable {
    private static final long serialVersionUID = 40000L;
    private K object;

    /** adapter with null default */
    public OWLAxiomVisitorExAdapter() {
        this(null);
    }

    /** adapter with object as default value
     * 
     * @param object
     *            default return value */
    public OWLAxiomVisitorExAdapter(K object) {
        this.object = object;
    }

    /** override to change default behaviour
     * 
     * @param axiom
     *            visited axiom
     * @return default return value; */
    protected K doDefault(@SuppressWarnings("unused") OWLAxiom axiom) {
        return object;
    }

    @Override
    public K visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLSubClassOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDisjointClassesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDataPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLObjectPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDifferentIndividualsAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDisjointDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLObjectPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLSubObjectPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDisjointUnionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDeclarationAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLAnnotationAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDataPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLFunctionalDataPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLClassAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLEquivalentClassesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLSubDataPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLSameIndividualAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLSubPropertyChainOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLInverseObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLHasKeyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(OWLDatatypeDefinitionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public K visit(SWRLRule rule) {
        return doDefault(rule);
    }
}
