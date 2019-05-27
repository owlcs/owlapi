package org.obolibrary.oboformat.parser;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.FrameMergeException;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * Implements the OBO Format 1.4 specification.
 */
public class OBOFormatParser {

    static final Logger LOG = LoggerFactory.getLogger(OBOFormatParser.class);
    protected final MyStream stream;
    private final LoadingCache<String, String> stringCache;
    private boolean followImport;
    private Object location;
    private ConcurrentHashMap<String, OBODoc> importCache = new ConcurrentHashMap<>();

    /**
    *
    */
    public OBOFormatParser() {
        this(new MyStream(), Collections.emptyMap());
    }

    /**
     * @param importsMap import map
     */
    public OBOFormatParser(Map<String, OBODoc> importsMap) {
        this(new MyStream(), importsMap);
    }

    /**
     * @param s input stream
     * @param importsMap import map
     */
    @SuppressWarnings("null")
    protected OBOFormatParser(MyStream s, Map<String, OBODoc> importsMap) {
        stream = s;
        importCache.putAll(importsMap);
        Caffeine<String, String> builder = Caffeine.newBuilder().maximumWeight(8388608)
            .weigher((String key, String value) -> key.length());
        if (LOG.isDebugEnabled()) {
            builder.recordStats();
        }
        stringCache = builder.build(key -> key);
    }

    private static void addOboNamespace(@Nullable Collection<Frame> frames,
        String defaultOboNamespace) {
        if (frames != null && !frames.isEmpty()) {
            for (Frame termFrame : frames) {
                Clause clause = termFrame.getClause(OboFormatTag.TAG_NAMESPACE);
                if (clause == null) {
                    clause = new Clause(OboFormatTag.TAG_NAMESPACE, defaultOboNamespace);
                    termFrame.addClause(clause);
                }
            }
        }
    }

    private static String mapDeprecatedTag(String tag) {
        if ("inverse_of_on_instance_level".equals(tag)) {
            return OboFormatTag.TAG_INVERSE_OF.getTag();
        }
        if ("xref_analog".equals(tag)) {
            return OboFormatTag.TAG_XREF.getTag();
        }
        if ("xref_unknown".equals(tag)) {
            return OboFormatTag.TAG_XREF.getTag();
        }
        if ("instance_level_is_transitive".equals(tag)) {
            return OboFormatTag.TAG_IS_TRANSITIVE.getTag();
        }
        return tag;
    }

    private static String removeTrailingWS(String s) {
        return s.replaceAll("\\s*$", "");
    }

    /**
     * @param key key for the import
     * @param doc document
     * @return true if the key is new
     */
    public boolean addImport(String key, OBODoc doc) {
        return importCache.put(key, doc) == null;
    }

    /**
     * @param r r
     */
    public void setReader(BufferedReader r) {
        stream.reader = r;
    }

    /**
     * @return follow imports
     */
    public boolean getFollowImports() {
        return followImport;
    }

    /**
     * @param followImports followImports
     */
    public void setFollowImports(boolean followImports) {
        followImport = followImports;
    }

    /**
     * Parses a local file or URL to an OBODoc.
     *
     * @param fn fn
     * @return parsed obo document
     * @throws IOException io exception
     * @throws OBOFormatParserException parser exception
     */
    public OBODoc parse(String fn) throws IOException {
        if (fn.startsWith("http:")) {
            return parse(new URL(fn));
        }
        return parse(new File(fn));
    }

    /**
     * Parses a local file to an OBODoc.
     *
     * @param file file
     * @return parsed obo document
     * @throws IOException io exception
     * @throws OBOFormatParserException parser exception
     */
    public OBODoc parse(File file) throws IOException {
        location = file;
        try (FileInputStream f = new FileInputStream(file);
            InputStreamReader in2 = new InputStreamReader(f, StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(in2);) {
            return parse(in);
        }
    }

    /**
     * Parses a remote URL to an OBODoc.
     *
     * @param url url
     * @return parsed obo document
     * @throws IOException io exception
     * @throws OBOFormatParserException parser exception
     */
    public OBODoc parse(URL url) throws IOException {
        location = url;
        BufferedReader in =
            new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        return parse(in);
    }

    /**
     * Parses a remote URL to an OBODoc.
     *
     * @param urlstr urlstr
     * @return parsed obo document
     * @throws IOException io exception
     * @throws OBOFormatParserException parser exception
     */
    public OBODoc parseURL(String urlstr) throws IOException {
        URL url = new URL(urlstr);
        return parse(url);
    }

    // ----------------------------------------
    // GRAMMAR
    // ----------------------------------------

    private String resolvePath(String inputPath) {
        String path = inputPath;
        if (!(path.startsWith("http:") || path.startsWith("file:") || path.startsWith("https:"))) {
            // path is not absolue then guess it.
            if (location != null) {
                if (location instanceof URL) {
                    URL url = (URL) location;
                    String p = url.toString();
                    int index = p.lastIndexOf('/');
                    path = p.substring(0, index + 1) + path;
                } else {
                    File f = new File(location.toString());
                    f = new File(f.getParent(), path);
                    path = f.toURI().toString();
                }
            }
        }
        return path;
    }

    /**
     * @param reader reader
     * @return parsed obo document
     * @throws IOException io exception
     * @throws OBOFormatParserException parser exception
     */
    public OBODoc parse(Reader reader) throws IOException {
        setReader(new BufferedReader(reader));
        OBODoc obodoc = new OBODoc();
        parseOBODoc(obodoc);
        // handle imports
        Frame hf = obodoc.getHeaderFrame();
        List<OBODoc> imports = new LinkedList<>();
        if (hf != null) {
            for (Clause cl : hf.getClauses(OboFormatTag.TAG_IMPORT)) {
                String path = resolvePath(cl.getValue(String.class));
                // TBD -- changing the relative path to absolute
                cl.setValue(path);
                if (followImport) {
                    // resolve OboDoc documents from import paths.
                    OBODoc doc = importCache.get(path);
                    if (doc == null) {
                        OBOFormatParser parser = new OBOFormatParser(importCache);
                        doc = parser.parseURL(path);
                    }
                    imports.add(doc);
                }
            }
            obodoc.setImportedOBODocs(imports);
        }
        return obodoc;
    }

    /**
     * @param obodoc obodoc
     * @throws OBOFormatParserException parser exception
     */
    public void parseOBODoc(OBODoc obodoc) {
        Frame h = new Frame(FrameType.HEADER);
        obodoc.setHeaderFrame(h);
        parseHeaderFrame(h);
        h.freeze();
        parseZeroOrMoreWsOptCmtNl();
        while (!stream.eof()) {
            parseEntityFrame(obodoc);
            parseZeroOrMoreWsOptCmtNl();
        }
        // set OBO namespace in frames
        String defaultOboNamespace =
            h.getTagValue(OboFormatTag.TAG_DEFAULT_NAMESPACE, String.class);
        if (defaultOboNamespace != null) {
            addOboNamespace(obodoc.getTermFrames(), defaultOboNamespace);
            addOboNamespace(obodoc.getTypedefFrames(), defaultOboNamespace);
            addOboNamespace(obodoc.getInstanceFrames(), defaultOboNamespace);
        }
    }

    /**
     * @param doc doc
     * @return list of references
     * @throws OBOFormatDanglingReferenceException dangling reference error
     */
    public List<String> checkDanglingReferences(OBODoc doc) {
        List<String> danglingReferences = new ArrayList<>();
        // check term frames
        for (Frame f : doc.getTermFrames()) {
            for (String tag : f.getTags()) {
                OboFormatTag tagconstant = OBOFormatConstants.getTag(tag);
                Clause c = f.getClause(tag);
                validate(doc, danglingReferences, f, tag, tagconstant, c);
            }
        }
        // check typedef frames
        for (Frame f : doc.getTypedefFrames()) {
            for (String tag : f.getTags()) {
                OboFormatTag tagConstant = OBOFormatConstants.getTag(tag);
                Clause c = f.getClause(tag);
                validate1(doc, danglingReferences, f, tag, tagConstant, c);
            }
        }
        return danglingReferences;
    }

    protected void validate1(OBODoc doc, List<String> danglingReferences, Frame f, String tag,
        @Nullable OboFormatTag tagConstant, @Nullable Clause c) {
        if (c != null) {
            if (OboFormatTag.TYPEDEF_FRAMES.contains(tagConstant)) {
                String error = checkRelation(c.getValue(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
            } else if (tagConstant == OboFormatTag.TAG_HOLDS_OVER_CHAIN
                || tagConstant == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN
                || tagConstant == OboFormatTag.TAG_RELATIONSHIP) {
                String error = checkRelation(c.getValue(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
                error = checkRelation(c.getValue2(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
            } else if (tagConstant == OboFormatTag.TAG_DOMAIN
                || tagConstant == OboFormatTag.TAG_RANGE) {
                String error = checkClassReference(c.getValue(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
            }
        }
    }

    protected void validate(OBODoc doc, List<String> danglingReferences, Frame f, String tag,
        @Nullable OboFormatTag tagconstant, @Nullable Clause c) {
        if (c != null && OboFormatTag.TERM_FRAMES.contains(tagconstant)) {
            if (c.getValues().size() > 1) {
                String error = checkRelation(c.getValue(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
                error = checkClassReference(c.getValue2(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
            } else {
                String error = checkClassReference(c.getValue(String.class), tag, f.getId(), doc);
                if (error != null) {
                    danglingReferences.add(error);
                }
            }
        }
    }

    @Nullable
    private String checkRelation(String relId, String tag, @Nullable String frameId, OBODoc doc) {
        if (doc.getTypedefFrame(relId, followImport) == null) {
            return "The relation '" + relId + "' reference in" + " the tag '" + tag
                + " ' in the frame of id '" + frameId + "' is not declared";
        }
        return null;
    }

    @Nullable
    private String checkClassReference(String classId, String tag, @Nullable String frameId,
        OBODoc doc) {
        if (doc.getTermFrame(classId, followImport) == null) {
            return "The class '" + classId + "' reference in" + " the tag '" + tag
                + " ' in the frame of id '" + frameId + "'is not declared";
        }
        return null;
    }

    /**
     * @param h h
     * @throws OBOFormatParserException parser exception
     */
    public void parseHeaderFrame(Frame h) {
        while (parseHeaderClauseNl(h)) {
            // repeat while more available
        }
    }

    /**
     * header-clause ::= format-version-TVP | ... | ...
     *
     * @param h header frame
     * @return false if there are no more header clauses, other wise true
     * @throws OBOFormatParserException parser exception
     */
    protected boolean parseHeaderClauseNl(Frame h) {
        parseZeroOrMoreWsOptCmtNl();
        if (stream.peekCharIs('[') || stream.eof()) {
            return false;
        }
        parseHeaderClause(h);
        parseHiddenComment();
        forceParseNlOrEof();
        return true;
    }

    protected Clause parseHeaderClause(Frame h) {
        String t = getParseTag();
        Clause cl = new Clause(t);
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        h.addClause(cl);
        if (tag == null) {
            return parseUnquotedString(cl);
        }
        switch (tag) {
            case TAG_SYNONYMTYPEDEF:
                return parseSynonymTypedef(cl);
            case TAG_SUBSETDEF:
                return parseSubsetdef(cl);
            case TAG_DATE:
                return parseHeaderDate(cl);
            case TAG_PROPERTY_VALUE:
                parsePropertyValue(cl);
                return parseQualifierAndHiddenComment(cl);
            case TAG_IMPORT:
                return parseImport(cl);
            case TAG_IDSPACE:
                return parseIdSpace(cl);
            // $CASES-OMITTED$
            default:
                return parseUnquotedString(cl);
        }
    }

    protected Clause parseQualifierAndHiddenComment(Clause cl) {
        parseZeroOrMoreWs();
        parseQualifierBlock(cl);
        parseHiddenComment();
        return cl;
    }

    // ----------------------------------------
    // [Term] Frames
    // ----------------------------------------

    /**
     * @param obodoc obodoc
     * @throws OBOFormatParserException parser exception
     */
    public void parseEntityFrame(OBODoc obodoc) {
        parseZeroOrMoreWsOptCmtNl();
        String rest = stream.rest();
        if (rest.startsWith("[Term]")) {
            parseTermFrame(obodoc);
        } else if (rest.startsWith("[Instance]")) {
            LOG.error("Error: Instance frames are not supported yet. Parsing stopped at line: {}",
                Integer.valueOf(stream.getLineNo()));
            while (!stream.eof()) {
                stream.advanceLine();
            }
        } else {
            parseTypedefFrame(obodoc);
        }
    }

    /**
     * term-frame ::= nl* '[Term]' nl id-Tag Class-ID EOL { term-frame-clause EOL }.
     *
     * @param obodoc obodoc
     * @throws OBOFormatParserException parser exception
     */
    public void parseTermFrame(OBODoc obodoc) {
        Frame f = new Frame(FrameType.TERM);
        parseZeroOrMoreWsOptCmtNl();
        if (stream.consume("[Term]")) {
            forceParseNlOrEof();
            parseIdLine(f);
            parseZeroOrMoreWsOptCmtNl();
            while (true) {
                if (stream.eof() || stream.peekCharIs('[')) {
                    // reached end of file or new stanza
                    break;
                }
                parseTermFrameClauseEOL(f);
                parseZeroOrMoreWsOptCmtNl();
            }
            try {
                f.freeze();
                obodoc.addFrame(f);
            } catch (FrameMergeException e) {
                throw new OBOFormatParserException(
                    "Could not add frame " + f + " to document, duplicate frame definition?", e,
                    stream.lineNo, stream.line);
            }
        } else {
            error("Expected a [Term] frame, but found unknown stanza type.");
        }
    }

    /**
     * @param f f
     * @throws OBOFormatParserException parser exception
     */
    protected void parseTermFrameClauseEOL(Frame f) {
        // comment line:
        if (stream.peekCharIs('!')) {
            parseHiddenComment();
            forceParseNlOrEof();
        } else {
            Clause cl = parseTermFrameClause();
            parseEOL(cl);
            f.addClause(cl);
        }
    }

    // ----------------------------------------
    // [Typedef] Frames
    // ----------------------------------------

    /**
     * @return parsed clause
     * @throws OBOFormatParserException parser exception
     */
    public Clause parseTermFrameClause() {
        String t = getParseTag();
        Clause cl = new Clause(t);
        if (parseDeprecatedSynonym(t, cl)) {
            return cl;
        }
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        if (tag == null) {
            // Treat unexpected tags as custom tags
            return parseCustomTag(cl);
        }
        switch (tag) {
            case TAG_IS_ANONYMOUS:
            case TAG_BUILTIN:
            case TAG_IS_OBSELETE:
                return parseBoolean(cl);
            case TAG_NAME:
            case TAG_COMMENT:
            case TAG_CREATED_BY:
                return parseUnquotedString(cl);
            case TAG_NAMESPACE:
            case TAG_ALT_ID:
            case TAG_IS_A:
            case TAG_UNION_OF:
            case TAG_EQUIVALENT_TO:
            case TAG_DISJOINT_FROM:
            case TAG_REPLACED_BY:
            case TAG_CONSIDER:
                return parseIdRef(cl);
            case TAG_DEF:
                return parseDef(cl);
            case TAG_SUBSET:
                // in the obof1.4 spec, subsets may not contain spaces.
                // unfortunately OE does not prohibit this, so subsets with spaces
                // frequently escape. We should either allow spaces in the spec
                // (which complicates parsing) or forbid them and reject all obo
                // documents that do not conform. Unfortunately that would limit
                // the utility of this parser, so for now we allow spaces. We may
                // make it strict again when community is sufficiently forewarned.
                // (alternatively we may add smarts to OE to translate the spaces to
                // underscores, so it's a one-off translation)
                return parseUnquotedString(cl);
            case TAG_SYNONYM:
                return parseSynonym(cl);
            case TAG_XREF:
                return parseDirectXref(cl);
            case TAG_PROPERTY_VALUE:
                return parsePropertyValue(cl);
            case TAG_INTERSECTION_OF:
                return parseTermIntersectionOf(cl);
            case TAG_RELATIONSHIP:
                return parseRelationship(cl);
            case TAG_CREATION_DATE:
                return parseISODate(cl);
            // $CASES-OMITTED$
            default:
                // Treat unexpected tags as custom tags
                return parseCustomTag(cl);
        }
    }

    /**
     * Typedef-frame ::= nl* '[Typedef]' nl id-Tag Class-ID EOL { Typedef-frame-clause EOL }.
     *
     * @param obodoc obodoc
     * @throws OBOFormatParserException parser exception
     */
    public void parseTypedefFrame(OBODoc obodoc) {
        Frame f = new Frame(FrameType.TYPEDEF);
        parseZeroOrMoreWsOptCmtNl();
        if (stream.consume("[Typedef]")) {
            forceParseNlOrEof();
            parseIdLine(f);
            parseZeroOrMoreWsOptCmtNl();
            while (true) {
                if (stream.eof() || stream.peekCharIs('[')) {
                    // reached end of file or new stanza
                    break;
                }
                parseTypedefFrameClauseEOL(f);
                parseZeroOrMoreWsOptCmtNl();
            }
            try {
                f.freeze();
                obodoc.addFrame(f);
            } catch (FrameMergeException e) {
                throw new OBOFormatParserException(
                    "Could not add frame " + f + " to document, duplicate frame definition?", e,
                    stream.lineNo, stream.line);
            }
        } else {
            error("Expected a [Typedef] frame, but found unknown stanza type.");
        }
    }

    /**
     * @param f f
     * @throws OBOFormatParserException parser exception
     */
    protected void parseTypedefFrameClauseEOL(Frame f) {
        // comment line:
        if (stream.peekCharIs('!')) {
            parseHiddenComment();
            forceParseNlOrEof();
        } else {
            Clause cl = parseTypedefFrameClause();
            parseEOL(cl);
            f.addClause(cl);
        }
    }

    /**
     * @return parsed clause
     * @throws OBOFormatParserException parser exception
     */
    public Clause parseTypedefFrameClause() {
        String t = getParseTag();
        if ("is_metadata".equals(t)) {
            LOG.info("is_metadata DEPRECATED; switching to is_metadata_tag");
            t = OboFormatTag.TAG_IS_METADATA_TAG.getTag();
        }
        Clause cl = new Clause(t);
        if (parseDeprecatedSynonym(t, cl)) {
            return cl;
        }
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        if (tag == null) {
            // Treat unexpected tags as custom tags
            return parseCustomTag(cl);
        }
        switch (tag) {
            case TAG_IS_ANONYMOUS:
            case TAG_BUILTIN:
            case TAG_IS_OBSELETE:
            case TAG_IS_ANTI_SYMMETRIC:
            case TAG_IS_CYCLIC:
            case TAG_IS_REFLEXIVE:
            case TAG_IS_SYMMETRIC:
            case TAG_IS_ASYMMETRIC:
            case TAG_IS_TRANSITIVE:
            case TAG_IS_FUNCTIONAL:
            case TAG_IS_INVERSE_FUNCTIONAL:
            case TAG_IS_METADATA_TAG:
            case TAG_IS_CLASS_LEVEL_TAG:
                return parseBoolean(cl);
            case TAG_NAME:
            case TAG_COMMENT:
            case TAG_CREATED_BY:
                return parseUnquotedString(cl);
            case TAG_NAMESPACE:
            case TAG_ALT_ID:
            case TAG_SUBSET:
            case TAG_IS_A:
            case TAG_UNION_OF:
            case TAG_EQUIVALENT_TO:
            case TAG_DISJOINT_FROM:
            case TAG_REPLACED_BY:
            case TAG_CONSIDER:
            case TAG_INVERSE_OF:
            case TAG_TRANSITIVE_OVER:
            case TAG_DISJOINT_OVER:
            case TAG_DOMAIN:
            case TAG_RANGE:
                return parseIdRef(cl);
            case TAG_DEF:
                return parseDef(cl);
            case TAG_SYNONYM:
                return parseSynonym(cl);
            case TAG_XREF:
                return parseDirectXref(cl);
            case TAG_PROPERTY_VALUE:
                return parsePropertyValue(cl);
            case TAG_INTERSECTION_OF:
                return parseTypedefIntersectionOf(cl);
            case TAG_RELATIONSHIP:
                return parseRelationship(cl);
            case TAG_CREATION_DATE:
                return parseISODate(cl);
            case TAG_HOLDS_OVER_CHAIN:
            case TAG_EQUIVALENT_TO_CHAIN:
                return parseIdRefPair(cl);
            case TAG_EXPAND_ASSERTION_TO:
            case TAG_EXPAND_EXPRESSION_TO:
                return parseOwlDef(cl);
            // $CASES-OMITTED$
            default:
                // Treat unexpected tags as custom tags
                return parseCustomTag(cl);
        }
    }

    // ----------------------------------------
    // [Instance] Frames - TODO
    // ----------------------------------------
    // ----------------------------------------
    // TVP
    // ----------------------------------------
    private String getParseTag() {
        if (stream.eof()) {
            error("Expected an id tag, not end of file.");
        }
        if (stream.eol()) {
            error("Expected an id tag, not end of line");
        }
        int i = stream.indexOf(':');
        if (i == -1) {
            error("Could not find tag separator ':' in line.");
        }
        String tag = stream.rest().substring(0, i);
        stream.advance(i + 1);
        parseWs();
        parseZeroOrMoreWs();
        // Memory optimization
        // re-use the tag string
        OboFormatTag formatTag = OBOFormatConstants.getTag(tag);
        if (formatTag != null) {
            tag = formatTag.getTag();
        }
        return mapDeprecatedTag(tag);
    }

    private Clause parseIdRef(Clause cl) {
        return parseIdRef(cl, false);
    }

    private Clause parseIdRef(Clause cl, boolean optional) {
        String id = getParseUntil(" !{");
        if (!optional && id.length() < 1) {
            error("");
        }
        cl.addValue(id);
        return cl;
    }

    private Clause parseIdRefPair(Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        return parseIdRef(cl);
    }

    private Clause parseISODate(Clause cl) {
        String dateStr = getParseUntil(" !{");
        cl.setValue(dateStr);
        return cl;
    }

    private Clause parseSubsetdef(Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        if (stream.consume("\"")) {
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
        } else {
            error("");
        }
        return parseQualifierAndHiddenComment(cl);
    }

    private Clause parseSynonymTypedef(Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        if (stream.consume("\"")) {
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
            // TODO: handle edge case where line ends with trailing whitespace
            // and no scope
            if (stream.peekCharIs(' ')) {
                parseOneOrMoreWs();
                parseIdRef(cl, true);
                // TODO - verify that this is a valid scope
            }
        }
        return parseQualifierAndHiddenComment(cl);
    }

    private Clause parseHeaderDate(Clause cl) {
        parseZeroOrMoreWs();
        String v = getParseUntil("!");
        v = removeTrailingWS(v);
        try {
            Date date = OBOFormatConstants.headerDateFormat().parse(v);
            cl.addValue(date);
            return cl;
        } catch (ParseException e) {
            throw new OBOFormatParserException("Could not parse date from string: " + v, e,
                stream.lineNo, stream.line);
        }
    }

    private Clause parseImport(Clause cl) {
        parseZeroOrMoreWs();
        String v = getParseUntil("!{");
        v = removeTrailingWS(v);
        cl.setValue(v);
        // parse and ignore annotations for import statements
        parseZeroOrMoreWs();
        if (stream.peekCharIs('{')) {
            // do noy parse trailing qualifiers.
            getParseUntilAdv("}");
        }
        parseHiddenComment();// ignore return value, as comments are optional
        return cl;
    }

    private Clause parseIdSpace(Clause cl) {
        parseZeroOrMoreWs();
        parseIdRefPair(cl);
        parseZeroOrMoreWs();
        if (stream.peekCharIs('"')) {
            stream.consume("\"");
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
        } else {
            String desc = getParseUntil(" !{");
            cl.addValue(desc);
        }
        return parseQualifierAndHiddenComment(cl);
    }

    private Clause parseRelationship(Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        return parseIdRef(cl);
    }

    private Clause parsePropertyValue(Clause cl) {
        // parse a pair or triple
        // the first and second value, may be quoted strings
        if (stream.peekCharIs('\"')) {
            stream.consume("\"");
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
        } else {
            parseIdRef(cl);
        }
        parseOneOrMoreWs();
        if (stream.peekCharIs('\"')) {
            stream.consume("\"");
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
        } else {
            parseIdRef(cl);
        }
        // check if there is a third value to parse
        parseZeroOrMoreWs();
        if (stream.peekCharIs('\"')) {
            stream.consume("\"");
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
        } else {
            String s = getParseUntil(" !{");
            if (!s.isEmpty()) {
                cl.addValue(s);
            }
        }
        return cl;
    }

    /**
     * intersection_of-Tag Class-ID | intersection_of-Tag Relation-ID Class-ID.
     *
     * @param cl cl
     * @throws OBOFormatParserException parser exception
     */
    private Clause parseTermIntersectionOf(Clause cl) {
        parseIdRef(cl);
        // consumed the first ID
        parseZeroOrMoreWs();
        if (!stream.eol()) {
            char c = stream.peekChar();
            if (c != '!' && c != '{') {
                // try to consume the second id
                parseIdRef(cl, true);
            }
        }
        return cl;
    }

    private Clause parseTypedefIntersectionOf(Clause cl) {
        // single values only
        return parseIdRef(cl);
    }

    // ----------------------------------------
    // Synonyms
    // ----------------------------------------
    private boolean parseDeprecatedSynonym(String tag, Clause cl) {
        String scope;
        if ("exact_synonym".equals(tag)) {
            scope = OboFormatTag.TAG_EXACT.getTag();
        } else if ("narrow_synonym".equals(tag)) {
            scope = OboFormatTag.TAG_NARROW.getTag();
        } else if ("broad_synonym".equals(tag)) {
            scope = OboFormatTag.TAG_BROAD.getTag();
        } else if ("related_synonym".equals(tag)) {
            scope = OboFormatTag.TAG_RELATED.getTag();
        } else {
            return false;
        }
        cl.setTag(OboFormatTag.TAG_SYNONYM.getTag());
        if (stream.consume("\"")) {
            String syn = getParseUntilAdv("\"");
            cl.setValue(syn);
            cl.addValue(scope);
            parseZeroOrMoreWs();
            parseXrefList(cl, false);
            return true;
        }
        return false;
    }

    private Clause parseSynonym(Clause cl) {
        if (stream.consume("\"")) {
            String syn = getParseUntilAdv("\"");
            cl.setValue(syn);
            parseZeroOrMoreWs();
            if (!stream.peekCharIs('[')) {
                parseIdRef(cl, true);
                parseZeroOrMoreWs();
                if (!stream.peekCharIs('[')) {
                    parseIdRef(cl, true);
                    parseZeroOrMoreWs();
                }
            }
            parseXrefList(cl, false);
        } else {
            error("The synonym is always a quoted string.");
        }
        return cl;
    }

    // ----------------------------------------
    // Definitions
    // ----------------------------------------
    private Clause parseDef(Clause cl) {
        if (stream.consume("\"")) {
            String def = getParseUntilAdv("\"");
            cl.setValue(def);
            parseZeroOrMoreWs();
            parseXrefList(cl, true);
        } else {
            error("Definitions should always be a quoted string.");
        }
        return cl;
    }

    private Clause parseOwlDef(Clause cl) {
        if (stream.consume("\"")) {
            String def = getParseUntilAdv("\"");
            cl.setValue(def);
            parseZeroOrMoreWs();
            parseXrefList(cl, true);
        } else {
            error("The " + cl.getTag() + " clause is always a quoted string.");
        }
        return cl;
    }

    // ----------------------------------------
    // XrefLists - e.g. [A:1, B:2, ... ]
    // ----------------------------------------
    private void parseXrefList(Clause cl, boolean optional) {
        if (stream.consume("[")) {
            parseZeroOrMoreXrefs(cl);
            parseZeroOrMoreWs();
            if (!stream.consume("]")) {
                error("Missing closing ']' for xref list at pos: " + stream.pos);
            }
        } else if (!optional) {
            error("Clause: " + cl.getTag()
                + "; expected an xref list, or at least an empty list '[]' at pos: " + stream.pos);
        }
    }

    private boolean parseZeroOrMoreXrefs(Clause cl) {
        if (parseXref(cl)) {
            while (stream.consume(",") && parseXref(cl)) {
                // repeat while more available
            }
        }
        return true;
    }

    // an xref that supports a value of values in a clause
    private boolean parseXref(Clause cl) {
        parseZeroOrMoreWs();
        String id = getParseUntil("\",]!{", true);
        if (!id.isEmpty()) {
            id = removeTrailingWS(id);
            if (id.contains(" ")) {
                warn("accepting bad xref with spaces:" + id);
            }
            Xref xref = new Xref(id);
            cl.addXref(xref);
            parseZeroOrMoreWs();
            if (stream.peekCharIs('"')) {
                stream.consume("\"");
                xref.setAnnotation(getParseUntilAdv("\""));
            }
            return true;
        }
        return false;
    }

    // an xref that is a direct value of a clause
    private Clause parseDirectXref(Clause cl) {
        parseZeroOrMoreWs();
        String id = getParseUntil("\",]!{", true);
        id = id.trim();
        if (id.contains(" ")) {
            warn("accepting bad xref with spaces:<" + id + '>');
        }
        id = id.replaceAll(" +\\Z", "");
        Xref xref = new Xref(id);
        cl.addValue(xref);
        parseZeroOrMoreWs();
        if (stream.peekCharIs('"')) {
            stream.consume("\"");
            xref.setAnnotation(getParseUntilAdv("\""));
        }
        return cl;
    }

    /**
     * Qualifier Value blocks - e.g. {a="1",b="foo", ...}
     *
     * @param cl clause
     */
    private void parseQualifierBlock(Clause cl) {
        if (stream.consume("{")) {
            parseZeroOrMoreQuals(cl);
            parseZeroOrMoreWs();
            boolean success = stream.consume("}");
            if (!success) {
                error("Missing closing '}' for trailing qualifier block.");
            }
        }
    }

    private void parseZeroOrMoreQuals(Clause cl) {
        if (parseQual(cl)) {
            while (stream.consume(",") && parseQual(cl)) {
                // repeat while more available
            }
        }
    }

    private boolean parseQual(Clause cl) {
        parseZeroOrMoreWs();
        String rest = stream.rest();
        if (!rest.contains("=")) {
            error(
                "Missing '=' in trailing qualifier block. This might happen for not properly escaped '{', '}' chars in comments.");
        }
        String q = getParseUntilAdv("=");
        parseZeroOrMoreWs();
        String v;
        if (stream.consume("\"")) {
            v = getParseUntilAdv("\"");
        } else {
            v = getParseUntil(" ,}");
            warn("qualifier values should be enclosed in quotes. You have: " + q + '='
                + stream.rest());
        }
        if (v.isEmpty()) {
            warn("Empty value for qualifier in trailing qualifier block.");
            v = "";
        }
        QualifierValue qv = new QualifierValue(q, v);
        cl.addQualifierValue(qv);
        parseZeroOrMoreWs();
        return true;
    }

    // ----------------------------------------
    // Other
    // ----------------------------------------
    private Clause parseBoolean(Clause cl) {
        if (stream.consume("true")) {
            cl.setValue(Boolean.TRUE);
        } else if (stream.consume("false")) {
            cl.setValue(Boolean.FALSE);
        } else {
            error("Could not parse boolean value.");
        }
        return cl;
    }

    // ----------------------------------------
    // End-of-line matter
    // ----------------------------------------

    protected void parseIdLine(Frame f) {
        String t = getParseTag();
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        if (tag != OboFormatTag.TAG_ID) {
            error("Expected id tag as first line in frame, but was: " + tag);
        }
        Clause cl = new Clause(t);
        f.addClause(cl);
        String id = getParseUntil(" !{");
        if (id.isEmpty()) {
            error("Could not find an valid id, id is empty.");
        }
        cl.addValue(id);
        f.setId(id);
        parseEOL(cl);
    }

    /**
     * @param cl cl
     * @throws OBOFormatParserException parser exception
     */
    public void parseEOL(Clause cl) {
        parseQualifierAndHiddenComment(cl);
        forceParseNlOrEof();
    }

    private void parseHiddenComment() {
        parseZeroOrMoreWs();
        if (stream.peekCharIs('!')) {
            stream.forceEol();
        }
    }

    protected Clause parseUnquotedString(Clause cl) {
        parseZeroOrMoreWs();
        String v = getParseUntil("!{");
        // strip whitespace from the end - TODO
        v = removeTrailingWS(v);
        cl.setValue(v);
        if (stream.peekCharIs('{')) {
            parseQualifierBlock(cl);
        }
        parseHiddenComment();
        return cl;
    }

    protected Clause parseCustomTag(Clause cl) {
        return parseUnquotedString(cl);
    }

    // Newlines, whitespace
    protected void forceParseNlOrEof() {
        parseZeroOrMoreWs();
        if (stream.eol()) {
            stream.advanceLine();
            return;
        }
        if (stream.eof()) {
            return;
        }
        error("expected newline or end of line but found: " + stream.rest());
    }

    protected void parseZeroOrMoreWsOptCmtNl() {
        while (true) {
            parseZeroOrMoreWs();
            parseHiddenComment();
            if (stream.eol()) {
                stream.advanceLine();
            } else {
                return;
            }
        }
    }

    // non-newline
    protected void parseWs() {
        if (stream.eol()) {
            error("Expected at least one white space, but found end of line at pos: " + stream.pos);
        }
        if (stream.eof()) {
            error("Expected at least one white space, but found end of file.");
        }
        if (stream.peekChar() == ' ') {
            stream.advance(1);
        } else {
            warn("Expected white space at pos: " + stream.pos);
        }
    }

    protected void parseOneOrMoreWs() {
        if (stream.eol() || stream.eof()) {
            error("Expected at least one white space at pos: " + stream.pos);
        }
        int n = 0;
        while (stream.peekCharIs(' ')) {
            stream.advance(1);
            n++;
        }
        if (n == 0) {
            error("Expected at least one white space at pos: " + stream.pos);
        }
    }

    protected void parseZeroOrMoreWs() {
        if (!stream.eol() && !stream.eof()) {
            while (stream.peekCharIs(' ')) {
                stream.advance(1);
            }
        }
    }

    private String getParseUntilAdv(String compl) {
        String ret = getParseUntil(compl);
        stream.advance(1);
        return ret;
    }

    private String getParseUntil(String compl) {
        return getParseUntil(compl, false);
    }

    private String getParseUntil(String compl, boolean commaWhitespace) {
        String r = stream.rest();
        int i = 0;
        boolean hasEscapedChars = false;
        while (i < r.length()) {
            if (r.charAt(i) == '\\') {
                hasEscapedChars = true;
                i += 2;// Escape
                continue;
            }
            if (compl.contains(r.subSequence(i, i + 1))) {
                if (commaWhitespace && r.charAt(i) == ',') {
                    // a comma is only a valid separator with a following
                    // whitespace
                    // see bug and specification update
                    // http://code.google.com/p/oboformat/issues/detail?id=54
                    if (i + 1 < r.length() && r.charAt(i + 1) == ' ') {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }
        if (i == 0) {
            return "";
        }
        String ret = r.substring(0, i);
        if (hasEscapedChars) {
            ret = handleEscapedChars(ret);
        }
        stream.advance(i);
        return stringCache.get(ret);
    }

    protected String handleEscapedChars(String ret) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < ret.length(); j++) {
            char c = ret.charAt(j);
            if (c == '\\') {
                int next = j + 1;
                if (next < ret.length()) {
                    char nextChar = ret.charAt(next);
                    handleNextChar(sb, nextChar);
                    j += 1;// skip the next char
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    protected void handleNextChar(StringBuilder sb, char nextChar) {
        switch (nextChar) {
            case 'n':// newline
                sb.append('\n');
                break;
            case 'W':// single space
                sb.append(' ');
                break;
            case 't':// tab
                sb.append('\t');
                break;
            default:
                // assume that any char after a backlash is an escaped char.
                // spec for this optional behavior
                // http://www.geneontology.org/GO.format.obo-1_2.shtml#S.1.5
                sb.append(nextChar);
                break;
        }
    }

    private void error(String message) {
        throw new OBOFormatParserException(message, stream.lineNo, stream.line);
    }

    private void warn(String message) {
        LOG.warn("LINE: {} {}  LINE:\n{}", Integer.valueOf(stream.lineNo), message, stream.line);
    }

    protected static class MyStream {

        int pos = 0;
        @Nullable
        String line;
        int lineNo = 0;
        @Nullable
        BufferedReader reader;

        public MyStream() {
            pos = 0;
        }

        public MyStream(BufferedReader r) {
            reader = r;
        }

        public static String getTag() {
            return "";
        }

        protected String line() {
            return verifyNotNull(line);
        }

        protected char peekChar() {
            prepare();
            return line().charAt(pos);
        }

        public char nextChar() {
            pos++;
            return line().charAt(pos - 1);
        }

        public String rest() {
            prepare();
            if (line == null) {
                return "";
            }
            if (pos >= line().length()) {
                return "";
            }
            return line().substring(pos);
        }

        public void advance(int dist) {
            pos += dist;
        }

        public void prepare() {
            if (line == null) {
                advanceLine();
            }
        }

        public void advanceLine() {
            try {
                line = verifyNotNull(reader, "reader must be set before accessing it").readLine();
                lineNo++;
                pos = 0;
            } catch (IOException e) {
                throw new OBOFormatParserException(e, lineNo, "Error reading from input.");
            }
        }

        public void forceEol() {
            if (line == null) {
                return;
            }
            pos = line().length();
        }

        public boolean eol() {
            prepare();
            if (line == null) {
                return false;
            }
            return pos >= line().length();
        }

        public boolean eof() {
            prepare();
            return line == null;
        }

        public boolean consume(String s) {
            String r = rest();
            if (r.isEmpty()) {
                return false;
            }
            if (r.startsWith(s)) {
                pos += s.length();
                return true;
            }
            return false;
        }

        public int indexOf(char c) {
            prepare();
            if (line == null) {
                return -1;
            }
            return line().substring(pos).indexOf(c);
        }

        @Override
        public String toString() {
            return line + "//" + pos + " LINE:" + lineNo;
        }

        public boolean peekCharIs(char c) {
            if (eol() || eof()) {
                return false;
            }
            return peekChar() == c;
        }

        public int getLineNo() {
            return lineNo;
        }
    }
}
