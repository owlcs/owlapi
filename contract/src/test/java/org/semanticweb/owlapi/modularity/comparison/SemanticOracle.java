package org.semanticweb.owlapi.modularity.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.locality.LocalityClass;
import org.semanticweb.owlapi.modularity.locality.SemanticLocalityEvaluator;

/**
 * Referee for module soundness, independent of the extractor under test.
 *
 * <p>A set M is a module of O for Sigma if every axiom of O \ M is semantically
 * local with respect to Sigma united with sig(M) (Cuenca Grau et al., JAIR 2008,
 * Proposition 24). This class checks that condition with the semantic locality
 * evaluator and HermiT. An axiom the evaluator cannot decide is reported as
 * undecided, never as a violation.</p>
 *
 * <p>The before/after comparison must use the same referee. The baseline run
 * against the released 5.5.1 jars therefore replaces this file by one that
 * delegates to a vendored copy of the current evaluator.</p>
 */
final class SemanticOracle implements AutoCloseable {

    /** Result of a soundness check. */
    static final class Verdict {
        final List<OWLAxiom> violations = new ArrayList<>();
        int undecided;
    }

    private final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
    private final SemanticLocalityEvaluator bottom;
    private final SemanticLocalityEvaluator top;
    private final Map<List<Object>, Optional<Boolean>> cache = new ConcurrentHashMap<>();

    SemanticOracle() {
        ReasonerFactory hermit = new ReasonerFactory();
        bottom = new SemanticLocalityEvaluator(LocalityClass.BOTTOM, manager, hermit);
        top = new SemanticLocalityEvaluator(LocalityClass.TOP, manager, hermit);
    }

    /** @return empty if the evaluator cannot decide the question */
    Optional<Boolean> isLocal(LocalityClass mode, OWLAxiom axiom, Set<OWLEntity> signature) {
        List<Object> key = new ArrayList<>(3);
        key.add(mode);
        key.add(axiom);
        key.add(signature);
        return cache.computeIfAbsent(key, k -> {
            try {
                return Optional.of(Boolean.valueOf(
                    (mode == LocalityClass.BOTTOM ? bottom : top).isLocal(axiom, signature)));
            } catch (RuntimeException e) {
                return Optional.empty();
            }
        });
    }

    /**
     * Checks that {@code module} is a module of {@code base} for {@code seed} via the
     * locality condition for {@code mode} (BOTTOM or TOP).
     */
    Verdict check(Set<OWLAxiom> base, Set<OWLAxiom> module, Set<OWLEntity> seed,
        LocalityClass mode) {
        Set<OWLEntity> signature = new HashSet<>(seed);
        module.forEach(ax -> ax.signature().forEach(signature::add));
        Set<OWLEntity> frozen = Collections.unmodifiableSet(signature);
        Verdict verdict = new Verdict();
        for (OWLAxiom axiom : base) {
            if (module.contains(axiom)) {
                continue;
            }
            Optional<Boolean> local = isLocal(mode, axiom, frozen);
            if (!local.isPresent()) {
                verdict.undecided++;
            } else if (!local.get().booleanValue()) {
                verdict.violations.add(axiom);
            }
        }
        return verdict;
    }

    @Override
    public void close() {
        bottom.dispose();
        top.dispose();
    }
}
