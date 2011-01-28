package org.coode.owl.rdfxml.parser.tests;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Mar-2007<br><br>
 */
public class RDFParserTestCases extends TestCase {

    private OWLOntologyManager man;


    @Override
	protected void setUp() throws Exception {
        super.setUp();
        // Use the reference implementation
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        ParsableOWLOntologyFactory factory = new ParsableOWLOntologyFactory();
        man.addOntologyFactory(factory);

    }


    public void testOWLAPI() throws Exception {
        parseFiles("/owlapi/");
    }

    private void parseFiles(String base) throws Exception {
        URL url = getClass().getResource(base);
        File file = new File(url.toURI());

        for (File testSuiteFolder : file.listFiles()) {
            if (testSuiteFolder.isDirectory()) {
                for (File ontologyFile : testSuiteFolder.listFiles()) {
                    if (ontologyFile.getName().endsWith(".rdf") || ontologyFile.getName().endsWith(".owlapi")) {
                        OWLOntology ont = man.loadOntologyFromOntologyDocument(ontologyFile);
                        System.out.println("Loaded: " + ont);
                        man.removeOntology(ont);
                    }
                }
            }
        }
    }
}
