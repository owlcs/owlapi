package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface SWRLObjectPropertyAtom extends SWRLBinaryAtom<SWRLIArgument, SWRLIArgument> {

    /**
     * Gets the predicate of this atom
     * @return The atom predicate
     */
    OWLObjectPropertyExpression getPredicate();

    /**
     * Gets a simplified form of this atom.  This basically creates and returns a new atom where the predicate is not
     * an inverse object property.  If the atom is of the form P(x, y) then P(x, y) is returned.  If the atom is of the
     * form inverseOf(P)(x, y) then P(y, x) is returned.
     * @return This atom in a simplified form
     */
    SWRLObjectPropertyAtom getSimplified();
}
