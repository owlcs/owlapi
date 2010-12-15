package org.coode.owl.rdfxml.parser.tests;

import java.net.URI;

import junit.framework.TestCase;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03-Aug-2007<br><br>
 */
public class SWRLTestCase extends TestCase {

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

    public void testSWRLParser() throws Exception {
        URI uri = getClass().getResource("/owlapi/SWRLTest.owl").toURI();
        OWLOntology ont = man.loadOntologyFromOntologyDocument(IRI.create(uri));
        for(OWLIndividual i : ont.getIndividualsInSignature()) {
            System.out.println(i);
        }
        assertTrue(ont.getIndividualsInSignature().isEmpty());
    }
}
