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

/**
 * An exception to describe a problem in accessing an ontology. Since there could be any kind of
 * implementation of {@code OWLOntology} (and other model interfaces), some of which may use
 * secondary storage, such as a database backend, there could be problems with accessing ontology
 * objects such as axioms. In such situations the implementation should wrap the implementation
 * specific exceptions in this exception and rethrow an instance of this exception. Note that
 * exceptions of this type are unchecked (runtime) exceptions - this is because they represent
 * potentially nasty situations where client code calling methods such as getAxioms() probably
 * doesn't know (or care) how to handle situations where network/database connections fail.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyResourceAccessException extends OWLRuntimeException {

    /**
     * @param message message
     */
    public OWLOntologyResourceAccessException(String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause cause
     */
    public OWLOntologyResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause cause
     */
    public OWLOntologyResourceAccessException(Throwable cause) {
        super(cause);
    }
}
