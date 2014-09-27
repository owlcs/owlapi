package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

@SuppressWarnings("javadoc")
public class SWRLAnnotationTestCase {

    String NS = "http://protege.org/ontologies/SWRLAnnotation.owl";
    OWLClass A;
    OWLClass B;
    OWLAxiom AXIOM;

    @Before
    public void setUp() {
        OWLDataFactory factory = Factory.getFactory();
        A = Class(IRI(NS + "#A"));
        B = Class(IRI(NS + "#B"));
        SWRLVariable x = factory.getSWRLVariable(IRI(NS + "#x"));
        SWRLAtom atom1 = factory.getSWRLClassAtom(A, x);
        SWRLAtom atom2 = factory.getSWRLClassAtom(B, x);
        Set<SWRLAtom> consequent = new TreeSet<SWRLAtom>();
        consequent.add(atom1);
        OWLAnnotation annotation = factory.getOWLAnnotation(RDFSComment(),
                Literal("Not a great rule"));
        Set<OWLAnnotation> annotations = new TreeSet<OWLAnnotation>();
        annotations.add(annotation);
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(atom2);
        AXIOM = factory.getSWRLRule(body, consequent, annotations);
        // System.out.println("Using " + AXIOM + " as a rule");
    }

    @Test
    public void shouldRoundTripAnnotation()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAxiom(AXIOM));
        String saved = saveOntology(ontology);
        ontology = loadOntology(saved);
        assertTrue(ontology.containsAxiom(AXIOM));
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        changes.add(new AddAxiom(ontology, AXIOM));
        manager.applyChanges(changes);
        return ontology;
    }

    public String saveOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        StringDocumentTarget target = new StringDocumentTarget();
        manager.saveOntology(ontology, target);
        return target.toString();
    }

    public OWLOntology loadOntology(String ontology)
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        return manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        ontology));
    }

    @Test
    public void replicateFailure() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF\n"
                + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "    xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\"\n"
                + "    xmlns=\"urn:test#\"\n"
                + "    xmlns:xsp=\"http://www.owl-ontologies.com/2005/08/07/xsp.owl#\"\n"
                + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "    xmlns:sqwrl=\"http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#\"\n"
                + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "    xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
                + "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n"
                + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "    xmlns:swrla=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#\"\n"
                + "  xml:base=\"urn:test\">\n"
                + "  <owl:Ontology rdf:about=\"\">\n"
                + "  </owl:Ontology>\n"
                + "  <owl:AnnotationProperty rdf:about=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled\"/>\n"
                + "  <owl:ObjectProperty rdf:ID=\"hasDriver\">\n"
                + "    <owl:inverseOf>\n"
                + "      <owl:ObjectProperty rdf:ID=\"drives\"/>\n"
                + "    </owl:inverseOf>\n"
                + "  </owl:ObjectProperty>\n"
                + "  <owl:ObjectProperty rdf:about=\"#drives\">\n"
                + "    <owl:inverseOf rdf:resource=\"#hasDriver\"/>\n"
                + "  </owl:ObjectProperty>\n"
                + "  <swrl:Imp rdf:ID=\"test-table5-prp-inv2-rule\">\n"
                + "    <swrl:body>\n"
                + "      <swrl:AtomList/>\n"
                + "    </swrl:body>\n"
                + "    <swrl:head>\n"
                + "      <swrl:AtomList>\n"
                + "        <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
                + "        <rdf:first>\n"
                + "          <swrl:IndividualPropertyAtom>\n"
                + "            <swrl:argument2>\n"
                + "              <Person rdf:ID=\"i62\"/>\n"
                + "            </swrl:argument2>\n"
                + "            <swrl:argument1>\n"
                + "              <Person rdf:ID=\"i61\"/>\n"
                + "            </swrl:argument1>\n"
                + "            <swrl:propertyPredicate rdf:resource=\"#drives\"/>\n"
                + "          </swrl:IndividualPropertyAtom>\n"
                + "        </rdf:first>\n"
                + "      </swrl:AtomList>\n"
                + "    </swrl:head>\n"
                + "    <swrla:isRuleEnabled rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\"\n"
                + "    >true</swrla:isRuleEnabled>\n"
                + "    <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n"
                + "    >:i62, :i61</rdfs:comment>\n" + "  </swrl:Imp>\n"
                + "</rdf:RDF>";
        OWLOntology ontology = loadOntology(input);
        assertTrue(ontology
                .getAxioms(AxiomType.SWRL_RULE)
                .toString()
                .contains(
                        "DLSafeRule(Annotation(<http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled> \"true\"^^xsd:boolean) Annotation(rdfs:comment \":i62, :i61\"^^xsd:string)  Body() Head(ObjectPropertyAtom(<#drives> <#i61> <#i62>)) )"));
    }

    @Test
    public void replicateSuccess() throws Exception {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF\n"
                + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "    xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\"\n"
                + "    xmlns=\"urn:test#\"\n"
                + "    xmlns:xsp=\"http://www.owl-ontologies.com/2005/08/07/xsp.owl#\"\n"
                + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "    xmlns:sqwrl=\"http://sqwrl.stanford.edu/ontologies/built-ins/3.4/sqwrl.owl#\"\n"
                + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "    xmlns:swrl=\"http://www.w3.org/2003/11/swrl#\"\n"
                + "    xmlns:swrlb=\"http://www.w3.org/2003/11/swrlb#\"\n"
                + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "    xmlns:swrla=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#\"\n"
                + "  xml:base=\"urn:test\">\n"
                + "  <owl:Ontology rdf:about=\"\">\n"
                + "  </owl:Ontology>\n"
                + "  <owl:AnnotationProperty rdf:about=\"http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled\"/>\n"
                + "  <owl:ObjectProperty rdf:ID=\"hasDriver\">\n"
                + "    <owl:inverseOf>\n"
                + "      <owl:ObjectProperty rdf:ID=\"drives\"/>\n"
                + "    </owl:inverseOf>\n"
                + "  </owl:ObjectProperty>\n"
                + "  <owl:ObjectProperty rdf:about=\"#drives\">\n"
                + "    <owl:inverseOf rdf:resource=\"#hasDriver\"/>\n"
                + "  </owl:ObjectProperty>\n"
                + "  <swrl:Imp>\n"
                + "    <swrl:body>\n"
                + "      <swrl:AtomList/>\n"
                + "    </swrl:body>\n"
                + "    <swrl:head>\n"
                + "      <swrl:AtomList>\n"
                + "        <rdf:rest rdf:resource=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil\"/>\n"
                + "        <rdf:first>\n"
                + "          <swrl:IndividualPropertyAtom>\n"
                + "            <swrl:argument2>\n"
                + "              <Person rdf:ID=\"i62\"/>\n"
                + "            </swrl:argument2>\n"
                + "            <swrl:argument1>\n"
                + "              <Person rdf:ID=\"i61\"/>\n"
                + "            </swrl:argument1>\n"
                + "            <swrl:propertyPredicate rdf:resource=\"#drives\"/>\n"
                + "          </swrl:IndividualPropertyAtom>\n"
                + "        </rdf:first>\n"
                + "      </swrl:AtomList>\n"
                + "    </swrl:head>\n"
                + "    <swrla:isRuleEnabled rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</swrla:isRuleEnabled>\n"
                + "    <rdfs:comment rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">:i62, :i61</rdfs:comment>\n"
                + "  </swrl:Imp>\n" + "</rdf:RDF>";
        OWLOntology ontology = loadOntology(input);
        assertTrue(ontology
                .getAxioms(AxiomType.SWRL_RULE)
                .toString()
                .contains(
                        "DLSafeRule(Annotation(<http://swrl.stanford.edu/ontologies/3.3/swrla.owl#isRuleEnabled> \"true\"^^xsd:boolean) Annotation(rdfs:comment \":i62, :i61\"^^xsd:string)  Body() Head(ObjectPropertyAtom(<#drives> <#i61> <#i62>)) )"));
    }
}
