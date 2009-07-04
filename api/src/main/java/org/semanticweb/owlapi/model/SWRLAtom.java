package org.semanticweb.owlapi.model;

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
public interface SWRLAtom extends SWRLObject {

    /**
     * Gets the predicate of this atom
     * @return The atom predicate
     */
    SWRLPredicate getPredicate();

    /**
     * Gets all of the arguments in this atom
     * @return The collection of arguments in this atom
     */
    Collection<SWRLArgument> getAllArguments();
    
}
