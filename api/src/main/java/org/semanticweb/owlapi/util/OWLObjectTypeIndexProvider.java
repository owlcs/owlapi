package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Feb-2008<br><br>
 */
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

    public void visit(OWLTypedLiteral node) {
        type = DATA_TYPE_INDEX_BASE + 8;
    }


    public void visit(OWLStringLiteral node) {
        type = DATA_TYPE_INDEX_BASE + 9;
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
