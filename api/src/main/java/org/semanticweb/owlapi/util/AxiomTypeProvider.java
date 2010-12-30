package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.model.AxiomType.*;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jan-2008<br><br>
 */
//XXX visitorEx?
@SuppressWarnings("unused")
public class AxiomTypeProvider implements OWLAxiomVisitor {

    private AxiomType<?> axiomType;

    public void visit(OWLSubClassOfAxiom axiom) {
        axiomType = SUBCLASS_OF;
    }


    public AxiomType<?> getAxiomType(OWLAxiom axiom) {
        axiom.accept(this);
        return axiomType;
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiomType = NEGATIVE_OBJECT_PROPERTY_ASSERTION;
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        axiomType = ASYMMETRIC_OBJECT_PROPERTY;
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        axiomType = REFLEXIVE_OBJECT_PROPERTY;
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        axiomType = DISJOINT_CLASSES;
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiomType = DATA_PROPERTY_DOMAIN;
    }
    

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        axiomType = OBJECT_PROPERTY_DOMAIN;
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        axiomType = EQUIVALENT_OBJECT_PROPERTIES;
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiomType = NEGATIVE_DATA_PROPERTY_ASSERTION;
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        axiomType = DIFFERENT_INDIVIDUALS;
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        axiomType = DISJOINT_DATA_PROPERTIES;
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        axiomType = DISJOINT_OBJECT_PROPERTIES;
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        axiomType = OBJECT_PROPERTY_RANGE;
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiomType = OBJECT_PROPERTY_ASSERTION;
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        axiomType = FUNCTIONAL_OBJECT_PROPERTY;
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiomType = SUB_OBJECT_PROPERTY;
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        axiomType = DISJOINT_UNION;
    }


    public void visit(OWLDeclarationAxiom axiom) {
        axiomType = DECLARATION;
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        axiomType = ANNOTATION_ASSERTION;
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiomType = SYMMETRIC_OBJECT_PROPERTY;
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        axiomType = DATA_PROPERTY_RANGE;
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        axiomType = FUNCTIONAL_DATA_PROPERTY;
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        axiomType = EQUIVALENT_DATA_PROPERTIES;
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiomType = CLASS_ASSERTION;
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        axiomType = EQUIVALENT_CLASSES;
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiomType = DATA_PROPERTY_ASSERTION;
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiomType = TRANSITIVE_OBJECT_PROPERTY;
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        axiomType = IRREFLEXIVE_OBJECT_PROPERTY;
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiomType = SUB_DATA_PROPERTY;
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        axiomType = INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        axiomType = SAME_INDIVIDUAL;
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        axiomType = SUB_PROPERTY_CHAIN_OF;
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiomType = INVERSE_OBJECT_PROPERTIES;
    }


    public void visit(SWRLRule rule) {
        axiomType = SWRL_RULE;
    }

    public void visit(OWLHasKeyAxiom axiom) {
        axiomType = HAS_KEY;
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        axiomType = ANNOTATION_PROPERTY_DOMAIN;
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        axiomType = ANNOTATION_PROPERTY_RANGE;
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        axiomType = SUB_ANNOTATION_PROPERTY_OF;
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        axiomType = DATATYPE_DEFINITION;
    }
}
