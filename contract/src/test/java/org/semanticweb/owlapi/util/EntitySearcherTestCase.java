package org.semanticweb.owlapi.util;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectInverseOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Ontology;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
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
        ontologies = Collections.singleton(ontology);
    }

    @Test
    void shouldReturnSuperProperty() {
        Collection<OWLProperty> supers =
            EntitySearcher.getSuperProperties((OWLProperty) subProperty, ontologies);
        assertThat(supers, hasItem(superProperty));
    }

    @Test
    void shouldWorkWithInverse() {
        Set<OWLObjectPropertyExpression> expressions = new HashSet<>();
        EntitySearcher.getSuperProperties(subProperty, ontologies).forEach(expressions::add);
        assertEquals(2, expressions.size());
    }

    @Test
    void shouldReturnSubProperty() {
        Collection<OWLProperty> subs =
            EntitySearcher.getSubProperties((OWLProperty) superProperty, ontologies);
        assertThat(subs, hasItem(subProperty));
    }
}
