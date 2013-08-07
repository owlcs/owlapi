package org.semanticweb.owlapi.util;

/** a set of personalized preconditions */
public class OWLAPIPreconditions {
    /** check for null and throw IllegalArgumentException if null
     * 
     * @param object
     *            reference to check
     * @return the input reference if not null
     * @throws IllegalArgumentException
     *             if object is null */
    public static <T> T checkNotNull(T object) {
        return checkNotNull(object, "this variable cannot be null");
    }

    /** check for null and throw IllegalArgumentException if null
     * 
     * @param object
     *            reference to check
     * @param message
     *            message for the illegal argument exception
     * @return the input reference if not null
     * @throws IllegalArgumentException
     *             if object is null */
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}
