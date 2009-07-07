package org.semanticweb.owlapi.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
 * Date: 27-Jul-2007<br><br>
 * Represents the type of axioms which can belong to ontologies
 */
public class AxiomType<C extends OWLAxiom> {

    private String name;

    private boolean owl2Axiom;

    private boolean nonSyntacticOWL11Axiom;

    private boolean isLogical;

    final public int index;

    public static final Set<AxiomType> AXIOM_TYPES;

    private static int count = 0;


    private AxiomType(String name, boolean owl2Axiom, boolean nonSyntacticOWL11Axiom, boolean isLogical) {
        this.name = name;
        this.owl2Axiom = owl2Axiom;
        this.nonSyntacticOWL11Axiom = nonSyntacticOWL11Axiom;
        this.isLogical = isLogical;
        index = count;
        count++;
    }


    public String toString() {
        return name + " axiom";
    }


    public boolean isOWL2Axiom() {
        return owl2Axiom;
    }


    public boolean isNonSyntacticOWL11Axiom() {
        return nonSyntacticOWL11Axiom;
    }


    public int getIndex() {
        return index;
    }


    public String getName() {
        return name;
    }

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
    public static Set<OWLAxiom> getAxiomsWithoutTypes(Set<OWLAxiom> sourceAxioms, AxiomType... axiomType) {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>(sourceAxioms);
        for (Iterator<OWLAxiom> it = result.iterator(); it.hasNext();) {
            if (it.next().isOfType(axiomType)) {
                it.remove();
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
    public static Set<OWLAxiom> getAxiomsOfTypes(Set<OWLAxiom> sourceAxioms, AxiomType... axiomType) {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>(sourceAxioms);
        for (Iterator<OWLAxiom> it = result.iterator(); it.hasNext();) {
            if (!it.next().isOfType(axiomType)) {
                it.remove();
            }
        }
        return result;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Class axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final AxiomType<OWLEquivalentClassesAxiom> EQUIVALENT_CLASSES = new AxiomType<OWLEquivalentClassesAxiom>("EquivalentClasses", false, false, true);

    public static final AxiomType<OWLSubClassOfAxiom> SUBCLASS = new AxiomType<OWLSubClassOfAxiom>("SubClassOf", false, false, true);

    public static final AxiomType<OWLDisjointClassesAxiom> DISJOINT_CLASSES = new AxiomType<OWLDisjointClassesAxiom>("DisjointClasses", false, false, true);


    public static final AxiomType<OWLDisjointUnionAxiom> DISJOINT_UNION = new AxiomType<OWLDisjointUnionAxiom>("DisjointUnion", true, false, true);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Individual axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public static final AxiomType<OWLClassAssertionAxiom> CLASS_ASSERTION = new AxiomType<OWLClassAssertionAxiom>("ClassAssertion", false, false, true);

    public static final AxiomType<OWLSameIndividualAxiom> SAME_INDIVIDUAL = new AxiomType<OWLSameIndividualAxiom>("SameIndividual", false, false, true);

    public static final AxiomType<OWLDifferentIndividualsAxiom> DIFFERENT_INDIVIDUALS = new AxiomType<OWLDifferentIndividualsAxiom>("DifferentIndividuals", false, false, true);

    public static final AxiomType<OWLObjectPropertyAssertionAxiom> OBJECT_PROPERTY_ASSERTION = new AxiomType<OWLObjectPropertyAssertionAxiom>("ObjectPropertyAssertion", false, false, true);

    public static final AxiomType<OWLNegativeObjectPropertyAssertionAxiom> NEGATIVE_OBJECT_PROPERTY_ASSERTION = new AxiomType<OWLNegativeObjectPropertyAssertionAxiom>("NegativeObjectPropertyAssertion", true, false, true);

    public static final AxiomType<OWLDataPropertyAssertionAxiom> DATA_PROPERTY_ASSERTION = new AxiomType<OWLDataPropertyAssertionAxiom>("DataPropertyAssertion", false, false, true);

    public static final AxiomType<OWLNegativeDataPropertyAssertionAxiom> NEGATIVE_DATA_PROPERTY_ASSERTION = new AxiomType<OWLNegativeDataPropertyAssertionAxiom>("NegativeDataPropertyAssertion", true, false, true);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object property axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final AxiomType<OWLEquivalentObjectPropertiesAxiom> EQUIVALENT_OBJECT_PROPERTIES = new AxiomType<OWLEquivalentObjectPropertiesAxiom>("EquivalentObjectProperties", false, false, true);

    public static final AxiomType<OWLSubObjectPropertyOfAxiom> SUB_OBJECT_PROPERTY = new AxiomType<OWLSubObjectPropertyOfAxiom>("SubObjectPropertyOf", false, false, true);

    public static final AxiomType<OWLInverseObjectPropertiesAxiom> INVERSE_OBJECT_PROPERTIES = new AxiomType<OWLInverseObjectPropertiesAxiom>("InverseObjectProperties", false, false, true);

    public static final AxiomType<OWLFunctionalObjectPropertyAxiom> FUNCTIONAL_OBJECT_PROPERTY = new AxiomType<OWLFunctionalObjectPropertyAxiom>("FunctionalObjectProperty", false, false, true);

    public static final AxiomType<OWLInverseFunctionalObjectPropertyAxiom> INVERSE_FUNCTIONAL_OBJECT_PROPERTY = new AxiomType<OWLInverseFunctionalObjectPropertyAxiom>("InverseFunctionalObjectProperty", false, false, true);

    public static final AxiomType<OWLSymmetricObjectPropertyAxiom> SYMMETRIC_OBJECT_PROPERTY = new AxiomType<OWLSymmetricObjectPropertyAxiom>("SymmetricObjectProperty", false, false, true);

    public static final AxiomType<OWLAsymmetricObjectPropertyAxiom> ASYMMETRIC_OBJECT_PROPERTY = new AxiomType<OWLAsymmetricObjectPropertyAxiom>("AsymmetricObjectProperty", true, true, true);

    public static final AxiomType<OWLTransitiveObjectPropertyAxiom> TRANSITIVE_OBJECT_PROPERTY = new AxiomType<OWLTransitiveObjectPropertyAxiom>("TransitiveObjectProperty", false, false, true);

    public static final AxiomType<OWLReflexiveObjectPropertyAxiom> REFLEXIVE_OBJECT_PROPERTY = new AxiomType<OWLReflexiveObjectPropertyAxiom>("ReflexiveObjectProperty", true, true, true);

    public static final AxiomType<OWLIrreflexiveObjectPropertyAxiom> IRREFLEXIVE_OBJECT_PROPERTY = new AxiomType<OWLIrreflexiveObjectPropertyAxiom>("IrrefexiveObjectProperty", true, true, true);

    public static final AxiomType<OWLObjectPropertyDomainAxiom> OBJECT_PROPERTY_DOMAIN = new AxiomType<OWLObjectPropertyDomainAxiom>("ObjectPropertyDomain", false, false, true);

    public static final AxiomType<OWLObjectPropertyRangeAxiom> OBJECT_PROPERTY_RANGE = new AxiomType<OWLObjectPropertyRangeAxiom>("ObjectPropertyRange", false, false, true);

    public static final AxiomType<OWLDisjointObjectPropertiesAxiom> DISJOINT_OBJECT_PROPERTIES = new AxiomType<OWLDisjointObjectPropertiesAxiom>("DisjointObjectProperties", true, true, true);

    public static final AxiomType<OWLSubPropertyChainOfAxiom> SUB_PROPERTY_CHAIN_OF = new AxiomType<OWLSubPropertyChainOfAxiom>("SubPropertyChainOf", true, true, true);


    public static final AxiomType<OWLEquivalentDataPropertiesAxiom> EQUIVALENT_DATA_PROPERTIES = new AxiomType<OWLEquivalentDataPropertiesAxiom>("EquivalentDataProperties", false, false, true);

    public static final AxiomType<OWLSubDataPropertyOfAxiom> SUB_DATA_PROPERTY = new AxiomType<OWLSubDataPropertyOfAxiom>("SubDataPropertyOf", false, false, true);

    public static final AxiomType<OWLFunctionalDataPropertyAxiom> FUNCTIONAL_DATA_PROPERTY = new AxiomType<OWLFunctionalDataPropertyAxiom>("FunctionalDataProperty", false, false, true);

    public static final AxiomType<OWLDataPropertyDomainAxiom> DATA_PROPERTY_DOMAIN = new AxiomType<OWLDataPropertyDomainAxiom>("DataPropertyDomain", false, false, true);

    public static final AxiomType<OWLDataPropertyRangeAxiom> DATA_PROPERTY_RANGE = new AxiomType<OWLDataPropertyRangeAxiom>("DataPropertyRange", false, false, true);

    public static final AxiomType<OWLDisjointDataPropertiesAxiom> DISJOINT_DATA_PROPERTIES = new AxiomType<OWLDisjointDataPropertiesAxiom>("DisjointDataProperties", true, true, true);


    public static final AxiomType<OWLHasKeyAxiom> HAS_KEY = new AxiomType<OWLHasKeyAxiom>("HasKey", true, true, true);


    public static final AxiomType<OWLDeclarationAxiom> DECLARATION = new AxiomType<OWLDeclarationAxiom>("Declaration", true, true, false);

    public static final AxiomType<SWRLRule> SWRL_RULE = new AxiomType<SWRLRule>("Rule", false, false, true);


    public static final AxiomType<OWLAnnotationAssertionAxiom> ANNOTATION_ASSERTION = new AxiomType<OWLAnnotationAssertionAxiom>("AnnotationAssertion", false, false, false);

    public static final AxiomType<OWLSubAnnotationPropertyOfAxiom> SUB_ANNOTATION_PROPERTY_OF = new AxiomType<OWLSubAnnotationPropertyOfAxiom>("SubAnnotationPropertyOf", true, true, false);

    public static final AxiomType<OWLAnnotationPropertyRangeAxiom> ANNOTATION_PROPERTY_RANGE = new AxiomType<OWLAnnotationPropertyRangeAxiom>("AnnotationPropertyRangeOf", true, true, false);


    public static final AxiomType<OWLAnnotationPropertyDomainAxiom> ANNOTATION_PROPERTY_DOMAIN = new AxiomType<OWLAnnotationPropertyDomainAxiom>("AnnotationPropertyDomain", true, true, false);

    public static final AxiomType<OWLDatatypeDefinitionAxiom> DATATYPE_DEFINITION = new AxiomType<OWLDatatypeDefinitionAxiom>("DatatypeDefinition", true, true, true);

    static {
        AXIOM_TYPES = new HashSet<AxiomType>();
        AXIOM_TYPES.add(SUBCLASS);
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

    }


}
