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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter;

/** A configurable search.
 * 
 * @param <T>
 *            the generic type
 * @author ignazio */
public class Searcher<T> implements Iterable<T> {
    /** The Enum Searches. */
    enum Searches {
        /** The classes. */
        CLASSES,
        /** The properties. */
        PROPERTIES,
        /** The annotationproperties. */
        ANNOTATIONPROPERTIES,
        /** The axioms. */
        AXIOMS,
        /** The entities. */
        ENTITIES,
        /** The domains. */
        DOMAINS,
        /** The ranges. */
        RANGES,
        /** The annotationaxioms. */
        ANNOTATIONAXIOMS,
        /** The annotations. */
        ANNOTATIONS,
        /** The values. */
        VALUES,
        /** The negativevalues. */
        NEGATIVEVALUES,
        /** The instances. */
        INSTANCES,
        /** The types. */
        TYPES
    }

    /** The Enum Tasks. */
    enum Tasks {
        /** The search. */
        SEARCH,
        /** The describe. */
        DESCRIBE
    }

    /** The Enum Direction. */
    enum Direction {
        /** The super. */
        SUPER,
        /** The sub. */
        SUB,
        /** The equivalent. */
        EQUIVALENT,
        /** The disjoint. */
        DISJOINT,
        /** The different. */
        DIFFERENT,
        /** The same. */
        SAME,
        /** The inverse. */
        INVERSE
    }

    /** The Enum Types. */
    enum Types {
        /** The object. */
        OBJECT,
        /** The data. */
        DATA,
        /** The annotation. */
        ANNOTATION
    }

    /** Find.
     * 
     * @param <T>
     *            the generic type
     * @return a new searcher */
    public static <T> Searcher<T> find() {
        return new Searcher<T>().task(Tasks.SEARCH);
    }

    /** Find.
     * 
     * @param <T>
     *            the generic type
     * @param c
     *            Class of the returned type
     * @return a Searcher whose final return type will be T */
    public static <T> Searcher<T> find(@SuppressWarnings("unused") Class<T> c) {
        return new Searcher<T>().task(Tasks.SEARCH);
    }

    /** Describe.
     * 
     * @param c
     *            the c
     * @return a searcher that retrieves the axioms describing (mentioning) c */
    public static Searcher<OWLAxiom> describe(OWLEntity c) {
        return find(OWLAxiom.class).task(Tasks.DESCRIBE).entity(c);
    }

    /** The o. */
    protected OWLOntology o;
    /** The search. */
    private Searches search;
    /** The task. */
    private Tasks task;
    /** The axiom type. */
    private AxiomType axiomType;
    /** The entity. */
    private OWLEntity entity;
    /** The direction. */
    private Direction direction = Direction.SUPER;
    /** The include imports. */
    private boolean includeImports = true;
    /** The ce. */
    private OWLClassExpression ce;
    /** The property. */
    private OWLEntity property;
    /** The individual. */
    private OWLIndividual individual;
    /** The object property. */
    private OWLObjectPropertyExpression objectProperty;
    /** The type. */
    private Types type;

    /** Task.
     * 
     * @param t
     *            the t
     * @return the searcher */
    Searcher<T> task(Tasks t) {
        task = t;
        return this;
    }

    /** toggle imports closure off.
     * 
     * @return modified searcher */
    public Searcher<T> excludeImports() {
        includeImports = false;
        return this;
    }

    /** Data.
     * 
     * @return modified searcher */
    public Searcher<T> data() {
        type = Types.DATA;
        return this;
    }

    /** Object.
     * 
     * @return modified searcher */
    public Searcher<T> object() {
        type = Types.OBJECT;
        return this;
    }

    /** Axioms of type.
     * 
     * @param type
     *            the type
     * @return modified searcher */
    public Searcher<T> axiomsOfType(AxiomType type) {
        axiomType = type;
        search = Searches.AXIOMS;
        return this;
    }

    /** Entity.
     * 
     * @param e
     *            the e
     * @return modified searcher */
    public Searcher<T> entity(OWLEntity e) {
        search = Searches.ENTITIES;
        entity = e;
        return this;
    }

    /** Classes.
     * 
     * @return modified searcher */
    public Searcher<T> classes() {
        search = Searches.CLASSES;
        return this;
    }

    /** Classes.
     * 
     * @param c
     *            the c
     * @return modified searcher */
    public Searcher<T> classes(OWLClass c) {
        search = Searches.ENTITIES;
        entity = c;
        return this;
    }

    /** Annotation axioms.
     * 
     * @param c
     *            annotated entity
     * @return modified searcher */
    public Searcher<T> annotationAxioms(OWLEntity c) {
        search = Searches.ANNOTATIONAXIOMS;
        entity = c;
        return this;
    }

    /** Annotations.
     * 
     * @param c
     *            annotated entity
     * @return modified searcher */
    public Searcher<T> annotations(OWLEntity c) {
        search = Searches.ANNOTATIONS;
        entity = c;
        return this;
    }

    /** For property.
     * 
     * @param p
     *            property to consider
     * @return modified searcher */
    public Searcher<T> forProperty(OWLEntity p) {
        property = p;
        return this;
    }

    /** In.
     * 
     * @param ontology
     *            the ontology
     * @return modified searcher */
    public Searcher<T> in(OWLOntology ontology) {
        this.o = ontology;
        return this;
    }

    /** Contains.
     * 
     * @param object
     *            the object
     * @return true if object is contained in the search results */
    public boolean contains(T object) {
        return asCollection().contains(object);
    }

    /** Checks if is empty.
     * 
     * @return true if there are no search results */
    public boolean isEmpty() {
        return asCollection().isEmpty();
    }

    /** Size.
     * 
     * @return number of results */
    public int size() {
        return asCollection().size();
    }

    /** As collection.
     * 
     * @return this searcher as a collection of results */
    public Collection<T> asCollection() {
        if (search == Searches.TYPES) {
            Collection<T> toReturn = new HashSet<T>();
            for (OWLClassAssertionAxiom c : o
                    .getClassAssertionAxioms(individual)) {
                toReturn.add((T) c.getClassExpression());
            }
            return toReturn;
        }
        if (search == Searches.INSTANCES) {
            Collection<T> toReturn = new HashSet<T>();
            for (OWLClassAssertionAxiom c : o.getClassAssertionAxioms(ce)) {
                toReturn.add((T) c.getIndividual());
            }
            return toReturn;
        }
        if (direction == Direction.INVERSE) {
            Collection<OWLObjectPropertyExpression> toReturn = new HashSet<OWLObjectPropertyExpression>();
            for (OWLInverseObjectPropertiesAxiom inverse : o
                    .getInverseObjectPropertyAxioms(objectProperty)) {
                if (inverse.getFirstProperty().equals(
                        inverse.getSecondProperty())) {
                    toReturn.add(inverse.getFirstProperty());
                } else {
                    if (inverse.getFirstProperty().equals(objectProperty)) {
                        toReturn.add(inverse.getSecondProperty());
                    } else {
                        toReturn.add(inverse.getFirstProperty());
                    }
                }
            }
            return (Collection<T>) toReturn;
        }
        if (search == Searches.VALUES) {
            if (entity == null) {
                if (type == Types.DATA) {
                    return (Collection<T>) o
                            .getDataPropertyAssertionAxioms(individual);
                }
                if (type == Types.OBJECT) {
                    return (Collection<T>) o
                            .getObjectPropertyAssertionAxioms(individual);
                }
                Set<T> hashSet = new HashSet<T>(
                        (Collection<T>) o
                                .getDataPropertyAssertionAxioms(individual));
                hashSet.addAll((Collection<T>) o
                        .getObjectPropertyAssertionAxioms(individual));
                return hashSet;
            }
            if (entity instanceof OWLDataProperty) {
                Set<T> set = new HashSet<T>();
                for (OWLDataPropertyAssertionAxiom ax : o
                        .getDataPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add((T) ax.getObject());
                    }
                }
                return set;
            }
            if (entity instanceof OWLObjectProperty) {
                Set<T> set = new HashSet<T>();
                for (OWLObjectPropertyAssertionAxiom ax : o
                        .getObjectPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add((T) ax.getObject());
                    }
                }
                return set;
            }
        }
        if (search == Searches.NEGATIVEVALUES) {
            if (entity == null) {
                if (type == Types.DATA) {
                    return (Collection<T>) o
                            .getNegativeDataPropertyAssertionAxioms(individual);
                }
                if (type == Types.OBJECT) {
                    return (Collection<T>) o
                            .getNegativeObjectPropertyAssertionAxioms(individual);
                }
                Set<T> hashSet = new HashSet<T>(
                        (Collection<T>) o
                                .getNegativeDataPropertyAssertionAxioms(individual));
                hashSet.addAll((Collection<T>) o
                        .getNegativeObjectPropertyAssertionAxioms(individual));
                return hashSet;
            }
            if (entity instanceof OWLDataProperty) {
                Set<OWLLiteral> set = new HashSet<OWLLiteral>();
                for (OWLNegativeDataPropertyAssertionAxiom ax : o
                        .getNegativeDataPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add(ax.getObject());
                    }
                }
                return (Collection<T>) set;
            }
            if (entity instanceof OWLObjectProperty) {
                Set<OWLIndividual> set = new HashSet<OWLIndividual>();
                for (OWLNegativeObjectPropertyAssertionAxiom ax : o
                        .getNegativeObjectPropertyAssertionAxioms(individual)) {
                    if (ax.getProperty().equals(entity)) {
                        set.add(ax.getObject());
                    }
                }
                return (Collection<T>) set;
            }
        }
        // TODO add imports closure to all searches
        if (individual != null) {
            if (direction == Direction.SAME) {
                Set<T> toReturn = new HashSet<T>();
                for (OWLSameIndividualAxiom i : o
                        .getSameIndividualAxioms(individual)) {
                    toReturn.addAll((Set<T>) i.getIndividuals());
                }
                toReturn.remove(individual);
                return toReturn;
            }
            if (direction == Direction.DIFFERENT) {
                Set<T> toReturn = new HashSet<T>();
                for (OWLDifferentIndividualsAxiom i : o
                        .getDifferentIndividualAxioms(individual)) {
                    toReturn.addAll((Set<T>) i.getIndividuals());
                }
                toReturn.remove(individual);
                return toReturn;
            }
        }
        if (task == Tasks.DESCRIBE && search == Searches.ENTITIES) {
            AxiomsRetriever retriever = new AxiomsRetriever();
            return entity.accept(retriever);
        }
        if (search == Searches.ANNOTATIONAXIOMS) {
            List<T> annotationAssertionAxioms = new ArrayList<T>();
            for (OWLAnnotationAssertionAxiom a : o
                    .getAnnotationAssertionAxioms(entity.getIRI())) {
                if (property == null || a.getProperty().equals(property)) {
                    annotationAssertionAxioms.add((T) a);
                }
            }
            return annotationAssertionAxioms;
        }
        if (search == Searches.ANNOTATIONS) {
            if (property != null) {
                return getAnnotations(entity, (OWLAnnotationProperty) property,
                        o.getImportsClosure());
            }
            return (Collection<T>) getAnnotations(entity, o.getImportsClosure());
        }
        if (search == Searches.ENTITIES) {
            if (entity != null) {
                if (direction == Direction.SUPER) {
                    return entity.accept(new SupRetriever());
                }
                if (direction == Direction.SUB) {
                    return entity.accept(new SubRetriever<T>());
                }
                if (direction == Direction.EQUIVALENT) {
                    return entity.accept(new EquivalentRetriever<T>());
                }
                if (direction == Direction.DISJOINT) {
                    return entity.accept(new DisjointRetriever<T>());
                }
            }
            if (entity == null) {
                return (Collection<T>) o.getSignature(includeImports);
            }
        }
        if (search == Searches.CLASSES) {
            if (entity == null) {
                return (Collection<T>) o.getClassesInSignature(includeImports);
            }
        }
        if (search == Searches.AXIOMS) {
            if (axiomType == null) {
                return (Collection<T>) o.getAxioms();
            }
            return o.getAxioms(axiomType, includeImports);
        }
        if (search == Searches.PROPERTIES) {
            if (direction == Direction.SUPER) {
                return entity.accept(new SupRetriever());
            }
            if (direction == Direction.SUB) {
                return entity.accept(new SubRetriever<T>());
            }
            if (direction == Direction.EQUIVALENT) {
                return entity.accept(new EquivalentRetriever<T>());
            }
            if (direction == Direction.DISJOINT) {
                return entity.accept(new DisjointRetriever<T>());
            }
        }
        if (search == Searches.DOMAINS) {
            Collection<T> classes = new ArrayList<T>();
            if (entity instanceof OWLObjectProperty) {
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLObjectPropertyDomainAxiom d : ont
                            .getObjectPropertyDomainAxioms((OWLObjectProperty) entity)) {
                        classes.add((T) d.getDomain());
                    }
                }
            }
            if (entity instanceof OWLDataProperty) {
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLDataPropertyDomainAxiom d : ont
                            .getDataPropertyDomainAxioms((OWLDataProperty) entity)) {
                        classes.add((T) d.getDomain());
                    }
                }
            }
            return classes;
        }
        if (search == Searches.RANGES) {
            if (entity instanceof OWLObjectProperty) {
                Collection<T> classes = new ArrayList<T>();
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLObjectPropertyRangeAxiom d : ont
                            .getObjectPropertyRangeAxioms((OWLObjectProperty) entity)) {
                        classes.add((T) d.getRange());
                    }
                }
                return classes;
            }
            if (entity instanceof OWLDataProperty) {
                Collection<T> classes = new ArrayList<T>();
                for (OWLOntology ont : o.getImportsClosure()) {
                    for (OWLDataPropertyRangeAxiom d : ont
                            .getDataPropertyRangeAxioms((OWLDataProperty) entity)) {
                        classes.add((T) d.getRange());
                    }
                }
                return classes;
            }
        }
        return Collections.emptyList();
    }

    /** Sub.
     * 
     * @return modified searcher */
    public Searcher<T> sub() {
        direction = Direction.SUB;
        return this;
    }

    /** Different.
     * 
     * @param i
     *            the i
     * @return modified searcher */
    public Searcher<T> different(OWLIndividual i) {
        direction = Direction.DIFFERENT;
        individual = i;
        return this;
    }

    /** Same.
     * 
     * @param i
     *            the i
     * @return modified searcher */
    public Searcher<T> same(OWLIndividual i) {
        direction = Direction.SAME;
        individual = i;
        return this;
    }

    /** Individual.
     * 
     * @param i
     *            the i
     * @return modified searcher */
    public Searcher<T> individual(OWLIndividual i) {
        individual = i;
        return this;
    }

    /** Sup.
     * 
     * @return modified searcher */
    public Searcher<T> sup() {
        direction = Direction.SUPER;
        return this;
    }

    /** Equivalent.
     * 
     * @return modified searcher */
    public Searcher<T> equivalent() {
        direction = Direction.EQUIVALENT;
        return this;
    }

    /** Disjoint.
     * 
     * @return modified searcher */
    public Searcher<T> disjoint() {
        direction = Direction.DISJOINT;
        return this;
    }

    /** Properties of.
     * 
     * @param d
     *            the d
     * @return modified searcher */
    public Searcher<T> propertiesOf(OWLEntity d) {
        search = Searches.PROPERTIES;
        entity = d;
        return this;
    }

    /** Domains.
     * 
     * @param property
     *            the property
     * @return modified searcher */
    public Searcher<T> domains(OWLEntity property) {
        search = Searches.DOMAINS;
        entity = property;
        return this;
    }

    /** Ranges.
     * 
     * @param property
     *            the property
     * @return modified searcher */
    public Searcher<T> ranges(OWLEntity property) {
        search = Searches.RANGES;
        entity = property;
        return this;
    }

    /** Values.
     * 
     * @param p
     *            the p
     * @return modified searcher */
    public Searcher<T> values(OWLEntity p) {
        entity = p;
        search = Searches.VALUES;
        return this;
    }

    /** Types.
     * 
     * @param p
     *            the p
     * @return modified searcher */
    public Searcher<T> types(OWLIndividual p) {
        individual = p;
        search = Searches.TYPES;
        return this;
    }

    /** Individuals.
     * 
     * @param p
     *            the p
     * @return modified searcher */
    public Searcher<T> individuals(OWLClassExpression p) {
        ce = p;
        search = Searches.INSTANCES;
        return this;
    }

    /** Inverse.
     * 
     * @param p
     *            the p
     * @return modified searcher */
    public Searcher<T> inverse(OWLObjectPropertyExpression p) {
        objectProperty = p;
        direction = Direction.INVERSE;
        return this;
    }

    /** Negative values.
     * 
     * @param p
     *            the p
     * @return modified searcher */
    public Searcher<T> negativeValues(OWLEntity p) {
        entity = p;
        search = Searches.NEGATIVEVALUES;
        return this;
    }

    /** Checks if is transitive.
     * 
     * @param e
     *            the e
     * @return true for transitive properties */
    public boolean isTransitive(OWLObjectProperty e) {
        return !o.getTransitiveObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is symmetric.
     * 
     * @param e
     *            the e
     * @return true for symmetric properties */
    public boolean isSymmetric(OWLObjectProperty e) {
        return !o.getSymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is asymmetric.
     * 
     * @param e
     *            the e
     * @return true for asymmetric properties */
    public boolean isAsymmetric(OWLObjectProperty e) {
        return !o.getAsymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is reflexive.
     * 
     * @param e
     *            the e
     * @return true for reflexive properties */
    public boolean isReflexive(OWLObjectProperty e) {
        return !o.getReflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is irreflexive.
     * 
     * @param e
     *            the e
     * @return true for irreflexive properties */
    public boolean isIrreflexive(OWLObjectProperty e) {
        return !o.getIrreflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is inverse functional.
     * 
     * @param e
     *            the e
     * @return true for inverse functional properties */
    public boolean isInverseFunctional(OWLObjectProperty e) {
        return !o.getInverseFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is functional.
     * 
     * @param e
     *            the e
     * @return true for functional object properties */
    public boolean isFunctional(OWLObjectProperty e) {
        return !o.getFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /** Checks if is functional.
     * 
     * @param e
     *            the e
     * @return true for functional data properties */
    public boolean isFunctional(OWLDataProperty e) {
        return !o.getFunctionalDataPropertyAxioms(e).isEmpty();
    }

    /** Checks if is defined.
     * 
     * @param c
     *            the c
     * @return true for defined classes */
    public boolean isDefined(OWLClass c) {
        return !o.getEquivalentClassesAxioms(c).isEmpty();
    }

    /** Gets the annotation axioms.
     * 
     * @param entity
     *            entity to search
     * @param ontologies
     *            ontologis to search
     * @return annotations about entity */
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAxioms(
            OWLEntity entity, Set<OWLOntology> ontologies) {
        Set<OWLAnnotationAssertionAxiom> result = new HashSet<OWLAnnotationAssertionAxiom>();
        for (OWLOntology ont : ontologies) {
            result.addAll(ont.getAnnotationAssertionAxioms(entity.getIRI()));
        }
        return result;
    }

    /** Gets the annotations.
     * 
     * @param entity
     *            entity to search
     * @param ontologies
     *            ontologies to search
     * @return annotations about entity */
    public Set<OWLAnnotation> getAnnotations(OWLEntity entity,
            Set<OWLOntology> ontologies) {
        Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
        for (OWLAnnotationAssertionAxiom ax : getAnnotationAxioms(entity,
                ontologies)) {
            result.add(ax.getAnnotation());
        }
        return result;
    }

    /** Gets the annotations.
     * 
     * @param entity
     *            entity to search
     * @param annotationProperty
     *            annotation property to match
     * @param ontologies
     *            ontologies to search
     * @return annotations about entity whose annotation property is
     *         annotationProperty */
    public Collection<T> getAnnotations(OWLEntity entity,
            OWLAnnotationProperty annotationProperty,
            Set<OWLOntology> ontologies) {
        Set<T> result = new HashSet<T>();
        for (OWLAnnotationAssertionAxiom ax : getAnnotationAxioms(entity,
                ontologies)) {
            if (ax.getAnnotation().getProperty().equals(annotationProperty)) {
                result.add((T) ax.getAnnotation());
            }
        }
        return result;
    }

    // @Override
    // public Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology)
    // {
    // return getSubProperties(Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSubProperties(OWLOntology ontology,
    // boolean includeImportsClosure) {
    // if (includeImportsClosure) {
    // return getSubProperties(ontology.getImportsClosure());
    // } else {
    // return getSubProperties(Collections.singleton(ontology));
    // }
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSubProperties(Set<OWLOntology>
    // ontologies) {
    // Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
    // for (OWLOntology ont : ontologies) {
    // for (OWLSubAnnotationPropertyOfAxiom ax : ont
    // .getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
    // if (ax.getSuperProperty().equals(this)) {
    // result.add(ax.getSubProperty());
    // }
    // }
    // }
    // return result;
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSuperProperties(OWLOntology
    // ontology) {
    // return getSuperProperties(Collections.singleton(ontology));
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSuperProperties(OWLOntology
    // ontology,
    // boolean includeImportsClosure) {
    // if (includeImportsClosure) {
    // return getSuperProperties(ontology.getImportsClosure());
    // } else {
    // return getSuperProperties(Collections.singleton(ontology));
    // }
    // }
    //
    // @Override
    // public Set<OWLAnnotationProperty> getSuperProperties(Set<OWLOntology>
    // ontologies) {
    // Set<OWLAnnotationProperty> result = new HashSet<OWLAnnotationProperty>();
    // for (OWLOntology ont : ontologies) {
    // for (OWLSubAnnotationPropertyOfAxiom ax : ont
    // .getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
    // if (ax.getSubProperty().equals(this)) {
    // result.add(ax.getSuperProperty());
    // }
    // }
    // }
    // return result;
    // }
    /** The Class AxiomsRetriever. */
    private class AxiomsRetriever implements OWLEntityVisitorEx<Collection<T>> {
        /** Instantiates a new axioms retriever. */
        public AxiomsRetriever() {
            // TODO Auto-generated constructor stub
        }

        /*
         * (non-Javadoc)
         * @see
         * org.semanticweb.owlapi.model.OWLEntityVisitorEx#visit(org.semanticweb
         * .owlapi.model.OWLClass)
         */
        @Override
        public Collection<T> visit(OWLClass e) {
            return (Collection<T>) o.getAxioms(e);
        }

        /*
         * (non-Javadoc)
         * @see
         * org.semanticweb.owlapi.model.OWLEntityVisitorEx#visit(org.semanticweb
         * .owlapi.model.OWLObjectProperty)
         */
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            return (Collection<T>) o.getAxioms(e);
        }

        /*
         * (non-Javadoc)
         * @see
         * org.semanticweb.owlapi.model.OWLEntityVisitorEx#visit(org.semanticweb
         * .owlapi.model.OWLDataProperty)
         */
        @Override
        public Collection<T> visit(OWLDataProperty e) {
            return (Collection<T>) o.getAxioms(e);
        }

        /*
         * (non-Javadoc)
         * @see
         * org.semanticweb.owlapi.model.OWLEntityVisitorEx#visit(org.semanticweb
         * .owlapi.model.OWLNamedIndividual)
         */
        @Override
        public Collection<T> visit(OWLNamedIndividual e) {
            return (Collection<T>) o.getAxioms(e);
        }

        /*
         * (non-Javadoc)
         * @see
         * org.semanticweb.owlapi.model.OWLEntityVisitorEx#visit(org.semanticweb
         * .owlapi.model.OWLDatatype)
         */
        @Override
        public Collection<T> visit(OWLDatatype e) {
            return (Collection<T>) o.getAxioms(e);
        }

        /*
         * (non-Javadoc)
         * @see
         * org.semanticweb.owlapi.model.OWLEntityVisitorEx#visit(org.semanticweb
         * .owlapi.model.OWLAnnotationProperty)
         */
        @Override
        public Collection<T> visit(OWLAnnotationProperty e) {
            return (Collection<T>) o.getAxioms(e);
        }
    }

    /** The Class SupRetriever. */
    private class SupRetriever extends OWLEntityVisitorExAdapter<Collection<T>> {
        /** Instantiates a new sup retriever. */
        public SupRetriever() {}

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLObjectProperty)
         */
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubObjectPropertyOfAxiom ax : ont
                        .getObjectSubPropertyAxiomsForSubProperty(e)) {
                    toReturn.add(ax.getSuperProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLDataProperty)
         */
        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubDataPropertyOfAxiom ax : ont
                        .getDataSubPropertyAxiomsForSubProperty(e)) {
                    toReturn.add(ax.getSuperProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLAnnotationProperty)
         */
        @Override
        public Collection<T> visit(OWLAnnotationProperty e) {
            Collection<OWLAnnotationProperty> toReturn = new ArrayList<OWLAnnotationProperty>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubAnnotationPropertyOfAxiom ax : ont
                        .getSubAnnotationPropertyOfAxioms(e)) {
                    toReturn.add(ax.getSuperProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLClass)
         */
        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubClassOfAxiom ax : ont
                        .getSubClassAxiomsForSubClass(desc)) {
                    toReturn.add(ax.getSuperClass());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    /** The Class SubRetriever.
     * 
     * @param <T>
     *            the generic type */
    private class SubRetriever<T> extends
            OWLEntityVisitorExAdapter<Collection<T>> {
        /** Instantiates a new sub retriever. */
        public SubRetriever() {
            // TODO Auto-generated constructor stub
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLObjectProperty)
         */
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubObjectPropertyOfAxiom ax : ont
                        .getObjectSubPropertyAxiomsForSuperProperty(e)) {
                    toReturn.add(ax.getSubProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLDataProperty)
         */
        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubDataPropertyOfAxiom ax : ont
                        .getDataSubPropertyAxiomsForSuperProperty(e)) {
                    toReturn.add(ax.getSubProperty());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLAnnotationProperty)
         */
        @Override
        public Collection<T> visit(OWLAnnotationProperty e) {
            Collection<OWLAnnotationProperty> toReturn = new ArrayList<OWLAnnotationProperty>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubAnnotationPropertyOfAxiom ax : ont
                        .getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
                    if (ax.getSuperProperty().equals(e)) {
                        toReturn.add(ax.getSubProperty());
                    }
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLClass)
         */
        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLSubClassOfAxiom ax : ont
                        .getSubClassAxiomsForSuperClass(desc)) {
                    toReturn.add(ax.getSubClass());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    /** The Class EquivalentRetriever.
     * 
     * @param <T>
     *            the generic type */
    private class EquivalentRetriever<T> extends
            OWLEntityVisitorExAdapter<Collection<T>> {
        /** Instantiates a new equivalent retriever. */
        public EquivalentRetriever() {
            // TODO Auto-generated constructor stub
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLObjectProperty)
         */
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLEquivalentObjectPropertiesAxiom ax : ont
                        .getEquivalentObjectPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLDataProperty)
         */
        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLEquivalentDataPropertiesAxiom ax : ont
                        .getEquivalentDataPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLClass)
         */
        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLEquivalentClassesAxiom ax : ont
                        .getEquivalentClassesAxioms(desc)) {
                    toReturn.addAll(ax.getClassExpressions());
                }
            }
            return (Collection<T>) toReturn;
        }
    }

    /** The Class DisjointRetriever.
     * 
     * @param <T>
     *            the generic type */
    private class DisjointRetriever<T> extends
            OWLEntityVisitorExAdapter<Collection<T>> {
        /** Instantiates a new disjoint retriever. */
        public DisjointRetriever() {
            // TODO Auto-generated constructor stub
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLObjectProperty)
         */
        @Override
        public Collection<T> visit(OWLObjectProperty e) {
            Collection<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLDisjointObjectPropertiesAxiom ax : ont
                        .getDisjointObjectPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLDataProperty)
         */
        @Override
        public Collection<T> visit(OWLDataProperty e) {
            Collection<OWLDataPropertyExpression> toReturn = new ArrayList<OWLDataPropertyExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLDisjointDataPropertiesAxiom ax : ont
                        .getDisjointDataPropertiesAxioms(e)) {
                    toReturn.addAll(ax.getProperties());
                }
            }
            return (Collection<T>) toReturn;
        }

        /*
         * (non-Javadoc)
         * @see org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter#visit(org.
         * semanticweb.owlapi.model.OWLClass)
         */
        @Override
        public Collection<T> visit(OWLClass desc) {
            // TODO same operation should be allowed on class expressions
            Collection<OWLClassExpression> toReturn = new ArrayList<OWLClassExpression>();
            for (OWLOntology ont : o.getImportsClosure()) {
                for (OWLDisjointClassesAxiom ax : ont
                        .getDisjointClassesAxioms(desc)) {
                    toReturn.addAll(ax.getClassExpressions());
                }
            }
            toReturn.remove(desc);
            return (Collection<T>) toReturn;
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return asCollection().iterator();
    }
}
