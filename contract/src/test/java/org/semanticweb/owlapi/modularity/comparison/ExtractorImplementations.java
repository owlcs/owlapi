package org.semanticweb.owlapi.modularity.comparison;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityModuleExtractor;

/**
 * Adapters that expose every locality-based module extractor of the tools module
 * through one interface. The comparison only passes logical axioms; the handling
 * of declarations and annotations is compared by {@code ModuleExtractorFeatureTest}.
 */
final class ExtractorImplementations {
    static OWLDataFactory DF = OWLManager.getOWLDataFactory();
    /** An extractor prepared for one axiom base and module type. */
    interface Prepared extends AutoCloseable {
        Set<OWLAxiom> extract(Set<OWLEntity> signature);

        @Override
        default void close() {}
    }

    /** A module extractor under comparison. */
    interface ExtractorImpl {
        String name();

        boolean reference();

        /** Semantic extractors call a reasoner for every locality check. */
        boolean semantic();

        Prepared prepare(Set<OWLAxiom> base, LocalityClass type);
    }

    private abstract static class Base implements ExtractorImpl {
        private final String name;
        private final boolean reference;
        private final boolean semantic;

        Base(String name, boolean reference, boolean semantic) {
            this.name = name;
            this.reference = reference;
            this.semantic = semantic;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public boolean reference() {
            return reference;
        }

        @Override
        public boolean semantic() {
            return semantic;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static final ExtractorImpl MODULARITY_SYNTACTIC = new Base("modularity-syntactic", true, false) {
        @Override
        public Prepared prepare(Set<OWLAxiom> base, LocalityClass type) {
            SyntacticLocalityModuleExtractor extractor =
                new SyntacticLocalityModuleExtractor(type, base.stream(), DF);
            return signature -> extractor.extract(signature.stream())
                .collect(Collectors.toSet());
        }
    };

    /** All extractors; the semantic one only when requested (it is slow). */
    static List<ExtractorImpl> all() {
        return Arrays.asList(MODULARITY_SYNTACTIC);
    }

    private ExtractorImplementations() {}
}
