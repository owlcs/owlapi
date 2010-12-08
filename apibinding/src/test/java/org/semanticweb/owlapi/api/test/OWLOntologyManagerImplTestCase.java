package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 02-Jan-2007<br><br>
 */
public class OWLOntologyManagerImplTestCase extends TestCase {

    private OWLOntologyManager manager;


    protected void setUp() throws Exception {
        super.setUp();
        manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        manager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        manager.addIRIMapper(new NonMappingOntologyIRIMapper());
    }

    public void testContains() throws Exception {
        OWLOntology ont = manager.createOntology(TestUtils.createIRI());
        assertTrue(manager.contains(ont.getOntologyID()));
        assertNotNull(manager.getOntology(ont.getOntologyID()));
        assertTrue(manager.getOntologies().contains(ont));
        assertNotNull(manager.getOntologyDocumentIRI(ont));
        manager.removeOntology(ont);
        assertFalse(manager.contains(ont.getOntologyID()));
    }

    public void testImports() throws Exception {
        OWLOntology ontA = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontB = manager.createOntology(TestUtils.createIRI());
        OWLImportsDeclaration decl = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        manager.applyChange(new AddImport(ontA, decl));
        assertTrue(manager.getDirectImports(ontA).contains(ontB));
        manager.removeOntology(ontB);
        assertFalse(manager.getDirectImports(ontA).contains(ontB));
    }

    public void testImportsClosure() throws OWLException {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontB = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontC = manager.createOntology(TestUtils.createIRI());
        OWLImportsDeclaration declA = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        OWLImportsDeclaration declB = manager.getOWLDataFactory().getOWLImportsDeclaration(ontC.getOntologyID().getOntologyIRI());
        manager.applyChange(new AddImport(ontA, declA));
        manager.applyChange(new AddImport(ontB, declB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontA));
        assertTrue(manager.getImportsClosure(ontA).contains(ontB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontC));
        assertTrue(manager.getImportsClosure(ontB).contains(ontB));
        assertTrue(manager.getImportsClosure(ontB).contains(ontC));
    }

}
