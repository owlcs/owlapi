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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * test for 3294629 - currently disabled. Not clear whether structure sharing is allowed or
 * disallowed. Data is equivalent, ontology annotations are not
 */
@SuppressWarnings("javadoc")
public class SharedBlankNodeTestCase extends TestBase {

    @Test
    public void shouldSaveOneIndividual() throws Exception {
        OWLOntology ontology = createOntology();
        StringDocumentTarget s = saveOntology(ontology, new RDFXMLDocumentFormat());
        StringDocumentTarget functionalSyntax =
                        saveOntology(ontology, new FunctionalSyntaxDocumentFormat());
        testAnnotation(loadOntologyFromString(functionalSyntax,
                        new FunctionalSyntaxDocumentFormat()));
        testAnnotation(loadOntologyFromString(s, new RDFXMLDocumentFormat()));
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
        String NS = "urn:test";
        OWLDataProperty P = DataProperty(IRI(NS + "#", "p"));
        OWLObjectProperty P1 = ObjectProperty(IRI(NS + "#", "p1"));
        OWLObjectProperty P2 = ObjectProperty(IRI(NS + "#", "p2"));
        OWLAnnotationProperty ann = AnnotationProperty(IRI(NS + "#", "ann"));
        OWLOntology ontology = m.createOntology(IRI(NS, ""));
        OWLAnonymousIndividual i = AnonymousIndividual();
        m.addAxiom(ontology, Declaration(P));
        m.addAxiom(ontology, Declaration(P1));
        m.addAxiom(ontology, Declaration(P2));
        m.addAxiom(ontology, Declaration(ann));
        m.applyChange(new AddOntologyAnnotation(ontology, Annotation(ann, i)));
        OWLAxiom ass = DataPropertyAssertion(P, i, Literal("hello world"));
        OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#", "test"));
        OWLAxiom ax1 = ObjectPropertyAssertion(P1, ind, i);
        OWLAxiom ax2 = ObjectPropertyAssertion(P2, ind, i);
        m.addAxiom(ontology, ass);
        m.addAxiom(ontology, ax1);
        m.addAxiom(ontology, ax2);
        return ontology;
    }

    public static void testAnnotation(OWLOntology o) {
        o.individualsInSignature()
                        .forEach(i -> assertEquals(2L, o.objectPropertyAssertionAxioms(i).count()));
        o.annotations().map(a -> (OWLIndividual) a.getValue())
                        .forEach(i -> assertEquals(1L, o.dataPropertyAssertionAxioms(i).count()));
    }

    @Test
    public void shouldRoundtripBlankNodeAnnotations() throws OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Class rdf:about=\"http://E\"><rdfs:comment><rdf:Description><rdfs:comment>E</rdfs:comment></rdf:Description></rdfs:comment></owl:Class></rdf:RDF>";
        OWLOntology o = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(
                        saveOntology(o, new FunctionalSyntaxDocumentFormat()),
                        new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 = loadOntologyFromString(saveOntology(o1, new RDFXMLDocumentFormat()),
                        new RDFXMLDocumentFormat());
        assertEquals(1L, o2.annotationAssertionAxioms(IRI("http://E", "")).count());
        Stream<OWLAnnotationSubject> s = o2.annotationAssertionAxioms(IRI("http://E", ""))
                        .map(a -> (OWLAnnotationSubject) a.getValue());
        s.forEach(a -> assertEquals(1L, o2.annotationAssertionAxioms(a).count()));
    }

    @Test
    public void shouldRemapUponReading() {
        String input = "Prefix(rdf:=<http://www.w3.org/1999/02/22-rdf-syntax-ns#>)\r\n"
                        + "Prefix(xml:=<http://www.w3.org/XML/1998/namespace>)\r\n"
                        + "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\r\n"
                        + "Prefix(rdfs:=<http://www.w3.org/2000/01/rdf-schema#>)\r\n"
                        + "Ontology(\r\n" + "Declaration(Class(<http://E>))\r\n"
                        + "AnnotationAssertion(rdfs:comment <http://E> _:genid1)\r\n"
                        + "AnnotationAssertion(rdfs:comment _:genid1 \"E\"))";
        OWLOntology o1 = loadOntologyFromString(input, new FunctionalSyntaxDocumentFormat());
        OWLOntology o2 = loadOntologyFromString(input, new FunctionalSyntaxDocumentFormat());
        Set<OWLAnnotationValue> values1 = asUnorderedSet(o1.axioms(AxiomType.ANNOTATION_ASSERTION)
                        .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        Set<OWLAnnotationValue> values2 = asUnorderedSet(o2.axioms(AxiomType.ANNOTATION_ASSERTION)
                        .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(values1.toString(), values1.size(), 1);
        assertEquals(values1.toString(), values2.size(), 1);
        assertNotEquals(values1, values2);
    }

    @Test
    public void shouldHaveOnlyOneAnonIndividual() {
        String input = "<?xml version=\"1.0\"?>\r\n<rdf:RDF xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"><owl:Class rdf:about=\"http://E\"><rdfs:comment><rdf:Description><rdfs:comment>E</rdfs:comment></rdf:Description></rdfs:comment></owl:Class></rdf:RDF>";
        OWLOntology o1 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        OWLOntology o2 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        Set<OWLAnnotationValue> values1 = asUnorderedSet(o1.axioms(AxiomType.ANNOTATION_ASSERTION)
                        .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        Set<OWLAnnotationValue> values2 = asUnorderedSet(o2.axioms(AxiomType.ANNOTATION_ASSERTION)
                        .map(a -> a.getValue()).filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(values1.toString(), values1.size(), 1);
        assertEquals(values1.toString(), values2.size(), 1);
        assertNotEquals(values1, values2);
    }

    @Test
    public void shouldNotRemapUponReloading() {
        m.getOntologyConfigurator().withRemapAllAnonymousIndividualsIds(false);
        String input = "<?xml version=\"1.0\"?>\r\n"
                        + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
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
        Set<OWLAnnotationValue> values1 = new HashSet<>();
        values1.add(m.getOWLDataFactory().getOWLAnonymousIndividual("_:genid-nodeid-1058025095"));
        OWLOntology o1 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        add(values1, o1.axioms(AxiomType.ANNOTATION_ASSERTION).map(a -> a.getValue())
                        .filter(a -> a instanceof OWLAnonymousIndividual));
        o1 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        add(values1, o1.axioms(AxiomType.ANNOTATION_ASSERTION).map(a -> a.getValue())
                        .filter(a -> a instanceof OWLAnonymousIndividual));
        assertEquals(values1.toString(), values1.size(), 1);
        m.getOntologyConfigurator().withRemapAllAnonymousIndividualsIds(true);
    }

    @Test
    public void shouldNotOutputNodeIdWhenNotNeeded() throws OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n"
                        + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
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
        OWLOntology o1 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertFalse(result.toString().contains("rdf:nodeID"));
    }

    @Test
    public void shouldOutputNodeIdEvenIfNotNeeded() throws OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n"
                        + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
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
        OWLOntology o1 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        masterConfigurator.withSaveIdsForAllAnonymousIndividuals(true);
        try {
            StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
            assertTrue(result.toString().contains("rdf:nodeID"));
            OWLOntology reloaded = loadOntologyFromString(result, new RDFXMLDocumentFormat());
            StringDocumentTarget resaved = saveOntology(reloaded, new RDFXMLDocumentFormat());
            assertEquals(result.toString(), resaved.toString());
        } finally {
            // make sure the static variable is reset after the test
            masterConfigurator.withSaveIdsForAllAnonymousIndividuals(false);
        }
    }

    @Test
    public void shouldOutputNodeIdWhenNeeded() throws OWLOntologyStorageException {
        String input = "<?xml version=\"1.0\"?>\r\n"
                        + "<rdf:RDF xmlns=\"http://www.w3.org/2002/07/owl#\"\r\n"
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
        OWLOntology o1 = loadOntologyFromString(input, new RDFXMLDocumentFormat());
        StringDocumentTarget result = saveOntology(o1, new RDFXMLDocumentFormat());
        assertTrue(result.toString().contains("rdf:nodeID"));
    }
}
