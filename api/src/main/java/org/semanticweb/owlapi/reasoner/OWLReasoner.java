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
 * <p>
 * An OWLReasoner reasons over a set of axioms that is defined by the imports closure of a particular ontology - the
 * "root" ontology.  This ontology can be obtained using the {@link OWLReasoner#getRootOntology()} method.
 * When the client responsible for creating the reasoner has finished with the
 * reasoner instance it must call the {@link #dispose()} method to free any resources that are used by the reasoner.
 * In general, reasoners should not be instantiated directly, but should be created using the appropriate
 * {@link org.semanticweb.owlapi.reasoner.OWLReasonerFactory}.
 * </p>
 * <p>
 * At creation time, an OWLReasoner will attach itself as a listener to the {@link org.semanticweb.owlapi.model.OWLOntologyManager}
 * that manages the root ontology.  The reasoner will listen to any
 * {@link org.semanticweb.owlapi.model.OWLOntologyChange}s and respond to these so that any queries that are asked after
 * the ontology changes are answered with respect to the changed ontologies.  Note that this does not guarentee that
 * the reasoner implementation will respond to changes in an incremental (and efficient manner) manner.
 * </p>
 * <h2>Nodes</h2>
 * <p>
 * The reasoner interface contains methods that return {@link org.semanticweb.owlapi.reasoner.NodeSet}s.  These are
 * sets of {@link org.semanticweb.owlapi.reasoner.Node}s.  A <code>Node</code> contains entities.
 * </p>
 * <p>
 * For a <code>Node&lt;OWLClass&gt;</code>
 * of classes, each class in the node is equivalent to the other classes in the <code>Node</code> with respect to the
 * imports closure of the root ontology.
 * </p>
 * <p>
 * For a <code>Node&lt;OWLObjectProperty&gt;</code> of object properties, each
 * object property in the <code>Node</code> is equivalent to the other object properties in the node with respect to the
 * imports closure of the root ontology.
 * </p>
 * <p>
 * For a <code>Node&lt;OWLDataProperty&gt;</code> of data properties, each data property
 * in the <code>Node</code> is equivalent to the other data properties in the node with respect to the
 * imports closure of the root ontology.
 * </p>
 * <p>
 * For a <code>Node&lt;OWLNamedIndividual&gt;</code> of named individuals, each individual in the node
 * is the same as the other individuals in the node with respect to the
 * imports closure of the root ontology.
 * </p>
 * </p>
 * <p/>
 * <p>
 * By abuse of notation, we say that a <code>NodeSet</code> "contains" an entity if that entity is contained in one
 * of the <code>Nodes</code> in the <code>NodeSet</code>.
 * </p>
 * <p/>
 *
 * <h2>Hierarchies</h2>
 *
 * A hierachy (class hierachy, object property hierarchy, data property hierarchy) is viewed as a directed acyclic
 * graph (DAG) containing nodes connected via edges.  Each node in the hierarchy represents a set of entities that
 * are equivalent to each other.  Each hierarchy has a top node (see {@link Node#isTopNode()}) and a bottom node
 * (see {@link Node#isBottomNode()}).
 * </p>
 * The figure below shows an example class hierarchy.  Each box in the hierarchy represents a <code>Node</code>.  In
 * this case the top node contains <code>owl:Thing</code> and the bottom node contains <code>owl:Nothing</code>
 * because the nodes in the hierarchy are <code>OWLClass</code> nodes.  In this case, class <code>G</code>
 * is equivalent to <code>owl:Thing</code> so it appears as an entity in the top node along with <code>owl:Thing</code>.
 * Similarly, class <code>K</code> is unsatisfiable, so it is equivalent to <code>owl:Nothing</code>, and therefore
 * appears in the bottom node containing <code>owl:Nothing</code>.  In this example, classes <code>A</code> and
 * <code>B</code> are equivalent so they appear in one node, also, classes <code>D</code> and <code>F</code> are
 * equivalent so they appear in one node.
 * </p>
 * Asking for the subclasses of a given class (expression) returns the a <code>NodeSet</code> containing the nodes that contain classes
 * that are strict subclasses of the specified class (expression). For example, asking for the subclasses of class <code>C</code>
 * returns the <code>NodeSet</code> <code>{E}</code> and
 * <code>{owl:Nothing, K}</code>.
 * </p>
 * Asking for the direct subclasses of a given class (expression) returns the <code>NodeSet</code> that
 * contains the nodes that contains classes that are direct subclasses of the specified class.  For example, asking for
 * the direct subclasses of class <code>A</code> returns the <code>NodeSet</code>
 * containing the nodes <code>{C}</code> and <code>{D, F}</code>.  Note that there are convenience methods on
 * {@link NodeSet} and {@link org.semanticweb.owlapi.reasoner.Node} that can be used to directly access the entities
 * in a <code>NodeSet</code> without having to iterate over the nodes and entities in a <code>NodeSet</code>. For
 * example, a "plain" set of classes contained inside the <code>Nodes</code> contained inside a <code>NodeSet</code>
 * can easily be obtained using the {@link NodeSet#getFlattened()} method.  In this case we could quickly obtain <code>{C,
 * D, F}</code> as the direct subclasses of <code>A</code> simply by using the
 * {@link #getSubClasses(org.semanticweb.owlapi.model.OWLClassExpression, boolean)}} (with boolean=true) method on
 * <code>OWLReasoner</code> and then we could use the {@link NodeSet#getFlattened()} method on the retuned <code>NodeSet</code>.
 * </p>
 * Asking for equivalent classes of a class (expression) returns a <code>Node</code> that contains classes that are equivalent to
 * the class (expression) .  For example, asking for the equivalent classes of <code>owl:Nothing</code>
 * (i.e. asking for the unsatisfiable classes) returns the <code>Node</code> <code>{owl:Nothing, K}</code>.
 * </p>
 * <div align="center">
 *  <img src="../../../../doc-files/hierarchy.png"/>
 * </div>
 * </p>
 *
 * <h2>Definitions</h2>
 * <p>
 * In what follows, an extension of the <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/">OWL 2 Functional Syntax</a>
 * is given in order to capture notions like a class being a "direct" subclass of another class.
 * <p/>
 *
 * <h3>StrictSubClassOf</h3>
 * <p>
 * Given two class expressions <code>CE1</code> and <code>CE2</code> and an ontology <code>O</code>, <code>CE1</code> is
 * a strict subclass of <code>CE2</code>, written <code>StrictSubClassOf(CE1 CE2)</code> if <code>O</code> entails
 * <code>SubClassOf(CE1 CE2)</code> and <code>O</code> does not entail <code>SubClassOf(CE2 CE1)</code>
 * <p/>
 *
 * <h3>DirectSubClassOf</h3>
 * <p>
 * Given two class expressions <code>CE1</code> and <code>CE2</code> and an ontology <code>O</code>,  <code>CE1</code>
 * is a <emph>direct</emph> subclass of <code>CE2</code>, written <code>DirectSubClassOf(CE1 CE2)</code>, with respect
 * to <code>O</code> if <code>O</code> entails <code>StrictSubClassOf(CE1 CE2)</code> and there is no class name
 * <code>C</code> in the signature of <code>O</code> such that <code>O</code> entails <code>StrictSubClassOf(CE1 C)</code>
 * and <code>O</code> entails <code>StrictSubClassOf(C CE2)</code>.
 * </p>
 *
 * <h3>StrictSubObjectPropertyOf</h3>
 * <p>
 * Given two object property expressions <code>OPE1</code> and <code>OPE2</code> and an ontology <code>O</code>,
 * <code>OPE1</code> is a strict subproperty of <code>OPE2</code>, written <code>StrictSubObjectPropertyOf(OPE1 OPE2)</code>
 * if <code>O</code> entails <code>SubObjectPropertyOf(OPE1 OPE2)</code> and <code>O</code> does not entail
 * <code>SubObjectPropertyOf(OPE1 OPE2)</code>
 * <p/>
 *
 *
 * <h3>DirectSubObjectPropertyOf</h3>
 * <p>
 * Given two object property expressions <code>OPE1</code> and <code>OPE2</code> and an ontology <code>O</code>,
 * <code>OPE1</code> is a <emph>direct</emph> subproperty of <code>OPE2</code>, written <code>DirectSubObjectPropertyOf(OPE1 OPE2)</code>,
 * with respect to <code>O</code> if <code>O</code> entails <code>StrictSubObjectPropertyOf(OPE1 OPE2)</code> and
 * there is no object property name <code>P</code> in the signature of <code>O</code> such that <code>O</code> entails
 * <code>StrictSubObjectPropertyOf(OPE1 P)</code> and <code>O</code> entails <code>StrictSubObjectPropertyOf(P OPE2)</code>.
 * </p>
 *
 * <h3>StrictSubDataPropertyOf</h3>
 * <p>
 * Given two dbject property expressions <code>DPE1</code> and <code>DPE2</code> and an ontology <code>O</code>,
 * <code>DPE1</code> is a strict subproperty of <code>DPE2</code>, written <code>StrictSubDataPropertyOf(DPE1 DPE2)</code>
 * if <code>O</code> entails <code>SubDataPropertyOf(DPE1 DPE2)</code> and <code>O</code> does not entail
 * <code>SubDataPropertyOf(DPE1 DPE2)</code>
 * <p/>
 *
 *
 * <h3>DirectSubDataPropertyOf</h3>
 * <p>
 * Given two data property expressions <code>DPE1</code> and <code>DPE2</code> and an ontology <code>O</code>,
 * <code>DPE1</code> is a <emph>direct</emph> subproperty of <code>DPE2</code>, written <code>DirectSubDataPropertyOf(DPE1 DPE2)</code>,
 * with respect to <code>O</code> if <code>O</code> entails <code>StrictSubDataPropertyOf(DPE1 DPE2)</code> and
 * there is no data property name <code>P</code> in the signature of <code>O</code> such that <code>O</code> entails
 * <code>StrictSubDataPropertyOf(DPE1 P)</code> and <code>O</code> entails <code>StrictSubDataPropertyOf(P DPE2)</code>.
 * </p>
 *
 * <h3>DirectClassAssertion</h3>
 *
 * Given an individual <code>j</code> and a class expression <code>CE</code> and an ontology <code>O</code>, <code>CE</code>
 * is a direct class assertion (type) for <code>j</code>, written <code>DirectClassAssertion(CE j)</code>, if <code>O</code> entails
 * <code>ClassAssertion(CE j)</code> and there is no class name <code>C</code> in the signature of <code>O</code>
 * such that <code>O</code> entails <code>ClassAssertion(C j)</code> and <code>O</code> entails
 * <code>StrictSubClassOf(C CE)</code>.
 *
 *
 */
public interface OWLReasoner {


    /**
     * Gets the "root" ontology that is loaded into this reasoner.  The reasoner takes into account the axioms
     * in this ontology and its imports closure when reasoning.  Note that the root ontology is set at reasoner
     * creation time and cannot be changed thereafter.  Clients that want to add ontologies to and remove ontologies
     * from the reasoner after creation time should create a "dummy" ontology that imports the "real" ontologies and
     * then specify the dummy ontology as the root ontology at reasoner creation time.
     *
     * @return The root ontology that is loaded into the reasoner.
     */
    OWLOntology getRootOntology();

    /**
     * Asks the reasoner to interrupt what it is currently doing.  An ReasonerInterruptedException will be thrown in the
     * thread that invoked the last reasoner operation.  The OWL API is not thread safe in general, but it is likely
     * that this method will be called from another thread than the event dispatch thread or the thread in which
     * reasoning takes place.
     */
    void interrupt();

    /**
     * Determines if the imports closure of the root ontology (the ontology returned by
     * {@link org.semanticweb.owlapi.reasoner.OWLReasoner#getRootOntology()}) is consistent.
     *
     * @return <code>true</code> if the imports closure of the root ontology is consistent,
     *         or <code>false</code> if the imports closure of the root ontology is inconsistent.
     *
     * @throws ReasonerInterruptedException if the reasoning process was interrupted for any particular reason (for example if
     *                                      reasoning was cancelled by a client process).
     * @throws TimeOutException if the reasoning processed timed out after the specified amount of time.
     * See {@link #getTimeOut()}
     */
    boolean isConsistent() throws ReasonerInterruptedException, TimeOutException;

    /**
     * A convenience method that determines if the specified class expression is satisfiable with respect to the
     * axioms in the imports closure of the root ontology.
     *
     * @param classExpression The class expression
     * @return <code>true</code> if classExpression is satisfiable with respect to the set of axioms, or
     *         <code>false</code> if classExpression is unsatisfiable with respect to the axioms.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileExpression if <code>classExpression</code> is not within the profile that is
     * supported by this reasoner.
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     *
     */
    boolean isSatisfiable(OWLClassExpression classExpression) throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileExpression, EntitiesNotInSignatureException, InconsistentOntologyException;


    /**
     * A convenience method that determines if the specified axiom is entailed by the set of axioms in the
     * imports closure of the root ontology.
     *
     * @param axiom The axiom
     * @return <code>true</code> if {@code axiom} is entailed by the reasoner axioms or <code>false</code> if
     *         {@code axiom} is not entailed by the reasoner axioms.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     * @throws UnsupportedEntailmentTypeException
     *                                      if the reasoner cannot perform a check to see if the specified
     *                                      axiom is entailed
     * @throws AxiomNotInProfileException if <code>axiom</code> is not in the profile that is supported by this reasoner.
     * @see #isEntailmentCheckingSupported(org.semanticweb.owlapi.model.AxiomType)
     */
    boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, EntitiesNotInSignatureException, InconsistentOntologyException;

    /**
     * Determines if entailment checking for the specified axiom type is supported.
     *
     * @param axiomType The axiom type
     * @return <code>true</code> if entailment checking for the specified axiom type is supported, otherwise
     *         <code>false</code>. If <code>true</code> then asking {@link #isEntailed(org.semanticweb.owlapi.model.OWLAxiom)}
     *         will <em>not</em> throw an exception of {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}.
     *         If <code>false</code> then asking {@link #isEntailed(org.semanticweb.owlapi.model.OWLAxiom)} <em>will</em> throw
     *         an {@link org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException}.
     */
    boolean isEntailmentCheckingSupported(AxiomType axiomType);


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Methods for dealing with the class hierarchy
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the set of named classes that are the strict (potentially direct) subclasses of the specified class expression with respect to the
     * imports closure of the root ontology.  Note that the classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param ce The class expression whose strict (direct) subclasses are to be retrieved.
     * @param direct Specifies if the direct subclasses should be retrived (<code>true</code>) or if the all subclasses (descendant)
     * classes should be retrieved (<code>false</code>).
     * @return If direct is <code>true</code>, a <code>NodeSet</code> such that for each class <code>C</code> in the <code>NodeSet</code>
     * the root ontology imports closure entails <code>DirectSubClassOf(C, ce)</code>.
     * </p>
     * If direct is <code>false</code>, a <code>NodeSet</code> such that for each class <code>C</code> in the <code>NodeSet</code>
     * the root ontology imports closure entails <code>StrictSubClassOf(C, ce)</code>.
     * </p>
     * If <code>ce</code> is equivalent to <code>owl:Nothing</code> then the empty <code>NodeSet</code> will be returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileExpression if <code>classExpression</code> is not within the profile that is
     * supported by this reasoner.
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the set of named classes that are the strict (potentially direct) super classes of the specified class expression with respect to the
     * imports closure of the root ontology.  Note that the classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param ce The class expression whose strict (direct) super classes are to be retrieved.
     * @param direct Specifies if the direct super classes should be retrived (<code>true</code>) or if the all super classes (ancestors)
     * classes should be retrieved (<code>false</code>).
     * @return If direct is <code>true</code>, a <code>NodeSet</code> such that for each class <code>C</code> in the <code>NodeSet</code>
     * the root ontology imports closure entails <code>DirectSubClassOf(ce, C)</code>.
     * </p>
     * If direct is <code>false</code>, a <code>NodeSet</code> such that for each class <code>C</code> in the <code>NodeSet</code>
     * the root ontology imports closure entails <code>StrictSubClassOf(ce, C)</code>.
     * </p>
     * If <code>ce</code> is equivalent to <code>owl:Thing</code> then the empty <code>NodeSet</code> will be returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileExpression if <code>classExpression</code> is not within the profile that is
     * supported by this reasoner.
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the set of named classes that are equivalent to the specified class expression with respect to the imports
     * closure of the root ontology. The classes are returned as a {@link org.semanticweb.owlapi.reasoner.Node}.
     *
     * @param ce The class expression whose equivalent classes are to be retrieved.
     * @return A node containing the named classes such that for each named class <code>C</code> in the node the root ontology
     * imports closure entails <code>EquivalentClasses(ce C)</code>.
     * </p>
     * If <code>ce</code> is a named class then <code>ce</code> will be contained in the node.
     * </p>
     * If <code>ce</code> is unsatisfiable with respect to the root ontology imports closure then the node
     * representing and containing <code>owl:Nothing</code>, i.e. the bottom node, will be returned.
     * </p>
     * If <code>ce</code> is equivalent to <code>owl:Thing</code> with respect to the root ontology imports closure
     * then the node representing and containing <code>owl:Thing</code>, i.e. the top node, will be returned
     * </p>.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileExpression if <code>classExpression</code> is not within the profile that is
     * supported by this reasoner.
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLClass> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Methods for dealing with the object property hierarchy
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the set of named object properties that are the strict (potentially direct) subproperties of the specified
     * object property expression with respect to the imports closure of the root ontology.
     * Note that the properties are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The object property expression whose strict (direct) subproperties are to be retrieved.
     * @param direct Specifies if the direct subproperties should be retrived (<code>true</code>) or if the all
     * subproperties (descendants) should be retrieved (<code>false</code>).
     * @return If direct is <code>true</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>DirectSubObjectPropertyOf(P, pe)</code>.
     * </p>
     * If direct is <code>false</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>StrictSubObjectPropertyOf(P, pe)</code>.
     * </p>
     * If <code>pe</code> is equivalent to <code>owl:bottomObjectProperty</code> then the empty <code>NodeSet</code>
     * will be returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLObjectProperty> getSubObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the set of named object properties that are the strict (potentially direct) super properties of the specified
     * object property expression with respect to the imports closure of the root ontology.
     * Note that the properties are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The object property expression whose strict (direct) super properties are to be retrieved.
     * @param direct Specifies if the direct super properties should be retrived (<code>true</code>) or if the all
     * super properties (ancestors) should be retrieved (<code>false</code>).
     * @return If direct is <code>true</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>DirectSubObjectPropertyOf(pe, P)</code>.
     * </p>
     * If direct is <code>false</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>StrictSubObjectPropertyOf(pe, P)</code>.
     * </p>
     * If <code>pe</code> is equivalent to <code>owl:topObjectProperty</code> then the empty <code>NodeSet</code>
     * will be returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLObjectProperty> getSuperObjectProperties(OWLObjectPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the set of named object properties that are equivalent to the specified object property expression with
     * respect to the imports closure of the root ontology. The properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.Node}.
     *
     * @param pe The object property expression whose equivalent properties are to be retrieved.
     * @return A node containing the named object properties such that for each named object property <code>P</code>
     * in the node, the root ontology imports closure entails <code>EquivalentObjectProperties(pe P)</code>.
     * </p>
     * If <code>pe</code> is a named object property then <code>pe</code> will be contained in the node.
     * </p>
     * If <code>pe</code> is unsatisfiable with respect to the root ontology imports closure then the node
     * representing and containing <code>owl:bottomObjectProperty</code>, i.e. the bottom node, will be returned.
     * </p>
     * If <code>ce</code> is equivalent to <code>owl:topObjectProperty</code> with respect to the root ontology imports closure
     * then the node representing and containing <code>owl:topObjectProperty</code>, i.e. the top node, will be returned
     * </p>.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLObjectProperty> getEquivalentObjectProperties(OWLObjectPropertyExpression pe)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the set of named object properties that are the inverses of the specified object property expression with
     * respect to the imports closure of the root ontology.  The properties are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}
     * @param pe The property expression whose inverse properties are to be retrieved.
     * @return A <code>NodeSet</code> containing object properties such that for each object property <code>P</code> in
     * the nodes set, the root ontology imports closure entails <code>InverseObjectProperties(pe, P)</code>
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLObjectProperty> getInverseObjectProperties(OWLObjectPropertyExpression pe)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the named classes that are the direct or indirect domains of this property with respect to the imports
     * closure of the root ontology.  The classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The property expression whose domains are to be retrieved.
     * @param direct Specifies if the direct domains should be retrieved (<code>true</code>), or if all domains
     * should be retrieved (<code>false</code>).
     * @return If <code>direct</code> is <code>true</code>, a <code>NodeSet</code> containing named classes such that for each named
     * class <code>C</code> in the node set, the root ontology imports closure entails <code>ObjectPropertyDomain(pe C)</code> and
     * the root ontology imports closure entails <code>DirectSubClassOf(ObjectSomeValuesFrom(pe owl:Thing) C)</code>
     * </p>
     * If <code>direct</code> is <code>false</code>, a <code>NodeSet</code> containing named classes such that for each named class
     * <code>C</code> in the node set, the root ontology imports closure entails <code>ObjectPropertyDomain(pe C)</code>, that is,
     * the root ontology imports closure entails <code>StrictSubClassOf(ObjectSomeValuesFrom(pe owl:Thing) C)</code> 
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the named classes that are the direct or indirect ranges of this property with respect to the imports
     * closure of the root ontology.  The classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The property expression whose ranges are to be retrieved.
     * @param direct Specifies if the direct ranges should be retrieved (<code>true</code>), or if all ranges
     * should be retrieved (<code>false</code>).
     * @return If <code>direct</code> is <code>true</code>, a <code>NodeSet</code> containing named classes such that for each named
     * class <code>C</code> in the node set, the root ontology imports closure entails <code>ObjectPropertyRange(pe C)</code>
     * (<code>SubClassOf(owl:Thing ObjectAllValuesFrom(pe C))</code>) and there is no other class <code>D</code> in the
     * signature of the root ontology imports closure such that the root ontology imports closure entails
     * <code>StrictSubClassOf(D C)</code> and <code>SubClassOf(owl:Thing ObjectAllValuesFrom(pe D))</code>.
     * </p>
     * If <code>direct</code> is <code>false</code>, a <code>NodeSet</code> containing named classes such that for each named class
     * <code>C</code> in the node set, the root ontology imports closure entails <code>ObjectPropertyRange(pe C)</code>, that is,
     * the root ontology imports closure entails <code>SubClassOf(owl:Thing ObjectAllValuesFrom(pe C))</code>.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Methods for dealing with the data property hierarchy
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the set of named data properties that are the strict (potentially direct) subproperties of the specified
     * data property expression with respect to the imports closure of the root ontology.
     * Note that the properties are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The data property expression whose strict (direct) subproperties are to be retrieved.
     * @param direct Specifies if the direct subproperties should be retrived (<code>true</code>) or if the all
     * subproperties (descendants) should be retrieved (<code>false</code>).
     * @return If direct is <code>true</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>DirectSubDataPropertyOf(P, pe)</code>.
     * </p>
     * If direct is <code>false</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>StrictSubDataPropertyOf(P, pe)</code>.
     * </p>
     * If <code>pe</code> is equivalent to <code>owl:bottomDataProperty</code> then the empty <code>NodeSet</code>
     * will be returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLDataProperty> getSubDataProperties(OWLDataPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the set of named data properties that are the strict (potentially direct) super properties of the specified
     * data property expression with respect to the imports closure of the root ontology.
     * Note that the properties are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The data property expression whose strict (direct) super properties are to be retrieved.
     * @param direct Specifies if the direct super properties should be retrived (<code>true</code>) or if the all
     * super properties (ancestors) should be retrieved (<code>false</code>).
     * @return If direct is <code>true</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>DirectSubDataPropertyOf(pe, P)</code>.
     * </p>
     * If direct is <code>false</code>, a <code>NodeSet</code> such that for each property <code>P</code> in the
     * <code>NodeSet</code> the root ontology imports closure entails <code>StrictSubDataPropertyOf(pe, P)</code>.
     * </p>
     * If <code>pe</code> is equivalent to <code>owl:topDataProperty</code> then the empty <code>NodeSet</code>
     * will be returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the set of named data properties that are equivalent to the specified data property expression with
     * respect to the imports closure of the root ontology. The properties are returned as a
     * {@link org.semanticweb.owlapi.reasoner.Node}.
     *
     * @param pe The data property expression whose equivalent properties are to be retrieved.
     * @return A node containing the named data properties such that for each named data property <code>P</code>
     * in the node, the root ontology imports closure entails <code>EquivalentDataProperties(pe P)</code>.
     * </p>
     * If <code>pe</code> is a named data property then <code>pe</code> will be contained in the node.
     * </p>
     * If <code>pe</code> is unsatisfiable with respect to the root ontology imports closure then the node
     * representing and containing <code>owl:bottomDataProperty</code>, i.e. the bottom node, will be returned.
     * </p>
     * If <code>ce</code> is equivalent to <code>owl:topDataProperty</code> with respect to the root ontology imports closure
     * then the node representing and containing <code>owl:topDataProperty</code>, i.e. the top node, will be returned
     * </p>.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the named classes that are the direct or indirect domains of this property with respect to the imports
     * closure of the root ontology.  The classes are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param pe The property expression whose domains are to be retrieved.
     * @param direct Specifies if the direct domains should be retrieved (<code>true</code>), or if all domains
     * should be retrieved (<code>false</code>).
     * @return If <code>direct</code> is <code>true</code>, a <code>NodeSet</code> containing named classes such that for each named
     * class <code>C</code> in the node set, the root ontology imports closure entails <code>DataPropertyDomain(pe C)</code> and
     * the root ontology imports closure entails <code>DirectSubClassOf(ObjectSomeValuesFrom(pe rdfs:Literal) C)</code>
     * </p>
     * If <code>direct</code> is <code>false</code>, a <code>NodeSet</code> containing named classes such that for each named class
     * <code>C</code> in the node set, the root ontology imports closure entails <code>DataPropertyDomain(pe C)</code>, that is,
     * the root ontology imports closure entails <code>StrictSubClassOf(ObjectSomeValuesFrom(pe rdfs:Literal) C)</code>
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getDataPropertyDomains(OWLDataPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Methods for dealing with individuals and their types
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the named classes which are (potentially direct) types of the specified named individual.  The classes
     *  are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * @param ind The individual whose types are to be retrieved.
     * @param direct Specifies if the direct types should be retrieved (<code>true</code>), or if all types
     * should be retrieved (<code>false</code>).
     * @return If <code>direct</code> is <code>true</code>, a <code>NodeSet</code> containing named classes such
     * that for each named class <code>C</code> in the node set, the root ontology imports closure entails
     * <code>DirectClassAssertion(C, ind)</code>.
     * </p>
     * If <code>direct</code> is <code>false</code>, a <code>NodeSet</code> containing named classes such that for
     * each named class <code>C</code> in the node set, the root ontology imports closure entails
     * <code>ClassAssertion(C, ind)</code>.
     * </p>
     * 
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the individual is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the individuals which are instances of the specified class expression.  The individuals are returned a
     * a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * @param ce The class expression whose instances are to be retrieved.
     * @param direct Specifies if the direct instances should be retrieved (<code>true</code>), or if all instances
     * should be retrieved (<code>false</code>).
     * @return If <code>direct</code> is <code>true</code>, a <code>NodeSet</code> containing named individuals such
     * that for each named individual <code>j</code> in the node set, the root ontology imports closure entails
     * <code>DirectClassAssertion(ce, j)</code>.
     * </p>
     * If <code>direct</code> is <code>false</code>, a <code>NodeSet</code> containing named individuals such that for
     * each named individual <code>j</code> in the node set, the root ontology imports closure entails
     * <code>ClassAssertion(ce, j)</code>.
     * </p>
     * If ce is unsatisfiable with respect to the root ontology imports closure then the empty <code>NodeSet</code>
     * is returned.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws ClassExpressionNotInProfileExpression if the class expression <code>ce</code> is not in the profile
     * that is supported by this reasoner.
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the class expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce, boolean direct)  throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the object property values for the specified individual and object property expression.  The individuals are
     *  returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * @param ind The individual that is the subject of the object property values
     * @param pe The object property expression whose values are to be retrieved for the specified individual
     * @return A <code>NodeSet</code> containing named individuals such that for each individual <code>j</code> in the
     * node set, the root ontology imports closure entails <code>ObjectPropertyAssertion(pe ind j)</code>
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the individual and property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual ind, OWLObjectPropertyExpression pe)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the data property values for the specified individual and data property expression.
     * @param ind The individual that is the subject of the data property values
     * @param pe The data property expression whose values are to be retrieved for the specified individual
     * @return A set of <code>OWLLiteral</code>s containing literals such that for each literal <code>l</code> in the
     * set, the root ontology imports closure entails <code>DataPropertyAssertion(pe ind l)</code>, and, the literal
     * <code>l</code> appears in an axiom that is contained in the imports closure of the root ontology.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the individual and property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind, OWLDataPropertyExpression pe)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the individuals that are the same as the specified individual.
     * @param ind The individual whose same individuals are to be retrieved.
     * @return A node containing individuals such that for each individual <code>j</code> in the node, the root
     * ontology imports closure entails <code>SameIndividual(j, ind)</code>.  Note that the node will contain
     * <code>j</code>.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws EntitiesNotInSignatureException
     *                                       if the signature of the individual is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind)  throws InconsistentOntologyException, EntitiesNotInSignatureException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the time out for the most basic reasoning operations.  That is the maximum time for a
     * satisfiability test, subsumption test etc.  The time out should be set at reasoner creation time.
     * During satisfiability (subsumption) checking the reasoner will check to see if the time it has spent
     * doing the single check is longer than the value returned by this method.  If this is the case, the
     * reasoner will throw a {@link org.semanticweb.owlapi.reasoner.TimeOutException} in the thread that is
     * executing the reasoning process.
     *
     * @return The time out for basic reasoner operation.  By default this is the value of
     * {@link Long#MAX_VALUE}.
     */
    long getTimeOut();



    /**
     * Disposes of this reasoner.  This frees up any resources used by the reasoner and detaches the reasoner
     * as an {@link org.semanticweb.owlapi.model.OWLOntologyChangeListener} from the {@link org.semanticweb.owlapi.model.OWLOntologyManager}
     * that manages the ontologies contained within the reasoner.
     */
    void dispose();


}
