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
package org.semanticweb.owlapi6.io;

import java.io.Serializable;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.2
 */
public interface RDFOntologyHeaderStatus extends Serializable {
    /**
     * Enumeration holding known instances.
     */
    enum KnownValues implements RDFOntologyHeaderStatus {
        /**
         * Specifies that during parsing, the ontology document did not contain any ontology
         * headers.
         */
        ZERO_HEADERS(0),
        /**
         * Specifies that during parsing, the ontology document that the ontology was created from
         * contained one header.
         */
        ONE_HEADER(1),
        /**
         * Specifies that during parsing, the ontology document that the ontology was created from
         * contained multiple headers.
         */
        MULTIPLE_HEADERS(2);

        private final int parsedHeaders;

        private KnownValues(int parsedHeaders) {
            this.parsedHeaders = parsedHeaders;
        }

        @Override
        public boolean noHeaders() {
            return parsedHeaders == 0;
        }

        @Override
        public boolean oneHeader() {
            return parsedHeaders == 1;
        }

        @Override
        public boolean multipleHeaders() {
            return parsedHeaders > 1;
        }
    }

    /**
     * Specifies that during parsing, the ontology document did not contain any ontology headers.
     */
    RDFOntologyHeaderStatus PARSED_ZERO_HEADERS = KnownValues.ZERO_HEADERS;
    /**
     * Specifies that during parsing, the ontology document that the ontology was created from
     * contained one header.
     */
    RDFOntologyHeaderStatus PARSED_ONE_HEADER = KnownValues.ONE_HEADER;
    /**
     * Specifies that during parsing, the ontology document that the ontology was created from
     * contained multiple headers.
     */
    RDFOntologyHeaderStatus PARSED_MULTIPLE_HEADERS = KnownValues.MULTIPLE_HEADERS;

    /** @return true if no headers were parsed */
    boolean noHeaders();

    /** @return true if one header was parsed */
    boolean oneHeader();

    /** @return true if more than one header was parsed */
    boolean multipleHeaders();
}
