package org.semanticweb.owlapi.io;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * @since 3.2
 * <br>
 * Describes why an RDF resource could not be parsed into an <code>OWLObject</code>.  For example, why an RDF resource could
 * not be parsed into an <code>OWLClassExpression</code>.
 * <br>
 * When these errors occur, the RDF parser generates an <code>OWLEntity</code> that represents the error and inserts
 * this where appropriate into the corresponding complete OWLObject (OWLAxiom) that could not be parsed.
 */
public class RDFResourceParseError {

    private final OWLEntity parserGeneratedErrorEntity;

    private final RDFNode mainNode;

    private final Set<RDFTriple> mainNodeTriples = new HashSet<RDFTriple>();

    public RDFResourceParseError(OWLEntity parserGeneratedErrorEntity, RDFNode mainNode, Set<RDFTriple> mainNodeTriples) {
        this.parserGeneratedErrorEntity = parserGeneratedErrorEntity;
        this.mainNode = mainNode;
        this.mainNodeTriples.addAll(mainNodeTriples);
    }

    public OWLEntity getParserGeneratedErrorEntity() {
        return parserGeneratedErrorEntity;
    }

    public RDFNode getMainNode() {
        return mainNode;
    }

    public Set<RDFTriple> getMainNodeTriples() {
        return CollectionFactory.getCopyOnRequestSet(mainNodeTriples);
    }

}
