package org.semanticweb.owlapi6.rdf.rdfxml.parser;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.IRI;

class AnonMap extends MultiMap<IRI, IRI> {

    public AnonMap() {
        super(x -> new ArrayList<>());
    }

    /**
     * Records an annotation on an anonymous node (either an annotation on an annotation, or an
     * annotation on an axiom).
     *
     * @param subject The subject that the annotation annotates
     * @param annotation The annotation
     * @return true if added
     */
    protected boolean addAnnotatedSource(IRI subject, IRI annotation) {
        return put(subject, annotation);
    }

    /**
     * Gets the main nodes of annotations that annotated the specified source.
     *
     * @param source The source (axiom or annotation main node)
     * @return The set of main nodes that annotate the specified source
     */
    protected Collection<IRI> getAnnotatedSource(IRI source) {
        return get(source);
    }

    protected void consumeAnnotatedSource(IRI source, Consumer<IRI> dest) {
        get(source).forEach(dest);
    }

    @Nullable
    protected IRI getAnnotatedSource(IRI source, Predicate<IRI> p) {
        return get(source).stream().filter(p).findAny().orElse(null);
    }
}
