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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A marker interface for annotation values, which can either be an IRI (URI), Literal or Anonymous
 * Individual, with visitor methods.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @see org.semanticweb.owlapi.model.IRI
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see org.semanticweb.owlapi.model.OWLAnonymousIndividual
 * @since 3.0.0
 */
public interface OWLAnnotationValue extends OWLAnnotationObject, OWLPrimitive, HasAnnotationValue {

    /**
     * @param visitor visitor to accept
     */
    void accept(OWLAnnotationValueVisitor visitor);

    /**
     * @param visitor visitor to accept
     * @param <O> visitor return type
     * @return visitor value
     */
    <O> O accept(OWLAnnotationValueVisitorEx<O> visitor);

    /**
     * @return if the value is a literal, return an optional containing it. Return Optional.absent
     *         otherwise.
     */
    default Optional<OWLLiteral> asLiteral() {
        return emptyOptional();
    }

    default boolean isLiteral() {
        return false;
    }

    @Override
    default OWLAnnotationValue annotationValue() {
        return this;
    }

    /** @deprecated Use {@code asIRI()} instead */
    @Override
    @Deprecated
    default Optional<IRI> iriValue() {
        return asIRI();
    }

    /** @deprecated Use {@code asLiteral()} instead */
    @Override
    @Deprecated
    default Optional<OWLLiteral> literalValue() {
        return asLiteral();
    }

    /** @deprecated Use {@code asAnonymousIndividual()} instead */
    @Override
    @Deprecated
    default Optional<OWLAnonymousIndividual> anonymousIndividualValue() {
        return asAnonymousIndividual();
    }

    @Override
    default void ifLiteral(Consumer<OWLLiteral> literalConsumer) {
        if (isLiteral()) {
            literalConsumer.accept((OWLLiteral) this);
        }
    }

    @Override
    default void ifLiteralOrElse(Consumer<OWLLiteral> literalConsumer, Runnable alternativeAction) {
        if (isLiteral()) {
            literalConsumer.accept((OWLLiteral) this);
        } else {
            alternativeAction.run();
        }
    }

    @Override
    default void ifIri(Consumer<IRI> iriConsumer) {
        if (isIRI()) {
            iriConsumer.accept((IRI) this);
        }
    }

    @Override
    default void ifIriOrElse(Consumer<IRI> iriConsumer, Runnable alternativeAction) {
        if (isIRI()) {
            iriConsumer.accept((IRI) this);
        } else {
            alternativeAction.run();
        }
    }

    @Override
    default void ifAnonymousIndividual(Consumer<OWLAnonymousIndividual> individualConsumer) {
        if (isIndividual()) {
            individualConsumer.accept((OWLAnonymousIndividual) this);
        }
    }

    @Override
    default void ifAnonymousIndividualOrElse(Consumer<OWLAnonymousIndividual> individualConsumer,
        Runnable alternativeAction) {
        if (isIndividual()) {
            individualConsumer.accept((OWLAnonymousIndividual) this);
        } else {
            alternativeAction.run();
        }
    }

    @Override
    default <T> Optional<T> mapLiteral(Function<OWLLiteral, T> function) {
        if (isLiteral()) {
            return Optional.ofNullable(function.apply((OWLLiteral) this));
        }
        return Optional.empty();
    }

    @Override
    default <T> T mapLiteralOrElse(Function<OWLLiteral, T> function, T defaultValue) {
        if (isLiteral()) {
            return function.apply((OWLLiteral) this);
        }
        return defaultValue;
    }

    @Override
    default <T> T mapLiteralOrElseGet(Function<OWLLiteral, T> function, Supplier<T> defaultValue) {
        if (isLiteral()) {
            return function.apply((OWLLiteral) this);
        }
        return defaultValue.get();
    }

    @Override
    default <T> Optional<T> mapIri(Function<IRI, T> function) {
        if (isIRI()) {
            return Optional.ofNullable(function.apply((IRI) this));
        }
        return Optional.empty();
    }

    @Override
    default <T> T mapIriOrElse(Function<IRI, T> function, T defaultValue) {
        if (isIRI()) {
            return function.apply((IRI) this);
        }
        return defaultValue;
    }

    @Override
    default <T> T mapIriOrElseGet(Function<IRI, T> function, Supplier<T> defaultValue) {
        if (isIRI()) {
            return function.apply((IRI) this);
        }
        return defaultValue.get();
    }

    @Override
    default <T> Optional<T> mapAnonymousIndividual(Function<OWLAnonymousIndividual, T> function) {
        if (isIRI()) {
            return Optional.ofNullable(function.apply((OWLAnonymousIndividual) this));
        }
        return Optional.empty();
    }

    @Override
    default <T> T mapAnonymousIndividualOrElse(Function<OWLAnonymousIndividual, T> function,
        T defaultValue) {
        if (isAnonymous()) {
            return function.apply((OWLAnonymousIndividual) this);
        }
        return defaultValue;
    }

    @Override
    default <T> T mapAnonymousIndividualOrElseGet(Function<OWLAnonymousIndividual, T> function,
        Supplier<T> defaultValue) {
        if (isAnonymous()) {
            return function.apply((OWLAnonymousIndividual) this);
        }
        return defaultValue.get();
    }

    @Override
    default void ifValue(Consumer<OWLLiteral> literalFunction, Consumer<IRI> iriFunction,
        Consumer<OWLAnonymousIndividual> anonymousIndividualFunction) {
        if (isLiteral()) {
            ifLiteral(literalFunction);
        } else if (isIRI()) {
            ifIri(iriFunction);
        } else {
            ifAnonymousIndividual(anonymousIndividualFunction);
        }
    }

    @Override
    default <T> Optional<T> mapValue(Function<OWLLiteral, T> literalFunction,
        Function<IRI, T> iriFunction,
        Function<OWLAnonymousIndividual, T> anonymousIndividualFunction) {
        if (isLiteral()) {
            return mapLiteral(literalFunction);
        } else if (isIRI()) {
            return mapIri(iriFunction);
        } else {
            return mapAnonymousIndividual(anonymousIndividualFunction);
        }
    }
}
