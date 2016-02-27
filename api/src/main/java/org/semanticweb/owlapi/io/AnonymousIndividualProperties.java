package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.*;

import java.util.EnumMap;

import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

/**
 * Settings for anonymous individual treatment. Note: these cannot be specified
 * for a specific ontology or manager at this point, and so they are system
 * wide.
 */
public class AnonymousIndividualProperties {

    /** Local override map. */
    private static EnumMap<ConfigurationOptions, Object> overrides = new EnumMap<>(ConfigurationOptions.class);

    /** Ensure the config is back to default values. */
    public static void resetToDefault() {
        overrides.clear();
    }

    /**
     * @return true if all anonymous individuals should have their ids persisted
     */
    public static boolean shouldSaveIdsForAllAnonymousIndividuals() {
        return SAVE_IDS.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @return true if all anonymous individuals should have their ids remapped
     *         upon reading
     */
    public static boolean shouldRemapAllAnonymousIndividualsIds() {
        return REMAP_IDS.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param b
     *        true if all anonymous individuals should have their ids persisted
     */
    public static void setSaveIdsForAllAnonymousIndividuals(boolean b) {
        overrides.put(SAVE_IDS, b);
    }

    /**
     * @param b
     *        true if all anonymous individuals should have their ids remapped
     *        after parsing
     */
    public static void setRemapAllAnonymousIndividualsIds(boolean b) {
        overrides.put(REMAP_IDS, b);
    }
}
