package org.semanticweb.owlapi.util;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.search.EntitySearcher;

@SuppressWarnings("javadoc")
public class EntitySearcherTestCase extends TestBase {

    private OWLObjectProperty superProperty;
    private OWLObjectProperty subProperty;
    private Set<OWLOntology> ontologies;

    @Before
    public void setUp() {
        PrefixManager pm = new DefaultPrefixManager();
        pm.setDefaultPrefix("http://www.ontologies.com/ontology");
        subProperty = ObjectProperty("subProperty", pm);
        superProperty = ObjectProperty("superProperty", pm);
        OWLOntology ontology = Ontology(m, SubObjectPropertyOf(subProperty, superProperty));
        ontologies = Collections.singleton(ontology);
    }

    @Test
    public void shouldReturnSuperProperty() {
        OWLProperty prop = subProperty;
        Collection<OWLProperty> supers = EntitySearcher.getSuperProperties(prop, ontologies);
        assertThat(supers, hasItem(superProperty));
    }

    @Test
    public void shouldReturnSubProperty() {
        OWLProperty prop = superProperty;
        Collection<OWLProperty> subs = EntitySearcher.getSubProperties(prop, ontologies);
        assertThat(subs, hasItem(subProperty));
    }
}
