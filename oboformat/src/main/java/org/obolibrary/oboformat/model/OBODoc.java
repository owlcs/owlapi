package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OboInOwlCardinalityTools;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/** An OBODoc is a container for a header frame and zero or more entity frames. */
public class OBODoc {

    /** The header frame. */
    protected Frame headerFrame;
    /** The term frame map. */
    @Nonnull
    protected final Map<String, Frame> termFrameMap = new HashMap<>();
    /** The typedef frame map. */
    @Nonnull
    protected final Map<String, Frame> typedefFrameMap = new HashMap<>();
    /** The instance frame map. */
    @Nonnull
    protected final Map<String, Frame> instanceFrameMap = new HashMap<>();
    /** The annotation frames. */
    @Nonnull
    protected final Collection<Frame> annotationFrames = new LinkedList<>();
    /** The imported obo docs. */
    protected Collection<OBODoc> importedOBODocs = new LinkedList<>();

    /** @return the header frame */
    public Frame getHeaderFrame() {
        return headerFrame;
    }

    /**
     * @param headerFrame
     *        the new header frame
     */
    public void setHeaderFrame(Frame headerFrame) {
        this.headerFrame = headerFrame;
    }

    /** @return the term frames */
    @Nonnull
    public Collection<Frame> getTermFrames() {
        return termFrameMap.values();
    }

    /** @return the typedef frames */
    @Nonnull
    public Collection<Frame> getTypedefFrames() {
        return typedefFrameMap.values();
    }

    /** @return the instance frames */
    @Nonnull
    public Collection<Frame> getInstanceFrames() {
        return instanceFrameMap.values();
    }

    /**
     * @param id
     *        the id
     * @return the term frame
     */
    @Nullable
    public Frame getTermFrame(String id) {
        return getTermFrame(id, false);
    }

    /**
     * @param id
     *        the id
     * @param followImport
     *        the follow import
     * @return the term frame
     */
    @Nullable
    public Frame getTermFrame(String id, boolean followImport) {
        if (!followImport) {
            return termFrameMap.get(id);
        }
        // this set is used to check for cycles
        Set<String> visited = new HashSet<>();
        visited.add(getHeaderDescriptor());
        return getTermFrame(id, visited);
    }

    /**
     * @param id
     *        the id
     * @param visitedDocs
     *        the visited docs
     * @return the frame
     */
    @Nullable
    private Frame getTermFrame(String id, @Nonnull Set<String> visitedDocs) {
        Frame f = termFrameMap.get(id);
        if (f != null) {
            return f;
        }
        for (OBODoc doc : importedOBODocs) {
            String headerDescriptor = doc.getHeaderDescriptor();
            if (!visitedDocs.contains(headerDescriptor)) {
                visitedDocs.add(headerDescriptor);
                f = doc.getTermFrame(id, true);
            }
            if (f != null) {
                return f;
            }
        }
        return null;
    }

    /**
     * @param id
     *        the id
     * @return the typedef frame
     */
    @Nullable
    public Frame getTypedefFrame(String id) {
        return getTypedefFrame(id, false);
    }

    /**
     * @param id
     *        the id
     * @param followImports
     *        the follow imports
     * @return the typedef frame
     */
    @Nullable
    public Frame getTypedefFrame(String id, boolean followImports) {
        if (!followImports) {
            return typedefFrameMap.get(id);
        }
        // this set is used to check for cycles
        Set<String> visited = new HashSet<>();
        visited.add(getHeaderDescriptor());
        return getTypedefFrame(id, visited);
    }

    /**
     * @param id
     *        the id
     * @param visitedDocs
     *        the visited docs
     * @return the frame
     */
    @Nullable
    private Frame getTypedefFrame(String id, @Nonnull Set<String> visitedDocs) {
        Frame f = typedefFrameMap.get(id);
        if (f != null) {
            return f;
        }
        for (OBODoc doc : importedOBODocs) {
            String headerDescriptor = doc.getHeaderDescriptor();
            if (!visitedDocs.contains(headerDescriptor)) {
                visitedDocs.add(headerDescriptor);
                f = doc.getTypedefFrame(id, true);
            }
            if (f != null) {
                return f;
            }
        }
        return null;
    }

    /**
     * @param id
     *        the id
     * @return the instance frame
     */
    public Frame getInstanceFrame(String id) {
        return instanceFrameMap.get(id);
    }

    /** @return the imported obo docs */
    public Collection<OBODoc> getImportedOBODocs() {
        return importedOBODocs;
    }

    /**
     * @param importedOBODocs
     *        the new imported obo docs
     */
    public void setImportedOBODocs(Collection<OBODoc> importedOBODocs) {
        this.importedOBODocs = importedOBODocs;
    }

    /**
     * Adds the imported obo doc.
     * 
     * @param doc
     *        the doc
     */
    public void addImportedOBODoc(OBODoc doc) {
        if (importedOBODocs == null) {
            importedOBODocs = new ArrayList<>();
        }
        importedOBODocs.add(doc);
    }

    /**
     * Adds the frame.
     * 
     * @param f
     *        the frame
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void addFrame(@Nonnull Frame f) throws FrameMergeException {
        if (f.getType() == FrameType.TERM) {
            addTermFrame(f);
        } else if (f.getType() == FrameType.TYPEDEF) {
            addTypedefFrame(f);
        } else if (f.getType() == FrameType.INSTANCE) {
            addInstanceFrame(f);
        }
    }

    /**
     * Adds the term frame.
     * 
     * @param f
     *        the frame
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void addTermFrame(@Nonnull Frame f) throws FrameMergeException {
        String id = f.getId();
        if (termFrameMap.containsKey(id)) {
            termFrameMap.get(id).merge(f);
        } else {
            termFrameMap.put(id, f);
        }
    }

    /**
     * Adds the typedef frame.
     * 
     * @param f
     *        the frame
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void addTypedefFrame(@Nonnull Frame f) throws FrameMergeException {
        String id = f.getId();
        if (typedefFrameMap.containsKey(id)) {
            typedefFrameMap.get(id).merge(f);
        } else {
            typedefFrameMap.put(id, f);
        }
    }

    /**
     * Adds the instance frame.
     * 
     * @param f
     *        the frame
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void addInstanceFrame(@Nonnull Frame f) throws FrameMergeException {
        String id = f.getId();
        if (instanceFrameMap.containsKey(id)) {
            instanceFrameMap.get(id).merge(f);
        } else {
            instanceFrameMap.put(id, f);
        }
    }

    /**
     * Looks up the ID prefix to IRI prefix mapping. Header-Tag: idspace
     * 
     * @param prefix
     *        prefix
     * @return IRI prefix as string
     */
    @Nullable
    public static String getIDSpace(@Nonnull String prefix) {
        // built-in
        if (prefix.equals("RO")) {
            return "http://purl.obolibrary.org/obo/RO_";
        }
        // TODO
        return null;
    }

    /**
     * @param prefix
     *        the prefix
     * @return true, if is treat xrefs as equivalent
     */
    public static boolean isTreatXrefsAsEquivalent(@Nullable String prefix) {
        if ("RO".equals(prefix)) {
            return true;
        }
        return false;
    }

    /**
     * Merge contents.
     * 
     * @param extDoc
     *        the external doc
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void mergeContents(@Nonnull OBODoc extDoc)
            throws FrameMergeException {
        for (Frame f : extDoc.getTermFrames()) {
            addTermFrame(f);
        }
        for (Frame f : extDoc.getTypedefFrames()) {
            addTypedefFrame(f);
        }
        for (Frame f : extDoc.getInstanceFrames()) {
            addInstanceFrame(f);
        }
    }

    /**
     * Adds the default ontology header.
     * 
     * @param defaultOnt
     *        the default ont
     */
    public void addDefaultOntologyHeader(String defaultOnt) {
        Frame hf = getHeaderFrame();
        Clause ontClause = hf.getClause(OboFormatTag.TAG_ONTOLOGY);
        if (ontClause == null) {
            ontClause = new Clause(OboFormatTag.TAG_ONTOLOGY, defaultOnt);
            hf.addClause(ontClause);
        }
    }

    /**
     * Check this document for violations, i.e. cardinality constraint
     * violations.
     * 
     * @throws FrameStructureException
     *         the frame structure exception
     * @see OboInOwlCardinalityTools for equivalent checks in OWL
     */
    public void check() throws FrameStructureException {
        getHeaderFrame().check();
        for (Frame f : getTermFrames()) {
            f.check();
        }
        for (Frame f : getTypedefFrames()) {
            f.check();
        }
        for (Frame f : getInstanceFrames()) {
            f.check();
        }
    }

    @Nonnull
    @Override
    public String toString() {
        return getHeaderDescriptor();
    }

    /** @return the header descriptor */
    @Nonnull
    private String getHeaderDescriptor() {
        return "OBODoc(" + headerFrame + ')';
    }
}
