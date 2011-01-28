package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 */
public class RDFResourceNode extends RDFNode {

    private IRI iri;

    private int anonId;


    /**
     * Constructs a named resource (i.e. a resource with
     * a URI).
     */
    public RDFResourceNode(IRI iri) {
        this.iri = iri;
    }


    /**
     * Constructs an anonymous node, which has the specified ID.
     * @param anonId The id of the node
     */
    public RDFResourceNode(int anonId) {
        this.anonId = anonId;
    }


    @Override
	public IRI getIRI() {
        return iri;
    }


    @Override
	public boolean isLiteral() {
        return false;
    }


    @Override
	public boolean isAnonymous() {
        return iri == null;
    }


    @Override
	public int hashCode() {
        int hashCode = 17;
        hashCode = hashCode * 37 + (iri == null ? anonId : iri.hashCode());
        return hashCode;
    }


    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof RDFResourceNode)) {
            return false;
        }
        RDFResourceNode other = (RDFResourceNode) obj;
        if (iri != null) {
            if (other.iri != null) {
                return other.iri.equals(iri);
            }
            else {
                return false;
            }
        }
        else {
            return other.anonId == anonId;
        }
    }


    @Override
	public String toString() {
        return (iri != null ? "<" + iri.toString() + ">" : "genid" + Integer.toString(anonId));
    }
}
