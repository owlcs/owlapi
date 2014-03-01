package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 18/02/2014
 *         <p>
 *         An interface to objects that have a prefixed name (a name composing
 *         of a prefix name e.g. "owl:" plus a local name e.g. "Thing").
 *         </p>
 * @since 3.5
 */
public interface HasPrefixedName {

    /**
     * Gets the prefixed name.
     * 
     * @return The prefixed name. Not {@code null}.
     * @since 3.5
     */
    String getPrefixedName();
}
