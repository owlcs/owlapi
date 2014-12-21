package org.semanticweb.owlapi.benchmarks;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;

import javax.management.MBeanServer;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by ses on 3/19/14.
 */
public class MemoryBenchmark {
    private static Logger logger = LoggerFactory.getLogger(MemoryBenchmark.class);
    private static HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean;
    private static RuntimeMXBean runtimeMXBean;

    public static void main(String[] args) throws Exception {
        if (args.length > 2) {
            System.err.println("usage: " + MemoryBenchmark.class.getCanonicalName() + "<src-ontology> <dest-hprof>");
        }
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        hotSpotDiagnosticMXBean = ManagementFactory.newPlatformMXBeanProxy(server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
        runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
        for (VMOption vmOption : hotSpotDiagnosticMXBean.getDiagnosticOptions()) {
            logger.info("vmOption = {}", vmOption);
        }

        String filename = "/Users/ses/ontologies/GO/go.ofn";

        if (args.length > 0) {
            filename = args[0];
        }
        Path ontologyPath = FileSystems.getDefault().getPath(filename);

        Path hprofPath = null;
        if (args.length > 1) {
            hprofPath = FileSystems.getDefault().getPath(args[1]);
        } else {
            String profileFileName = "ontology-hprof-" + runtimeMXBean.getName() + ".hprof";
            hprofPath = ontologyPath.resolveSibling(profileFileName);
        }
        System.out.println("hprofPath = " + hprofPath);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File(filename);
        manager.getIRIMappers().add(new AutoIRIMapper(file.getParentFile(), false));

        FileDocumentSource ds = new FileDocumentSource(file);
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration().setStrict(false);
        OWLOntologyImpl ontology = (OWLOntologyImpl) manager.loadOntologyFromOntologyDocument(ds, config);
        try {
            hprofPath.toFile().delete();
        } catch (Exception e) {
        }
        hotSpotDiagnosticMXBean.dumpHeap(hprofPath.toString(), true);
        manager.removeOntology(ontology);
        System.exit(0);
    }
}
