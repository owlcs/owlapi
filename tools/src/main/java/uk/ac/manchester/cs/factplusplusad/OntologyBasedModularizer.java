package uk.ac.manchester.cs.factplusplusad;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.stream.Stream;

import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * Ontology based modularizer.
 */
public class OntologyBasedModularizer {

    /**
     * ontology to work with
     */
    OWLOntology ontology;
    /**
     * pointer to a modularizer
     */
    Modularizer modularizer;

    /**
     * init c'tor
     *
     * @param ontology ontology to modularise
     * @param moduleMethod modularisation method
     */
    public OntologyBasedModularizer(OWLOntology ontology, ModuleMethod moduleMethod) {
        this.ontology = ontology;
        modularizer = new Modularizer(moduleMethod);
        modularizer.preprocessOntology(asList(ontology.axioms().map(a -> new AxiomWrapper(a))));
    }

    /**
     * Get module.
     *
     * @param from axioms to modularise
     * @param sig signature
     * @param type type of module
     * @return module
     */
    Collection<AxiomWrapper> getModule(Collection<AxiomWrapper> from, Signature sig,
        ModuleType type) {
        modularizer.extract(from, sig, type);
        return modularizer.getModule();
    }

    /**
     * Get module.
     *
     * @param sig signature
     * @param type type of module
     * @return module
     */
    Collection<AxiomWrapper> getModule(Signature sig, ModuleType type) {
        return getModule(asList(ontology.axioms().map(a -> new AxiomWrapper(a))), sig, type);
    }

    /**
     * @return the modularizer
     */
    Modularizer getModularizer() {
        return modularizer;
    }

    /**
     * @param entities signature
     * @param type modulet type
     * @return module
     */
    public Collection<OWLAxiom> getModule(Stream<OWLEntity> entities, ModuleType type) {
        return asList(getModule(new Signature(entities), type).stream().map(AxiomWrapper::getAxiom)
            .filter(a -> a != null));
    }
}
