package org.semanticweb.owlapi.model;

/**
 * Interface for types with a hash index; this is used to seed the hashcode computation for
 * instances, so that the hashcodes of objects with different types are /more/ different than the
 * hashcodes of objects with the same type. One application is ensuring that punned entities do not
 * have clashing hashcodes.
 */
@FunctionalInterface
public interface HasHashIndex {

    /**
     * @return index for this type. This is not a hashcode for instances, rather a hashcode for the
     * types.
     */
    int hashIndex();
}
