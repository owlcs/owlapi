package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;

public class RDFResourceIRI extends RDFResource {
    private static final long serialVersionUID = 30405L;
    
    private final IRI resource;

    /**
     * @param resource the resource
     */
    public RDFResourceIRI(IRI resource) {
        this.resource = resource;
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
     * @return the IRI
     */
    @Override
    public IRI getIRI() {
        return resource;
    }

    @Override
    public IRI getResource() {
        return getIRI();
    }
    
    /**
     * @return true if resource is anonymous
     */
    @Override
    public boolean isAnonymous() {
        return false;
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
        if (!(o instanceof RDFResourceIRI)) {
            return false;
        }
        RDFResourceIRI other = (RDFResourceIRI) o;
        return resource.equals(other.getIRI());
    }

    @Override
    public int compareTo(RDFNode b) {
        if (this == b) {
           return 0; 
        }
        if (!(b instanceof RDFResourceIRI)) {
            return 1;
        }
        RDFResourceIRI other = (RDFResourceIRI) b;
        
        return resource.compareTo(other.getIRI());
    }
    
    @Override
    public String toString() {
        return resource.toQuotedString();
    }
    
}

