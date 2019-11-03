package org.semanticweb.owlapi6.util;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Ontology;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLProperty;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.search.Searcher;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;

public class EntitySearcherTestCase extends TestBase {

    private OWLObjectProperty superProperty;
    private OWLObjectProperty subProperty;
    private Set<OWLOntology> ontologies;

    @Before
    public void setUp() {
        PrefixManager pm =
            new PrefixManagerImpl().withDefaultPrefix("http://www.ontologies.com/ontology");
        subProperty = ObjectProperty("subProperty", pm);
        superProperty = ObjectProperty("superProperty", pm);
        OWLOntology ontology = Ontology(m, SubObjectPropertyOf(subProperty, superProperty));
        ontologies = Collections.singleton(ontology);
    }

    @Test
    public void shouldReturnSuperProperty() {
        List<OWLProperty> supers =
            asList(Searcher.getSuperProperties(subProperty, ontologies.stream()));
        assertTrue(supers.toString(), supers.contains(superProperty));
    }

    @Test
    public void shouldReturnSubProperty() {
        Stream<OWLProperty> subs = Searcher.getSubProperties(superProperty, ontologies.stream());
        assertTrue(contains(subs, subProperty));
    }
}
