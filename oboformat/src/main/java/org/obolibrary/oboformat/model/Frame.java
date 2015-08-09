package org.obolibrary.oboformat.model;

import java.util.*;

import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OboInOwlCardinalityTools;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/** The Class Frame. */
public class Frame {

    /** The Enum FrameType. */
    public enum FrameType {
        //@formatter:off
        /** HEADER. */          HEADER, 
        /** TERM. */            TERM, 
        /** TYPEDEF. */         TYPEDEF, 
        /** INSTANCE. */        INSTANCE, 
        /** ANNOTATION. */      ANNOTATION
        //@formatter:on
    }

    /** The clauses. */
    protected Collection<Clause> clauses;
    /** The id. */
    protected String id;
    /** The type. */
    protected FrameType type;

    /** Instantiates a new frame. */
    public Frame() {
        init();
    }

    /**
     * Instantiates a new frame.
     * 
     * @param type
     *        the type
     */
    public Frame(FrameType type) {
        init();
        this.type = type;
    }

    /** Init clauses. */
    protected final void init() {
        clauses = new ArrayList<>();
    }

    /**
     * freezing a frame signals that a frame has become quiescent, and that data
     * structures can be adjusted to increase performance or reduce memory
     * consumption. If a frozen frame is subsequently modified it will be thawed
     * as necessary.
     */
    public void freeze() {
        if (clauses.isEmpty()) {
            clauses = Collections.emptyList();
            return;
        }
        for (Clause clause : clauses) {
            clause.freeze();
        }
        if (clauses.size() == 1) {
            clauses = Collections.singletonList(clauses.iterator().next());
            return;
        }
        if (clauses instanceof ArrayList<?>) {
            ArrayList<?> arrayList = (ArrayList<?>) clauses;
            arrayList.trimToSize();
        }
    }

    /**
     * @return the type
     */
    public FrameType getType() {
        return type;
    }

    /**
     * @param type
     *        the new type
     */
    public void setType(FrameType type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public @Nullable String getId() {
        return id;
    }

    /**
     * @param id
     *        the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the clauses
     */
    public Collection<Clause> getClauses() {
        return clauses;
    }

    /**
     * @param tag
     *        the tag
     * @return the clauses for tag
     */
    public List<Clause> getClauses(@Nullable String tag) {
        List<Clause> cls = new ArrayList<>();
        if (tag == null) {
            return cls;
        }
        for (Clause cl : clauses) {
            if (tag.equals(cl.getTag())) {
                cls.add(cl);
            }
        }
        return cls;
    }

    /**
     * @param tag
     *        the tag
     * @return the clauses for tag
     */
    public List<Clause> getClauses(OboFormatTag tag) {
        return getClauses(tag.getTag());
    }

    /**
     * @param tag
     *        the tag
     * @return null if no value set, otherwise first value
     */
    public @Nullable Clause getClause(@Nullable String tag) {
        if (tag == null) {
            return null;
        }
        for (Clause cl : clauses) {
            if (tag.equals(cl.getTag())) {
                return cl;
            }
            // TODO - throw exception if more than one clause of this type?
        }
        return null;
    }

    /**
     * @param tag
     *        the tag
     * @return the clause for tag
     */
    public @Nullable Clause getClause(OboFormatTag tag) {
        return getClause(tag.getTag());
    }

    /**
     * @param clauses
     *        the new clauses
     */
    public void setClauses(Collection<Clause> clauses) {
        this.clauses = clauses;
    }

    /**
     * @param cl
     *        the clause
     */
    public void addClause(Clause cl) {
        if (!(clauses instanceof ArrayList)) {
            Collection<Clause> tmp = new ArrayList<>(clauses.size() + 1);
            tmp.addAll(clauses);
            clauses = tmp;
        }
        clauses.add(cl);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Frame(");
        sb.append(id);
        sb.append(' ');
        clauses.forEach(cl -> sb.append(cl).append(' '));
        sb.append(')');
        return sb.toString();
    }

    /**
     * @param tag
     *        the tag
     * @return the tag value for tag
     */
    public @Nullable Object getTagValue(String tag) {
        Clause clause = getClause(tag);
        if (clause == null) {
            return null;
        }
        return clause.getValue();
    }

    /**
     * @param tag
     *        the tag
     * @return the tag value for tag
     */
    public @Nullable Object getTagValue(OboFormatTag tag) {
        return getTagValue(tag.getTag());
    }

    /**
     * @param <T>
     *        the generic type
     * @param tag
     *        the tag
     * @param cls
     *        the cls
     * @return the tag value for tag and class
     */
    public @Nullable <T> T getTagValue(String tag, Class<T> cls) {
        Clause clause = getClause(tag);
        if (clause == null) {
            return null;
        }
        Object value = clause.getValue();
        if (value.getClass().isAssignableFrom(cls)) {
            return cls.cast(value);
        }
        return null;
    }

    /**
     * @param <T>
     *        the generic type
     * @param tag
     *        the tag
     * @param cls
     *        the cls
     * @return the tag value for tag and class
     */
    public @Nullable <T> T getTagValue(OboFormatTag tag, Class<T> cls) {
        return getTagValue(tag.getTag(), cls);
    }

    /**
     * @param tag
     *        the tag
     * @return the tag values for tag
     */
    public Collection<Object> getTagValues(OboFormatTag tag) {
        return getTagValues(tag.getTag());
    }

    /**
     * @param tag
     *        the tag
     * @return the tag values for tag
     */
    public Collection<Object> getTagValues(String tag) {
        Collection<Object> vals = new ArrayList<>();
        getClauses(tag).forEach(v -> vals.add(v.getValue()));
        return vals;
    }

    /**
     * @param <T>
     *        the generic type
     * @param tag
     *        the tag
     * @param cls
     *        the cls
     * @return the tag values for tag and class
     */
    public <T> Collection<T> getTagValues(OboFormatTag tag, Class<T> cls) {
        return getTagValues(tag.getTag(), cls);
    }

    /**
     * @param <T>
     *        the generic type
     * @param tag
     *        the tag
     * @param cls
     *        the cls
     * @return the tag values for tag and class
     */
    public <T> Collection<T> getTagValues(String tag, Class<T> cls) {
        Collection<T> vals = new ArrayList<>();
        getClauses(tag).forEach(c -> vals.add(c.getValue(cls)));
        return vals;
    }

    /**
     * @param tag
     *        the tag
     * @return the tag xrefs for tg
     */
    public Collection<Xref> getTagXrefs(String tag) {
        Collection<Xref> xrefs = new ArrayList<>();
        Clause clause = getClause(tag);
        if (clause != null) {
            for (Object ob : clause.getValues()) {
                if (ob instanceof Xref) {
                    xrefs.add((Xref) ob);
                }
            }
        }
        return xrefs;
    }

    /**
     * @return the tags
     */
    public Set<String> getTags() {
        Set<String> tags = new HashSet<>();
        getClauses().forEach(cl -> tags.add(cl.getTag()));
        return tags;
    }

    private boolean sameID(Frame f) {
        if (id == null) {
            return f.getId() == null;
        }
        return id.equals(f.getId());
    }

    /**
     * @param extFrame
     *        the external frame
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void merge(Frame extFrame) throws FrameMergeException {
        if (this == extFrame) {
            return;
        }
        if (!sameID(extFrame)) {
            throw new FrameMergeException("ids do not match");
        }
        if (!extFrame.getType().equals(getType())) {
            throw new FrameMergeException("frame types do not match");
        }
        extFrame.getClauses().forEach(c -> addClause(c));
        // note we do not perform a document structure check at this point
    }

    /**
     * Check this frame for violations, i.e. cardinality constraint violations.
     * 
     * @throws FrameStructureException
     *         the frame structure exception
     * @see OboInOwlCardinalityTools for equivalent checks in OWL
     */
    public void check() throws FrameStructureException {
        if (FrameType.HEADER.equals(type)) {
            checkMaxOneCardinality(OboFormatTag.TAG_ONTOLOGY, OboFormatTag.TAG_FORMAT_VERSION, OboFormatTag.TAG_DATE,
                OboFormatTag.TAG_DEFAULT_NAMESPACE, OboFormatTag.TAG_SAVED_BY, OboFormatTag.TAG_AUTO_GENERATED_BY);
        }
        if (FrameType.TYPEDEF.equals(type)) {
            checkMaxOneCardinality(OboFormatTag.TAG_DOMAIN, OboFormatTag.TAG_RANGE, OboFormatTag.TAG_IS_METADATA_TAG,
                OboFormatTag.TAG_IS_CLASS_LEVEL_TAG);
        }
        if (!FrameType.HEADER.equals(getType())) {
            List<Clause> tagIdClauses = getClauses(OboFormatTag.TAG_ID);
            if (tagIdClauses.size() != 1) {
                throw new FrameStructureException(this, "cardinality of id field must be 1");
            }
            // this call will verify that the value is not null
            tagIdClauses.get(0).getValue();
            if (getId() == null) {
                throw new FrameStructureException(this, "id field must be set");
            }
        }
        Collection<Clause> iClauses = getClauses(OboFormatTag.TAG_INTERSECTION_OF);
        if (iClauses.size() == 1) {
            throw new FrameStructureException(this, "single intersection_of tags are not allowed");
        }
        checkMaxOneCardinality(OboFormatTag.TAG_IS_ANONYMOUS, OboFormatTag.TAG_NAME,
            // OboFormatTag.TAG_NAMESPACE,
            OboFormatTag.TAG_DEF, OboFormatTag.TAG_COMMENT, OboFormatTag.TAG_IS_ANTI_SYMMETRIC,
            OboFormatTag.TAG_IS_CYCLIC, OboFormatTag.TAG_IS_REFLEXIVE, OboFormatTag.TAG_IS_SYMMETRIC,
            OboFormatTag.TAG_IS_TRANSITIVE, OboFormatTag.TAG_IS_FUNCTIONAL, OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL,
            OboFormatTag.TAG_IS_OBSELETE, OboFormatTag.TAG_CREATED_BY, OboFormatTag.TAG_CREATION_DATE);
    }

    /**
     * Check max one cardinality.
     * 
     * @param tags
     *        the tags
     * @throws FrameStructureException
     *         frame structure exception
     */
    private void checkMaxOneCardinality(OboFormatTag... tags) throws FrameStructureException {
        for (OboFormatTag tag : tags) {
            if (getClauses(tag).size() > 1) {
                throw new FrameStructureException(this, "multiple " + tag.getTag() + " tags not allowed.");
            }
        }
    }
}
