package org.semanticweb.owl.model;

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
 * <p/>
 * Represents a class description in OWL.  This interface covers named and anonymous classes.
 */
public interface OWLClassExpression extends OWLObject, OWLPropertyRange {

    /**
     * Determines whether or not this description represents an anonymous class description.
     *
     * @return <code>true</code> if this is an anonymous class description, or <code>false</code> if this is a named
     *         class (<code>OWLClass</code>)
     */
    public boolean isAnonymous();


    /**
     * Determines if this class is a literal.  A literal being either a named class or the negation of a named class
     * (i.e. A or not(A)).
     *
     * @return <code>true</code> if this is a literal, or false if this is not a literal.
     */
    public boolean isLiteral();


    /**
     * If this class description is in fact a named class then this method may be used to obtain the description as an
     * <code>OWLClass</code> without the need for casting.  The general pattern of use is to use the
     * <code>isAnonymous</code> to first check
     *
     * @return This class description as an <code>OWLClass</code>.
     *
     * @throws OWLRuntimeException if this class description is not an <code>OWLClass</code>.
     */
    public OWLClass asOWLClass();


    /**
     * Determines if this description is the built in class owl:Thing. This method does not determine if the class is
     * equivalent to owl:Thing.
     *
     * @return <code>true</code> if this description is owl:Thing, or <code>false</code> if this description is not
     *         owl:Thing
     */
    public boolean isOWLThing();


    /**
     * Determines if this description is the built in class owl:Nothing. This method does not determine if the class is
     * equivalent to owl:Nothing.
     *
     * @return <code>true</code> if this description is owl:Nothing, or <code>false</code> if this description is not
     *         owl:Nothing.
     */
    public boolean isOWLNothing();


    /**
     * Gets this description in negation normal form.
     *
     * @return The description in negation normal form.
     */
    public OWLClassExpression getNNF();


    /**
     * Gets the negation normal form of the complement of this description.
     *
     * @return A description that represents the NNF of the complement of this description.
     */
    public OWLClassExpression getComplementNNF();


    /**
     * Interprets this description as a conjunction and returns the conjuncts. This method does not normalise the
     * description (full CNF is not computed).
     *
     * @return The conjucts of this description if it is a conjunction (object intersection of), or otherwise a
     *         singleton set containing this description. Note that nested conjunctions will be flattened, for example,
     *         calling this method on (A and B) and C will return the set {A, B, C}
     */
    public Set<OWLClassExpression> asConjunctSet();


     /**
     * Interprets this description as a disjunction and returns the disjuncts. This method does not normalise the
     * description (full DNF is not computed).
     *
     * @return The disjuncts of this description if it is a disjunction (object union of), or otherwise a
     *         singleton set containing this description. Note that nested disjunctions will be flattened, for example,
     *         calling this method on (A or B) or C will return the set {A, B, C}
     */
    public Set<OWLClassExpression> asDisjunctSet();


    /**
     * Accepts a visit from an <code>OWLDescriptionVisitor</code>
     *
     * @param visitor The visitor that wants to visit
     */
    public void accept(OWLDescriptionVisitor visitor);


    <O> O accept(OWLDescriptionVisitorEx<O> visitor);
}
