package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * @since 3.2
 */
public class RDFParserMetaData implements OWLOntologyLoaderMetaData {

    private int tripleCount;

    private RDFOntologyHeaderStatus headerStatus;

    private Set<RDFTriple> unparsedTriples = new HashSet<RDFTriple>();

    public RDFParserMetaData(RDFOntologyHeaderStatus headerStatus, int tripleCount, Set<RDFTriple> unparsedTriples) {
        this.tripleCount = tripleCount;
        this.headerStatus = headerStatus;
        this.unparsedTriples = new HashSet<RDFTriple>(unparsedTriples);
    }

    /**
     * Gets a count of the triples process during loading.
     * @return The number of triples process during loading.
     */
    public int getTripleCount() {
        return tripleCount;
    }

    public RDFOntologyHeaderStatus getHeaderState() {
        return headerStatus;
    }

    public Set<RDFTriple> getUnparsedTriples() {
        return Collections.unmodifiableSet(unparsedTriples);
    }

}
