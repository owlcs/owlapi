package org.semanticweb.owlapi.model;

/**
 * Anonymous or named object interface. Named {@code OWLObject} instances have an IRI; this includes
 * {@code OWLClass}, {@code OWLDataProperty}, {@code OWLObjectProperty}, {@code OWLDatatype},
 * {@code OWLAnnotationProperty} and {@code OWLNamedIndividual} instances. {@code OWLOntology}
 * instances also have an IRI or can be anonymous, depending on the contents of their ontology id.
 * Ontologies are mutable and therefore can switch from anonymous to named, when their id is
 * changed; all other objects are immutable, and therefore will not change from anonymous to named.
 */
public interface IsAnonymous {

    /**
     * @return {@code true} if this object is anonymous, {@code false} otherwise. For example, class
     *         expressions are anonymous while class entities are not (they have an IRI);
     *         {@code OWLNamedIndividual} instances are named, {@code OWLAnonymousIndividual}
     *         instances are anonymous. An ontology is anonymous if it does not have an ontology
     *         IRI. In this case, getOntologyID().getOntologyIRI() will return an empty optional.
     */
    default boolean isAnonymous() {
        return true;
    }

    /**
     * @return {@code true} if this object is named, {@code false} otherwise. For example, class
     *         entities are named (they have an IRI) while class expressions are anonymous;
     *         {@code OWLNamedIndividual} instances are named, {@code OWLAnonymousIndividual}
     *         instances are anonymous.
     */
    default boolean isNamed() {
        return !isAnonymous();
    }
}
