package org.semanticweb.owlapi6.debugging;

import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to detect clashing imports, when different versions of the same ontology are
 * imported through different URLs. (There cannot be any clashes when the same import URL is used,
 * and the same ontology can be imported through different URLs without problem. A clash is only
 * significant when the ontologies imported differ in content.)
 */
public class ImportsCompatibilityVerifier {
    private static final Logger LOGGER =
        LoggerFactory.getLogger(ImportsCompatibilityVerifier.class);
    private static final String TEMPLATE_IDS =
        "Different versions of the same ontology loaded: %s and %s loaded from %s and %s: ontology ids differ";
    private static final String TEMPLATE_AXIOMS =
        "Different versions of the same ontology loaded: %s and %s loaded from %s and %s: axioms differ";
    private static final String TEMPLATE_ANNOTATIONS =
        "Different versions of the same ontology loaded: %s and %s loaded from %s and %s: ontology annotations differ";
    private Supplier<OWLOntologyManager> supplier;

    /**
     * @param supplier This class needs to create a separate ontology manager for each ontology in
     *        the imports closure. This parameter allows the caller to specify how the managers
     *        should be created; for example, the manager can be set up to resolve ontologies from
     *        local files or zipped files.
     */
    public ImportsCompatibilityVerifier(Supplier<OWLOntologyManager> supplier) {
        this.supplier = supplier;
    }

    /**
     * @param ontologyIRI root of the imports closure
     * @return true if the imports closure has no conflicts
     */
    public boolean ensureImportsClosureIsConflictFree(IRI ontologyIRI) {
        Map<IRI, OWLOntology> indexByLoadIRI = new HashMap<>();
        // each ontology must be loaded on a separate menager
        // with no imports resolution, so that conflicting imports can be explored
        Map<IRI, List<OWLOntology>> indexByLogicalIRI = new HashMap<>();
        OWLOntology o = loadOneOntology(ontologyIRI, indexByLoadIRI, indexByLogicalIRI);
        iterate(o, indexByLoadIRI, indexByLogicalIRI);
        // The map contains the imports closure ontologies, grouped by ontology IRI.
        // A conflict free imports closure has either no two ontologies with the same ontology iri,
        // or the contents of these ontologies are identical (i.e., the same ontology is imported
        // from two other ontologies in the closure)
        List<String> reports = computeReports(indexByLoadIRI, indexByLogicalIRI);
        reports.forEach(LOGGER::error);
        return reports.isEmpty();
    }

    protected List<String> computeReports(Map<IRI, OWLOntology> indexByLoadIRI,
        Map<IRI, List<OWLOntology>> indexByLogicalIRI) {
        List<String> reports = new ArrayList<>();
        indexByLogicalIRI.values().stream().filter(p -> p.size() > 1).forEach(c -> {
            OWLOntology ref = c.get(0);
            IRI refIRI = loadedFromIRI(indexByLoadIRI, ref);
            OWLOntologyID refId = ref.getOntologyID();
            for (int i = 1; i < c.size(); i++) {
                OWLOntology o = c.get(i);
                IRI oIRI = loadedFromIRI(indexByLoadIRI, o);
                OWLOntologyID id = o.getOntologyID();
                if (!refId.equals(id)) {
                    reports.add(String.format(TEMPLATE_IDS, refId, id, refIRI, oIRI));
                }
                if (!asUnorderedSet(ref.axioms()).equals(asUnorderedSet(o.axioms()))) {
                    reports.add(String.format(TEMPLATE_AXIOMS, refId, id, refIRI, oIRI));
                }
                if (!asUnorderedSet(ref.annotations()).equals(asUnorderedSet(o.annotations()))) {
                    reports.add(String.format(TEMPLATE_ANNOTATIONS, refId, id, refIRI, oIRI));
                }
            }
        });
        return reports;
    }

    @Nullable
    protected IRI loadedFromIRI(Map<IRI, OWLOntology> indexByLoadIRI, OWLOntology ref) {
        return indexByLoadIRI.entrySet().stream().filter(e -> e.getValue() == ref).findAny()
            .map(Map.Entry::getKey).orElse(null);
    }

    protected OWLOntology loadOneOntology(IRI ontologyIRI, Map<IRI, OWLOntology> list,
        Map<IRI, List<OWLOntology>> map) {
        try {
            OWLOntologyManager m = managerWitoutImportsResolution();
            OWLOntology o = m.loadOntology(ontologyIRI);
            map.computeIfAbsent(o.getOntologyID().getOntologyIRI().orElse(null),
                x -> new ArrayList<>()).add(o);
            list.put(ontologyIRI, o);
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void iterate(OWLOntology o, Map<IRI, OWLOntology> iris,
        Map<IRI, List<OWLOntology>> map) {
        o.importsDeclarations().filter(i -> !iris.containsKey(i.getIRI()))
            .forEach(i -> loadOneOntology(i.getIRI(), iris, map));
    }

    protected OWLOntologyManager managerWitoutImportsResolution() {
        OWLOntologyManager m = supplier.get();
        m.getOntologyConfigurator().withDisableImportsLoading(true);
        return m;
    }
}
