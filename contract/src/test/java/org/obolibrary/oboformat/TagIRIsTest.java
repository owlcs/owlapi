package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class TagIRIsTest extends TestBase {

    @Test
    void testTagIRIMapping() {
        OWLAnnotationProperty definition =
            df.getOWLAnnotationProperty(IRI.create("http://purl.obolibrary.org/obo/IAO_0000115"));
        OWLAnnotationProperty oioCreatedBy = df.getOWLAnnotationProperty(
            IRI.create("http://www.geneontology.org/formats/oboInOwl#created_by"));
        OWLAnnotationProperty oioInventedBy = df.getOWLAnnotationProperty(
            IRI.create("http://www.geneontology.org/formats/oboInOwl#invented_by"));
        OWLAnnotationProperty source =
            df.getOWLAnnotationProperty(IRI.create("http://purl.obolibrary.org/obo/MYONT_20"));
        OWLOntology ont = load("obo/tag_iris.obo", m);
        Set<OWLAxiom> axioms = ont.getAxioms();
        OWLClass term1 = df.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_1"));
        OWLClass term2 = df.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_2"));
        OWLClass term3 = df.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_3"));
        OWLClass term4 = df.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/MYONT_4"));
        Set<OWLAnnotation> annotations = Stream
            .of(df.getOWLAnnotation(df.getRDFSComment(), string("Here is a sub-annotation.")),
                df.getOWLAnnotation(df.getRDFSSeeAlso(), string("A nested see also value.")))
            .collect(Collectors.toSet());
        assertTrue(axioms.contains(df.getOWLAnnotationAssertionAxiom(definition, term1.getIRI(),
            string("Definition of term one."), annotations)));
        assertTrue(axioms.contains(df.getOWLAnnotationAssertionAxiom(df.getRDFSSeeAlso(),
            term1.getIRI(), string("See also value."))));
        assertTrue(axioms.contains(df.getOWLAnnotationAssertionAxiom(definition, term2.getIRI(),
            string("Definition of term two."), Collections
                .singleton(df.getOWLAnnotation(source, string("A nested annotation value."))))));
        assertTrue(axioms.contains(df.getOWLAnnotationAssertionAxiom(definition, term3.getIRI(),
            string("Definition of term three."), Collections
                .singleton(df.getOWLAnnotation(source, string("A definition source value."))))));
        assertTrue(
            axioms.contains(
                df.getOWLAnnotationAssertionAxiom(oioCreatedBy, term3.getIRI(), string("goc:bro"))),
            "created_by is built in and should not be overridden by a typedef");
        assertTrue(
            axioms.contains(df.getOWLAnnotationAssertionAxiom(definition, term4.getIRI(),
                string("Definition of term four."),
                Collections
                    .singleton(df.getOWLAnnotation(oioInventedBy, string("An inventor value."))))),
            "An undeclared tag should have oio namespace");

    }

    protected OWLLiteral string(String l) {
        return df.getOWLLiteral(l, OWL2Datatype.XSD_STRING);
    }
}
