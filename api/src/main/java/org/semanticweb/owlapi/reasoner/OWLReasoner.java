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
package org.semanticweb.owlapi.reasoner;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.Version;

/**
 * An OWLReasoner reasons over a set of axioms (the set of reasoner axioms) that
 * is based on the imports closure of a particular ontology - the "root"
 * ontology. This ontology can be obtained using the
 * {@link OWLReasoner#getRootOntology()} method. When the client responsible for
 * creating the reasoner has finished with the reasoner instance it must call
 * the {@link #dispose()} method to free any resources that are used by the
 * reasoner. In general, reasoners should not be instantiated directly, but
 * should be created using the appropriate
 * {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}. <br>
 * <h2>Ontology Change Management (Buffering and Non-Buffering Modes)</h2> At
 * creation time, an OWLReasoner will load the axioms in the root ontology
 * imports closure. It will attach itself as a listener to the
 * {@link org.semanticweb.owlapi.model.OWLOntologyManager} that manages the root
 * ontology. The reasoner will listen to any
 * {@link org.semanticweb.owlapi.model.OWLOntologyChange}s and respond
 * appropriately to them before answering any queries. If the
 * {@link org.semanticweb.owlapi.reasoner.BufferingMode} of the reasoner (the
 * answer to {@link #getBufferingMode()} is
 * {@link org.semanticweb.owlapi.reasoner.BufferingMode#NON_BUFFERING}) the
 * ontology changes are processed by the reasoner immediately so that any
 * queries asked after the changes are answered with respect to the changed
 * ontologies. If the {@link org.semanticweb.owlapi.reasoner.BufferingMode} of
 * the reasoner is
 * {@link org.semanticweb.owlapi.reasoner.BufferingMode#BUFFERING} then ontology
 * changes are stored in a buffer and are only taken into consideration when the
 * buffer is flushed with the {@link #flush()} method. When reasoning, axioms in
 * the root ontology imports closure, minus the axioms returned by the
 * {@link #getPendingAxiomAdditions()} method, plus the axioms returned by the
 * {@link #getPendingAxiomRemovals()} are taken into consideration. <br>
 * Note that there is no guarantee that the reasoner implementation will respond
 * to changes in an incremental (and efficient manner) manner. <br>
 * <h2>Reasoner Axioms</h2> The set of axioms that the reasoner takes into
 * consideration when answering queries is known as the <i>set of reasoner
 * axioms</i>. This corresponds the axioms in the imports closure of the root
 * ontology plus the axioms returned by the {@link #getPendingAxiomRemovals()}
 * minus the axioms returned by {@link #getPendingAxiomAdditions()} <br>
 * <h2>Nodes</h2> The reasoner interface contains methods that return
 * {@link org.semanticweb.owlapi.reasoner.NodeSet}s. These are sets of
 * {@link org.semanticweb.owlapi.reasoner.Node}s. A {@code Node} contains
 * entities. <br>
 * For a {@code Node&lt;OWLClass&gt;} of classes, each class in the node is
 * equivalent to the other classes in the {@code Node} with respect to the
 * imports closure of the root ontology. <br>
 * For a {@code Node&lt;OWLObjectProperty&gt;} of object properties, each object
 * property in the {@code Node} is equivalent to the other object properties in
 * the node with respect to the imports closure of the root ontology. <br>
 * For a {@code Node&lt;OWLDataProperty&gt;} of data properties, each data
 * property in the {@code Node} is equivalent to the other data properties in
 * the node with respect to the imports closure of the root ontology. <br>
 * For a {@code Node&lt;OWLNamedIndividual&gt;} of named individuals, each
 * individual in the node is the same as the other individuals in the node with
 * respect to the imports closure of the root ontology. <br>
 * By abuse of notation, we say that a {@code NodeSet} "contains" an entity if
 * that entity is contained in one of the {@code Nodes} in the {@code NodeSet}. <br>
 * <h2>Hierarchies</h2> A hierachy (class hierachy, object property hierarchy,
 * data property hierarchy) is viewed as a directed acyclic graph (DAG)
 * containing nodes connected via edges. Each node in the hierarchy represents a
 * set of entities that are equivalent to each other. Each hierarchy has a top
 * node (see org.semanticweb.owlapi.reasoner.Node#isTopNode()) and a bottom node
 * (see org.semanticweb.owlapi.reasoner.Node#isBottomNode()). <br>
 * The figure below shows an example class hierarchy. Each box in the hierarchy
 * represents a {@code Node}. In this case the top node contains
 * {@code owl:Thing} and the bottom node contains {@code owl:Nothing} because
 * the nodes in the hierarchy are {@code OWLClass} nodes. In this case, class
 * {@code G} is equivalent to {@code owl:Thing} so it appears as an entity in
 * the top node along with {@code owl:Thing}. Similarly, class {@code K} is
 * unsatisfiable, so it is equivalent to {@code owl:Nothing}, and therefore
 * appears in the bottom node containing {@code owl:Nothing}. In this example,
 * classes {@code A} and {@code B} are equivalent so they appear in one node,
 * also, classes {@code D} and {@code F} are equivalent so they appear in one
 * node. <br>
 * Asking for the subclasses of a given class (expression) returns the a
 * {@code NodeSet} containing the nodes that contain classes that are strict
 * subclasses of the specified class (expression). For example, asking for the
 * subclasses of class {@code C} returns the {@code NodeSet}
 * {@code &#123;E&#125;} and {@code &#123;owl:Nothing, K&#125;}. <br>
 * Asking for the direct subclasses of a given class (expression) returns the
 * {@code NodeSet} that contains the nodes that contains classes that are direct
 * subclasses of the specified class. For example, asking for the direct
 * subclasses of class {@code A} returns the {@code NodeSet} containing the
 * nodes {@code &#123;C&#125;} and {@code &#123;D, F&#125;}. Note that there are
 * convenience methods on {@link NodeSet} and
 * {@link org.semanticweb.owlapi.reasoner.Node} that can be used to directly
 * access the entities in a {@code NodeSet} without having to iterate over the
 * nodes and entities in a {@code NodeSet}. For example, a "plain" stream of
 * classes contained inside the {@code Nodes} contained inside a {@code NodeSet}
 * can easily be obtained using the {@link NodeSet#entities()} method. In this
 * case we could quickly obtain {@code &#123;C, D, F&#125;} as the direct
 * subclasses of {@code A} simply by using the
 * {@link #getSubClasses(org.semanticweb.owlapi.model.OWLClassExpression, boolean)}
 * (with boolean=true) method on {@code OWLReasoner} and then we could use the
 * {@link NodeSet#entities()} method on the retuned {@code NodeSet} . <br>
 * Asking for equivalent classes of a class (expression) returns a {@code Node}
 * that contains classes that are equivalent to the class (expression) . For
 * example, asking for the equivalent classes of {@code owl:Nothing} (i.e.
 * asking for the unsatisfiable classes) returns the {@code Node}
 * {@code &#123;owl:Nothing, K&#125;}. <br>
 * <img src="../../../../doc-files/hierarchy.png" alt="hierarchy"><br>
 * <h2>Definitions</h2> In what follows, an extension of the <a
 * href="http://www.w3.org/TR/owl2-syntax/">OWL 2 Functional Syntax</a> is given
 * in order to capture notions like a class being a "direct" subclass of another
 * class. <br>
 * <h3>StrictSubClassOf</h3> Given two class expressions {@code CE1} and
 * {@code CE2} and an ontology {@code O}, {@code CE1} is a strict subclass of
 * {@code CE2}, written {@code StrictSubClassOf(CE1 CE2)} if {@code O} entails
 * {@code SubClassOf(CE1 CE2)} and {@code O} does not entail
 * {@code SubClassOf(CE2 CE1)} <br>
 * <h3>DirectSubClassOf</h3> Given two class expressions {@code CE1} and
 * {@code CE2} and an ontology {@code O}, {@code CE1} is a <i>direct</i>
 * subclass of {@code CE2}, written {@code DirectSubClassOf(CE1 CE2)}, with
 * respect to {@code O} if {@code O} entails {@code StrictSubClassOf(CE1 CE2)}
 * and there is no class name {@code C} in the signature of {@code O} such that
 * {@code O} entails {@code StrictSubClassOf(CE1 C)} and {@code O} entails
 * {@code StrictSubClassOf(C CE2)}. <br>
 * <h3>StrictSubObjectPropertyOf</h3> Given two object property expressions
 * {@code OPE1} and {@code OPE2} and an ontology {@code O}, {@code OPE1} is a
 * strict subproperty of {@code OPE2}, written
 * {@code StrictSubObjectPropertyOf(OPE1 OPE2)} if {@code O} entails
 * {@code SubObjectPropertyOf(OPE1 OPE2)} and {@code O} does not entail
 * {@code SubObjectPropertyOf(OPE2 OPE1)} <br>
 * <h3>DirectSubObjectPropertyOf</h3> Given two object property expressions
 * {@code OPE1} and {@code OPE2} and an ontology {@code O}, {@code OPE1} is a
 * <i>direct</i> subproperty of {@code OPE2}, written
 * {@code DirectSubObjectPropertyOf(OPE1 OPE2)}, with respect to {@code O} if
 * {@code O} entails {@code StrictSubObjectPropertyOf(OPE1 OPE2)} and there is
 * no object property name {@code P} in the signature of {@code O} such that
 * {@code O} entails {@code StrictSubObjectPropertyOf(OPE1 P)} and {@code O}
 * entails {@code StrictSubObjectPropertyOf(P OPE2)}. <br>
 * <h3>StrictSubDataPropertyOf</h3> Given two dbject property expressions
 * {@code DPE1} and {@code DPE2} and an ontology {@code O}, {@code DPE1} is a
 * strict subproperty of {@code DPE2}, written
 * {@code StrictSubDataPropertyOf(DPE1 DPE2)} if {@code O} entails
 * {@code SubDataPropertyOf(DPE1 DPE2)} and {@code O} does not entail
 * {@code SubDataPropertyOf(DPE1 DPE2)} <br>
 * <h3>DirectSubDataPropertyOf</h3> Given two data property expressions
 * {@code DPE1} and {@code DPE2} and an ontology {@code O}, {@code DPE1} is a
 * <i>direct</i> subproperty of {@code DPE2}, written
 * {@code DirectSubDataPropertyOf(DPE1 DPE2)}, with respect to {@code O} if
 * {@code O} entails {@code StrictSubDataPropertyOf(DPE1 DPE2)} and there is no
 * data property name {@code P} in the signature of {@code O} such that
 * {@code O} entails {@code StrictSubDataPropertyOf(DPE1 P)} and {@code O}
 * entails {@code StrictSubDataPropertyOf(P DPE2)}. <br>
 * <h3>DirectClassAssertion</h3> Given an individual {@code j} and a class
 * expression {@code CE} and an ontology {@code O}, {@code CE} is a direct class
 * assertion (type) for {@code j}, written {@code DirectClassAssertion(CE j)},
 * if {@code O} entails {@code ClassAssertion(CE j)} and there is no class name
 * {@code C} in the signature of {@code O} such that {@code O} entails
 * {@code ClassAssertion(C j)} and {@code O} entails
 * {@code StrictSubClassOf(C CE)}. <h3>ObjectPropertyComplementOf</h3> Given an
 * object property expression {@code pe}, the object property complement of
 * {@code pe} is written as {@code ObjectPropertyComplementOf(pe)}. The
 * interpretation of {@code ObjectPropertyComplementOf(pe)} is equal to the
 * interpretation of {@code owl:topObjectProperty} minus the interpretation of
 * {@code pe}. In other words, {@code ObjectPropertyComplementOf(pe)} is the set
 * of pairs of individuals that are not in {@code pe}. <h3>
 * DataPropertyComplementOf</h3> Given a data property expression {@code pe} ,
 * the data property complement of {@code pe} is written as
 * {@code DataPropertyComplementOf(pe)}. The interpretation of
 * {@code DataPropertyComplementOf(pe)} is equal to the interpretation of
 * {@code owl:topDataProperty} minus the interpretation of {@code pe}. In other
 * words, {@code DataPropertyComplementOf(pe)} is the set of pairs of individual
 * and literals that are not in {@code pe}. <h3 id="spe">Simplified Object
 * Property Expression</h3> A simplified object property expression is either a
 * named property {@code P}, or an object inverse property of the form
 * {@code ObjectInverseOf(P)} where {@code P} is a named property. In other
 * words, there is no nesting of {@code ObjectInverseOf} operators. <h2>Error
 * Handling</h2> An {@code OWLReasoner} may throw the following exceptions to
 * indicate errors. More documentation for each type of exception can be found
 * on the particular exception class.
 * <ul>
 * <li>
 *         {@link org.semanticweb.owlapi.reasoner.AxiomNotInProfileException}</li>
 * <li>
 * {@link org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException}</li>
 * <li>{@link org.semanticweb.owlapi.reasoner.FreshEntitiesException}</li>
 * <li>
 *         {@link org.semanticweb.owlapi.reasoner.InconsistentOntologyException}</li>
 * <li>{@link org.semanticweb.owlapi.reasoner.TimeOutException}</li>
 * <li>
 *         {@link org.semanticweb.owlapi.reasoner.ReasonerInterruptedException}</li>
 * <li>
 * {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}</li>
 * <li>{@link org.semanticweb.owlapi.reasoner.ReasonerInternalException}</li>
 * </ul>
 * Note that {@link org.semanticweb.owlapi.reasoner.ReasonerInternalException}
 * may be throw by any of the reasoner methods below.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public interface OWLReasoner {

    /**
     * Gets the name of this reasoner.
     * 
     * @return A string that represents the name of this reasoner.
     */
    @Nonnull
    String getReasonerName();

    /**
     * Gets the version of this reasoner.
     * 
     * @return The version of this reasoner. Not {@code null}.
     */
    @Nonnull
    Version getReasonerVersion();

    /**
     * Gets the buffering mode of this reasoner.
     * 
     * @return The buffering mode of this reasoner.
     */
    @Nonnull
    BufferingMode getBufferingMode();

    /**
     * Flushes any changes stored in the buffer, which causes the reasoner to
     * take into consideration the changes the current root ontology specified
     * by the changes. If the reasoner buffering mode is
     * {@link org.semanticweb.owlapi.reasoner.BufferingMode#NON_BUFFERING} then
     * this method will have no effect.
     */
    void flush();

    /**
     * Gets the pending changes which need to be taken into consideration by the
     * reasoner so that it is up to date with the root ontology imports closure.
     * After the {@link #flush()} method is called the set of pending changes
     * will be empty.
     * 
     * @return A set of changes. Note that the changes represent the raw changes
     *         as applied to the imports closure of the root ontology.
     */
    @Nonnull
    List<OWLOntologyChange> getPendingChanges();

    /**
     * Gets the axioms that as a result of ontology changes need to be added to
     * the reasoner to synchronise it with the root ontology imports closure. If
     * the buffering mode is
     * {@link org.semanticweb.owlapi.reasoner.BufferingMode#NON_BUFFERING} then
     * there will be no pending axiom additions.
     * 
     * @return The set of axioms that need to added to the reasoner to the
     *         reasoner to synchronise it with the root ontology imports
     *         closure.
     */
    @Nonnull
    Set<OWLAxiom> getPendingAxiomAdditions();

    /**
     * Gets the axioms that as a result of ontology changes need to removed to
     * the reasoner to synchronise it with the root ontology imports closure. If
     * the buffering mode is
     * {@link org.semanticweb.owlapi.reasoner.BufferingMode#NON_BUFFERING} then
     * there will be no pending axiom additions.
     * 
     * @return The set of axioms that need to added to the reasoner to the
     *         reasoner to synchronise it with the root ontology imports
     *         closure.
     */
    @Nonnull
    Set<OWLAxiom> getPendingAxiomRemovals();

    /**
     * Gets the "root" ontology that is loaded into this reasoner. The reasoner
     * takes into account the axioms in this ontology and its imports closure,
     * plus the axioms returned by {@link #getPendingAxiomRemovals()}, minus the
     * axioms returned by {@link #getPendingAxiomAdditions()} when reasoning. <br>
     * Note that the root ontology is set at reasoner creation time and cannot
     * be changed thereafter. Clients that want to add ontologies to and remove
     * ontologies from the reasoner after creation time should create a "dummy"
     * ontology that imports the "real" ontologies and then specify the dummy
     * ontology as the root ontology at reasoner creation time.
     * 
     * @return The root ontology that is loaded into the reasoner.
     */
    @Nonnull
    OWLOntology getRootOntology();

    /**
     * Asks the reasoner to interrupt what it is currently doing. An
     * ReasonerInterruptedException will be thrown in the thread that invoked
     * the last reasoner operation. The OWL API is not thread safe in general,
     * but it is likely that this method will be called from another thread than
     * the event dispatch thread or the thread in which reasoning takes place. <br>
     * Note that the reasoner will periodically check for interupt requests.
     * Asking the reasoner to interrupt the current process does not mean that
     * it will be interrupted immediately. However, clients can expect to be
     * able to interupt individual consistency checks, satisfiability checks
     * etc.
     */
    void interrupt();

    /**
     * Asks the reasoner to precompute certain types of inferences. Note that it
     * is NOT necessary to call this method before asking any other queries -
     * the reasoner will answer all queries correctly regardless of whether
     * inferences are precomputed or not. For example, if the imports closure of
     * the root ontology entails {@code SubClassOf(A B)} then the result of
     * {@code getSubClasses(B)} will contain {@code A}, regardless of whether
     * {@code precomputeInferences( InferenceType#CLASS_HIERARCHY)} has been
     * called. <br>
     * If the reasoner does not support the precomputation of a particular type
     * of inference then it will silently ignore the request.
     * 
     * @param inferenceTypes
     *        Suggests a list of the types of inferences that should be
     *        precomputed. If the list is empty then the reasoner will determine
     *        which types of inferences are precomputed. Note that the order of
     *        the list is unimportant - the reasoner will determine the order in
     *        which inferences are computed.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    void precomputeInferences(@Nonnull InferenceType... inferenceTypes);

    /**
     * Determines if a specific set of inferences have been precomputed.
     * 
     * @param inferenceType
     *        The type of inference to check for.
     * @return {@code true} if the specified type of inferences have been
     *         precomputed, otherwise {@code false}.
     */
    boolean isPrecomputed(@Nonnull InferenceType inferenceType);

    /**
     * Returns the set of {@link org.semanticweb.owlapi.reasoner.InferenceType}s
     * that are precomputable by reasoner.
     * 
     * @return A set of {@link org.semanticweb.owlapi.reasoner.InferenceType}s
     *         that can be precomputed by this reasoner.
     */
    @Nonnull
    Set<InferenceType> getPrecomputableInferenceTypes();

    /**
     * Determines if the set of reasoner axioms is consistent. Note that this
     * method will NOT throw an
     * {@link org.semanticweb.owlapi.reasoner.InconsistentOntologyException}
     * even if the root ontology imports closure is inconsistent.
     * 
     * @return {@code true} if the imports closure of the root ontology is
     *         consistent, or {@code false} if the imports closure of the root
     *         ontology is inconsistent.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process).
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    boolean isConsistent();

    /**
     * A convenience method that determines if the specified class expression is
     * satisfiable with respect to the reasoner axioms.
     * 
     * @param classExpression
     *        The class expression
     * @return {@code true} if classExpression is satisfiable with respect to
     *         the set of axioms, or {@code false} if classExpression is
     *         unsatisfiable with respect to the axioms.
     * @throws InconsistentOntologyException
     *         if the set of reasoner axioms is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code classExpression} is not within the profile that is
     *         supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of the classExpression is not contained within
     *         the signature of the set of reasoner axioms.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    boolean isSatisfiable(@Nonnull OWLClassExpression classExpression);

    /**
     * A convenience method that obtains the classes in the signature of the
     * root ontology that are unsatisfiable.
     * 
     * @return A {@code Node} that is the bottom node in the class hierarchy.
     *         This node represents {@code owl:Nothing} and contains
     *         {@code owl:Nothing} itself plus classes that are equivalent to
     *         {@code owl:Nothing}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     * @throws InconsistentOntologyException
     *         if the set of reasoner axioms is inconsistent
     */
    @Nonnull
    Node<OWLClass> getUnsatisfiableClasses();

    /**
     * A convenience method that determines if the specified axiom is entailed
     * by the set of reasoner axioms.
     * 
     * @param axiom
     *        The axiom
     * @return {@code true} if {@code axiom} is entailed by the reasoner axioms
     *         or {@code false} if {@code axiom} is not entailed by the reasoner
     *         axioms. {@code true} if the set of reasoner axioms is
     *         inconsistent.
     * @throws FreshEntitiesException
     *         if the signature of the axiom is not contained within the
     *         signature of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     * @throws UnsupportedEntailmentTypeException
     *         if the reasoner cannot perform a check to see if the specified
     *         axiom is entailed
     * @throws AxiomNotInProfileException
     *         if {@code axiom} is not in the profile that is supported by this
     *         reasoner.
     * @throws InconsistentOntologyException
     *         if the set of reasoner axioms is inconsistent
     * @see #isEntailmentCheckingSupported(org.semanticweb.owlapi.model.AxiomType)
     */
    boolean isEntailed(@Nonnull OWLAxiom axiom);

    /**
     * Determines if the specified set of axioms is entailed by the reasoner
     * axioms.
     * 
     * @param axioms
     *        The set of axioms to be tested
     * @return {@code true} if the set of axioms is entailed by the axioms in
     *         the imports closure of the root ontology, otherwise {@code false}
     *         . If the set of reasoner axioms is inconsistent then {@code true}
     *         .
     * @throws FreshEntitiesException
     *         if the signature of the set of axioms is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     * @throws UnsupportedEntailmentTypeException
     *         if the reasoner cannot perform a check to see if the specified
     *         axiom is entailed
     * @throws AxiomNotInProfileException
     *         if {@code axiom} is not in the profile that is supported by this
     *         reasoner.
     * @throws InconsistentOntologyException
     *         if the set of reasoner axioms is inconsistent
     * @see #isEntailmentCheckingSupported(org.semanticweb.owlapi.model.AxiomType)
     */
    boolean isEntailed(@Nonnull Set<? extends OWLAxiom> axioms);

    /**
     * Determines if entailment checking for the specified axiom type is
     * supported.
     * 
     * @param axiomType
     *        The axiom type
     * @return {@code true} if entailment checking for the specified axiom type
     *         is supported, otherwise {@code false}. If {@code true} then
     *         asking {@link #isEntailed(org.semanticweb.owlapi.model.OWLAxiom)}
     *         will <em>not</em> throw an exception of
     *         {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}
     *         . If {@code false} then asking
     *         {@link #isEntailed(org.semanticweb.owlapi.model.OWLAxiom)}
     *         <em>will</em> throw an
     *         {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}
     *         .
     */
    boolean isEntailmentCheckingSupported(@Nonnull AxiomType<?> axiomType);

    // Methods for dealing with the class hierarchy
    /**
     * Gets the {@code Node} corresponding to the top node (containing
     * {@code owl:Thing}) in the class hierarchy.
     * 
     * @return A {@code Node} containing {@code owl:Thing} that is the top node
     *         in the class hierarchy. This {@code Node} is essentially equal to
     *         the {@code Node} returned by calling
     *         {@link #getEquivalentClasses(org.semanticweb.owlapi.model.OWLClassExpression)}
     *         with a parameter of {@code owl:Thing}.
     */
    @Nonnull
    Node<OWLClass> getTopClassNode();

    /**
     * Gets the {@code Node} corresponding to the bottom node (containing
     * {@code owl:Nothing}) in the class hierarchy.
     * 
     * @return A {@code Node} containing {@code owl:Nothing} that is the bottom
     *         node in the class hierarchy. This {@code Node} is essentially
     *         equal to the {@code Node} that will be returned by calling
     *         {@link #getEquivalentClasses(org.semanticweb.owlapi.model.OWLClassExpression)}
     *         with a parameter of {@code owl:Nothing}.
     */
    @Nonnull
    Node<OWLClass> getBottomClassNode();

    /**
     * Gets the set of named classes that are the strict (potentially direct)
     * subclasses of the specified class expression with respect to the reasoner
     * axioms. Note that the classes are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ce
     *        The class expression whose strict (direct) subclasses are to be
     *        retrieved.
     * @param direct
     *        Specifies if the direct subclasses should be retrived (
     *        {@code true}) or if the all subclasses (descendant) classes should
     *        be retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code DirectSubClassOf(C, ce)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code StrictSubClassOf(C, ce)}. <br>
     *         If {@code ce} is equivalent to {@code owl:Nothing} then the empty
     *         {@code NodeSet} will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code classExpression} is not within the profile that is
     *         supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of the classExpression is not contained within
     *         the signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getSubClasses(@Nonnull OWLClassExpression ce,
            boolean direct);

    /**
     * Gets the set of named classes that are the strict (potentially direct)
     * super classes of the specified class expression with respect to the
     * imports closure of the root ontology. Note that the classes are returned
     * as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ce
     *        The class expression whose strict (direct) super classes are to be
     *        retrieved.
     * @param direct
     *        Specifies if the direct super classes should be retrived (
     *        {@code true}) or if the all super classes (ancestors) classes
     *        should be retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code DirectSubClassOf(ce, C)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code StrictSubClassOf(ce, C)}. <br>
     *         If {@code ce} is equivalent to {@code owl:Thing} then the empty
     *         {@code NodeSet} will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code classExpression} is not within the profile that is
     *         supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of the classExpression is not contained within
     *         the signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getSuperClasses(@Nonnull OWLClassExpression ce,
            boolean direct);

    /**
     * Gets the set of named classes that are equivalent to the specified class
     * expression with respect to the set of reasoner axioms. The classes are
     * returned as a {@link org.semanticweb.owlapi.reasoner.Node}.
     * 
     * @param ce
     *        The class expression whose equivalent classes are to be retrieved.
     * @return A node containing the named classes such that for each named
     *         class {@code C} in the node the root ontology imports closure
     *         entails {@code EquivalentClasses(ce C)}. If {@code ce} is not a
     *         class name (i.e. it is an anonymous class expression) and there
     *         are no such classes {@code C} then the node will be empty. <br>
     *         If {@code ce} is a named class then {@code ce} will be contained
     *         in the node. <br>
     *         If {@code ce} is unsatisfiable with respect to the set of
     *         reasoner axioms then the node representing and containing
     *         {@code owl:Nothing}, i.e. the bottom node, will be returned. <br>
     *         If {@code ce} is equivalent to {@code owl:Thing} with respect to
     *         the set of reasoner axioms then the node representing and
     *         containing {@code owl:Thing}, i.e. the top node, will be returned <br>
     *         .
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code classExpression} is not within the profile that is
     *         supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of the classExpression is not contained within
     *         the signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    Node<OWLClass> getEquivalentClasses(@Nonnull OWLClassExpression ce);

    /**
     * Gets the classes that are disjoint with the specified class expression
     * {@code ce}. The classes are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ce
     *        The class expression whose disjoint classes are to be retrieved.
     * @return The return value is a {@code NodeSet} such that for each class
     *         {@code D} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code EquivalentClasses(D, ObjectComplementOf(ce))} or
     *         {@code StrictSubClassOf(D, ObjectComplementOf(ce))}.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code classExpression} is not within the profile that is
     *         supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of the classExpression is not contained within
     *         the signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getDisjointClasses(@Nonnull OWLClassExpression ce);

    // Methods for dealing with the object property hierarchy
    /**
     * Gets the {@code Node} corresponding to the top node (containing
     * {@code owl:topObjectProperty}) in the object property hierarchy.
     * 
     * @return A {@code Node} containing {@code owl:topObjectProperty} that is
     *         the top node in the object property hierarchy. This {@code Node}
     *         is essentially equivalent to the {@code Node} returned by calling
     *         {@link #getEquivalentObjectProperties(org.semanticweb.owlapi.model.OWLObjectPropertyExpression)}
     *         with a parameter of {@code owl:topObjectProperty}.
     */
    @Nonnull
    Node<OWLObjectPropertyExpression> getTopObjectPropertyNode();

    /**
     * Gets the {@code Node} corresponding to the bottom node (containing
     * {@code owl:bottomObjectProperty}) in the object property hierarchy.
     * 
     * @return A {@code Node}, containing {@code owl:bottomObjectProperty}, that
     *         is the bottom node in the object property hierarchy. This
     *         {@code Node} is essentially equal to the {@code Node} that will
     *         be returned by calling
     *         {@link #getEquivalentObjectProperties(org.semanticweb.owlapi.model.OWLObjectPropertyExpression)}
     *         with a parameter of {@code owl:bottomObjectProperty}.
     */
    @Nonnull
    Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode();

    /**
     * Gets the set of <a href="#spe">simplified object property expressions</a>
     * that are the strict (potentially direct) subproperties of the specified
     * object property expression with respect to the imports closure of the
     * root ontology. Note that the properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param pe
     *        The object property expression whose strict (direct) subproperties
     *        are to be retrieved.
     * @param direct
     *        Specifies if the direct subproperties should be retrived (
     *        {@code true}) or if the all subproperties (descendants) should be
     *        retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} of <a
     *         href="#spe">simplified object property expressions</a>, such that
     *         for each <a href="#spe">simplified object property
     *         expression</a>, {@code P}, in the {@code NodeSet} the set of
     *         reasoner axioms entails {@code DirectSubObjectPropertyOf(P, pe)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} of <a
     *         href="#spe">simplified object property expressions</a>, such that
     *         for each <a href="#spe">simplified object property
     *         expression</a>, {@code P}, in the {@code NodeSet} the set of
     *         reasoner axioms entails {@code StrictSubObjectPropertyOf(P, pe)}. <br>
     *         If {@code pe} is equivalent to {@code owl:bottomObjectProperty}
     *         then the empty {@code NodeSet} will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
            @Nonnull OWLObjectPropertyExpression pe, boolean direct);

    /**
     * Gets the set of <a href="#spe">simplified object property expressions</a>
     * that are the strict (potentially direct) super properties of the
     * specified object property expression with respect to the imports closure
     * of the root ontology. Note that the properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param pe
     *        The object property expression whose strict (direct) super
     *        properties are to be retrieved.
     * @param direct
     *        Specifies if the direct super properties should be retrived (
     *        {@code true}) or if the all super properties (ancestors) should be
     *        retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} of <a
     *         href="#spe">simplified object property expressions</a>, such that
     *         for each <a href="#spe">simplified object property
     *         expression</a>, {@code P}, in the {@code NodeSet}, the set of
     *         reasoner axioms entails {@code DirectSubObjectPropertyOf(pe, P)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} of <a
     *         href="#spe">simplified object property expressions</a>, such that
     *         for each <a href="#spe">simplified object property
     *         expression</a>, {@code P}, in the {@code NodeSet}, the set of
     *         reasoner axioms entails {@code StrictSubObjectPropertyOf(pe, P)}. <br>
     *         If {@code pe} is equivalent to {@code owl:topObjectProperty} then
     *         the empty {@code NodeSet} will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
            @Nonnull OWLObjectPropertyExpression pe, boolean direct);

    /**
     * Gets the set of <a href="#spe">simplified object property expressions</a>
     * that are equivalent to the specified object property expression with
     * respect to the set of reasoner axioms. The properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.Node}.
     * 
     * @param pe
     *        The object property expression whose equivalent properties are to
     *        be retrieved.
     * @return A node containing the <a href="#spe">simplified object property
     *         expressions</a> such that for each <a href="#spe">simplified
     *         object property expression</a>, {@code P}, in the node, the set
     *         of reasoner axioms entails
     *         {@code EquivalentObjectProperties(pe P)}. <br>
     *         If {@code pe} is a <a href="#spe">simplified object property
     *         expression</a> If {@code pe} is unsatisfiable with respect to the
     *         set of reasoner axioms then the node representing and containing
     *         {@code owl:bottomObjectProperty}, i.e. the bottom node, will be
     *         returned. <br>
     *         If {@code pe} is equivalent to {@code owl:topObjectProperty} with
     *         respect to the set of reasoner axioms then the node representing
     *         and containing {@code owl:topObjectProperty}, i.e. the top node,
     *         will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
            @Nonnull OWLObjectPropertyExpression pe);

    /**
     * Gets the <a href="#spe">simplified object property expressions</a> that
     * are disjoint with the specified object property expression {@code pe}.
     * The object properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param pe
     *        The object property expression whose disjoint object properties
     *        are to be retrieved.
     * @return The return value is a {@code NodeSet} of <a
     *         href="#spe">simplified object property expressions</a>, such that
     *         for each <a href="#spe">simplified object property
     *         expression</a>, {@code P}, in the {@code NodeSet} the set of
     *         reasoner axioms entails
     *         {@code EquivalentObjectProperties(P, ObjectPropertyComplementOf(pe))}
     *         or
     *         {@code StrictSubObjectPropertyOf(P, ObjectPropertyComplementOf(pe))}
     *         .
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code object propertyExpression} is not within the profile
     *         that is supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of {@code pe} is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.and the undeclared entity
     *         policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
            @Nonnull OWLObjectPropertyExpression pe);

    /**
     * Gets the set of <a href="#spe">simplified object property expressions</a>
     * that are the inverses of the specified object property expression with
     * respect to the imports closure of the root ontology. The properties are
     * returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}
     * 
     * @param pe
     *        The property expression whose inverse properties are to be
     *        retrieved.
     * @return A {@code NodeSet} of <a href="#spe">simplified object property
     *         expressions</a>, such that for each simplified object property
     *         expression {@code P} in the nodes set, the set of reasoner axioms
     *         entails {@code InverseObjectProperties(pe, P)}.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    Node<OWLObjectPropertyExpression> getInverseObjectProperties(
            @Nonnull OWLObjectPropertyExpression pe);

    /**
     * Gets the named classes that are the direct or indirect domains of this
     * property with respect to the imports closure of the root ontology. The
     * classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}
     * .
     * 
     * @param pe
     *        The property expression whose domains are to be retrieved.
     * @param direct
     *        Specifies if the direct domains should be retrieved ( {@code true}
     *        ), or if all domains should be retrieved ( {@code false}).
     * @return Let
     *         {@code N = getEquivalentClasses(ObjectSomeValuesFrom(pe owl:Thing))}
     *         . <br>
     *         If {@code direct} is {@code true}: then if {@code N} is not empty
     *         then the return value is {@code N}, else the return value is the
     *         result of
     *         {@code getSuperClasses(ObjectSomeValuesFrom(pe owl:Thing), true)}
     *         . <br>
     *         If {@code direct} is {@code false}: then the result of
     *         {@code getSuperClasses(ObjectSomeValuesFrom(pe owl:Thing), false)}
     *         together with {@code N} if {@code N} is non-empty.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getObjectPropertyDomains(
            @Nonnull OWLObjectPropertyExpression pe, boolean direct);

    /**
     * Gets the named classes that are the direct or indirect ranges of this
     * property with respect to the imports closure of the root ontology. The
     * classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}
     * .
     * 
     * @param pe
     *        The property expression whose ranges are to be retrieved.
     * @param direct
     *        Specifies if the direct ranges should be retrieved ( {@code true}
     *        ), or if all ranges should be retrieved ( {@code false}).
     * @return Let
     *         {@code N = getEquivalentClasses(ObjectSomeValuesFrom(ObjectInverseOf(pe) owl:Thing))}
     *         . <br>
     *         If {@code direct} is {@code true}: then if {@code N} is not empty
     *         then the return value is {@code N}, else the return value is the
     *         result of
     *         {@code getSuperClasses(ObjectSomeValuesFrom(ObjectInverseOf(pe) owl:Thing), true)}
     *         . <br>
     *         If {@code direct} is {@code false}: then the result of
     *         {@code getSuperClasses(ObjectSomeValuesFrom(ObjectInverseOf(pe) owl:Thing), false)}
     *         together with {@code N} if {@code N} is non-empty.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getObjectPropertyRanges(
            @Nonnull OWLObjectPropertyExpression pe, boolean direct);

    // Methods for dealing with the data property hierarchy
    /**
     * Gets the {@code Node} corresponding to the top node (containing
     * {@code owl:topDataProperty}) in the data property hierarchy.
     * 
     * @return A {@code Node}, containing {@code owl:topDataProperty}, that is
     *         the top node in the data property hierarchy. This {@code Node} is
     *         essentially equal to the {@code Node} returned by calling
     *         {@link #getEquivalentDataProperties(org.semanticweb.owlapi.model.OWLDataProperty)}
     *         with a parameter of {@code owl:topDataProperty}.
     */
    @Nonnull
    Node<OWLDataProperty> getTopDataPropertyNode();

    /**
     * Gets the {@code Node} corresponding to the bottom node (containing
     * {@code owl:bottomDataProperty}) in the data property hierarchy.
     * 
     * @return A {@code Node}, containing {@code owl:bottomDataProperty}, that
     *         is the bottom node in the data property hierarchy. This
     *         {@code Node} is essentially equal to the {@code Node} that will
     *         be returned by calling
     *         {@link #getEquivalentDataProperties(org.semanticweb.owlapi.model.OWLDataProperty)}
     *         with a parameter of {@code owl:bottomDataProperty}.
     */
    @Nonnull
    Node<OWLDataProperty> getBottomDataPropertyNode();

    /**
     * Gets the set of named data properties that are the strict (potentially
     * direct) subproperties of the specified data property expression with
     * respect to the imports closure of the root ontology. Note that the
     * properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param pe
     *        The data property whose strict (direct) subproperties are to be
     *        retrieved.
     * @param direct
     *        Specifies if the direct subproperties should be retrived (
     *        {@code true}) or if the all subproperties (descendants) should be
     *        retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} such that for each
     *         property {@code P} in the {@code NodeSet} the set of reasoner
     *         axioms entails {@code DirectSubDataPropertyOf(P, pe)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} such that for each
     *         property {@code P} in the {@code NodeSet} the set of reasoner
     *         axioms entails {@code StrictSubDataPropertyOf(P, pe)}. <br>
     *         If {@code pe} is equivalent to {@code owl:bottomDataProperty}
     *         then the empty {@code NodeSet} will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the data property is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLDataProperty> getSubDataProperties(@Nonnull OWLDataProperty pe,
            boolean direct);

    /**
     * Gets the set of named data properties that are the strict (potentially
     * direct) super properties of the specified data property with respect to
     * the imports closure of the root ontology. Note that the properties are
     * returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param pe
     *        The data property whose strict (direct) super properties are to be
     *        retrieved.
     * @param direct
     *        Specifies if the direct super properties should be retrived (
     *        {@code true}) or if the all super properties (ancestors) should be
     *        retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} such that for each
     *         property {@code P} in the {@code NodeSet} the set of reasoner
     *         axioms entails {@code DirectSubDataPropertyOf(pe, P)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} such that for each
     *         property {@code P} in the {@code NodeSet} the set of reasoner
     *         axioms entails {@code StrictSubDataPropertyOf(pe, P)}. <br>
     *         If {@code pe} is equivalent to {@code owl:topDataProperty} then
     *         the empty {@code NodeSet} will be returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the data property is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLDataProperty> getSuperDataProperties(
            @Nonnull OWLDataProperty pe, boolean direct);

    /**
     * Gets the set of named data properties that are equivalent to the
     * specified data property expression with respect to the imports closure of
     * the root ontology. The properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.Node}.
     * 
     * @param pe
     *        The data property expression whose equivalent properties are to be
     *        retrieved.
     * @return A node containing the named data properties such that for each
     *         named data property {@code P} in the node, the set of reasoner
     *         axioms entails {@code EquivalentDataProperties(pe P)}. <br>
     *         If {@code pe} is a named data property then {@code pe} will be
     *         contained in the node. <br>
     *         If {@code pe} is unsatisfiable with respect to the set of
     *         reasoner axioms then the node representing and containing
     *         {@code owl:bottomDataProperty}, i.e. the bottom node, will be
     *         returned. <br>
     *         If {@code ce} is equivalent to {@code owl:topDataProperty} with
     *         respect to the set of reasoner axioms then the node representing
     *         and containing {@code owl:topDataProperty}, i.e. the top node,
     *         will be returned <br>
     *         .
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the data property expression is not contained
     *         within the signature of the imports closure of the root ontology
     *         and the undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    Node<OWLDataProperty> getEquivalentDataProperties(
            @Nonnull OWLDataProperty pe);

    /**
     * Gets the data properties that are disjoint with the specified data
     * property expression {@code pe}. The data properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param pe
     *        The data property expression whose disjoint data properties are to
     *        be retrieved.
     * @return The return value is a {@code NodeSet} such that for each data
     *         property {@code P} in the {@code NodeSet} the set of reasoner
     *         axioms entails
     *         {@code EquivalentDataProperties(P, DataPropertyComplementOf(pe))}
     *         or
     *         {@code StrictSubDataPropertyOf(P, DataPropertyComplementOf(pe))}
     *         .
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if {@code data propertyExpression} is not within the profile that
     *         is supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of {@code pe} is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLDataProperty> getDisjointDataProperties(
            @Nonnull OWLDataPropertyExpression pe);

    /**
     * Gets the named classes that are the direct or indirect domains of this
     * property with respect to the imports closure of the root ontology. The
     * classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}
     * .
     * 
     * @param pe
     *        The property expression whose domains are to be retrieved.
     * @param direct
     *        Specifies if the direct domains should be retrieved ( {@code true}
     *        ), or if all domains should be retrieved ( {@code false}).
     * @return Let
     *         {@code N = getEquivalentClasses(DataSomeValuesFrom(pe rdfs:Literal))}
     *         . <br>
     *         If {@code direct} is {@code true}: then if {@code N} is not empty
     *         then the return value is {@code N}, else the return value is the
     *         result of
     *         {@code getSuperClasses(DataSomeValuesFrom(pe rdfs:Literal), true)}
     *         . <br>
     *         If {@code direct} is {@code false}: then the result of
     *         {@code getSuperClasses(DataSomeValuesFrom(pe rdfs:Literal), false)}
     *         together with {@code N} if {@code N} is non-empty. <br>
     *         (Note, {@code rdfs:Literal} is the top datatype).
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the object property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getDataPropertyDomains(@Nonnull OWLDataProperty pe,
            boolean direct);

    // Methods for dealing with individuals and their types
    /**
     * Gets the named classes which are (potentially direct) types of the
     * specified named individual. The classes are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ind
     *        The individual whose types are to be retrieved.
     * @param direct
     *        Specifies if the direct types should be retrieved ( {@code true}),
     *        or if all types should be retrieved ( {@code false}).
     * @return If {@code direct} is {@code true}, a {@code NodeSet} containing
     *         named classes such that for each named class {@code C} in the
     *         node set, the set of reasoner axioms entails
     *         {@code DirectClassAssertion(C, ind)}. <br>
     *         If {@code direct} is {@code false}, a {@code NodeSet} containing
     *         named classes such that for each named class {@code C} in the
     *         node set, the set of reasoner axioms entails
     *         {@code ClassAssertion(C, ind)}. <br>
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the individual is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLClass> getTypes(@Nonnull OWLNamedIndividual ind, boolean direct);

    /**
     * Gets the individuals which are instances of the specified class
     * expression. The individuals are returned a a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ce
     *        The class expression whose instances are to be retrieved.
     * @param direct
     *        Specifies if the direct instances should be retrieved (
     *        {@code true}), or if all instances should be retrieved (
     *        {@code false}).
     * @return If {@code direct} is {@code true}, a {@code NodeSet} containing
     *         named individuals such that for each named individual {@code j}
     *         in the node set, the set of reasoner axioms entails
     *         {@code DirectClassAssertion(ce, j)}. <br>
     *         If {@code direct} is {@code false}, a {@code NodeSet} containing
     *         named individuals such that for each named individual {@code j}
     *         in the node set, the set of reasoner axioms entails
     *         {@code ClassAssertion(ce, j)}. <br>
     *         If ce is unsatisfiable with respect to the set of reasoner axioms
     *         then the empty {@code NodeSet} is returned.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileException
     *         if the class expression {@code ce} is not in the profile that is
     *         supported by this reasoner.
     * @throws FreshEntitiesException
     *         if the signature of the class expression is not contained within
     *         the signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     * @see org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy
     */
    @Nonnull
    NodeSet<OWLNamedIndividual> getInstances(@Nonnull OWLClassExpression ce,
            boolean direct);

    /**
     * Gets the object property values for the specified individual and object
     * property expression. The individuals are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ind
     *        The individual that is the subject of the object property values
     * @param pe
     *        The object property expression whose values are to be retrieved
     *        for the specified individual
     * @return A {@code NodeSet} containing named individuals such that for each
     *         individual {@code j} in the node set, the set of reasoner axioms
     *         entails {@code ObjectPropertyAssertion(pe ind j)}.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the individual and property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     * @see org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy
     */
    @Nonnull
    NodeSet<OWLNamedIndividual> getObjectPropertyValues(
            @Nonnull OWLNamedIndividual ind,
            @Nonnull OWLObjectPropertyExpression pe);

    /**
     * Gets the data property values for the specified individual and data
     * property expression. The values are a set of literals. Note that the
     * results are not guaranteed to be complete for this method. The reasoner
     * may also return canonical literals or they may be in a form that bears a
     * resemblance to the syntax of the literals in the root ontology imports
     * closure.
     * 
     * @param ind
     *        The individual that is the subject of the data property values
     * @param pe
     *        The data property expression whose values are to be retrieved for
     *        the specified individual
     * @return A set of {@code OWLLiteral}s containing literals such that for
     *         each literal {@code l} in the set, the set of reasoner axioms
     *         entails {@code DataPropertyAssertion(pe ind l)}.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the individual and property expression is not
     *         contained within the signature of the imports closure of the root
     *         ontology and the undeclared entity policy of this reasoner is set
     *         to {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     * @see org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy
     */
    @Nonnull
    Set<OWLLiteral> getDataPropertyValues(@Nonnull OWLNamedIndividual ind,
            @Nonnull OWLDataProperty pe);

    /**
     * Gets the individuals that are the same as the specified individual.
     * 
     * @param ind
     *        The individual whose same individuals are to be retrieved.
     * @return A node containing individuals such that for each individual
     *         {@code j} in the node, the root ontology imports closure entails
     *         {@code SameIndividual(j, ind)}. Note that the node will contain
     *         {@code j}.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the individual is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    Node<OWLNamedIndividual>
            getSameIndividuals(@Nonnull OWLNamedIndividual ind);

    /**
     * Gets the individuals which are entailed to be different from the
     * specified individual. The individuals are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * 
     * @param ind
     *        The individual whose different individuals are to be returned.
     * @return A {@code NodeSet} containing {@code OWLNamedIndividual} s such
     *         that for each individual {@code i} in the {@code NodeSet} the set
     *         of reasoner axioms entails {@code DifferentIndividuals(ind, i)}.
     * @throws InconsistentOntologyException
     *         if the imports closure of the root ontology is inconsistent
     * @throws FreshEntitiesException
     *         if the signature of the individual is not contained within the
     *         signature of the imports closure of the root ontology and the
     *         undeclared entity policy of this reasoner is set to
     *         {@link FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException
     *         if the reasoning process was interrupted for any particular
     *         reason (for example if reasoning was cancelled by a client
     *         process)
     * @throws TimeOutException
     *         if the reasoner timed out during a basic reasoning operation. See
     *         {@link #getTimeOut()}.
     */
    @Nonnull
    NodeSet<OWLNamedIndividual> getDifferentIndividuals(
            @Nonnull OWLNamedIndividual ind);

    /**
     * Gets the time out (in milliseconds) for the most basic reasoning
     * operations. That is the maximum time for a satisfiability test,
     * subsumption test etc. The time out should be set at reasoner creation
     * time. During satisfiability (subsumption) checking the reasoner will
     * check to see if the time it has spent doing the single check is longer
     * than the value returned by this method. If this is the case, the reasoner
     * will throw a {@link org.semanticweb.owlapi.reasoner.TimeOutException} in
     * the thread that is executing the reasoning process. <br>
     * Note that clients that want a higher level timeout, at the level of
     * classification for example, should start their own timers and request
     * that the reasoner interrupts the current process using the
     * {@link #interrupt()} method.
     * 
     * @return The time out in milliseconds for basic reasoner operation. By
     *         default this is the value of {@link Long#MAX_VALUE}.
     */
    long getTimeOut();

    /**
     * Gets the Fresh Entity Policy in use by this reasoner. The policy is set
     * at reasoner creation time.
     * 
     * @return The policy.
     */
    @Nonnull
    FreshEntityPolicy getFreshEntityPolicy();

    /**
     * Gets the IndividualNodeSetPolicy in use by this reasoner. The policy is
     * set at reasoner creation time.
     * 
     * @return The policy.
     */
    @Nonnull
    IndividualNodeSetPolicy getIndividualNodeSetPolicy();

    /**
     * Disposes of this reasoner. This frees up any resources used by the
     * reasoner and detaches the reasoner as an
     * {@link org.semanticweb.owlapi.model.OWLOntologyChangeListener} from the
     * {@link org.semanticweb.owlapi.model.OWLOntologyManager} that manages the
     * ontologies contained within the reasoner.
     */
    void dispose();
}
