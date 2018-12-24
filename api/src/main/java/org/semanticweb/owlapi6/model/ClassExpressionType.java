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
package org.semanticweb.owlapi6.model;

import java.io.Serializable;

import org.semanticweb.owlapi6.vocab.OWLRDFVocabulary;

/**
 * Represents the different types of OWL 2 class expressions.
 *
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public enum ClassExpressionType implements Serializable, HasShortForm, HasPrefixedName, HasIRI {
    //@formatter:off
    /** Represents {@link OWLClass}.                    */  OWL_CLASS               (OWLRDFVocabulary.OWL_CLASS                 ),
    /** Represents {@link OWLObjectSomeValuesFrom}.     */  OBJECT_SOME_VALUES_FROM (OWLRDFVocabulary.OWL_ObjectSomeValuesFrom  ),
    /** Represents {@link OWLObjectAllValuesFrom}.      */  OBJECT_ALL_VALUES_FROM  (OWLRDFVocabulary.OWL_ObjectAllValuesFrom   ),
    /** Represents {@link OWLObjectMinCardinality}.     */  OBJECT_MIN_CARDINALITY  (OWLRDFVocabulary.OWL_ObjectMinCardinality  ),
    /** Represents {@link OWLObjectMaxCardinality}.     */  OBJECT_MAX_CARDINALITY  (OWLRDFVocabulary.OWL_ObjectMaxCardinality  ),
    /** Represents {@link OWLObjectExactCardinality}.   */  OBJECT_EXACT_CARDINALITY(OWLRDFVocabulary.OWL_ObjectExactCardinality),
    /** Represents {@link OWLObjectHasValue}.           */  OBJECT_HAS_VALUE        (OWLRDFVocabulary.OWL_ObjectHasValue        ),
    /** Represents {@link OWLObjectHasSelf}.            */  OBJECT_HAS_SELF         (OWLRDFVocabulary.OWL_ObjectHasSelf         ),
    /** Represents {@link OWLDataSomeValuesFrom}.       */  DATA_SOME_VALUES_FROM   (OWLRDFVocabulary.OWL_DataSomeValuesFrom    ),
    /** Represents {@link OWLDataAllValuesFrom}.        */  DATA_ALL_VALUES_FROM    (OWLRDFVocabulary.OWL_DataAllValuesFrom     ),
    /** Represents {@link OWLDataMinCardinality}.       */  DATA_MIN_CARDINALITY    (OWLRDFVocabulary.OWL_DataMinCardinality    ),
    /** Represents {@link OWLDataMaxCardinality}.       */  DATA_MAX_CARDINALITY    (OWLRDFVocabulary.OWL_DataMaxCardinality    ),
    /** Represents {@link OWLDataExactCardinality}.     */  DATA_EXACT_CARDINALITY  (OWLRDFVocabulary.OWL_DataExactCardinality  ),
    /** Represents {@link OWLDataHasValue}.             */  DATA_HAS_VALUE          (OWLRDFVocabulary.OWL_DataHasValue          ),
    /** Represents {@link OWLObjectIntersectionOf}.     */  OBJECT_INTERSECTION_OF  (OWLRDFVocabulary.OWL_ObjectIntersectionOf  ),
    /** Represents {@link OWLObjectUnionOf}.            */  OBJECT_UNION_OF         (OWLRDFVocabulary.OWL_ObjectUnionOf         ),
    /** Represents {@link OWLObjectComplementOf}.       */  OBJECT_COMPLEMENT_OF    (OWLRDFVocabulary.OWL_ObjectComplementOf    ),
    /** Represents {@link OWLObjectComplementOf}.       */  OBJECT_ONE_OF           (OWLRDFVocabulary.OWL_ObjectOneOf           );
    //@formatter:on
    private final OWLRDFVocabulary v;

    ClassExpressionType(OWLRDFVocabulary name) {
        v = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return v.getShortForm();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getShortForm() {
        return getName();
    }

    @Override
    public IRI getIRI() {
        return v.getIRI();
    }

    @Override
    public String getPrefixedName() {
        return v.getPrefixedName();
    }
}
