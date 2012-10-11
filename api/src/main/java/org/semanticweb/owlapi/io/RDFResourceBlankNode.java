package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;

public class RDFResourceBlankNode extends RDFResource {
    private static final long serialVersionUID = 30405L;
    
    private final IRI resource;

    /**
     * Create an RDFResource that is anonymous
     * @param resource The IRI of the resource
     */
    public RDFResourceBlankNode(IRI resource) {
        this.resource = resource;
    }

    public RDFResourceBlankNode(int anonId) {
        this.resource = IRI.create("_:genid" + Integer.toString(anonId));
    }

    /**
     * Determines if this node is a literal node.
     * @return <code>true</code> if this node is a literal, otherwise <code>false</code>.
     */
    @Override
    public boolean isLiteral() {
        return false;
    }

    /**
     * @return true if resource is anonymous
     */
    public boolean isAnonymous() {
        return true;
    }

    @Override
    public int hashCode() {
        return resource.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RDFResourceBlankNode)) {
            return false;
        }
        RDFResourceBlankNode other = (RDFResourceBlankNode) o;
        return this.toString().equals(other.toString());
    }
    
    @Override
    public int compareTo(RDFNode b) {
        if (this == b) {
           return 0; 
        }
        if (b instanceof RDFResourceIRI) {
            return -1;
        }
        else if(b instanceof RDFLiteral) {
            return 1;
        }
        RDFResourceBlankNode other = (RDFResourceBlankNode) b;
        
        return toString().compareTo(other.toString());
    }
    
    @Override
    public String toString() {
        if(!resource.toString().startsWith("_:")) {
            return "_:" + resource.toString();
        } else {
            return resource.toString();
        }
    }

    @Override
    public IRI getIRI() {
        return resource;
    }

    @Override
    public IRI getResource() {
        return getIRI();
    }
    
}

