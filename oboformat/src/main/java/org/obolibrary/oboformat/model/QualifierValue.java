package org.obolibrary.oboformat.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Qualifier value. */
public class QualifierValue implements Comparable<QualifierValue> {

    protected @Nonnull String qualifier;
    protected @Nonnull String value;

    /**
     * @param q
     *        qualifier
     * @param v
     *        value
     */
    public QualifierValue(String q, String v) {
        qualifier = q;
        value = v;
    }

    /**
     * @return qualifier
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * @param qualifier
     *        qualifier
     */
    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *        value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return '{' + qualifier + '=' + value + '}';
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + qualifier.hashCode();
        result = prime * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof QualifierValue)) {
            return false;
        }
        QualifierValue other = (QualifierValue) obj;
        if (!qualifier.equals(other.qualifier)) {
            return false;
        }
        if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(@Nullable QualifierValue o) {
        if (o == null) {
            return 1;
        }
        // use toString representation
        return toString().compareTo(o.toString());
    }
}
