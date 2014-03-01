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

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings("javadoc")
public class SerializationTest {

    private static final OWLDataFactory f = OWLManager.getOWLDataFactory();
    OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    OWLDataPropertyExpression dp = f.getOWLDataProperty(IRI.create("urn:dp"));
    OWLObjectPropertyExpression op = f.getOWLObjectProperty(IRI
            .create("urn:op"));
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
    OWLFacetRestriction[] lowlfacetrestriction = new OWLFacetRestriction[] { f
            .getOWLFacetRestriction(owlfacet, 1) };
    OWLFacetRestriction[] nulllowlfacetrestriction = new OWLFacetRestriction[] { f
            .getOWLFacetRestriction(owlfacet, 1) };
    Set<OWLClassExpression> setowlclassexpression = new HashSet<OWLClassExpression>();
    Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<OWLFacetRestriction>();
    OWLPropertyExpression[] owlpropertyexpression = new OWLPropertyExpression[] {};

    @Test
    public void testrun() throws Exception {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.addIRIMapper(new AutoIRIMapper(new File("."), false));
        OWLOntology o = m.createOntology(IRI
                .create("urn:src/test/resources/pizza.owl"));
        m.addAxiom(o, f.getOWLDeclarationAxiom(f.getOWLClass(iri)));
        m.addAxiom(o, f.getOWLSubClassOfAxiom(c,
                f.getOWLClass(string, prefixmanager)));
        m.addAxiom(o, f.getOWLEquivalentClassesAxiom(f.getOWLClass(iri), c));
        m.addAxiom(o, f.getOWLDisjointClassesAxiom(f.getOWLClass(iri), c));
        m.addAxiom(o, f.getOWLSubObjectPropertyOfAxiom(op, op));
        m.addAxiom(o, f.getOWLSubPropertyChainOfAxiom(
                listowlobjectpropertyexpression, op));
        m.addAxiom(o, f.getOWLEquivalentObjectPropertiesAxiom(setop));
        m.addAxiom(o, f.getOWLDisjointObjectPropertiesAxiom(setop));
        m.addAxiom(o, f.getOWLInverseObjectPropertiesAxiom(op, op));
        m.addAxiom(o, f.getOWLObjectPropertyDomainAxiom(op, c));
        m.addAxiom(o, f.getOWLObjectPropertyRangeAxiom(op, c));
        m.addAxiom(o, f.getOWLFunctionalObjectPropertyAxiom(op));
        m.addAxiom(o,
                f.getOWLAnnotationAssertionAxiom(ap, as, owlannotationvalue));
        m.applyChange(new AddImport(o, f.getOWLImportsDeclaration(iri)));
        m.addAxiom(o, f.getOWLAnnotationPropertyDomainAxiom(ap, iri));
        m.addAxiom(o, f.getOWLAnnotationPropertyRangeAxiom(ap, iri));
        m.addAxiom(o, f.getOWLSubAnnotationPropertyOfAxiom(ap, ap));
        m.addAxiom(o, f.getOWLInverseFunctionalObjectPropertyAxiom(op));
        m.addAxiom(o, f.getOWLReflexiveObjectPropertyAxiom(op));
        m.addAxiom(o, f.getOWLIrreflexiveObjectPropertyAxiom(op));
        m.addAxiom(o, f.getOWLSymmetricObjectPropertyAxiom(op));
        m.addAxiom(o, f.getOWLAsymmetricObjectPropertyAxiom(op));
        m.addAxiom(o, f.getOWLTransitiveObjectPropertyAxiom(op));
        m.addAxiom(o, f.getOWLSubDataPropertyOfAxiom(dp, dp));
        m.addAxiom(o, f.getOWLEquivalentDataPropertiesAxiom(setdp));
        m.addAxiom(o, f.getOWLDisjointDataPropertiesAxiom(setdp));
        m.addAxiom(o, f.getOWLDataPropertyDomainAxiom(dp, c));
        m.addAxiom(o, f.getOWLDataPropertyRangeAxiom(dp, dr));
        m.addAxiom(o, f.getOWLFunctionalDataPropertyAxiom(dp));
        m.addAxiom(o, f.getOWLHasKeyAxiom(c, setowlpropertyexpression));
        m.addAxiom(o, f.getOWLDatatypeDefinitionAxiom(owldatatype, dr));
        m.addAxiom(o, f.getOWLSameIndividualAxiom(setowlindividual));
        m.addAxiom(o, f.getOWLDifferentIndividualsAxiom(setowlindividual));
        m.addAxiom(o, f.getOWLClassAssertionAxiom(c, ai));
        m.addAxiom(o, f.getOWLObjectPropertyAssertionAxiom(op, ai, ai));
        m.addAxiom(o, f.getOWLNegativeObjectPropertyAssertionAxiom(op, ai, ai));
        m.addAxiom(o, f.getOWLDataPropertyAssertionAxiom(dp, ai, owlliteral));
        m.addAxiom(o,
                f.getOWLNegativeDataPropertyAssertionAxiom(dp, ai, owlliteral));
        m.addAxiom(
                o,
                f.getOWLInverseObjectPropertiesAxiom(op,
                        f.getOWLObjectInverseOf(op)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLDataExactCardinality(1, dp)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLDataMaxCardinality(1, dp)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLDataMinCardinality(1, dp)));
        m.addAxiom(
                o,
                f.getOWLSubClassOfAxiom(c,
                        f.getOWLObjectExactCardinality(1, op)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLObjectMaxCardinality(1, op)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLObjectMinCardinality(1, op)));
        m.addAxiom(
                o,
                f.getOWLDataPropertyRangeAxiom(dp,
                        f.getOWLDatatype(string, prefixmanager)));
        m.addAxiom(
                o,
                f.getOWLDataPropertyAssertionAxiom(dp, ai,
                        f.getOWLLiteral(string, owldatatype)));
        m.addAxiom(
                o,
                f.getOWLDataPropertyRangeAxiom(dp,
                        f.getOWLDataOneOf(owlliteral)));
        m.addAxiom(o,
                f.getOWLDataPropertyRangeAxiom(dp, f.getOWLDataUnionOf(dr)));
        m.addAxiom(
                o,
                f.getOWLDataPropertyRangeAxiom(dp,
                        f.getOWLDataIntersectionOf(dr)));
        m.addAxiom(o, f.getOWLDataPropertyRangeAxiom(dp,
                f.getOWLDatatypeRestriction(owldatatype, owlfacet, owlliteral)));
        m.addAxiom(
                o,
                f.getOWLDataPropertyRangeAxiom(
                        dp,
                        f.getOWLDatatypeRestriction(owldatatype,
                                f.getOWLFacetRestriction(owlfacet, 1))));
        m.addAxiom(
                o,
                f.getOWLSubClassOfAxiom(
                        c,
                        f.getOWLObjectIntersectionOf(c,
                                f.getOWLClass(string, prefixmanager))));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLDataSomeValuesFrom(dp, dr)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLDataAllValuesFrom(dp, dr)));
        m.addAxiom(o, f.getOWLSubClassOfAxiom(c,
                f.getOWLDataHasValue(dp, owlliteral)));
        m.addAxiom(
                o,
                f.getOWLSubClassOfAxiom(c,
                        f.getOWLObjectComplementOf(f.getOWLClass(iri))));
        m.addAxiom(
                o,
                f.getOWLSubClassOfAxiom(c,
                        f.getOWLObjectOneOf(f.getOWLNamedIndividual(iri))));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLObjectAllValuesFrom(op, c)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLObjectSomeValuesFrom(op, c)));
        m.addAxiom(o,
                f.getOWLSubClassOfAxiom(c, f.getOWLObjectHasValue(op, ai)));
        m.addAxiom(
                o,
                f.getOWLSubClassOfAxiom(c,
                        f.getOWLObjectUnionOf(f.getOWLClass(iri))));
        m.addAxiom(
                o,
                f.getOWLAnnotationAssertionAxiom(iri,
                        f.getOWLAnnotation(ap, owlannotationvalue)));
        m.addAxiom(o, f.getOWLAnnotationAssertionAxiom(
                f.getOWLNamedIndividual(iri).getIRI(),
                f.getOWLAnnotation(ap, owlannotationvalue)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(m);
        stream.flush();
        // System.out.println(out.toString());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(in);
        OWLOntologyManager copy = (OWLOntologyManager) inStream.readObject();
        for (OWLOntology onto : copy.getOntologies()) {
            OWLOntology original = m.getOntology(onto.getOntologyID()
                    .getOntologyIRI());
            assertEquals("Troubles with ontology " + onto.getOntologyID(),
                    original.getAxioms(), onto.getAxioms());
        }
    }
}
