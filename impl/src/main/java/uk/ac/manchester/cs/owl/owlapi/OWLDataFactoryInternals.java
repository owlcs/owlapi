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
package uk.ac.manchester.cs.owl.owlapi;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * @author ignazio Mutable state in an OWLDataFactory is encapsulated by this
 *         interface
 */
public interface OWLDataFactoryInternals extends Serializable {

    /**
     * @param iri
     *        new class IRI
     * @return a new OWLClass object, or a cached one depending on policies
     */
    @Nonnull
    OWLClass getOWLClass(@Nonnull IRI iri);

    /** purge any caches. */
    void purge();

    /**
     * @param iri
     *        new object property IRI
     * @return a new OWLObjectProperty object, or a cached one depending on
     *         policies
     */
    @Nonnull
    OWLObjectProperty getOWLObjectProperty(@Nonnull IRI iri);

    /**
     * @param iri
     *        new OWLDataProperty IRI
     * @return a new OWLDataProperty object, or a cached one depending on
     *         policies
     */
    @Nonnull
    OWLDataProperty getOWLDataProperty(@Nonnull IRI iri);

    /**
     * @param iri
     *        new OWLNamedIndividual IRI
     * @return a new OWLNamedIndividual object, or a cached one depending on
     *         policies
     */
    @Nonnull
    OWLNamedIndividual getOWLNamedIndividual(@Nonnull IRI iri);

    /**
     * @param iri
     *        new OWLDatatype IRI
     * @return a new OWLDatatype object, or a cached one depending on policies
     */
    @Nonnull
    OWLDatatype getOWLDatatype(@Nonnull IRI iri);

    /**
     * @param iri
     *        new OWLAnnotationProperty IRI
     * @return a new OWLAnnotationProperty object, or a cached one depending on
     *         policies
     */
    @Nonnull
    OWLAnnotationProperty getOWLAnnotationProperty(@Nonnull IRI iri);

    /**
     * @param lexicalValue
     *        literal value for literal
     * @param datatype
     *        datatype for literal
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String lexicalValue,
            @Nonnull OWLDatatype datatype);

    /**
     * @param value
     *        int value for literal
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(int value);

    /**
     * @param value
     *        boolean value for literal
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(boolean value);

    /**
     * @param value
     *        double value for literal
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(double value);

    /**
     * @param value
     *        float value for literal
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(float value);

    /**
     * @param value
     *        literal form
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String value);

    /**
     * @param literal
     *        literal form
     * @param lang
     *        language tag, can be null
     * @return new literal
     */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String literal, @Nullable String lang);

    /** @return top datatype */
    @Nonnull
    OWLDatatype getTopDatatype();

    /** @return integer datatype */
    @Nonnull
    OWLDatatype getIntegerOWLDatatype();

    /** @return float datatype */
    @Nonnull
    OWLDatatype getFloatOWLDatatype();

    /** @return double datatype */
    @Nonnull
    OWLDatatype getDoubleOWLDatatype();

    /** @return boolean datatype */
    @Nonnull
    OWLDatatype getBooleanOWLDatatype();

    /** @return plain literal datatype */
    @Nonnull
    OWLDatatype getRDFPlainLiteral();

    /**
     * construct an OWLAnnotation
     * @param property property to use
     * @param value value to use
     * @param annotations annotations to use
     * @return new annotation instance
     */
    public OWLAnnotation getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
                                          @Nonnull OWLAnnotationValue value,
                                          @Nonnull Set<? extends OWLAnnotation> annotations);

}
