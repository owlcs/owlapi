package org.semanticweb.owlapi.obolibrary.obo2owl;

import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.vocab.Obo2OWLConstants.DEFAULT_IRI_PREFIX;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.documents.IRIDocumentSource;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.oboformat.OBOFormatOWLAPIParser;
import org.semanticweb.owlapi.obolibrary.oboformat.model.Clause;
import org.semanticweb.owlapi.obolibrary.oboformat.model.Frame;
import org.semanticweb.owlapi.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.obolibrary.oboformat.model.QualifierValue;
import org.semanticweb.owlapi.obolibrary.oboformat.model.Xref;
import org.semanticweb.owlapi.obolibrary.oboformat.parser.OBOFormatException;
import org.semanticweb.owlapi.obolibrary.oboformat.parser.OBOFormatParserException;
import org.semanticweb.owlapi.utility.CollectionFactory;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OBOFormatConstants;
import org.semanticweb.owlapi.vocab.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.semanticweb.owlapi.vocab.Obo2OWLConstants;
import org.semanticweb.owlapi.vocab.Obo2OWLConstants.Obo2OWLVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OWLAPIObo2Owl.
 */
public class OWLAPIObo2Owl {

    private class IDCache extends LinkedHashMap<String, IRI> {
        private final int cacheSize;

        IDCache(int cacheSize) {
            this.cacheSize = cacheSize;
        }

        @Override
        protected boolean removeEldestEntry(@Nullable Map.Entry<String, IRI> eldest) {
            return size() > cacheSize;
        }
    }

    private static final String TRUE = "true";
    private static final String CANNOT_TRANSLATE = "Cannot translate: {}";
    /**
     * The Constant IRI_PROP_isReversiblePropertyChain.
     */
    public static final String IRI_PROP_ISREVERSIBLEPROPERTYCHAIN =
        DEFAULT_IRI_PREFIX + "IAO_isReversiblePropertyChain";
    protected static final Map<String, IRI> ANNOTATIONPROPERTYMAP = initAnnotationPropertyMap();
    private static final Logger LOG = LoggerFactory.getLogger(OWLAPIObo2Owl.class);
    private static final Set<String> SKIPPED_QUALIFIERS =
        new HashSet<>(Arrays.asList("gci_relation", "gci_filler", "cardinality", "minCardinality",
            "maxCardinality", "all_some", "all_only"));
    protected final Map<String, String> idSpaceMap;
    protected final Set<OWLAnnotationProperty> apToDeclare;
    protected final Map<String, OWLClass> clsToDeclare;
    protected final Map<String, OWLAnnotationProperty> typedefToAnnotationProperty;
    protected String defaultIDSpace = "";
    protected OWLOntologyManager manager;
    protected OWLOntology owlOntology;
    protected OWLDataFactory df;
    protected OBODoc obodoc;
    /**
     * Cache for the id to IRI conversion. This cannot be replaced with a Caffeine cache - the
     * loading of keys is recursive, and a bug in ConcurrentHashMap implementation causes livelocks
     * for this particular situation.
     */
    private Map<String, IRI> idToIRICache;
    private OntologyConfigurator config;

    /**
     * @param manager the manager
     */
    public OWLAPIObo2Owl(OWLOntologyManager manager) {
        idSpaceMap = new HashMap<>();
        apToDeclare = new HashSet<>();
        clsToDeclare = new HashMap<>();
        typedefToAnnotationProperty = new HashMap<>();
        init(manager);
    }

    /**
     * Static convenience method which: (1) creates an Obo2Owl bridge object (2) parses an obo file
     * from a URL (3) converts that to an OWL ontology (4) saves the OWL ontology as RDF/XML.
     *
     * @param iri the iri
     * @param outFile the out file
     * @param manager manager to use
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws OWLOntologyCreationException ontology creation exception
     * @throws OWLOntologyStorageException ontology storage exception
     * @throws OBOFormatParserException format parser exception
     */
    public static void convertURL(String iri, String outFile, OWLOntologyManager manager)
        throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = manager.createOntology();
        new IRIDocumentSource(iri).acceptParser(new OBOFormatOWLAPIParser(), o,
            new OntologyConfigurator());
        saveAsRDFXML(outFile, o);
    }

    /**
     * See.
     *
     * @param iri the iri
     * @param outFile the out file
     * @param defaultOnt -- e.g. "go". If the obo file contains no "ontology:" header tag, this is
     *        added
     * @param manager the manager to be used
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws OWLOntologyCreationException ontology creation exception
     * @throws OWLOntologyStorageException ontology storage exception
     * @throws OBOFormatParserException format parser exception
     *         {@link #convertURL(String, String, OWLOntologyManager)}
     */
    public static void convertURL(String iri, String outFile, String defaultOnt,
        OWLOntologyManager manager)
        throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = manager.createOntology();
        OBOFormatOWLAPIParser parser =
            new OBOFormatOWLAPIParser(obo -> obo.addDefaultOntologyHeader(defaultOnt));
        new IRIDocumentSource(iri).acceptParser(parser, o, new OntologyConfigurator());
        saveAsRDFXML(outFile, o);
    }

    protected static void saveAsRDFXML(String outFile, OWLOntology o)
        throws OWLOntologyStorageException, IOException {
        try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
            OWLDocumentFormat format = new RDFXMLDocumentFormat();
            LOG.info("saving to {} fmt={}", outFile, format);
            o.saveOntology(format, outputStream);
        }
    }

    /**
     * Table 5.8 Translation of Annotation Vocabulary.
     *
     * @return property map
     */
    protected static Map<String, IRI> initAnnotationPropertyMap() {
        Map<String, IRI> map = new HashMap<>();
        map.put(OboFormatTag.TAG_IS_OBSOLETE.getTag(), OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
        map.put(OboFormatTag.TAG_NAME.getTag(), OWLRDFVocabulary.RDFS_LABEL.getIRI());
        map.put(OboFormatTag.TAG_COMMENT.getTag(), OWLRDFVocabulary.RDFS_COMMENT.getIRI());
        for (Obo2OWLVocabulary vac : Obo2OWLVocabulary.values()) {
            map.put(vac.getMappedTag(), vac.getIRI());
        }
        return map;
    }

    /**
     * @param path the path
     * @return the URI
     */
    protected static String getURI(String path) {
        if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("file:")) {
            return path;
        }
        File f = new File(path);
        return f.toURI().toString();
    }

    /**
     * Translate relation union of.
     *
     * @param id the id
     * @param p the p
     * @param clauses the clauses
     * @return the OWL axiom
     */
    @SuppressWarnings("unused")
    protected static Optional<OWLAxiom> trRelationUnionOf(String id, OWLProperty p,
        Collection<Clause> clauses) {
        // TODO not expressible in OWL - use APs. SWRL?
        LOG.error(
            "The relation union_of for {} is currently non-translatable to OWL. Ignoring clauses: {}",
            id, clauses);
        return Optional.empty();
    }

    /**
     * Translate relation intersection of.
     *
     * @param id the id
     * @param p the p
     * @param clauses the clauses
     * @return the OWL axiom
     */
    @SuppressWarnings("unused")
    protected static Optional<OWLAxiom> trRelationIntersectionOf(String id, OWLProperty p,
        Collection<Clause> clauses) {
        // TODO not expressible in OWL - use APs. SWRL?
        LOG.error(
            "The relation intersection_of for {} is currently non-translatable to OWL. Ignoring clauses: {}",
            id, clauses);
        return Optional.empty();
    }

    /**
     * Gets the qualifier value string.
     * 
     * @param q the qualifier value
     * @param quals the qualifier values
     * @return the qualifier value string
     */
    protected static String getQVString(String q, Collection<QualifierValue> quals) {
        for (QualifierValue qv : quals) {
            if (qv.getQualifier().equals(q)) {
                return qv.getValue();
            }
        }
        return "";
    }

    /**
     * Gets the qualifier value boolean.
     * 
     * @param q the qualifier value
     * @param quals the qualifier values
     * @return the qualifier value boolean
     */
    protected static boolean getQVBoolean(String q, Collection<QualifierValue> quals) {
        for (QualifierValue qv : quals) {
            if (qv.getQualifier().equals(q)) {
                Object v = qv.getValue();
                return Boolean.parseBoolean((String) v);
            }
        }
        return false;
    }

    /**
     * Gets the qualifier value int.
     * 
     * @param q the qualifier value
     * @param quals the qualifier values
     * @return the qualifier value int
     */
    @Nullable
    protected static Integer getQVInt(String q, Collection<QualifierValue> quals) {
        for (QualifierValue qv : quals) {
            if (qv.getQualifier().equals(q)) {
                Object v = qv.getValue();
                return Integer.valueOf((String) v);
            }
        }
        return null;
    }

    /**
     * Gets the id prefix.
     *
     * @param x the x
     * @return the id prefix
     */
    protected static String getIdPrefix(String x) {
        String[] parts = x.split(":", 2);
        return parts[0];
    }

    /**
     * Translate tag to iri.
     *
     * @param tag the tag
     * @param df data factory for creating IRIs
     * @return the iri
     */
    public static IRI trTagToIRI(String tag, OWLDataFactory df) {
        IRI iri = ANNOTATIONPROPERTYMAP.get(tag);
        if (iri == null) {
            iri = df.getIRI(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX, tag);
        }
        return iri;
    }

    protected void init(OWLOntologyManager m) {
        // use the given manager and its factory
        manager = m;
        df = manager.getOWLDataFactory();
        // clear all internal maps.
        idSpaceMap.clear();
        apToDeclare.clear();
        clsToDeclare.clear();
        typedefToAnnotationProperty.clear();
        config = m.getOntologyConfigurator();
        initIDCache();
    }

    /**
     * Gets the manager.
     *
     * @return the manager
     */
    public OWLOntologyManager getManager() {
        return manager;
    }

    /**
     * Sets the manager.
     *
     * @param manager the new manager
     */
    public void setManager(OWLOntologyManager manager) {
        this.manager = manager;
    }

    /**
     * Gets the obodoc.
     *
     * @return the obodoc
     */
    public OBODoc getObodoc() {
        return obodoc;
    }

    /**
     * Sets the obodoc.
     *
     * @param obodoc the new obodoc
     */
    public void setObodoc(OBODoc obodoc) {
        this.obodoc = obodoc;
    }

    /**
     * Gets the owl ontology.
     *
     * @return the owlOntology
     */
    protected OWLOntology getOwlOntology() {
        return verifyNotNull(owlOntology);
    }

    /**
     * Sets the owl ontology.
     *
     * @param owlOntology the owlOntology to set
     */
    protected void setOwlOntology(OWLOntology owlOntology) {
        this.owlOntology = owlOntology;
    }

    /**
     * Creates an OBOFormatParser object to parse a file and then converts it using the convert
     * method.
     *
     * @param oboFile the obo file
     * @return ontology
     * @throws OWLOntologyCreationException the OWL ontology creation exception
     */
    public OWLOntology convert(String oboFile) throws OWLOntologyCreationException {
        OWLOntology o = manager.createOntology();
        new IRIDocumentSource(oboFile).acceptParser(new OBOFormatOWLAPIParser(), o,
            manager.getOntologyConfigurator());
        return o;
    }

    /**
     * Convert.
     *
     * @param doc the obodoc
     * @return ontology
     * @throws OWLOntologyCreationException the OWL ontology creation exception
     */
    public OWLOntology convert(OBODoc doc) throws OWLOntologyCreationException {
        return convert(doc, null);
    }

    /**
     * Convert.
     *
     * @param doc the obodoc
     * @param in the in
     * @return the OWL ontology
     * @throws OWLOntologyCreationException if an ontology must be created because null was passed
     *         in input but creation failed
     */
    public OWLOntology convert(OBODoc doc, @Nullable OWLOntology in)
        throws OWLOntologyCreationException {
        obodoc = doc;
        init(in == null ? manager : in.getOWLOntologyManager());
        config = in == null ? manager.getOntologyConfigurator() : in.getOntologyConfigurator();
        initIDCache();
        return tr(in == null ? manager.createOntology() : in);
    }

    protected void initIDCache() {
        idToIRICache = new IDCache(config.getCacheSize());
    }

    /**
     * Translate ontology.
     *
     * @param in the in
     * @return the OWL ontology
     */
    protected OWLOntology tr(OWLOntology in) {
        setOwlOntology(in);
        Frame hf = verifyNotNull(obodoc.getHeaderFrame());
        Clause ontClause = hf.getClause(OboFormatTag.TAG_ONTOLOGY);
        if (ontClause != null) {
            String ontOboId = (String) ontClause.getValue();
            defaultIDSpace = ontOboId;
            IRI ontIRI;
            if (ontOboId.contains(":")) {
                ontIRI = df.getIRI(ontOboId);
            } else {
                ontIRI = df.getIRI(DEFAULT_IRI_PREFIX + ontOboId + ".owl");
            }
            Clause dvclause = hf.getClause(OboFormatTag.TAG_DATA_VERSION);
            if (dvclause != null) {
                String dv = dvclause.getValue().toString();
                IRI vIRI =
                    df.getIRI(DEFAULT_IRI_PREFIX + ontOboId + '/' + dv + '/' + ontOboId + ".owl");
                OWLOntologyID oid = df.getOWLOntologyID(ontIRI, vIRI);
                // if the ontology being read has a differet id from the one
                // that was passed in, update it
                // when parsing, the original ontology is likely an anonymous,
                // empty one
                if (!oid.equals(in.getOntologyID())) {
                    in.applyChange(new SetOntologyID(in, oid));
                }
            } else {
                // if the ontology being read has a differet id from the one
                // that was passed in, update it
                // when parsing, the original ontology is likely an anonymous,
                // empty one
                if (!ontIRI.equals(in.getOntologyID().getOntologyIRI().orElse(null))) {
                    in.applyChange(new SetOntologyID(in, df.getOWLOntologyID(ontIRI)));
                }
            }
        } else {
            defaultIDSpace = "TEMP";
            in.applyChange(new SetOntologyID(in,
                df.getOWLOntologyID(df.getIRI(DEFAULT_IRI_PREFIX, defaultIDSpace))));
            LOG.warn("Setting ontology id to default as the ontology tag is missing");
        }
        trHeaderFrame(hf, in);
        obodoc.getTypedefFrames().forEach(this::trTypedefToAnnotationProperty);
        obodoc.getTypedefFrames().forEach(this::trTypedefFrame);
        obodoc.getTermFrames().forEach(this::trTermFrame);
        // TODO - individuals
        for (Clause cl : hf.getClauses(OboFormatTag.TAG_IMPORT)) {
            String path = getURI(cl.getValue().toString());
            IRI importIRI = df.getIRI(path);
            OWLImportsDeclaration owlImportsDeclaration = df.getOWLImportsDeclaration(importIRI);
            manager.makeLoadImportRequest(owlImportsDeclaration, new OntologyConfigurator());
            AddImport ai = new AddImport(in, owlImportsDeclaration);
            in.applyChange(ai);
        }
        postProcess(in);
        return in;
    }

    /**
     * perform any necessary post-processing. currently this only includes the experimental
     * logical-definitions-view-property
     *
     * @param ontology the ontology
     */
    protected void postProcess(OWLOntology ontology) {
        OWLAnnotationProperty p =
            df.getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_OIO_LogicalDefinitionViewRelation);
        Optional<String> findAny = ontology.annotations().filter(a -> a.getProperty().equals(p))
            .map(a -> a.getValue().asLiteral()).filter(Optional::isPresent)
            .map(x -> x.get().getLiteral()).findAny();
        if (!findAny.isPresent()) {
            return;
        }
        IRI pIRI = oboIdToIRI(findAny.get());
        OWLObjectProperty vp = df.getOWLObjectProperty(pIRI);
        Set<OWLAxiom> rmAxioms = new HashSet<>();
        Set<OWLAxiom> newAxioms = new HashSet<>();
        ontology.axioms(AxiomType.EQUIVALENT_CLASSES).forEach(eca -> {
            AtomicInteger numNamed = new AtomicInteger();
            Set<OWLClassExpression> xs = new HashSet<>();
            eca.classExpressions().forEach(x -> {
                if (x instanceof OWLClass) {
                    xs.add(x);
                    numNamed.incrementAndGet();
                } else {
                    // anonymous class expressions are 'prefixed' with view
                    // property
                    xs.add(df.getOWLObjectSomeValuesFrom(vp, x));
                }
            });
            if (numNamed.get() == 1) {
                rmAxioms.add(eca);
                newAxioms.add(df.getOWLEquivalentClassesAxiom(xs));
            }
        });
        ontology.remove(rmAxioms);
        ontology.add(newAxioms);
    }

    /**
     * Translate header frame.
     *
     * @param headerFrame the header frame
     * @param o ontology to add to
     */
    public void trHeaderFrame(Frame headerFrame, OWLOntology o) {
        for (String t : headerFrame.getTags()) {
            OboFormatTag tag = OBOFormatConstants.getTag(t);
            if (tag == OboFormatTag.TAG_ONTOLOGY) {
                // already processed
            } else if (tag == OboFormatTag.TAG_IMPORT) {
                // TODO
            } else if (tag == OboFormatTag.TAG_SUBSETDEF) {
                parseSubsetDef(headerFrame, t);
            } else if (tag == OboFormatTag.TAG_SYNONYMTYPEDEF) {
                parseSynonymTypeDef(headerFrame, t);
            } else if (tag == OboFormatTag.TAG_DATE) {
                handleDate(t, headerFrame.getClause(tag));
            } else if (tag == OboFormatTag.TAG_PROPERTY_VALUE) {
                addPropertyValueHeaders(headerFrame.getClauses(OboFormatTag.TAG_PROPERTY_VALUE));
            } else if (tag == OboFormatTag.TAG_DATA_VERSION) {
                // TODO Add versionIRI
            } else if (tag == OboFormatTag.TAG_REMARK) {
                // translate remark as rdfs:comment
                headerFrame.getClauses(t).forEach(c -> addOntologyAnnotation(df.getRDFSComment(),
                    trLiteral(c.getValue()), trAnnotations(c)));
            } else if (tag == OboFormatTag.TAG_IDSPACE) {
                // do not translate, as they are just directives
            } else if (tag == OboFormatTag.TAG_OWL_AXIOMS) {
                // in theory, there should only be one tag
                // but we can silently collapse multiple tags
                headerFrame.getTagValues(tag, String.class)
                    .forEach(s -> OwlStringTools.translate(s, o));
            } else {
                headerFrame.getClauses(t)
                    .forEach(c -> addOntologyAnnotation(trTagToAnnotationProp(t),
                        trLiteral(c.getValue()), trAnnotations(c)));
            }
        }
    }

    protected void parseSynonymTypeDef(Frame headerFrame, String t) {
        OWLAnnotationProperty parentAnnotProp = trTagToAnnotationProp(t);
        for (Clause clause : headerFrame.getClauses(t)) {
            Object[] values = clause.getValues().toArray();
            OWLAnnotationProperty childAnnotProp = trAnnotationProp(values[0].toString());
            IRI childIRI = childAnnotProp.getIRI();
            Set<OWLAnnotation> annotations = trAnnotations(clause);
            add(df.getOWLSubAnnotationPropertyOfAxiom(childAnnotProp, parentAnnotProp,
                annotations));
            OWLAnnotationProperty ap = trTagToAnnotationProp(OboFormatTag.TAG_NAME.getTag());
            add(df.getOWLAnnotationAssertionAxiom(ap, childIRI, trLiteral(values[1])));
            if (values.length > 2 && !values[2].toString().isEmpty()) {
                ap = trTagToAnnotationProp(OboFormatTag.TAG_SCOPE.getTag());
                add(df.getOWLAnnotationAssertionAxiom(ap, childIRI,
                    trTagToAnnotationProp(values[2].toString()).getIRI()));
            }
        }
    }

    protected void parseSubsetDef(Frame headerFrame, String t) {
        OWLAnnotationProperty parentAnnotProp = trTagToAnnotationProp(t);
        for (Clause clause : headerFrame.getClauses(t)) {
            OWLAnnotationProperty childAnnotProp = trAnnotationProp(clause.getValue(String.class));
            Set<OWLAnnotation> annotations = trAnnotations(clause);
            add(df.getOWLSubAnnotationPropertyOfAxiom(childAnnotProp, parentAnnotProp,
                annotations));
            OWLAnnotationProperty ap = trTagToAnnotationProp(OboFormatTag.TAG_COMMENT.getTag());
            add(df.getOWLAnnotationAssertionAxiom(ap, childAnnotProp.getIRI(),
                trLiteral(clause.getValue2())));
        }
    }

    protected void handleDate(String t, @Nullable Clause clause) {
        if (clause != null) {
            Object value = clause.getValue();
            String dateString = null;
            if (value instanceof Date) {
                dateString = OBOFormatConstants.headerDateFormat().format((Date) value);
            } else if (value instanceof String) {
                dateString = (String) value;
            }
            if (dateString != null) {
                addOntologyAnnotation(trTagToAnnotationProp(t), trLiteral(dateString),
                    trAnnotations(clause));
            } else {
                // TODO: Throw Exceptions
                OBOFormatException e =
                    new OBOFormatException("Cannot translate clause [" + clause + ']');
                LOG.error(CANNOT_TRANSLATE, clause, e);
            }
        }
    }

    /**
     * Adds the property value headers.
     *
     * @param clauses the clauses
     */
    protected void addPropertyValueHeaders(Collection<Clause> clauses) {
        for (Clause clause : clauses) {
            Set<OWLAnnotation> annotations = trAnnotations(clause);
            Collection<Object> values = clause.getValues();
            Object v = clause.getValue();
            Object v2 = clause.getValue2();
            if (values.size() == 2) {
                // property_value(Rel-ID Entity-ID Qualifiers)
                OWLAnnotationProperty prop = trAnnotationProp((String) v);
                OWLAnnotationValue value = trAnnotationProp(v2.toString()).getIRI();
                OWLAnnotation ontAnn = df.getOWLAnnotation(prop, value, annotations);
                AddOntologyAnnotation addAnn = new AddOntologyAnnotation(getOwlOntology(), ontAnn);
                apply(addAnn);
            } else if (values.size() == 3) {
                // property_value(Rel-ID Value XSD-Type Qualifiers)
                Iterator<Object> it = clause.getValues().iterator();
                it.next();
                it.next();
                String v3String = (String) it.next();
                IRI valueIRI;
                if (v3String.startsWith("xsd:")) {
                    valueIRI = df.getIRI(Namespaces.XSD.getPrefixIRI(), v3String.substring(4));
                } else {
                    valueIRI = df.getIRI(v3String);
                }
                OWLAnnotationValue value =
                    df.getOWLLiteral((String) v2, OWL2Datatype.getDatatype(valueIRI));
                OWLAnnotationProperty prop = trAnnotationProp((String) v);
                OWLAnnotation ontAnn = df.getOWLAnnotation(prop, value, annotations);
                AddOntologyAnnotation addAnn = new AddOntologyAnnotation(getOwlOntology(), ontAnn);
                apply(addAnn);
            } else {
                LOG.error(CANNOT_TRANSLATE, clause);
                // TODO
            }
        }
    }

    /**
     * Adds the ontology annotation.
     *
     * @param ap the ap
     * @param v the v
     * @param annotations the annotations
     */
    protected void addOntologyAnnotation(OWLAnnotationProperty ap, OWLAnnotationValue v,
        Set<OWLAnnotation> annotations) {
        OWLAnnotation ontAnn = df.getOWLAnnotation(ap, v, annotations);
        AddOntologyAnnotation addAnn = new AddOntologyAnnotation(getOwlOntology(), ontAnn);
        apply(addAnn);
    }

    /**
     * Translate term frame.
     *
     * @param termFrame the term frame
     * @return the OWL class expression
     */
    public OWLClassExpression trTermFrame(Frame termFrame) {
        OWLClass cls = trClass(checkNotNull(termFrame.getId()));
        add(df.getOWLDeclarationAxiom(cls));
        termFrame.getTags().stream().filter(OboFormatTag.TAG_ALT_ID.getTag()::equals).forEach(t ->
        // Generate deprecated and replaced_by details for alternate
        // identifier
        add(translateAltIds(termFrame.getClauses(t), cls.getIRI(), true)));
        termFrame.getTags().forEach(t -> add(trTermFrameClauses(cls, termFrame.getClauses(t), t)));
        return cls;
    }

    /**
     * Generate axioms for the alternate identifiers of an {@link OWLClass} or
     * {@link OWLObjectProperty}.
     *
     * @param clauses collection of alt_id clauses
     * @param replacedBy IRI of the entity
     * @param isClass set to true if the alt_id is represents a class, false in case of an property
     * @return set of axioms generated for the alt_id clauses
     */
    protected Set<OWLAxiom> translateAltIds(Collection<Clause> clauses, IRI replacedBy,
        boolean isClass) {
        Set<OWLAxiom> axioms = new HashSet<>();
        for (Clause clause : clauses) {
            String altId = clause.getValue(String.class);
            OWLEntity altIdEntity;
            if (isClass) {
                altIdEntity = trClass(altId);
            } else {
                IRI altIdIRI = oboIdToIRI(altId);
                altIdEntity = df.getOWLObjectProperty(altIdIRI);
            }
            // entity declaration axiom
            axioms.add(df.getOWLDeclarationAxiom(altIdEntity));
            // annotate as deprecated
            axioms.add(df.getOWLAnnotationAssertionAxiom(altIdEntity.getIRI(),
                df.getOWLAnnotation(df.getOWLDeprecated(), df.getOWLLiteral(true))));
            // annotate with replaced_by (IAO_0100001)
            axioms.add(df.getOWLAnnotationAssertionAxiom(altIdEntity.getIRI(),
                df.getOWLAnnotation(
                    df.getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0100001.getIRI()),
                    replacedBy)));
            // annotate with obo:IAO_0000231=obo:IAO_0000227
            // 'has obsolescence reason' 'terms merged'
            axioms.add(df.getOWLAnnotationAssertionAxiom(altIdEntity.getIRI(),
                df.getOWLAnnotation(df.getOWLAnnotationProperty(Obo2OWLConstants.IRI_IAO_0000231),
                    Obo2OWLConstants.IRI_IAO_0000227)));
        }
        return axioms;
    }

    /**
     * Translate term frame clauses.
     *
     * @param cls the cls
     * @param clauses the clauses
     * @param t the t
     * @return the sets the
     */
    public Set<OWLAxiom> trTermFrameClauses(OWLClass cls, Collection<Clause> clauses, String t) {
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        Set<OWLAxiom> axioms = new HashSet<>();
        if (tag == OboFormatTag.TAG_INTERSECTION_OF) {
            axioms.add(trIntersectionOf(cls, clauses));
        } else if (tag == OboFormatTag.TAG_UNION_OF) {
            axioms.add(trUnionOf(cls, clauses));
        } else {
            clauses.forEach(c -> axioms.add(trTermClause(cls, t, c)));
        }
        return axioms;
    }

    /**
     * Translate typedef to annotation property.
     *
     * @param typedefFrame the typedef frame
     * @return the OWL named object
     */
    @Nullable
    protected OWLNamedObject trTypedefToAnnotationProperty(Frame typedefFrame) {
        Object tagValue = typedefFrame.getTagValue(OboFormatTag.TAG_IS_METADATA_TAG);
        if (Boolean.TRUE.equals(tagValue)) {
            String id = checkNotNull(typedefFrame.getId());
            OWLAnnotationProperty p = trAnnotationProp(id);
            add(df.getOWLDeclarationAxiom(p));
            // handle xrefs also for meta data tags
            String xid = translateShorthandIdToExpandedId(id);
            if (!id.equals(xid)) {
                OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp("shorthand"),
                    p.getIRI(), trLiteral(id), new HashSet<>());
                add(ax);
            }
            typedefToAnnotationProperty.put(p.getIRI().toString(), p);
            for (String tag : typedefFrame.getTags()) {
                OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
                if (tagConstant == OboFormatTag.TAG_IS_A) {
                    // todo - subAnnotationProperty
                } else {
                    typedefFrame.getClauses(tag).forEach(c -> add(trGenericClause(p, tag, c)));
                }
            }
            return p;
        }
        return null;
    }

    /**
     * Translate typedef frame.
     *
     * @param typedefFrame the typedef frame
     * @return the OWL named object
     */
    @Nullable
    public OWLNamedObject trTypedefFrame(Frame typedefFrame) {
        // TODO - annotation props
        Object tagValue = typedefFrame.getTagValue(OboFormatTag.TAG_IS_METADATA_TAG);
        if (Boolean.TRUE.equals(tagValue)) {
            // already handled
            // see: trTypedefToAnnotationProperty(Frame typedefFrame)
            return null;
        } else {
            String id = checkNotNull(typedefFrame.getId());
            OWLObjectProperty p = trObjectProp(id);
            add(df.getOWLDeclarationAxiom(p));
            handleShorthand(id, p);
            // TODO See 5.9.3 Special Rules for Relations
            for (String tag : typedefFrame.getTags()) {
                handleRelationTag(typedefFrame, id, p, tag);
            }
            return p;
        }
    }

    protected void handleShorthand(String id, OWLObjectProperty p) {
        String xid = translateShorthandIdToExpandedId(id);
        if (!xid.equals(id)) {
            OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp("shorthand"),
                p.getIRI(), trLiteral(id), new HashSet<>());
            add(ax);
        }
    }

    protected void handleRelationTag(Frame typedefFrame, String id, OWLObjectProperty p,
        String tag) {
        Collection<Clause> clauses = typedefFrame.getClauses(tag);
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        if (tagConstant == OboFormatTag.TAG_INTERSECTION_OF) {
            trRelationIntersectionOf(id, p, clauses).ifPresent(this::add);
        } else if (tagConstant == OboFormatTag.TAG_UNION_OF) {
            trRelationUnionOf(id, p, clauses).ifPresent(this::add);
        } else if (tagConstant == OboFormatTag.TAG_ALT_ID) {
            add(translateAltIds(clauses, p.getIRI(), false));
        } else {
            clauses.forEach(c -> add(trTypedefClause(p, tag, c)));
        }
    }

    /**
     * Translate union of.
     *
     * @param cls the cls
     * @param clauses the clauses
     * @return the OWL axiom
     */
    protected OWLAxiom trUnionOf(OWLClass cls, Collection<Clause> clauses) {
        Set<OWLAnnotation> annotations = trAnnotations(clauses);
        Set<OWLClassExpression> eSet = new HashSet<>();
        eSet.add(cls);
        Set<OWLClassExpression> iSet = new HashSet<>();
        for (Clause clause : clauses) {
            Collection<QualifierValue> qvs = clause.getQualifierValues();
            // TODO - quals
            if (clause.getValues().size() == 1) {
                iSet.add(trClass(clause.getValue()));
            } else {
                LOG.error("union_of n-ary slots not is standard - converting anyway");
                iSet.add(trRel((String) clause.getValue(), (String) clause.getValue2(), qvs));
            }
        }
        eSet.add(df.getOWLObjectUnionOf(iSet));
        return df.getOWLEquivalentClassesAxiom(eSet, annotations);
    }

    /**
     * Translate intersection of.
     *
     * @param cls the cls
     * @param clauses the clauses
     * @return the OWL axiom
     */
    protected OWLAxiom trIntersectionOf(OWLClass cls, Collection<Clause> clauses) {
        Set<OWLAnnotation> annotations = trAnnotations(clauses);
        Set<OWLClassExpression> eSet = new HashSet<>();
        eSet.add(cls);
        Set<OWLClassExpression> iSet = new HashSet<>();
        for (Clause clause : clauses) {
            Collection<QualifierValue> qvs = clause.getQualifierValues();
            if (clause.getValues().size() == 1) {
                iSet.add(trClass(clause.getValue()));
            } else {
                iSet.add(trRel((String) clause.getValue(), (String) clause.getValue2(), qvs));
            }
        }
        eSet.add(df.getOWLObjectIntersectionOf(iSet));
        return df.getOWLEquivalentClassesAxiom(eSet, annotations);
    }

    // no data properties in obo
    /**
     * @param axiom the axiom to add
     */
    protected void add(@Nullable OWLAxiom axiom) {
        if (axiom == null) {
            LOG.error("no axiom");
            return;
        }
        add(Collections.singleton(axiom));
    }

    /**
     * @param axioms the axioms to add
     */
    protected void add(@Nullable Set<OWLAxiom> axioms) {
        if (axioms == null || axioms.isEmpty()) {
            LOG.error("no axiom");
            return;
        }
        getOwlOntology().add(axioms);
    }

    /**
     * Apply the change.
     *
     * @param change the change
     */
    protected void apply(OWLOntologyChange change) {
        try {
            change.getOntology().applyChange(change);
        } catch (Exception e) {
            LOG.error("COULD NOT TRANSLATE AXIOM", e);
        }
    }

    /**
     * #5.2
     *
     * @param cls the cls
     * @param tag the tag
     * @param clause the clause
     * @return axiom
     */
    @Nullable
    protected OWLAxiom trTermClause(OWLClass cls, String tag, Clause clause) {
        Collection<QualifierValue> qvs = clause.getQualifierValues();
        Set<OWLAnnotation> annotations = trAnnotations(clause);
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        // 5.2.2
        // The gci_relation qualifier translate cls to a class expression
        OWLClassExpression clsx = cls;
        String gciRel = getQVString("gci_relation", qvs);
        String gciFiller = getQVString("gci_filler", qvs);
        if (!gciRel.isEmpty() && !gciFiller.isEmpty()) {
            OWLClassExpression r = trRel(gciRel, gciFiller, Collections.emptySet());
            clsx = df.getOWLObjectIntersectionOf(cls, r);
        }
        OWLAxiom ax;
        if (tagConstant == OboFormatTag.TAG_IS_A) {
            ax = df.getOWLSubClassOfAxiom(clsx, trClass((String) clause.getValue()), annotations);
        } else if (tagConstant == OboFormatTag.TAG_RELATIONSHIP) {
            // TODO
            IRI relId = oboIdToIRI((String) clause.getValue());
            OWLAnnotationProperty prop = typedefToAnnotationProperty.get(relId.toString());
            if (prop != null) {
                ax = df.getOWLAnnotationAssertionAxiom(prop, cls.getIRI(),
                    oboIdToIRI((String) clause.getValue2()), annotations);
            } else {
                ax = df.getOWLSubClassOfAxiom(clsx,
                    trRel((String) clause.getValue(), (String) clause.getValue2(), qvs),
                    annotations);
            }
        } else if (tagConstant == OboFormatTag.TAG_DISJOINT_FROM) {
            Set<OWLClassExpression> cSet = new HashSet<>();
            cSet.add(clsx);
            cSet.add(trClass((String) clause.getValue()));
            ax = df.getOWLDisjointClassesAxiom(cSet, annotations);
        } else if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO) {
            Set<OWLClassExpression> cSet = new HashSet<>();
            cSet.add(clsx);
            cSet.add(trClass((String) clause.getValue()));
            ax = df.getOWLEquivalentClassesAxiom(cSet, annotations);
        } else {
            return trGenericClause(cls, tag, clause);
        }
        return ax;
    }

    /**
     * Translate typedef clause.
     *
     * @param p the p
     * @param tag the tag
     * @param clause the clause
     * @return the OWL axiom
     */
    @Nullable
    protected OWLAxiom trTypedefClause(OWLObjectProperty p, String tag, Clause clause) {
        OWLAxiom ax = null;
        Object v = clause.getValue();
        assert v != null;
        Set<OWLAnnotation> annotations = trAnnotations(clause);
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        if (tagConstant == OboFormatTag.TAG_IS_A) {
            ax = df.getOWLSubObjectPropertyOfAxiom(p, trObjectProp((String) v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_RELATIONSHIP) {
            ax = parseRelationship(p, clause, ax, v, annotations);
        } else if (tagConstant == OboFormatTag.TAG_DISJOINT_FROM) {
            ax = df.getOWLDisjointObjectPropertiesAxiom(Arrays.asList(p, trObjectProp((String) v)),
                annotations);
        } else if (tagConstant == OboFormatTag.TAG_INVERSE_OF) {
            ax = df.getOWLInverseObjectPropertiesAxiom(p, trObjectProp((String) v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO) {
            ax = df.getOWLEquivalentObjectPropertiesAxiom(
                Arrays.asList(p, trObjectProp((String) v)), annotations);
        } else if (tagConstant == OboFormatTag.TAG_DOMAIN) {
            ax = df.getOWLObjectPropertyDomainAxiom(p, trClass(v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_RANGE) {
            ax = df.getOWLObjectPropertyRangeAxiom(p, trClass(v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_TRANSITIVE_OVER) {
            ax = df.getOWLSubPropertyChainOfAxiom(Arrays.asList(p, trObjectProp(v)), p,
                annotations);
        } else if (tagConstant == OboFormatTag.TAG_HOLDS_OVER_CHAIN
            || tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
            addAnnotations(annotations, tagConstant);
            ax = df.getOWLSubPropertyChainOfAxiom(
                Arrays.asList(trObjectProp(v), trObjectProp(clause.getValue2())), p, annotations);
            // TODO - annotations for equivalent to
        } else if (tagConstant == OboFormatTag.TAG_IS_TRANSITIVE && isTrue(clause)) {
            ax = df.getOWLTransitiveObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_REFLEXIVE && isTrue(clause)) {
            ax = df.getOWLReflexiveObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_SYMMETRIC && isTrue(clause)) {
            ax = df.getOWLSymmetricObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_ASYMMETRIC && isTrue(clause)) {
            ax = df.getOWLAsymmetricObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_FUNCTIONAL && isTrue(clause)) {
            ax = df.getOWLFunctionalObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL && isTrue(clause)) {
            ax = df.getOWLInverseFunctionalObjectPropertyAxiom(p, annotations);
        } else {
            return trGenericClause(p, tag, clause);
        }
        // TODO - disjointOver
        return ax;
    }

    protected boolean isTrue(Clause clause) {
        return TRUE.equals(clause.getValue().toString());
    }

    protected void addAnnotations(Set<OWLAnnotation> annotations,
        @Nullable OboFormatTag tagConstant) {
        if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
            OWLAnnotation ann = df.getOWLAnnotation(
                trAnnotationProp(IRI_PROP_ISREVERSIBLEPROPERTYCHAIN), trLiteral(TRUE));
            annotations.add(ann);
        }
    }

    protected OWLAxiom parseRelationship(OWLObjectProperty p, Clause clause, @Nullable OWLAxiom ax,
        Object v, Set<OWLAnnotation> annotations) {
        IRI relId = oboIdToIRI((String) v);
        OWLAnnotationProperty metaProp = typedefToAnnotationProperty.get(relId.toString());
        if (metaProp != null) {
            return df.getOWLAnnotationAssertionAxiom(metaProp, p.getIRI(),
                oboIdToIRI((String) clause.getValue2()), annotations);
        }
        return ax;
    }

    /**
     * Translate generic clause.
     *
     * @param e the e
     * @param tag the tag
     * @param clause the clause
     * @return the OWL axiom
     */
    @Nullable
    protected OWLAxiom trGenericClause(OWLNamedObject e, String tag, Clause clause) {
        return trGenericClause(e.getIRI(), tag, clause);
    }

    /**
     * Translate generic clause.
     *
     * @param sub the sub
     * @param tag the tag
     * @param clause the clause
     * @return the OWL axiom
     */
    @Nullable
    protected OWLAxiom trGenericClause(OWLAnnotationSubject sub, String tag, Clause clause) {
        Set<OWLAnnotation> annotations = trAnnotations(clause);
        OWLAxiom ax = null;
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        if (tagConstant == OboFormatTag.TAG_NAME) {
            ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub,
                trLiteral(clause.getValue()), annotations);
        } else if (tagConstant == OboFormatTag.TAG_DEF) {
            ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub,
                trLiteral(clause.getValue()), annotations);
        } else if (tagConstant == OboFormatTag.TAG_SUBSET) {
            ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub,
                trAnnotationProp(clause.getValue(String.class)).getIRI(), annotations);
        } else if (tagConstant == OboFormatTag.TAG_PROPERTY_VALUE) {
            ax = parsePropertyValue(sub, clause, annotations, ax);
        } else if (tagConstant == OboFormatTag.TAG_SYNONYM) {
            ax = parseSynonym(sub, clause, annotations);
        } else if (tagConstant == OboFormatTag.TAG_XREF) {
            ax = parseXref(sub, tag, clause, annotations);
        } else {
            // generic
            ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub,
                trLiteral(clause.getValue()), annotations);
        }
        return ax;
    }

    protected OWLAxiom parseXref(OWLAnnotationSubject sub, String tag, Clause clause,
        Set<OWLAnnotation> annotations) {
        OWLAxiom ax;
        Xref xref = (Xref) clause.getValue();
        String xrefAnnotation = xref.getAnnotation();
        if (xrefAnnotation != null) {
            annotations.add(df.getRDFSLabel(xrefAnnotation));
        }
        ax = df.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub,
            trLiteral(clause.getValue()), annotations);
        return ax;
    }

    protected OWLAxiom parseSynonym(OWLAnnotationSubject sub, Clause clause,
        Set<OWLAnnotation> annotations) {
        OWLAxiom ax;
        Object[] values = clause.getValues().toArray();
        String synType;
        if (values.length > 1) {
            synType = values[1].toString();
            if (values.length > 2) {
                OWLAnnotation ann = df.getOWLAnnotation(
                    trTagToAnnotationProp(OboFormatTag.TAG_HAS_SYNONYM_TYPE.getTag()),
                    trAnnotationProp(values[2].toString()).getIRI());
                annotations.add(ann);
            }
        } else {
            LOG.warn("Assume 'RELATED'for missing scope in synonym clause: {}", clause);
            // we make allowances for obof1.0, where the synonym scope is
            // optional
            synType = OboFormatTag.TAG_RELATED.getTag();
        }
        ax = df.getOWLAnnotationAssertionAxiom(trSynonymType(synType), sub,
            trLiteral(clause.getValue()), annotations);
        return ax;
    }

    protected OWLAxiom parsePropertyValue(OWLAnnotationSubject sub, Clause clause,
        Set<OWLAnnotation> annotations, @Nullable OWLAxiom ax) {
        Collection<Object> values = clause.getValues();
        Object v = clause.getValue();
        Object v2 = clause.getValue2();
        if (values.size() == 2) {
            // property_value(Rel-ID Entity-ID Qualifiers)
            return df.getOWLAnnotationAssertionAxiom(trAnnotationProp((String) v), sub,
                trAnnotationProp(v2.toString()).getIRI(), annotations);
        }
        if (values.size() == 3) {
            // property_value(Rel-ID Value XSD-Type Qualifiers)
            Iterator<Object> it = clause.getValues().iterator();
            it.next();
            it.next();
            String v3String = (String) it.next();
            IRI valueIRI;
            if (v3String.startsWith("xsd:")) {
                valueIRI = df.getIRI(Namespaces.XSD.getPrefixIRI(), v3String.substring(4));
            } else {
                valueIRI = df.getIRI(v3String);
            }
            OWLAnnotationValue value =
                df.getOWLLiteral((String) v2, OWL2Datatype.getDatatype(valueIRI));
            return df.getOWLAnnotationAssertionAxiom(trAnnotationProp((String) v), sub, value,
                annotations);
        }
        LOG.error(CANNOT_TRANSLATE, clause);
        // TODO
        return ax;
    }

    /**
     * Translate synonym type.
     *
     * @param type the type
     * @return the OWL annotation property
     */
    protected OWLAnnotationProperty trSynonymType(String type) {
        if (type.equals(OboFormatTag.TAG_RELATED.getTag())
            || type.equals(OboFormatTag.TAG_EXACT.getTag())
            || type.equals(OboFormatTag.TAG_NARROW.getTag())
            || type.equals(OboFormatTag.TAG_BROAD.getTag())) {
            return trTagToAnnotationProp(type);
        }
        return trAnnotationProp(type);
    }

    /**
     * Translate annotations.
     *
     * @param clause the clause
     * @return the sets the
     */
    protected Set<OWLAnnotation> trAnnotations(Clause clause) {
        if (clause.hasNoAnnotations()) {
            return CollectionFactory.createSet();
        }
        Set<OWLAnnotation> anns = new HashSet<>();
        trAnnotations(clause, anns);
        return anns;
    }

    /**
     * Translate annotations.
     *
     * @param clause the clause
     * @param anns the anns
     */
    protected void trAnnotations(Clause clause, Set<OWLAnnotation> anns) {
        Collection<Xref> xrefs = clause.getXrefs();
        for (Xref x : xrefs) {
            if (!x.getIdref().isEmpty()) {
                OWLAnnotationProperty ap = trTagToAnnotationProp(OboFormatTag.TAG_XREF.getTag());
                OWLAnnotation ann = df.getOWLAnnotation(ap, trLiteral(x));
                anns.add(ann);
            }
        }
        Collection<QualifierValue> qvs = clause.getQualifierValues();
        for (QualifierValue qv : qvs) {
            String qTag = qv.getQualifier();
            if (SKIPPED_QUALIFIERS.contains(qTag)) {
                continue;
            }
            OWLAnnotationProperty ap = trTagToAnnotationProp(qTag);
            OWLAnnotation ann = df.getOWLAnnotation(ap, trLiteral(qv.getValue()));
            anns.add(ann);
        }
    }

    /**
     * Translate annotations.
     *
     * @param clauses the clauses
     * @return the set of annotations
     */
    protected Set<OWLAnnotation> trAnnotations(Collection<Clause> clauses) {
        Set<OWLAnnotation> anns = new HashSet<>();
        clauses.forEach(c -> trAnnotations(c, anns));
        return anns;
    }

    /**
     * Translate rel.
     *
     * @param relId the rel id
     * @param classId the class id
     * @param quals the quals
     * @return the OWL class expression
     */
    public OWLClassExpression trRel(String relId, String classId,
        Collection<QualifierValue> quals) {
        Frame relFrame = obodoc.getTypedefFrame(relId);
        OWLObjectPropertyExpression pe = trObjectProp(relId);
        OWLClassExpression ce = trClass(classId);
        Integer exact = getQVInt("cardinality", quals);
        Integer min = getQVInt("minCardinality", quals);
        Integer max = getQVInt("maxCardinality", quals);
        boolean allSome = getQVBoolean("all_some", quals);
        boolean allOnly = getQVBoolean("all_only", quals);
        // obo-format allows dangling references to classes in class
        // expressions.
        // Create an explicit class declaration to be sure
        if (ce instanceof OWLClass c && config.shouldAddMissingTypes()) {
            add(df.getOWLDeclarationAxiom(c));
        }
        if (exact != null && exact.intValue() > 0) {
            return df.getOWLObjectExactCardinality(exact.intValue(), pe, ce);
        } else if (exact != null && exact.intValue() == 0 || max != null && max.intValue() == 0) {
            OWLObjectComplementOf ceCompl = df.getOWLObjectComplementOf(ce);
            return df.getOWLObjectAllValuesFrom(pe, ceCompl);
        } else if (max != null && min != null) {
            return df.getOWLObjectIntersectionOf(
                df.getOWLObjectMinCardinality(min.intValue(), pe, ce),
                df.getOWLObjectMaxCardinality(max.intValue(), pe, ce));
        } else if (min != null) {
            return df.getOWLObjectMinCardinality(min.intValue(), pe, ce);
        } else if (max != null) {
            return df.getOWLObjectMaxCardinality(max.intValue(), pe, ce);
        } else if (allSome && allOnly) {
            return df.getOWLObjectIntersectionOf(df.getOWLObjectSomeValuesFrom(pe, ce),
                df.getOWLObjectAllValuesFrom(pe, ce));
        } else if (allOnly) {
            return df.getOWLObjectAllValuesFrom(pe, ce);
        } else if (relFrame != null
            && Boolean.TRUE.equals(relFrame.getTagValue(OboFormatTag.TAG_IS_CLASS_LEVEL_TAG))) {
            // pun
            return df.getOWLObjectHasValue(pe, trIndividual(classId));
        }
        // default
        return df.getOWLObjectSomeValuesFrom(pe, ce);
    }

    /**
     * Translate class.
     *
     * @param classId the class id
     * @return the OWL class
     */
    protected OWLClass trClass(String classId) {
        return df.getOWLClass(oboIdToIRI(classId));
    }

    /**
     * Translate class.
     *
     * @param v the v
     * @return the OWL class expression
     */
    protected OWLClassExpression trClass(Object v) {
        return trClass((String) v);
    }

    /**
     * See section "header macros" and treat-xrefs-as-equivalent.
     *
     * @param id the id
     * @return mapped id
     */
    protected String mapPropId(String id) {
        Frame f = obodoc.getTypedefFrame(id);
        if (f != null) {
            Collection<Xref> xrefs = f.getTagValues(OboFormatTag.TAG_XREF, Xref.class);
            for (Xref x : xrefs) {
                String xid = x.getIdref();
                if (OBODoc.isTreatXrefsAsEquivalent(getIdPrefix(xid))) {
                    return xid;
                }
            }
        }
        return id;
    }

    /**
     * Translate individual.
     *
     * @param instId the individual id
     * @return the OWL individual
     */
    protected OWLIndividual trIndividual(String instId) {
        IRI iri = oboIdToIRI(instId);
        return df.getOWLNamedIndividual(iri);
    }

    /**
     * Translate tag to annotation prop.
     *
     * @param tag the tag
     * @return the OWL annotation property
     */
    protected OWLAnnotationProperty trTagToAnnotationProp(String tag) {
        IRI iri = trTagToIRI(tag, df);
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri);
        if (!apToDeclare.contains(ap)) {
            apToDeclare.add(ap);
            add(df.getOWLDeclarationAxiom(ap));
            Obo2OWLVocabulary vocab = Obo2OWLConstants.getVocabularyObj(tag);
            if (vocab != null) {
                add(df.getOWLAnnotationAssertionAxiom(iri, df.getRDFSLabel(vocab.getLabel())));
            }
        }
        return ap;
    }

    /**
     * Adds the declared annotation properties.
     *
     * @param declaredProperties the declared properties
     */
    protected void addDeclaredAnnotationProperties(
        @Nullable Collection<OWLAnnotationProperty> declaredProperties) {
        if (declaredProperties != null) {
            apToDeclare.addAll(declaredProperties);
        }
    }

    /**
     * Translate annotation prop.
     *
     * @param relId the relation id
     * @return the OWL annotation property
     */
    protected OWLAnnotationProperty trAnnotationProp(String relId) {
        return df.getOWLAnnotationProperty(oboIdToIRI(mapPropId(relId)));
    }

    /**
     * Translate object prop.
     *
     * @param relId the relation id
     * @return the OWL object property
     */
    protected OWLObjectProperty trObjectProp(String relId) {
        return df.getOWLObjectProperty(oboIdToIRI(mapPropId(relId)));
    }

    /**
     * Translate object prop.
     *
     * @param v the v
     * @return the OWL object property expression
     */
    protected OWLObjectPropertyExpression trObjectProp(Object v) {
        return df.getOWLObjectProperty(oboIdToIRI(mapPropId((String) v)));
    }

    /**
     * Translate literal.
     *
     * @param value the value
     * @return the OWL annotation value
     */
    protected OWLAnnotationValue trLiteral(Object value) {
        return switch (value) {
            case Xref x -> df.getOWLLiteral(x.getIdref());
            case Date d ->
                // use proper OWL2 data type, write lexical value as ISO 8601 date string
                df.getOWLLiteral(OBOFormatConstants.headerDateFormat().format(d),
                    OWL2Datatype.XSD_DATE_TIME);
            case Boolean b -> df.getOWLLiteral(b.booleanValue());
            case String v -> df.getOWLLiteral(v);
            default -> df.getOWLLiteral(value.toString());
        };
    }

    /**
     * Obo id to iri.
     *
     * @param id the id
     * @return the iri
     */
    public IRI oboIdToIRI(String id) {
        IRI iri = idToIRICache.get(id);
        if (iri == null) {
            iri = loadOboToIRI(id);
            idToIRICache.put(id, iri);
        }
        return iri;
    }

    /**
     * Obo id to iri.
     *
     * @param id the id
     * @return the iri
     */
    public IRI loadOboToIRI(String id) {
        if (id.contains(" ")) {
            LOG.error("id contains space: \"{}\"", id);
            throw new OWLParserException("spaces not allowed: '" + id + '\'');
        }
        // No conversion is required if this is already an IRI (ID-as-URI rule)
        if (id.startsWith("http:") || id.startsWith("https:") || id.startsWith("ftp:")
            || id.startsWith("urn:")) {
            // TODO - roundtrip from other schemes
            return df.getIRI(id);
        } else if (id.startsWith("owl:") || id.startsWith("xsd:") || id.startsWith("rdf:")
            || id.startsWith("rdfs:")) {
            for (OWL2Datatype d : OWL2Datatype.values()) {
                if (d.getPrefixedName().equals(id)) {
                    return d.getIRI();
                }
            }
            for (OWLRDFVocabulary d : OWLRDFVocabulary.values()) {
                if (d.getPrefixedName().equals(id)) {
                    return d.getIRI();
                }
            }
            for (OWLXMLVocabulary d : OWLXMLVocabulary.values()) {
                if (d.getPrefixedName().equals(id)) {
                    return d.getIRI();
                }
            }
        }
        // TODO - treat_xrefs_as_equivalent
        // special case rule for relation xrefs:
        // 5.9.3. Special Rules for Relations
        if (!id.contains(":")) {
            String xid = translateShorthandIdToExpandedId(id);
            if (!xid.equals(id)) {
                return oboIdToIRI(xid);
            }
        }
        return otherProtocols(id);
    }

    protected IRI otherProtocols(String id) {
        String[] idParts = id.split(":", 2);
        String db;
        String localId;
        if (idParts.length > 1) {
            db = idParts[0];
            localId = idParts[1];
            if (localId.contains("_")) {
                db += "#_";// NonCanonical-Prefixed-ID
            } else {
                db += "_";
            }
        } else if (idParts.length == 0) {
            db = getDefaultIDSpace() + '#';
            localId = id;
        } else {
            // TODO use owlOntology IRI
            db = getDefaultIDSpace() + '#';
            localId = idParts[0];
        }
        String uriPrefix = uriPrefix(db);
        String safeId = safeID(localId);
        try {
            return df.getIRI(uriPrefix + safeId);
        } catch (IllegalArgumentException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected String safeID(String localId) {
        String safeId;
        try {
            safeId = java.net.URLEncoder.encode(localId, "US-ASCII");
        } catch (UnsupportedEncodingException e1) {
            throw new OWLRuntimeException(e1);
        }
        if (safeId.contains(" ")) {
            safeId = safeId.replace(" ", "_");
        }
        return safeId;
    }

    protected String uriPrefix(String db) {
        String uriPrefix = DEFAULT_IRI_PREFIX + db;
        if (idSpaceMap.containsKey(db)) {
            uriPrefix = idSpaceMap.get(db);
        }
        return uriPrefix;
    }

    // 5.9.3. Special Rules for Relations
    /**
     * Translate shorthand id to expanded id.
     *
     * @param id the id
     * @return the string
     */
    protected String translateShorthandIdToExpandedId(String id) {
        if (id.contains(":")) {
            return id;
        }
        Frame tdf = obodoc.getTypedefFrame(id);
        if (tdf == null) {
            return id;
        }
        Collection<Xref> xrefs = tdf.getTagValues(OboFormatTag.TAG_XREF, Xref.class);
        String matchingExpandedId = null;
        for (Xref xref : xrefs) {
            matchingExpandedId = handleXref(id, matchingExpandedId, xref);
        }
        if (matchingExpandedId == null) {
            return id;
        }
        return matchingExpandedId;
    }

    @Nullable
    protected String handleXref(String id, @Nullable String matchingExpandedId,
        @Nullable Xref xref) {
        if (xref != null) {
            String xid = xref.getIdref();
            if (xid.equals(id)) {
                return matchingExpandedId;
            }
            if (matchingExpandedId == null) {
                return xid;
            }
            // RO and BFO take precedence over others
            if (xid.startsWith("RO") || xid.startsWith("BFO")) {
                return xid;
            }
        }
        return matchingExpandedId;
    }

    /**
     * Gets the default id space.
     *
     * @return the default id space
     */
    protected String getDefaultIDSpace() {
        return defaultIDSpace;
    }
}
