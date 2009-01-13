package org.semanticweb.owl.model;

import java.util.Collection;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Represents an atom in a rule.  Atoms can either be in the head
 * (concequent) or body (antecedent) of the rule.  Atoms hold objects
 * which are either data objects or individual objects.
 */
public interface SWRLAtom<P> extends SWRLObject {

    P getPredicate();

    Collection<? extends SWRLAtomObject> getAllArguments();
}
