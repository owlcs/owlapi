package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * @since 3.2
 */
public class RDFResource extends RDFNode {

    private IRI resource;

    private boolean anonymous;

    public RDFResource(IRI resource, boolean anonymous) {
        this.resource = resource;
        this.anonymous = anonymous;
    }

    public IRI getResource() {
        return resource;
    }

    public boolean isAnonymous() {
        return anonymous;
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
        if (!(o instanceof RDFResource)) {
            return false;
        }
        RDFResource other = (RDFResource) o;
        return resource.equals(other.resource) && anonymous == other.anonymous;
    }
}
