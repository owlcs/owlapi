package uk.ac.manchester.cs.factplusplusad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.OntologyAtom;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * Test-only bridge to the package-private parts of the FaCT++ atomic decomposition
 * port, so that its locality checkers and decomposer can take part in the
 * implementation comparison. Only the public {@link OntologyBasedModularizer} is
 * reachable for regular API users.
 */
@SuppressWarnings("deprecation")
public final class FactPlusPlusAdAccess {

    private FactPlusPlusAdAccess() {}

    /**
     * @param method SYNTACTIC_STANDARD or SYNTACTIC_COUNTING
     * @param top true for TOP locality, false for BOTTOM locality
     * @return locality of the axiom with respect to the signature
     */
    public static boolean syntacticLocal(ModuleMethod method, boolean top, OWLAxiom axiom,
        Collection<OWLEntity> signature) {
        Signature sig = new Signature(signature.stream());
        sig.setLocality(top);
        LocalityChecker checker = LocalityChecker.createLocalityChecker(method, sig);
        return checker.local(axiom);
    }

    /**
     * The semantic checker is not reachable through the public API: the factory method
     * for {@link ModuleMethod#QUERY_ANSWERING} throws.
     *
     * @return locality of the axiom with respect to the signature
     */
    public static boolean semanticLocal(OWLOntologyManager manager, OWLReasonerFactory factory,
        boolean top, OWLAxiom axiom, Collection<OWLEntity> signature) {
        Signature sig = new Signature(signature.stream());
        sig.setLocality(top);
        SemanticLocalityChecker checker = new SemanticLocalityChecker(sig, manager, factory);
        List<AxiomWrapper> wrapped = new ArrayList<>();
        wrapped.add(new AxiomWrapper(axiom));
        checker.preprocessOntology(wrapped);
        return checker.local(axiom);
    }

    /** Result of a FaCT++ style atomic decomposition, as plain axiom sets. */
    public static final class Decomposition {
        /** Atoms as axiom sets, in creation order. */
        public final List<Set<OWLAxiom>> atoms = new ArrayList<>();
        /** Direct dependencies by atom index, as reported by the structure. */
        public final Map<Integer, Set<Integer>> dependencies = new HashMap<>();
        /** Axioms removed as tautologies. */
        public final Set<OWLAxiom> tautologies = new HashSet<>();
    }

    /**
     * Runs the package-private {@link AtomicDecomposer}.
     *
     * @param type BOT, TOP or STAR
     * @return the decomposition
     */
    public static Decomposition decompose(Collection<OWLAxiom> axioms, ModuleType type) {
        AtomicDecomposer decomposer =
            new AtomicDecomposer(new Modularizer(ModuleMethod.SYNTACTIC_STANDARD));
        List<AxiomWrapper> wrapped =
            axioms.stream().map(AxiomWrapper::new).collect(Collectors.toList());
        AOStructure aos = decomposer.getAOS(wrapped, type);
        Decomposition result = new Decomposition();
        Map<OntologyAtom, Integer> index = new HashMap<>();
        for (int i = 0; i < aos.size(); i++) {
            OntologyAtom atom = aos.get(i);
            index.put(atom, Integer.valueOf(i));
            result.atoms.add(atom.getAtomAxioms().stream().map(AxiomWrapper::getAxiom)
                .collect(Collectors.toSet()));
        }
        for (int i = 0; i < aos.size(); i++) {
            Set<Integer> deps = new HashSet<>();
            for (OntologyAtom dep : aos.get(i).getDependencies()) {
                deps.add(index.get(dep));
            }
            result.dependencies.put(Integer.valueOf(i), deps);
        }
        if (decomposer.tautologies != null) {
            decomposer.tautologies.forEach(w -> result.tautologies.add(w.getAxiom()));
        }
        return result;
    }
}
