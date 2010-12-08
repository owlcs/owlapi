package org.semanticweb.owlapi.reasoner;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 * Indicates that a query whose signature contained fresh entities was posed to the reasoner. This exception is only thrown
 * if the fresh entity policy is set appropriately. (See {@link FreshEntityPolicy}
 * and {@link org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration}.
 */
public class FreshEntitiesException extends OWLReasonerRuntimeException {

    private Set<OWLEntity> entities;

    public FreshEntitiesException(Set<OWLEntity> entities) {
        this.entities = Collections.unmodifiableSet(new LinkedHashSet<OWLEntity>(entities));
    }

    public FreshEntitiesException(OWLEntity entity) {
        this.entities = Collections.singleton(entity);
    }


    /**
     * Gets the entities
     * @return The entities, none of which are contained in the signature of the union of a set of ontologies.
     */
    public Set<OWLEntity> getEntities() {
        return entities;
    }


    /**
     * Returns the detail message string of this throwable.
     * @return the detail message string of this <tt>Throwable</tt> instance
     *         (which may be <tt>null</tt>).
     */
    public String getMessage() {
        return entities + " not in signature";
    }
}
