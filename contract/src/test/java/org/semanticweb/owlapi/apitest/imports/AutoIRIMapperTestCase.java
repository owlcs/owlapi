package org.semanticweb.owlapi.apitest.imports;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.utility.AutoIRIMapper;

public class AutoIRIMapperTestCase extends TestBase {

    @Test
    public void shouldTestIRIMapperForOWLXML() {
        AutoIRIMapper mapper = new AutoIRIMapper(RESOURCES, false, df);
        IRI documentIRI = mapper.getDocumentIRI(df.getIRI("urn:test:", "prem"));
        assert documentIRI != null;
        assertTrue(documentIRI.toString().endsWith("/urntestontology.xml"));
    }
}
