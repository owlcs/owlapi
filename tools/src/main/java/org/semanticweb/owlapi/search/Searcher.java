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

import static org.semanticweb.owlapi.model.parameters.AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi.model.parameters.AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.fromBoolean;
import static org.semanticweb.owlapi.search.Filters.subAnnotationWithSub;
import static org.semanticweb.owlapi.search.Filters.subAnnotationWithSuper;
import static org.semanticweb.owlapi.search.Filters.subDataPropertyWithSub;
import static org.semanticweb.owlapi.search.Filters.subDataPropertyWithSuper;
import static org.semanticweb.owlapi.search.Filters.subObjectPropertyWithSub;
import static org.semanticweb.owlapi.search.Filters.subObjectPropertyWithSuper;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.contains;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.empty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.HasObject;
import org.semanticweb.owlapi.model.HasProperty;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * A collection of static search utilities.
 *
 * @author ignazio
 */
public final class Searcher {

    private Searcher() {}

    private static boolean filter(@Nullable OWLPropertyExpression p, HasProperty<?> ax) {
        return p == null || ax.getProperty().equals(p);
    }

    private static <T extends OWLPropertyAssertionObject> Stream<T> filterValues(
        Stream<? extends OWLPropertyAssertionAxiom<?, T>> stream,
        @Nullable OWLPropertyExpression p) {
        return stream.filter(ax -> filter(p, ax)).map(HasObject::getObject).distinct();
    }

    private static OWLObjectPropertyExpression getInverse(OWLObjectPropertyExpression p,
        OWLInverseObjectPropertiesAxiom ax) {
        return ax.getFirstProperty().equals(p) ? ax.getSecondProperty() : ax.getFirstProperty();
    }

    /**
     * Retrieve literals from a collection of assertions.
     *
     * @param axioms axioms
     * @param p optional property to match. Null means all.
     * @return literals
     */
    public static Stream<OWLLiteral> values(Stream<OWLDataPropertyAssertionAxiom> axioms,
        @Nullable OWLDataPropertyExpression p) {
        return filterValues(axioms, p);
    }

    /**
     * Retrieve objects from a collection of assertions.
     *
     * @param axioms axioms
     * @param p optional property to match. Null means all.
     * @return objects
     */
    public static Stream<OWLIndividual> values(Stream<OWLObjectPropertyAssertionAxiom> axioms,
        @Nullable OWLObjectPropertyExpression p) {
        return filterValues(axioms, p);
    }

    /**
     * Retrieve literals from a collection of negative assertions.
     *
     * @param axioms axioms
     * @param p optional property to match. Null means all.
     * @return literals
     */
    public static Stream<OWLLiteral> negValues(Stream<OWLNegativeDataPropertyAssertionAxiom> axioms,
        @Nullable OWLDataPropertyExpression p) {
        return filterValues(axioms, p);
    }

    /**
     * Retrieve objects from a collection of negative assertions.
     *
     * @param axioms axioms
     * @param p optional property to match. Null means all.
     * @return objects
     */
    public static Stream<OWLIndividual> negValues(
        Stream<OWLNegativeObjectPropertyAssertionAxiom> axioms,
        @Nullable OWLObjectPropertyExpression p) {
        return filterValues(axioms, p);
    }

    /**
     * Retrieve classes from class assertions.
     *
     * @param axioms axioms
     * @return classes
     */
    public static Stream<OWLClassExpression> types(Stream<OWLClassAssertionAxiom> axioms) {
        return axioms.map(OWLClassAssertionAxiom::getClassExpression);
    }

    /**
     * Retrieve individuals from class assertions.
     *
     * @param axioms axioms
     * @return individuals
     */
    public static Stream<OWLIndividual> instances(Stream<OWLClassAssertionAxiom> axioms) {
        return axioms.map(OWLClassAssertionAxiom::getIndividual);
    }

    /**
     * Retrieve inverses from a collection of inverse axioms.
     *
     * @param axioms axioms to check
     * @param p property to match; not returned in the set
     * @return inverses of p
     */
    public static Stream<OWLObjectPropertyExpression> inverse(
        Stream<OWLInverseObjectPropertiesAxiom> axioms, OWLObjectPropertyExpression p) {
        return axioms.map(ax -> getInverse(p, ax));
    }

    /**
     * Retrieve annotation values from annotations.
     *
     * @param annotations annotations
     * @return annotation values
     */
    public static Stream<OWLAnnotationValue> values(Stream<OWLAnnotation> annotations) {
        return values(annotations, null);
    }

    /**
     * Retrieve annotation values from annotations.
     *
     * @param annotations annotations
     * @param p optional annotation property to match. Null means all.
     * @return annotation values
     */
    public static Stream<OWLAnnotationValue> values(Stream<OWLAnnotation> annotations,
        @Nullable OWLAnnotationProperty p) {
        return annotations.filter(a -> filter(p, a)).map(OWLAnnotation::getValue);
    }

    /**
     * Retrieve annotations from a collection of annotation assertion axioms.
     *
     * @param axioms axioms
     * @param p optional annotation property to match. Null means all.
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotationObjects(
        Stream<OWLAnnotationAssertionAxiom> axioms, @Nullable OWLAnnotationProperty p) {
        return axioms.flatMap(ax -> annotationObject(ax, p)).distinct();
    }

    /**
     * Retrieve the annotation from an annotation assertion axiom.
     *
     * @param axiom axiom
     * @param p optional annotation property to match. Null means all.
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotationObject(OWLAnnotationAssertionAxiom axiom,
        @Nullable OWLAnnotationProperty p) {
        if (p == null || axiom.getProperty().equals(p)) {
            return Stream.of(axiom.getAnnotation());
        }
        return Stream.empty();
    }

    /**
     * Retrieve annotations from a collection of annotation assertion axioms. This is limited to the
     * annotation object and excludes annotations on the axiom itself.
     *
     * @param axioms axioms
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotationObjects(
        Stream<OWLAnnotationAssertionAxiom> axioms) {
        return axioms.map(OWLAnnotationAssertionAxiom::getAnnotation).distinct();
    }

    /**
     * Retrieve annotations from a collection of axioms. For regular axioms, their annotations are
     * retrieved; for annotation assertion axioms, their asserted annotation is retrieved as well.
     *
     * @param axioms axioms
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotations(Stream<? extends OWLAxiom> axioms) {
        return annotations(axioms, null);
    }

    /**
     * Retrieve annotations from a collection of axioms. For regular axioms, their annotations are
     * retrieved; for annotation assertion axioms, their asserted annotation is retrieved as well.
     *
     * @param axioms axioms
     * @param p optional annotation property to match. Null means all.
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotations(Stream<? extends OWLAxiom> axioms,
        @Nullable OWLAnnotationProperty p) {
        return axioms.flatMap(ax -> annotations(ax, p)).distinct();
    }

    /**
     * Retrieve annotations from an axiom. For regular axioms, their annotations are retrieved; for
     * annotation assertion axioms, their asserted annotation is retrieved as well.
     *
     * @param axiom axiom
     * @param p optional annotation property to match. Null means all.
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotations(OWLAxiom axiom,
        @Nullable OWLAnnotationProperty p) {
        Stream<OWLAnnotation> stream = empty();
        if (axiom instanceof OWLAnnotationAssertionAxiom ann) {
            stream = Stream.of(ann.getAnnotation());
        }
        stream = Stream.concat(stream, axiom.annotations()).distinct().sorted();
        if (p != null) {
            stream = stream.filter(a -> a.getProperty().equals(p));
        }
        return stream.distinct();
    }

    /**
     * Retrieve equivalent entities from axioms, including individuals from sameAs axioms. A mixture
     * of axiom types can be passed in, as long as the entity type they contain is compatible with
     * the return type for the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @return equivalent entities
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> equivalent(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) equivalent(axioms, OWLObject.class);
    }

    /**
     * Retrieve equivalent entities from axioms, including individuals from sameAs axioms. A mixture
     * of axiom types can be passed in, as long as the entity type they contain is compatible with
     * the return type for the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @param type type contained in the returned collection
     * @return equivalent entities
     */
    public static <C extends OWLObject> Stream<C> equivalent(Stream<? extends OWLAxiom> axioms,
        Class<C> type) {
        return axioms.flatMap(ax -> equivalent(ax).map(type::cast));
    }

    /**
     * Retrieve equivalent entities from an axiom, including individuals from sameAs axioms.
     *
     * @param axiom axiom
     * @param <C> type contained in the returned collection
     * @return equivalent entities
     */
    public static <C extends OWLObject> Stream<C> equivalent(OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(true));
    }

    /**
     * Retrieve disjoint entities from axioms, including individuals from differentFrom axioms. A
     * mixture of axiom types can be passed in, as long as the entity type they contain is
     * compatible with the return type for the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @return disjoint entities
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> different(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) different(axioms, OWLObject.class);
    }

    /**
     * Retrieve disjoint entities from axioms, including individuals from differentFrom axioms. A
     * mixture of axiom types can be passed in, as long as the entity type they contain is
     * compatible with the return type for the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @param type type contained in the returned collection
     * @return disjoint entities
     */
    public static <C extends OWLObject> Stream<C> different(Stream<? extends OWLAxiom> axioms,
        Class<C> type) {
        return axioms.flatMap(ax -> different(ax).map(type::cast));
    }

    /**
     * Retrieve disjoint entities from an axiom, including individuals from differentFrom axioms.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @return disjoint entities
     */
    public static <C extends OWLObject> Stream<C> different(OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(false));
    }

    /**
     * Retrieve the sub part of axioms, i.e., subclass or subproperty. A mixture of axiom types can
     * be passed in, as long as the entity type they contain is compatible with the return type for
     * the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> sub(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) sub(axioms, OWLObject.class);
    }

    /**
     * Retrieve the sub part of axioms, i.e., subclass or subproperty. A mixture of axiom types can
     * be passed in, as long as the entity type they contain is compatible with the return type for
     * the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @param type type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> sub(Stream<? extends OWLAxiom> axioms,
        Class<C> type) {
        return axioms.map(ax -> type.cast(sub(ax)));
    }

    /**
     * Retrieve the sub part of an axiom, i.e., subclass or subproperty. A mixture of axiom types
     * can be passed in, as long as the entity type they contain is compatible with the return type
     * for the collection.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C sub(OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(false));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or super property. A mixture of axiom
     * types can be passed in, as long as the entity type they contain is compatible with the return
     * type for the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @param type type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> sup(Stream<? extends OWLAxiom> axioms,
        Class<C> type) {
        return axioms.map(ax -> type.cast(sup(ax)));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or super property. A mixture of axiom
     * types can be passed in, as long as the entity type they contain is compatible with the return
     * type for the collection.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> sup(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) sup(axioms, OWLObject.class);
    }

    /**
     * Retrieve the super part of an axiom, i.e., superclass or super property. A mixture of axiom
     * types can be passed in, as long as the entity type they contain is compatible with the return
     * type for the collection.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C sup(OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(true));
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> domain(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) domain(axioms, OWLObject.class);
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @param type type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> domain(Stream<? extends OWLAxiom> axioms,
        Class<C> type) {
        return axioms.map(ax -> type.cast(domain(ax)));
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C domain(OWLAxiom axiom) {
        return axiom.accept(new DomainVisitor<C>());
    }

    /**
     * Retrieve the ranges from range axioms. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> range(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) range(axioms, OWLObject.class);
    }

    /**
     * Retrieve the ranges from range axioms. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axioms axioms
     * @param type type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> range(Stream<? extends OWLAxiom> axioms,
        Class<C> type) {
        return axioms.map(ax -> type.cast(range(ax)));
    }

    /**
     * Retrieve the ranges from a range axiom. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C range(OWLAxiom axiom) {
        return axiom.accept(new RangeVisitor<C>());
    }


    /**
     * Transform a {@code Stream} of ontologies into a {@code Stream} of IRIs of those ontologies.
     * Anonymous ontologies are skipped.
     *
     * @param ontologies ontologies to transform
     * @return collection of IRIs for the ontologies.
     */
    public static Stream<IRI> ontologiesIRIs(Stream<OWLOntology> ontologies) {
        return ontologyIRIs(ontologies.map(OWLOntology::getOntologyID));
    }

    /**
     * Transform a {@code Stream} of ontology ids into a {@code Stream} of IRIs of those ontology
     * ids. Anonymous ontology ids are skipped.
     *
     * @param ids ontology ids to transform
     * @return collection of IRIs for the ontology ids.
     */
    public static Stream<IRI> ontologyIRIs(Stream<OWLOntologyID> ids) {
        return ids.filter(i -> i.getOntologyIRI().isPresent()).map(i -> i.getOntologyIRI().get());
    }

    /**
     * Gets the annotations for e. These are deemed to be annotations in annotation assertion axioms
     * that have a subject that is an IRI that is equal to the IRI of e, and it also includes
     * annotations on the annotation assertion axioms whose annotation property matches
     *
     * @param e entity
     * @param ontology The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation assertion whose subject is
     *         an IRI corresponding to the IRI of e.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, OWLOntology ontology) {
        return getAnnotations(e.getIRI(), ontology);
    }

    /**
     * Gets the annotations for e. These are deemed to be annotations in annotation assertion axioms
     * that have a subject that is an IRI that is equal to the IRI of e, and it also includes
     * annotations on the annotation assertion axioms whose annotation property matches.
     *
     * @param e entity
     * @param ontology The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation assertion whose subject is
     *         an IRI corresponding to the IRI of e.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e,
        OWLOntology ontology) {
        return annotations(ontology.annotationAssertionAxioms(e));
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property. This
     * includes the annotations on annotation assertion axioms with matching annotation property.
     *
     * @param e entity
     * @param ontology The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, OWLOntology ontology,
        OWLAnnotationProperty annotationProperty) {
        return getAnnotations(e.getIRI(), ontology, annotationProperty);
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property; this
     * is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param ontology The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property. If null, any annotation property will
     *        match.
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, OWLOntology ontology,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return getAnnotationObjects(e.getIRI(), ontology, annotationProperty);
    }

    /**
     * Obtains the annotations on e; this is restricted to the object of annotation assertion
     * axioms.
     *
     * @param e entity
     * @param ontology The ontology to examine for annotation axioms
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, OWLOntology ontology) {
        return getAnnotationObjects(e.getIRI(), ontology, null);
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property; this
     * is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param ontology The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property. If null, any annotation property will
     *        match.
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLAnnotationSubject e,
        OWLOntology ontology, @Nullable OWLAnnotationProperty annotationProperty) {
        return annotationObjects(ontology.annotationAssertionAxioms(e), annotationProperty);
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property; this
     * is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param ontologies The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property. If null, any annotation property will
     *        match.
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLAnnotationSubject e,
        Stream<OWLOntology> ontologies, @Nullable OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotationObjects(e, o, annotationProperty));
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property; this
     * is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param ontologies The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property. If null, any annotation property will
     *        match.
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e,
        Stream<OWLOntology> ontologies, @Nullable OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotationObjects(e, o, annotationProperty));
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property. This
     * includes the annotations on annotation assertion axioms with matching annotation property.
     *
     * @param e entity
     * @param ontology The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e, OWLOntology ontology,
        OWLAnnotationProperty annotationProperty) {
        return annotations(ontology.annotationAssertionAxioms(e), annotationProperty);
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property. This
     * includes the annotations on annotation assertion axioms with matching annotation property.
     *
     * @param e entity
     * @param ontologies The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e,
        Stream<OWLOntology> ontologies, OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotations(e, o, annotationProperty));
    }

    /**
     * Obtains the annotations on e where the annotation has the specified annotation property. This
     * includes the annotations on annotation assertion axioms with matching annotation property.
     *
     * @param e entity
     * @param ontologies The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A {@code Stream} of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, Stream<OWLOntology> ontologies,
        OWLAnnotationProperty annotationProperty) {
        return ontologies.flatMap(o -> getAnnotations(e, o, annotationProperty));
    }

    /**
     * @param e entity
     * @param ontology the ontology to use
     * @return the annotation assertion axioms about e in the provided ontology
     */
    public static Stream<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLEntity e,
        OWLOntology ontology) {
        return getAnnotationAssertionAxioms(e.getIRI(), ontology);
    }

    /**
     * @param e entity
     * @param ontology the ontology to use
     * @return the annotation assertion axioms about e in the provided ontology
     */
    public static Stream<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
        OWLAnnotationSubject e, OWLOntology ontology) {
        return ontology.annotationAssertionAxioms(e);
    }

    /**
     * Gets the properties which are asserted to be sub-properties of e in the specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} the
     *         {@code ontology} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLObjectPropertyExpression> getSubProperties(
        OWLObjectPropertyExpression e, OWLOntology ontology) {
        return sub(ontology.axioms(subObjectPropertyWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of e in the specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} the
     *         {@code ontology} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLDataProperty> getSubProperties(OWLDataProperty e,
        OWLOntology ontology) {
        return sub(ontology.axioms(subDataPropertyWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of e in the specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} the
     *         {@code ontology} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLAnnotationProperty> getSubProperties(OWLAnnotationProperty e,
        OWLOntology ontology) {
        return sub(ontology.axioms(subAnnotationWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of e in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} one of the
     *         ontologies in {@code ontologies} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLObjectPropertyExpression> getSubProperties(
        OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSubProperties(e, o));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of e in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} one of the
     *         ontologies in {@code ontologies} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLDataProperty> getSubProperties(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSubProperties(e, o));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of e in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} one of the
     *         ontologies in {@code ontologies} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLAnnotationProperty> getSubProperties(OWLAnnotationProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSubProperties(e, o));
    }

    /**
     * Gets the object property expressions (entities or inverses) which are asserted to be
     * super-properties of e in the specified ontology.
     *
     * @param e object property expression
     * @param ontology The ontology to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} {@code ontology}
     *         contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLObjectPropertyExpression> getSuperProperties(
        OWLObjectPropertyExpression e, OWLOntology ontology) {
        return sup(ontology.axioms(subObjectPropertyWithSub, e, EXCLUDED));
    }

    /**
     * Gets the data properties which are asserted to be super-properties of e in the specified
     * ontology.
     *
     * @param e data property
     * @param ontology The ontology to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} {@code ontology}
     *         contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLDataProperty> getSuperProperties(OWLDataProperty e,
        OWLOntology ontology) {
        return sup(ontology.axioms(subDataPropertyWithSub, e, EXCLUDED));
    }

    /**
     * Gets the annotation properties which are asserted to be super-properties of e in the
     * specified ontology.
     *
     * @param e annotation property
     * @param ontology The ontology to be examined for SubProperty axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} {@code ontology}
     *         contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLAnnotationProperty> getSuperProperties(OWLAnnotationProperty e,
        OWLOntology ontology) {
        return sup(ontology.axioms(subAnnotationWithSub, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be super-properties of e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for SubPropertyOf axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} one of the
     *         ontologies in {@code ontologies} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLObjectPropertyExpression> getSuperProperties(
        OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSuperProperties(e, o));
    }

    /**
     * Gets the properties which are asserted to be super-properties of e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for SubPropertyOf axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} one of the
     *         ontologies in {@code ontologies} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLDataProperty> getSuperProperties(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSuperProperties(e, o));
    }

    /**
     * Gets the properties which are asserted to be super-properties of e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for SubPropertyOf axioms.
     * @return A {@code Stream} of properties such that for each property {@code p} one of the
     *         ontologies in {@code ontologies} contains an {@code SubPropertyOf(p, e)} axiom.
     */
    public static Stream<OWLAnnotationProperty> getSuperProperties(OWLAnnotationProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSuperProperties(e, o));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be superclasses of e in the specified
     * ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the superclasses of e,
     *         which have been asserted in the specified ontology.
     */
    public static Stream<OWLClassExpression> getSuperClasses(OWLClass e, OWLOntology ontology) {
        return sup(ontology.subClassAxiomsForSubClass(e));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be superclasses of e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined.
     * @return A {@code Stream} of {@code OWLClassExpressions}s that represent the super classes of
     *         e
     */
    public static Stream<OWLClassExpression> getSuperClasses(OWLClass e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSuperClasses(e, o));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of e in the specified
     * ontology.
     *
     * @param e entity
     * @param ontology The ontology which should be examined for subclass axioms.
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the asserted
     *         subclasses of e.
     */
    public static Stream<OWLClassExpression> getSubClasses(OWLClass e, OWLOntology ontology) {
        return sub(ontology.subClassAxiomsForSuperClass(e));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies which should be examined for subclass axioms.
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the asserted
     *         subclasses of e.
     */
    public static Stream<OWLClassExpression> getSubClasses(OWLClass e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSubClasses(e, o));
    }

    /**
     * Gets the classes which have been asserted to be equivalent with e by axioms in the specified
     * ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of e, that have been asserted in the specified ontology.
     */
    public static Stream<OWLClassExpression> getEquivalentClasses(OWLClass e,
        OWLOntology ontology) {
        return equivalent(ontology.equivalentClassesAxioms(e)).filter(c -> !c.equals(e))
            .map(OWLClassExpression.class::cast);
    }

    /**
     * Gets the classes which have been asserted to be equivalent with e by axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of e, that have been asserted in the specified ontologies.
     */
    public static Stream<OWLClassExpression> getEquivalentClasses(OWLClass e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getEquivalentClasses(e, o));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with e by axioms in the specified
     * ontology.
     *
     * @param e entity
     * @param ontology The ontology to search for disjoint class axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the disjoint classes
     *         of e.
     */
    public static Stream<OWLClassExpression> getDisjointClasses(OWLClass e, OWLOntology ontology) {
        return different(ontology.disjointClassesAxioms(e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with e by axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to search for disjoint class axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the disjoint classes
     *         of e.
     */
    public static Stream<OWLClassExpression> getDisjointClasses(OWLClass e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDisjointClasses(e, o));
    }

    /**
     * Gets the individuals asserted to be different to e in the specified ontology.
     *
     * @param e individual
     * @param ontology The ontology to search for different individuals
     * @return A {@code Stream} of different {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getDifferentIndividuals(OWLIndividual e,
        OWLOntology ontology) {
        return different(ontology.differentIndividualAxioms(e));
    }

    /**
     * Gets the individuals asserted to be different to e in the specified ontologies.
     *
     * @param e individual
     * @param ontologies The ontologies to search for different individuals
     * @return A {@code Stream} of different {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getDifferentIndividuals(OWLIndividual e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDifferentIndividuals(e, o));
    }

    /**
     * Gets the individuals asserted to be same as e in the specified ontology.
     *
     * @param e individual
     * @param ontology The ontology to search for same individuals
     * @return A {@code Stream} of same {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getSameIndividuals(OWLIndividual e, OWLOntology ontology) {
        return equivalent(ontology.sameIndividualAxioms(e), OWLIndividual.class)
            .filter(c -> !c.equals(e));
    }

    /**
     * Gets the individuals asserted to be same as e in the specified ontologies.
     *
     * @param e individual
     * @param ontologies The ontologies to search for same individuals
     * @return A {@code Stream} of same {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getSameIndividuals(OWLIndividual e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getSameIndividuals(e, o));
    }

    /**
     * Gets the data properties which have been asserted to be equivalent with e by axioms in the
     * specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLDataPropertyExpression}s that represent the data
     *         properties equivalent to e, that have been asserted in the specified ontology.
     */
    public static Stream<OWLDataPropertyExpression> getEquivalentProperties(OWLDataProperty e,
        OWLOntology ontology) {
        return equivalent(ontology.equivalentDataPropertiesAxioms(e),
            OWLDataPropertyExpression.class).filter(c -> !c.equals(e));
    }

    /**
     * Gets the data properties which have been asserted to be equivalent with e by axioms in the
     * specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLDataPropertyExpression}s that represent the data
     *         properties equivalent to e, that have been asserted in the specified ontologies.
     */
    public static Stream<OWLDataPropertyExpression> getEquivalentProperties(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getEquivalentProperties(e, o)).filter(c -> !c.equals(e));
    }

    /**
     * Gets the annotation properties which have been asserted to be equivalent with e by axioms in
     * the specified ontology. Note: this method is here to avoid special casing annotation
     * properties. They cannot be asserted to be equivalent, so result is always empty.
     *
     * @param e entity
     * @param ontology The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLAnnotationProperty}s that represent the annotation
     *         properties equivalent to e, that have been asserted in the specified ontology.
     */
    @SuppressWarnings("unused")
    public static Stream<OWLAnnotationProperty> getEquivalentProperties(OWLAnnotationProperty e,
        OWLOntology ontology) {
        return Stream.empty();
    }

    /**
     * Gets the annotation properties which have been asserted to be equivalent with e by axioms in
     * the specified ontologies. Note: this method is here to avoid special casing annotation
     * properties. They cannot be asserted to be equivalent, so result is always empty.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLAnnotationProperty}s that represent the annotation
     *         properties equivalent to e, that have been asserted in the specified ontologies.
     */
    @SuppressWarnings("unused")
    public static Stream<OWLAnnotationProperty> getEquivalentProperties(OWLAnnotationProperty e,
        Stream<OWLOntology> ontologies) {
        return Stream.empty();
    }

    /**
     * Gets the object properties which have been asserted to be equivalent with e by axioms in the
     * specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLObjectPropertyExpression}s that represent the object
     *         properties equivalent to e, that have been asserted in the specified ontology.
     */
    public static Stream<OWLObjectPropertyExpression> getEquivalentProperties(
        OWLObjectPropertyExpression e, OWLOntology ontology) {
        return equivalent(ontology.equivalentObjectPropertiesAxioms(e),
            OWLObjectPropertyExpression.class).filter(c -> !c.equals(e));
    }

    /**
     * Gets the object properties which have been asserted to be equivalent with e by axioms in the
     * specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLObjectPropertyExpression}s that represent the object
     *         properties equivalent to e, that have been asserted in the specified ontologies.
     */
    public static Stream<OWLObjectPropertyExpression> getEquivalentProperties(
        OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getEquivalentProperties(e, o)).filter(c -> !c.equals(e));
    }

    /**
     * Gets the object properties which have been asserted to be disjoint with e in the specified
     * ontology.
     *
     * @param e entity
     * @param ontology The ontology to search for disjoint axioms
     * @return A {@code Stream} of {@code OWLObjectPropertyExpression}s that represent the object
     *         properties disjoint with e.
     */
    public static Stream<OWLObjectPropertyExpression> getDisjointProperties(
        OWLObjectPropertyExpression e, OWLOntology ontology) {
        return different(ontology.disjointObjectPropertiesAxioms(e));
    }

    /**
     * Gets the object properties which have been asserted to be disjoint with e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to search for disjoint axioms
     * @return A {@code Stream} of {@code OWLObjectPropertyExpression}s that represent the object
     *         properties disjoint with e.
     */
    public static Stream<OWLObjectPropertyExpression> getDisjointProperties(
        OWLObjectPropertyExpression e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDisjointProperties(e, o));
    }

    /**
     * Gets the data properties which have been asserted to be disjoint with e in the specified
     * ontology.
     *
     * @param e entity
     * @param ontology The ontology to search for disjoint axioms
     * @return A {@code Stream} of {@code OWLDataProperty}s that represent the data properties
     *         disjoint with e.
     */
    public static Stream<OWLDataProperty> getDisjointProperties(OWLDataProperty e,
        OWLOntology ontology) {
        return different(ontology.disjointDataPropertiesAxioms(e));
    }

    /**
     * Gets the data properties which have been asserted to be disjoint with e in the specified
     * ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to search for disjoint axioms
     * @return A {@code Stream} of {@code OWLDataProperty}s that represent the data properties
     *         disjoint with e.
     */
    public static Stream<OWLDataProperty> getDisjointProperties(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDisjointProperties(e, o));
    }

    /**
     * Gets the annotation properties which have been asserted to be disjoint with e in the
     * specified ontology. Note: This method is here for backwards compatibility. No disjoint axioms
     * for annotation properties.
     *
     * @param e entity
     * @param ontology The ontology to search for disjoint axioms
     * @return A {@code Stream} of {@code OWLAnnotationProperty}s that represent the properties
     *         disjoint with e.
     */
    @SuppressWarnings("unused")
    public static Stream<OWLAnnotationProperty> getDisjointProperties(OWLAnnotationProperty e,
        OWLOntology ontology) {
        // no annotation property disjoints
        return empty();
    }

    /**
     * Gets the annotation properties which have been asserted to be disjoint with e in the
     * specified ontologies. Note: This method is here for backwards compatibility. No disjoint
     * axioms for annotation properties.
     *
     * @param e entity
     * @param ontologies The ontologies to search for disjoint axioms
     * @return A {@code Stream} of {@code OWLAnnotationProperty}s that represent the properties
     *         disjoint with e.
     */
    @SuppressWarnings("unused")
    public static Stream<OWLAnnotationProperty> getDisjointProperties(OWLAnnotationProperty e,
        Stream<OWLOntology> ontologies) {
        // no annotation property disjoints
        return empty();
    }

    /**
     * Gets the individuals that have been asserted to be an instance of e by axioms in the
     * specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be examined for class assertion axioms that assert an
     *        individual to be an instance of e.
     * @return A {@code Stream} of {@code OWLIndividual}s that represent the individual that have
     *         been asserted to be an instance of e.
     */
    public static Stream<OWLIndividual> getIndividuals(OWLClass e, OWLOntology ontology) {
        return instances(ontology.classAssertionAxioms(e));
    }

    /**
     * Gets the individuals that have been asserted to be an instance of e by axioms in the
     * specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for class assertion axioms that assert an
     *        individual to be an instance of e.
     * @return A {@code Stream} of {@code OWLIndividual}s that represent the individual that have
     *         been asserted to be an instance of e.
     */
    public static Stream<OWLIndividual> getIndividuals(OWLClass e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getIndividuals(e, o));
    }

    /**
     * Gets the axioms in the specified ontology that contain e in their signature.
     *
     * @param e entity
     * @param ontology The ontology that will be searched for axioms
     * @return The axioms in the specified ontology whose signature contains e.
     */
    public static Stream<OWLAxiom> getReferencingAxioms(OWLEntity e, OWLOntology ontology) {
        return ontology.referencingAxioms(e, EXCLUDED);
    }

    /**
     * Gets the axioms in the specified ontology and possibly its imports closure that contain e in
     * their signature.
     *
     * @param e entity
     * @param ontology The ontology that will be searched for axioms
     * @param includeImports If {@code true} then axioms in the imports closure will also be
     *        returned, if {@code false} then only the axioms in the specified ontology will be
     *        returned.
     * @return The axioms in the specified ontology whose signature contains e.
     */
    public static Stream<OWLAxiom> getReferencingAxioms(OWLEntity e, OWLOntology ontology,
        Imports includeImports) {
        return ontology.referencingAxioms(e, includeImports);
    }

    /**
     * Gets the asserted domains of e.
     *
     * @param e entity
     * @param ontology The ontology that should be examined for axioms which assert a domain of e
     * @return A {@code Stream} of {@code OWLClassExpression}s corresponding to the domains of e
     *         (the domain of e is essentially the intersection of these class expressions).
     */
    public static Stream<OWLClassExpression> getDomains(OWLDataProperty e, OWLOntology ontology) {
        return domain(ontology.dataPropertyDomainAxioms(e));
    }

    /**
     * Gets the asserted domains of e by examining the axioms in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined.
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the asserted domains
     *         of e.
     */
    public static Stream<OWLClassExpression> getDomains(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDomains(e, o));
    }

    /**
     * Gets the asserted domains of e.
     *
     * @param e entity
     * @param ontology The ontology that should be examined for axioms which assert a domain of e
     * @return A {@code Stream} of {@code OWLClassExpression}s corresponding to the domains of e
     *         (the domain of e is essentially the intersection of these class expressions).
     */
    public static Stream<OWLClassExpression> getDomains(OWLObjectPropertyExpression e,
        OWLOntology ontology) {
        return domain(ontology.objectPropertyDomainAxioms(e));
    }

    /**
     * Gets the ranges of e that have been asserted in the specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be searched for axioms which assert a range for e.
     * @return A {@code Stream} of ranges for e.
     */
    public static Stream<OWLDataRange> getRanges(OWLDataProperty e, OWLOntology ontology) {
        return range(ontology.dataPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of e by examining the axioms in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for range axioms.
     * @return A {@code Stream} of ranges for e, which have been asserted by axioms in the specified
     *         ontologies.
     */
    public static Stream<OWLDataRange> getRanges(OWLDataProperty e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getRanges(e, o));
    }

    /**
     * Gets the asserted domains of e by examining the axioms in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined.
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the asserted domains
     *         of e.
     */
    public static Stream<OWLClassExpression> getDomains(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDomains(e, o));
    }

    /**
     * Gets the ranges of e that have been asserted in the specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be searched for axioms which assert a range for e.
     * @return A {@code Stream} of ranges for e.
     */
    public static Stream<OWLClassExpression> getRanges(OWLObjectPropertyExpression e,
        OWLOntology ontology) {
        return range(ontology.objectPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of e by examining the axioms in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for range axioms.
     * @return A {@code Stream} of ranges for e, which have been asserted by axioms in the specified
     *         ontologies.
     */
    public static Stream<OWLClassExpression> getRanges(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getRanges(e, o));
    }

    /**
     * Gets the asserted domains of e.
     *
     * @param e entity
     * @param ontology The ontology that should be examined for axioms which assert a domain of e
     * @return A {@code Stream} of {@code IRI}s corresponding to the domains of e (the domain of e
     *         is essentially the intersection of these IRIs).
     */
    public static Stream<IRI> getDomains(OWLAnnotationProperty e, OWLOntology ontology) {
        return domain(ontology.annotationPropertyDomainAxioms(e));
    }

    /**
     * Gets the asserted domains of e by examining the axioms in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined.
     * @return A {@code Stream} of {@code IRI}s that represent the asserted domains of e.
     */
    public static Stream<IRI> getDomains(OWLAnnotationProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDomains(e, o));
    }

    /**
     * Gets the ranges of e that have been asserted in the specified ontology.
     *
     * @param e entity
     * @param ontology The ontology to be searched for axioms which assert a range for e.
     * @return A {@code Stream} of ranges for e.
     */
    public static Stream<IRI> getRanges(OWLAnnotationProperty e, OWLOntology ontology) {
        return range(ontology.annotationPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of e by examining the axioms in the specified ontologies.
     *
     * @param e entity
     * @param ontologies The ontologies to be examined for range axioms.
     * @return A {@code Stream} of ranges for e, which have been asserted by axioms in the specified
     *         ontologies.
     */
    public static Stream<IRI> getRanges(OWLAnnotationProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getRanges(e, o));
    }

    /**
     * Checks if e is declared transitive in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for transitive properties
     */
    public static boolean isTransitive(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.transitiveObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared transitive in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for transitive properties
     */
    public static boolean isTransitive(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isTransitive(e, o));
    }

    /**
     * Checks if e is declared symmetric in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.symmetricObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared symmetric in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isSymmetric(e, o));
    }

    /**
     * Checks if e is declared asymmetric in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.asymmetricObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared asymmetric in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isAsymmetric(e, o));
    }

    /**
     * Checks if e is declared reflexive in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.reflexiveObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared reflexive in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isReflexive(e, o));
    }

    /**
     * Checks if e is declared irreflexive in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.irreflexiveObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared irreflexive in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isIrreflexive(e, o));
    }

    /**
     * Checks if e is declared inverse functional in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.inverseFunctionalObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared inverse functional in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isInverseFunctional(e, o));
    }

    /**
     * Checks if e is declared functional in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for functional object properties
     */
    public static boolean isFunctional(OWLObjectPropertyExpression e, OWLOntology ontology) {
        return ontology.functionalObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared functional in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for functional object properties
     */
    public static boolean isFunctional(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isFunctional(e, o));
    }

    /**
     * Checks if e is declared functional in the ontology.
     *
     * @param ontology ontology
     * @param e property
     * @return true for functional data properties
     */
    public static boolean isFunctional(OWLDataProperty e, OWLOntology ontology) {
        return ontology.functionalDataPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared functional in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param e property
     * @return true for functional data properties
     */
    public static boolean isFunctional(OWLDataProperty e, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isFunctional(e, o));
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in the ontology.
     *
     * @param ontology ontology
     * @param c class
     * @return true for defined classes
     */
    public static boolean isDefined(OWLClass c, OWLOntology ontology) {
        return ontology.equivalentClassesAxioms(c).findAny().isPresent();
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in a collection of ontologies.
     *
     * @param ontologies ontologies
     * @param c class
     * @return true for defined classes
     */
    public static boolean isDefined(OWLClass c, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> isDefined(c, o));
    }

    /**
     * Checks if the ontology contains axiom a, with or without imports closure.
     *
     * @param ontology ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiom(OWLAxiom a, OWLOntology ontology, Imports imports) {
        return ontology.containsAxiom(a, imports, CONSIDER_AXIOM_ANNOTATIONS);
    }

    /**
     * Checks if any of the ontologies contains axiom a, with or without imports closure.
     *
     * @param ontologies ontologies
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiom(OWLAxiom a, Stream<OWLOntology> ontologies,
        Imports imports) {
        return ontologies.anyMatch(o -> containsAxiom(a, o, imports));
    }

    /**
     * Checks if the ontology contains axiom a, with or without imports closure, ignoring
     * annotations.
     *
     * @param ontology ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(OWLAxiom a, OWLOntology ontology,
        boolean imports) {
        return ontology.containsAxiom(a, fromBoolean(imports), IGNORE_AXIOM_ANNOTATIONS);
    }

    /**
     * Checks if any of the ontologies contains axiom a, with or without imports closure.
     *
     * @param ontologies ontologies
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(OWLAxiom a, Stream<OWLOntology> ontologies,
        boolean imports) {
        return ontologies.anyMatch(o -> containsAxiomIgnoreAnnotations(a, o, imports));
    }

    /**
     * Get matching axioms for a, ignoring annotations.
     *
     * @param ontology ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return matching axioms
     */
    public static Collection<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom a, OWLOntology ontology,
        Imports imports) {
        return ontology.axiomsIgnoreAnnotations(a, imports).toList();
    }

    /**
     * Get matching axioms for a, ignoring annotations.
     *
     * @param ontology ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return matching axioms
     */
    public static Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom a, OWLOntology ontology,
        Imports imports) {
        return ontology.axiomsIgnoreAnnotations(a, imports);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return literal values
     */
    public static Stream<OWLLiteral> getDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, OWLOntology ontology) {
        return values(ontology.dataPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return literal values
     */
    public static Stream<OWLLiteral> getDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return property values
     */
    public static Stream<OWLIndividual> getObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLOntology ontology) {
        return values(ontology.objectPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return property values
     */
    public static Stream<OWLIndividual> getObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return property values
     */
    public static Stream<OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, OWLOntology ontology) {
        return negValues(ontology.negativeDataPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return property values
     */
    public static Stream<OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getNegativeDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return property values
     */
    public static Stream<OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLOntology ontology) {
        return negValues(ontology.negativeObjectPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return property values
     */
    public static Stream<OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getNegativeObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return true if values are present
     */
    public static boolean hasDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        OWLOntology ontology) {
        return values(ontology.dataPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLOntology ontology) {
        return values(ontology.objectPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, OWLOntology ontology) {
        return negValues(ontology.negativeDataPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLOntology ontology) {
        return negValues(ontology.negativeObjectPropertyAssertionAxioms(i), p).findAny()
            .isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, OWLOntology ontology) {
        return contains(values(ontology.dataPropertyAssertionAxioms(i), p), lit);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasDataPropertyValue(i, p, lit, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLIndividual j, OWLOntology ontology) {
        return contains(values(ontology.objectPropertyAssertionAxioms(i), p), j);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLIndividual j, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasObjectPropertyValue(i, p, j, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, OWLOntology ontology) {
        return contains(negValues(ontology.negativeDataPropertyAssertionAxioms(i), p), lit);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeDataPropertyValue(i, p, lit, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param ontology ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValue(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLIndividual j, OWLOntology ontology) {
        return contains(negValues(ontology.negativeObjectPropertyAssertionAxioms(i), p), j);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param ontologies ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValue(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLIndividual j, Stream<OWLOntology> ontologies) {
        return ontologies.anyMatch(o -> hasNegativeObjectPropertyValue(i, p, j, o));
    }

    /**
     * @param i individual
     * @param ontology ontology to search
     * @return property values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getDataPropertyValues(
        OWLIndividual i, OWLOntology ontology) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        ontology.dataPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param ontologies ontologies to search
     * @return literal values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getDataPropertyValues(
        OWLIndividual i, Stream<OWLOntology> ontologies) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        ontologies.map(o -> getDataPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    /**
     * @param i individual
     * @param ontology ontology to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getObjectPropertyValues(
        OWLIndividual i, OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        ontology.objectPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param ontologies ontologies to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getObjectPropertyValues(
        OWLIndividual i, Stream<OWLOntology> ontologies) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        ontologies.map(o -> getObjectPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    /**
     * @param i individual
     * @param ontology ontology to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getNegativeObjectPropertyValues(
        OWLIndividual i, OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        ontology.negativeObjectPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param ontology ontology to search
     * @return property values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getNegativeDataPropertyValues(
        OWLIndividual i, OWLOntology ontology) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        ontology.negativeDataPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param ontologies ontologies to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getNegativeObjectPropertyValues(
        OWLIndividual i, Stream<OWLOntology> ontologies) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        ontologies.map(o -> getNegativeObjectPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    /**
     * @param i individual
     * @param ontologies ontologies to search
     * @return property values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getNegativeDataPropertyValues(
        OWLIndividual i, Stream<OWLOntology> ontologies) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        ontologies.map(o -> getNegativeDataPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    private static <T, K> void merge(Map<T, List<K>> merge, Map<T, List<K>> m) {
        m.forEach((a, b) -> merge.computeIfAbsent(a, x -> new ArrayList<>()).addAll(b));
    }

    /**
     * @param e object property
     * @param ontology ontology to search
     * @return property inverses
     */
    public static Stream<OWLObjectPropertyExpression> getInverses(OWLObjectPropertyExpression e,
        OWLOntology ontology) {
        return inverse(ontology.inverseObjectPropertyAxioms(e), e);
    }

    /**
     * @param e object property
     * @param ontologies ontologies to search
     * @return property inverses
     */
    public static Stream<OWLObjectPropertyExpression> getInverses(OWLObjectPropertyExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getInverses(e, o));
    }

    /**
     * @param e class
     * @param ontology ontology to search
     * @return instances of class
     */
    public static Stream<OWLIndividual> getInstances(OWLClassExpression e, OWLOntology ontology) {
        return instances(ontology.classAssertionAxioms(e));
    }

    /**
     * @param e class
     * @param ontologies ontologies to search
     * @return instances of class
     */
    public static Stream<OWLIndividual> getInstances(OWLClassExpression e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getInstances(e, o));
    }

    /**
     * @param e individual
     * @param ontology ontology to search
     * @return types for individual
     */
    public static Stream<OWLClassExpression> getTypes(OWLIndividual e, OWLOntology ontology) {
        return types(ontology.classAssertionAxioms(e));
    }

    /**
     * @param e individual
     * @param ontologies ontologies to search
     * @return types for individual
     */
    public static Stream<OWLClassExpression> getTypes(OWLIndividual e,
        Stream<OWLOntology> ontologies) {
        return ontologies.flatMap(o -> getTypes(e, o));
    }
}
