package org.semanticweb.owlapi.api.test;

import java.io.IOException;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** test for 3294629 - currently disabled. Not clear whether structure sharing is
 * allowed or disallowed. Data is equivalent, ontology annotations are not */
@SuppressWarnings("javadoc")
public class SharedBlankNodeTestCase {
    // @Test
    public void verify() throws OWLOntologyCreationException {
        String input = "Ontology:\n" + "    \n" + "    DataProperty: xsd:a\n"
                + "        Range: {1.2}";
        for (OWLAxiom ax : OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(input))
                .getAxioms()) {
            // System.out.println("HasKeyTestCase.verify() " + ax);
        }
    }

    @Test
    public void shouldSaveOneIndividual() throws Exception {
        OWLOntology ontology = createOntology();
        // OWL2DLProfile profile = new OWL2DLProfile();
        // for (OWLProfileViolation v :
        // profile.checkOntology(ontology).getViolations()) {
        // System.out.println("TestClass.shouldSaveOneIndividual() 1 " + v);
        // }
        // for (OWLAxiom ax : ontology.getAxioms()) {
        // System.out.println("TestClass.shouldSaveOneIndividual() 1 " + ax);
        // }
        testAnnotation(ontology);
        // displayOntology(ontology);
        String s = saveOntology(ontology);
        ontology = loadOntology(s);
        // for (OWLProfileViolation v :
        // profile.checkOntology(ontology).getViolations()) {
        // System.out.println("TestClass.shouldSaveOneIndividual() 2 " + v);
        // }
        // for (OWLAxiom ax : ontology.getAxioms()) {
        // System.out.println("TestClass.shouldSaveOneIndividual() 2 " + ax);
        // }
        // displayOntology(ontology);
        testAnnotation(ontology);
    }

    public static OWLOntology createOntology() throws OWLOntologyCreationException {
        String NS = "http://protege.org/more/anonymous/junk";
        OWLDataProperty P = OWLManager.getOWLDataFactory().getOWLDataProperty(
                IRI.create(NS + "#p"));
        OWLObjectProperty P1 = OWLManager.getOWLDataFactory().getOWLObjectProperty(
                IRI.create(NS + "#p1"));
        OWLObjectProperty P2 = OWLManager.getOWLDataFactory().getOWLObjectProperty(
                IRI.create(NS + "#p2"));
        OWLAnnotationProperty ann = OWLManager.getOWLDataFactory()
                .getOWLAnnotationProperty(IRI.create(NS + "#ann"));
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLOntology ontology = manager.createOntology(IRI.create(NS));
        OWLAnonymousIndividual i = factory.getOWLAnonymousIndividual();
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(P));
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(P1));
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(P2));
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(ann));
        manager.applyChange(new AddOntologyAnnotation(ontology, factory.getOWLAnnotation(
                ann, i)));
        OWLAxiom ass = factory.getOWLDataPropertyAssertionAxiom(P, i,
                factory.getOWLLiteral("hello world"));
        OWLNamedIndividual ind = factory
                .getOWLNamedIndividual(IRI.create(NS + "#test"));
        OWLAxiom ax1 = factory.getOWLObjectPropertyAssertionAxiom(P1, ind, i);
        OWLAxiom ax2 = factory.getOWLObjectPropertyAssertionAxiom(P2, ind, i);
        manager.addAxiom(ontology, ass);
        manager.addAxiom(ontology, ax1);
        manager.addAxiom(ontology, ax2);
        return ontology;
    }

    public static String saveOntology(OWLOntology ontology) throws IOException,
    OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology,
                new RDFXMLOntologyFormat(), target);
        return target.toString();
    }

    public static OWLOntology loadOntology(String ontology)
            throws OWLOntologyCreationException {
        return OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(ontology));
    }

    public static void displayOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLFunctionalSyntaxOntologyFormat format = new OWLFunctionalSyntaxOntologyFormat();
        // format.setDefaultPrefix(NS + "#");
        manager.saveOntology(ontology, format, new SystemOutDocumentTarget());
        System.out.println();
    }

    public static void testAnnotation(OWLOntology ontology) {
        for (OWLAnnotation annotation : ontology.getAnnotations()) {
            OWLIndividual i = (OWLIndividual) annotation.getValue();
            // System.out.println("Found individual " + i);
            // System.out.println("Property values = " +
            // i.getDataPropertyValues(P, ontology));
        }
    }
}
