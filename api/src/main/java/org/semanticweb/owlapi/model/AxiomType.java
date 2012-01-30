/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * </p>
 * Represents the type of axioms which can belong to ontologies.  Axioms can be retrieved from ontologies
 * by their <code>AxiomType</code>.  For example, see {@link org.semanticweb.owlapi.model.OWLOntology#getAxioms(AxiomType)} and
 * {@link org.semanticweb.owlapi.model.OWLOntology#getAxiomCount(AxiomType, boolean)}.
 */
@SuppressWarnings("javadoc")
public class AxiomType<C extends OWLAxiom> implements Serializable {


	private static final long serialVersionUID = -8371103622896763859L;

    private final String name;

    private final boolean owl2Axiom;

    private final boolean nonSyntacticOWL2Axiom;

    private final boolean isLogical;

    public final int index;

    public static final Set<AxiomType<?>> AXIOM_TYPES= new HashSet<AxiomType<?>>();

    private static final Map<String, AxiomType<?>> NAME_TYPE_MAP= new HashMap<String, AxiomType<?>>();
    private static final AtomicInteger counter=new AtomicInteger();

//    private static int count = 0;


    private AxiomType(int ind, String name, boolean owl2Axiom, boolean nonSyntacticOWL2Axiom, boolean isLogical) {
        this.name = name;
        this.owl2Axiom = owl2Axiom;
        this.nonSyntacticOWL2Axiom = nonSyntacticOWL2Axiom;
        this.isLogical = isLogical;
        index = ind;
        //count++;
    }

    private static <O extends OWLAxiom> AxiomType<O> getInstance(String name, boolean owl2Axiom, boolean nonSyntacticOWL2Axiom, boolean isLogical ){
    	return new AxiomType<O>(counter.getAndIncrement(), name, owl2Axiom, nonSyntacticOWL2Axiom, isLogical);
    }

    @Override
	public String toString() {
        return name;
    }

    /**
     * Determines if this axiom is structurally an OWL 2 axiom.
     * @return <code>true</code> if this axiom is an OWL 2 axiom, <code>false</code> if this axiom is not an OWL 2
     * axiom and it can be represented using OWL 1.
     */
    public boolean isOWL2Axiom() {
        return owl2Axiom;
    }

    /**
     * Some OWL 2 axioms, for example, {@link org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom} axioms
     * are structurally OWL 2 axioms, but can be represented using OWL 1 syntax. This method determines if this axiom type
     * is a pure OWL 2 axiom and cannot be represented using OWL 1 syntax.
     * @return <code>true</code> if this axiom is a pure OWL 2 axiom and cannot be represented using OWL 1 syntax, otherwise
     * <code>false</code>.
     */
    public boolean isNonSyntacticOWL2Axiom() {
        return nonSyntacticOWL2Axiom;
    }


    public int getIndex() {
        return index;
    }


    public String getName() {
        return name;
    }

    /**
     * Determines if this axiom type is a logical axiom type.
     * @return <code>true</code> if this axiom type is a logical axiom type, otherwise false;
     */
    public boolean isLogical() {
        return isLogical;
    }

    /**
     * Gets the set of axioms from a source set of axioms that are not of the specified type
     * @param sourceAxioms The source set of axioms
     * @param axiomType    The types that will be filtered out of the source set
     * @return A set of axioms that represents the sourceAxioms without the specified types.  Note that sourceAxioms
     *         will not be modified.  The returned set is a copy.
     */
    public static Set<OWLAxiom> getAxiomsWithoutTypes(Set<OWLAxiom> sourceAxioms, AxiomType<?>... axiomTypes) {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        Set<AxiomType<?>> disallowed=new HashSet<AxiomType<?>>();
        for(AxiomType<?> t:axiomTypes) {
        	disallowed.add(t);
        }
        for (OWLAxiom ax:sourceAxioms) {
            if (!disallowed.contains(ax.getAxiomType())) {
                result.add(ax);
            }
        }
        return result;
    }

    /**
     * Gets the set of axioms from a source set of axioms that have a specified type
     * @param sourceAxioms The source set of axioms
     * @param axiomType    The types of axioms that will be returned
     * @return A set of axioms that represents the sourceAxioms that have the specified types.  Note that sourceAxioms
     *         will not be modified.  The returned set is a copy.
     */
    public static Set<OWLAxiom> getAxiomsOfTypes(Set<OWLAxiom> sourceAxioms, AxiomType<?>... axiomTypes) {
    	Set<OWLAxiom> result = new HashSet<OWLAxiom>();
    	Set<AxiomType<?>> allowed=new HashSet<AxiomType<?>>();
        for(AxiomType<?> t:axiomTypes) {
        	allowed.add(t);
        }
        for (OWLAxiom ax:sourceAxioms) {
            if (allowed.contains(ax.getAxiomType())) {
                result.add(ax);
            }
        }
        return result;
    }

    /**
     * Gets an axiom type by its name
     * @param name The name of the axiom type
     * @return The axiom type with the specified name, or <code>null</code> if there is no such axiom type with the
     * specified name
     */
    public static AxiomType<?> getAxiomType(String name) {
        return NAME_TYPE_MAP.get(name);
    }

    /**
     * Determines if there is an axiom type with the specified name
     * @param _name The name to test for
     * @return <code>true</code> if there is an axiom type with the specified name, or <code>false</code> if there
     * is no axiom type with the specified name.
     */
    public boolean isAxiomType(String _name) {
        return NAME_TYPE_MAP.containsKey(_name);
    }


    public static final AxiomType<OWLDeclarationAxiom> DECLARATION = getInstance("Declaration", true, true, false);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Class axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final AxiomType<OWLEquivalentClassesAxiom> EQUIVALENT_CLASSES = getInstance("EquivalentClasses", false, false, true);

    public static final AxiomType<OWLSubClassOfAxiom> SUBCLASS_OF = getInstance("SubClassOf", false, false, true);

    public static final AxiomType<OWLDisjointClassesAxiom> DISJOINT_CLASSES = getInstance("DisjointClasses", false, false, true);

    public static final AxiomType<OWLDisjointUnionAxiom> DISJOINT_UNION = getInstance("DisjointUnion", true, false, true);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Individual axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public static final AxiomType<OWLClassAssertionAxiom> CLASS_ASSERTION = getInstance("ClassAssertion", false, false, true);

    public static final AxiomType<OWLSameIndividualAxiom> SAME_INDIVIDUAL = getInstance("SameIndividual", false, false, true);

    public static final AxiomType<OWLDifferentIndividualsAxiom> DIFFERENT_INDIVIDUALS = getInstance("DifferentIndividuals", false, false, true);

    public static final AxiomType<OWLObjectPropertyAssertionAxiom> OBJECT_PROPERTY_ASSERTION = getInstance("ObjectPropertyAssertion", false, false, true);

    public static final AxiomType<OWLNegativeObjectPropertyAssertionAxiom> NEGATIVE_OBJECT_PROPERTY_ASSERTION = getInstance("NegativeObjectPropertyAssertion", true, false, true);

    public static final AxiomType<OWLDataPropertyAssertionAxiom> DATA_PROPERTY_ASSERTION = getInstance("DataPropertyAssertion", false, false, true);

    public static final AxiomType<OWLNegativeDataPropertyAssertionAxiom> NEGATIVE_DATA_PROPERTY_ASSERTION = getInstance("NegativeDataPropertyAssertion", true, false, true);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object property axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final AxiomType<OWLEquivalentObjectPropertiesAxiom> EQUIVALENT_OBJECT_PROPERTIES = getInstance("EquivalentObjectProperties", false, false, true);

    public static final AxiomType<OWLSubObjectPropertyOfAxiom> SUB_OBJECT_PROPERTY = getInstance("SubObjectPropertyOf", false, false, true);

    public static final AxiomType<OWLInverseObjectPropertiesAxiom> INVERSE_OBJECT_PROPERTIES = getInstance("InverseObjectProperties", false, false, true);

    public static final AxiomType<OWLFunctionalObjectPropertyAxiom> FUNCTIONAL_OBJECT_PROPERTY = getInstance("FunctionalObjectProperty", false, false, true);

    public static final AxiomType<OWLInverseFunctionalObjectPropertyAxiom> INVERSE_FUNCTIONAL_OBJECT_PROPERTY = getInstance("InverseFunctionalObjectProperty", false, false, true);

    public static final AxiomType<OWLSymmetricObjectPropertyAxiom> SYMMETRIC_OBJECT_PROPERTY = getInstance("SymmetricObjectProperty", false, false, true);

    public static final AxiomType<OWLAsymmetricObjectPropertyAxiom> ASYMMETRIC_OBJECT_PROPERTY = getInstance("AsymmetricObjectProperty", true, true, true);

    public static final AxiomType<OWLTransitiveObjectPropertyAxiom> TRANSITIVE_OBJECT_PROPERTY = getInstance("TransitiveObjectProperty", false, false, true);

    public static final AxiomType<OWLReflexiveObjectPropertyAxiom> REFLEXIVE_OBJECT_PROPERTY = getInstance("ReflexiveObjectProperty", true, true, true);

    public static final AxiomType<OWLIrreflexiveObjectPropertyAxiom> IRREFLEXIVE_OBJECT_PROPERTY = getInstance("IrrefexiveObjectProperty", true, true, true);

    public static final AxiomType<OWLObjectPropertyDomainAxiom> OBJECT_PROPERTY_DOMAIN = getInstance("ObjectPropertyDomain", false, false, true);

    public static final AxiomType<OWLObjectPropertyRangeAxiom> OBJECT_PROPERTY_RANGE = getInstance("ObjectPropertyRange", false, false, true);

    public static final AxiomType<OWLDisjointObjectPropertiesAxiom> DISJOINT_OBJECT_PROPERTIES = getInstance("DisjointObjectProperties", true, true, true);

    public static final AxiomType<OWLSubPropertyChainOfAxiom> SUB_PROPERTY_CHAIN_OF = getInstance("SubPropertyChainOf", true, true, true);


    public static final AxiomType<OWLEquivalentDataPropertiesAxiom> EQUIVALENT_DATA_PROPERTIES = getInstance("EquivalentDataProperties", false, false, true);

    public static final AxiomType<OWLSubDataPropertyOfAxiom> SUB_DATA_PROPERTY = getInstance("SubDataPropertyOf", false, false, true);

    public static final AxiomType<OWLFunctionalDataPropertyAxiom> FUNCTIONAL_DATA_PROPERTY = getInstance("FunctionalDataProperty", false, false, true);

    public static final AxiomType<OWLDataPropertyDomainAxiom> DATA_PROPERTY_DOMAIN = getInstance("DataPropertyDomain", false, false, true);

    public static final AxiomType<OWLDataPropertyRangeAxiom> DATA_PROPERTY_RANGE = getInstance("DataPropertyRange", false, false, true);

    public static final AxiomType<OWLDisjointDataPropertiesAxiom> DISJOINT_DATA_PROPERTIES = getInstance("DisjointDataProperties", true, true, true);

    public static final AxiomType<OWLHasKeyAxiom> HAS_KEY = getInstance("HasKey", true, true, true);

    public static final AxiomType<SWRLRule> SWRL_RULE = getInstance("Rule", false, false, true);


    public static final AxiomType<OWLAnnotationAssertionAxiom> ANNOTATION_ASSERTION = getInstance("AnnotationAssertion", false, false, false);

    public static final AxiomType<OWLSubAnnotationPropertyOfAxiom> SUB_ANNOTATION_PROPERTY_OF = getInstance("SubAnnotationPropertyOf", true, true, false);

    public static final AxiomType<OWLAnnotationPropertyRangeAxiom> ANNOTATION_PROPERTY_RANGE = getInstance("AnnotationPropertyRangeOf", true, true, false);


    public static final AxiomType<OWLAnnotationPropertyDomainAxiom> ANNOTATION_PROPERTY_DOMAIN = getInstance("AnnotationPropertyDomain", true, true, false);

    public static final AxiomType<OWLDatatypeDefinitionAxiom> DATATYPE_DEFINITION = getInstance("DatatypeDefinition", true, true, true);

    static {

        AXIOM_TYPES.add(SUBCLASS_OF);
        AXIOM_TYPES.add(EQUIVALENT_CLASSES);
        AXIOM_TYPES.add(DISJOINT_CLASSES);
        AXIOM_TYPES.add(CLASS_ASSERTION);
        AXIOM_TYPES.add(SAME_INDIVIDUAL);
        AXIOM_TYPES.add(DIFFERENT_INDIVIDUALS);
        AXIOM_TYPES.add(OBJECT_PROPERTY_ASSERTION);
        AXIOM_TYPES.add(NEGATIVE_OBJECT_PROPERTY_ASSERTION);
        AXIOM_TYPES.add(DATA_PROPERTY_ASSERTION);
        AXIOM_TYPES.add(NEGATIVE_DATA_PROPERTY_ASSERTION);
        AXIOM_TYPES.add(OBJECT_PROPERTY_DOMAIN);
        AXIOM_TYPES.add(OBJECT_PROPERTY_RANGE);
        AXIOM_TYPES.add(DISJOINT_OBJECT_PROPERTIES);
        AXIOM_TYPES.add(SUB_OBJECT_PROPERTY);
        AXIOM_TYPES.add(EQUIVALENT_OBJECT_PROPERTIES);
        AXIOM_TYPES.add(INVERSE_OBJECT_PROPERTIES);
        AXIOM_TYPES.add(SUB_PROPERTY_CHAIN_OF);
        AXIOM_TYPES.add(FUNCTIONAL_OBJECT_PROPERTY);
        AXIOM_TYPES.add(INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        AXIOM_TYPES.add(SYMMETRIC_OBJECT_PROPERTY);
        AXIOM_TYPES.add(ASYMMETRIC_OBJECT_PROPERTY);
        AXIOM_TYPES.add(TRANSITIVE_OBJECT_PROPERTY);
        AXIOM_TYPES.add(REFLEXIVE_OBJECT_PROPERTY);
        AXIOM_TYPES.add(IRREFLEXIVE_OBJECT_PROPERTY);
        AXIOM_TYPES.add(DATA_PROPERTY_DOMAIN);
        AXIOM_TYPES.add(DATA_PROPERTY_RANGE);
        AXIOM_TYPES.add(DISJOINT_DATA_PROPERTIES);
        AXIOM_TYPES.add(SUB_DATA_PROPERTY);
        AXIOM_TYPES.add(EQUIVALENT_DATA_PROPERTIES);
        AXIOM_TYPES.add(FUNCTIONAL_DATA_PROPERTY);
        AXIOM_TYPES.add(DATATYPE_DEFINITION);
        AXIOM_TYPES.add(DISJOINT_UNION);
        AXIOM_TYPES.add(DECLARATION);
        AXIOM_TYPES.add(SWRL_RULE);
        AXIOM_TYPES.add(ANNOTATION_ASSERTION);
        AXIOM_TYPES.add(SUB_ANNOTATION_PROPERTY_OF);
        AXIOM_TYPES.add(ANNOTATION_PROPERTY_DOMAIN);
        AXIOM_TYPES.add(ANNOTATION_PROPERTY_RANGE);
        AXIOM_TYPES.add(HAS_KEY);


        for(AxiomType<?> type : AXIOM_TYPES) {
            NAME_TYPE_MAP.put(type.name, type);
        }
    }

	@SuppressWarnings("unchecked")
	public static final Set<AxiomType<?>> TBoxAxiomTypes = new HashSet<AxiomType<?>>(
			Arrays.asList(SUBCLASS_OF, EQUIVALENT_CLASSES, DISJOINT_CLASSES,
					OBJECT_PROPERTY_DOMAIN, OBJECT_PROPERTY_RANGE,
					INVERSE_OBJECT_PROPERTIES, FUNCTIONAL_OBJECT_PROPERTY,
					INVERSE_FUNCTIONAL_OBJECT_PROPERTY,
					SYMMETRIC_OBJECT_PROPERTY, ASYMMETRIC_OBJECT_PROPERTY,
					TRANSITIVE_OBJECT_PROPERTY, REFLEXIVE_OBJECT_PROPERTY,
					IRREFLEXIVE_OBJECT_PROPERTY, DATA_PROPERTY_DOMAIN,
					DATA_PROPERTY_RANGE, FUNCTIONAL_DATA_PROPERTY,
					DATATYPE_DEFINITION, DISJOINT_UNION, HAS_KEY));
	@SuppressWarnings("unchecked")
	public static final Set<AxiomType<?>> ABoxAxiomTypes = new HashSet<AxiomType<?>>(
			Arrays.asList(CLASS_ASSERTION, SAME_INDIVIDUAL,
					DIFFERENT_INDIVIDUALS, OBJECT_PROPERTY_ASSERTION,
					NEGATIVE_OBJECT_PROPERTY_ASSERTION,
					DATA_PROPERTY_ASSERTION, NEGATIVE_DATA_PROPERTY_ASSERTION,
					DATATYPE_DEFINITION));
	@SuppressWarnings("unchecked")
	public static final Set<AxiomType<?>> RBoxAxiomTypes = new HashSet<AxiomType<?>>(
			Arrays.asList(TRANSITIVE_OBJECT_PROPERTY, DISJOINT_DATA_PROPERTIES,
					SUB_DATA_PROPERTY, EQUIVALENT_DATA_PROPERTIES,
					DISJOINT_OBJECT_PROPERTIES, SUB_OBJECT_PROPERTY,
					EQUIVALENT_OBJECT_PROPERTIES, SUB_PROPERTY_CHAIN_OF));
}
