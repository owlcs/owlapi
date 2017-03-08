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
package org.semanticweb.owlapi.model.providers;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Utility shortcuts for OWL basic entities. For the sake of convenience, this also includes
 * RDF/RDFS and XSD entities.
 */
public interface OWLVocabularyProvider {

    // Entities and data stuff

    /**
     * Gets the built in owl:Thing class, which has a URI of
     * &lt;http://www.w3.org/2002/07/owl#Thing&gt;
     *
     * @return The OWL Class corresponding to owl:Thing
     */
    OWLClass getOWLThing();

    /**
     * Gets the built in owl:Nothing class, which has a URI of
     * &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
     *
     * @return The OWL Class corresponding to owl:Nothing
     */
    OWLClass getOWLNothing();

    /**
     * @return the built in top object property
     */
    OWLObjectProperty getOWLTopObjectProperty();

    /**
     * @return the built in top data property
     */
    OWLDataProperty getOWLTopDataProperty();

    /**
     * @return The OWL Datatype corresponding to the top data type (rdfs:Literal, with a URI of
     * $lt;http://www.w3.org/2000/01/rdf-schema#&gt;).
     */
    OWLDatatype getTopDatatype();

    /**
     * @return the built in bottom object property
     */
    OWLObjectProperty getOWLBottomObjectProperty();

    /**
     * @return the built in bottom data property
     */
    OWLDataProperty getOWLBottomDataProperty();

    /**
     * Gets an annotation property that has an IRI corresponding to {@code rdfs:label}.
     *
     * @return An annotation property with an IRI of {@code rdfs:label}.
     */
    OWLAnnotationProperty getRDFSLabel();

    /**
     * Gets an annotation property that has an IRI corresponding to {@code rdfs:comment}.
     *
     * @return An annotation property with an IRI of {@code rdfs:comment}.
     */
    OWLAnnotationProperty getRDFSComment();

    /**
     * Gets an annotation property that has an IRI corresponding to {@code rdfs:seeAlso}.
     *
     * @return An annotation property with an IRI of {@code rdfs:seeAlso}.
     */
    OWLAnnotationProperty getRDFSSeeAlso();

    /**
     * Gets an annotation property that has an IRI corresponding to {@code rdfs:isDefinedBy}.
     *
     * @return An annotation property with an IRI of {@code rdfs:isDefinedBy}.
     */
    OWLAnnotationProperty getRDFSIsDefinedBy();

    /**
     * Gets an annotation property that has an IRI corresponding to {@code owl:versionInfo}.
     *
     * @return An annotation property with an IRI of {@code owl:versionInfo}.
     */
    OWLAnnotationProperty getOWLVersionInfo();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code owl:backwardCompatibleWith}.
     *
     * @return An annotation property with an IRI of {@code owl:backwardCompatibleWith}.
     */
    OWLAnnotationProperty getOWLBackwardCompatibleWith();

    /**
     * Gets an annotation property that has an IRI corresponding to {@code owl:incompatibleWith}.
     *
     * @return An annotation property with an IRI of {@code owl:incompatibleWith}.
     */
    OWLAnnotationProperty getOWLIncompatibleWith();

    /**
     * Gets an annotation property that has an IRI corresponding to
     * {@code owl:backwardCompatibleWith}.
     *
     * @return An annotation property with an IRI of {@code owl:backwardCompatibleWith}.
     */
    OWLAnnotationProperty getOWLDeprecated();

    /**
     * Gets the rdf:PlainLiteral datatype.
     *
     * @return The datatype with an IRI of {@code rdf:PlainLiteral}
     */
    OWLDatatype getRDFPlainLiteral();

    /**
     * A convenience method that obtains the datatype that represents integers. This datatype will
     * have the URI of &lt;http://www.w3.org/2001/XMLSchema#integer&gt;
     *
     * @return An object representing an integer datatype.
     */
    OWLDatatype getIntegerOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents floats. This datatype will
     * have the URI of &lt;http://www.w3.org/2001/XMLSchema#float&gt;
     *
     * @return An object representing the float datatype.
     */
    OWLDatatype getFloatOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents doubles. This datatype will
     * have the URI of &lt;http://www.w3.org/2001/XMLSchema#double&gt;
     *
     * @return An object representing a double datatype.
     */
    OWLDatatype getDoubleOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents the boolean datatype. This
     * datatype will have the URI of &lt;http://www.w3.org/2001/XMLSchema#boolean&gt;
     *
     * @return An object representing the boolean datatype.
     */
    OWLDatatype getBooleanOWLDatatype();

    /**
     * A convenience method that obtains the datatype that represents the string datatype. This
     * datatype will have the URI of &lt;http://www.w3.org/2001/XMLSchema#string&gt;
     *
     * @return An object representing the string datatype.
     */
    OWLDatatype getStringOWLDatatype();
}
