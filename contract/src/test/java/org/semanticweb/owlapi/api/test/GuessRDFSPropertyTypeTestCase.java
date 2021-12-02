package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.search.Searcher;

/**
 * This is a test of the property type guessing for rdf:Property instances of the kind encountered
 * when attempting to parse an RDFS schema.
 * <p/>
 * The CIDOC schema is a good test case, because it every property has a domain and a range
 * specified; some properties have a range of Literal (and hence are data properties), and there are
 * subclass relations specified for both data and object properties.
 * <p/>
 * There should be no Annotation Properties.
 * <p/>
 * See <a href="http://www.cidoc-crm.org/">The CIDOC Web Site</a> for more details.
 */
class GuessRDFSPropertyTypeTestCase extends TestBase {

    @Nonnull
    private static final String CIDOC_FILE = "/cidoc_crm_v5.0.4_official_release.rdfs.xml";
    @Nonnull
    private static final String CIDOC_PREFIX = "http://www.cidoc-crm.org/cidoc-crm/";
    @Nonnull
    private OWLOntology cidocOntology;
    @Nonnull
    private PrefixDocumentFormat prefixOWLDocumentFormat;

    @BeforeEach
    void setUp() {
        @SuppressWarnings("resource")
        InputStream in = getClass().getResourceAsStream(CIDOC_FILE);
        assertNotNull(in);
        cidocOntology = loadOntologyFromSource(new StreamDocumentSource(in),
            new OWLOntologyLoaderConfiguration().setStrict(false));
        assertNotNull(cidocOntology);
        OWLDocumentFormat format =
            cidocOntology.getOWLOntologyManager().getOntologyFormat(cidocOntology);
        assertNotNull(format);
        assertTrue(format.isPrefixOWLOntologyFormat());
        prefixOWLDocumentFormat = format.asPrefixOWLOntologyFormat();
        prefixOWLDocumentFormat.setDefaultPrefix(CIDOC_PREFIX);
    }

    @Test
    void testObjectProperty() {
        testProperty("P11_had_participant", "E5_Event", "E39_Actor",
            "P12_occurred_in_the_presence_of");
    }

    @Test
    void testDataProperty() {
        testProperty("P79_beginning_is_qualified_by", "E52_Time-Span",
            "http://www.w3.org/2000/01/rdf-schema#Literal", "P3_has_note");
    }

    void testProperty(@Nonnull String propertyName, @Nonnull String expectedDomain,
        @Nonnull String expectedRange, @Nonnull String expectedSuperProperty) {
        IRI p11IRI = prefixOWLDocumentFormat.getIRI(propertyName);
        Set<OWLEntity> hadParticipant = cidocOntology.getEntitiesInSignature(p11IRI);
        assertEquals(1, hadParticipant.size(), "should have found " + propertyName);
        OWLEntity entity = hadParticipant.iterator().next();
        assertTrue(OWLProperty.class.isAssignableFrom(entity.getClass()));
        if (entity instanceof OWLObjectProperty) {
            testProperty((OWLObjectProperty) entity, expectedDomain, expectedRange,
                expectedSuperProperty);
        }
        if (entity instanceof OWLDataProperty) {
            testProperty((OWLDataProperty) entity, expectedDomain, expectedRange,
                expectedSuperProperty);
        }
    }

    private void testProperty(@Nonnull OWLObjectProperty p11property,
        @Nonnull String expectedDomain, @Nonnull String expectedRange,
        @Nonnull String expectedSuperProperty) {
        Collection<OWLClassExpression> ranges =
            Searcher.range(cidocOntology.getObjectPropertyRangeAxioms(p11property));
        assertEquals(1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLDocumentFormat.getIRI(expectedRange);
        }
        assertEquals(expectedIRI, rangeIRI);
        Collection<OWLClassExpression> domains =
            Searcher.domain(cidocOntology.getObjectPropertyDomainAxioms(p11property));
        assertEquals(1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedDomain), domainIRI);
        Collection<OWLObjectPropertyExpression> superProperties = Searcher.sup(
            cidocOntology.filterAxioms(Filters.subObjectPropertyWithSub, p11property, INCLUDED));
        assertEquals(1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    private void testProperty(@Nonnull OWLDataProperty p11property, @Nonnull String expectedDomain,
        @Nonnull String expectedRange, @Nonnull String expectedSuperProperty) {
        Collection<OWLClassExpression> ranges =
            Searcher.range(cidocOntology.getDataPropertyRangeAxioms(p11property));
        assertEquals(1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLDocumentFormat.getIRI(expectedRange);
        }
        assertEquals(expectedIRI, rangeIRI);
        Collection<OWLClassExpression> domains =
            Searcher.domain(cidocOntology.getDataPropertyDomainAxioms(p11property));
        assertEquals(1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedDomain), domainIRI);
        Collection<OWLObjectPropertyExpression> superProperties = Searcher
            .sup(cidocOntology.filterAxioms(Filters.subDataPropertyWithSub, p11property, INCLUDED));
        assertEquals(1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    @Test
    void testObjectPropertyAndDataPropertySetsNonTriviallyDisjoint() {
        Set<OWLObjectProperty> objectProperties = cidocOntology.getObjectPropertiesInSignature();
        Set<OWLDataProperty> dataProperties = cidocOntology.getDataPropertiesInSignature();
        assertFalse(objectProperties.isEmpty());
        assertFalse(dataProperties.isEmpty());
        assertTrue(Collections.disjoint(objectProperties, dataProperties));
    }

    @Test
    void testAnnotationPropertyCount() {
        Set<OWLAnnotationProperty> annotationProperties =
            cidocOntology.getAnnotationPropertiesInSignature(INCLUDED);
        assertEquals(2, annotationProperties.size());
    }
}
