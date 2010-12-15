package org.coode.owl.rdf;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-May-2007<br><br>
 * <p/>
 * Test cases for rendering of disjoint axioms.  The OWL 1.1 specification makes it
 * possible to specify that a set of classes are mutually disjoint.  Unfortunately,
 * this must be represented in RDF as a set of pairwise disjoint statements. In otherwords,
 * DisjointClasses(A, B, C) must be represented as DisjointWith(A, B), DisjointWith(A, C)
 * DisjointWith(B, C).  ~This test case ensure that these axioms are serialsed correctly.
 */
public class TestDisjoints extends TestCase {

    private OWLOntologyManager man;


    @Override
	protected void setUp() throws Exception {
        super.setUp();
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        man.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        man.addOntologyFactory(new ParsableOWLOntologyFactory());
        man.addOntologyStorer(new RDFXMLOntologyStorer());
    }

    public void testAnonDisjoints() throws Exception {
        OWLOntology ontA = man.createOntology(TestUtils.createIRI());
        OWLClass clsA = man.getOWLDataFactory().getOWLClass(TestUtils.createIRI());
        OWLClass clsB = man.getOWLDataFactory().getOWLClass(TestUtils.createIRI());
        OWLObjectProperty prop = man.getOWLDataFactory().getOWLObjectProperty(TestUtils.createIRI());
        OWLClassExpression descA = man.getOWLDataFactory().getOWLObjectSomeValuesFrom(prop, clsA);
        OWLClassExpression descB = man.getOWLDataFactory().getOWLObjectSomeValuesFrom(prop, clsB);
        Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>();
        classExpressions.add(descA);
        classExpressions.add(descB);
        OWLAxiom ax = man.getOWLDataFactory().getOWLDisjointClassesAxiom(classExpressions);
        man.applyChange(new AddAxiom(ontA, ax));
        File tempFile = File.createTempFile("Ontology", ".owlapi");
        man.saveOntology(ontA, IRI.create(tempFile.toURI()));
        OWLOntology ontB = man.loadOntologyFromOntologyDocument(tempFile);
        assertTrue(ontB.getAxioms().contains(ax));
    }


}
