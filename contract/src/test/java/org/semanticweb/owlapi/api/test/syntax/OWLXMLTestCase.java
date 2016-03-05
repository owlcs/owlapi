package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;

@SuppressWarnings("javadoc")
public class OWLXMLTestCase extends TestBase {

    @Test
    public void shouldFindExpectedAxiomsForBlankNodes() throws OWLOntologyCreationException {
        OWLObjectProperty r = ObjectProperty(IRI.create(
            "http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        OWLOntology o = m.loadOntologyFromOntologyDocument(new File(RESOURCES, "owlxml_anonloop.owx"));
        for (OWLClassAssertionAxiom ax : o.getAxioms(AxiomType.CLASS_ASSERTION)) {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(r, ax.getIndividual(), ax.getIndividual());
            assertTrue(expected + " not found", o.containsAxiom(expected));
        }
    }
}
