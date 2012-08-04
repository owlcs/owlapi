package org.semanticweb.owlapi.api.test;

import java.io.IOException;

import junit.framework.TestCase;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

public class FunctionalSyntaxIRIProblemTestCase extends TestCase{

	/**
	 * @param args
	 * @throws OWLOntologyStorageException
	 * @throws OWLOntologyCreationException
	 * @throws IOException
	 */
	@Test
    public void testmain() throws Exception {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ontology = manager.createOntology(IRI.create("urn:testontology:o1"));
		OWLObjectProperty p = factory.getOWLObjectProperty(IRI.create("http://example.org/A_#part_of"));
		OWLClass a = factory.getOWLClass(IRI.create("http://example.org/A_A"));
		OWLClass b = factory.getOWLClass(IRI.create("http://example.org/A_B"));
		manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(p));
		manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(a));
		manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(b));
		manager.addAxiom(ontology, factory.getOWLSubClassOfAxiom(b, factory.getOWLObjectSomeValuesFrom(p, a)));

		String rdfxmlSaved = saveOntology(ontology, new RDFXMLOntologyFormat());
		final OWLOntology loadOntology = loadOntology(rdfxmlSaved);
		//System.out.println("Loaded: " + loadOntology);

		OWLFunctionalSyntaxOntologyFormat functionalFormat = new OWLFunctionalSyntaxOntologyFormat();
		functionalFormat.asPrefixOWLOntologyFormat().setPrefix("example", "http://example.org/");
		String functionalSaved = saveOntology(ontology, functionalFormat);
		final OWLOntology loadOntology2 = loadOntology(functionalSaved);
		//System.out.println("Loaded: " + loadOntology2);

		// won't reach here if functional syntax fails - comment it out and uncomment this to test Manchester
		ManchesterOWLSyntaxOntologyFormat manchesterFormat = new ManchesterOWLSyntaxOntologyFormat();
		manchesterFormat.asPrefixOWLOntologyFormat().setPrefix("example", "http://example.org/");
		String manchesterSaved = saveOntology(ontology, manchesterFormat);
		final OWLOntology loadOntology3 = loadOntology(manchesterSaved);
		//System.out.println("Loaded: " + loadOntology3);

		assertEquals(ontology, loadOntology);
		assertEquals(ontology, loadOntology2);
		assertEquals(ontology, loadOntology3);
		assertEquals(ontology.getAxioms(), loadOntology.getAxioms());
		assertEquals(ontology.getAxioms(), loadOntology2.getAxioms());
		assertEquals(ontology.getAxioms(), loadOntology3.getAxioms());

	}

    public static String saveOntology(OWLOntology ontology, PrefixOWLOntologyFormat format) throws IOException, OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        StringDocumentTarget t=new StringDocumentTarget();
        manager.saveOntology(ontology, format, t);

        return t.toString();
    }

    public static OWLOntology loadOntology(String ontologyFile) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        StringDocumentSource s=new StringDocumentSource(ontologyFile);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(s);
        return ontology;
    }

}
