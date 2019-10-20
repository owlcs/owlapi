package org.semanticweb.owlapi.model;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Convenience interface for classes that have an annotation value referred - i.e., annotation
 * assertion axioms, annotations and annotation values. The annotation value referred is the main
 * annotation value, nested annotations or annotations on axioms are excluded.
 * 
 * The purpose of these methods is providing stream friendly shortcuts to operate on literals, iris
 * and anonymous individuals used as values for annotations.
 */
public interface HasAnnotationValue {

    /**
     * @return for IRI values, the IRI, else an empty Optional
     */
    default Optional<IRI> iriValue() {
        return annotationValue().asIRI();
    }

    /**
     * @return for literal values, the literal, else an empty Optional
     */
    default Optional<OWLLiteral> literalValue() {
        return annotationValue().asLiteral();
    }

    /**
     * @return for anonymous individual values, the individual, else an empty Optional
     */
    default Optional<OWLAnonymousIndividual> anonymousIndividualValue() {
        return annotationValue().asAnonymousIndividual();
    }

    /**
     * @return the annotation value itself; this method allows OWLAnnotationAssertionAxiom and
     *         OWLAnnotation to use the default methods rather than replicate the methods. For
     *         OWLAnnotationValue instances, the value eturned is the object itself.
     */
    OWLAnnotationValue annotationValue();

    /**
     * Execute the consumer if and only if the annotation value is of the specified type and the
     * predicate evaluates to true; the alternative runnable is executed if the predicate does not
     * match or the type does not match (only once if both conditions do not match).
     * 
     * @param witness class for which predicate and consumer should be executed
     * @param p predicate to test
     * @param c consumer to apply
     * @param r runnable to execute if the predicate does not match or the annotation type does not
     *        match
     * @param <T> type
     */
    default <T extends OWLAnnotationValue> void when(Class<T> witness, Predicate<T> p,
        Consumer<T> c, Runnable r) {
        OWLAnnotationValue t = annotationValue();
        if (witness.isInstance(t)) {
            T av = witness.cast(t);
            if (p.test(av)) {
                c.accept(av);
            } else {
                r.run();
            }
        } else {
            r.run();
        }
    }

    /**
     * Apply the function if and only if the annotation value is of the specified type and the
     * predicate evaluates to true; the alternative supplier is executed if the predicate does not
     * match or the type does not match (only once if both conditions do not match).
     * 
     * @param <T> type to match
     * @param <O> return type
     * @param witness class for which predicate and function should be executed
     * @param p predicate to test
     * @param f function to apply
     * @param s supplier to execute if the predicate does not match or the annotation type does not
     *        match
     * @return function result or supplier result, depending on predicate evaluation
     */
    default <T extends OWLAnnotationValue, O> O map(Class<T> witness, Predicate<T> p,
        Function<T, O> f, Supplier<O> s) {
        OWLAnnotationValue t = annotationValue();
        if (witness.isInstance(t)) {
            T av = witness.cast(t);
            if (p.test(av)) {
                return f.apply(av);
            } else {
                return s.get();
            }
        }
        return s.get();
    }

    /**
     * Apply the function if and only if the annotation value is of the specified type and the
     * predicate evaluates to true; the default value is returned if the predicate does not match or
     * the type does not match.
     * 
     * @param <T> type to match
     * @param <O> return type
     * @param witness class for which predicate and function should be executed
     * @param p predicate to test
     * @param f function to apply
     * @param defaultValue default value to return if the predicate does not match or the annotation
     *        type does not match
     * @return function result or default value, depending on predicate evaluation
     */
    default <T extends OWLAnnotationValue, O> O map(Class<T> witness, Predicate<T> p,
        Function<T, O> f, O defaultValue) {
        OWLAnnotationValue t = annotationValue();
        if (witness.isInstance(t)) {
            T av = witness.cast(t);
            if (p.test(av)) {
                return f.apply(av);
            } else {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * @param literalConsumer consumer to run if the value is a literal
     */
    default void ifLiteral(Consumer<OWLLiteral> literalConsumer) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            literalConsumer.accept((OWLLiteral) value);
        }
    }


    /**
     * @param literalConsumer consumer to run if the value is a literal
     * @param elseAction runnable to run if the value iS not a literal
     */
    default void ifLiteralOrElse(Consumer<OWLLiteral> literalConsumer, Runnable elseAction) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            literalConsumer.accept((OWLLiteral) value);
        } else {
            elseAction.run();
        }
    }

    /**
     * @param iriConsumer consumer to run if the value is an IRI
     */
    default void ifIri(Consumer<IRI> iriConsumer) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIRI()) {
            iriConsumer.accept((IRI) value);
        }
    }

    /**
     * @param iriConsumer consumer to run if the value is an IRI
     * @param elseAction runnable to run if the value is not an IRI
     */
    default void ifIriOrElse(Consumer<IRI> iriConsumer, Runnable elseAction) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIRI()) {
            iriConsumer.accept((IRI) value);
        } else {
            elseAction.run();
        }
    }

    /**
     * @param anonConsumer consumer to run if the value is an anonymous individual
     */
    default void ifAnonymousIndividual(Consumer<OWLAnonymousIndividual> anonConsumer) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIndividual()) {
            anonConsumer.accept((OWLAnonymousIndividual) value);
        }
    }

    /**
     * @param anonConsumer consumer to run if the value is an anonymous individual
     * @param elseAction runnable to run if the value is not an anonymous individual
     */
    default void ifAnonymousIndividualOrElse(Consumer<OWLAnonymousIndividual> anonConsumer,
        Runnable elseAction) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIndividual()) {
            anonConsumer.accept((OWLAnonymousIndividual) value);
        } else {
            elseAction.run();
        }
    }

    /**
     * @param function function to run if the value is a literal
     * @param <T> returned type
     * @return mapped value for literals, empty otherwise
     */
    default <T> Optional<T> mapLiteral(Function<OWLLiteral, T> function) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            return Optional.ofNullable(function.apply((OWLLiteral) value));
        }
        return Optional.empty();
    }

    /**
     * @param function function to run if the value is a literal
     * @param defaultValue value returned if the value if not a literal
     * @param <T> returned type
     * @return mapped value for literals, default value for non literals
     */
    default <T> T mapLiteralOrElse(Function<OWLLiteral, T> function, T defaultValue) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            return function.apply((OWLLiteral) value);
        }
        return defaultValue;
    }

    /**
     * @param function function to run if the value is a literal
     * @param defaultValue supplier to run if the value is not a literal
     * @param <T> returned type
     * @return mapped value for literals, supplier result for non literals
     */
    default <T> T mapLiteralOrElseGet(Function<OWLLiteral, T> function, Supplier<T> defaultValue) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            return function.apply((OWLLiteral) value);
        }
        return defaultValue.get();
    }

    /**
     * @param function function to run if the value is an IRI
     * @param <T> returned type
     * @return mapped value for IRIs, empty for non IRIs
     */
    default <T> Optional<T> mapIri(Function<IRI, T> function) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIRI()) {
            return Optional.ofNullable(function.apply((IRI) value));
        }
        return Optional.empty();
    }

    /**
     * @param function function to run if the value is an IRI
     * @param defaultValue default value to return if the value is not an IRI
     * @param <T> return type
     * @return mapped value for IRIs, default value for non IRIs
     */
    default <T> T mapIriOrElse(Function<IRI, T> function, T defaultValue) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIRI()) {
            return function.apply((IRI) value);
        }
        return defaultValue;
    }

    /**
     * @param function function to run if the value is an IRI
     * @param defaultValue supplier to run if the value is not an IRI
     * @param <T> returned type
     * @return mapped value for IRIs, supplier result for non IRIs
     */
    default <T> T mapIriOrElseGet(Function<IRI, T> function, Supplier<T> defaultValue) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIRI()) {
            return function.apply((IRI) value);
        }
        return defaultValue.get();
    }

    /**
     * @param function function to run if the value is an anonymous individual
     * @param <T> returned type
     * @return mapped value for anonymous individuals, empty for non individuals
     */
    default <T> Optional<T> mapAnonymousIndividual(Function<OWLAnonymousIndividual, T> function) {
        OWLAnnotationValue value = annotationValue();
        if (value.isIRI()) {
            return Optional.ofNullable(function.apply((OWLAnonymousIndividual) value));
        }
        return Optional.empty();
    }

    /**
     * @param function function to run if the value is an anonymous individual
     * @param defaultValue default value to if the value is not an anonymous individual
     * @param <T> returned type
     * @return mapped value for anonymous individuals, default value for non individuals
     */
    default <T> T mapAnonymousIndividualOrElse(Function<OWLAnonymousIndividual, T> function,
        T defaultValue) {
        OWLAnnotationValue value = annotationValue();
        if (value.isAnonymous()) {
            return function.apply((OWLAnonymousIndividual) value);
        }
        return defaultValue;
    }

    /**
     * @param function function to run if the value is an anonymous individual
     * @param defaultValue supplier to run if the value is not an anonymous individual
     * @param <T> returned type
     * @return mapped value for anonymous individuals, supplier result for non individuals
     */
    default <T> T mapAnonymousIndividualOrElseGet(Function<OWLAnonymousIndividual, T> function,
        Supplier<T> defaultValue) {
        OWLAnnotationValue value = annotationValue();
        if (value.isAnonymous()) {
            return function.apply((OWLAnonymousIndividual) value);
        }
        return defaultValue.get();
    }

    /**
     * @param literalConsumer consumer to run for literals
     * @param iriConsumer consumer to run for IRIs
     * @param anonConsumer consumer to run for anonymous individuals
     */
    default void ifValue(Consumer<OWLLiteral> literalConsumer, Consumer<IRI> iriConsumer,
        Consumer<OWLAnonymousIndividual> anonConsumer) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            ifLiteral(literalConsumer);
        } else if (value.isIRI()) {
            ifIri(iriConsumer);
        } else {
            ifAnonymousIndividual(anonConsumer);
        }
    }

    /**
     * @param literalFunction function to run for literals
     * @param iriFunction function to run for IRIs
     * @param anonFunction function to run for anonymous individuals
     * @param <T> returned type
     * @return mapped value, or empty if none matches (currently there will be always one matching
     *         function, but the creation of a new OWLAnnotationValue subinterface would change
     *         that)
     */
    default <T> Optional<T> mapValue(Function<OWLLiteral, T> literalFunction,
        Function<IRI, T> iriFunction, Function<OWLAnonymousIndividual, T> anonFunction) {
        OWLAnnotationValue value = annotationValue();
        if (value.isLiteral()) {
            return mapLiteral(literalFunction);
        } else if (value.isIRI()) {
            return mapIri(iriFunction);
        } else {
            return mapAnonymousIndividual(anonFunction);
        }
    }
}
