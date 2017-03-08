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
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * A vocabulary for XML Schema Data Types (XSD).
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public enum XSDVocabulary implements HasShortForm, HasIRI, HasPrefixedName {
    // @formatter:off
    /** ANY_TYPE. */             ANY_TYPE        ("anyType"),
    /** ANY_SIMPLE_TYPE. */      ANY_SIMPLE_TYPE ("anySimpleType"),
    /** STRING. */               STRING          ("string"),
    /** INTEGER. */              INTEGER         ("integer"),
    /** LONG. */                 LONG            ("long"),
    /** INT. */                  INT             ("int"),
    /** SHORT. */                SHORT           ("short"),
    /** BYTE. */                 BYTE            ("byte"),
    /** DECIMAL. */              DECIMAL         ("decimal"),
    /** FLOAT. */                FLOAT           ("float"),
    /** BOOLEAN. */              BOOLEAN         ("boolean"),
    /** DOUBLE. */               DOUBLE          ("double"),
    /** NON_POSITIVE_INTEGER. */ NON_POSITIVE_INTEGER("nonPositiveInteger"),
    /** NEGATIVE_INTEGER. */     NEGATIVE_INTEGER    ("negativeInteger"),
    /** NON_NEGATIVE_INTEGER. */ NON_NEGATIVE_INTEGER("nonNegativeInteger"),
    /** UNSIGNED_LONG. */        UNSIGNED_LONG       ("unsignedLong"),
    /** UNSIGNED_INT. */         UNSIGNED_INT        ("unsignedInt"),
    /** POSITIVE_INTEGER. */     POSITIVE_INTEGER    ("positiveInteger"),
    /** BASE_64_BINARY. */       BASE_64_BINARY      ("base64Binary"),
    /** NORMALIZED_STRING. */    NORMALIZED_STRING   ("normalizedString"),
    /** HEX_BINARY. */           HEX_BINARY      ("hexBinary"),
    /** ANY_URI. */              ANY_URI         ("anyURI"),
    /** Q_NAME. */               Q_NAME          ("QName"),
    /** NOTATION. */             NOTATION        ("NOTATION"),
    /** TOKEN. */                TOKEN           ("token"),
    /** LANGUAGE. */             LANGUAGE        ("language"),
    /** NAME. */                 NAME            ("Name"),
    /** NCNAME. */               NCNAME          ("NCName"),
    /** NMTOKEN. */              NMTOKEN         ("NMTOKEN"),
    /** NMTOKENS. */             NMTOKENS        ("NMTOKENS"),
    /** ID. */                   ID              ("ID"),
    /** IDREF. */                IDREF           ("IDREF"),
    /** IDREFS. */               IDREFS          ("IDREFS"),
    /** ENTITY. */               ENTITY          ("ENTITY"),
    /** ENTITIES. */             ENTITIES        ("ENTITIES"),
    /** DURATION. */             DURATION        ("duration"),
    /** DATE_TIME. */            DATE_TIME       ("dateTime"),
    /** DATE_TIME_STAMP. */      DATE_TIME_STAMP ("dateTimeStamp"),
    /** TIME. */                 TIME            ("time"),
    /** DATE. */                 DATE            ("date"),
    /** G_YEAR_MONTH. */         G_YEAR_MONTH    ("gYearMonth"),
    /** G_YEAR. */               G_YEAR          ("gYear"),
    /** G_MONTH_DAY. */          G_MONTH_DAY     ("gMonthYear"),
    /** G_DAY. */                G_DAY           ("gDay"),
    /** G_MONTH. */              G_MONTH         ("gMonth"),
    /** UNSIGNED_SHORT. */       UNSIGNED_SHORT  ("unsignedShort"),
    /** UNSIGNED_BYTE. */        UNSIGNED_BYTE   ("unsignedByte");
    // @formatter:on
    private final String shortName;
    private final IRI iri;
    private final String prefixedName;

    XSDVocabulary(String name) {
        shortName = name;
        prefixedName = Namespaces.XSD.getPrefixName() + ':' + name;
        iri = IRI.create(Namespaces.XSD.toString(), name);
    }

    /**
     * Easy parse of short names of the kind "xsd:typename". Note that the match must be exact -
     * uppercase or lowercase variants are not accepted. An IllegalArgumentException will be thrown
     * for non matching input.
     *
     * @param s string of the form {@code xsd:typename}
     * @return the XSDVocabulary item matching xsd:typename, e.g., {@code STRING} for {@code
     * "xsd:string"}
     */
    public static XSDVocabulary parseShortName(String s) {
        checkNotNull(s, "the input string cannot be null");
        if (s.startsWith("xsd:")) {
            String name = s.substring(4);
            for (XSDVocabulary v : values()) {
                if (v.shortName.equals(name)) {
                    return v;
                }
            }
        }
        throw new IllegalArgumentException(
            "the input value does not match any of the known xsd types: " + s);
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public String toString() {
        return iri.toString();
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }
}
