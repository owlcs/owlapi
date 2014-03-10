package org.semanticweb.owlapi.rdf.rdfxml.parser;

import java.util.concurrent.atomic.AtomicInteger;

import org.semanticweb.owlapi.model.OWLOntologyID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper for triple logging functions.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public class TripleLogger {

    /** The Constant log. */
    private static final Logger log = LoggerFactory
            .getLogger(TripleLogger.class);
    // Debug stuff
    private AtomicInteger count = new AtomicInteger();

    /** @return triples counted */
    public int count() {
        return count.get();
    }

    /**
     * log triples at debug level and increment triple count
     * 
     * @param s
     *        subject
     * @param p
     *        predicate
     * @param o
     *        object
     */
    public void logTriple(Object s, Object p, Object o) {
        log.trace("s={} p={} o={}", s, p, o);
        incrementTripleCount();
    }

    /**
     * log triples at debug level, including language and datatype, and
     * increment triple count
     * 
     * @param s
     *        subject
     * @param p
     *        predicate
     * @param o
     *        object
     * @param lang
     *        language
     * @param datatype
     *        datatype
     */
    public void logTriple(Object s, Object p, Object o, Object lang,
            Object datatype) {
        log.trace("s={} p={} o={} l={} dt={}", s, p, o, lang, datatype);
        incrementTripleCount();
    }

    /** increment count and log. */
    private void incrementTripleCount() {
        if (count.incrementAndGet() % 10000 == 0) {
            log.debug("Parsed: {} triples", count);
        }
    }

    /** log finl count. */
    public void logNumberOfTriples() {
        log.debug("Total number of triples: {}", count);
    }

    /**
     * @param id
     *        log ontology id
     */
    synchronized public void logOntologyID(OWLOntologyID id) {
        log.debug("Loaded {}", id);
    }
}
