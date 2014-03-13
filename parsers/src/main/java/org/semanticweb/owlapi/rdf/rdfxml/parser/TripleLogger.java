package org.semanticweb.owlapi.rdf.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.PrefixManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 * Wrapper for triple logging functions.
 *
 * @author ignazio
 * @since 4.0.0
 */
public class TripleLogger {

    /**
     * The Constant log.
     */
    private final PrefixManager prefixManager;
    private final Logger log;
    // Debug stuff
    private int count = 0;

    public TripleLogger(PrefixManager prefixManager) {
        this(LoggerFactory.getLogger(TripleLogger.class), prefixManager);
    }

    public TripleLogger(@Nonnull Logger log, PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
        this.log = log;
    }

    /**
     * @return triples counted
     */
    synchronized public int count() {
        return count;
    }

    synchronized public void logResourceTriple(String s, String p, String o) {
        if (log.isTraceEnabled()) {
            logResourceTriple(IRI.create(s), IRI.create(p), IRI.create(o));
        }
    }

    synchronized public void logResourceTriple(IRI s, IRI p, IRI o) {
        if (log.isTraceEnabled()) {
            log.trace("{} {} {}.", shorten(s), shorten(p), shorten(o));
            incrementTripleCount();
        }
    }


    synchronized public void logLiteralTriple(String s, String p, String o) {
        if (log.isTraceEnabled()) {
            logLiteralTriple(IRI.create(s), IRI.create(p), o);
        }
    }

    synchronized public void logLiteralTriple(IRI s, IRI p, String o) {
        if (log.isTraceEnabled()) {
            log.trace("{} {} \"{}\".", shorten(s), shorten(p), o);
            incrementTripleCount();
        }
    }

    /**
     * increment count and log.
     */
    private void incrementTripleCount() {
        count++;
        if (count % 10000 == 0) {
            log.debug("Parsed: {} triples", count);
        }
    }

    /**
     * log finl count.
     */
    synchronized public void logNumberOfTriples() {
        log.debug("Total number of triples: {}", count);
    }

    /**
     * @param id log ontology id
     */
    synchronized public void logOntologyID(OWLOntologyID id) {
        log.debug("Loaded {}", id);
    }

    private String shorten(IRI iri) {
        if (prefixManager == null) {
            return iri.toQuotedString();
        } else {
            String result = prefixManager.getPrefixIRI(iri);
            if (result == null) {
                result = iri.toQuotedString();
            }
            return result;
        }
    }

}
