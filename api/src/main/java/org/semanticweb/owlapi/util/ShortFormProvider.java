package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 * A short form provider produces renderings of entities.  These
 * renderings are strings which in general can be used for display
 * and serialisation purposes.  A given entity only has one short
 * form for a given short form provider.  However, a short form may
 * map to multiple enntities for a given short form provider.  In
 * other words, for a given short form provider the mapping from
 * entity to short form is functional, but is not inverse functional i.e.
 * an injective mapping.
 */
public interface ShortFormProvider {

    /**
     * Gets the short form for the specified entity.
     * @param entity The entity.
     * @return A string which represents a short rendering
     *         of the speicified entity.
     */
    String getShortForm(OWLEntity entity);


    /**
     * Disposes of the short form proivider.  This frees any
     * resources and clears any caches.
     */
    void dispose();
}
