package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OboInOwlCardinalityTools;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/** The Class Frame. */
public class Frame {

    /** The Enum FrameType. */
    public enum FrameType {
        /** HEADER. */
        HEADER, /** TERM. */
        TERM, /** TYPEDEF. */
        TYPEDEF, /** INSTANCE. */
        INSTANCE, /** ANNOTATION. */
        ANNOTATION
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
    public String getId() {
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
    @Nonnull
    public List<Clause> getClauses(String tag) {
        List<Clause> cls = new ArrayList<>();
        for (Clause cl : clauses) {
            if (cl.getTag().equals(tag)) {
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
    @Nonnull
    public List<Clause> getClauses(@Nonnull OboFormatTag tag) {
        return getClauses(tag.getTag());
    }

    /**
     * @param tag
     *        the tag
     * @return null if no value set, otherwise first value
     */
    @Nullable
    public Clause getClause(String tag) {
        for (Clause cl : clauses) {
            if (cl.getTag().equals(tag)) {
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
    @Nullable
    public Clause getClause(@Nonnull OboFormatTag tag) {
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
    @Nullable
    public Object getTagValue(String tag) {
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
    @Nullable
    public Object getTagValue(@Nonnull OboFormatTag tag) {
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
    @Nullable
    public <T> T getTagValue(String tag, @Nonnull Class<T> cls) {
        if (getClause(tag) == null) {
            return null;
        }
        Object value = getClause(tag).getValue();
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
    @Nullable
    public <T> T getTagValue(@Nonnull OboFormatTag tag, @Nonnull Class<T> cls) {
        return getTagValue(tag.getTag(), cls);
    }

    /**
     * @param tag
     *        the tag
     * @return the tag values for tag
     */
    @Nonnull
    public Collection<Object> getTagValues(@Nonnull OboFormatTag tag) {
        return getTagValues(tag.getTag());
    }

    /**
     * @param tag
     *        the tag
     * @return the tag values for tag
     */
    @Nonnull
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
    @Nonnull
    public <T> Collection<T> getTagValues(@Nonnull OboFormatTag tag,
        @Nonnull Class<T> cls) {
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
    @Nonnull
    public <T> Collection<T> getTagValues(String tag, @Nonnull Class<T> cls) {
        Collection<T> vals = new ArrayList<>();
        getClauses(tag).forEach(c -> vals.add(c.getValue(cls)));
        return vals;
    }

    /**
     * @param tag
     *        the tag
     * @return the tag xrefs for tg
     */
    @Nonnull
    public Collection<Xref> getTagXrefs(String tag) {
        Collection<Xref> xrefs = new ArrayList<>();
        for (Object ob : getClause(tag).getValues()) {
            if (ob instanceof Xref) {
                xrefs.add((Xref) ob);
            }
        }
        return xrefs;
    }

    /**
     * @return the tags
     */
    @Nonnull
    public Set<String> getTags() {
        Set<String> tags = new HashSet<>();
        getClauses().forEach(cl -> tags.add(cl.getTag()));
        return tags;
    }

    /**
     * @param extFrame
     *        the external frame
     * @throws FrameMergeException
     *         the frame merge exception
     */
    public void merge(@Nonnull Frame extFrame) throws FrameMergeException {
        if (this == extFrame) {
            return;
        }
        if (!extFrame.getId().equals(getId())) {
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
            checkMaxOneCardinality(OboFormatTag.TAG_ONTOLOGY,
                OboFormatTag.TAG_FORMAT_VERSION, OboFormatTag.TAG_DATE,
                OboFormatTag.TAG_DEFAULT_NAMESPACE, OboFormatTag.TAG_SAVED_BY,
                OboFormatTag.TAG_AUTO_GENERATED_BY);
        }
        if (FrameType.TYPEDEF.equals(type)) {
            checkMaxOneCardinality(OboFormatTag.TAG_DOMAIN,
                OboFormatTag.TAG_RANGE, OboFormatTag.TAG_IS_METADATA_TAG,
                OboFormatTag.TAG_IS_CLASS_LEVEL_TAG);
        }
        if (!FrameType.HEADER.equals(getType())) {
            List<Clause> tagIdClauses = getClauses(OboFormatTag.TAG_ID);
            if (tagIdClauses.size() != 1) {
                throw new FrameStructureException(this,
                    "cardinality of id field must be 1");
            }
            // this call will verify that the value is not null
            tagIdClauses.get(0).getValue();
            if (getId() == null) {
                throw new FrameStructureException(this, "id field must be set");
            }
        }
        Collection<Clause> iClauses = getClauses(
            OboFormatTag.TAG_INTERSECTION_OF);
        if (iClauses.size() == 1) {
            throw new FrameStructureException(this,
                "single intersection_of tags are not allowed");
        }
        checkMaxOneCardinality(OboFormatTag.TAG_IS_ANONYMOUS,
            OboFormatTag.TAG_NAME,
            // OboFormatTag.TAG_NAMESPACE,
            OboFormatTag.TAG_DEF, OboFormatTag.TAG_COMMENT,
            OboFormatTag.TAG_IS_ANTI_SYMMETRIC, OboFormatTag.TAG_IS_CYCLIC,
            OboFormatTag.TAG_IS_REFLEXIVE, OboFormatTag.TAG_IS_SYMMETRIC,
            OboFormatTag.TAG_IS_TRANSITIVE, OboFormatTag.TAG_IS_FUNCTIONAL,
            OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL,
            OboFormatTag.TAG_IS_OBSELETE, OboFormatTag.TAG_CREATED_BY,
            OboFormatTag.TAG_CREATION_DATE);
    }

    /**
     * Check max one cardinality.
     * 
     * @param tags
     *        the tags
     * @throws FrameStructureException
     *         frame structure exception
     */
    private void checkMaxOneCardinality(@Nonnull OboFormatTag... tags)
        throws FrameStructureException {
        for (OboFormatTag tag : tags) {
            if (getClauses(tag).size() > 1) {
                throw new FrameStructureException(this, "multiple " + tag
                    .getTag() + " tags not allowed.");
            }
        }
    }
}
