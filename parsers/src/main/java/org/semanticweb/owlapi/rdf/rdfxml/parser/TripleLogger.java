package org.semanticweb.owlapi.rdf.rdfxml.parser;

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
    private int count = 0;

    /** @return triples counted */
    synchronized public int count() {
        return count;
    }
    synchronized public void logTriple(Object s, Object p, Object o) {
        log.debug("s={} p={} o={}",s,p,o);
        incrementTripleCount();
    }
    /** increment count and log. */
    private  void incrementTripleCount() {
        count++;
        if (count % 10000 == 0) {
            log.debug("Parsed: {} triples", count);
        }
    }

    /** log finl count. */
   synchronized public void logNumberOfTriples() {
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
