package org.semanticweb.owlapi.benchmarks;

import static java.lang.management.ManagementFactory.*;

import java.io.File;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.GZipFileDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;

/**
 * Created by ses on 3/19/14.
 */
public class MemoryBenchmark {

    private static Logger logger = LoggerFactory.getLogger(MemoryBenchmark.class);

    @SuppressWarnings("javadoc")
    public static void main(String[] args) throws Exception {
        if (args.length > 2) {
            System.err.println("usage: " + MemoryBenchmark.class.getCanonicalName() + "<src-ontology> <dest-hprof>");
        }
        String filename = "/Users/ses/ontologies/GO/go.ofn";
        if (args.length > 0) {
            filename = args[0];
        }
        Path ontologyPath = FileSystems.getDefault().getPath(filename);
        Path hprofPath = getHprofPath(args, ontologyPath);
        memoryProfile(ontologyPath, hprofPath);
        System.exit(0);
    }

    /**
     * Run memory profiling for an input ontology and output the dump file to
     * the hprof path provided
     * 
     * @param ontologyPath
     *        input ontology
     * @param hprofPath
     *        dump file path for output
     * @throws OWLOntologyCreationException
     *         if the ontology cannot be created or loaded
     * @throws IOException
     *         if the dump file cannot be created
     */
    public static void memoryProfile(Path ontologyPath, Path hprofPath)
            throws OWLOntologyCreationException, IOException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = ontologyPath.toFile();
        manager.getIRIMappers().add(new AutoIRIMapper(file.getParentFile(), false));
        OWLOntologyDocumentSource ds = null;
        if (file.getName().endsWith(".gz")) {
            ds = new GZipFileDocumentSource(file);
        } else {
            ds = new FileDocumentSource(file);
        }
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration().setStrict(false);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ds, config);
        getDiagnostics().dumpHeap(hprofPath.toString(), true);
        manager.removeOntology(ontology);
    }

    protected static HotSpotDiagnosticMXBean getDiagnostics() throws IOException {
        HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = newPlatformMXBeanProxy(getPlatformMBeanServer(),
                "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
        for (VMOption vmOption : hotSpotDiagnosticMXBean.getDiagnosticOptions()) {
            logger.info("vmOption = {}", vmOption);
        }
        return hotSpotDiagnosticMXBean;
    }

    protected static Path getHprofPath(String[] args, Path ontologyPath) {
        Path hprofPath = null;
        if (args.length > 1) {
            hprofPath = FileSystems.getDefault().getPath(args[1]);
        } else {
            try {
                String name = newPlatformMXBeanProxy(getPlatformMBeanServer(), RUNTIME_MXBEAN_NAME, RuntimeMXBean.class)
                        .getName();
                String profileFileName = "ontology-hprof-" + name + ".hprof";
                hprofPath = ontologyPath.resolveSibling(profileFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("hprofPath = " + hprofPath);
        try {
            if (hprofPath.toFile().exists()) {
                hprofPath.toFile().delete();
            }
        } catch (@SuppressWarnings("unused") Exception e) {}
        return hprofPath;
    }
}
