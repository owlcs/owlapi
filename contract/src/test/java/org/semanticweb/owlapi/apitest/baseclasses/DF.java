package org.semanticweb.owlapi.apitest.baseclasses;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

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
import org.semanticweb.owlapi.model.OWLOntologyID;
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
import org.semanticweb.owlapi.vocab.Obo2OWLConstants;
import org.semanticweb.owlapi.vocab.Obo2OWLConstants.Obo2OWLVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

public class DF {

    //@formatter:off
    protected static final OWLDataFactory df = OWLManager.getOWLDataFactory();
    public static final String ONTOLOGIES_COM = "http://www.ontologies.com/ontology#";
    public static final String NS           = "urn:test";
    public static final String URNTESTS_URI = NS+"#uri";
    public static final String uriBase      = "http://www.semanticweb.org/owlapi/test";
    public static final String OWLAPI_TEST  = uriBase + "#";
    public static final String OBO          = "http://purl.obolibrary.org/obo/";
    public static final String OBO_IN_OWL   = "http://www.geneontology.org/formats/oboInOwl#";
    public static final String OBO_UO       = OBO + "uo#";
    public static final String URN_TEST     = NS + "#";
    public static final String URN_TEST_    = NS + ":";
    public static final String START        = OWLThing().getIRI().getNamespace();
    public static final String TEST_0001    = "TEST:0001";
    public static final String COMMENT      = "Comment";
    public static final String DECLARATIONS = "http://www.semanticweb.org/ontologies/declarations#";
    public static class IRIS {
        public static final IRI TEST_IMPORT     = iri(OBO + "uberon/", "test_import.owl");
        public static final IRI UBERON          = iri(URN_TEST, "uberon");
        public static final IRI t2              = iri(URN_TEST, "t2");
        public static final IRI t3              = iri(URN_TEST, "t3");
        public static final IRI t1              = iri(URN_TEST, "t1");
        public static final IRI iriTest         = iri(OWLAPI_TEST, "test");
        public static final IRI iriTest1        = iri(OWLAPI_TEST, "test1");
        public static final IRI iriTest2        = iri(OWLAPI_TEST, "test2");
        public static final IRI iri             = iri(OWLAPI_TEST, "iri");
        public static final IRI OWL_TEST        = iri(START, "test");
        public static final IRI iriLiterals     = iri(URN_TEST, "literals");
        public static final IRI individualIRI   = iri("http://example.com/ontology/x,", "y");
        public static final IRI SHORTHAND       = iri(OBO_IN_OWL, "shorthand");
        public static final IRI ID              = iri(OBO_IN_OWL, "id");
        public static final IRI BFO50           = iri(OBO, "BFO_0000050");
        public static final IRI RO2111          = iri(OBO, "RO_0002111");
        public static final IRI BAR1            = iri(OBO, "BAR_0000001");
        public static final IRI BFO51           = iri(OBO, "BFO_0000051");
        public static final IRI ontologyIRITest = iri(OBO, "test.owl");
        public static final IRI southAfrica     = iri("http://dbpedia.org/resource/", "South_Africa");
        public static final IRI clsA            = iri(OWLAPI_TEST, "ClsA");
        public static final IRI prop            = iri(OWLAPI_TEST, "prop");
        public static final IRI TEST_OBO        = iri("http://purl.obolibrary.org/obo/tests/", "test.obo");
    }
    public static class DATATYPES {
        public static final OWLDatatype datatype    = Datatype(iri("http://www.ont.com/myont/", "mydatatype"));
        public static final OWLDatatype ssnDT       = Datatype(iri("file:/c/test.owlapi#", "SSN"));
        public static final OWLDatatype LANG_STRING = Datatype(OWL2Datatype.RDF_LANG_STRING.getIRI());
        public static final OWLDatatype MY_DATATYPE = Datatype(iri("urn:my#", "datatype"));
        public static final OWLDatatype DT2         = Datatype(URN_TEST_, "datatype2");
        public static final OWLDatatype DT1         = Datatype(URN_TEST_, "datatype1");
        public static final OWLDatatype FAKEDT      = Datatype(iri("urn:datatype#", "fakedatatype"));
        public static final OWLDatatype DT3         = Datatype(iri(START, "unknownfakedatatype"));
        public static final OWLDatatype OWLTEST_DT  = Datatype(IRIS.OWL_TEST);
        public static final OWLDatatype IRI_DT      = Datatype(IRIS.iri);
        public static final OWLDatatype FAKEDT2     = Datatype(iri("fakeundeclareddatatype"));
        public static final OWLDatatype DType       = Datatype(iri("datatype"));
        public static final OWLDatatype DTT         = Datatype(IRIS.iriTest);
        public static final OWLDatatype DT          = Datatype(iri("DT"));
        public static final OWLDatatype DTA         = Datatype(iri("DtA"));
        public static final OWLDatatype DTB         = Datatype(iri("DtB"));
        public static final OWLDatatype DTC         = Datatype(iri("DtC"));
        public static final OWLDatatype DATAB       = Datatype(iri("B"));
        public static final OWLDatatype dateTime    = Datatype(XSDVocabulary.DATE_TIME.getIRI());
    }
    public static class ANNPROPS {
        public static final OWLAnnotationProperty MY_COMMENT            = AnnotationProperty("http://ont.com#", "myComment");
        public static final OWLAnnotationProperty hasDefinition         = AnnotationProperty(iri("urn:obo:", "hasDefinition"));
        public static final OWLAnnotationProperty hasdbxref             = AnnotationProperty(iri("urn:obo:", "hasDbXref"));
        public static final OWLAnnotationProperty replaced              = AnnotationProperty(iri(URN_TEST_, "term_replaced_by"));
        public static final OWLAnnotationProperty infIRI                = AnnotationProperty(iri(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX, "is_inferred"));
        public static final OWLAnnotationProperty ap                    = AnnotationProperty(OWLAPI_TEST, "ann");
        public static final OWLAnnotationProperty OWLTEST_AP            = AnnotationProperty(IRIS.OWL_TEST);
        public static final OWLAnnotationProperty APP                   = AnnotationProperty(iri("fakedatatypeproperty"));
        public static final OWLAnnotationProperty broader               = AnnotationProperty(iri("http://www.w3.org/2004/02/skos/core#", "broader"));
        public static final OWLAnnotationProperty IRI_AP                = AnnotationProperty(IRIS.iri);
        public static final OWLAnnotationProperty pred                  = AnnotationProperty("http://www.example.org/", "pred");
        public static final OWLAnnotationProperty testDP                = AnnotationProperty(iri("urn:test:test#", "dp"));
        public static final OWLAnnotationProperty testDT                = AnnotationProperty(iri("urn:test:test#", "datatype"));
        public static final OWLAnnotationProperty g                     = AnnotationProperty(iri(URN_TEST, "g"));
        public static final OWLAnnotationProperty h                     = AnnotationProperty(iri(URN_TEST, "h"));
        public static final OWLAnnotationProperty date                  = AnnotationProperty(iri(OBO_IN_OWL, "date"));
        public static final OWLAnnotationProperty mpathSlim             = AnnotationProperty(iri(OBO_UO, "mpath_slim"));
        public static final OWLAnnotationProperty subsetProperty        = AnnotationProperty(iri(OBO_IN_OWL, "SubsetProperty"));
        public static final OWLAnnotationProperty attributeSlim         = AnnotationProperty(iri(OBO_UO, "attribute_slim"));
        public static final OWLAnnotationProperty hasOBONamespace       = AnnotationProperty(iri(OBO_IN_OWL, "hasOBONamespace"));
        public static final OWLAnnotationProperty autogeneratedby       = AnnotationProperty(iri(OBO_IN_OWL, "auto-generated-by"));
        public static final OWLAnnotationProperty hasDbXref             = AnnotationProperty(iri(OBO_IN_OWL, "hasDbXref"));
        public static final OWLAnnotationProperty defaultnamespace      = AnnotationProperty(iri(OBO_IN_OWL, "default-namespace"));
        public static final OWLAnnotationProperty hasOBOFormatVersion   = AnnotationProperty(iri(OBO_IN_OWL, "hasOBOFormatVersion"));
        public static final OWLAnnotationProperty iao0000115            = AnnotationProperty(iri(OBO, "IAO_0000115"));
        public static final OWLAnnotationProperty namespaceIdRule       = AnnotationProperty(iri(OBO_IN_OWL, "NamespaceIdRule"));
        public static final OWLAnnotationProperty createdBy             = AnnotationProperty(iri(OBO_IN_OWL, "created_by"));
        public static final OWLAnnotationProperty inSubset              = AnnotationProperty(iri(OBO_IN_OWL, "inSubset"));
        public static final OWLAnnotationProperty savedby               = AnnotationProperty(iri(OBO_IN_OWL, "saved-by"));
        public static final OWLAnnotationProperty id                    = AnnotationProperty(iri(OBO_IN_OWL, "id"));
        public static final OWLAnnotationProperty abnormalSlim          = AnnotationProperty(iri(OBO_UO, "abnormal_slim"));
        public static final OWLAnnotationProperty scalarSlim            = AnnotationProperty(iri(OBO_UO, "scalar_slim"));
        public static final OWLAnnotationProperty unitSlim              = AnnotationProperty(iri(OBO_UO, "unit_slim"));
        public static final OWLAnnotationProperty absentSlim            = AnnotationProperty(iri(OBO_UO, "absent_slim"));
        public static final OWLAnnotationProperty cellQuality           = AnnotationProperty(iri(OBO_UO, "cell_quality"));
        public static final OWLAnnotationProperty unitGroupSlim         = AnnotationProperty(iri(OBO_UO, "unit_group_slim"));
        public static final OWLAnnotationProperty valueSlim             = AnnotationProperty(iri(OBO_UO, "value_slim"));
        public static final OWLAnnotationProperty prefixSlim            = AnnotationProperty(iri(OBO_UO, "prefix_slim"));
        public static final OWLAnnotationProperty dispositionSlim       = AnnotationProperty(iri(OBO_UO, "disposition_slim"));
        public static final OWLAnnotationProperty relationalSlim        = AnnotationProperty(iri(OBO_UO, "relational_slim"));
        public static final OWLAnnotationProperty defProperty           = AnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0000115.getIRI());
        public static final OWLAnnotationProperty OBO_ID                = AnnotationProperty(IRIS.ID);
        public static final OWLAnnotationProperty apShortHand           = AnnotationProperty(IRIS.SHORTHAND);
        public static final OWLAnnotationProperty p_insubset            = AnnotationProperty(OBO_IN_OWL, "inSubset");
        public static final OWLAnnotationProperty APT                   = AnnotationProperty(iri("t"));
        public static final OWLAnnotationProperty AP                    = AnnotationProperty(iri("propA"));
        public static final OWLAnnotationProperty propP                 = AnnotationProperty(iri("propP"));
        public static final OWLAnnotationProperty propQ                 = AnnotationProperty(iri("propQ"));
        public static final OWLAnnotationProperty propR                 = AnnotationProperty(iri("propR"));
        public static final OWLAnnotationProperty areaTotal             = AnnotationProperty(iri("http://dbpedia.org/ontology/", "areaTotal"));
    }
    public static class CLASSES {
        public static final OWLClass dbxref         = Class(iri("urn:obo:", "DbXref"));
        public static final OWLClass definition     = Class(iri("urn:obo:", "Definition"));
        public static final OWLClass xClass         = Class("http://www.race.org#", "X");
        public static final OWLClass yClass         = Class("http://www.race.org#", "Y");
        public static final OWLClass CT1            = Class(IRIS.t1);
        public static final OWLClass CT2            = Class(IRIS.t2);
        public static final OWLClass CT3            = Class(IRIS.t3);
        public static final OWLClass MAN            = Class(iri("http://example.com/owl/families/", "Man"));
        public static final OWLClass FAMILY_PERSON  = Class(iri("http://example.com/owl/families/", "Person"));
        public static final OWLClass ce             = Class(URN_TEST, "c");
        public static final OWLClass FAKECLASS      = Class(DATATYPES.FAKEDT.getIRI());
        public static final OWLClass CL             = Class(iri("fakeclass"));
        public static final OWLClass profileIdentificationTestClass = Class(iri("http://www.w3.org/2007/OWL/testOntology#", "ProfileIdentificationTest"));
        public static final OWLClass familyX        = Class("http://example.com/owl/families/", "X");
        public static final OWLClass female         = Class("http://example.com/owl/families/", "Female");
        public static final OWLClass parent         = Class(iri("http://example.com/owl/families/", "Parent"));
        public static final OWLClass TEST01         = Class(iri("http://www.ebi.ac.uk/fgpt/ontologies/test/", "TEST_00001"));
        public static final OWLClass WOMAN          = Class(iri("http://www.example.org/#", "Woman"));
        public static final OWLClass taxTerm        = Class(iri("http://schema.wolterskluwer.de/", "TaxonomyTerm"));
        public static final OWLClass concept        = Class(iri("http://www.w3.org/2004/02/skos/core#", "Concept"));
        public static final OWLClass TEST_Q         = Class("urn:test#test.owl/", "q");
        public static final OWLClass TEST_T         = Class("urn:test#test.owl/", "t");
        public static final OWLClass IRI_CLASS      = Class(IRIS.iri);
        public static final OWLClass C_IRI          = Class(IRIS.iri);
        public static final OWLClass contactInfo    = Class(iri("urn:test.owl#", "ContactInformation"));
        public static final OWLClass cheesy         = Class(iri(URN_TEST, "CheeseyPizza"));
        public static final OWLClass cheese         = Class(iri(URN_TEST, "CheeseTopping"));
        public static final OWLClass pato0001708    = Class(iri(OBO, "PATO_0001708"));
        public static final OWLClass uo0            = Class(iri(OBO, "UO_0000000"));
        public static final OWLClass uo1            = Class(iri(OBO, "UO_0000001"));
        public static final OWLClass powerYoga      = Class(URN_TEST, "PowerYoga");
        public static final OWLClass yoga           = Class(URN_TEST, "Yoga");
        public static final OWLClass relaxation     = Class(URN_TEST, "Relaxation");
        public static final OWLClass activity       = Class(URN_TEST, "Activity");
        public static final OWLClass cls_union      = Class(OBO, "NCBITaxon_Union_0000000");
        public static final OWLClass OTHERFAKECLASS = Class(iri("otherfakeclass"));
        public static final OWLClass A              = Class(iri("A"));
        public static final OWLClass B              = Class(iri("B"));
        public static final OWLClass C              = Class(iri("C"));
        public static final OWLClass D              = Class(iri("D"));
        public static final OWLClass E              = Class(iri("E"));
        public static final OWLClass F              = Class(iri("F"));
        public static final OWLClass G              = Class(iri("G"));
        public static final OWLClass K              = Class(iri("K"));
        public static final OWLClass X              = Class(iri("X"));
        public static final OWLClass Y              = Class(iri("Y"));
        public static final OWLClass C1             = Class(iri(OBO, "TEST_1"));
        public static final OWLClass C2             = Class(iri(OBO, "TEST_2"));
        public static final OWLClass C3             = Class(iri(OBO, "TEST_3"));
        public static final OWLClass C4             = Class(iri(OBO, "TEST_4"));
        public static final OWLClass C5             = Class(iri(OBO, "TEST_5"));
        public static final OWLClass PERSON         = Class(iri("http://example.com/", "Person"));
    }
    public static class OBJPROPS {
        public static final OWLObjectProperty adjacent_to   = ObjectProperty(iri("urn:obo:", "adjacent_to"));
        public static final OWLObjectProperty OP123         = ObjectProperty(iri("http://example.com/place/123", ""));
        public static final OWLObjectProperty OPT1          = ObjectProperty(IRIS.t1);
        public static final OWLObjectProperty OPT2          = ObjectProperty(IRIS.t2);
        public static final OWLObjectProperty OPT3          = ObjectProperty(IRIS.t3);
        public static final OWLObjectProperty ANON_R        = ObjectProperty(iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        public static final OWLObjectProperty op            = ObjectProperty(URN_TEST, "op");
        public static final OWLObjectProperty OWLTEST_OP    = ObjectProperty(IRIS.OWL_TEST);
        public static final OWLObjectProperty OPP           = ObjectProperty(iri("fakedatatypeproperty"));
        public static final OWLObjectProperty superProperty = ObjectProperty(ONTOLOGIES_COM, "superProperty");
        public static final OWLObjectProperty subProperty   = ObjectProperty(ONTOLOGIES_COM, "subProperty");
        public static final OWLObjectProperty speciesProperty = ObjectProperty(iri("http://www.w3.org/2007/OWL/testOntology#", "species"));
        public static final OWLObjectProperty hasParent     = ObjectProperty(iri("http://example.com/owl/families/", "hasParent"));
        public static final OWLObjectProperty anonR         = ObjectProperty(iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
        public static final OWLObjectProperty hasChild      = ObjectProperty(iri("http://example.com/owl/families/", "hasChild"));
        public static final OWLObjectProperty isUnitOf      = ObjectProperty(iri(OBO_UO, "is_unit_of"));
        public static final OWLObjectProperty OP_IRI        = ObjectProperty(IRIS.iri);
        public static final OWLObjectProperty hasTopping    = ObjectProperty(iri(URN_TEST, "hasTopping"));
        public static final OWLObjectProperty op_BFO    = ObjectProperty(OBO, "BFO_0000050");
        public static final OWLObjectProperty father    = ObjectProperty(iri(URN_TEST_, "hasFather"));
        public static final OWLObjectProperty brother   = ObjectProperty(iri(URN_TEST_, "hasBrother"));
        public static final OWLObjectProperty child     = ObjectProperty(iri(URN_TEST_, "hasChild"));
        public static final OWLObjectProperty uncle     = ObjectProperty(iri(URN_TEST_, "hasUncle"));
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
        public static final OWLObjectProperty PROP      = ObjectProperty(iri("prop"));
        public static final OWLObjectProperty op1       = ObjectProperty(iri("op1"));
        public static final OWLObjectProperty op2       = ObjectProperty(iri("op2"));
        public static final OWLObjectProperty predicate = ObjectProperty(iri("http://Example.com#", "located_at"));
        public static final OWLObjectProperty OP        = ObjectProperty(iri("http://example.com/", "objectProperty"));
        public static final OWLObjectProperty p_RO      = ObjectProperty(iri(OBO, "RO_0002104"));
    }
    public static class DATAPROPS {
        public static final OWLDataProperty hasuri      = DataProperty(iri("urn:obo:", "hasURI"));
        public static final OWLDataProperty DPT3        = DataProperty(IRIS.t3);
        public static final OWLDataProperty DPT2        = DataProperty(IRIS.t2);
        public static final OWLDataProperty DPT1        = DataProperty(IRIS.t1);
        public static final OWLDataProperty TEST_DP     = DataProperty(iri("http://protege.org/Test.owl#", "p"));
        public static final OWLDataProperty dp          = DataProperty(URN_TEST, "dp");
        public static final OWLDataProperty TEST_DTP    = DataProperty(IRIS.iriTest);
        public static final OWLDataProperty DT_FAIL     = DataProperty(iri(START, "fail"));
        public static final OWLDataProperty OWLTEST_DP  = DataProperty(IRIS.OWL_TEST);
        public static final OWLDataProperty DATAP       = DataProperty(iri("fakedatatypeproperty"));
        public static final OWLDataProperty rdfXML      = DataProperty(iri("http://www.w3.org/2007/OWL/testOntology#", "rdfXmlPremiseOntology"));
        public static final OWLDataProperty IRI_DP      = DataProperty(IRIS.iri);
        public static final OWLDataProperty DP_IRI      = DataProperty(IRIS.iri);
        public static final OWLDataProperty city        = DataProperty(iri("urn:test.owl#", "city"));
        public static final OWLDataProperty s           = DataProperty(iri(URN_TEST, "s"));
        public static final OWLDataProperty dt          = DataProperty(iri(URN_TEST, "t"));
        public static final OWLDataProperty v           = DataProperty(iri(URN_TEST, "v"));
        public static final OWLDataProperty hasAge      = DataProperty(iri("http://example.org/", "hasAge"));
        public static final OWLDataProperty OTHER_DP    = DataProperty(iri("other"));
        public static final OWLDataProperty DP          = DataProperty(iri("p"));
        public static final OWLDataProperty DQ          = DataProperty(iri("q"));
        public static final OWLDataProperty DR          = DataProperty(iri("r"));
        public static final OWLDataProperty DS          = DataProperty(iri("s"));
        public static final OWLDataProperty DPT         = DataProperty(iri(URN_TEST, "t"));
        public static final OWLDataProperty DPROP       = DataProperty(iri("prop"));
        public static final OWLDataProperty DPP         = DataProperty(iri("dp"));
        public static final OWLDataProperty dp1         = DataProperty(iri("dp1"));
        public static final OWLDataProperty dp2         = DataProperty(iri("dp2"));
        public static final OWLDataProperty dp3         = DataProperty(iri("dp3"));
        public static final OWLDataProperty PD          = DataProperty(iri("propD"));
        public static final OWLDataProperty DAP         = DataProperty(iri("http://example.com/", "dataProperty"));
    }
    public static class INDIVIDUALS {
        public static final OWLNamedIndividual IND_N2   = NamedIndividual(iri("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395B", ""));
        public static final OWLNamedIndividual IND_N1   = NamedIndividual(iri("http://example.com/place/112013e2-df48-4a34-8a9d-99ef572a395A", ""));
        public static final OWLNamedIndividual IT3      = NamedIndividual(IRIS.t3);
        public static final OWLNamedIndividual IT2      = NamedIndividual(IRIS.t2);
        public static final OWLNamedIndividual IT1      = NamedIndividual(IRIS.t1);
        public static final OWLNamedIndividual IND_OTHER = NamedIndividual(iri("urn:other:", "test"));
        public static final OWLNamedIndividual el       = NamedIndividual(iri("http://www.w3.org/2007/OWL/testOntology#", "EL"));
        public static final OWLNamedIndividual ql       = NamedIndividual(iri("http://www.w3.org/2007/OWL/testOntology#", "QL"));
        public static final OWLNamedIndividual rl       = NamedIndividual(iri("http://www.w3.org/2007/OWL/testOntology#", "RL"));
        public static final OWLNamedIndividual full     = NamedIndividual(iri("http://www.w3.org/2007/OWL/testOntology#", "FULL"));
        public static final OWLNamedIndividual dl       = NamedIndividual(iri("http://www.w3.org/2007/OWL/testOntology#", "DL"));
        public static final OWLNamedIndividual MEG      = NamedIndividual(iri("http://example.com/owl/families/", "Meg"));
        public static final OWLNamedIndividual MARY     = NamedIndividual(iri("http://example.com/owl/families/", "Mary"));
        public static final OWLNamedIndividual BILL     = NamedIndividual(iri("http://example.com/owl/families/", "Bill"));
        public static final OWLNamedIndividual individual = NamedIndividual(IRIS.individualIRI);
        public static final OWLNamedIndividual PRACTICE_IND = NamedIndividual(iri("http://taxonomy.wolterskluwer.de/practicearea/", "10112"));
        public static final OWLNamedIndividual IRI_IND  = NamedIndividual(IRIS.iri);
        public static final OWLNamedIndividual IND_IRI  = NamedIndividual(IRIS.iri);
        public static final OWLNamedIndividual IND_TEST = NamedIndividual(IRIS.iriTest);
        public static final OWLNamedIndividual x        = NamedIndividual(iri(URN_TEST, "x"));
        public static final OWLNamedIndividual y        = NamedIndividual(iri(URN_TEST, "y"));
        public static final OWLNamedIndividual indz     = NamedIndividual(iri(URN_TEST, "z"));
        public static final OWLNamedIndividual I0       = NamedIndividual(iri("ind"));
        public static final OWLNamedIndividual I1       = NamedIndividual(iri("i1"));
        public static final OWLNamedIndividual I2       = NamedIndividual(iri("i2"));
        public static final OWLNamedIndividual I3       = NamedIndividual(iri(START, "i"));
        public static final OWLNamedIndividual II       = NamedIndividual(iri("I"));
        public static final OWLNamedIndividual I4       = NamedIndividual(IRIS.iriTest);
        public static final OWLNamedIndividual I        = NamedIndividual(iri("i"));
        public static final OWLNamedIndividual J        = NamedIndividual(iri("j"));
        public static final OWLNamedIndividual k        = NamedIndividual(iri("k"));
        public static final OWLNamedIndividual l        = NamedIndividual(iri("l"));
        public static final OWLNamedIndividual indA     = NamedIndividual(iri("a"));
        public static final OWLNamedIndividual indB     = NamedIndividual(iri("b"));
        public static final OWLNamedIndividual indC     = NamedIndividual(iri("c"));
        public static final OWLNamedIndividual i        = NamedIndividual(iri("I"));
        public static final OWLNamedIndividual subject  = NamedIndividual(iri("http://Example.com#", "myBuilding"));
        public static final OWLNamedIndividual object   = NamedIndividual(iri("http://Example.com#", "myLocation"));
        public static final OWLNamedIndividual peter    = NamedIndividual(iri("http://www.another.com/ont#", "peter"));
    }
    public static class LITERALS {
        public static final OWLLiteral lit          = Literal(false);
        public static final OWLLiteral plainlit     = Literal("string", "en");
        public static final OWLLiteral lit1         = Literal(3.5D);
        public static final OWLLiteral LITVALUE3    = Literal("value3");
        public static final OWLLiteral LITVALUE     = Literal("value");
        public static final OWLLiteral LITVALUE2    = Literal("value2");
        public static final OWLLiteral LITVALUE1    = Literal("value1");
        public static final OWLLiteral literal      = Literal("Wikipedia:Wikipedia");
        public static final OWLLiteral LIT_TRUE     = Literal(true);
        public static final OWLLiteral LIT_FALSE    = Literal(false);
        public static final OWLLiteral LIT_ONE      = Literal(1);
        public static final OWLLiteral LIT_TWO      = Literal(2);
        public static final OWLLiteral LIT_THREE    = Literal(3);
        public static final OWLLiteral LIT_FOUR     = Literal(4);
        public static final OWLLiteral val          = Literal("Test", "");
        public static final OWLLiteral LIT_33 = Literal(33);
        public static final OWLLiteral oneMillionth = Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE);
    }
    public static class SWRL {
        public static final SWRLVariable vy   = SWRLVariable(URN_TEST, "Y");
        public static final SWRLVariable vx   = SWRLVariable(URN_TEST, "X");
        public static final SWRLVariable var1 = SWRLVariable(URN_TEST, "var1");
        public static final SWRLVariable var2 = SWRLVariable(URN_TEST, "var2");
        public static final SWRLVariable var3 = SWRLVariable(URN_TEST, "var3");
        public static final SWRLVariable var4 = SWRLVariable(URN_TEST, "var4");
        public static final SWRLVariable var5 = SWRLVariable(URN_TEST, "var5");
        public static final SWRLVariable var6 = SWRLVariable(URN_TEST, "var6");
    }

    public static final OWLAnnotation LABEL_DATATYPE_DEFINITION = RDFSLabel("datatype definition");
    public static final List<OWLAnnotation> as                  = l(Annotation(ANNPROPS.ap, "test"));
    public static final OWLObjectComplementOf notC              = ObjectComplementOf(CLASSES.C);
    public static final OWLObjectComplementOf notB              = ObjectComplementOf(CLASSES.B);
    public static final OWLObjectComplementOf notA              = ObjectComplementOf(CLASSES.A);

    protected DF() {}
    @SafeVarargs
    public static <T> List<T> l(T... vals) {if (vals.length==0) {return Collections.emptyList();} return Stream.of(vals).distinct().collect(Collectors.toList());}
    public static <T> List<T> l(T value) {return Collections.singletonList(value);}
    public static IRI iri(String name) {return iri(OWLAPI_TEST, name);}
    public static IRI iri(File file) {return df.getIRI(file);}
    public static IRI iri(URI file) {return df.getIRI(file);}
    public static IRI iri(URL file) {return df.getIRI(file);}
    public static IRI iri(String p, String fragment) {return df.getIRI(p, fragment);}
    public static OWLImportsDeclaration ImportsDeclaration(IRI in) {return df.getOWLImportsDeclaration(in);}
    // Entities
    public static OWLClass Class(IRI iri) {return df.getOWLClass(iri);}
    public static OWLClass Class(String iri) {return df.getOWLClass(iri);}
    public static OWLClass Class(String ns, String in) {return df.getOWLClass(ns, in);}
    public static OWLClass createClass() {return Class(df.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLObjectProperty createObjectProperty() {return ObjectProperty(df.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLDataProperty createDataProperty() {return DataProperty(df.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLNamedIndividual createIndividual() {return NamedIndividual(df.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLAnnotationProperty createAnnotationProperty() {return AnnotationProperty(df.getNextDocumentIRI(URNTESTS_URI));}
    public static OWLLiteral createOWLLiteral() {return Literal("Test" + System.currentTimeMillis(), Datatype(df.getNextDocumentIRI(URNTESTS_URI)));}
    public static OWLClass Class(String abbreviatedIRI, PrefixManager pm) {return df.getOWLClass(abbreviatedIRI, pm);}
    public static OWLAnnotationProperty RDFSComment() {return df.getRDFSComment();}
    public static OWLAnnotation RDFSComment(String value) {return df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral(value));}
    public static OWLAnnotation RDFSComment(OWLLiteral value) {return df.getOWLAnnotation(df.getRDFSComment(), value);}
    public static OWLAnnotationProperty Deprecated() {return df.getOWLDeprecated();}
    public static OWLAnnotationProperty RDFSLabel() {return df.getRDFSLabel();}
    public static OWLAnnotationProperty VersionInfo() {return df.getOWLVersionInfo();}
    public static OWLDatatype String() {return df.getStringOWLDatatype();}
    public static OWLDatatype RDFSLiteral() {return df.getOWLDatatype(OWL2Datatype.RDFS_LITERAL.getIRI());}
    public static OWLDatatype RDFPlainLiteral() {return df.getRDFPlainLiteral();}
    public static OWLAnnotationProperty BackwardCompatibleWith() {return df.getOWLBackwardCompatibleWith();}
    public static OWLAnnotationAssertionAxiom DeprecatedOWLAnnotationAssertion(IRI in) {return df.getDeprecatedOWLAnnotationAssertionAxiom(in);}
    public static OWLDatatypeRestriction DatatypeMinInclusiveRestriction(double in) {return df.getOWLDatatypeMinInclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMaxInclusiveRestriction(double in) {return df.getOWLDatatypeMaxInclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMinMaxInclusiveRestriction(double min, double max) {return df.getOWLDatatypeMinMaxInclusiveRestriction(min, max);}
    public static OWLDatatypeRestriction DatatypeMinExclusiveRestriction(int in) {return df.getOWLDatatypeMinExclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMaxExclusiveRestriction(int in) {return df.getOWLDatatypeMaxExclusiveRestriction(in);}
    public static OWLDatatypeRestriction DatatypeMaxExclusiveRestriction(double in) {return df.getOWLDatatypeMaxExclusiveRestriction(in);}
    public static OWLAnnotationProperty IncompatibleWith() {return df.getOWLIncompatibleWith();}
    public static OWLAnnotationProperty RDFSIsDefinedBy() {return df.getRDFSIsDefinedBy();}
    public static OWLAnnotationProperty RDFSSeeAlso() {return df.getRDFSSeeAlso();}
    public static OWLDataProperty BottomDataProperty() {return df.getOWLBottomDataProperty();}
    public static OWLAnnotation RDFSLabel(String value) {return df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(value));}
    public static OWLAnnotation RDFSLabel(Stream<OWLAnnotation> annotations, String value) {return df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(value), annotations);}
    public static OWLAnnotation RDFSLabel(String value, OWLAnnotation... annotations) {return RDFSLabel(Stream.of(annotations), value);}
    public static OWLAnnotation RDFSLabel(OWLAnnotationValue value, OWLAnnotation... annotations) {return RDFSLabel(Stream.of(annotations), value);}
    public static OWLAnnotation RDFSLabel(OWLAnnotationValue value) {return df.getOWLAnnotation(df.getRDFSLabel(), value);}
    public static OWLAnnotation RDFSLabel(Stream<OWLAnnotation> annotations, OWLAnnotationValue value) {return df.getOWLAnnotation(df.getRDFSLabel(), value, annotations);}
    public static OWLDataProperty TopDataProperty() {return df.getOWLTopDataProperty();}
    public static OWLObjectProperty TopObjectProperty() {return df.getOWLTopObjectProperty();}
    public static OWLObjectProperty BottomObjectProperty() {return df.getOWLBottomObjectProperty();}
    public static OWLDatatype TopDatatype() {return df.getTopDatatype();}
    public static OWLClass OWLThing() {return df.getOWLThing();}
    public static OWLDatatype Integer() {return df.getIntegerOWLDatatype();}
    public static OWLDatatype Double() {return df.getDoubleOWLDatatype();}
    public static OWLDatatype Long() {return df.getOWLDatatype(OWL2Datatype.XSD_LONG.getIRI());}
    public static OWLDatatype Float() {return df.getFloatOWLDatatype();}
    public static OWLDatatype Boolean() {return df.getBooleanOWLDatatype();}
    public static OWLClass OWLNothing() {return df.getOWLNothing();}
    public static OWLObjectProperty ObjectProperty(IRI iri) {return df.getOWLObjectProperty(iri);}
    public static OWLObjectProperty ObjectProperty(String iri) {return df.getOWLObjectProperty(iri);}
    public static OWLObjectProperty ObjectProperty(String iri, String fragment) {return df.getOWLObjectProperty(iri, fragment);}
    public static OWLObjectProperty ObjectProperty(String abbreviatedIRI, PrefixManager pm) {return df.getOWLObjectProperty(abbreviatedIRI, pm);}
    public static OWLObjectInverseOf ObjectInverseOf(OWLObjectProperty pe) {return df.getOWLObjectInverseOf(pe);}
    public static OWLDataProperty DataProperty(IRI iri) {return df.getOWLDataProperty(iri);}
    public static OWLDataProperty DataProperty(String iri) {return df.getOWLDataProperty(iri);}
    public static OWLDataProperty DataProperty(String iri, String fragment) {return df.getOWLDataProperty(iri, fragment);}
    public static OWLDataProperty DataProperty(String abbreviatedIRI, PrefixManager pm) {return df.getOWLDataProperty(abbreviatedIRI, pm);}
    public static OWLAnnotationProperty AnnotationProperty(IRI iri) {return df.getOWLAnnotationProperty(iri);}
    public static OWLAnnotationProperty AnnotationProperty(String iri) {return df.getOWLAnnotationProperty(iri);}
    public static OWLAnnotationProperty AnnotationProperty(String iri, String fragment) {return df.getOWLAnnotationProperty(iri, fragment);}
    public static OWLAnnotationProperty AnnotationProperty(String abbreviatedIRI, PrefixManager pm) {return df.getOWLAnnotationProperty(abbreviatedIRI, pm);}
    public static OWLNamedIndividual NamedIndividual(IRI iri) {return df.getOWLNamedIndividual(iri);}
    public static OWLNamedIndividual NamedIndividual(String iri) {return df.getOWLNamedIndividual(iri);}
    public static OWLNamedIndividual NamedIndividual(String iri, String fragment) {return df.getOWLNamedIndividual(iri,fragment);}
    public static OWLAnonymousIndividual AnonymousIndividual() {return df.getOWLAnonymousIndividual();}
    public static OWLAnonymousIndividual AnonymousIndividual(String in) {return df.getOWLAnonymousIndividual(in);}
    public static OWLNamedIndividual NamedIndividual(String abbreviatedIRI, PrefixManager pm) {return df.getOWLNamedIndividual(abbreviatedIRI, pm);}
    public static OWLDatatype Datatype(IRI iri) {return df.getOWLDatatype(iri);}
    public static OWLDatatype Datatype(String iri) {return df.getOWLDatatype(iri);}
    public static OWLDatatype Datatype(String iri, String fragment) {return df.getOWLDatatype(iri,fragment);}
    public static OWLDeclarationAxiom Declaration(OWLEntity entity) {return df.getOWLDeclarationAxiom(entity);}
    public static OWLDeclarationAxiom Declaration(Collection<OWLAnnotation> a, OWLEntity entity) {return df.getOWLDeclarationAxiom(entity, a);}
    public static OWLDeclarationAxiom Declaration(OWLEntity entity, OWLAnnotation... a) {return Declaration(l(a), entity);}
    public static OWLDeclarationAxiom Declaration(OWLAnnotation a, OWLEntity entity) {return Declaration(l(a), entity);}
    // Class Expressions
    public static OWLObjectIntersectionOf ObjectIntersectionOf(OWLClassExpression... classExpressions) {return df.getOWLObjectIntersectionOf(classExpressions);}
    public static OWLObjectUnionOf ObjectUnionOf(OWLClassExpression... classExpressions) {return df.getOWLObjectUnionOf(classExpressions);}
    public static OWLObjectComplementOf ObjectComplementOf(OWLClassExpression classExpression) {return df.getOWLObjectComplementOf(classExpression);}
    public static OWLObjectSomeValuesFrom ObjectSomeValuesFrom(OWLObjectPropertyExpression pe, OWLClassExpression ce) {return df.getOWLObjectSomeValuesFrom(pe, ce);}
    public static OWLObjectAllValuesFrom ObjectAllValuesFrom(OWLObjectPropertyExpression pe, OWLClassExpression ce) {return df.getOWLObjectAllValuesFrom(pe, ce);}
    public static OWLObjectHasValue ObjectHasValue(OWLObjectPropertyExpression pe, OWLIndividual individual) {return df.getOWLObjectHasValue(pe, individual);}
    public static OWLObjectMinCardinality ObjectMinCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {return df.getOWLObjectMinCardinality(cardinality, pe, ce);}
    public static OWLObjectMaxCardinality ObjectMaxCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {return df.getOWLObjectMaxCardinality(cardinality, pe, ce);}
    public static OWLObjectExactCardinality ObjectExactCardinality(int cardinality, OWLObjectPropertyExpression pe, OWLClassExpression ce) {return df.getOWLObjectExactCardinality(cardinality, pe, ce);}
    public static OWLObjectHasSelf ObjectHasSelf(OWLObjectPropertyExpression pe) {return df.getOWLObjectHasSelf(pe);}
    public static OWLObjectOneOf ObjectOneOf(OWLIndividual... individuals) {return df.getOWLObjectOneOf(individuals);}
    public static OWLDataSomeValuesFrom DataSomeValuesFrom(OWLDataPropertyExpression pe, OWLDataRange dr) {return df.getOWLDataSomeValuesFrom(pe, dr);}
    public static OWLDataAllValuesFrom DataAllValuesFrom(OWLDataPropertyExpression pe, OWLDataRange dr) {return df.getOWLDataAllValuesFrom(pe, dr);}
    public static OWLDataHasValue DataHasValue(OWLDataPropertyExpression pe, OWLLiteral in) {return df.getOWLDataHasValue(pe, in);}
    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {return df.getOWLDataMinCardinality(cardinality, pe, dr);}
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {return df.getOWLDataMaxCardinality(cardinality, pe, dr);}
    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe, OWLDataRange dr) {return df.getOWLDataExactCardinality(cardinality, pe, dr);}
    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe, OWL2Datatype dr) {return df.getOWLDataMinCardinality(cardinality, pe, dr);}
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe, OWL2Datatype dr) {return df.getOWLDataMaxCardinality(cardinality, pe, dr);}
    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe, OWL2Datatype dr) {return df.getOWLDataExactCardinality(cardinality, pe, dr);}
    public static OWLDataMinCardinality DataMinCardinality(int cardinality, OWLDataPropertyExpression pe) {return df.getOWLDataMinCardinality(cardinality, pe);}
    public static OWLDataMaxCardinality DataMaxCardinality(int cardinality, OWLDataPropertyExpression pe) {return df.getOWLDataMaxCardinality(cardinality, pe);}
    public static OWLDataExactCardinality DataExactCardinality(int cardinality, OWLDataPropertyExpression pe) {return df.getOWLDataExactCardinality(cardinality, pe);}
    // Data Ranges other than datatype
    public static OWLDataIntersectionOf DataIntersectionOf(OWLDataRange... dataRanges) {return df.getOWLDataIntersectionOf(dataRanges);}
    public static OWLDataUnionOf DataUnionOf(OWLDataRange... dataRanges) {return df.getOWLDataUnionOf(dataRanges);}
    public static OWLDataComplementOf DataComplementOf(OWLDataRange dataRange) {return df.getOWLDataComplementOf(dataRange);}
    public static OWLDataOneOf DataOneOf(OWLLiteral... literals) {return df.getOWLDataOneOf(literals);}
    public static OWLDatatypeRestriction DatatypeMinMaxExclusiveRestriction(int minExclusive, int maxExclusive) {return DatatypeRestriction(df.getIntegerOWLDatatype(),     df.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, df.getOWLLiteral(minExclusive)),     df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));}
    public static OWLDatatypeRestriction DatatypeMinMaxExclusiveRestriction(double minExclusive, double maxExclusive) {return DatatypeRestriction(df.getDoubleOWLDatatype(),     df.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, df.getOWLLiteral(minExclusive)),     df.getOWLFacetRestriction(OWLFacet.MAX_EXCLUSIVE, maxExclusive));}
    public static OWLDatatypeRestriction DatatypeRestriction(OWLDatatype datatype, OWLFacetRestriction... facetRestrictions) {return df.getOWLDatatypeRestriction(datatype, facetRestrictions);}
    public static OWLFacetRestriction FacetRestriction(OWLFacet facet, OWLLiteral facetValue) {return df.getOWLFacetRestriction(facet, facetValue);}
    // Axioms
    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass, OWLClassExpression superClass) {return df.getOWLSubClassOfAxiom(subClass, superClass);}
    public static OWLSubClassOfAxiom SubClassOf(Collection<OWLAnnotation> a, OWLClassExpression subClass, OWLClassExpression superClass) {return df.getOWLSubClassOfAxiom(subClass, superClass, a);}
    public static OWLSubClassOfAxiom SubClassOf(OWLAnnotation a, OWLClassExpression subClass, OWLClassExpression superClass) {return df.getOWLSubClassOfAxiom(subClass, superClass, l(a));}
    public static OWLSubClassOfAxiom SubClassOf(OWLClassExpression subClass, OWLClassExpression superClass,OWLAnnotation... a) {return df.getOWLSubClassOfAxiom(subClass, superClass, l(a));}
    public static OWLEquivalentClassesAxiom EquivalentClasses(OWLClassExpression... classExpressions) {return df.getOWLEquivalentClassesAxiom(classExpressions);}
    public static OWLEquivalentClassesAxiom EquivalentClasses(Collection<OWLAnnotation> a, OWLClassExpression... classExpressions) {return df.getOWLEquivalentClassesAxiom(l(classExpressions), a);}
    public static OWLEquivalentClassesAxiom EquivalentClasses(OWLAnnotation a, OWLClassExpression... classExpressions) {return df.getOWLEquivalentClassesAxiom(l(classExpressions), l(a));}
    public static OWLDisjointClassesAxiom DisjointClasses(OWLClassExpression... classExpressions) {return df.getOWLDisjointClassesAxiom(classExpressions);}
    public static OWLDisjointClassesAxiom DisjointClasses(Collection<? extends OWLClassExpression> classExpressions) {return df.getOWLDisjointClassesAxiom(classExpressions);}
    public static OWLDisjointClassesAxiom DisjointClasses(Collection<OWLAnnotation> a, Collection<OWLClassExpression> classExpressions) {return df.getOWLDisjointClassesAxiom(classExpressions, a);}
    public static OWLDisjointClassesAxiom DisjointClasses(OWLAnnotation a, Collection<OWLClassExpression> classExpressions) {return df.getOWLDisjointClassesAxiom(classExpressions, l(a));}
    public static OWLDisjointClassesAxiom DisjointClasses(OWLAnnotation a, OWLClassExpression... classExpressions) {return df.getOWLDisjointClassesAxiom(l(classExpressions), l(a));}
    public static OWLDisjointUnionAxiom DisjointUnion(OWLClass cls, OWLClassExpression... classExpressions) {return df.getOWLDisjointUnionAxiom(cls, l(classExpressions));}
    public static OWLDisjointUnionAxiom DisjointUnion(Collection<OWLAnnotation> a, OWLClass cls, OWLClassExpression... classExpressions) {return df.getOWLDisjointUnionAxiom(cls, l(classExpressions), a);}
    public static OWLDisjointUnionAxiom DisjointUnion(OWLAnnotation a, OWLClass cls, OWLClassExpression... classExpressions) {return df.getOWLDisjointUnionAxiom(cls, l(classExpressions), l(a));}
    public static OWLDisjointClassesAxiom DisjointClasses(Collection<OWLAnnotation> a, OWLClassExpression... classExpressions) {return df.getOWLDisjointClassesAxiom(l(classExpressions), a);}
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {return df.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);}
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {return df.getOWLSubPropertyChainOfAxiom(chain, superProperty);}
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(Collection<OWLAnnotation> a, List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {return df.getOWLSubPropertyChainOfAxiom(chain, superProperty, a);}
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(Collection<OWLAnnotation> a, OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {return df.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, a);}
    public static OWLSubPropertyChainOfAxiom SubPropertyChainOf(OWLAnnotation a, List<? extends OWLObjectPropertyExpression> chain, OWLObjectPropertyExpression superProperty) {return df.getOWLSubPropertyChainOfAxiom(chain, superProperty, l(a));}
    public static OWLSubObjectPropertyOfAxiom SubObjectPropertyOf(OWLAnnotation a, OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty) {return df.getOWLSubObjectPropertyOfAxiom(subProperty, superProperty, l(a));}
    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(OWLObjectPropertyExpression... properties) {return df.getOWLEquivalentObjectPropertiesAxiom(properties);}
    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(Collection<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {return df.getOWLEquivalentObjectPropertiesAxiom(l(properties), a);}
    public static OWLEquivalentObjectPropertiesAxiom EquivalentObjectProperties(OWLAnnotation a, OWLObjectPropertyExpression... properties) {return df.getOWLEquivalentObjectPropertiesAxiom(l(properties), l(a));}
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(OWLObjectPropertyExpression... properties) {return df.getOWLDisjointObjectPropertiesAxiom(properties);}
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(Collection<OWLAnnotation> a, OWLObjectPropertyExpression... properties) {return df.getOWLDisjointObjectPropertiesAxiom(l(properties), a);}
    public static OWLDisjointObjectPropertiesAxiom DisjointObjectProperties(OWLAnnotation a, OWLObjectPropertyExpression... properties) {return df.getOWLDisjointObjectPropertiesAxiom(l(properties), l(a));}
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {return df.getOWLInverseObjectPropertiesAxiom(peA, peB);}
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(Collection<OWLAnnotation> a, OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {return df.getOWLInverseObjectPropertiesAxiom(peA, peB, a);}
    public static OWLInverseObjectPropertiesAxiom InverseObjectProperties(OWLAnnotation a, OWLObjectPropertyExpression peA, OWLObjectPropertyExpression peB) {return df.getOWLInverseObjectPropertiesAxiom(peA, peB, l(a));}
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLObjectPropertyExpression property, OWLClassExpression domain) {return df.getOWLObjectPropertyDomainAxiom(property, domain);}
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLClassExpression domain) {return df.getOWLObjectPropertyDomainAxiom(property, domain, a);}
    public static OWLObjectPropertyDomainAxiom ObjectPropertyDomain(OWLAnnotation a, OWLObjectPropertyExpression property, OWLClassExpression domain) {return df.getOWLObjectPropertyDomainAxiom(property, domain, l(a));}
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLObjectPropertyExpression property, OWLClassExpression range) {return df.getOWLObjectPropertyRangeAxiom(property, range);}
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLClassExpression range) {return df.getOWLObjectPropertyRangeAxiom(property, range, a);}
    public static OWLObjectPropertyRangeAxiom ObjectPropertyRange(OWLAnnotation a, OWLObjectPropertyExpression property, OWLClassExpression range) {return df.getOWLObjectPropertyRangeAxiom(property, range, l(a));}
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLFunctionalObjectPropertyAxiom(property);}
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLFunctionalObjectPropertyAxiom(property, a);}
    public static OWLFunctionalObjectPropertyAxiom FunctionalObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLFunctionalObjectPropertyAxiom(property, l(a));}
    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLInverseFunctionalObjectPropertyAxiom(property);}
    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLInverseFunctionalObjectPropertyAxiom(property, a);}
    public static OWLInverseFunctionalObjectPropertyAxiom InverseFunctionalObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLInverseFunctionalObjectPropertyAxiom(property, l(a));}
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLReflexiveObjectPropertyAxiom(property);}
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLReflexiveObjectPropertyAxiom(property, a);}
    public static OWLReflexiveObjectPropertyAxiom ReflexiveObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLReflexiveObjectPropertyAxiom(property, l(a));}
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLIrreflexiveObjectPropertyAxiom(property);}
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLIrreflexiveObjectPropertyAxiom(property, a);}
    public static OWLIrreflexiveObjectPropertyAxiom IrreflexiveObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLIrreflexiveObjectPropertyAxiom(property, l(a));}
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLSymmetricObjectPropertyAxiom(property);}
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLSymmetricObjectPropertyAxiom(property, a);}
    public static OWLSymmetricObjectPropertyAxiom SymmetricObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLSymmetricObjectPropertyAxiom(property, l(a));}
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLAsymmetricObjectPropertyAxiom(property);}
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLAsymmetricObjectPropertyAxiom(property, a);}
    public static OWLAsymmetricObjectPropertyAxiom AsymmetricObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLAsymmetricObjectPropertyAxiom(property, l(a));}
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(OWLObjectPropertyExpression property) {return df.getOWLTransitiveObjectPropertyAxiom(property);}
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property) {return df.getOWLTransitiveObjectPropertyAxiom(property, a);}
    public static OWLTransitiveObjectPropertyAxiom TransitiveObjectProperty(OWLAnnotation a, OWLObjectPropertyExpression property) {return df.getOWLTransitiveObjectPropertyAxiom(property, l(a));}
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {return df.getOWLSubDataPropertyOfAxiom(subProperty, superProperty);}
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(Collection<OWLAnnotation> a, OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {return df.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, a);}
    public static OWLSubDataPropertyOfAxiom SubDataPropertyOf(OWLAnnotation a, OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty) {return df.getOWLSubDataPropertyOfAxiom(subProperty, superProperty, l(a));}
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(OWLDataPropertyExpression... properties) {return df.getOWLEquivalentDataPropertiesAxiom(properties);}
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(Collection<OWLAnnotation> a, OWLDataPropertyExpression... properties) {return df.getOWLEquivalentDataPropertiesAxiom(l(properties), a);}
    public static OWLEquivalentDataPropertiesAxiom EquivalentDataProperties(OWLAnnotation a, OWLDataPropertyExpression... properties) {return df.getOWLEquivalentDataPropertiesAxiom(l(properties), l(a));}
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(OWLDataPropertyExpression... properties) {return df.getOWLDisjointDataPropertiesAxiom(properties);}
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(Collection<OWLAnnotation> a, OWLDataPropertyExpression... properties) {return df.getOWLDisjointDataPropertiesAxiom(l(properties), a);}
    public static OWLDisjointDataPropertiesAxiom DisjointDataProperties(OWLAnnotation a, OWLDataPropertyExpression... properties) {return df.getOWLDisjointDataPropertiesAxiom(l(properties), l(a));}
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLDataPropertyExpression property, OWLClassExpression domain) {return df.getOWLDataPropertyDomainAxiom(property, domain);}
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLClassExpression domain) {return df.getOWLDataPropertyDomainAxiom(property, domain, a);}
    public static OWLDataPropertyDomainAxiom DataPropertyDomain(OWLAnnotation a, OWLDataPropertyExpression property, OWLClassExpression domain) {return df.getOWLDataPropertyDomainAxiom(property, domain, l(a));}
    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLDataPropertyExpression property, OWLDataRange range) {return df.getOWLDataPropertyRangeAxiom(property, range);}
    public static OWLDataPropertyRangeAxiom DataPropertyRange(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLDataRange range) {return df.getOWLDataPropertyRangeAxiom(property, range, a);}
    public static OWLDataPropertyRangeAxiom DataPropertyRange(OWLAnnotation a, OWLDataPropertyExpression property, OWLDataRange range) {return df.getOWLDataPropertyRangeAxiom(property, range, l(a));}
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(OWLDataPropertyExpression property) {return df.getOWLFunctionalDataPropertyAxiom(property);}
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(Collection<OWLAnnotation> a, OWLDataPropertyExpression property) {return df.getOWLFunctionalDataPropertyAxiom(property, a);}
    public static OWLFunctionalDataPropertyAxiom FunctionalDataProperty(OWLAnnotation a, OWLDataPropertyExpression property) {return df.getOWLFunctionalDataPropertyAxiom(property, l(a));}
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLDatatype datatype, OWLDataRange dataRange) {return df.getOWLDatatypeDefinitionAxiom(datatype, dataRange);}
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(Collection<OWLAnnotation> annotations, OWLDatatype datatype, OWLDataRange dataRange) {return df.getOWLDatatypeDefinitionAxiom(datatype, dataRange, annotations);}
    public static OWLDatatypeDefinitionAxiom DatatypeDefinition(OWLAnnotation a, OWLDatatype datatype, OWLDataRange dataRange) {return df.getOWLDatatypeDefinitionAxiom(datatype, dataRange, l(a));}
    public static OWLHasKeyAxiom HasKey(OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {return df.getOWLHasKeyAxiom(classExpression, propertyExpressions);}
    public static OWLHasKeyAxiom HasKey(Collection<OWLAnnotation> a, OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {return df.getOWLHasKeyAxiom(classExpression, l(propertyExpressions), a);}
    public static OWLHasKeyAxiom HasKey(OWLAnnotation a, OWLClassExpression classExpression, OWLPropertyExpression... propertyExpressions) {return df.getOWLHasKeyAxiom(classExpression, l(propertyExpressions), l(a));}
    public static OWLSameIndividualAxiom SameIndividual(OWLIndividual... individuals) {return df.getOWLSameIndividualAxiom(individuals);}
    public static OWLSameIndividualAxiom SameIndividual(Collection<OWLAnnotation> a, OWLIndividual... individuals) {return df.getOWLSameIndividualAxiom(l(individuals), a);}
    public static OWLSameIndividualAxiom SameIndividual(OWLAnnotation a, OWLIndividual... individuals) {return df.getOWLSameIndividualAxiom(l(individuals), l(a));}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLIndividual... individuals) {return df.getOWLDifferentIndividualsAxiom(individuals);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(Collection<OWLAnnotation> a, OWLIndividual... individuals) {return df.getOWLDifferentIndividualsAxiom(l(individuals), a);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLAnnotation a, OWLIndividual... individuals) {return df.getOWLDifferentIndividualsAxiom(l(individuals), l(a));}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(Collection<OWLAnnotation> a, Collection<? extends OWLIndividual> individuals) {return df.getOWLDifferentIndividualsAxiom(individuals, a);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(OWLAnnotation a, Collection<? extends OWLIndividual> individuals) {return df.getOWLDifferentIndividualsAxiom(individuals, l(a));}
    public static OWLSameIndividualAxiom SameIndividual(Collection<? extends OWLIndividual> individuals) {return df.getOWLSameIndividualAxiom(individuals);}
    public static OWLDifferentIndividualsAxiom DifferentIndividuals(Collection<? extends OWLIndividual> individuals) {return df.getOWLDifferentIndividualsAxiom(individuals);}
    public static OWLClassAssertionAxiom ClassAssertion(Collection<OWLAnnotation> a, OWLClassExpression ce, OWLIndividual ind) {return df.getOWLClassAssertionAxiom(ce, ind, a);}
    public static OWLClassAssertionAxiom ClassAssertion(OWLAnnotation a, OWLClassExpression ce, OWLIndividual ind) {return df.getOWLClassAssertionAxiom(ce, ind, l(a));}
    public static OWLClassAssertionAxiom ClassAssertion(OWLClassExpression ce, OWLIndividual ind) {return df.getOWLClassAssertionAxiom(ce, ind);}
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return df.getOWLObjectPropertyAssertionAxiom(property, source, target);}
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return df.getOWLObjectPropertyAssertionAxiom(property, source, target, a);}
    public static OWLObjectPropertyAssertionAxiom ObjectPropertyAssertion(OWLAnnotation a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return df.getOWLObjectPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return df.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target);}
    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(Collection<OWLAnnotation> a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return df.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target, a);}
    public static OWLNegativeObjectPropertyAssertionAxiom NegativeObjectPropertyAssertion(OWLAnnotation a, OWLObjectPropertyExpression property, OWLIndividual source, OWLIndividual target) {return df.getOWLNegativeObjectPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return df.getOWLDataPropertyAssertionAxiom(property, source, target);}
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return df.getOWLDataPropertyAssertionAxiom(property, source, target, a);}
    public static OWLDataPropertyAssertionAxiom DataPropertyAssertion(OWLAnnotation a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return df.getOWLDataPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return df.getOWLNegativeDataPropertyAssertionAxiom(property, source, target);}
    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(Collection<OWLAnnotation> a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return df.getOWLNegativeDataPropertyAssertionAxiom(property, source, target, a);}
    public static OWLNegativeDataPropertyAssertionAxiom NegativeDataPropertyAssertion(OWLAnnotation a, OWLDataPropertyExpression property, OWLIndividual source, OWLLiteral target) {return df.getOWLNegativeDataPropertyAssertionAxiom(property, source, target, l(a));}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value) {return df.getOWLAnnotationAssertionAxiom(property, subj, value);}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(Collection<OWLAnnotation> set, OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value) {return df.getOWLAnnotationAssertionAxiom(property, subj, value, set);}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotation a, OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value) {return df.getOWLAnnotationAssertionAxiom(property, subj, value, l(a));}
    public static OWLAnnotationAssertionAxiom AnnotationAssertion(OWLAnnotationProperty property, OWLAnnotationSubject subj, OWLAnnotationValue value, OWLAnnotation... set) {return df.getOWLAnnotationAssertionAxiom(property, subj, value,     new HashSet<>(l(set)));}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, OWLAnnotationValue value) {return df.getOWLAnnotation(property, value);}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, String value) {return df.getOWLAnnotation(property, df.getOWLLiteral(value));}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, boolean value) {return df.getOWLAnnotation(property, df.getOWLLiteral(value));}
    public static OWLAnnotation Annotation(Collection<OWLAnnotation> anns, OWLAnnotationProperty property, OWLAnnotationValue value) {if (anns.isEmpty()) {return df.getOWLAnnotation(property, value);} return df.getOWLAnnotation(property, value, anns.stream());}
    public static OWLAnnotation Annotation(OWLAnnotation a, OWLAnnotationProperty property, OWLAnnotationValue value) {return df.getOWLAnnotation(property, value, l(a));}
    public static OWLAnnotation Annotation(OWLAnnotationProperty property, OWLAnnotationValue value, OWLAnnotation... anns) {if (anns.length == 0) {return df.getOWLAnnotation(property, value);} return df.getOWLAnnotation(property, value, Stream.of(anns));}
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {return df.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty);}
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(Collection<OWLAnnotation> a, OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {return df.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty, a);}
    public static OWLSubAnnotationPropertyOfAxiom SubAnnotationPropertyOf(OWLAnnotation a, OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty) {return df.getOWLSubAnnotationPropertyOfAxiom(subProperty, superProperty, l(a));}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty property, IRI iri) {return df.getOWLAnnotationPropertyDomainAxiom(property, iri);}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(Collection<OWLAnnotation> annotations, OWLAnnotationProperty property, IRI iri) {return df.getOWLAnnotationPropertyDomainAxiom(property, iri, annotations);}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotation a, OWLAnnotationProperty property, IRI iri) {return df.getOWLAnnotationPropertyDomainAxiom(property, iri, l(a));}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty property, IRI iri) {return df.getOWLAnnotationPropertyRangeAxiom(property, iri);}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(Collection<OWLAnnotation> annotations, OWLAnnotationProperty property, IRI iri) {return df.getOWLAnnotationPropertyRangeAxiom(property, iri, annotations);}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotation a, OWLAnnotationProperty property, IRI iri) {return df.getOWLAnnotationPropertyRangeAxiom(property, iri, l(a));}
    public static OWLAnnotationPropertyDomainAxiom AnnotationPropertyDomain(OWLAnnotationProperty property, String iri) {return df.getOWLAnnotationPropertyDomainAxiom(property, df.getIRI(iri));}
    public static OWLAnnotationPropertyRangeAxiom AnnotationPropertyRange(OWLAnnotationProperty property, String iri) {return df.getOWLAnnotationPropertyRangeAxiom(property, df.getIRI(iri));}
    public static IRI IRI(String iri) {return df.getIRI(iri);}
    public static IRI NextIRI(String iri) {return df.getNextDocumentIRI(iri);}
    public static OWLLiteral PlainLiteral(String in) {return df.getOWLLiteral(in, "");}
    public static OWLDatatype PlainLiteral() {return df.getRDFPlainLiteral();}
    public static OWLLiteral Literal(String in, @Nullable String lang) {return df.getOWLLiteral(in, lang);}
    public static OWLLiteral Literal(String in, OWLDatatype type) {return df.getOWLLiteral(in, type);}
    public static OWLLiteral Literal(String in, OWL2Datatype type) {return df.getOWLLiteral(in, type);}
    public static OWLLiteral Literal(String in) {return df.getOWLLiteral(in);}
    public static OWLLiteral Literal(boolean in) {return df.getOWLLiteral(in);}
    public static OWLLiteral Literal(int in) {return df.getOWLLiteral(in);}
    public static OWLLiteral Literal(double in) {return df.getOWLLiteral(in);}
    public static OWLLiteral Literal(float in) {return df.getOWLLiteral(in);}
    public static OWLLiteral Literal(long in) {return df.getOWLLiteral(in);}
    public static SWRLRule SWRLRule(Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {return df.getSWRLRule(body, head);}
    public static SWRLRule SWRLRule(Collection<OWLAnnotation> annotations, Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {return df.getSWRLRule(body, head, annotations);}
    public static SWRLRule SWRLRule(OWLAnnotation a, Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head) {return df.getSWRLRule(body, head, l(a));}
    public static SWRLClassAtom SWRLClassAtom(OWLClassExpression pred, SWRLIArgument arg) {return df.getSWRLClassAtom(pred, arg);}
    public static SWRLDataRangeAtom SWRLDataRangeAtom(OWLDataRange pres, SWRLDArgument arg) {return df.getSWRLDataRangeAtom(pres, arg);}
    public static SWRLDataRangeAtom SWRLDataRangeAtom(OWL2Datatype pred, SWRLDArgument arg) {return df.getSWRLDataRangeAtom(pred, arg);}
    public static SWRLObjectPropertyAtom SWRLObjectPropertyAtom(OWLObjectPropertyExpression property, SWRLIArgument arg0, SWRLIArgument arg1) {return df.getSWRLObjectPropertyAtom(property, arg0, arg1);}
    public static SWRLDataPropertyAtom SWRLDataPropertyAtom(OWLDataPropertyExpression property, SWRLIArgument arg0, SWRLDArgument arg1) {return df.getSWRLDataPropertyAtom(property, arg0, arg1);}
    public static SWRLBuiltInAtom SWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args) {return df.getSWRLBuiltInAtom(builtInIRI, args);}
    public static SWRLBuiltInAtom SWRLBuiltInAtom(IRI builtInIRI, SWRLDArgument... args) {return df.getSWRLBuiltInAtom(builtInIRI, l(args));}
    public static SWRLVariable SWRLVariable(IRI var) {return df.getSWRLVariable(var);}
    public static SWRLVariable SWRLVariable(String iri) {return SWRLVariable(df.getIRI(iri));}
    public static SWRLVariable SWRLVariable(String namespace, @Nullable String remainder) {return SWRLVariable(df.getIRI(namespace, remainder));}
    public static SWRLVariable SWRLVariable(HasIRI var) {return SWRLVariable(var.getIRI());}
    public static SWRLIndividualArgument SWRLIndividualArgument(OWLIndividual individual) {return df.getSWRLIndividualArgument(individual);}
    public static SWRLLiteralArgument SWRLLiteralArgument(OWLLiteral in) {return df.getSWRLLiteralArgument(in);}
    public static SWRLSameIndividualAtom SWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1) {return df.getSWRLSameIndividualAtom(arg0, arg1);}
    public static SWRLDifferentIndividualsAtom SWRLDifferentIndividualsAtom(SWRLIArgument arg0, SWRLIArgument arg1) {return df.getSWRLDifferentIndividualsAtom(arg0, arg1);}
    public static OWLOntology Ontology(OWLOntologyManager man, OWLAxiom... axioms) {try {return man.createOntology(l(axioms));} catch (OWLOntologyCreationException ex) {throw new OWLRuntimeException(ex);}}
    public static OWLOntologyID OntologyID() {return df.getOWLOntologyID();}
    public static OWLOntologyID OntologyID(IRI ont) {return df.getOWLOntologyID(ont);}
    public static OWLOntologyID OntologyID(IRI ont, IRI version) {return df.getOWLOntologyID(ont, version);}
  //@formatter:on
}
