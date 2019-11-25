package org.semanticweb.owlapi6.factplusplusad;

import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import org.semanticweb.owlapi6.atomicdecomposition.AxiomWrapper;
import org.semanticweb.owlapi6.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi6.atomicdecomposition.Signature;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.modularity.ModuleType;

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
        modularizer.preprocessOntology(asList(ontology.axioms().map(AxiomWrapper::new)));
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
        return getModule(asList(ontology.axioms().map(AxiomWrapper::new)), sig, type);
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
            .filter(Objects::nonNull));
    }
}
