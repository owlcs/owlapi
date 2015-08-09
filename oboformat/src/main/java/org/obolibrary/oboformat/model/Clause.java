package org.obolibrary.oboformat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/** Clause */
public class Clause {

    protected String tag;
    @Nonnull
    protected List<Object> values = new ArrayList<>();
    @Nonnull
    protected Collection<Xref> xrefs = new ArrayList<>();
    @Nonnull
    protected Collection<QualifierValue> qualifierValues = new ArrayList<>();

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
     * default constructor
     * 
     * @deprecated use Clause(String). Using this constructor makes the hashcode
     *             variable.
     */
    @Deprecated
    public Clause() {}

    /**
     * freezing a clause signals that the clause has become quiescent, and that data structures can be adjusted to
     * increase performance, or reduce memory consumption.
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
                ((ArrayList) values).trimToSize();
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
                ((ArrayList) xrefs).trimToSize();
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
                    ((ArrayList) qualifierValues).trimToSize();
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
     * @param v
     *        v
     */
    public void setValue(Object v) {
        if(values instanceof ArrayList) {
            values.clear(); // TODO: Remove this line after profile gathering
            values.add(v);
        }  else {
            this.values = Collections.singletonList(v);
        }
    }

    /**
     * @param v
     *        v
     */
    public void addValue(Object v) {
        if (!(this.values instanceof ArrayList)) {
            List<Object> newValues = new ArrayList<>(values.size() + 1);
            newValues.addAll(this.values);
            this.values = newValues;
        }
        values.add(v);
    }

    /**
     * @return value
     */
    @Nullable
    public Object getValue() {
        Object value = null;
        if (!values.isEmpty()) {
            value = values.get(0);
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

    /**
     * @return value2
     */
    @Nullable
    public Object getValue2() {
        Object value = null;
        if (values.size() > 1) {
            value = values.get(1);
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
     * @param xref
     *        xref
     */
    public void addXref(Xref xref) {
        if (!(xrefs instanceof ArrayList)) {
            List<Xref> newXrefs = new ArrayList<>(xrefs.size() + 1);
            newXrefs.addAll(xrefs);
            this.xrefs = newXrefs;
        }
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
    public void setQualifierValues(@Nonnull Collection<QualifierValue> qualifierValues) {
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
     * @param qv
     *        qv
     */
    public void addQualifierValue(QualifierValue qv) {
        if (!(qualifierValues instanceof ArrayList)) {
            List<QualifierValue> newQualifierValues = new ArrayList<>(qualifierValues.size() + 1);
            newQualifierValues.addAll(qualifierValues);
            this.qualifierValues = newQualifierValues;
        }

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

    private static boolean collectionsEquals(@Nullable Collection<?> c1, @Nullable Collection<?> c2) {
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
        return 31 * 31 * 31 * qualifierValues.hashCode() + 31 * xrefs.hashCode() + 31 * 31 * values.hashCode()
            + (tag == null ? 0 : tag.hashCode());
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
            Object v1 = getValue();
            Object v2 = other.getValue();
            if (v1 != v2) {
                if (v1 != null) {
                    if (!v1.equals(v2)) {
                        if (Boolean.TRUE.equals(v1) && "true".equals(v2)) {
                            // special case - OK
                        } else if (Boolean.TRUE.equals(v2) && "true".equals(v1)) {
                            // special case - OK
                        } else if (Boolean.FALSE.equals(v1) && "false".equals(v2)) {
                            // special case - OK
                        } else if (Boolean.FALSE.equals(v2) && "false".equals(v1)) {
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
