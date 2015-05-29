package org.semanticweb.owlapi.api.test;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.search.Searcher;

/**
 * This is a test of the property type guessing for rdf:Property instances of
 * the kind encountered when attempting to parse an rdfs schema.
 * <p/>
 * The CIDOC schema is a good test case, because it every property has a domain
 * and a range specified; some properties have a range of Literal (and hence are
 * data properties), and there are subclass relations specified for both data
 * and object properties.
 * <p/>
 * There should be no Annotation Properties.
 * <p/>
 * See <a href="http://www.cidoc-crm.org/">The CIDOC Web Site</a> for more
 * details.
 */
@SuppressWarnings({ "javadoc", "null" })
public class GuessRDFSPropertyTypeTestCase {

    private static final @Nonnull String CIDOC_FILE = "/cidoc_crm_v5.0.4_official_release.rdfs.xml";
    private static final @Nonnull String CIDOC_PREFIX = "http://www.cidoc-crm.org/cidoc-crm/";
    private @Nonnull OWLOntology cidocOntology;
    private @Nonnull PrefixDocumentFormat prefixOWLOntologyFormat;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration().setStrict(false);
        @SuppressWarnings("resource")
        InputStream in = getClass().getResourceAsStream(CIDOC_FILE);
        assertNotNull("can't find CIDOC_FILE", in);
        cidocOntology = manager.loadOntologyFromOntologyDocument(new StreamDocumentSource(in), config);
        assertNotNull(cidocOntology);
        OWLDocumentFormat format = manager.getOntologyFormat(cidocOntology);
        assertNotNull(format);
        assertTrue(format.isPrefixOWLOntologyFormat());
        prefixOWLOntologyFormat = format.asPrefixOWLOntologyFormat();
        prefixOWLOntologyFormat.setDefaultPrefix(CIDOC_PREFIX);
    }

    @Test
    public void testObjectProperty() {
        testProperty("P11_had_participant", "E5_Event", "E39_Actor", "P12_occurred_in_the_presence_of");
    }

    @Test
    public void testDataProperty() {
        testProperty("P79_beginning_is_qualified_by", "E52_Time-Span", "http://www.w3.org/2000/01/rdf-schema#Literal",
                "P3_has_note");
    }

    public void testProperty(String propertyName, String expectedDomain, String expectedRange,
            String expectedSuperProperty) {
        IRI p11IRI = prefixOWLOntologyFormat.getIRI(propertyName);
        Set<OWLEntity> hadParticipant = asSet(cidocOntology.entitiesInSignature(p11IRI));
        assertEquals("should have found " + propertyName, 1, hadParticipant.size());
        OWLEntity entity = hadParticipant.iterator().next();
        assertTrue("EntityType", OWLProperty.class.isAssignableFrom(entity.getClass()));
        if (entity.isOWLObjectProperty()) {
            testProperty(entity.asOWLObjectProperty(), expectedDomain, expectedRange, expectedSuperProperty);
        }
        if (entity.isOWLDataProperty()) {
            testProperty(entity.asOWLDataProperty(), expectedDomain, expectedRange, expectedSuperProperty);
        }
    }

    private void testProperty(OWLObjectProperty p11property, String expectedDomain, String expectedRange,
            String expectedSuperProperty) {
        Stream<OWLClassExpression> rangeStream = Searcher.range(cidocOntology.objectPropertyRangeAxioms(p11property));
        Collection<OWLClassExpression> ranges = asSet(rangeStream);
        assertEquals("should have 1 range", 1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLOntologyFormat.getIRI(expectedRange);
        }
        assertEquals("range", expectedIRI, rangeIRI);
        Stream<OWLClassExpression> domainStream = Searcher
                .domain(cidocOntology.objectPropertyDomainAxioms(p11property));
        Collection<OWLClassExpression> domains = asSet(domainStream);
        assertEquals("should have 1 domain", 1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals("domain should be E5_Event", prefixOWLOntologyFormat.getIRI(expectedDomain), domainIRI);
        Stream<OWLObjectPropertyExpression> superStream = Searcher
                .sup(cidocOntology.axioms(Filters.subObjectPropertyWithSub, p11property, INCLUDED));
        Collection<OWLObjectPropertyExpression> superProperties = asSet(superStream);
        // Set<OWLPropertyExpression> superProperties =
        // p11_property.getSuperProperties(cidocOntology);
        assertEquals("should have 1 super Property", 1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals("super property ", prefixOWLOntologyFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    private void testProperty(OWLDataProperty p11property, String expectedDomain, String expectedRange,
            String expectedSuperProperty) {
        Stream<OWLClassExpression> rangeClasses = Searcher.range(cidocOntology.dataPropertyRangeAxioms(p11property));
        Collection<OWLClassExpression> ranges = asSet(rangeClasses);
        assertEquals("should have 1 range", 1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLOntologyFormat.getIRI(expectedRange);
        }
        assertEquals("range", expectedIRI, rangeIRI);
        Stream<OWLClassExpression> domainStream = Searcher.domain(cidocOntology.dataPropertyDomainAxioms(p11property));
        Collection<OWLClassExpression> domains = asSet(domainStream);
        // p11_property .getDomains(cidocOntology);
        assertEquals("should have 1 domain", 1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals("domain should be E5_Event", prefixOWLOntologyFormat.getIRI(expectedDomain), domainIRI);
        Stream<OWLObjectPropertyExpression> supStream = Searcher
                .sup(cidocOntology.axioms(Filters.subDataPropertyWithSub, p11property, INCLUDED));
        Collection<OWLObjectPropertyExpression> superProperties = supStream.collect(toSet());
        // Set<OWLPropertyExpression> superProperties =
        // p11_property.getSuperProperties(cidocOntology);
        assertEquals("should have 1 super Property", 1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals("super property ", prefixOWLOntologyFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    @Test
    public void testObjectPropertyAndDataPropertySetsNonTriviallyDisjoint() {
        Set<OWLObjectProperty> objectProperties = asSet(cidocOntology.objectPropertiesInSignature());
        Set<OWLDataProperty> dataProperties = asSet(cidocOntology.dataPropertiesInSignature());
        assertFalse("should have some object Properties", objectProperties.isEmpty());
        assertFalse("should have some data Properties", dataProperties.isEmpty());
        assertTrue("object properties and data properties should be disjoint",
                Collections.disjoint(objectProperties, dataProperties));
    }

    @Test
    public void testAnnotationPropertyCount() {
        assertEquals("should only have 2 rdfs annotation properties", 2,
                cidocOntology.annotationPropertiesInSignature(INCLUDED).count());
    }
}
