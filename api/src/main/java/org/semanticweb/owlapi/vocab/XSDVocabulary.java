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

package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 * A vocabulary for XML Schema Data Types (XSD)
 */
public enum XSDVocabulary {

    ANY_TYPE("anyType"),
    ANY_SIMPLE_TYPE("anySimpleType"),
    STRING("string"),
    INTEGER("integer"),
    LONG("long"),
    INT("int"),
    SHORT("short"),
    BYTE("byte"),
    DECIMAL("decimal"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    DOUBLE("double"),
    NON_POSITIVE_INTEGER("nonPositiveInteger"),
    NEGATIVE_INTEGER("negativeInteger"),
    NON_NEGATIVE_INTEGER("nonNegativeInteger"),
    UNSIGNED_LONG("unsignedLong"),
    UNSIGNED_INT("unsignedInt"),
    POSITIVE_INTEGER("positiveInteger"),
    BASE_64_BINARY("base64Binary"),
    HEX_BINARY("hexBinary"),
    ANY_URI("anyURI"),
    Q_NAME("QName"),
    NOTATION("NOTATION"),
    NORMALIZED_STRING("normalizedString"),
    TOKEN("token"),
    LANGUAGE("language"),
    NAME("Name"),
    NCNAME("NCName"),
    NMTOKEN("NMTOKEN"),
    ID("ID"),
    IDREF("IDREF"),
    IDREFS("IDREFS"),
    ENTITY("ENTITY"),
    ENTITIES("ENTITIES"),
    DURATION("duration"),
    DATE_TIME("dateTime"),
    DATE_TIME_STAMP("dateTimeStamp"),
    TIME("time"),
    DATE("date"),
    G_YEAR_MONTH("gYearMonth"),
    G_YEAR("gYear"),
    G_MONTH_DAY("gMonthYear"),
    G_DAY("gDay"),
    G_MONTH("gMonth"),
    UNSIGNED_SHORT("unsignedShort"),
    UNSIGNED_BYTE("unsignedByte");

    private String shortName;

    private IRI iri;

    XSDVocabulary(String name) {
        this.shortName = name;
        iri = IRI.create(Namespaces.XSD + name);
    }


    public String getShortName() {
        return shortName;
    }


//    public URI getURI() {
//        return iri.toURI();
//    }

    public IRI getIRI() {
        return iri;
    }

//    public static final Set<URI> ALL_DATATYPES;
//
//    static {
//        ALL_DATATYPES = new HashSet<URI>();
//        for(XSDVocabulary v : XSDVocabulary.values()) {
//            ALL_DATATYPES.add(v.getURI());
//        }
//    }


    @Override
	public String toString() {
        return iri.toString();
    }
}
