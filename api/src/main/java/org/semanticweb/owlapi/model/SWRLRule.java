package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Represent a rule.  A rule consists of a head and a body.
 * Both the head and the body consist of a conjunction of
 * atoms.
 */
public interface SWRLRule extends OWLLogicalAxiom, SWRLObject {
    

    /**
     * Gets the atoms in the body of the rule
     * @return A set of <code>SWRLAtom</code>s, which represent the atoms
     * in the body of the rule.
     */
    Set<SWRLAtom> getBody();


    /**
     * Gets the atoms in the head of the rule
     * @return A set of <code>SWRLAtom</code>s, which represent the atoms
     * in the head of the rule
     */
    Set<SWRLAtom> getHead();

    /**
     * If this rule contains atoms that have predicates that are inverse object properties, then this method
     * creates and returns a rule where the arguments of these atoms are fliped over and the predicate is the
     * inverse (simplified) property
     * @return The rule such that any atoms of the form  inverseOf(p)(x, y) are transformed to p(x, y).
     */
    SWRLRule getSimplified();

    /**
     * Gets the variables that appear in this rule.
     * @return A set of variables.
     */
    Set<SWRLVariable> getVariables();

    /**
     * Determines if this rule uses anonymous class expressions in
     * class atoms.
     * @return <code>true</code> if this rule contains anonymous
     * class expression in class atoms, otherwise <code>false</code>.
     */
    boolean containsAnonymousClassExpressions();


    /**
     * Gets the predicates of class atoms.
     * @return A set of class expressions that represent the
     * class class expressions that are predicates of class atoms.
     */
    Set<OWLClassExpression> getClassAtomPredicates();

    SWRLRule getAxiomWithoutAnnotations();
}
