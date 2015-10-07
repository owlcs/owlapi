package org.semanticweb.owlapi.io;

/**
 * Settings for anonymous individual treatment. Note: these cannot be specified
 * for a specific ontology or manager at this point, and so they are system
 * wide.
 */
public class AnonymousIndividualProperties {

    private static boolean saveIds = false;
    private static boolean remapIds = true;

    /** Ensure the config is back to default values. */
    public static void resetToDefault() {
        saveIds = false;
        remapIds = true;
    }

    /**
     * @return true if all anonymous individuals should have their ids persisted
     */
    public static boolean shouldSaveIdsForAllAnonymousIndividuals() {
        return saveIds;
    }

    /**
     * @return true if all anonymous individuals should have their ids remapped
     *         upon reading
     */
    public static boolean shouldRemapAllAnonymousIndividualsIds() {
        return remapIds;
    }

    /**
     * @param b
     *        true if all anonymous individuals should have their ids persisted
     */
    public static void setSaveIdsForAllAnonymousIndividuals(boolean b) {
        saveIds = b;
    }

    /**
     * @param b
     *        true if all anonymous individuals should have their ids remapped
     *        after parsing
     */
    public static void setRemapAllAnonymousIndividualsIds(boolean b) {
        remapIds = b;
    }
}
