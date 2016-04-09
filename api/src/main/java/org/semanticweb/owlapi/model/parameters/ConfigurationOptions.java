package org.semanticweb.owlapi.model.parameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.ByName;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy;
import org.semanticweb.owlapi.model.PriorityCollectionSorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This enum handles default values and config file or system property
 * overrides. The config file name is {@code owlapi.properties}; to enable, make
 * sure the file is in the classpath. The property names are
 * {@code "org.semanticweb.owlapi.model.parameters.ConfigurationOptions."+name()}
 * , both in the properties file and in the system properties.
 */
public enum ConfigurationOptions {
    //@formatter:off
    /** True if http compression 
     * should be used. */
    ACCEPT_HTTP_COMPRESSION             (Boolean.TRUE),
    /** Timeout for connections. */
    CONNECTION_TIMEOUT                  (Integer.valueOf(20000)),
    /** True if redirects should be 
     * followed across protocols. */
    FOLLOW_REDIRECTS                    (Boolean.TRUE),
    /** True if annotations should 
     * be loaded, false if skipped. */
    LOAD_ANNOTATIONS                    (Boolean.TRUE),
    /** Missing imports handling 
     * strategy. */
    MISSING_IMPORT_HANDLING_STRATEGY    (MissingImportHandlingStrategy.THROW_EXCEPTION),
    /** Default missing ontology 
     * strategy. */
    MISSING_ONTOLOGY_HEADER_STRATEGY    (MissingOntologyHeaderStrategy.INCLUDE_GRAPH),
    /** Flag to enable stack 
     * traces on parsing exceptions. */
    REPORT_STACK_TRACES                 (Boolean.TRUE),
    /** Number of retries to 
     * attempt when retrieving an 
     * ontology form a remote URL. 
     * Defaults to 5. */
    RETRIES_TO_ATTEMPT                  (Integer.valueOf(5)),
    /** True if strict parsing 
     * should be used. */
    PARSE_WITH_STRICT_CONFIGURATION     (Boolean.FALSE),
    /** True if Dublin Core. */
    TREAT_DUBLINCORE_AS_BUILTIN         (Boolean.TRUE),
    /** sort configuration for 
     * priority collections */
    PRIORITY_COLLECTION_SORTING         (PriorityCollectionSorting.ON_SET_INJECTION_ONLY),
    // Save options
    /** True if ids for blank 
     * nodes should always be 
     * written (axioms and 
     * anonymous individuals 
     * only). */
    SAVE_IDS                            (Boolean.FALSE),
    /** True if all anonymous 
     * individuals should have 
     * their ids remapped after 
     * parsing. */
    REMAP_IDS                           (Boolean.TRUE),
    /** True if entities should 
     * be used for namespace 
     * abbreviations. */
    USE_NAMESPACE_ENTITIES              (Boolean.FALSE),
    /** True if indenting should 
     * be used when writing out 
     * a file. */
    INDENTING                           (Boolean.TRUE),
    /** Size of indentation 
     * between levels. Only used 
     * if indenting is to true. */
    INDENT_SIZE                         (Integer.valueOf(4)),
    /** True if rdfs:label values 
     * are to be used as banners 
     * in text output. */
    LABELS_AS_BANNER                    (Boolean.FALSE),
    /** True if banners for ontology 
     * sections and entity comments 
     * should be outputted. */
    BANNERS_ENABLED                     (Boolean.TRUE),
    /** List of banned 
     * parsers keys. */
    BANNED_PARSERS                      ("");
    //@formatter:on
    private static final String PREFIX = "org.semanticweb.owlapi.model.parameters.ConfigurationOptions.";
    private Object defaultValue;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationOptions.class);
    private static final EnumMap<ConfigurationOptions, Object> owlapiProperties = loadProperties();

    ConfigurationOptions(Object o) {
        defaultValue = o;
    }

    private static EnumMap<ConfigurationOptions, Object> loadProperties() {
        EnumMap<ConfigurationOptions, Object> map = new EnumMap<>(ConfigurationOptions.class);
        Properties props = new Properties();
        try (InputStream stream = ConfigurationOptions.class.getResourceAsStream("/owlapi.properties")) {
            if (stream != null) {
                props.load(stream);
            }
        } catch (IOException e) {
            LOGGER.error("Properties cannot be loaded", e);
        }
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            ConfigurationOptions option = find(e.getKey().toString());
            if (option != null) {
                map.put(option, e.getValue());
            }
        }
        return map;
    }

    /**
     * @param parameterName
     *        parameter name - by default the full name of this enumeration plus
     *        the enum member name
     * @return ,atching ConfigurationOptions member, or null if none found
     */
    @Nullable
    public static ConfigurationOptions find(String parameterName) {
        if (!parameterName.startsWith(PREFIX)) {
            return null;
        }
        return valueOf(parameterName.substring(PREFIX.length()));
    }

    /**
     * @param value
     *        value to parse according to the enum default value
     * @param type
     *        type of the returned value
     * @return parsed value
     */
    protected <T> T parse(Object value, Class<T> type) {
        if (Boolean.class.equals(type)) {
            return type.cast(Boolean.valueOf(value.toString()));
        }
        if (Long.class.equals(type)) {
            return type.cast(Long.valueOf(value.toString()));
        }
        if (Integer.class.equals(type)) {
            return type.cast(Integer.valueOf(value.toString()));
        }
        if (defaultValue instanceof ByName) {
            return type.cast(((ByName<?>) defaultValue).byName(value.toString()));
        }
        return type.cast(value);
    }

    /**
     * @param type
     *        type for this value
     * @param overrides
     *        local overrides
     * @return value for this configuration option. Values are evaluated as
     *         follows: first, check overrides; if no overrides are present,
     *         check if a system property with the expected name is set; if not,
     *         check the config file; if no value is set in the config file, use
     *         the default defined in this enumeration.
     */
    public <T> T getValue(Class<T> type, EnumMap<ConfigurationOptions, Object> overrides) {
        Object override = overrides.get(this);
        if (override != null) {
            return parse(override, type);
        }
        // first system properties
        Object fromSystemProperties = System.getProperty(PREFIX + name());
        if (fromSystemProperties != null) {
            return parse(fromSystemProperties, type);
        }
        Object fromConfigFile = owlapiProperties.get(this);
        if (fromConfigFile != null) {
            return parse(fromConfigFile, type);
        }
        return type.cast(defaultValue);
    }

    /**
     * @param type
     *        type to cast to
     * @return default value
     */
    public <T> T getDefaultValue(Class<T> type) {
        return type.cast(defaultValue);
    }
}
