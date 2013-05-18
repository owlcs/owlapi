package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
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
@Ignore
public class SharedBlankNodeTestCase {
    @Test
    public void verify() throws OWLOntologyCreationException {
        String input = "Ontology:\n" + "    DataProperty: xsd:a\n" + "        Range: {1.2}";
        for (OWLAxiom ax : Factory.getManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input)).getAxioms()) {
            System.out.println("HasKeyTestCase.verify() " + ax);
        }
    }

    @Test
    public void shouldSaveOneIndividual() throws Exception {
        OWLOntology ontology = createOntology();
        testAnnotation(ontology);
        String s = saveOntology(ontology);
        System.out.println(s);
        ontology = loadOntology(s);
        testAnnotation(ontology);
    }

    public static OWLOntology createOntology() throws OWLOntologyCreationException {
        String NS = "urn:test";
        OWLDataProperty P = DataProperty(IRI(NS + "#p"));
        OWLObjectProperty P1 = ObjectProperty(IRI(NS + "#p1"));
        OWLObjectProperty P2 = ObjectProperty(IRI(NS + "#p2"));
        OWLAnnotationProperty ann = AnnotationProperty(IRI(NS + "#ann"));
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        OWLAnonymousIndividual i = AnonymousIndividual();
        manager.addAxiom(ontology, Declaration(P));
        manager.addAxiom(ontology, Declaration(P1));
        manager.addAxiom(ontology, Declaration(P2));
        manager.addAxiom(ontology, Declaration(ann));
        manager.applyChange(new AddOntologyAnnotation(ontology, Annotation(ann, i)));
        OWLAxiom ass = DataPropertyAssertion(P, i, Literal("hello world"));
        OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#test"));
        OWLAxiom ax1 = ObjectPropertyAssertion(P1, ind, i);
        OWLAxiom ax2 = ObjectPropertyAssertion(P2, ind, i);
        manager.addAxiom(ontology, ass);
        manager.addAxiom(ontology, ax1);
        manager.addAxiom(ontology, ax2);
        return ontology;
    }

    public static String saveOntology(OWLOntology ontology) throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology, new RDFXMLOntologyFormat(), target);
        return target.toString();
    }

    public static OWLOntology loadOntology(String ontology) throws OWLOntologyCreationException {
        return Factory.getManager().loadOntologyFromOntologyDocument(new StringDocumentSource(ontology));
    }

    public static void displayOntology(OWLOntology ontology) throws OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLFunctionalSyntaxOntologyFormat format = new OWLFunctionalSyntaxOntologyFormat();
        manager.saveOntology(ontology, format, new SystemOutDocumentTarget());
        System.out.println();
    }

    public static void testAnnotation(OWLOntology ontology) {
        for (OWLAnnotation annotation : ontology.getAnnotations()) {
            OWLIndividual i = (OWLIndividual) annotation.getValue();
            System.out.println("Found individual " + i);
            System.out.println("Property values = " + i.getDataPropertyValues(ontology));
        }
    }
}
