package org.semanticweb.owlapi.apitest.imports;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.utility.AutoIRIMapper;

class AutoIRIMapperTestCase extends TestBase {

    @Test
    void shouldTestIRIMapperForOWLXML() {
        AutoIRIMapper mapper = new AutoIRIMapper(RESOURCES, false, df);
        IRI documentIRI = mapper.getDocumentIRI(iri("urn:test:", "prem"));
        assert documentIRI != null;
        assertTrue(documentIRI.toString().endsWith("/urntestontology.xml"));
    }
}
