/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.semanticweb.owlapi.util.Version;

/**
 * @author ignazio
 *
 *a threadsafe wrapper for OWLReasoners
 */
public class ThreadSafeOWLReasoner implements OWLReasoner {
	private final OWLReasoner delegate;
	private boolean log = false;

	/**
	 * @param reasoner the reasoner to wrap
	 * @param log true if logging is required
	 */
	public ThreadSafeOWLReasoner(OWLReasoner reasoner, boolean log) {
		this(reasoner);
		this.log=log;
	}
	/**
	 * @param reasoner the reasoner to wrap
	 */
	public ThreadSafeOWLReasoner(OWLReasoner reasoner) {
		if (reasoner == null) {
			throw new IllegalArgumentException(
					"The input reasoner cannot be null");
		}
		delegate = reasoner;
	}

	public String getReasonerName() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getReasonerName()");
				System.out.flush();
			}
			return this.delegate.getReasonerName();
		}
	}

	public Version getReasonerVersion() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getReasonerVersion()");
				System.out.flush();
			}
			return this.delegate.getReasonerVersion();
		}
	}

	public BufferingMode getBufferingMode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getBufferingMode()");
				System.out.flush();
			}
			return this.delegate.getBufferingMode();
		}
	}

	public void flush() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.flush()");
				System.out.flush();
			}
			this.delegate.flush();
		}
	}

	public List<OWLOntologyChange> getPendingChanges() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getPendingChanges()");
				System.out.flush();
			}
			return this.delegate.getPendingChanges();
		}
	}

	public Set<OWLAxiom> getPendingAxiomAdditions() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getPendingAxiomAdditions()");
				System.out.flush();
			}
			return this.delegate.getPendingAxiomAdditions();
		}
	}

	public Set<OWLAxiom> getPendingAxiomRemovals() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getPendingAxiomRemovals()");
				System.out.flush();
			}
			return this.delegate.getPendingAxiomRemovals();
		}
	}

	public OWLOntology getRootOntology() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getRootOntology()");
				System.out.flush();
			}
			return this.delegate.getRootOntology();
		}
	}

	public void interrupt() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.interrupt()");
				System.out.flush();
			}
			this.delegate.interrupt();
		}
	}

	public void precomputeInferences(InferenceType... inferenceTypes)
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.precomputeInferences() "
						+ Arrays.toString(inferenceTypes));
				System.out.flush();
			}
			this.delegate.precomputeInferences(inferenceTypes);
		}
	}

	public boolean isPrecomputed(InferenceType inferenceType) {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.isPrecomputed() " + inferenceType);
				System.out.flush();
			}
			return this.delegate.isPrecomputed(inferenceType);
		}
	}

	public Set<InferenceType> getPrecomputableInferenceTypes() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getPrecomputableInferenceTypes()");
				System.out.flush();
			}
			return this.delegate.getPrecomputableInferenceTypes();
		}
	}

	public boolean isConsistent() throws ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.isConsistent()");
				System.out.flush();
			}
			return this.delegate.isConsistent();
		}
	}

	public boolean isSatisfiable(OWLClassExpression classExpression)
			throws ReasonerInterruptedException, TimeOutException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.isSatisfiable() " + classExpression);
				System.out.flush();
			}
			return this.delegate.isSatisfiable(classExpression);
		}
	}

	public Node<OWLClass> getUnsatisfiableClasses()
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getUnsatisfiableClasses()");
				System.out.flush();
			}
			return this.delegate.getUnsatisfiableClasses();
		}
	}

	public boolean isEntailed(OWLAxiom axiom)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.isEntailed() " + axiom);
				System.out.flush();
			}
			try {
			return this.delegate.isEntailed(axiom);
			}catch (RuntimeException e) {
				throw new RuntimeException("Exception checking entailment of axiom: "+axiom, e);
			}
		}
	}

	public boolean isEntailed(Set<? extends OWLAxiom> axioms)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.isEntailed() " + axioms);
				System.out.flush();
			}
			return this.delegate.isEntailed(axioms);
		}
	}

	public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.isEntailmentCheckingSupported() "
						+ axiomType);
				System.out.flush();
			}
			return this.delegate.isEntailmentCheckingSupported(axiomType);
		}
	}

	public Node<OWLClass> getTopClassNode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getTopClassNode()");
				System.out.flush();
			}
			return this.delegate.getTopClassNode();
		}
	}

	public Node<OWLClass> getBottomClassNode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getBottomClassNode()");
				System.out.flush();
			}
			return this.delegate.getBottomClassNode();
		}
	}

	public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException,
			ClassExpressionNotInProfileException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSubClasses() " + ce + " " + direct);
				System.out.flush();
			}
			NodeSet<OWLClass> subClasses = this.delegate.getSubClasses(ce,
					direct);
			return subClasses;
		}
	}

	public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce,
			boolean direct) throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSuperClasses()" + ce + " " + direct);
				System.out.flush();
			}
			NodeSet<OWLClass> superClasses = this.delegate.getSuperClasses(ce,
					direct);
			return superClasses;
		}
	}

	public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce)
			throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getEquivalentClasses() " + ce);
				System.out.flush();
			}
			Node<OWLClass> equivalentClasses = this.delegate
					.getEquivalentClasses(ce);
			return equivalentClasses;
		}
	}

	public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getDisjointClasses() " + ce);
				System.out.flush();
			}
			NodeSet<OWLClass> disjointClasses = this.delegate
					.getDisjointClasses(ce);
			return disjointClasses;
		}
	}

	public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getTopObjectPropertyNode()");
				System.out.flush();
			}
			return this.delegate.getTopObjectPropertyNode();
		}
	}

	public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getBottomObjectPropertyNode()");
				System.out.flush();
			}
			return this.delegate.getBottomObjectPropertyNode();
		}
	}

	public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSubObjectProperties() " + pe + " "
						+ direct);
				System.out.flush();
			}
			NodeSet<OWLObjectPropertyExpression> subObjectProperties = this.delegate
					.getSubObjectProperties(pe, direct);
			return subObjectProperties;
		}
	}

	public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSuperObjectProperties() " + pe + " "
						+ direct);
				System.out.flush();
			}
			NodeSet<OWLObjectPropertyExpression> superObjectProperties = this.delegate
					.getSuperObjectProperties(pe, direct);
			return superObjectProperties;
		}
	}

	public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getEquivalentObjectProperties() " + pe);
				System.out.flush();
			}
			Node<OWLObjectPropertyExpression> equivalentObjectProperties = this.delegate
					.getEquivalentObjectProperties(pe);
			return equivalentObjectProperties;
		}
	}

	public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getDisjointObjectProperties() " + pe);
				System.out.flush();
			}
			return this.delegate.getDisjointObjectProperties(pe);
		}
	}

	public Node<OWLObjectPropertyExpression> getInverseObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getInverseObjectProperties() " + pe);
				System.out.flush();
			}
			return this.delegate.getInverseObjectProperties(pe);
		}
	}

	public NodeSet<OWLClass> getObjectPropertyDomains(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getObjectPropertyDomains() " + pe + " "
						+ direct);
				System.out.flush();
			}
			return this.delegate.getObjectPropertyDomains(pe, direct);
		}
	}

	public NodeSet<OWLClass> getObjectPropertyRanges(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getObjectPropertyRanges() " + pe + " "
						+ direct);
				System.out.flush();
			}
			return this.delegate.getObjectPropertyRanges(pe, direct);
		}
	}

	public Node<OWLDataProperty> getTopDataPropertyNode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getTopDataPropertyNode()");
				System.out.flush();
			}
			return this.delegate.getTopDataPropertyNode();
		}
	}

	public Node<OWLDataProperty> getBottomDataPropertyNode() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getBottomDataPropertyNode()");
				System.out.flush();
			}
			return this.delegate.getBottomDataPropertyNode();
		}
	}

	public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSubDataProperties() " + pe + " "
						+ direct);
				System.out.flush();
			}
			return this.delegate.getSubDataProperties(pe, direct);
		}
	}

	public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSuperDataProperties() " + pe + " "
						+ direct);
				System.out.flush();
			}
			return this.delegate.getSuperDataProperties(pe, direct);
		}
	}

	public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getEquivalentDataProperties() " + pe);
				System.out.flush();
			}
			return this.delegate.getEquivalentDataProperties(pe);
		}
	}

	public NodeSet<OWLDataProperty> getDisjointDataProperties(
			OWLDataPropertyExpression pe) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getDisjointDataProperties() " + pe);
				System.out.flush();
			}
			return this.delegate.getDisjointDataProperties(pe);
		}
	}

	public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getDataPropertyDomains() " + pe + " "
						+ direct);
				System.out.flush();
			}
			return this.delegate.getDataPropertyDomains(pe, direct);
		}
	}

	public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getTypes() " + ind + " " + direct);
				System.out.flush();
			}
			return this.delegate.getTypes(ind, direct);
		}
	}

	public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce,
			boolean direct) throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getInstances() " + ce + " " + direct);
				System.out.flush();
			}
			return this.delegate.getInstances(ce, direct);
		}
	}

	public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
			OWLNamedIndividual ind, OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getObjectPropertyValues() " + ind + " "
						+ pe);
				System.out.flush();
			}
			return this.delegate.getObjectPropertyValues(ind, pe);
		}
	}

	public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind,
			OWLDataProperty pe) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getDataPropertyValues() " + ind + " "
						+ pe);
				System.out.flush();
			}
			return this.delegate.getDataPropertyValues(ind, pe);
		}
	}

	public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getSameIndividuals() " + ind);
				System.out.flush();
			}
			return this.delegate.getSameIndividuals(ind);
		}
	}

	public NodeSet<OWLNamedIndividual> getDifferentIndividuals(
			OWLNamedIndividual ind) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getDifferentIndividuals() " + ind);
				System.out.flush();
			}
			return this.delegate.getDifferentIndividuals(ind);
		}
	}

	public long getTimeOut() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getTimeOut()");
				System.out.flush();
			}
			return this.delegate.getTimeOut();
		}
	}

	public FreshEntityPolicy getFreshEntityPolicy() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getFreshEntityPolicy()");
				System.out.flush();
			}
			return this.delegate.getFreshEntityPolicy();
		}
	}

	public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.getIndividualNodeSetPolicy()");
				System.out.flush();
			}
			return this.delegate.getIndividualNodeSetPolicy();
		}
	}

	public void dispose() {
		synchronized (delegate) {
			if (log) {
				System.out.println(Thread.currentThread().getName()+" reasoner.dispose()");
				System.out.flush();
			}
			this.delegate.dispose();
		}
	}
}
