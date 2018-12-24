package org.semanticweb.owlapi6.bindings;
/**
 * Created by ses on 1/12/17.
 */

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntologyManagerCreateBenchmark {
    @SuppressWarnings("UnusedDeclaration")
    private static Logger logger = LoggerFactory.getLogger(OntologyManagerCreateBenchmark.class);

    @Warmup(iterations = 4)
    @Measurement(iterations = 1)
    @Fork(10)
    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    public OWLOntologyManager createOWLOntologyManager() throws OWLOntologyCreationException {
        return OWLManager.createOWLOntologyManager();

    }

    @Warmup(iterations = 4)
    @Measurement(iterations = 1)
    @Fork(10)
    @BenchmarkMode(Mode.AverageTime)
    @Benchmark
    public int countFactories() throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager.getOntologyFactories().size() +
                manager.getOntologyParsers().size() +
                manager.getOntologyStorers().size();

    }

}
