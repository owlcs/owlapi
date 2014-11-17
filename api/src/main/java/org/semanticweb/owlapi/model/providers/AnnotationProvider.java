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

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;

/**
 * Annotation provider interface.
 */
public interface AnnotationProvider {

    // Annotations
    /**
     * Gets an annotation
     * 
     * @param property
     *        the annotation property.
     * @param value
     *        The annotation value.
     * @return The annotation on the specified property with the specified value
     */
    @Nonnull
    OWLAnnotation getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value);

    /**
     * Gets an annotation
     * 
     * @param property
     *        the annotation property.
     * @param value
     *        The annotation value.
     * @param annotations
     *        A set of annotations. Cannot be null or contain nulls.
     * @return The annotation on the specified property with the specified value
     */
    @Nonnull
    default OWLAnnotation getOWLAnnotation(
            @Nonnull OWLAnnotationProperty property,
            @Nonnull OWLAnnotationValue value,
            @Nonnull Set<OWLAnnotation> annotations) {
        return getOWLAnnotation(property, value, annotations.stream());
    }

    /**
     * Gets an annotation
     * 
     * @param property
     *        the annotation property.
     * @param value
     *        The annotation value.
     * @param annotation
     *        Annotation on this annotation. Cannot be null or contain nulls.
     * @return The annotation on the specified property with the specified value
     */
    @Nonnull
    default OWLAnnotation
            getOWLAnnotation(@Nonnull OWLAnnotationProperty property,
                    @Nonnull OWLAnnotationValue value,
                    @Nonnull OWLAnnotation annotation) {
        return getOWLAnnotation(property, value, Stream.of(annotation));
    }

    /**
     * Gets an annotation
     * 
     * @param property
     *        the annotation property.
     * @param value
     *        The annotation value.
     * @param annotations
     *        A stream of annotations. Cannot be null or contain nulls.
     * @return The annotation on the specified property with the specified value
     */
    public OWLAnnotation
            getOWLAnnotation(OWLAnnotationProperty property,
                    OWLAnnotationValue value,
                    @Nonnull Stream<OWLAnnotation> annotations);
}
