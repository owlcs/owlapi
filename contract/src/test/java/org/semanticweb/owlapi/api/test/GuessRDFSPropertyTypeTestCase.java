package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
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

    private static final String CIDOC_FILE = "cidoc_crm_v5.0.4_official_release.rdfs.xml";
    private static final String CIDOC_PREFIX = "http://www.cidoc-crm.org/cidoc-crm/";
    private OWLOntology cidocOntology;
    private PrefixDocumentFormat prefixOWLDocumentFormat;

    @BeforeEach
    void setUp() {
        cidocOntology = ontologyFromClasspathFile(CIDOC_FILE, config);
        assertNotNull(cidocOntology);
        OWLDocumentFormat format = cidocOntology.getFormat();
        assertNotNull(format);
        assertTrue(format.isPrefixOWLDocumentFormat());
        prefixOWLDocumentFormat = format.asPrefixOWLDocumentFormat();
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

    void testProperty(String propertyName, String expectedDomain, String expectedRange,
        String expectedSuperProperty) {
        IRI p11IRI = prefixOWLDocumentFormat.getIRI(propertyName);
        Set<OWLEntity> hadParticipant = asUnorderedSet(cidocOntology.entitiesInSignature(p11IRI));
        assertEquals(1, hadParticipant.size(), "should have found " + propertyName);
        OWLEntity entity = hadParticipant.iterator().next();
        assertTrue(OWLProperty.class.isAssignableFrom(entity.getClass()));
        if (entity.isOWLObjectProperty()) {
            testProperty(entity.asOWLObjectProperty(), expectedDomain, expectedRange,
                expectedSuperProperty);
        }
        if (entity.isOWLDataProperty()) {
            testProperty(entity.asOWLDataProperty(), expectedDomain, expectedRange,
                expectedSuperProperty);
        }
    }

    private void testProperty(OWLObjectProperty p11property, String expectedDomain,
        String expectedRange, String expectedSuperProperty) {
        Stream<OWLClassExpression> rangeStream =
            Searcher.range(cidocOntology.objectPropertyRangeAxioms(p11property));
        Collection<OWLClassExpression> ranges = asUnorderedSet(rangeStream);
        assertEquals(1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLDocumentFormat.getIRI(expectedRange);
        }
        assertEquals(expectedIRI, rangeIRI);
        Stream<OWLClassExpression> domainStream =
            Searcher.domain(cidocOntology.objectPropertyDomainAxioms(p11property));
        Collection<OWLClassExpression> domains = asUnorderedSet(domainStream);
        assertEquals(1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedDomain), domainIRI);
        Stream<OWLObjectPropertyExpression> superStream = Searcher
            .sup(cidocOntology.axioms(Filters.subObjectPropertyWithSub, p11property, INCLUDED));
        Collection<OWLObjectPropertyExpression> superProperties = asUnorderedSet(superStream);
        assertEquals(1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    private void testProperty(OWLDataProperty p11property, String expectedDomain,
        String expectedRange, String expectedSuperProperty) {
        Stream<OWLClassExpression> rangeClasses =
            Searcher.range(cidocOntology.dataPropertyRangeAxioms(p11property));
        Collection<OWLClassExpression> ranges = asUnorderedSet(rangeClasses);
        assertEquals(1, ranges.size());
        HasIRI range = (HasIRI) ranges.iterator().next();
        IRI rangeIRI = range.getIRI();
        IRI expectedIRI = IRI.create(expectedRange);
        if (!expectedIRI.isAbsolute()) {
            expectedIRI = prefixOWLDocumentFormat.getIRI(expectedRange);
        }
        assertEquals(expectedIRI, rangeIRI);
        Stream<OWLClassExpression> domainStream =
            Searcher.domain(cidocOntology.dataPropertyDomainAxioms(p11property));
        Collection<OWLClassExpression> domains = asUnorderedSet(domainStream);
        assertEquals(1, domains.size());
        HasIRI domain = (HasIRI) domains.iterator().next();
        IRI domainIRI = domain.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedDomain), domainIRI);
        Stream<OWLObjectPropertyExpression> supStream = Searcher
            .sup(cidocOntology.axioms(Filters.subDataPropertyWithSub, p11property, INCLUDED));
        Collection<OWLObjectPropertyExpression> superProperties = asUnorderedSet(supStream);
        assertEquals(1, superProperties.size());
        HasIRI superProperty = (HasIRI) superProperties.iterator().next();
        IRI superPropertyIRI = superProperty.getIRI();
        assertEquals(prefixOWLDocumentFormat.getIRI(expectedSuperProperty), superPropertyIRI);
    }

    @Test
    void testObjectPropertyAndDataPropertySetsNonTriviallyDisjoint() {
        Set<OWLObjectProperty> objectProperties =
            asUnorderedSet(cidocOntology.objectPropertiesInSignature());
        Set<OWLDataProperty> dataProperties =
            asUnorderedSet(cidocOntology.dataPropertiesInSignature());
        assertFalse(objectProperties.isEmpty());
        assertFalse(dataProperties.isEmpty());
        assertTrue(Collections.disjoint(objectProperties, dataProperties));
    }

    @Test
    void testAnnotationPropertyCount() {
        assertEquals(2, cidocOntology.annotationPropertiesInSignature(INCLUDED).count());
    }
}
