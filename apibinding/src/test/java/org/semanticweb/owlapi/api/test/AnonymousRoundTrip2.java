package org.semanticweb.owlapi.api.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class AnonymousRoundTrip2 extends TestCase{
	public void testRoundTrip() {
	        try {
	            AnonymousRoundTrip ma = new AnonymousRoundTrip();
	            ma.buildOntology();
	            ma.printAxioms();
	            ma.write();
	            
	            System.out.println(" -------------- Take 2");
	            
	            ma.loadOntology();
	            ma.printAxioms();
	        }
	        catch (Throwable t) {
	            t.printStackTrace();
	        }
	    }

}
class AnonymousRoundTrip {
    public static final String NS = "http://smi-protege.stanford.edu/ontologies/AnonymousIndividuals.owl";
    
    private OWLOntologyManager manager;
    private OWLOntology ontology;
    private OWLClass a;
    private OWLAnonymousIndividual h, i;
    private OWLAnnotationProperty p;
    private OWLObjectProperty q;
    
    private File savedLocation;

    public AnonymousRoundTrip() throws OWLOntologyCreationException {
        OWLDataFactory factory = new OWLDataFactoryImpl();
        a = factory.getOWLClass(IRI.create(NS + "#A"));
        p = factory.getOWLAnnotationProperty(IRI.create(NS + "#p"));
        q = factory.getOWLObjectProperty(IRI.create(NS + "#q"));
        h = factory.getOWLAnonymousIndividual();
        i = factory.getOWLAnonymousIndividual();
    }
    
    public void buildOntology() throws OWLOntologyCreationException {
        manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        ontology = manager.createOntology(IRI.create(NS));
        
        OWLAnnotation annotation1 = factory.getOWLAnnotation(p, h);
        manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(a.getIRI(), annotation1));
        manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(a, h));
        manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(q, h, i));
        OWLAnnotation annotation2 = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral("Second", "en"));
        manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(h, annotation2));
    }
    
    public void loadOntology() throws OWLOntologyCreationException {
        System.out.println("Reading from location " + savedLocation);
        manager = OWLManager.createOWLOntologyManager();
        ontology = manager.loadOntologyFromOntologyDocument(savedLocation);
    } 
    
    public void printAxioms() {
        System.out.println("Printing axioms --- ");
        for (OWLAxiom axiom : ontology.getAxioms()) {
            System.out.println("\tAxiom: " + axiom);
        }
        System.out.println("End of axiom printout");
    }
    
    public void write() throws OWLOntologyStorageException, IOException {
        savedLocation = File.createTempFile("RoundTripTest", ".owl");
        FileOutputStream out = new FileOutputStream(savedLocation);
        StreamDocumentTarget writer = new StreamDocumentTarget(out);
        manager.saveOntology(ontology, new ManchesterOWLSyntaxOntologyFormat(), writer);
        out.flush();
        out.close();
        System.out.println("Saved to location " + savedLocation);
    }
    

}
