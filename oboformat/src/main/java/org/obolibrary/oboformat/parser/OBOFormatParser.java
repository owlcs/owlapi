package org.obolibrary.oboformat.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Nonnull;
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

/** implements the OBO Format 1.4 specification. */
public class OBOFormatParser {

    static final Logger LOG = LoggerFactory.getLogger(OBOFormatParser.class);

    // TODO use this to validate date strings for OboFormatTag.TAG_CREATION_DATE
    @Nonnull
    protected SimpleDateFormat getISODateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    private boolean followImport;
    private Object location;

    protected static class MyStream {

        int pos = 0;
        String line;
        int lineNo = 0;
        BufferedReader reader;

        public MyStream() {
            pos = 0;
        }

        public MyStream(BufferedReader r) {
            reader = r;
        }

        protected char peekChar() {
            prepare();
            return line.charAt(pos);
        }

        public char nextChar() {
            pos++;
            return line.charAt(pos - 1);
        }

        @Nullable
        public String rest() {
            prepare();
            if (line == null) {
                return null;
            }
            if (pos >= line.length()) {
                return "";
            }
            return line.substring(pos);
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
                line = reader.readLine();
                lineNo++;
                pos = 0;
            } catch (IOException e) {
                throw new OBOFormatParserException(e, lineNo,
                        "Error reading from input.");
            }
        }

        public void forceEol() {
            if (line == null) {
                return;
            }
            pos = line.length();
        }

        public boolean eol() {
            prepare();
            if (line == null) {
                return false;
            }
            return pos >= line.length();
        }

        public boolean eof() {
            prepare();
            if (line == null) {
                return true;
            }
            return false;
        }

        @Nonnull
        public String getTag() {
            return "";
        }

        public boolean consume(@Nonnull String s) {
            String r = rest();
            if (r == null) {
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
            return line.substring(pos).indexOf(c);
        }

        @Nonnull
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

    protected MyStream stream;

    /**
     * 
     */
    public OBOFormatParser() {
        this(new MyStream());
    }

    protected OBOFormatParser(MyStream s) {
        super();
        stream = s;
    }

    /**
     * @param r
     *        r
     */
    public void setReader(BufferedReader r) {
        stream.reader = r;
    }

    /**
     * @param followImports
     *        followImports
     */
    public void setFollowImports(boolean followImports) {
        followImport = followImports;
    }

    /** @return follow imports */
    public boolean getFollowImports() {
        return followImport;
    }

    /**
     * Parses a local file or URL to an OBODoc.
     * 
     * @param fn
     *        fn
     * @return parsed obo document
     * @throws IOException
     *         io exception
     * @throws OBOFormatParserException
     *         parser exception
     */
    @Nonnull
    public OBODoc parse(@Nonnull String fn) throws IOException {
        if (fn.startsWith("http:")) {
            return parse(new URL(fn));
        }
        return parse(new File(fn));
    }

    /**
     * Parses a local file to an OBODoc.
     * 
     * @param file
     *        file
     * @return parsed obo document
     * @throws IOException
     *         io exception
     * @throws OBOFormatParserException
     *         parser exception
     */
    @Nonnull
    public OBODoc parse(File file) throws IOException {
        location = file;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(file),
                OBOFormatConstants.DEFAULT_CHARACTER_ENCODING));
        return parse(in);
    }

    /**
     * Parses a remote URL to an OBODoc.
     * 
     * @param url
     *        url
     * @return parsed obo document
     * @throws IOException
     *         io exception
     * @throws OBOFormatParserException
     *         parser exception
     */
    @Nonnull
    public OBODoc parse(@Nonnull URL url) throws IOException {
        location = url;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream(),
                        OBOFormatConstants.DEFAULT_CHARACTER_ENCODING));
        return parse(in);
    }

    /**
     * Parses a remote URL to an OBODoc.
     * 
     * @param urlstr
     *        urlstr
     * @return parsed obo document
     * @throws IOException
     *         io exception
     * @throws OBOFormatParserException
     *         parser exception
     */
    @Nonnull
    public OBODoc parseURL(String urlstr) throws IOException {
        URL url = new URL(urlstr);
        return parse(url);
    }

    @Nonnull
    private String resolvePath(@Nonnull String path) {
        if (!(path.startsWith("http:") || path.startsWith("file:") || path
                .startsWith("https:"))) {
            // path is not absolue then guess it.
            if (location != null) {
                if (location instanceof URL) {
                    URL url = (URL) location;
                    String p = url.toString();
                    int index = p.lastIndexOf("/");
                    path = p.substring(0, index + 1) + path;
                } else {
                    File f = new File(location + "");
                    f = new File(f.getParent(), path);
                    path = f.toURI().toString();
                }
            }
        }
        return path;
    }

    /**
     * @param reader
     *        reader
     * @return parsed obo document
     * @throws IOException
     *         io exception
     * @throws OBOFormatParserException
     *         parser exception
     */
    @Nonnull
    public OBODoc parse(BufferedReader reader) throws IOException {
        setReader(reader);
        OBODoc obodoc = new OBODoc();
        parseOBODoc(obodoc);
        // handle imports
        Frame hf = obodoc.getHeaderFrame();
        List<OBODoc> imports = new LinkedList<OBODoc>();
        if (hf != null) {
            for (Clause cl : hf.getClauses(OboFormatTag.TAG_IMPORT)) {
                String path = resolvePath(cl.getValue(String.class));
                // TBD -- changing the relative path to absolute
                cl.setValue(path);
                if (followImport) {
                    // resolve OboDoc documents from import paths.
                    OBOFormatParser parser = new OBOFormatParser();
                    OBODoc doc = parser.parseURL(path);
                    imports.add(doc);
                }/*
                  * else{ //build a proxy document which reference import path
                  * as ontology id Frame importHeaer = new
                  * Frame(FrameType.HEADER); Clause ontologyCl = new Clause();
                  * ontologyCl.setTag(OboFormatTag.TAG_ONTOLOGY.getTag());
                  * ontologyCl.setValue(path);
                  * importHeaer.addClause(ontologyCl);
                  * doc.setHeaderFrame(importHeaer); }
                  */
            }
            obodoc.setImportedOBODocs(imports);
        }
        return obodoc;
    }

    // ----------------------------------------
    // GRAMMAR
    // ----------------------------------------
    /**
     * @param obodoc
     *        obodoc
     * @throws OBOFormatParserException
     *         parser exception
     */
    public void parseOBODoc(@Nonnull OBODoc obodoc) {
        Frame h = new Frame(FrameType.HEADER);
        obodoc.setHeaderFrame(h);
        parseHeaderFrame(h);
        parseZeroOrMoreWsOptCmtNl();
        while (stream.eof() == false) {
            parseEntityFrame(obodoc);
            parseZeroOrMoreWsOptCmtNl();
        }
        // set OBO namespace in frames
        String defaultOboNamespace = h.getTagValue(
                OboFormatTag.TAG_DEFAULT_NAMESPACE, String.class);
        if (defaultOboNamespace != null) {
            addOboNamespace(obodoc.getTermFrames(), defaultOboNamespace);
            addOboNamespace(obodoc.getTypedefFrames(), defaultOboNamespace);
            addOboNamespace(obodoc.getInstanceFrames(), defaultOboNamespace);
        }
    }

    private static void addOboNamespace(@Nullable Collection<Frame> frames,
            String defaultOboNamespace) {
        if (frames != null && !frames.isEmpty()) {
            for (Frame termFrame : frames) {
                Clause clause = termFrame.getClause(OboFormatTag.TAG_NAMESPACE);
                if (clause == null) {
                    clause = new Clause(OboFormatTag.TAG_NAMESPACE,
                            defaultOboNamespace);
                    termFrame.addClause(clause);
                }
            }
        }
    }

    /**
     * @param doc
     *        doc
     * @return list of references
     * @throws OBOFormatDanglingReferenceException
     *         dangling reference error
     */
    @Nonnull
    public List<String> checkDanglingReferences(@Nonnull OBODoc doc) {
        List<String> danglingReferences = new ArrayList<String>();
        // check term frames
        for (Frame f : doc.getTermFrames()) {
            for (String tag : f.getTags()) {
                OboFormatTag _tag = OBOFormatConstants.getTag(tag);
                Clause c = f.getClause(tag);
                if (_tag == OboFormatTag.TAG_INTERSECTION_OF
                        || _tag == OboFormatTag.TAG_UNION_OF
                        || _tag == OboFormatTag.TAG_EQUIVALENT_TO
                        || _tag == OboFormatTag.TAG_DISJOINT_FROM
                        || _tag == OboFormatTag.TAG_RELATIONSHIP
                        || _tag == OboFormatTag.TAG_IS_A) {
                    if (c.getValues().size() > 1) {
                        String error = checkRelation(c.getValue(String.class),
                                tag, f.getId(), doc);
                        if (error != null) {
                            danglingReferences.add(error);
                        }
                        error = checkClassReference(c.getValue2(String.class),
                                tag, f.getId(), doc);
                        if (error != null) {
                            danglingReferences.add(error);
                        }
                    } else {
                        String error = checkClassReference(
                                c.getValue(String.class), tag, f.getId(), doc);
                        if (error != null) {
                            danglingReferences.add(error);
                        }
                    }
                }
            }
        }
        // check typedef frames
        for (Frame f : doc.getTypedefFrames()) {
            for (String tag : f.getTags()) {
                OboFormatTag _tag = OBOFormatConstants.getTag(tag);
                Clause c = f.getClause(tag);
                if (_tag == OboFormatTag.TAG_IS_A
                        || _tag == OboFormatTag.TAG_INTERSECTION_OF
                        || _tag == OboFormatTag.TAG_UNION_OF
                        || _tag == OboFormatTag.TAG_EQUIVALENT_TO
                        || _tag == OboFormatTag.TAG_DISJOINT_FROM
                        || _tag == OboFormatTag.TAG_INVERSE_OF
                        || _tag == OboFormatTag.TAG_TRANSITIVE_OVER
                        || _tag == OboFormatTag.TAG_DISJOINT_OVER) {
                    String error = checkRelation(c.getValue(String.class), tag,
                            f.getId(), doc);
                    if (error != null) {
                        danglingReferences.add(error);
                    }
                } else if (_tag == OboFormatTag.TAG_HOLDS_OVER_CHAIN
                        || _tag == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN
                        || _tag == OboFormatTag.TAG_RELATIONSHIP) {
                    String error = checkRelation(c.getValue().toString(), tag,
                            f.getId(), doc);
                    if (error != null) {
                        danglingReferences.add(error);
                    }
                    error = checkRelation(c.getValue2().toString(), tag,
                            f.getId(), doc);
                    if (error != null) {
                        danglingReferences.add(error);
                    }
                } else if (_tag == OboFormatTag.TAG_DOMAIN
                        || _tag == OboFormatTag.TAG_RANGE) {
                    String error = checkClassReference(c.getValue().toString(),
                            tag, f.getId(), doc);
                    if (error != null) {
                        danglingReferences.add(error);
                    }
                }
            }
        }
        return danglingReferences;
    }

    @Nullable
    private String checkRelation(String relId, String tag, String frameId,
            @Nonnull OBODoc doc) {
        if (doc.getTypedefFrame(relId, followImport) == null) {
            return "The relation '" + relId + "' reference in" + " the tag '"
                    + tag + " ' in the frame of id '" + frameId
                    + "' is not declared";
        }
        return null;
    }

    @Nullable
    private String checkClassReference(String classId, String tag,
            String frameId, @Nonnull OBODoc doc) {
        if (doc.getTermFrame(classId, followImport) == null) {
            return "The class '" + classId + "' reference in" + " the tag '"
                    + tag + " ' in the frame of id '" + frameId
                    + "'is not declared";
        }
        return null;
    }

    /**
     * @param h
     *        h
     * @throws OBOFormatParserException
     *         parser exception
     */
    public void parseHeaderFrame(@Nonnull Frame h) {
        while (parseHeaderClauseNl(h)) {}
    }

    /**
     * header-clause ::= format-version-TVP | ... | ...
     * 
     * @param h
     *        header frame
     * @return false if there are no more header clauses, other wise true
     * @throws OBOFormatParserException
     *         parser exception
     */
    protected boolean parseHeaderClauseNl(@Nonnull Frame h) {
        parseZeroOrMoreWsOptCmtNl();
        if (stream.peekCharIs('[') || stream.eof()) {
            return false;
        }
        parseHeaderClause(h);
        forceParseNlOrEof();
        return true;
    }

    protected void parseHeaderClause(@Nonnull Frame h) {
        String t = getParseTag();
        if (t == null) {
            error("Could not extract tag from line.");
        }
        Clause cl = new Clause(t);
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        h.addClause(cl);
        if (tag == OboFormatTag.TAG_DATA_VERSION) {
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_FORMAT_VERSION) {
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_SYNONYMTYPEDEF) {
            parseSynonymTypedef(cl);
        } else if (tag == OboFormatTag.TAG_SUBSETDEF) {
            parseSubsetdef(cl);
        } else if (tag == OboFormatTag.TAG_DATE) {
            parseHeaderDate(cl);
        } else if (tag == OboFormatTag.TAG_PROPERTY_VALUE) {
            parsePropertyValue(cl);
            parseZeroOrMoreWs();
            parseQualifierBlock(cl);
            parseHiddenComment();
        } else if (tag == OboFormatTag.TAG_IMPORT) {
            parseImport(cl);
        } else if (tag == OboFormatTag.TAG_IDSPACE) {
            parseIdSpace(cl);
        } else {
            parseUnquotedString(cl);
        }
    }

    /**
     * @param obodoc
     *        obodoc
     * @throws OBOFormatParserException
     *         parser exception
     */
    public void parseEntityFrame(@Nonnull OBODoc obodoc) {
        parseZeroOrMoreWsOptCmtNl();
        String rest = stream.rest();
        if (rest != null && rest.startsWith("[Term]")) {
            parseTermFrame(obodoc);
        } else {
            parseTypedefFrame(obodoc);
        }
    }

    // ----------------------------------------
    // [Term] Frames
    // ----------------------------------------
    /**
     * term-frame ::= nl* '[Term]' nl id-Tag Class-ID EOL { term-frame-clause
     * EOL }.
     * 
     * @param obodoc
     *        obodoc
     * @throws OBOFormatParserException
     *         parser exception
     */
    public void parseTermFrame(@Nonnull OBODoc obodoc) {
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
                obodoc.addFrame(f);
            } catch (FrameMergeException e) {
                throw new OBOFormatParserException("Could not add frame " + f
                        + " to document, duplicate frame definition?", e,
                        stream.lineNo, stream.line);
            }
        } else {
            error("Expected a [Term] frame, but found unknown stanza type.");
        }
    }

    /**
     * @param f
     *        f
     * @throws OBOFormatParserException
     *         parser exception
     */
    protected void parseTermFrameClauseEOL(@Nonnull Frame f) {
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

    /**
     * @throws OBOFormatParserException
     *         parser exception
     * @return parsed clause
     */
    @Nonnull
    public Clause parseTermFrameClause() {
        String t = getParseTag();
        if (t == null) {
            error("Could not find tag in clause.");
        }
        Clause cl = new Clause(t);
        if (parseDeprecatedSynonym(t, cl)) {
            return cl;
        }
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        if (tag == null) {
            error("Could not find tag for: " + t);
        }
        if (tag == OboFormatTag.TAG_IS_ANONYMOUS) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_NAME) {
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_NAMESPACE) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_ALT_ID) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_DEF) {
            parseDef(cl);
        } else if (tag == OboFormatTag.TAG_COMMENT) {
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_SUBSET) {
            // in the obof1.4 spec, subsets may not contain spaces.
            // unfortunately OE does not prohibit this, so subsets with spaces
            // frequently escape. We should either allow spaces in the spec
            // (with complicates parsing) or forbid them and reject all obo
            // documents
            // that do not conform. Unfortunately that would limit the utility
            // of
            // this parser, so for now we allow spaces. We may make it strict
            // again
            // when community is sufficiently forewarned.
            // (alternatively we may add smarts to OE to translate the spaces to
            // underscores,
            // so it's a one-off translation)
            //
            // return parseIdRef(cl);
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_SYNONYM) {
            parseSynonym(cl);
        } else if (tag == OboFormatTag.TAG_XREF) {
            parseDirectXref(cl);
        } else if (tag == OboFormatTag.TAG_BUILTIN) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_PROPERTY_VALUE) {
            parsePropertyValue(cl);
        } else if (tag == OboFormatTag.TAG_IS_A) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_INTERSECTION_OF) {
            parseTermIntersectionOf(cl);
        } else if (tag == OboFormatTag.TAG_UNION_OF) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_EQUIVALENT_TO) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_DISJOINT_FROM) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_RELATIONSHIP) {
            parseRelationship(cl);
        } else if (tag == OboFormatTag.TAG_CREATED_BY) {
            parsePerson(cl);
        } else if (tag == OboFormatTag.TAG_CREATION_DATE) {
            parseISODate(cl);
        } else if (tag == OboFormatTag.TAG_IS_OBSELETE) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_REPLACED_BY) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_CONSIDER) {
            parseIdRef(cl);
        } else {
            error("Unexpected tag " + tag.getTag() + " in term frame.");
        }
        return cl;
    }

    // ----------------------------------------
    // [Typedef] Frames
    // ----------------------------------------
    /**
     * Typedef-frame ::= nl* '[Typedef]' nl id-Tag Class-ID EOL {
     * Typedef-frame-clause EOL }.
     * 
     * @param obodoc
     *        obodoc
     * @throws OBOFormatParserException
     *         parser exception
     */
    public void parseTypedefFrame(@Nonnull OBODoc obodoc) {
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
                obodoc.addFrame(f);
            } catch (FrameMergeException e) {
                throw new OBOFormatParserException("Could not add frame " + f
                        + " to document, duplicate frame definition?", e,
                        stream.lineNo, stream.line);
            }
        } else {
            error("Expected a [Typedef] frame, but found unknown stanza type.");
        }
    }

    /**
     * @param f
     *        f
     * @throws OBOFormatParserException
     *         parser exception
     */
    protected void parseTypedefFrameClauseEOL(@Nonnull Frame f) {
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
     * @throws OBOFormatParserException
     *         parser exception
     */
    @Nonnull
    public Clause parseTypedefFrameClause() {
        String t = getParseTag();
        if (t == null) {
            error("Could not find tag in clause.");
        }
        if (t.equals("is_metadata")) {
            LOG.info("is_metadata DEPRECATED; switching to is_metadata_tag");
            t = OboFormatTag.TAG_IS_METADATA_TAG.getTag();
        }
        Clause cl = new Clause(t);
        if (parseDeprecatedSynonym(t, cl)) {
            return cl;
        }
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        if (tag == null) {
            error("Could not find tag for: " + t);
        }
        if (tag == OboFormatTag.TAG_IS_ANONYMOUS) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_NAME) {
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_NAMESPACE) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_ALT_ID) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_DEF) {
            parseDef(cl);
        } else if (tag == OboFormatTag.TAG_COMMENT) {
            parseUnquotedString(cl);
        } else if (tag == OboFormatTag.TAG_SUBSET) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_SYNONYM) {
            parseSynonym(cl);
        } else if (tag == OboFormatTag.TAG_XREF) {
            parseDirectXref(cl);
        } else if (tag == OboFormatTag.TAG_PROPERTY_VALUE) {
            parsePropertyValue(cl);
        } else if (tag == OboFormatTag.TAG_DOMAIN) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_RANGE) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_BUILTIN) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_ANTI_SYMMETRIC) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_CYCLIC) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_REFLEXIVE) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_SYMMETRIC) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_ASYMMETRIC) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_TRANSITIVE) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_FUNCTIONAL) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_A) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_INTERSECTION_OF) {
            parseTypedefIntersectionOf(cl);
        } else if (tag == OboFormatTag.TAG_UNION_OF) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_EQUIVALENT_TO) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_DISJOINT_FROM) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_INVERSE_OF) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_TRANSITIVE_OVER) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_HOLDS_OVER_CHAIN) {
            parseIdRefPair(cl);
        } else if (tag == OboFormatTag.TAG_EQUIVALENT_TO_CHAIN) {
            parseIdRefPair(cl);
        } else if (tag == OboFormatTag.TAG_DISJOINT_OVER) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_RELATIONSHIP) {
            parseRelationship(cl);
        } else if (tag == OboFormatTag.TAG_CREATED_BY) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_CREATION_DATE) {
            parseISODate(cl);
        } else if (tag == OboFormatTag.TAG_IS_OBSELETE) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_REPLACED_BY) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_CONSIDER) {
            parseIdRef(cl);
        } else if (tag == OboFormatTag.TAG_IS_METADATA_TAG) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_IS_CLASS_LEVEL_TAG) {
            parseBoolean(cl);
        } else if (tag == OboFormatTag.TAG_EXPAND_ASSERTION_TO) {
            parseOwlDef(cl);
        } else if (tag == OboFormatTag.TAG_EXPAND_EXPRESSION_TO) {
            parseOwlDef(cl);
        } else {
            error("Unexpected tag " + tag.getTag() + " in type def frame.");
        }
        return cl;
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

    private void parseIdRef(@Nonnull Clause cl) {
        parseIdRef(cl, false);
    }

    private void parseIdRef(@Nonnull Clause cl, boolean optional) {
        String id = getParseUntil(" !{");
        if (!optional) {
            if (id == null || id.length() < 1) {
                error("");
            }
        }
        cl.addValue(id);
    }

    private void parseIdRefPair(@Nonnull Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        parseIdRef(cl);
    }

    private void parsePerson(@Nonnull Clause cl) {
        parseUnquotedString(cl);
    }

    private boolean parseISODate(@Nonnull Clause cl) {
        String dateStr = getParseUntil(" !{");
        // Date date;
        // try {
        // date = isoDateFormat.parse(dateStr);
        cl.setValue(dateStr);
        return true;
        /*
         * } catch (ParseException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); return false; }
         */
    }

    private void parseSubsetdef(@Nonnull Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        if (stream.consume("\"")) {
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
        } else {
            error("");
        }
        parseZeroOrMoreWs();
        parseQualifierBlock(cl);
        parseHiddenComment();
    }

    private void parseSynonymTypedef(@Nonnull Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        if (stream.consume("\"")) {
            String desc = getParseUntilAdv("\"");
            cl.addValue(desc);
            // TODO: handle edge case where line ends with trailing whitespace
            // and no scope
            if (stream.peekCharIs(' ')) {
                parseOneOrMoreWs();
                parseIdRef(cl, true); // TODO - verify that this is a valid
                                      // scope
            }
        }
        parseZeroOrMoreWs();
        parseQualifierBlock(cl);
        parseHiddenComment();
    }

    private void parseHeaderDate(@Nonnull Clause cl) {
        parseZeroOrMoreWs();
        String v = getParseUntil("!");
        v = removeTrailingWS(v);
        try {
            Date date = OBOFormatConstants.headerDateFormat.get().parse(v);
            cl.addValue(date);
        } catch (ParseException e) {
            throw new OBOFormatParserException(
                    "Could not parse date from string: " + v, e, stream.lineNo,
                    stream.line);
        }
    }

    private boolean parseImport(@Nonnull Clause cl) {
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
        parseHiddenComment(); // ignore return value, as comments are optional
        return true;
    }

    private void parseIdSpace(@Nonnull Clause cl) {
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
        parseZeroOrMoreWs();
        parseQualifierBlock(cl);
        parseHiddenComment();
    }

    private void parseRelationship(@Nonnull Clause cl) {
        parseIdRef(cl);
        parseOneOrMoreWs();
        parseIdRef(cl);
    }

    private void parsePropertyValue(@Nonnull Clause cl) {
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
        String s = getParseUntil(" !{");
        if (s.length() > 0) {
            cl.addValue(s);
        }
    }

    /**
     * intersection_of-Tag Class-ID | intersection_of-Tag Relation-ID Class-ID.
     * 
     * @param cl
     *        cl
     * @throws OBOFormatParserException
     *         parser exception
     */
    private void parseTermIntersectionOf(@Nonnull Clause cl) {
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
    }

    private void parseTypedefIntersectionOf(@Nonnull Clause cl) {
        // single values only
        parseIdRef(cl);
    }

    // ----------------------------------------
    // Synonyms
    // ----------------------------------------
    private boolean parseDeprecatedSynonym(@Nonnull String tag,
            @Nonnull Clause cl) {
        String scope = null;
        if (tag.equals("exact_synonym")) {
            scope = OboFormatTag.TAG_EXACT.getTag();
        } else if (tag.equals("narrow_synonym")) {
            scope = OboFormatTag.TAG_NARROW.getTag();
        } else if (tag.equals("broad_synonym")) {
            scope = OboFormatTag.TAG_BROAD.getTag();
        } else if (tag.equals("related_synonym")) {
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

    private void parseSynonym(@Nonnull Clause cl) {
        if (stream.consume("\"")) {
            String syn = getParseUntilAdv("\"");
            cl.setValue(syn);
            parseZeroOrMoreWs();
            if (stream.peekCharIs('[') == false) {
                parseIdRef(cl, true);
                parseZeroOrMoreWs();
                if (stream.peekCharIs('[') == false) {
                    parseIdRef(cl, true);
                    parseZeroOrMoreWs();
                }
            }
            parseXrefList(cl, false);
            Collection<Xref> xrefs = cl.getXrefs();
            if (xrefs == null) {
                cl.setXrefs(new Vector<Xref>(0));
            }
        } else {
            error("The synonym is always a quoted string.");
        }
    }

    // ----------------------------------------
    // Definitions
    // ----------------------------------------
    private void parseDef(@Nonnull Clause cl) {
        if (stream.consume("\"")) {
            String def = getParseUntilAdv("\"");
            cl.setValue(def);
            parseZeroOrMoreWs();
            parseXrefList(cl, true);
        } else {
            error("Definitions should always be a quoted string.");
        }
    }

    private void parseOwlDef(@Nonnull Clause cl) {
        if (stream.consume("\"")) {
            String def = getParseUntilAdv("\"");
            cl.setValue(def);
            parseZeroOrMoreWs();
            parseXrefList(cl, true);
        } else {
            error("The " + cl.getTag() + " clause is always a quoted string.");
        }
    }

    // ----------------------------------------
    // XrefLists - e.g. [A:1, B:2, ... ]
    // ----------------------------------------
    private void parseXrefList(@Nonnull Clause cl, boolean optional) {
        if (stream.consume("[")) {
            parseZeroOrMoreXrefs(cl);
            parseZeroOrMoreWs();
            if (stream.consume("]") == false) {
                error("Missing closing ']' for xref list at pos: " + stream.pos);
            }
        } else if (!optional) {
            error("Clause: "
                    + cl.getTag()
                    + "; expected an xref list, or at least an empty list '[]' at pos: "
                    + stream.pos);
        }
    }

    private boolean parseZeroOrMoreXrefs(@Nonnull Clause cl) {
        if (parseXref(cl)) {
            while (stream.consume(",") && parseXref(cl)) {}
        }
        return true;
    }

    // an xref that supports a value of values in a clause
    private boolean parseXref(@Nonnull Clause cl) {
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
    private boolean parseDirectXref(@Nonnull Clause cl) {
        parseZeroOrMoreWs();
        String id = getParseUntil("\",]!{", true);
        id = id.trim();
        if (id.contains(" ")) {
            warn("accepting bad xref with spaces:<" + id + ">");
        }
        id = id.replaceAll(" +\\Z", "");
        Xref xref = new Xref(id);
        cl.addValue(xref);
        parseZeroOrMoreWs();
        if (stream.peekCharIs('"')) {
            stream.consume("\"");
            xref.setAnnotation(getParseUntilAdv("\""));
        }
        return true;
    }

    // ----------------------------------------
    // Qualifier Value blocks - e.g. {a="1",b="foo", ...}
    // ----------------------------------------
    private void parseQualifierBlock(@Nonnull Clause cl) {
        if (stream.consume("{")) {
            parseZeroOrMoreQuals(cl);
            parseZeroOrMoreWs();
            boolean success = stream.consume("}");
            if (!success) {
                error("Missing closing '}' for trailing qualifier block.");
            }
        }
    }

    private void parseZeroOrMoreQuals(@Nonnull Clause cl) {
        if (parseQual(cl)) {
            while (stream.consume(",") && parseQual(cl)) {}
        }
    }

    private boolean parseQual(@Nonnull Clause cl) {
        parseZeroOrMoreWs();
        String rest = stream.rest();
        if (rest.contains("=") == false) {
            error("Missing '=' in trailing qualifier block. This might happen for not properly escaped '{', '}' chars in comments.");
        }
        String q = getParseUntilAdv("=");
        parseZeroOrMoreWs();
        String v;
        if (stream.consume("\"")) {
            v = getParseUntilAdv("\"");
        } else {
            v = getParseUntil(" ,}");
            warn("qualifier values should be enclosed in quotes. You have: "
                    + q + "=" + stream.rest());
        }
        if (v == null || v.length() == 0) {
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
    private void parseBoolean(@Nonnull Clause cl) {
        if (stream.consume("true")) {
            cl.setValue(true);
        } else if (stream.consume("false")) {
            cl.setValue(false);
        } else {
            error("Could not parse boolean value.");
        }
    }

    protected void parseIdLine(@Nonnull Frame f) {
        String t = getParseTag();
        OboFormatTag tag = OBOFormatConstants.getTag(t);
        if (tag != OboFormatTag.TAG_ID) {
            error("Expected id tag as first line in frame, but was: " + tag);
        }
        Clause cl = new Clause(t);
        f.addClause(cl);
        String id = getParseUntil(" !{");
        if (id == null || id.length() == 0) {
            error("Could not find an valid id, id is empty.");
        }
        cl.addValue(id);
        f.setId(id);
        parseEOL(cl);
    }

    // ----------------------------------------
    // End-of-line matter
    // ----------------------------------------
    /**
     * @param cl
     *        cl
     * @throws OBOFormatParserException
     *         parser exception
     */
    public void parseEOL(@Nonnull Clause cl) {
        parseZeroOrMoreWs();
        parseQualifierBlock(cl);
        parseHiddenComment();
        forceParseNlOrEof();
    }

    private void parseHiddenComment() {
        parseZeroOrMoreWs();
        if (stream.peekCharIs('!')) {
            stream.forceEol();
        }
    }

    protected void parseUnquotedString(@Nonnull Clause cl) {
        parseZeroOrMoreWs();
        String v = getParseUntil("!{");
        // strip whitespace from the end - TODO
        v = removeTrailingWS(v);
        cl.setValue(v);
        if (stream.peekCharIs('{')) {
            parseQualifierBlock(cl);
        }
        parseHiddenComment();
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
            error("Expected at least one white space, but found end of line at pos: "
                    + stream.pos);
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

    @Nonnull
    private String getParseUntilAdv(@Nonnull String compl) {
        String ret = getParseUntil(compl);
        stream.advance(1);
        return ret;
    }

    @Nonnull
    private String getParseUntil(@Nonnull String compl) {
        return getParseUntil(compl, false);
    }

    @Nonnull
    private String
            getParseUntil(@Nonnull String compl, boolean commaWhitespace) {
        String r = stream.rest();
        int i = 0;
        boolean hasEscapedChars = false;
        while (i < r.length()) {
            if (r.charAt(i) == '\\') {
                hasEscapedChars = true;
                i += 2; // Escape
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
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < ret.length(); j++) {
                char c = ret.charAt(j);
                if (c == '\\') {
                    int next = j + 1;
                    if (next < ret.length()) {
                        char nextChar = ret.charAt(next);
                        switch (nextChar) {
                            case 'n': // newline
                                sb.append('\n');
                                break;
                            case 'W': // single space
                                sb.append(' ');
                                break;
                            case 't': // tab
                                sb.append('\n');
                                break;
                            default:
                                // assume that any char after a backlash is an
                                // escaped char.
                                // spec for this optional behavior
                                // http://www.geneontology.org/GO.format.obo-1_2.shtml#S.1.5
                                sb.append(nextChar);
                                break;
                        }
                        j += 1; // skip the next char
                    }
                } else {
                    sb.append(c);
                }
            }
            ret = sb.toString();
        }
        stream.advance(i);
        return ret;
    }

    private static String mapDeprecatedTag(@Nonnull String tag) {
        if (tag.equals("inverse_of_on_instance_level")) {
            return OboFormatTag.TAG_INVERSE_OF.getTag();
        }
        if (tag.equals("xref_analog")) {
            return OboFormatTag.TAG_XREF.getTag();
        }
        if (tag.equals("xref_unknown")) {
            return OboFormatTag.TAG_XREF.getTag();
        }
        if (tag.equals("instance_level_is_transitive")) {
            return OboFormatTag.TAG_IS_TRANSITIVE.getTag();
        }
        return tag;
    }

    private static String removeTrailingWS(@Nonnull String s) {
        // TODO make this more efficient
        return s.replaceAll("\\s*$", "");
    }

    private void error(String message) {
        throw new OBOFormatParserException(message, stream.lineNo, stream.line);
    }

    private void warn(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("LINE: ");
        sb.append(stream.lineNo);
        sb.append("  ");
        sb.append(message);
        sb.append("  LINE:\n");
        sb.append(stream.line);
        LOG.warn(sb.toString());
    }
}
