package org.semanticweb.owlapi.api.test.alternate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.semanticweb.owlapi.api.test.Factory;
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

@SuppressWarnings({"javadoc","deprecation"})
public class NullCheckTest_otherfactory extends TestCase {
	private static final String MESSAGE = "Exception expected!";
	private static final OWLDataFactory f = Factory.getFactory();
	OWL2Datatype owl2datatype = OWL2Datatype.XSD_INT;
	OWLDataPropertyExpression owldatapropertyexpression = f.getOWLDataProperty(IRI
			.create("urn:dp"));
	OWLObjectPropertyExpression owlobjectpropertyexpression = f.getOWLObjectProperty(IRI
			.create("urn:op"));
	IRI iri = IRI.create("urn:iri");
	OWLLiteral owlliteral = f.getOWLLiteral(true);
	OWLAnnotationSubject owlannotationsubject = IRI.create("urn:i");
	OWLDatatype owldatatype = f.getOWLDatatype(owl2datatype.getIRI());
	OWLDataRange owldatarange = f.getOWLDatatypeRestriction(owldatatype);
	OWLAnnotationProperty owlannotationproperty = f.getOWLAnnotationProperty(IRI
			.create("urn:ap"));
	OWLFacet owlfacet = OWLFacet.MIN_EXCLUSIVE;
	OWLAnnotation owlannotation = f.getOWLAnnotation(owlannotationproperty, owlliteral);
	String string = "testString";
	OWLClass owlclass = f.getOWLClass(IRI.create("urn:classexpression"));
	OWLClassExpression owlclassexpression = owlclass;
	PrefixManager prefixmanager = new DefaultPrefixManager();
	OWLIndividual owlindividual = f.getOWLAnonymousIndividual();
	OWLAnnotationValue owlannotationvalue = owlliteral;
	Set<OWLObjectPropertyExpression> setowlobjectpropertyexpression = new HashSet<OWLObjectPropertyExpression>();
	Set<OWLObjectPropertyExpression> nullSetOWLObjectPropertyExpression = getNullSet();
	Set<OWLAnnotation> setowlannotation = new HashSet<OWLAnnotation>();
	Set<OWLAnnotation> nullsetowlannotation = getNullSet();
	Set<OWLDataPropertyExpression> setowldatapropertyexpression = new HashSet<OWLDataPropertyExpression>();
	Set<OWLDataPropertyExpression> nullsetowldatapropertyexpression = getNullSet();
	List<OWLObjectPropertyExpression> listowlobjectpropertyexpression = new ArrayList<OWLObjectPropertyExpression>();
	List<OWLObjectPropertyExpression> nulllistowlobjectpropertyexpression = new ArrayList<OWLObjectPropertyExpression>(
			Arrays.asList((OWLObjectPropertyExpression) null));
	Set<OWLIndividual> setowlindividual = new HashSet<OWLIndividual>();
	Set<OWLIndividual> nullsetowlindividual = getNullSet();
	Set<OWLPropertyExpression<?, ?>> setowlpropertyexpression = new HashSet<OWLPropertyExpression<?, ?>>();
	Set<OWLPropertyExpression<?, ?>> nullsetowlpropertyexpression = getNullSet();
	OWLFacetRestriction[] lowlfacetrestriction = new OWLFacetRestriction[] { f
			.getOWLFacetRestriction(owlfacet, 1) };
	OWLFacetRestriction[] nulllowlfacetrestriction = new OWLFacetRestriction[] {
			f.getOWLFacetRestriction(owlfacet, 1), null };
	Set<OWLClassExpression> setowlclassexpression = new HashSet<OWLClassExpression>();
	Set<OWLClassExpression> nullsetowlclassexpression = getNullSet();
	Set<OWLFacetRestriction> setowlfacetrestriction = new HashSet<OWLFacetRestriction>();
	Set<OWLFacetRestriction> nullsetowlfacetrestriction = getNullSet();
	OWLPropertyExpression[] owlpropertyexpression = new OWLPropertyExpression[] {};
	OWLPropertyExpression[] nullowlpropertyexpression = new OWLPropertyExpression[] { null };

	public void testgetOWLEntity0_2() {
		try {
			f.getOWLEntity(null, iri);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEntity1_2() {
		try {
			f.getOWLEntity(EntityType.CLASS, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClass0_1() {
		try {
			f.getOWLClass(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClass0_2() {
		try {
			f.getOWLClass(null, prefixmanager);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClass1_2() {
		try {
			f.getOWLClass(string, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectProperty0_1() {
		try {
			f.getOWLObjectProperty(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectProperty0_2() {
		try {
			f.getOWLObjectProperty(null, prefixmanager);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectProperty1_2() {
		try {
			f.getOWLObjectProperty(string, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectInverseOf0_1() {
		try {
			f.getOWLObjectInverseOf(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataProperty0_1() {
		try {
			f.getOWLDataProperty(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataProperty0_2() {
		try {
			f.getOWLDataProperty(null, prefixmanager);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataProperty1_2() {
		try {
			f.getOWLDataProperty(string, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNamedIndividual0_1() {
		try {
			f.getOWLNamedIndividual(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNamedIndividual0_2() {
		try {
			f.getOWLNamedIndividual(null, prefixmanager);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNamedIndividual1_2() {
		try {
			f.getOWLNamedIndividual(string, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnonymousIndividual0_1() {
		try {
			f.getOWLAnonymousIndividual(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationProperty0_1() {
		try {
			f.getOWLAnnotationProperty(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationProperty0_2() {
		try {
			f.getOWLAnnotationProperty(null, prefixmanager);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationProperty1_2() {
		try {
			f.getOWLAnnotationProperty(string, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatype0_1() {
		try {
			f.getOWLDatatype(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatype0_2() {
		try {
			f.getOWLDatatype(null, prefixmanager);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatype1_2() {
		try {
			f.getOWLDatatype(string, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLLiteral0_2() {
		try {
			f.getOWLLiteral(null, owldatatype);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLLiteral1_2_0() {
		try {
			f.getOWLLiteral(string, (OWLDatatype) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLLiteral0_2_1() {
		try {
			f.getOWLLiteral(null, owl2datatype);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLLiteral1_2_1() {
		try {
			f.getOWLLiteral(string, (OWL2Datatype) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLLiteral0_1_0() {
		try {
			f.getOWLLiteral(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLLiteral0_2_0() {
		try {
			f.getOWLLiteral(null, string);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTypedLiteral0_2_0() {
		try {
			f.getOWLTypedLiteral(null, owldatatype);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTypedLiteral1_2_0() {
		try {
			f.getOWLTypedLiteral(string, (OWLDatatype) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTypedLiteral0_2() {
		try {
			f.getOWLTypedLiteral(null, owl2datatype);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTypedLiteral1_2() {
		try {
			f.getOWLTypedLiteral(string, (OWL2Datatype) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTypedLiteral0_1_0() {
		try {
			f.getOWLTypedLiteral(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLStringLiteral0_2() {
		try {
			f.getOWLStringLiteral(null, string);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLStringLiteral0_1() {
		try {
			f.getOWLStringLiteral(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataOneOf0_1_0() {
		try {
			f.getOWLDataOneOf((OWLLiteral) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataOneOf0_1_0_1() {
		try {
			f.getOWLDataOneOf(owlliteral, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataOneOf0_1_1() {
		try {
			f.getOWLDataOneOf((Set<OWLLiteral>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLDataOneOf0_1_1_1() {
		try {
			f.getOWLDataOneOf(this.<OWLLiteral>getNullSet());
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataComplementOf0_1() {
		try {
			f.getOWLDataComplementOf(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction0_2_0() {
		try {
			f.getOWLDatatypeRestriction(null, setowlfacetrestriction);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction1_2_0() {
		try {
			f.getOWLDatatypeRestriction(owldatatype, (OWLFacetRestriction) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction1_2_0_1() {
		try {
			f.getOWLDatatypeRestriction(owldatatype, nulllowlfacetrestriction);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction0_3() {
		try {
			f.getOWLDatatypeRestriction(null, owlfacet, owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction1_3() {
		try {
			f.getOWLDatatypeRestriction(owldatatype, null, owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction2_3() {
		try {
			f.getOWLDatatypeRestriction(owldatatype, owlfacet, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction0_2() {
		try {
			f.getOWLDatatypeRestriction(null, lowlfacetrestriction);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction1_2() {
		try {
			f.getOWLDatatypeRestriction(owldatatype, (Set<OWLFacetRestriction>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeRestriction1_2_1() {
		try {
			f.getOWLDatatypeRestriction(owldatatype, nullsetowlfacetrestriction);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction0_2_0() {
		try {
			f.getOWLFacetRestriction(null, 1);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction1_2_0() {
		try {
			f.getOWLFacetRestriction(owlfacet, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction0_2_1() {
		try {
			f.getOWLFacetRestriction(null, 1F);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction1_2_1() {
		try {
			f.getOWLFacetRestriction(owlfacet, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction0_2_3() {
		try {
			f.getOWLFacetRestriction(null, 1D);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction1_2() {
		try {
			f.getOWLFacetRestriction(owlfacet, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction0_2_2() {
		try {
			f.getOWLFacetRestriction(null, owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFacetRestriction1_2_3() {
		try {
			f.getOWLFacetRestriction(owlfacet, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataUnionOf0_1() {
		try {
			f.getOWLDataUnionOf((OWLDataUnionOf) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLDataUnionOf0_1_1() {
		try {
			f.getOWLDataUnionOf(owldatarange, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataUnionOf0_1_0() {
		try {
			f.getOWLDataUnionOf((Set<OWLDataUnionOf>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLDataUnionOf0_1_0_1() {
		try {
			f.getOWLDataUnionOf(this.<OWLDataUnionOf>getNullSet());
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataIntersectionOf0_1_0() {
		try {
			f.getOWLDataIntersectionOf((OWLDataRange) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLDataIntersectionOf0_1_0_1() {
		try {
			f.getOWLDataIntersectionOf(owldatarange, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataIntersectionOf0_1_1() {
		try {
			f.getOWLDataIntersectionOf((Set<OWLDataRange>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLDataIntersectionOf0_1_1_1() {
		try {
			f.getOWLDataIntersectionOf(this.<OWLDataRange>getNullSet());
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectIntersectionOf0_1_0() {
		try {
			f.getOWLObjectIntersectionOf((OWLClassExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectIntersectionOf0_1_1() {
		try {
			f.getOWLObjectIntersectionOf((Set<OWLClassExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLObjectIntersectionOf0_1_0_1() {
		try {
			f.getOWLObjectIntersectionOf(owlclass, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectIntersectionOf0_1_1_1() {
		try {
			f.getOWLObjectIntersectionOf(this.<OWLClassExpression>getNullSet());
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataSomeValuesFrom0_2() {
		try {
			f.getOWLDataSomeValuesFrom(null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataSomeValuesFrom1_2() {
		try {
			f.getOWLDataSomeValuesFrom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataAllValuesFrom0_2() {
		try {
			f.getOWLDataAllValuesFrom(null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataAllValuesFrom1_2() {
		try {
			f.getOWLDataAllValuesFrom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataExactCardinality0_2() {
		try {
			f.getOWLDataExactCardinality(-1, owldatapropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataExactCardinality1_2() {
		try {
			f.getOWLDataExactCardinality(1, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataExactCardinality0_3() {
		try {
			f.getOWLDataExactCardinality(-1, owldatapropertyexpression, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataExactCardinality1_3() {
		try {
			f.getOWLDataExactCardinality(1, null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataExactCardinality2_3() {
		try {
			f.getOWLDataExactCardinality(1, owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMaxCardinality0_2() {
		try {
			f.getOWLDataMaxCardinality(-1, owldatapropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMaxCardinality1_2() {
		try {
			f.getOWLDataMaxCardinality(1, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMaxCardinality0_3() {
		try {
			f.getOWLDataMaxCardinality(-1, owldatapropertyexpression, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMaxCardinality1_3() {
		try {
			f.getOWLDataMaxCardinality(1, null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMaxCardinality2_3() {
		try {
			f.getOWLDataMaxCardinality(1, owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMinCardinality0_2() {
		try {
			f.getOWLDataMinCardinality(-1, owldatapropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMinCardinality1_2() {
		try {
			f.getOWLDataMinCardinality(1, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMinCardinality0_3() {
		try {
			f.getOWLDataMinCardinality(-1, owldatapropertyexpression, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMinCardinality1_3() {
		try {
			f.getOWLDataMinCardinality(1, null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataMinCardinality2_3() {
		try {
			f.getOWLDataMinCardinality(1, owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataHasValue0_2() {
		try {
			f.getOWLDataHasValue(null, owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataHasValue1_2() {
		try {
			f.getOWLDataHasValue(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectComplementOf0_1() {
		try {
			f.getOWLObjectComplementOf(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectOneOf0_1() {
		try {
			f.getOWLObjectOneOf((OWLIndividual) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectOneOf0_1_0() {
		try {
			f.getOWLObjectOneOf((Set<OWLIndividual>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLObjectOneOf0_1_1() {
		try {
			f.getOWLObjectOneOf(owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectOneOf0_1_0_1() {
		try {
			f.getOWLObjectOneOf(this.<OWLIndividual>getNullSet());
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectAllValuesFrom0_2() {
		try {
			f.getOWLObjectAllValuesFrom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectAllValuesFrom1_2() {
		try {
			f.getOWLObjectAllValuesFrom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectSomeValuesFrom0_2() {
		try {
			f.getOWLObjectSomeValuesFrom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectSomeValuesFrom1_2() {
		try {
			f.getOWLObjectSomeValuesFrom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectExactCardinality0_2() {
		try {
			f.getOWLObjectExactCardinality(-1, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectExactCardinality1_2() {
		try {
			f.getOWLObjectExactCardinality(1, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectExactCardinality0_3() {
		try {
			f.getOWLObjectExactCardinality(-1, owlobjectpropertyexpression,
					owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectExactCardinality1_3() {
		try {
			f.getOWLObjectExactCardinality(1, null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectExactCardinality2_3() {
		try {
			f.getOWLObjectExactCardinality(1, owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMinCardinality0_3() {
		try {
			f.getOWLObjectMinCardinality(-1, owlobjectpropertyexpression,
					owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMinCardinality1_3() {
		try {
			f.getOWLObjectMinCardinality(1, null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMinCardinality2_3() {
		try {
			f.getOWLObjectMinCardinality(1, owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMinCardinality0_2() {
		try {
			f.getOWLObjectMinCardinality(-1, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMinCardinality1_2() {
		try {
			f.getOWLObjectMinCardinality(1, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMaxCardinality0_2() {
		try {
			f.getOWLObjectMaxCardinality(-1, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMaxCardinality1_2() {
		try {
			f.getOWLObjectMaxCardinality(1, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMaxCardinality0_3() {
		try {
			f.getOWLObjectMaxCardinality(-1, owlobjectpropertyexpression,
					owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMaxCardinality1_3() {
		try {
			f.getOWLObjectMaxCardinality(1, null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectMaxCardinality2_3() {
		try {
			f.getOWLObjectMaxCardinality(1, owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectHasSelf0_1() {
		try {
			f.getOWLObjectHasSelf(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectHasValue0_2() {
		try {
			f.getOWLObjectHasValue(null, owlindividual);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectHasValue1_2() {
		try {
			f.getOWLObjectHasValue(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectUnionOf0_1() {
		try {
			f.getOWLObjectUnionOf((OWLClassExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectUnionOf0_1_0() {
		try {
			f.getOWLObjectUnionOf((Set<OWLClassExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
	public void testgetOWLObjectUnionOf0_1_1() {
		try {
			f.getOWLObjectUnionOf(owlclass, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectUnionOf0_1_0_1() {
		try {
			f.getOWLObjectUnionOf(this.<OWLClassExpression>getNullSet());
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDeclarationAxiom0_1() {
		try {
			f.getOWLDeclarationAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDeclarationAxiom0_2() {
		try {
			f.getOWLDeclarationAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDeclarationAxiom1_2() {
		try {
			f.getOWLDeclarationAxiom(owlclass, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDeclarationAxiom1_2_1() {
		try {
			f.getOWLDeclarationAxiom(owlclass, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubClassOfAxiom0_2() {
		try {
			f.getOWLSubClassOfAxiom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubClassOfAxiom1_2() {
		try {
			f.getOWLSubClassOfAxiom(owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubClassOfAxiom0_3() {
		try {
			f.getOWLSubClassOfAxiom(null, owlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubClassOfAxiom1_3() {
		try {
			f.getOWLSubClassOfAxiom(owlclassexpression, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubClassOfAxiom2_3() {
		try {
			f.getOWLSubClassOfAxiom(owlclassexpression, owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubClassOfAxiom2_3_1() {
		try {
			f.getOWLSubClassOfAxiom(owlclassexpression, owlclassexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom0_2_0() {
		try {
			f.getOWLEquivalentClassesAxiom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom1_2_0() {
		try {
			f.getOWLEquivalentClassesAxiom(owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom0_3() {
		try {
			f.getOWLEquivalentClassesAxiom(null, owlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom1_3() {
		try {
			f.getOWLEquivalentClassesAxiom(owlclassexpression, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom2_3() {
		try {
			f.getOWLEquivalentClassesAxiom(owlclassexpression, owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom2_3_1() {
		try {
			f.getOWLEquivalentClassesAxiom(owlclassexpression, owlclassexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom0_1_0() {
		try {
			f.getOWLEquivalentClassesAxiom((OWLClassExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom0_2_1() {
		try {
			f.getOWLEquivalentClassesAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom0_2_1_1() {
		try {
			f.getOWLEquivalentClassesAxiom(nullsetowlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom1_2_1() {
		try {
			f.getOWLEquivalentClassesAxiom(setowlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom1_2_1_1() {
		try {
			f.getOWLEquivalentClassesAxiom(setowlclassexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentClassesAxiom0_1_1() {
		try {
			f.getOWLEquivalentClassesAxiom((Set<OWLClassExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointClassesAxiom0_2() {
		try {
			f.getOWLDisjointClassesAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointClassesAxiom0_2_1() {
		try {
			f.getOWLDisjointClassesAxiom(nullsetowlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointClassesAxiom1_2() {
		try {
			f.getOWLDisjointClassesAxiom(setowlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointClassesAxiom1_2_1() {
		try {
			f.getOWLDisjointClassesAxiom(setowlclassexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointClassesAxiom0_1() {
		try {
			f.getOWLDisjointClassesAxiom((OWLClassExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointClassesAxiom0_1_0() {
		try {
			f.getOWLDisjointClassesAxiom((Set<OWLClassExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom0_3() {
		try {
			f.getOWLDisjointUnionAxiom(null, setowlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom1_3() {
		try {
			f.getOWLDisjointUnionAxiom(owlclass, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom1_3_1() {
		try {
			f.getOWLDisjointUnionAxiom(owlclass, nullsetowlclassexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom2_3() {
		try {
			f.getOWLDisjointUnionAxiom(owlclass, setowlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom2_3_1() {
		try {
			f.getOWLDisjointUnionAxiom(owlclass, setowlclassexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom0_2() {
		try {
			f.getOWLDisjointUnionAxiom(null, setowlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointUnionAxiom1_2() {
		try {
			f.getOWLDisjointUnionAxiom(owlclass, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubObjectPropertyOfAxiom0_2() {
		try {
			f.getOWLSubObjectPropertyOfAxiom(null, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubObjectPropertyOfAxiom1_2() {
		try {
			f.getOWLSubObjectPropertyOfAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubObjectPropertyOfAxiom0_3() {
		try {
			f.getOWLSubObjectPropertyOfAxiom(null, owlobjectpropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubObjectPropertyOfAxiom1_3() {
		try {
			f.getOWLSubObjectPropertyOfAxiom(owlobjectpropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubObjectPropertyOfAxiom2_3() {
		try {
			f.getOWLSubObjectPropertyOfAxiom(owlobjectpropertyexpression,
					owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubObjectPropertyOfAxiom2_3_1() {
		try {
			f.getOWLSubObjectPropertyOfAxiom(owlobjectpropertyexpression,
					owlobjectpropertyexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom0_3() {
		try {
			f.getOWLSubPropertyChainOfAxiom(null, owlobjectpropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom0_3_1() {
		try {
			f.getOWLSubPropertyChainOfAxiom(nulllistowlobjectpropertyexpression,
					owlobjectpropertyexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom1_3() {
		try {
			f.getOWLSubPropertyChainOfAxiom(listowlobjectpropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom2_3() {
		try {
			f.getOWLSubPropertyChainOfAxiom(listowlobjectpropertyexpression,
					owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom2_3_1() {
		try {
			f.getOWLSubPropertyChainOfAxiom(listowlobjectpropertyexpression,
					owlobjectpropertyexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom0_2() {
		try {
			f.getOWLSubPropertyChainOfAxiom(null, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom0_2_1() {
		try {
			f.getOWLSubPropertyChainOfAxiom(nulllistowlobjectpropertyexpression,
					owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubPropertyChainOfAxiom1_2() {
		try {
			f.getOWLSubPropertyChainOfAxiom(listowlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom0_1_0() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom((OWLObjectPropertyExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom0_2_0() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom0_2_0_1() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(nullSetOWLObjectPropertyExpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom1_2_0() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(setowlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom1_2_0_2() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(setowlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom1_2_0_1() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(nullSetOWLObjectPropertyExpression,
					null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom0_1_1() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom((Set<OWLObjectPropertyExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom0_2_1() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(null, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom1_2_1() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom0_3() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(null, owlobjectpropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom1_3() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(owlobjectpropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom2_3() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(owlobjectpropertyexpression,
					owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentObjectPropertiesAxiom2_3_1() {
		try {
			f.getOWLEquivalentObjectPropertiesAxiom(owlobjectpropertyexpression,
					owlobjectpropertyexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom0_1() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom((OWLObjectPropertyExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom0_1_0() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom((Set<OWLObjectPropertyExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom0_2() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom0_2_1() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom(nullSetOWLObjectPropertyExpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom1_2() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom(setowlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom1_2_2() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom(setowlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointObjectPropertiesAxiom1_2_1() {
		try {
			f.getOWLDisjointObjectPropertiesAxiom(nullSetOWLObjectPropertyExpression,
					null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public <O> Set<O> getNullSet() {
		Set<O> s = new HashSet<O>();
		s.add(null);
		return s;
	}

	public void testgetOWLInverseObjectPropertiesAxiom0_2() {
		try {
			f.getOWLInverseObjectPropertiesAxiom(null, owlobjectpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseObjectPropertiesAxiom1_2() {
		try {
			f.getOWLInverseObjectPropertiesAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseObjectPropertiesAxiom0_3() {
		try {
			f.getOWLInverseObjectPropertiesAxiom(null, owlobjectpropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseObjectPropertiesAxiom1_3() {
		try {
			f.getOWLInverseObjectPropertiesAxiom(owlobjectpropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseObjectPropertiesAxiom2_3() {
		try {
			f.getOWLInverseObjectPropertiesAxiom(owlobjectpropertyexpression,
					owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseObjectPropertiesAxiom2_3_1() {
		try {
			f.getOWLInverseObjectPropertiesAxiom(owlobjectpropertyexpression,
					owlobjectpropertyexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyDomainAxiom0_2() {
		try {
			f.getOWLObjectPropertyDomainAxiom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyDomainAxiom1_2() {
		try {
			f.getOWLObjectPropertyDomainAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyDomainAxiom0_3() {
		try {
			f.getOWLObjectPropertyDomainAxiom(null, owlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyDomainAxiom1_3() {
		try {
			f.getOWLObjectPropertyDomainAxiom(owlobjectpropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyDomainAxiom2_3() {
		try {
			f.getOWLObjectPropertyDomainAxiom(owlobjectpropertyexpression,
					owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyDomainAxiom2_3_1() {
		try {
			f.getOWLObjectPropertyDomainAxiom(owlobjectpropertyexpression,
					owlclassexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyRangeAxiom0_3() {
		try {
			f.getOWLObjectPropertyRangeAxiom(null, owlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyRangeAxiom1_3() {
		try {
			f.getOWLObjectPropertyRangeAxiom(owlobjectpropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyRangeAxiom2_3() {
		try {
			f.getOWLObjectPropertyRangeAxiom(owlobjectpropertyexpression,
					owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyRangeAxiom2_3_1() {
		try {
			f.getOWLObjectPropertyRangeAxiom(owlobjectpropertyexpression,
					owlclassexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyRangeAxiom0_2() {
		try {
			f.getOWLObjectPropertyRangeAxiom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyRangeAxiom1_2() {
		try {
			f.getOWLObjectPropertyRangeAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalObjectPropertyAxiom0_1() {
		try {
			f.getOWLFunctionalObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalObjectPropertyAxiom0_2() {
		try {
			f.getOWLFunctionalObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalObjectPropertyAxiom1_2() {
		try {
			f.getOWLFunctionalObjectPropertyAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLFunctionalObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseFunctionalObjectPropertyAxiom0_1() {
		try {
			f.getOWLInverseFunctionalObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseFunctionalObjectPropertyAxiom0_2() {
		try {
			f.getOWLInverseFunctionalObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseFunctionalObjectPropertyAxiom1_2() {
		try {
			f.getOWLInverseFunctionalObjectPropertyAxiom(owlobjectpropertyexpression,
					null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLInverseFunctionalObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLInverseFunctionalObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLReflexiveObjectPropertyAxiom0_2() {
		try {
			f.getOWLReflexiveObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLReflexiveObjectPropertyAxiom1_2() {
		try {
			f.getOWLReflexiveObjectPropertyAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLReflexiveObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLReflexiveObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLReflexiveObjectPropertyAxiom0_1() {
		try {
			f.getOWLReflexiveObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLIrreflexiveObjectPropertyAxiom0_2() {
		try {
			f.getOWLIrreflexiveObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLIrreflexiveObjectPropertyAxiom1_2() {
		try {
			f.getOWLIrreflexiveObjectPropertyAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLIrreflexiveObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLIrreflexiveObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLIrreflexiveObjectPropertyAxiom0_1() {
		try {
			f.getOWLIrreflexiveObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSymmetricObjectPropertyAxiom0_1() {
		try {
			f.getOWLSymmetricObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSymmetricObjectPropertyAxiom0_2() {
		try {
			f.getOWLSymmetricObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSymmetricObjectPropertyAxiom1_2() {
		try {
			f.getOWLSymmetricObjectPropertyAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSymmetricObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLSymmetricObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAsymmetricObjectPropertyAxiom0_2() {
		try {
			f.getOWLAsymmetricObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAsymmetricObjectPropertyAxiom1_2() {
		try {
			f.getOWLAsymmetricObjectPropertyAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAsymmetricObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLAsymmetricObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAsymmetricObjectPropertyAxiom0_1() {
		try {
			f.getOWLAsymmetricObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTransitiveObjectPropertyAxiom0_1() {
		try {
			f.getOWLTransitiveObjectPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTransitiveObjectPropertyAxiom0_2() {
		try {
			f.getOWLTransitiveObjectPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTransitiveObjectPropertyAxiom1_2() {
		try {
			f.getOWLTransitiveObjectPropertyAxiom(owlobjectpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLTransitiveObjectPropertyAxiom1_2_1() {
		try {
			f.getOWLTransitiveObjectPropertyAxiom(owlobjectpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubDataPropertyOfAxiom0_2() {
		try {
			f.getOWLSubDataPropertyOfAxiom(null, owldatapropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubDataPropertyOfAxiom1_2() {
		try {
			f.getOWLSubDataPropertyOfAxiom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubDataPropertyOfAxiom0_3() {
		try {
			f.getOWLSubDataPropertyOfAxiom(null, owldatapropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubDataPropertyOfAxiom1_3() {
		try {
			f.getOWLSubDataPropertyOfAxiom(owldatapropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubDataPropertyOfAxiom2_3() {
		try {
			f.getOWLSubDataPropertyOfAxiom(owldatapropertyexpression,
					owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubDataPropertyOfAxiom2_3_1() {
		try {
			f.getOWLSubDataPropertyOfAxiom(owldatapropertyexpression,
					owldatapropertyexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom0_2_0() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(null, owldatapropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom1_2_0() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom0_1_0() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom((OWLDataPropertyExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom0_2_1() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom0_2_1_1() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(nullsetowldatapropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom1_2_1() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(setowldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom1_2_1_1() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(setowldatapropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom0_1_1() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom((Set<OWLDataPropertyExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom0_3() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(null, owldatapropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom1_3() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(owldatapropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom2_3() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(owldatapropertyexpression,
					owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLEquivalentDataPropertiesAxiom2_3_1() {
		try {
			f.getOWLEquivalentDataPropertiesAxiom(owldatapropertyexpression,
					owldatapropertyexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointDataPropertiesAxiom0_1() {
		try {
			f.getOWLDisjointDataPropertiesAxiom((OWLDataPropertyExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointDataPropertiesAxiom0_1_0() {
		try {
			f.getOWLDisjointDataPropertiesAxiom((Set<OWLDataPropertyExpression>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointDataPropertiesAxiom0_2() {
		try {
			f.getOWLDisjointDataPropertiesAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointDataPropertiesAxiom0_2_1() {
		try {
			f.getOWLDisjointDataPropertiesAxiom(nullsetowldatapropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointDataPropertiesAxiom1_2() {
		try {
			f.getOWLDisjointDataPropertiesAxiom(setowldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDisjointDataPropertiesAxiom1_2_1() {
		try {
			f.getOWLDisjointDataPropertiesAxiom(setowldatapropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyDomainAxiom0_2() {
		try {
			f.getOWLDataPropertyDomainAxiom(null, owlclassexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyDomainAxiom1_2() {
		try {
			f.getOWLDataPropertyDomainAxiom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyDomainAxiom0_3() {
		try {
			f.getOWLDataPropertyDomainAxiom(null, owlclassexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyDomainAxiom1_3() {
		try {
			f.getOWLDataPropertyDomainAxiom(owldatapropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyDomainAxiom2_3() {
		try {
			f.getOWLDataPropertyDomainAxiom(owldatapropertyexpression,
					owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyDomainAxiom2_3_1() {
		try {
			f.getOWLDataPropertyDomainAxiom(owldatapropertyexpression,
					owlclassexpression, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyRangeAxiom0_3() {
		try {
			f.getOWLDataPropertyRangeAxiom(null, owldatarange, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyRangeAxiom1_3() {
		try {
			f.getOWLDataPropertyRangeAxiom(owldatapropertyexpression, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyRangeAxiom2_3() {
		try {
			f.getOWLDataPropertyRangeAxiom(owldatapropertyexpression, owldatarange, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyRangeAxiom2_3_1() {
		try {
			f.getOWLDataPropertyRangeAxiom(owldatapropertyexpression, owldatarange,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyRangeAxiom0_2() {
		try {
			f.getOWLDataPropertyRangeAxiom(null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyRangeAxiom1_2() {
		try {
			f.getOWLDataPropertyRangeAxiom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalDataPropertyAxiom0_1() {
		try {
			f.getOWLFunctionalDataPropertyAxiom(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalDataPropertyAxiom0_2() {
		try {
			f.getOWLFunctionalDataPropertyAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalDataPropertyAxiom1_2() {
		try {
			f.getOWLFunctionalDataPropertyAxiom(owldatapropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLFunctionalDataPropertyAxiom1_2_1() {
		try {
			f.getOWLFunctionalDataPropertyAxiom(owldatapropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom0_2_0() {
		try {
			f.getOWLHasKeyAxiom(null, setowlpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom1_2_0() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, (OWLPropertyExpression) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom1_2_0_1() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, nullowlpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom0_2_1() {
		try {
			f.getOWLHasKeyAxiom(null, owlpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom1_2_1() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression,
					(Set<OWLPropertyExpression<?, ?>>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom1_2_1_1() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, nullsetowlpropertyexpression);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom0_3() {
		try {
			f.getOWLHasKeyAxiom(null, setowlpropertyexpression, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom1_3() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom1_3_1() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, nullsetowlpropertyexpression,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom2_3() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, setowlpropertyexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLHasKeyAxiom2_3_1() {
		try {
			f.getOWLHasKeyAxiom(owlclassexpression, setowlpropertyexpression,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeDefinitionAxiom0_2() {
		try {
			f.getOWLDatatypeDefinitionAxiom(null, owldatarange);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeDefinitionAxiom1_2() {
		try {
			f.getOWLDatatypeDefinitionAxiom(owldatatype, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeDefinitionAxiom0_3() {
		try {
			f.getOWLDatatypeDefinitionAxiom(null, owldatarange, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeDefinitionAxiom1_3() {
		try {
			f.getOWLDatatypeDefinitionAxiom(owldatatype, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeDefinitionAxiom2_3() {
		try {
			f.getOWLDatatypeDefinitionAxiom(owldatatype, owldatarange, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDatatypeDefinitionAxiom2_3_1() {
		try {
			f.getOWLDatatypeDefinitionAxiom(owldatatype, owldatarange,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSameIndividualAxiom0_1() {
		try {
			f.getOWLSameIndividualAxiom((OWLIndividual) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSameIndividualAxiom0_2() {
		try {
			f.getOWLSameIndividualAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSameIndividualAxiom0_2_1() {
		try {
			f.getOWLSameIndividualAxiom(nullsetowlindividual, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSameIndividualAxiom1_2() {
		try {
			f.getOWLSameIndividualAxiom(setowlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSameIndividualAxiom1_2_1() {
		try {
			f.getOWLSameIndividualAxiom(setowlindividual, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSameIndividualAxiom0_1_0() {
		try {
			f.getOWLSameIndividualAxiom((Set<OWLIndividual>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDifferentIndividualsAxiom0_2() {
		try {
			f.getOWLDifferentIndividualsAxiom(null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDifferentIndividualsAxiom0_2_1() {
		try {
			f.getOWLDifferentIndividualsAxiom(nullsetowlindividual, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDifferentIndividualsAxiom1_2() {
		try {
			f.getOWLDifferentIndividualsAxiom(setowlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDifferentIndividualsAxiom1_2_1() {
		try {
			f.getOWLDifferentIndividualsAxiom(setowlindividual, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDifferentIndividualsAxiom0_1_0() {
		try {
			f.getOWLDifferentIndividualsAxiom((OWLIndividual) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDifferentIndividualsAxiom0_1() {
		try {
			f.getOWLDifferentIndividualsAxiom((Set<OWLIndividual>) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClassAssertionAxiom0_2() {
		try {
			f.getOWLClassAssertionAxiom(null, owlindividual);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClassAssertionAxiom1_2() {
		try {
			f.getOWLClassAssertionAxiom(owlclassexpression, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClassAssertionAxiom0_3() {
		try {
			f.getOWLClassAssertionAxiom(null, owlindividual, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClassAssertionAxiom1_3() {
		try {
			f.getOWLClassAssertionAxiom(owlclassexpression, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClassAssertionAxiom2_3() {
		try {
			f.getOWLClassAssertionAxiom(owlclassexpression, owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLClassAssertionAxiom2_3_1() {
		try {
			f.getOWLClassAssertionAxiom(owlclassexpression, owlindividual,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom0_3() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(null, owlindividual, owlindividual);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom1_3() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(owlobjectpropertyexpression, null,
					owlindividual);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom2_3() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom0_4() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(null, owlindividual, owlindividual,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom1_4() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(owlobjectpropertyexpression, null,
					owlindividual, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom2_4() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom3_4() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLObjectPropertyAssertionAxiom3_4_1() {
		try {
			f.getOWLObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, owlindividual, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom0_4() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(null, owlindividual,
					owlindividual, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom1_4() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					null, owlindividual, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom2_4() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom3_4() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom3_4_1() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, owlindividual, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom0_3() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(null, owlindividual,
					owlindividual);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom1_3() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					null, owlindividual);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeObjectPropertyAssertionAxiom2_3() {
		try {
			f.getOWLNegativeObjectPropertyAssertionAxiom(owlobjectpropertyexpression,
					owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, owlliteral,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null,
					owlliteral, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom2_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, owlindividual,
					null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom3_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, owlindividual,
					owlliteral, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom3_4_1() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, owlindividual,
					owlliteral, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_3_0() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_3_0() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null,
					owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_3_1() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, 1);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_3_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null, 1);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_3_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, 1D);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_3_1() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null, 1D);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_3_2() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, 1F);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_3_5() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null, 1F);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom2_3_4() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, owlindividual,
					(String) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_3_5() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, true);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_3_2() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null, true);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom2_3_2() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, owlindividual,
					(OWLLiteral) null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom0_3_3() {
		try {
			f.getOWLDataPropertyAssertionAxiom(null, owlindividual, string);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLDataPropertyAssertionAxiom1_3_3() {
		try {
			f.getOWLDataPropertyAssertionAxiom(owldatapropertyexpression, null, string);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom0_3() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(null, owlindividual, owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom1_3() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(owldatapropertyexpression, null,
					owlliteral);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom2_3() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(owldatapropertyexpression,
					owlindividual, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom0_4() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(null, owlindividual, owlliteral,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom1_4() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(owldatapropertyexpression, null,
					owlliteral, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom2_4() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(owldatapropertyexpression,
					owlindividual, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom3_4() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(owldatapropertyexpression,
					owlindividual, owlliteral, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLNegativeDataPropertyAssertionAxiom3_4_1() {
		try {
			f.getOWLNegativeDataPropertyAssertionAxiom(owldatapropertyexpression,
					owlindividual, owlliteral, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotation0_2() {
		try {
			f.getOWLAnnotation(null, owlannotationvalue);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotation1_2() {
		try {
			f.getOWLAnnotation(owlannotationproperty, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotation0_3() {
		try {
			f.getOWLAnnotation(null, owlannotationvalue, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotation1_3() {
		try {
			f.getOWLAnnotation(owlannotationproperty, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotation2_3() {
		try {
			f.getOWLAnnotation(owlannotationproperty, owlannotationvalue, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotation2_3_1() {
		try {
			f.getOWLAnnotation(owlannotationproperty, owlannotationvalue,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom0_2() {
		try {
			f.getOWLAnnotationAssertionAxiom(null, owlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom1_2() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationsubject, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom0_3_0() {
		try {
			f.getOWLAnnotationAssertionAxiom(null, owlannotationsubject,
					owlannotationvalue);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom1_3_0() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationproperty, null,
					owlannotationvalue);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom2_3_0() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationproperty, owlannotationsubject,
					null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom0_4() {
		try {
			f.getOWLAnnotationAssertionAxiom(null, owlannotationsubject,
					owlannotationvalue, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom1_4() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationproperty, null,
					owlannotationvalue, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom2_4() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationproperty, owlannotationsubject,
					null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom3_4() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationproperty, owlannotationsubject,
					owlannotationvalue, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom3_4_1() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationproperty, owlannotationsubject,
					owlannotationvalue, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom0_3_1() {
		try {
			f.getOWLAnnotationAssertionAxiom(null, owlannotation, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom1_3_1() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationsubject, null, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom2_3_1() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationsubject, owlannotation, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationAssertionAxiom2_3_1_1() {
		try {
			f.getOWLAnnotationAssertionAxiom(owlannotationsubject, owlannotation,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLImportsDeclaration0_1() {
		try {
			f.getOWLImportsDeclaration(null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyDomainAxiom0_2() {
		try {
			f.getOWLAnnotationPropertyDomainAxiom(null, iri);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyDomainAxiom1_2() {
		try {
			f.getOWLAnnotationPropertyDomainAxiom(owlannotationproperty, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyDomainAxiom0_3() {
		try {
			f.getOWLAnnotationPropertyDomainAxiom(null, iri, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyDomainAxiom1_3() {
		try {
			f.getOWLAnnotationPropertyDomainAxiom(owlannotationproperty, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyDomainAxiom2_3() {
		try {
			f.getOWLAnnotationPropertyDomainAxiom(owlannotationproperty, iri, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyDomainAxiom2_3_1() {
		try {
			f.getOWLAnnotationPropertyDomainAxiom(owlannotationproperty, iri,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyRangeAxiom0_2() {
		try {
			f.getOWLAnnotationPropertyRangeAxiom(null, iri);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyRangeAxiom1_2() {
		try {
			f.getOWLAnnotationPropertyRangeAxiom(owlannotationproperty, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyRangeAxiom0_3() {
		try {
			f.getOWLAnnotationPropertyRangeAxiom(null, iri, setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyRangeAxiom1_3() {
		try {
			f.getOWLAnnotationPropertyRangeAxiom(owlannotationproperty, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyRangeAxiom2_3() {
		try {
			f.getOWLAnnotationPropertyRangeAxiom(owlannotationproperty, iri, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLAnnotationPropertyRangeAxiom2_3_1() {
		try {
			f.getOWLAnnotationPropertyRangeAxiom(owlannotationproperty, iri,
					nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubAnnotationPropertyOfAxiom0_2() {
		try {
			f.getOWLSubAnnotationPropertyOfAxiom(null, owlannotationproperty);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubAnnotationPropertyOfAxiom1_2() {
		try {
			f.getOWLSubAnnotationPropertyOfAxiom(owlannotationproperty, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubAnnotationPropertyOfAxiom0_3() {
		try {
			f.getOWLSubAnnotationPropertyOfAxiom(null, owlannotationproperty,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubAnnotationPropertyOfAxiom1_3() {
		try {
			f.getOWLSubAnnotationPropertyOfAxiom(owlannotationproperty, null,
					setowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubAnnotationPropertyOfAxiom2_3() {
		try {
			f.getOWLSubAnnotationPropertyOfAxiom(owlannotationproperty,
					owlannotationproperty, null);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}

	public void testgetOWLSubAnnotationPropertyOfAxiom2_3_1() {
		try {
			f.getOWLSubAnnotationPropertyOfAxiom(owlannotationproperty,
					owlannotationproperty, nullsetowlannotation);
		} catch (IllegalArgumentException ex) {
			return;
		}
		fail(MESSAGE);
	}
}