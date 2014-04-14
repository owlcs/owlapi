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
package org.semanticweb.owlapi.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Represents the type of axioms which can belong to ontologies. Axioms can be
 * retrieved from ontologies by their {@code AxiomType}. For example, see
 * {@link org.semanticweb.owlapi.model.OWLOntology#getAxioms(AxiomType)} and
 * {@link org.semanticweb.owlapi.model.OWLOntology#getAxiomCount(AxiomType, Imports)}
 * .
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.0
 * @param <C>
 *        axiom type
 */
@SuppressWarnings("unchecked")
public class AxiomType<C extends OWLAxiom> implements Serializable {

    private static final long serialVersionUID = 40000L;
    private final String name;
    private final boolean owl2Axiom;
    private final boolean nonSyntacticOWL2Axiom;
    private final boolean isLogical;
    private final int index;

    private AxiomType(int ind, String name, boolean owl2Axiom,
            boolean nonSyntacticOWL2Axiom, boolean isLogical) {
        this.name = name;
        this.owl2Axiom = owl2Axiom;
        this.nonSyntacticOWL2Axiom = nonSyntacticOWL2Axiom;
        this.isLogical = isLogical;
        index = ind;
    }

    private static <O extends OWLAxiom> AxiomType<O> getInstance(int i,
            String name, boolean owl2Axiom, boolean nonSyntacticOWL2Axiom,
            boolean isLogical) {
        return new AxiomType<O>(i, name, owl2Axiom, nonSyntacticOWL2Axiom,
                isLogical);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Determines if this axiom is structurally an OWL 2 axiom.
     * 
     * @return {@code true} if this axiom is an OWL 2 axiom, {@code false} if
     *         this axiom is not an OWL 2 axiom and it can be represented using
     *         OWL 1.
     */
    public boolean isOWL2Axiom() {
        return owl2Axiom;
    }

    /**
     * Some OWL 2 axioms, for example,
     * {@link org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom}
     * axioms are structurally OWL 2 axioms, but can be represented using OWL 1
     * syntax. This method determines if this axiom type is a pure OWL 2 axiom
     * and cannot be represented using OWL 1 syntax.
     * 
     * @return {@code true} if this axiom is a pure OWL 2 axiom and cannot be
     *         represented using OWL 1 syntax, otherwise {@code false}.
     */
    public boolean isNonSyntacticOWL2Axiom() {
        return nonSyntacticOWL2Axiom;
    }

    /** @return index in the axiom type list */
    public int getIndex() {
        return index;
    }

    /** @return axiom type name */
    public String getName() {
        return name;
    }

    /**
     * Determines if this axiom type is a logical axiom type.
     * 
     * @return {@code true} if this axiom type is a logical axiom type,
     *         otherwise false;
     */
    public boolean isLogical() {
        return isLogical;
    }

    /**
     * Gets the set of axioms from a source set of axioms that are not of the
     * specified type
     * 
     * @param sourceAxioms
     *        The source set of axioms
     * @param axiomTypes
     *        The types that will be filtered out of the source set
     * @return A set of axioms that represents the sourceAxioms without the
     *         specified types. Note that sourceAxioms will not be modified. The
     *         returned set is a copy.
     */
    public static Set<OWLAxiom> getAxiomsWithoutTypes(
            Set<OWLAxiom> sourceAxioms, AxiomType<?>... axiomTypes) {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        Set<AxiomType<?>> disallowed = new HashSet<AxiomType<?>>();
        for (AxiomType<?> t : axiomTypes) {
            disallowed.add(t);
        }
        for (OWLAxiom ax : sourceAxioms) {
            if (!disallowed.contains(ax.getAxiomType())) {
                result.add(ax);
            }
        }
        return result;
    }

    /**
     * Gets the set of axioms from a source set of axioms that have a specified
     * type
     * 
     * @param sourceAxioms
     *        The source set of axioms
     * @param axiomTypes
     *        The types of axioms that will be returned
     * @return A set of axioms that represents the sourceAxioms that have the
     *         specified types. Note that sourceAxioms will not be modified. The
     *         returned set is a copy.
     */
    public static Set<OWLAxiom> getAxiomsOfTypes(Set<OWLAxiom> sourceAxioms,
            AxiomType<?>... axiomTypes) {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        Set<AxiomType<?>> allowed = new HashSet<AxiomType<?>>();
        for (AxiomType<?> t : axiomTypes) {
            allowed.add(t);
        }
        for (OWLAxiom ax : sourceAxioms) {
            if (allowed.contains(ax.getAxiomType())) {
                result.add(ax);
            }
        }
        return result;
    }

    /**
     * Gets an axiom type by its name
     * 
     * @param name
     *        The name of the axiom type
     * @return The axiom type with the specified name, or {@code null} if there
     *         is no such axiom type with the specified name
     */
    public static AxiomType<?> getAxiomType(String name) {
        return NAME_TYPE_MAP.get(name);
    }

    /**
     * Determines if there is an axiom type with the specified name
     * 
     * @param _name
     *        The name to test for
     * @return {@code true} if there is an axiom type with the specified name,
     *         or {@code false} if there is no axiom type with the specified
     *         name.
     */
    public boolean isAxiomType(String _name) {
        return NAME_TYPE_MAP.containsKey(_name);
    }

//@formatter:off
    /** Declaration */                      public static final AxiomType<OWLDeclarationAxiom>                      DECLARATION                         = getInstance(0, "Declaration",                      true, true, false);

    /** EquivalentClasses */                public static final AxiomType<OWLEquivalentClassesAxiom>                EQUIVALENT_CLASSES                  = getInstance(1, "EquivalentClasses",                false, false, true);
    /** SubClassOf */                       public static final AxiomType<OWLSubClassOfAxiom>                       SUBCLASS_OF                         = getInstance(2, "SubClassOf",                       false, false, true);
    /** DisjointClasses */                  public static final AxiomType<OWLDisjointClassesAxiom>                  DISJOINT_CLASSES                    = getInstance(3, "DisjointClasses",                  false, false, true);
    /** DisjointUnion */                    public static final AxiomType<OWLDisjointUnionAxiom>                    DISJOINT_UNION                      = getInstance(4, "DisjointUnion",                    true, false, true);

    /** ClassAssertion */                   public static final AxiomType<OWLClassAssertionAxiom>                   CLASS_ASSERTION                     = getInstance(5, "ClassAssertion",                   false, false, true);
    /** SameIndividual */                   public static final AxiomType<OWLSameIndividualAxiom>                   SAME_INDIVIDUAL                     = getInstance(6, "SameIndividual",                   false, false, true);
    /** DifferentIndividuals */             public static final AxiomType<OWLDifferentIndividualsAxiom>             DIFFERENT_INDIVIDUALS               = getInstance(7, "DifferentIndividuals",             false, false, true);
    /** ObjectPropertyAssertion */          public static final AxiomType<OWLObjectPropertyAssertionAxiom>          OBJECT_PROPERTY_ASSERTION           = getInstance(8, "ObjectPropertyAssertion",          false, false, true);
    /** NegativeObjectPropertyAssertion */  public static final AxiomType<OWLNegativeObjectPropertyAssertionAxiom>  NEGATIVE_OBJECT_PROPERTY_ASSERTION  = getInstance(9, "NegativeObjectPropertyAssertion",  true, false, true);
    /** DataPropertyAssertion */            public static final AxiomType<OWLDataPropertyAssertionAxiom>            DATA_PROPERTY_ASSERTION             = getInstance(10, "DataPropertyAssertion",           false, false, true);
    /** NegativeDataPropertyAssertion */    public static final AxiomType<OWLNegativeDataPropertyAssertionAxiom>    NEGATIVE_DATA_PROPERTY_ASSERTION    = getInstance(11, "NegativeDataPropertyAssertion",   true, false, true);
  
    /** EquivalentObjectProperties */       public static final AxiomType<OWLEquivalentObjectPropertiesAxiom>       EQUIVALENT_OBJECT_PROPERTIES        = getInstance(12, "EquivalentObjectProperties",      false, false, true);
    /** SubObjectPropertyOf */              public static final AxiomType<OWLSubObjectPropertyOfAxiom>              SUB_OBJECT_PROPERTY                 = getInstance(13, "SubObjectPropertyOf",             false, false, true);
    /** InverseObjectProperties */          public static final AxiomType<OWLInverseObjectPropertiesAxiom>          INVERSE_OBJECT_PROPERTIES           = getInstance(14, "InverseObjectProperties",         false, false, true);
    /** FunctionalObjectProperty */         public static final AxiomType<OWLFunctionalObjectPropertyAxiom>         FUNCTIONAL_OBJECT_PROPERTY          = getInstance(15, "FunctionalObjectProperty",        false, false, true);
    /** InverseFunctionalObjectProperty */  public static final AxiomType<OWLInverseFunctionalObjectPropertyAxiom>  INVERSE_FUNCTIONAL_OBJECT_PROPERTY  = getInstance(16, "InverseFunctionalObjectProperty", false, false, true);
    /** SymmetricObjectProperty */          public static final AxiomType<OWLSymmetricObjectPropertyAxiom>          SYMMETRIC_OBJECT_PROPERTY           = getInstance(17, "SymmetricObjectProperty",         false, false, true);
    /** AsymmetricObjectProperty */         public static final AxiomType<OWLAsymmetricObjectPropertyAxiom>         ASYMMETRIC_OBJECT_PROPERTY          = getInstance(18, "AsymmetricObjectProperty",        true, true, true);
    /** TransitiveObjectProperty */         public static final AxiomType<OWLTransitiveObjectPropertyAxiom>         TRANSITIVE_OBJECT_PROPERTY          = getInstance(19, "TransitiveObjectProperty",        false, false, true);
    /** ReflexiveObjectProperty */          public static final AxiomType<OWLReflexiveObjectPropertyAxiom>          REFLEXIVE_OBJECT_PROPERTY           = getInstance(20, "ReflexiveObjectProperty",         true, true, true);
    /** IrrefexiveObjectProperty */         public static final AxiomType<OWLIrreflexiveObjectPropertyAxiom>        IRREFLEXIVE_OBJECT_PROPERTY         = getInstance(21, "IrrefexiveObjectProperty",        true, true, true);
    /** ObjectPropertyDomain */             public static final AxiomType<OWLObjectPropertyDomainAxiom>             OBJECT_PROPERTY_DOMAIN              = getInstance(22, "ObjectPropertyDomain",            false, false, true);
    /** ObjectPropertyRange */              public static final AxiomType<OWLObjectPropertyRangeAxiom>              OBJECT_PROPERTY_RANGE               = getInstance(23, "ObjectPropertyRange",             false, false, true);
    /** DisjointObjectProperties */         public static final AxiomType<OWLDisjointObjectPropertiesAxiom>         DISJOINT_OBJECT_PROPERTIES          = getInstance(24, "DisjointObjectProperties",        true, true, true);
    /** SubPropertyChainOf */               public static final AxiomType<OWLSubPropertyChainOfAxiom>               SUB_PROPERTY_CHAIN_OF               = getInstance(25, "SubPropertyChainOf",              true, true, true);

    /** EquivalentDataProperties */         public static final AxiomType<OWLEquivalentDataPropertiesAxiom>         EQUIVALENT_DATA_PROPERTIES          = getInstance(26, "EquivalentDataProperties",        false, false, true);
    /** SubDataPropertyOf */                public static final AxiomType<OWLSubDataPropertyOfAxiom>                SUB_DATA_PROPERTY                   = getInstance(27, "SubDataPropertyOf",               false, false, true);
    /** FunctionalDataProperty */           public static final AxiomType<OWLFunctionalDataPropertyAxiom>           FUNCTIONAL_DATA_PROPERTY            = getInstance(28, "FunctionalDataProperty",          false, false, true);
    /** DataPropertyDomain */               public static final AxiomType<OWLDataPropertyDomainAxiom>               DATA_PROPERTY_DOMAIN                = getInstance(29, "DataPropertyDomain",              false, false, true);
    /** DataPropertyRange */                public static final AxiomType<OWLDataPropertyRangeAxiom>                DATA_PROPERTY_RANGE                 = getInstance(30, "DataPropertyRange",               false, false, true);
    /** DisjointDataProperties */           public static final AxiomType<OWLDisjointDataPropertiesAxiom>           DISJOINT_DATA_PROPERTIES            = getInstance(31, "DisjointDataProperties",          true, true, true);

    /** DatatypeDefinition */               public static final AxiomType<OWLDatatypeDefinitionAxiom>               DATATYPE_DEFINITION                 = getInstance(38, "DatatypeDefinition",              true, true, true);

    /** HasKey */                           public static final AxiomType<OWLHasKeyAxiom>                           HAS_KEY                             = getInstance(32, "HasKey",                          true, true, true);

    /** Rule */                             public static final AxiomType<SWRLRule>                                 SWRL_RULE                           = getInstance(33, "Rule",                            false, false, true);

    /** AnnotationAssertion */              public static final AxiomType<OWLAnnotationAssertionAxiom>              ANNOTATION_ASSERTION                = getInstance(34, "AnnotationAssertion",             false, false, false);
    /** SubAnnotationPropertyOf */          public static final AxiomType<OWLSubAnnotationPropertyOfAxiom>          SUB_ANNOTATION_PROPERTY_OF          = getInstance(35, "SubAnnotationPropertyOf",         true, true, false);
    /** AnnotationPropertyRangeOf */        public static final AxiomType<OWLAnnotationPropertyRangeAxiom>          ANNOTATION_PROPERTY_RANGE           = getInstance(36, "AnnotationPropertyRangeOf",       true, true, false);
    /** AnnotationPropertyDomain */         public static final AxiomType<OWLAnnotationPropertyDomainAxiom>         ANNOTATION_PROPERTY_DOMAIN          = getInstance(37, "AnnotationPropertyDomain",        true, true, false);
  //@formatter:on
    /** axiom types */
    public static final Set<AxiomType<?>> AXIOM_TYPES = Sets.newHashSet(
            SUBCLASS_OF, EQUIVALENT_CLASSES, DISJOINT_CLASSES, CLASS_ASSERTION,
            SAME_INDIVIDUAL, DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION,
            NEGATIVE_OBJECT_PROPERTY_ASSERTION, DATA_PROPERTY_ASSERTION,
            NEGATIVE_DATA_PROPERTY_ASSERTION, OBJECT_PROPERTY_DOMAIN,
            OBJECT_PROPERTY_RANGE, DISJOINT_OBJECT_PROPERTIES,
            SUB_OBJECT_PROPERTY, EQUIVALENT_OBJECT_PROPERTIES,
            INVERSE_OBJECT_PROPERTIES, SUB_PROPERTY_CHAIN_OF,
            FUNCTIONAL_OBJECT_PROPERTY, INVERSE_FUNCTIONAL_OBJECT_PROPERTY,
            SYMMETRIC_OBJECT_PROPERTY, ASYMMETRIC_OBJECT_PROPERTY,
            TRANSITIVE_OBJECT_PROPERTY, REFLEXIVE_OBJECT_PROPERTY,
            IRREFLEXIVE_OBJECT_PROPERTY, DATA_PROPERTY_DOMAIN,
            DATA_PROPERTY_RANGE, DISJOINT_DATA_PROPERTIES, SUB_DATA_PROPERTY,
            EQUIVALENT_DATA_PROPERTIES, FUNCTIONAL_DATA_PROPERTY,
            DATATYPE_DEFINITION, DISJOINT_UNION, DECLARATION, SWRL_RULE,
            ANNOTATION_ASSERTION, SUB_ANNOTATION_PROPERTY_OF,
            ANNOTATION_PROPERTY_DOMAIN, ANNOTATION_PROPERTY_RANGE, HAS_KEY);
    private static final Map<String, AxiomType<?>> NAME_TYPE_MAP = Maps
            .uniqueIndex(AXIOM_TYPES, new Function<AxiomType<?>, String>() {

                @Override
                public String apply(AxiomType<?> input) {
                    return input.getName();
                }
            });
    /** set of tbox axiom types */
    public static final Set<AxiomType<?>> TBoxAxiomTypes = Sets.newHashSet(
            (AxiomType<?>) SUBCLASS_OF, EQUIVALENT_CLASSES, DISJOINT_CLASSES,
            OBJECT_PROPERTY_DOMAIN, OBJECT_PROPERTY_RANGE,
            INVERSE_OBJECT_PROPERTIES, FUNCTIONAL_OBJECT_PROPERTY,
            INVERSE_FUNCTIONAL_OBJECT_PROPERTY, SYMMETRIC_OBJECT_PROPERTY,
            ASYMMETRIC_OBJECT_PROPERTY, TRANSITIVE_OBJECT_PROPERTY,
            REFLEXIVE_OBJECT_PROPERTY, IRREFLEXIVE_OBJECT_PROPERTY,
            DATA_PROPERTY_DOMAIN, DATA_PROPERTY_RANGE,
            FUNCTIONAL_DATA_PROPERTY, DATATYPE_DEFINITION, DISJOINT_UNION,
            HAS_KEY);
    /** set of abox axiom types */
    public static final Set<AxiomType<?>> ABoxAxiomTypes = Sets.newHashSet(
            (AxiomType<?>) CLASS_ASSERTION, SAME_INDIVIDUAL,
            DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION,
            NEGATIVE_OBJECT_PROPERTY_ASSERTION, DATA_PROPERTY_ASSERTION,
            NEGATIVE_DATA_PROPERTY_ASSERTION);
    /** set of rbox axiom types */
    public static final Set<AxiomType<?>> RBoxAxiomTypes = Sets.newHashSet(
            (AxiomType<?>) TRANSITIVE_OBJECT_PROPERTY,
            DISJOINT_DATA_PROPERTIES, SUB_DATA_PROPERTY,
            EQUIVALENT_DATA_PROPERTIES, DISJOINT_OBJECT_PROPERTIES,
            SUB_OBJECT_PROPERTY, EQUIVALENT_OBJECT_PROPERTIES,
            SUB_PROPERTY_CHAIN_OF);
    /** set of tbox and rbox axiom types */
    public static final Set<AxiomType<?>> TBoxAndRBoxAxiomTypes = Sets
            .newHashSet(Iterables.concat(TBoxAxiomTypes, RBoxAxiomTypes));
}
