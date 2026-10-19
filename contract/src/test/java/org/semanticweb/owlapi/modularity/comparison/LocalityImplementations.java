package org.semanticweb.owlapi.modularity.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SemanticLocalityEvaluator;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityEvaluator;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.Signature;

import uk.ac.manchester.cs.factplusplusad.FactPlusPlusAdAccess;

/**
 * Adapters that expose every locality checker of the tools module through one
 * interface: {@code isLocal(axiom, signature, BOTTOM|TOP)}.
 */
@SuppressWarnings("deprecation")
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
        implementations.add(new Base("nolte-syntactic", true, false) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                return (mode == LocalityClass.BOTTOM ? SyntacticLocalityEvaluator.BOTTOM
                    : SyntacticLocalityEvaluator.TOP).isLocal(axiom, signature);
            }
        });
        implementations.add(new Base("clarkparsia-syntactic", false, false) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                com.clarkparsia.owlapi.modularity.locality.LocalityClass legacy =
                    mode == LocalityClass.BOTTOM
                        ? com.clarkparsia.owlapi.modularity.locality.LocalityClass.BOTTOM_BOTTOM
                        : com.clarkparsia.owlapi.modularity.locality.LocalityClass.TOP_TOP;
                return new com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator(
                    legacy).isLocal(axiom, signature);
            }
        });
        implementations.add(new Base("owlapitools-syntactic", false, false) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                org.semanticweb.owlapitools.decomposition.SyntacticLocalityChecker checker =
                    new org.semanticweb.owlapitools.decomposition.SyntacticLocalityChecker();
                checker.setSignatureValue(new Signature(signature.stream()));
                checker.getSignature().setLocality(mode == LocalityClass.TOP);
                return checker.local(axiom);
            }
        });
        implementations.add(new Base("factplusplusad-syntactic", false, false) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                return FactPlusPlusAdAccess.syntacticLocal(ModuleMethod.SYNTACTIC_STANDARD,
                    mode == LocalityClass.TOP, axiom, signature);
            }
        });
        implementations.add(new Base("factplusplusad-counting", false, false) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                return FactPlusPlusAdAccess.syntacticLocal(ModuleMethod.SYNTACTIC_COUNTING,
                    mode == LocalityClass.TOP, axiom, signature);
            }
        });
        if (includeSemantic) {
            addSemantic();
        }
    }

    private void addSemantic() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        ReasonerFactory hermit = new ReasonerFactory();
        SemanticLocalityEvaluator[] nolte = new SemanticLocalityEvaluator[2];
        implementations.add(new Base("nolte-semantic", true, true) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                int index = mode == LocalityClass.BOTTOM ? 0 : 1;
                synchronized (nolte) {
                    if (nolte[index] == null) {
                        nolte[index] = new SemanticLocalityEvaluator(mode, manager, hermit);
                    }
                }
                // The strict variant keeps "cannot decide" visible as unsupported;
                // isLocal itself would answer the conservative "non-local".
                return nolte[index].isLocalOrThrow(axiom, signature);
            }
        });
        cleanup.add(() -> {
            for (SemanticLocalityEvaluator e : nolte) {
                if (e != null) {
                    e.dispose();
                }
            }
        });
        com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator[] clarkParsia =
            new com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator[1];
        implementations.add(new Base("clarkparsia-semantic", false, true) {
            @Override
            public boolean supports(LocalityClass mode) {
                // The Clark & Parsia semantic evaluator only implements BOTTOM.
                return mode == LocalityClass.BOTTOM;
            }

            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                synchronized (clarkParsia) {
                    if (clarkParsia[0] == null) {
                        clarkParsia[0] =
                            new com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator(
                                manager, hermit);
                    }
                }
                return clarkParsia[0].isLocal(axiom, signature);
            }
        });
        implementations.add(new Base("owlapitools-semantic", false, true) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                org.semanticweb.owlapitools.decomposition.SemanticLocalityChecker checker =
                    new org.semanticweb.owlapitools.decomposition.SemanticLocalityChecker(hermit,
                        manager);
                checker.preprocessOntology(Collections.singletonList(new AxiomWrapper(axiom)));
                checker.setSignatureValue(new Signature(signature.stream()));
                checker.getSignature().setLocality(mode == LocalityClass.TOP);
                return checker.local(axiom);
            }
        });
        implementations.add(new Base("factplusplusad-semantic", false, true) {
            @Override
            public boolean isLocal(OWLAxiom axiom, Set<OWLEntity> signature,
                LocalityClass mode) {
                return FactPlusPlusAdAccess.semanticLocal(manager, hermit,
                    mode == LocalityClass.TOP, axiom, signature);
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
