package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 * <p/>
 * A very simple short form provider which is intended to provide
 * human readable display names for entities.  The following strategy
 * is used:
 * 1) If the entity URI has a fragment then that fragment
 * is returned e.g.  http://an.other.com#A would have a short form of
 * "A".
 * 2) If the entity URI does not have a fragment then the last
 * segment of the URI path is used e.g.  http://an.other.com/A/B would
 * have a short form of "B".
 * 3) If the entity URI does not have a path
 * then the full URI is returned as a string.
 */
public class SimpleShortFormProvider implements ShortFormProvider {

    private SimpleIRIShortFormProvider uriShortFormProvider = new SimpleIRIShortFormProvider();

    public String getShortForm(OWLEntity entity) {
        return uriShortFormProvider.getShortForm(entity.getIRI());
    }


    public void dispose() {
        // Nothing to do here
    }
}
