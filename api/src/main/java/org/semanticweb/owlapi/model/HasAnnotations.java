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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

/**
 * An interface to an object that has annotation.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.4.6
 */
public interface HasAnnotations {

    /**
     * @return a stream of OWLAnnotations on this object. This will only include
     *         the annotations contained in this object, not the value of
     *         annotation assertion axioms in an ontology or in other
     *         ontologies. Use the EntitySearcher methods for that purpose.
     */
    @Nonnull
    default Stream<OWLAnnotation> annotations() {
        return empty();
    }

    /**
     * @return a stream of OWLAnnotations on this object, with filter applied.
     *         This will only include the annotations contained in this object,
     *         not the value of annotation assertion axioms in an ontology or in
     *         other ontologies. Use the EntitySearcher methods for that
     *         purpose.
     * @param p
     *        filter predicate for annotations. Can be used to match the
     *        annotation property or the annotation value
     */
    @Nonnull
    default Stream<OWLAnnotation> annotations(
        @Nonnull Predicate<OWLAnnotation> p) {
        return annotations().filter(p);
    }

    /**
     * @return a stream of OWLAnnotations on this object, with filter applied.
     *         This will only include the annotations contained in this object,
     *         not the value of annotation assertion axioms in an ontology or in
     *         other ontologies. Use the EntitySearcher methods for that
     *         purpose.
     * @param p
     *        annotation property to filter on
     */
    @Nonnull
    default Stream<OWLAnnotation> annotations(
        @Nonnull OWLAnnotationProperty p) {
        return annotations().filter(a -> a.getProperty().equals(p));
    }

    /**
     * Gets the annotations on this object.
     * 
     * @return A set of annotations on this object. For an OWLOntology, these
     *         are the ontology annotations, not the annotations attached to
     *         axioms or annotation axioms contained in the ontology. For an
     *         OWLAnnotation, these are the annotations on the annotation; the
     *         annotation itself is not included. The set returned will be a
     *         copy - modifying the set will have no effect on the annotations
     *         in this object, similarly, any changes that affect the
     *         annotations on this object will not change the returned set.
     *         Note: for iterating over this set of annotations, using the
     *         annotations() stream is more efficient.
     */
    @Deprecated
    @Nonnull
    default Set<OWLAnnotation> getAnnotations() {
        return asSet(annotations());
    }

    /**
     * Gets the annotations whose annotation property is equal to
     * {@code annotationProperty}.
     * 
     * @param annotationProperty
     *        The annotation property that will be equal to the annotation
     *        property of each returned annotation.
     * @return A set of annotations whose annotation properties is equals to
     *         {@code annotationProperty}.
     */
    @Deprecated
    @Nonnull
    default Set<OWLAnnotation> getAnnotations(
        @Nonnull OWLAnnotationProperty annotationProperty) {
        return asSet(
            annotations(a -> a.getProperty().equals(annotationProperty)));
    }
}
