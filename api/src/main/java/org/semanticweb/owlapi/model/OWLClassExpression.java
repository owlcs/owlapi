package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group Date: 24-Oct-2006
 * Represents <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Class_Expressions">Class Expressions</a> in
 * the OWL 2 specification.  This interface covers named and anonymous classes.
 */
public interface OWLClassExpression extends OWLObject, OWLPropertyRange, SWRLPredicate {

    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType();

    /**
     * Determines whether or not this expression represents an anonymous class expression.
     * @return <code>true</code> if this is an anonymous class expression, or <code>false</code> if this is a named
     *         class (<code>OWLClass</code>)
     */
    public boolean isAnonymous();


    /**
     * Determines if this class is a literal.  A literal being either a named class or the negation of a named class
     * (i.e. A or not(A)).
     * @return <code>true</code> if this is a literal, or false if this is not a literal.
     */
    public boolean isClassExpressionLiteral();


    /**
     * If this class expression is in fact a named class then this method may be used to obtain the expression as an
     * <code>OWLClass</code> without the need for casting.  The general pattern of use is to use the
     * <code>isAnonymous</code> to first check
     * @return This class expression as an <code>OWLClass</code>.
     * @throws OWLRuntimeException if this class expression is not an <code>OWLClass</code>.
     */
    public OWLClass asOWLClass();


    /**
     * Determines if this expression is the built in class owl:Thing. This method does not determine if the class is
     * equivalent to owl:Thing.
     * @return <code>true</code> if this expression is owl:Thing, or <code>false</code> if this expression is not
     *         owl:Thing
     */
    public boolean isOWLThing();


    /**
     * Determines if this expression is the built in class owl:Nothing. This method does not determine if the class is
     * equivalent to owl:Nothing.
     * @return <code>true</code> if this expression is owl:Nothing, or <code>false</code> if this expression is not
     *         owl:Nothing.
     */
    public boolean isOWLNothing();


    /**
     * Gets this expression in negation normal form.
     * @return The expression in negation normal form.
     */
    public OWLClassExpression getNNF();


    /**
     * Gets the negation normal form of the complement of this expression.
     * @return A expression that represents the NNF of the complement of this expression.
     */
    public OWLClassExpression getComplementNNF();

    /**
     * Gets the object complement of this class expression.
     * @return A class expression that is the complement of this class expression.
     */
    public OWLClassExpression getObjectComplementOf();

    /**
     * Interprets this expression as a conjunction and returns the conjuncts. This method does not normalise the
     * expression (full CNF is not computed).
     * @return The conjucts of this expression if it is a conjunction (object intersection of), or otherwise a
     *         singleton set containing this expression. Note that nested conjunctions will be flattened, for example,
     *         calling this method on (A and B) and C will return the set {A, B, C}
     */
    public Set<OWLClassExpression> asConjunctSet();

    /**
     * Determines if this class expression contains a particular conjunct. This method does not do any normalisation
     * such as applying DeMorgans rules.
     * @param ce The conjunct to test for
     * @return <code>true</code> if this class expression is equal to <code>ce</code> or if this class expression
     * is an <code>ObjectIntersectionOf</code> (possibly nested withing another <code>ObjectIntersectionOf</code>)
     * that contains <code>ce</code>, otherwise <code>false</code>.
     */
    public boolean containsConjunct(OWLClassExpression ce);


    /**
     * Interprets this expression as a disjunction and returns the disjuncts. This method does not normalise the
     * expression (full DNF is not computed).
     * @return The disjuncts of this expression if it is a disjunction (object union of), or otherwise a
     *         singleton set containing this expression. Note that nested disjunctions will be flattened, for example,
     *         calling this method on (A or B) or C will return the set {A, B, C}
     */
    public Set<OWLClassExpression> asDisjunctSet();


    /**
     * Accepts a visit from an <code>OWLExpressionVisitor</code>
     * @param visitor The visitor that wants to visit
     */
    public void accept(OWLClassExpressionVisitor visitor);


    <O> O accept(OWLClassExpressionVisitorEx<O> visitor);


}
