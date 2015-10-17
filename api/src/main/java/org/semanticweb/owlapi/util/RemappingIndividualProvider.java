package org.semanticweb.owlapi.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.providers.AnonymousIndividualByIdProvider;

/**
 * A provider for anonymous individuals that remaps input ids consistently
 * across all requests. This class obeys the preferences set in
 * {@link AnonymousIndividualProperties} at the time the instance is created;
 * changing the property while the instance is in use will not affect the
 * instance.
 */
public class RemappingIndividualProvider implements AnonymousIndividualByIdProvider {

    private OWLDataFactory df;
    private boolean remapEnabled;
    private Map<String, OWLAnonymousIndividual> map;

    /**
     * @param df
     *        data factory
     */
    public RemappingIndividualProvider(OWLDataFactory df) {
        this.df = df;
        remapEnabled = AnonymousIndividualProperties.shouldRemapAllAnonymousIndividualsIds();
        if (remapEnabled) {
            map = new HashMap<>();
        } else {
            map = Collections.emptyMap();
        }
    }

    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        if (!remapEnabled) {
            return df.getOWLAnonymousIndividual(nodeId);
        }
        OWLAnonymousIndividual toReturn = map.get(nodeId);
        if (toReturn == null) {
            toReturn = df.getOWLAnonymousIndividual();
            map.put(nodeId, toReturn);
        }
        return toReturn;
    }
}
