package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 * <p/>
 * Provides a set of entities to inputs that require this.
 */
public interface OWLEntitySetProvider<E extends OWLEntity> {

    /**
     * Gets the entities that are provided by this provider.
     */
    Set<E> getEntities();
}
