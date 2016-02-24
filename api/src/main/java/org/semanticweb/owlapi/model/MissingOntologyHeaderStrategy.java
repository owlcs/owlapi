package org.semanticweb.owlapi.model;

/**
 * what action to take if the ontology header is missing.
 */
public enum MissingOntologyHeaderStrategy implements ByName<MissingOntologyHeaderStrategy> {
    //@formatter:off
    /** Include triples. */         INCLUDE_GRAPH, 
    /** Keep import structure. */   IMPORT_GRAPH;
    //@formatter:on
    @Override
    public MissingOntologyHeaderStrategy byName(CharSequence name) {
        return valueOf(name.toString());
    }
}
