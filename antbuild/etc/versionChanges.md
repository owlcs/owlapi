### Command line used: 
    java -jar ~/Downloads/japicmp-0.14.4-jar-with-dependencies.jar -b  --ignore-missing-classes -o v1/owlapi-distribution-v1.jar -n v2/owlapi-distribution-v2.jar

## Ignored changes:

 - OWLObject is serializable, and therefore most of the model classes are, but their serialVersionUID are not fixed, therefore japicmp reports many of them changing between versions.
 - Serializability implementation for OWLAPI is meant to support short term serialization (e.g., allow transmission over network connections for editors such as WebProtege) where both sides use the same OWLAPI version, and the objects are not guaranteed to be deserializable across different versions. Those warnings are removed here to reduce the size of this page.
 - Also, changes in generated code, e.g., parsers for many languages created with JavaCC from .javacc files, are ignored s not relevant

### 4.0.1/owlapi-distribution-4.0.1.jar against 4.0.0/owlapi-distribution-4.0.0.jar
 * Rio:
     * MODIFIED INTERFACE (<- CLASS) : `PUBLIC ABSTRACT (<- NON_ABSTRACT) org.semanticweb.owlapi.formats.RioRDFDocumentFormat  (class type modified)`
        * REMOVED INTERFACE: `org.semanticweb.owlapi.model.MIMETypeAware`
        * REMOVED INTERFACE: `org.semanticweb.owlapi.formats.PrefixDocumentFormat`
        * REMOVED INTERFACE: `PrefixManager`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRDFDocumentFormat(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC ABSTRACT (<- NON_ABSTRACT) org.openrdf.rio.RDFFormat getRioFormat()`
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.oboformat.OBOFormatRenderer`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) void render(OWLOntology, java.io.Writer)`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.formats.RDFDocumentFormat (<-org.semanticweb.owlapi.formats.AbstractRDFDocumentFormat) getOntologyFormat()`
        * REMOVED METHOD: `PUBLIC(-) void setOntologyFormat(org.semanticweb.owlapi.formats.AbstractRDFDocumentFormat)`
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) org.semanticweb.owlapi.formats.AbstractRDFDocumentFormat`
 * Impl:
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLAxiomImpl`
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLIndividualAxiomImpl`
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLLogicalAxiomImpl`
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLObjectImpl`
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLPropertyAxiomImpl`

### 4.0.2/owlapi-distribution-4.0.2.jar against 4.0.1/owlapi-distribution-4.0.1.jar
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.PriorityCollection  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) PriorityCollection()`
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.model.Clause`
        * MODIFIED FIELD: `PROTECTED FINAL List (<- Collection) values`
     * REMOVED CLASS: `PUBLIC(-) org.semanticweb.owlapi.oboformat.OWLAPIOBOModule`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer`
        * REMOVED METHOD: `PROTECTED(-) Set writeAxioms(OWLEntity)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.OWLAPIParsersModule`
        * REMOVED METHOD: `PROTECTED(-) void configureParsers()`
        * REMOVED METHOD: `PROTECTED(-) void configureStorers()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.OWLAPIServiceLoaderModule`
        * REMOVED METHOD: `PROTECTED(-) void loadFactories(Class, Class)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.RDFParser`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) javax.xml.parsers.SAXParserFactory parserFactory`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) XMLWriterImpl(java.io.Writer, org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager, String)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.MapPointer`
        * MODIFIED METHOD: `PUBLIC List (<-Iterable) getValues(Object)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLAPIImplModule`
        * REMOVED METHOD: `PROTECTED(-) void configureIRIMappers()`
        * REMOVED METHOD: `PROTECTED(-) void configureOntologyFactories()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl  (field removed)`
        * REMOVED FIELD: `PROTECTED(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals data`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals`
     * REMOVED CLASS: `PUBLIC(-) uk.ac.manchester.cs.owl.owlapi.InternalsNoCache`

### 4.1.0/owlapi-distribution-4.1.0.jar against 4.0.2/owlapi-distribution-4.0.2.jar
Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.macro.MacroExpansionGCIVisitor`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) MacroExpansionGCIVisitor(OWLOntology, OWLOntologyManager)`
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFResourceBlankNode  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(IRI)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(int)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT HasAddAxioms`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) addAxioms(OWLOntology, Set)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.HasApplyChanges`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) applyChanges(List)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.HasDirectAddAxioms`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) addAxioms(Set)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.HasOntologyLoaderConfigurationProvider`
        * REMOVED METHOD: `PUBLIC(-) ABSTRACT(-) void setOntologyLoaderConfigurationProvider(com.google.inject.Provider)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.HasRemoveAxiom`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) removeAxiom(OWLOntology, OWLAxiom)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.HasRemoveAxioms`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) removeAxioms(OWLOntology, Set)`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) ManchesterOWLSyntaxParserImpl(com.google.inject.Provider, OWLDataFactory)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxFrameRenderer`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(OWLClass)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(OWLObjectPropertyExpression)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(OWLDataProperty)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(OWLIndividual)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(OWLDatatype)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(SWRLRule)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) write(OWLAnnotationProperty)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) writeAnnotations(OWLAnnotationSubject)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Set) writeFrame(OWLEntity)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) AbstractTranslator(OWLOntologyManager, OWLOntology, boolean)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFGraph  (compatible)`
        * REMOVED METHOD: `PUBLIC(-) Collection getTriplesForSubject(org.semanticweb.owlapi.io.RDFNode, boolean)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResourceBlankNode getBlankNodeFor(Object)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer  (compatible)`
        * REMOVED METHOD: `PROTECTED(-) void setPendingAnnotations(Set)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLWriter`
        * REMOVED METHOD: `PUBLIC(-) void writeNodeIDAttribute(org.semanticweb.owlapi.io.RDFResource)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLAPIImplModule`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLAPIImplModule()`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) OWLDataFactory provideOWLDataFactory()`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) OWLOntologyBuilder provideOWLOntologyBuilder()`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) OWLOntologyManager provideOWLOntologyManager(OWLDataFactory)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl  (compatible)`
        * MODIFIED FIELD: `PRIVATE (<- PROTECTED) FINAL (<- NON_FINAL) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals dataFactoryInternals`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataFactoryImpl(boolean, boolean)`
        * REMOVED METHOD: `PUBLIC(-) boolean isCachingEnabled()`
        * REMOVED METHOD: `PUBLIC(-) boolean isCompressionEnabled()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl  (compatible)`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) addAxioms(Set)`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) applyChanges(List)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLOntologyManagerImpl(OWLDataFactory)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLOntologyManagerImpl(OWLDataFactory, PriorityCollectionSorting)`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) addAxioms(OWLOntology, Set)`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) applyChanges(List)`
        * MODIFIED METHOD: `PRIVATE (<- PROTECTED) void fireChangeApplied(OWLOntologyChange)`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) removeAxiom(OWLOntology, OWLAxiom)`
        * MODIFIED METHOD: `PUBLIC org.semanticweb.owlapi.model.parameters.ChangeApplied (<-List) removeAxioms(OWLOntology, Set)`
     * REMOVED CLASS: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.AbstractInMemOWLOntologyFactory`
     * REMOVED CLASS: `PUBLIC(-) uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory`
     * REMOVED CLASS: `PUBLIC(-) uk.ac.manchester.cs.owl.owlapi.OWLOntologyBuilderImpl`
     * REMOVED CLASS: `PUBLIC(-) uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory`

### 4.1.1/owlapi-distribution-4.1.1.jar against 4.1.0/owlapi-distribution-4.1.0.jar

 * No changes.

### 4.1.2/owlapi-distribution-4.1.2.jar against 4.1.1/owlapi-distribution-4.1.1.jar

 * No changes.

### 4.1.3/owlapi-distribution-4.1.3.jar against 4.1.2/owlapi-distribution-4.1.2.jar
 * No changes.

### 4.1.4/owlapi-distribution-4.1.4.jar against 4.1.3/owlapi-distribution-4.1.3.jar

### 4.2.0/owlapi-distribution-4.2.0.jar against 4.1.4/owlapi-distribution-4.1.4.jar
 * No changes.

### 4.2.1/owlapi-distribution-4.2.1.jar against 4.2.0/owlapi-distribution-4.2.0.jar
 * No changes.

### 4.2.2/owlapi-distribution-4.2.2.jar against 4.2.1/owlapi-distribution-4.2.1.jar
 * No changes.

### 4.2.3/owlapi-distribution-4.2.3.jar against 4.2.2/owlapi-distribution-4.2.2.jar
 * No changes.

### 4.2.4/owlapi-distribution-4.2.4.jar against 4.2.3/owlapi-distribution-4.2.3.jar
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.turtle.renderer.TurtleRenderer`
        * MODIFIED FIELD: `PROTECTED (<- PACKAGE_PROTECTED) FINAL Deque (<- Stack) tabs`

### 4.2.5/owlapi-distribution-4.2.5.jar against 4.2.4/owlapi-distribution-4.2.4.jar
 * No changes.

### 4.2.6/owlapi-distribution-4.2.6.jar against 4.2.5/owlapi-distribution-4.2.5.jar
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) org.semanticweb.owlapi.util.DefaultPrefixManager pm`

### 4.2.7/owlapi-distribution-4.2.7.jar against 4.2.6/owlapi-distribution-4.2.6.jar
 * No changes.

### 4.2.8/owlapi-distribution-4.2.8.jar against 4.2.7/owlapi-distribution-4.2.7.jar
 * Api:
     * MODIFIED ENUM: `PUBLIC FINAL org.semanticweb.owlapi.model.parameters.ConfigurationOptions  (compatible)`
        * REMOVED METHOD: `PUBLIC(-) Object getValue(Class, EnumMap)`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxStorerBase  (compatible)`
        * REMOVED METHOD: `PROTECTED(-) void beginWritingUsage(OWLEntity, Set, java.io.PrintWriter)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) AbstractTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResourceBlankNode getBlankNodeFor(Object, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC FINAL org.semanticweb.owlapi.util.SAXParsers`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) javax.xml.parsers.SAXParser initParserWithOWLAPIStandards(org.xml.sax.ext.DeclHandler)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl  (type of field has changed)`
        * MODIFIED FIELD: `PROTECTED FINAL Map (<- Set) importedIRIs`

### 4.2.9/owlapi-distribution-4.2.9.jar against 4.2.8/owlapi-distribution-4.2.8.jar
 * No changes.

### 4.3.0/owlapi-distribution-4.3.0.jar against 4.2.9/owlapi-distribution-4.2.9.jar
 * Apibinding:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.apibinding.OWLManager  (compatible)`
        * MODIFIED METHOD: `PUBLIC (<- PRIVATE) STATIC Object (<-com.google.inject.Injector) createInjector(uk.ac.manchester.cs.owl.owlapi.concurrent.Concurrency)`
 * Api:
     * MODIFIED CLASS: `PUBLIC OWLOntologyLoaderConfiguration  (compatible)`
        * REMOVED METHOD: `PUBLIC(-) String getAuthorizationValue()`
        * REMOVED METHOD: `PUBLIC(-) OWLOntologyLoaderConfiguration setAuthorizationValue(String)`
     * MODIFIED ENUM: `PUBLIC FINAL org.semanticweb.owlapi.model.parameters.ConfigurationOptions  (field removed)`
        * REMOVED FIELD: `PUBLIC(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.model.parameters.ConfigurationOptions AUTHORIZATION_VALUE`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFResourceBlankNode  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(boolean, boolean)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(IRI, boolean, boolean)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(int, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFTriple  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTriple(IRI, boolean, IRI, IRI, boolean)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTriple(IRI, boolean, IRI, OWLLiteral)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator`
        * REMOVED METHOD: `PROTECTED(-) ABSTRACT(-) java.io.Serializable getAnonymousNodeForExpressions(Object)`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResourceBlankNode getBlankNodeFor(Object, boolean, boolean)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFResource getAnonymousNodeForExpressions(Object)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase`
        * REMOVED METHOD: `PUBLIC(-) ABSTRACT(-) void render(org.semanticweb.owlapi.io.RDFResource)`
 * Impl:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplementationFactory`
        * REMOVED METHOD: `PUBLIC(-) ABSTRACT(-) OWLOntology createOWLOntology(OWLOntologyID)`

### 4.3.1/owlapi-distribution-4.3.1.jar against 4.3.0/owlapi-distribution-4.3.0.jar
 * No changes.

### 4.3.2/owlapi-distribution-4.3.2.jar against 4.3.1/owlapi-distribution-4.3.1.jar
 * No changes.

### 4.5.0/owlapi-distribution-4.5.0.jar against 4.3.2/owlapi-distribution-4.3.2.jar
 * Require OWLAPI 8
        * CLASS FILE FORMAT VERSION: `52.0 <- 51.0`
 * Oboformat:
        * MODIFIED CLASS: `PUBLIC org.coode.owlapi.obo12.parser.OBO12ParserFactory  (compatible)`
            * REMOVED METHOD: `PUBLIC(-) List getMIMETypes()`
            * REMOVED METHOD: `PUBLIC(-) boolean handlesMimeType(String)`
 * Rio:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory`
 * Api:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.io.AbstractOWLParser  (compatible)`
        * REMOVED METHOD: `PROTECTED(-) java.io.InputStream getInputStream(IRI, OWLOntologyLoaderConfiguration)`
        * REMOVED METHOD: `PROTECTED(-) String getRequestTypes()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.io.OWLOntologyDocumentSource`
        * NEW METHOD: `PUBLIC(+) Optional getAcceptHeaders()`
        * NEW METHOD: `PUBLIC(+) void setAcceptHeaders(String)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.MIMETypeAware`
        * MODIFIED METHOD: `PUBLIC NON_ABSTRACT (<- ABSTRACT) List getMIMETypes()`
        * MODIFIED METHOD: `PUBLIC NON_ABSTRACT (<- ABSTRACT) boolean handlesMimeType(String)`
        * NEW METHOD: `PUBLIC(+) STATIC(+) String stripWeight(String)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLAnnotationValue`
        * NEW METHOD: `PUBLIC(+) boolean isLiteral()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLLiteral`
        * NEW METHOD: `PUBLIC(+) boolean isLiteral()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLObject`
        * NEW METHOD: `PUBLIC(+) boolean isIndividual()`
        * NEW METHOD: `PUBLIC(+) boolean isIRI()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLSignature`
        * NEW METHOD: `PUBLIC(+) boolean containsEntitiesOfTypeInSignature(EntityType)`

### 4.5.1/owlapi-distribution-4.5.1.jar against 4.5.0/owlapi-distribution-4.5.0.jar
 * No changes.

### 4.5.2/owlapi-distribution-4.5.2.jar against 4.5.1/owlapi-distribution-4.5.1.jar
 * No changes.

### 4.5.4/owlapi-distribution-4.5.4.jar against 4.5.2/owlapi-distribution-4.5.2.jar
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.obo2owl.OWLAPIOwl2Obo`
        * MODIFIED METHOD: `PROTECTED Object (<-String) getValue(OWLAnnotationValue, String)`
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.parser.OBOFormatConstants`
        * REMOVED FIELD: `PUBLIC(-) STATIC(-) FINAL(-) ThreadLocal headerDateFormat`
        * REMOVED FIELD: `PUBLIC(-) STATIC(-) FINAL(-) String DEFAULT_CHARACTER_ENCODING`
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.parser.OBOFormatParser`
        * MODIFIED FIELD: `PRIVATE (<- PUBLIC) FINAL com.github.benmanes.caffeine.cache.LoadingCache (<- com.google.common.cache.LoadingCache) stringCache`
 * Apibinding:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.apibinding.OWLManager  (serialVersionUID removed but not matches new default serialVersionUID)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) Object createInjector(uk.ac.manchester.cs.owl.owlapi.concurrent.Concurrency)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) OWLOntologyManager createOWLOntologyManager(Object)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) OWLDataFactory getOWLDataFactory(Object)`
 * Api:
     * REMOVED ANNOTATION: `PUBLIC(-) ABSTRACT(-) org.semanticweb.owlapi.annotations.OwlapiModule`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.io.RDFResourceBlankNode  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFResourceBlankNode(int, boolean, boolean, boolean)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.HasOntologyLoaderConfigurationProvider`
        * REMOVED METHOD: `PUBLIC(-) ABSTRACT(-) void setOntologyLoaderConfigurationProvider(javax.inject.Provider)`
 * Parsers:
     * REMOVED CLASS: `PUBLIC(-) org.semanticweb.owlapi.OWLAPIParsersModule`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) ManchesterOWLSyntaxParserImpl(javax.inject.Provider, OWLDataFactory)`
     * MODIFIED CLASS: `PUBLIC FINAL NodeID  (compatible)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) IRI nodeId(int)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.concurrent.NonConcurrentOWLOntologyBuilder`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) NonConcurrentOWLOntologyBuilder(uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplementationFactory)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.MapPointer`
        * MODIFIED METHOD: `PUBLIC boolean (<-Boolean) containsKey(Object)`
        * REMOVED METHOD: `PUBLIC(-) boolean containsReference(OWLEntity)`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OWLDataFactoryImpl(uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals)`
     * REMOVED CLASS: `PUBLIC(-) org.semanticweb.owlapi.OWLAPIServiceLoaderModule`
     * REMOVED CLASS: `PUBLIC(-) uk.ac.manchester.cs.owl.owlapi.OWLAPIImplModule`
     * REMOVED INTERFACE: `PUBLIC(-) ABSTRACT(-) uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplementationFactory`

### 4.5.5/owlapi-distribution-4.5.5.jar against 4.5.4/owlapi-distribution-4.5.4.jar
 * No changes.

### 4.5.6/owlapi-distribution-4.5.6.jar against 4.5.5/owlapi-distribution-4.5.5.jar
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.utilities.Injector`
        * REMOVED METHOD: `PROTECTED(-) ClassLoader classLoader()`

### 4.5.7/owlapi-distribution-4.5.7.jar against 4.5.6/owlapi-distribution-4.5.6.jar
 * Api:
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.IsAnonymous`
        * NEW METHOD: `PUBLIC(+) boolean isNamed()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLMutableOntology`
        * NEW METHOD: `PUBLIC(+) void setLock(concurrent.locks.ReadWriteLock)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLOntologyBuilder`
        * NEW METHOD: `PUBLIC(+) void setLock(concurrent.locks.ReadWriteLock)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLOntologyFactory`
        * NEW METHOD: `PUBLIC(+) void setLock(concurrent.locks.ReadWriteLock)`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) AbstractTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance, org.semanticweb.owlapi.util.AxiomAppearance, concurrent.atomic.AtomicInteger)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance, org.semanticweb.owlapi.util.AxiomAppearance, concurrent.atomic.AtomicInteger)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer`
        * REMOVED METHOD: `PROTECTED(-) void setOntologyID(OWLOntologyID)`
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.concurrent.ConcurrentOWLOntologyBuilder`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.concurrent.ConcurrentOWLOntologyImpl`
     * MODIFIED CLASS: `PROTECTED uk.ac.manchester.cs.owl.owlapi.Internals$SetPointer  (compatible)`
        * MODIFIED METHOD: `PUBLIC Collection (<-Iterable) iterable()`
     * REMOVED ENUM: `PUBLIC(-) STATIC(-) FINAL(-) org.semanticweb.owlapi.util.DLExpressivityChecker$Construct`

### 4.5.8/owlapi-distribution-4.5.8.jar against 4.5.7/owlapi-distribution-4.5.7.jar
 * Api:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.OWL2ELProfile`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) Set ALLOWED_DATATYPES`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.OWL2QLProfile`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) Set ALLOWED_DATATYPES`
        * REMOVED METHOD: `PROTECTED(-) boolean isOWL2QLSubClassExpression(OWLClassExpression)`
        * REMOVED METHOD: `PUBLIC(-) boolean isOWL2QLSuperClassExpression(OWLClassExpression)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.OWL2RLProfile`
        * REMOVED FIELD: `PROTECTED(-) STATIC(-) FINAL(-) Set ALLOWED_DATATYPES`
        * REMOVED METHOD: `PUBLIC(-) boolean isOWL2RLEquivalentClassExpression(OWLClassExpression)`
        * REMOVED METHOD: `PROTECTED(-) boolean isOWL2RLSubClassExpression(OWLClassExpression)`
        * REMOVED METHOD: `PUBLIC(-) boolean isOWL2RLSuperClassExpression(OWLClassExpression)`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.profiles.OWLProfile`
        * NEW METHOD: `PUBLIC(+) org.semanticweb.owlapi.profiles.OWLProfileReport checkOntologyClosureInProfiles(OWLOntology, org.semanticweb.owlapi.profiles.Profiles[])`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) UseOfNonSimplePropertyInDisjointPropertiesAxiom(OWLOntology, OWLDisjointObjectPropertiesAxiom, OWLObjectPropertyExpression)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.profiles.violations.UseOfPropertyInChainCausesCycle`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) UseOfPropertyInChainCausesCycle(OWLOntology, OWLSubPropertyChainOfAxiom, OWLObjectPropertyExpression)`
     * REMOVED CLASS: `PROTECTED(-) org.semanticweb.owlapi.profiles.OWL2ELProfile$OWL2ELProfileObjectVisitor`

### 4.5.9/owlapi-distribution-4.5.9.jar against 4.5.8/owlapi-distribution-4.5.8.jar
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.obo2owl.OWLAPIOwl2Obo`
        * MODIFIED FIELD: `PROTECTED FINAL (<- NON_FINAL) OWLDataFactory fac`

### 4.5.10/owlapi-distribution-4.5.10.jar against 4.5.9/owlapi-distribution-4.5.9.jar
 * No changes.

### 4.5.11/owlapi-distribution-4.5.11.jar against 4.5.10/owlapi-distribution-4.5.10.jar

### 4.5.12/owlapi-distribution-4.5.12.jar against 4.5.11/owlapi-distribution-4.5.11.jar
 * Parsers:
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer`
        * REMOVED METHOD: `PROTECTED(-) void addVersionIRI(IRI)`

### 4.5.13/owlapi-distribution-4.5.13.jar against 4.5.12/owlapi-distribution-4.5.12.jar
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC org.obolibrary.oboformat.parser.OBOFormatParser`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) java.text.SimpleDateFormat getISODateFormat()`
        * MODIFIED METHOD: `PROTECTED org.obolibrary.oboformat.model.Clause (<-void) parseHeaderClause(org.obolibrary.oboformat.model.Frame)`
        * MODIFIED METHOD: `PROTECTED org.obolibrary.oboformat.model.Clause (<-void) parseUnquotedString(org.obolibrary.oboformat.model.Clause)`

### 4.5.14/owlapi-distribution-4.5.14.jar against 4.5.13/owlapi-distribution-4.5.13.jar
 * Oboformat:
     * MODIFIED CLASS: `PUBLIC STATIC org.obolibrary.oboformat.writer.OBOFormatWriter$OWLOntologyNameProvider`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) OBOFormatWriter$OWLOntologyNameProvider(OWLOntology, String)`
 * Api:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.io.RDFNode  (compatible)`
        * REMOVED METHOD: `PUBLIC(-) boolean idRequiredForIndividualOrAxiom()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLDocumentFormat`
        * NEW METHOD: `PUBLIC(+) boolean supportsRelativeIRIs()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.model.OWLEntity`
        * NEW METHOD: `PUBLIC(+) boolean isAnonymous()`
 * Parsers:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.model.AbstractTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) AbstractTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance, org.semanticweb.owlapi.util.AxiomAppearance, concurrent.atomic.AtomicInteger, Map)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFGraph  (compatible)`
        * REMOVED METHOD: `PUBLIC(-) Map computeRemappingForSharedNodes()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rdf.model.RDFTranslator`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RDFTranslator(OWLOntologyManager, OWLOntology, boolean, org.semanticweb.owlapi.util.IndividualAppearance, org.semanticweb.owlapi.util.AxiomAppearance, concurrent.atomic.AtomicInteger, Map)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rdf.RDFRendererBase`
        * REMOVED FIELD: `PROTECTED(-) Map triplesWithRemappedNodes`
        * REMOVED METHOD: `PROTECTED(-) org.semanticweb.owlapi.io.RDFTriple remapNodesIfNecessary(org.semanticweb.owlapi.io.RDFResource, org.semanticweb.owlapi.io.RDFTriple)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.util.OWLObjectDesharer`
        * REMOVED FIELD: `PROTECTED(-) org.semanticweb.owlapi.util.RemappingIndividualProvider anonProvider`

### 4.5.15/owlapi-distribution-4.5.15.jar against 4.5.14/owlapi-distribution-4.5.14.jar

### 4.5.16/owlapi-distribution-4.5.16.jar against 4.5.15/owlapi-distribution-4.5.15.jar
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLImmutableOntologyImpl  (compatible)`
        * REMOVED METHOD: `PROTECTED(-) boolean hasLiteralInAnnotations(OWLPrimitive, OWLAxiom)`

### 4.5.17/owlapi-distribution-4.5.17.jar against 4.5.16/owlapi-distribution-4.5.16.jar
 * Rio:
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.AbstractRioRDFDocumentFormatFactory  (compatible)`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) AbstractRioRDFDocumentFormatFactory(org.openrdf.rio.RDFFormat, boolean)`
        * REMOVED CONSTRUCTOR: `PROTECTED(-) AbstractRioRDFDocumentFormatFactory(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.RioRDFDocumentFormat`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED INTERFACE: `PUBLIC ABSTRACT org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory`
        * MODIFIED METHOD: `PUBLIC ABSTRACT org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.formats.RioRDFNonPrefixDocumentFormat  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRDFNonPrefixDocumentFormat(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.formats.RioRDFPrefixDocumentFormat  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRDFPrefixDocumentFormat(org.openrdf.rio.RDFFormat)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFFormat (<-org.openrdf.rio.RDFFormat) getRioFormat()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParser  (superclass modified)`
        * MODIFIED SUPERCLASS: `org.semanticweb.owlapi.io.AbstractOWLParser (<- Object)`
     * MODIFIED CLASS: `PUBLIC ABSTRACT org.semanticweb.owlapi.rio.RioAbstractParserFactory`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFParser (<-org.openrdf.rio.RDFParser) getParser()`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioMemoryTripleSource`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioMemoryTripleSource(info.aduna.iteration.CloseableIteration, Map)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioMemoryTripleSource(info.aduna.iteration.CloseableIteration)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioOWLRDFConsumerAdapter`
        * REMOVED METHOD: `PUBLIC(-) void handleStatement(org.openrdf.model.Statement)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioOWLRDFParser`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioOWLRDFParser(org.semanticweb.owlapi.rio.OWLAPIRDFFormat, org.openrdf.model.ValueFactory)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioParserImpl  (compatible)`
        * REMOVED METHOD: `PROTECTED(-) void parseDocumentSource(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, String, org.openrdf.rio.RDFHandler)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioRenderer`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioRenderer(OWLOntology, org.openrdf.rio.RDFHandler, OWLDocumentFormat, org.openrdf.model.Resource[])`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.RioStorer  (compatible)`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioStorer(OWLDocumentFormatFactory, org.openrdf.model.Resource[])`
        * REMOVED CONSTRUCTOR: `PUBLIC(-) RioStorer(OWLDocumentFormatFactory, org.openrdf.rio.RDFHandler, org.openrdf.model.Resource[])`
        * REMOVED METHOD: `PROTECTED(-) STATIC(-) org.openrdf.rio.RDFHandler getRDFHandlerForOutputStream(org.openrdf.rio.RDFFormat, java.io.OutputStream)`
        * REMOVED METHOD: `PROTECTED(-) org.openrdf.rio.RDFHandler getRDFHandlerForWriter(org.openrdf.rio.RDFFormat, java.io.Writer)`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.rio.RDFHandler (<-org.openrdf.rio.RDFHandler) getRioHandler()`
        * REMOVED METHOD: `PUBLIC(-) void setRioHandler(org.openrdf.rio.RDFHandler)`
     * MODIFIED CLASS: `PUBLIC org.semanticweb.owlapi.rio.utils.OWLAPISimpleSAXParser`
        * MODIFIED FIELD: `PROTECTED org.eclipse.rdf4j.common.xml.SimpleSAXListener (<- info.aduna.xml.SimpleSAXListener) listener`
        * MODIFIED METHOD: `PUBLIC org.eclipse.rdf4j.common.xml.SimpleSAXListener (<-info.aduna.xml.SimpleSAXListener) getListener()`
        * REMOVED METHOD: `PUBLIC(-) void setListener(info.aduna.xml.SimpleSAXListener)`
     * MODIFIED CLASS: `PUBLIC FINAL org.semanticweb.owlapi.rio.utils.RioUtils`
        * MODIFIED METHOD: `PUBLIC STATIC org.eclipse.rdf4j.model.Statement (<-org.openrdf.model.Statement) tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)`
        * REMOVED METHOD: `PUBLIC(-) STATIC(-) Collection tripleAsStatements(org.semanticweb.owlapi.io.RDFTriple, org.openrdf.model.Resource[])`

### 4.5.18/owlapi-distribution-4.5.18.jar against 4.5.17/owlapi-distribution-4.5.17.jar
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.Internals  (compatible)`
        * MODIFIED METHOD: `PUBLIC List (<-Iterable) getAxioms()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.MapPointer`
        * MODIFIED METHOD: `PUBLIC List (<-Iterable) getAllValues()`
        * MODIFIED METHOD: `PUBLIC List (<-Iterable) keySet()`
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl  (compatible)`
        * MODIFIED METHOD: `PROTECTED Optional (<-com.google.common.base.Optional) absent()`
        * MODIFIED METHOD: `PROTECTED Optional (<-com.google.common.base.Optional) of(Object)`

### 4.5.19/owlapi-distribution-4.5.19.jar against 4.5.18/owlapi-distribution-4.5.18.jar
 * Impl:
     * MODIFIED CLASS: `PUBLIC uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl  (compatible)`
        * REMOVED METHOD: `PROTECTED(-) FINAL(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl$BuildableWeakIndexCache buildCache()`
     * REMOVED ENUM: `PROTECTED(-) ABSTRACT(-) STATIC(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl$Buildable`
     * REMOVED CLASS: `PROTECTED(-) uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl$BuildableWeakIndexCache`

