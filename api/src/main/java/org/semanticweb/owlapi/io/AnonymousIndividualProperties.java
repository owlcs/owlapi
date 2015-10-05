package org.semanticweb.owlapi.io;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Settings for anonymous individual treatment. Note: these cannot be specified
 * for a specific ontology or manager at this point, and so they are system
 * wide.
 */
public class AnonymousIndividualProperties {

    private static AtomicBoolean saveIds = new AtomicBoolean(false);
    private static AtomicBoolean remapIds = new AtomicBoolean(true);

    /** Ensure the config is back to default values. */
    public static void resetToDefault() {
        saveIds.set(false);
        remapIds.set(true);
    }

    /**
     * @return true if all anonymous individuals should have their ids persisted
     */
    public static boolean shouldSaveIdsForAllAnonymousIndividuals() {
        return saveIds.get();
    }

    /**
     * @return true if all anonymous individuals should have their ids remapped
     *         upon reading
     */
    public static boolean shouldRemapAllAnonymousIndividualsIds() {
        return remapIds.get();
    }

    /**
     * @param b
     *        true if all anonymous individuals should have their ids persisted
     */
    public static void setSaveIdsForAllAnonymousIndividuals(boolean b) {
        saveIds.set(b);
    }

    /**
     * @param b
     *        true if all anonymous individuals should have their ids remapped
     *        after parsing
     */
    public static void setRemapAllAnonymousIndividualsIds(boolean b) {
        remapIds.set(b);
    }
}
