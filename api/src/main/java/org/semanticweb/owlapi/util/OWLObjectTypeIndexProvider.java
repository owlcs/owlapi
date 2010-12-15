package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
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
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Feb-2008<br><br>
 */
@SuppressWarnings("unused")
public class OWLObjectTypeIndexProvider implements OWLObjectVisitor {

    public static final int ENTITY_TYPE_INDEX_BASE = 1000;

    private static final int IRI = 0;

    private static final int ONTOLOGY = 1;

    public static final int OWL_CLASS = ENTITY_TYPE_INDEX_BASE + ONTOLOGY;


    public static final int OBJECT_PROPERTY = ENTITY_TYPE_INDEX_BASE + 2;


    public int type;

    public static final int OBJECT_PROPERTY_INVERSE = ENTITY_TYPE_INDEX_BASE + 3;

    public static final int DATA_PROPERTY = ENTITY_TYPE_INDEX_BASE + 4;

    public static final int INDIVIDUAL = ENTITY_TYPE_INDEX_BASE + 5;

    public static final int ANNOTATION_PROPERTY = ENTITY_TYPE_INDEX_BASE + 6;

    public static final int ANON_INDIVIDUAL = ENTITY_TYPE_INDEX_BASE + 7;


    public static final int AXIOM_TYPE_INDEX_BASE = 2000;

    public static final int SUBCLASS_AXIOM = AXIOM_TYPE_INDEX_BASE + AxiomType.SUBCLASS_OF.index;

    public static final int NEGATIVE_OBJECT_PROPERTY_ASSERTION = AXIOM_TYPE_INDEX_BASE + AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION.getIndex();


    public int getTypeIndex(OWLObject object) {
        object.accept(this);
        return type;
    }

    public void visit(IRI iri) {
        type = IRI;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Entities
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLClass desc) {
        type = OWL_CLASS;
    }

    public void visit(OWLObjectProperty property) {
        type = OBJECT_PROPERTY;
    }


    public void visit(OWLObjectInverseOf property) {
        type = OBJECT_PROPERTY_INVERSE;
    }


    public void visit(OWLDataProperty property) {
        type = DATA_PROPERTY;
    }


    public void visit(OWLNamedIndividual individual) {
        type = INDIVIDUAL;
    }

    public void visit(OWLAnnotationProperty property) {
        type = ANNOTATION_PROPERTY;
    }

    public void visit(OWLOntology ontology) {
        type = ONTOLOGY;
    }

    public void visit(OWLAnonymousIndividual individual) {
        type = ANON_INDIVIDUAL;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Axioms
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLSubClassOfAxiom axiom) {
        type = SUBCLASS_AXIOM;
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        type = NEGATIVE_OBJECT_PROPERTY_ASSERTION;
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(SWRLRule rule) {
        type = AXIOM_TYPE_INDEX_BASE + rule.getAxiomType().getIndex();
    }

    public void visit(OWLHasKeyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Anon class expressions
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int CLASS_EXPRESSION_TYPE_INDEX_BASE = 3000;


    public void visit(OWLObjectIntersectionOf desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + ONTOLOGY;
    }

    public void visit(OWLObjectUnionOf desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 2;
    }


    public void visit(OWLObjectComplementOf desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 3;
    }


    public void visit(OWLObjectOneOf desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 4;
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 5;
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 6;
    }


    public void visit(OWLObjectHasValue desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 7;
    }


    public void visit(OWLObjectMinCardinality desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 8;
    }


    public void visit(OWLObjectExactCardinality desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 9;
    }


    public void visit(OWLObjectMaxCardinality desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 10;
    }


    public void visit(OWLObjectHasSelf desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 11;
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 12;
    }


    public void visit(OWLDataAllValuesFrom desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 13;
    }


    public void visit(OWLDataHasValue desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 14;
    }


    public void visit(OWLDataMinCardinality desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 15;
    }


    public void visit(OWLDataExactCardinality desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 16;
    }


    public void visit(OWLDataMaxCardinality desc) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 17;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Data types and data values
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int DATA_TYPE_INDEX_BASE = 4000;

    public void visit(OWLDatatype node) {
        type = DATA_TYPE_INDEX_BASE + ONTOLOGY;
    }


    public void visit(OWLDataComplementOf node) {
        type = DATA_TYPE_INDEX_BASE + 2;
    }


    public void visit(OWLDataOneOf node) {
        type = DATA_TYPE_INDEX_BASE + 3;
    }

    public void visit(OWLDataIntersectionOf node) {
        type = DATA_TYPE_INDEX_BASE + 4;
    }

    public void visit(OWLDataUnionOf node) {
        type = AXIOM_TYPE_INDEX_BASE + 5;
    }

    public void visit(OWLDatatypeRestriction node) {
        type = DATA_TYPE_INDEX_BASE + 6;
    }

    public void visit(OWLFacetRestriction node) {
        type = DATA_TYPE_INDEX_BASE + 7;
    }

    public void visit(OWLLiteral node) {
        type = DATA_TYPE_INDEX_BASE + 8;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Annotations
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int ANNOTATION_TYPE_INDEX_BASE = 5000;

    public void visit(OWLAnnotation node) {
        type = ANNOTATION_TYPE_INDEX_BASE + 1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Rule objects
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int RULE_OBJECT_TYPE_INDEX_BASE = 6000;


    public void visit(SWRLClassAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + ONTOLOGY;
    }


    public void visit(SWRLDataRangeAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 2;
    }


    public void visit(SWRLObjectPropertyAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 3;
    }


    public void visit(SWRLDataPropertyAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 4;
    }


    public void visit(SWRLBuiltInAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 5;
    }

    public void visit(SWRLVariable node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 6;
    }

    public void visit(SWRLIndividualArgument node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 7;
    }


    public void visit(SWRLLiteralArgument node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 8;
    }


    public void visit(SWRLSameIndividualAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 9;
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 10;
    }
}
