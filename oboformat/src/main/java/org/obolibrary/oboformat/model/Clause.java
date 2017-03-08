package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * Clause.
 */
public class Clause {

    private static final Logger LOGGER = LoggerFactory.getLogger(Clause.class);
    @SuppressWarnings("unchecked")
    private static final Set<?> trueValues = Sets.newHashSet(Boolean.TRUE, "true");
    @SuppressWarnings("unchecked")
    private static final Set<?> falseValues = Sets.newHashSet(Boolean.FALSE, "false");
    @Nullable
    protected String tag;
    protected List<Object> values = new ArrayList<>();
    protected Collection<Xref> xrefs = new ArrayList<>();
    protected Collection<QualifierValue> qualifierValues = new ArrayList<>();

    /**
     * @param tag tag
     */
    public Clause(OboFormatTag tag) {
        this(tag.getTag());
    }

    /**
     * @param tag tag
     */
    public Clause(@Nullable String tag) {
        this.tag = tag;
    }

    /**
     * @param tag tag
     * @param value value
     */
    public Clause(@Nullable String tag, String value) {
        this(tag);
        setValue(value);
    }

    /**
     * @param tag tag
     * @param value value
     */
    public Clause(OboFormatTag tag, String value) {
        this(tag.getTag(), value);
    }

    /**
     * Default constructor.
     *
     * @deprecated use Clause(String). Using this constructor makes the hashcode variable.
     */
    @Deprecated
    public Clause() {
        super();
    }

    private static boolean collectionsEquals(@Nullable Collection<?> c1,
        @Nullable Collection<?> c2) {
        if (c1 == null || c1.isEmpty()) {
            return c2 == null || c2.isEmpty();
        }
        if (c2 == null || c1.size() != c2.size()) {
            return false;
        }
        return checkContents(c1, c2);
    }

    protected static boolean checkContents(Collection<?> c1, Collection<?> c2) {
        // xrefs are stored as lists to preserve order, but order is not
        // important for comparisons
        Set<?> s = new HashSet<>(c2);
        for (Object x : c1) {
            if (!s.contains(x)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param value value to set
     * @return modified clause
     */
    public Clause withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * freezing a clause signals that the clause has become quiescent, and that data structures can
     * be adjusted to increase performance, or reduce memory consumption.
     */
    void freeze() {
        freezeValues();
        freezeXrefs();
        freezeQualifiers();
    }

    void freezeValues() {
        switch (values.size()) {
            case 0:
                values = Collections.emptyList();
                break;
            case 1:
                Object o = values.iterator().next();
                values = Collections.singletonList(o);
                break;
            default:
                ((ArrayList<?>) values).trimToSize();
        }
    }

    void freezeXrefs() {
        switch (xrefs.size()) {
            case 0:
                xrefs = Collections.emptyList();
                break;
            case 1:
                Xref xref = xrefs.iterator().next();
                xrefs = Collections.singletonList(xref);
                break;
            default:
                if (xrefs instanceof ArrayList) {
                    ((ArrayList<?>) xrefs).trimToSize();
                }
        }
    }

    void freezeQualifiers() {
        switch (qualifierValues.size()) {
            case 0:
                qualifierValues = Collections.emptyList();
                break;
            case 1:
                QualifierValue qualifierValue = qualifierValues.iterator().next();
                qualifierValues = Collections.singletonList(qualifierValue);
                break;
            default:
                if (qualifierValues instanceof ArrayList) {
                    ((ArrayList<?>) qualifierValues).trimToSize();
                }
        }
    }

    /**
     * @return true if no xrefs or qualifiers exist
     */
    public boolean hasNoAnnotations() {
        return xrefs.isEmpty() && qualifierValues.isEmpty();
    }

    /**
     * @return tag
     */
    @Nullable
    public String getTag() {
        return tag;
    }

    /**
     * @param tag tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return values
     */
    public Collection<Object> getValues() {
        return values;
    }

    /**
     * @param values values
     */
    public void setValues(Collection<Object> values) {
        if (!(this.values instanceof ArrayList)) {
            switch (values.size()) {
                case 0:
                    this.values = Collections.emptyList();
                    break;
                case 1:
                    Object o = values.iterator().next();
                    this.values = Collections.singletonList(o);
                    break;
                default:
                    this.values = new ArrayList<>(values);
            }
        } else {
            this.values.clear();
            this.values.addAll(values);
        }
    }

    /**
     * @param v v
     */
    public void addValue(@Nullable Object v) {
        if (!(values instanceof ArrayList)) {
            List<Object> newValues = new ArrayList<>(values.size() + 1);
            newValues.addAll(values);
            values = newValues;
        }
        values.add(v);
    }

    /**
     * @return value
     * @throws FrameStructureException if there is no value
     */
    public Object getValue() {
        Object value = null;
        if (!values.isEmpty()) {
            value = values.get(0);
        }
        if (value == null) {
            // TODO: Throw Exceptions
            LOGGER.error("Cannot translate: {}", this);
            throw new FrameStructureException("Clause value is null: " + this);
        }
        return value;
    }

    /**
     * @param v v
     */
    public void setValue(Object v) {
        if (values instanceof ArrayList) {
            values.clear();// TODO: Remove this line after profile gathering
            values.add(v);
        } else {
            values = Collections.singletonList(v);
        }
    }

    /**
     * @param cls cls
     * @param <T> value type
     * @return value
     */
    public <T> T getValue(Class<T> cls) {
        Object value = getValue();
        return cls.cast(value);
    }

    /**
     * @return value2
     * @throws FrameStructureException if there is no value
     */
    public Object getValue2() {
        Object value = null;
        if (values.size() > 1) {
            value = values.get(1);
        }
        if (value == null) {
            // TODO: Throw Exceptions
            LOGGER.error("Cannot translate: {}", this);
            throw new FrameStructureException("Clause second value is null: " + this);
        }
        return value;
    }

    /**
     * @param cls cls
     * @param <T> value type
     * @return value2
     */
    public <T> T getValue2(Class<T> cls) {
        Object value = getValue2();
        return cls.cast(value);
    }

    /**
     * @return xrefs
     */
    public Collection<Xref> getXrefs() {
        return xrefs;
    }

    /**
     * @param xrefs xrefs
     */
    public void setXrefs(Collection<Xref> xrefs) {
        if (!(this.xrefs instanceof ArrayList)) {
            switch (xrefs.size()) {
                case 0:
                    this.xrefs = Collections.emptyList();
                    break;
                case 1:
                    Xref xref = xrefs.iterator().next();
                    this.xrefs = Collections.singletonList(xref);
                    break;
                default:
                    this.xrefs = new ArrayList<>(xrefs);
            }
        } else {
            this.xrefs.clear();
            this.xrefs.addAll(xrefs);
        }
    }

    /**
     * @param xref xref
     */
    public void addXref(Xref xref) {
        if (!(xrefs instanceof ArrayList)) {
            List<Xref> newXrefs = new ArrayList<>(xrefs.size() + 1);
            newXrefs.addAll(xrefs);
            xrefs = newXrefs;
        }
        xrefs.add(xref);
    }

    /**
     * @return qualifier values
     */
    public Collection<QualifierValue> getQualifierValues() {
        return qualifierValues;
    }

    /**
     * @param qualifierValues qualifierValues
     */
    public void setQualifierValues(Collection<QualifierValue> qualifierValues) {
        if (!(this.qualifierValues instanceof ArrayList)) {
            switch (qualifierValues.size()) {
                case 0:
                    this.qualifierValues = Collections.emptyList();
                    break;
                case 1:
                    QualifierValue qualifierValue = qualifierValues.iterator().next();
                    this.qualifierValues = Collections.singletonList(qualifierValue);
                    break;
                default:
                    this.qualifierValues = new ArrayList<>(qualifierValues);
            }
        } else {
            this.qualifierValues.clear();
            this.qualifierValues.addAll(qualifierValues);
        }
    }

    /**
     * @param qv qv
     */
    public void addQualifierValue(QualifierValue qv) {
        if (!(qualifierValues instanceof ArrayList)) {
            List<QualifierValue> newQualifierValues = new ArrayList<>(qualifierValues.size() + 1);
            newQualifierValues.addAll(qualifierValues);
            qualifierValues = newQualifierValues;
        }
        qualifierValues.add(qv);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tag);
        sb.append('(');
        for (Object ob : values) {
            sb.append(' ');
            sb.append(ob);
        }
        if (!qualifierValues.isEmpty()) {
            sb.append('{');
            for (QualifierValue qv : qualifierValues) {
                sb.append(qv);
                sb.append(' ');
            }
            sb.append('}');
        }
        if (!xrefs.isEmpty()) {
            sb.append('[');
            for (Xref x : xrefs) {
                sb.append(x);
                sb.append(' ');
            }
            sb.append(']');
        }
        sb.append(')');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return 31 * 31 * 31 * qualifierValues.hashCode() + 31 * xrefs.hashCode()
            + 31 * 31 * values.hashCode() + taghash();
    }

    private int taghash() {
        if (tag == null) {
            return 0;
        } else {
            assert tag != null;
            return tag.hashCode();
        }
    }

    private boolean tag(@Nullable String otherTag) {
        if (tag == null) {
            return otherTag == null;
        }
        assert tag != null;
        return tag.equals(otherTag);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Clause)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Clause other = (Clause) obj;
        if (!tag(other.tag)) {
            return false;
        }
        if (getValues().size() == 1 && other.getValues().size() == 1) {
            // special case for comparing booleans
            // this is a bit of a hack - ideally owl2obo would use the correct
            // types
            if (!compareValues(other)) {
                return false;
            }
        } else {
            if (!getValues().equals(other.getValues())) {
                return false;
            }
        }
        if (!collectionsEquals(xrefs, other.getXrefs())) {
            return false;
        }
        return collectionsEquals(qualifierValues, other.getQualifierValues());
    }

    protected boolean compareValues(Clause other) {
        try {
            Object v1 = getValue();
            Object v2 = other.getValue();
            if (v1 != v2 && !v1.equals(v2)) {
                return trueValues.contains(v1) && trueValues.contains(v2)
                    || falseValues.contains(v1) && falseValues.contains(v2);
            }
        } catch (FrameStructureException e) {
            // this cannot happen as it's already been tested
            LOGGER.debug(e.getMessage(), e);
        }
        return true;
    }
}
