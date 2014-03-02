package org.semanticweb.owlapi.rdf.rdfxml.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Wrapper for triple logging functions.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public class TripleLogger {

    /** The Constant tripleProcessor. */
    private static final Logger tripleProcessor = Logger
            .getLogger(TripleLogger.class.getName());
    // Debug stuff
    private int count = 0;

    /** @return triples counted */
    public int count() {
        return count;
    }

    /** increment count and log. */
    public void incrementTripleCount() {
        count++;
        if (tripleProcessor.isLoggable(Level.FINE) && count % 10000 == 0) {
            tripleProcessor.fine("Parsed: " + count + " triples");
        }
    }

    /** log finl count. */
    public void logNumberOfTriples() {
        if (tripleProcessor.isLoggable(Level.FINE)) {
            tripleProcessor.fine("Total number of triples: " + count);
        }
    }

    /**
     * @param id
     *        log ontology id
     */
    public void logOntologyID(OWLOntologyID id) {
        if (tripleProcessor.isLoggable(Level.FINE)) {
            tripleProcessor.fine("Loaded " + id);
        }
    }
}
