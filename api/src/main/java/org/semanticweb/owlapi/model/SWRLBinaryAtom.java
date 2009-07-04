package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Represents an atom with two ordered arguments
 */
public interface SWRLBinaryAtom<A extends SWRLArgument, B extends SWRLArgument> extends SWRLAtom {

    /**
     * Gets the first argument
     * @return The second argument
     */
    A getFirstArgument();

    /**
     * Gets the second argument
     * @return The second argument
     */
    B getSecondArgument();
}
