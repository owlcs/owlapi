package org.semanticweb.owlapi.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5
 */
class OWLOntologyIRIShortFormProviderTestCase {

    static final String ONT = "ont";
    static final String ONTOLOGIES = "/ontologies/";
    static final String SCHEME_DOMAIN = "http://www.semanticweb.org";
    final OntologyIRIShortFormProvider sfp = new OntologyIRIShortFormProvider();

    @Test
    void shouldFindLastPathElement() {
        String shortForm = sfp.getShortForm(IRI.create(SCHEME_DOMAIN + ONTOLOGIES, ONT));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    /*
     * A test to see if a meaningful short form is returned when the ontology IRI encodes version
     * information at the end. For example, the dublin core IRIs do this.
     */
    @Test
    void shouldReturnLastNonNumericPathElement() {
        String shortForm =
            sfp.getShortForm(IRI.create(SCHEME_DOMAIN + "/ontologies/ont/1.1.11", ""));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    @Test
    void shouldReturnLastNonVersionPathElement() {
        String shortForm =
            sfp.getShortForm(IRI.create(SCHEME_DOMAIN + "/ontologies/ont/", "v1.1.11"));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    @Test
    void shouldReturnFullIRIForNoPath() {
        String shortForm = sfp.getShortForm(IRI.create(SCHEME_DOMAIN, ""));
        assertThat(shortForm, is(equalTo(SCHEME_DOMAIN)));
    }

    @Test
    void shouldStripAwayOWLExtension() {
        String shortForm = sfp.getShortForm(IRI.create(SCHEME_DOMAIN + ONTOLOGIES, "ont.owl"));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    @Test
    void shouldStripAwayRDFExtension() {
        String shortForm = sfp.getShortForm(IRI.create(SCHEME_DOMAIN + ONTOLOGIES, "ont.rdf"));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    @Test
    void shouldStripAwayXMLExtension() {
        String shortForm = sfp.getShortForm(IRI.create(SCHEME_DOMAIN + ONTOLOGIES, "ont.xml"));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    @Test
    void shouldStripAwayOBOExtension() {
        String shortForm = sfp.getShortForm(IRI.create(SCHEME_DOMAIN + ONTOLOGIES, "ont.obo"));
        assertThat(shortForm, is(equalTo(ONT)));
    }

    @Test
    void shouldReturnSkosForSKOSNamespace() {
        String shortForm = sfp.getShortForm(IRI.create("http://www.w3.org/2004/02/skos/", "core"));
        assertThat(shortForm, is(CoreMatchers.equalTo("skos")));
    }

    @Test
    void shouldReturnDcForDublinCoreNamespace() {
        String shortForm = sfp.getShortForm(IRI.create("http://purl.org/dc/elements/1.1", ""));
        assertThat(shortForm, is(CoreMatchers.equalTo("dc")));
    }

    @Test
    void shouldReturnDcForDublinCoreNamespaceEndingWithSlash() {
        String shortForm = sfp.getShortForm(IRI.create("http://purl.org/dc/elements/1.1/", ""));
        assertThat(shortForm, is(CoreMatchers.equalTo("dc")));
    }
}
