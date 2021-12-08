package org.semanticweb.owlapi.api.test.baseclasses;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings("javadoc")
public class DF {
//@formatter:off
    private static final String URNTESTS_URI = "urn:tests#uri";
    private static final OWLDataFactory DF = OWLManager.getOWLDataFactory();
    public static final String uriBase = "http://www.semanticweb.org/owlapi/test";
    public static final String OWLAPI_TEST = uriBase + "#";
    public static final String OBO = "http://purl.obolibrary.org/obo/";
    public static final IRI TEST_IMPORT = iri("http://purl.obolibrary.org/obo/uberon/", "test_import.owl");
    public static final IRI UBERON = iri("urn:test:", "uberon");
    public static final String OBO_IN_OWL = "http://www.geneontology.org/formats/oboInOwl#";
    public static final String OBO_UO = "http://purl.obolibrary.org/obo/uo#";
    public static final OWLAnnotationProperty g = AnnotationProperty(iri("urn:test#", "g"));
    public static final OWLAnnotationProperty h = AnnotationProperty(iri("urn:test#", "h"));
    public static final OWLLiteral lit1 = Literal(3.5D);
    public static final OWLDataProperty s = DataProperty(iri("urn:test#", "s"));
    public static final OWLDataProperty dt = DataProperty(iri("urn:test#", "t"));
    public static final OWLDataProperty v = DataProperty(iri("urn:test#", "v"));
    public static final OWLNamedIndividual x = NamedIndividual(iri("urn:test#", "x"));
    public static final OWLNamedIndividual y = NamedIndividual(iri("urn:test#", "y"));
    public static final OWLNamedIndividual indz = NamedIndividual(iri("urn:test#", "z"));
    public static final OWLClass cheesy = Class(iri("urn:test#", "CheeseyPizza"));
    public static final OWLClass cheese = Class(iri("urn:test#", "CheeseTopping"));
    public static final OWLObjectProperty hasTopping = ObjectProperty(iri("urn:test#", "hasTopping"));
    public static final IRI TEST_OBO = iri("http://purl.obolibrary.org/obo/tests/", "test.obo");
    public static final OWLLiteral LITVALUE3 = Literal("value3");
    public static final OWLLiteral LITVALUE = Literal("value");
    public static final OWLLiteral LITVALUE2 = Literal("value2");
    public static final OWLLiteral LITVALUE1 = Literal("value1");
    public static final OWLAnnotationProperty date = AnnotationProperty(iri(OBO_IN_OWL, "date"));
    public static final OWLAnnotationProperty mpathSlim = AnnotationProperty(iri(OBO_UO, "mpath_slim"));
    public static final OWLAnnotationProperty subsetProperty = AnnotationProperty(iri(OBO_IN_OWL, "SubsetProperty"));
    public static final OWLAnnotationProperty attributeSlim = AnnotationProperty(iri(OBO_UO, "attribute_slim"));
    public static final OWLAnnotationProperty hasOBONamespace = AnnotationProperty(iri(OBO_IN_OWL, "hasOBONamespace"));
    public static final OWLAnnotationProperty autogeneratedby = AnnotationProperty(iri(OBO_IN_OWL, "auto-generated-by"));
    public static final OWLAnnotationProperty hasDbXref = AnnotationProperty(iri(OBO_IN_OWL, "hasDbXref"));
    public static final OWLAnnotationProperty defaultnamespace = AnnotationProperty(iri(OBO_IN_OWL, "default-namespace"));
    public static final OWLAnnotationProperty hasOBOFormatVersion = AnnotationProperty(iri(OBO_IN_OWL, "hasOBOFormatVersion"));
    public static final OWLAnnotationProperty iao0000115 = AnnotationProperty(iri(OBO, "IAO_0000115"));
    public static final OWLAnnotationProperty namespaceIdRule = AnnotationProperty(iri(OBO_IN_OWL, "NamespaceIdRule"));
    public static final OWLAnnotationProperty createdBy = AnnotationProperty(iri(OBO_IN_OWL, "created_by"));
    public static final OWLAnnotationProperty inSubset = AnnotationProperty(iri(OBO_IN_OWL, "inSubset"));
    public static final OWLAnnotationProperty savedby = AnnotationProperty(iri(OBO_IN_OWL, "saved-by"));
    public static final OWLClass pato0001708 = Class(iri(OBO, "PATO_0001708"));
    public static final OWLClass uo0 = Class(iri(OBO, "UO_0000000"));
    public static final OWLClass uo1 = Class(iri(OBO, "UO_0000001"));
    public static final OWLAnnotationProperty id = AnnotationProperty(iri(OBO_IN_OWL, "id"));
    public static final OWLAnnotationProperty abnormalSlim = AnnotationProperty(iri(OBO_UO, "abnormal_slim"));
    public static final OWLAnnotationProperty scalarSlim = AnnotationProperty(iri(OBO_UO, "scalar_slim"));
    public static final OWLLiteral literal = Literal("Wikipedia:Wikipedia");
    public static final OWLAnnotationProperty unitSlim = AnnotationProperty(iri(OBO_UO, "unit_slim"));
    public static final OWLAnnotationProperty absentSlim = AnnotationProperty(iri(OBO_UO, "absent_slim"));
    public static final OWLObjectProperty isUnitOf = ObjectProperty(iri(OBO_UO, "is_unit_of"));
    public static final OWLAnnotationProperty cellQuality = AnnotationProperty(iri(OBO_UO, "cell_quality"));
    public static final OWLAnnotationProperty unitGroupSlim = AnnotationProperty(iri(OBO_UO, "unit_group_slim"));
    public static final OWLAnnotationProperty valueSlim = AnnotationProperty(iri(OBO_UO, "value_slim"));
    public static final OWLAnnotationProperty prefixSlim = AnnotationProperty(iri(OBO_UO, "prefix_slim"));
    public static final OWLAnnotationProperty dispositionSlim = AnnotationProperty(iri(OBO_UO, "disposition_slim"));
    public static final OWLAnnotationProperty relationalSlim = AnnotationProperty(iri(OBO_UO, "relational_slim"));
    public static final IRI SHORTHAND = iri(OBO_IN_OWL, "shorthand");
    public static final IRI ID = iri(OBO_IN_OWL, "id");
    public static final IRI BFO50 = iri(OBO, "BFO_0000050");
    public static final IRI RO2111 = iri(OBO, "RO_0002111");
    public static final IRI BAR1 = iri(OBO, "BAR_0000001");
    public static final IRI BFO51 = iri(OBO, "BFO_0000051");
    public static final OWLLiteral LIT_TRUE = Literal(true);
    public static final OWLLiteral LIT_FALSE = Literal(false);
    public static final OWLLiteral LIT_ONE = Literal(1);
    public static final OWLLiteral LIT_TWO = Literal(2);
    public static final OWLLiteral LIT_THREE = Literal(3);
    public static final OWLLiteral LIT_FOUR = Literal(4);
    public static final IRI ontologyIRITest = iri(OBO, "test.owl");
    public static final String DECLARATIONS         = "http://www.semanticweb.org/ontologies/declarations#";
    public static final OWLAnnotationProperty defProperty =            AnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0000115.getIRI());
    public static final OWLDeclarationAxiom DATATYPE = Declaration(Datatype(iri(DECLARATIONS, "dt")));
    public static final OWLDeclarationAxiom ANNOTATIONP = Declaration(AnnotationProperty(iri(DECLARATIONS, "ap")));
    public static final OWLDeclarationAxiom NAMED_INDIVIDUAL = Declaration(NamedIndividual(iri(DECLARATIONS, "ni")));
    public static final OWLDeclarationAxiom DATA_PROPERTY = Declaration(DataProperty(iri(DECLARATIONS, "dp")));
    public static final OWLDeclarationAxiom DECLARATION_A = Declaration(Class(iri("http://owlapi.sourceforge.net/ontology#", "ClsA")));
    public static final OWLDataProperty hasAge = DataProperty(iri("http://example.org/", "hasAge"));
    public static final IRI iriTest = iri("test");
    public static final OWLLiteral val = Literal("Test", "");
    public static final OWLClass powerYoga = Class("urn:test#", "PowerYoga");
    public static final OWLClass yoga = Class("urn:test#", "Yoga");
    public static final OWLClass relaxation = Class("urn:test#", "Relaxation");
    public static final OWLClass activity = Class("urn:test#", "Activity");

    public static final OWLClass A                  = Class(iri("A"));
    public static final OWLClass B                  = Class(iri("B"));
    public static final OWLClass C                  = Class(iri("C"));
    public static final OWLClass D                  = Class(iri("D"));
    public static final OWLClass E                  = Class(iri("E"));
    public static final OWLClass F                  = Class(iri("F"));
    public static final OWLClass G                  = Class(iri("G"));
    public static final OWLClass K                  = Class(iri("K"));
    public static final OWLClass X                  = Class(iri("X"));
    public static final OWLClass Y                  = Class(iri("Y"));
    public static final OWLClass C1                 = Class(iri(OBO, "TEST_1"));
    public static final OWLClass C2                 = Class(iri(OBO, "TEST_2"));
    public static final OWLClass C3                 = Class(iri(OBO, "TEST_3"));
    public static final OWLClass C4                 = Class(iri(OBO, "TEST_4"));
    public static final OWLClass C5                 = Class(iri(OBO, "TEST_5"));
    public static final OWLObjectProperty c         = ObjectProperty(iri("c"));
    public static final OWLObjectProperty d         = ObjectProperty(iri("d"));
    public static final OWLObjectProperty e         = ObjectProperty(iri("e"));
    public static final OWLObjectProperty f         = ObjectProperty(iri("f"));
    public static final OWLObjectProperty P         = ObjectProperty(iri("p"));
    public static final OWLObjectProperty Q         = ObjectProperty(iri("q"));
    public static final OWLObjectProperty R         = ObjectProperty(iri("r"));
    public static final OWLObjectProperty S         = ObjectProperty(iri("s"));
    public static final OWLObjectProperty t         = ObjectProperty(iri("t"));
    public static final OWLObjectProperty u         = ObjectProperty(iri("u"));
    public static final OWLObjectProperty w         = ObjectProperty(iri("w"));
    public static final OWLObjectProperty z         = ObjectProperty(iri("z"));
    public static final OWLDataProperty DP          = DataProperty(iri("p"));
    public static final OWLDataProperty DQ          = DataProperty(iri("q"));
    public static final OWLDataProperty DR          = DataProperty(iri("r"));
    public static final OWLDataProperty DS          = DataProperty(iri("s"));
    public static final OWLDataProperty DPT         = DataProperty(iri("urn:test#", "t"));
    public static final OWLDataProperty DPROP       = DataProperty(iri("prop"));
    public static final OWLObjectProperty PROP      = ObjectProperty(iri("prop"));
    public static final OWLDataProperty DPP         = DataProperty(iri("dp"));
    public static final OWLDataProperty dp1         = DataProperty(iri("dp1"));
    public static final OWLDataProperty dp2         = DataProperty(iri("dp2"));
    public static final OWLDataProperty dp3         = DataProperty(iri("dp3"));
    public static final OWLObjectProperty op1       = ObjectProperty(iri("op1"));
    public static final OWLObjectProperty op2       = ObjectProperty(iri("op2"));
    public static final OWLNamedIndividual I        = NamedIndividual(iri("i"));
    public static final OWLNamedIndividual J        = NamedIndividual(iri("j"));
    public static final OWLNamedIndividual k        = NamedIndividual(iri("k"));
    public static final OWLNamedIndividual l        = NamedIndividual(iri("l"));
    public static final OWLNamedIndividual indA     = NamedIndividual(iri("a"));
    public static final OWLNamedIndividual indB     = NamedIndividual(iri("b"));
    public static final OWLNamedIndividual indC     = NamedIndividual(iri("c"));
    public static final OWLObjectComplementOf notC  = ObjectComplementOf(C);
    public static final OWLObjectComplementOf notB  = ObjectComplementOf(B);
    public static final OWLObjectComplementOf notA  = ObjectComplementOf(A);
    public static final OWLNamedIndividual i        = NamedIndividual(iri("I"));
    public static final OWLAnnotationProperty AP    = AnnotationProperty(iri("propA"));
    public static final OWLAnnotationProperty propP = AnnotationProperty(iri("propP"));
    public static final OWLAnnotationProperty propQ = AnnotationProperty(iri("propQ"));
    public static final OWLAnnotationProperty propR = AnnotationProperty(iri("propR"));
    public static final OWLDataProperty PD          = DataProperty(iri("propD"));
    public static final OWLDatatype DT              = Datatype(iri("DT"));
    public static final OWLDatatype DTA             = Datatype(iri("DtA"));
    public static final OWLDatatype DTB             = Datatype(iri("DtB"));
    public static final OWLDatatype DTC             = Datatype(iri("DtC"));
    public static final OWLAnnotationProperty areaTotal = AnnotationProperty(iri("http://dbpedia.org/ontology/", "areaTotal"));
    public static final IRI southAfrica             = iri("http://dbpedia.org/resource/", "South_Africa");
    public static final OWLLiteral oneMillionth     = Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE);
    public static final IRI clsA                    = iri("ClsA");
    public static final IRI prop                    = iri("prop");
    public static final OWLNamedIndividual subject  = NamedIndividual(iri("http://Example.com#", "myBuilding"));
    public static final OWLObjectProperty predicate = ObjectProperty(iri("http://Example.com#", "located_at"));
    public static final OWLNamedIndividual object   = NamedIndividual(iri("http://Example.com#", "myLocation"));
    public static final OWLDataProperty DAP         = DataProperty(iri("http://example.com/", "dataProperty"));
    public static final OWLObjectProperty OP        = ObjectProperty(iri("http://example.com/", "objectProperty"));
    public static final OWLClass PERSON             = Class(iri("http://example.com/", "Person"));
    public static final OWLDatatype DATAB           = Datatype(iri("B"));
    protected DF() {}
    @SafeVarargs
    public static <T> List<T> l(T... vals) {if (vals.length==0) {return Collections.emptyList();} return asList(Stream.of(vals).distinct());}
    public static <T> List<T> l(T value) {return Collections.singletonList(value);}
    public static IRI iri(String name) {return iri(OWLAPI_TEST, name);}
    public static IRI iri(File file) {return IRI.create(file);}
    public static IRI iri(String p, String fragment) {return IRI.create(p, fragment);}
    public static OWLImportsDeclaration ImportsDeclaration(IRI in) {return DF.getOWLImportsDeclaration(in);}
    // Entities
    public static OWLClass Class(IRI iri) {return DF.getOWLClass(iri);}
    public static OWLClass Class(String ns, String in) {return DF.getOWLClass(ns, in);}
    public static OWLClass createClass() {return Class(IRI.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLObjectProperty createObjectProperty() {return ObjectProperty(IRI.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLDataProperty createDataProperty() {return DataProperty(IRI.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLNamedIndividual createIndividual() {return NamedIndividual(IRI.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLAnnotationProperty createAnnotationProperty() {return AnnotationProperty(IRI.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLLiteral createOWLLiteral() {return Literal("Test" + System.currentTimeMillis(),     Datatype(IRI.getNextDocumentIRI(URNTESTS_URI)));}
    public static OWLClass Class(String abbreviatedIRI, PrefixManager pm) {return DF.getOWLClass(abbreviatedIRI, pm);}
    public static OWLAnnotationProperty RDFSComment() {return DF.getRDFSComment();}
    public static OWLAnnotation RDFSComment(String value) {return DF.getOWLAnnotation(DF.getRDFSComment(), DF.getOWLLiteral(value));}
    public static OWLAnnotation RDFSComment(OWLLiteral value) {return DF.getOWLAnnotation(DF.getRDFSComment(), value);}
    public static OWLAnnotationProperty Deprecated() {return DF.getOWLDeprecated();}
    public static OWLAnnotationProperty RDFSLabel() {return DF.getRDFSLabel();}
    public static OWLAnnotationProperty VersionInfo() {return DF.getOWLVersionInfo();}
    public static OWLDatatype String() {return DF.getStringOWLDatatype();}
    public static OWLDatatype RDFSLiteral() {return DF.getOWLDatatype(OWL2Datatype.RDFS_LITERAL.getIRI());}
    public static OWLDatatype RDFPlainLiteral() {return DF.getRDFPlainLiteral();}
    public static OWLAnnotationProperty BackwardCompatibleWith() {return DF.getOWLBackwardCompatibleWith();}
    public static OWLAnnotationAssertionAxiom DeprecatedOWLAnnotationAssertion(IRI in) {return DF.getDeprecatedOWLAnnotationAssertionAxiom(in);}
    public static OWLDatatypeRestriction DatatypeMinInclusiveRestriction(double in) {return DF.getOWLDatatypeMinInclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMaxInclusiveRestriction(double in) {return DF.getOWLDatatypeMaxInclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMinMaxInclusiveRestriction(double min, double max) {return DF.getOWLDatatypeMinMaxInclusiveRestriction(min, max);}
    public static OWLDatatypeRestriction DatatypeMinExclusiveRestriction(int in) {return DF.getOWLDatatypeMinExclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMaxExclusiveRestriction(int in) {return DF.getOWLDatatypeMaxExclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMaxExclusiveRestriction(double in) {return DF.getOWLDatatypeMaxExclusiveRestriction(in);}
    public static OWLAnnotationProperty IncompatibleWith() {return DF.getOWLIncompatibleWith();}
    public static OWLAnnotationProperty RDFSIsDefinedBy() {return DF.getRDFSIsDefinedBy();}
    public static OWLAnnotationProperty RDFSSeeAlso() {return DF.getRDFSSeeAlso();}
    public static OWLDataProperty BottomDataProperty() {return DF.getOWLBottomDataProperty();}
    public static OWLAnnotation RDFSLabel(String value) {return DF.getOWLAnnotation(DF.getRDFSLabel(), DF.getOWLLiteral(value));}
    public static OWLAnnotation RDFSLabel(Stream<OWLAnnotation> annotations, String value) {return DF.getOWLAnnotation(DF.getRDFSLabel(), DF.getOWLLiteral(value), annotations);}
    public static OWLAnnotation RDFSLabel(String value, OWLAnnotation... annotations) {return RDFSLabel(Stream.of(annotations), value);}
    public static OWLAnnotation RDFSLabel(OWLAnnotationValue value, OWLAnnotation... annotations) {return RDFSLabel(Stream.of(annotations), value);}
    public static OWLAnnotation RDFSLabel(OWLAnnotationValue value) {return DF.getOWLAnnotation(DF.getRDFSLabel(), value);}
    public static OWLAnnotation RDFSLabel(Stream<OWLAnnotation> annotations, OWLAnnotationValue value) {return DF.getOWLAnnotation(DF.getRDFSLabel(), value, annotations);}
    public static OWLDataProperty TopDataProperty() {return DF.getOWLTopDataProperty();}
    public static OWLObjectProperty TopObjectProperty() {return DF.getOWLTopObjectProperty();}
    public static OWLObjectProperty BottomObjectProperty() {return DF.getOWLBottomObjectProperty();}
    public static OWLDatatype TopDatatype() {return DF.getTopDatatype();}
    public static OWLClass OWLThing() {return DF.getOWLThing();}
    public static OWLDatatype Integer() {return DF.getIntegerOWLDatatype();}
    public static OWLDatatype Double() {return DF.getDoubleOWLDatatype();}
    public static OWLDatatype Long() {return DF.getOWLDatatype(OWL2Datatype.XSD_LONG.getIRI());}
    public static OWLDatatype Float() {return DF.getFloatOWLDatatype();}
    public static OWLDatatype Boolean() {return DF.getBooleanOWLDatatype();}
    public static OWLClass OWLNothing() {return DF.getOWLNothing();}
    public static OWLObjectProperty ObjectProperty(IRI iri) {return DF.getOWLObjectProperty(iri);}
    public static OWLObjectProperty ObjectProperty(String abbreviatedIRI, PrefixManager pm) {return DF.getOWLObjectProperty(abbreviatedIRI, pm);}
    public static OWLObjectInverseOf ObjectInverseOf(OWLObjectProperty pe) {return DF.getOWLObjectInverseOf(pe);}
    public static OWLDataProperty DataProperty(IRI iri) {return DF.getOWLDataProperty(iri);}
    public static OWLDataProperty DataProperty(String abbreviatedIRI, PrefixManager pm) {return DF.getOWLDataProperty(abbreviatedIRI, pm);}
    public static OWLAnnotationProperty AnnotationProperty(IRI iri) {return DF.getOWLAnnotationProperty(iri);}
    public static OWLAnnotationProperty AnnotationProperty(String abbreviatedIRI, PrefixManager pm) {return DF.getOWLAnnotationProperty(abbreviatedIRI, pm);}
    public static OWLNamedIndividual NamedIndividual(IRI iri) {return DF.getOWLNamedIndividual(iri);}
    public static OWLAnonymousIndividual AnonymousIndividual() {return DF.getOWLAnonymousIndividual();}
    public static OWLAnonymousIndividual AnonymousIndividual(String in) {return DF.getOWLAnonymousIndividual(in);}
    public static OWLNamedIndividual NamedIndividual(String abbreviatedIRI, PrefixManager pm) {return DF.getOWLNamedIndividual(abbreviatedIRI, pm);}
    public static OWLDatatype Datatype(IRI iri) {return DF.getOWLDatatype(iri);}
    public static OWLDeclarationAxiom Declaration(OWLEntity entity) {return DF.getOWLDeclarationAxiom(entity);}
    public static OWLDeclarationAxiom Declaration(Collection<OWLAnnotation> a, OWLEntity entity) {return DF.getOWLDeclarationAxiom(entity, a);}
    public static OWLDeclarationAxiom Declaration(OWLEntity entity, OWLAnnotation... a) {return Declaration(l(a), entity);}
    public static OWLDeclarationAxiom Declaration(OWLAnnotation a, OWLEntity entity) {return Declaration(l(a), entity);}
    // Class Expressions
    public static OWLObjectIntersectionOf ObjectIntersectionOf(OWLClassExpression... classExpressions) {return DF.getOWLObjectIntersectionOf(classExpressions);}
    public static OWLObjectUnionOf ObjectUnionOf(OWLClassExpression... classExpressions) {return DF.getOWLObjectUnionOf(classExpressions);}
    public static OWLObjectComplementOf ObjectComplementOf(OWLClassExpression classExpression) {return DF.getOWLObjectComplementOf(classExpression);}
    public static OWLObjectSomeValuesFrom ObjectSomeValuesFrom(OWLObjectPropertyExpression pe, OWLClassExpression ce) {return DF.getOWLObjectSomeValuesFrom(pe, ce);}
    public static OWLObjectAllValuesFrom ObjectAllValuesFrom(OWLObjectPropertyExpression pe, OWLClassExpression ce) {return DF.getOWLObjectAllValuesFrom(pe, ce);}
    public static OWLObjectHasValue ObjectHasValue(OWLObjectPropertyExpression pe, OWLIndividual individual) {return DF.getOWLObjectHasValue(pe, individual);}
    public static OWLObjectMinCardinality ObjectMinCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {return DF.getOWLObjectMinCardinality(cardinality, pe, ce);}
    public static OWLObjectMaxCardinality ObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {return DF.getOWLObjectMaxCardinality(cardinality, pe, ce);}
    public static OWLObjectExactCardinality ObjectExactCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {return DF.getOWLObjectExactCardinality(cardinality, pe, ce);}
    public static OWLObjectHasSelf ObjectHasSelf(OWLObjectPropertyExpression pe) {return DF.getOWLObjectHasSelf(pe);}
    public static OWLObjectOneOf ObjectOneOf(OWLIndividual... individuals) {return DF.getOWLObjectOneOf(individuals);}
    public static OWLDataSomeValuesFrom DataSomeValuesFrom(OWLDataPropertyExpression pe, OWLDataRange dr) {return DF.getOWLDataSomeValuesFrom(pe, dr);}
    public static OWLDataAllValuesFrom DataAllValuesFrom(OWLDataPropertyExpression pe, OWLDataRange dr) {return DF.getOWLDataAllValuesFrom(pe, dr);}
    public static OWLDataHasValue DataHasValue(OWLDataPropertyExpression pe, OWLLiteral in) {return DF.getOWLDataHasValue(pe, in);}
    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {return DF.getOWLDataMinCardinality(cardinality, pe, dr);}
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {return DF.getOWLDataMaxCardinality(cardinality, pe, dr);}
    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {return DF.getOWLDataExactCardinality(cardinality, pe, dr);}
    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe, OWL2Datatype dr) {return DF.getOWLDataMinCardinality(cardinality, pe, dr);}
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe, OWL2Datatype dr) {return DF.getOWLDataMaxCardinality(cardinality, pe, dr);}
    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe, OWL2Datatype dr) {return DF.getOWLDataExactCardinality(cardinality, pe, dr);}
    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe) {return DF.getOWLDataMinCardinality(cardinality, pe);}
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe) {return DF.getOWLDataMaxCardinality(cardinality, pe);}
    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe) {return DF.getOWLDataExactCardinality(cardinality, pe);}
    // Data Ranges other than datatype
    public static OWLDataIntersectionOf DataIntersectionOf(OWLDataRange... dataRanges) {return DF.getOWLDataIntersectionOf(dataRanges);}
    public static OWLDataUnionOf DataUnionOf(OWLDataRange... dataRanges) {return DF.getOWLDataUnionOf(dataRanges);}
    public static OWLDataComplementOf DataComplementOf(OWLDataRange dataRange) {return DF.getOWLDataComplementOf(dataRange);}
    public static OWLDataOneOf DataOneOf(OWLLiteral... literals) {return DF.getOWLDataOneOf(literals);}
    public static OWLDatatypeRestriction DatatypeMinMaxExclusiveRestriction(int minExclusive, int maxExclusive) {return DatatypeRestriction(DF.getIntegerOWLDatatype(),     DF.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, DF.getOWLLiteral(minExclusive)),     DF.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));}
    public static OWLDatatypeRestriction DatatypeMinMaxExclusiveRestriction(double minExclusive, double maxExclusive) {return DatatypeRestriction(DF.getDoubleOWLDatatype(),     DF.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, DF.getOWLLiteral(minExclusive)),     DF.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));}
    public static OWLDatatypeRestriction DatatypeRestriction(OWLDatatype datatype, OWLFacetRestriction... facetRestrictions) {return DF.getOWLDatatypeRestriction(datatype, facetRestrictions);}
    public static OWLFacetRestriction FacetRestriction(OWLFacet facet, OWLLiteral facetValue) {return DF.getOWLFacetRestriction(facet, facetValue);}
    // Axioms
    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass, OWLClassExpression superClass) {return DF.getOWLSubClassOfAxiom(subClass, superClass);}
    public static OWLSubClassOfAxiom SubClassOf(Collection<OWLAnnotation> a, OWLClassExpression subClass, OWLClassExpression superClass) {return DF.getOWLSubClassOfAxiom(subClass, superClass, a);}
    public static OWLSubClassOfAxiom SubClassOf(OWLAnnotation a, OWLClassExpression subClass, OWLClassExpression superClass) {return DF.getOWLSubClassOfAxiom(subClass, superClass, l(a));}
    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass, OWLClassExpression superClass,OWLAnnotation... a) {return DF.getOWLSubClassOfAxiom(subClass, superClass, l(a));}
    public static OWLEquivalentClassesAxiom EquivalentClasses(OWLClassExpression... classExpressions) {return DF.getOWLEquivalentClassesAxiom(classExpressions);}
    public static OWLEquivalentClassesAxiom EquivalentClasses(Collection<OWLAnnotation> a, OWLClassExpression... classExpressions) {return DF.getOWLEquivalentClassesAxiom(l(classExpressions), a);}
    public static OWLEquivalentClassesAxiom EquivalentClasses(OWLAnnotation a, OWLClassExpression... classExpressions) {return DF.getOWLEquivalentClassesAxiom(l(classExpressions), l(a));}
    public static OWLDisjointClassesAxiom DisjointClasses(OWLClassExpression... classExpressions) {return DF.getOWLDisjointClassesAxiom(classExpressions);}
    public static OWLDisjointClassesAxiom DisjointClasses(Collection<? extends OWLClassExpression> classExpressions) {return DF.getOWLDisjointClassesAxiom(classExpressions);}
    public static OWLDisjointClassesAxiom DisjointClasses(Collection<OWLAnnotation> a, Collection<OWLClassExpression> classExpressions) {return DF.getOWLDisjointClassesAxiom(classExpressions, a);}
    public static OWLDisjointClassesAxiom DisjointClasses(OWLAnnotation a, Collection<OWLClassExpression> classExpressions) {return DF.getOWLDisjointClassesAxiom(classExpressions, l(a));}
    public static OWLDisjointClassesAxiom DisjointClasses(OWLAnnotation a, OWLClassExpression... classExpressions) {return DF.getOWLDisjointClassesAxiom(l(classExpressions), l(a));}
    public static OWLDisjointUnionAxiom DisjointUnion(OWLClass cls, OWLClassExpression... classExpressions) {return DF.getOWLDisjointUnionAxiom(cls, l(classExpressions));}
    public static OWLDisjointUnionAxiom DisjointUnion(Collection<OWLAnnotation> a, OWLClass cls, OWLClassExpression... classExpressions) {return DF.getOWLDisjointUnionAxiom(cls, l(classExpressions), a);}
    public static OWLDisjointUnionAxiom DisjointUnion(OWLAnnotation a, OWLClass cls, OWLClassExpression... classExpressions) {return DF.getOWLDisjointUnionAxiom(cls, l(classExpressions), l(a));}
    public static OWLDisjointClassesAxiom DisjointClasses(Collection<OWLAnnotation> a, OWLClassExpression... classExpressions) {return DF.getOWLDisjointClassesAxiom(l(classExpressions), a);}
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);}
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty);}
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(Collection<OWLAnnotation> a, List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty, a);}
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(Collection<OWLAnnotation> a, OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, a);}
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(OWLAnnotation a, List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {return DF.getOWLSubPropertyChainOfAxiom(chain, superProperty, l(a));}
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(OWLAnnotation a, OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {return DF.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, l(a));}
    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(OWLObjectPropertyExpression... properties) {return DF.getOWLEquivalentObjectPropertiesAxiom(properties);}
    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(Collection<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {return DF.getOWLEquivalentObjectPropertiesAxiom(l(properties), a);}
    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(OWLAnnotation a, OWLObjectPropertyExpression... properties) {return DF.getOWLEquivalentObjectPropertiesAxiom(l(properties), l(a));}
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(OWLObjectPropertyExpression... properties) {return DF.getOWLDisjointObjectPropertiesAxiom(properties);}
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(Collection<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {return DF.getOWLDisjointObjectPropertiesAxiom(l(properties), a);}
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(OWLAnnotation a, OWLObjectPropertyExpression... properties) {return DF.getOWLDisjointObjectPropertiesAxiom(l(properties), l(a));}
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {return DF.getOWLInverseObjectPropertiesAxiom(peA, peB);}
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(Collection<OWLAnnotation> a, OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {return DF.getOWLInverseObjectPropertiesAxiom(peA, peB, a);}
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(OWLAnnotation a, OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {return DF.getOWLInverseObjectPropertiesAxiom(peA, peB, l(a));}
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLObjectPropertyExpression property, OWLClassExpression domain) {return DF.getOWLObjectPropertyDomainAxiom(property, domain);}
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLClassExpression domain) {return DF.getOWLObjectPropertyDomainAxiom(property, domain, a);}
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLAnnotation a, OWLObjectPropertyExpression property, OWLClassExpression domain) {return DF.getOWLObjectPropertyDomainAxiom(property, domain, l(a));}
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLObjectPropertyExpression property, OWLClassExpression range) {return DF.getOWLObjectPropertyRangeAxiom(property, range);}
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLClassExpression range) {return DF.getOWLObjectPropertyRangeAxiom(property, range, a);}
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLAnnotation a, OWLObjectPropertyExpression property, OWLClassExpression range) {return DF.getOWLObjectPropertyRangeAxiom(property, range, l(a));}
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLFunctionalObjectPropertyAxiom(property);}
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLFunctionalObjectPropertyAxiom(property, a);}
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLFunctionalObjectPropertyAxiom(property, l(a));}
    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLInverseFunctionalObjectPropertyAxiom(property);}
    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLInverseFunctionalObjectPropertyAxiom(property, a);}
    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLInverseFunctionalObjectPropertyAxiom(property, l(a));}
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLReflexiveObjectPropertyAxiom(property);}
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLReflexiveObjectPropertyAxiom(property, a);}
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLReflexiveObjectPropertyAxiom(property, l(a));}
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLIrreflexiveObjectPropertyAxiom(property);}
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLIrreflexiveObjectPropertyAxiom(property, a);}
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLIrreflexiveObjectPropertyAxiom(property, l(a));}
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLSymmetricObjectPropertyAxiom(property);}
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLSymmetricObjectPropertyAxiom(property, a);}
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLSymmetricObjectPropertyAxiom(property, l(a));}
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLAsymmetricObjectPropertyAxiom(property);}
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLAsymmetricObjectPropertyAxiom(property, a);}
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLAsymmetricObjectPropertyAxiom(property, l(a));}
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(OWLObjectPropertyExpression property) {return DF.getOWLTransitiveObjectPropertyAxiom(property);}
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return DF.getOWLTransitiveObjectPropertyAxiom(property, a);}
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return DF.getOWLTransitiveObjectPropertyAxiom(property, l(a));}
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty);}
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(Collection<OWLAnnotation> a, OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, a);}
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLAnnotation a, OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {return DF.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, l(a));}
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(OWLDataPropertyExpression... properties) {return DF.getOWLEquivalentDataPropertiesAxiom(properties);}
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(Collection<OWLAnnotation> a, OWLDataPropertyExpression... properties) {return DF.getOWLEquivalentDataPropertiesAxiom(l(properties), a);}
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(OWLAnnotation a, OWLDataPropertyExpression... properties) {return DF.getOWLEquivalentDataPropertiesAxiom(l(properties), l(a));}
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(OWLDataPropertyExpression... properties) {return DF.getOWLDisjointDataPropertiesAxiom(properties);}
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(Collection<OWLAnnotation> a, OWLDataPropertyExpression... properties) {return DF.getOWLDisjointDataPropertiesAxiom(l(properties), a);}
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(OWLAnnotation a, OWLDataPropertyExpression... properties) {return DF.getOWLDisjointDataPropertiesAxiom(l(properties), l(a));}
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression property, OWLClassExpression domain) {return DF.getOWLDataPropertyDomainAxiom(property, domain);}
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLClassExpression domain) {return DF.getOWLDataPropertyDomainAxiom(property, domain, a);}
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLAnnotation a, OWLDataPropertyExpression property, OWLClassExpression domain) {return DF.getOWLDataPropertyDomainAxiom(property, domain, l(a));}
    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression property, OWLDataRange range) {return DF.getOWLDataPropertyRangeAxiom(property, range);}
    public static OWLDataPropertyRangeAxiom DataPropertyRange(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLDataRange range) {return DF.getOWLDataPropertyRangeAxiom(property, range, a);}
    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLAnnotation a, OWLDataPropertyExpression property, OWLDataRange range) {return DF.getOWLDataPropertyRangeAxiom(property, range, l(a));}
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(OWLDataPropertyExpression property) {return DF.getOWLFunctionalDataPropertyAxiom(property);}
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(Collection<OWLAnnotation> a, OWLDataPropertyExpression property) {return DF.getOWLFunctionalDataPropertyAxiom(property, a);}
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(OWLAnnotation a, OWLDataPropertyExpression property) {return DF.getOWLFunctionalDataPropertyAxiom(property, l(a));}
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLDatatype datatype, OWLDataRange dataRange) {return DF.getOWLDatatypeDefinitionAxiom(datatype, dataRange);}
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(Collection<OWLAnnotation> annotations, OWLDatatype datatype, OWLDataRange dataRange) {return DF.getOWLDatatypeDefinitionAxiom(datatype, dataRange, annotations);}
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLAnnotation a, OWLDatatype datatype, OWLDataRange dataRange) {return DF.getOWLDatatypeDefinitionAxiom(datatype, dataRange, l(a));}
    public static OWLHasKeyAxiom HasKey(OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {return DF.getOWLHasKeyAxiom(classExpression, propertyExpressions);}
    public static OWLHasKeyAxiom HasKey(Collection<OWLAnnotation> a, OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {return DF.getOWLHasKeyAxiom(classExpression, l(propertyExpressions), a);}
    public static OWLHasKeyAxiom HasKey(OWLAnnotation a, OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {return DF.getOWLHasKeyAxiom(classExpression, l(propertyExpressions), l(a));}
    public static OWLSameIndividualAxiom SameIndividual(OWLIndividual... individuals) {return DF.getOWLSameIndividualAxiom(individuals);}
    public static OWLSameIndividualAxiom SameIndividual(Collection<OWLAnnotation> a, OWLIndividual... individuals) {return DF.getOWLSameIndividualAxiom(l(individuals), a);}
    public static OWLSameIndividualAxiom SameIndividual(OWLAnnotation a, OWLIndividual... individuals) {return DF.getOWLSameIndividualAxiom(l(individuals), l(a));}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLIndividual... individuals) {return DF.getOWLDifferentIndividualsAxiom(individuals);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(Collection<OWLAnnotation> a, OWLIndividual... individuals) {return DF.getOWLDifferentIndividualsAxiom(l(individuals), a);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLAnnotation a, OWLIndividual... individuals) {return DF.getOWLDifferentIndividualsAxiom(l(individuals), l(a));}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(Collection<OWLAnnotation> a, Collection<? extends OWLIndividual> individuals) {return DF.getOWLDifferentIndividualsAxiom(individuals, a);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLAnnotation a, Collection<? extends OWLIndividual> individuals) {return DF.getOWLDifferentIndividualsAxiom(individuals, l(a));}
    public static OWLSameIndividualAxiom SameIndividual(Collection<? extends OWLIndividual> individuals) {return DF.getOWLSameIndividualAxiom(individuals);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(Collection<? extends OWLIndividual> individuals) {return DF.getOWLDifferentIndividualsAxiom(individuals);}
    public static OWLClassAssertionAxiom ClassAssertion(Collection<OWLAnnotation> a, OWLClassExpression ce, OWLIndividual ind) {return DF.getOWLClassAssertionAxiom(ce, ind, a);}
    public static OWLClassAssertionAxiom ClassAssertion(OWLAnnotation a, OWLClassExpression ce, OWLIndividual ind) {return DF.getOWLClassAssertionAxiom(ce, ind, l(a));}
    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind) {return DF.getOWLClassAssertionAxiom(ce, ind);}
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return DF.getOWLObjectPropertyAssertionAxiom(property, source, target);}
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return DF.getOWLObjectPropertyAssertionAxiom(property, source, target, a);}
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(OWLAnnotation a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return DF.getOWLObjectPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target);}
    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target, a);}
    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(OWLAnnotation a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return DF.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return DF.getOWLDataPropertyAssertionAxiom(property, source, target);}
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return DF.getOWLDataPropertyAssertionAxiom(property, source, target, a);}
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLAnnotation a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return DF.getOWLDataPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source, target);}
    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source, target, a);}
    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(OWLAnnotation a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return DF.getOWLNegativeDataPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value) {return DF.getOWLAnnotationAssertionAxiom(property, subj, value);}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(Collection<OWLAnnotation> set, OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value) {return DF.getOWLAnnotationAssertionAxiom(property, subj, value, set);}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotation a, OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value) {return DF.getOWLAnnotationAssertionAxiom(property, subj, value, l(a));}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value, OWLAnnotation... set) {return DF.getOWLAnnotationAssertionAxiom(property, subj, value,     new HashSet<>(l(set)));}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, OWLAnnotationValue value) {return DF.getOWLAnnotation(property, value);}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, String value) {return DF.getOWLAnnotation(property, DF.getOWLLiteral(value));}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, boolean value) {return DF.getOWLAnnotation(property, DF.getOWLLiteral(value));}
    public static OWLAnnotation Annotation(Collection<OWLAnnotation> anns, OWLAnnotationProperty property, OWLAnnotationValue value) {if (anns.isEmpty()) {return DF.getOWLAnnotation(property, value);} return DF.getOWLAnnotation(property, value, anns.stream());}
    public static OWLAnnotation Annotation(OWLAnnotation a, OWLAnnotationProperty property, OWLAnnotationValue value) {return DF.getOWLAnnotation(property, value, l(a));}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, OWLAnnotationValue value, OWLAnnotation... anns) {if (anns.length == 0) {return DF.getOWLAnnotation(property, value);} return DF.getOWLAnnotation(property, value, Stream.of(anns));}
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {return DF.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty);}
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(Collection<OWLAnnotation> a, OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {return DF.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty, a);}
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(OWLAnnotation a, OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {return DF.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty, l(a));}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty property, IRI iri) {return DF.getOWLAnnotationPropertyDomainAxiom(property, iri);}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(Collection<OWLAnnotation> annotations, OWLAnnotationProperty property, IRI iri) {return DF.getOWLAnnotationPropertyDomainAxiom(property, iri, annotations);}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotation a, OWLAnnotationProperty property, IRI iri) {return DF.getOWLAnnotationPropertyDomainAxiom(property, iri, l(a));}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty property, IRI iri) {return DF.getOWLAnnotationPropertyRangeAxiom(property, iri);}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(Collection<OWLAnnotation> annotations, OWLAnnotationProperty property, IRI iri) {return DF.getOWLAnnotationPropertyRangeAxiom(property, iri, annotations);}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotation a, OWLAnnotationProperty property, IRI iri) {return DF.getOWLAnnotationPropertyRangeAxiom(property, iri, l(a));}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty property, String iri) {return DF.getOWLAnnotationPropertyDomainAxiom(property, IRI.create(iri));}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty property, String iri) {return DF.getOWLAnnotationPropertyRangeAxiom(property, IRI.create(iri));}
    public static IRI IRI(String iri) {return IRI.create(iri);}
    public static OWLLiteral PlainLiteral(String in) {return DF.getOWLLiteral(in, "");}
    public static OWLDatatype PlainLiteral() {return DF.getRDFPlainLiteral();}
    public static OWLLiteral Literal(String in, @Nullable String lang) {return DF.getOWLLiteral(in, lang);}
    public static OWLLiteral Literal(String in, OWLDatatype type) {return DF.getOWLLiteral(in, type);}
    public static OWLLiteral Literal(String in, OWL2Datatype type) {return DF.getOWLLiteral(in, type);}
    public static OWLLiteral Literal(String in) {return DF.getOWLLiteral(in);}
    public static OWLLiteral Literal(boolean in) {return DF.getOWLLiteral(in);}
    public static OWLLiteral Literal(int in) {return DF.getOWLLiteral(in);}
    public static OWLLiteral Literal(double in) {return DF.getOWLLiteral(in);}
    public static OWLLiteral Literal(float in) {return DF.getOWLLiteral(in);}
    public static SWRLRule SWRLRule(Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {return DF.getSWRLRule(body, head);}
    public static SWRLRule SWRLRule(Collection<OWLAnnotation> annotations, Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {return DF.getSWRLRule(body, head, annotations);}
    public static SWRLRule SWRLRule(OWLAnnotation a, Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {return DF.getSWRLRule(body, head, l(a));}
    public static SWRLClassAtom SWRLClassAtom(OWLClassExpression pred, SWRLIArgument arg) {return DF.getSWRLClassAtom(pred, arg);}
    public static SWRLDataRangeAtom SWRLDataRangeAtom(OWLDataRange pres, SWRLDArgument arg) {return DF.getSWRLDataRangeAtom(pres, arg);}
    public static SWRLDataRangeAtom SWRLDataRangeAtom(OWL2Datatype pred, SWRLDArgument arg) {return DF.getSWRLDataRangeAtom(pred, arg);}
    public static SWRLObjectPropertyAtom SWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIArgument arg0, SWRLIArgument arg1) {return DF.getSWRLObjectPropertyAtom(property, arg0, arg1);}
    public static SWRLDataPropertyAtom SWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIArgument arg0, SWRLDArgument arg1) {return DF.getSWRLDataPropertyAtom(property, arg0, arg1);}
    public static SWRLBuiltInAtom SWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args) {return DF.getSWRLBuiltInAtom(builtInIRI, args);}
    public static SWRLBuiltInAtom SWRLBuiltInAtom(IRI builtInIRI, SWRLDArgument... args) {return DF.getSWRLBuiltInAtom(builtInIRI, l(args));}
    public static SWRLVariable SWRLVariable(IRI var) {return DF.getSWRLVariable(var);}
    public static SWRLVariable SWRLVariable(String iri) {return SWRLVariable(IRI.create(iri));}
    public static SWRLVariable SWRLVariable(String namespace, @Nullable String remainder) {return SWRLVariable(IRI.create(namespace, remainder));}
    public static SWRLVariable SWRLVariable(HasIRI var) {return SWRLVariable(var.getIRI());}
    public static SWRLIndividualArgument SWRLIndividualArgument(OWLIndividual individual) {return DF.getSWRLIndividualArgument(individual);}
    public static SWRLLiteralArgument SWRLLiteralArgument(OWLLiteral in) {return DF.getSWRLLiteralArgument(in);}
    public static SWRLSameIndividualAtom SWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1) {return DF.getSWRLSameIndividualAtom(arg0, arg1);}
    public static SWRLDifferentIndividualsAtom SWRLDifferentIndividualsAtom(SWRLIArgument arg0, SWRLIArgument arg1) {return DF.getSWRLDifferentIndividualsAtom(arg0, arg1);}
    public static OWLOntology Ontology(OWLOntologyManager man, OWLAxiom... axioms) {try {return man.createOntology(l(axioms));} catch (OWLOntologyCreationException ex) {throw new OWLRuntimeException(ex);}}
  //@formatter:on
}
