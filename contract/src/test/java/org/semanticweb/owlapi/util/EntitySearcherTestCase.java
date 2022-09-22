package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.contains;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.Searcher;

class EntitySearcherTestCase extends TestBase {

    private Set<OWLOntology> ontologies;

    @BeforeEach
    void setUp() {
        OWLOntology ontology =
            Ontology(m, SubObjectPropertyOf(OBJPROPS.subProperty, OBJPROPS.superProperty),
                SubObjectPropertyOf(OBJPROPS.subProperty,
                    ObjectInverseOf(ObjectProperty(ONTOLOGIES_COM, "inverseProperty"))));
        ontologies = set(ontology);
    }

    @Test
    void shouldReturnSuperProperty() {
        List<OWLObjectPropertyExpression> supers =
            asList(Searcher.getSuperProperties(OBJPROPS.subProperty, ontologies.stream()));
        assertTrue(supers.contains(OBJPROPS.superProperty), supers.toString());
    }

    @Test
    void shouldWorkWithInverse() {
        Set<OWLObjectPropertyExpression> expressions = new HashSet<>();
        Searcher.getSuperProperties(OBJPROPS.subProperty, ontologies.stream())
            .forEach(expressions::add);
        assertEquals(2, expressions.size());
    }

    @Test
    void shouldReturnSubProperty() {
        Stream<OWLObjectPropertyExpression> subs =
            Searcher.getSubProperties(OBJPROPS.superProperty, ontologies.stream());
        assertTrue(contains(subs, OBJPROPS.subProperty));
    }
}
