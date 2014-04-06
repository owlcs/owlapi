package org.semanticweb.owlapi.api.test;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings({ "javadoc", "deprecation" })
public class NullCheckTestCase {

    private static final OWLDataFactory f = Factory.getFactory();
    OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    OWLDataPropertyExpression owldpe = f.getOWLDataProperty(IRI("urn:dp"));
    OWLObjectPropertyExpression ope = f.getOWLObjectProperty(IRI("urn:op"));
    IRI iri = IRI("urn:iri");
    OWLLiteral lit = f.getOWLLiteral(true);
    OWLAnnotationSubject owlannsubj = IRI("urn:i");
    OWLDatatype owldatatype = f.getOWLDatatype(owl2datatype.getIRI());
    OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    OWLFacetRestriction[] lowlfacetrestriction = new OWLFacetRestriction[] { f
            .getOWLFacetRestriction(owlfacet, 1) };
    OWLDataRange owldatarange = f.getOWLDatatypeRestriction(owldatatype,
            lowlfacetrestriction);
    OWLAnnotationProperty owlap = f.getOWLAnnotationProperty(IRI("urn:ap"));
    OWLAnnotation owlannotation = f.getOWLAnnotation(owlap, lit);
    String string = "testString";
    OWLClass owlclass = f.getOWLClass(IRI("urn:classexpression"));
    OWLClassExpression ce = owlclass;
    PrefixManager prefixmanager = new DefaultPrefixManager();
    OWLIndividual ind = f.getOWLAnonymousIndividual();
    OWLAnnotationValue owlannvalue = lit;
    Set<OWLObjectPropertyExpression> setowlope = new HashSet<OWLObjectPropertyExpression>();
    Set<OWLObjectPropertyExpression> nullSetOWLOPE = getNullSet();
    Set<OWLAnnotation> setowlann = new HashSet<OWLAnnotation>();
    Set<OWLAnnotation> nullsetowlann = getNullSet();
    Set<OWLDataPropertyExpression> setowldpe = new HashSet<OWLDataPropertyExpression>();
    Set<OWLDataPropertyExpression> nullsetodpe = getNullSet();
    List<OWLObjectPropertyExpression> listowlope = new ArrayList<OWLObjectPropertyExpression>();
    List<OWLObjectPropertyExpression> nulllistowlope = new ArrayList<OWLObjectPropertyExpression>(
            Arrays.asList((OWLObjectPropertyExpression) null));
    Set<OWLIndividual> setowlindividual = new HashSet<OWLIndividual>();
    Set<OWLIndividual> nullsetowlindividual = getNullSet();
    Set<OWLPropertyExpression<?, ?>> setowlpropertyexpression = new HashSet<OWLPropertyExpression<?, ?>>();
    Set<OWLPropertyExpression<?, ?>> nullsetowlpropertyexpression = getNullSet();
    OWLFacetRestriction[] nulllowlfacetrestriction = new OWLFacetRestriction[] {
            f.getOWLFacetRestriction(owlfacet, 1), null };
    Set<OWLClassExpression> setce = new HashSet<OWLClassExpression>();
    Set<OWLClassExpression> nullsetowlclassexpression = getNullSet();
    Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<OWLFacetRestriction>();
    Set<OWLFacetRestriction> nullsetowlfacetrestriction = getNullSet();
    @SuppressWarnings("rawtypes")
    OWLPropertyExpression[] owlpropertyexpression = new OWLPropertyExpression[] {};
    @SuppressWarnings("rawtypes")
    OWLPropertyExpression[] nullowlpropertyexpression = new OWLPropertyExpression[] { null };

    @Test(expected = NullPointerException.class)
    public void testgetOWLEntity0_2() {
        f.getOWLEntity(null, iri);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEntity1_2() {
        f.getOWLEntity(EntityType.CLASS, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClass0_1() {
        f.getOWLClass(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClass0_2() {
        f.getOWLClass(null, prefixmanager);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClass1_2() {
        f.getOWLClass(string, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectProperty0_1() {
        f.getOWLObjectProperty(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectProperty0_2() {
        f.getOWLObjectProperty(null, prefixmanager);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectProperty1_2() {
        f.getOWLObjectProperty(string, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectInverseOf0_1() {
        f.getOWLObjectInverseOf(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataProperty0_1() {
        f.getOWLDataProperty(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataProperty0_2() {
        f.getOWLDataProperty(null, prefixmanager);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataProperty1_2() {
        f.getOWLDataProperty(string, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNamedIndividual0_1() {
        f.getOWLNamedIndividual(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNamedIndividual0_2() {
        f.getOWLNamedIndividual(null, prefixmanager);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNamedIndividual1_2() {
        f.getOWLNamedIndividual(string, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnonymousIndividual0_1() {
        f.getOWLAnonymousIndividual(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationProperty0_1() {
        f.getOWLAnnotationProperty(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationProperty0_2() {
        f.getOWLAnnotationProperty(null, prefixmanager);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationProperty1_2() {
        f.getOWLAnnotationProperty(string, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatype0_1() {
        f.getOWLDatatype(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatype0_2() {
        f.getOWLDatatype(null, prefixmanager);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatype1_2() {
        f.getOWLDatatype(string, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLLiteral0_2() {
        f.getOWLLiteral(null, owldatatype);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLLiteral1_2_0() {
        f.getOWLLiteral(string, (OWLDatatype) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLLiteral0_2_1() {
        f.getOWLLiteral(null, owl2datatype);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLLiteral1_2_1() {
        f.getOWLLiteral(string, (OWL2Datatype) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLLiteral0_1_0() {
        f.getOWLLiteral(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLLiteral0_2_0() {
        f.getOWLLiteral(null, string);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTypedLiteral0_2_0() {
        f.getOWLTypedLiteral(null, owldatatype);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTypedLiteral1_2_0() {
        f.getOWLTypedLiteral(string, (OWLDatatype) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTypedLiteral0_2() {
        f.getOWLTypedLiteral(null, owl2datatype);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTypedLiteral1_2() {
        f.getOWLTypedLiteral(string, (OWL2Datatype) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTypedLiteral0_1_0() {
        f.getOWLTypedLiteral(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLStringLiteral0_2() {
        f.getOWLStringLiteral(null, string);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLStringLiteral0_1() {
        f.getOWLStringLiteral(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataOneOf0_1_0() {
        f.getOWLDataOneOf((OWLLiteral) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataOneOf0_1_0_1() {
        f.getOWLDataOneOf(lit, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataOneOf0_1_1() {
        f.getOWLDataOneOf((Set<OWLLiteral>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataOneOf0_1_1_1() {
        f.getOWLDataOneOf(this.<OWLLiteral> getNullSet());
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataComplementOf0_1() {
        f.getOWLDataComplementOf(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction0_2_0() {
        f.getOWLDatatypeRestriction(null, setowlfacetrestriction);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction1_2_0() {
        f.getOWLDatatypeRestriction(owldatatype, (OWLFacetRestriction) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction1_2_0_1() {
        f.getOWLDatatypeRestriction(owldatatype, nulllowlfacetrestriction);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction0_3() {
        f.getOWLDatatypeRestriction(null, owlfacet, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction1_3() {
        f.getOWLDatatypeRestriction(owldatatype, null, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction2_3() {
        f.getOWLDatatypeRestriction(owldatatype, owlfacet, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction0_2() {
        f.getOWLDatatypeRestriction(null, lowlfacetrestriction);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction1_2() {
        f.getOWLDatatypeRestriction(owldatatype,
                (Set<OWLFacetRestriction>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeRestriction1_2_1() {
        f.getOWLDatatypeRestriction(owldatatype, nullsetowlfacetrestriction);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction0_2_0() {
        f.getOWLFacetRestriction(null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction1_2_0() {
        f.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction0_2_1() {
        f.getOWLFacetRestriction(null, 1F);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction1_2_1() {
        f.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction0_2_3() {
        f.getOWLFacetRestriction(null, 1D);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction1_2() {
        f.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction0_2_2() {
        f.getOWLFacetRestriction(null, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFacetRestriction1_2_3() {
        f.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataUnionOf0_1() {
        f.getOWLDataUnionOf((OWLDataUnionOf) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataUnionOf0_1_1() {
        f.getOWLDataUnionOf(owldatarange, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataUnionOf0_1_0() {
        f.getOWLDataUnionOf((Set<OWLDataUnionOf>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataUnionOf0_1_0_1() {
        f.getOWLDataUnionOf(this.<OWLDataUnionOf> getNullSet());
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataIntersectionOf0_1_0() {
        f.getOWLDataIntersectionOf((OWLDataRange) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataIntersectionOf0_1_0_1() {
        f.getOWLDataIntersectionOf(owldatarange, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataIntersectionOf0_1_1() {
        f.getOWLDataIntersectionOf((Set<OWLDataRange>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataIntersectionOf0_1_1_1() {
        f.getOWLDataIntersectionOf(this.<OWLDataRange> getNullSet());
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectIntersectionOf0_1_0() {
        f.getOWLObjectIntersectionOf((OWLClassExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectIntersectionOf0_1_1() {
        f.getOWLObjectIntersectionOf((Set<OWLClassExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectIntersectionOf0_1_0_1() {
        f.getOWLObjectIntersectionOf(owlclass, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectIntersectionOf0_1_1_1() {
        f.getOWLObjectIntersectionOf(this.<OWLClassExpression> getNullSet());
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataSomeValuesFrom0_2() {
        f.getOWLDataSomeValuesFrom(null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataSomeValuesFrom1_2() {
        f.getOWLDataSomeValuesFrom(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataAllValuesFrom0_2() {
        f.getOWLDataAllValuesFrom(null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataAllValuesFrom1_2() {
        f.getOWLDataAllValuesFrom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality0_2() {
        f.getOWLDataExactCardinality(-1, owldpe);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataExactCardinality1_2() {
        f.getOWLDataExactCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality0_3() {
        f.getOWLDataExactCardinality(-1, owldpe, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataExactCardinality1_3() {
        f.getOWLDataExactCardinality(1, null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataExactCardinality2_3() {
        f.getOWLDataExactCardinality(1, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality0_2() {
        f.getOWLDataMaxCardinality(-1, owldpe);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataMaxCardinality1_2() {
        f.getOWLDataMaxCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality0_3() {
        f.getOWLDataMaxCardinality(-1, owldpe, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataMaxCardinality1_3() {
        f.getOWLDataMaxCardinality(1, null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataMaxCardinality2_3() {
        f.getOWLDataMaxCardinality(1, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality0_2() {
        f.getOWLDataMinCardinality(-1, owldpe);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataMinCardinality1_2() {
        f.getOWLDataMinCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality0_3() {
        f.getOWLDataMinCardinality(-1, owldpe, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataMinCardinality1_3() {
        f.getOWLDataMinCardinality(1, null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataMinCardinality2_3() {
        f.getOWLDataMinCardinality(1, owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataHasValue0_2() {
        f.getOWLDataHasValue(null, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataHasValue1_2() {
        f.getOWLDataHasValue(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectComplementOf0_1() {
        f.getOWLObjectComplementOf(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectOneOf0_1() {
        f.getOWLObjectOneOf((OWLIndividual) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectOneOf0_1_0() {
        f.getOWLObjectOneOf((Set<OWLIndividual>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectOneOf0_1_1() {
        f.getOWLObjectOneOf(ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectOneOf0_1_0_1() {
        f.getOWLObjectOneOf(this.<OWLIndividual> getNullSet());
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectAllValuesFrom0_2() {
        f.getOWLObjectAllValuesFrom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectAllValuesFrom1_2() {
        f.getOWLObjectAllValuesFrom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectSomeValuesFrom0_2() {
        f.getOWLObjectSomeValuesFrom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectSomeValuesFrom1_2() {
        f.getOWLObjectSomeValuesFrom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality0_2() {
        f.getOWLObjectExactCardinality(-1, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectExactCardinality1_2() {
        f.getOWLObjectExactCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality0_3() {
        f.getOWLObjectExactCardinality(-1, ope, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectExactCardinality1_3() {
        f.getOWLObjectExactCardinality(1, null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectExactCardinality2_3() {
        f.getOWLObjectExactCardinality(1, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality0_3() {
        f.getOWLObjectMinCardinality(-1, ope, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectMinCardinality1_3() {
        f.getOWLObjectMinCardinality(1, null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectMinCardinality2_3() {
        f.getOWLObjectMinCardinality(1, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality0_2() {
        f.getOWLObjectMinCardinality(-1, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectMinCardinality1_2() {
        f.getOWLObjectMinCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality0_2() {
        f.getOWLObjectMaxCardinality(-1, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectMaxCardinality1_2() {
        f.getOWLObjectMaxCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality0_3() {
        f.getOWLObjectMaxCardinality(-1, ope, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectMaxCardinality1_3() {
        f.getOWLObjectMaxCardinality(1, null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectMaxCardinality2_3() {
        f.getOWLObjectMaxCardinality(1, ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectHasSelf0_1() {
        f.getOWLObjectHasSelf(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectHasValue0_2() {
        f.getOWLObjectHasValue(null, ind);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectHasValue1_2() {
        f.getOWLObjectHasValue(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectUnionOf0_1() {
        f.getOWLObjectUnionOf((OWLClassExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectUnionOf0_1_0() {
        f.getOWLObjectUnionOf((Set<OWLClassExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectUnionOf0_1_1() {
        f.getOWLObjectUnionOf(owlclass, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectUnionOf0_1_0_1() {
        f.getOWLObjectUnionOf(this.<OWLClassExpression> getNullSet());
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDeclarationAxiom0_1() {
        f.getOWLDeclarationAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDeclarationAxiom0_2() {
        f.getOWLDeclarationAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDeclarationAxiom1_2() {
        f.getOWLDeclarationAxiom(owlclass, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDeclarationAxiom1_2_1() {
        f.getOWLDeclarationAxiom(owlclass, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubClassOfAxiom0_2() {
        f.getOWLSubClassOfAxiom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubClassOfAxiom1_2() {
        f.getOWLSubClassOfAxiom(ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubClassOfAxiom0_3() {
        f.getOWLSubClassOfAxiom(null, ce, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubClassOfAxiom1_3() {
        f.getOWLSubClassOfAxiom(ce, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubClassOfAxiom2_3() {
        f.getOWLSubClassOfAxiom(ce, ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubClassOfAxiom2_3_1() {
        f.getOWLSubClassOfAxiom(ce, ce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom0_2_0() {
        f.getOWLEquivalentClassesAxiom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom1_2_0() {
        f.getOWLEquivalentClassesAxiom(ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom0_3() {
        f.getOWLEquivalentClassesAxiom(null, ce, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom1_3() {
        f.getOWLEquivalentClassesAxiom(ce, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom2_3() {
        f.getOWLEquivalentClassesAxiom(ce, ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom2_3_1() {
        f.getOWLEquivalentClassesAxiom(ce, ce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom0_1_0() {
        f.getOWLEquivalentClassesAxiom((OWLClassExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom0_2_1() {
        f.getOWLEquivalentClassesAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom0_2_1_1() {
        f.getOWLEquivalentClassesAxiom(nullsetowlclassexpression, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom1_2_1() {
        f.getOWLEquivalentClassesAxiom(setce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom1_2_1_1() {
        f.getOWLEquivalentClassesAxiom(setce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentClassesAxiom0_1_1() {
        f.getOWLEquivalentClassesAxiom((Set<OWLClassExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointClassesAxiom0_2() {
        f.getOWLDisjointClassesAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointClassesAxiom0_2_1() {
        f.getOWLDisjointClassesAxiom(nullsetowlclassexpression, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointClassesAxiom1_2() {
        f.getOWLDisjointClassesAxiom(setce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointClassesAxiom1_2_1() {
        f.getOWLDisjointClassesAxiom(setce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointClassesAxiom0_1() {
        f.getOWLDisjointClassesAxiom((OWLClassExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointClassesAxiom0_1_0() {
        f.getOWLDisjointClassesAxiom((Set<OWLClassExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom0_3() {
        f.getOWLDisjointUnionAxiom(null, setce, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom1_3() {
        f.getOWLDisjointUnionAxiom(owlclass, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom1_3_1() {
        f.getOWLDisjointUnionAxiom(owlclass, nullsetowlclassexpression,
                setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom2_3() {
        f.getOWLDisjointUnionAxiom(owlclass, setce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom2_3_1() {
        f.getOWLDisjointUnionAxiom(owlclass, setce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom0_2() {
        f.getOWLDisjointUnionAxiom(null, setce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointUnionAxiom1_2() {
        f.getOWLDisjointUnionAxiom(owlclass, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubObjectPropertyOfAxiom0_2() {
        f.getOWLSubObjectPropertyOfAxiom(null, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubObjectPropertyOfAxiom1_2() {
        f.getOWLSubObjectPropertyOfAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubObjectPropertyOfAxiom0_3() {
        f.getOWLSubObjectPropertyOfAxiom(null, ope, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubObjectPropertyOfAxiom1_3() {
        f.getOWLSubObjectPropertyOfAxiom(ope, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubObjectPropertyOfAxiom2_3() {
        f.getOWLSubObjectPropertyOfAxiom(ope, ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubObjectPropertyOfAxiom2_3_1() {
        f.getOWLSubObjectPropertyOfAxiom(ope, ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom0_3() {
        f.getOWLSubPropertyChainOfAxiom(null, ope, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom0_3_1() {
        f.getOWLSubPropertyChainOfAxiom(nulllistowlope, ope, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom1_3() {
        f.getOWLSubPropertyChainOfAxiom(listowlope, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom2_3() {
        f.getOWLSubPropertyChainOfAxiom(listowlope, ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom2_3_1() {
        f.getOWLSubPropertyChainOfAxiom(listowlope, ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom0_2() {
        f.getOWLSubPropertyChainOfAxiom(null, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom0_2_1() {
        f.getOWLSubPropertyChainOfAxiom(nulllistowlope, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubPropertyChainOfAxiom1_2() {
        f.getOWLSubPropertyChainOfAxiom(listowlope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0_1_0() {
        f.getOWLEquivalentObjectPropertiesAxiom((OWLObjectPropertyExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0_2_0() {
        f.getOWLEquivalentObjectPropertiesAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0_2_0_1() {
        f.getOWLEquivalentObjectPropertiesAxiom(nullSetOWLOPE, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1_2_0() {
        f.getOWLEquivalentObjectPropertiesAxiom(setowlope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1_2_0_2() {
        f.getOWLEquivalentObjectPropertiesAxiom(setowlope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1_2_0_1() {
        f.getOWLEquivalentObjectPropertiesAxiom(nullSetOWLOPE, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0_1_1() {
        f.getOWLEquivalentObjectPropertiesAxiom((Set<OWLObjectPropertyExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0_2_1() {
        f.getOWLEquivalentObjectPropertiesAxiom(null, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1_2_1() {
        f.getOWLEquivalentObjectPropertiesAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0_3() {
        f.getOWLEquivalentObjectPropertiesAxiom(null, ope, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1_3() {
        f.getOWLEquivalentObjectPropertiesAxiom(ope, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom2_3() {
        f.getOWLEquivalentObjectPropertiesAxiom(ope, ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom2_3_1() {
        f.getOWLEquivalentObjectPropertiesAxiom(ope, ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom0_1() {
        f.getOWLDisjointObjectPropertiesAxiom((OWLObjectPropertyExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom0_1_0() {
        f.getOWLDisjointObjectPropertiesAxiom((Set<OWLObjectPropertyExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom0_2() {
        f.getOWLDisjointObjectPropertiesAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom0_2_1() {
        f.getOWLDisjointObjectPropertiesAxiom(nullSetOWLOPE, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom1_2() {
        f.getOWLDisjointObjectPropertiesAxiom(setowlope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom1_2_2() {
        f.getOWLDisjointObjectPropertiesAxiom(setowlope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom1_2_1() {
        f.getOWLDisjointObjectPropertiesAxiom(nullSetOWLOPE, null);
    }

    public <O> Set<O> getNullSet() {
        Set<O> s = new HashSet<O>();
        s.add(null);
        return s;
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseObjectPropertiesAxiom0_2() {
        f.getOWLInverseObjectPropertiesAxiom(null, ope);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseObjectPropertiesAxiom1_2() {
        f.getOWLInverseObjectPropertiesAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseObjectPropertiesAxiom0_3() {
        f.getOWLInverseObjectPropertiesAxiom(null, ope, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseObjectPropertiesAxiom1_3() {
        f.getOWLInverseObjectPropertiesAxiom(ope, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseObjectPropertiesAxiom2_3() {
        f.getOWLInverseObjectPropertiesAxiom(ope, ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseObjectPropertiesAxiom2_3_1() {
        f.getOWLInverseObjectPropertiesAxiom(ope, ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyDomainAxiom0_2() {
        f.getOWLObjectPropertyDomainAxiom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyDomainAxiom1_2() {
        f.getOWLObjectPropertyDomainAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyDomainAxiom0_3() {
        f.getOWLObjectPropertyDomainAxiom(null, ce, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyDomainAxiom1_3() {
        f.getOWLObjectPropertyDomainAxiom(ope, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyDomainAxiom2_3() {
        f.getOWLObjectPropertyDomainAxiom(ope, ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyDomainAxiom2_3_1() {
        f.getOWLObjectPropertyDomainAxiom(ope, ce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyRangeAxiom0_3() {
        f.getOWLObjectPropertyRangeAxiom(null, ce, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyRangeAxiom1_3() {
        f.getOWLObjectPropertyRangeAxiom(ope, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyRangeAxiom2_3() {
        f.getOWLObjectPropertyRangeAxiom(ope, ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyRangeAxiom2_3_1() {
        f.getOWLObjectPropertyRangeAxiom(ope, ce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyRangeAxiom0_2() {
        f.getOWLObjectPropertyRangeAxiom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyRangeAxiom1_2() {
        f.getOWLObjectPropertyRangeAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom0_1() {
        f.getOWLFunctionalObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom0_2() {
        f.getOWLFunctionalObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom1_2() {
        f.getOWLFunctionalObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom1_2_1() {
        f.getOWLFunctionalObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom0_1() {
        f.getOWLInverseFunctionalObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom0_2() {
        f.getOWLInverseFunctionalObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom1_2() {
        f.getOWLInverseFunctionalObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom1_2_1() {
        f.getOWLInverseFunctionalObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom0_2() {
        f.getOWLReflexiveObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom1_2() {
        f.getOWLReflexiveObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom1_2_1() {
        f.getOWLReflexiveObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom0_1() {
        f.getOWLReflexiveObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom0_2() {
        f.getOWLIrreflexiveObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom1_2() {
        f.getOWLIrreflexiveObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom1_2_1() {
        f.getOWLIrreflexiveObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom0_1() {
        f.getOWLIrreflexiveObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom0_1() {
        f.getOWLSymmetricObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom0_2() {
        f.getOWLSymmetricObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom1_2() {
        f.getOWLSymmetricObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom1_2_1() {
        f.getOWLSymmetricObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom0_2() {
        f.getOWLAsymmetricObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom1_2() {
        f.getOWLAsymmetricObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom1_2_1() {
        f.getOWLAsymmetricObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom0_1() {
        f.getOWLAsymmetricObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom0_1() {
        f.getOWLTransitiveObjectPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom0_2() {
        f.getOWLTransitiveObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom1_2() {
        f.getOWLTransitiveObjectPropertyAxiom(ope, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom1_2_1() {
        f.getOWLTransitiveObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubDataPropertyOfAxiom0_2() {
        f.getOWLSubDataPropertyOfAxiom(null, owldpe);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubDataPropertyOfAxiom1_2() {
        f.getOWLSubDataPropertyOfAxiom(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubDataPropertyOfAxiom0_3() {
        f.getOWLSubDataPropertyOfAxiom(null, owldpe, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubDataPropertyOfAxiom1_3() {
        f.getOWLSubDataPropertyOfAxiom(owldpe, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubDataPropertyOfAxiom2_3() {
        f.getOWLSubDataPropertyOfAxiom(owldpe, owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubDataPropertyOfAxiom2_3_1() {
        f.getOWLSubDataPropertyOfAxiom(owldpe, owldpe, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0_2_0() {
        f.getOWLEquivalentDataPropertiesAxiom(null, owldpe);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom1_2_0() {
        f.getOWLEquivalentDataPropertiesAxiom(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0_1_0() {
        f.getOWLEquivalentDataPropertiesAxiom((OWLDataPropertyExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0_2_1() {
        f.getOWLEquivalentDataPropertiesAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0_2_1_1() {
        f.getOWLEquivalentDataPropertiesAxiom(nullsetodpe, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom1_2_1() {
        f.getOWLEquivalentDataPropertiesAxiom(setowldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom1_2_1_1() {
        f.getOWLEquivalentDataPropertiesAxiom(setowldpe, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0_1_1() {
        f.getOWLEquivalentDataPropertiesAxiom((Set<OWLDataPropertyExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0_3() {
        f.getOWLEquivalentDataPropertiesAxiom(null, owldpe, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom1_3() {
        f.getOWLEquivalentDataPropertiesAxiom(owldpe, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom2_3() {
        f.getOWLEquivalentDataPropertiesAxiom(owldpe, owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom2_3_1() {
        f.getOWLEquivalentDataPropertiesAxiom(owldpe, owldpe, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointDataPropertiesAxiom0_1() {
        f.getOWLDisjointDataPropertiesAxiom((OWLDataPropertyExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointDataPropertiesAxiom0_1_0() {
        f.getOWLDisjointDataPropertiesAxiom((Set<OWLDataPropertyExpression>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointDataPropertiesAxiom0_2() {
        f.getOWLDisjointDataPropertiesAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointDataPropertiesAxiom0_2_1() {
        f.getOWLDisjointDataPropertiesAxiom(nullsetodpe, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointDataPropertiesAxiom1_2() {
        f.getOWLDisjointDataPropertiesAxiom(setowldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDisjointDataPropertiesAxiom1_2_1() {
        f.getOWLDisjointDataPropertiesAxiom(setowldpe, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyDomainAxiom0_2() {
        f.getOWLDataPropertyDomainAxiom(null, ce);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyDomainAxiom1_2() {
        f.getOWLDataPropertyDomainAxiom(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyDomainAxiom0_3() {
        f.getOWLDataPropertyDomainAxiom(null, ce, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyDomainAxiom1_3() {
        f.getOWLDataPropertyDomainAxiom(owldpe, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyDomainAxiom2_3() {
        f.getOWLDataPropertyDomainAxiom(owldpe, ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyDomainAxiom2_3_1() {
        f.getOWLDataPropertyDomainAxiom(owldpe, ce, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyRangeAxiom0_3() {
        f.getOWLDataPropertyRangeAxiom(null, owldatarange, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyRangeAxiom1_3() {
        f.getOWLDataPropertyRangeAxiom(owldpe, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyRangeAxiom2_3() {
        f.getOWLDataPropertyRangeAxiom(owldpe, owldatarange, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyRangeAxiom2_3_1() {
        f.getOWLDataPropertyRangeAxiom(owldpe, owldatarange, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyRangeAxiom0_2() {
        f.getOWLDataPropertyRangeAxiom(null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyRangeAxiom1_2() {
        f.getOWLDataPropertyRangeAxiom(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalDataPropertyAxiom0_1() {
        f.getOWLFunctionalDataPropertyAxiom(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalDataPropertyAxiom0_2() {
        f.getOWLFunctionalDataPropertyAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalDataPropertyAxiom1_2() {
        f.getOWLFunctionalDataPropertyAxiom(owldpe, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLFunctionalDataPropertyAxiom1_2_1() {
        f.getOWLFunctionalDataPropertyAxiom(owldpe, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom0_2_0() {
        f.getOWLHasKeyAxiom(null, setowlpropertyexpression);
    }

    @SuppressWarnings("rawtypes")
    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom1_2_0() {
        f.getOWLHasKeyAxiom(ce, (OWLPropertyExpression) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom1_2_0_1() {
        f.getOWLHasKeyAxiom(ce, nullowlpropertyexpression);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom0_2_1() {
        f.getOWLHasKeyAxiom(null, owlpropertyexpression);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom1_2_1() {
        f.getOWLHasKeyAxiom(ce, (Set<OWLPropertyExpression<?, ?>>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom1_2_1_1() {
        f.getOWLHasKeyAxiom(ce, nullsetowlpropertyexpression);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom0_3() {
        f.getOWLHasKeyAxiom(null, setowlpropertyexpression, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom1_3() {
        f.getOWLHasKeyAxiom(ce, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom1_3_1() {
        f.getOWLHasKeyAxiom(ce, nullsetowlpropertyexpression, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom2_3() {
        f.getOWLHasKeyAxiom(ce, setowlpropertyexpression, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLHasKeyAxiom2_3_1() {
        f.getOWLHasKeyAxiom(ce, setowlpropertyexpression, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeDefinitionAxiom0_2() {
        f.getOWLDatatypeDefinitionAxiom(null, owldatarange);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeDefinitionAxiom1_2() {
        f.getOWLDatatypeDefinitionAxiom(owldatatype, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeDefinitionAxiom0_3() {
        f.getOWLDatatypeDefinitionAxiom(null, owldatarange, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeDefinitionAxiom1_3() {
        f.getOWLDatatypeDefinitionAxiom(owldatatype, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeDefinitionAxiom2_3() {
        f.getOWLDatatypeDefinitionAxiom(owldatatype, owldatarange, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDatatypeDefinitionAxiom2_3_1() {
        f.getOWLDatatypeDefinitionAxiom(owldatatype, owldatarange,
                nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSameIndividualAxiom0_1() {
        f.getOWLSameIndividualAxiom((OWLIndividual) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSameIndividualAxiom0_2() {
        f.getOWLSameIndividualAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSameIndividualAxiom0_2_1() {
        f.getOWLSameIndividualAxiom(nullsetowlindividual, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSameIndividualAxiom1_2() {
        f.getOWLSameIndividualAxiom(setowlindividual, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSameIndividualAxiom1_2_1() {
        f.getOWLSameIndividualAxiom(setowlindividual, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSameIndividualAxiom0_1_0() {
        f.getOWLSameIndividualAxiom((Set<OWLIndividual>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDifferentIndividualsAxiom0_2() {
        f.getOWLDifferentIndividualsAxiom(null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDifferentIndividualsAxiom0_2_1() {
        f.getOWLDifferentIndividualsAxiom(nullsetowlindividual, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDifferentIndividualsAxiom1_2() {
        f.getOWLDifferentIndividualsAxiom(setowlindividual, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDifferentIndividualsAxiom1_2_1() {
        f.getOWLDifferentIndividualsAxiom(setowlindividual, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDifferentIndividualsAxiom0_1_0() {
        f.getOWLDifferentIndividualsAxiom((OWLIndividual) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDifferentIndividualsAxiom0_1() {
        f.getOWLDifferentIndividualsAxiom((Set<OWLIndividual>) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClassAssertionAxiom0_2() {
        f.getOWLClassAssertionAxiom(null, ind);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClassAssertionAxiom1_2() {
        f.getOWLClassAssertionAxiom(ce, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClassAssertionAxiom0_3() {
        f.getOWLClassAssertionAxiom(null, ind, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClassAssertionAxiom1_3() {
        f.getOWLClassAssertionAxiom(ce, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClassAssertionAxiom2_3() {
        f.getOWLClassAssertionAxiom(ce, ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLClassAssertionAxiom2_3_1() {
        f.getOWLClassAssertionAxiom(ce, ind, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom0_3() {
        f.getOWLObjectPropertyAssertionAxiom(null, ind, ind);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom1_3() {
        f.getOWLObjectPropertyAssertionAxiom(ope, null, ind);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom2_3() {
        f.getOWLObjectPropertyAssertionAxiom(ope, ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom0_4() {
        f.getOWLObjectPropertyAssertionAxiom(null, ind, ind, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom1_4() {
        f.getOWLObjectPropertyAssertionAxiom(ope, null, ind, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom2_4() {
        f.getOWLObjectPropertyAssertionAxiom(ope, ind, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom3_4() {
        f.getOWLObjectPropertyAssertionAxiom(ope, ind, ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLObjectPropertyAssertionAxiom3_4_1() {
        f.getOWLObjectPropertyAssertionAxiom(ope, ind, ind, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom0_4() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(null, ind, ind, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom1_4() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(ope, null, ind, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom2_4() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom3_4() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom3_4_1() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, ind,
                nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom0_3() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(null, ind, ind);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom1_3() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(ope, null, ind);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom2_3() {
        f.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_4() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, lit, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_4() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, lit, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom2_4() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, ind, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom3_4() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, ind, lit, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom3_4_1() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, ind, lit, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_3_0() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_3_0() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_3_1() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_3_4() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_3_4() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, 1D);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_3_1() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, 1D);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_3_2() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, 1F);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_3_5() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, 1F);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom2_3_4() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, ind, (String) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_3_5() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, true);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_3_2() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, true);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom2_3_2() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, ind, (OWLLiteral) null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom0_3_3() {
        f.getOWLDataPropertyAssertionAxiom(null, ind, string);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLDataPropertyAssertionAxiom1_3_3() {
        f.getOWLDataPropertyAssertionAxiom(owldpe, null, string);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom0_3() {
        f.getOWLNegativeDataPropertyAssertionAxiom(null, ind, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom1_3() {
        f.getOWLNegativeDataPropertyAssertionAxiom(owldpe, null, lit);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom2_3() {
        f.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom0_4() {
        f.getOWLNegativeDataPropertyAssertionAxiom(null, ind, lit, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom1_4() {
        f.getOWLNegativeDataPropertyAssertionAxiom(owldpe, null, lit, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom2_4() {
        f.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom3_4() {
        f.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, lit, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom3_4_1() {
        f.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, lit,
                nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotation0_2() {
        f.getOWLAnnotation(null, owlannvalue);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotation1_2() {
        f.getOWLAnnotation(owlap, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotation0_3() {
        f.getOWLAnnotation(null, owlannvalue, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotation1_3() {
        f.getOWLAnnotation(owlap, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotation2_3() {
        f.getOWLAnnotation(owlap, owlannvalue, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotation2_3_1() {
        f.getOWLAnnotation(owlap, owlannvalue, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom0_2() {
        f.getOWLAnnotationAssertionAxiom(null, owlannotation);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom1_2() {
        f.getOWLAnnotationAssertionAxiom(owlannsubj, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom0_3_0() {
        f.getOWLAnnotationAssertionAxiom(null, owlannsubj, owlannvalue);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom1_3_0() {
        f.getOWLAnnotationAssertionAxiom(owlap, null, owlannvalue);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom2_3_0() {
        f.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom0_4() {
        f.getOWLAnnotationAssertionAxiom(null, owlannsubj, owlannvalue,
                setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom1_4() {
        f.getOWLAnnotationAssertionAxiom(owlap, null, owlannvalue, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom2_4() {
        f.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom3_4() {
        f.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, owlannvalue, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom3_4_1() {
        f.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, owlannvalue,
                nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom0_3_1() {
        f.getOWLAnnotationAssertionAxiom(null, owlannotation, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom1_3_1() {
        f.getOWLAnnotationAssertionAxiom(owlannsubj, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom2_3_1() {
        f.getOWLAnnotationAssertionAxiom(owlannsubj, owlannotation, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationAssertionAxiom2_3_1_1() {
        f.getOWLAnnotationAssertionAxiom(owlannsubj, owlannotation,
                nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLImportsDeclaration0_1() {
        f.getOWLImportsDeclaration(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom0_2() {
        f.getOWLAnnotationPropertyDomainAxiom(null, iri);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom1_2() {
        f.getOWLAnnotationPropertyDomainAxiom(owlap, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom0_3() {
        f.getOWLAnnotationPropertyDomainAxiom(null, iri, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom1_3() {
        f.getOWLAnnotationPropertyDomainAxiom(owlap, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom2_3() {
        f.getOWLAnnotationPropertyDomainAxiom(owlap, iri, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom2_3_1() {
        f.getOWLAnnotationPropertyDomainAxiom(owlap, iri, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom0_2() {
        f.getOWLAnnotationPropertyRangeAxiom(null, iri);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom1_2() {
        f.getOWLAnnotationPropertyRangeAxiom(owlap, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom0_3() {
        f.getOWLAnnotationPropertyRangeAxiom(null, iri, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom1_3() {
        f.getOWLAnnotationPropertyRangeAxiom(owlap, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom2_3() {
        f.getOWLAnnotationPropertyRangeAxiom(owlap, iri, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom2_3_1() {
        f.getOWLAnnotationPropertyRangeAxiom(owlap, iri, nullsetowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom0_2() {
        f.getOWLSubAnnotationPropertyOfAxiom(null, owlap);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom1_2() {
        f.getOWLSubAnnotationPropertyOfAxiom(owlap, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom0_3() {
        f.getOWLSubAnnotationPropertyOfAxiom(null, owlap, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom1_3() {
        f.getOWLSubAnnotationPropertyOfAxiom(owlap, null, setowlann);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom2_3() {
        f.getOWLSubAnnotationPropertyOfAxiom(owlap, owlap, null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom2_3_1() {
        f.getOWLSubAnnotationPropertyOfAxiom(owlap, owlap, nullsetowlann);
    }
}
