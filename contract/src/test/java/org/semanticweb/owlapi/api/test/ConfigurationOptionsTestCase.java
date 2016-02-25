package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.MissingOntologyHeaderStrategy;
import org.semanticweb.owlapi.model.PriorityCollectionSorting;
import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

@RunWith(Parameterized.class)
@SuppressWarnings("javadoc")
public class ConfigurationOptionsTestCase {

    @Parameter(0) public ConfigurationOptions config;
    @Parameter(1) public Object value;

    @Parameters(name = "{0}")
    public static List<Object[]> values() {
        List<Object[]> toReturn = new ArrayList<>();
        toReturn.add(new Object[] { ConfigurationOptions.ACCEPT_HTTP_COMPRESSION, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.CONNECTION_TIMEOUT, 20000L });
        toReturn.add(new Object[] { ConfigurationOptions.FOLLOW_REDIRECTS, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.INDENT_SIZE, 4 });
        toReturn.add(new Object[] { ConfigurationOptions.INDENTING, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.LABELS_AS_BANNER, Boolean.FALSE });
        toReturn.add(new Object[] { ConfigurationOptions.LOAD_ANNOTATIONS, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.PARSE_WITH_STRICT_CONFIGURATION, Boolean.FALSE });
        toReturn.add(new Object[] { ConfigurationOptions.MISSING_IMPORT_HANDLING_STRATEGY,
            MissingImportHandlingStrategy.THROW_EXCEPTION });
        toReturn.add(new Object[] { ConfigurationOptions.MISSING_ONTOLOGY_HEADER_STRATEGY,
            MissingOntologyHeaderStrategy.INCLUDE_GRAPH });
        toReturn.add(new Object[] { ConfigurationOptions.PRIORITY_COLLECTION_SORTING,
            PriorityCollectionSorting.ON_SET_INJECTION_ONLY });
        toReturn.add(new Object[] { ConfigurationOptions.REMAP_IDS, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.REPORT_STACK_TRACES, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.RETRIES_TO_ATTEMPT, 5 });
        toReturn.add(new Object[] { ConfigurationOptions.SAVE_IDS, Boolean.FALSE });
        toReturn.add(new Object[] { ConfigurationOptions.TREAT_DUBLINCORE_AS_BUILTIN, Boolean.TRUE });
        toReturn.add(new Object[] { ConfigurationOptions.USE_NAMESPACE_ENTITIES, Boolean.FALSE });
        return toReturn;
    }

    @BeforeClass
    public static void setUp() {
        String namespace = "org.semanticweb.owlapi.model.parameters.ConfigurationOptions.";
        System.setProperty(namespace + "ACCEPT_HTTP_COMPRESSION", "true");
        System.setProperty(namespace + "CONNECTION_TIMEOUT", "20000");
        System.setProperty(namespace + "FOLLOW_REDIRECTS", "true");
        System.setProperty(namespace + "INDENT_SIZE", "4");
        System.setProperty(namespace + "INDENTING", "true");
        System.setProperty(namespace + "LABELS_AS_BANNER", "false");
        System.setProperty(namespace + "LOAD_ANNOTATIONS", "true");
        System.setProperty(namespace + "PARSE_WITH_STRICT_CONFIGURATION", "false");
        System.setProperty(namespace + "REMAP_IDS", "true");
        System.setProperty(namespace + "REPORT_STACK_TRACES", "true");
        System.setProperty(namespace + "RETRIES_TO_ATTEMPT", "5");
        System.setProperty(namespace + "SAVE_IDS", "false");
        System.setProperty(namespace + "TREAT_DUBLINCORE_AS_BUILTIN", "true");
        System.setProperty(namespace + "USE_NAMESPACE_ENTITIES", "false");
        System.setProperty(namespace + "MISSING_IMPORT_HANDLING_STRATEGY", "THROW_EXCEPTION");
        System.setProperty(namespace + "MISSING_ONTOLOGY_HEADER_STRATEGY", "INCLUDE_GRAPH");
        System.setProperty(namespace + "PRIORITY_COLLECTION_SORTING", "ON_SET_INJECTION_ONLY");
    }

    @Test
    public void shouldFindExpectedValue() {
        assertEquals(value, config.getValue(value.getClass(), new EnumMap<>(ConfigurationOptions.class)));
    }
}
