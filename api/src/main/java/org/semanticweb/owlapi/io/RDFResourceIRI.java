package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;

/** IRI node implementation */
public class RDFResourceIRI extends RDFResource {
    private final IRI resource;

    /** @param resource
     *            the resource */
    public RDFResourceIRI(IRI resource) {
        this.resource = resource;
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public IRI getIRI() {
        return resource;
    }

    @Override
    public IRI getResource() {
        return resource;
    }

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
        if (o == null || !(o instanceof RDFResourceIRI)) {
            return false;
        }
        RDFResourceIRI other = (RDFResourceIRI) o;
        return resource.equals(other.resource);
    }

    @Override
    public String toString() {
        return resource.toQuotedString();
    }
}
