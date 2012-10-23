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
 * Copyright 2011, The University of Manchester
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

package org.semanticweb.owlapi.binaryowl;

import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.binaryowl.owlobject.*;
import org.semanticweb.owlapi.model.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 06/04/2012
 */
public class OWLObjectBinaryType<C extends OWLObject> {



    private static List<OWLObjectBinaryType<?>> values = new ArrayList<OWLObjectBinaryType<?>>();



    public static final OWLObjectBinaryType<OWLOntology> OWL_ONTOLOGY = getInstance(1, OWLOntology.class, new OWLOntologySerializer());

    public static final OWLObjectBinaryType<OWLDeclarationAxiom> OWL_DECLARATION = getInstance(2, OWLDeclarationAxiom.class, new OWLDeclarationSerializer());


    public static final OWLObjectBinaryType<IRI> IRI = getInstance(3, IRI.class, new IRISerializer());


    public static final OWLObjectBinaryType<OWLClass> OWL_CLASS = getInstance(4, OWLClass.class, new OWLEntitySerializer<OWLClass>(EntityType.CLASS));

    public static final OWLObjectBinaryType<OWLObjectProperty> OWL_OBJECT_PROPERTY = getInstance(5, OWLObjectProperty.class, new OWLEntitySerializer<OWLObjectProperty>(EntityType.OBJECT_PROPERTY));

    public static final OWLObjectBinaryType<OWLDataProperty> OWL_DATA_PROPERTY = getInstance(6, OWLDataProperty.class,  new OWLEntitySerializer<OWLDataProperty>(EntityType.DATA_PROPERTY));

    public static final OWLObjectBinaryType<OWLAnnotationProperty> OWL_ANNOTATION_PROPERTY = getInstance(7, OWLAnnotationProperty.class,  new OWLEntitySerializer<OWLAnnotationProperty>(EntityType.ANNOTATION_PROPERTY));

    public static final OWLObjectBinaryType<OWLDatatype> OWL_DATATYPE = getInstance(8, OWLDatatype.class,  new OWLEntitySerializer<OWLDatatype>(EntityType.DATATYPE));

    public static final OWLObjectBinaryType<OWLNamedIndividual> OWL_NAMED_INDIVIDUAL = getInstance(9, OWLNamedIndividual.class,  new OWLEntitySerializer<OWLNamedIndividual>(EntityType.NAMED_INDIVIDUAL));

    public static final OWLObjectBinaryType<OWLAnonymousIndividual> OWL_ANONYMOUS_INDIVIDUAL = getInstance(10, OWLAnonymousIndividual.class, new OWLAnonymousIndividualSerializer());

    public static final OWLObjectBinaryType<OWLLiteral> OWL_LITERAL = getInstance(11, OWLLiteral.class, new OWLLiteralSerializer());



    public static final OWLObjectBinaryType<OWLObjectInverseOf> OWL_OBJECT_INVERSE_OF = getInstance(12, OWLObjectInverseOf.class, new OWLObjectInverseOfSerializer());

    public static final OWLObjectBinaryType<OWLDataIntersectionOf> OWL_DATA_INTERSECTION_OF = getInstance(13, OWLDataIntersectionOf.class, new OWLDataIntersectionOfSerializer());
    
    public static final OWLObjectBinaryType<OWLDataUnionOf> OWL_DATA_UNION_OF = getInstance(14, OWLDataUnionOf.class, new OWLDataUnionOfSerializer());
    
    public static final OWLObjectBinaryType<OWLDataComplementOf> OWL_DATA_COMPLEMENT_OF = getInstance(15, OWLDataComplementOf.class, new OWLDataComplementOfSerializer());
    
    public static final OWLObjectBinaryType<OWLDataOneOf> OWL_DATA_ONE_OF = getInstance(16, OWLDataOneOf.class, new OWLDataOneOfSerializer());
    
    public static final OWLObjectBinaryType<OWLDatatypeRestriction> OWL_DATATYPE_RESTRICTION = getInstance(17, OWLDatatypeRestriction.class, new OWLDatatypeRestrictionSerializer());

    public static final OWLObjectBinaryType<OWLFacetRestriction> OWL_FACET_RESTRICTION = getInstance(18, OWLFacetRestriction.class, new OWLFacetRestrictionSerializer());



    public static final OWLObjectBinaryType<OWLObjectIntersectionOf> OWL_OBJECT_INTERSECTION_OF = getInstance(19, OWLObjectIntersectionOf.class, new OWLObjectIntersectionOfSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectUnionOf> OWL_OBJECT_UNION_OF = getInstance(20, OWLObjectUnionOf.class, new OWLObjectUnionOfSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectComplementOf> OWL_OBJECT_COMPLEMENT_OF = getInstance(21, OWLObjectComplementOf.class, new OWLObjectComplementOfSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectOneOf> OWL_OBJECT_ONE_OF = getInstance(22, OWLObjectOneOf.class, new OWLObjectOneOfSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectSomeValuesFrom> OWL_OBJECT_SOME_VALUES_FROM = getInstance(23, OWLObjectSomeValuesFrom.class, new OWLObjectSomeValuesFromSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectAllValuesFrom> OWL_OBJECT_ALL_VALUES_FROM = getInstance(24, OWLObjectAllValuesFrom.class, new OWLObjectAllValuesFromSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectHasValue> OWL_OBJECT_HAS_VALUE = getInstance(25, OWLObjectHasValue.class, new OWLObjectHasValueSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectHasSelf> OWL_OBJECT_HAS_SELF = getInstance(26, OWLObjectHasSelf.class, new OWLObjectHasSelfSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectMinCardinality> OWL_OBJECT_MIN_CARDINALITY = getInstance(27, OWLObjectMinCardinality.class, new OWLObjectMinCardinalityRestrictionSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectMaxCardinality> OWL_OBJECT_MAX_CARDINALITY = getInstance(28, OWLObjectMaxCardinality.class, new OWLObjectMaxCardinalityRestrictionSerializer());
    
    public static final OWLObjectBinaryType<OWLObjectExactCardinality> OWL_OBJECT_EXACT_CARDINALITY = getInstance(29, OWLObjectExactCardinality.class, new OWLObjectExactCardinalityRestrictionSerializer());



    public static final OWLObjectBinaryType<OWLDataSomeValuesFrom> OWL_DATA_SOME_VALUES_FROM = getInstance(30, OWLDataSomeValuesFrom.class, new OWLDataSomeValuesFromSerializer());

    public static final OWLObjectBinaryType<OWLDataAllValuesFrom> OWL_DATA_ALL_VALUES_FROM = getInstance(31, OWLDataAllValuesFrom.class, new OWLDataAllValuesFromSerializer());

    public static final OWLObjectBinaryType<OWLDataHasValue> OWL_DATA_HAS_VALUE = getInstance(32, OWLDataHasValue.class, new OWLDataHasValueSerializer());

    public static final OWLObjectBinaryType<OWLDataMinCardinality> OWL_DATA_MIN_CARDINALITY = getInstance(33, OWLDataMinCardinality.class, new OWLDataMinCardinalityRestrictionSerializer());

    public static final OWLObjectBinaryType<OWLDataMaxCardinality> OWL_DATA_MAX_CARDINALITY = getInstance(34, OWLDataMaxCardinality.class, new OWLDataMaxCardinalityRestrictionSerializer());

    public static final OWLObjectBinaryType<OWLDataExactCardinality> OWL_DATA_EXACT_CARDINALITY = getInstance(35, OWLDataExactCardinality.class, new OWLDataExactCardinalityRestrictionSerializer());



    public static final OWLObjectBinaryType<OWLSubClassOfAxiom> OWL_SUBCLASS_OF = getInstance(36, OWLSubClassOfAxiom.class, new OWLSubclassOfAxiomSerializer());

    public static final OWLObjectBinaryType<OWLEquivalentClassesAxiom> OWL_EQUIVALENT_CLASSES = getInstance(37, OWLEquivalentClassesAxiom.class, new OWLEquivalentClassesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDisjointClassesAxiom> OWL_DISJOINT_CLASSES = getInstance(38, OWLDisjointClassesAxiom.class, new OWLDisjointClassesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDisjointUnionAxiom> OWL_DISJOINT_UNION = getInstance(39, OWLDisjointUnionAxiom.class, new OWLDisjointUnionAxiomSerializer());



    public static final OWLObjectBinaryType<OWLSubObjectPropertyOfAxiom> OWL_SUB_OBJECT_PROPERTY_OF = getInstance(40, OWLSubObjectPropertyOfAxiom.class, new OWLSubObjectPropertyOfAxiomSerializer());

    public static final OWLObjectBinaryType<OWLSubPropertyChainOfAxiom> OWL_SUB_OBJECT_PROPERTY_CHAIN_OF = getInstance(41, OWLSubPropertyChainOfAxiom.class, new OWLSubPropertyChainOfAxiomSerializer());

    public static final OWLObjectBinaryType<OWLEquivalentObjectPropertiesAxiom> OWL_EQUIVALENT_OBJECT_PROPERTIES = getInstance(42, OWLEquivalentObjectPropertiesAxiom.class, new OWLEquivalentObjectPropertiesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDisjointObjectPropertiesAxiom> OWL_DISJOINT_OBJECT_PROPERTIES = getInstance(43, OWLDisjointObjectPropertiesAxiom.class, new OWLDisjointObjectPropertiesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLObjectPropertyDomainAxiom> OWL_OBJECT_PROPERTY_DOMAIN = getInstance(44, OWLObjectPropertyDomainAxiom.class, new OWLObjectPropertyDomainSerializer());

    public static final OWLObjectBinaryType<OWLObjectPropertyRangeAxiom> OWL_OBJECT_PROPERTY_RANGE = getInstance(45, OWLObjectPropertyRangeAxiom.class, new OWLObjectPropertyRangeAxiomSerializer());

    public static final OWLObjectBinaryType<OWLInverseObjectPropertiesAxiom> OWL_INVERSE_OBJECT_PROPERTIES = getInstance(46, OWLInverseObjectPropertiesAxiom.class, new OWLInverseObjectPropertiesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLFunctionalObjectPropertyAxiom> OWL_FUNCTIONAL_OBJECT_PROPERTY = getInstance(47, OWLFunctionalObjectPropertyAxiom.class, new OWLFunctionalObjectPropertyAxiomSerializer());

    public static final OWLObjectBinaryType<OWLInverseFunctionalObjectPropertyAxiom> OWL_INVERSE_FUNCTIONAL_OBJECT_PROPERTY = getInstance(48, OWLInverseFunctionalObjectPropertyAxiom.class, new OWLInverseFunctionalObjectPropertyAxiomSerializer());

    public static final OWLObjectBinaryType<OWLReflexiveObjectPropertyAxiom> OWL_REFLEXIVE_OBJECT_PROPERTY = getInstance(49, OWLReflexiveObjectPropertyAxiom.class, new OWLReflexiveObjectPropertyAxiomSerializer());

    public static final OWLObjectBinaryType<OWLIrreflexiveObjectPropertyAxiom> OWL_IRREFLEXIVE_OBJECT_PROPERTY = getInstance(50, OWLIrreflexiveObjectPropertyAxiom.class, new OWLIrreflexiveObjectPropertyAxiomSerializer());

    public static final OWLObjectBinaryType<OWLSymmetricObjectPropertyAxiom> OWL_SYMMETRIC_OBJECT_PROPERTY = getInstance(51, OWLSymmetricObjectPropertyAxiom.class, new OWLSymmetricObjectPropertyAxiomSerializer());

    public static final OWLObjectBinaryType<OWLAsymmetricObjectPropertyAxiom> OWL_ASYMMETRIC_OBJECT_PROPERTY = getInstance(52, OWLAsymmetricObjectPropertyAxiom.class, new OWLAsymmetricObjectPropertyAxiomSerializer());

    public static final OWLObjectBinaryType<OWLTransitiveObjectPropertyAxiom> OWL_TRANSITIVE_OBJECT_PROPERTY = getInstance(53, OWLTransitiveObjectPropertyAxiom.class, new OWLTransitiveObjectPropertyAxiomSerializer());




    public static final OWLObjectBinaryType<OWLSubDataPropertyOfAxiom> OWL_SUB_DATA_PROPERTY_OF = getInstance(54, OWLSubDataPropertyOfAxiom.class, new OWLSubDataPropertyOfAxiomSerializer());

    public static final OWLObjectBinaryType<OWLEquivalentDataPropertiesAxiom> OWL_EQUIVALENT_DATA_PROPERTIES = getInstance(55, OWLEquivalentDataPropertiesAxiom.class, new OWLEquivalentDataPropertiesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDisjointDataPropertiesAxiom> OWL_DISJOINT_DATA_PROPERTIES = getInstance(56, OWLDisjointDataPropertiesAxiom.class, new OWLDisjointDataPropertiesAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDataPropertyDomainAxiom> OWL_DATA_PROPERTY_DOMAIN = getInstance(57, OWLDataPropertyDomainAxiom.class, new OWLDataPropertyDomainAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDataPropertyRangeAxiom> OWL_DATA_PROPERTY_RANGE = getInstance(58, OWLDataPropertyRangeAxiom.class, new OWLDataPropertyRangeAxiomSerializer());

    public static final OWLObjectBinaryType<OWLFunctionalDataPropertyAxiom> OWL_FUNCTIONAL_DATA_PROPERTY = getInstance(59, OWLFunctionalDataPropertyAxiom.class, new OWLFunctionalDataPropertyAxiomSerializer());

    
    
    public static final OWLObjectBinaryType<OWLDatatypeDefinitionAxiom> OWL_DATATYPE_DEFINITION = getInstance(60, OWLDatatypeDefinitionAxiom.class, new OWLDatatypeDefinitionAxiomSerializer());

    public static final OWLObjectBinaryType<OWLHasKeyAxiom> OWL_HAS_KEY = getInstance(61, OWLHasKeyAxiom.class, new OWLHasKeyAxiomSerializer());




    public static final OWLObjectBinaryType<OWLSameIndividualAxiom> OWL_SAME_INDIVIDUAL = getInstance(62, OWLSameIndividualAxiom.class, new OWLSameIndividualAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDifferentIndividualsAxiom> OWL_DIFFERENT_INDIVIDUALS = getInstance(63, OWLDifferentIndividualsAxiom.class, new OWLDifferentIndividualsAxiomSerializer());

    public static final OWLObjectBinaryType<OWLClassAssertionAxiom> OWL_CLASS_ASSERTION = getInstance(64, OWLClassAssertionAxiom.class, new OWLClassAssertionAxiomSerializer());

    public static final OWLObjectBinaryType<OWLObjectPropertyAssertionAxiom> OWL_OBJECT_PROPERTY_ASSERTION = getInstance(65, OWLObjectPropertyAssertionAxiom.class, new OWLObjectPropertyAssertionSerializer());

    public static final OWLObjectBinaryType<OWLNegativeObjectPropertyAssertionAxiom> OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION = getInstance(66, OWLNegativeObjectPropertyAssertionAxiom.class, new OWLNegativeObjectPropertyAssertionAxiomSerializer());

    public static final OWLObjectBinaryType<OWLDataPropertyAssertionAxiom> OWL_DATA_PROPERTY_ASSERTION = getInstance(67, OWLDataPropertyAssertionAxiom.class, new OWLDataPropertyAssertionAxiomSerializer());

    public static final OWLObjectBinaryType<OWLNegativeDataPropertyAssertionAxiom> OWL_NEGATIVE_DATA_PROPERTY_ASSERTION = getInstance(68, OWLNegativeDataPropertyAssertionAxiom.class, new OWLNegativeDataPropertyAssertionAxiomSerializer());



    public static final OWLObjectBinaryType<OWLAnnotationAssertionAxiom> OWL_ANNOTATION_ASSERTION = getInstance(69, OWLAnnotationAssertionAxiom.class, new OWLAnnotationAssertionAxiomSerializer());

    public static final OWLObjectBinaryType<OWLSubAnnotationPropertyOfAxiom> OWL_SUB_ANNOTATION_PROPERTY_OF = getInstance(70, OWLSubAnnotationPropertyOfAxiom.class, new OWLSubAnnotationPropertyOfAxiomSerializer());

    public static final OWLObjectBinaryType<OWLAnnotationPropertyDomainAxiom> OWL_ANNOTATION_PROPERTY_DOMAIN = getInstance(71, OWLAnnotationPropertyDomainAxiom.class, new OWLAnnotationPropertyDomainAxiomSerializer());

    public static final OWLObjectBinaryType<OWLAnnotationPropertyRangeAxiom> OWL_ANNOTATION_PROPERTY_RANGE = getInstance(72, OWLAnnotationPropertyRangeAxiom.class, new OWLAnnotationPropertyRangeAxiomSerializer());

    public static final OWLObjectBinaryType<OWLAnnotation> OWL_ANNOTATION = getInstance(73, OWLAnnotation.class, new OWLAnnotationSerializer());





    public static final OWLObjectBinaryType<SWRLRule> SWRL_RULE = getInstance(74, SWRLRule.class, null);

    public static final OWLObjectBinaryType<SWRLDifferentIndividualsAtom> SWRL_DIFFERENT_INDIVIDUALS_ATOM = getInstance(75, SWRLDifferentIndividualsAtom.class, null);

    public static final OWLObjectBinaryType<SWRLSameIndividualAtom> SWRL_SAME_INDIVIDUAL_ATOM = getInstance(76, SWRLSameIndividualAtom.class, null);

    public static final OWLObjectBinaryType<SWRLClassAtom> SWRL_CLASS_ATOM = getInstance(77, SWRLClassAtom.class, null);

    public static final OWLObjectBinaryType<SWRLDataRangeAtom> SWRL_DATA_RANGE_ATOM = getInstance(78, SWRLDataRangeAtom.class, null);

    public static final OWLObjectBinaryType<SWRLObjectPropertyAtom> SWRL_OBJECT_PROPERTY_ATOM = getInstance(79, SWRLObjectPropertyAtom.class, null);

    public static final OWLObjectBinaryType<SWRLDataPropertyAtom> SWRL_DATA_PROPERTY_ATOM = getInstance(80, SWRLDataPropertyAtom.class, null);

    public static final OWLObjectBinaryType<SWRLBuiltInAtom> SWRL_BUILT_IN_ATOM = getInstance(81, SWRLBuiltInAtom.class, null);

    public static final OWLObjectBinaryType<SWRLVariable> SWRL_VARIABLE = getInstance(82, SWRLVariable.class, null);

    public static final OWLObjectBinaryType<SWRLIndividualArgument> SWRL_INDIVIDUAL_ARGUMENT = getInstance(83, SWRLIndividualArgument.class, null);

    public static final OWLObjectBinaryType<SWRLLiteralArgument> SWRL_LITERAL_ARGUMENT = getInstance(84, SWRLLiteralArgument.class, null);




    private static <C extends OWLObject> OWLObjectBinaryType<C> getInstance(int markerId, Class<C> cls, OWLObjectSerializer<C> serializer) {
        OWLObjectBinaryType<C> value = new OWLObjectBinaryType<C>(markerId, cls, serializer);
        values.add(value);
        return value;
    }


    static {
        // Seal list of values
        List<OWLObjectBinaryType<?>> vals = new ArrayList<OWLObjectBinaryType<?>>(values);
        Collections.sort(vals, new Comparator<OWLObjectBinaryType<?>>() {
            public int compare(OWLObjectBinaryType<?> o1, OWLObjectBinaryType<?> o2) {
                return o1.getMarker() - o2.getMarker();
            }
        });
        values = Collections.unmodifiableList(vals);
    }

    

    public static List<OWLObjectBinaryType<?>> values() {
        return values;
    }
    

    static  {
        doIntegrityCheck();

    }


    private static Map<String, OWLObjectBinaryType> simpleClassName2TypeMap = new HashMap<String, OWLObjectBinaryType>();


    private static OWLObjectBinaryTypeSelector selector = new OWLObjectBinaryTypeSelector();

    private byte marker;

    private Class<? extends OWLObject> cls;

    private OWLObjectSerializer<C> serializer;

    private OWLObjectBinaryType(int marker, Class<C> c, OWLObjectSerializer<C> serializer) {
        this.marker = (byte) marker;
        this.cls = c;
        this.serializer = serializer;

    }

    public Class<? extends OWLObject> getOWLObjectClass() {
        return cls;
    }

    public byte getMarker() {
        return marker;
    }

    public OWLObjectSerializer<C> getSerializer() {
        return serializer;
    }
    
    public static <C extends OWLObject> C read(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        byte typeMarker = dataInput.readByte();
        if(typeMarker <= 0) {
            throw new BinaryOWLParseException("Invalid type marker: " + typeMarker);
        }
        if(typeMarker >= values.size()) {
            throw new BinaryOWLParseException("Invalid type marker: " + typeMarker);
        }
        OWLObjectBinaryType<C> type = getType(typeMarker);
        return type.getSerializer().read(lookupTable, dataInput, dataFactory);
    }


    public static void write(OWLObject object, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
        OWLObjectBinaryType<OWLObject> type = OWLObjectBinaryType.<OWLObject>getType(object);
        dataOutput.writeByte(type.getMarker());
        type.getSerializer().write(object, lookupTable, dataOutput);
    }

    public static <C extends OWLObject>  OWLObjectBinaryType<C> getType(C object) {
        return (OWLObjectBinaryType<C>) object.accept(selector);
    }

    public static OWLObjectBinaryType getType(byte typeMarker) {
        return values.get(typeMarker - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OWLObjectBinaryType(");
        sb.append("Marker(");
        sb.append(marker);
        sb.append(")");
        sb.append(" Class(");
        sb.append(cls.getSimpleName());
        sb.append(")");
        sb.append(")");
        return sb.toString();
    }

    private static void doIntegrityCheck() {
        Method[] methods = OWLObjectVisitor.class.getMethods();
        Set<String> typeValues = new HashSet<String>();
        Set<Byte> markers = new HashSet<Byte>();
        for(OWLObjectBinaryType binaryType : values()) {
            String typeClassName = binaryType.getOWLObjectClass().getSimpleName();
            typeValues.add(typeClassName);
            if(!markers.add(binaryType.getMarker())) {
                throw new RuntimeException("Duplicate marker found: " + binaryType);
            }
        }
        for(Method method : methods) {
            Class [] parameters = method.getParameterTypes();
            String simpleName = parameters[0].getSimpleName();
            if(!typeValues.contains(simpleName)) {
                throw new RuntimeException("BinaryOWLObjectType not defined for " + simpleName);
            }
        }
    }

    static {
        for(OWLObjectBinaryType type : values()) {
            simpleClassName2TypeMap.put(type.getOWLObjectClass().getSimpleName(), type);
        }
    }


    public static void main(String[] args) {
        OWLObjectBinaryType.values();
        for(OWLObjectBinaryType<?> type : values()) {
            System.out.println(type);
        }
    }

}

