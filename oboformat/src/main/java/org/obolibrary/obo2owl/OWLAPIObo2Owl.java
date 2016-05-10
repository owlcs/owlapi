package org.obolibrary.obo2owl;

import static org.obolibrary.obo2owl.Obo2OWLConstants.DEFAULT_IRI_PREFIX;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.obolibrary.obo2owl.OwlStringTools.OwlStringException;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatException;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.UncheckedExecutionException;

/** The Class OWLAPIObo2Owl. */
public class OWLAPIObo2Owl {

    /** The log. */
    private static final Logger LOG = LoggerFactory.getLogger(OWLAPIObo2Owl.class);
    /** The Constant IRI_PROP_isReversiblePropertyChain. */
    public static final String IRI_PROP_ISREVERSIBLEPROPERTYCHAIN = DEFAULT_IRI_PREFIX
        + "IAO_isReversiblePropertyChain";
    /** The default id space. */
    @Nonnull protected String defaultIDSpace = "";
    /** The manager. */
    protected OWLOntologyManager manager;
    /** The owl ontology. */
    protected OWLOntology owlOntology;
    /** The fac. */
    protected OWLDataFactory fac;
    /** The obodoc. */
    protected OBODoc obodoc;
    /** The id space map. */
    @Nonnull protected final Map<String, String> idSpaceMap;
    /** The annotation property map. */
    @Nonnull public static final Map<String, IRI> ANNOTATIONPROPERTYMAP = initAnnotationPropertyMap();
    /** The ap to declare. */
    @Nonnull protected final Set<OWLAnnotationProperty> apToDeclare;
    /** The cls to declar. */
    @Nonnull protected final Map<String, OWLClass> clsToDeclare;
    /** The typedef to annotation property. */
    @Nonnull protected final Map<String, OWLAnnotationProperty> typedefToAnnotationProperty;
    private static final Set<String> SKIPPED_QUALIFIERS = Sets.newHashSet("gci_relation", "gci_filler", "cardinality",
        "minCardinality", "maxCardinality", "all_some", "all_only");
    /** Cache for the id to IRI conversion. */
    private LoadingCache<String, IRI> idToIRICache = CacheBuilder.newBuilder().maximumSize(1024).build(
        new CacheLoader<String, IRI>() {

            @Override
            public IRI load(String key) {
                return oboIdToIRI_load(key);
            }
        });

    /**
     * Instantiates a new oWLAPI obo2 owl.
     * 
     * @param manager
     *        the manager
     */
    public OWLAPIObo2Owl(OWLOntologyManager manager) {
        idSpaceMap = new HashMap<>();
        apToDeclare = new HashSet<>();
        clsToDeclare = new HashMap<>();
        typedefToAnnotationProperty = new HashMap<>();
        init(manager);
    }

    /**
     * Init
     * 
     * @param m
     *        the manager
     */
    protected void init(OWLOntologyManager m) {
        // use the given manager and its factory
        manager = m;
        fac = manager.getOWLDataFactory();
        // clear all internal maps.
        idSpaceMap.clear();
        apToDeclare.clear();
        clsToDeclare.clear();
        typedefToAnnotationProperty.clear();
    }

    /**
     * Static convenience method which: (1) creates an Obo2Owl bridge object (2)
     * parses an obo file from a URL (3) converts that to an OWL ontology (4)
     * saves the OWL ontology as RDF/XML.
     * 
     * @param iri
     *        the iri
     * @param outFile
     *        the out file
     * @param manager
     *        manager to use
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     * @throws OWLOntologyCreationException
     *         the oWL ontology creation exception
     * @throws OWLOntologyStorageException
     *         the oWL ontology storage exception
     * @throws OBOFormatParserException
     *         the oBO format parser exception
     */
    public static void convertURL(@Nonnull String iri, @Nonnull String outFile, @Nonnull OWLOntologyManager manager)
        throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(manager);
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(new URL(iri));
        OWLOntology ontology = bridge.convert(obodoc);
        IRI outputStream = IRI.create(outFile);
        OWLDocumentFormat format = new RDFXMLDocumentFormat();
        LOG.info("saving to {} fmt={}", outputStream, format);
        manager.saveOntology(ontology, format, outputStream);
    }

    /**
     * See.
     * 
     * @param iri
     *        the iri
     * @param outFile
     *        the out file
     * @param defaultOnt
     *        -- e.g. "go". If the obo file contains no "ontology:" header tag,
     *        this is added
     * @param manager
     *        the manager to be used
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     * @throws OWLOntologyCreationException
     *         the oWL ontology creation exception
     * @throws OWLOntologyStorageException
     *         the oWL ontology storage exception
     * @throws OBOFormatParserException
     *         the oBO format parser exception
     *         {@link #convertURL(String iri, String outFile, OWLOntologyManager manager)}
     */
    public static void convertURL(String iri, @Nonnull String outFile, String defaultOnt,
        @Nonnull OWLOntologyManager manager) throws IOException, OWLOntologyCreationException,
        OWLOntologyStorageException {
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(manager);
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(new URL(iri));
        obodoc.addDefaultOntologyHeader(defaultOnt);
        OWLOntology ontology = bridge.convert(obodoc);
        IRI outputStream = IRI.create(outFile);
        OWLDocumentFormat format = new RDFXMLDocumentFormat();
        LOG.info("saving to {} fmt={}", outputStream, format);
        manager.saveOntology(ontology, format, outputStream);
    }

    /**
     * Table 5.8 Translation of Annotation Vocabulary.
     * 
     * @return property map
     */
    @Nonnull
    protected static Map<String, IRI> initAnnotationPropertyMap() {
        Map<String, IRI> map = new HashMap<>();
        map.put(OboFormatTag.TAG_IS_OBSELETE.getTag(), OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
        map.put(OboFormatTag.TAG_NAME.getTag(), OWLRDFVocabulary.RDFS_LABEL.getIRI());
        map.put(OboFormatTag.TAG_COMMENT.getTag(), OWLRDFVocabulary.RDFS_COMMENT.getIRI());
        for (Obo2OWLVocabulary vac : Obo2OWLVocabulary.values()) {
            map.put(vac.getMappedTag(), vac.getIRI());
        }
        return map;
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
     * @param manager
     *        the new manager
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
     * @param obodoc
     *        the new obodoc
     */
    public void setObodoc(OBODoc obodoc) {
        this.obodoc = obodoc;
    }

    /**
     * Gets the owl ontology.
     * 
     * @return the owlOntology
     */
    @Nonnull
    protected OWLOntology getOwlOntology() {
        return verifyNotNull(owlOntology);
    }

    /**
     * Sets the owl ontology.
     * 
     * @param owlOntology
     *        the owlOntology to set
     */
    protected void setOwlOntology(@Nonnull OWLOntology owlOntology) {
        this.owlOntology = owlOntology;
    }

    /**
     * Creates an OBOFormatParser object to parse a file and then converts it
     * using the convert method.
     * 
     * @param oboFile
     *        the obo file
     * @return ontology
     * @throws OWLOntologyCreationException
     *         the oWL ontology creation exception
     */
    public OWLOntology convert(@Nonnull String oboFile) throws OWLOntologyCreationException {
        try {
            OBOFormatParser p = new OBOFormatParser();
            return convert(p.parse(oboFile));
        } catch (IOException ex) {
            throw new OWLOntologyCreationException("Error Occured while parsing OBO '" + oboFile + '\'', ex);
        } catch (OBOFormatParserException ex) {
            throw new OWLOntologyCreationException("Syntax error occured while parsing OBO '" + oboFile + '\'', ex);
        }
    }

    /**
     * Convert.
     * 
     * @param doc
     *        the obodoc
     * @return ontology
     * @throws OWLOntologyCreationException
     *         the oWL ontology creation exception
     */
    @Nonnull
    public OWLOntology convert(OBODoc doc) throws OWLOntologyCreationException {
        obodoc = doc;
        init(manager);
        return tr(manager.createOntology());
    }

    /**
     * Convert.
     * 
     * @param doc
     *        the obodoc
     * @param in
     *        the in
     * @return the oWL ontology
     */
    public OWLOntology convert(OBODoc doc, @Nonnull OWLOntology in) {
        obodoc = doc;
        init(in.getOWLOntologyManager());
        return tr(in);
    }

    /**
     * Tr.
     * 
     * @param in
     *        the in
     * @return the oWL ontology
     */
    @SuppressWarnings("null")
    @Nonnull
    protected OWLOntology tr(@Nonnull OWLOntology in) {
        setOwlOntology(in);
        Frame hf = obodoc.getHeaderFrame();
        Clause ontClause = hf.getClause(OboFormatTag.TAG_ONTOLOGY);
        if (ontClause != null) {
            String ontOboId = (String) ontClause.getValue();
            defaultIDSpace = ontOboId;
            IRI ontIRI;
            if (ontOboId.contains(":")) {
                ontIRI = IRI.create(ontOboId);
            } else {
                ontIRI = IRI.create(DEFAULT_IRI_PREFIX + ontOboId + ".owl");
            }
            Clause dvclause = hf.getClause(OboFormatTag.TAG_DATA_VERSION);
            if (dvclause != null) {
                String dv = dvclause.getValue().toString();
                IRI vIRI = IRI.create(DEFAULT_IRI_PREFIX + ontOboId + '/' + dv + '/' + ontOboId + ".owl");
                OWLOntologyID oid = new OWLOntologyID(Optional.of(ontIRI), Optional.of(vIRI));
                // if the ontology being read has a differet id from the one
                // that was passed in, update it
                // when parsing, the original ontology is likely an anonymous,
                // empty one
                if (!oid.equals(in.getOntologyID())) {
                    manager.applyChange(new SetOntologyID(in, oid));
                }
            } else {
                // if the ontology being read has a differet id from the one
                // that was passed in, update it
                // when parsing, the original ontology is likely an anonymous,
                // empty one
                if (!ontIRI.equals(in.getOntologyID().getOntologyIRI().orNull())) {
                    manager.applyChange(new SetOntologyID(in, new OWLOntologyID(Optional.of(ontIRI), Optional
                        .<IRI> absent())));
                }
            }
        } else {
            defaultIDSpace = "TEMP";
            manager.applyChange(new SetOntologyID(in, new OWLOntologyID(Optional.of(IRI.create(DEFAULT_IRI_PREFIX
                + defaultIDSpace)), Optional.<IRI> absent())));
            // TODO - warn
        }
        trHeaderFrame(hf);
        for (Frame f : obodoc.getTypedefFrames()) {
            trTypedefToAnnotationProperty(f);
        }
        for (Frame f : obodoc.getTypedefFrames()) {
            trTypedefFrame(f);
        }
        for (Frame f : obodoc.getTermFrames()) {
            trTermFrame(f);
        }
        // TODO - individuals
        for (Clause cl : hf.getClauses(OboFormatTag.TAG_IMPORT)) {
            String path = getURI(cl.getValue().toString());
            IRI importIRI = IRI.create(path);
            OWLImportsDeclaration owlImportsDeclaration = fac.getOWLImportsDeclaration(importIRI);
            manager.makeLoadImportRequest(owlImportsDeclaration, new OWLOntologyLoaderConfiguration());
            AddImport ai = new AddImport(in, owlImportsDeclaration);
            manager.applyChange(ai);
        }
        postProcess(in);
        return in;
    }

    /**
     * perform any necessary post-processing. currently this only includes the
     * experimental logical-definitions-view-property
     * 
     * @param ontology
     *        the ontology
     */
    protected void postProcess(@Nonnull OWLOntology ontology) {
        IRI pIRI = null;
        for (OWLAnnotation ann : ontology.getAnnotations()) {
            if (Obo2OWLVocabulary.IRI_OIO_LogicalDefinitionViewRelation.sameIRI(ann.getProperty())) {
                OWLAnnotationValue v = ann.getValue();
                if (v instanceof OWLLiteral) {
                    String rel = ((OWLLiteral) v).getLiteral();
                    pIRI = oboIdToIRI(rel);
                } else {
                    pIRI = (IRI) v;
                }
                break;
            }
        }
        if (pIRI != null) {
            OWLObjectProperty vp = fac.getOWLObjectProperty(pIRI);
            Set<OWLAxiom> rmAxioms = new HashSet<>();
            Set<OWLAxiom> newAxioms = new HashSet<>();
            for (OWLEquivalentClassesAxiom eca : ontology.getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
                int numNamed = 0;
                Set<OWLClassExpression> xs = new HashSet<>();
                for (OWLClassExpression x : eca.getClassExpressions()) {
                    assert x != null;
                    if (x instanceof OWLClass) {
                        xs.add(x);
                        numNamed++;
                        continue;
                    }
                    // anonymous class expressions are 'prefixed' with view
                    // property
                    xs.add(fac.getOWLObjectSomeValuesFrom(vp, x));
                }
                if (numNamed == 1) {
                    rmAxioms.add(eca);
                    newAxioms.add(fac.getOWLEquivalentClassesAxiom(xs));
                }
            }
            manager.removeAxioms(ontology, rmAxioms);
            manager.addAxioms(ontology, newAxioms);
        }
    }

    /**
     * Gets the uri.
     * 
     * @param path
     *        the path
     * @return the uri
     */
    protected static String getURI(@Nonnull String path) {
        if (path.startsWith("http://") || path.startsWith("https://") || path.startsWith("file:")) {
            return path;
        }
        File f = new File(path);
        return f.toURI().toString();
    }

    /**
     * Tr header frame.
     * 
     * @param headerFrame
     *        the header frame
     */
    @SuppressWarnings("null")
    public void trHeaderFrame(@Nonnull Frame headerFrame) {
        for (String t : headerFrame.getTags()) {
            OboFormatTag tag = OBOFormatConstants.getTag(t);
            if (tag == OboFormatTag.TAG_ONTOLOGY) {
                // already processed
            } else if (tag == OboFormatTag.TAG_IMPORT) {
                // TODO
                // fac.getOWLImportsDeclaration(importedOntologyIRI);
                // manager.applyChange(new AddImport(baseOnt,
                // manager.getOWLDataFactory()
                // .getOWLImportsDeclaration(importedIRI)));
            } else if (tag == OboFormatTag.TAG_SUBSETDEF) {
                OWLAnnotationProperty parentAnnotProp = trTagToAnnotationProp(t);
                /*
                 * OWLClass cls = clsToDeclar.get(t); if(cls == null){ cls =
                 * trClass(trTagToIRI(t).toString());
                 * add(fac.getOWLDeclarationAxiom(cls)); clsToDeclar.put(t,
                 * cls); }
                 */
                for (Clause clause : headerFrame.getClauses(t)) {
                    OWLAnnotationProperty childAnnotProp = trAnnotationProp(clause.getValue(String.class));
                    Set<OWLAnnotation> annotations = trAnnotations(clause);
                    add(fac.getOWLSubAnnotationPropertyOfAxiom(childAnnotProp, parentAnnotProp, annotations));
                    // OWLIndividual indv= trIndividual(
                    // clause.getValue().toString() );
                    // add (fac.getOWLClassAssertionAxiom(cls, indv) );
                    OWLAnnotationProperty ap = trTagToAnnotationProp(OboFormatTag.TAG_COMMENT.getTag());
                    add(fac.getOWLAnnotationAssertionAxiom(ap, childAnnotProp.getIRI(), trLiteral(clause.getValue2())));
                }
            } else if (tag == OboFormatTag.TAG_SYNONYMTYPEDEF) {
                OWLAnnotationProperty parentAnnotProp = trTagToAnnotationProp(t);
                for (Clause clause : headerFrame.getClauses(t)) {
                    Object[] values = clause.getValues().toArray();
                    OWLAnnotationProperty childAnnotProp = trAnnotationProp(values[0].toString());
                    IRI childIRI = childAnnotProp.getIRI();
                    Set<OWLAnnotation> annotations = trAnnotations(clause);
                    add(fac.getOWLSubAnnotationPropertyOfAxiom(childAnnotProp, parentAnnotProp, annotations));
                    OWLAnnotationProperty ap = trTagToAnnotationProp(OboFormatTag.TAG_NAME.getTag());
                    add(fac.getOWLAnnotationAssertionAxiom(ap, childIRI, trLiteral(values[1])));
                    if (values.length > 2 && !values[2].toString().isEmpty()) {
                        ap = trTagToAnnotationProp(OboFormatTag.TAG_SCOPE.getTag());
                        add(fac.getOWLAnnotationAssertionAxiom(ap, childIRI, trTagToAnnotationProp(values[2].toString())
                            .getIRI()));
                    }
                }
            } else if (tag == OboFormatTag.TAG_DATE) {
                Clause clause = headerFrame.getClause(tag);
                Object value = clause.getValue();
                String dateString = null;
                if (value instanceof Date) {
                    dateString = OBOFormatConstants.headerDateFormat().format((Date) value);
                } else if (value instanceof String) {
                    dateString = (String) value;
                }
                if (dateString != null) {
                    addOntologyAnnotation(trTagToAnnotationProp(t), trLiteral(dateString), trAnnotations(clause));
                } else {
                    // TODO: Throw Exceptions
                    OBOFormatException e = new OBOFormatException("Cannot translate clause «" + clause + '»');
                    LOG.error("Cannot translate: {}", clause, e);
                }
            } else if (tag == OboFormatTag.TAG_PROPERTY_VALUE) {
                addPropertyValueHeaders(headerFrame.getClauses(OboFormatTag.TAG_PROPERTY_VALUE));
            } else if (tag == OboFormatTag.TAG_DATA_VERSION) {
                /*
                 * Clause clause = headerFrame.getClause(tag); String dv =
                 * clause.getValue().toString(); String ontOboId =
                 * headerFrame.getClause
                 * (OboFormatTag.TAG_ONTOLOGY).getValue().toString(); IRI vIRI =
                 * IRI
                 * .create(Obo2OWLConstants.DEFAULT_IRI_PREFIX+ontOboId+"/"+dv
                 * +"/"+ontOboId+".owl");
                 * System.out.println("Adding versionIRI "+vIRI);
                 * addOntologyAnnotation(fac.getOWLVersionInfo(),
                 * fac.getOWLLiteral(vIRI.toString(),
                 * OWL2Datatype.XSD_ANY_URI));
                 */
            } else if (tag == OboFormatTag.TAG_REMARK) {
                // translate remark as rdfs:comment
                Collection<Clause> clauses = headerFrame.getClauses(t);
                for (Clause clause : clauses) {
                    addOntologyAnnotation(fac.getRDFSComment(), trLiteral(clause.getValue()), trAnnotations(clause));
                }
            } else if (tag == OboFormatTag.TAG_IDSPACE) {
                // do not translate, as they are just directives? TODO ask Chris
            } else if (tag == OboFormatTag.TAG_OWL_AXIOMS) {
                // in theory, there should only be one tag
                // but we can silently collapse multiple tags
                Collection<String> axiomStrings = headerFrame.getTagValues(tag, String.class);
                try {
                    for (String axiomString : axiomStrings) {
                        Set<OWLAxiom> axioms = OwlStringTools.translate(axiomString, manager);
                        if (axioms != null) {
                            manager.addAxioms(getOwlOntology(), axioms);
                        }
                    }
                } catch (OwlStringException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Collection<Clause> clauses = headerFrame.getClauses(t);
                for (Clause clause : clauses) {
                    addOntologyAnnotation(trTagToAnnotationProp(t), trLiteral(clause.getValue()), trAnnotations(
                        clause));
                }
            }
        }
    }

    /**
     * Adds the property value headers.
     * 
     * @param clauses
     *        the clauses
     */
    @SuppressWarnings("null")
    protected void addPropertyValueHeaders(@Nonnull Collection<Clause> clauses) {
        for (Clause clause : clauses) {
            Set<OWLAnnotation> annotations = trAnnotations(clause);
            Collection<Object> values = clause.getValues();
            Object v = clause.getValue();
            Object v2 = clause.getValue2();
            if (v == null) {
                // TODO: Throw Exceptions
                LOG.error("Cannot translate: {}", clause);
            } else if (values.size() == 2) {
                // property_value(Rel-ID Entity-ID Qualifiers)
                OWLAnnotationProperty prop = trAnnotationProp((String) v);
                OWLAnnotationValue value = trAnnotationProp(v2.toString()).getIRI();
                OWLAnnotation ontAnn = fac.getOWLAnnotation(prop, value, annotations);
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
                    valueIRI = IRI.create(Namespaces.XSD + v3String.substring(4));
                } else {
                    valueIRI = IRI.create(v3String);
                }
                OWLAnnotationValue value = fac.getOWLLiteral((String) v2, OWL2Datatype.getDatatype(valueIRI));
                OWLAnnotationProperty prop = trAnnotationProp((String) v);
                OWLAnnotation ontAnn = fac.getOWLAnnotation(prop, value, annotations);
                AddOntologyAnnotation addAnn = new AddOntologyAnnotation(getOwlOntology(), ontAnn);
                apply(addAnn);
            } else {
                LOG.error("Cannot translate: {}", clause);
                // TODO
            }
        }
    }

    /**
     * Adds the ontology annotation.
     * 
     * @param ap
     *        the ap
     * @param v
     *        the v
     * @param annotations
     *        the annotations
     */
    protected void addOntologyAnnotation(@Nonnull OWLAnnotationProperty ap, @Nonnull OWLAnnotationValue v,
        @Nonnull Set<OWLAnnotation> annotations) {
        OWLAnnotation ontAnn = fac.getOWLAnnotation(ap, v, annotations);
        AddOntologyAnnotation addAnn = new AddOntologyAnnotation(getOwlOntology(), ontAnn);
        apply(addAnn);
    }

    /**
     * Tr term frame.
     * 
     * @param termFrame
     *        the term frame
     * @return the oWL class expression
     */
    public OWLClassExpression trTermFrame(@Nonnull Frame termFrame) {
        OWLClass cls = trClass(termFrame.getId());
        add(fac.getOWLDeclarationAxiom(cls));
        for (String t : termFrame.getTags()) {
            // System.out.println("tag:"+tag);
            Collection<Clause> clauses = termFrame.getClauses(t);
            if (OboFormatTag.TAG_ALT_ID.getTag().equals(t)) {
                // Generate deprecated and replaced_by details for alternate
                // identifier
                Set<OWLAxiom> axioms = translateAltIds(clauses, cls.getIRI(), true);
                if (!axioms.isEmpty()) {
                    add(axioms);
                }
            }
            Set<OWLAxiom> axioms = trTermFrameClauses(cls, clauses, t);
            if (!axioms.isEmpty()) {
                add(axioms);
            }
        }
        return cls;
    }

    /**
     * Generate axioms for the alternate identifiers of an {@link OWLClass} or
     * {@link OWLObjectProperty}.
     * 
     * @param clauses
     *        collection of alt_id clauses
     * @param replacedBy
     *        IRI of the entity
     * @param isClass
     *        set to true if the alt_id is represents a class, false in case of
     *        an property
     * @return set of axioms generated for the alt_id clauses
     */
    @Nonnull
    protected Set<OWLAxiom> translateAltIds(@Nonnull Collection<Clause> clauses, @Nonnull IRI replacedBy,
        boolean isClass) {
        Set<OWLAxiom> axioms = new HashSet<>();
        for (Clause clause : clauses) {
            final String altId = clause.getValue(String.class);
            if (altId != null) {
                final OWLEntity altIdEntity;
                if (isClass) {
                    altIdEntity = trClass(altId);
                } else {
                    IRI altIdIRI = oboIdToIRI(altId);
                    altIdEntity = fac.getOWLObjectProperty(altIdIRI);
                }
                // entity declaration axiom
                axioms.add(fac.getOWLDeclarationAxiom(altIdEntity));
                // annotate as deprecated
                axioms.add(fac.getOWLAnnotationAssertionAxiom(altIdEntity.getIRI(), fac.getOWLAnnotation(fac
                    .getOWLDeprecated(), fac.getOWLLiteral(true))));
                // annotate with replaced_by (IAO_0100001)
                axioms.add(fac.getOWLAnnotationAssertionAxiom(altIdEntity.getIRI(), fac.getOWLAnnotation(fac
                    .getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0100001.iri), replacedBy)));
                // annotate with obo:IAO_0000231=obo:IAO_0000227
                // 'has obsolescence reason' 'terms merged'
                axioms.add(fac.getOWLAnnotationAssertionAxiom(altIdEntity.getIRI(), fac.getOWLAnnotation(fac
                    .getOWLAnnotationProperty(Obo2OWLConstants.IRI_IAO_0000231), Obo2OWLConstants.IRI_IAO_0000227)));
            }
        }
        return axioms;
    }

    /**
     * Tr term frame clauses.
     * 
     * @param cls
     *        the cls
     * @param clauses
     *        the clauses
     * @param t
     *        the t
     * @return the sets the
     */
    @Nonnull
    public Set<OWLAxiom> trTermFrameClauses(@Nonnull OWLClass cls, @Nonnull Collection<Clause> clauses, String t) {
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        Set<OWLAxiom> axioms = new HashSet<>();
        if (tag == OboFormatTag.TAG_INTERSECTION_OF) {
            axioms.add(trIntersectionOf(cls, clauses));
        } else if (tag == OboFormatTag.TAG_UNION_OF) {
            axioms.add(trUnionOf(cls, clauses));
        } else {
            for (Clause clause : clauses) {
                assert clause != null;
                axioms.add(trTermClause(cls, t, clause));
            }
        }
        return axioms;
    }

    /**
     * Tr typedef to annotation property.
     * 
     * @param typedefFrame
     *        the typedef frame
     * @return the oWL named object
     */
    @Nullable
    protected OWLNamedObject trTypedefToAnnotationProperty(@Nonnull Frame typedefFrame) {
        Object tagValue = typedefFrame.getTagValue(OboFormatTag.TAG_IS_METADATA_TAG);
        if (Boolean.TRUE.equals(tagValue)) {
            String id = typedefFrame.getId();
            OWLAnnotationProperty p = trAnnotationProp(id);
            add(fac.getOWLDeclarationAxiom(p));
            // handle xrefs also for meta data tags
            String xid = translateShorthandIdToExpandedId(id);
            if (!id.equals(xid)) {
                OWLAxiom ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp("shorthand"), p.getIRI(),
                    trLiteral(id), new HashSet<OWLAnnotation>());
                add(ax);
            }
            typedefToAnnotationProperty.put(p.getIRI().toString(), p);
            for (String tag : typedefFrame.getTags()) {
                OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
                if (tagConstant == OboFormatTag.TAG_IS_A) {
                    // todo - subAnnotationProperty
                    /*
                     * OWLAxiom ax = fac.getOWLSubAnnotationPropertyOfAxiom( p,
                     * trObjectProp((String)typedefFrame.getC), annotations);
                     */
                } else {
                    for (Clause clause : typedefFrame.getClauses(tag)) {
                        // System.out.println(p+" p "+tag+" t "+clause);
                        add(trGenericClause(p, tag, clause));
                    }
                }
            }
            return p;
        }
        return null;
    }

    /**
     * Tr typedef frame.
     * 
     * @param typedefFrame
     *        the typedef frame
     * @return the oWL named object
     */
    @Nullable
    public OWLNamedObject trTypedefFrame(@Nonnull Frame typedefFrame) {
        // TODO - annotation props
        Object tagValue = typedefFrame.getTagValue(OboFormatTag.TAG_IS_METADATA_TAG);
        if (Boolean.TRUE.equals(tagValue)) {
            // already handled
            // see: trTypedefToAnnotationProperty(Frame typedefFrame)
            return null;
        } else {
            String id = typedefFrame.getId();
            OWLObjectProperty p = trObjectProp(id);
            add(fac.getOWLDeclarationAxiom(p));
            String xid = translateShorthandIdToExpandedId(id);
            if (!xid.equals(id)) {
                OWLAxiom ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp("shorthand"), p.getIRI(),
                    trLiteral(id), new HashSet<OWLAnnotation>());
                add(ax);
            }
            /*
             * // See 5.9.3 Special Rules for Relations Collection<Xref> xrefs =
             * typedefFrame.getTagValues(OboFormatTag.TAG_XREF, Xref.class);
             * String xrefStr = null; for (Xref xref: xrefs) { if (xref != null)
             * { String xid = xref.getIdref(); if ((xid.startsWith("RO") ||
             * xid.startsWith("BFO")) && !xid.equals(id)) { // RO and BFO have
             * special status. // avoid cycles (in case of self-xref) //
             * fac.getOWLAnnotationAssertionAxiom(prop, p.getIRI(),
             * trLiteral(id), new HashSet<>()); OWLAxiom ax =
             * fac.getOWLAnnotationAssertionAxiom(
             * trTagToAnnotationProp("shorthand"), p.getIRI(), trLiteral(id),
             * new HashSet<>()); add(ax); // return
             * oboIdToIRI(xid); } } }
             */
            for (String tag : typedefFrame.getTags()) {
                Collection<Clause> clauses = typedefFrame.getClauses(tag);
                OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
                if (tagConstant == OboFormatTag.TAG_INTERSECTION_OF) {
                    OWLAxiom axiom = trRelationIntersectionOf(id, p, clauses);
                    if (axiom != null) {
                        add(axiom);
                    }
                } else if (tagConstant == OboFormatTag.TAG_UNION_OF) {
                    OWLAxiom axiom = trRelationUnionOf(id, p, clauses);
                    if (axiom != null) {
                        add(axiom);
                    }
                } else if (tagConstant == OboFormatTag.TAG_ALT_ID) {
                    add(translateAltIds(clauses, p.getIRI(), false));
                } else {
                    for (Clause clause : clauses) {
                        add(trTypedefClause(p, tag, clause));
                    }
                }
            }
            return p;
        }
    }

    /**
     * Tr relation union of.
     * 
     * @param id
     *        the id
     * @param p
     *        the p
     * @param clauses
     *        the clauses
     * @return the oWL axiom
     */
    @Nullable
    @SuppressWarnings("unused")
    protected static OWLAxiom trRelationUnionOf(String id, OWLProperty p, Collection<Clause> clauses) {
        // TODO not expressible in OWL - use APs. SWRL?
        LOG.error("The relation union_of for {} is currently non-translatable to OWL. Ignoring clauses: {}", id,
            clauses);
        return null;
    }

    /**
     * Tr relation intersection of.
     * 
     * @param id
     *        the id
     * @param p
     *        the p
     * @param clauses
     *        the clauses
     * @return the oWL axiom
     */
    @Nullable
    @SuppressWarnings("unused")
    protected static OWLAxiom trRelationIntersectionOf(String id, OWLProperty p, Collection<Clause> clauses) {
        // TODO not expressible in OWL - use APs. SWRL?
        LOG.error("The relation intersection_of for {} is currently non-translatable to OWL. Ignoring clauses: {}", id,
            clauses);
        return null;
    }

    /**
     * Tr union of.
     * 
     * @param cls
     *        the cls
     * @param clauses
     *        the clauses
     * @return the oWL axiom
     */
    @SuppressWarnings("null")
    @Nonnull
    protected OWLAxiom trUnionOf(OWLClass cls, @Nonnull Collection<Clause> clauses) {
        Set<? extends OWLAnnotation> annotations = trAnnotations(clauses);
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
                // System.err.println("union_of n-ary slots not is standard -
                // converting anyway");
                iSet.add(trRel((String) clause.getValue(), (String) clause.getValue2(), qvs));
            }
        }
        // out.println(cls+" CL:"+clauses+" I:"+iSet+" E:"+eSet);
        eSet.add(fac.getOWLObjectUnionOf(iSet));
        // TODO - fix this
        if (annotations.isEmpty()) {
            return fac.getOWLEquivalentClassesAxiom(eSet);
        } else {
            return fac.getOWLEquivalentClassesAxiom(eSet, annotations);
        }
    }

    /**
     * Tr intersection of.
     * 
     * @param cls
     *        the cls
     * @param clauses
     *        the clauses
     * @return the oWL axiom
     */
    @SuppressWarnings("null")
    protected OWLAxiom trIntersectionOf(OWLClass cls, @Nonnull Collection<Clause> clauses) {
        Set<? extends OWLAnnotation> annotations = trAnnotations(clauses);
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
        // out.println(cls+" CL:"+clauses+" I:"+iSet+" E:"+eSet);
        eSet.add(fac.getOWLObjectIntersectionOf(iSet));
        // TODO - fix this
        if (annotations.isEmpty()) {
            return fac.getOWLEquivalentClassesAxiom(eSet);
        } else {
            return fac.getOWLEquivalentClassesAxiom(eSet, annotations);
        }
    }

    /**
     * Adds the.
     * 
     * @param axiom
     *        the axiom
     */
    protected void add(@Nullable OWLAxiom axiom) {
        if (axiom == null) {
            LOG.error("no axiom");
            return;
        }
        add(Collections.singleton(axiom));
    }

    /**
     * Adds the.
     * 
     * @param axioms
     *        the axioms
     */
    protected void add(@Nullable Set<OWLAxiom> axioms) {
        if (axioms == null || axioms.isEmpty()) {
            LOG.error("no axiom");
            return;
        }
        manager.addAxioms(getOwlOntology(), axioms);
    }

    /**
     * Apply the change.
     * 
     * @param change
     *        the change
     */
    protected void apply(OWLOntologyChange change) {
        apply(CollectionFactory.list(change));
    }

    /**
     * Apply the changes.
     * 
     * @param changes
     *        the changes
     */
    protected void apply(@Nonnull List<OWLOntologyChange> changes) {
        try {
            manager.applyChanges(changes);
        } catch (Exception e) {
            LOG.error("COULD NOT TRANSLATE AXIOM", e);
        }
    }

    /**
     * #5.2
     * 
     * @param cls
     *        the cls
     * @param tag
     *        the tag
     * @param clause
     *        the clause
     * @return axiom
     */
    @SuppressWarnings("null")
    protected OWLAxiom trTermClause(@Nonnull OWLClass cls, String tag, @Nonnull Clause clause) {
        Collection<QualifierValue> qvs = clause.getQualifierValues();
        Set<? extends OWLAnnotation> annotations = trAnnotations(clause);
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        // 5.2.2
        // The gci_relation qualifier translate cls to a class expression
        OWLClassExpression clsx = cls;
        String gciRel = getQVString("gci_relation", qvs);
        if (gciRel != null && !gciRel.isEmpty()) {
            String gciFiller = getQVString("gci_filler", qvs);
            OWLClassExpression r = trRel(gciRel, gciFiller, new HashSet<QualifierValue>());
            clsx = fac.getOWLObjectIntersectionOf(cls, r);
        }
        OWLAxiom ax;
        if (tagConstant == OboFormatTag.TAG_IS_A) {
            ax = fac.getOWLSubClassOfAxiom(clsx, trClass((String) clause.getValue()), annotations);
        } else if (tagConstant == OboFormatTag.TAG_RELATIONSHIP) {
            // TODO
            IRI relId = oboIdToIRI((String) clause.getValue());
            OWLAnnotationProperty prop = typedefToAnnotationProperty.get(relId.toString());
            if (prop != null) {
                ax = fac.getOWLAnnotationAssertionAxiom(prop, cls.getIRI(), oboIdToIRI((String) clause.getValue2()),
                    annotations);
            } else {
                ax = fac.getOWLSubClassOfAxiom(clsx, trRel((String) clause.getValue(), (String) clause.getValue2(),
                    qvs), annotations);
            }
        } else if (tagConstant == OboFormatTag.TAG_DISJOINT_FROM) {
            Set<OWLClassExpression> cSet = new HashSet<>();
            cSet.add(clsx);
            cSet.add(trClass((String) clause.getValue()));
            ax = fac.getOWLDisjointClassesAxiom(cSet, annotations);
        } else if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO) {
            Set<OWLClassExpression> cSet = new HashSet<>();
            cSet.add(clsx);
            cSet.add(trClass((String) clause.getValue()));
            ax = fac.getOWLEquivalentClassesAxiom(cSet, annotations);
        } else {
            return trGenericClause(cls, tag, clause);
        }
        return ax;
    }

    // no data properties in obo
    /**
     * Tr typedef clause.
     * 
     * @param p
     *        the p
     * @param tag
     *        the tag
     * @param clause
     *        the clause
     * @return the oWL axiom
     */
    @SuppressWarnings("null")
    @Nullable
    protected OWLAxiom trTypedefClause(@Nonnull OWLObjectProperty p, String tag, @Nonnull Clause clause) {
        OWLAxiom ax = null;
        Object v = clause.getValue();
        Set<OWLAnnotation> annotations = trAnnotations(clause);
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        if (tagConstant == OboFormatTag.TAG_IS_A) {
            ax = fac.getOWLSubObjectPropertyOfAxiom(p, trObjectProp((String) v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_RELATIONSHIP) {
            IRI relId = oboIdToIRI((String) clause.getValue());
            OWLAnnotationProperty metaProp = typedefToAnnotationProperty.get(relId.toString());
            if (metaProp != null) {
                ax = fac.getOWLAnnotationAssertionAxiom(metaProp, p.getIRI(), oboIdToIRI((String) clause.getValue2()),
                    annotations);
            } else {
                // System.err.println("no annotation prop:"+relId);
                // ax = null; // TODO
            }
        } else if (tagConstant == OboFormatTag.TAG_DISJOINT_FROM) {
            Set<OWLObjectPropertyExpression> cSet = new HashSet<>();
            cSet.add(p);
            cSet.add(trObjectProp((String) v));
            ax = fac.getOWLDisjointObjectPropertiesAxiom(cSet, annotations);
        } else if (tagConstant == OboFormatTag.TAG_INVERSE_OF) {
            ax = fac.getOWLInverseObjectPropertiesAxiom(p, trObjectProp((String) v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO) {
            Set<OWLObjectPropertyExpression> cSet = new HashSet<>();
            cSet.add(p);
            cSet.add(trObjectProp((String) v));
            ax = fac.getOWLEquivalentObjectPropertiesAxiom(cSet, annotations);
        } else if (tagConstant == OboFormatTag.TAG_DOMAIN) {
            ax = fac.getOWLObjectPropertyDomainAxiom(p, trClass(v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_RANGE) {
            ax = fac.getOWLObjectPropertyRangeAxiom(p, trClass(v), annotations);
        } else if (tagConstant == OboFormatTag.TAG_TRANSITIVE_OVER) {
            List<OWLObjectPropertyExpression> chain = new ArrayList<>(2);
            chain.add(p);
            chain.add(trObjectProp(v));
            ax = fac.getOWLSubPropertyChainOfAxiom(chain, p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_HOLDS_OVER_CHAIN
            || tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
            if (tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
                OWLAnnotation ann = fac.getOWLAnnotation(trAnnotationProp(IRI_PROP_ISREVERSIBLEPROPERTYCHAIN),
                    trLiteral("true"));
                annotations.add(ann);
                // isReversiblePropertyChain
            }
            List<OWLObjectPropertyExpression> chain = new ArrayList<>();
            chain.add(trObjectProp(v));
            chain.add(trObjectProp(clause.getValue2()));
            ax = fac.getOWLSubPropertyChainOfAxiom(chain, p, annotations);
            // System.out.println("chain:"+ax);
            // TODO - annotations for equivalent to
        } else if (tagConstant == OboFormatTag.TAG_IS_TRANSITIVE && "true".equals(clause.getValue().toString())) {
            ax = fac.getOWLTransitiveObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_REFLEXIVE && "true".equals(clause.getValue().toString())) {
            ax = fac.getOWLReflexiveObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_SYMMETRIC && "true".equals(clause.getValue().toString())) {
            ax = fac.getOWLSymmetricObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_ASYMMETRIC && "true".equals(clause.getValue().toString())) {
            ax = fac.getOWLAsymmetricObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_FUNCTIONAL && "true".equals(clause.getValue().toString())) {
            ax = fac.getOWLFunctionalObjectPropertyAxiom(p, annotations);
        } else if (tagConstant == OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL && "true".equals(clause.getValue()
            .toString())) {
            ax = fac.getOWLInverseFunctionalObjectPropertyAxiom(p, annotations);
        } else {
            return trGenericClause(p, tag, clause);
        }
        // TODO - disjointOver
        return ax;
    }

    /**
     * Tr generic clause.
     * 
     * @param e
     *        the e
     * @param tag
     *        the tag
     * @param clause
     *        the clause
     * @return the oWL axiom
     */
    protected OWLAxiom trGenericClause(@Nonnull OWLNamedObject e, @Nonnull String tag, @Nonnull Clause clause) {
        /*
         * Collection<QualifierValue> qvs = clause.getQualifierValues(); Set<?
         * extends OWLAnnotation> annotations = trAnnotations(clause);
         * OWLAnnotationSubject sub = (OWLAnnotationSubject) e.getIRI();
         * //System.out.println(e+" ==> "+sub); if (clause.getValue() == null) {
         * System.err.println("Problem:"+clause); } OWLAxiom ax = null; if
         * (tag.equals("name")) { ax = fac.getOWLAnnotationAssertionAxiom(
         * trTagToAnnotationProp(tag), sub, trLiteral(clause.getValue()),
         * annotations); } else if (tag.equals("def")) { // TODO ax =
         * fac.getOWLAnnotationAssertionAxiom( trTagToAnnotationProp(tag), sub,
         * trLiteral(clause.getValue()), annotations); } else { // generic
         * //System.out.println("generic clause:"+clause); ax =
         * fac.getOWLAnnotationAssertionAxiom( trTagToAnnotationProp(tag), sub,
         * trLiteral(clause.getValue()), annotations); } // TODO synonyms return
         * ax;
         */
        return trGenericClause(e.getIRI(), tag, clause);
    }

    /**
     * Tr generic clause.
     * 
     * @param sub
     *        the sub
     * @param tag
     *        the tag
     * @param clause
     *        the clause
     * @return the oWL axiom
     */
    @SuppressWarnings("null")
    @Nullable
    protected OWLAxiom trGenericClause(@Nonnull OWLAnnotationSubject sub, @Nonnull String tag, @Nonnull Clause clause) {
        Set<OWLAnnotation> annotations = trAnnotations(clause);
        if (clause.getValue() == null) {
            LOG.error("Problem: {}", clause);
        }
        OWLAxiom ax = null;
        OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
        // System.out.println("CLAUSE: "+clause+" // TAG="+_tag);
        if (tagConstant == OboFormatTag.TAG_NAME) {
            ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub, trLiteral(clause.getValue()),
                annotations);
        } else if (tagConstant == OboFormatTag.TAG_DEF) {
            ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub, trLiteral(clause.getValue()),
                annotations);
        } else if (tagConstant == OboFormatTag.TAG_SUBSET) {
            Object v = clause.getValue();
            if (v == null) {
                // TODO: Throw Exceptions
                LOG.error("Cannot translate: {}", clause);
            } else {
                ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub, trAnnotationProp(v.toString())
                    .getIRI(), annotations);
            }
        } else if (tagConstant == OboFormatTag.TAG_PROPERTY_VALUE) {
            Collection<Object> values = clause.getValues();
            Object v = clause.getValue();
            Object v2 = clause.getValue2();
            if (v == null) {
                // TODO: Throw Exceptions
                LOG.error("Cannot translate: {}", clause);
            } else if (values.size() == 2) {
                // property_value(Rel-ID Entity-ID Qualifiers)
                ax = fac.getOWLAnnotationAssertionAxiom(trAnnotationProp((String) v), sub, trAnnotationProp(v2
                    .toString()).getIRI(), annotations);
            } else if (values.size() == 3) {
                // property_value(Rel-ID Value XSD-Type Qualifiers)
                Iterator<Object> it = clause.getValues().iterator();
                it.next();
                it.next();
                String v3String = (String) it.next();
                IRI valueIRI;
                if (v3String.startsWith("xsd:")) {
                    valueIRI = IRI.create(Namespaces.XSD + v3String.substring(4));
                } else {
                    valueIRI = IRI.create(v3String);
                }
                OWLAnnotationValue value = fac.getOWLLiteral((String) v2, OWL2Datatype.getDatatype(valueIRI));
                ax = fac.getOWLAnnotationAssertionAxiom(trAnnotationProp((String) v), sub, value, annotations);
            } else {
                LOG.error("Cannot translate: {}", clause);
                // TODO
            }
        } else if (tagConstant == OboFormatTag.TAG_SYNONYM) {
            Object[] values = clause.getValues().toArray();
            String synType;
            if (values.length > 1) {
                synType = values[1].toString();
                if (values.length > 2) {
                    OWLAnnotation ann = fac.getOWLAnnotation(trTagToAnnotationProp(OboFormatTag.TAG_HAS_SYNONYM_TYPE
                        .getTag()), trAnnotationProp(values[2].toString()).getIRI());
                    annotations.add(ann);
                }
            } else {
                LOG.warn("Assume 'RELATED'for missing scope in synonym clause: {}", clause);
                // we make allowances for obof1.0, where the synonym scope is
                // optional
                synType = OboFormatTag.TAG_RELATED.getTag();
            }
            ax = fac.getOWLAnnotationAssertionAxiom(trSynonymType(synType), sub, trLiteral(clause.getValue()),
                annotations);
        } else if (tagConstant == OboFormatTag.TAG_XREF) {
            Xref xref = (Xref) clause.getValue();
            String xrefAnnotation = xref.getAnnotation();
            if (xrefAnnotation != null) {
                OWLAnnotation owlAnnotation = fac.getOWLAnnotation(fac.getRDFSLabel(), fac.getOWLLiteral(
                    xrefAnnotation));
                annotations.add(owlAnnotation);
            }
            ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub, trLiteral(clause.getValue()),
                annotations);
        } else {
            // generic
            // System.out.println("generic clause:"+clause);
            ax = fac.getOWLAnnotationAssertionAxiom(trTagToAnnotationProp(tag), sub, trLiteral(clause.getValue()),
                annotations);
        }
        return ax;
    }

    /**
     * Tr synonym type.
     * 
     * @param type
     *        the type
     * @return the oWL annotation property
     */
    protected OWLAnnotationProperty trSynonymType(@Nonnull String type) {
        if (type.equals(OboFormatTag.TAG_RELATED.getTag()) || type.equals(OboFormatTag.TAG_EXACT.getTag()) || type
            .equals(OboFormatTag.TAG_NARROW.getTag()) || type.equals(OboFormatTag.TAG_BROAD.getTag())) {
            return trTagToAnnotationProp(type);
        }
        return trAnnotationProp(type);
    }

    /**
     * Tr annotations.
     * 
     * @param clause
     *        the clause
     * @return the sets the
     */
    @Nonnull
    protected Set<OWLAnnotation> trAnnotations(@Nonnull Clause clause) {
        if (clause.hasNoAnnotations()) {
            return CollectionFactory.createSet();
        }
        Set<OWLAnnotation> anns = new HashSet<>();
        trAnnotations(clause, anns);
        return anns;
    }

    /**
     * Tr annotations.
     * 
     * @param clause
     *        the clause
     * @param anns
     *        the anns
     */
    @SuppressWarnings("null")
    protected void trAnnotations(@Nonnull Clause clause, @Nonnull Set<OWLAnnotation> anns) {
        Collection<Xref> xrefs = clause.getXrefs();
        for (Xref x : xrefs) {
            if (x.getIdref() != null && !x.getIdref().isEmpty()) {
                OWLAnnotationProperty ap = trTagToAnnotationProp(OboFormatTag.TAG_XREF.getTag());
                OWLAnnotation ann = fac.getOWLAnnotation(ap, trLiteral(x));
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
            OWLAnnotation ann = fac.getOWLAnnotation(ap, trLiteral(qv.getValue()));
            anns.add(ann);
        }
    }

    /**
     * Tr annotations.
     * 
     * @param clauses
     *        the clauses
     * @return the set of annotations
     */
    @Nullable
    protected Set<? extends OWLAnnotation> trAnnotations(@Nonnull Collection<Clause> clauses) {
        Set<OWLAnnotation> anns = new HashSet<>();
        for (Clause clause : clauses) {
            assert clause != null;
            trAnnotations(clause, anns);
        }
        return anns;
    }

    /**
     * Tr rel.
     * 
     * @param relId
     *        the rel id
     * @param classId
     *        the class id
     * @param quals
     *        the quals
     * @return the oWL class expression
     */
    public OWLClassExpression trRel(@Nonnull String relId, @Nonnull String classId,
        @Nonnull Collection<QualifierValue> quals) {
        Frame relFrame = obodoc.getTypedefFrame(relId);
        OWLObjectPropertyExpression pe = trObjectProp(relId);
        OWLClassExpression ce = trClass(classId);
        assert pe != null;
        assert ce != null;
        Integer exact = getQVInt("cardinality", quals);
        Integer min = getQVInt("minCardinality", quals);
        Integer max = getQVInt("maxCardinality", quals);
        boolean allSome = getQVBoolean("all_some", quals);
        boolean allOnly = getQVBoolean("all_only", quals);
        // obo-format allows dangling references to classes in class
        // expressions;
        // create an explicit class declaration to be sure
        if (ce instanceof OWLClass) {
            add(fac.getOWLDeclarationAxiom((OWLClass) ce));
        }
        OWLClassExpression ex;
        if (exact != null && exact > 0) {
            ex = fac.getOWLObjectExactCardinality(exact, pe, ce);
        } else if (exact != null && exact == 0 || max != null && max == 0) {
            OWLObjectComplementOf ceCompl = fac.getOWLObjectComplementOf(ce);
            ex = fac.getOWLObjectAllValuesFrom(pe, ceCompl);
        } else if (max != null && min != null) {
            ex = fac.getOWLObjectIntersectionOf(fac.getOWLObjectMinCardinality(min, pe, ce), fac
                .getOWLObjectMaxCardinality(max, pe, ce));
        } else if (min != null) {
            ex = fac.getOWLObjectMinCardinality(min, pe, ce);
        } else if (max != null) {
            ex = fac.getOWLObjectMaxCardinality(max, pe, ce);
        } else if (allSome && allOnly) {
            ex = fac.getOWLObjectIntersectionOf(fac.getOWLObjectSomeValuesFrom(pe, ce), fac.getOWLObjectAllValuesFrom(
                pe, ce));
        } else if (allOnly) {
            ex = fac.getOWLObjectAllValuesFrom(pe, ce);
        } else if (relFrame != null && Boolean.TRUE.equals(relFrame.getTagValue(OboFormatTag.TAG_IS_CLASS_LEVEL_TAG))) {
            // pun
            ex = fac.getOWLObjectHasValue(pe, trIndividual(classId));
        } else {
            // default
            ex = fac.getOWLObjectSomeValuesFrom(pe, ce);
        }
        return ex;
    }

    /**
     * Gets the qV string.
     * 
     * @param q
     *        the q
     * @param quals
     *        the quals
     * @return the qV string
     */
    @Nullable
    protected static String getQVString(String q, @Nonnull Collection<QualifierValue> quals) {
        for (QualifierValue qv : quals) {
            if (qv.getQualifier().equals(q)) {
                return qv.getValue();
            }
        }
        return null;
    }

    /**
     * Gets the qV boolean.
     * 
     * @param q
     *        the q
     * @param quals
     *        the quals
     * @return the qV boolean
     */
    protected static boolean getQVBoolean(String q, @Nonnull Collection<QualifierValue> quals) {
        for (QualifierValue qv : quals) {
            if (qv.getQualifier().equals(q)) {
                Object v = qv.getValue();
                return Boolean.parseBoolean((String) v);
            }
        }
        return false;
    }

    /**
     * Gets the qV int.
     * 
     * @param q
     *        the q
     * @param quals
     *        the quals
     * @return the qV int
     */
    @Nullable
    protected static Integer getQVInt(String q, @Nonnull Collection<QualifierValue> quals) {
        for (QualifierValue qv : quals) {
            if (qv.getQualifier().equals(q)) {
                Object v = qv.getValue();
                return Integer.valueOf((String) v);
            }
        }
        return null;
    }

    /**
     * Tr class.
     * 
     * @param classId
     *        the class id
     * @return the oWL class
     */
    protected OWLClass trClass(@Nonnull String classId) {
        IRI iri = oboIdToIRI(classId);
        return fac.getOWLClass(iri);
    }

    /**
     * Tr class.
     * 
     * @param v
     *        the v
     * @return the oWL class expression
     */
    protected OWLClassExpression trClass(@Nonnull Object v) {
        return trClass((String) v);
    }

    /**
     * See section "header macros" and treat-xrefs-as-equivalent.
     * 
     * @param id
     *        the id
     * @return mapped id
     */
    @Nonnull
    protected String mapPropId(@Nonnull String id) {
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
     * Gets the id prefix.
     * 
     * @param x
     *        the x
     * @return the id prefix
     */
    protected static String getIdPrefix(@Nonnull String x) {
        String[] parts = x.split(":", 2);
        return parts[0];
    }

    /**
     * Tr individual.
     * 
     * @param instId
     *        the inst id
     * @return the oWL individual
     */
    @Nonnull
    protected OWLIndividual trIndividual(@Nonnull String instId) {
        IRI iri = oboIdToIRI(instId);
        return fac.getOWLNamedIndividual(iri);
    }

    /**
     * Tr tag to iri.
     * 
     * @param tag
     *        the tag
     * @return the iri
     */
    @Nonnull
    public static IRI trTagToIRI(String tag) {
        IRI iri = ANNOTATIONPROPERTYMAP.get(tag);
        if (iri == null) {
            iri = IRI.create(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX + tag);
        }
        return iri;
    }

    /**
     * Tr tag to annotation prop.
     * 
     * @param tag
     *        the tag
     * @return the oWL annotation property
     */
    @Nonnull
    protected OWLAnnotationProperty trTagToAnnotationProp(@Nonnull String tag) {
        IRI iri = trTagToIRI(tag);
        OWLAnnotationProperty ap = fac.getOWLAnnotationProperty(iri);
        if (!apToDeclare.contains(ap)) {
            apToDeclare.add(ap);
            add(fac.getOWLDeclarationAxiom(ap));
            Obo2OWLVocabulary vocab = Obo2OWLConstants.getVocabularyObj(tag);
            if (vocab != null) {
                add(fac.getOWLAnnotationAssertionAxiom(fac.getRDFSLabel(), iri, trLiteral(vocab.getLabel())));
            }
        }
        return ap;
    }

    /**
     * Adds the declared annotation properties.
     * 
     * @param declaredProperties
     *        the declared properties
     */
    protected void addDeclaredAnnotationProperties(@Nullable Collection<OWLAnnotationProperty> declaredProperties) {
        if (declaredProperties != null) {
            apToDeclare.addAll(declaredProperties);
        }
    }

    /**
     * Tr annotation prop.
     * 
     * @param relId
     *        the rel id
     * @return the oWL annotation property
     */
    @Nonnull
    protected OWLAnnotationProperty trAnnotationProp(@Nonnull String relId) {
        IRI iri = oboIdToIRI(mapPropId(relId));
        return fac.getOWLAnnotationProperty(iri);
    }

    /**
     * Tr object prop.
     * 
     * @param relId
     *        the rel id
     * @return the oWL object property
     */
    @Nonnull
    protected OWLObjectProperty trObjectProp(@Nonnull String relId) {
        IRI iri = oboIdToIRI(mapPropId(relId));
        return fac.getOWLObjectProperty(iri);
    }

    /**
     * Tr object prop.
     * 
     * @param v
     *        the v
     * @return the oWL object property expression
     */
    @Nonnull
    protected OWLObjectPropertyExpression trObjectProp(@Nonnull Object v) {
        IRI iri = oboIdToIRI(mapPropId((String) v));
        return fac.getOWLObjectProperty(iri);
    }

    /**
     * Tr literal.
     * 
     * @param inputValue
     *        the value
     * @return the oWL annotation value
     */
    @Nonnull
    protected OWLAnnotationValue trLiteral(@Nonnull Object inputValue) {
        Object value = inputValue;
        if (value instanceof Xref) {
            value = ((Xref) value).getIdref();
        } else if (value instanceof Date) {
            // use proper OWL2 data type, write lexical value as ISO 8601 date
            // string
            String lexicalValue = Obo2OWLConstants.format((Date) value);
            return fac.getOWLLiteral(lexicalValue, OWL2Datatype.XSD_DATE_TIME);
        } else if (value instanceof Boolean) {
            return fac.getOWLLiteral((Boolean) value);
        } else if (!(value instanceof String)) {
            // TODO
            // e.g. boolean
            value = value.toString();
        }
        String value2 = (String) value;
        assert value2 != null;
        return fac.getOWLLiteral(value2);// TODO
    }

    /**
     * Obo id to iri.
     * 
     * @param id
     *        the id
     * @return the iri
     */
    @Nonnull
    public IRI oboIdToIRI(@Nonnull String id) {
        try {
            return idToIRICache.get(id);
        } catch (ExecutionException | UncheckedExecutionException e) {
            if (e.getCause() instanceof OWLParserException) {
                throw (OWLParserException) e.getCause();
            }
            LOG.error("error executing obo id to IRI", e);
            return oboIdToIRI_load(id);
        }
    }

    /**
     * Obo id to iri.
     * 
     * @param id
     *        the id
     * @return the iri
     */
    @Nonnull
    public IRI oboIdToIRI_load(@Nonnull String id) {
        if (id.contains(" ")) {
            LOG.error("id contains space: \"{}\"", id);
            throw new OWLParserException("spaces not allowed: '" + id + '\'');
        }
        // No conversion is required if this is already an IRI (ID-as-URI rule)
        if (id.startsWith("http:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        } else if (id.startsWith("https:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        } else if (id.startsWith("ftp:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
        } else if (id.startsWith("urn:")) {
            // TODO - roundtrip from other schemes
            return IRI.create(id);
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
        } else {// == 1
                // todo use owlOntology IRI
            db = getDefaultIDSpace() + '#';
            // if(id.contains("_"))
            // db += "_";
            localId = idParts[0];// Unprefixed-ID
        }
        String uriPrefix = DEFAULT_IRI_PREFIX + db;
        if (idSpaceMap.containsKey(db)) {
            uriPrefix = idSpaceMap.get(db);
        }
        String safeId;
        try {
            safeId = java.net.URLEncoder.encode(localId, "US-ASCII");
        } catch (UnsupportedEncodingException e1) {
            throw new OWLRuntimeException(e1);
        }
        if (safeId.contains(" ")) {
            safeId = safeId.replace(" ", "_");
        }
        IRI iri = null;
        try {
            iri = IRI.create(uriPrefix + safeId);
        } catch (IllegalArgumentException e) {
            throw new OWLRuntimeException(e);
        }
        return iri;
    }

    // 5.9.3. Special Rules for Relations
    /**
     * Translate shorthand id to expanded id.
     * 
     * @param id
     *        the id
     * @return the string
     */
    @Nonnull
    protected String translateShorthandIdToExpandedId(@Nonnull String id) {
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
            // System.err.println("ID:"+id+" xref:"+xref);
            if (xref != null) {
                String xid = xref.getIdref();
                // System.err.println(" ID:"+id+" xid:"+xid);
                if (xid.equals(id)) {
                    continue;
                }
                if (matchingExpandedId == null) {
                    matchingExpandedId = xid;
                } else {
                    // RO and BFO take precedence over others
                    if (xid.startsWith("RO") || xid.startsWith("BFO")) {
                        matchingExpandedId = xid;
                    }
                }
            }
        }
        if (matchingExpandedId == null) {
            return id;
        }
        // System.err.println(" ID:"+id+" matching:"+matchingExpandedId);
        return matchingExpandedId;
    }

    /**
     * Gets the default id space.
     * 
     * @return the default id space
     */
    @Nonnull
    protected String getDefaultIDSpace() {
        return defaultIDSpace;
    }
}
