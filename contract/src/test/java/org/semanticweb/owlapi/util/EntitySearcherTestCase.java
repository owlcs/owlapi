package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.search.EntitySearcher;

class EntitySearcherTestCase extends TestBase {

    private OWLObjectProperty superProperty;
    private OWLObjectPropertyExpression inverseProperty;
    private OWLObjectProperty subProperty;
    private Set<OWLOntology> ontologies;

    @BeforeEach
    void setUp() {
        PrefixManager pm = new DefaultPrefixManager();
        pm.setDefaultPrefix("http://www.ontologies.com/ontology");
        subProperty = ObjectProperty("subProperty", pm);
        superProperty = ObjectProperty("superProperty", pm);
        inverseProperty = ObjectInverseOf(ObjectProperty("inverseProperty", pm));
        OWLOntology ontology = Ontology(m, SubObjectPropertyOf(subProperty, superProperty),
            SubObjectPropertyOf(subProperty, inverseProperty));
        ontologies = set(ontology);
    }

    @Test
    void shouldReturnSuperProperty() {
        Stream<OWLObjectPropertyExpression> superProperties =
            EntitySearcher.getSuperProperties(subProperty, ontologies.stream());
        assertTrue(superProperties.anyMatch(superProperty::equals));
    }

    @Test
    void shouldWorkWithInverse() {
        Set<OWLObjectPropertyExpression> expressions = new HashSet<>();
        EntitySearcher.getSuperProperties(subProperty, ontologies.stream())
            .forEach(expressions::add);
        assertEquals(2, expressions.size());
    }

    @Test
    void shouldReturnSubProperty() {
        Stream<OWLObjectPropertyExpression> subs =
            EntitySearcher.getSubProperties(superProperty, ontologies.stream());
        assertTrue(subs.anyMatch(subProperty::equals));
    }
}
