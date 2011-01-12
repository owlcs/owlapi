package org.semanticweb.owlapi.reasoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private List<OWLEntity> entities;

    public FreshEntitiesException(Set<OWLEntity> entities) {
        this.entities = Collections.unmodifiableList(new ArrayList<OWLEntity>(entities));
    }

    public FreshEntitiesException(OWLEntity entity) {
        this.entities = Collections.singletonList(entity);
    }


    /**
     * Gets the entities
     * @return The entities, none of which are contained in the signature of the union of a set of ontologies.
     */
    public List<OWLEntity> getEntities() {
        return entities;
    }


    /**
     * Returns the detail message string of this throwable.
     * @return the detail message string of this <tt>Throwable</tt> instance
     *         (which may be <tt>null</tt>).
     */
    @Override
	public String getMessage() {
        return entities + " not in signature";
    }
}
