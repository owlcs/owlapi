package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.*;

import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 04-Jun-2009
 */
public interface BufferedOWLReasoner {


    /**
     * Gets the "root" ontology that is loaded into this reasoner.  A buffered reasoner takes into account the axioms
     * in this ontology and its imports closure with the "inverse" of the set of ontology changes returned by
     * the {@link #getPendingChanges()} method applied to the imports closure.
     * </p>
     * Note that the root ontology is set at reasoner creation time and cannot be changed thereafter.
     * Clients that want to add ontologies to and remove ontologies
     * from the reasoner after creation time should create a "dummy" ontology that imports the "real" ontologies and
     * then specify the dummy ontology as the root ontology at reasoner creation time.
     *
     * @return The root ontology that is loaded into the reasoner.
     */
    OWLOntology getRootOntology();


    /**
     * Gets the pending changes which need to be taken into consideration by the reasoner so that it is up to date
     * with the root ontology imports closure.  After the {@link #flush()} method is called the set of pending changes
     * will be empty.
     * @return A set of changes.  Note that the changes represent all of the changes that need to be applied to the
     * reasoner.  This may be different from the actual changes to the root ontology imports closure.  For example,
     * suppose the following changes were applied <code>AddAxiom([rootontology], SubClassOf(A B)),
     * RemoveAxiom([rootontology], SubClassAxiom(A B))</code> then the set of pending changes would be empty.
     */
    Set<OWLOntologyChange> getPendingChanges();

    /**
     * Flushes any changes stored in the buffer, which causes the reasoner to take into consideration the
     * changes the current root ontology plus the changes.
     */
    void flush();



    /**
     * Asks the reasoner to interrupt what it is currently doing.  An ReasonerInterruptedException will be thrown in the
     * thread that invoked the last reasoner operation.  The OWL API is not thread safe in general, but it is likely
     * that this method will be called from another thread than the event dispatch thread or the thread in which
     * reasoning takes place.
     */
    void interrupt();

    /**
     * Asks the reasoner to perform various tasks that prepare it for querying.  These tasks include consistency
     * checking, computation of class, object property and data property hierarchies, computation of individual
     * type and the relationships between individuals.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    void prepareReasoner() throws ReasonerInterruptedException, TimeOutException;

    /**
     * Determines if the imports closure of the root ontology (the ontology returned by
     * {@link org.semanticweb.owlapi.reasoner.OWLReasoner#getRootOntology()}) is consistent.  Note that this method
     * will not throw an {@link org.semanticweb.owlapi.reasoner.InconsistentOntologyException} even if the root ontology
     * imports closure is inconsistent.
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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     *
     */
    boolean isSatisfiable(OWLClassExpression classExpression) throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileExpression, UndeclaredEntitiesException, InconsistentOntologyException;


    /**
     * A convenience method that determines if the specified axiom is entailed by the set of axioms in the
     * imports closure of the root ontology.
     *
     * @param axiom The axiom
     * @return <code>true</code> if {@code axiom} is entailed by the reasoner axioms or <code>false</code> if
     *         {@code axiom} is not entailed by the reasoner axioms.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws UndeclaredEntitiesException
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
    boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, UndeclaredEntitiesException, InconsistentOntologyException;

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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the classExpression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLClass> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLObjectProperty> getSubObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLObjectProperty> getSuperObjectProperties(OWLObjectPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLObjectProperty> getEquivalentObjectProperties(OWLObjectPropertyExpression pe)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the set of named object properties that are the inverses of the specified object property expression with
     * respect to the imports closure of the root ontology.  The properties are returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}
     * @param pe The property expression whose inverse properties are to be retrieved.
     * @return A <code>NodeSet</code> containing object properties such that for each object property <code>P</code> in
     * the nodes set, the root ontology imports closure entails <code>InverseObjectProperties(pe, P)</code>
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLObjectProperty> getInverseObjectProperties(OWLObjectPropertyExpression pe)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;

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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;

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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the object property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLDataProperty> getSubDataProperties(OWLDataPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;

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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the data property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getDataPropertyDomains(OWLDataPropertyExpression pe, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the individual is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;

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
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the class expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce, boolean direct)  throws InconsistentOntologyException, ClassExpressionNotInProfileExpression, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the object property values for the specified individual and object property expression.  The individuals are
     *  returned as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     * @param ind The individual that is the subject of the object property values
     * @param pe The object property expression whose values are to be retrieved for the specified individual
     * @return A <code>NodeSet</code> containing named individuals such that for each individual <code>j</code> in the
     * node set, the root ontology imports closure entails <code>ObjectPropertyAssertion(pe ind j)</code>
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the individual and property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual ind, OWLObjectPropertyExpression pe)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


    /**
     * Gets the data property values for the specified individual and data property expression.
     * @param ind The individual that is the subject of the data property values
     * @param pe The data property expression whose values are to be retrieved for the specified individual
     * @return A set of <code>OWLLiteral</code>s containing literals such that for each literal <code>l</code> in the
     * set, the root ontology imports closure entails <code>DataPropertyAssertion(pe ind l)</code>, and, the literal
     * <code>l</code> appears in an axiom that is contained in the imports closure of the root ontology.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the individual and property expression is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind, OWLDataPropertyExpression pe)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;

    /**
     * Gets the individuals that are the same as the specified individual.
     * @param ind The individual whose same individuals are to be retrieved.
     * @return A node containing individuals such that for each individual <code>j</code> in the node, the root
     * ontology imports closure entails <code>SameIndividual(j, ind)</code>.  Note that the node will contain
     * <code>j</code>.
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws UndeclaredEntitiesException
     *                                       if the signature of the individual is not contained within the signature
     *                                       of the imports closure of the root ontology.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException if the reasoner timed out the satisfiability check. See {@link #getTimeOut()}.
     */
    Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind)  throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException;


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
