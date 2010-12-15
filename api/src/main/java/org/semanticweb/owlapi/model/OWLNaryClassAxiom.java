package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 */
public interface OWLNaryClassAxiom extends OWLClassAxiom, OWLNaryAxiom, OWLSubClassOfAxiomSetShortCut {

    /**
     * Gets all of the top level class expressions that appear in this
     * axiom.
     * @return A <code>Set</code> of class expressions that appear in the
     *         axiom.
     */
    public Set<OWLClassExpression> getClassExpressions();

    /**
     * A convenience method that obtains the class expression returned by the {@link #getClassExpressions()} method
     * as a list of class expressions.
     * @return A list of the class expressions in this axiom.
     */
    public List<OWLClassExpression> getClassExpressionsAsList();


    /**
     * Determines if this class axiom contains the specified class expression as an operand
     * @param ce The class expression to test for
     * @return <code>true</code> if this axiom contains the specified class expression as an operand,
     *         otherwise <code>false</code>.
     */
    boolean contains(OWLClassExpression ce);


    /**
     * Gets the set of class expressions that appear in this axiom minus the specfied
     * class expressions.
     * @param desc The class expressions to subtract from the class expressions in this axiom
     * @return A set containing all of the class expressions in this axiom (the class expressions
     *         returned by getClassExpressions()) minus the specified list of class expressions
     */
    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc);
}
