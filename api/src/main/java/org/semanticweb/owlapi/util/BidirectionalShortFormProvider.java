package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 *
 * A short form provider which is capable of translating back and forth
 * between entities and their short forms.
 */
public interface BidirectionalShortFormProvider extends ShortFormProvider {

    /**
     * For a given short form this method obtains the entities
     * which have this short form.
     * @param shortForm The short form of the entities that will
     * be retrieved.
     * @return The set of entities that have the specified short
     * form.  If there are no entities which have the specified
     * short form then an empty set will be returned.
     */
    Set<OWLEntity> getEntities(String shortForm);

    /**
     * A convenience method which gets an entity from its short form.
     * @param shortForm The short form of the entity.
     * @return The actual entity or <code>null</code> if there
     * is no entity which has the specified short form.  If the specified
     * short form corresponds to more than one entity then an entity will
     * be chosen by the implementation of the short form provider.
     */
    OWLEntity getEntity(String shortForm);


    /**
     * Gets all of the short forms that are mapped to entities.
     * @return A set, which contains the strings representing
     * the short forms of entities for which there is a mapping.
     */
    Set<String> getShortForms();
}
