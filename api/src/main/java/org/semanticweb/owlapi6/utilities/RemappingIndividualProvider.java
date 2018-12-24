package org.semanticweb.owlapi6.utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.model.providers.AnonymousIndividualByIdProvider;

/**
 * A provider for anonymous individuals that remaps input ids consistently across all requests. This
 * class obeys the preferences set in {@link OWLOntologyManager#getOntologyConfigurator()}.
 */
public class RemappingIndividualProvider implements AnonymousIndividualByIdProvider {

    private OWLDataFactory df;
    private OntologyConfigurator cf;
    private Map<String, OWLAnonymousIndividual> map;

    /**
     * @param m ontology configurator
     * @param df data factory
     */
    public RemappingIndividualProvider(OntologyConfigurator m, OWLDataFactory df) {
        this.df = df;
        cf = m;
        if (cf.shouldRemapIds()) {
            map = new HashMap<>();
        } else {
            map = Collections.emptyMap();
        }
    }

    @Override
    public OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId) {
        if (!cf.shouldRemapIds()) {
            return df.getOWLAnonymousIndividual(nodeId);
        }
        return map.computeIfAbsent(nodeId, x -> df.getOWLAnonymousIndividual());
    }
}
