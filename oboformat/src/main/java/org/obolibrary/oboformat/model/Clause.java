package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Clause. */
public class Clause {

    private static final Logger LOGGER = LoggerFactory.getLogger(Clause.class);
    protected String tag;
    @Nonnull
    protected final Collection<Object> values = new ArrayList<>();
    @Nonnull
    protected final Collection<Xref> xrefs = new ArrayList<>();
    @Nonnull
    protected final Collection<QualifierValue> qualifierValues = new ArrayList<>();

    /**
     * @param tag
     *        tag
     */
    public Clause(@Nonnull OboFormatTag tag) {
        this(tag.getTag());
    }

    /**
     * @param tag
     *        tag
     */
    public Clause(String tag) {
        this.tag = tag;
    }

    /**
     * @param tag
     *        tag
     * @param value
     *        value
     */
    public Clause(String tag, String value) {
        this(tag);
        setValue(value);
    }

    /**
     * @param tag
     *        tag
     * @param value
     *        value
     */
    public Clause(@Nonnull OboFormatTag tag, String value) {
        this(tag.getTag(), value);
    }

    /**
     * @param value
     *        value to set
     * @return modified clause
     */
    public Clause withValue(String value) {
        setValue(value);
        return this;
    }

    /**
     * Default constructor.
     * 
     * @deprecated use Clause(String). Using this constructor makes the hashcode
     *             variable.
     */
    @Deprecated
    public Clause() {}

    /**
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     *        tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return values
     */
    @Nonnull
    public Collection<Object> getValues() {
        return values;
    }

    /**
     * @param values
     *        values
     */
    public void setValues(@Nonnull Collection<Object> values) {
        this.values.clear();
        this.values.addAll(values);
    }

    /**
     * @param v
     *        v
     */
    public void setValue(Object v) {
        values.clear();
        values.add(v);
    }

    /**
     * @param v
     *        v
     */
    public void addValue(Object v) {
        values.add(v);
    }

    /**
     * @return value
     * @throws FrameStructureException
     *         if there is no value
     */
    @Nonnull
    public Object getValue() throws FrameStructureException {
        Object value = null;
        if (!values.isEmpty()) {
            value = values.iterator().next();
        }
        if (value == null) {
            // TODO: Throw Exceptions
            LOGGER.error("Cannot translate: {}", this);
            throw new FrameStructureException("Clause value is null: " + this);
        }
        return value;
    }

    /**
     * @param cls
     *        cls
     * @param <T>
     *        value type
     * @return value
     */
    @Nonnull
    public <T> T getValue(@Nonnull Class<T> cls) {
        Object value = getValue();
        return cls.cast(value);
    }

    /**
     * @return value2
     * @throws FrameStructureException
     *         if there is no value
     */
    @Nonnull
    public Object getValue2() throws FrameStructureException {
        Object value = null;
        if (values.size() > 1) {
            Iterator<Object> iterator = values.iterator();
            iterator.next();
            value = iterator.next();
        }
        if (value == null) {
            // TODO: Throw Exceptions
            LOGGER.error("Cannot translate: {}", this);
            throw new FrameStructureException("Clause second value is null: "
                + this);
        }
        return value;
    }

    /**
     * @param cls
     *        cls
     * @param <T>
     *        value type
     * @return value2
     */
    @Nonnull
    public <T> T getValue2(@Nonnull Class<T> cls) {
        Object value = getValue2();
        return cls.cast(value);
    }

    /**
     * @return xrefs
     */
    @Nonnull
    public Collection<Xref> getXrefs() {
        return xrefs;
    }

    /**
     * @param xrefs
     *        xrefs
     */
    public void setXrefs(@Nonnull Collection<Xref> xrefs) {
        this.xrefs.clear();
        this.xrefs.addAll(xrefs);
    }

    /**
     * @param xref
     *        xref
     */
    public void addXref(Xref xref) {
        xrefs.add(xref);
    }

    /**
     * @return qualifier values
     */
    @Nonnull
    public Collection<QualifierValue> getQualifierValues() {
        return qualifierValues;
    }

    /**
     * @param qualifierValues
     *        qualifierValues
     */
    public void setQualifierValues(
        @Nonnull Collection<QualifierValue> qualifierValues) {
        this.qualifierValues.clear();
        this.qualifierValues.addAll(qualifierValues);
    }

    /**
     * @param qv
     *        qv
     */
    public void addQualifierValue(QualifierValue qv) {
        qualifierValues.add(qv);
    }

    @Nonnull
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

    private static boolean collectionsEquals(@Nullable Collection<?> c1,
        @Nullable Collection<?> c2) {
        if (c1 == null || c1.isEmpty()) {
            return c2 == null || c2.isEmpty();
        }
        if (c2 == null) {
            return false;
        }
        if (c1.size() != c2.size()) {
            return false;
        }
        // xrefs are stored as lists to preserve order, but order is not import
        // for comparisons
        for (Object x : c1) {
            if (!c2.contains(x)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * 31 * 31 * qualifierValues.hashCode() + 31 * xrefs.hashCode()
            + 31 * 31 * values.hashCode() + (tag == null ? 0 : tag.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Clause)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Clause other = (Clause) obj;
        if (!getTag().equals(other.getTag())) {
            return false;
        }
        if (getValues().size() == 1 && other.getValues().size() == 1) {
            // special case for comparing booleans
            // this is a bit of a hack - ideally owl2obo would use the correct
            // types
            try {
                Object v1 = getValue();
                Object v2 = other.getValue();
                if (v1 != v2) {
                    if (!v1.equals(v2)) {
                        if (Boolean.TRUE.equals(v1) && "true".equals(v2)) {
                            // special case - OK
                        } else if (Boolean.TRUE.equals(v2) && "true".equals(
                            v1)) {
                            // special case - OK
                        } else if (Boolean.FALSE.equals(v1) && "false".equals(
                            v2)) {
                            // special case - OK
                        } else if (Boolean.FALSE.equals(v2) && "false".equals(
                            v1)) {
                            // special case - OK
                        } else {
                            return false;
                        }
                    }
                }
            } catch (@SuppressWarnings("unused") FrameStructureException e) {
                // this cannot happen as it's already been tested
            }
        } else {
            if (!getValues().equals(other.getValues())) {
                return false;
            }
        }
        if (!collectionsEquals(xrefs, other.getXrefs())) {
            return false;
        }
        /*
         * if (xrefs != null) { if (other.getXrefs() == null) return false; if
         * (!xrefs.equals(other.getXrefs())) return false; } else { if
         * (other.getXrefs() != null && other.getXrefs().size() > 0) { return
         * false; } }
         */
        return collectionsEquals(qualifierValues, other.getQualifierValues());
    }
}
