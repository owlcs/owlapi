package org.semanticweb.owlapi.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5
 */
public class OWLOntologyIRIShortFormProviderTestCase {

    public static final String SCHEME_DOMAIN = "http://www.semanticweb.org";
    private OntologyIRIShortFormProvider sfp = new OntologyIRIShortFormProvider();

    @Test
    public void shouldFindLastPathElement() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont");
        assertThat(shortForm, is(equalTo("ont")));
    }

    /*
     * A test to see if a meaningful short form is returned when the ontology IRI encodes version
     * information at the end. For example, the dublin core IRIs do this.
     */
    @Test
    public void shouldReturnLastNonNumericPathElement() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont/1.1.11");
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldReturnLastNonVersionPathElement() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont/v1.1.11");
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldReturnFullIRIForNoPath() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN);
        assertThat(shortForm, is(equalTo(SCHEME_DOMAIN)));
    }

    @Test
    public void shouldStripAwayOWLExtension() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont.owl");
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldStripAwayRDFExtension() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont.rdf");
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldStripAwayXMLExtension() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont.xml");
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldStripAwayOBOExtension() {
        String shortForm = sfp.getShortForm(SCHEME_DOMAIN + "/ontologies/ont.obo");
        assertThat(shortForm, is(equalTo("ont")));
    }

    @Test
    public void shouldReturnSkosForSKOSNamespace() {
        String shortForm = sfp.getShortForm("http://www.w3.org/2004/02/skos/core");
        assertThat(shortForm, is(CoreMatchers.equalTo("skos")));
    }

    @Test
    public void shouldReturnDcForDublinCoreNamespace() {
        String shortForm = sfp.getShortForm("http://purl.org/dc/elements/1.1");
        assertThat(shortForm, is(CoreMatchers.equalTo("dc")));
    }

    @Test
    public void shouldReturnDcForDublinCoreNamespaceEndingWithSlash() {
        String shortForm = sfp.getShortForm("http://purl.org/dc/elements/1.1/");
        assertThat(shortForm, is(CoreMatchers.equalTo("dc")));
    }
}
