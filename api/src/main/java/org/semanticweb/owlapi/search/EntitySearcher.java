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

import static org.semanticweb.owlapi.model.parameters.AxiomAnnotations.*;
import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.search.Filters.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Collection;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;

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
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, OWLOntology ontology) {
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
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e, OWLOntology ontology) {
        return Searcher.annotations(ontology.annotationAssertionAxioms(e));
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
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, OWLOntology ontology,
        OWLAnnotationProperty annotationProperty) {
        return getAnnotations(e.getIRI(), ontology, annotationProperty);
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
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, OWLOntology ontology,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return getAnnotationObjects(e.getIRI(), ontology, annotationProperty);
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
     * @return A set of {@code OWLAnnotation} objects that have the specified
     *         URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, OWLOntology ontology) {
        return getAnnotationObjects(e.getIRI(), ontology, null);
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
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLAnnotationSubject e, OWLOntology ontology,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return Searcher.annotationObjects(ontology.annotationAssertionAxioms(e), annotationProperty);
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
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLAnnotationSubject e, Stream<OWLOntology> ontologies,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotationObjects(e, o, annotationProperty));
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
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, Stream<OWLOntology> ontologies,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotationObjects(e, o, annotationProperty));
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
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e, OWLOntology ontology,
        OWLAnnotationProperty annotationProperty) {
        return Searcher.annotations(ontology.annotationAssertionAxioms(e), annotationProperty);
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
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e, Stream<OWLOntology> ontologies,
        OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotations(e, o, annotationProperty));
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
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, Stream<OWLOntology> ontologies,
        OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotations(e, o, annotationProperty));
    }

    /**
     * @param e
     *        entity
     * @param ontology
     *        the ontology to use
     * @return the annotation assertion axioms about this entity in the provided
     *         ontology
     */
    public static Stream<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLEntity e, OWLOntology ontology) {
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
    public static Stream<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject e,
        OWLOntology ontology) {
        return ontology.annotationAssertionAxioms(e);
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     *
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for SubProperty axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSubProperties(P e, OWLOntology ontology) {
        return Searcher.sub(ontology.axioms(subAnnotationWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for SubProperty axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSubProperties(P e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSubProperties(e, o));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontology
     *        The ontology to be examined for SubProperty axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSuperProperties(P e, OWLOntology ontology) {
        return Searcher.sup(ontology.axioms(subAnnotationWithSub, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this
     * property in the specified ontology.
     * 
     * @param e
     *        entity
     * @param ontologies
     *        The ontologies to be examined for SubPropertyOf axioms.
     * @return A set of properties such that for each property {@code p} in the
     *         set, it is the case that {@code ontology} contains an
     *         {@code SubPropertyOf(p, this)} axiom where {@code this} refers to
     *         this property.
     * @since 3.2
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSuperProperties(P e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSuperProperties(e, o));
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
    public static Stream<OWLClassExpression> getSuperClasses(OWLClass e, OWLOntology ontology) {
        return Searcher.sup(ontology.subClassAxiomsForSubClass(e));
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
    public static Stream<OWLClassExpression> getSuperClasses(OWLClass e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSuperClasses(e, o));
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
    public static Stream<OWLClassExpression> getSubClasses(OWLClass e, OWLOntology ontology) {
        return Searcher.sub(ontology.subClassAxiomsForSuperClass(e));
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
    public static Stream<OWLClassExpression> getSubClasses(OWLClass e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSubClasses(e, o));
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
    public static Stream<OWLClassExpression> getEquivalentClasses(OWLClass e, OWLOntology ontology) {
        return Searcher.equivalent(ontology.equivalentClassesAxioms(e));
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
    public static Stream<OWLClassExpression> getEquivalentClasses(OWLClass e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getEquivalentClasses(e, o));
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
    public static Stream<OWLClassExpression> getDisjointClasses(OWLClass e, OWLOntology ontology) {
        return Searcher.different(ontology.disjointClassesAxioms(e));
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
    public static Stream<OWLClassExpression> getDisjointClasses(OWLClass e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDisjointClasses(e, o));
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
    public static Stream<OWLIndividual> getDifferentIndividuals(OWLIndividual e, OWLOntology ontology) {
        return Searcher.different(ontology.differentIndividualAxioms(e));
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
    public static Stream<OWLIndividual> getDifferentIndividuals(OWLIndividual e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDifferentIndividuals(e, o));
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
    public static Stream<OWLIndividual> getSameIndividuals(OWLIndividual e, OWLOntology ontology) {
        return Searcher.equivalent(ontology.sameIndividualAxioms(e));
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
    public static Stream<OWLIndividual> getSameIndividuals(OWLIndividual e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSameIndividuals(e, o));
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
    public static Stream<OWLDataPropertyExpression> getEquivalentProperties(OWLDataProperty e, OWLOntology ontology) {
        return Searcher.equivalent(ontology.equivalentDataPropertiesAxioms(e));
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
    public static Stream<OWLDataPropertyExpression> getEquivalentProperties(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getEquivalentProperties(e, o));
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
    public static <P extends OWLPropertyExpression> Stream<P> getDisjointProperties(P e, OWLOntology ontology) {
        if (e.isObjectPropertyExpression()) {
            return Searcher.different(ontology.disjointObjectPropertiesAxioms(e.asObjectPropertyExpression()));
        }
        if (e.isDataPropertyExpression()) {
            return Searcher.different(ontology.disjointDataPropertiesAxioms((OWLDataProperty) e));
        }
        // else e must have been an annotation property. No disjoints on those
        return empty();
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
    public static <P extends OWLPropertyExpression> Stream<P> getDisjointProperties(P e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDisjointProperties(e, o));
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
    public static Stream<OWLObjectPropertyExpression> getEquivalentProperties(OWLObjectPropertyExpression e,
        OWLOntology ontology) {
        return Searcher.equivalent(ontology.equivalentObjectPropertiesAxioms(e));
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
    public static Stream<OWLObjectPropertyExpression> getEquivalentProperties(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getEquivalentProperties(e, o));
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
    public static Stream<OWLIndividual> getIndividuals(OWLClass e, OWLOntology ontology) {
        return Searcher.instances(ontology.classAssertionAxioms(e));
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
    public static Stream<OWLIndividual> getIndividuals(OWLClass e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getIndividuals(e, o));
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
    public static Stream<OWLAxiom> getReferencingAxioms(OWLEntity e, OWLOntology ontology) {
        return ontology.referencingAxioms(e, EXCLUDED);
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
    public static Stream<OWLAxiom> getReferencingAxioms(OWLEntity e, OWLOntology ontology, Imports includeImports) {
        return ontology.referencingAxioms(e, includeImports);
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
    public static Stream<OWLClassExpression> getDomains(OWLDataProperty e, OWLOntology ontology) {
        return Searcher.domain(ontology.dataPropertyDomainAxioms(e));
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
    public static Stream<OWLClassExpression> getDomains(OWLDataProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDomains(e, o));
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
    public static Stream<OWLClassExpression> getDomains(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return Searcher.domain(ontology.objectPropertyDomainAxioms(e));
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
    public static Stream<OWLDataRange> getRanges(OWLDataProperty e, OWLOntology ontology) {
        return Searcher.range(ontology.dataPropertyRangeAxioms(e));
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
    public static Stream<OWLDataRange> getRanges(OWLDataProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getRanges(e, o));
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
    public static Stream<OWLClassExpression> getDomains(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDomains(e, o));
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
    public static Stream<OWLClassExpression> getRanges(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return Searcher.range(ontology.objectPropertyRangeAxioms(e));
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
    public static Stream<OWLClassExpression> getRanges(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getRanges(e, o));
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
    public static Stream<IRI> getDomains(OWLAnnotationProperty e, OWLOntology ontology) {
        return Searcher.domain(ontology.annotationPropertyRangeAxioms(e));
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
    public static Stream<IRI> getDomains(OWLAnnotationProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDomains(e, o));
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
    public static Stream<IRI> getRanges(OWLAnnotationProperty e, OWLOntology ontology) {
        return Searcher.range(ontology.annotationPropertyRangeAxioms(e));
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
    public static Stream<IRI> getRanges(OWLAnnotationProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getRanges(e, o));
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
    public static boolean isTransitive(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.transitiveObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isTransitive(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isTransitive(e, o));
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
    public static boolean isSymmetric(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.symmetricObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isSymmetric(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isSymmetric(e, o));
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
    public static boolean isAsymmetric(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.asymmetricObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isAsymmetric(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isAsymmetric(e, o));
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
    public static boolean isReflexive(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.reflexiveObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isReflexive(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isReflexive(e, o));
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
    public static boolean isIrreflexive(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.irreflexiveObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isIrreflexive(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isIrreflexive(e, o));
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
    public static boolean isInverseFunctional(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.inverseFunctionalObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isInverseFunctional(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isInverseFunctional(e, o));
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
    public static boolean isFunctional(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.functionalObjectPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isFunctional(OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isFunctional(e, o));
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
    public static boolean isFunctional(OWLDataProperty e, OWLOntology o) {
        return o.functionalDataPropertyAxioms(e).findAny().isPresent();
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
    public static boolean isFunctional(OWLDataProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isFunctional(e, o));
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
    public static boolean isDefined(OWLClass c, OWLOntology o) {
        return o.equivalentClassesAxioms(c).findAny().isPresent();
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
    public static boolean isDefined(OWLClass c, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isDefined(c, o));
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
    public static boolean containsAxiom(OWLAxiom a, OWLOntology o, Imports imports) {
        return o.containsAxiom(a, imports, CONSIDER_AXIOM_ANNOTATIONS);
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
     *        true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiom(OWLAxiom a, Stream<OWLOntology> ontologies, Imports imports) {
        return ontologies.anyMatch(o -> containsAxiom(a, o, imports));
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
    public static boolean containsAxiomIgnoreAnnotations(OWLAxiom a, OWLOntology o, boolean imports) {
        return o.containsAxiom(a, fromBoolean(imports), IGNORE_AXIOM_ANNOTATIONS);
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
     *        true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(OWLAxiom a, Stream<OWLOntology> ontologies, boolean imports) {
        return ontologies.anyMatch(o -> containsAxiomIgnoreAnnotations(a, o, imports));
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
    public static Collection<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom a, OWLOntology o, Imports imports) {
        return asList(o.axiomsIgnoreAnnotations(a, imports));
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
    public static Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom a, OWLOntology o, Imports imports) {
        return o.axiomsIgnoreAnnotations(a, imports);
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
    public static Stream<OWLLiteral> getDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.values(ontology.dataPropertyAssertionAxioms(i), p);
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
    public static Stream<OWLLiteral> getDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDataPropertyValues(i, p, o));
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
    public static Stream<OWLIndividual> getObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.values(ontology.objectPropertyAssertionAxioms(i), p);
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
    public static Stream<OWLIndividual> getObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getObjectPropertyValues(i, p, o));
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
    public static Stream<OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.negValues(ontology.negativeDataPropertyAssertionAxioms(i), p);
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
    public static Stream<OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getNegativeDataPropertyValues(i, p, o));
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
    public static Stream<OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.negValues(ontology.negativeObjectPropertyAssertionAxioms(i), p);
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
    public static Stream<OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getNegativeObjectPropertyValues(i, p, o));
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
    public static boolean hasDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p, OWLOntology ontology) {
        return Searcher.values(ontology.dataPropertyAssertionAxioms(i), p).findAny().isPresent();
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
    public static boolean hasDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasDataPropertyValues(i, p, o));
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
    public static boolean hasObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.values(ontology.objectPropertyAssertionAxioms(i), p).findAny().isPresent();
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
    public static boolean hasObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasObjectPropertyValues(i, p, o));
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
    public static boolean hasNegativeDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.negValues(ontology.negativeDataPropertyAssertionAxioms(i), p).findAny().isPresent();
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
    public static boolean hasNegativeDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeDataPropertyValues(i, p, o));
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
    public static boolean hasNegativeObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLOntology ontology) {
        return Searcher.negValues(ontology.negativeObjectPropertyAssertionAxioms(i), p).findAny().isPresent();
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
    public static boolean hasNegativeObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeObjectPropertyValues(i, p, o));
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
    public static boolean hasDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p, OWLLiteral lit,
        OWLOntology ontology) {
        return contains(Searcher.values(ontology.dataPropertyAssertionAxioms(i), p), lit);
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
    public static boolean hasDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p, OWLLiteral lit,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasDataPropertyValue(i, p, lit, o));
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
    public static boolean hasObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p, OWLIndividual j,
        OWLOntology ontology) {
        return contains(Searcher.values(ontology.objectPropertyAssertionAxioms(i), p), j);
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
    public static boolean hasObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p, OWLIndividual j,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasObjectPropertyValue(i, p, j, o));
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
    public static boolean hasNegativeDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p, OWLLiteral lit,
        OWLOntology ontology) {
        return contains(Searcher.negValues(ontology.negativeDataPropertyAssertionAxioms(i), p), lit);
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
    public static boolean hasNegativeDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p, OWLLiteral lit,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeDataPropertyValue(i, p, lit, o));
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
    public static boolean hasNegativeObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLIndividual j, OWLOntology ontology) {
        return contains(Searcher.negValues(ontology.negativeObjectPropertyAssertionAxioms(i), p), j);
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
    public static boolean hasNegativeObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLIndividual j, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeObjectPropertyValue(i, p, j, o));
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getDataPropertyValues(OWLIndividual i,
        OWLOntology ontology) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        ontology.dataPropertyAssertionAxioms(i).forEach(ax -> map.put(ax.getProperty(), ax.getObject()));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return literal values
     */
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getDataPropertyValues(OWLIndividual i,
        Stream<OWLOntology> ontologies) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        ontologies.forEach(o -> map.putAll(getDataPropertyValues(i, o)));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getObjectPropertyValues(OWLIndividual i,
        OWLOntology ontology) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        ontology.objectPropertyAssertionAxioms(i).forEach(ax -> map.put(ax.getProperty(), ax.getObject()));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getObjectPropertyValues(OWLIndividual i,
        Stream<OWLOntology> ontologies) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        ontologies.forEach(o -> map.putAll(getObjectPropertyValues(i, o)));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i,
        OWLOntology ontology) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        ontology.negativeObjectPropertyAssertionAxioms(i).forEach(ax -> map.put(ax.getProperty(), ax.getObject()));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontology
     *        ontology to search
     * @return property values
     */
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i,
        OWLOntology ontology) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        ontology.negativeDataPropertyAssertionAxioms(i).forEach(ax -> map.put(ax.getProperty(), ax.getObject()));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    public static Multimap<OWLObjectPropertyExpression, OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i,
        Stream<OWLOntology> ontologies) {
        Multimap<OWLObjectPropertyExpression, OWLIndividual> map = LinkedListMultimap.create();
        ontologies.forEach(o -> map.putAll(getNegativeObjectPropertyValues(i, o)));
        return map;
    }

    /**
     * @param i
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return property values
     */
    public static Multimap<OWLDataPropertyExpression, OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i,
        Stream<OWLOntology> ontologies) {
        Multimap<OWLDataPropertyExpression, OWLLiteral> map = LinkedListMultimap.create();
        ontologies.forEach(o -> map.putAll(getNegativeDataPropertyValues(i, o)));
        return map;
    }

    /**
     * @param e
     *        object property
     * @param ontology
     *        ontology to search
     * @return property inverses
     */
    public static Stream<OWLObjectPropertyExpression> getInverses(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return Searcher.inverse(ontology.inverseObjectPropertyAxioms(e), e);
    }

    /**
     * @param e
     *        object property
     * @param ontologies
     *        ontologies to search
     * @return property inverses
     */
    public static Stream<OWLObjectPropertyExpression> getInverses(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getInverses(e, o));
    }

    /**
     * @param e
     *        class
     * @param ontology
     *        ontology to search
     * @return instances of class
     */
    public static Stream<OWLIndividual> getInstances(OWLClassExpression e, OWLOntology ontology) {
        return Searcher.instances(ontology.classAssertionAxioms(e));
    }

    /**
     * @param e
     *        class
     * @param ontologies
     *        ontologies to search
     * @return instances of class
     */
    public static Stream<OWLIndividual> getInstances(OWLClassExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getInstances(e, o));
    }

    /**
     * @param e
     *        individual
     * @param ontology
     *        ontology to search
     * @return types for individual
     */
    public static Stream<OWLClassExpression> getTypes(OWLIndividual e, OWLOntology ontology) {
        return Searcher.types(ontology.classAssertionAxioms(e));
    }

    /**
     * @param e
     *        individual
     * @param ontologies
     *        ontologies to search
     * @return types for individual
     */
    public static Stream<OWLClassExpression> getTypes(OWLIndividual e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getTypes(e, o));
    }
}
