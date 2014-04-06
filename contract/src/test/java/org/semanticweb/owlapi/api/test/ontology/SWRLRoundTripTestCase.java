package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class SWRLRoundTripTestCase {

    private OWLDataFactory factory = Factory.getFactory();

    // test for 3562978
    @Test
    public void shouldDoCompleteRoundtrip()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String NS = "urn:test";
        OWLClass A = Class(IRI(NS + "#A"));
        OWLDataProperty P = factory.getOWLDataProperty(IRI(NS + "#P"));
        SWRLVariable X = factory.getSWRLVariable(IRI(NS + "#X"));
        SWRLVariable Y = factory.getSWRLVariable(IRI(NS + "#Y"));
        OWLOntology ontology = Factory.getManager().createOntology(IRI(NS));
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(factory.getSWRLDataPropertyAtom(P, X, Y));
        body.add(factory.getSWRLDataRangeAtom(
                factory.getOWLDatatype(XSDVocabulary.STRING.getIRI()), Y));
        Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
        head.add(factory.getSWRLClassAtom(A, X));
        SWRLRule rule = factory.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        StringDocumentTarget t = new StringDocumentTarget();
        OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto1 = t.toString();
        ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(t.toString()));
        t = new StringDocumentTarget();
        format = new OWLXMLOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto2 = t.toString();
        assertEquals(onto1, onto2);
    }

    private StringDocumentTarget save(OWLOntology o, OWLOntologyFormat f)
            throws OWLOntologyStorageException {
        StringDocumentTarget t = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o, f, t);
        return t;
    }

    private OWLOntology load(StringDocumentTarget source)
            throws OWLOntologyCreationException {
        return Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(source));
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsOWLXML()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = prepareOntology();
        OWLXMLOntologyFormat f = new OWLXMLOntologyFormat();
        OWLOntology ontology2 = load(save(ontology, f));
        AbstractOWLAPITestCase.equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsTurtle()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = prepareOntology();
        OWLOntologyFormat f = new TurtleOntologyFormat();
        OWLOntology ontology2 = load(save(ontology, f));
        AbstractOWLAPITestCase.equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsFunctional()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = prepareOntology();
        OWLOntologyFormat f = new OWLFunctionalSyntaxOntologyFormat();
        OWLOntology ontology2 = load(save(ontology, f));
        AbstractOWLAPITestCase.equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
    }

    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsRDFXML()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = prepareOntology();
        OWLOntologyFormat f = new RDFXMLOntologyFormat();
        OWLOntology ontology2 = load(save(ontology, f));
        AbstractOWLAPITestCase.equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
    }

    @Ignore
    @Test
    public void shouldDoCompleteRoundtripWithAnnotationsMan()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ontology = prepareOntology();
        OWLOntologyFormat f = new ManchesterOWLSyntaxOntologyFormat();
        StringDocumentTarget save = save(ontology, f);
        System.out
                .println("SWRLRoundTripTestCase.shouldDoCompleteRoundtripWithAnnotationsMan()\n"
                        + save);
        OWLOntology ontology2 = load(save);
        AbstractOWLAPITestCase.equal(ontology, ontology2);
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.SWRL_RULE)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
        for (OWLAxiom r : ontology2.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
            assertFalse(r.getAnnotations(factory.getRDFSLabel()).isEmpty());
        }
    }

    /**
     * @return
     * @throws OWLOntologyCreationException
     */
    OWLOntology prepareOntology() throws OWLOntologyCreationException {
        String NS = "urn:test";
        OWLClass A = Class(IRI(NS + "#A"));
        OWLDataProperty P = factory.getOWLDataProperty(IRI(NS + "#P"));
        SWRLVariable X = factory.getSWRLVariable(IRI(NS + "#X"));
        SWRLVariable Y = factory.getSWRLVariable(IRI(NS + "#Y"));
        OWLOntology ontology = Factory.getManager().createOntology(IRI(NS));
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(factory.getSWRLDataPropertyAtom(P, X, Y));
        body.add(factory.getSWRLDataRangeAtom(
                factory.getOWLDatatype(XSDVocabulary.STRING.getIRI()), Y));
        Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
        head.add(factory.getSWRLClassAtom(A, X));
        OWLAnnotation ann = factory.getOWLAnnotation(factory.getRDFSLabel(),
                factory.getOWLLiteral("test", ""));
        SWRLRule rule = factory.getSWRLRule(body, head,
                Collections.singleton(ann));
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        OWLDatatypeDefinitionAxiom def = factory.getOWLDatatypeDefinitionAxiom(
                factory.getOWLDatatype(IRI.create("urn:mydatatype")),
                factory.getOWLDatatypeMaxExclusiveRestriction(200D),
                Collections.singleton(factory.getOWLAnnotation(
                        factory.getRDFSLabel(),
                        factory.getOWLLiteral("datatype definition"))));
        ontology.getOWLOntologyManager().addAxiom(ontology, def);
        return ontology;
    }

    @Test
    public void shouldDoCompleteRoundtripManchesterOWLSyntax()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String NS = "urn:test";
        OWLClass A = Class(IRI(NS + "#A"));
        OWLDataProperty P = factory.getOWLDataProperty(IRI(NS + "#P"));
        SWRLVariable X = factory.getSWRLVariable(IRI(NS + "#X"));
        SWRLVariable Y = factory.getSWRLVariable(IRI(NS + "#Y"));
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
        body.add(factory.getSWRLDataPropertyAtom(P, X, Y));
        body.add(factory.getSWRLDataRangeAtom(
                factory.getOWLDatatype(XSDVocabulary.STRING.getIRI()), Y));
        Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
        head.add(factory.getSWRLClassAtom(A, X));
        SWRLRule rule = factory.getSWRLRule(body, head);
        ontology.getOWLOntologyManager().addAxiom(ontology, rule);
        StringDocumentTarget t = new StringDocumentTarget();
        ManchesterOWLSyntaxOntologyFormat format = new ManchesterOWLSyntaxOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto1 = t.toString();
        manager = Factory.getManager();
        ontology = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        onto1));
        t = new StringDocumentTarget();
        format = new ManchesterOWLSyntaxOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto2 = t.toString();
        assertEquals(onto1, onto2);
    }
}
