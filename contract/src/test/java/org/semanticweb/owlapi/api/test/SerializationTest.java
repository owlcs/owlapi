package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings("javadoc")
public class SerializationTest {

    private static final OWLDataFactory f = OWLManager.getOWLDataFactory();
    OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    OWLDataPropertyExpression dp = f.getOWLDataProperty(IRI.create("urn:dp"));
    OWLObjectPropertyExpression op = f.getOWLObjectProperty(IRI.create("urn:op"));
    IRI iri = IRI.create("urn:iri");
    OWLLiteral owlliteral = f.getOWLLiteral(true);
    OWLAnnotationSubject as = IRI.create("urn:i");
    OWLDatatype owldatatype = f.getOWLDatatype(owl2datatype.getIRI());
    OWLDataRange dr = f.getOWLDatatypeRestriction(owldatatype);
    OWLAnnotationProperty ap = f.getOWLAnnotationProperty(IRI.create("urn:ap"));
    OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    OWLAnnotation owlannotation = f.getOWLAnnotation(ap, owlliteral);
    String string = "testString";
    OWLClassExpression c = f.getOWLClass(IRI.create("urn:classexpression"));
    PrefixManager prefixmanager = new DefaultPrefixManager();
    OWLIndividual ai = f.getOWLAnonymousIndividual();
    OWLAnnotationValue owlannotationvalue = owlliteral;
    Set<OWLObjectPropertyExpression> setop = new HashSet<OWLObjectPropertyExpression>();
    Set<OWLAnnotation> setowlannotation = new HashSet<OWLAnnotation>();
    Set<OWLDataPropertyExpression> setdp = new HashSet<OWLDataPropertyExpression>();
    List<OWLObjectPropertyExpression> listowlobjectpropertyexpression = new ArrayList<OWLObjectPropertyExpression>();
    Set<OWLIndividual> setowlindividual = new HashSet<OWLIndividual>();
    Set<OWLPropertyExpression<?, ?>> setowlpropertyexpression = new HashSet<OWLPropertyExpression<?, ?>>();
    OWLFacetRestriction[] lowlfacetrestriction = new OWLFacetRestriction[] { f.getOWLFacetRestriction(owlfacet, 1) };
    OWLFacetRestriction[] nulllowlfacetrestriction = new OWLFacetRestriction[] { f.getOWLFacetRestriction(owlfacet,
        1) };
    Set<OWLClassExpression> setowlclassexpression = new HashSet<OWLClassExpression>();
    Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<OWLFacetRestriction>();
    OWLPropertyExpression[] owlpropertyexpression = new OWLPropertyExpression[] {};
    IRI ontologyIRI = IRI.create("urn:src/test/resources/pizza.owl");
    protected OWLOntologyManager m;
    protected OWLOntology o;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        m = OWLManager.createOWLOntologyManager();
        m.addIRIMapper(new AutoIRIMapper(new File("."), false));
        o = m.createOntology(ontologyIRI);
    }

    protected void add(OWLAxiom ax) {
        m.addAxiom(o, ax);
    }

    @Test
    public void testrun() throws Exception {
        add(f.getOWLDeclarationAxiom(f.getOWLClass(iri)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLClass(string, prefixmanager)));
        add(f.getOWLEquivalentClassesAxiom(f.getOWLClass(iri), c));
        add(f.getOWLDisjointClassesAxiom(f.getOWLClass(iri), c));
        add(f.getOWLSubObjectPropertyOfAxiom(op, op));
        add(f.getOWLSubPropertyChainOfAxiom(listowlobjectpropertyexpression, op));
        add(f.getOWLEquivalentObjectPropertiesAxiom(setop));
        add(f.getOWLDisjointObjectPropertiesAxiom(setop));
        add(f.getOWLInverseObjectPropertiesAxiom(op, op));
        add(f.getOWLObjectPropertyDomainAxiom(op, c));
        add(f.getOWLObjectPropertyRangeAxiom(op, c));
        add(f.getOWLFunctionalObjectPropertyAxiom(op));
        add(f.getOWLAnnotationAssertionAxiom(ap, as, owlannotationvalue));
        m.applyChange(new AddImport(o, f.getOWLImportsDeclaration(iri)));
        add(f.getOWLAnnotationPropertyDomainAxiom(ap, iri));
        add(f.getOWLAnnotationPropertyRangeAxiom(ap, iri));
        add(f.getOWLSubAnnotationPropertyOfAxiom(ap, ap));
        add(f.getOWLInverseFunctionalObjectPropertyAxiom(op));
        add(f.getOWLReflexiveObjectPropertyAxiom(op));
        add(f.getOWLIrreflexiveObjectPropertyAxiom(op));
        add(f.getOWLSymmetricObjectPropertyAxiom(op));
        add(f.getOWLAsymmetricObjectPropertyAxiom(op));
        add(f.getOWLTransitiveObjectPropertyAxiom(op));
        add(f.getOWLSubDataPropertyOfAxiom(dp, dp));
        add(f.getOWLEquivalentDataPropertiesAxiom(setdp));
        add(f.getOWLDisjointDataPropertiesAxiom(setdp));
        add(f.getOWLDataPropertyDomainAxiom(dp, c));
        add(f.getOWLDataPropertyRangeAxiom(dp, dr));
        add(f.getOWLFunctionalDataPropertyAxiom(dp));
        add(f.getOWLHasKeyAxiom(c, setowlpropertyexpression));
        add(f.getOWLDatatypeDefinitionAxiom(owldatatype, dr));
        add(f.getOWLSameIndividualAxiom(setowlindividual));
        add(f.getOWLDifferentIndividualsAxiom(setowlindividual));
        add(f.getOWLClassAssertionAxiom(c, ai));
        add(f.getOWLObjectPropertyAssertionAxiom(op, ai, ai));
        add(f.getOWLNegativeObjectPropertyAssertionAxiom(op, ai, ai));
        add(f.getOWLDataPropertyAssertionAxiom(dp, ai, owlliteral));
        add(f.getOWLNegativeDataPropertyAssertionAxiom(dp, ai, owlliteral));
        add(f.getOWLInverseObjectPropertiesAxiom(op, f.getOWLObjectInverseOf(op)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLDataExactCardinality(1, dp)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLDataMaxCardinality(1, dp)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLDataMinCardinality(1, dp)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectExactCardinality(1, op)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectMaxCardinality(1, op)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectMinCardinality(1, op)));
        add(f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDatatype(string, prefixmanager)));
        add(f.getOWLDataPropertyAssertionAxiom(dp, ai, f.getOWLLiteral(string, owldatatype)));
        add(f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDataOneOf(owlliteral)));
        add(f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDataUnionOf(dr)));
        add(f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDataIntersectionOf(dr)));
        add(f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDatatypeRestriction(owldatatype, owlfacet, owlliteral)));
        add(f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDatatypeRestriction(owldatatype, f.getOWLFacetRestriction(
            owlfacet, 1))));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectIntersectionOf(c, f.getOWLClass(string, prefixmanager))));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLDataSomeValuesFrom(dp, dr)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLDataAllValuesFrom(dp, dr)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLDataHasValue(dp, owlliteral)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectComplementOf(f.getOWLClass(iri))));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectOneOf(f.getOWLNamedIndividual(iri))));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectAllValuesFrom(op, c)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectSomeValuesFrom(op, c)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectHasValue(op, ai)));
        add(f.getOWLSubClassOfAxiom(c, f.getOWLObjectUnionOf(f.getOWLClass(iri))));
        add(f.getOWLAnnotationAssertionAxiom(iri, f.getOWLAnnotation(ap, owlannotationvalue)));
        add(f.getOWLAnnotationAssertionAxiom(f.getOWLNamedIndividual(iri).getIRI(), f.getOWLAnnotation(ap,
            owlannotationvalue)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        OWLOntology o1 = copy.getOntology(ontologyIRI);
        assertEquals(o.getAxioms(), o1.getAxioms());
        assertEquals(o.getAxiomCount(), o1.getAxiomCount());
        assertEquals(o.getLogicalAxiomCount(), o1.getLogicalAxiomCount());
        assertEquals(o.getAnnotations(), o1.getAnnotations());
        assertEquals(o.getOntologyID(), o1.getOntologyID());
    }
}
