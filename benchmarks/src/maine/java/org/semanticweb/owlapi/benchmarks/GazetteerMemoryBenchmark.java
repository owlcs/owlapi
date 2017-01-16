package org.semanticweb.owlapi.benchmarks;

import org.obolibrary.oboformat.parser.OBOFormatParser;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;

@SuppressWarnings("javadoc")
public class GazetteerMemoryBenchmark {

    public static void main(String[] args) throws Exception {
        memoryProfile(FileSystems.getDefault().getPath("/Users/ignazio/gaz-fixed.obo"), FileSystems.getDefault()
            .getPath("gazetteer" + new Date() + ".hprof"));
    }

    public static void memoryProfile(Path ontologyPath, Path hprofPath) throws IOException {
        OBOFormatParser parser = new OBOFormatParser();
        parser.parse(ontologyPath.toFile());
        MemoryBenchmark.getDiagnostics().dumpHeap(hprofPath.toString(), true);
    }
}
