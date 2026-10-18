package org.semanticweb.owlapi.modularity.comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;
import org.semanticweb.owlapi.modularity.comparison.ComparisonReport.Outcome;
import org.semanticweb.owlapi.modularity.comparison.LocalityCases.LocalityCase;
import org.semanticweb.owlapi.modularity.comparison.LocalityImplementations.LocalityImpl;

/**
 * Scores every locality checker of the tools module, syntactic and semantic, on
 * the shared case table {@link LocalityCases#shared()}. The scoreboard is written
 * to {@code target/modularity-comparison/locality-shared.md}.
 *
 * <p>Semantic checkers are exact, so they must agree with every case. Syntactic
 * checkers must agree with every {@code soundness}, {@code non-logical} and
 * {@code signature} case, and should agree with every {@code calculus} case.</p>
 */
@Tag("comparison")
class SharedLocalityComparisonTest {

    private static final ComparisonReport REPORT =
        new ComparisonReport("locality-shared", "Locality checkers: shared cases");
    private static LocalityImplementations implementations;

    @BeforeAll
    static void setUp() {
        implementations = new LocalityImplementations(true);
        implementations.all().forEach(i -> REPORT.implementation(i.name()));
    }

    @AfterAll
    static void tearDown() {
        REPORT.write();
        implementations.close();
    }

    @TestFactory
    Stream<DynamicNode> sharedCases() {
        return dynamicTests(implementations.all(), LocalityCases.shared(), REPORT);
    }

    static Stream<DynamicNode> dynamicTests(List<LocalityImpl> impls, List<LocalityCase> cases,
        ComparisonReport report) {
        List<DynamicNode> nodes = new ArrayList<>();
        for (LocalityImpl impl : impls) {
            List<DynamicNode> tests = new ArrayList<>();
            for (LocalityCase c : cases) {
                if (!impl.supports(c.mode)) {
                    continue;
                }
                tests.add(DynamicTest.dynamicTest(c.id, () -> check(impl, c, report)));
            }
            nodes.add(DynamicContainer.dynamicContainer(impl.name(), tests));
        }
        return nodes.stream();
    }

    static void check(LocalityImpl impl, LocalityCase c, ComparisonReport report) {
        boolean actual;
        try {
            actual = impl.isLocal(c.axiom, c.signature, c.mode);
        } catch (RuntimeException | StackOverflowError e) {
            report.recordFailure(impl.name(), impl.reference(), c.id, c.category, e);
            return;
        }
        Outcome outcome;
        if (actual == c.expected) {
            outcome = Outcome.PASS;
        } else {
            outcome = actual ? Outcome.UNSOUND : Outcome.INCOMPLETE;
        }
        // A syntactic checker may be incomplete on cases beyond its calculus: that is
        // recorded, but not asserted, by the beyond-calculus suite.
        boolean asserted = impl.reference()
            && !(outcome == Outcome.INCOMPLETE && !impl.semantic()
                && LocalityCases.BEYOND.equals(c.category));
        report.record(impl.name(), asserted, c.id, c.category, outcome, c.describe());
    }
}
