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
 */
public enum ClassExpressionType {

    OWL_CLASS("OWLClass"),

    OBJECT_SOME_VALUES_FROM("ObjectSomeValuesFrom"),

    OBJECT_ALL_VALUES_FROM("ObjectAllValuesFrom"),

    OBJECT_MIN_CARDINALITY("ObjectMinCardinality"),

    OBJECT_MAX_CARDINALITY("ObjectMaxCardinality"),

    OBJECT_EXACT_CARDINALITY("ObjectExactCardinality"),

    OBJECT_HAS_VALUE("ObjectHasValue"),

    OBJECT_HAS_SELF("ObjectHasSelf"),

    DATA_SOME_VALUES_FROM("DataSomeValuesFrom"),

    DATA_ALL_VALUES_FROM("DataAllValuesFrom"),

    DATA_MIN_CARDINALITY("DataMinCardinality"),

    DATA_MAX_CARDINALITY("DataMaxCardinality"),

    DATA_EXACT_CARDINALITY("DataExactCardinality"),

    DATA_HAS_VALUE("DataHasValue"),

    OBJECT_INTERSETION_OF("ObjectIntersectionOf"),

    OBJECT_UNION_OF("ObjectUnionOf"),

    OBJECT_COMPLEMENT_OF("ObjectComplementOf"),

    OBJECT_ONE_OF("ObjectOneOf");

    private String name;


    ClassExpressionType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public String toString() {
        return name;
    }
}
