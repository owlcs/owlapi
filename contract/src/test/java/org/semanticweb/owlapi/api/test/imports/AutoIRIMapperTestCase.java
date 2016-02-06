package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.AutoIRIMapper;

@SuppressWarnings("javadoc")
public class AutoIRIMapperTestCase extends TestBase {

    @Test
    public void shouldTestIRIMapperForOWLXML() {
        AutoIRIMapper mapper = new AutoIRIMapper(RESOURCES, false);
        IRI documentIRI = mapper.getDocumentIRI(IRI.create("urn:test:", "prem"));
        assert documentIRI != null;
        assertTrue(documentIRI.toString().endsWith("/urntestontology.xml"));
    }
}
