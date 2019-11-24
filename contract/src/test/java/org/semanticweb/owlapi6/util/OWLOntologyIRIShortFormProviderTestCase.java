package org.semanticweb.owlapi6.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.utility.OntologyIRIShortFormProvider;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5
 */
public class OWLOntologyIRIShortFormProviderTestCase extends TestBase {

    private static final String ONT2 = "ont";
    private static final String ont = "/ontologies/ont/";
    private static final String onts = "/ontologies/";
    public static final String SCHEME_DOMAIN = "http://www.semanticweb.org";
    private final OntologyIRIShortFormProvider sfp = new OntologyIRIShortFormProvider();

    @Test
    public void shouldFindLastPathElement() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + onts, ONT2));
        assertEquals(ONT2, shortForm);
    }

    /*
     * A test to see if a meaningful short form is returned when the ontology IRI encodes version
     * information at the end. For example, the dublin core IRIs do this.
     */
    @Test
    public void shouldReturnLastNonNumericPathElement() {
        String shortForm =
            sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + "/ontologies/ont/1.1.11", ""));
        assertEquals(ONT2, shortForm);
    }

    @Test
    public void shouldReturnLastNonVersionPathElement() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + ont, "v1.1.11"));
        assertEquals(ONT2, shortForm);
    }

    @Test
    public void shouldReturnFullIRIForNoPath() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN, ""));
        assertEquals(SCHEME_DOMAIN, shortForm);
    }

    @Test
    public void shouldStripAwayOWLExtension() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + onts, "ont.owl"));
        assertEquals(ONT2, shortForm);
    }

    @Test
    public void shouldStripAwayRDFExtension() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + onts, "ont.rdf"));
        assertEquals(ONT2, shortForm);
    }

    @Test
    public void shouldStripAwayXMLExtension() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + onts, "ont.xml"));
        assertEquals(ONT2, shortForm);
    }

    @Test
    public void shouldStripAwayOBOExtension() {
        String shortForm = sfp.getShortForm(df.getIRI(SCHEME_DOMAIN + onts, "ont.obo"));
        assertEquals(ONT2, shortForm);
    }

    @Test
    public void shouldReturnSkosForSKOSNamespace() {
        String shortForm = sfp.getShortForm(df.getIRI("http://www.w3.org/2004/02/skos/", "core"));
        assertEquals("skos", shortForm);
    }

    @Test
    public void shouldReturnDcForDublinCoreNamespace() {
        String shortForm = sfp.getShortForm(df.getIRI("http://purl.org/dc/elements/1.1", ""));
        assertEquals("dc", shortForm);
    }

    @Test
    public void shouldReturnDcForDublinCoreNamespaceEndingWithSlash() {
        String shortForm = sfp.getShortForm(df.getIRI("http://purl.org/dc/elements/1.1/", ""));
        assertEquals("dc", shortForm);
    }
}
