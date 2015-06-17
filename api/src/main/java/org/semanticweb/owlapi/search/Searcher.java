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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

/**
 * A collection of static search utilities.
 * 
 * @author ignazio
 */
public final class Searcher {

    private Searcher() {}

    /**
     * Retrieve literals from a collection of assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return literals
     */
    public static Stream<OWLLiteral> values(Stream<OWLDataPropertyAssertionAxiom> axioms,
        @Nullable OWLDataPropertyExpression p) {
        return axioms.filter(ax -> p == null || ax.getProperty().equals(p)).map(ax -> ax.getObject()).distinct();
    }

    /**
     * Retrieve objects from a collection of assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return objects
     */
    public static Stream<OWLIndividual> values(Stream<OWLObjectPropertyAssertionAxiom> axioms,
        @Nullable OWLObjectPropertyExpression p) {
        return axioms.filter(ax -> p == null || ax.getProperty().equals(p)).map(ax -> ax.getObject());
    }

    /**
     * Retrieve literals from a collection of negative assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return literals
     */
    public static Stream<OWLLiteral> negValues(Stream<OWLNegativeDataPropertyAssertionAxiom> axioms,
        @Nullable OWLDataPropertyExpression p) {
        return axioms.filter(ax -> p == null || ax.getProperty().equals(p)).map(ax -> ax.getObject());
    }

    /**
     * Retrieve objects from a collection of negative assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return objects
     */
    public static Stream<OWLIndividual> negValues(Stream<OWLNegativeObjectPropertyAssertionAxiom> axioms,
        @Nullable OWLObjectPropertyExpression p) {
        return axioms.filter(ax -> p == null || ax.getProperty().equals(p)).map(ax -> ax.getObject());
    }

    /**
     * Retrieve classes from class assertions.
     * 
     * @param axioms
     *        axioms
     * @return classes
     */
    public static Stream<OWLClassExpression> types(Stream<OWLClassAssertionAxiom> axioms) {
        return axioms.map(ax -> ax.getClassExpression());
    }

    /**
     * Retrieve individuals from class assertions.
     * 
     * @param axioms
     *        axioms
     * @return individuals
     */
    public static Stream<OWLIndividual> instances(Stream<OWLClassAssertionAxiom> axioms) {
        return axioms.map(ax -> ax.getIndividual());
    }

    /**
     * Retrieve inverses from a collection of inverse axioms.
     * 
     * @param axioms
     *        axioms to check
     * @param p
     *        property to match; not returned in the set
     * @return inverses of p
     */
    public static Stream<OWLObjectPropertyExpression> inverse(Stream<OWLInverseObjectPropertiesAxiom> axioms,
        OWLObjectPropertyExpression p) {
        return axioms.map(ax -> getInverse(p, ax));
    }

    protected static OWLObjectPropertyExpression getInverse(OWLObjectPropertyExpression p,
        OWLInverseObjectPropertiesAxiom ax) {
        if (ax.getFirstProperty().equals(p)) {
            return ax.getSecondProperty();
        } else {
            return ax.getFirstProperty();
        }
    }

    /**
     * Retrieve annotation values from annotations.
     * 
     * @param annotations
     *        annotations
     * @return annotation values
     */
    public static Stream<OWLAnnotationValue> values(Stream<OWLAnnotation> annotations) {
        return values(annotations, null);
    }

    /**
     * Retrieve annotation values from annotations.
     * 
     * @param annotations
     *        annotations
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotation values
     */
    public static Stream<OWLAnnotationValue> values(Stream<OWLAnnotation> annotations,
        @Nullable OWLAnnotationProperty p) {
        return annotations.filter(ax -> p == null || ax.getProperty().equals(p)).map(ax -> ax.getValue());
    }

    /**
     * Retrieve annotations from a collection of axioms. For regular axioms,
     * their annotations are retrieved; for annotation assertion axioms, their
     * asserted annotation is retrieved as well.
     * 
     * @param axioms
     *        axioms
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotations(Stream<? extends OWLAxiom> axioms) {
        return annotations(axioms, null);
    }

    /**
     * Retrieve annotations from a collection of annotation assertion axioms.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotationObjects(Stream<OWLAnnotationAssertionAxiom> axioms,
        @Nullable OWLAnnotationProperty p) {
        return axioms.flatMap(ax -> annotationObject(ax, p)).distinct();
    }

    /**
     * Retrieve the annotation from an annotation assertion axiom.
     * 
     * @param axiom
     *        axiom
     * @param p
     *        optional annotation property to filter. Null means all.
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
     * Retrieve annotations from a collection of annotation assertion axioms.
     * This is limited to the annotation object and excludes annotations on the
     * axiom itself.
     * 
     * @param axioms
     *        axioms
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotationObjects(Stream<OWLAnnotationAssertionAxiom> axioms) {
        return axioms.map(ax -> ax.getAnnotation()).distinct();
    }

    /**
     * Retrieve annotations from a collection of axioms. For regular axioms,
     * their annotations are retrieved; for annotation assertion axioms, their
     * asserted annotation is retrieved as well.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotations
     */
    public static Stream<OWLAnnotation> annotations(Stream<? extends OWLAxiom> axioms,
        @Nullable OWLAnnotationProperty p) {
        return axioms.flatMap(ax -> annotations(ax, p)).distinct();
    }

    /**
     * Retrieve annotations from an axiom. For regular axioms, their annotations
     * are retrieved; for annotation assertion axioms, their asserted annotation
     * is retrieved as well.
     * 
     * @param axiom
     *        axiom
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotations
     */
    @SuppressWarnings("resource")
    public static Stream<OWLAnnotation> annotations(OWLAxiom axiom, @Nullable OWLAnnotationProperty p) {
        Stream<OWLAnnotation> stream = empty();
        if (axiom instanceof OWLAnnotationAssertionAxiom) {
            stream = Stream.of(((OWLAnnotationAssertionAxiom) axiom).getAnnotation());
        }
        stream = Stream.concat(stream, axiom.annotations());
        if (p != null) {
            stream = stream.filter(a -> a.getProperty().equals(p));
        }
        return stream.distinct();
    }

    /**
     * Retrieve equivalent entities from axioms, including individuals from
     * sameAs axioms. A mixture of axiom types can be passed in, as long as the
     * entity type they contain is compatible with the return type for the
     * collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return equivalent entities
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> equivalent(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) equivalent(axioms, OWLObject.class);
    }

    /**
     * Retrieve equivalent entities from axioms, including individuals from
     * sameAs axioms. A mixture of axiom types can be passed in, as long as the
     * entity type they contain is compatible with the return type for the
     * collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return equivalent entities
     */
    public static <C extends OWLObject> Stream<C> equivalent(Stream<? extends OWLAxiom> axioms, Class<C> type) {
        return axioms.flatMap(ax -> equivalent(ax, type));
    }

    /**
     * Retrieve equivalent entities from an axiom, including individuals from
     * sameAs axioms.
     * 
     * @param axiom
     *        axiom
     * @param <C>
     *        type contained in the returned collection
     * @return equivalent entities
     */
    public static <C extends OWLObject> Stream<C> equivalent(OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(true));
    }

    /**
     * Retrieve equivalent entities from an axiom, including individuals from
     * sameAs axioms.
     * 
     * @param axiom
     *        axiom
     * @param type
     *        type returned
     * @param <C>
     *        type contained in the returned collection
     * @return equivalent entities
     */
    public static <C extends OWLObject> Stream<C> equivalent(OWLAxiom axiom,
        @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new EquivalentVisitor<C>(true));
    }

    /**
     * Retrieve disjoint entities from axioms, including individuals from
     * differentFrom axioms. A mixture of axiom types can be passed in, as long
     * as the entity type they contain is compatible with the return type for
     * the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return disjoint entities
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> different(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) different(axioms, OWLObject.class);
    }

    /**
     * Retrieve disjoint entities from axioms, including individuals from
     * differentFrom axioms. A mixture of axiom types can be passed in, as long
     * as the entity type they contain is compatible with the return type for
     * the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return disjoint entities
     */
    public static <C extends OWLObject> Stream<C> different(Stream<? extends OWLAxiom> axioms, Class<C> type) {
        return axioms.flatMap(ax -> different(ax, type));
    }

    /**
     * Retrieve disjoint entities from an axiom, including individuals from
     * differentFrom axioms.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return disjoint entities
     */
    public static <C extends OWLObject> Stream<C> different(OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(false));
    }

    /**
     * Retrieve disjoint entities from an axiom, including individuals from
     * differentFrom axioms.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @param type
     *        witness for returned type
     * @return disjoint entities
     */
    public static <C extends OWLObject> Stream<C> different(OWLAxiom axiom, @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new EquivalentVisitor<C>(false));
    }

    /**
     * Retrieve the sub part of axioms, i.e., subclass or subproperty. A mixture
     * of axiom types can be passed in, as long as the entity type they contain
     * is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> sub(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) sub(axioms, OWLObject.class);
    }

    /**
     * Retrieve the sub part of axioms, i.e., subclass or subproperty. A mixture
     * of axiom types can be passed in, as long as the entity type they contain
     * is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> sub(Stream<? extends OWLAxiom> axioms, Class<C> type) {
        return axioms.map(ax -> sub(ax, type));
    }

    /**
     * Retrieve the sub part of an axiom, i.e., subclass or subproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C sub(OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(false));
    }

    /**
     * Retrieve the sub part of an axiom, i.e., subclass or subproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @param type
     *        witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C sub(OWLAxiom axiom, @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new SupSubVisitor<C>(false));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> sup(Stream<? extends OWLAxiom> axioms, Class<C> type) {
        return axioms.map(ax -> sup(ax, type));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> sup(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) sup(axioms, OWLObject.class);
    }

    /**
     * Retrieve the super part of an axiom, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C sup(OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(true));
    }

    /**
     * Retrieve the super part of an axiom, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @param type
     *        witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C sup(OWLAxiom axiom, @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new SupSubVisitor<C>(true));
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> domain(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) domain(axioms, OWLObject.class);
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> domain(Stream<? extends OWLAxiom> axioms, Class<C> type) {
        return axioms.map(ax -> domain(ax, type));
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C domain(OWLAxiom axiom) {
        return axiom.accept(new DomainVisitor<C>());
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @param type
     *        witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C domain(OWLAxiom axiom, @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new DomainVisitor<C>());
    }

    /**
     * Retrieve the ranges from range axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Stream<C> range(Stream<? extends OWLAxiom> axioms) {
        return (Stream<C>) range(axioms, OWLObject.class);
    }

    /**
     * Retrieve the ranges from range axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    public static <C extends OWLObject> Stream<C> range(Stream<? extends OWLAxiom> axioms, Class<C> type) {
        return axioms.map(ax -> range(ax, type));
    }

    /**
     * Retrieve the ranges from a range axiom. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    public static <C extends OWLObject> C range(OWLAxiom axiom) {
        return axiom.accept(new RangeVisitor<C>());
    }

    /**
     * Retrieve the ranges from a range axiom. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @param type
     *        witness for returned type
     * @return sub expressions
     */
    public static <C extends OWLObject> C range(OWLAxiom axiom, @SuppressWarnings("unused") Class<C> type) {
        return axiom.accept(new RangeVisitor<C>());
    }

    /**
     * Transform a collection of ontologies to a collection of IRIs of those
     * ontologies. Anonymous ontologies are skipped.
     * 
     * @param ontologies
     *        ontologies to transform
     * @return collection of IRIs for the ontologies.
     */
    public static Stream<IRI> ontologiesIRIs(Stream<OWLOntology> ontologies) {
        return ontologyIRIs(ontologies.map(o -> o.getOntologyID()));
    }

    /**
     * Transform a collection of ontology ids to a collection of IRIs of those
     * ontology ids. Anonymous ontology ids are skipped.
     * 
     * @param ids
     *        ontology ids to transform
     * @return collection of IRIs for the ontology ids.
     */
    public static Stream<IRI> ontologyIRIs(Stream<OWLOntologyID> ids) {
        return ids.filter(i -> i.getOntologyIRI().isPresent()).map(i -> i.getOntologyIRI().get());
    }
}
