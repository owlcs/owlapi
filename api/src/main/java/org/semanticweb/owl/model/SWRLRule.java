package org.semanticweb.owl.model;

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
     * Determines if this rule is anonymous.  Rules may be named
     * using URIs.
     * @return <code>true</code> if this rule is anonymous and therefore
     * doesn't have a URI.
     */
    public boolean isAnonymous();

    public URI getURI();

    /**
     * Gets the atoms in the body
     * @return A set of <code>SWRLAtom</code>s, which represent the atoms
     * in the body of the rule.
     */
    Set<SWRLAtom> getBody();


    /**
     * Gets the atoms in the head.
     * @return A set of <code>SWRLAtom</code>s, which represent the atoms
     * in the head of the rule
     */
    Set<SWRLAtom> getHead();


    /**
     * Gets the variables that appear in this rule.
     * @return A set of variables.
     */
    Set<SWRLAtomVariable> getVariables();


    /**
     * Gets the object variables that appear in this rule
     * @return A set of object variables
     */
    Set<SWRLAtomIVariable> getIVariables();


    /**
     * Gets the data variables that appear in this rule
     * @return A set of data variables
     */
    Set<SWRLAtomDVariable> getDVariables();


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
