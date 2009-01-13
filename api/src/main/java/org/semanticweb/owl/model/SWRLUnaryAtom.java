package org.semanticweb.owl.model;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface SWRLUnaryAtom<P, A extends SWRLAtomObject> extends SWRLAtom<P> {

    A getArgument();
}
