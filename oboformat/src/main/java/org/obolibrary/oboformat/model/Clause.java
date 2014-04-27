package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/** Clause */
public class Clause {

    protected String tag;
    @Nonnull
    protected final Collection<Object> values = new ArrayList<Object>();
    @Nonnull
    protected final Collection<Xref> xrefs = new ArrayList<Xref>();
    @Nonnull
    protected final Collection<QualifierValue> qualifierValues = new ArrayList<QualifierValue>();

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
        super();
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
     * default constructor
     * 
     * @deprecated use Clause(String). Using this constructor makes the hashcode
     *             variable.
     */
    @Deprecated
    public Clause() {
        super();
    }

    /** @return tag */
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

    /** @return values */
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

    /** @return value */
    @Nullable
    public Object getValue() {
        Object value = null;
        if (!values.isEmpty()) {
            value = values.iterator().next();
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
    @Nullable
    public <T> T getValue(@Nonnull Class<T> cls) {
        Object value = getValue();
        if (value != null && value.getClass().isAssignableFrom(cls)) {
            return cls.cast(value);
        }
        return null;
    }

    /** @return value2 */
    @Nullable
    public Object getValue2() {
        Object value = null;
        if (values.size() > 1) {
            Iterator<Object> iterator = values.iterator();
            iterator.next();
            value = iterator.next();
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
    @Nullable
    public <T> T getValue2(@Nonnull Class<T> cls) {
        Object value = getValue2();
        if (value != null && value.getClass().isAssignableFrom(cls)) {
            return cls.cast(value);
        }
        return null;
    }

    /** @return xrefs */
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

    /** @return qualifier values */
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

    @SuppressWarnings("null")
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
        if (c1 == null || c1.size() == 0) {
            return c2 == null || c2.size() == 0;
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
        return 31 * 31 * 31 * qualifierValues.hashCode() + 31
                * xrefs.hashCode() + 31 * 31 * values.hashCode()
                + (tag == null ? 0 : tag.hashCode());
    }

    @Override
    public boolean equals(Object e) {
        if (e == null || !(e instanceof Clause)) {
            return false;
        }
        if (e == this) {
            return true;
        }
        Clause other = (Clause) e;
        if (!getTag().equals(other.getTag())) {
            return false;
        }
        if (getValues().size() == 1 && other.getValues().size() == 1) {
            // special case for comparing booleans
            // this is a bit of a hack - ideally owl2obo would use the correct
            // types
            Object _v1 = getValue();
            Object _v2 = other.getValue();
            if (_v1 != _v2) {
                if (_v1 != null) {
                    if (!_v1.equals(_v2)) {
                        if (Boolean.TRUE.equals(_v1) && "true".equals(_v2)) {
                            // special case - OK
                        } else if (Boolean.TRUE.equals(_v2)
                                && "true".equals(_v1)) {
                            // special case - OK
                        } else if (Boolean.FALSE.equals(_v1)
                                && "false".equals(_v2)) {
                            // special case - OK
                        } else if (Boolean.FALSE.equals(_v2)
                                && "false".equals(_v1)) {
                            // special case - OK
                        } else {
                            return false;
                        }
                    }
                }
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
