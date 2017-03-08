package org.obolibrary.oboformat.model;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OboInOwlCardinalityTools;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/**
 * The Class Frame.
 */
public class Frame {

    /**
     * The clauses.
     */
    protected Collection<Clause> clauses = new ArrayList<>();
    /**
     * The id.
     */
    @Nullable
    protected String id;
    /**
     * The type.
     */
    @Nullable
    protected FrameType type;
    /**
     * Instantiates a new frame.
     */
    public Frame() {
        this(null);
    }

    /**
     * Instantiates a new frame.
     *
     * @param type the type
     */
    public Frame(@Nullable FrameType type) {
        this.type = type;
    }

    /**
     * freezing a frame signals that a frame has become quiescent, and that data structures can be
     * adjusted to increase performance or reduce memory consumption. If a frozen frame is
     * subsequently modified it will be thawed as necessary.
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
    @Nullable
    public FrameType getType() {
        return type;
    }

    /**
     * @param type the new type
     */
    public void setType(FrameType type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    @Nullable
    public String getId() {
        return id;
    }

    /**
     * @param id the new id
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
     * @param clauses the new clauses
     */
    public void setClauses(Collection<Clause> clauses) {
        this.clauses = clauses;
    }

    /**
     * @param tag the tag
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
     * @param tag the tag
     * @return the clauses for tag
     */
    public List<Clause> getClauses(OboFormatTag tag) {
        return getClauses(tag.getTag());
    }

    /**
     * @param tag the tag
     * @return null if no value set, otherwise first value
     */
    @Nullable
    public Clause getClause(@Nullable String tag) {
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
     * @param tag the tag
     * @return the clause for tag
     */
    @Nullable
    public Clause getClause(OboFormatTag tag) {
        return getClause(tag.getTag());
    }

    /**
     * @param cl the clause
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
        StringBuilder sb = new StringBuilder("Frame(");
        sb.append(id);
        sb.append(' ');
        clauses.forEach(cl -> sb.append(cl).append(' '));
        sb.append(')');
        return sb.toString();
    }

    /**
     * @param tag the tag
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
     * @param tag the tag
     * @return the tag value for tag
     */
    @Nullable
    public Object getTagValue(OboFormatTag tag) {
        return getTagValue(tag.getTag());
    }

    /**
     * @param <T> the generic type
     * @param tag the tag
     * @param cls the cls
     * @return the tag value for tag and class
     */
    @Nullable
    public <T> T getTagValue(String tag, Class<T> cls) {
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
     * @param <T> the generic type
     * @param tag the tag
     * @param cls the cls
     * @return the tag value for tag and class
     */
    @Nullable
    public <T> T getTagValue(OboFormatTag tag, Class<T> cls) {
        return getTagValue(tag.getTag(), cls);
    }

    /**
     * @param tag the tag
     * @return the tag values for tag
     */
    public Collection<Object> getTagValues(OboFormatTag tag) {
        return getTagValues(tag.getTag());
    }

    /**
     * @param tag the tag
     * @return the tag values for tag
     */
    public Collection<Object> getTagValues(String tag) {
        Collection<Object> vals = new ArrayList<>();
        getClauses(tag).forEach(v -> vals.add(v.getValue()));
        return vals;
    }

    /**
     * @param <T> the generic type
     * @param tag the tag
     * @param cls the cls
     * @return the tag values for tag and class
     */
    public <T> Collection<T> getTagValues(OboFormatTag tag, Class<T> cls) {
        return getTagValues(tag.getTag(), cls);
    }

    /**
     * @param <T> the generic type
     * @param tag the tag
     * @param cls the cls
     * @return the tag values for tag and class
     */
    public <T> Collection<T> getTagValues(String tag, Class<T> cls) {
        Collection<T> vals = new ArrayList<>();
        getClauses(tag).forEach(c -> vals.add(c.getValue(cls)));
        return vals;
    }

    /**
     * @param tag the tag
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
        return verifyNotNull(id).equals(f.getId());
    }

    private boolean sameType(Frame f) {
        if (type == null) {
            return f.getType() == null;
        }
        return verifyNotNull(type).equals(f.getType());
    }

    /**
     * @param extFrame the external frame
     * @throws FrameMergeException the frame merge exception
     */
    public void merge(Frame extFrame) throws FrameMergeException {
        if (this == extFrame) {
            return;
        }
        if (!sameID(extFrame)) {
            throw new FrameMergeException("ids do not match");
        }
        if (!sameType(extFrame)) {
            throw new FrameMergeException("frame types do not match");
        }
        extFrame.getClauses().forEach(this::addClause);
        // note we do not perform a document structure check at this point
    }

    /**
     * Check this frame for violations, i.e. cardinality constraint violations.
     *
     * @throws FrameStructureException the frame structure exception
     * @see OboInOwlCardinalityTools for equivalent checks in OWL
     */
    public void check() {
        if (FrameType.HEADER.equals(type)) {
            checkMaxOneCardinality(OboFormatTag.TAG_ONTOLOGY, OboFormatTag.TAG_FORMAT_VERSION,
                OboFormatTag.TAG_DATE, OboFormatTag.TAG_DEFAULT_NAMESPACE,
                OboFormatTag.TAG_SAVED_BY, OboFormatTag.TAG_AUTO_GENERATED_BY);
        }
        if (FrameType.TYPEDEF.equals(type)) {
            checkMaxOneCardinality(OboFormatTag.TAG_DOMAIN, OboFormatTag.TAG_RANGE,
                OboFormatTag.TAG_IS_METADATA_TAG, OboFormatTag.TAG_IS_CLASS_LEVEL_TAG);
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
            OboFormatTag.TAG_DEF, OboFormatTag.TAG_COMMENT,
            OboFormatTag.TAG_IS_ANTI_SYMMETRIC, OboFormatTag.TAG_IS_CYCLIC,
            OboFormatTag.TAG_IS_REFLEXIVE, OboFormatTag.TAG_IS_SYMMETRIC,
            OboFormatTag.TAG_IS_TRANSITIVE, OboFormatTag.TAG_IS_FUNCTIONAL,
            OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL, OboFormatTag.TAG_IS_OBSELETE,
            OboFormatTag.TAG_CREATED_BY, OboFormatTag.TAG_CREATION_DATE);
    }

    /**
     * Check max one cardinality.
     *
     * @param tags the tags
     * @throws FrameStructureException frame structure exception
     */
    private void checkMaxOneCardinality(OboFormatTag... tags) {
        for (OboFormatTag tag : tags) {
            if (getClauses(tag).size() > 1) {
                throw new FrameStructureException(this,
                    "multiple " + tag.getTag() + " tags not allowed.");
            }
        }
    }

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
}
