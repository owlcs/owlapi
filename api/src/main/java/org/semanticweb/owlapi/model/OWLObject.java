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

import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.empty;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.flatComponents;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLObject
    extends Comparable<OWLObject>, Serializable, HasSignature, HasContainsEntityInSignature,
    HasAnonymousIndividuals, HasClassesInSignature, HasObjectPropertiesInSignature,
    HasDataPropertiesInSignature, HasIndividualsInSignature, HasDatatypesInSignature,
    HasAnnotationPropertiesInSignature, HasIndex, HasHashIndex, IsAnonymous {

    /**
     * Gets all of the nested (includes top level) class expressions (anonymous and named) that are
     * used in this object. For an ontology, this method visits the logical axioms, so entities that
     * are only declared (and possibly annotated) but do not appear in any logical axiom are not
     * returned as part of the stream. The default implementation of this method returns an empty
     * stream.
     *
     * @return A stream of {@link org.semanticweb.owlapi.model.OWLClassExpression}s that represent
     *         the nested class expressions used in this object.
     */
    default Stream<OWLClassExpression> nestedClassExpressions() {
        return empty();
    }

    /**
     * Accepts a visitor
     *
     * @param visitor The visitor
     */
    void accept(OWLObjectVisitor visitor);

    /**
     * Accepts a visitor
     *
     * @param visitor The visitor
     * @param <O> visitor return type
     * @return visitor value
     */
    <O> O accept(OWLObjectVisitorEx<O> visitor);

    /**
     * Determines if this object is either, owl:Thing (the top class), owl:topObjectProperty (the
     * top object property) , owl:topDataProperty (the top data property) or rdfs:Literal (the top
     * datatype).
     *
     * @return {@code true} if this object corresponds to one of the above entities.
     */
    default boolean isTopEntity() {
        return false;
    }

    /**
     * Determines if this object is either, owl:Nothing (the bottom class), owl:bottomObjectProperty
     * (the bottom object property) , owl:bottomDataProperty (the bottom data property).
     *
     * @return {@code true} if this object corresponds to one of the above entities.
     */
    default boolean isBottomEntity() {
        return false;
    }

    /**
     * @return true if this object is an IRI.
     */
    default boolean isIRI() {
        return false;
    }

    /**
     * @return true if this object is an individual.
     */
    default boolean isIndividual() {
        return false;
    }

    /**
     * @return true if this object is an axiom.
     */
    default boolean isAxiom() {
        return false;
    }

    /**
     * @return true for ontologies, false for any other OWL object
     */
    default boolean isOntology() {
        return false;
    }

    /**
     * @return true if this object is not an axiom, not an individual and anonymous; this is true
     *         for class and property expressions, as well as data ranges.
     */
    default boolean isAnonymousExpression() {
        return !isAxiom() && !isIndividual() && !isOntology() && !isIRI()
            && !(this instanceof OWLLiteral) && isAnonymous();
    }

    /**
     * @return true if this object contains anonymous expressions referred multiple times. This is
     *         called structure sharing. An example can be:<br>
     *
     *         <pre>
     * some P C subClassOf some Q (some P C)
     *         </pre>
     *
     *         <br>
     *         This can happen in axioms as well as in expressions:<br>
     *
     *         <pre>
     * (some P C) and (some Q (some P C))
     *         </pre>
     *
     *         <br>
     */
    default boolean hasSharedStructure() {
        Map<OWLObject, AtomicInteger> counters = new HashMap<>();
        Stream<OWLObject> filter = flatComponents(this).filter(x -> x instanceof OWLObject)
            .map(x -> (OWLObject) x).filter(OWLObject::isAnonymousExpression);
        filter
            .forEach(x -> counters.computeIfAbsent(x, q -> new AtomicInteger(0)).incrementAndGet());
        return counters.values().stream().anyMatch(x -> x.get() > 1);
    }

    /**
     * @return hash code for the object; called on first use, cached by OWLObjectImpl in the default
     *         implementation.
     */
    default int initHashCode() {
        return type().hashCode(this);
    }

    /**
     * OWLObject based equals. Used in the default implementation for equals calls.
     * 
     * @param obj object to compare to
     * @return true if obj equals this object
     */
    default boolean equalsOWLObject(OWLObject obj) {
        return OWLObjectType.equals(this, obj);
    }

    /**
     * Comparator implementation for OWLObjects. Used in the default implementation for compareTo
     * calls.
     * 
     * @param obj object to compare to
     * @return negative value for {@code this < obj}, positive value for {@code obj < this}, zero if
     *         this object and obj are equal.
     */
    default int compareToOWLObject(OWLObject obj) {
        return OWLObjectType.compareTo(this, obj);
    }

    /**
     * @return the OWLObjectType for this OWL object
     */
    OWLObjectType type();

    /**
     * @return components as a stream. The stream is ordered (by visit order) but not sorted.
     *         Annotation lists are skipped.
     */
    default Stream<?> componentStream() {
        return type().components(this);
    }

    /**
     * @return components as a stream; for objects that can have annotations on them, the annotation
     *         list appears first. The stream is ordered (by visit order) but not sorted.
     */
    default Stream<?> componentsAnnotationsFirst() {
        return type().componentsAnnotationsFirst(this);
    }

    /**
     * @return components as a stream; for objects that can have annotations on them, the annotation
     *         list appears last. The stream is ordered (by visit order) but not sorted.
     */
    default Stream<?> componentsAnnotationsLast() {
        return type().componentsAnnotationsLast(this);
    }

    @Override
    default int hashIndex() {
        return type().hashIndex();
    }

    @Override
    default int typeIndex() {
        return type().typeIndex();
    }

    /**
     * @param format document format to use
     * @return string serialization
     */
    String toSyntax(OWLDocumentFormat format);

    /**
     * @param format document format to use
     * @param pm prefix manager
     * @return string serialization
     */
    String toSyntax(OWLDocumentFormat format, PrefixManager pm);

    /**
     * @return format the object to functional syntax
     */
    String toFunctionalSyntax();

    /**
     * @return format the object to manchester syntax
     */
    String toManchesterSyntax();

    /**
     * @param pm prefix manager
     * @return format the object to functional syntax
     */
    String toFunctionalSyntax(PrefixManager pm);

    /**
     * @param pm prefix manager
     * @return format the object to manchester syntax
     */
    String toManchesterSyntax(PrefixManager pm);
}
