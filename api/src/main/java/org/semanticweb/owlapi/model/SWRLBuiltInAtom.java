package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface SWRLBuiltInAtom extends SWRLAtom {

    /**
     * Gets the predicate of this atom
     * @return The atom predicate
     */
    IRI getPredicate();

    List<SWRLDArgument> getArguments();

    /**
     * Determines if the predicate of this atom is is a core builtin.
     * @return <code>true</code> if this is a core builtin, otherwise <code>false</code>
     */
    boolean isCoreBuiltIn();

}
