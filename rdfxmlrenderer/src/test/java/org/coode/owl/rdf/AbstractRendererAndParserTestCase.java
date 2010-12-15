package org.coode.owl.rdf;

import java.io.File;
import java.util.Set;

import junit.framework.TestCase;

import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
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
 */
public abstract class AbstractRendererAndParserTestCase extends TestCase {

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

    public OWLClass createClass() {
        return man.getOWLDataFactory().getOWLClass(TestUtils.createIRI());
    }
    
    public OWLAnnotationProperty createAnnotationProperty() {
        return getManager().getOWLDataFactory().getOWLAnnotationProperty(TestUtils.createIRI());
    }

    public OWLObjectProperty createObjectProperty() {
        return man.getOWLDataFactory().getOWLObjectProperty(TestUtils.createIRI());
    }

    public OWLDataProperty createDataProperty() {
        return man.getOWLDataFactory().getOWLDataProperty(TestUtils.createIRI());
    }

    public OWLIndividual createIndividual() {
        return man.getOWLDataFactory().getOWLNamedIndividual(TestUtils.createIRI());
    }


    public OWLOntologyManager getManager() {
        return man;
    }


    protected OWLDataFactory getDataFactory() {
        return man.getOWLDataFactory();
    }

    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = man.createOntology(IRI.create("http://rdfxmltests/ontology"));
        for (OWLAxiom ax : getAxioms()) {
            man.applyChange(new AddAxiom(ontA, ax));
        }
//        OWLOntologyAnnotationAxiom anno = getDataFactory().getOWLOntologyAnnotationAxiom(ontA, getDataFactory().getCommentAnnotation(getClassExpression()));
//        man.applyChange(new AddAxiom(ontA, anno));
        File tempFile = File.createTempFile("Ontology", ".owlapi");
        man.saveOntology(ontA, IRI.create(tempFile.toURI()));
        man.removeOntology(ontA);
        OWLOntology ontB = man.loadOntologyFromOntologyDocument(IRI.create(tempFile.toURI()));
        
        Set<OWLAxiom> AminusB = ontA.getAxioms();
        AminusB.removeAll(ontB.getAxioms());
       
        Set<OWLAxiom> BminusA = ontB.getAxioms();
        BminusA.removeAll(ontA.getAxioms());
        
        StringBuffer msg = new StringBuffer();
        if (AminusB.isEmpty() && BminusA.isEmpty()) {
			msg.append("Ontology save/load roundtripp OK.\n");
		} else {
			msg.append("Ontology save/load roundtripping error.\n");
			msg.append("=> " + AminusB.size() + " axioms lost in roundtripping.\n");
			for (OWLAxiom axiom : AminusB) {
				msg.append(axiom.toString() + "\n");
			}
			msg.append("=> " + BminusA.size() + " axioms added after roundtripping.\n");
			for (OWLAxiom axiom : BminusA) {
				msg.append(axiom.toString() + "\n");
			}
		}
        assertTrue(msg.toString(), AminusB.isEmpty() && BminusA.isEmpty());
    }

    protected abstract Set<OWLAxiom> getAxioms();

    protected abstract String getClassExpression();

}
