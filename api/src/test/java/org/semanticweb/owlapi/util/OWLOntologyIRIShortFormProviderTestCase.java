package org.semanticweb.owlapi.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
@SuppressWarnings("javadoc")
public class OWLOntologyIRIShortFormProviderTestCase {

    public static final String SCHEME_DOMAIN = "http://www.semanticweb.org";
    private OntologyIRIShortFormProvider sfp;

    @Before
    public void setUp() {
        sfp = new OntologyIRIShortFormProvider();
    }

    @Test
    public void shouldFindLastPathElement() {
        String input = SCHEME_DOMAIN + "/ontologies/ont";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    /*
     * A test to see if a meaningful short form is returned when the ontology
     * IRI encodes version information at the end. For example, the dublin core
     * IRIs do this.
     */
    @Test
    public void shouldReturnLastNonNumericPathElement() {
        String input = SCHEME_DOMAIN + "/ontologies/ont/1.1.11";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldReturnLastNonVersionPathElement() {
        String input = SCHEME_DOMAIN + "/ontologies/ont/v1.1.11";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldReturnFullIRIForNoPath() {
        String input = SCHEME_DOMAIN;
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo(SCHEME_DOMAIN)));
    }

    @Test
    public void shouldStripAwayOWLExtension() {
        String input = SCHEME_DOMAIN + "/ontologies/ont.owl";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldStripAwayRDFExtension() {
        String input = SCHEME_DOMAIN + "/ontologies/ont.rdf";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldStripAwayXMLExtension() {
        String input = SCHEME_DOMAIN + "/ontologies/ont.xml";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldStripAwayOBOExtension() {
        String input = SCHEME_DOMAIN + "/ontologies/ont.obo";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldReturnSkosForSKOSNamespace() {
        String input = "http://www.w3.org/2004/02/skos/core";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(CoreMatchers.equalTo("skos")));
    }

    @Test
    public void shouldReturnDcForDublinCoreNamespace() {
        String input = "http://purl.org/dc/elements/1.1";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(CoreMatchers.equalTo("dc")));
    }

    @Test
    public void shouldReturnDcForDublinCoreNamespaceEndingWithSlash() {
        String input = "http://purl.org/dc/elements/1.1/";
        String shortForm = sfp.getShortForm(IRI.create(input));
        assertThat(shortForm, is(CoreMatchers.equalTo("dc")));
    }
}
