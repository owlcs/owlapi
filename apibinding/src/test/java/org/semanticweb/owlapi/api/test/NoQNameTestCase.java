package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.coode.xml.IllegalElementNameException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-Dec-2009
 */
public class NoQNameTestCase extends AbstractAxiomsRoundTrippingTestCase {


    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLNamedIndividual indA = getFactory().getOWLNamedIndividual(IRI.create("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395A"));
        OWLNamedIndividual indB = getFactory().getOWLNamedIndividual(IRI.create("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395B"));
        OWLObjectProperty property = getFactory().getOWLObjectProperty(IRI.create("http://example.com/place/123"));
        axioms.add(getFactory().getOWLObjectPropertyAssertionAxiom(property, indA, indB));
        return axioms;
    }

    @Override
    public void testRDFXML() throws Exception {
        try {
            super.testRDFXML();
            fail("Expected an exception specifying that a QName could not be generated");
        }
        catch (OWLOntologyStorageException e) {
            if (e.getCause() instanceof IllegalElementNameException) {
                System.out.println("Caught IllegalElementNameException as expected: " + e.getMessage());
            }
            else {
                throw e;
            }
        }
    }
}
