package org.semanticweb.owlapi.benchmarks;

import java.nio.file.FileSystems;

@SuppressWarnings("javadoc")
public class FullGalenMemoryBenchmark {

    public static void main(String[] args) throws Exception {
        MemoryBenchmark.memoryProfile(FileSystems.getDefault().getPath(
        "/Users/ignazio/full-galen.owl"), FileSystems.getDefault().getPath(
        "fullgalen.hprof"));
    }
}
