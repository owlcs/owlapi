package org.obolibrary.oboformat;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagIRIsTest extends TestBase {

    @Test
    public void testTagIRIMapping() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLAnnotationProperty definition = factory.getOWLAnnotationProperty(IRI.create("http://purl.obolibrary.org/obo/IAO_0000115"));
        OWLAnnotationProperty oioCreatedBy = factory.getOWLAnnotationProperty(IRI.create("http://www.geneontology.org/formats/oboInOwl#created_by"));
        OWLAnnotationProperty oioInventedBy = factory.getOWLAnnotationProperty(IRI.create("http://www.geneontology.org/formats/oboInOwl#invented_by"));
        OWLAnnotationProperty source = factory.getOWLAnnotationProperty(IRI.create("http://purl.obolibrary.org/obo/MYONT_20"));
        OWLOntology ont = loadOntology("obo/tag_iris.obo", manager);
        Set<OWLAxiom> axioms = ont.getAxioms();
        OWLClass term1 = factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_1"));
        OWLClass term2 = factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_2"));
        OWLClass term3 = factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_3"));
        OWLClass term4 = factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_4"));
        assertTrue(axioms.contains(
                factory.getOWLAnnotationAssertionAxiom(definition, term1.getIRI(), factory.getOWLLiteral("Definition of term one.", OWL2Datatype.XSD_STRING),
                        Stream.of(
                                        factory.getOWLAnnotation(factory.getRDFSComment(), factory.getOWLLiteral("Here is a sub-annotation.", OWL2Datatype.XSD_STRING)),
                                        factory.getOWLAnnotation(factory.getRDFSSeeAlso(), factory.getOWLLiteral("A nested see also value.", OWL2Datatype.XSD_STRING)))
                                .collect(Collectors.toSet())
                )));
        assertTrue(axioms.contains(
                factory.getOWLAnnotationAssertionAxiom(factory.getRDFSSeeAlso(), term1.getIRI(), factory.getOWLLiteral("See also value.", OWL2Datatype.XSD_STRING))));
        assertTrue(axioms.contains(
                factory.getOWLAnnotationAssertionAxiom(definition, term2.getIRI(), factory.getOWLLiteral("Definition of term two.", OWL2Datatype.XSD_STRING),
                        Collections.singleton(factory.getOWLAnnotation(source, factory.getOWLLiteral("A nested annotation value.", OWL2Datatype.XSD_STRING))))));
        assertTrue(axioms.contains(
                factory.getOWLAnnotationAssertionAxiom(definition, term3.getIRI(), factory.getOWLLiteral("Definition of term three.", OWL2Datatype.XSD_STRING),
                        Collections.singleton(factory.getOWLAnnotation(source, factory.getOWLLiteral("A definition source value.", OWL2Datatype.XSD_STRING))))));
        assertTrue(axioms.contains(
                factory.getOWLAnnotationAssertionAxiom(oioCreatedBy, term3.getIRI(), factory.getOWLLiteral("goc:bro", OWL2Datatype.XSD_STRING))), "created_by is built in and should not be overridden by a typedef");
        assertTrue(axioms.contains(
                factory.getOWLAnnotationAssertionAxiom(definition, term4.getIRI(), factory.getOWLLiteral("Definition of term four.", OWL2Datatype.XSD_STRING),
                        Collections.singleton(factory.getOWLAnnotation(oioInventedBy, factory.getOWLLiteral("An inventor value.", OWL2Datatype.XSD_STRING))))), "An undeclared tag should have oio namespace");

    }
}
