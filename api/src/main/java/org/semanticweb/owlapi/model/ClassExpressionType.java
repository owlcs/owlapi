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

import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * Represents the different types of OWL 2 class expressions.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public enum ClassExpressionType
        implements
        Serializable,
        HasShortForm,
        HasPrefixedName,
        HasIRI {
    /** Represents {@link OWLClass} */
    OWL_CLASS("Class"),
    /** Represents {@link OWLObjectSomeValuesFrom} */
    OBJECT_SOME_VALUES_FROM("ObjectSomeValuesFrom"),
    /** Represents {@link OWLObjectAllValuesFrom} */
    OBJECT_ALL_VALUES_FROM("ObjectAllValuesFrom"),
    /** Represents {@link OWLObjectMinCardinality} */
    OBJECT_MIN_CARDINALITY("ObjectMinCardinality"),
    /** Represents {@link OWLObjectMaxCardinality} */
    OBJECT_MAX_CARDINALITY("ObjectMaxCardinality"),
    /** Represents {@link OWLObjectExactCardinality} */
    OBJECT_EXACT_CARDINALITY("ObjectExactCardinality"),
    /** Represents {@link OWLObjectHasValue} */
    OBJECT_HAS_VALUE("ObjectHasValue"),
    /** Represents {@link org.semanticweb.owlapi.model.OWLObjectHasSelf} */
    OBJECT_HAS_SELF("ObjectHasSelf"),
    /** Represents {@link OWLDataSomeValuesFrom} */
    DATA_SOME_VALUES_FROM("DataSomeValuesFrom"),
    /** Represents {@link OWLDataAllValuesFrom} */
    DATA_ALL_VALUES_FROM("DataAllValuesFrom"),
    /** Represents {@link OWLDataMinCardinality} */
    DATA_MIN_CARDINALITY("DataMinCardinality"),
    /** Represents {@link OWLDataMaxCardinality} */
    DATA_MAX_CARDINALITY("DataMaxCardinality"),
    /** Represents {@link OWLDataExactCardinality} */
    DATA_EXACT_CARDINALITY("DataExactCardinality"),
    /** Represents {@link OWLDataHasValue} */
    DATA_HAS_VALUE("DataHasValue"),
    /** Represents {@link org.semanticweb.owlapi.model.OWLObjectIntersectionOf} */
    OBJECT_INTERSECTION_OF("ObjectIntersectionOf"),
    /** Represents {@link org.semanticweb.owlapi.model.OWLObjectUnionOf} */
    OBJECT_UNION_OF("ObjectUnionOf"),
    /** Represents {@link org.semanticweb.owlapi.model.OWLObjectComplementOf} */
    OBJECT_COMPLEMENT_OF("ObjectComplementOf"),
    /** Represents {@link OWLObjectComplementOf} */
    OBJECT_ONE_OF("ObjectOneOf");

    private final String name;
    private final String prefixedName;
    private final IRI iri;

    ClassExpressionType(String name) {
        this.name = name;
        prefixedName = Namespaces.OWL.getPrefixName() + ":" + name;
        iri = IRI.create(Namespaces.OWL.getPrefixIRI(), name);
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getShortForm() {
        return name;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }
}
