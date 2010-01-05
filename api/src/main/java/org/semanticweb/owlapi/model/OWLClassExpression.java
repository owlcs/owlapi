package org.semanticweb.owlapi.model;

import java.util.Collection;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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


    /**
     * Adds an {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom} to <code>ontology</code> that specifies
     * <code>SubClassOf(this, classExpression)</code>, where <code>this</code> refers to this class expression.  The
     * {@link org.semanticweb.owlapi.model.AddAxiom} change that gets generated will be applied straight away.  To build
     * up a list of changes that can be applied in one go, see the {@link org.semanticweb.owlapi.model.OWLClassExpression#addSubClassOf(OWLOntology, OWLClassExpression, OWLOntologyChangeBuilder)}
     * method.
     * @param ontology The ontology that the subclass axiom will be added to.
     * @param classExpression The class expression that will be specified as the super class in the <code>SubClassOf</code>
     * axiom.
     */
    void addSubClassOf(OWLOntology ontology, OWLClassExpression classExpression);

    /**
     * Adds an {@link org.semanticweb.owlapi.model.AddAxiom} change to the <code>changeBuilder</code> which specifies that
     * an {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom} should be added to <code>ontology</code> that specifies
     * <code>SubClassOf(this, classExpression)</code>, where <code>this</code> refers to this class expression.
     * @param ontology The ontology that the {@link org.semanticweb.owlapi.model.AddAxiom} change will apply to.
     * @param classExpression The class expression that will be specified as the super class in the <code>SubClassOf</code>
     * axiom that will be generated.
     * @param changeBuilder An <code>OWLOntologyChangeBuilder</code> that the {@link org.semanticweb.owlapi.model.AddAxiom} change will be added to.
     */
    void addSubClassOf(OWLOntology ontology, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder);

    /**
     * Adds an {@link org.semanticweb.owlapi.model.AddAxiom} to the specified {@link org.semanticweb.owlapi.model.OWLOntologyChangeBuilder},
     * which specifies that an {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom} should be added to the specified
     * <code>ontology</code>, that specifies <code>SubClassOf(this, classExpression)</code>, where <code>this</code>
     * refers to this class expression.
     * @param ontology The ontology that the {@link org.semanticweb.owlapi.model.AddAxiom} change will apply to.
     * @param classExpression The class expression that will be specified as the super class in the <code>SubClassOf</code>
     * axiom that will be generated.
     * @param annotations A set of annotations that will annotate the generated {@link org.semanticweb.owlapi.model.OWLSubClassOfAxiom}
     * @param changeBuilder An <code>OWLOntologyChangeBuilder</code> that the {@link org.semanticweb.owlapi.model.AddAxiom}
     * change will be added to.
     */
    void addSubClassOf(OWLOntology ontology, OWLClassExpression classExpression, Set<? extends OWLAnnotation> annotations, OWLOntologyChangeBuilder changeBuilder);

    void removeSubClassOf(OWLOntology ontology, boolean fromImportsClosure, OWLClassExpression classExpression);

    void removeSubClassOf(OWLOntology ontology, boolean fromImportsClosure, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder);

    void addEquivalentClasses(OWLOntology ontology, OWLClassExpression classExpression);

    void addEquivalentClasses(OWLOntology ontology, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder);

    void removeEquivalentClasses(OWLOntology ontology, boolean fromImportsClosure, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder);

    void addDisjointClasses(OWLOntology ontology, OWLClassExpression classExpression, OWLOntologyChangeBuilder changeBuilder);

    void addClassAssertion(OWLOntology ontology, OWLIndividual individual, OWLOntologyChangeBuilder changeBuilder);

    void removeClassAssertion(OWLOntology ontology, boolean fromImportsClosure, OWLIndividual individual, OWLOntologyChangeBuilder changeBuilder);
}
