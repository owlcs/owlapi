package org.semanticweb.owlapi.model;

/**
 * what action to take if the ontology header is missing.
 */
public enum MissingOntologyHeaderStrategy {
    //@formatter:off
    /** Include triples. */         INCLUDE_GRAPH, 
    /** Keep import structure. */   IMPORT_GRAPH
    //@formatter:on
}
