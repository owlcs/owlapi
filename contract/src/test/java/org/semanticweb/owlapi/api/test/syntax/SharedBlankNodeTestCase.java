package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is allowed or
 * disallowed. Data is equivalent, ontology annotations are not
 */
@SuppressWarnings("javadoc")
public class SharedBlankNodeTestCase extends TestBase {

    String NS = "urn:test";
    OWLAnonymousIndividual i = AnonymousIndividual();
    OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#", "test"));

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
    public void shouldSaveOneIndividual() throws Exception {
        OWLOntology ontology = createOntology();
        StringDocumentTarget s = saveOntology(ontology, new RDFXMLDocumentFormat());
        StringDocumentTarget functionalSyntax =
            saveOntology(ontology, new FunctionalSyntaxDocumentFormat());
        testAnnotation(loadOntologyFromString(functionalSyntax));
        testAnnotation(loadOntologyFromString(s));
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntology ontology = m.createOntology(IRI(NS, ""));
        annotate(ontology, NS + "#ann", i);
        ontology.getOWLOntologyManager().addAxiom(ontology,
            dataAssertion(NS + "#p", i, "hello world"));
        ontology.getOWLOntologyManager().addAxiom(ontology, objectAssertion(NS + "#p1", ind, i));
        ontology.getOWLOntologyManager().addAxiom(ontology, objectAssertion(NS + "#p2", ind, i));
        return ontology;
    }

    private void annotate(OWLOntology o, String p, OWLAnnotationValue v) {
        m.applyChange(new AddOntologyAnnotation(o, Annotation(AnnotationProperty(IRI(p)), v)));
    }

    private static OWLAxiom dataAssertion(String p, OWLIndividual i, String l) {
        return DataPropertyAssertion(DataProperty(IRI(p)), i, Literal(l));
    }

    private static OWLAxiom objectAssertion(String p, OWLIndividual i, OWLIndividual j) {
        return ObjectPropertyAssertion(ObjectProperty(IRI(p)), i, j);
    }

    @Test
    public void shouldRoundtripBlankNodeAnnotations()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input =
            "<?xml version=\"1.0\"?>\r\n<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Class rdf:about=\"http://E\"><rdfs:comment><rdf:Description><rdfs:comment>E</rdfs:comment></rdf:Description></rdfs:comment></owl:Class></rdf:RDF>";
        OWLOntology o = loadOntologyFromString(input);
        OWLOntology o1 =
            loadOntologyFromString(saveOntology(o, new FunctionalSyntaxDocumentFormat()));
        OWLOntology o2 = loadOntologyFromString(saveOntology(o1, new RDFXMLDocumentFormat()));
        Set<OWLAnnotationAssertionAxiom> annotationAssertionAxioms =
            o2.getAnnotationAssertionAxioms(IRI("http://E"));
        assertEquals(1, annotationAssertionAxioms.size());
        OWLAnnotationAssertionAxiom next = annotationAssertionAxioms.iterator().next();
        assertEquals(1,
            o2.getAnnotationAssertionAxioms((OWLAnnotationSubject) next.getValue()).size());
    }

    @Test
    public void shouldRemapUponReading() throws OWLOntologyCreationException {
        String input = "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\r\n"
            + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\r\n"
            + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\r\n"
            + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\r\n" + "Ontology(\r\n"
            + "Declaration(Class(<http://E>))\r\n"
            + "AnnotationAssertion(rdfs:comment <http://E> _:genid1)\r\n"
            + "AnnotationAssertion(rdfs:comment _:genid1 \"E\"))";
        OWLOntology o1 = loadOntologyFromString(input);
        OWLOntology o2 = loadOntologyFromString(input);
        Set<OWLAnnotationValue> values1 = new HashSet<>();
        Set<OWLAnnotationValue> values2 = new HashSet<>();
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
    public void shouldHaveOnlyOneAnonIndividual() throws OWLOntologyCreationException {
        String input =
            "<?xml version=\"1.0\"?>\r\n<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Class rdf:about=\"http://E\"><rdfs:comment><rdf:Description><rdfs:comment>E</rdfs:comment></rdf:Description></rdfs:comment></owl:Class></rdf:RDF>";
        OWLOntology o1 = loadOntologyFromString(input);
        OWLOntology o2 = loadOntologyFromString(input);
        Set<OWLAnnotationValue> values1 = new HashSet<>();
        Set<OWLAnnotationValue> values2 = new HashSet<>();
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
        AnonymousIndividualProperties.setRemapAllAnonymousIndividualsIds(false);
        String input =
            "<?xml version=\"1.0\"?>\r\n" + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n"
                + "    <Ontology/>\r\n" + "    <Class rdf:about=\"http://E\">\r\n"
                + "        <rdfs:comment>\r\n"
                + "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n"
                + "                <rdfs:comment>E</rdfs:comment>\r\n"
                + "            </rdf:Description>\r\n" + "        </rdfs:comment>\r\n"
                + "    </Class>\r\n" + "</rdf:RDF>";
        try {
            Set<OWLAnnotationValue> values1 = new HashSet<>();
            values1
                .add(m.getOWLDataFactory().getOWLAnonymousIndividual("_:genid-nodeid-1058025095"));
            OWLOntology o1 = loadOntologyFromString(input);
            for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                OWLAnnotationValue value = a.getValue();
                if (value instanceof OWLAnonymousIndividual) {
                    values1.add(value);
                }
            }
            o1 = loadOntologyFromString(input);
            for (OWLAnnotationAssertionAxiom a : o1.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                OWLAnnotationValue value = a.getValue();
                if (value instanceof OWLAnonymousIndividual) {
                    values1.add(value);
                }
            }
            assertEquals(values1.toString(), values1.size(), 1);
        } finally {
            // make sure config is restored
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    @Test
    public void shouldNotOutputNodeIdWhenNotNeeded()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input =
            "<?xml version=\"1.0\"?>\r\n" + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n"
                + "    <Ontology/>\r\n" + "    <Class rdf:about=\"http://E\">\r\n"
                + "        <rdfs:comment>\r\n"
                + "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n"
                + "                <rdfs:comment>E</rdfs:comment>\r\n"
                + "            </rdf:Description>\r\n" + "        </rdfs:comment>\r\n"
                + "    </Class>\r\n" + "</rdf:RDF>";
        OWLOntology o1 = loadOntologyFromString(input);
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertFalse(result.toString().contains("rdf:nodeID"));
    }

    @Test
    public void shouldOutputNodeIdEvenIfNotNeeded()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input =
            "<?xml version=\"1.0\"?>\r\n" + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n"
                + "    <Ontology/>\r\n" + "    <Class rdf:about=\"http://E\">\r\n"
                + "        <rdfs:comment>\r\n" + "            <rdf:Description>\r\n"
                + "                <rdfs:comment>E</rdfs:comment>\r\n"
                + "            </rdf:Description>\r\n" + "        </rdfs:comment>\r\n"
                + "    </Class>\r\n" + "</rdf:RDF>";
        OWLOntology o1 = loadOntologyFromString(input);
        AnonymousIndividualProperties.setSaveIdsForAllAnonymousIndividuals(true);
        try {
            StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
            assertTrue(result.toString().contains("rdf:nodeID"));
            OWLOntology reloaded = loadOntologyFromString(result);
            StringDocumentTarget resaved = saveOntology(reloaded, new RDFXMLDocumentFormat());
            assertEquals(result.toString(), resaved.toString());
        } finally {
            // make sure the static variable is reset after the test
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    @Test
    public void shouldOutputNodeIdWhenNeeded()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input =
            "<?xml version=\"1.0\"?>\r\n" + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xml:base=\"http://www.w3.org/2002/07/owl\"\r\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n"
                + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\r\n"
                + "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\r\n"
                + "    <Ontology/>\r\n" + "    <Class rdf:about=\"http://E\">\r\n"
                + "        <rdfs:comment>\r\n"
                + "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n"
                + "                <rdfs:comment>E</rdfs:comment>\r\n"
                + "            </rdf:Description>\r\n" + "        </rdfs:comment>\r\n"
                + "    </Class>\r\n" + "    <Class rdf:about=\"http://F\">\r\n"
                + "        <rdfs:comment>\r\n"
                + "            <rdf:Description rdf:nodeID=\"1058025095\">\r\n"
                + "                <rdfs:comment>E</rdfs:comment>\r\n"
                + "            </rdf:Description>\r\n" + "        </rdfs:comment>\r\n"
                + "    </Class>\r\n" + "</rdf:RDF>";
        OWLOntology o1 = loadOntologyFromString(input);
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertTrue(result.toString().contains("rdf:nodeID"));
    }
}
