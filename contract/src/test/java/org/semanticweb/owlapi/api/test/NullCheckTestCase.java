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

@SuppressWarnings("javadoc")
public class NullCheckTestCase {
    private static final OWLDataFactory DF = Factory.getFactory();
    OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
    OWLDataPropertyExpression owldpe = DF.getOWLDataProperty(IRI("urn:dp"));
    OWLObjectPropertyExpression ope = DF.getOWLObjectProperty(IRI("urn:op"));
    IRI iri = IRI("urn:iri");
    OWLLiteral lit = DF.getOWLLiteral(true);
    OWLAnnotationSubject owlannsubj = IRI("urn:i");
    OWLDatatype owldatatype = DF.getOWLDatatype(owl2datatype.getIRI());
    OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
    OWLFacetRestriction[] lowlfacetrestriction = new OWLFacetRestriction[] { DF
            .getOWLFacetRestriction(owlfacet, 1) };
    OWLDataRange owldatarange = DF.getOWLDatatypeRestriction(owldatatype,
            lowlfacetrestriction);
    OWLAnnotationProperty owlap = DF.getOWLAnnotationProperty(IRI("urn:ap"));
    OWLAnnotation owlannotation = DF.getOWLAnnotation(owlap, lit);
    String string = "testString";
    OWLClass owlclass = DF.getOWLClass(IRI("urn:classexpression"));
    OWLClassExpression ce = owlclass;
    PrefixManager prefixmanager = new DefaultPrefixManager();
    OWLIndividual ind = DF.getOWLAnonymousIndividual();
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
    Set<OWLPropertyExpression> setowlpropertyexpression = new HashSet<OWLPropertyExpression>();
    Set<OWLPropertyExpression> nullsetowlpropertyexpression = getNullSet();
    OWLFacetRestriction[] nulllowlfacetrestriction = new OWLFacetRestriction[] {
            DF.getOWLFacetRestriction(owlfacet, 1), null };
    Set<OWLClassExpression> setce = new HashSet<OWLClassExpression>();
    Set<OWLClassExpression> nullsetowlclassexpression = getNullSet();
    Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<OWLFacetRestriction>();
    Set<OWLFacetRestriction> nullsetowlfacetrestriction = getNullSet();
    OWLPropertyExpression[] owlpropertyexpression = new OWLPropertyExpression[] {};
    OWLPropertyExpression[] nullowlpropertyexpression = new OWLPropertyExpression[] { null };

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEntity02() {
        DF.getOWLEntity(null, iri);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEntity12() {
        DF.getOWLEntity(EntityType.CLASS, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClass01() {
        DF.getOWLClass(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClass02() {
        DF.getOWLClass(null, prefixmanager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClass12() {
        DF.getOWLClass(string, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectProperty01() {
        DF.getOWLObjectProperty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectProperty02() {
        DF.getOWLObjectProperty(null, prefixmanager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectProperty12() {
        DF.getOWLObjectProperty(string, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectInverseOf01() {
        DF.getOWLObjectInverseOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataProperty01() {
        DF.getOWLDataProperty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataProperty02() {
        DF.getOWLDataProperty(null, prefixmanager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataProperty12() {
        DF.getOWLDataProperty(string, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNamedIndividual01() {
        DF.getOWLNamedIndividual(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNamedIndividual02() {
        DF.getOWLNamedIndividual(null, prefixmanager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNamedIndividual12() {
        DF.getOWLNamedIndividual(string, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnonymousIndividual01() {
        DF.getOWLAnonymousIndividual(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationProperty01() {
        DF.getOWLAnnotationProperty(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationProperty02() {
        DF.getOWLAnnotationProperty(null, prefixmanager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationProperty12() {
        DF.getOWLAnnotationProperty(string, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatype01() {
        DF.getOWLDatatype(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatype02() {
        DF.getOWLDatatype(null, prefixmanager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatype12() {
        DF.getOWLDatatype(string, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLLiteral02() {
        DF.getOWLLiteral(null, owldatatype);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLLiteral120() {
        DF.getOWLLiteral(string, (OWLDatatype) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLLiteral021() {
        DF.getOWLLiteral(null, owl2datatype);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLLiteral121() {
        DF.getOWLLiteral(string, (OWL2Datatype) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLLiteral010() {
        DF.getOWLLiteral(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLLiteral020() {
        DF.getOWLLiteral(null, string);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataOneOf010() {
        DF.getOWLDataOneOf((OWLLiteral) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataOneOf0101() {
        DF.getOWLDataOneOf(lit, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataOneOf011() {
        DF.getOWLDataOneOf((Set<OWLLiteral>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataOneOf0111() {
        DF.getOWLDataOneOf(this.<OWLLiteral> getNullSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataComplementOf01() {
        DF.getOWLDataComplementOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction020() {
        DF.getOWLDatatypeRestriction(null, setowlfacetrestriction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction120() {
        DF.getOWLDatatypeRestriction(owldatatype, (OWLFacetRestriction) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction1201() {
        DF.getOWLDatatypeRestriction(owldatatype, nulllowlfacetrestriction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction03() {
        DF.getOWLDatatypeRestriction(null, owlfacet, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction13() {
        DF.getOWLDatatypeRestriction(owldatatype, null, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction23() {
        DF.getOWLDatatypeRestriction(owldatatype, owlfacet, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction02() {
        DF.getOWLDatatypeRestriction(null, lowlfacetrestriction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction12() {
        DF.getOWLDatatypeRestriction(owldatatype, (Set<OWLFacetRestriction>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeRestriction121() {
        DF.getOWLDatatypeRestriction(owldatatype, nullsetowlfacetrestriction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction020() {
        DF.getOWLFacetRestriction(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction120() {
        DF.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction021() {
        DF.getOWLFacetRestriction(null, 1F);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction121() {
        DF.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction023() {
        DF.getOWLFacetRestriction(null, 1D);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction12() {
        DF.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction022() {
        DF.getOWLFacetRestriction(null, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFacetRestriction123() {
        DF.getOWLFacetRestriction(owlfacet, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataUnionOf01() {
        DF.getOWLDataUnionOf((OWLDataUnionOf) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataUnionOf011() {
        DF.getOWLDataUnionOf(owldatarange, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataUnionOf010() {
        DF.getOWLDataUnionOf((Set<OWLDataUnionOf>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataUnionOf0101() {
        DF.getOWLDataUnionOf(this.<OWLDataUnionOf> getNullSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataIntersectionOf010() {
        DF.getOWLDataIntersectionOf((OWLDataRange) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataIntersectionOf0101() {
        DF.getOWLDataIntersectionOf(owldatarange, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataIntersectionOf011() {
        DF.getOWLDataIntersectionOf((Set<OWLDataRange>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataIntersectionOf0111() {
        DF.getOWLDataIntersectionOf(this.<OWLDataRange> getNullSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectIntersectionOf010() {
        DF.getOWLObjectIntersectionOf((OWLClassExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectIntersectionOf011() {
        DF.getOWLObjectIntersectionOf((Set<OWLClassExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectIntersectionOf0101() {
        DF.getOWLObjectIntersectionOf(owlclass, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectIntersectionOf0111() {
        DF.getOWLObjectIntersectionOf(this.<OWLClassExpression> getNullSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataSomeValuesFrom02() {
        DF.getOWLDataSomeValuesFrom(null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataSomeValuesFrom12() {
        DF.getOWLDataSomeValuesFrom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataAllValuesFrom02() {
        DF.getOWLDataAllValuesFrom(null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataAllValuesFrom12() {
        DF.getOWLDataAllValuesFrom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality02() {
        DF.getOWLDataExactCardinality(-1, owldpe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality12() {
        DF.getOWLDataExactCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality03() {
        DF.getOWLDataExactCardinality(-1, owldpe, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality13() {
        DF.getOWLDataExactCardinality(1, null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataExactCardinality23() {
        DF.getOWLDataExactCardinality(1, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality02() {
        DF.getOWLDataMaxCardinality(-1, owldpe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality12() {
        DF.getOWLDataMaxCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality03() {
        DF.getOWLDataMaxCardinality(-1, owldpe, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality13() {
        DF.getOWLDataMaxCardinality(1, null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMaxCardinality23() {
        DF.getOWLDataMaxCardinality(1, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality02() {
        DF.getOWLDataMinCardinality(-1, owldpe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality12() {
        DF.getOWLDataMinCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality03() {
        DF.getOWLDataMinCardinality(-1, owldpe, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality13() {
        DF.getOWLDataMinCardinality(1, null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataMinCardinality23() {
        DF.getOWLDataMinCardinality(1, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataHasValue02() {
        DF.getOWLDataHasValue(null, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataHasValue12() {
        DF.getOWLDataHasValue(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectComplementOf01() {
        DF.getOWLObjectComplementOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectOneOf01() {
        DF.getOWLObjectOneOf((OWLIndividual) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectOneOf010() {
        DF.getOWLObjectOneOf((Set<OWLIndividual>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectOneOf011() {
        DF.getOWLObjectOneOf(ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectOneOf0101() {
        DF.getOWLObjectOneOf(this.<OWLIndividual> getNullSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectAllValuesFrom02() {
        DF.getOWLObjectAllValuesFrom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectAllValuesFrom12() {
        DF.getOWLObjectAllValuesFrom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectSomeValuesFrom02() {
        DF.getOWLObjectSomeValuesFrom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectSomeValuesFrom12() {
        DF.getOWLObjectSomeValuesFrom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality02() {
        DF.getOWLObjectExactCardinality(-1, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality12() {
        DF.getOWLObjectExactCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality03() {
        DF.getOWLObjectExactCardinality(-1, ope, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality13() {
        DF.getOWLObjectExactCardinality(1, null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectExactCardinality23() {
        DF.getOWLObjectExactCardinality(1, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality03() {
        DF.getOWLObjectMinCardinality(-1, ope, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality13() {
        DF.getOWLObjectMinCardinality(1, null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality23() {
        DF.getOWLObjectMinCardinality(1, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality02() {
        DF.getOWLObjectMinCardinality(-1, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMinCardinality12() {
        DF.getOWLObjectMinCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality02() {
        DF.getOWLObjectMaxCardinality(-1, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality12() {
        DF.getOWLObjectMaxCardinality(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality03() {
        DF.getOWLObjectMaxCardinality(-1, ope, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality13() {
        DF.getOWLObjectMaxCardinality(1, null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectMaxCardinality23() {
        DF.getOWLObjectMaxCardinality(1, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectHasSelf01() {
        DF.getOWLObjectHasSelf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectHasValue02() {
        DF.getOWLObjectHasValue(null, ind);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectHasValue12() {
        DF.getOWLObjectHasValue(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectUnionOf01() {
        DF.getOWLObjectUnionOf((OWLClassExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectUnionOf010() {
        DF.getOWLObjectUnionOf((Set<OWLClassExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectUnionOf011() {
        DF.getOWLObjectUnionOf(owlclass, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectUnionOf0101() {
        DF.getOWLObjectUnionOf(this.<OWLClassExpression> getNullSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDeclarationAxiom01() {
        DF.getOWLDeclarationAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDeclarationAxiom02() {
        DF.getOWLDeclarationAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDeclarationAxiom12() {
        DF.getOWLDeclarationAxiom(owlclass, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDeclarationAxiom121() {
        DF.getOWLDeclarationAxiom(owlclass, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubClassOfAxiom02() {
        DF.getOWLSubClassOfAxiom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubClassOfAxiom12() {
        DF.getOWLSubClassOfAxiom(ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubClassOfAxiom03() {
        DF.getOWLSubClassOfAxiom(null, ce, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubClassOfAxiom13() {
        DF.getOWLSubClassOfAxiom(ce, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubClassOfAxiom23() {
        DF.getOWLSubClassOfAxiom(ce, ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubClassOfAxiom231() {
        DF.getOWLSubClassOfAxiom(ce, ce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom020() {
        DF.getOWLEquivalentClassesAxiom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom120() {
        DF.getOWLEquivalentClassesAxiom(ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom03() {
        DF.getOWLEquivalentClassesAxiom(null, ce, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom13() {
        DF.getOWLEquivalentClassesAxiom(ce, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom23() {
        DF.getOWLEquivalentClassesAxiom(ce, ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom231() {
        DF.getOWLEquivalentClassesAxiom(ce, ce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom010() {
        DF.getOWLEquivalentClassesAxiom((OWLClassExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom021() {
        DF.getOWLEquivalentClassesAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom0211() {
        DF.getOWLEquivalentClassesAxiom(nullsetowlclassexpression, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom121() {
        DF.getOWLEquivalentClassesAxiom(setce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom1211() {
        DF.getOWLEquivalentClassesAxiom(setce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentClassesAxiom011() {
        DF.getOWLEquivalentClassesAxiom((Set<OWLClassExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointClassesAxiom02() {
        DF.getOWLDisjointClassesAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointClassesAxiom021() {
        DF.getOWLDisjointClassesAxiom(nullsetowlclassexpression, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointClassesAxiom12() {
        DF.getOWLDisjointClassesAxiom(setce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointClassesAxiom121() {
        DF.getOWLDisjointClassesAxiom(setce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointClassesAxiom01() {
        DF.getOWLDisjointClassesAxiom((OWLClassExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointClassesAxiom010() {
        DF.getOWLDisjointClassesAxiom((Set<OWLClassExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom03() {
        DF.getOWLDisjointUnionAxiom(null, setce, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom13() {
        DF.getOWLDisjointUnionAxiom(owlclass, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom131() {
        DF.getOWLDisjointUnionAxiom(owlclass, nullsetowlclassexpression, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom23() {
        DF.getOWLDisjointUnionAxiom(owlclass, setce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom231() {
        DF.getOWLDisjointUnionAxiom(owlclass, setce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom02() {
        DF.getOWLDisjointUnionAxiom(null, setce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointUnionAxiom12() {
        DF.getOWLDisjointUnionAxiom(owlclass, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubObjectPropertyOfAxiom02() {
        DF.getOWLSubObjectPropertyOfAxiom(null, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubObjectPropertyOfAxiom12() {
        DF.getOWLSubObjectPropertyOfAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubObjectPropertyOfAxiom03() {
        DF.getOWLSubObjectPropertyOfAxiom(null, ope, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubObjectPropertyOfAxiom13() {
        DF.getOWLSubObjectPropertyOfAxiom(ope, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubObjectPropertyOfAxiom23() {
        DF.getOWLSubObjectPropertyOfAxiom(ope, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubObjectPropertyOfAxiom231() {
        DF.getOWLSubObjectPropertyOfAxiom(ope, ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom03() {
        DF.getOWLSubPropertyChainOfAxiom(null, ope, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom031() {
        DF.getOWLSubPropertyChainOfAxiom(nulllistowlope, ope, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom13() {
        DF.getOWLSubPropertyChainOfAxiom(listowlope, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom23() {
        DF.getOWLSubPropertyChainOfAxiom(listowlope, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom231() {
        DF.getOWLSubPropertyChainOfAxiom(listowlope, ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom02() {
        DF.getOWLSubPropertyChainOfAxiom(null, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom021() {
        DF.getOWLSubPropertyChainOfAxiom(nulllistowlope, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubPropertyChainOfAxiom12() {
        DF.getOWLSubPropertyChainOfAxiom(listowlope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom010() {
        DF.getOWLEquivalentObjectPropertiesAxiom((OWLObjectPropertyExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom020() {
        DF.getOWLEquivalentObjectPropertiesAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom0201() {
        DF.getOWLEquivalentObjectPropertiesAxiom(nullSetOWLOPE, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom120() {
        DF.getOWLEquivalentObjectPropertiesAxiom(setowlope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1202() {
        DF.getOWLEquivalentObjectPropertiesAxiom(setowlope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom1201() {
        DF.getOWLEquivalentObjectPropertiesAxiom(nullSetOWLOPE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom011() {
        DF.getOWLEquivalentObjectPropertiesAxiom((Set<OWLObjectPropertyExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom021() {
        DF.getOWLEquivalentObjectPropertiesAxiom(null, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom121() {
        DF.getOWLEquivalentObjectPropertiesAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom03() {
        DF.getOWLEquivalentObjectPropertiesAxiom(null, ope, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom13() {
        DF.getOWLEquivalentObjectPropertiesAxiom(ope, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom23() {
        DF.getOWLEquivalentObjectPropertiesAxiom(ope, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentObjectPropertiesAxiom231() {
        DF.getOWLEquivalentObjectPropertiesAxiom(ope, ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom01() {
        DF.getOWLDisjointObjectPropertiesAxiom((OWLObjectPropertyExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom010() {
        DF.getOWLDisjointObjectPropertiesAxiom((Set<OWLObjectPropertyExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom02() {
        DF.getOWLDisjointObjectPropertiesAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom021() {
        DF.getOWLDisjointObjectPropertiesAxiom(nullSetOWLOPE, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom12() {
        DF.getOWLDisjointObjectPropertiesAxiom(setowlope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom122() {
        DF.getOWLDisjointObjectPropertiesAxiom(setowlope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointObjectPropertiesAxiom121() {
        DF.getOWLDisjointObjectPropertiesAxiom(nullSetOWLOPE, null);
    }

    public <O> Set<O> getNullSet() {
        Set<O> s = new HashSet<O>();
        s.add(null);
        return s;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseObjectPropertiesAxiom02() {
        DF.getOWLInverseObjectPropertiesAxiom(null, ope);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseObjectPropertiesAxiom12() {
        DF.getOWLInverseObjectPropertiesAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseObjectPropertiesAxiom03() {
        DF.getOWLInverseObjectPropertiesAxiom(null, ope, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseObjectPropertiesAxiom13() {
        DF.getOWLInverseObjectPropertiesAxiom(ope, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseObjectPropertiesAxiom23() {
        DF.getOWLInverseObjectPropertiesAxiom(ope, ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseObjectPropertiesAxiom231() {
        DF.getOWLInverseObjectPropertiesAxiom(ope, ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyDomainAxiom02() {
        DF.getOWLObjectPropertyDomainAxiom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyDomainAxiom12() {
        DF.getOWLObjectPropertyDomainAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyDomainAxiom03() {
        DF.getOWLObjectPropertyDomainAxiom(null, ce, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyDomainAxiom13() {
        DF.getOWLObjectPropertyDomainAxiom(ope, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyDomainAxiom23() {
        DF.getOWLObjectPropertyDomainAxiom(ope, ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyDomainAxiom231() {
        DF.getOWLObjectPropertyDomainAxiom(ope, ce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyRangeAxiom03() {
        DF.getOWLObjectPropertyRangeAxiom(null, ce, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyRangeAxiom13() {
        DF.getOWLObjectPropertyRangeAxiom(ope, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyRangeAxiom23() {
        DF.getOWLObjectPropertyRangeAxiom(ope, ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyRangeAxiom231() {
        DF.getOWLObjectPropertyRangeAxiom(ope, ce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyRangeAxiom02() {
        DF.getOWLObjectPropertyRangeAxiom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyRangeAxiom12() {
        DF.getOWLObjectPropertyRangeAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom01() {
        DF.getOWLFunctionalObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom02() {
        DF.getOWLFunctionalObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom12() {
        DF.getOWLFunctionalObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalObjectPropertyAxiom121() {
        DF.getOWLFunctionalObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom01() {
        DF.getOWLInverseFunctionalObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom02() {
        DF.getOWLInverseFunctionalObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom12() {
        DF.getOWLInverseFunctionalObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLInverseFunctionalObjectPropertyAxiom121() {
        DF.getOWLInverseFunctionalObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom02() {
        DF.getOWLReflexiveObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom12() {
        DF.getOWLReflexiveObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom121() {
        DF.getOWLReflexiveObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLReflexiveObjectPropertyAxiom01() {
        DF.getOWLReflexiveObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom02() {
        DF.getOWLIrreflexiveObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom12() {
        DF.getOWLIrreflexiveObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom121() {
        DF.getOWLIrreflexiveObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLIrreflexiveObjectPropertyAxiom01() {
        DF.getOWLIrreflexiveObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom01() {
        DF.getOWLSymmetricObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom02() {
        DF.getOWLSymmetricObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom12() {
        DF.getOWLSymmetricObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSymmetricObjectPropertyAxiom121() {
        DF.getOWLSymmetricObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom02() {
        DF.getOWLAsymmetricObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom12() {
        DF.getOWLAsymmetricObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom121() {
        DF.getOWLAsymmetricObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAsymmetricObjectPropertyAxiom01() {
        DF.getOWLAsymmetricObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom01() {
        DF.getOWLTransitiveObjectPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom02() {
        DF.getOWLTransitiveObjectPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom12() {
        DF.getOWLTransitiveObjectPropertyAxiom(ope, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLTransitiveObjectPropertyAxiom121() {
        DF.getOWLTransitiveObjectPropertyAxiom(ope, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubDataPropertyOfAxiom02() {
        DF.getOWLSubDataPropertyOfAxiom(null, owldpe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubDataPropertyOfAxiom12() {
        DF.getOWLSubDataPropertyOfAxiom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubDataPropertyOfAxiom03() {
        DF.getOWLSubDataPropertyOfAxiom(null, owldpe, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubDataPropertyOfAxiom13() {
        DF.getOWLSubDataPropertyOfAxiom(owldpe, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubDataPropertyOfAxiom23() {
        DF.getOWLSubDataPropertyOfAxiom(owldpe, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubDataPropertyOfAxiom231() {
        DF.getOWLSubDataPropertyOfAxiom(owldpe, owldpe, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom020() {
        DF.getOWLEquivalentDataPropertiesAxiom(null, owldpe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom120() {
        DF.getOWLEquivalentDataPropertiesAxiom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom010() {
        DF.getOWLEquivalentDataPropertiesAxiom((OWLDataPropertyExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom021() {
        DF.getOWLEquivalentDataPropertiesAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom0211() {
        DF.getOWLEquivalentDataPropertiesAxiom(nullsetodpe, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom121() {
        DF.getOWLEquivalentDataPropertiesAxiom(setowldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom1211() {
        DF.getOWLEquivalentDataPropertiesAxiom(setowldpe, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom011() {
        DF.getOWLEquivalentDataPropertiesAxiom((Set<OWLDataPropertyExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom03() {
        DF.getOWLEquivalentDataPropertiesAxiom(null, owldpe, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom13() {
        DF.getOWLEquivalentDataPropertiesAxiom(owldpe, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom23() {
        DF.getOWLEquivalentDataPropertiesAxiom(owldpe, owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLEquivalentDataPropertiesAxiom231() {
        DF.getOWLEquivalentDataPropertiesAxiom(owldpe, owldpe, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointDataPropertiesAxiom01() {
        DF.getOWLDisjointDataPropertiesAxiom((OWLDataPropertyExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointDataPropertiesAxiom010() {
        DF.getOWLDisjointDataPropertiesAxiom((Set<OWLDataPropertyExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointDataPropertiesAxiom02() {
        DF.getOWLDisjointDataPropertiesAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointDataPropertiesAxiom021() {
        DF.getOWLDisjointDataPropertiesAxiom(nullsetodpe, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointDataPropertiesAxiom12() {
        DF.getOWLDisjointDataPropertiesAxiom(setowldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDisjointDataPropertiesAxiom121() {
        DF.getOWLDisjointDataPropertiesAxiom(setowldpe, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyDomainAxiom02() {
        DF.getOWLDataPropertyDomainAxiom(null, ce);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyDomainAxiom12() {
        DF.getOWLDataPropertyDomainAxiom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyDomainAxiom03() {
        DF.getOWLDataPropertyDomainAxiom(null, ce, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyDomainAxiom13() {
        DF.getOWLDataPropertyDomainAxiom(owldpe, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyDomainAxiom23() {
        DF.getOWLDataPropertyDomainAxiom(owldpe, ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyDomainAxiom231() {
        DF.getOWLDataPropertyDomainAxiom(owldpe, ce, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyRangeAxiom03() {
        DF.getOWLDataPropertyRangeAxiom(null, owldatarange, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyRangeAxiom13() {
        DF.getOWLDataPropertyRangeAxiom(owldpe, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyRangeAxiom23() {
        DF.getOWLDataPropertyRangeAxiom(owldpe, owldatarange, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyRangeAxiom231() {
        DF.getOWLDataPropertyRangeAxiom(owldpe, owldatarange, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyRangeAxiom02() {
        DF.getOWLDataPropertyRangeAxiom(null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyRangeAxiom12() {
        DF.getOWLDataPropertyRangeAxiom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalDataPropertyAxiom01() {
        DF.getOWLFunctionalDataPropertyAxiom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalDataPropertyAxiom02() {
        DF.getOWLFunctionalDataPropertyAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalDataPropertyAxiom12() {
        DF.getOWLFunctionalDataPropertyAxiom(owldpe, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLFunctionalDataPropertyAxiom121() {
        DF.getOWLFunctionalDataPropertyAxiom(owldpe, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom020() {
        DF.getOWLHasKeyAxiom(null, setowlpropertyexpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom120() {
        DF.getOWLHasKeyAxiom(ce, (OWLPropertyExpression) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom1201() {
        DF.getOWLHasKeyAxiom(ce, nullowlpropertyexpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom021() {
        DF.getOWLHasKeyAxiom(null, owlpropertyexpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom121() {
        DF.getOWLHasKeyAxiom(ce, (Set<OWLPropertyExpression>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom1211() {
        DF.getOWLHasKeyAxiom(ce, nullsetowlpropertyexpression);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom03() {
        DF.getOWLHasKeyAxiom(null, setowlpropertyexpression, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom13() {
        DF.getOWLHasKeyAxiom(ce, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom131() {
        DF.getOWLHasKeyAxiom(ce, nullsetowlpropertyexpression, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom23() {
        DF.getOWLHasKeyAxiom(ce, setowlpropertyexpression, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLHasKeyAxiom231() {
        DF.getOWLHasKeyAxiom(ce, setowlpropertyexpression, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeDefinitionAxiom02() {
        DF.getOWLDatatypeDefinitionAxiom(null, owldatarange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeDefinitionAxiom12() {
        DF.getOWLDatatypeDefinitionAxiom(owldatatype, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeDefinitionAxiom03() {
        DF.getOWLDatatypeDefinitionAxiom(null, owldatarange, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeDefinitionAxiom13() {
        DF.getOWLDatatypeDefinitionAxiom(owldatatype, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeDefinitionAxiom23() {
        DF.getOWLDatatypeDefinitionAxiom(owldatatype, owldatarange, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDatatypeDefinitionAxiom231() {
        DF.getOWLDatatypeDefinitionAxiom(owldatatype, owldatarange, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSameIndividualAxiom01() {
        DF.getOWLSameIndividualAxiom((OWLIndividual) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSameIndividualAxiom02() {
        DF.getOWLSameIndividualAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSameIndividualAxiom021() {
        DF.getOWLSameIndividualAxiom(nullsetowlindividual, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSameIndividualAxiom12() {
        DF.getOWLSameIndividualAxiom(setowlindividual, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSameIndividualAxiom121() {
        DF.getOWLSameIndividualAxiom(setowlindividual, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSameIndividualAxiom010() {
        DF.getOWLSameIndividualAxiom((Set<OWLIndividual>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDifferentIndividualsAxiom02() {
        DF.getOWLDifferentIndividualsAxiom(null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDifferentIndividualsAxiom021() {
        DF.getOWLDifferentIndividualsAxiom(nullsetowlindividual, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDifferentIndividualsAxiom12() {
        DF.getOWLDifferentIndividualsAxiom(setowlindividual, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDifferentIndividualsAxiom121() {
        DF.getOWLDifferentIndividualsAxiom(setowlindividual, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDifferentIndividualsAxiom010() {
        DF.getOWLDifferentIndividualsAxiom((OWLIndividual) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDifferentIndividualsAxiom01() {
        DF.getOWLDifferentIndividualsAxiom((Set<OWLIndividual>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClassAssertionAxiom02() {
        DF.getOWLClassAssertionAxiom(null, ind);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClassAssertionAxiom12() {
        DF.getOWLClassAssertionAxiom(ce, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClassAssertionAxiom03() {
        DF.getOWLClassAssertionAxiom(null, ind, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClassAssertionAxiom13() {
        DF.getOWLClassAssertionAxiom(ce, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClassAssertionAxiom23() {
        DF.getOWLClassAssertionAxiom(ce, ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLClassAssertionAxiom231() {
        DF.getOWLClassAssertionAxiom(ce, ind, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom03() {
        DF.getOWLObjectPropertyAssertionAxiom(null, ind, ind);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom13() {
        DF.getOWLObjectPropertyAssertionAxiom(ope, null, ind);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom23() {
        DF.getOWLObjectPropertyAssertionAxiom(ope, ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom04() {
        DF.getOWLObjectPropertyAssertionAxiom(null, ind, ind, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom14() {
        DF.getOWLObjectPropertyAssertionAxiom(ope, null, ind, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom24() {
        DF.getOWLObjectPropertyAssertionAxiom(ope, ind, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom34() {
        DF.getOWLObjectPropertyAssertionAxiom(ope, ind, ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLObjectPropertyAssertionAxiom341() {
        DF.getOWLObjectPropertyAssertionAxiom(ope, ind, ind, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom04() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(null, ind, ind, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom14() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(ope, null, ind, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom24() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom34() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom341() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, ind, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom03() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(null, ind, ind);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom13() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(ope, null, ind);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeObjectPropertyAssertionAxiom23() {
        DF.getOWLNegativeObjectPropertyAssertionAxiom(ope, ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom04() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, lit, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom14() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, lit, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom24() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, ind, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom34() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, ind, lit, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom341() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, ind, lit, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom030() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom130() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom031() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom134() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom034() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, 1D);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom131() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, 1D);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom032() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, 1F);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom135() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, 1F);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom234() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, ind, (String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom035() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom132() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom232() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, ind, (OWLLiteral) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom033() {
        DF.getOWLDataPropertyAssertionAxiom(null, ind, string);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLDataPropertyAssertionAxiom133() {
        DF.getOWLDataPropertyAssertionAxiom(owldpe, null, string);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom03() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(null, ind, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom13() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(owldpe, null, lit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom23() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom04() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(null, ind, lit, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom14() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(owldpe, null, lit, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom24() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom34() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, lit, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLNegativeDataPropertyAssertionAxiom341() {
        DF.getOWLNegativeDataPropertyAssertionAxiom(owldpe, ind, lit, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotation02() {
        DF.getOWLAnnotation(null, owlannvalue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotation12() {
        DF.getOWLAnnotation(owlap, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotation03() {
        DF.getOWLAnnotation(null, owlannvalue, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotation13() {
        DF.getOWLAnnotation(owlap, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotation23() {
        DF.getOWLAnnotation(owlap, owlannvalue, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotation231() {
        DF.getOWLAnnotation(owlap, owlannvalue, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom02() {
        DF.getOWLAnnotationAssertionAxiom(null, owlannotation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom12() {
        DF.getOWLAnnotationAssertionAxiom(owlannsubj, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom030() {
        DF.getOWLAnnotationAssertionAxiom(null, owlannsubj, owlannvalue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom130() {
        DF.getOWLAnnotationAssertionAxiom(owlap, null, owlannvalue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom230() {
        DF.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom04() {
        DF.getOWLAnnotationAssertionAxiom(null, owlannsubj, owlannvalue, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom14() {
        DF.getOWLAnnotationAssertionAxiom(owlap, null, owlannvalue, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom24() {
        DF.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom34() {
        DF.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, owlannvalue, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom341() {
        DF.getOWLAnnotationAssertionAxiom(owlap, owlannsubj, owlannvalue, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom031() {
        DF.getOWLAnnotationAssertionAxiom(null, owlannotation, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom131() {
        DF.getOWLAnnotationAssertionAxiom(owlannsubj, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom231() {
        DF.getOWLAnnotationAssertionAxiom(owlannsubj, owlannotation, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationAssertionAxiom2311() {
        DF.getOWLAnnotationAssertionAxiom(owlannsubj, owlannotation, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLImportsDeclaration01() {
        DF.getOWLImportsDeclaration(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom02() {
        DF.getOWLAnnotationPropertyDomainAxiom(null, iri);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom12() {
        DF.getOWLAnnotationPropertyDomainAxiom(owlap, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom03() {
        DF.getOWLAnnotationPropertyDomainAxiom(null, iri, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom13() {
        DF.getOWLAnnotationPropertyDomainAxiom(owlap, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom23() {
        DF.getOWLAnnotationPropertyDomainAxiom(owlap, iri, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyDomainAxiom231() {
        DF.getOWLAnnotationPropertyDomainAxiom(owlap, iri, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom02() {
        DF.getOWLAnnotationPropertyRangeAxiom(null, iri);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom12() {
        DF.getOWLAnnotationPropertyRangeAxiom(owlap, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom03() {
        DF.getOWLAnnotationPropertyRangeAxiom(null, iri, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom13() {
        DF.getOWLAnnotationPropertyRangeAxiom(owlap, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom23() {
        DF.getOWLAnnotationPropertyRangeAxiom(owlap, iri, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLAnnotationPropertyRangeAxiom231() {
        DF.getOWLAnnotationPropertyRangeAxiom(owlap, iri, nullsetowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom02() {
        DF.getOWLSubAnnotationPropertyOfAxiom(null, owlap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom12() {
        DF.getOWLSubAnnotationPropertyOfAxiom(owlap, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom03() {
        DF.getOWLSubAnnotationPropertyOfAxiom(null, owlap, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom13() {
        DF.getOWLSubAnnotationPropertyOfAxiom(owlap, null, setowlann);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom23() {
        DF.getOWLSubAnnotationPropertyOfAxiom(owlap, owlap, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetOWLSubAnnotationPropertyOfAxiom231() {
        DF.getOWLSubAnnotationPropertyOfAxiom(owlap, owlap, nullsetowlann);
    }
}
