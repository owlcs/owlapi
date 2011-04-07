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

package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Datatypes">Datatype</a> (named data range) in the OWL 2 Specification.
 */
public interface OWLDatatype extends OWLDataRange, OWLLogicalEntity, OWLNamedObject {

    /**
     * Gets the built in datatype information if this datatype is a built in
     * datatype.  This method should only be called if the isBuiltIn() method
     * returns <code>true</code>
     * @return The OWLDatatypeVocabulary that describes this built in datatype
     * @throws OWLRuntimeException if this datatype is not a built in datatype.
     */
    OWL2Datatype getBuiltInDatatype();

    /**
     * Determines if this datatype has the IRI <code>xsd:string</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:string</code>, otherwise <code>false</code>.
     */
    boolean isString();


    /**
     * Determines if this datatype has the IRI <code>xsd:integer</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:integer</code>, otherwise <code>false</code>.
     */
    boolean isInteger();


    /**
     * Determines if this datatype has the IRI <code>xsd:float</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:float</code>, otherwise <code>false</code>.
     */
    boolean isFloat();


    /**
     * Determines if this datatype has the IRI <code>xsd:double</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:double</code>, otherwise <code>false</code>.
     */
    boolean isDouble();


    /**
     * Determines if this datatype has the IRI <code>xsd:boolean</code>
     * @return <code>true</code> if this datatype has the IRI <code>xsd:boolean</code>, otherwise <code>false</code>.
     */
    boolean isBoolean();

    /**
     * Determines if this datatype has the IRI <code>rdf:PlainLiteral</code>
     * @return <code>true</code> if this datatype has the IRI <code>rdf:PlainLiteral</code> otherwise <code>false</code>
     */
    boolean isRDFPlainLiteral();

}
