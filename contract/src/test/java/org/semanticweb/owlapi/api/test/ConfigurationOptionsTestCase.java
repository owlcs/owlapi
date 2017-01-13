package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.model.MissingImportHandlingStrategy.THROW_EXCEPTION;
import static org.semanticweb.owlapi.model.MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
import static org.semanticweb.owlapi.model.PriorityCollectionSorting.ON_SET_INJECTION_ONLY;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

@RunWith(Parameterized.class)
@SuppressWarnings({ "javadoc", "null" })
public class ConfigurationOptionsTestCase {

    @Parameter(0) public ConfigurationOptions config;
    @Parameter(1) public Object value;

    @Parameters(name = "{0}")
    public static List<Object[]> values() {
        List<Object[]> toReturn = new ArrayList<>();
        toReturn.add(new Object[] { ACCEPT_HTTP_COMPRESSION, Boolean.TRUE });
        toReturn.add(new Object[] { CONNECTION_TIMEOUT, Integer.valueOf(20000) });
        toReturn.add(new Object[] { FOLLOW_REDIRECTS, Boolean.TRUE });
        toReturn.add(new Object[] { INDENT_SIZE, Integer.valueOf(4) });
        toReturn.add(new Object[] { INDENTING, Boolean.TRUE });
        toReturn.add(new Object[] { LABELS_AS_BANNER, Boolean.FALSE });
        toReturn.add(new Object[] { LOAD_ANNOTATIONS, Boolean.TRUE });
        toReturn.add(new Object[] { PARSE_WITH_STRICT_CONFIGURATION, Boolean.FALSE });
        toReturn.add(new Object[] { MISSING_IMPORT_HANDLING_STRATEGY, THROW_EXCEPTION });
        toReturn.add(new Object[] { MISSING_ONTOLOGY_HEADER_STRATEGY, INCLUDE_GRAPH });
        toReturn.add(new Object[] { PRIORITY_COLLECTION_SORTING, ON_SET_INJECTION_ONLY });
        toReturn.add(new Object[] { REMAP_IDS, Boolean.TRUE });
        toReturn.add(new Object[] { REPORT_STACK_TRACES, Boolean.TRUE });
        toReturn.add(new Object[] { RETRIES_TO_ATTEMPT, Integer.valueOf(5) });
        toReturn.add(new Object[] { SAVE_IDS, Boolean.FALSE });
        toReturn.add(new Object[] { TREAT_DUBLINCORE_AS_BUILTIN, Boolean.TRUE });
        toReturn.add(new Object[] { USE_NAMESPACE_ENTITIES, Boolean.FALSE });
        return toReturn;
    }

    @Test
    public void shouldFindExpectedValue() {
        assertEquals(value, config.getValue(value.getClass(), new EnumMap<>(ConfigurationOptions.class)));
        assertEquals(value, config.getDefaultValue(value.getClass()));
    }
}
