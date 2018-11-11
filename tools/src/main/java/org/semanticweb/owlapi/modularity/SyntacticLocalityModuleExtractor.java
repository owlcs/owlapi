package org.semanticweb.owlapi.modularity;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import com.clarkparsia.owlapi.modularity.locality.LocalityClass;

/**
 * Enum of syntactic locality module classes to extract modules with.
 *
 * @author Marc Robin Nolte (modified version)
 *
 */
public enum SyntacticLocalityModuleExtractor implements AbstractLocalityModuleExtractor {

	/**
	 * The module type based on {@link LocalityClass#BOTTOM}.
	 */
	BOTTOM {

		/**
		 * The used \bottom-{@link SyntacticLocalityEvaluator}.
		 */
		private @Nonnull final SyntacticLocalityEvaluator localityEvaluator = SyntacticLocalityEvaluator.BOTTOM;

		@Override
		public boolean everyModuleContains(final OWLAxiom axiom) {
			return !localityEvaluator.isLocal(axiom, Stream.empty());
		}

		@Override
		public @Nonnull Stream<OWLAxiom> extractModule(final Stream<OWLAxiom> axioms, final Stream<OWLEntity> signature,
				final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction) {
			return AbstractLocalityModuleExtractor
					.extractModule(axioms, signature, localityEvaluator, guaranteeFunction).stream();
		}

		@Override
		public boolean noModuleContains(final OWLAxiom axiom) {
			return localityEvaluator.isLocal(axiom, axiom.signature());
		}
	},

	/**
	 * The module type based on recurring, alternating usage of
	 * {@link LocalityClass#BOTTOM} and {@link LocalityClass#TOP}.
	 */
	STAR {
		@Override
		public boolean everyModuleContains(final OWLAxiom axiom) {
			return BOTTOM.everyModuleContains(axiom) && TOP.everyModuleContains(axiom);
		}

		@Override
		public Stream<OWLAxiom> extractModule(final Stream<OWLAxiom> axioms, final Stream<OWLEntity> signature,
				final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction) {
			final Set<OWLEntity> collectedSignature = signature.collect(Collectors.toSet());
			Set<OWLAxiom> module = AbstractLocalityModuleExtractor.extractModule(axioms, collectedSignature.stream(),
					SyntacticLocalityEvaluator.BOTTOM, guaranteeFunction);
			Integer previousSize = module.size();
			SyntacticLocalityEvaluator nextExtractionType = SyntacticLocalityEvaluator.TOP;
			boolean changed;
			do {
				module = AbstractLocalityModuleExtractor.extractModule(module.stream(), collectedSignature.stream(),
						nextExtractionType, guaranteeFunction);
				changed = previousSize != module.size();
				previousSize = module.size();
				nextExtractionType = nextExtractionType == SyntacticLocalityEvaluator.BOTTOM
						? SyntacticLocalityEvaluator.TOP
						: SyntacticLocalityEvaluator.BOTTOM;
			} while (changed);
			return module.stream();
		}

		@Override
		public boolean noModuleContains(final OWLAxiom axiom) {
			return BOTTOM.noModuleContains(axiom) || TOP.noModuleContains(axiom);
		}
	},

	/**
	 * The module type based on {@link LocalityClass#TOP}.
	 */
	TOP {
		/**
		 * The used \bottom-{@link SyntacticLocalityEvaluator}.
		 */
		private @Nonnull final SyntacticLocalityEvaluator localityEvaluator = SyntacticLocalityEvaluator.TOP;

		@Override
		public boolean everyModuleContains(final OWLAxiom axiom) {
			return !localityEvaluator.isLocal(axiom, Stream.empty());
		}

		@Override
		public Stream<OWLAxiom> extractModule(final Stream<OWLAxiom> axioms, final Stream<OWLEntity> signature,
				final Function<OWLAxiom, ModuleGuarantee> guaranteeFunction) {
			return AbstractLocalityModuleExtractor
					.extractModule(axioms, signature, localityEvaluator, guaranteeFunction).stream();
		}

		@Override
		public boolean noModuleContains(final OWLAxiom axiom) {
			return localityEvaluator.isLocal(axiom, axiom.signature());
		}
	};

}
