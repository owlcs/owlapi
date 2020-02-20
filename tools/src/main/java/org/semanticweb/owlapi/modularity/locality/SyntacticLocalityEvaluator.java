package org.semanticweb.owlapi.modularity.locality;
/*
 * Following com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator, and
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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNaryPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * Thread safe class for checking syntactic locality.
 *
 * @author Marc Robin Nolte (modified version)
 *
 */
public enum SyntacticLocalityEvaluator implements LocalityEvaluator {

	/**
	 * The {@link SyntacticLocalityEvaluator} for \bottom-locality: Locality class
	 * obtained when concepts and roles not present in the signature are given empty
	 * interpretations. In the literature, $\emptyset$ for semantic and $\bottom$
	 * for syntactic locality.
	 */
	BOTTOM {
		@Override
		protected LocalityAxiomVisitor createLocalityAxiomVisitor(final OWLAxiom axiom,
				final Collection<OWLEntity> signature) {
			return new BottomLocalityAxiomVisitor(axiom, signature);
		}
	},

	/**
	 * The {@link SyntacticLocalityEvaluator} for \top-locality: Locality class
	 * obtained when concepts not present in the signature are given top concept
	 * interpretation, roles not present are universal role interpretation. In the
	 * literature, $\Delta$ for semantic and $\top$ for syntactic locality.
	 */
	TOP {
		@Override
		protected LocalityAxiomVisitor createLocalityAxiomVisitor(final OWLAxiom axiom,
				final Collection<OWLEntity> signature) {
			return new TopLocalityAxiomVisitor(axiom, signature);
		}
	};

	/**
	 * Class implementing the syntactic \bottom-locality check using the
	 * visitor-pattern. Needs to be instantiated for thread safety.
	 *
	 * @author Marc Robin Nolte (modified version)
	 *
	 */
	private final class BottomLocalityAxiomVisitor extends LocalityAxiomVisitor {

		/**
		 * Class to determine if class expressions are syntactically equivalent to
		 * \bottom for \bottom-locality.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		private final class BottomLocalityBottomEquivalenceEvaluator extends AbstractBottomEquivalenceEvaluator {

			@Override
			public void visit(final OWLClass ce) {
				isEquivalent = ce.isOWLNothing() || !ce.isOWLThing() && !signature.contains(ce);
			}

			@Override
			public void visit(final OWLDataAllValuesFrom ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataExactCardinality ce) {
				isEquivalent = ce.getCardinality() > 0 && !signature.contains(ce.getProperty().asOWLDataProperty());
			}

			@Override
			public void visit(final OWLDataHasValue ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty());
			}

			@Override
			public void visit(final OWLDataMaxCardinality ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataMinCardinality ce) {
				isEquivalent = ce.getCardinality() > 0 && !signature.contains(ce.getProperty().asOWLDataProperty());
			}

			@Override
			public void visit(final OWLDataSomeValuesFrom ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectAllValuesFrom ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectExactCardinality ce) {
				isEquivalent = ce.getCardinality() > 0
						&& (!signature.contains(ce.getProperty().getNamedProperty()) || isEquivalent(ce.getFiller()));
			}

			@Override
			public void visit(final OWLObjectHasSelf ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty());
			}

			@Override
			public void visit(final OWLObjectHasValue ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty());
			}

			@Override
			public void visit(final OWLObjectMaxCardinality ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectMinCardinality ce) {
				isEquivalent = ce.getCardinality() > 0
						&& (!signature.contains(ce.getProperty().getNamedProperty()) || isEquivalent(ce.getFiller()));
			}

			@Override
			public void visit(final OWLObjectSomeValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) || isEquivalent(ce.getFiller());
			}
		}

		/**
		 * Class to determine if class expressions are syntactically equivalent to \top
		 * for a \bottom-locality.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		private final class BottomLocalityTopEquivalenceEvaluator extends AbstractTopEquivalenceEvaluator {

			@Override
			public void visit(final OWLClass ce) {
				isEquivalent = ce.isOWLThing();
			}

			@Override
			public void visit(final OWLDataAllValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty())
						|| ce.getFiller().isTopDatatype();
			}

			@Override
			public void visit(final OWLDataExactCardinality ce) {
				isEquivalent = ce.getCardinality() == 0 && !signature.contains(ce.getProperty().asOWLDataProperty());
			}

			@Override
			public void visit(final OWLDataHasValue ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataMaxCardinality ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty());
			}

			@Override
			public void visit(final OWLDataMinCardinality ce) {
				isEquivalent = ce.getCardinality() == 0
						|| ce.getCardinality() == 1 && !signature.contains(ce.getProperty().asOWLDataProperty())
								&& isTopOrBuiltInDatatype(ce.getFiller())
						|| ce.getCardinality() > 1 && !signature.contains(ce.getProperty().asOWLDataProperty())
								&& isTopOrBuiltInInfiniteDatatype(ce.getFiller());
			}

			@Override
			public void visit(final OWLDataSomeValuesFrom ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectAllValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) || isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectExactCardinality ce) {
				isEquivalent = ce.getCardinality() == 0 && (!signature.contains(ce.getProperty().getNamedProperty())
						|| getBottomEquivalenceEvaluator().isEquivalent(ce.getFiller()));
			}

			@Override
			public void visit(final OWLObjectHasSelf ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectHasValue ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectMaxCardinality ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty())
						|| getBottomEquivalenceEvaluator().isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectMinCardinality ce) {
				isEquivalent = ce.getCardinality() == 0;
			}

			@Override
			public void visit(final OWLObjectSomeValuesFrom ce) {
				isEquivalent = false;
			}

		}

		/**
		 * Instantiates a new {@link BottomLocalityAxiomVisitor}.
		 *
		 * @param axiom     the axiom to test
		 * @param signature the signature to test against
		 */
		private BottomLocalityAxiomVisitor(final OWLAxiom axiom, final Collection<OWLEntity> signature) {
			super(axiom, signature);
		}

		protected boolean checkIfLocalProperties(final OWLNaryPropertyAxiom<? extends OWLPropertyExpression> axiom) {
			final Collection<? extends OWLPropertyExpression> disjs = asList(axiom.properties());
			final int size = disjs.size();
			if (size > 1) {
				boolean nonBottomEquivPropFound = false;
				for (final OWLPropertyExpression dpe : disjs) {
					if (dpe.isOWLDataProperty() && signature.contains(dpe.asOWLDataProperty())
							|| dpe.isOWLObjectProperty() && signature.contains(dpe.asOWLObjectProperty())) {
						if (nonBottomEquivPropFound) {
							return false;
						} else {
							nonBottomEquivPropFound = true;
						}
					}
				}
			}
			return true;
		}

		@Override
		protected BottomLocalityBottomEquivalenceEvaluator createBottomEquivalenceEvaluator() {
			return new BottomLocalityBottomEquivalenceEvaluator();
		}

		@Override
		protected BottomLocalityTopEquivalenceEvaluator createTopEquivalenceEvaluator() {
			return new BottomLocalityTopEquivalenceEvaluator();
		}

		@Override
		public void visit(final OWLAsymmetricObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLDataPropertyAssertionAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLDisjointDataPropertiesAxiom axiom) {
			isLocal = checkIfLocalProperties(axiom);
		}

		@Override
		public void visit(final OWLDisjointObjectPropertiesAxiom axiom) {
			isLocal = checkIfLocalProperties(axiom);
		}

		@Override
		public void visit(final OWLFunctionalDataPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty());
		}

		@Override
		public void visit(final OWLFunctionalObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLInverseFunctionalObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLIrreflexiveObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLNegativeDataPropertyAssertionAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty());
		}

		@Override
		public void visit(final OWLNegativeObjectPropertyAssertionAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLObjectPropertyAssertionAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLObjectPropertyDomainAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty())
					|| getTopEquivalenceEvaluator().isEquivalent(axiom.getDomain());
		}

		@Override
		public void visit(final OWLObjectPropertyRangeAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty())
					|| getTopEquivalenceEvaluator().isEquivalent(axiom.getRange());
		}

		@Override
		public void visit(final OWLReflexiveObjectPropertyAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLSubObjectPropertyOfAxiom axiom) {
			isLocal = !signature.contains(axiom.getSubProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLSubPropertyChainOfAxiom axiom) {
			// Axiom is local iff at least one prop in the chain is
			// bot-equiv
			isLocal = axiom.getPropertyChain().stream().anyMatch(ope -> !signature.contains(ope.getNamedProperty()));
		}

	}

	/**
	 * Abstract superclass for classes implementing the syntactic locality check
	 * using the visitor-pattern. Needs to be instantiated for thread safety.
	 *
	 * @author Marc Robin Nolte (modified version)
	 *
	 */
	protected abstract class LocalityAxiomVisitor implements OWLAxiomVisitor {

		/**
		 * Abstract superclass for class that determine if class expressions are
		 * syntactically equivalent to \bottom for a specific locality class.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		protected abstract class AbstractBottomEquivalenceEvaluator extends AbstractEquivalenceEvaluator {

			@Override
			public final void visit(final OWLObjectComplementOf ce) {
				isEquivalent = getTopEquivalenceEvaluator().isEquivalent(ce.getOperand());
			}

			@Override
			public final void visit(final OWLObjectOneOf ce) {
				isEquivalent = ce.individuals().count() == 0;
			}

		}

		/**
		 * Abstract superclass for class that determine if class expressions are
		 * syntactically equivalent to another one for a specific locality class.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		protected abstract class AbstractEquivalenceEvaluator implements OWLClassExpressionVisitor {

			/**
			 * Whether the {@link OWLClassExpression} to test is equivalent to the
			 * expression represented by this evaluator. This value will be manipulated
			 * during the evaluation.
			 */
			protected boolean isEquivalent;

			/**
			 * Checks if the given class expression is equivalent to the expression
			 * represented by this evaluator.
			 *
			 * @param classExpression The {@link OWLClassExpression} to test
			 * @return <code>true</code>, if the class expression is equivalent to the
			 *         expression represented by this evaluator; <code>false</code>
			 *         otherwise
			 */
			protected final synchronized boolean isEquivalent(final OWLClassExpression classExpression) {
				Objects.requireNonNull(classExpression, "The given class expression may not be null").accept(this);
				return isEquivalent;
			}

			@Override
			public abstract void visit(final OWLClass ce);

			@Override
			public abstract void visit(final OWLDataAllValuesFrom ce);

			@Override
			public abstract void visit(final OWLDataExactCardinality ce);

			@Override
			public abstract void visit(final OWLDataHasValue ce);

			@Override
			public abstract void visit(final OWLDataMaxCardinality ce);

			@Override
			public abstract void visit(final OWLDataMinCardinality ce);

			@Override
			public abstract void visit(final OWLDataSomeValuesFrom ce);

			@Override
			public abstract void visit(final OWLObjectAllValuesFrom ce);

			@Override
			public abstract void visit(final OWLObjectComplementOf ce);

			@Override
			public abstract void visit(final OWLObjectExactCardinality ce);

			@Override
			public abstract void visit(final OWLObjectHasSelf ce);

			@Override
			public abstract void visit(final OWLObjectHasValue ce);

			@Override
			public final void visit(final OWLObjectIntersectionOf ce) {
				isEquivalent = !ce.operands().anyMatch(this::isEquivalent);
			}

			@Override
			public abstract void visit(final OWLObjectMaxCardinality ce);

			@Override
			public abstract void visit(final OWLObjectMinCardinality ce);

			@Override
			public abstract void visit(final OWLObjectOneOf ce);

			@Override
			public abstract void visit(final OWLObjectSomeValuesFrom ce);

			@Override
			public final void visit(final OWLObjectUnionOf ce) {
				isEquivalent = ce.operands().anyMatch(this::isEquivalent);
			}

		}

		/**
		 * Abstract superclass for class that determine if class expressions are
		 * syntactically equivalent to \top for a specific locality class.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		protected abstract class AbstractTopEquivalenceEvaluator extends AbstractEquivalenceEvaluator {

			@Override
			public final void visit(final OWLObjectComplementOf ce) {
				isEquivalent = getBottomEquivalenceEvaluator().isEquivalent(ce.getOperand());
			}

			@Override
			public final void visit(final OWLObjectOneOf ce) {
				isEquivalent = false;
			}

		}

		/**
		 * The used {@link AbstractBottomEquivalenceEvaluator} to test for equivalence
		 * with \bottom.
		 */
		private final @Nonnull AbstractBottomEquivalenceEvaluator bottomEquivalenceEvaluator;

		/**
		 * Whether the axiom is local w.r.t the signature this
		 * {@link AxiomLocalityVisitor} was instantiated with. This value is manipulated
		 * during the evaluation.
		 */
		protected boolean isLocal;

		/**
		 * The signature the axiom should be checked against.
		 */
		protected final @Nonnull Collection<OWLEntity> signature;

		/**
		 * The used {@link AbstractTopEquivalenceEvaluator} to test for equivalence with
		 * \top.
		 */
		private final @Nonnull AbstractTopEquivalenceEvaluator topEquivalenceEvaluator;

		/**
		 * Instantiates a new {@link LocalityAxiomVisitor} and tests whether the given
		 * axiom is local w.r.t the given signature. The result may be retrieved calling
		 * {@link LocalityAxiomVisitor#isLocal()}.
		 *
		 * @param axiom     the axiom to test
		 * @param signature the signature to test against
		 */
		private LocalityAxiomVisitor(final OWLAxiom axiom, final Collection<OWLEntity> signature) {
			this.signature = signature;
			isLocal = false;
			bottomEquivalenceEvaluator = createBottomEquivalenceEvaluator();
			topEquivalenceEvaluator = createTopEquivalenceEvaluator();
			axiom.accept(this);
		}

		/**
		 * Instantiates a new {@link AbstractBottomEquivalenceEvaluator} to test
		 * equivalence with \bottom.
		 *
		 * @return A new {@link AbstractBottomEquivalenceEvaluator}
		 */
		protected abstract AbstractBottomEquivalenceEvaluator createBottomEquivalenceEvaluator();

		/**
		 * Instantiates a new {@link AbstractTopEquivalenceEvaluator} to test
		 * equivalence with \top.
		 *
		 * @return A new {@link AbstractTopEquivalenceEvaluator}
		 */
		protected abstract AbstractTopEquivalenceEvaluator createTopEquivalenceEvaluator();

		/**
		 * Returns the used {@link AbstractEquivalenceEvaluator} to test equivalence
		 * with \bottom.
		 *
		 * @return The used {@link AbstractEquivalenceEvaluator} to test equivalence
		 *         with \bottom
		 */
		protected final @Nonnull AbstractEquivalenceEvaluator getBottomEquivalenceEvaluator() {
			return bottomEquivalenceEvaluator;
		}

		/**
		 * Returns the used {@link AbstractEquivalenceEvaluator} to test for equivalence
		 * with top.
		 *
		 * @return The used {@link AbstractEquivalenceEvaluator} to test for equivalence
		 *         with top
		 */
		protected final @Nonnull AbstractEquivalenceEvaluator getTopEquivalenceEvaluator() {
			return topEquivalenceEvaluator;
		}

		/**
		 * Returns whether the axiom is local w.r.t the signature this
		 * {@link AxiomLocalityVisitor} was instantiated with.
		 *
		 * @return Whether the axiom is local w.r.t the signature this
		 *         {@link AxiomLocalityVisitor} was instantiated with.
		 */
		protected final boolean isLocal() {
			return isLocal;
		}

		private <T extends OWLObject> boolean isLocal(final OWLEquivalentClassesAxiom axiom) {
			final Iterator<OWLClassExpression> eqs = axiom.classExpressions().iterator();
			final OWLClassExpression first = eqs.next();
			// axiom is local if it contains a single class expression
			if (!eqs.hasNext()) {
				return true;
			}
			// axiom is local iff either all class expressions evaluate to TOP or all
			// evaluate to BOTTOM check if first class expr. is BOTTOM
			final boolean isBottom = getBottomEquivalenceEvaluator().isEquivalent(first);
			// if not BOTTOM or not TOP then this axiom is non-local
			if (!isBottom && !getTopEquivalenceEvaluator().isEquivalent(first)) {
				return false;
			}
			// unless we find a non-locality, process all the class expressions
			while (eqs.hasNext()) {
				if (isBottom ? !getBottomEquivalenceEvaluator().isEquivalent(eqs.next())
						: !getTopEquivalenceEvaluator().isEquivalent(eqs.next())) {
					// first class expr. was BOTTOM (TOP), so this one should be BOTTOM (TOP) too
					return false;
				}
			}
			return true;
		}

		@Override
		public void visit(final OWLClassAssertionAxiom axiom) {
			isLocal = getTopEquivalenceEvaluator().isEquivalent(axiom.getClassExpression());
		}

		@Override
		public abstract void visit(final OWLDataPropertyAssertionAxiom axiom);

		@Override
		public void visit(final OWLDatatypeDefinitionAxiom axiom) {
			isLocal = true;
		}

		@Override
		public final void visit(final OWLDifferentIndividualsAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLDisjointClassesAxiom axiom) {
			final Collection<OWLClassExpression> disjs = asList(axiom.classExpressions());
			boolean nonBottomEquivDescFound = false;
			for (final OWLClassExpression desc : disjs) {
				if (!getBottomEquivalenceEvaluator().isEquivalent(desc)) {
					if (nonBottomEquivDescFound) {
						isLocal = false;
						return;
					} else {
						nonBottomEquivDescFound = true;
					}
				}
			}
			isLocal = true;
		}

		@Override
		public abstract void visit(OWLDisjointDataPropertiesAxiom axiom);

		@Override
		public abstract void visit(OWLDisjointObjectPropertiesAxiom axiom);

		@Override
		public final void visit(final OWLEquivalentClassesAxiom axiom) {
			isLocal = isLocal(axiom);
		}

		@Override
		public final void visit(final OWLEquivalentDataPropertiesAxiom axiom) {
			if (axiom.properties().count() < 2) {
				isLocal = true;
				return;
			}
			if (axiom.properties().anyMatch(property -> signature.contains(property.asOWLDataProperty()))) {
				isLocal = false;
				return;
			}
			isLocal = true;
		}

		@Override
		public final void visit(final OWLEquivalentObjectPropertiesAxiom axiom) {
			if (axiom.properties().count() < 2) {
				isLocal = true;
				return;
			}
			if (axiom.properties().anyMatch(property -> signature.contains(property.getNamedProperty()))) {
				isLocal = false;
				return;
			}
			isLocal = true;

		}

		@Override
		public abstract void visit(final OWLFunctionalDataPropertyAxiom axiom);

		@Override
		public abstract void visit(final OWLFunctionalObjectPropertyAxiom axiom);

		@Override
		public final void visit(final OWLHasKeyAxiom axiom) {
			isLocal = true;
		}

		@Override
		public abstract void visit(final OWLInverseFunctionalObjectPropertyAxiom axiom);

		@Override
		public final void visit(final OWLInverseObjectPropertiesAxiom axiom) {
			isLocal = !signature.contains(axiom.getFirstProperty().getNamedProperty())
					&& !signature.contains(axiom.getSecondProperty().getNamedProperty());
		}

		@Override
		public abstract void visit(final OWLIrreflexiveObjectPropertyAxiom axiom);

		@Override
		public abstract void visit(final OWLNegativeDataPropertyAssertionAxiom axiom);

		@Override
		public abstract void visit(final OWLNegativeObjectPropertyAssertionAxiom axiom);

		@Override
		public abstract void visit(final OWLObjectPropertyAssertionAxiom axiom);

		@Override
		public abstract void visit(final OWLObjectPropertyDomainAxiom axiom);

		@Override
		public abstract void visit(final OWLObjectPropertyRangeAxiom axiom);

		@Override
		public abstract void visit(final OWLReflexiveObjectPropertyAxiom axiom);

		@Override
		public final void visit(final OWLSameIndividualAxiom axiom) {
			isLocal = false;
		}

		@Override
		public final void visit(final OWLSubClassOfAxiom axiom) {
			isLocal = getBottomEquivalenceEvaluator().isEquivalent(axiom.getSubClass())
					|| getTopEquivalenceEvaluator().isEquivalent(axiom.getSuperClass());
		}

		@Override
		public abstract void visit(final OWLSubObjectPropertyOfAxiom axiom);

		@Override
		public abstract void visit(final OWLSubPropertyChainOfAxiom axiom);

		@Override
		public final void visit(final OWLSymmetricObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public final void visit(final OWLTransitiveObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public final void visit(final SWRLRule rule) {
			// TODO: (TS) Can't we treat this in a more differentiated way?
			isLocal = false;
		}
	}

	/**
	 * Class implementing the syntactic \top-locality check using the
	 * visitor-pattern. Needs to be instantiated for thread safety.
	 *
	 * @author Marc Robin Nolte (modified version)
	 *
	 */
	private final class TopLocalityAxiomVisitor extends LocalityAxiomVisitor {

		/**
		 * Class to determine if class expressions are syntactically equivalent to
		 * \bottom for \top-locality.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		private final class TopLocalityBottomEquivalenceEvaluator extends AbstractBottomEquivalenceEvaluator {

			@Override
			public void visit(final OWLClass ce) {
				isEquivalent = ce.isOWLNothing();
			}

			@Override
			public void visit(final OWLDataAllValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty())
						&& !ce.getFiller().isTopDatatype();
			}

			@Override
			public void visit(final OWLDataExactCardinality ce) {
				isEquivalent = ce.getCardinality() == 0 && !signature.contains(ce.getProperty().asOWLDataProperty())
						&& isTopOrBuiltInDatatype(ce.getFiller())
						|| ce.getCardinality() > 0 && !signature.contains(ce.getProperty().asOWLDataProperty())
								&& isTopOrBuiltInInfiniteDatatype(ce.getFiller());
			}

			@Override
			public void visit(final OWLDataHasValue ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataMaxCardinality ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty()) && ce.getCardinality() <= 1
						&& isTopOrBuiltInDatatype(ce.getFiller());

			}

			@Override
			public void visit(final OWLDataMinCardinality ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataSomeValuesFrom ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectAllValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) && isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectExactCardinality ce) {
				isEquivalent = ce.getCardinality() > 0
						&& (isEquivalent(ce.getFiller()) || !signature.contains(ce.getProperty().getNamedProperty())
								&& getTopEquivalenceEvaluator().isEquivalent(ce.getFiller()));
			}

			@Override
			public void visit(final OWLObjectHasSelf ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectHasValue ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLObjectMaxCardinality ce) {
				isEquivalent = ce.getCardinality() > 0 && !signature.contains(ce.getProperty().getNamedProperty())
						&& getTopEquivalenceEvaluator().isEquivalent(ce.getFiller());

			}

			@Override
			public void visit(final OWLObjectMinCardinality ce) {
				isEquivalent = ce.getCardinality() > 0 && isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectSomeValuesFrom ce) {
				isEquivalent = isEquivalent(ce.getFiller());
			}
		}

		/**
		 * Class to determine if class expressions are syntactically equivalent to \top
		 * for \top-locality.
		 *
		 * @author Marc Robin Nolte (modified version)
		 */
		private final class TopLocalityTopEquivalenceEvaluator extends AbstractTopEquivalenceEvaluator {

			@Override
			public void visit(final OWLClass ce) {
				isEquivalent = ce.isOWLThing() || !ce.isOWLNothing() && !signature.contains(ce);
			}

			@Override
			public void visit(final OWLDataAllValuesFrom ce) {
				isEquivalent = ce.getFiller().isTopDatatype();
			}

			@Override
			public void visit(final OWLDataExactCardinality ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataHasValue ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty());
			}

			@Override
			public void visit(final OWLDataMaxCardinality ce) {
				isEquivalent = false;
			}

			@Override
			public void visit(final OWLDataMinCardinality ce) {
				isEquivalent = ce.getCardinality() == 0
						|| ce.getCardinality() == 1 && !signature.contains(ce.getProperty().asOWLDataProperty())
								&& isTopOrBuiltInDatatype(ce.getFiller())
						|| ce.getCardinality() > 1 && !signature.contains(ce.getProperty().asOWLDataProperty())
								&& isTopOrBuiltInInfiniteDatatype(ce.getFiller());
			}

			@Override
			public void visit(final OWLDataSomeValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().asOWLDataProperty())
						&& isTopOrBuiltInDatatype(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectAllValuesFrom ce) {
				isEquivalent = isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectExactCardinality ce) {
				isEquivalent = ce.getCardinality() == 0 && getBottomEquivalenceEvaluator().isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectHasSelf ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty());
			}

			@Override
			public void visit(final OWLObjectHasValue ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty());
			}

			@Override
			public void visit(final OWLObjectMaxCardinality ce) {
				isEquivalent = getBottomEquivalenceEvaluator().isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectMinCardinality ce) {
				isEquivalent = ce.getCardinality() == 0
						|| !signature.contains(ce.getProperty().getNamedProperty()) && isEquivalent(ce.getFiller());
			}

			@Override
			public void visit(final OWLObjectSomeValuesFrom ce) {
				isEquivalent = !signature.contains(ce.getProperty().getNamedProperty()) && isEquivalent(ce.getFiller());
			}

		}

		/**
		 * Instantiates a new {@link TopLocalityAxiomVisitor}.
		 *
		 * @param axiom     the axiom to test
		 * @param signature the signature to test against
		 */
		private TopLocalityAxiomVisitor(final OWLAxiom axiom, final Collection<OWLEntity> signature) {
			super(axiom, signature);
		}

		@Override
		protected TopLocalityBottomEquivalenceEvaluator createBottomEquivalenceEvaluator() {
			return new TopLocalityBottomEquivalenceEvaluator();
		}

		@Override
		protected TopLocalityTopEquivalenceEvaluator createTopEquivalenceEvaluator() {
			return new TopLocalityTopEquivalenceEvaluator();
		}

		@Override
		public void visit(final OWLAsymmetricObjectPropertyAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLDataPropertyAssertionAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().asOWLDataProperty());
		}

		@Override
		public void visit(final OWLDisjointDataPropertiesAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLDisjointObjectPropertiesAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLFunctionalDataPropertyAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLFunctionalObjectPropertyAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLInverseFunctionalObjectPropertyAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLIrreflexiveObjectPropertyAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLNegativeDataPropertyAssertionAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLNegativeObjectPropertyAssertionAxiom axiom) {
			isLocal = false;
		}

		@Override
		public void visit(final OWLObjectPropertyAssertionAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLObjectPropertyDomainAxiom axiom) {
			isLocal = getTopEquivalenceEvaluator().isEquivalent(axiom.getDomain());
		}

		@Override
		public void visit(final OWLObjectPropertyRangeAxiom axiom) {
			isLocal = getTopEquivalenceEvaluator().isEquivalent(axiom.getRange());
		}

		@Override
		public void visit(final OWLReflexiveObjectPropertyAxiom axiom) {
			isLocal = !signature.contains(axiom.getProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLSubObjectPropertyOfAxiom axiom) {
			isLocal = !signature.contains(axiom.getSuperProperty().getNamedProperty());
		}

		@Override
		public void visit(final OWLSubPropertyChainOfAxiom axiom) {
			// Axiom is local iff RHS is top-equiv
			isLocal = !signature.contains(axiom.getSuperProperty().getNamedProperty());
		}

	}

	/**
	 * This is a convenience method for determining whether a given data range
	 * expression is the top datatype or a built-in datatype. This is used in the
	 * {@link org.semanticweb.owlapi.modularity.SyntacticLocalityEvaluator.AxiomLocalityVisitor.BottomEquivalenceEvaluator}
	 * and
	 * {@link org.semanticweb.owlapi.modularity.SyntacticLocalityEvaluator.AxiomLocalityVisitor.TopEquivalenceEvaluator}
	 * for treating cardinality restrictions.
	 *
	 * @param dataRange a data range expression
	 * @return <code>true</code> if the specified data range expression is the top
	 *         datatype or a built-in datatype; <code>false</code> otherwise
	 */
	private static boolean isTopOrBuiltInDatatype(final OWLDataRange dataRange) {
		if (dataRange.isOWLDatatype()) {
			final OWLDatatype dataType = dataRange.asOWLDatatype();
			return dataType.isTopDatatype() || dataType.isBuiltIn();
		}
		return false;
	}

	/**
	 * This is a convenience method for determining whether a given data range
	 * expression is the top datatype or a built-in infinite datatype. This is used
	 * in the
	 * {@link org.semanticweb.owlapi.modularity.SyntacticLocalityEvaluator.AxiomLocalityVisitor.BottomEquivalenceEvaluator}
	 * and
	 * {@link org.semanticweb.owlapi.modularity.SyntacticLocalityEvaluator.AxiomLocalityVisitor.TopEquivalenceEvaluator}
	 * for treating cardinality restrictions.
	 *
	 * @param dataRange a data range expression
	 * @return <code>true</code> if the specified data range expression is the top
	 *         datatype or a built-in infinite datatype; <code>false</code>
	 *         otherwise
	 */
	private static boolean isTopOrBuiltInInfiniteDatatype(final OWLDataRange dataRange) {
		if (dataRange.isOWLDatatype()) {
			final OWLDatatype dataType = dataRange.asOWLDatatype();
			return dataType.isTopDatatype() || dataType.isBuiltIn() && !dataType.getBuiltInDatatype().isFinite();
		}
		return false;
	}

	/**
	 * Creates the {@link LocalityAxiomVisitor} to check locality with associated
	 * with the locality class represented by this
	 * {@link SyntacticLocalityEvaluator}.
	 *
	 * @param axiom     The axiom to visit
	 * @param signature The signature to check against
	 * @return
	 */
	protected abstract LocalityAxiomVisitor createLocalityAxiomVisitor(final OWLAxiom axiom,
			final Collection<OWLEntity> signature);

	@Override
	public final boolean isLocal(final OWLAxiom axiom, final Collection<OWLEntity> signature) {
//		if (axiom.isOfType(AxiomType.SUBCLASS_OF) && axiom.classesInSignature().map(Object::toString)
//				.allMatch(string -> string.contains("http://mouse.brain-map.org/atlas/index.html#MY-mot")
//						|| string.contains("http://mouse.brain-map.org/atlas/index.html#MY"))) {
//			System.out.println("new");
//		}
		if (this == TOP && axiom.isOfType(AxiomType.DISJOINT_CLASSES)) {
			System.out.println("new");
		}
		return !axiom.isLogicalAxiom()
				|| createLocalityAxiomVisitor(Objects.requireNonNull(axiom, "The given axiom may not be null"),
						Objects.requireNonNull(signature, "The given signature may not be null")).isLocal();
	}

}
