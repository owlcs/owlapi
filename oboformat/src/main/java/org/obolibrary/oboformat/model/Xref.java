package org.obolibrary.oboformat.model;

import javax.annotation.Nullable;

/**
 * Xref.
 */
public class Xref {

    protected String idref;
    @Nullable
    protected String annotation;

    /**
     * @param idref idref
     */
    public Xref(String idref) {
        this.idref = idref;
    }

    /**
     * @return idref
     */
    public String getIdref() {
        return idref;
    }

    /**
     * @param idref idref
     */
    public void setIdref(String idref) {
        this.idref = idref;
    }

    /**
     * @return annotation
     */
    @Nullable
    public String getAnnotation() {
        return annotation;
    }

    /**
     * @param annotation annotation
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Xref)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Xref other = (Xref) obj;
        if (!idref.equals(other.idref)) {
            return false;
        }
        // if (false) {
        // // TODO: make this configurable?
        // // xref comments are treated as semi-invisible
        // if (annotation == null && other.annotation == null) {
        // return true;
        // }
        // if (annotation == null || other.annotation == null) {
        // return false;
        // }
        // return annotation.equals(other.annotation);
        // }
        return true;
    }

    @Override
    public int hashCode() {
        return idref.hashCode();
    }

    @Override
    public String toString() {
        if (annotation == null) {
            return idref;
        }
        return '<' + idref + " \"" + annotation + "\">";
    }
}
