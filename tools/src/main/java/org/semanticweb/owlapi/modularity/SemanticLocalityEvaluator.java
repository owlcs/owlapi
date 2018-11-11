package org.semanticweb.owlapi.modularity;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/*
 * Following com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator, and
 * modified for a more modern, thread safe and maintainable implementation. Original Copyright is:
 *
 * This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/**
 * Thread safe class for checking semantic locality.
 *
 * @author Marc Robin Nolte (modified version)
 *
 */
public class SemanticLocalityEvaluator implements LocalityEvaluator {

	/**
	 * Class to replace certain {@link OWLClassExpression}s within an
	 * {@link OWLAxiom} with \bottom.
	 */
	private class BottomReplacer implements OWLAxiomVisitor, OWLClassExpressionVisitor {

		/**
		 * The {@link OWLAxiom} with replaced {@link OWLClassExpression}s. May be
		 * manipulated during evaluation.
		 */
		@Nullable
		private OWLAxiom newAxiom;

		/**
		 * The {@link OWLClassExpression} with replaced {@link OWLClassExpression}s. May
		 * be manipulated during evaluation.
		 */
		@Nullable
		private OWLClassExpression newClassExpression;

		/**
		 * The signature that should not be replaced.
		 */
		@Nonnull
		private final Collection<OWLEntity> signature;

		/**
		 * Instantiates a new {@link BottomReplacer}.
		 *
		 * @param collection The signature that should not be replaced with \bottom.
		 */
		private BottomReplacer(final Collection<OWLEntity> collection) {
			signature = collection;
		}

		/**
		 * Returns a new {@link OWLAxiom} resulting by replacing all entities not
		 * present in this {@link BottomReplacer}'s signature with \bottom.
		 *
		 * @param axiom The {@link OWLAxiom}, which entities should be replaced
		 * @return The new {@link OWLAxiom} as specified above
		 */
		public @Nonnull OWLAxiom replaceBottom(final OWLAxiom axiom) {
			newAxiom = null;
			axiom.accept(this);
			if (newAxiom == null) {
				throw new OWLRuntimeException("Unsupported axiom: " + axiom);
			}
			return newAxiom;
		}

		/**
		 * Returns a new {@link OWLClassExpression} resulting by replacing all entities
		 * of the given {@link OWLClassExpression} not present in this
		 * {@link BottomReplacer}'s signature with \bottom.
		 *
		 * @param classExpression The {@link OWLClassExpression}, which entities should
		 *                        be replaced
		 * @return The new {@link OWLClassExpression} as specified above
		 */
		public @Nonnull OWLClassExpression replaceBottom(final OWLClassExpression classExpression) {
			newClassExpression = null;
			classExpression.accept(this);
			if (newClassExpression == null) {
				throw new OWLRuntimeException("Unsupported class expression: " + classExpression);
			}
			return newClassExpression;
		}

		/**
		 * Returns a {@link Stream} of new {@link OWLClassExpression} resulting by
		 * replacing all entities of the given {@link OWLClassExpression}s not present
		 * in this {@link BottomReplacer}'s signature with \bottom.
		 *
		 * @param classExpressions The {@link OWLClassExpression}s, which entities
		 *                         should be replaced
		 * @return A {@link Stream} as specified above
		 */
		public @Nonnull Stream<OWLClassExpression> replaceBottom(
				final Stream<? extends OWLClassExpression> classExpressions) {
			return classExpressions.map(this::replaceBottom);
		}

		@Override
		public void visit(@Nonnull final OWLClass ce) {
			if (signature.contains(ce)) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLDataAllValuesFrom ce) {
			if (signature.contains(ce.getProperty().asOWLDataProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLThing();
			}
		}

		@Override
		public void visit(final OWLDataExactCardinality ce) {
			if (signature.contains(ce.getProperty().asOWLDataProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLDataHasValue ce) {
			newClassExpression = dataFactory.getOWLNothing();
		}

		@Override
		public void visit(final OWLDataMaxCardinality ce) {
			if (signature.contains(ce.getProperty().asOWLDataProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLThing();
			}
		}

		@Override
		public void visit(final OWLDataMinCardinality ce) {
			if (signature.contains(ce.getProperty().asOWLDataProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLDataSomeValuesFrom ce) {
			if (signature.contains(ce.getProperty().asOWLDataProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLDisjointClassesAxiom axiom) {
			newAxiom = dataFactory.getOWLDisjointClassesAxiom(replaceBottom(axiom.classExpressions()));
		}

		@Override
		public void visit(final OWLEquivalentClassesAxiom axiom) {
			newAxiom = dataFactory.getOWLEquivalentClassesAxiom(replaceBottom(axiom.classExpressions()));
		}

		@Override
		public void visit(final OWLObjectAllValuesFrom ce) {
			if (signature.contains(ce.getProperty().getNamedProperty())) {
				newClassExpression = dataFactory.getOWLObjectAllValuesFrom(ce.getProperty(),
						replaceBottom(ce.getFiller()));
			} else {
				newClassExpression = dataFactory.getOWLThing();
			}
		}

		@Override
		public void visit(final OWLObjectComplementOf ce) {
			newClassExpression = dataFactory.getOWLObjectComplementOf(replaceBottom(ce.getOperand()));
		}

		@Override
		public void visit(final OWLObjectExactCardinality ce) {
			if (signature.contains(ce.getProperty().getNamedProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLObjectHasSelf ce) {
			newClassExpression = dataFactory.getOWLNothing();
		}

		@Override
		public void visit(final OWLObjectHasValue ce) {
			newClassExpression = dataFactory.getOWLNothing();
		}

		@Override
		public void visit(final OWLObjectIntersectionOf ce) {
			newClassExpression = dataFactory.getOWLObjectIntersectionOf(replaceBottom(ce.operands()));
		}

		@Override
		public void visit(final OWLObjectMaxCardinality ce) {
			if (signature.contains(ce.getProperty().getNamedProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLThing();
			}
		}

		@Override
		public void visit(final OWLObjectMinCardinality ce) {
			if (signature.contains(ce.getProperty().getNamedProperty())) {
				newClassExpression = ce;
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLObjectOneOf ce) {
			newClassExpression = dataFactory.getOWLNothing();
		}

		@Override
		public void visit(final OWLObjectSomeValuesFrom ce) {
			if (signature.contains(ce.getProperty().getNamedProperty())) {
				newClassExpression = dataFactory.getOWLObjectSomeValuesFrom(ce.getProperty(),
						replaceBottom(ce.getFiller()));
			} else {
				newClassExpression = dataFactory.getOWLNothing();
			}
		}

		@Override
		public void visit(final OWLObjectUnionOf ce) {
			newClassExpression = dataFactory.getOWLObjectUnionOf(replaceBottom(ce.operands()));
		}

		@Override
		public void visit(final OWLSubClassOfAxiom axiom) {
			newAxiom = dataFactory.getOWLSubClassOfAxiom(replaceBottom(axiom.getSuperClass()),
					replaceBottom(axiom.getSubClass()));
		}
	}

	/**
	 * The used {@link OWLDataFactory}.
	 */
	private final @Nonnull OWLDataFactory dataFactory;

	/**
	 * The {@link OWLReasoner} to check if axioms are tautologies.
	 */
	private final @Nonnull OWLReasoner reasoner;

	/**
	 * Instantiates a new {@link SemanticLocalityEvaluator}
	 *
	 * @param ontologyManager The {@link OWLOntologyManager} to create a reasoner
	 *                        with
	 * @param reasonerFactory The {@link OWLReasonerFactory} to create a reasoner
	 *                        with
	 */
	public SemanticLocalityEvaluator(final OWLOntologyManager ontologyManager,
			final OWLReasonerFactory reasonerFactory) {
		dataFactory = Objects.requireNonNull(ontologyManager, "The given ontologyManager may not be null")
				.getOWLDataFactory();
		try {
			reasoner = Objects.requireNonNull(reasonerFactory, "The given reasonerFactory may not be null")
					.createNonBufferingReasoner(ontologyManager.createOntology());
		} catch (final OWLOntologyCreationException e) {
			throw new OWLRuntimeException(e);
		}

	}

	@Override
	public boolean isLocal(final OWLAxiom axiom, final Collection<OWLEntity> signature) {
		return !axiom.isLogicalAxiom()
				|| reasoner.isEntailed(new BottomReplacer(Objects.requireNonNull(signature, "signature cannot be null"))
						.replaceBottom(Objects.requireNonNull(axiom, "The given axiom may not be null")));
	}

}
