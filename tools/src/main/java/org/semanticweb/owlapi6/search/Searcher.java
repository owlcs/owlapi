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
package org.semanticweb.owlapi6.search;

import static org.semanticweb.owlapi6.model.parameters.AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi6.model.parameters.AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS;
import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.model.parameters.Imports.fromBoolean;
import static org.semanticweb.owlapi6.search.Filters.subAnnotationWithSub;
import static org.semanticweb.owlapi6.search.Filters.subAnnotationWithSuper;
import static org.semanticweb.owlapi6.search.Filters.subDataPropertyWithSub;
import static org.semanticweb.owlapi6.search.Filters.subDataPropertyWithSuper;
import static org.semanticweb.owlapi6.search.Filters.subObjectPropertyWithSub;
import static org.semanticweb.owlapi6.search.Filters.subObjectPropertyWithSuper;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.empty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.HasObject;
import org.semanticweb.owlapi6.model.HasProperty;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnnotationValue;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataRange;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.parameters.Imports;

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
        if (axiom instanceof OWLAnnotationAssertionAxiom) {
            stream = Stream.of(((OWLAnnotationAssertionAxiom) axiom).getAnnotation());
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
        return axioms.flatMap(ax -> equivalent(ax, type));
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
     * Retrieve equivalent entities from an axiom, including individuals from sameAs axioms.
     *
     * @param axiom axiom
     * @param type type returned
     * @param <C> type contained in the returned collection
     * @return equivalent entities
     */
    public static <C extends OWLObject> Stream<C> equivalent(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
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
        return axioms.flatMap(ax -> different(ax, type));
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
     * Retrieve disjoint entities from an axiom, including individuals from differentFrom axioms.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @param type witness for returned type
     * @return disjoint entities
     */
    public static <C extends OWLObject> Stream<C> different(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
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
        return axioms.map(ax -> sub(ax, type));
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
     * Retrieve the sub part of an axiom, i.e., subclass or subproperty. A mixture of axiom types
     * can be passed in, as long as the entity type they contain is compatible with the return type
     * for the collection.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @param type witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C sub(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new SupSubVisitor<C>(false));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or superproperty. A mixture of axiom
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
        return axioms.map(ax -> sup(ax, type));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or superproperty. A mixture of axiom
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
     * Retrieve the super part of an axiom, i.e., superclass or superproperty. A mixture of axiom
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
     * Retrieve the super part of an axiom, i.e., superclass or superproperty. A mixture of axiom
     * types can be passed in, as long as the entity type they contain is compatible with the return
     * type for the collection.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @param type witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C sup(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
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
        return axioms.map(ax -> domain(ax, type));
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
     * Retrieve the domains from domain axioms. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @param type witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C domain(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
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
        return axioms.map(ax -> range(ax, type));
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
     * Retrieve the ranges from a range axiom. A mixture of axiom types can be passed in.
     *
     * @param <C> returned type
     * @param axiom axiom
     * @param type witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C range(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new RangeVisitor<C>());
    }

    /**
     * Transform a stream of ontologies into a stream of IRIs of those ontologies. Anonymous
     * ontologies are skipped.
     *
     * @param onts ontologies to transform
     * @return collection of IRIs for the ontologies.
     */
    public static Stream<IRI> ontologiesIRIs(Stream<OWLOntology> onts) {
        return ontologyIRIs(onts.map(OWLOntology::getOntologyID));
    }

    /**
     * Transform a stream of ontology ids into a stream of IRIs of those ontology ids. Anonymous
     * ontology ids are skipped.
     *
     * @param ids ontology ids to transform
     * @return collection of IRIs for the ontology ids.
     */
    public static Stream<IRI> ontologyIRIs(Stream<OWLOntologyID> ids) {
        return ids.filter(i -> i.getOntologyIRI().isPresent()).map(i -> i.getOntologyIRI().get());
    }

    /**
     * Gets the annotations for this entity. These are deemed to be annotations in annotation
     * assertion axioms that have a subject that is an IRI that is equal to the IRI of this entity,
     * and it also includes annotations on the annotation assertion axioms whose annotation property
     * matches
     *
     * @param e entity
     * @param o The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation assertion whose subject is
     *         an IRI corresponding to the IRI of this entity.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, OWLOntology o) {
        return getAnnotations(e.getIRI(), o);
    }

    /**
     * Gets the annotations for this entity. These are deemed to be annotations in annotation
     * assertion axioms that have a subject that is an IRI that is equal to the IRI of this entity,
     * and it also includes annotations on the annotation assertion axioms whose annotation property
     * matches.
     *
     * @param e entity
     * @param o The ontology to be examined for annotation assertion axioms
     * @return The annotations that participate directly in an annotation assertion whose subject is
     *         an IRI corresponding to the IRI of this entity.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e, OWLOntology o) {
        return annotations(o.annotationAssertionAxioms(e));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property. This includes the annotations on annotation assertion axioms with matching
     * annotation property.
     *
     * @param e entity
     * @param o The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, OWLOntology o,
        OWLAnnotationProperty annotationProperty) {
        return getAnnotations(e.getIRI(), o, annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property; this is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param o The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, OWLOntology o,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return getAnnotationObjects(e.getIRI(), o, annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property; this is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param o The ontology to examine for annotation axioms
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, OWLOntology o) {
        return getAnnotationObjects(e.getIRI(), o, null);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property; this is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param o The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLAnnotationSubject e, OWLOntology o,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return annotationObjects(o.annotationAssertionAxioms(e), annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property; this is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param onts The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLAnnotationSubject e,
        Stream<OWLOntology> onts, @Nullable OWLAnnotationProperty annotationProperty) {
        return onts.flatMap(o -> getAnnotationObjects(e, o, annotationProperty));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property; this is restricted to the object of annotation assertion axioms.
     *
     * @param e entity
     * @param onts The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotationObjects(OWLEntity e, Stream<OWLOntology> onts,
        @Nullable OWLAnnotationProperty annotationProperty) {
        return onts.flatMap(o -> getAnnotationObjects(e, o, annotationProperty));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property. This includes the annotations on annotation assertion axioms with matching
     * annotation property.
     *
     * @param e entity
     * @param o The ontology to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e, OWLOntology o,
        OWLAnnotationProperty annotationProperty) {
        return annotations(o.annotationAssertionAxioms(e), annotationProperty);
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property. This includes the annotations on annotation assertion axioms with matching
     * annotation property.
     *
     * @param e entity
     * @param onts The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLAnnotationSubject e,
        Stream<OWLOntology> onts, OWLAnnotationProperty annotationProperty) {
        return onts.flatMap(o -> getAnnotations(e, o, annotationProperty));
    }

    /**
     * Obtains the annotations on this entity where the annotation has the specified annotation
     * property. This includes the annotations on annotation assertion axioms with matching
     * annotation property.
     *
     * @param e entity
     * @param onts The ontologies to examine for annotation axioms
     * @param annotationProperty The annotation property
     * @return A set of {@code OWLAnnotation} objects that have the specified URI.
     */
    public static Stream<OWLAnnotation> getAnnotations(OWLEntity e, Stream<OWLOntology> onts,
        OWLAnnotationProperty annotationProperty) {
        return onts.flatMap(o -> getAnnotations(e, o, annotationProperty));
    }

    /**
     * @param e entity
     * @param o the ontology to use
     * @return the annotation assertion axioms about this entity in the provided ontology
     */
    public static Stream<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLEntity e,
        OWLOntology o) {
        return getAnnotationAssertionAxioms(e.getIRI(), o);
    }

    /**
     * @param e entity
     * @param o the ontology to use
     * @return the annotation assertion axioms about this entity in the provided ontology
     */
    public static Stream<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
        OWLAnnotationSubject e, OWLOntology o) {
        return o.annotationAssertionAxioms(e);
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this property in the specified
     * ontology.
     *
     * @param <P> property type
     * @param e entity
     * @param o The ontology to be examined for SubProperty axioms.
     * @return A set of properties such that for each property {@code p} in the set, it is the case
     *         that {@code ontology} contains an {@code SubPropertyOf(p, this)} axiom where
     *         {@code this} refers to this property.
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSubProperties(P e, OWLOntology o) {
        if (e.isObjectPropertyExpression()) {
            return sub(o.axioms(subObjectPropertyWithSuper, e, EXCLUDED));
        }
        if (e.isDataPropertyExpression()) {
            return sub(o.axioms(subDataPropertyWithSuper, e, EXCLUDED));
        }
        return sub(o.axioms(subAnnotationWithSuper, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this property in the specified
     * ontology.
     *
     * @param <P> property type
     * @param e entity
     * @param onts The ontologies to be examined for SubProperty axioms.
     * @return A set of properties such that for each property {@code p} in the set, it is the case
     *         that {@code ontology} contains an {@code SubPropertyOf(p, this)} axiom where
     *         {@code this} refers to this property.
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSubProperties(P e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getSubProperties(e, o));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this property in the specified
     * ontology.
     *
     * @param <P> property type
     * @param e entity
     * @param o The ontology to be examined for SubProperty axioms.
     * @return A set of properties such that for each property {@code p} in the set, it is the case
     *         that {@code ontology} contains an {@code SubPropertyOf(p, this)} axiom where
     *         {@code this} refers to this property.
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSuperProperties(P e,
        OWLOntology o) {
        if (e.isObjectPropertyExpression()) {
            return sup(o.axioms(subObjectPropertyWithSub, e, EXCLUDED));
        }
        if (e.isDataPropertyExpression()) {
            return sup(o.axioms(subDataPropertyWithSub, e, EXCLUDED));
        }
        return sup(o.axioms(subAnnotationWithSub, e, EXCLUDED));
    }

    /**
     * Gets the properties which are asserted to be sub-properties of this property in the specified
     * ontology.
     *
     * @param <P> property type
     * @param e entity
     * @param onts The ontologies to be examined for SubPropertyOf axioms.
     * @return A set of properties such that for each property {@code p} in the set, it is the case
     *         that {@code ontology} contains an {@code SubPropertyOf(p, this)} axiom where
     *         {@code this} refers to this property.
     */
    public static <P extends OWLPropertyExpression> Stream<P> getSuperProperties(P e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getSuperProperties(e, o));
    }

    /**
     * A convenience method that examines the axioms in the specified ontology and return the class
     * expressions corresponding to super classes of this class.
     *
     * @param e entity
     * @param o The ontology to be examined
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the superclasses of
     *         this class, which have been asserted in the specified ontology.
     */
    public static Stream<OWLClassExpression> getSuperClasses(OWLClass e, OWLOntology o) {
        return sup(o.subClassAxiomsForSubClass(e));
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies and returns the
     * class expression corresponding to the asserted super classes of this class.
     *
     * @param e entity
     * @param onts The set of ontologies to be examined.
     * @return A set of {@code OWLClassExpressions}s that represent the super classes of this class
     */
    public static Stream<OWLClassExpression> getSuperClasses(OWLClass e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getSuperClasses(e, o));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this class in the
     * specified ontology.
     *
     * @param e entity
     * @param o The ontology which should be examined for subclass axioms.
     * @return A {@code Stream} of {@code OWLClassExpression}s that represet the asserted subclasses
     *         of this class.
     */
    public static Stream<OWLClassExpression> getSubClasses(OWLClass e, OWLOntology o) {
        return sub(o.subClassAxiomsForSuperClass(e));
    }

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this class in the
     * specified ontologies.
     *
     * @param e entity
     * @param onts The ontologies which should be examined for subclass axioms.
     * @return A {@code Stream} of {@code OWLClassExpression}s that represet the asserted subclasses
     *         of this class.
     */
    public static Stream<OWLClassExpression> getSubClasses(OWLClass e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getSubClasses(e, o));
    }

    /**
     * A convenience method that examines the axioms in the specified ontology and returns the class
     * expressions corresponding to equivalent classes of this class.
     *
     * @param e entity
     * @param o The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of this class, that have been asserted in the specified ontology.
     */
    public static Stream<OWLClassExpression> getEquivalentClasses(OWLClass e, OWLOntology o) {
        return equivalent(o.equivalentClassesAxioms(e)).filter(c -> !c.equals(e))
            .map(c -> (OWLClassExpression) c);
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies and returns the
     * class expressions corresponding to equivalent classes of this class.
     *
     * @param e entity
     * @param onts The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of this class, that have been asserted in the specified ontologies.
     */
    public static Stream<OWLClassExpression> getEquivalentClasses(OWLClass e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getEquivalentClasses(e, o));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class by axioms in the
     * specified ontology.
     *
     * @param e entity
     * @param o The ontology to search for disjoint class axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the disjoint classes
     *         of this class.
     */
    public static Stream<OWLClassExpression> getDisjointClasses(OWLClass e, OWLOntology o) {
        return different(o.disjointClassesAxioms(e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class by axioms in the
     * specified ontologies.
     *
     * @param e entity
     * @param onts The ontologies to search for disjoint class axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the disjoint classes
     *         of this class.
     */
    public static Stream<OWLClassExpression> getDisjointClasses(OWLClass e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDisjointClasses(e, o));
    }

    /**
     * Gets the different individuals in the specified ontology.
     *
     * @param e individual
     * @param o The ontology to search for different individuals
     * @return A {@code Stream} of different {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getDifferentIndividuals(OWLIndividual e, OWLOntology o) {
        return different(o.differentIndividualAxioms(e));
    }

    /**
     * Gets the different individuals in the specified ontologies.
     *
     * @param e individual
     * @param onts The ontologies to search for different individuals
     * @return A {@code Stream} of different {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getDifferentIndividuals(OWLIndividual e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDifferentIndividuals(e, o));
    }

    /**
     * Gets the same individuals in the specified ontology.
     *
     * @param e individual
     * @param o The ontology to search for same individuals
     * @return A {@code Stream} of same {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getSameIndividuals(OWLIndividual e, OWLOntology o) {
        return equivalent(o.sameIndividualAxioms(e)).filter(c -> !c.equals(e))
            .map(c -> (OWLIndividual) c);
    }

    /**
     * Gets the same individuals in the specified ontologies.
     *
     * @param e individual
     * @param onts The ontologies to search for same individuals
     * @return A {@code Stream} of same {@code OWLIndividual}s.
     */
    public static Stream<OWLIndividual> getSameIndividuals(OWLIndividual e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getSameIndividuals(e, o));
    }

    /**
     * A convenience method that examines the axioms in the specified ontology and returns the class
     * expressions corresponding to equivalent classes of this class.
     *
     * @param e entity
     * @param o The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of this class, that have been asserted in the specified ontology.
     */
    public static Stream<OWLDataPropertyExpression> getEquivalentProperties(OWLDataProperty e,
        OWLOntology o) {
        return equivalent(o.equivalentDataPropertiesAxioms(e)).filter(c -> !c.equals(e))
            .map(c -> (OWLDataPropertyExpression) c);
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies and returns the
     * class expressions corresponding to equivalent classes of this class.
     *
     * @param e entity
     * @param onts The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of this class, that have been asserted in the specified ontologies.
     */
    public static Stream<OWLDataPropertyExpression> getEquivalentProperties(OWLDataProperty e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getEquivalentProperties(e, o)).filter(c -> !c.equals(e));
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class by axioms in the
     * specified ontology.
     *
     * @param <P> property type
     * @param e entity
     * @param o The ontology to search for disjoint class axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the disjoint classes
     *         of this class.
     */
    public static <P extends OWLPropertyExpression> Stream<P> getDisjointProperties(P e,
        OWLOntology o) {
        if (e.isObjectPropertyExpression()) {
            return different(o.disjointObjectPropertiesAxioms(e.asObjectPropertyExpression()));
        }
        if (e.isDataPropertyExpression()) {
            return different(o.disjointDataPropertiesAxioms((OWLDataProperty) e));
        }
        // else e must have been an annotation property. No disjoints on those
        return empty();
    }

    /**
     * Gets the classes which have been asserted to be disjoint with this class by axioms in the
     * specified ontologies.
     *
     * @param <P> property type
     * @param e entity
     * @param onts The ontologies to search for disjoint class axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the disjoint classes
     *         of this class.
     */
    public static <P extends OWLPropertyExpression> Stream<P> getDisjointProperties(P e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDisjointProperties(e, o));
    }

    /**
     * A convenience method that examines the axioms in the specified ontology and returns the class
     * expressions corresponding to equivalent classes of this class.
     *
     * @param e entity
     * @param o The ontology to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of this class, that have been asserted in the specified ontology.
     */
    public static Stream<OWLObjectPropertyExpression> getEquivalentProperties(
        OWLObjectPropertyExpression e, OWLOntology o) {
        return equivalent(o.equivalentObjectPropertiesAxioms(e)).filter(c -> !c.equals(e))
            .map(c -> (OWLObjectPropertyExpression) c);
    }

    /**
     * A convenience method that examines the axioms in the specified ontologies and returns the
     * class expressions corresponding to equivalent classes of this class.
     *
     * @param e entity
     * @param onts The ontologies to be examined for axioms
     * @return A {@code Stream} of {@code OWLClassExpression}s that represent the equivalent classes
     *         of this class, that have been asserted in the specified ontologies.
     */
    public static Stream<OWLObjectPropertyExpression> getEquivalentProperties(
        OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getEquivalentProperties(e, o));
    }

    /**
     * Gets the individuals that have been asserted to be an instance of this class by axioms in the
     * specified ontology.
     *
     * @param e entity
     * @param o The ontology to be examined for class assertion axioms that assert an individual to
     *        be an instance of this class.
     * @return A {@code Stream} of {@code OWLIndividual}s that represent the individual that have
     *         been asserted to be an instance of this class.
     */
    public static Stream<OWLIndividual> getIndividuals(OWLClass e, OWLOntology o) {
        return instances(o.classAssertionAxioms(e));
    }

    /**
     * Gets the individuals that have been asserted to be an instance of this class by axioms in the
     * speficied ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined for class assertion axioms that assert an
     *        individual to be an instance of this class.
     * @return A {@code Stream} of {@code OWLIndividual}s that represent the individual that have
     *         been asserted to be an instance of this class.
     */
    public static Stream<OWLIndividual> getIndividuals(OWLClass e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getIndividuals(e, o));
    }

    /**
     * Gets the axioms in the specified ontology that contain this entity in their signature.
     *
     * @param e entity
     * @param o The ontology that will be searched for axioms
     * @return The axioms in the specified ontology whose signature contains this entity.
     */
    public static Stream<OWLAxiom> getReferencingAxioms(OWLEntity e, OWLOntology o) {
        return o.referencingAxioms(e, EXCLUDED);
    }

    /**
     * Gets the axioms in the specified ontology and possibly its imports closure that contain this
     * entity in their signature.
     *
     * @param e entity
     * @param o The ontology that will be searched for axioms
     * @param includeImports If {@code true} then axioms in the imports closure will also be
     *        returned, if {@code false} then only the axioms in the specified ontology will be
     *        returned.
     * @return The axioms in the specified ontology whose signature contains this entity.
     */
    public static Stream<OWLAxiom> getReferencingAxioms(OWLEntity e, OWLOntology o,
        Imports includeImports) {
        return o.referencingAxioms(e, includeImports);
    }

    /**
     * Gets the asserted domains of this property.
     *
     * @param e entity
     * @param o The ontology that should be examined for axioms which assert a domain of this
     *        property
     * @return A set of {@code OWLClassExpression}s corresponding to the domains of this property
     *         (the domain of the property is essentially the intersection of these class
     *         expressions).
     */
    public static Stream<OWLClassExpression> getDomains(OWLDataProperty e, OWLOntology o) {
        return domain(o.dataPropertyDomainAxioms(e));
    }

    /**
     * Gets the asserted domains of this property by examining the axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined.
     * @return A set of {@code OWLClassExpression}s that represent the asserted domains of this
     *         property.
     */
    public static Stream<OWLClassExpression> getDomains(OWLDataProperty e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDomains(e, o));
    }

    /**
     * Gets the asserted domains of this property.
     *
     * @param e entity
     * @param o The ontology that should be examined for axioms which assert a domain of this
     *        property
     * @return A set of {@code OWLClassExpression}s corresponding to the domains of this property
     *         (the domain of the property is essentially the intersection of these class
     *         expressions).
     */
    public static Stream<OWLClassExpression> getDomains(OWLObjectPropertyExpression e,
        OWLOntology o) {
        return domain(o.objectPropertyDomainAxioms(e));
    }

    /**
     * Gets the ranges of this property that have been asserted in the specified ontology.
     *
     * @param e entity
     * @param o The ontology to be searched for axioms which assert a range for this property.
     * @return A set of ranges for this property.
     */
    public static Stream<OWLDataRange> getRanges(OWLDataProperty e, OWLOntology o) {
        return range(o.dataPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of this property by examining the axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted by axioms in the
     *         specified ontologies.
     */
    public static Stream<OWLDataRange> getRanges(OWLDataProperty e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getRanges(e, o));
    }

    /**
     * Gets the asserted domains of this property by examining the axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined.
     * @return A set of {@code OWLClassExpression}s that represent the asserted domains of this
     *         property.
     */
    public static Stream<OWLClassExpression> getDomains(OWLObjectPropertyExpression e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDomains(e, o));
    }

    /**
     * Gets the ranges of this property that have been asserted in the specified ontology.
     *
     * @param e entity
     * @param o The ontology to be searched for axioms which assert a range for this property.
     * @return A set of ranges for this property.
     */
    public static Stream<OWLClassExpression> getRanges(OWLObjectPropertyExpression e,
        OWLOntology o) {
        return range(o.objectPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of this property by examining the axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted by axioms in the
     *         specified ontologies.
     */
    public static Stream<OWLClassExpression> getRanges(OWLObjectPropertyExpression e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getRanges(e, o));
    }

    /**
     * Gets the asserted domains of this property.
     *
     * @param e entity
     * @param o The ontology that should be examined for axioms which assert a domain of this
     *        property
     * @return A set of {@code OWLClassExpression}s corresponding to the domains of this property
     *         (the domain of the property is essentially the intersection of these class
     *         expressions).
     */
    public static Stream<IRI> getDomains(OWLAnnotationProperty e, OWLOntology o) {
        return domain(o.annotationPropertyDomainAxioms(e));
    }

    /**
     * Gets the asserted domains of this property by examining the axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined.
     * @return A set of {@code OWLClassExpression}s that represent the asserted domains of this
     *         property.
     */
    public static Stream<IRI> getDomains(OWLAnnotationProperty e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDomains(e, o));
    }

    /**
     * Gets the ranges of this property that have been asserted in the specified ontology.
     *
     * @param e entity
     * @param o The ontology to be searched for axioms which assert a range for this property.
     * @return A set of ranges for this property.
     */
    public static Stream<IRI> getRanges(OWLAnnotationProperty e, OWLOntology o) {
        return range(o.annotationPropertyRangeAxioms(e));
    }

    /**
     * Gets the asserted ranges of this property by examining the axioms in the specified
     * ontologies.
     *
     * @param e entity
     * @param onts The ontologies to be examined for range axioms.
     * @return A set of ranges for this property, which have been asserted by axioms in the
     *         specified ontologies.
     */
    public static Stream<IRI> getRanges(OWLAnnotationProperty e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getRanges(e, o));
    }

    /**
     * Checks if e is declared transitive in o.
     *
     * @param o ontology
     * @param e property
     * @return true for transitive properties
     */
    public static boolean isTransitive(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.transitiveObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared transitive in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for transitive properties
     */
    public static boolean isTransitive(OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isTransitive(e, o));
    }

    /**
     * Checks if e is declared symmetric in o.
     *
     * @param o ontology
     * @param e property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.symmetricObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared symmetric in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isSymmetric(e, o));
    }

    /**
     * Checks if e is declared asymmetric in o.
     *
     * @param o ontology
     * @param e property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.asymmetricObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared asymmetric in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isAsymmetric(e, o));
    }

    /**
     * Checks if e is declared reflexive in o.
     *
     * @param o ontology
     * @param e property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.reflexiveObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared reflexive in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isReflexive(e, o));
    }

    /**
     * Checks if e is declared irreflexive in o.
     *
     * @param o ontology
     * @param e property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.irreflexiveObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared irreflexive in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isIrreflexive(e, o));
    }

    /**
     * Checks if e is declared inverse functional in o.
     *
     * @param o ontology
     * @param e property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.inverseFunctionalObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared inverse functional in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(OWLObjectPropertyExpression e,
        Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isInverseFunctional(e, o));
    }

    /**
     * Checks if e is declared functional in o.
     *
     * @param o ontology
     * @param e property
     * @return true for functional object properties
     */
    public static boolean isFunctional(OWLObjectPropertyExpression e, OWLOntology o) {
        return o.functionalObjectPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared functional in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for functional object properties
     */
    public static boolean isFunctional(OWLObjectPropertyExpression e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isFunctional(e, o));
    }

    /**
     * Checks if e is declared functional in o.
     *
     * @param o ontology
     * @param e property
     * @return true for functional data properties
     */
    public static boolean isFunctional(OWLDataProperty e, OWLOntology o) {
        return o.functionalDataPropertyAxioms(e).findAny().isPresent();
    }

    /**
     * Checks if e is declared functional in a collection of ontologies.
     *
     * @param onts ontologies
     * @param e property
     * @return true for functional data properties
     */
    public static boolean isFunctional(OWLDataProperty e, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isFunctional(e, o));
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in o.
     *
     * @param o ontology
     * @param c class
     * @return true for defined classes
     */
    public static boolean isDefined(OWLClass c, OWLOntology o) {
        return o.equivalentClassesAxioms(c).findAny().isPresent();
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in a collection of ontologies.
     *
     * @param onts ontologies
     * @param c class
     * @return true for defined classes
     */
    public static boolean isDefined(OWLClass c, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> isDefined(c, o));
    }

    /**
     * Checks if o contains axiom a, with or without imports closure.
     *
     * @param o ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiom(OWLAxiom a, OWLOntology o, Imports imports) {
        return o.containsAxiom(a, imports, CONSIDER_AXIOM_ANNOTATIONS);
    }

    /**
     * Checks if any of the ontologies contains axiom a, with or without imports closure.
     *
     * @param onts ontologies
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiom(OWLAxiom a, Stream<OWLOntology> onts, Imports imports) {
        return onts.anyMatch(o -> containsAxiom(a, o, imports));
    }

    /**
     * Checks if o contains axiom a, with or without imports closure, ignoring annotations.
     *
     * @param o ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(OWLAxiom a, OWLOntology o,
        boolean imports) {
        return o.containsAxiom(a, fromBoolean(imports), IGNORE_AXIOM_ANNOTATIONS);
    }

    /**
     * Checks if any of the ontologies contains axiom a, with or without imports closure.
     *
     * @param onts ontologies
     * @param a axiom
     * @param imports true if imports closure is included
     * @return true if a is contained
     */
    public static boolean containsAxiomIgnoreAnnotations(OWLAxiom a, Stream<OWLOntology> onts,
        boolean imports) {
        return onts.anyMatch(o -> containsAxiomIgnoreAnnotations(a, o, imports));
    }

    /**
     * Get matching axioms for a, ignoring annotations.
     *
     * @param o ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return matching axioms
     */
    public static Collection<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom a, OWLOntology o,
        Imports imports) {
        return asList(o.axiomsIgnoreAnnotations(a, imports));
    }

    /**
     * Get matching axioms for a, ignoring annotations.
     *
     * @param o ontology
     * @param a axiom
     * @param imports true if imports closure is included
     * @return matching axioms
     */
    public static Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom a, OWLOntology o,
        Imports imports) {
        return o.axiomsIgnoreAnnotations(a, imports);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return literal values
     */
    public static Stream<OWLLiteral> getDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, OWLOntology o) {
        return values(o.dataPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return literal values
     */
    public static Stream<OWLLiteral> getDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return property values
     */
    public static Stream<OWLIndividual> getObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLOntology o) {
        return values(o.objectPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return property values
     */
    public static Stream<OWLIndividual> getObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return property values
     */
    public static Stream<OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, OWLOntology o) {
        return negValues(o.negativeDataPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return property values
     */
    public static Stream<OWLLiteral> getNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getNegativeDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return property values
     */
    public static Stream<OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLOntology o) {
        return negValues(o.negativeObjectPropertyAssertionAxioms(i), p);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return property values
     */
    public static Stream<OWLIndividual> getNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getNegativeObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return true if values are present
     */
    public static boolean hasDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        OWLOntology o) {
        return values(o.dataPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValues(OWLIndividual i, OWLDataPropertyExpression p,
        Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLOntology o) {
        return values(o.objectPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValues(OWLIndividual i, OWLObjectPropertyExpression p,
        Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, OWLOntology o) {
        return negValues(o.negativeDataPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValues(OWLIndividual i,
        OWLDataPropertyExpression p, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasNegativeDataPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLOntology o) {
        return negValues(o.negativeObjectPropertyAssertionAxioms(i), p).findAny().isPresent();
    }

    /**
     * @param i individual
     * @param p property to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValues(OWLIndividual i,
        OWLObjectPropertyExpression p, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasNegativeObjectPropertyValues(i, p, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, OWLOntology o) {
        return contains(values(o.dataPropertyAssertionAxioms(i), p), lit);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasDataPropertyValue(i, p, lit, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLIndividual j, OWLOntology o) {
        return contains(values(o.objectPropertyAssertionAxioms(i), p), j);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasObjectPropertyValue(OWLIndividual i, OWLObjectPropertyExpression p,
        OWLIndividual j, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasObjectPropertyValue(i, p, j, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, OWLOntology o) {
        return contains(negValues(o.negativeDataPropertyAssertionAxioms(i), p), lit);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param lit literal to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeDataPropertyValue(OWLIndividual i, OWLDataPropertyExpression p,
        OWLLiteral lit, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasNegativeDataPropertyValue(i, p, lit, o));
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param o ontology to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValue(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLIndividual j, OWLOntology o) {
        return contains(negValues(o.negativeObjectPropertyAssertionAxioms(i), p), j);
    }

    /**
     * @param i individual
     * @param p property to search
     * @param j individual to search
     * @param onts ontologies to search
     * @return true if value present
     */
    public static boolean hasNegativeObjectPropertyValue(OWLIndividual i,
        OWLObjectPropertyExpression p, OWLIndividual j, Stream<OWLOntology> onts) {
        return onts.anyMatch(o -> hasNegativeObjectPropertyValue(i, p, j, o));
    }

    /**
     * @param i individual
     * @param o ontology to search
     * @return property values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getDataPropertyValues(
        OWLIndividual i, OWLOntology o) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        o.dataPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param onts ontologies to search
     * @return literal values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getDataPropertyValues(
        OWLIndividual i, Stream<OWLOntology> onts) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        onts.map(o -> getDataPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    /**
     * @param i individual
     * @param o ontology to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getObjectPropertyValues(
        OWLIndividual i, OWLOntology o) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        o.objectPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param onts ontologies to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getObjectPropertyValues(
        OWLIndividual i, Stream<OWLOntology> onts) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        onts.map(o -> getObjectPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    /**
     * @param i individual
     * @param o ontology to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getNegativeObjectPropertyValues(
        OWLIndividual i, OWLOntology o) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        o.negativeObjectPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param o ontology to search
     * @return property values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getNegativeDataPropertyValues(
        OWLIndividual i, OWLOntology o) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        o.negativeDataPropertyAssertionAxioms(i).forEach(ax -> map
            .computeIfAbsent(ax.getProperty(), x -> new ArrayList<>()).add(ax.getObject()));
        return map;
    }

    /**
     * @param i individual
     * @param onts ontologies to search
     * @return property values
     */
    public static Map<OWLObjectPropertyExpression, List<OWLIndividual>> getNegativeObjectPropertyValues(
        OWLIndividual i, Stream<OWLOntology> onts) {
        Map<OWLObjectPropertyExpression, List<OWLIndividual>> map = new HashMap<>();
        onts.map(o -> getNegativeObjectPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    /**
     * @param i individual
     * @param onts ontologies to search
     * @return property values
     */
    public static Map<OWLDataPropertyExpression, List<OWLLiteral>> getNegativeDataPropertyValues(
        OWLIndividual i, Stream<OWLOntology> onts) {
        Map<OWLDataPropertyExpression, List<OWLLiteral>> map = new HashMap<>();
        onts.map(o -> getNegativeDataPropertyValues(i, o)).forEach(m -> merge(map, m));
        return map;
    }

    private static <T, K> void merge(Map<T, List<K>> merge, Map<T, List<K>> m) {
        m.forEach((a, b) -> merge.computeIfAbsent(a, x -> new ArrayList<>()).addAll(b));
    }

    /**
     * @param e object property
     * @param o ontology to search
     * @return property inverses
     */
    public static Stream<OWLObjectPropertyExpression> getInverses(OWLObjectPropertyExpression e,
        OWLOntology o) {
        return inverse(o.inverseObjectPropertyAxioms(e), e);
    }

    /**
     * @param e object property
     * @param onts ontologies to search
     * @return property inverses
     */
    public static Stream<OWLObjectPropertyExpression> getInverses(OWLObjectPropertyExpression e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getInverses(e, o));
    }

    /**
     * @param e class
     * @param o ontology to search
     * @return instances of class
     */
    public static Stream<OWLIndividual> getInstances(OWLClassExpression e, OWLOntology o) {
        return instances(o.classAssertionAxioms(e));
    }

    /**
     * @param e class
     * @param onts ontologies to search
     * @return instances of class
     */
    public static Stream<OWLIndividual> getInstances(OWLClassExpression e,
        Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getInstances(e, o));
    }

    /**
     * @param e individual
     * @param o ontology to search
     * @return types for individual
     */
    public static Stream<OWLClassExpression> getTypes(OWLIndividual e, OWLOntology o) {
        return types(o.classAssertionAxioms(e));
    }

    /**
     * @param e individual
     * @param onts ontologies to search
     * @return types for individual
     */
    public static Stream<OWLClassExpression> getTypes(OWLIndividual e, Stream<OWLOntology> onts) {
        return onts.flatMap(o -> getTypes(e, o));
    }

}
