package org.semanticweb.owlapi.model;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public interface HasAnnotationValue {

    Optional<IRI> iriValue();

    Optional<OWLLiteral> literalValue();

    Optional<OWLAnonymousIndividual> anonymousIndividualValue();

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
     */
    default <T extends OWLAnnotationValue> void when(Class<T> witness, Predicate<T> p,
        Consumer<T> c, Runnable r) {
        OWLAnnotationValue t = annotationValue();
        if (witness.isInstance(t)) {
            T av = witness.cast(t);
            if (p.test(av)) {
                c.accept(av);
                return;
            }
            r.run();
            return;
        }
        r.run();
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

    void ifLiteral(Consumer<OWLLiteral> iri);

    void ifLiteralOrElse(Consumer<OWLLiteral> iri, Runnable emptyAction);

    void ifIri(Consumer<IRI> iri);

    void ifIriOrElse(Consumer<IRI> iri, Runnable emptyAction);

    void ifAnonymousIndividual(Consumer<OWLAnonymousIndividual> iri);

    void ifAnonymousIndividualOrElse(Consumer<OWLAnonymousIndividual> iri, Runnable emptyAction);

    <T> Optional<T> mapLiteral(Function<OWLLiteral, T> function);

    <T> T mapLiteralOrElse(Function<OWLLiteral, T> function, T defaultValue);

    <T> T mapLiteralOrElseGet(Function<OWLLiteral, T> function, Supplier<T> defaultValue);

    <T> Optional<T> mapIri(Function<IRI, T> function);

    <T> T mapIriOrElse(Function<IRI, T> function, T defaultValue);

    <T> T mapIriOrElseGet(Function<IRI, T> function, Supplier<T> defaultValue);

    <T> Optional<T> mapAnonymousIndividual(Function<OWLAnonymousIndividual, T> function);

    <T> T mapAnonymousIndividualOrElse(Function<OWLAnonymousIndividual, T> function,
        T defaultValue);

    <T> T mapAnonymousIndividualOrElseGet(Function<OWLAnonymousIndividual, T> function,
        Supplier<T> defaultValue);

    void ifValue(Consumer<OWLLiteral> literalFunction, Consumer<IRI> iriFunction,
        Consumer<OWLAnonymousIndividual> anonymousIndividualFunction);

    <T> Optional<T> mapValue(Function<OWLLiteral, T> literalFunction, Function<IRI, T> iriFunction,
        Function<OWLAnonymousIndividual, T> anonymousIndividualFunction);
}
