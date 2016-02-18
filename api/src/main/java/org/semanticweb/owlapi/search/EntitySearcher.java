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
package org.semanticweb.owlapi.search;

import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.search.Filters.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Convenience methods moved from OWLEntity and its subinterfaces.
 * 
 * @author ignazio
 */
public class EntitySearcher {

    /**
     * Gets the annotations for this entity. These are deemed to be annotations
     * in annotation assertion axioms that have a subject that is an IRI that is
     * equal to the IRI of this entity, and it also includes annotations on the
     * annotation assertion axioms whose annotation property matches
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation
     *         assertion whose subject is an IRI corresponding to the IRI of
     *         this entity.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotations(@Nonnull OWLEntity e, @Nonnull OWLOntology ontology) {
        return getAnnotations(e.getIRI(), ontology);
    }

    /**
     * Gets the annotations for this entity. These are deemed to be annotations
     * in annotation assertion axioms that have a subject that is an IRI that is
     * equal to the IRI of this entity, and it also includes annotations on the
     * annotation assertion axioms whose annotation property matches.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation
     *         assertion whose subject is an IRI corresponding to the IRI of
     *         this entity.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotations(@Nonnull OWLAnnotationSubject e,
        @Nonnull OWLOntology ontology) {
        return Searcher.annotations(ontology.getAnnotationAssertionAxioms(e));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property. This includes the annotations on
     * annotation assertion axioms with matching annotation property.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotations(@Nonnull OWLEntity e, @Nonnull OWLOntology ontology,
        @Nonnull OWLAnnotationProperty annotationProperty) {
        return getAnnotations(e.getIRI(), ontology, annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property. This includes the annotations on
     * annotation assertion axioms with matching annotation property.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotations(@Nonnull OWLAnnotationSubject e,
        @Nonnull OWLOntology ontology, @Nonnull OWLAnnotationProperty annotationProperty) {
        return Searcher.annotations(ontology.getAnnotationAssertionAxioms(e), annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property. This includes the annotations on
     * annotation assertion axioms with matching annotation property.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotations(@Nonnull OWLAnnotationSubject e,
        @Nonnull Iterable<OWLOntology> ontologies, @Nonnull OWLAnnotationProperty annotationProperty) {
        Set<OWLAnnotation> toReturn = new HashSet<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            toReturn.addAll(getAnnotations(e, o, annotationProperty));
        }
        return toReturn;
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property. This includes the annotations on
     * annotation assertion axioms with matching annotation property.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotations(@Nonnull OWLEntity e,
        @Nonnull Iterable<OWLOntology> ontologies, @Nonnull OWLAnnotationProperty annotationProperty) {
        Set<OWLAnnotation> toReturn = new HashSet<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            toReturn.addAll(getAnnotations(e, o, annotationProperty));
        }
        return toReturn;
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property; this is restricted to the object of
     * annotation assertion axioms.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotationObjects(@Nonnull OWLEntity e, @Nonnull OWLOntology ontology,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return getAnnotationObjects(e.getIRI(), ontology, annotationProperty);
    }

    /**
     * Obtains the annotations on this entity; this is restricted to the object
     * of annotation assertion axioms.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to examine for annotation axioms
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotationObjects(@Nonnull OWLEntity e, @Nonnull OWLOntology ontology) {
        return getAnnotationObjects(e.getIRI(), ontology, null);
    }

    /**
     * Obtains the annotations on this entity; this is restricted to the object
     * of annotation assertion axioms.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to examine for annotation axioms
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotationObjects(@Nonnull OWLEntity e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        return getAnnotationObjects(e.getIRI(), ontologies, null);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property; this is restricted to the object of
     * annotation assertion axioms.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotationObjects(@Nonnull OWLAnnotationSubject e,
        @Nonnull OWLOntology ontology, @Nullable OWLAnnotationProperty annotationProperty) {
        return Searcher.annotationObjects(ontology.getAnnotationAssertionAxioms(e), annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property; this is restricted to the object of
     * annotation assertion axioms.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotationObjects(@Nonnull OWLAnnotationSubject e,
        @Nonnull Iterable<OWLOntology> ontologies, @Nonnull OWLAnnotationProperty annotationProperty) {
        Set<OWLAnnotation> toReturn = new HashSet<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            toReturn.addAll(getAnnotationObjects(e, o, annotationProperty));
        }
        return toReturn;
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property; this is restricted to the object of
     * annotation assertion axioms.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to examine for annotation axioms
     * @param annotationProperty
     *        The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    @Nonnull
    public static Collection<OWLAnnotation> getAnnotationObjects(@Nonnull OWLEntity e,
        @Nonnull Iterable<OWLOntology> ontologies, @Nonnull OWLAnnotationProperty annotationProperty) {
        Set<OWLAnnotation> toReturn = new HashSet<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            toReturn.addAll(getAnnotationObjects(e, o, annotationProperty));
        }
        return toReturn;
    }

    /**
     * @param e
     *        entity
     * @param ontology
     *        the ontology to use
     * @return the annotation assertion axioms about this entity in the provided
     *         ontology
     */
    @Nonnull
    public static Collection<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(@Nonnull OWLEntity e,
        @Nonnull OWLOntology ontology) {
        return getAnnotationAssertionAxioms(e.getIRI(), ontology);
    }

    /**
     * @param e
     *        entity
     * @param ontology
     *        the ontology to use
     * @return the annotation assertion axioms about this entity in the provided
     *         ontology
     */
    @Nonnull
    public static Collection<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(@Nonnull OWLAnnotationSubject e,
        @Nonnull OWLOntology ontology) {
        return ontology.getAnnotationAssertionAxioms(e);
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLAnnotationProperty> getSubProperties(@Nonnull OWLAnnotationProperty e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subAnnotationWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLAnnotationProperty> getSubProperties(@Nonnull OWLAnnotationProperty e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subAnnotationWithSuper, e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLAnnotationProperty> getSubProperties(@Nonnull OWLAnnotationProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLAnnotationProperty> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSubProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLAnnotationProperty> getSuperProperties(@Nonnull OWLAnnotationProperty e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(subAnnotationWithSub, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLAnnotationProperty> getSuperProperties(@Nonnull OWLAnnotationProperty e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sup(ontology.filterAxioms(subAnnotationWithSub, e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLAnnotationProperty> getSuperProperties(@Nonnull OWLAnnotationProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLAnnotationProperty> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSuperProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getSubProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subObjectPropertyWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getSubProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subObjectPropertyWithSuper, e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getSubProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLObjectPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSubProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param
     *        <P>
     *        type of property
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getSubProperties(@Nonnull P e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subPropertiesFilter(e), e, EXCLUDED));
    }

    protected static <P extends OWLPropertyExpression> OWLAxiomSearchFilter subPropertiesFilter(P e) {
        if (e.isDataPropertyExpression()) {
            return subDataPropertyWithSuper;
        }
        if (e.isObjectPropertyExpression()) {
            return subObjectPropertyWithSuper;
        }
        return subAnnotationWithSuper;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @param
     *        <P>
     *        type of property
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getSubProperties(@Nonnull P e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subPropertiesFilter(e), e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param
     *        <P>
     *        type of property
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getSubProperties(@Nonnull P e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<P> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSubProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param
     *        <P>
     *        type of property
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getSuperProperties(@Nonnull P e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(superPropertiesFilter(e), e, EXCLUDED));
    }

    protected static <P extends OWLPropertyExpression> OWLAxiomSearchFilter superPropertiesFilter(P e) {
        if (e.isDataPropertyExpression()) {
            return subDataPropertyWithSub;
        }
        if (e.isObjectPropertyExpression()) {
            return subObjectPropertyWithSub;
        }
        return subAnnotationWithSub;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @param
     *        <P>
     *        type of property
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getSuperProperties(@Nonnull P e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sub(ontology.filterAxioms(superPropertiesFilter(e), e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param
     *        <P>
     *        type of property
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getSuperProperties(@Nonnull P e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<P> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSuperProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getSuperProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(subObjectPropertyWithSub, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getSuperProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sup(ontology.filterAxioms(subObjectPropertyWithSub, e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getSuperProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLObjectPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSuperProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getSubProperties(@Nonnull OWLDataPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subDataPropertyWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getSubProperties(@Nonnull OWLDataPropertyExpression e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subDataPropertyWithSuper, e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getSubProperties(@Nonnull OWLDataPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLDataPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSubProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getSuperProperties(@Nonnull OWLDataPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(subDataPropertyWithSub, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @param imports
     *        if true include imports closure
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getSuperProperties(@Nonnull OWLDataPropertyExpression e,
        @Nonnull OWLOntology ontology, boolean imports) {
        return Searcher.sup(ontology.filterAxioms(subDataPropertyWithSub, e, imports ? INCLUDED : EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for {@code SubAnnotationPropertyOf}
     *        axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getSuperProperties(@Nonnull OWLDataPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLDataPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSuperProperties(e, o));
        }
        return collection;
    }

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and return the class expressions corresponding to super classes of this
     * class.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         superclasses of this class, which have been asserted in the
     *         specified ontology.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getSuperClasses(@Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.getSubClassAxiomsForSubClass(e));
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expression corresponding to the asserted super
     * classes of this class.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The set of ontologies to be examined.
     * @return A set of {@code OWLClassExpressions}s that represent the super
     *         classes of this class
     */
    @Nonnull
    public static Collection<OWLClassExpression> getSuperClasses(@Nonnull OWLClass e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLClassExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSuperClasses(e, o));
        }
        return collection;
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this
     * class in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology which should be examined for subclass axioms.
     * @return A {@code Set} of {@code OWLClassExpression}s that represet the
     *         asserted subclasses of this class.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getSubClasses(@Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.getSubClassAxiomsForSuperClass(e));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this
     * class in the specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies which should be examined for subclass axioms.
     * @return A {@code Set} of {@code OWLClassExpression}s that represet the
     *         asserted subclasses of this class.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getSubClasses(@Nonnull OWLClass e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLClassExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSubClasses(e, o));
        }
        return collection;
    }

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontology.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getEquivalentClasses(@Nonnull OWLClass e,
        @Nonnull OWLOntology ontology) {
        return Searcher.equivalent(ontology.getEquivalentClassesAxioms(e));
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontologies.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getEquivalentClasses(@Nonnull OWLClass e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLClassExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getEquivalentClasses(e, o));
        }
        return collection;
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to search for disjoint class axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getDisjointClasses(@Nonnull OWLClass e,
        @Nonnull OWLOntology ontology) {
        return Searcher.different(ontology.getDisjointClassesAxioms(e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to search for disjoint class axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getDisjointClasses(@Nonnull OWLClass e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLClassExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDisjointClasses(e, o));
        }
        return collection;
    }

    /**
     * Gets the different individuals in the specified ontology.
     * 
     * @param e
     *        individual
     * @param ontology
     *        The ontology to search for different individuals
     * @return A {@code Set} of different {@code OWLIndividual}s.
     */
    @Nonnull
    public static Collection<OWLIndividual> getDifferentIndividuals(@Nonnull OWLIndividual e,
        @Nonnull OWLOntology ontology) {
        return Searcher.different(ontology.getDifferentIndividualAxioms(e));
    }

    /**
     * Gets the different individuals in the specified ontologies.
     * 
     * @param e
     *        individual
     * @param ontologies
     *        The ontologies to search for different individuals
     * @return A {@code Set} of different {@code OWLIndividual}s.
     */
    @Nonnull
    public static Collection<OWLIndividual> getDifferentIndividuals(@Nonnull OWLIndividual e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLIndividual> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDifferentIndividuals(e, o));
        }
        return collection;
    }

    /**
     * Gets the same individuals in the specified ontology.
     * 
     * @param e
     *        individual
     * @param ontology
     *        The ontology to search for same individuals
     * @return A {@code Set} of same {@code OWLIndividual}s.
     */
    @Nonnull
    public static Collection<OWLIndividual> getSameIndividuals(@Nonnull OWLIndividual e,
        @Nonnull OWLOntology ontology) {
        return Searcher.equivalent(ontology.getSameIndividualAxioms(e));
    }

    /**
     * Gets the same individuals in the specified ontologies.
     * 
     * @param e
     *        individual
     * @param ontologies
     *        The ontologies to search for same individuals
     * @return A {@code Set} of same {@code OWLIndividual}s.
     */
    @Nonnull
    public static Collection<OWLIndividual> getSameIndividuals(@Nonnull OWLIndividual e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLIndividual> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getSameIndividuals(e, o));
        }
        return collection;
    }

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontology.
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getEquivalentProperties(@Nonnull OWLDataProperty e,
        @Nonnull OWLOntology ontology) {
        return Searcher.equivalent(ontology.getEquivalentDataPropertiesAxioms(e));
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontologies.
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getEquivalentProperties(@Nonnull OWLDataProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLDataPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getEquivalentProperties(e, o));
        }
        return collection;
    }

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for axioms
     * @param
     *        <P>
     *        type of property
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontology.
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getEquivalentProperties(@Nonnull P e,
        @Nonnull OWLOntology ontology) {
        if (e.isDataPropertyExpression()) {
            return Searcher.equivalent(ontology.getEquivalentDataPropertiesAxioms((OWLDataProperty) e));
        }
        return Searcher.equivalent(ontology.getEquivalentObjectPropertiesAxioms((OWLObjectPropertyExpression) e));
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for axioms
     * @param
     *        <P>
     *        type of property
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontologies.
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getEquivalentProperties(@Nonnull P e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<P> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getEquivalentProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to search for disjoint class axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getDisjointProperties(@Nonnull OWLDataProperty e,
        @Nonnull OWLOntology ontology) {
        return Searcher.different(ontology.getDisjointDataPropertiesAxioms(e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to search for disjoint class axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static Collection<OWLDataPropertyExpression> getDisjointProperties(@Nonnull OWLDataProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLDataPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDisjointProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to search for disjoint class axioms
     * @param
     *        <P>
     *        type of property
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getDisjointProperties(@Nonnull P e,
        @Nonnull OWLOntology ontology) {
        if (e.isDataPropertyExpression()) {
            return Searcher.different(ontology.getDisjointDataPropertiesAxioms((OWLDataProperty) e));
        }
        return Searcher.different(ontology.getDisjointObjectPropertiesAxioms((OWLObjectPropertyExpression) e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to search for disjoint class axioms
     * @param
     *        <P>
     *        type of property
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static <P extends OWLPropertyExpression> Collection<P> getDisjointProperties(@Nonnull P e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<P> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDisjointProperties(e, o));
        }
        return collection;
    }

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontology.
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getEquivalentProperties(
        @Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology ontology) {
        return Searcher.equivalent(ontology.getEquivalentObjectPropertiesAxioms(e));
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expressions corresponding to equivalent classes of
     * this class.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         equivalent classes of this class, that have been asserted in the
     *         specified ontologies.
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getEquivalentProperties(
        @Nonnull OWLObjectPropertyExpression e, @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLObjectPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getEquivalentProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to search for disjoint class axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getDisjointProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.different(ontology.getDisjointObjectPropertiesAxioms(e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class
     * by axioms in the specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to search for disjoint class axioms
     * @return A {@code Set} of {@code OWLClassExpression}s that represent the
     *         disjoint classes of this class.
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getDisjointProperties(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLObjectPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDisjointProperties(e, o));
        }
        return collection;
    }

    /**
     * Gets the individuals that have been asserted to be an instance of this
     * class by axioms in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for class assertion axioms that assert
     *        an individual to be an instance of this class.
     * @return A {@code Set} of {@code OWLIndividual}s that represent the
     *         individual that have been asserted to be an instance of this
     *         class.
     */
    @Nonnull
    public static Collection<OWLIndividual> getIndividuals(@Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
        return Searcher.instances(ontology.getClassAssertionAxioms(e));
    }

    /**
     * Gets the individuals that have been asserted to be an instance of this
     * class by axioms in the speficied ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for class assertion axioms that
     *        assert an individual to be an instance of this class.
     * @return A {@code Set} of {@code OWLIndividual}s that represent the
     *         individual that have been asserted to be an instance of this
     *         class.
     */
    @Nonnull
    public static Collection<OWLIndividual> getIndividuals(@Nonnull OWLClass e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<OWLIndividual> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getIndividuals(e, o));
        }
        return list;
    }

    /**
     * Gets the axioms in the specified ontology that contain this entity in
     * their signature.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology that will be searched for axioms
     * @return The axioms in the specified ontology whose signature contains
     *         this entity.
     */
    @Nonnull
    public static Collection<OWLAxiom> getReferencingAxioms(@Nonnull OWLEntity e, @Nonnull OWLOntology ontology) {
        return ontology.getReferencingAxioms(e, EXCLUDED);
    }

    /**
     * Gets the axioms in the specified ontology and possibly its imports
     * closure that contain this entity in their signature.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology that will be searched for axioms
     * @param includeImports
     *        If {@code true} then axioms in the imports closure will also be
     *        returned, if {@code false} then only the axioms in the specified
     *        ontology will be returned.
     * @return The axioms in the specified ontology whose signature contains
     *         this entity.
     */
    @Nonnull
    public static Collection<OWLAxiom> getReferencingAxioms(@Nonnull OWLEntity e, @Nonnull OWLOntology ontology,
        boolean includeImports) {
        return ontology.getReferencingAxioms(e, includeImports ? INCLUDED : EXCLUDED);
    }

    /**
     * Gets the asserted domains of this property.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology that should be examined for axioms which assert a
     *        domain of this property
     * @return A set of {@code OWLClassExpression}s corresponding to the domains
     *         of this property (the domain of the property is essentially the
     *         intersection of these class expressions).
     */
    @Nonnull
    public static Collection<OWLClassExpression> getDomains(@Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.domain(ontology.getDataPropertyDomainAxioms(e));
    }

    /**
     * Gets the asserted domains of this property by examining the axioms in the
     * specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined.
     * @return A set of {@code OWLClassExpression}s that represent the asserted
     *         domains of this property.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getDomains(@Nonnull OWLDataProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<OWLClassExpression> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getDomains(e, o));
        }
        return list;
    }

    /**
     * Gets the asserted domains of this property.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology that should be examined for axioms which assert a
     *        domain of this property
     * @return A set of {@code OWLClassExpression}s corresponding to the domains
     *         of this property (the domain of the property is essentially the
     *         intersection of these class expressions).
     */
    @Nonnull
    public static Collection<OWLClassExpression> getDomains(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.domain(ontology.getObjectPropertyDomainAxioms(e));
    }

    /**
     * Gets the ranges of this property that have been asserted in the specified
     * ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be searched for axioms which assert a range for
     *        this property.
     * @return A set of ranges for this property.
     */
    @Nonnull
    public static Collection<OWLDataRange> getRanges(@Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.range(ontology.getDataPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of this property by examining the axioms in the
     * specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted by
     *         axioms in the specified ontologies.
     */
    @Nonnull
    public static Collection<OWLDataRange> getRanges(@Nonnull OWLDataProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<OWLDataRange> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getRanges(e, o));
        }
        return list;
    }

    /**
     * Gets the asserted domains of this property by examining the axioms in the
     * specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined.
     * @return A set of {@code OWLClassExpression}s that represent the asserted
     *         domains of this property.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getDomains(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<OWLClassExpression> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getDomains(e, o));
        }
        return list;
    }

    /**
     * Gets the ranges of this property that have been asserted in the specified
     * ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be searched for axioms which assert a range for
     *        this property.
     * @return A set of ranges for this property.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getRanges(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.range(ontology.getObjectPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of this property by examining the axioms in the
     * specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted by
     *         axioms in the specified ontologies.
     */
    @Nonnull
    public static Collection<OWLClassExpression> getRanges(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<OWLClassExpression> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getRanges(e, o));
        }
        return list;
    }

    /**
     * Gets the asserted domains of this property.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology that should be examined for axioms which assert a
     *        domain of this property
     * @return A set of {@code OWLClassExpression}s corresponding to the domains
     *         of this property (the domain of the property is essentially the
     *         intersection of these class expressions).
     */
    @Nonnull
    public static Collection<IRI> getDomains(@Nonnull OWLAnnotationProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.domain(ontology.getAnnotationPropertyDomainAxioms(e));
    }

    /**
     * Gets the asserted domains of this property by examining the axioms in the
     * specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined.
     * @return A set of {@code OWLClassExpression}s that represent the asserted
     *         domains of this property.
     */
    @Nonnull
    public static Collection<IRI> getDomains(@Nonnull OWLAnnotationProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<IRI> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getDomains(e, o));
        }
        return list;
    }

    /**
     * Gets the ranges of this property that have been asserted in the specified
     * ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be searched for axioms which assert a range for
     *        this property.
     * @return A set of ranges for this property.
     */
    @Nonnull
    public static Collection<IRI> getRanges(@Nonnull OWLAnnotationProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.range(ontology.getAnnotationPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of this property by examining the axioms in the
     * specified ontologies.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted by
     *         axioms in the specified ontologies.
     */
    @Nonnull
    public static Collection<IRI> getRanges(@Nonnull OWLAnnotationProperty e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        List<IRI> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getRanges(e, o));
        }
        return list;
    }

    /**
     * Checks if e is declared transitive in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for transitive properties
     */
    public static boolean isTransitive(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getTransitiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared transitive in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for transitive properties
     */
    public static boolean isTransitive(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isTransitive(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared symmetric in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getSymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared symmetric in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isSymmetric(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared asymmetric in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getAsymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared asymmetric in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isAsymmetric(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared reflexive in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getReflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared reflexive in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isReflexive(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared irreflexive in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getIrreflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared irreflexive in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isIrreflexive(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared inverse functional in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getInverseFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared inverse functional in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isInverseFunctional(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared functional in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for functional object properties
     */
    public static boolean isFunctional(@Nonnull OWLObjectPropertyExpression e, @Nonnull OWLOntology o) {
        return !o.getFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared functional in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for functional object properties
     */
    public static boolean isFunctional(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isFunctional(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if e is declared functional in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for functional data properties
     */
    public static boolean isFunctional(@Nonnull OWLDataProperty e, @Nonnull OWLOntology o) {
        return !o.getFunctionalDataPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared functional in a collection of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param e
     *        property
     * @return true for functional data properties
     */
    public static boolean isFunctional(@Nonnull OWLDataProperty e, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isFunctional(e, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in o.
     * 
     * @param o
     *        ontology
     * @param c
     *        class
     * @return true for defined classes
     */
    public static boolean isDefined(@Nonnull OWLClass c, @Nonnull OWLOntology o) {
        return !o.getEquivalentClassesAxioms(c).isEmpty();
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in a collection
     * of ontologies.
     * 
     * @param ontologies
     *        ontologies
     * @param c
     *        class
     * @return true for defined classes
     */
    public static boolean isDefined(@Nonnull OWLClass c, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (isDefined(c, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if o contains axiom a, with or without imports closure.
     * 
     * @param o
     *        ontology
     * @param a
     *        axiom
     * @param imports
     *        true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiom(@Nonnull OWLAxiom a, @Nonnull OWLOntology o, boolean imports) {
        return o.containsAxiom(a, imports ? Imports.INCLUDED : Imports.EXCLUDED,
            AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS);
    }

    /**
     * Checks if any of the ontologies contains axiom a, with or without imports
     * closure.
     * 
     * @param ontologies
     *        ontologies
     * @param a
     *        axiom
     * @param imports
     *        true if imports closure is included @return true if a is contained
     * @return true if a is contained
     */
    public static boolean containsAxiom(@Nonnull OWLAxiom a, @Nonnull Iterable<OWLOntology> ontologies,
        boolean imports) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (containsAxiom(a, o, imports)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if o contains axiom a, with or without imports closure, ignoring
     * annotations.
     * 
     * @param o
     *        ontology
     * @param a
     *        axiom
     * @param imports
     *        true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom a, @Nonnull OWLOntology o, boolean imports) {
        return o.containsAxiom(a, imports == true ? Imports.INCLUDED : Imports.EXCLUDED,
            AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
    }

    /**
     * Checks if any of the ontologies contains axiom a, with or without imports
     * closure.
     * 
     * @param ontologies
     *        ontologies
     * @param a
     *        axiom
     * @param imports
     *        true if imports closure is included @return true if a is contained
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom a, @Nonnull Iterable<OWLOntology> ontologies,
        boolean imports) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (containsAxiomIgnoreAnnotations(a, o, imports)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get matching axioms for a, ignoring annotations.
     * 
     * @param o
     *        ontology
     * @param a
     *        axiom
     * @param imports
     *        true if imports closure is included
     * @return matching axioms
     */
    public static Collection<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom a, @Nonnull OWLOntology o,
        boolean imports) {
        return o.getAxiomsIgnoreAnnotations(a, imports == true ? Imports.INCLUDED : Imports.EXCLUDED);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return literal values
     */
    @Nonnull
    public static Collection<OWLLiteral> getDataPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLDataPropertyExpression p, @Nonnull OWLOntology ontology) {
        return Searcher.values(ontology.getDataPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return literal values
     */
    @Nonnull
    public static Collection<OWLLiteral> getDataPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLDataPropertyExpression p, @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLLiteral> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDataPropertyValues(i, p, o));
        }
        return collection;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Collection<OWLIndividual> getObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull OWLOntology ontology) {
        return Searcher.values(ontology.getObjectPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    @Nonnull
    public static Collection<OWLIndividual> getObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLIndividual> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getObjectPropertyValues(i, p, o));
        }
        return collection;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Collection<OWLLiteral> getNegativeDataPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLDataPropertyExpression p, @Nonnull OWLOntology ontology) {
        return Searcher.negValues(ontology.getNegativeDataPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    @Nonnull
    public static Collection<OWLLiteral> getNegativeDataPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLDataPropertyExpression p, @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLLiteral> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getNegativeDataPropertyValues(i, p, o));
        }
        return collection;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Collection<OWLIndividual> getNegativeObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull OWLOntology ontology) {
        return Searcher.negValues(ontology.getNegativeObjectPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    @Nonnull
    public static Collection<OWLIndividual> getNegativeObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLIndividual> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getNegativeObjectPropertyValues(i, p, o));
        }
        return collection;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return true if values are present
     */
    public static boolean hasDataPropertyValues(@Nonnull OWLIndividual i, OWLDataPropertyExpression p,
        @Nonnull OWLOntology ontology) {
        return !Searcher.values(ontology.getDataPropertyAssertionAxioms(i), p).isEmpty();
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValues(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasDataPropertyValues(i, p, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValues(@Nonnull OWLIndividual i, @Nonnull OWLObjectPropertyExpression p,
        @Nonnull OWLOntology ontology) {
        return !Searcher.values(ontology.getObjectPropertyAssertionAxioms(i), p).isEmpty();
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValues(@Nonnull OWLIndividual i, @Nonnull OWLObjectPropertyExpression p,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasObjectPropertyValues(i, p, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValues(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull OWLOntology ontology) {
        return !Searcher.negValues(ontology.getNegativeDataPropertyAssertionAxioms(i), p).isEmpty();
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValues(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasNegativeDataPropertyValues(i, p, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull OWLOntology ontology) {
        return !Searcher.negValues(ontology.getNegativeObjectPropertyAssertionAxioms(i), p).isEmpty();
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasNegativeObjectPropertyValues(i, p, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param lit
     *        literal to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValue(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull OWLLiteral lit, @Nonnull OWLOntology ontology) {
        return Searcher.values(ontology.getDataPropertyAssertionAxioms(i), p).contains(lit);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param lit
     *        literal to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValue(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull OWLLiteral lit, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasDataPropertyValue(i, p, lit, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param j
     *        individual to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValue(@Nonnull OWLIndividual i, @Nonnull OWLObjectPropertyExpression p,
        @Nonnull OWLIndividual j, @Nonnull OWLOntology ontology) {
        return Searcher.values(ontology.getObjectPropertyAssertionAxioms(i), p).contains(j);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param j
     *        individual to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValue(@Nonnull OWLIndividual i, @Nonnull OWLObjectPropertyExpression p,
        @Nonnull OWLIndividual j, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasObjectPropertyValue(i, p, j, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param lit
     *        literal to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValue(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull OWLLiteral lit, @Nonnull OWLOntology ontology) {
        return Searcher.negValues(ontology.getNegativeDataPropertyAssertionAxioms(i), p).contains(lit);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param lit
     *        literal to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValue(@Nonnull OWLIndividual i, @Nonnull OWLDataPropertyExpression p,
        @Nonnull OWLLiteral lit, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasNegativeDataPropertyValue(i, p, lit, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param j
     *        individual to search
     * @param ontology
     *        ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValue(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull OWLIndividual j, @Nonnull OWLOntology ontology) {
        return Searcher.negValues(ontology.getNegativeObjectPropertyAssertionAxioms(i), p).contains(j);
    }

    /**
     * @param i
     *        individual
     * @param p
     *        property to search
     * @param j
     *        individual to search
     * @param ontologies
     *        ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValue(@Nonnull OWLIndividual i,
        @Nonnull OWLObjectPropertyExpression p, @Nonnull OWLIndividual j, @Nonnull Iterable<OWLOntology> ontologies) {
        for (OWLOntology o : ontologies) {
            assert o != null;
            if (hasNegativeObjectPropertyValue(i, p, j, o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getDataPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLOntology ontology) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        for (OWLDataPropertyAssertionAxiom ax : ontology.getDataPropertyAssertionAxioms(i)) {
            map.put(ax.getProperty(), ax.getObject());
        }
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return literal values
     */
    @Nonnull
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getDataPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> collection = LinkedListMultimap.create();
        assert collection != null;
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.putAll(getDataPropertyValues(i, o));
        }
        return collection;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull OWLOntology ontology) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        for (OWLObjectPropertyAssertionAxiom ax : ontology.getObjectPropertyAssertionAxioms(i)) {
            map.put(ax.getProperty(), ax.getObject());
        }
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getObjectPropertyValues(@Nonnull OWLIndividual i,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        for (OWLOntology o : ontologies) {
            map.putAll(getObjectPropertyValues(i, o));
        }
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getNegativeObjectPropertyValues(
        @Nonnull OWLIndividual i, @Nonnull OWLOntology ontology) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology.getNegativeObjectPropertyAssertionAxioms(i)) {
            map.put(ax.getProperty(), ax.getObject());
        }
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getNegativeDataPropertyValues(
        @Nonnull OWLIndividual i, @Nonnull OWLOntology ontology) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        for (OWLNegativeDataPropertyAssertionAxiom ax : ontology.getNegativeDataPropertyAssertionAxioms(i)) {
            map.put(ax.getProperty(), ax.getObject());
        }
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getNegativeObjectPropertyValues(
        @Nonnull OWLIndividual i, @Nonnull Iterable<OWLOntology> ontologies) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        for (OWLOntology o : ontologies) {
            map.putAll(getNegativeObjectPropertyValues(i, o));
        }
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    @Nonnull
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getNegativeDataPropertyValues(
        @Nonnull OWLIndividual i, @Nonnull Iterable<OWLOntology> ontologies) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        for (OWLOntology o : ontologies) {
            map.putAll(getNegativeDataPropertyValues(i, o));
        }
        return map;
    }

    /**
     * @param e
     *        object property
     * @param ontology
     *        ontology to search
     * @return property inverses
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getInverses(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull OWLOntology ontology) {
        return Searcher.inverse(ontology.getInverseObjectPropertyAxioms(e), e);
    }

    /**
     * @param e
     *        object property
     * @param ontologies
     *        ontologies to search
     * @return property inverses
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> getInverses(@Nonnull OWLObjectPropertyExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLObjectPropertyExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getInverses(e, o));
        }
        return collection;
    }

    /**
     * @param e
     *        class
     * @param ontology
     *        ontology to search
     * @return instances of class
     */
    @Nonnull
    public static Collection<OWLIndividual> getInstances(@Nonnull OWLClassExpression e, @Nonnull OWLOntology ontology) {
        return Searcher.instances(ontology.getClassAssertionAxioms(e));
    }

    /**
     * @param e
     *        class
     * @param ontologies
     *        ontologies to search
     * @return instances of class
     */
    @Nonnull
    public static Collection<OWLIndividual> getInstances(@Nonnull OWLClassExpression e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLIndividual> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getInstances(e, o));
        }
        return list;
    }

    /**
     * @param e
     *        individual
     * @param ontology
     *        ontology to search
     * @return types for individual
     */
    @Nonnull
    public static Collection<OWLClassExpression> getTypes(@Nonnull OWLIndividual e, @Nonnull OWLOntology ontology) {
        return Searcher.types(ontology.getClassAssertionAxioms(e));
    }

    /**
     * @param e
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return types for individual
     */
    @Nonnull
    public static Collection<OWLClassExpression> getTypes(@Nonnull OWLIndividual e,
        @Nonnull Iterable<OWLOntology> ontologies) {
        Collection<OWLClassExpression> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getTypes(e, o));
        }
        return list;
    }
}
