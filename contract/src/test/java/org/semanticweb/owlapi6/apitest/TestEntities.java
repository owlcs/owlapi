package org.semanticweb.owlapi6.apitest;

import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.apitest.baseclasses.TestBase.iri;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

public class TestEntities {

    public static final OWLObjectProperty S = ObjectProperty(iri("s"));
    public static final OWLClass K = Class(iri("K"));
    public static final OWLClass G = Class(iri("G"));
    public static final OWLClass F = Class(iri("F"));
    public static final OWLClass E = Class(iri("E"));
    public static final OWLClass D = Class(iri("D"));
    public static final OWLClass C = Class(iri("C"));
    public static final OWLDataProperty DR = DataProperty(iri("r"));
    public static final OWLObjectProperty R = ObjectProperty(iri("r"));
    public static final OWLClass B = Class(iri("B"));
    public static final OWLClass A = Class(iri("A"));
    public static final OWLDataProperty DQ = DataProperty(iri("q"));
    public static final OWLDataProperty DPROP = DataProperty(iri("prop"));
    public static final OWLObjectProperty PROP = ObjectProperty(iri("prop"));
    public static final OWLObjectProperty Q = ObjectProperty(iri("q"));
    public static final OWLDataProperty DP = DataProperty(iri("p"));
    public static final OWLObjectProperty P = ObjectProperty(iri("p"));
    public static final OWLNamedIndividual J = NamedIndividual(iri("j"));
    public static final OWLNamedIndividual I = NamedIndividual(iri("i"));
    public static final OWLNamedIndividual indA = NamedIndividual(iri("a"));
    public static final OWLObjectComplementOf notC = ObjectComplementOf(C);
    public static final OWLObjectComplementOf notB = ObjectComplementOf(B);
    public static final OWLObjectComplementOf notA = ObjectComplementOf(A);
    public static final OWLNamedIndividual i = NamedIndividual(iri("I"));
    public static final OWLAnnotationProperty AP = AnnotationProperty(iri("propA"));
    public static final OWLDataProperty PD = DataProperty(iri("propD"));
    public static final OWLDatatype DT = Datatype(iri("DT"));
    public static final OWLAnnotationProperty areaTotal =
        AnnotationProperty(IRI("http://dbpedia.org/ontology/", "areaTotal"));
    public static final IRI southAfrica = IRI("http://dbpedia.org/resource/", "South_Africa");
    public static final OWLLiteral oneMillionth = Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE);
}
