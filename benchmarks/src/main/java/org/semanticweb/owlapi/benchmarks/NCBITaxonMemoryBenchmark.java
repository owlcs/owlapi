package org.semanticweb.owlapi.benchmarks;

import java.nio.file.FileSystems;

public class NCBITaxonMemoryBenchmark {

    public static void main(String[] args) throws Exception {
        MemoryBenchmark.memoryProfile(FileSystems.getDefault().getPath(
            "/Users/ignazio/workspace/benchmarks/ncbitaxon/src/main/resources/ncbitaxon.rdf.ofn.gz"),
            FileSystems.getDefault().getPath("ncbitaxon.hprof"));
    }
}
