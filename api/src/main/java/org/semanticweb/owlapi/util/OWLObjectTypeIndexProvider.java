/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

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
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class OWLObjectTypeIndexProvider implements OWLObjectVisitor {

    //@formatter:off
    /** ENTITY_TYPE_INDEX_BASE.           */ public static final int ENTITY_TYPE_INDEX_BASE              = 1000;
    /** IRI.                              */ public static final int IRI                                 = 0;
    /** ONTOLOGY.                         */ public static final int ONTOLOGY                            = 1;
    /** OWL_CLASS.                        */ public static final int OWL_CLASS                           = ENTITY_TYPE_INDEX_BASE + 1;
    /** OBJECT_PROPERTY.                  */ public static final int OBJECT_PROPERTY                     = ENTITY_TYPE_INDEX_BASE + 2;
    /** OBJECT_PROPERTY_INVERSE.          */ public static final int OBJECT_PROPERTY_INVERSE             = ENTITY_TYPE_INDEX_BASE + 3;
    /** DATA_PROPERTY.                    */ public static final int DATA_PROPERTY                       = ENTITY_TYPE_INDEX_BASE + 4;
    /** INDIVIDUAL.                       */ public static final int INDIVIDUAL                          = ENTITY_TYPE_INDEX_BASE + 5;
    /** ANNOTATION_PROPERTY.              */ public static final int ANNOTATION_PROPERTY                 = ENTITY_TYPE_INDEX_BASE + 6;
    /** ANON_INDIVIDUAL.                  */ public static final int ANON_INDIVIDUAL                     = ENTITY_TYPE_INDEX_BASE + 7;
    /** AXIOM_TYPE_INDEX_BASE.            */ public static final int AXIOM_TYPE_INDEX_BASE               = 2000;
    /** DATA_TYPE_INDEX_BASE.             */ public static final int DATA_TYPE_INDEX_BASE                = 4000;
    /** ANNOTATION_TYPE_INDEX_BASE.       */ public static final int ANNOTATION_TYPE_INDEX_BASE          = 5000;
    /** RULE_OBJECT_TYPE_INDEX_BASE.      */ public static final int RULE_OBJECT_TYPE_INDEX_BASE         = 6000;
    /** CLASS_EXPRESSION_TYPE_INDEX_BASE. */ public static final int CLASS_EXPRESSION_TYPE_INDEX_BASE    = 3000;
//@formatter:on
    private int type;

    /**
     * @param object
     *        the object to compute the type index of
     * @return the type
     */
    public int getTypeIndex(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null").accept(this);
        return type;
    }

    @Override
    public void visit(IRI iri) {
        type = IRI;
    }

    // Entities
    @Override
    public void visit(OWLClass ce) {
        type = OWL_CLASS;
    }

    @Override
    public void visit(OWLObjectProperty property) {
        type = OBJECT_PROPERTY;
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        type = OBJECT_PROPERTY_INVERSE;
    }

    @Override
    public void visit(OWLDataProperty property) {
        type = DATA_PROPERTY;
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        type = INDIVIDUAL;
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        type = ANNOTATION_PROPERTY;
    }

    @Override
    public void visit(OWLOntology ontology) {
        type = ONTOLOGY;
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        type = ANON_INDIVIDUAL;
    }

    // Axioms
    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    @Override
    public void visit(SWRLRule rule) {
        type = AXIOM_TYPE_INDEX_BASE + rule.getAxiomType().getIndex();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        type = AXIOM_TYPE_INDEX_BASE + axiom.getAxiomType().getIndex();
    }

    // Anon class expressions
    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 1;
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 2;
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 3;
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 4;
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 5;
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 6;
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 7;
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 8;
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 9;
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 10;
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 11;
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 12;
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 13;
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 14;
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 15;
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 16;
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        type = CLASS_EXPRESSION_TYPE_INDEX_BASE + 17;
    }

    // Data types and data values
    @Override
    public void visit(OWLDatatype node) {
        type = DATA_TYPE_INDEX_BASE + 1;
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        type = DATA_TYPE_INDEX_BASE + 2;
    }

    @Override
    public void visit(OWLDataOneOf node) {
        type = DATA_TYPE_INDEX_BASE + 3;
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        type = DATA_TYPE_INDEX_BASE + 4;
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        type = AXIOM_TYPE_INDEX_BASE + 5;
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        type = DATA_TYPE_INDEX_BASE + 6;
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        type = DATA_TYPE_INDEX_BASE + 7;
    }

    @Override
    public void visit(OWLLiteral node) {
        type = DATA_TYPE_INDEX_BASE + 8;
    }

    // Annotations
    @Override
    public void visit(OWLAnnotation node) {
        type = ANNOTATION_TYPE_INDEX_BASE + 1;
    }

    // Rule objects
    @Override
    public void visit(SWRLClassAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 1;
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 2;
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 3;
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 4;
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 5;
    }

    @Override
    public void visit(SWRLVariable node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 6;
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 7;
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 8;
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 9;
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        type = RULE_OBJECT_TYPE_INDEX_BASE + 10;
    }
}
