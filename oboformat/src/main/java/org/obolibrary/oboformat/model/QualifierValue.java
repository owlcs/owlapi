package org.obolibrary.oboformat.model;

import javax.annotation.Nonnull;

/** qualifier value */
public class QualifierValue implements Comparable<QualifierValue> {

    @Nonnull
    protected String qualifier;
    @Nonnull
    protected String value;

    /**
     * @param q
     *        qualifier
     * @param v
     *        value
     */
    public QualifierValue(@Nonnull String q, @Nonnull String v) {
        qualifier = q;
        value = v;
    }

    /**
     * @return qualifier
     */
    @Nonnull
    public String getQualifier() {
        return qualifier;
    }

    /**
     * @param qualifier
     *        qualifier
     */
    public void setQualifier(@Nonnull String qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * @return value
     */
    @Nonnull
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *        value
     */
    public void setValue(@Nonnull String value) {
        this.value = value;
    }

    @Nonnull
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
    public boolean equals(Object obj) {
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
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(QualifierValue o) {
        if (o == null) {
            return 1;
        }
        // use toString representation
        return toString().compareTo(o.toString());
    }
}
