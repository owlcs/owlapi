package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.AutoIRIMapper;

@SuppressWarnings("javadoc")
public class AutoIRIMapperTestCase {

    private static final File RESOURCES;
    static {
        File f = new File("contract/src/test/resources/");
        if (f.exists()) {
            RESOURCES = f;
        } else {
            f = new File("src/test/resources/");
            if (f.exists()) {
                RESOURCES = f;
            } else {
                throw new OWLRuntimeException(
                        "ManchesterImportTestCase: NO RESOURCE FOLDER ACCESSIBLE");
            }
        }
    }

    @Test
    public void shouldTestIRIMapperForOWLXML() {
        AutoIRIMapper mapper = new AutoIRIMapper(RESOURCES, false);
        assertTrue(mapper.getDocumentIRI(IRI.create("urn:test:prem"))
                .toString().endsWith("/urntestontology.xml"));
    }
}
