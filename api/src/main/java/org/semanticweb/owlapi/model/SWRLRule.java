package org.semanticweb.owlapi.model;

import java.net.URI;
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
     * Determines if this rule is anonymous.  If the rule is anonymous it does not have an IRI, instead, it has a
     * node ID to identify it.
     * @return <code>true</code> if this rule is anonymous, <code>false</code> if this rule is not anonymous
     */
    boolean isAnonymous();

    /**
     * Gets the node ID if this rule is anonymous
     * @return The NodeID
     * @throws NullPointerException if this rule is not anonymous
     */
    NodeID getNodeID();

    /**
     * Gets the rule IRI if this rule is not anonymous.
     * @return The IRI of the rule if it is not anonymous.
     * @throws NullPointerException If this rule is anonymous.
     */
    IRI getIRI();

    /**
     * Gets a String representation of the identity of this rule.  If the rule is anonymous then this will be
     * a string representation of the anonymous ID, otherwise, it will be a string represetation of the IRI.
     * @return A string representation of the identity of this rule.
     */
    String toStringID();

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
     * Gets the object variables that appear in this rule
     * @return A set of object variables
     */
    Set<SWRLIndividualVariable> getIVariables();


    /**
     * Gets the data variables that appear in this rule
     * @return A set of data variables
     */
    Set<SWRLLiteralVariable> getDVariables();


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
