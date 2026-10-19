package org.semanticweb.owlapi.modularity.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SemanticLocalityModuleExtractor;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityModuleExtractor;
import org.semanticweb.owlapitools.decomposition.AxiomSelector;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.Decomposer;
import org.semanticweb.owlapitools.decomposition.SyntacticLocalityChecker;

import uk.ac.manchester.cs.factplusplusad.OntologyBasedModularizer;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * Adapters that expose every locality-based module extractor of the tools module
 * through one interface. The comparison only passes logical axioms; the handling
 * of declarations and annotations is compared by {@code ModuleExtractorFeatureTest}.
 */
@SuppressWarnings("deprecation")
final class ExtractorImplementations {

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

    static ModuleType legacy(LocalityClass type) {
        switch (type) {
            case BOTTOM:
                return ModuleType.BOT;
            case TOP:
                return ModuleType.TOP;
            default:
                return ModuleType.STAR;
        }
    }

    private static OWLOntology ontology(OWLOntologyManager manager, Set<OWLAxiom> base) {
        try {
            return manager.createOntology(base);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    static final ExtractorImpl MODULARITY_SYNTACTIC = new Base("modularity-syntactic", true, false) {
        @Override
        public Prepared prepare(Set<OWLAxiom> base, LocalityClass type) {
            SyntacticLocalityModuleExtractor extractor =
                new SyntacticLocalityModuleExtractor(type, base.stream());
            return signature -> extractor.extract(signature.stream())
                .collect(Collectors.toSet());
        }
    };

    static final ExtractorImpl MODULARITY_SEMANTIC = new Base("modularity-semantic", true, true) {
        @Override
        public Prepared prepare(Set<OWLAxiom> base, LocalityClass type) {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            SemanticLocalityModuleExtractor extractor = new SemanticLocalityModuleExtractor(type,
                base.stream(), manager, new ReasonerFactory());
            return new Prepared() {
                @Override
                public Set<OWLAxiom> extract(Set<OWLEntity> signature) {
                    return extractor.extract(signature.stream()).collect(Collectors.toSet());
                }

                @Override
                public void close() {
                    extractor.dispose();
                }
            };
        }
    };

    static final ExtractorImpl MANCHESTER_SEGMENTER =
        new Base("manchester-segmenter", false, false) {
            @Override
            public Prepared prepare(Set<OWLAxiom> base, LocalityClass type) {
                // The legacy extractor creates an ontology in the manager it is given.
                OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
                uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor extractor =
                    new uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor(
                        manager, base.stream(), legacy(type));
                // Default mode: its enrichment step adds SameIndividual/DifferentIndividuals
                // axioms (which its evaluator treats as local) for individuals in the module
                // signature. Declarations and annotations it adds are not logical axioms and
                // are compared separately, so only the logical part is kept.
                return signature -> extractor.extract(signature).stream()
                    .filter(OWLAxiom::isLogicalAxiom).collect(Collectors.toSet());
            }
        };

    static final ExtractorImpl OWLAPITOOLS_DECOMPOSER =
        new Base("owlapitools-decomposer", false, false) {
            @Override
            public Prepared prepare(Set<OWLAxiom> base, LocalityClass type) {
                Decomposer decomposer = new Decomposer(
                    AxiomSelector.wrap(new ArrayList<>(base)), new SyntacticLocalityChecker());
                return signature -> decomposer
                    .getModule(signature.stream(), false, legacy(type)).stream()
                    .map(AxiomWrapper::getAxiom).collect(Collectors.toSet());
            }
        };

    private static ExtractorImpl factPlusPlus(String name, ModuleMethod method) {
        return new Base(name, false, false) {
            @Override
            public Prepared prepare(Set<OWLAxiom> base, LocalityClass type) {
                OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
                OntologyBasedModularizer modularizer =
                    new OntologyBasedModularizer(ontology(manager, base), method);
                return signature -> new java.util.HashSet<>(
                    modularizer.getModule(signature.stream(), legacy(type)));
            }
        };
    }

    static final ExtractorImpl FACTPLUSPLUSAD_MODULARIZER =
        factPlusPlus("factplusplusad-modularizer", ModuleMethod.SYNTACTIC_STANDARD);
    static final ExtractorImpl FACTPLUSPLUSAD_COUNTING =
        factPlusPlus("factplusplusad-counting", ModuleMethod.SYNTACTIC_COUNTING);

    /** All extractors; the semantic one only when requested (it is slow). */
    static List<ExtractorImpl> all(boolean includeSemantic) {
        List<ExtractorImpl> list = new ArrayList<>();
        list.add(MODULARITY_SYNTACTIC);
        if (includeSemantic) {
            list.add(MODULARITY_SEMANTIC);
        }
        list.add(MANCHESTER_SEGMENTER);
        list.add(OWLAPITOOLS_DECOMPOSER);
        list.add(FACTPLUSPLUSAD_MODULARIZER);
        list.add(FACTPLUSPLUSAD_COUNTING);
        return Collections.unmodifiableList(list);
    }

    private ExtractorImplementations() {}
}
