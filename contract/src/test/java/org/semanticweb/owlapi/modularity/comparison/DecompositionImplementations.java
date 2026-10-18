package org.semanticweb.owlapi.modularity.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.modularity.AtomicDecomposition;
import org.semanticweb.owlapi.modularity.comparison.ExtractorImplementations.ExtractorImpl;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SyntacticLocalityModuleExtractor;

import uk.ac.manchester.cs.atomicdecomposition.Atom;
import uk.ac.manchester.cs.atomicdecomposition.AtomicDecompositionImpl;
import uk.ac.manchester.cs.factplusplusad.FactPlusPlusAdAccess;

/**
 * Adapters for the three atomic decomposition implementations. Results are
 * normalised to plain axiom sets: atoms without the tautologies, the
 * (transitive, irreflexive) dependency relation and the set of tautologies.
 */
@SuppressWarnings("deprecation")
final class DecompositionImplementations {

    /** A normalised atomic decomposition. */
    static final class Decomposed {
        final List<Set<OWLAxiom>> atoms = new ArrayList<>();
        /** atom -> all atoms it depends on (transitively, without itself). */
        final Map<Set<OWLAxiom>, Set<Set<OWLAxiom>>> dependencies = new HashMap<>();
        final Set<OWLAxiom> tautologies = new HashSet<>();

        Set<OWLAxiom> atomOf(OWLAxiom axiom) {
            return atoms.stream().filter(a -> a.contains(axiom)).findFirst().orElse(null);
        }

        /** The principal ideal: the atom of the axiom and everything it depends on. */
        Set<OWLAxiom> ideal(OWLAxiom axiom) {
            Set<OWLAxiom> atom = atomOf(axiom);
            if (atom == null) {
                return null;
            }
            Set<OWLAxiom> result = new HashSet<>(atom);
            dependencies.getOrDefault(atom, Collections.emptySet()).forEach(result::addAll);
            return result;
        }

        int edges() {
            return dependencies.values().stream().mapToInt(Set::size).sum();
        }
    }

    /** An atomic decomposition under comparison. */
    interface DecompositionImpl {
        String name();

        boolean reference();

        /** The module extractor whose modules the principal ideals must equal. */
        ExtractorImpl extractor();

        Decomposed decompose(Set<OWLAxiom> base, LocalityClass type);
    }

    private abstract static class Base implements DecompositionImpl {
        private final String name;
        private final boolean reference;
        private final ExtractorImpl extractor;

        Base(String name, boolean reference, ExtractorImpl extractor) {
            this.name = name;
            this.reference = reference;
            this.extractor = extractor;
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
        public ExtractorImpl extractor() {
            return extractor;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /** Closes a direct dependency relation transitively and drops self loops. */
    static Map<Integer, Set<Integer>> closure(Map<Integer, Set<Integer>> direct) {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        for (Integer start : direct.keySet()) {
            Set<Integer> seen = new HashSet<>();
            List<Integer> todo = new ArrayList<>(direct.getOrDefault(start, Collections.emptySet()));
            while (!todo.isEmpty()) {
                Integer next = todo.remove(todo.size() - 1);
                if (next != null && seen.add(next)) {
                    todo.addAll(direct.getOrDefault(next, Collections.emptySet()));
                }
            }
            seen.remove(start);
            result.put(start, seen);
        }
        return result;
    }

    static final DecompositionImpl NOLTE = new Base("nolte-ad", true,
        ExtractorImplementations.NOLTE_SYNTACTIC) {
        @Override
        public Decomposed decompose(Set<OWLAxiom> base, LocalityClass type) {
            AtomicDecomposition ad = new AtomicDecomposition(base.stream(),
                stream -> new SyntacticLocalityModuleExtractor(type, stream));
            Decomposed result = new Decomposed();
            Map<AtomicDecomposition.Atom, Set<OWLAxiom>> sets = new HashMap<>();
            for (AtomicDecomposition.Atom atom : ad.atoms().collect(Collectors.toList())) {
                Set<OWLAxiom> axioms = atom.axioms().collect(Collectors.toSet());
                // The tautologies form one atom whose axioms are not in their own modules.
                boolean tautological = axioms.stream()
                    .noneMatch(ax -> ad.moduleToSignatureOf(ax).anyMatch(ax::equals));
                if (tautological) {
                    result.tautologies.addAll(axioms);
                } else {
                    result.atoms.add(axioms);
                    sets.put(atom, axioms);
                }
            }
            sets.forEach((atom, axioms) -> result.dependencies.put(axioms, atom.dependencies()
                .filter(sets::containsKey).map(sets::get).collect(Collectors.toSet())));
            return result;
        }
    };

    static final DecompositionImpl MANCHESTER = new Base("manchester-ad", false,
        ExtractorImplementations.OWLAPITOOLS_DECOMPOSER) {
        @Override
        public Decomposed decompose(Set<OWLAxiom> base, LocalityClass type) {
            AtomicDecompositionImpl ad =
                new AtomicDecompositionImpl(new ArrayList<>(base), ExtractorImplementations.legacy(type));
            Decomposed result = new Decomposed();
            Map<Atom, Set<OWLAxiom>> sets = new HashMap<>();
            for (Atom atom : ad.getAtoms()) {
                Set<OWLAxiom> axioms = new HashSet<>(atom.getAxioms());
                sets.put(atom, axioms);
                result.atoms.add(axioms);
            }
            sets.forEach((atom, axioms) -> {
                Set<Set<OWLAxiom>> deps = new HashSet<>();
                for (Atom dep : ad.getDependencies(atom, false)) {
                    if (dep != atom && sets.containsKey(dep)) {
                        deps.add(sets.get(dep));
                    }
                }
                result.dependencies.put(axioms, deps);
            });
            result.tautologies.addAll(ad.getTautologies());
            return result;
        }
    };

    static final DecompositionImpl FACTPLUSPLUS = new Base("factplusplusad-ad", false,
        ExtractorImplementations.FACTPLUSPLUSAD_MODULARIZER) {
        @Override
        public Decomposed decompose(Set<OWLAxiom> base, LocalityClass type) {
            FactPlusPlusAdAccess.Decomposition d =
                FactPlusPlusAdAccess.decompose(base, ExtractorImplementations.legacy(type));
            Decomposed result = new Decomposed();
            result.atoms.addAll(d.atoms);
            Map<Integer, Set<Integer>> all = closure(d.dependencies);
            for (int i = 0; i < d.atoms.size(); i++) {
                result.dependencies.put(d.atoms.get(i), all.getOrDefault(Integer.valueOf(i),
                    Collections.emptySet()).stream().map(d.atoms::get).collect(Collectors.toSet()));
            }
            result.tautologies.addAll(d.tautologies);
            return result;
        }
    };

    static List<DecompositionImpl> all() {
        List<DecompositionImpl> list = new ArrayList<>();
        list.add(NOLTE);
        list.add(MANCHESTER);
        list.add(FACTPLUSPLUS);
        return Collections.unmodifiableList(list);
    }

    private DecompositionImplementations() {}
}
