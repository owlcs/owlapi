package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.IRI;

/*
 * Copyright (C) 2006, University of Manchester
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
