package org.semanticweb.owlapi.modularity.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SemanticLocalityEvaluator;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityEvaluator;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 * Adapters that expose every locality checker of the tools module through one
 * interface: {@code isLocal(axiom, signature, BOTTOM|TOP)}.
 */
final class LocalityImplementations implements AutoCloseable {

    /** A locality checker under comparison. */
    interface LocalityImpl {
        String name();

        /** Reference implementations are asserted; the others are reported. */
        boolean reference();

        /** Semantic checkers decide locality exactly, syntactic ones approximate it. */
        boolean semantic();

        boolean supports(LocalityClass mode);

        boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature, LocalityClass mode);
    }

    private abstract static class Base implements LocalityImpl {
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
        public boolean supports(LocalityClass mode) {
            return mode == LocalityClass.BOTTOM || mode == LocalityClass.TOP;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final List<LocalityImpl> implementations = new ArrayList<>();
    private final List<Runnable> cleanup = new ArrayList<>();

    /**
     * @param includeSemantic whether to create the (HermiT based) semantic checkers
     */
    LocalityImplementations(boolean includeSemantic) {
        implementations.add(new Base("modularity-syntactic", true, false) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                return (mode == LocalityClass.BOTTOM ? SyntacticLocalityEvaluator.BOTTOM
                    : SyntacticLocalityEvaluator.TOP).isLocal(axiom, signature);
            }
        });
        if (includeSemantic) {
            addSemantic();
        }
    }

    private void addSemantic() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        SemanticLocalityEvaluator[] evaluators = new SemanticLocalityEvaluator[2];
        implementations.add(new Base("modularity-semantic", true, true) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                int index = mode == LocalityClass.BOTTOM ? 0 : 1;
                synchronized (evaluators) {
                    if (evaluators[index] == null) {
                        evaluators[index] = new SemanticLocalityEvaluator(mode, manager, new StructuralReasonerFactory());
                    }
                }
                // The strict variant keeps "cannot decide" visible as unsupported;
                // isLocal itself would answer the conservative "non-local".
                return evaluators[index].isLocalOrThrow(axiom, signature);
            }
        });
        cleanup.add(() -> {
            for (SemanticLocalityEvaluator e : evaluators) {
                if (e != null) {
                    e.dispose();
                }
            }
        });
    }

    List<LocalityImpl> all() {
        return Collections.unmodifiableList(implementations);
    }

    @Override
    public void close() {
        cleanup.forEach(Runnable::run);
    }
}
