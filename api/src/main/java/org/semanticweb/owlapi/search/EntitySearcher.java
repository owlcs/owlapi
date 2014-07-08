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
import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Convenience methods moved from OWLEntity and its subinterfaces.
 * 
 * @author ignazio
 */
public class EntitySearcher {

    /**
     * Gets the annotations for this entity. These are deemed to be annotations
     * in annotation assertion axioms that have a subject that is an IRI that is
     * equal to the IRI of this entity.
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
    public static Collection<OWLAnnotation> getAnnotations(
            @Nonnull OWLEntity e, @Nonnull OWLOntology ontology) {
        return getAnnotations(e.getIRI(), ontology);
    }

    /**
     * Gets the annotations for this entity. These are deemed to be annotations
     * in annotation assertion axioms that have a subject that is an IRI that is
     * equal to the IRI of this entity.
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
    public static Collection<OWLAnnotation> getAnnotations(
            @Nonnull OWLAnnotationSubject e, @Nonnull OWLOntology ontology) {
        return Searcher.annotations(ontology.getAnnotationAssertionAxioms(e));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property.
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
    public static Collection<OWLAnnotation> getAnnotations(
            @Nonnull OWLEntity e, @Nonnull OWLOntology ontology,
            @Nonnull OWLAnnotationProperty annotationProperty) {
        return getAnnotations(e.getIRI(), ontology, annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the
     * specified annotation property.
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
    public static Collection<OWLAnnotation> getAnnotations(
            @Nonnull OWLAnnotationSubject e, @Nonnull OWLOntology ontology,
            @Nonnull OWLAnnotationProperty annotationProperty) {
        return Searcher.annotations(ontology.getAnnotationAssertionAxioms(e),
                annotationProperty);
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
    public static Collection<OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxioms(@Nonnull OWLEntity e,
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
    public static Collection<OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxioms(@Nonnull OWLAnnotationSubject e,
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
    public static Collection<OWLAnnotationProperty> getSubProperties(
            @Nonnull OWLAnnotationProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subAnnotationWithSuper, e,
                EXCLUDED));
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
    public static Collection<OWLAnnotationProperty> getSubProperties(
            @Nonnull OWLAnnotationProperty e, @Nonnull OWLOntology ontology,
            boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subAnnotationWithSuper, e,
                imports ? INCLUDED : EXCLUDED));
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
    public static Collection<OWLAnnotationProperty> getSubProperties(
            @Nonnull OWLAnnotationProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLAnnotationProperty> getSuperProperties(
            @Nonnull OWLAnnotationProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(subAnnotationWithSub, e,
                EXCLUDED));
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
    public static Collection<OWLAnnotationProperty> getSuperProperties(
            @Nonnull OWLAnnotationProperty e, @Nonnull OWLOntology ontology,
            boolean imports) {
        return Searcher.sup(ontology.filterAxioms(subAnnotationWithSub, e,
                imports ? INCLUDED : EXCLUDED));
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
    public static Collection<OWLAnnotationProperty> getSuperProperties(
            @Nonnull OWLAnnotationProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLObjectProperty> getSubProperties(
            @Nonnull OWLObjectProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subObjectPropertyWithSuper,
                e, EXCLUDED));
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
    public static Collection<OWLObjectProperty> getSubProperties(
            @Nonnull OWLObjectProperty e, @Nonnull OWLOntology ontology,
            boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subObjectPropertyWithSuper,
                e, imports ? INCLUDED : EXCLUDED));
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
    public static Collection<OWLObjectProperty> getSubProperties(
            @Nonnull OWLObjectProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLObjectProperty> collection = new ArrayList<>();
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
    public static Collection<OWLObjectProperty> getSuperProperties(
            @Nonnull OWLObjectProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(subObjectPropertyWithSub, e,
                EXCLUDED));
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
    public static Collection<OWLObjectProperty> getSuperProperties(
            @Nonnull OWLObjectProperty e, @Nonnull OWLOntology ontology,
            boolean imports) {
        return Searcher.sup(ontology.filterAxioms(subObjectPropertyWithSub, e,
                imports ? INCLUDED : EXCLUDED));
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
    public static Collection<OWLObjectProperty> getSuperProperties(
            @Nonnull OWLObjectProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLObjectProperty> collection = new ArrayList<>();
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
    public static Collection<OWLDataProperty> getSubProperties(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.sub(ontology.filterAxioms(subDataPropertyWithSuper, e,
                EXCLUDED));
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
    public static Collection<OWLDataProperty> getSubProperties(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology,
            boolean imports) {
        return Searcher.sub(ontology.filterAxioms(subDataPropertyWithSuper, e,
                imports ? INCLUDED : EXCLUDED));
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
    public static Collection<OWLDataProperty> getSubProperties(
            @Nonnull OWLDataProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLDataProperty> collection = new ArrayList<>();
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
    public static Collection<OWLDataProperty> getSuperProperties(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.sup(ontology.filterAxioms(subDataPropertyWithSub, e,
                EXCLUDED));
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
    public static Collection<OWLDataProperty> getSuperProperties(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology,
            boolean imports) {
        return Searcher.sup(ontology.filterAxioms(subDataPropertyWithSub, e,
                imports ? INCLUDED : EXCLUDED));
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
    public static Collection<OWLDataProperty> getSuperProperties(
            @Nonnull OWLDataProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLDataProperty> collection = new ArrayList<>();
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
    public static Collection<OWLClassExpression> getSuperClasses(
            @Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLClassExpression> getSuperClasses(
            @Nonnull OWLClass e, @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLClassExpression> getSubClasses(
            @Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLClassExpression> getSubClasses(
            @Nonnull OWLClass e, @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLClassExpression> getEquivalentClasses(
            @Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLClassExpression> getEquivalentClasses(
            @Nonnull OWLClass e, @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLClassExpression> getDisjointClasses(
            @Nonnull OWLClass e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLClassExpression> getDisjointClasses(
            @Nonnull OWLClass e, @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLClassExpression> collection = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            collection.addAll(getDisjointClasses(e, o));
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
    public static Collection<OWLDataPropertyExpression>
            getEquivalentProperties(@Nonnull OWLDataProperty e,
                    @Nonnull OWLOntology ontology) {
        return Searcher.equivalent(ontology
                .getEquivalentDataPropertiesAxioms(e));
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
    public static Collection<OWLDataPropertyExpression>
            getEquivalentProperties(@Nonnull OWLDataProperty e,
                    @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLDataPropertyExpression> collection = new ArrayList<>();
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
    public static Collection<OWLDataPropertyExpression> getDisjointProperties(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLDataPropertyExpression> getDisjointProperties(
            @Nonnull OWLDataProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
        Collection<OWLDataPropertyExpression> collection = new ArrayList<>();
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
    public static Collection<OWLObjectPropertyExpression>
            getEquivalentProperties(@Nonnull OWLObjectProperty e,
                    @Nonnull OWLOntology ontology) {
        return Searcher.equivalent(ontology
                .getEquivalentObjectPropertiesAxioms(e));
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
    public static Collection<OWLObjectPropertyExpression>
            getEquivalentProperties(@Nonnull OWLObjectProperty e,
                    @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLObjectPropertyExpression>
            getDisjointProperties(@Nonnull OWLObjectProperty e,
                    @Nonnull OWLOntology ontology) {
        return Searcher
                .different(ontology.getDisjointObjectPropertiesAxioms(e));
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
    public static Collection<OWLObjectPropertyExpression>
            getDisjointProperties(@Nonnull OWLObjectProperty e,
                    @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLIndividual> getIndividuals(@Nonnull OWLClass e,
            @Nonnull OWLOntology ontology) {
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
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLAxiom> getReferencingAxioms(
            @Nonnull OWLEntity e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLAxiom> getReferencingAxioms(
            @Nonnull OWLEntity e, @Nonnull OWLOntology ontology,
            boolean includeImports) {
        return ontology.getReferencingAxioms(e, includeImports ? INCLUDED
                : EXCLUDED);
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
    public static Collection<OWLClassExpression> getDomains(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.domain(ontology.getDataPropertyRangeAxioms(e));
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
    public static Collection<OWLClassExpression> getDomains(
            @Nonnull OWLDataProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLDataRange> getRanges(
            @Nonnull OWLDataProperty e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLDataRange> getRanges(
            @Nonnull OWLDataProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
        List<OWLDataRange> list = new ArrayList<>();
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
    public static Collection<OWLClassExpression> getDomains(
            @Nonnull OWLObjectProperty e, @Nonnull OWLOntology ontology) {
        return Searcher.domain(ontology.getObjectPropertyRangeAxioms(e));
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
    public static Collection<OWLClassExpression> getDomains(
            @Nonnull OWLObjectProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<OWLClassExpression> getRanges(
            @Nonnull OWLObjectProperty e, @Nonnull OWLOntology ontology) {
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
    public static Collection<OWLClassExpression> getRanges(
            @Nonnull OWLObjectProperty e,
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<IRI> getDomains(@Nonnull OWLAnnotationProperty e,
            @Nonnull OWLOntology ontology) {
        return Searcher.domain(ontology.getAnnotationPropertyRangeAxioms(e));
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
            @Nonnull Collection<OWLOntology> ontologies) {
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
    public static Collection<IRI> getRanges(@Nonnull OWLAnnotationProperty e,
            @Nonnull OWLOntology ontology) {
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
            @Nonnull Collection<OWLOntology> ontologies) {
        List<IRI> list = new ArrayList<>();
        for (OWLOntology o : ontologies) {
            assert o != null;
            list.addAll(getRanges(e, o));
        }
        return list;
    }
}
