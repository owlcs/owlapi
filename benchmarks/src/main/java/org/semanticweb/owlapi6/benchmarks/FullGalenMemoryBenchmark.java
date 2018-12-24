package org.semanticweb.owlapi6.benchmarks;

import java.nio.file.FileSystems;
import java.util.Date;

@SuppressWarnings("javadoc")
public class FullGalenMemoryBenchmark {

    public static void main(String[] args) throws Exception {
        MemoryBenchmark.memoryProfile(
            FileSystems.getDefault().getPath("/Users/ignazio/Downloads/full-galen.owl"),
            FileSystems.getDefault().getPath("fullgalen" + new Date() + ".hprof"));
    }
}
