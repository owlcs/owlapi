package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is
 * allowed or disallowed. Data is equivalent, ontology annotations are not
 */
@SuppressWarnings("javadoc")
public class SharedBlankNodeTestCase {

    @Test
    public void shouldSaveOneIndividual() throws Exception {
        OWLOntology ontology = createOntology();
        String s = saveOntology(ontology, new RDFXMLOntologyFormat());
        String functionalSyntax = saveOntology(ontology, new OWLFunctionalSyntaxOntologyFormat());
        testAnnotation(loadOntology(functionalSyntax));
        testAnnotation(loadOntology(s));
    }

    public static OWLOntology createOntology()
        throws OWLOntologyCreationException {
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
        manager.applyChange(new AddOntologyAnnotation(ontology, Annotation(ann,
            i)));
        OWLAxiom ass = DataPropertyAssertion(P, i, Literal("hello world"));
        OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#test"));
        OWLAxiom ax1 = ObjectPropertyAssertion(P1, ind, i);
        OWLAxiom ax2 = ObjectPropertyAssertion(P2, ind, i);
        manager.addAxiom(ontology, ass);
        manager.addAxiom(ontology, ax1);
        manager.addAxiom(ontology, ax2);
        return ontology;
    }

    public static String saveOntology(OWLOntology ontology, OWLOntologyFormat format)
        throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology,
            format, target);
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
        manager.saveOntology(ontology, format, new SystemOutDocumentTarget());
    }

    public static void testAnnotation(OWLOntology ontology) {
        for (OWLIndividual i : ontology.getIndividualsInSignature()) {
            assertEquals(2, ontology.getObjectPropertyAssertionAxioms(i).size());
        }
        for (OWLAnnotation annotation : ontology.getAnnotations()) {
            OWLIndividual i = (OWLIndividual) annotation.getValue();
            assertEquals(1, ontology.getDataPropertyAssertionAxioms(i).size());
        }
    }

    @Test
    public void shouldRoundtripBlankNodeAnnotations() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Class rdf:about=\"http://E\"><rdfs:comment><rdf:Description><rdfs:comment>E</rdfs:comment></rdf:Description></rdfs:comment></owl:Class></rdf:RDF>";
        OWLOntology o = loadOntology(input);
        OWLOntology o1 = loadOntology(saveOntology(o, new OWLFunctionalSyntaxOntologyFormat()));
        OWLOntology o2 = loadOntology(saveOntology(o1, new RDFXMLOntologyFormat()));
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms = o2.getAnnotationAssertionAxioms(IRI("http://E"));
        assertEquals(1, annotationAssertionAxioms.size());
        OWLAnnotationAssertionAxiom next = annotationAssertionAxioms.iterator().next();
        assertEquals(1, o2.getAnnotationAssertionAxioms((OWLAnnotationSubject) next.getValue()).size());
    }

    @Test
    public void shouldRemapUponReading() throws OWLOntologyCreationException {
        String input = "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\r\n" +
            "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\r\n" +
            "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\r\n" +
            "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\r\n" +
            "Ontology(\r\n" +
            "Declaration(Class(<http://E>))\r\n" +
            "AnnotationAssertion(rdfs:comment <http://E> _:genid1)\r\n" +
            "AnnotationAssertion(rdfs:comment _:genid1 \"E\"))";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        OWLOntology o2 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        Set<OWLAnnotationValue> values1 = new HashSet<OWLAnnotationValue>();
        Set<OWLAnnotationValue> values2 = new HashSet<OWLAnnotationValue>();
        for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values1.add(value);
            }
        }
        for (OWLAnnotationAssertionAxiom a : o2.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values2.add(value);
            }
        }
        assertEquals(values1.toString(), values1.size(), 1);
        assertEquals(values1.toString(), values2.size(), 1);
        // XXX enable to test functional syntax remapping
        // assertNotEquals(values1, values2);
    }

    @Test
    public void shouldHaveOnlyOneAnonIndividual() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\r\n<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Class rdf:about=\"http://E\"><rdfs:comment><rdf:Description><rdfs:comment>E</rdfs:comment></rdf:Description></rdfs:comment></owl:Class></rdf:RDF>";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        OWLOntology o2 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        Set<OWLAnnotationValue> values1 = new HashSet<OWLAnnotationValue>();
        Set<OWLAnnotationValue> values2 = new HashSet<OWLAnnotationValue>();
        for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values1.add(value);
            }
        }
        for (OWLAnnotationAssertionAxiom a : o2.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values2.add(value);
            }
        }
        assertEquals(values1.toString(), values1.size(), 1);
        assertEquals(values1.toString(), values2.size(), 1);
        assertNotEquals(values1, values2);
    }

    @Test
    public void shouldNotRemapUponReloading() throws OWLOntologyCreationException {
        // XXX this test shows a condition that can cause clashes between
        // imported ontologies
        // XXX Code needs to be changed so remapping always happens
        String input = "<?xml version=\"1.0\"?>\r\n" +
            "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n" +
            "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" +
            "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n" +
            "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n" +
            "    <Ontology/>\r\n" +
            "    <Class rdf:about=\"http://E\">\r\n" +
            "        <rdfs:comment>\r\n" +
            "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n" +
            "                <rdfs:comment>E</rdfs:comment>\r\n" +
            "            </rdf:Description>\r\n" +
            "        </rdfs:comment>\r\n" +
            "    </Class>\r\n" +
            "</rdf:RDF>";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        Set<OWLAnnotationValue> values1 = new HashSet<OWLAnnotationValue>();
        values1.add(m.getOWLDataFactory().getOWLAnonymousIndividual("_:genid-nodeid-1058025095"));
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values1.add(value);
            }
        }
        o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = a.getValue();
            if (value instanceof OWLAnonymousIndividual) {
                values1.add(value);
            }
        }
        assertEquals(values1.toString(), values1.size(), 1);
    }

    @Test
    public void shouldNotOutputNodeIdWhenNotNeeded() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n" +
            "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n" +
            "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" +
            "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n" +
            "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n" +
            "    <Ontology/>\r\n" +
            "    <Class rdf:about=\"http://E\">\r\n" +
            "        <rdfs:comment>\r\n" +
            "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n" +
            "                <rdfs:comment>E</rdfs:comment>\r\n" +
            "            </rdf:Description>\r\n" +
            "        </rdfs:comment>\r\n" +
            "    </Class>\r\n" +
            "</rdf:RDF>";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        String result = saveOntology(o1,
            new RDFXMLOntologyFormat());
        assertFalse(result.contains("rdf:nodeID"));
    }

    @Test
    public void shouldOutputNodeIdEvenIfNotNeeded() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n" +
            "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n" +
            "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" +
            "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n" +
            "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n" +
            "    <Ontology/>\r\n" +
            "    <Class rdf:about=\"http://E\">\r\n" +
            "        <rdfs:comment>\r\n" +
            "            <rdf:Description>\r\n" +
            "                <rdfs:comment>E</rdfs:comment>\r\n" +
            "            </rdf:Description>\r\n" +
            "        </rdfs:comment>\r\n" +
            "    </Class>\r\n" +
            "</rdf:RDF>";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        AnonymousIndividualProperties.setSaveIdsForAllAnonymousIndividuals(true);
        try {
            String result = saveOntology(o1, new RDFXMLOntologyFormat());
            assertTrue(result.contains("rdf:nodeID"));
        } finally {
            // make sure the static variable is reset after the test
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    @Test
    public void shouldOutputNodeIdWhenNeeded() throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n" +
            "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n" +
            "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" +
            "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" +
            "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n" +
            "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n" +
            "    <Ontology/>\r\n" +
            "    <Class rdf:about=\"http://E\">\r\n" +
            "        <rdfs:comment>\r\n" +
            "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n" +
            "                <rdfs:comment>E</rdfs:comment>\r\n" +
            "            </rdf:Description>\r\n" +
            "        </rdfs:comment>\r\n" +
            "    </Class>\r\n" +
            "    <Class rdf:about=\"http://F\">\r\n" +
            "        <rdfs:comment>\r\n" +
            "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n" +
            "                <rdfs:comment>E</rdfs:comment>\r\n" +
            "            </rdf:Description>\r\n" +
            "        </rdfs:comment>\r\n" +
            "    </Class>\r\n" +
            "</rdf:RDF>";
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o1 = m.loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        String result = saveOntology(o1,
            new RDFXMLOntologyFormat());
        assertTrue(result.contains("rdf:nodeID"));
    }
}
