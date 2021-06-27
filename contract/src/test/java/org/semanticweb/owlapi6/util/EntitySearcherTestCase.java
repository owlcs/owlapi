package org.semanticweb.owlapi6.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectInverseOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Ontology;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.search.Searcher;

class EntitySearcherTestCase extends TestBase {

    private static final String NS = "http://www.ontologies.com/ontology#";
    private OWLObjectProperty superProperty;
    private OWLObjectProperty subProperty;
    private OWLObjectInverseOf inverseProperty;
    private Set<OWLOntology> ontologies;

    @BeforeEach
    void setUp() {

        subProperty = ObjectProperty(df.getIRI(NS, "subProperty"));
        superProperty = ObjectProperty(df.getIRI(NS, "superProperty"));
        inverseProperty = ObjectInverseOf(ObjectProperty(df.getIRI(NS, "inverseProperty")));
        OWLOntology ontology = Ontology(m, SubObjectPropertyOf(subProperty, superProperty),
            SubObjectPropertyOf(subProperty, inverseProperty));
        ontologies = Collections.singleton(ontology);
    }

    @Test
    void shouldReturnSuperProperty() {
        List<OWLObjectPropertyExpression> supers =
            asList(Searcher.getSuperProperties(subProperty, ontologies.stream()));
        assertTrue(supers.contains(superProperty), supers.toString());
    }

    @Test
    void shouldWorkWithInverse() {
        Set<OWLObjectPropertyExpression> expressions = new HashSet<>();
        Searcher.getSuperProperties(subProperty, ontologies.stream()).forEach(expressions::add);
        assertEquals(2, expressions.size());
    }

    @Test
    void shouldReturnSubProperty() {
        Stream<OWLObjectPropertyExpression> subs =
            Searcher.getSubProperties(superProperty, ontologies.stream());
        assertTrue(contains(subs, subProperty));
    }
}
