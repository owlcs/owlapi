package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
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
public class GuessRDFSPropertyTypeTestCase extends TestBase {

    @Nonnull private static final String CIDOC_FILE = "/cidoc_crm_v5.0.4_official_release.rdfs.xml";
    @Nonnull private static final String CIDOC_PREFIX = "http://www.cidoc-crm.org/cidoc-crm/";
    @Nonnull private OWLOntology cidocOntology;
    @Nonnull private PrefixDocumentFormat prefixOWLOntologyFormat;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration().setStrict(false);
        @SuppressWarnings("resource") InputStream in = getClass().getResourceAsStream(CIDOC_FILE);
        assertNotNull("can't find CIDOC_FILE", in);
        cidocOntology = m.loadOntologyFromOntologyDocument(new StreamDocumentSource(in), config);
        assertNotNull(cidocOntology);
        OWLDocumentFormat format = m.getOntologyFormat(cidocOntology);
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

    public void testProperty(@Nonnull String propertyName, @Nonnull String expectedDomain,
        @Nonnull String expectedRange, @Nonnull String expectedSuperProperty) {
        IRI p11IRI = prefixOWLOntologyFormat.getIRI(propertyName);
        Set<OWLEntity> hadParticipant = cidocOntology.getEntitiesInSignature(p11IRI);
        assertEquals("should have found " + propertyName, 1, hadParticipant.size());
        OWLEntity entity = hadParticipant.iterator().next();
        assertTrue("EntityType", OWLProperty.class.isAssignableFrom(entity.getClass()));
        if (entity instanceof OWLObjectProperty) {
            testProperty((OWLObjectProperty) entity, expectedDomain, expectedRange, expectedSuperProperty);
        }
        if (entity instanceof OWLDataProperty) {
            testProperty((OWLDataProperty) entity, expectedDomain, expectedRange, expectedSuperProperty);
        }
    }

    private void testProperty(@Nonnull OWLObjectProperty p11property, @Nonnull String expectedDomain,
        @Nonnull String expectedRange, @Nonnull String expectedSuperProperty) {
        Collection<OWLClassExpression> ranges = Searcher.range(cidocOntology.getObjectPropertyRangeAxioms(p11property));
        assertEquals("should have 1 range", 1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLOntologyFormat.getIRI(expectedRange);
        }
        assertEquals("range", expectedIRI, rangeIRI);
        Collection<OWLClassExpression> domains = Searcher.domain(cidocOntology.getObjectPropertyDomainAxioms(
            p11property));
        // p11_property .getDomains(cidocOntology);
        assertEquals("should have 1 domain", 1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals("domain should be E5_Event", prefixOWLOntologyFormat.getIRI(expectedDomain), domainIRI);
        Collection<OWLObjectPropertyExpression> superProperties = Searcher.sup(cidocOntology.filterAxioms(
            Filters.subObjectPropertyWithSub, p11property, INCLUDED));
        // Set<OWLPropertyExpression> superProperties =
        // p11_property.getSuperProperties(cidocOntology);
        assertEquals("should have 1 super Property", 1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals("super property ", prefixOWLOntologyFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    private void testProperty(@Nonnull OWLDataProperty p11property, @Nonnull String expectedDomain,
        @Nonnull String expectedRange, @Nonnull String expectedSuperProperty) {
        Collection<OWLClassExpression> ranges = Searcher.range(cidocOntology.getDataPropertyRangeAxioms(p11property));
        assertEquals("should have 1 range", 1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLOntologyFormat.getIRI(expectedRange);
        }
        assertEquals("range", expectedIRI, rangeIRI);
        Collection<OWLClassExpression> domains = Searcher.domain(cidocOntology.getDataPropertyDomainAxioms(
            p11property));
        // p11_property .getDomains(cidocOntology);
        assertEquals("should have 1 domain", 1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals("domain should be E5_Event", prefixOWLOntologyFormat.getIRI(expectedDomain), domainIRI);
        Collection<OWLObjectPropertyExpression> superProperties = Searcher.sup(cidocOntology.filterAxioms(
            Filters.subDataPropertyWithSub, p11property, INCLUDED));
        // Set<OWLPropertyExpression> superProperties =
        // p11_property.getSuperProperties(cidocOntology);
        assertEquals("should have 1 super Property", 1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals("super property ", prefixOWLOntologyFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    @Test
    public void testObjectPropertyAndDataPropertySetsNonTriviallyDisjoint() {
        Set<OWLObjectProperty> objectProperties = cidocOntology.getObjectPropertiesInSignature();
        Set<OWLDataProperty> dataProperties = cidocOntology.getDataPropertiesInSignature();
        assertFalse("should have some object Properties", objectProperties.isEmpty());
        assertFalse("should have some data Properties", dataProperties.isEmpty());
        assertTrue("object properties and data properties should be disjoint", Collections.disjoint(objectProperties,
            dataProperties));
    }

    @Test
    public void testAnnotationPropertyCount() {
        Set<OWLAnnotationProperty> annotationProperties = cidocOntology.getAnnotationPropertiesInSignature(INCLUDED);
        assertEquals("should only have 2 rdfs annotation properties", 2, annotationProperties.size());
    }
}
