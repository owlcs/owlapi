package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings("unused")
public class ContractApibindingTest {
    public void shouldTestOWLFunctionalSyntaxFactory() throws Exception {
        OWLFunctionalSyntaxFactory testSubject0 = new OWLFunctionalSyntaxFactory();
        OWLDeclarationAxiom result0 = OWLFunctionalSyntaxFactory.Declaration(Utils
                .mockOWLEntity());
        OWLEquivalentClassesAxiom result1 = OWLFunctionalSyntaxFactory
                .EquivalentClasses(Utils.mockAnonClass());
        OWLSubClassOfAxiom result2 = OWLFunctionalSyntaxFactory.SubClassOf(
                Utils.mockAnonClass(), Utils.mockAnonClass());
        OWLDisjointClassesAxiom result3 = OWLFunctionalSyntaxFactory
                .DisjointClasses(Utils.mockAnonClass());
        OWLDisjointUnionAxiom result4 = OWLFunctionalSyntaxFactory.DisjointUnion(
                mock(OWLClass.class), Utils.mockAnonClass());
        OWLClassAssertionAxiom result5 = OWLFunctionalSyntaxFactory.ClassAssertion(
                Utils.mockAnonClass(), mock(OWLIndividual.class));
        OWLSameIndividualAxiom result6 = OWLFunctionalSyntaxFactory
                .SameIndividual(mock(OWLIndividual[].class));
        OWLDifferentIndividualsAxiom result7 = OWLFunctionalSyntaxFactory
                .DifferentIndividuals(mock(OWLIndividual[].class));
        OWLObjectPropertyAssertionAxiom result8 = OWLFunctionalSyntaxFactory
                .ObjectPropertyAssertion(Utils.mockObjectProperty(),
                        mock(OWLIndividual.class), mock(OWLIndividual.class));
        OWLNegativeObjectPropertyAssertionAxiom result9 = OWLFunctionalSyntaxFactory
                .NegativeObjectPropertyAssertion(Utils.mockObjectProperty(),
                        mock(OWLIndividual.class), mock(OWLIndividual.class));
        OWLDataPropertyAssertionAxiom result10 = OWLFunctionalSyntaxFactory
                .DataPropertyAssertion(mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), mock(OWLLiteral.class));
        OWLNegativeDataPropertyAssertionAxiom result11 = OWLFunctionalSyntaxFactory
                .NegativeDataPropertyAssertion(mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), mock(OWLLiteral.class));
        OWLEquivalentObjectPropertiesAxiom result12 = OWLFunctionalSyntaxFactory
                .EquivalentObjectProperties(mock(OWLObjectPropertyExpression[].class));
        OWLSubObjectPropertyOfAxiom result13 = OWLFunctionalSyntaxFactory
                .SubObjectPropertyOf(Utils.mockObjectProperty(),
                        Utils.mockObjectProperty());
        OWLInverseObjectPropertiesAxiom result14 = OWLFunctionalSyntaxFactory
                .InverseObjectProperties(Utils.mockObjectProperty(),
                        Utils.mockObjectProperty());
        OWLFunctionalObjectPropertyAxiom result15 = OWLFunctionalSyntaxFactory
                .FunctionalObjectProperty(Utils.mockObjectProperty());
        OWLInverseFunctionalObjectPropertyAxiom result16 = OWLFunctionalSyntaxFactory
                .InverseFunctionalObjectProperty(Utils.mockObjectProperty());
        OWLSymmetricObjectPropertyAxiom result17 = OWLFunctionalSyntaxFactory
                .SymmetricObjectProperty(Utils.mockObjectProperty());
        OWLAsymmetricObjectPropertyAxiom result18 = OWLFunctionalSyntaxFactory
                .AsymmetricObjectProperty(Utils.mockObjectProperty());
        OWLTransitiveObjectPropertyAxiom result19 = OWLFunctionalSyntaxFactory
                .TransitiveObjectProperty(Utils.mockObjectProperty());
        OWLReflexiveObjectPropertyAxiom result20 = OWLFunctionalSyntaxFactory
                .ReflexiveObjectProperty(Utils.mockObjectProperty());
        OWLObjectPropertyDomainAxiom result21 = OWLFunctionalSyntaxFactory
                .ObjectPropertyDomain(Utils.mockObjectProperty(), Utils.mockAnonClass());
        OWLObjectPropertyRangeAxiom result22 = OWLFunctionalSyntaxFactory
                .ObjectPropertyRange(Utils.mockObjectProperty(), Utils.mockAnonClass());
        OWLDisjointObjectPropertiesAxiom result23 = OWLFunctionalSyntaxFactory
                .DisjointObjectProperties(mock(OWLObjectPropertyExpression[].class));
        OWLEquivalentDataPropertiesAxiom result24 = OWLFunctionalSyntaxFactory
                .EquivalentDataProperties(mock(OWLDataPropertyExpression[].class));
        OWLSubDataPropertyOfAxiom result25 = OWLFunctionalSyntaxFactory
                .SubDataPropertyOf(mock(OWLDataPropertyExpression.class),
                        mock(OWLDataPropertyExpression.class));
        OWLFunctionalDataPropertyAxiom result26 = OWLFunctionalSyntaxFactory
                .FunctionalDataProperty(mock(OWLDataPropertyExpression.class));
        OWLDataPropertyDomainAxiom result27 = OWLFunctionalSyntaxFactory
                .DataPropertyDomain(mock(OWLDataPropertyExpression.class),
                        Utils.mockAnonClass());
        OWLDataPropertyRangeAxiom result28 = OWLFunctionalSyntaxFactory
                .DataPropertyRange(mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class));
        OWLDisjointDataPropertiesAxiom result29 = OWLFunctionalSyntaxFactory
                .DisjointDataProperties(mock(OWLDataPropertyExpression[].class));
        OWLHasKeyAxiom result30 = OWLFunctionalSyntaxFactory.HasKey(
                Utils.mockAnonClass(), mock(OWLPropertyExpression[].class));
        OWLAnnotationAssertionAxiom result31 = OWLFunctionalSyntaxFactory
                .AnnotationAssertion(mock(OWLAnnotationProperty.class),
                        mock(OWLAnnotationSubject.class), mock(OWLAnnotationValue.class));
        OWLSubAnnotationPropertyOfAxiom result32 = OWLFunctionalSyntaxFactory
                .SubAnnotationPropertyOf(mock(OWLAnnotationProperty.class),
                        mock(OWLAnnotationProperty.class));
        OWLAnnotationPropertyDomainAxiom result33 = OWLFunctionalSyntaxFactory
                .AnnotationPropertyDomain(mock(OWLAnnotationProperty.class),
                        IRI.create("urn:aFake"));
        OWLDatatypeDefinitionAxiom result34 = OWLFunctionalSyntaxFactory
                .DatatypeDefinition(mock(OWLDatatype.class), mock(OWLDataRange.class));
        OWLClass result35 = OWLFunctionalSyntaxFactory.Class(IRI.create("urn:aFake"));
        OWLClass result36 = OWLFunctionalSyntaxFactory.Class("",
                new DefaultPrefixManager());
        OWLObjectSomeValuesFrom result37 = OWLFunctionalSyntaxFactory
                .ObjectSomeValuesFrom(Utils.mockObjectProperty(), Utils.mockAnonClass());
        OWLObjectAllValuesFrom result38 = OWLFunctionalSyntaxFactory.ObjectAllValuesFrom(
                Utils.mockObjectProperty(), Utils.mockAnonClass());
        OWLObjectMinCardinality result39 = OWLFunctionalSyntaxFactory
                .ObjectMinCardinality(0, Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectMaxCardinality result40 = OWLFunctionalSyntaxFactory
                .ObjectMaxCardinality(0, Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectExactCardinality result41 = OWLFunctionalSyntaxFactory
                .ObjectExactCardinality(0, Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectHasValue result42 = OWLFunctionalSyntaxFactory.ObjectHasValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class));
        OWLObjectHasSelf result43 = OWLFunctionalSyntaxFactory.ObjectHasSelf(Utils
                .mockObjectProperty());
        OWLDataSomeValuesFrom result44 = OWLFunctionalSyntaxFactory.DataSomeValuesFrom(
                mock(OWLDataPropertyExpression.class), mock(OWLDataRange.class));
        OWLDataAllValuesFrom result45 = OWLFunctionalSyntaxFactory.DataAllValuesFrom(
                mock(OWLDataPropertyExpression.class), mock(OWLDataRange.class));
        OWLDataMinCardinality result46 = OWLFunctionalSyntaxFactory.DataMinCardinality(0,
                mock(OWLDataPropertyExpression.class), mock(OWLDataRange.class));
        OWLDataMaxCardinality result47 = OWLFunctionalSyntaxFactory.DataMaxCardinality(0,
                mock(OWLDataPropertyExpression.class), mock(OWLDataRange.class));
        OWLDataExactCardinality result48 = OWLFunctionalSyntaxFactory
                .DataExactCardinality(0, mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class));
        OWLDataHasValue result49 = OWLFunctionalSyntaxFactory.DataHasValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class));
        OWLObjectIntersectionOf result50 = OWLFunctionalSyntaxFactory
                .ObjectIntersectionOf(Utils.mockAnonClass());
        OWLObjectUnionOf result51 = OWLFunctionalSyntaxFactory.ObjectUnionOf(Utils
                .mockAnonClass());
        OWLObjectComplementOf result52 = OWLFunctionalSyntaxFactory
                .ObjectComplementOf(Utils.mockAnonClass());
        OWLObjectOneOf result53 = OWLFunctionalSyntaxFactory
                .ObjectOneOf(mock(OWLIndividual[].class));
        OWLDatatype result54 = OWLFunctionalSyntaxFactory.Datatype(IRI
                .create("urn:aFake"));
        OWLDataOneOf result55 = OWLFunctionalSyntaxFactory
                .DataOneOf(mock(OWLLiteral[].class));
        OWLDatatypeRestriction result56 = OWLFunctionalSyntaxFactory.DatatypeRestriction(
                mock(OWLDatatype.class), mock(OWLFacetRestriction[].class));
        OWLDataComplementOf result57 = OWLFunctionalSyntaxFactory
                .DataComplementOf(mock(OWLDataRange.class));
        OWLDataUnionOf result58 = OWLFunctionalSyntaxFactory
                .DataUnionOf(mock(OWLDataRange[].class));
        OWLDataIntersectionOf result59 = OWLFunctionalSyntaxFactory
                .DataIntersectionOf(mock(OWLDataRange[].class));
        OWLObjectProperty result60 = OWLFunctionalSyntaxFactory.ObjectProperty(IRI
                .create("urn:aFake"));
        OWLObjectProperty result61 = OWLFunctionalSyntaxFactory.ObjectProperty("",
                new DefaultPrefixManager());
        OWLDataProperty result62 = OWLFunctionalSyntaxFactory.DataProperty("",
                new DefaultPrefixManager());
        OWLDataProperty result63 = OWLFunctionalSyntaxFactory.DataProperty(IRI
                .create("urn:aFake"));
        OWLAnnotationProperty result64 = OWLFunctionalSyntaxFactory.AnnotationProperty(
                "", new DefaultPrefixManager());
        OWLAnnotationProperty result65 = OWLFunctionalSyntaxFactory
                .AnnotationProperty(IRI.create("urn:aFake"));
        OWLNamedIndividual result66 = OWLFunctionalSyntaxFactory.NamedIndividual(IRI
                .create("urn:aFake"));
        OWLNamedIndividual result67 = OWLFunctionalSyntaxFactory.NamedIndividual("",
                new DefaultPrefixManager());
        OWLOntology result68 = OWLFunctionalSyntaxFactory.Ontology(
                Utils.getMockManager(), mock(OWLAxiom[].class));
        OWLLiteral result69 = OWLFunctionalSyntaxFactory.Literal(false);
        OWLLiteral result70 = OWLFunctionalSyntaxFactory.Literal(0F);
        OWLLiteral result71 = OWLFunctionalSyntaxFactory.Literal(0D);
        OWLLiteral result72 = OWLFunctionalSyntaxFactory.Literal("", "");
        OWLLiteral result73 = OWLFunctionalSyntaxFactory.Literal("");
        OWLLiteral result74 = OWLFunctionalSyntaxFactory.Literal(0);
        OWLLiteral result75 = OWLFunctionalSyntaxFactory.PlainLiteral("");
        IRI result76 = OWLFunctionalSyntaxFactory.IRI("");
        OWLObjectInverseOf result77 = OWLFunctionalSyntaxFactory.ObjectInverseOf(Utils
                .mockObjectProperty());
        OWLFacetRestriction result78 = OWLFunctionalSyntaxFactory.FacetRestriction(
                OWLFacet.MAX_INCLUSIVE, mock(OWLLiteral.class));
        OWLIrreflexiveObjectPropertyAxiom result79 = OWLFunctionalSyntaxFactory
                .IrreflexiveObjectProperty(Utils.mockObjectProperty());
        OWLAnnotationPropertyRangeAxiom result80 = OWLFunctionalSyntaxFactory
                .AnnotationPropertyRange(mock(OWLAnnotationProperty.class),
                        IRI.create("urn:aFake"));
        OWLClass result81 = OWLFunctionalSyntaxFactory.OWLThing();
        OWLClass result82 = OWLFunctionalSyntaxFactory.OWLNothing();
        String result83 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLManager() throws Exception {
        OWLManager testSubject0 = new OWLManager();
        OWLDataFactory result0 = testSubject0.getFactory();
        OWLDataFactory result1 = OWLManager.getOWLDataFactory();
        OWLOntologyManager result2 = testSubject0
                .buildOWLOntologyManager(mock(OWLDataFactory.class));
        OWLOntologyManager result3 = testSubject0.buildOWLOntologyManager();
        OWLOntologyManager result4 = OWLManager.createOWLOntologyManager();
        OWLOntologyManager result5 = OWLManager
                .createOWLOntologyManager(mock(OWLDataFactory.class));
        String result6 = testSubject0.toString();
    }
}
