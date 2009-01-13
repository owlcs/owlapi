package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;
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

    private static final int ONTOLOGY = 1;

    public static final int OWL_CLASS = ENTITY_TYPE_INDEX_BASE + ONTOLOGY;


    public static final int OBJECT_PROPERTY = ENTITY_TYPE_INDEX_BASE + 2;



    public int type;

    public static final int OBJECT_PROPERTY_INVERSE = ENTITY_TYPE_INDEX_BASE + 3;

    public static final int DATA_PROPERTY = ENTITY_TYPE_INDEX_BASE + 4;

    public static final int INDIVIDUAL = ENTITY_TYPE_INDEX_BASE + 5;



    public static final int AXIOM_TYPE_INDEX_BASE = 2000;

    public static final int SUBCLASS_AXIOM = AXIOM_TYPE_INDEX_BASE + AxiomType.SUBCLASS.index;

    public static final int NEGATIVE_OBJECT_PROPERTY_ASSERTION = AXIOM_TYPE_INDEX_BASE + AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION.getIndex();


    public int getTypeIndex(OWLObject object) {
        object.accept(this);
        return type;
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


    public void visit(OWLObjectPropertyInverse property) {
        type = OBJECT_PROPERTY_INVERSE;
    }


    public void visit(OWLDataProperty property) {
        type = DATA_PROPERTY;
    }


    public void visit(OWLIndividual individual) {
        type = INDIVIDUAL;
    }


    public void visit(OWLOntology ontology) {
        type = ONTOLOGY;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Axioms
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////



    public void visit(OWLSubClassAxiom axiom) {
        type = SUBCLASS_AXIOM;
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        type = NEGATIVE_OBJECT_PROPERTY_ASSERTION;
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
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


    public void visit(OWLImportsDeclaration axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
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


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
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


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    public void visit(SWRLRule rule) {
        type = AXIOM_TYPE_INDEX_BASE + rule.getAxiomType().getIndex();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Anon Descriptions
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int DESCRIPTION_TYPE_INDEX_BASE = 3000;


    public void visit(OWLObjectIntersectionOf desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + ONTOLOGY;
    }

    public void visit(OWLObjectUnionOf desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 2;
    }


    public void visit(OWLObjectComplementOf desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 3;
    }


    public void visit(OWLObjectOneOf desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 4;
    }
    

    public void visit(OWLObjectSomeRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 5;
    }


    public void visit(OWLObjectAllRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 6;
    }


    public void visit(OWLObjectValueRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 7;
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 8;
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 9;
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 10;
    }


    public void visit(OWLObjectSelfRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 11;
    }




    public void visit(OWLDataSomeRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 12;
    }


    public void visit(OWLDataAllRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 13;
    }


    public void visit(OWLDataValueRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 14;
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 15;
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 16;
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        type = DESCRIPTION_TYPE_INDEX_BASE + 17;
    }





    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Data types and data values
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int DATA_TYPE_INDEX_BASE = 4000;

    public void visit(OWLDataType node) {
        type = DATA_TYPE_INDEX_BASE + ONTOLOGY;
    }


    public void visit(OWLDataComplementOf node) {
        type = DATA_TYPE_INDEX_BASE + 2;
    }


    public void visit(OWLDataOneOf node) {
        type = DATA_TYPE_INDEX_BASE + 3;
    }


    public void visit(OWLDataRangeRestriction node) {
        type = DATA_TYPE_INDEX_BASE + 4;
    }

    public void visit(OWLDataRangeFacetRestriction node) {
        type = DATA_TYPE_INDEX_BASE + 5;
    }

    public void visit(OWLTypedConstant node) {
        type = DATA_TYPE_INDEX_BASE + 6;
    }


    public void visit(OWLUntypedConstant node) {
        type = DATA_TYPE_INDEX_BASE + 7;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Annotations
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int ANNOTATION_TYPE_INDEX_BASE = 5000;

    public void visit(OWLObjectAnnotation annotation) {
        type = ANNOTATION_TYPE_INDEX_BASE + ONTOLOGY;
    }


    public void visit(OWLConstantAnnotation annotation) {
        type = ANNOTATION_TYPE_INDEX_BASE + 2;
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


    public void visit(SWRLDataValuedPropertyAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 4;
    }


    public void visit(SWRLBuiltInAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 5;
    }

    public void visit(SWRLAtomIVariable node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 6;
    }

    public void visit(SWRLAtomDVariable node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 7;
    }


    public void visit(SWRLAtomIndividualObject node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 8;
    }


    public void visit(SWRLAtomConstantObject node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 9;
    }


    public void visit(SWRLSameAsAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 10;
    }


    public void visit(SWRLDifferentFromAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 11;
    }
}
