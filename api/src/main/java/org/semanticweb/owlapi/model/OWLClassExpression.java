/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.model;

import java.util.Set;

import javax.annotation.Nonnull;

/**
 * Represents <a
 * href="http://www.w3.org/TR/owl2-syntax/#Class_Expressions">Class
 * Expressions</a> in the OWL 2 specification. This interface covers named and
 * anonymous classes.
 * 
 * @author Matthew Horridge The University Of Manchester Bio-Health Informatics
 *         Group
 * @since 2.0.0
 */
public interface OWLClassExpression extends OWLObject, OWLPropertyRange,
        SWRLPredicate {

    /**
     * Gets the class expression type for this class expression
     * 
     * @return The class expression type
     */
    @Nonnull
    ClassExpressionType getClassExpressionType();

    /**
     * Determines whether or not this expression represents an anonymous class
     * expression.
     * 
     * @return {@code true} if this is an anonymous class expression, or
     *         {@code false} if this is a named class ( {@code OWLClass})
     */
    boolean isAnonymous();

    /**
     * Determines if this class is a literal. A literal being either a named
     * class or the negation of a named class (i.e. A or not(A)).
     * 
     * @return {@code true} if this is a literal, or false if this is not a
     *         literal.
     */
    boolean isClassExpressionLiteral();

    /**
     * If this class expression is in fact a named class then this method may be
     * used to obtain the expression as an {@code OWLClass} without the need for
     * casting. The general pattern of use is to use the {@code isAnonymous} to
     * first check
     * 
     * @return This class expression as an {@code OWLClass}.
     * @throws OWLRuntimeException
     *         if this class expression is not an {@code OWLClass}.
     */
    @Nonnull
    OWLClass asOWLClass();

    /**
     * Determines if this expression is the built in class owl:Thing. This
     * method does not determine if the class is equivalent to owl:Thing.
     * 
     * @return {@code true} if this expression is owl:Thing, or {@code false} if
     *         this expression is not owl:Thing
     */
    boolean isOWLThing();

    /**
     * Determines if this expression is the built in class owl:Nothing. This
     * method does not determine if the class is equivalent to owl:Nothing.
     * 
     * @return {@code true} if this expression is owl:Nothing, or {@code false}
     *         if this expression is not owl:Nothing.
     */
    boolean isOWLNothing();

    /**
     * Gets this expression in negation normal form.
     * 
     * @return The expression in negation normal form.
     */
    @Nonnull
    OWLClassExpression getNNF();

    /**
     * Gets the negation normal form of the complement of this expression.
     * 
     * @return A expression that represents the NNF of the complement of this
     *         expression.
     */
    @Nonnull
    OWLClassExpression getComplementNNF();

    /**
     * Gets the object complement of this class expression.
     * 
     * @return A class expression that is the complement of this class
     *         expression.
     */
    @Nonnull
    OWLClassExpression getObjectComplementOf();

    /**
     * Interprets this expression as a conjunction and returns the conjuncts.
     * This method does not normalise the expression (full CNF is not computed).
     * 
     * @return The conjucts of this expression if it is a conjunction (object
     *         intersection of), or otherwise a singleton set containing this
     *         expression. Note that nested conjunctions will be flattened, for
     *         example, calling this method on (A and B) and C will return the
     *         set {A, B, C}
     */
    @Nonnull
    Set<OWLClassExpression> asConjunctSet();

    /**
     * Determines if this class expression contains a particular conjunct. This
     * method does not do any normalisation such as applying DeMorgans rules.
     * 
     * @param ce
     *        The conjunct to test for
     * @return {@code true} if this class expression is equal to {@code ce} or
     *         if this class expression is an {@code ObjectIntersectionOf}
     *         (possibly nested withing another {@code ObjectIntersectionOf})
     *         that contains {@code ce}, otherwise {@code false}.
     */
    boolean containsConjunct(@Nonnull OWLClassExpression ce);

    /**
     * Interprets this expression as a disjunction and returns the disjuncts.
     * This method does not normalise the expression (full DNF is not computed).
     * 
     * @return The disjuncts of this expression if it is a disjunction (object
     *         union of), or otherwise a singleton set containing this
     *         expression. Note that nested disjunctions will be flattened, for
     *         example, calling this method on (A or B) or C will return the set
     *         {A, B, C}
     */
    @Nonnull
    Set<OWLClassExpression> asDisjunctSet();

    /**
     * Accepts a visit from an {@code OWLExpressionVisitor}
     * 
     * @param visitor
     *        The visitor that wants to visit
     */
    void accept(@Nonnull OWLClassExpressionVisitor visitor);

    /**
     * @param visitor
     *        visitor
     * @param <O>
     *        visitor return type
     * @return visitor return value
     */
    @Nonnull
    <O> O accept(@Nonnull OWLClassExpressionVisitorEx<O> visitor);
}
