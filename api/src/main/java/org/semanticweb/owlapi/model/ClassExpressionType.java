package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 08-Sep-2008<br><br>
 * </p>
 * Represents the different types of OWL 2 class expressions.
 */
public enum ClassExpressionType {


    /**
     * Represents {@link OWLClass}
     */
    OWL_CLASS("Class"),


    /**
     * Represents {@link OWLObjectSomeValuesFrom}
     */
    OBJECT_SOME_VALUES_FROM("ObjectSomeValuesFrom"),


    /**
     * Represents {@link OWLObjectAllValuesFrom}
     */
    OBJECT_ALL_VALUES_FROM("ObjectAllValuesFrom"),


    /**
     * Represents {@link OWLObjectMinCardinality}
     */
    OBJECT_MIN_CARDINALITY("ObjectMinCardinality"),


    /**
     * Represents {@link OWLObjectMaxCardinality}
     */
    OBJECT_MAX_CARDINALITY("ObjectMaxCardinality"),


    /**
     * Represents {@link OWLObjectExactCardinality}
     */
    OBJECT_EXACT_CARDINALITY("ObjectExactCardinality"),


    /**
     * Represents {@link OWLObjectHasValue}
     */
    OBJECT_HAS_VALUE("ObjectHasValue"),

    /**
     * Represents {@link org.semanticweb.owlapi.model.OWLObjectHasSelf}
     */
    OBJECT_HAS_SELF("ObjectHasSelf"),


    /**
     * Represents {@link OWLDataSomeValuesFrom}
     */
    DATA_SOME_VALUES_FROM("DataSomeValuesFrom"),


    /**
     * Represents {@link OWLDataAllValuesFrom}
     */
    DATA_ALL_VALUES_FROM("DataAllValuesFrom"),


    /**
     * Represents {@link OWLDataMinCardinality}
     */
    DATA_MIN_CARDINALITY("DataMinCardinality"),


    /**
     * Represents {@link OWLDataMaxCardinality}
     */
    DATA_MAX_CARDINALITY("DataMaxCardinality"),


    /**
     * Represents {@link OWLDataExactCardinality}
     */
    DATA_EXACT_CARDINALITY("DataExactCardinality"),


    /**
     * Represents {@link OWLDataHasValue}
     */
    DATA_HAS_VALUE("DataHasValue"),


    /**
     * Represents {@link org.semanticweb.owlapi.model.OWLObjectIntersectionOf}
     */
    OBJECT_INTERSECTION_OF("ObjectIntersectionOf"),


    /**
     * Represents {@link org.semanticweb.owlapi.model.OWLObjectUnionOf}
     */
    OBJECT_UNION_OF("ObjectUnionOf"),


    /**
     * Represents {@link org.semanticweb.owlapi.model.OWLObjectComplementOf}
     */
    OBJECT_COMPLEMENT_OF("ObjectComplementOf"),


    /**
     * Represents {@link OWLObjectComplementOf}
     */
    OBJECT_ONE_OF("ObjectOneOf");

    private String name;


    ClassExpressionType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
	public String toString() {
        return name;
    }
}
