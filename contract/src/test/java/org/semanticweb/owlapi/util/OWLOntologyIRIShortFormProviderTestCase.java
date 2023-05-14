package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.utility.OntologyIRIShortFormProvider;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5
 */
class OWLOntologyIRIShortFormProviderTestCase extends TestBase {

    private final OntologyIRIShortFormProvider sfp = new OntologyIRIShortFormProvider();

    @ParameterizedTest
    @CsvSource({
        // shouldFindLastPathElement
        "ont,http://www.semanticweb.org/ontologies/,ont",
        // void shouldReturnLastNonNumericPathElement
        // A test to see if a meaningful short form is returned when the ontology IRI encodes
        // version information at the end. For example, the dublin core IRIs do this.
        "ont,http://www.semanticweb.org/ontologies/ont/1.1.11,",
        // shouldReturnLastNonVersionPathElement
        "ont,http://www.semanticweb.org/ontologies/ont/,v1.1.11",
        // shouldReturnFullIRIForNoPath
        "http://www.semanticweb.org,http://www.semanticweb.org,",
        // shouldStripAwayOWLExtension
        "ont,http://www.semanticweb.org/ontologies/,ont.owl",
        // shouldStripAwayRDFExtension
        "ont,http://www.semanticweb.org/ontologies/,ont.rdf",
        // shouldStripAwayXMLExtension
        "ont,http://www.semanticweb.org/ontologies/,ont.xml",
        // shouldStripAwayOBOExtension
        "ont,http://www.semanticweb.org/ontologies/,ont.obo",
        // shouldReturnSkosForSKOSNamespace
        "skos,http://www.w3.org/2004/02/skos/,core",
        // shouldReturnDcForDublinCoreNamespace
        "dc,http://purl.org/dc/elements/1.1,",
        // shouldReturnDcForDublinCoreNamespaceEndingWithSlash
        "dc,http://purl.org/dc/elements/1.1/,"})
    void shouldFindExpected(String expected, String iri, String fragment) {
        String shortForm = sfp.getShortForm(iri(iri, fragment));
        assertEquals(expected, shortForm);
    }
}
