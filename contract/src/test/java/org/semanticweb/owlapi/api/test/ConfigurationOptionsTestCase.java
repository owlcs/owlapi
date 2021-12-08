package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.model.MissingImportHandlingStrategy.THROW_EXCEPTION;
import static org.semanticweb.owlapi.model.MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
import static org.semanticweb.owlapi.model.PriorityCollectionSorting.ON_SET_INJECTION_ONLY;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.*;

import java.util.EnumMap;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

class ConfigurationOptionsTestCase {

    static Stream<Arguments> values() {
        return Stream.of(Arguments.of(ACCEPT_HTTP_COMPRESSION, Boolean.TRUE),
            Arguments.of(CONNECTION_TIMEOUT, Integer.valueOf(20000)),
            Arguments.of(FOLLOW_REDIRECTS, Boolean.TRUE),
            Arguments.of(INDENT_SIZE, Integer.valueOf(4)), Arguments.of(INDENTING, Boolean.TRUE),
            Arguments.of(LABELS_AS_BANNER, Boolean.FALSE),
            Arguments.of(LOAD_ANNOTATIONS, Boolean.TRUE),
            Arguments.of(PARSE_WITH_STRICT_CONFIGURATION, Boolean.FALSE),
            Arguments.of(MISSING_IMPORT_HANDLING_STRATEGY, THROW_EXCEPTION),
            Arguments.of(MISSING_ONTOLOGY_HEADER_STRATEGY, INCLUDE_GRAPH),
            Arguments.of(PRIORITY_COLLECTION_SORTING, ON_SET_INJECTION_ONLY),
            Arguments.of(REMAP_IDS, Boolean.TRUE), Arguments.of(REPORT_STACK_TRACES, Boolean.TRUE),
            Arguments.of(RETRIES_TO_ATTEMPT, Integer.valueOf(5)),
            Arguments.of(SAVE_IDS, Boolean.FALSE),
            Arguments.of(TREAT_DUBLINCORE_AS_BUILTIN, Boolean.TRUE),
            Arguments.of(USE_NAMESPACE_ENTITIES, Boolean.FALSE));
    }

    @ParameterizedTest
    @MethodSource("values")
    void shouldFindExpectedValue(ConfigurationOptions config, Object value) {
        assertEquals(value,
            config.getValue(value.getClass(), new EnumMap<>(ConfigurationOptions.class)));
        assertEquals(value, config.getDefaultValue(value.getClass()));
    }
}
