# OWLAPI
======

## OWL API main repository

The OWL API is a Java API for creating, manipulating and serialising OWL Ontologies. 

* The latest version of the API supports OWL 2.

* OWLAPI 5.5.0 requires Java 11 

* It is available under Open Source licenses (LGPL and Apache).

The following components are included:

* An API for OWL 2 and an in-memory reference implementation.
* Read and write support for RDF/XML, OWL/XML, Functional syntax, Manchester syntax, Turtle, OBO.
* Write support for KRSS, DL syntax, LaTeX.
* Other formats via RIO integration (NTriples, JSON, etc.).
* Reasoner interfaces for working with reasoners such as FaCT++, HermiT, Pellet, Racer, JFact and Chainsaw.
* See documentation pages on the wiki for more details.

## Release notes

## 5.5.0 14 January 2023

### Features:

*    Java 11 required to include dependencies that require Java 9 or newer
*    Added support for MS syntax to include all features from OWL2 DL

### Bug fixes:

*    Memory leaks in semantic module extraction
*    Ensure pending annotations are cleared after handling triple
*    Only remove unannotated axiom if it received annotations.
*    Check for empty collections on AxiomSubjectProvder #1069
*    Allow forcing explicit xsd:xstrig type #1063
*    Bump rdf4j.version to 4.2.0, JDK to 11, version to 5.5.0
*    OBO serializer incorrectly quoting IRI property values (#1085)

## 5.1.20 19 February 2022

### Features:

*    Upgraded to RDF4j 3.7.4 and fixed issues with OSGi bundles
*    Improve MAVEN build Performance
*    Extend messages for OWL exceptions #1020

### Bug fixes:

*    Fix OWLDifferentIndividuals with two ops rendered with one triple #1031
*    Fix Abbreviated IRI as OntologyIRI in Manchester syntax #1041
*    Fix Individuals: anonymous individuals in Manchester syntax #1006
*    Fix UNPARSED TRIPLES ERROR for class assertions #1023


## 5.1.19 31 July 2021

### Bug fixes:

*    vulnerable dependencies update


## 5.1.18 30 July 2021

### Features:

*    Specify RioSetting values for Rio renderers #614

### Bug fixes:

*    Fix sameAs failure when more than 2 entities included #994
*    Fix Trig and rdf/json should include a named graph. #1002
*    Fix ObjectHasSelf rendered wrongly in manchester syntax #1005

## 5.1.17 6 November 2020

### Features:

*    Remove @Deprecated annotations for Set based methods in OWLAPI 5 #981
*    Support RDF4J Rio HDT parser #931
*    OWLLiteral for XSD:Long #970

### Bug fixes:

*    Fix Performance of signature checks during ontology changes #968
*    Fix Error on RIO renderer when expression has 6000 elements #971
*    Fix OWLOntology#datatypesInSignature to include ontology header #965
*    Fix EntitySearcher.getSuperProperties fails when parent is inverse #964
*    Update guava and junit versions
*    Fix OWLParser not ensuring streams are closed on exit #973
*    Error with undeclared classes in domain axioms #962
*    Fix Ontology caches should use weak keys #984

## 5.1.16 28 July 2020

### Bug fixes:

*    Fix follow multiple redirects across protocols #954
*    Javadoc fixes for deprecated stream methods #950

## 5.1.15 02 July 2020

### Features:

*    Allow creation of tautologies for n-ary axioms #776
*    Configurable fast pruning window size

### Bug fixes:

*    Fix javadoc for OWLObject::nestedClassExpressions #937
*    Fix classAssertionAxioms with OWLClassExpression fails #930
*    Fix Include ontology annotations in signature #928
*    Fix Unable to set base directive for turtle writers #938
*    Fix OWLAPI accepts IRIs with leading spaces #940
*    Fix SWRL body reordered when structure shared #936
*    Fix roundtrip through OBO changes IRI of owl:versionInfo #947



## 5.1.14 18 April 2020

### Features:
*    General modularity classes contributed by Robn Nolte

### Bug fixes:

*    Fix XSD datatypes are erroneously quoted in OBO writer #918
*    Fix referencingAxioms(OWLPrimitive) misses nested literals #912
*    Fix Empty line in META-INF/services/ files causes exceptions #924
*    Fix OWLOntologyWriterConfiguration does not disable banner comments #904


## 5.1.13 27 January 2020

### Bug fixes:

*    Fix OWLEntityRenamer and anonymous individuals #892
*    Fix Builtin annotation properties lost during parsing #895
*    Deal with OWLAnnotationProperty entities in OWLEntityURIConverter class #896


## 5.1.12 20 October 2019

### Bug fixes:

*    Implement Allow gzipped imports #887
*    Fix Race condition in Injector #883
*    Jackson update
*    Fix containsReference(OWLEntity) should be deprecated #864
*    Fix referencingAxioms(OWLPrimitive) misses IRI appearances #865
*    Fix Javadoc on applyChange/applyChanges and using the wrong manager #868
*    Fix OWLObjectPropertyExpression#getSimplified() used incorrectly #882
*    Fix Incomplete javadoc on OWLNaryAxiom#asPairwiseAxioms #884
*    Fix OWLNegative*AssertionAxiom#containsAnonymousIndividuals javadoc #885
*    Fix Annotated axiom with anon expression saved incorrectly #881
*    Fix Ontology with relative IRIs is serialized incorrectly #880
*    Fix Ann. annotation with anon individual saved in RDF incorrectly #877
*    Fix Annotations dropped if annotation property is undeclared #875
*    Fix SAXException from AutoIRIMapper at debug logging level #878
*    Ensure isAnonymous is implemented correctly #867
*    Fix Null pointers with imports and relation declarations #859
*    Amend base and escaped characters in Tutle parsing #857
*    Fix Exception when converting obi to obo #860


## 5.1.11 02 June 2019

### Features:

*    Add support to load an ontology from classpath #837
*    Implement Allow annotations to be skipped in module extraction #838
*    Add support for custom tags in obo files.

### Bug fixes:

*    Fix Unescaping characters: OBOFormatParser#handleNextChar() #822
*    Fix IRI PREFIX_CACHE instance uses too much memory #825
*    Fix roundtrip of escaped values #833
*    Fix MaximumNumberOfNamedSuperclasses should count super classes #836
*    Fix OWLDataPropertyAxiom not a subinterface of OWLPropertyAxiom #831
*    Fix HTTP 307 and HTTP 308 redirects are not followed (in 4.x) #821
*    Fix Missing escape character in OBO output #828
*    Fix 5.1.10 Regression in OwlStringTools#translate(..) #829
*    Fixed several incorrect XSD datatype matching patterns.
*    Fix OWLDataFactory::getLiteral error with empty string and integer #846
*    Fix Unnecessary dc prefix added by Manchester syntax parser #845
*    Fix Multiple Ontology Definitions should obey strict parsing #840
*    Fix OWLLogicalEntity is not an OWLAnnotationProperty #847
*    Security: Jackson to 2.9.9
*    Fix Manchester syntax parser crashes on class/property punning #851
*    Fix OBO parser does not support qualifier block #852


## 5.1.10 04 March 2019

### Bug fixes:

*    Fix DLExpressivity checker never computes anything #810
*    Jackson version to 2.9.8
*    Fix ensure allValuesFrom axioms are not lost #808
*    Fix HTTP 307 and HTTP 308 redirects are not followed #821
*    Fix OBO renderer stuck with untranslatable axioms on concurrent managers
*    Fix Annotations on DifferentIndividualsAxioms lost #816
*    Fix No roundtrip for IRIs with colons in the fragment or path #817
*    Fix EOFException in CustomTokenizer #813
*    Fix Cyclic imports and missing declarations cause parsing error #798

## 5.1.9 12 December 2018

### Bug fixes:

*    Refactor OWLProfile implementations #638
*    Fix Missing user defined datatype violation in literals #639
*    Fix RDFGraph getSubjectsForObjects caught in infinite loop #809

## 5.1.8 1 December 2018

### Features:

*    Add OWLClassExpression.isNamed method #790
*    Fix injection problem under OSGi
*    Implement Allow Atomic Decomposition to skip assertions #796
*    Expressivity Checker for EL and FL #500

### Bug fixes:

*    Fix ReadWriteLock should be injector singleton #785
*    Fix Cyclic import of versioned ontologies fails #788
*    Fix Annotate ontology annotations #791
*    Fix Incorrect documentation for OWLOntologyManager methods. #795
*    DisjointClasses with OWL:Thing produces incorrect axiom #747
*    Fix Concurrent managers with own lock shared with own ontologies #806

## 5.1.7 2 September 2018

### Features:

*    PROV and TIME vocabularies

### Bug fixes:

*    Add representativeInstances() to OWLReasoner #772
*    SWRLRule hash code computed incorrectly
*    Fix OWLOntology with shared structure causes incorrect RDF/XML #780

## 5.1.6 24 July 2018

### Features:

*    Remove Guice dependencies
*    Upgrade to jsonld-java version 0.12.0 (performance) #763
*    Allow building with Java 10

### Bug fixes:

*    Move from Trove4j to HPPC-RT #774
*    Fix incorrect sorting of OBO header tags
*    Fix Line breaks in rdfs:label cause invalid FS output #758
*    Fix AutoIriMapper chooses wrong IRIs. #755

## 5.1.5 23 April 2018

### Features:

*    Add an option to represent version build as strings
*    Implement #375 OWLZip reader and writer
*    Allow trimming to size after load to be disabled
*    do not register deprecated oboparser by default #729

### Bug fixes:

*    Fix SWRL variable IRIs violate URN spec #732
*    default IRI for unnamed ontologies is not valid
*    doubling of # at the end of default namespace
*    IRI should return true for isIRI()
*    Fix OWL/XML writes langString unnecessarily #748
*    Fix importsDeclaration not returning imports for *.obo #727

## 5.1.4 4 January 2018

### Features:

*    Support Authorization header in remote loading

### Bug fixes:

*    Fix Problem saving ontologies in Turtle #719
*    Fix Null pointer in OWLObjectPropertyManager #723
*    Remove com.google.inject and add exclusion of javax.annotation. #720

## 5.1.3 4 November 2017

Features:

* Performance improvements on parsing of large ontologies.

## 5.1.2 13 October 2017

### Features:

*    Accept Headers to include all MIME types with supporting parsers #705
*    Add HasAnnotationValue interface with methods for mapping
*    Optional methods for OWLOntologyChange
*    Implement efficient way to test if an ontology refers entity type #698

### Bug fixes:

*    Do not output xsd:string when unnecessary #640
*    OWL/XML should reject XML files that are not valid OWL/XML #657
*    getFragment advices an non existent replacement #684
*    OWLObject immutable collections sorted #702
*    Sort imports and imports closure #702
*    Sort namespace prefixes for XML serialization #702
*    OWLOntologyMerger fails with ConcurrentModificationException #673
*    Poor performance in OWLImmutableOntologyImpl.getImportsClosure #696
*    Fix AtomicDecomposition throws Nullpointer #695
*    Fix hashcode and equals on OWLOntology differ #694

## 5.1.1 25 July 2017

### Features:

*    Add REPAIR_ILLEGAL_PUNNINGS property to disable fix of illegal punnings
*    Move punning log to warning

### Bug fixes:

*    Fix Profiles.OWL2_FULL returns Profiles.OWL2_DL #667
*    Fix EntitySearcher.getEquivalentClasses incorrectly returns itself #663
*    OWLEntityRenamer should rename annotation props in ontology annotations
*    Fix blank node ids should be NCNames in the RDF/XML output #689

## 5.1.0 30 March 2017

### Features:

*    Add stream support on reasoning interface.
*    Allow SyntacticLocalityModuleExtractor to exclude assertions. #462
*    Explanations: do not fail hard for profile violations
*    Latex formatting improvements
*    ensure ontology annotations are sorted
*    Implement HasApplyChanges.applyChanges returns ChangeApplied #544
*    recompile parsers with javacc 7
*    issue #612 : Add japicmp maven plugin to track API changes
*    Implement Zip with dependencies included for non Maven users #584
*    Dependencies update, move to rdf4j

### Bug fixes:

*    Fix Relating to inferred axiom generators #646
*    Fix duplication and inverseOf properties in generators #646
*    Fix SimpleRenderer writes InverseOf instead of ObjectInverseOf #647
*    Fix nested annotation bug
*    Fix Turtle parser failure on some bioportal ontology #610
*    Fix Manchester expression parser bug with data cardinalities #609

## 5.0.5 4 January 2017

### Bug fixes:

*    Allow supplier for null error messages
*    IsAnonymous, isIndividual, isAxiom, isAnonymousExpression on OWLObject
*    Performance improved in creating OWLOntologyManager
*    Ensure XXE vulnerability is prevented
*    Fix Structural sharing in GCIs causes errors #564
*    Fix Issue with serialization - OWLAPI version 3.5.5 #586
*    Fix Problems compiling version5 for Android v24 Nougat #585
*    Fix Order of RDF triples affects annotation parsing #574
*    Fix Property Axiom Generator should check set size #527
*    Fix Imports not properly loaded after all ontologies removed #580
*    Fix Incomplete Parsing of DifferentIndividuals with distinctMembers #569
*    Fix Apache Harmoony SAX parser not supported #581
*    Fix line separators in `DLSyntaxHTMLStorer` #583
*    Fix IRIs with query string cause OFN unparseable output #570
*    Fix Unqualified data restriction considered qualified #576

## 5.0.4 16 October 2016

### Bug fixes:

*    Serializability issues

## 5.0.3 10 September 2016

### Bug fixes:

*    Fix OMN Parser mistakes punned class for object property #548
*    Fix OWL/XML format does not support SWRL variable IRIs #535
*    Fix Explicit prefix ignored during serialization #529
*    Fix Saving to OFN loses namespaces due to double default prefix #537
*    Fix DL-Syntax HTML formatter issues #536
*    Fix ManchesterOWLSyntaxRenderer SubPropertyOf render inconsistent #534
*    Fix Slow inferred disjoint generator #542
*    Fix Relative Ontology IRI causes missing declaration #557
*    Fix Invalid Turtle ontologies when hash IRIs are used #543
*    Fix Calling setOntologyDocumentIRI reset imports closure cache #541
*    Fix OWLOntology::annotationPropertiesInSignature has duplicates #555
*    Fix Latex Renderer produces bad LaTeX for property inverses #526
*    Fix ManchesterOWLSyntaxParser doesn't handle the OWL 2 datatypes #556
*    Fix Turtle Renderer doesn't escape periods #525
*    Fix Import IRI not resolved correctly in OBO input #523

## 5.0.2 7 May 2016

### Features:

*    Allow banning of parsers #510
*    Allow disabling banner comments
*    Move builders to api to be able to use them in parsers
*    Make AxiomType Comparable

### Bug fixes:

*    Fix performance regression in String.intern() and improve memory footprint
*    Fix Default prefix overrides explicit prefixes #522
*    Fix Anonymous individuals parsing problems #494
*    Fix Noisy print messages from logging framework #516
*    Fix SWRL rules with incorrect equals() #512
*    Fix OWL 2 Full Profile #508
*    Fix Annotated entities not declared in RDF/XML #511
*    Fix XML literal is not self contained #509

## 5.0.1 19 March 2016

### Bug fixes:

*    Fix Fixing punnings fails on dp subDataPropertyOf rdfs:label #505
*    "Dumping remaining triples" displayed when no triples left #502
*    Fix Annotations lost on entity renaming of SWRLRules #501
*    Fix Profile validation throws exception on ontology annotations #498
*    Fix RDF/XML parser failing to load ontology containing XMLLiteral #496
*    Fix #489 makeLoadImportRequest log should be a warning #489
*    Fix Anonymous individuals parsing problems #494
*    Fix Saving fails for ontologies containing XMLLiteral #495
*    Fix Anonymous individuals parsing problems #494

## 5.0.0 28 February 2015

### Features:

*    Allow overridable and defaultable properties
*    FaCT++ AD implementation from OWLAPITOOLS
*    Add OWLObjectTransformer to replace any part of an ontology
*    Add HasOperands interface
*    Ad componentsWithoutAnnotations() to simplify equalsIgnoreAnnotations
*    Add componentsAnnotationsFirst() to OWLObject
*    Add component() to OWLObject to create a common interface for hashcode
*    Add rdf:langString data type
*    commons-rdf-api integration
*    Add InferenceDepth and convenience methods to OWLReasoner
*    OWLRDFConsumer fills guessed type declaration table with blank nodes
*    Marked methods returning sets where a stream is available as deprecated
*    Update guice version to beta 5
*    Replace Google Optional with Java 8 Optional (#250)
*    OWLProfileViolationVisitorEx returns Optional<T> rather than nulls
*    Add default methods to OWLOntology for direct add/remove of axioms
*    Added a getFormat method to OWLOntology
*    Merged visitor adapters into the interfaces with default methods
*    isAvailable/get pattern in OWLOntologyDocumentSource changed to Optional
*    OWLOntology.getReferencingAxioms() finds any of the OWLPrimitive objects
*    Enabled OWLOntologyManager to build and keep an OntologyConfigurator instance
*    Declaration of save methods on OWLOntology
*    OWLAPI 5 uses Java 8

## 4.5.26 18 July 2023

### Bug fixes:
*    Fix violations in declarations not reported #1094 #1046
*    Allow arbitrary annotation properties as qualifier tags in OBO #1099
*    Fix Shared class expression causes extra triples in output #1109

## 4.5.25 15 February 2023

### Bug fixes:

*    Add Trix to list of BANNED_PARSERS in OWLAPI 4.X #1088
*    OBO serialisation illegal axiom annotation #1093
*    Remove unnecessary log when OBO unsupported axioms are serialized

## 4.5.24 20 January 2023

### Features:

*    Fix OSGI distribution Guava version #1086

## 4.5.23 14 January 2023

### Features:

*    Allow forcing explicit xsd:xstrig type #1063

### Bug fixes:

*    OBO serializer incorrectly quoting IRI property values #1085
*    Update dependencies for security issues
*    Check for empty collections on AxiomSubjectProvder #1069
*    Correct bad default args #1063
*    Fix OWLEntityRenamer and anonymous individuals #892

## 4.5.22 7 May 2022

### Bug fixes:

*    Do not output xsd:string when unnecessary #640

## 4.5.21 19 February 2022

### Features:

*    Improve MAVEN build Performance
*    Extend messages for OWL exceptions #1020

### Bug fixes:

*    Fix OWLDifferentIndividuals with two ops rendered with one triple #1031
*    Fix Abbreviated IRI as OntologyIRI in Manchester syntax #1041
*    Fix Individuals: anonymous individuals in Manchester syntax #1006
*    Fix UNPARSED TRIPLES ERROR for class assertions #1023


## 4.5.20 31 July 2021

### Features:

*    Specify RioSetting values for Rio renderers #614

### Bug fixes:

*    Fix sameAs failure when more than 2 entities included #994
*    Ordering of literals to be consistent with OWLAPI 5
*    Fix Trig and rdf/json should include a named graph. #1002
*    Fix ObjectHasSelf rendered wrongly in manchester syntax #1005

## 4.5.19 7 November 2020

### Bug fixes:

*    Fix OWLParser not ensuring streams are closed on exit #973
*    Error with undeclared classes in domain axioms #962
*    Fix Ontology caches should use weak keys #984


## 4.5.18 23 October 2020

### Bug fixes:

*    Fix Performance of signature checks during ontology changes #968
*    Fix Error on RIO renderer when expression has 6000 elements #971
*    Fix OWLOntology#datatypesInSignature to include ontology header #965
*    Fix Ontology not loaded in case of multiple HTTP redirects #954


## 4.5.17 02 July 2020

### Features:

*    Let OBO parser follow redirects
*    Allow creation of tautologies for n-ary axioms #776
*    Configurable fast pruning window size

### Bug fixes:

*    Fix javadoc for OWLObject::nestedClassExpressions #937
*    Fix classAssertionAxioms with OWLClassExpression fails #930
*    Fix Include ontology annotations in signature #928
*    Fix Unable to set base directive for turtle writers #938
*    Fix OWLAPI accepts IRIs with leading spaces #940
*    Fix SWRL body reordered when structure shared #936
*    Fix roundtrip through OBO changes IRI of owl:versionInfo #947


## 4.5.16 18 April 2020

### Bug fixes:

*    Fix XSD datatypes are erroneously quoted in OBO writer #918
*    Fix referencingAxioms(OWLPrimitive) misses nested literals #912
*    Fix Empty line in META-INF/services/ files causes exceptions #924


## 4.5.15 28 January 2020

### Bug fixes:

*    Fix OWLEntityRenamer and anonymous individuals #892
*    Fix Builtin annotation properties lost during parsing #895
*    Deal with OWLAnnotationProperty entities in OWLEntityURIConverter class #896


## 4.5.14 19 October 2019

### Bug fixes:

*    Implement Allow gzipped imports #887
*    Fix Race condition in Injector #883
*    Jackson update
*    Fix containsReference(OWLEntity) should be deprecated #864
*    Fix Javadoc on applyChange/applyChanges and using the wrong manager #868
*    Fix OWLObjectPropertyExpression#getSimplified() used incorrectly #882
*    Fix Incomplete javadoc on OWLNaryAxiom#asPairwiseAxioms #884
*    Fix OWLNegative*AssertionAxiom#containsAnonymousIndividuals javadoc #885
*    Fix Annotated axiom with anon expression saved incorrectly #881
*    Fix Ontology with relative IRIs is serialized incorrectly #880
*    Fix Ann. annotation with anon individual saved in RDF incorrectly #877
*    Fix Annotations dropped if annotation property is undeclared #875
*    Fix SAXException from AutoIRIMapper at debug logging level #878
*    Ensure isAnonymous is implemented correctly #867
*    Fix Null pointers with imports and relation declarations #859
*    Amend base and escaped characters in Tutle parsing #857

## 4.5.13 02 June 2019

### Features:

*    Add support for custom tags in obo files. #848

### Bug fixes:

*    Fix OWLLogicalEntity is not an OWLAnnotationProperty #847
*    Security: Jackson to 2.9.9
*    Fix Manchester syntax parser crashes on class/property punning #851
*    Fix OBO parser does not support qualifier block #852

## 4.5.12 06 May 2019

### Features:

*    Add support to load an ontology from classpath #837
*    Implement Allow annotations to be skipped in module extraction #838

### Bug fixes:

*    Fix Multiple Ontology Definitions should obey strict parsing #840
*    Fix Unnecessary dc prefix added by Manchester syntax parser #845
*    Fix OWLDataFactory::getLiteral error with empty string and integer #846
*    Fixed several incorrect XSD datatype matching patterns #844

## 4.5.11 17 April 2019

### Bug fixes:

*    Fix HTTP 307 and HTTP 308 redirects are not followed (in 4.x) #821
*    Fix Missing escape character in OBO output #828
*    Fix OWLDataPropertyAxiom not a subinterface of OWLPropertyAxiom #831
*    Fix MaximumNumberOfNamedSuperclasses should count super classes #836
*    Fix OWLOntology::getGeneralClassAxioms slow #839
*    Fix roundtrip of escaped values #833

## 4.5.10 14 March 2019

### Bug fixes:

*    Fix HTTP 307 and HTTP 308 redirects are not followed (in 4.x) #821
*    Fix Ensure allValuesFrom axioms are not lost #808
*    Fix EOFException in CustomTokenizer #813
*    Fix Cyclic imports and missing declarations cause parsing error #798
*    Fix Unescaping characters: OBOFormatParser#handleNextChar() #822
*    Fix IRI PREFIX_CACHE instance uses too much memory #825


## 4.5.9 1 February 2019

### Bug fixes:

*    Jackson version to 2.9.8
*    Fix compatibility with Guava 27 #814
*    Fix OBO renderer stuck with untranslatable axioms on concurrent managers
*    Fix Annotations on DifferentIndividualsAxioms lost #816
*    Fix No roundtrip for IRIs with colons in the fragment or path #817

## 4.5.8 22 December 2018

### Features:

*    Refactor OWLProfile implementations #638

### Bug fixes:

*    Fix Missing user defined datatype violation in literals #639
*    Fix RDFGraph getSubjectsForObjects caught in infinite loop #809
*    Fix DLExpressivity checker never computes anything #810

## 4.5.7 1 December 2018

### Features:

*    Add OWLClassExpression.isNamed method #790
*    Fix injection problem under OSGi
*    Expressivity Checker for EL and FL #500

### Bug fixes:

*    Fix ReadWriteLock should be injector singleton #785
*    Fix Cyclic import of versioned ontologies fails #788
*    Fix Annotate ontology annotations #791
*    Fix Incorrect documentation for OWLOntologyManager methods. #795
*    DisjointClasses with OWL:Thing produces incorrect axiom #747
*    Fix Concurrent managers with own lock shared with own ontologies #806

## 4.5.6 6 September 2018

### Bug fixes:

*    OSGi issues fixed

## 4.5.5 2 September 2018

### Features:

*    PROV and TIME vocabularies

### Bug fixes:

*    Prefix splitting at wrong place with percent encoded IRI #737
*    Fix OWLOntology with shared structure causes incorrect RDF/XML #780

## 4.5.4 26 July 2018

### Features:

*    Remove Guice dependencies
*    Upgrade to jsonld-java version 0.12.0 (performance) #763
*    Build with Java 10

### Bug fixes:

*    Move from Trove4j to HPPC-RT #774
*    Literals with no lang and string literals must equal each other
*    Fix OWLLiteral.parseDouble should throw NumberFormatException #764
*    No need for Guice and Guava version updates
*    Amend pom files to build vaild osgi distribution #768
*    Fix incorrect sorting of OBO header tags
*    Fix Line breaks in rdfs:label cause invalid FS output #758
*    Fix AutoIriMapper chooses wrong IRIs. #755

## 4.5.2 22 April 2018

### Features:

*    Add an option to represent version build as strings
*    Implement #375 OWLZip reader and writer
*    Signature cache for ontologies
*    Allow trimming to size after load to be disabled
*    do not register deprecated oboparser by default #729

### Bug fixes:

*    Fix SWRL variable IRIs violate URN spec #732
*    default IRI for unnamed ontologies is not valid
*    doubling of # at the end of default namespace
*    IRI should return true for isIRI()
*    Fix OWL/XML writes langString unnecessarily #748

## 4.5.1 7 December 2017

### Features:

*    Support Authorization header in remote loading

## 4.5.0 13 October 2017

### Features:

*    Accept Headers to include all MIME types with supporting parsers #705
*    Add HasAnnotationValue interface with methods for mapping
*    Optional methods for OWLOntologyChange
*    Implement efficient way to test if an ontology refers entity type #698

### Bug fixes:

*    OWL/XML should reject XML files that are not valid OWL/XML #657
*    OWLObject immutable collections sorted #702
*    Sort imports and imports closure #702
*    Sort namespace prefixes for XML serialization #702
*    OWLOntologyMerger fails with ConcurrentModificationException #673
*    getFragment advices an non existent replacement #684
*    Fix Poor performance in OWLImmutableOntologyImpl.getImportsClosure #696

## 4.3.2 25 July 2017

### Features:

*    Add REPAIR_ILLEGAL_PUNNINGS property to disable fix of illegal punnings
*    Move punning log to warning

### Bug fixes:

*    Fix Profiles.OWL2_FULL returns Profiles.OWL2_DL #667
*    Fix EntitySearcher.getEquivalentClasses incorrectly returns itself #663
*    Fix blank node ids should be NCNames in the RDF/XML output #689
*    OWLEntityRenamer should rename annotation props in ontology annotations

## 4.3.1 27 March 2017

### Features:

*    Allow SyntacticLocalityModuleExtractor to exclude assertions. #462
*    Explanations: do not fail hard for profile violations

### Bug fixes:

*    Fix Relating to inferred axiom generators #646
*    Fix duplication and inverseOf properties in generators #646

## 4.3.0 22 March 2017

### Features:

*    Implement HasApplyChanges.applyChanges returns ChangeApplied #544
*    Implement Zip with dependencies included for non Maven users #584
*    issue #612 : Add japicmp maven plugin to track API changes
*    dependencies update

### Bug fixes:

*    Fix OWLLiteral produce different hash codes in version 4 #645
*    Multiple nested annotations fail in RDF/XML #470
*    Parsing of nested anonymous nodes broken in 4.1.1 #478
*    ensure ontology annotations are sorted
*    Fix Turtle parser failure on some bioportal ontology #610
*    Fix Manchester expression parser bug with data cardinalities #609

## 4.2.8 4 January 2017

### Bug fixes:

*    Fix Structural sharing in GCIs causes errors #564
*    Fix Issue with serialization - OWLAPI version 3.5.5 #586
*    Fix Order of RDF triples affects annotation parsing #574
*    Fix Property Axiom Generator should check set size #527
*    Fix Imports not properly loaded after all ontologies removed #580
*    Performance improved in creating OWLOntologyManager
*    Fix Incomplete Parsing of DifferentIndividuals with distinctMembers #569
*    Fix Apache Harmoony SAX parser not supported #581
*    Ensure XXE vulnerability does not exist
*    Fix line separators in `DLSyntaxHTMLStorer` #583
*    Fix IRIs with query string cause OFN unparseable output #570
*    Fix Unqualified data restriction considered qualified #576
*    Fix OWLOntologyManager in OWLImmutableOntologyImpl should be @Nullable #568


## 4.2.7 16 October 2016

### Bug fixes:

*    Serialization issues


## 4.2.6 10 September 2016

### Bug fixes:

*    Fix OMN Parser mistakes punned class for object property #548
*    Fix OWL/XML format does not support SWRL variable IRIs #535
*    Fix Explicit prefix ignored during serialization #529
*    Fix Saving to OFN loses namespaces due to double default prefix #537
*    Fix DL-Syntax HTML formatter issues #536
*    Fix ManchesterOWLSyntaxRenderer SubPropertyOf render inconsistent #534
*    Fix Slow inferred disjoint generator #542
*    Fix Relative Ontology IRI causes missing declaration #557
*    Fix Invalid Turtle ontologies when hash IRIs are used #543
*    Fix Calling setOntologyDocumentIRI reset imports closure cache #541
*    Fix Latex Renderer produces bad LaTeX for property inverses #526
*    Fix ManchesterOWLSyntaxParser doesn't handle the OWL 2 datatypes #556

## 4.2.5 17 May 2016

### Bug fixes:

*    Fix Turtle Renderer doesn't escape periods #525
*    Fix Import IRI not resolved correctly in OBO input #523

## 4.2.4 7 May 2016

### Bug fixes:

*    Fix Default prefix overrides explicit prefixes #522
*    Fix Anonymous individuals parsing problems #494
*    Fix OWL 2 Full Profile #508
*    Fix Annotated entities not declared in RDF/XML #511
*    Fix XML literal is not self contained #509

### Features:

*    Allow banning of parsers #510
*    Allow disabling banner comments
*    Fix Noisy print messages from logging framework #516

## 4.2.3 19 March 2016

### Bug fixes:

*    Fix Fixing punnings fails on dp subDataPropertyOf rdfs:label #505

## 4.2.2 15 March 2016

### Bug fixes:

*    Fix RDF/XML parser failing to load ontology containing XMLLiteral #496
*    Fix Profile validation throws exception on ontology annotations #498
*    Fix Annotations lost on entity renaming of SWRLRules #501
*    Fix "Dumping remaining triples" displayed when no triples left #502

## 4.2.1 5 March 2016

### Bug fixes:

*    Fix Anonymous individuals parsing problems #494
*    Fix makeLoadImportRequest log should be a warning #489
*    Fix Saving fails for ontologies containing XMLLiteral #495

## 4.2.0 28 February 2016

### Features:

*    Allow overridable and defaultable properties

### Bug fixes:

*    Fix EntitySearcher.getDomains(OWLAnnotationProperty, OWLOntology) returns the range axioms instead of the domain axioms #492

## 4.1.4 3 February 2016

### Features:

*    Add OWLObjectTransformer to replace any part of an ontology #487
*    Add OWLLiteralReplacer to replace literals with different values #487
*    Throw error on malformed ontologies when parsing is strict (avoid Error1 classes) #444

### Bug fixes:

*    Fix RBox axiom types #479
*    Fix Parsing of nested anonymous nodes broken in 4.1.1 #478
*    Fix Error in rdf handling of deep nested annotations
*    Fix EntitySearcher.subPropertiesFilter is broken #486
*    Fix Cyclic imports cause illegal punning #483
*    Fix OWLOntologyManager.copyOntology does not copy imports #480
*    Fix Changes not successfully applied broadcasted as applied #476
*    Add restriction on aduna packages #471
*    Fix BOM removal wrapper is applied to all input streams #212
*    Fix Serialization into incorrect OWL syntax #468
*    Fix Constraint Sesame Versions in OSGi Imports #471
*    Fix ExplanationOrdererImpl.SeedExtractor throws exception #469
*    Fix for Multiple nested annotations fail in RDF/XML #470

## 4.1.0 25 October 2015

### Features:

*    Port protege-owlapi concurrency support to the OWL API
*    Enabled the creation of a concurrent manager
*    Part of Add transactional support to OWLOntology #382 : ability to rollback the application of a set of changes if one fails. Added a ChangeApplied.NO_OPERATION to track those changes that did not have any effect (e.g., adding an axiom that is already present or removing an axiom that is not present).
*    ManchesterOWLSyntaxParser could use a convenience method parseClassExpression(String s) #384
*    Add setting to use labels as banner comment or use IRIs as before
*    Add config to switch saving all anon individuals
*    Enforce correct argument types for OWLObjectInverseProperty in default impl
*    Add control flag to structure walking to allow annotations to be walked
*    Reduce space used by OBO Parser
*    update jackson dependency to 2.5.1

### Bug fixes:

*    OWLImmutableOntologyImpl.asSet() assumes input iterable is coming from a duplicate free collection #404
*    OWLOntologyManagerImpl.getVersions() calls get() on Optional without checking if the contained value is present #401
*    OWLOntologyManagerImpl tests storers with keys rather than canStoreOntology #400
*    setOntologyLoaderConfigurationProvider should accept a javax.inject.Provider #399
*    Cleaned distribution jar
*    Losing Annotations on Annotation Assertion Axioms #406
*    Return types of RemoveAxiom(s) vs. AddAxiom(s) #408
*    Examples file needs review #407
*    BFO fails to load in API version 4.0.X #405
*    Fix RDF/XML renderer does not like properties using urns (or almost urns) #301
*    All anonymous individuals are remapped consistently upon loading. #443
*    Only output node id for anonymous individuals if needed
*    Anon individuals appearing more than once as objects #443
*    Sort annotation assertions on output for manchester syntax #332
*    OWL 2 profile validation mistakes #435
*    [Typedef] created_by don't support space #419
*    ttl namespace abbreviations not preserved on serialization #421
*    Use EntitySearcher.getAnnotationObjects() when interested only in the object of annotation assertion axioms on an entity rather than the annotations on the annotation assertion axioms.
*    convincing owlapi-distribution to export rio dependencies
*    RDF issues testing 4.1.0 RC 1 #412
*    Annotation subproperty/domain/range disappear after save/load cycle (punning enhancement) #351


## 4.0.2 17 April 2015

### Features:

*    Simple OWL/FSS IRI sniffing for AutoIRIMapper.
*    Fix #373 Individuals in SWRL rules get extra type
*    XZ compression support
*    Sorting of FunctionalSyntaxObjectRenderer
*    Sort untyped IRI Annotation Assertions, general axioms.
*    Sort axiom Annotations, if they are output using the translateAnnotations method. Negative property assertions not yet ordered; neither are nary axioms and class expressions.
*    Add legacy files  to the OSGI distribution for  version 3.5 backwards compatibility.
*    Some reasoners may not support certain types of reasoning. They may also not support OWLReasoner::isEntailed, and hence may return false for all calls to  OWLReasoner::isEntailmentCheckingSupported.
*    Support preserving, transferring, and adding annotations during OBO Macro expansion
*    Add Extensions to link document formats and common file extensions
*    Optimise annotation lookup for module extraction enrichment
*    Change strategy for MapPointer storage: do all initial insertions in a list, and turn it into a THashSet upon first call to trimToSize or any get/size calls
*    Add benchmarking util to load ontology then dump to an hprof file for use with MAT
*    Improve conversion from OWL to OBO, add test case

### Bug fixes:

*    Fix PriorityCollectionSorting should be fixed at manager creation time #395
*    Fix OWLOntologyManagerImpl.getVersions() calls get() on Optional without checking if the contained value is present #401
*    Fix XMLLiterals should be output as XML, not quoted XML #333
*    Fix Should RDF/XML Renderer include annotation assertions from the entire closure? #371
*    Fix Using a PriorityCollection for IRI mappers (and perhaps other things) is confusing #386
*    Fix THashSets got left using default load factor (0.50) after construction is finished. #380
*    Fix RDF Consumer failing to guess property types #322
*    Fix NonMappingOntologyIRIMapper confuses extra OWLOntologyIRIMapper implementations #383
*    Fix Data property with xsd:integer range parsed as Object property (RDF/XML source) #378
*    Fix imports in OWL Manchester Notation fail #347
*    Fix ELProfile incorrectly rejects DisjointClasses axioms. #343
*    Fix OWLRDFConsumer fills guessed type declaration table with blank nodes #336
*    Fix #338 OWL-API: DOCTYPE declaration missing in XML serialization
*    Fix #337 Axiom Equality short-circuits are in the wrong place
*    Fixed bug when rendering anonymous individual in Manchester OWL syntax.
*    Reverted incorrect setting of all packaging types to bundle

## 4.0.1 22 November 2014

### Features:

*    trimToSize available to cut ontology internals collections to minimal size after loading (Simon Spero)
*    various memory optimizations and caching of rarely used values removed (Simon Spero)
*    use of Trove collections for ontology internals
*    OBOFormat: Configuration option to skip validation before writing OBO file #290

### Bug fixes:

*    fix a multithread bug revealed by owlapitools tests
*    fix #299 roundtrip errors on TriX and various other syntaxes
*    fix #292 SWRL rules saved by older versions of OWLAPI/Protege lose annotations
*    fix #288 Errors with object property assertions with inverseOf properties
*    fix #289 Manchester syntax errors: inverse() not recognized, SWRL rules rendered twice
*    fix #287 Error parsing RDF/Turtle with triple quoted literals - mismatching single and double quotes
*    fix #281 OntologyIRIShortFormProvider does wrong shortform generation


## 4.0.0 10 September 2014

Supported Java versions: Java 7, Java 8

### Features:

*    added HasAnnotationPropertiesInSignature to uniform treatment of annotation properties
*    added missing EntitySearcher methods for negative property assertions
*    Use Trove collections for ontology internals
*    improved performance of OWLAxiomImpl.equals
*    Transform functions from Collection<OWLOntoloy> and Collection<OWLOntologyID> to Collection<IRI>
*    PMD critical violations addressed
*    fix ServiceLoader use to be OSGi compatible
*    create osgidistribution: osgidistribution is a jar with embedded dependencies and including the compatibility module. It addresses the issues due to the OWLAPI dependencies not being wrapped in OSGi bundles separately.
*    enabled OWLOntologyManager to build and keep a loader configuration
*    OBO 1.2 ontologies cannot be parsed by the 1.4 parser. Added 1.2 parser from OWLAPITOOLS to compatibility package.
*    added saveOntology() methods to OWLMutableOntology
*    Add a copy/move ontology method to OWLOntologyManager #12
*    Search introduced to replace forSuperPosition, forSubPosition, ignoreAnnotations booleans
*    Imports.INCLUDED, Imports.EXCLUDED introduced instead of boolean arguments #156
*    introduced ChangeApplied to simplify code around applying changes and remove null warnings
*    Introduction of Searcher with transform functions
*    OWLOntology now exposes a new method for searching for axioms referring an entity, abstracting all various getAxiomsBy... methods.
*    Refactor OWLSignature for uniform imports closure use
*    Refactored OWLAxiomCollection for uniform use of import closure
*    Added targeted parsing to allow one and only one parser to be tried on a document input source. Takes into account MIME type as well.
*    Use MultiMap from Guava throughout V4.0.0 #153
*    OBO parser updated and replaced with oboformat 5.5 release
*    @Nonnull annotations and warnings
*    Centralised and uniformed sax parser factories
*    OWLOntologyID to use Optional #160
*    Refactored property hierarchy to remove generics.
*    Added an OWLParser implementation to enable actual use of DL parser
*    Added a pairwise visitor, changed storage strategy for nary axioms. The pairwise visitor interface is a functional interface that is applied to all distinct pairs in a collection of objects.
*    Added asPairwiseAxioms to all nary axiom types
*    Added HasPriority annotation and comparator for prioritizable objects
*    Added PriorityCollection for managing injected collections
*    Added ServiceLoader adapter for Guice injection
*    Added ManchesterOWLSyntaxParser interface to api
*    OWLAPITOOLS profiles and fixers included
*    Added Profiles enumeration and supported OWL profile for known reasoners.
*    Dependency injection enabled for fixers.
*    Added Guice module for creating OWLOntologyManager and OWLDataFactory
*    Introduced OWLOntologyBuilder to allow swapping OWLOntology implementations via Guice
*    Service loader module in api to be used independently of the owlapi-impl module
*    Add RDFa parser and use static META-INF/services files for owlapi-rio
*    Add JSON-LD support
*    Add rdf:langString from RDF 1.1  to OWL/RDF vocabulary.
*    Better BOM treatment through Apache BOMInputStream
*    Use SLFJ for logging
*    Guava 17
*    Guice 4.0-beta
*    Added jsr305 for Nonnull and Nullable annotations
*    Fix build and javadoc for Java 8 
*    Do not raise violations from entities used only in declarations.
*    Do not add declarations for illegal punnings when saving ontologies #112
*    Do not cache any literals in OWLDataFactoryInternalsImpl. Overall improvement of about 9% when parsing Gene Ontology in FSS.
*    Implement Default prefix manager in prefix ontology format objects cannot be overriden #9
*    Implement automatic retries when loading from URLs, managed through OWLOntologyLoaderConfiguration #66
*    Switched OWLDataFactory IllegalArgumentExceptions to NullPointerExceptions #131
*    Handle annotations on punned types when rendering RDF #183
*    Add OWLOntologyManagerFactory and code to allow use of RioOWLRDFParser through an injector
*    Convert RioParserImpl to stream statements through wherever possible
*    Default datatype in RDFLiteral to PLAIN_LITERAL.
*    Made generated classes package protected
*    Rename OWLObjectRenderer to FunctionalSyntaxObjectRenderer
*    OWLReasoner change lists generic
*    Moved OWLOntologyFormat implementation to formats package
*    Performance improvement for rdf/xml rendering
*    Optimised functional renderer and prefix manager
*    feature: OBO parser replaced with oboformat.
*    OWL2Datatype implements HasIRI
*    Add XSD IRIs simple parsing support #56
*    GZip read/write ability
*    Manchester OWL syntax cleanup

### Bug fixes:

*    Fix #278 AutoIRIMapper is not namespace aware
*    Direct imports result not updated correctly after manual load #277
*    fix #275 DL Syntax rendering of disjoint classes missing comma
*    fix #271 OWLOntology.getXXXInSignature(boolean) and similar methods
*    fix #270 Add OWLOntology.getReferencingAxioms(OWLPrimitive)
*    fix #268 Add documentation to OWLOntologyID to clarify the relationship between isAnonymous() and getOntologyIRI()
*    fix #267 Consider adding containsXInSignature methods that do not have an imports flag to OWLOntology.
*    fix #254 OWLAsymmetricObjectPropertyAxiom not rendered in DL Syntax
*    fix OWLDocumentFormat as interface #258 #259
*    fix #260 and fix #261 data and object cardinality are quantified restricitons
*    fix #255 PrefixOWLOntologyFormat is missing from the compatibility module
*    fix #253 StructuralReasoner.getSameIndividuals does not behave as advertised
*    Fixed #198 A SubClassOf B will not parse
*    Fixed IRI with a space: %20 escape #146
*    Old OWLEntityCollector brought forward as DeprecatedOWLEntityCollector
*    Fixed serialization warnings reported in #163
*    Use AtomicLong for concurrency support in gzip document sources
*    Moved transitive object property type to rbox
*    Set turtle default prefix slash aware #121 
*    Fixed #116 import statement erroneously names anonymous ontology
*    Added test for roundtripping annotated SWRL rules and datatype definitions
*    Fixed annotations on datatype definition axioms fixed in OWL/XML
*    Fixed #20 and #60 use zipped buffer for StreamDocumentBase
*    Fixed #71 Misleading message on resolving illegal IRI
*    Fixed variable rendering in Manchester OWL Syntax to preserve ns.
*    Fixed #46 Round tripping error in functional syntax
*    Fixed #40 RDFGraph.getSortedTriplesForSubject throws exception in Java7
*    Fixed infinite recursion on cyclic import structures when declaration does not match location.

## 3.4.10 18 January 2014

### Features:

*    Improved feature 32 implementation (simple SWRL variables rendering)
*    javadoc warnings and errors removed to allow building with Java 8
*    refactored OWLAxiomVisitorExAdapter for general use
*    performance improvement for rdf/xml
*    Renamed variables, added explicit flush. Fixes #67 slow functional sytnax renderer on Java 6
*    default, final and abstract classes changed to allow interface verification
*    add oraclejdk8 early access build to travis
*    update oboformat class names to sync with oboformat project
*    make OBOFormatException extend OWLParserException

### Bug fixes:

*    Fixes #72 Manchester syntax roundtrip of doubles and SWRL rules fails
*    Fixes #24 Manchester Syntax writer generates unparsable file
*    simple renderer writes duplicate annotations
*    Optimised functional renderer and prefix manager
*    Fix #63 null pointer exception loading an ontology and fix #64 empty ontology returned
*    Fixes #63 OBO Ontology Parsing throws NullPointer
*    updated OSGi settings for oboformat, module name, dependencies
*    fix bundle configuration for oboformat

## 3.4.9 25 November 2013

### Features:

*    Added default methods and return values for a few visitor adapters
*    #57 Reimplement NodeID so that it doesn't use an inner class
*    #56 parse things like xsd:string easily
*    OBO parser replaced with oboformat. Original code at: https://code.google.com/p/oboformat/

### Bug fixes:

*    Fixed wrong naming of gzip file source and target and added stream only versions.
*    Restore UnparseableOntologyException interface
*    Added back deprecated parseConstant for compatibility with Pellet CLI code. Warning: it has bugs.


## 3.4.8 02 November 2013

### Features:

*    GZip read/write ability. Compressed gzip input and output streams, but make sure to close the stream in the calling code.
*    OSGI dependency removed
*    Added Namespaces.inNamespace
*    Made ParserException to extend OWLParserException, and OWLParserException a runtime exception.
*    Tidying up Manchester OWL Syntax parser code
*    Added to the documentation on getOWLDatatype(OWLDataFactory)
*    Added a convenience method to convert an OWL2Datatype object to an OWLDatatype.
*    mvn install and mvn deploy now make aggregate sources available.
*    Added more well known namespaces, removed non-explanatory comments
*    Added RDFa Core Initial Context prefixes and other well known prefixes and prefix names.
*    GITHUB-10 : Add RDFa Core Initial Context prefixes
*    Extend Namespaces enum with common prefixes

### Bug fixes:

*    Fixes #40 Fixes #7 RDFGraph.getSortedTriplesForSubject throws exception in Java7 Refactored TripleComparator into RDFTriple and RDFNode as Comparable
*    DLQueryExample did not print the StringBuilder with the answers
*    Fixes #50 Null anonymous individual parsing ObjectHasValue from OWL/XML
*    Fixes SF 313 Man Syntax Parser gives facets unexpected datatype
*    Fixes SF 101 Manchester OWL Syntax Parser doesn't do precedence correctly
*    Fixes #43 Functional Syntax Parser does not support comments
*    Fixes #41 Parsing error of SWRL atom.
*    Fixes #46 Round tripping error in functional syntax
*    Fixes #37 setAddMissingTypes logging if disabled
*    Missed serialization transients and tests, serialization bug fixes
*    Patch IRI.create(String,String) to match IRI.create(String)

## 3.4.5 25 July 2013

### Features:

*    Refactored DefaultPrefixManager to expose addPrefixes
*    Updated pom files for OSGI compatible artifacts.
*    Allow override of DefaultPrefixManager in PrefixOWLOntologyFormat
*    Add CollectionFactory.getExpectedThreads
*    Reduce expected threads from 16 to 8 to improve general performance

### Bug fixes:

*    literal parsing error was stopping HermiT builds with 3.4.4.
*    errors in OSGI support were stopping Protege from using the feature.
*    Fix #370 Typo in website/htdocs/reasoners.html.
*    Fixed a bug with large integer values being parsed as negative ints.
*    Fix infinite recursion on cyclic import structures.
*    Reverted the use of ThreadLocal as this causes memory leaks in webapps.
*    Bug #343 SWRL roundtrip in manchester syntax

## 3.4.4 19 May 2013

Features and updates implemented:

*    feature 103 - Add methods to get EntityType names.
*    Updated IRI prefix caching to use thread local caches rather than synchronized caches.
*    added test for annotation retrieval for anonymous individuals.
*    WeakIndexCache vulnerability fixed.
*    Improved redirect following.
*    Added method to OWLOntologyLoaderConfiguration to disable cross protocol redirects on URLConnections.
*    Added parameter to OWLOntologyFormat for temp files, default disabled.
*    Update all JavaCharStream versions to fix the UTF BOM issue.
*    Travis Integration (on github).
*    Update KE interface with getBlocker method.
*    OSGI compatible build.
*    Removed IRI.toString() call when checking blank nodes.
*    Change logging of remaining triples to use less memory.
*    updates for javadoc.
*    Update Mockito to 1.9.5.

### Bug fixes:

*    Bug #369 IRI.create(String) uses / and # to look for namespace and fragments. 
*    Bug #348 ManchesterOWLSyntax round trip problem with disjoint classes.
*    Bug #269 single anonymous individual split in two on save to RDF.
*    Bug #319 redirects from http to https are not followed.
*    Bug #367 Serving ontologies from github as well.
*    Bug 4 (github) Missing method in OWLIndividual.
*    Bug 359 Incorrect Javadoc in OWL2DatatypeImpl and redundant javadoc.
*    Bug 360 - Axiom annotations are not parsed correctly.
*    Bug 364 Null pointer checking for containment of anonymous ontologies.
*    Bug #363 Turtle parser fails when loading valid Turtle document.
*    Bug 362 - owlapi files in temp folder.
*    Bug 357 duplicate axioms saving in OWL functional syntax.
*    Bug 355 - added assertions. Ability to parse 1+e7 as 10000000.
*    Bug 355 - Turtle Parser + Integer format Problem.
*    OntologyAlreadyExist bug when calls are made too fast.


## 3.4.3 26 Jan 2013

Features implemented:

*    Updated OWLFunctionalSyntaxFactory with more methods.
*    Removed deprecated method calls.
*    Test package broken up in smaller packages.
*    Web site updated and added to version management.

### Bug fixes:

*    Changed VersionInfo message default to provide more information.
*    Bug 354 fixed: There is AnnotationChange, but no AnnotationChangeData
*    Bug 353 fixed: SimpleRenderer renders SubDataPropertyOfAxiom wrong.
*    Bug 351 fixed: QNameShortFormProvider swapped prefix and iri.
*    Bug 352 fixed: SimpleRenderer does not escape double quotes in literals.


## 3.4.2 4 December 2012

Features implemented:

*    Repository migrated to Git.
*    Fixed memory leak with OWLObject signatures never being released once cached.
*    Improved Serializable implementation.
*    Made timeout property functional.
*    3585007 	Make OWLAxiomChange.isAdd().
*    3579488 	Clean documentation page.
*    3578004 	Javadoc OWLRDFVocabulary.
*    3578003 	Add OWLOntologyManager.removeOntology(OntologyID).
*    3578002 	Add new constructor for SetOntologyID.
*    3576182 	Bump minimum java version to 1.6.
*    3575834 	Improvements to OWLOntologyManager and related contrib.
*    3566810 	Lack of support in OWLOntologyManager for version IRIs.
*    3521809 	KRSS parser throws Error instead of RuntimeException.

### Bug fixes:

*    Some OWLLiterals cannot be optimised: -0.0^^xsd:float, 01^^xsd:int (optimisation with primitive types makes reasoners queasy in combination with W3C tests).
*    3590243 	hashCode for literals inconsistent.
*    3590084     misuse of XMLUtils.getNCNameSuffix().
*    3581575 	OWLOntologyDocumentAlreadyExistsException in OWL API 3.4.1.
*    3580114 	IRI isAbsolute method.
*    3579862 	OWLDataFactory.getRDFPlainLiteral creates new objects.
*    3579861 	rdf:PlainLiteral is not in the allowed OWL2EL datatypes.

## 3.4.1 16 September 2012

### Features:

*    3578004    OWLRDFVocabulary Javadoc
*    3575834    Improvements to OWLOntologyManager and related contrib

### Bug fixes:

*    3463200    rdf/functional round trip problem (anonymous individuals)
*    3341637    round trip problem with owl functional or rdf/xml syntax
*    3576182    Bump minimum java version to 1.6 (Fixed the bugs, moved the request to feature requests)
*    3497161    Manchester syntax imports
*    3403855    Imports of multiple ont docs containing the same ont fails
*    3491516    unhelpful Manchester parse exception
*    3536150    TypeOntologyHandler/TPVersionIRIHandler overwrite ontologyID
*    3314432    Manchester OWL parser does not handle data ranges in rules
*    3186250    annotation assertion lost when annotation prop undeclared
*    3309666    [Turtle] Parsing turtle files containing relative IRIs
*    3562296    Switch version 3.4.1 to 3.4.1-SNAPSHOT
*    3560287    Is QNameShortFormProvider deprecated?
*    3566820    Missing Imports are not reported
*    3306980    file in Manchester OWL Syntax with BOM doesn't parse
*    3559116    Wrong VersionInfo in OWL-API 3.4 release
*    2887890    Parsing hasValue restrictions with punned properties fails
*    3178902    RDF/XML round trip problem with 3-way equivalent classes
*    3174734    ManSyntax fails to read ontology with single data property
*    3440117    Turtle parser doesn't handle qnames with empty name part

## 3.4 12 August 2012

This version restructures the Maven modules into six modules: api, impl, parsers, 
tools, apibinding and contract. Code is unchanged, only its organization in 
the SVN is changed.

To split the dependencies accordingly, DefaultExplanationOrderer is in 
contract and is only a shell for ExplanationOrdererImpl, which does not 
depend on OWLManager directly. This enables the tools module to be 
used with a different apibinfing without recompilation.

### Bug fixes:

*    3554073 	Manchester Syntax Parser won't parse DisjointUnionOf
*    3552028 	DataFactory returns integer instead of double restrictions
*    3550607 	property cycle detection does not work
*    3545194 	OWLEquivalentObjectProperties as SubObjectPropertyOfAxioms
*    3541476 	OWLRDFConsumer.getErrorEntity counter is not thread safe
*    3541475 	Remove shared static in OWLOntologyXMLNamespaceManager
*    3535046 	Use ArrayList instead of TreeSet in TurtleRenderer
*    3532600 	Use AtomicInteger in OWLOntologyID for counter

### 3.3 15 May 2012

This version wraps together some minor bug fixes and some performance improvements for loading time and memory footprint for large ontologies.
Maven support has been much improved, thanks to Thomas Scharrenbach's efforts and Peter Ansell's feedback and suggestions.

### Features:

*    Performance improvements at loading time

### Bug fixes:

*    OBO Parser updated to be more compliant with the latest draft of the OBO syntax and semantics spec.
*    OBO Parser doesn't expand XREF values  (3515525)
*    Integrating with OpenRDF  (3512217)
*    maven should use snapshot versions during development  (3511755)
*    junit should be in maven test scope  (3511754)
*    mvn clean install requires gpg key  (3511732)
*    OWLOntologyManager.contains(IRI) bug (3497086)
*    Functional and Manchester syntax writers prefix problem  (3479677)
*    SyntacticLocalityModuleExtractor and SubAnnotationPropertyOf  (3477470)
*    Ignored Imports List uses the wrong IRIs  (3472712)
*    OWLLiteralImpl corrupts non-ascii unicode (3452932)
*    OWLOntologyXMLNamespaceManager QName problem  (3449316)
*    MultiImportsTestCase assumes incorrect working directory  (3448125)
*    Mismatch between HasKeyTestCase and corresponding resource  (3447280)
*    functional Renderer: OWLObjectRenderer swaps SWRL variables (3442060)
*    maven build fails because of dependency issue  (3440757)
*    manchester owl syntax doesn't handle rules with anon classes  (3421317)
*    RDFTurtleFormatter doesn't escape '\'  (3415108)
*    RDF consumer does not check for all annotated axiom triples  (3405822)
*    mis-parsing/mis-serialization of haskey (3403359)
*    Literal not a builtin  (3305113)
*    OWL API throws NullPointerException loading ontology  (3302982)

## 3.2.1 22 July 2011

This version of the API is released under both LGPL v3 and Apache license v2; developers are therefore free to choose which one to use.

### Features:

*    Tutorial code has been added following the OWLED 2011 tutorial (subpackage tutorialowled2011);
*    A visitor to determine whether a set of axioms is a Horn-SHIQ ontology has been added (HornAxiomVisitorEx.java).

### Bug fixes:

*    Some test files and extra modules had not had their copyright notices updated to the new license; this has now been fixed.

## 3.2.3 27 May 2011

This version of the API is released under both LGPL v3 and Apache license v2; developers are therefore free to choose which one to use.

### Bug fixes:

*    MAVEN support has been fixed (3296393).
*    Some minor problems with rdfs:Literal have been fixed (3305113).
*    Parsers and renderers for various languages have had some corner case errors fixed (3302982, 3300090, 3207844, 3235181, 3277496, 3294069, 3293620, 3235198).
*    An error in OWLOntologyManager.removeAxioms() which was throwing ConcurrentModificationExceptions when called on a specific axiom type has been fixed (3290632).
*    OWLOntologyLoaderConfiguration has been introduced to provide ontology wise parsing configuration (strict and lax parsing modes now enabled) (3203646) More options coming up.
*    Parsers no longer leave streams open when a parsing error is detected (3189947).


## 3.2.2 17 February 2011

### Bug fixes:

*    In RDF based serialisations, type triples for entities that don't have "defining axioms" don't get added even if the renderer is instructed to type undeclared entities (related to 3184131). Fixed.
*    The RDF parser would not recognise XSD datatypes that aren't OWL 2 Datatypes even in lax parsing mode (related to 3184131). Fixed.
*    OWLOntologyManager.createOntology() methods don't set the document IRI of the created ontologies as adverstised if there aren't any ontology IRI mappers installed (3184878). Fixed.


## 3.2.1 4 February 2011

### Bug fixes:

*    Issues with -INF as serialisation for -infinity for floating point literals. Fixed.
*    Null pointer exception can be thrown by OBO parser (3162800). Fixed.
*    OWLOntologyManager.loadOntology() can throw unchecked exceptions (IllegalArgumentException) (3158293). Fixed.
*    OWLOntologyAlreadyExistsException get wrapped as as a parse exception by the OBO parser. (3165446). Fixed.
*    OWLRDFConsumer (OWL RDFParser) used owl:Datatype instead of rdfs:Datatype. Fixed.
*    OWLOntology.getDisjointUnionAxioms() does not work as expected. (3165000). Fixed.
*    Anonymous ontologies do not get typed as owl:Ontology during saving in RDF. (3158177). Fixed.
*    OWLOntology hashCode is not implemented properly. (3165583). Fixed.

## 3.2.0 14 January 2011

This version of the API includes various bug fixes and performance enhancements since version 3.1.0

### Bug fixes:

*    Various round tripping problems for various syntaxes (3155509, 3154524, 3149789, 3141366, 3140693, 3137303, 3121903)
*    Plain Literals are rendered incorrectly. Fixed.
*    Cyclic imports cause an OntologyAlreadyExistsException to be thrown. Fixed.
*    OWLAxiom.getAnnotatedAxiom() does not merge annotations.  Fixed.
*    OWLObject.getSignature() returns an unmodifiable collection. Fixed.
*    Various problems with character encodings (3068076, 3077637, 3096546, 3140693). Fixed.
*    IRI.create resulted in a memory leak.  Fixed.
*    Imports by location does not work. Fixed.
*    Various problems with anonymous individuals (2998616, 2943908, 3073742). Fixed.
*    Dublin Core Vocabulary is built into OWL API parsers.  Fixed.
*    Files are left open on parse errors.  Fixed.

## 3.1.0 20 August 2010

This version of the API includes various bug fixes and enhancements since version 3.0.0.

### Features:

Changes to the representation of literals:

Please note that there is a slight incompatibility between version 3.1.0 and version 3.0.0.  This is due to some changes
in the way that literals are represented and handled in the API.  There was a disparity between how they are described
in the OWL 2 specification and how they are represented in version 3.0.0 of the API.  The changes bring 3.1.0 inline
with the OWL 2 specification.  The changes are as follows: OWLStringLiteral and OWLTypedLiteral have been removed and
replaced OWLLiteral.  In version 3.0.0 OWLLiteral was a super-interface of OWLStringLiteral and OWLTypedLiteral.
Clients therefore need to replace occurrences of OWLStringLiteral and OWLTypedLiteral with OWLLiteral.  Method calls
need not be changed - methods on OWLStringLiteral and OWLTypedLiteral have corresponding methods on OWLLiteral.  Note
that all literals are now typed.  What used to be regarded as OWLStringLiterals are now OWLLiterals with the datatype
rdf:PlainLiteral.  Although this change introduces a slight backward incompatibility with the previous version of the
API we believe that the handling of literals in 3.1.0 is much cleaner and follows the specification more closely.

Changes to the OWLReasoner interface:

The OWLReasoner interface has been updated.  There are now methods which allow for fine-grained control over reasoning
tasks.  For example, it is now possible to request that a reasoner just classifies the class hierarchy, when in 
version 3.0.0 the prepare reasoner method caused realisation to occur as well.  The prepareReasoner method has been removed from
the interface due to the huge amount of confusion it caused (it was not necessary to call this method to get correct
results, but the name suggested that it was necessary).  Clients should update their code to replace any calls to
prepareReasoner with appropriate calls to precomputeInferences.

JavaDoc for various methods has been cleaned up.

### Bug fixes:

*    Annotations on Declaration axioms were not save.  Fixed.
*    OWLObjectMaxCardinality restrictions incorrectly resulted in a profile violation of the OWL2RL profile. Fixed.
*    OWLOntology.containsAxiom() failed various round trip tests. Fixed.
*    OWLObjectPropertyExpression.getInverses() did not include properties that were asserted to be inverses of themselves. Fixed.
*    Writing large rdf:Lists can cause a stack overflow on saving. Fixed.
*    System.out is closed when using SystemOutDocumentTarget. Fixed.
*    An OWLOntologyAlreadyExists exception is thrown when an imports graph contains multiple imports of the same ontology. Fixed.
*    OWLReasoner.getSubclasses() is missing declarations in the throws list for runtime exceptions that get thrown. Fixed.
*    Parsing RDF graphs containing blank nodes with NodeIDs is broken.  Fixed.
*    Some methods on OWLOntology return sets of object that change with ontology changes. Fixed.
*    Double dashes (--) are not escaped in XML comments output by the XMLWriter. Fixed.
*    The API sometimes prints to System.err.  Fixed.
*    The functional syntax writer writes out annotation assertions twice. Fixed.
*    Ontologies with DataSomeValuesFrom restrictions are incorrectly considered non-OWL2QL. Fixed.
*    Ontologies with Functional data properties are incorrectly considered non-OWL2RL. Fixed.
*    OWL2Datatype.XSD_NAME has the wrong regex pattern. Fixed.
*    RDF rendering of GCIs with multiple elements is broken. Fixed.
*    SubObjectPropertyOf axioms with annotations and property chains don't get parsed correctly from RDF.  Fixed.
*    StructuralReasoner sometimes crashes when determining superclasses. Fixed.
*    SimpleRenderer renders declarations incorrectly.  Fixed.
*    Object property characteristics are not answered correctly.  Fixed.
*    OWLOntology.getReferencingAxioms gives mutable and incorrect results.  Fixed.
*    StructuralReasoner call OWLClassExpression.asOWLClass() on anonymous classes. Fixed.
*    SKOSVocabulary is not up to date.  Fixed.
*    Regular expression for xsd:double has extra white space. Fixed.
*    Structural reasoner sometimes misses subclasses of owl:Thing. Fixed.
*    OWLOntologyManager.getImportsClosure() fails on reload. Fixed.
*    PrefixOWLOntologyFormat has not methods to remove prefixes.  Fixed.
*    RDF/XML rendering of unicode characters is ugly. Fixed.
*    Turtle parser does parse shared blank nodes correctly. Fixed.

## 3.0.0 28 January 2010

Version 3.0.0 is incompatible with previous releases.  Many interface names have been changed in order to acheive a
close alignment with the names used in the OWL 2 Structural Specification and Functional Style Syntax.
(See http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/)

The opportunity to clean up method names was also taken.


## 2.2.0  17 April 2008

### Features:

*    Added support for building using ANT
*    OWL 1.1 namespaces changed to OWL 2.  Old ontologies that are written using the owl11 namespace will still load, but will be converted to use the owl2 namespace.
*    Updated the RDF parser and RDF rendere to support AllDisjointClasses and AllDisjointProperties
*    Added the ability to save ontologies in Turtle.
*    Added the ability to load ontologies that are written in Turtle
*    Added explanation code contributed by Clark & Parsia
*    Added a KRSS renderer (contributed by Olaf Noppens)
*    Added a new, more comprehensive KRSS parser (contributed by Olaf Noppens).  This parser can parser the version of the KRSS syntax that is used by Racer.
*    Added the ability to specify a connection timeout for URL connections via a system property (owlapi.connectionTimeOut) - the default value for the timeout is 20 seconds.
*    Added a method to OWLOntologyManager to clear all registered URI mappers
*    Added a method to OWLOntologyManager so that imports can be obtained by an imports declaration.
*    Added a convenience method to OWLOntologyManager to add a set of axioms to an ontology without having to create the AddAxiom changes
*    Added a makeLoadImportsRequest method on OWLOntologyManager which should be used by parsers and other loaders in order to load imports
*    Added the ability to set an option for silent missing imports handling on OWLOntologyManager.  When this option is set,  exceptions are not thrown when imports cannot be found or cannot be loaded.  It is possible to set a listeners that gets informed when an import cannot be found, so that the exception doesn't get lost entirely.
*    Added the ability to add a ontology loader listener to OWLOntologyManager.  The listener gets informed when the loading process for an ontology starts and finishes (which ontology is being loaded, from where and whether it was successfully loaded etc.).
*    Added a method to OWLReasonerFactory to obtain the human readable name of the reasoner that a factory creates.
*    Added a convenience method to OWLOntology to obtain all referenced entities
*    Added convenience methods to OWLEntity that check whether the entity is an OWLClass, OWLObjectProperty, OWLDataProperty, OWLIndividual or OWLDatatype.  Also added asXXX to obtain an entity in its more specific form.
*    Added convenience methods to OWLDataFactory for creating disjoint class axioms and equivalent classes axioms.
*    Added a general purpose renderer interface for OWLObjects
*    Added an OWLInconsistentOntologyException to the inference module.
*    Added SKOS core to the list of well known namespaces
*    Added a SKOS vocabulary enum
*    Added methods to the OWLOntologyManager interface, so that ontologies can be saved to an output target as well as a URI.  Added implementations of OWLOntologyOutputTarget to enable writing directly to OutputStreams and Writers.
*    Added a StringOutputTarget for writing ontologies into a buffer that can be obtained as a string.
*    Added some new input sources:  StreamInputSource, ReaderInputSource, FileInputSource
*    RDF Parser. Made the classExpression translator selector more intelligent so that when properties aren't typed as either object or data properties, other triples are examined to make the appropriate choice.
*    OWLRestrictedDataRangeFacetVocabulary.  Added methods to obtain facets by their symbolic name (e.g. >=)
*    BidirectionalShortFormProvider.  Added a method to obtain all short forms cached by the provider.
*    Added an option to turn tabbing on/off when rendering Manchester Syntax
*    Added more documentation for the method which adds ontology URI mappers
*    Improved error handling when loading ontologies: For errors that have nothing to do with parse errors e.g. unknown host exceptions, the factory will rethrow the error at the earliest opportunity rather than trying all parsers.
*    Updated parser to throw ManchesterOWLSyntaxOntologyParserException which is a more specific type of OWLParserException
*    Updated the BidirectionalShortFormProviderAdapter with functionality to track ontology changes and update the rendering cache depending on whether entities are referenced or not.
*    Added a latex renderer for rendering ontology axioms in a latex format
*    Added the ability to parse ontologies written in ManchesterOWLSyntax
*    Added URIShortFormProvider as a general purpose interface for providing short forms for URIs. Changed SimpleShortFormProvider to use the SimpleURIShortFormProvider as a base
*    Made the toString rendering of the default implementation pluggable via the ToStringRenderer singleton class.
*    Added some convenience methods to the OWLDataFactory to make creating certain types of objects less tedious.  Specifically: ObjectIntersectionOf, ObjectUnionOf, ObjectOneOf and DataOneOf can now be created using methods that take a variable number of arguments (OWLDescriptions, OWLIndividuals or OWLConstants as appropriate).  Also, added convenience methods that create typed literals directly from Java Strings, ints, doubles, floats and booleans.  For example, createOWLTypedConstant(3) will create a typed literal with a lexical value of "3" and a datatype of xsd:integer.  Added convenice methods for creating entity annotations without manually having to create OWLAnnotation objects.
*    Added a getAxiomType method on to the OWLAxiom interface for convenience.
*    Added functionality to the debugging module for ordering explanations
*    Added generics to the inferred axiom generator API
*    Added a new constructor to OWLOntologyNamespaceManager so that it is possible to override the ontology format that is used as a hint when generating namespaces.
*    Added a dlsyntax renderer module that can renderer axioms etc. in the traditional dlsyntax using unicode for the dlsyntax symbols.
*    Modified the RDFXMLNamespaceManager to select the minimal amount of entities for which namespaces need to be generated.  Namespaces are only generated for classes in OWLClassAssertionAxioms, and properties in OWLObjectPropertyAssertionAxioms and OWLDataPropertyAssertionAxioms.  This basically corresponds to the places where valid QNames are needed for entities.
*    Added code to add declarations for "dangling entities".  If an RDF graph contains  <ClsA> <rdfs:type> <owl:Class> and ClsA has not been referenced by any other axioms then this would have been dropped by the parser - this has been changed so that declaration axioms are added to the ontology in such cases.  (Hopefully, the OWL 1.1 spec will be updated to do something like this in the mapping to RDF graphs).
*    Added a utility class, AxiomSubjectProvider, which given an axiom returns an object which is regarded to be the "subject" of the axioms.  For example given SubClassOf(ClsA ClsB), ClsA is regarded as being the subject.
*    Modified the ontology URI short form provider to provide nicer looking short forms.
*    Added a convenience method to get the individuals that have been asserted to be an instance of an OWLClass.
*    Commons lang is no longer used in the API because it had been replaced with a lightweight utility class in order to escape strings.
*    Removed the fragments module and replaced it with the profiles module.  The EL++ profile is currently implemented.
*    Added support for extended visitors that can return objects in the visit method.
*    Turned off logging in the RDF parser classes by default.

### Bug fixes:

*    The getOntologyURIs method on AutoURIMapper would return physical rather than logic URIs. Fixed.
*    Namespaces for annotation URIs weren't generated. Fixed.
*    Removing a subclass axiom from an ontology cause the axiom to be added to the ontology as a GCI. Fixed.
*    When parsing an ontology, the accept types has been set to include RDF/XML.  This means that ontologies can be parsed correctly from servers that are configured to return RDF or HTML depending on the request type.
*    OWL/XML writer has been modified to write the datatype URI attribute name correctly.  Previously the name was written as "Datatype", however it should be "datatypeURI".
*    OWL/XML parser. Modified the literal handler to parse literals using the correct datatype URI attribute name (was "Datatype" and should have been "datatypeURI").
*    The constructor that required a manager in BidirectionalShortFormProviderAdapter did not rebuild the cache. Fixed.
*    Unqualified cardinality restrictions were rendered out as qualified cardinality restrictions. Fixed.
*    Saving an ontology would fail if the necessary directories did not exist. Fixed.
*    Rendering anonymous property inverses in OWL/XML was incorrect. Fixed.
*    Label and Comment annotations in the functional syntax weren't parsed properly, they were parsed as regular annotations. Fixed.
*    In the OWLXMLParserHandler, no handler for negative data property assertions was registered. Fixed.
*    Annotations that have anonymous individuals as values weren't rendered correctly. Fixed.
*    RDFXMLOntologyStorer and RDFXMLRenderer always used the ontology format that is obtainable from the manager, regardless of whether or not a custom ontology format was specified - fixed.
*    Rules that contained individual or data value objects couldn't be rendered. Fixed.
*    Declaration axioms were automatically added for data properties whether an ontology contained declaredAs triples or not. Fixed.
*    Anonymous properties weren't rendered correcty. Fixed.
*    RDF rendering for sub property axioms whose sub property is a property chain used an old rendering.  The rendering now complies with the latest OWL 2 specification.  Ontologies that use the old rendering can still be parsed.
*    RDF lists were reordered on rendering. Fixed.