package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Feb-2007<br><br>
 * <p/>
 * A utility class which checks if a class expression has a named conjunct or
 * a specific named conjunct.
 */
public class NamedConjunctChecker {

    private OWLClass conjunct;

    private boolean found;

    private boolean collect;

    private Set<OWLClass> conjuncts;

    private NamedConjunctCheckerVisitor visitor;


    public NamedConjunctChecker() {
        visitor = new NamedConjunctCheckerVisitor();
        conjuncts = new HashSet<OWLClass>();
    }


    /**
     * Checks whether a named class is a conjunct in a given class expression.
     * For class expressions which aren't named classes or object intersections this
     * method will always return false.
     * @param conjunct The conjunct to check for
     * @param classExpression The expression to be checked
     */
    public boolean isNamedConjunct(OWLClass conjunct, OWLClassExpression classExpression) {
        reset();
        this.conjunct = conjunct;
        classExpression.accept(visitor);
        return found;
    }


    /**
     * Checks whether the specified expression has a named conjunct.  For
     * For class expressions which aren't named classes or object intersections this
     * method will always return false.
     * @param classExpression The expression to be checked.
     * @return <code>true</code> if the expression is in fact a named class (<code>OWLClass</code>)
     *         or if the expression is an intersection that has a named operand (included nested intersections),
     *         otherwise <code>false</code>
     */
    public boolean hasNamedConjunct(OWLClassExpression classExpression) {
        reset();
        conjunct = null;
        classExpression.accept(visitor);
        return found;
    }


    private void reset() {
        found = false;
        collect = false;
    }


    /**
     * Gets the named conjuncts for the specified expression.
     * @param classExpression The expression whose conjuncts are to be retrieved.
     * @return A set containing the named conjuncts of the specified expression.  If
     *         the expression is not a named class or an intersection then the set will
     *         definitely be empty.
     */
    public Set<OWLClass> getNamedConjuncts(OWLClassExpression classExpression) {
        conjuncts.clear();
        reset();
        collect = true;
        classExpression.accept(visitor);
        return conjuncts;
    }


    private class NamedConjunctCheckerVisitor extends OWLClassExpressionVisitorAdapter {

        @Override
		public void visit(OWLClass desc) {
            if (conjunct == null) {
                found = true;
                if (collect) {
                    conjuncts.add(desc);
                }
            }
            else if (desc.equals(conjunct)) {
                found = true;
                if (collect) {
                    conjuncts.add(desc);
                }
            }
        }


        @Override
		public void visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
                // Early termination if we have found a named conjunct
                // and we don't need to collect
                if (found && !collect) {
                    break;
                }
            }
        }
    }
}
