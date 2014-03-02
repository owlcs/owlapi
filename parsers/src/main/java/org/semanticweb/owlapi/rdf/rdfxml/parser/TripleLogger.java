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

    /** The Constant tripleProcessor. */
    private static final Logger tripleProcessor = LoggerFactory
            .getLogger(TripleLogger.class);
    // Debug stuff
    private int count = 0;

    /** @return triples counted */
    public int count() {
        return count;
    }

    /** increment count and log. */
    public void incrementTripleCount() {
        count++;
        if (count % 10000 == 0) {
            tripleProcessor.info("Parsed: {} triples", count);
        }
    }

    /** log finl count. */
    public void logNumberOfTriples() {
        tripleProcessor.info("Total number of triples: {}", count);
    }

    /**
     * @param id
     *        log ontology id
     */
    public void logOntologyID(OWLOntologyID id) {
        tripleProcessor.info("Loaded {}", id);
    }
}
