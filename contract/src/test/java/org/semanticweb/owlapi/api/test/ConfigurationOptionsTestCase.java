package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy;
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
        toReturn.add(new Object[] { ConfigurationOptions.CONNECTION_TIMEOUT, 20000 });
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

    @Test
    public void shouldFindExpectedValue() {
        assertEquals(value, config.getValue(value.getClass(), new EnumMap<>(ConfigurationOptions.class)));
        assertEquals(value, config.getDefaultValue(value.getClass()));
    }
}
