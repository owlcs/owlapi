package org.semanticweb.owlapi6.chainsaw;

import java.io.Serializable;

/**
 * factory for int sets
 */
public class FastSetFactory implements Serializable {

    private FastSetFactory() {}

    /**
     * @return int set instance
     */
    public static FastSet create() {
        return new FastSetSimple();
    }
}
