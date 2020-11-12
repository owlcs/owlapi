### Command line used: 
    java -jar ~/Downloads/japicmp-0.14.4-jar-with-dependencies.jar -b  --ignore-missing-classes -o v1/owlapi-distribution-v1.jar -n v2/owlapi-distribution-v2.jar

## Ignored changes:

 - OWLObject is serializable, and therefore most of the model classes are, but their serialVersionUID are not fixed, therefore japicmp reports many of them changing between versions.
 - Serializability implementation for OWLAPI is meant to support short term serialization (e.g., allow transmission over network connections for editors such as WebProtege) where both sides use the same OWLAPI version, and the objects are not guaranteed to be deserializable across different versions. Those warnings are removed here to reduce the size of this page.
 - Also, changes in generated code, e.g., parsers for many languages created with JavaCC from .javacc files, are ignored s not relevant

### 5.0.1/owlapi-distribution-5.0.1.jar against 5.0.0/owlapi-distribution-5.0.0.jar

 Semantic version jump suggested: 1.0.0
 * Api:
    * MODIFIED ENUM: `PUBLIC FINAL org.semanticweb.owlapi.model.parameters.ConfigurationOptions  (compatible)`
       * REMOVED METHOD: `PUBLIC(-) Object getValue(Class, EnumMap)`

### 5.0.2/owlapi-distribution-5.0.2.jar against 5.0.1/owlapi-distribution-5.0.1.jar

 Semantic version jump suggested: 1.0.0
 * Api:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.axiomproviders.DisjointAxiomProvider  (not serializable)`
     * NEW METHOD: `PUBLIC(+) OWLDisjointClassesAxiom getOWLDisjointClassesAxiom(OWLClassExpression, OWLClassExpression, Collection)`
     * NEW METHOD: `PUBLIC(+) OWLDisjointDataPropertiesAxiom getOWLDisjointDataPropertiesAxiom(OWLDataPropertyExpression, OWLDataPropertyExpression, Collection)`
     * NEW METHOD: `PUBLIC(+) OWLDisjointObjectPropertiesAxiom getOWLDisjointObjectPropertiesAxiom(OWLObjectPropertyExpression, OWLObjectPropertyExpression, Collection)`
         * default methods
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLDataFactory`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSComment(String)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSComment(String, stream.Stream)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSComment(OWLAnnotationValue)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSComment(OWLAnnotationValue, stream.Stream)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSLabel(String)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSLabel(String, stream.Stream)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSLabel(OWLAnnotationValue)`
     * NEW METHOD: `PUBLIC(+) OWLAnnotation getRDFSLabel(OWLAnnotationValue, stream.Stream)`
         * default methods
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.providers.IndividualAssertionProvider`
     * NEW METHOD: `PUBLIC(+) OWLDifferentIndividualsAxiom getOWLDifferentIndividualsAxiom(OWLIndividual, OWLIndividual, Collection)`
     * NEW METHOD: `PUBLIC(+) OWLSameIndividualAxiom getOWLSameIndividualAxiom(OWLIndividual, OWLIndividual, Collection)`
         * default methods
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapitools.builders.BuilderEntity  (not serializable)`
     * REMOVED CONSTRUCTOR: `PUBLIC(-) BuilderEntity(OWLClass, OWLDataFactory)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapitools.builders.BuilderSWRLDifferentIndividualsAtom  (not serializable)`
     * MODIFIED SUPERCLASS: org.semanticweb.owlapitools.builders.BuilderSWRLIndividualsAtom (<- org.semanticweb.owlapitools.builders.BaseBuilder)
     * REMOVED METHOD: `PUBLIC(-) org.semanticweb.owlapitools.builders.BuilderSWRLDifferentIndividualsAtom withArg0(SWRLIArgument)`
     * REMOVED METHOD: `PUBLIC(-) org.semanticweb.owlapitools.builders.BuilderSWRLDifferentIndividualsAtom withArg1(SWRLIArgument)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapitools.builders.BuilderSWRLObjectPropertyAtom  (not serializable)`
     * REMOVED INTERFACE: org.semanticweb.owlapi.model.HasProperty
     * REMOVED INTERFACE: org.semanticweb.owlapitools.builders.SettableProperty
     * MODIFIED SUPERCLASS: org.semanticweb.owlapitools.builders.BuilderSWRLIndividualsAtom (<- org.semanticweb.owlapitools.builders.BaseObjectPropertyBuilder)
     * REMOVED METHOD: `PUBLIC(-) org.semanticweb.owlapitools.builders.BuilderSWRLObjectPropertyAtom withArg0(SWRLIArgument)`
     * REMOVED METHOD: `PUBLIC(-) org.semanticweb.owlapitools.builders.BuilderSWRLObjectPropertyAtom withArg1(SWRLIArgument)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapitools.builders.BuilderSWRLSameIndividualAtom  (not serializable)`
     * MODIFIED SUPERCLASS: `org.semanticweb.owlapitools.builders.BuilderSWRLIndividualsAtom (<- org.semanticweb.owlapitools.builders.BaseBuilder)`
     * REMOVED METHOD: `PUBLIC(-) org.semanticweb.owlapitools.builders.BuilderSWRLSameIndividualAtom withArg0(SWRLIArgument)`
     * REMOVED METHOD: `PUBLIC(-) org.semanticweb.owlapitools.builders.BuilderSWRLSameIndividualAtom withArg1(SWRLIArgument)`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl  (not serializable)`
     * MODIFIED METHOD: `PROTECTED OWLAxiom (<-OWLPropertyAssertionAxiom) parseFact(OWLIndividual)`
         * b2df2dcbb48b4af8e6842eef65baf3d3b1f4f345
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase  (not serializable)`
     * REMOVED CONSTRUCTOR: `PROTECTED(-) RDFRendererBase(OWLOntology, OWLDocumentFormat)`
 * ImplL
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl`
     * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataFactoryImpl(uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl`
     * REMOVED METHOD: `PROTECTED(-) FINAL(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl$BuildableWeakIndexCache buildCache()`
     * REMOVED ENUM: `PROTECTED(-) ABSTRACT(-) STATIC(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl$Buildable  (class removed)`
     * REMOVED CLASS: `PROTECTED(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl$BuildableWeakIndexCache  (class removed)`

### 5.0.3/owlapi-distribution-5.0.3.jar against 5.0.2/owlapi-distribution-5.0.2.jar

 Semantic version jump suggested: 1.0.0
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl  (not serializable)`
     * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) org.semanticweb.owlapi.util.DefaultPrefixManager pm`
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator  (not serializable)`
     * REMOVED METHOD: `PROTECTED(-) void adddisjointIfIntersectionNotSatisfiable(OWLClass, org.semanticweb.owlapi.reasoner.OWLReasoner, OWLDataFactory, Set, OWLClass)`

### 5.0.4/owlapi-distribution-5.0.4.jar against 5.0.3/owlapi-distribution-5.0.3.jar

 Semantic version jump suggested: 0.0.1
 - No changes.

### 5.0.5/owlapi-distribution-5.0.5.jar against 5.0.4/owlapi-distribution-5.0.4.jar

 Semantic version jump suggested: 1.0.0
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.obo2owl.OWLAPIOwl2Obo  (not serializable)`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) Set apToDeclare`
        * REMOVED FIELD: `PROTECTED(-) OWLDataFactory fac`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) Set untranslatableAxioms`
        * REMOVED FIELD: `PROTECTED(-) FINAL(-) regex.Pattern absoulteURLPattern`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) Map idSpaceMap`
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.model.Frame  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) FINAL(-) void init()`
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.model.OBODoc  (not serializable)`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) Collection importedOBODocs`
 * Api:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLAxiom`
        * NEW METHOD: `PUBLIC(+) boolean isAnonymous()`
        * NEW METHOD: `PUBLIC(+) boolean isAxiom()`
        * NEW METHOD: `PUBLIC(+) boolean isIndividual()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLIndividual`
        * NEW METHOD: `PUBLIC(+) boolean isIndividual()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLObject`
        * NEW METHOD: `PUBLIC(+) boolean hasSharedStructure()`
        * NEW METHOD: `PUBLIC(+) boolean isAnonymousExpression()`
        * NEW METHOD: `PUBLIC(+) boolean isAxiom()`
        * NEW METHOD: `PUBLIC(+) boolean isIndividual()`
        * NEW METHOD: `PUBLIC(+) boolean isOntology()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLOntology`
        * NEW METHOD: `PUBLIC(+) OWLDocumentFormat getNonnullFormat()`
        * NEW METHOD: `PUBLIC(+) boolean isAxiom()`
        * NEW METHOD: `PUBLIC(+) boolean isIndividual()`
        * NEW METHOD: `PUBLIC(+) boolean isOntology()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLOntologyManager`
        * NEW METHOD: `PUBLIC(+) OWLDocumentFormat getNonnullOntologyFormat(OWLOntology)`
     * MODIFIED ENUM: `PUBLIC FINAL org.semanticweb.owlapi.vocab.OWLFacet  (type of field has changed)`
        * MODIFIED FIELD: `PUBLIC STATIC FINAL Map (<- Set) FACET_IRIS`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) ABSTRACT(-) java.io.Serializable getAnonymousNodeForExpressions(Object)`
        * MODIFIED METHOD: `PUBLIC Object (<-java.io.Serializable) getMappedNode(OWLObject)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance)`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResource getAnonymousNodeForExpressions(Object)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResourceBlankNode getBlankNodeFor(Object, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter  (not serializable)`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) Collection ontologies`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.OWLObjectDuplicator  (not serializable)`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) org.semanticweb.owlapi.util.RemappingIndividualProvider anonProvider`
     * MODIFIED CLASS: `PUBLIC FINAL org.semanticweb.owlapi.util.SAXParsers  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) void addExpansionLimit(javax.xml.parsers.SAXParser)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) boolean addOracleExpansionLimit(javax.xml.parsers.SAXParser)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) javax.xml.parsers.SAXParser initParserWithOWLAPIStandards(org.xml.sax.ext.DeclHandler)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl  (type of field has changed)`
        * MODIFIED FIELD: `PROTECTED FINAL Map (<- Set) importedIRIs`

### 5.1.0/owlapi-distribution-5.1.0.jar against 5.0.5/owlapi-distribution-5.0.5.jar

 Semantic version jump suggested: 1.0.0
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC (<- PACKAGE_PROTECTED) org.coode.owlapi.obo12.parser.OBOParser  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OBOParser(java.io.Reader)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OBOParser(java.io.InputStream, String)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OBOParser(java.io.InputStream)`
        * REMOVED METHOD: `PUBLIC(-) void ReInit(java.io.InputStream)`
        * REMOVED METHOD: `PUBLIC(-) void ReInit(java.io.InputStream, String)`
        * REMOVED METHOD: `PUBLIC(-) void ReInit(java.io.Reader)`
     * MODIFIED CLASS: `PUBLIC (<- PACKAGE_PROTECTED) org.coode.owlapi.obo12.parser.ParseException  (superclass modified)`
        * MODIFIED SUPERCLASS: `org.semanticweb.owlapi.io.OWLParserException (<- OWLRuntimeException)`
        * REMOVED FIELD: `PROTECTED(-) String eol`
 * Apibinding:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.apibinding.OWLManager`
        * MODIFIED METHOD: `PUBLIC (<- PRIVATE) STATIC Object (<-com.google.inject.Injector) createInjector(uk.ac.manchester.cs.owl.owlapi.concurrent.Concurrency)`
 * Rio:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.AbstractRioRDFDocumentFormatFactory`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) AbstractRioRDFDocumentFormatFactory(org.openrdf.rio.RDFFormat, boolean)`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) AbstractRioRDFDocumentFormatFactory(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.RioRDFDocumentFormat`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.formats.RioRDFNonPrefixDocumentFormat`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRDFNonPrefixDocumentFormat(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.formats.RioRDFPrefixDocumentFormat`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRDFPrefixDocumentFormat(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rio.RioAbstractParserFactory  (not serializable)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFParser (<-org.openrdf.rio.RDFParser) getParser()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioMemoryTripleSource  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioMemoryTripleSource(info.aduna.iteration.CloseableIteration, Map)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioMemoryTripleSource(info.aduna.iteration.CloseableIteration)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioOWLRDFConsumerAdapter  (not serializable)`
        * REMOVED METHOD: `PUBLIC(-) void handleStatement(org.openrdf.model.Statement)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioOWLRDFParser  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioOWLRDFParser(org.semanticweb.owlapi.rio.OWLAPIRDFFormat, org.openrdf.model.ValueFactory)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioParserImpl`
        * REMOVED METHOD: `PROTECTED(-) void parseDocumentSource(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, String, org.openrdf.rio.RDFHandler, OWLOntologyLoaderConfiguration)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioRenderer  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRenderer(OWLOntology, org.openrdf.rio.RDFHandler, OWLDocumentFormat, org.openrdf.model.Resource[])`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioStorer`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioStorer(OWLDocumentFormatFactory, org.openrdf.model.Resource[])`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioStorer(OWLDocumentFormatFactory, org.openrdf.rio.RDFHandler, org.openrdf.model.Resource[])`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) org.openrdf.rio.RDFHandler getRDFHandlerForOutputStream(org.openrdf.rio.RDFFormat, java.io.OutputStream)`
        * REMOVED METHOD: `PROTECTED(-) org.openrdf.rio.RDFHandler getRDFHandlerForWriter(org.openrdf.rio.RDFFormat, java.io.Writer)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFHandler (<-org.openrdf.rio.RDFHandler) getRioHandler()`
        * REMOVED METHOD: `PUBLIC(-) void setRioHandler(org.openrdf.rio.RDFHandler)`
     * MODIFIED CLASS: `PUBLIC FINAL org.semanticweb.owlapi.rio.utils.RioUtils  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) org.openrdf.model.Value literal(org.openrdf.model.ValueFactory, org.semanticweb.owlapi.io.RDFLiteral)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) org.openrdf.model.BNode node(org.semanticweb.owlapi.io.RDFNode, org.openrdf.model.ValueFactory)`
        * MODIFIED METHOD: `PUBLIC STATIC org.eclipse.rdf4j.model.Statement (<-org.openrdf.model.Statement) tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) Collection tripleAsStatements(org.semanticweb.owlapi.io.RDFTriple, org.openrdf.model.Resource[])`
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFResourceBlankNode`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(boolean, boolean)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(IRI, boolean, boolean)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(int, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFTriple`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTriple(IRI, boolean, IRI, IRI, boolean)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTriple(IRI, boolean, IRI, OWLLiteral)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT HasApplyChanges  (not serializable)`
        * MODIFIED METHOD: `PUBLIC NON_ABSTRACT (<- ABSTRACT) org.semanticweb.owlapi.model.parameters.ChangeApplied applyChanges(List)`
        * NEW METHOD: `PUBLIC(+) ChangeDetails applyChangesAndGetDetails(org.semanticweb.owlapi.model.OWLOntologyChange[])`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.reasoner.impl.DefaultNode  (not serializable)`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLObjectProperty BOTTOM_OBJECT_PROPERTY`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNode BOTTOM_DATA_NODE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLDatatype TOP_DATATYPE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.reasoner.impl.OWLClassNode BOTTOM_NODE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLDataProperty TOP_DATA_PROPERTY`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode BOTTOM_OBJECT_NODE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNode TOP_DATA_NODE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode TOP_OBJECT_NODE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLDataProperty BOTTOM_DATA_PROPERTY`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLClass TOP_CLASS`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.reasoner.impl.OWLClassNode TOP_NODE`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLClass BOTTOM_CLASS`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) OWLObjectProperty TOP_OBJECT_PROPERTY`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.reasoner.OWLReasoner  (not serializable)`
        * NEW METHOD: `PUBLIC(+) stream.Stream bottomClassNode()`
        * NEW METHOD: `PUBLIC(+) stream.Stream bottomDataPropertyNode()`
        * NEW METHOD: `PUBLIC(+) stream.Stream bottomObjectPropertyNode()`
        * NEW METHOD: `PUBLIC(+) stream.Stream dataPropertyDomains(OWLDataProperty, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream dataPropertyDomains(OWLDataProperty, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream dataPropertyDomains(OWLDataProperty)`
        * NEW METHOD: `PUBLIC(+) stream.Stream dataPropertyValues(OWLNamedIndividual, OWLDataProperty)`
        * NEW METHOD: `PUBLIC(+) stream.Stream differentIndividuals(OWLNamedIndividual)`
        * NEW METHOD: `PUBLIC(+) stream.Stream disjointClasses(OWLClassExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream disjointDataProperties(OWLDataProperty)`
        * NEW METHOD: `PUBLIC(+) stream.Stream disjointObjectProperties(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream equivalentClasses(OWLClassExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream equivalentDataProperties(OWLDataProperty)`
        * NEW METHOD: `PUBLIC(+) stream.Stream equivalentObjectProperties(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream instances(OWLClassExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream instances(OWLClassExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream instances(OWLClassExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream inverseObjectProperties(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyDomains(OWLObjectPropertyExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyDomains(OWLObjectPropertyExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyDomains(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyRanges(OWLObjectPropertyExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyRanges(OWLObjectPropertyExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyRanges(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream objectPropertyValues(OWLNamedIndividual, OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream pendingAxiomAdditions()`
        * NEW METHOD: `PUBLIC(+) stream.Stream pendingAxiomRemovals()`
        * NEW METHOD: `PUBLIC(+) stream.Stream pendingChanges()`
        * NEW METHOD: `PUBLIC(+) stream.Stream precomputableInferenceTypes()`
        * NEW METHOD: `PUBLIC(+) stream.Stream sameIndividuals(OWLNamedIndividual)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subClasses(OWLClassExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subClasses(OWLClassExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subClasses(OWLClassExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subDataProperties(OWLDataProperty, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subDataProperties(OWLDataProperty, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subDataProperties(OWLDataProperty)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subObjectProperties(OWLObjectPropertyExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subObjectProperties(OWLObjectPropertyExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream subObjectProperties(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superClasses(OWLClassExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superClasses(OWLClassExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superClasses(OWLClassExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superDataProperties(OWLDataProperty, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superDataProperties(OWLDataProperty, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superDataProperties(OWLDataProperty)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superObjectProperties(OWLObjectPropertyExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superObjectProperties(OWLObjectPropertyExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream superObjectProperties(OWLObjectPropertyExpression)`
        * NEW METHOD: `PUBLIC(+) stream.Stream topClassNode()`
        * NEW METHOD: `PUBLIC(+) stream.Stream topDataPropertyNode()`
        * NEW METHOD: `PUBLIC(+) stream.Stream topObjectPropertyNode()`
        * NEW METHOD: `PUBLIC(+) stream.Stream types(OWLNamedIndividual, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream types(OWLNamedIndividual, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream types(OWLNamedIndividual)`
        * NEW METHOD: `PUBLIC(+) stream.Stream unsatisfiableClasses()`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResourceBlankNode getBlankNodeFor(Object, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase  (not serializable)`
        * REMOVED METHOD: `PUBLIC(-) ABSTRACT(-) void render(org.semanticweb.owlapi.io.RDFResource)`
     * MODIFIED CLASS: `PUBLIC (<- PACKAGE_PROTECTED) org.semanticweb.owlapi.rdf.turtle.parser.TurtleParser  (not serializable)`
        * MODIFIED METHOD: `PUBLIC (<- PACKAGE_PROTECTED) FINAL (<- NON_FINAL) org.semanticweb.owlapi.rdf.turtle.parser.Token getNextToken()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.OWLObjectDuplicator  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) OWLObject get(OWLObject)`
     * REMOVED CLASS: `PUBLIC(-) org.semanticweb.owlapi.rio.utils.OWLAPISimpleSAXParser  (not serializable)`

### 5.1.1/owlapi-distribution-5.1.1.jar against 5.1.0/owlapi-distribution-5.1.0.jar

 Semantic version jump suggested: 0.1.0

### 5.1.2/owlapi-distribution-5.1.2.jar against 5.1.1/owlapi-distribution-5.1.1.jar

 Semantic version jump suggested: 1.0.0
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.coode.owlapi.obo12.parser.OBO12ParserFactory`
        * REMOVED METHOD: `PUBLIC(-) List getMIMETypes()`
        * REMOVED METHOD: `PUBLIC(-) boolean handlesMimeType(String)`
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.DocumentSources  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.net.URLConnection connect(OWLOntologyLoaderConfiguration, String, java.net.URLConnection, int)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.net.URLConnection rebuildConnection(OWLOntologyLoaderConfiguration, int, java.net.URL)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.io.OWLOntologyDocumentSource  (not serializable)`
        * NEW METHOD: `PUBLIC(+) Optional getAcceptHeaders()`
        * NEW METHOD: `PUBLIC(+) void setAcceptHeaders(String)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT MIMETypeAware  (not serializable)`
        * MODIFIED METHOD: `PUBLIC NON_ABSTRACT (<- ABSTRACT) List getMIMETypes()`
        * MODIFIED METHOD: `PUBLIC NON_ABSTRACT (<- ABSTRACT) boolean handlesMimeType(String)`
        * NEW METHOD: `PUBLIC(+) STATIC(+) String stripWeight(String)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLAnnotationValue`
        * NEW METHOD: `PUBLIC(+) boolean isLiteral()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLLiteral`
        * NEW METHOD: `PUBLIC(+) boolean isLiteral()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLSameIndividualAxiom`
        * MODIFIED METHOD: `PUBLIC ABSTRACT Collection (<-Set) asPairwiseAxioms()`
        * MODIFIED METHOD: `PUBLIC ABSTRACT Collection (<-Set) splitToAnnotatedPairs()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLSignature  (not serializable)`
        * NEW METHOD: `PUBLIC(+) boolean containsEntitiesOfTypeInSignature(EntityType)`
        * NEW METHOD: `PUBLIC(+) boolean containsEntitiesOfTypeInSignature(EntityType, org.semanticweb.owlapi.model.parameters.Imports)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.CollectionFactory  (not serializable)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionally(List)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionally(List, Class)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionally(Collection)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionally(stream.Stream)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionally(stream.Stream, Class)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionallyComparables(List)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) List sortOptionallyComparables(Collection)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLObjectImpl`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) List asAnnotations(Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLSameIndividualAxiomImpl`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) asPairwiseAxioms()`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) splitToAnnotatedPairs()`

### 5.1.3/owlapi-distribution-5.1.3.jar against 5.1.2/owlapi-distribution-5.1.2.jar

 Semantic version jump suggested: 1.0.0
 * Api:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT HasAnnotations  (not serializable)`
        * NEW METHOD: `PUBLIC(+) List annotationsAsList()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT HasOperands  (not serializable)`
        * NEW METHOD: `PUBLIC(+) List getOperandsAsList()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT HasSignature  (not serializable)`
        * NEW METHOD: `PUBLIC(+) stream.Stream unsortedSignature()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLDatatypeRestriction`
        * NEW METHOD: `PUBLIC(+) List facetRestrictionsAsList()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLObject`
        * NEW METHOD: `PUBLIC(+) STATIC(+) int hashIteration(int, int)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT SWRLRule`
        * NEW METHOD: `PUBLIC(+) List bodyList()`
        * NEW METHOD: `PUBLIC(+) List headList()`
 * Impl:
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLObjectImpl  (field removed)`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) function.IntBinaryOperator hashIteration`
        * REMOVED METHOD: `PROTECTED(-) int hashCode(OWLObject)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl`

### 5.1.4/owlapi-distribution-5.1.4.jar against 5.1.3/owlapi-distribution-5.1.3.jar

 Semantic version jump suggested: 1.0.0
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.Internals`
        * MODIFIED FIELD: `PROTECTED STATIC FINAL (<- NON_FINAL) org.slf4j.Logger LOGGER`

### 5.1.5/owlapi-distribution-5.1.5.jar against 5.1.4/owlapi-distribution-5.1.4.jar

 Semantic version jump suggested: 1.0.0
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.parser.OBOFormatParser  (not serializable)`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) OBOFormatParser(org.obolibrary.oboformat.parser.OBOFormatParser$MyStream)`

### 5.1.6/owlapi-distribution-5.1.6.jar against 5.1.5/owlapi-distribution-5.1.5.jar

 Semantic version jump suggested: 1.0.0
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.obo2owl.OWLAPIOwl2Obo  (not serializable)`
        * MODIFIED METHOD: `PROTECTED Object (<-String) getValue(OWLAnnotationValue, String)`
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.writer.OBOFormatWriter  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) int actualGet(String, gnu.trove.map.hash.TObjectIntHashMap)`
 * Apibinding:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.apibinding.OWLManager`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) Object createInjector(uk.ac.manchester.cs.owl.owlapi.concurrent.Concurrency)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) OWLOntologyManager createOWLOntologyManager(Object)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) OWLDataFactory getOWLDataFactory(Object)`
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFResourceBlankNode`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(int, boolean, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC FINAL NodeID`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) IRI nodeId(int)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLStorerFactory`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.reasoner.OWLReasoner  (not serializable)`
        * NEW METHOD: `PUBLIC(+) stream.Stream representativeInstances(OWLClassExpression, boolean)`
        * NEW METHOD: `PUBLIC(+) stream.Stream representativeInstances(OWLClassExpression, org.semanticweb.owlapi.reasoner.InferenceDepth)`
        * NEW METHOD: `PUBLIC(+) stream.Stream representativeInstances(OWLClassExpression)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.chainsaw.ArrayIntMap  (not serializable)`
        * MODIFIED METHOD: `PUBLIC com.carrotsearch.hppcrt.lists.IntArrayList (<-gnu.trove.list.TIntList) getAllValues()`
        * MODIFIED METHOD: `PUBLIC com.carrotsearch.hppcrt.lists.IntArrayList (<-gnu.trove.list.TIntList) keySet()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.concurrent.NonConcurrentOWLOntologyBuilder`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) NonConcurrentOWLOntologyBuilder(uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplementationFactory)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.MapPointer  (not serializable)`
        * REMOVED METHOD: `PUBLIC(-) boolean containsReference(OWLEntity)`
        * REMOVED METHOD: `PUBLIC(-) boolean hasValues(Object)`
        * MODIFIED METHOD: `PUBLIC stream.Stream (<-Collection) keySet()`
        * REMOVED METHOD: `PUBLIC(-) void trimToSize()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataFactoryImpl(boolean)`
     * REMOVED CLASS: `PUBLIC(-) org.semanticweb.owlapi.OWLAPIParsersModule  (not serializable)`
     * REMOVED CLASS: `PUBLIC(-) org.semanticweb.owlapi.OWLAPIServiceLoaderModule  (not serializable)`
     * REMOVED CLASS: `PUBLIC(-) uk.ac.manchester.cs.owl.owlapi.OWLAPIImplModule  (not serializable)`
     * REMOVED ANNOTATION: `PUBLIC(-) ABSTRACT(-) org.semanticweb.owlapi.annotations.OwlapiModule  (not serializable)`
     * REMOVED INTERFACE: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplementationFactory  (class removed)`

### 5.1.7/owlapi-distribution-5.1.7.jar against 5.1.6/owlapi-distribution-5.1.6.jar

 Semantic version jump suggested: 1.0.0

### 5.1.8/owlapi-distribution-5.1.8.jar against 5.1.7/owlapi-distribution-5.1.7.jar

 Semantic version jump suggested: 1.0.0
 * Api:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT IsAnonymous  (not serializable)`
        * NEW METHOD: `PUBLIC(+) boolean isNamed()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLMutableOntology`
        * NEW METHOD: `PUBLIC(+) void setLock(concurrent.locks.ReadWriteLock)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLOntologyBuilder`
        * NEW METHOD: `PUBLIC(+) void setLock(concurrent.locks.ReadWriteLock)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLOntologyFactory`
        * NEW METHOD: `PUBLIC(+) void setLock(concurrent.locks.ReadWriteLock)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.utilities.Injector  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) ClassLoader classLoader()`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator  (not serializable)`
        * MODIFIED METHOD: `PROTECTED java.io.Serializable (<-void) putHasIRI(OWLObject, function.Function)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance, org.semanticweb.owlapi.util.AxiomAppearance, concurrent.atomic.AtomicInteger)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) void setOntologyID(OWLOntologyID)`
 * Impl
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.Internals`
        * REMOVED METHOD: `PROTECTED(-) uk.ac.manchester.cs.owl.owlapi.MapPointer build()`
        * REMOVED METHOD: `PROTECTED(-) uk.ac.manchester.cs.owl.owlapi.MapPointer build(AxiomType, OWLAxiomVisitorEx)`
        * REMOVED METHOD: `PROTECTED(-) uk.ac.manchester.cs.owl.owlapi.MapPointer buildLazy(AxiomType, OWLAxiomVisitorEx)`
        * REMOVED METHOD: `PUBLIC(-) void trimToSize()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.MapPointer  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) MapPointer(AxiomType, OWLAxiomVisitorEx, boolean, uk.ac.manchester.cs.owl.owlapi.Internals)`
     * REMOVED ENUM: `PUBLIC(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.util.DLExpressivityChecker$Construct  (class removed)`

### 5.1.9/owlapi-distribution-5.1.9.jar against 5.1.8/owlapi-distribution-5.1.8.jar

 Semantic version jump suggested: 1.0.0
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.OWL2ELProfile  (not serializable)`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) Set ALLOWED_DATATYPES`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.OWL2QLProfile  (not serializable)`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) Set ALLOWED_DATATYPES`
        * REMOVED METHOD: `PROTECTED(-) boolean isOWL2QLSubClassExpression(OWLClassExpression)`
        * REMOVED METHOD: `PUBLIC(-) boolean isOWL2QLSuperClassExpression(OWLClassExpression)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.OWL2RLProfile  (not serializable)`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) Set ALLOWED_DATATYPES`
        * REMOVED METHOD: `PUBLIC(-) boolean isOWL2RLEquivalentClassExpression(OWLClassExpression)`
        * REMOVED METHOD: `PROTECTED(-) boolean isOWL2RLSubClassExpression(OWLClassExpression)`
        * REMOVED METHOD: `PUBLIC(-) boolean isOWL2RLSuperClassExpression(OWLClassExpression)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.profiles.OWLProfile  (not serializable)`
        * NEW METHOD: `PUBLIC(+) org.semanticweb.owlapi.profiles.OWLProfileReport checkOntologyClosureInProfiles(OWLOntology, org.semanticweb.owlapi.profiles.Profiles[])`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.profiles.OWLProfileViolationVisitor  (not serializable)`
        * NEW METHOD: `PUBLIC(+) void visit(org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInLiteral)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.profiles.OWLProfileViolationVisitorEx  (not serializable)`
        * NEW METHOD: `PUBLIC(+) Optional visit(org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInLiteral)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) UseOfNonSimplePropertyInDisjointPropertiesAxiom(OWLOntology, OWLDisjointObjectPropertiesAxiom, OWLObjectPropertyExpression)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.violations.UseOfPropertyInChainCausesCycle  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) UseOfPropertyInChainCausesCycle(OWLOntology, OWLSubPropertyChainOfAxiom, OWLObjectPropertyExpression)`
     * REMOVED CLASS: `PROTECTED(-) org.semanticweb.owlapi.profiles.OWL2ELProfile$OWL2ELProfileObjectVisitor  (not serializable)`

### 5.1.10/owlapi-distribution-5.1.10.jar against 5.1.9/owlapi-distribution-5.1.9.jar

 Semantic version jump suggested: 0.1.0

### 5.1.11/owlapi-distribution-5.1.11.jar against 5.1.10/owlapi-distribution-5.1.10.jar

 Semantic version jump suggested: 1.0.0
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.parser.OBOFormatParser  (not serializable)`
        * MODIFIED METHOD: `PROTECTED org.obolibrary.oboformat.model.Clause (<-void) parseHeaderClause(org.obolibrary.oboformat.model.Frame)`
        * MODIFIED METHOD: `PROTECTED org.obolibrary.oboformat.model.Clause (<-void) parseUnquotedString(org.obolibrary.oboformat.model.Clause)`
     * MODIFIED CLASS: `PUBLIC STATIC org.obolibrary.oboformat.writer.OBOFormatWriter$OWLOntologyNameProvider  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OBOFormatWriter$OWLOntologyNameProvider(OWLOntology, String)`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer  (not serializable)`
        * MODIFIED FIELD: `PROTECTED FINAL Optional (<- OWLOntology) ont`
        * MODIFIED FIELD: `PROTECTED Optional (<- org.semanticweb.owlapi.util.AnnotationValueShortFormProvider) labelMaker`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) AbstractTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFGraph`
        * REMOVED METHOD: `PUBLIC(-) Map computeRemappingForSharedNodes()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator  (not serializable)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance, org.semanticweb.owlapi.util.AxiomAppearance, concurrent.atomic.AtomicInteger, Map)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase  (not serializable)`
        * REMOVED FIELD: `PROTECTED(-) Map triplesWithRemappedNodes`
        * REMOVED METHOD: `PROTECTED(-) void createGraph(stream.Stream)`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFTriple remapNodesIfNecessary(org.semanticweb.owlapi.io.RDFResource, org.semanticweb.owlapi.io.RDFTriple)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) void addVersionIRI(IRI)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.OWLObjectDesharer  (not serializable)`
        * REMOVED FIELD: `PROTECTED(-) org.semanticweb.owlapi.util.RemappingIndividualProvider anonProvider`
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.DocumentSources  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.net.URLConnection connect(OWLOntologyLoaderConfiguration, String, java.net.URLConnection, int, String)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.io.InputStream handleKnownContentEncodings(String, java.io.InputStream)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.io.InputStream handleZips(IRI, java.net.URLConnection, java.io.InputStream)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.io.RDFNode`
        * REMOVED METHOD: `PUBLIC(-) boolean idRequiredForIndividualOrAxiom()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLDocumentFormat`
        * NEW METHOD: `PUBLIC(+) boolean supportsRelativeIRIs()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT OWLEntity`
        * NEW METHOD: `PUBLIC(+) boolean isAnonymous()`

### 5.1.12/owlapi-distribution-5.1.12.jar against 5.1.11/owlapi-distribution-5.1.11.jar

 Semantic version jump suggested: 0.0.1
 * No changes.

### 5.1.13/owlapi-distribution-5.1.13.jar against 5.1.12/owlapi-distribution-5.1.12.jar

 Semantic version jump suggested: 0.0.1

### 5.1.14/owlapi-distribution-5.1.14.jar against 5.1.13/owlapi-distribution-5.1.13.jar

 Semantic version jump suggested: 1.0.0
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLImmutableOntologyImpl`
        * REMOVED METHOD: `PROTECTED(-) boolean hasLiteralInAnnotations(OWLPrimitive, OWLAxiom)`

### 5.1.15/owlapi-distribution-5.1.15.jar against 5.1.14/owlapi-distribution-5.1.14.jar

 Semantic version jump suggested: 1.0.0
 * Tools:
     * MODIFIED CLASS: `PUBLIC FINAL org.semanticweb.owlapi.modularity.AtomicDecomposition$Atom  (not serializable)`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) AtomicDecomposition$Atom(org.semanticweb.owlapi.modularity.AtomicDecomposition, OWLAxiom)`
 * Rio:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioStorer`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) org.eclipse.rdf4j.rio.RDFHandler getRDFHandlerForOutputStream(org.eclipse.rdf4j.rio.RDFFormat, java.io.OutputStream)`
        * REMOVED METHOD: `PROTECTED(-) org.eclipse.rdf4j.rio.RDFHandler getRDFHandlerForWriter(org.eclipse.rdf4j.rio.RDFFormat, java.io.Writer)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataIntersectionOfImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataIntersectionOfImpl(stream.Stream)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataIntersectionOfImpl(Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataUnionOfImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataUnionOfImpl(Collection)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataUnionOfImpl(stream.Stream)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDifferentIndividualsAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDifferentIndividualsAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDisjointClassesAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDisjointClassesAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDisjointDataPropertiesAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDisjointDataPropertiesAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDisjointObjectPropertiesAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDisjointObjectPropertiesAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDisjointUnionAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDisjointUnionAxiomImpl(OWLClass, stream.Stream, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLEquivalentClassesAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLEquivalentClassesAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLEquivalentDataPropertiesAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLEquivalentDataPropertiesAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLEquivalentObjectPropertiesAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLEquivalentObjectPropertiesAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLNaryBooleanClassExpressionImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLNaryBooleanClassExpressionImpl(stream.Stream)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLNaryBooleanClassExpressionImpl(Collection)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLNaryClassAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLNaryClassAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLNaryDataRangeImpl`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) OWLNaryDataRangeImpl(stream.Stream)`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) OWLNaryDataRangeImpl(Collection)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLNaryIndividualAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLNaryIndividualAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLNaryPropertyAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLNaryPropertyAxiomImpl(stream.Stream, Collection)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLNaryPropertyAxiomImpl(Collection, Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLObjectIntersectionOfImpl(Collection)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLObjectIntersectionOfImpl(org.semanticweb.owlapi.model.OWLClassExpression[])`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLObjectIntersectionOfImpl(stream.Stream)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLObjectUnionOfImpl(stream.Stream)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLObjectUnionOfImpl(Collection)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLSameIndividualAxiomImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLSameIndividualAxiomImpl(Collection, Collection)`

### 5.1.16/owlapi-distribution-5.1.16.jar against 5.1.15/owlapi-distribution-5.1.15.jar

 Semantic version jump suggested: 1.0.0
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.DocumentSources  (not serializable)`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.net.URLConnection connect(OWLOntologyLoaderConfiguration, java.net.URLConnection, int, String)`

### 5.1.17/owlapi-distribution-5.1.17.jar against 5.1.16/owlapi-distribution-5.1.16.jar

 Semantic version jump suggested: 1.0.0
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.search.EntitySearcher  (not serializable)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) stream.Stream getDisjointProperties(OWLPropertyExpression, OWLOntology)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) stream.Stream getDisjointProperties(OWLPropertyExpression, stream.Stream)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) stream.Stream getSubProperties(OWLPropertyExpression, OWLOntology)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) stream.Stream getSubProperties(OWLPropertyExpression, stream.Stream)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) stream.Stream getSuperProperties(OWLPropertyExpression, OWLOntology)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) stream.Stream getSuperProperties(OWLPropertyExpression, stream.Stream)`

