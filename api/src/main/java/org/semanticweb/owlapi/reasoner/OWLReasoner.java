package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.*;

import java.util.Set;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 *
 * <p>
 * An OWLReasoner reasons over a set of axioms.  The set of axioms can be obtained using the
 * {@link #getAxioms()} method.  When the client responsible for creating the reasoner has finished with the
 * reasoner instance it must call the {@link #dispose()} method to free any resources that are used by the reasoner.
 * In general, reasoners should not be instantiated directly, but should be created using the appropriate
 * {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}.
 * </p>
 * <p>
 * At creation time, an OWLReasoner will attach itself as a listener to the {@link org.semanticweb.owlapi.model.OWLOntologyManager}
 * that manages the ontologies contained within the reasoner.  The reasoner will listen to any
 * {@link org.semanticweb.owlapi.model.OWLOntologyChange}s and respond to these so that any queries that are asked after
 * the ontology changes are answered with respect to the changed ontologies.
 * </p>
 */
public interface OWLReasoner {

    /**
     * Gets the set of axioms that this reasoner answers queries with respect to.  These are "asserted axioms", that is
     * they are the axioms that were loaded into the reasoner.
     * @return The set of axioms that the reasoner reasons over.
     */
    Set<OWLAxiom> getAxioms();

    /**
     * Asks the reasoner to interrupt what it is currently doing.  An InterruptedException will be thrown in the
     * thread that invoked the last reasoner operation.  The OWL API is not thread safe in general, but it is likely
     * that this method will be called from another thread than the event dispatch thread or the thread in which
     * reasoning takes place.
     */
    void interrupt();

    /**
     * A convenience method that determines if the set of reasoner axioms (the set of axioms returned by
     * the {@link #getAxioms()} method) is consistent.
     * @return <code>true</code> if the set of axioms is consistent,
     * or <code>false</code> if the set of axioms is inconsistent.
     * @throws InterruptedException if the reasoning process was interrupted for any particular reason (for example if
     * reasoning was cancelled by a client process)
     */
    boolean isConsistent() throws InterruptedException;

    /**
     * A convenience method that determines if the specified class expression is satisfiable with respect to the
     * set of reasoner axioms (the set of axioms returned by the {@link #getAxioms()} method)
     * @param classExpression The class expression
     * @return <code>true</code> if classExpression is satisfiable with respect to the set of axioms, or
     * <code>false</code> if classExpression is unsatisfiable with respect to the axioms.
     * @throws InconsistentOntologiesException if the reasoner's axiom set is inconsistent
     * @throws EntitiesNotInSignatureException if the signature of the classExpression is not contained within the signature
     * of the reasoner's axiom set.
     * @throws ExpressivenessOutOfScopeException If the class expression contains constructs that are out of the scope
     * of expressiveness that is supported by this reasoner.
     * @throws InterruptedException if the reasoning process was interrupted for any particular reason (for example if
     * reasoning was cancelled by a client process)
     */
    boolean isSatisfiable(OWLClassExpression classExpression) throws InterruptedException;


    /**
     * A convenience method that determines if the specified axiom is entailed by the set of reasoner axioms
     * (the set of axioms returned by the {@link #getAxioms()} method)
     * @param axiom The axiom
     * @return <code>true</code> if {@code axiom} is entailed by the reasoner axioms or <code>false</code> if
     * {@code axiom} is not entailed by the reasoner axioms.
     * @throws InterruptedException if the reasoning process was interupped for any particular reason (for example if
     * reasoning was cancelled by a client process).
     * @throws UnsupportedEntailmentTypeException if the reasoner cannot perform a check to see if the specified
     * axiom is entailed
     * @see #isEntailmentCheckingSupported(org.semanticweb.owlapi.model.AxiomType) 
     */
    boolean isEntailed(OWLAxiom axiom) throws InterruptedException, UnsupportedEntailmentTypeException;

    /**
     * Determines if entailment checking for the specified axiom type is supported.
     * @param axiomType The axiom type
     * @return <code>true</code> if entailment checking for the specified axiom type is supported, otherwise
     * <code>false</code>. If <code>true</code> then asking {@link #isEntailed(org.semanticweb.owlapi.model.OWLAxiom)}
     * will <em>not</em> throw an exception of {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}.
     * If <code>false</code> then asking {@link #isEntailed(org.semanticweb.owlapi.model.OWLAxiom)} <em>will</em> throw
     * an {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}.
     */
    boolean isEntailmentCheckingSupported(AxiomType axiomType);

    /**
     * Gets the subclasses of the specified class expression.
     * @param classExpression The class expression whose subclasses are to be retrieved.
     * @param direct Determines if the direct subclasses should be retrived or if the descendant classes should be
     * retrieved.
     * @return If direct is <code>true</code>, the set of classes such that any class "A" in the set is entailed to
     * be a subclass of classExpression and there is no class "B" that is entailed to be a subclass of classExpression
     * for which "A" is entailed to be a subclass of "B".  
     */
    HierarchyNodeSet<OWLClass> getSubClasses(OWLClassExpression classExpression, boolean direct);

    HierarchyNodeSet<OWLClass> getSuperClasses(OWLClassExpression classExpression, boolean direct);

    HierarchyNode<OWLClass> getEquivalentClasses(OWLClassExpression classExpression);



    HierarchyNodeSet<OWLObjectProperty> getSuperProperties(OWLObjectPropertyExpression property, boolean direct);

    HierarchyNodeSet<OWLObjectProperty> getSubProperties(OWLObjectPropertyExpression property, boolean direct);

    HierarchyNode<OWLObjectProperty> getEquivalentProperties(OWLObjectPropertyExpression property);

    HierarchyNodeSet<OWLObjectProperty> getInverseProperties(OWLObjectPropertyExpression property);

    HierarchyNodeSet<OWLClass> getDomains(OWLObjectPropertyExpression property, boolean direct);

    HierarchyNodeSet<OWLClass> getRanges(OWLObjectPropertyExpression property, boolean direct);


    HierarchyNodeSet<OWLDataProperty> getSuperProperties(OWLDataPropertyExpression property, boolean direct);

    HierarchyNodeSet<OWLDataProperty> getSubProperties(OWLDataPropertyExpression property, boolean direct);

    HierarchyNode<OWLDataProperty> getEquivalentProperties(OWLDataProperty property);

    HierarchyNodeSet<OWLClass> getDomains(OWLDataPropertyExpression property, boolean direct);


    HierarchyNodeSet<OWLClass> getTypes(OWLNamedIndividual individual, boolean direct);

    IndividualSynonymsSet getInstances(OWLClassExpression classExpression, boolean direct);

    IndividualSynonymsSet getPropertyValues(OWLNamedIndividual individual, OWLObjectPropertyExpression property);

    Set<OWLLiteral> getPropertyValues(OWLNamedIndividual individual, OWLDataPropertyExpression property);

    /**
     * Asks the reasoner to answer a query.
     * @param query The query to be answered.  The specific type of query defines the query semantics and what answers
     * are expected.
     * @return The query answer.  The type of query specifies the semantics of the query and the query answers.
     * @throws UnsupportedQueryTypeException if this reasoner cannot answer the type of query being asked
     * @throws InconsistentOntologiesException if the reasoner's axiom set is inconsistent
     * @throws EntitiesNotInSignatureException if the signature of the query is not contained within the signature
     * of the reasoner's axiom set.
     * @throws ExpressivenessOutOfScopeException If the reasoner's axiom set contains axioms that are
     * out of the scope of expressiveness that is supported by this reasoner
     * @throws InterruptedException if the reasoning process was interrupted for any particular reason (for example if
     * reasoning was cancelled by a client process)
     */
    <R> R answerQuery(Query<R> query) throws UnsupportedQueryTypeException, InterruptedException;

    /**
     * Disposes of this reasoner.  This frees up any resources used by the reasoner and detaches the reasoner
     * as an {@link org.semanticweb.owlapi.model.OWLOntologyChangeListener} from the {@link org.semanticweb.owlapi.model.OWLOntologyManager}
     * that manages the ontologies contained within the reasoner.
     */
    void dispose();

    

}
