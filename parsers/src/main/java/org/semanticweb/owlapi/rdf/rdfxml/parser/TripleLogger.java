package org.semanticweb.owlapi.rdf.rdfxml.parser;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.PrefixManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper for triple logging functions.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public class TripleLogger {

    private static final Logger log = LoggerFactory
            .getLogger(TripleLogger.class);
    private final PrefixManager prefixManager;
    // Debug stuff
    private AtomicInteger count = new AtomicInteger();

    /**
     * @param prefixManager
     *        prefix manager
     */
    public TripleLogger(@Nullable PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

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
        justLog(s, p, o);
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
        justLog(s, p, o, lang, datatype);
        incrementTripleCount();
    }

    /**
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
    public void justLog(Object s, Object p, Object o, Object lang,
            Object datatype) {
        if (log.isTraceEnabled()) {
            log.trace("s={} p={} o={} l={} dt={}", shorten(s), shorten(p),
                    shorten(o), lang, shorten(datatype));
        }
    }

    /**
     * @param s
     *        subject
     * @param p
     *        predicate
     * @param o
     *        object
     */
    public void justLog(Object s, Object p, Object o) {
        if (log.isTraceEnabled()) {
            log.trace("s={} p={} o={}", shorten(s), shorten(p), shorten(o));
        }
    }

    private Object shorten(@Nullable Object o) {
        if (o == null) {
            return "null";
        }
        if (o instanceof String
                && (((String) o).startsWith("http:") || ((String) o)
                        .startsWith("urn:"))) {
            return shorten(IRI.create((String) o));
        }
        if (prefixManager == null || !(o instanceof IRI)) {
            // quote strings and bnodes
            return "\"" + o + "\"";
        }
        // there is a prefix manager and o is an IRI
        IRI i = (IRI) o;
        String result = prefixManager.getPrefixIRI(i);
        if (result == null) {
            result = i.toQuotedString();
        }
        return result;
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
    public void logOntologyID(OWLOntologyID id) {
        log.debug("Loaded {}", id);
    }
}
