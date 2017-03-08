package uk.ac.manchester.cs.factplusplusad;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.semanticweb.owlapitools.decomposition.AxiomWrapper;
import org.semanticweb.owlapitools.decomposition.OntologyAtom;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * atomical decomposer of the ontology
 */
class AtomicDecomposer {

    /**
     * atomic structure to build
     */
    AOStructure aos = null;
    /**
     * modularizer to build modules
     */
    Modularizer modularizer;
    /**
     * tautologies of the ontology
     */
    Set<AxiomWrapper> tautologies;
    /**
     * fake atom that represents the whole ontology
     */
    OntologyAtom rootAtom = null;
    /**
     * module type for current AOS creation
     */
    ModuleType type;

    /**
     * @param m modulariser
     */
    AtomicDecomposer(Modularizer m) {
        modularizer = m;
    }

    /**
     * restore all tautologies back
     */
    void restoreTautologies() {
        tautologies.forEach(p -> p.setUsed(true));
    }

    /**
     * @return already created atomic structure
     */
    AOStructure getAOS() {
        return aos;
    }

    /**
     * @return number of performed locality checks
     */
    long getLocChekNumber() {
        return modularizer.getNChecks();
    }

    /**
     * Remove tautologies (axioms that are always local) from the ontology temporarily.
     *
     * @param o ontology
     */
    void removeTautologies(Collection<AxiomWrapper> o) {
        // we might use it for another decomposition
        tautologies.clear();
        for (AxiomWrapper p : o) {
            if (p.isUsed() && modularizer.isTautology(p.getAxiom(), type)) {
                tautologies.add(p);
                p.setUsed(false);
            }
        }
    }

    /**
     * Build a module for given axiom AX; use parent atom's module as a base for the module search.
     *
     * @param sig signature
     * @param parent parent atom
     * @return module atom
     */
    Optional<OntologyAtom> buildModule(Signature sig, OntologyAtom parent) {
        // build a module for a given signature
        modularizer.extract(parent.getModule(), sig, type);
        Collection<AxiomWrapper> module = modularizer.getModule();
        // if module is empty (empty bottom atom) -- do nothing
        if (module.isEmpty()) {
            return Optional.empty();
        }
        // check if the module corresponds to a PARENT one; modules are the same
        // iff their sizes are the same
        if (parent != rootAtom && module.size() == parent.getModule().size()) {
            return Optional.of(parent);
        }
        // create new atom with that module
        OntologyAtom atom = aos.newAtom();
        atom.setModule(module);
        return Optional.of(atom);
    }

    /**
     * Create atom for given axiom AX; use parent atom's module as a base for the module search.
     *
     * @param ax axiom
     * @param parent parent atom
     * @return atom
     */
    OntologyAtom createAtom(AxiomWrapper ax, OntologyAtom parent) {
        // check whether axiom already has an atom
        if (ax.getAtom().isPresent()) {
            return ax.getAtom().get();
        }
        // build an atom: use a module to find atomic dependencies
        Optional<OntologyAtom> atom = buildModule(new Signature(ax.signature()), parent);
        // no empty modules should be here
        assert atom.isPresent();
        // register axiom as a part of an atom
        atom.get().addAxiom(ax);
        // if atom is the same as parent -- nothing more to do
        if (atom.get() == parent) {
            return parent;
        }
        // not the same as parent: for all atom's axioms check their atoms and
        // make ATOM depend on them
        for (AxiomWrapper q : atom.get().getModule()) {
            if (q != ax) {
                atom.get().addDepAtom(createAtom(q, atom.get()));
            }
        }
        return atom.get();
    }

    /**
     * @param o ontology
     * @param t module type
     * @return atomic structure for given module type T
     */
    AOStructure getAOS(Collection<AxiomWrapper> o, ModuleType t) {
        // remember the type of the module
        type = t;
        // prepare a new AO structure
        aos = new AOStructure();
        // init semantic locality checker
        modularizer.preprocessOntology(o);
        // we don't need tautologies here
        removeTautologies(o);
        // init the root atom
        rootAtom = new OntologyAtom();
        rootAtom.setModule(o);
        // build the "bottom" atom for an empty signature
        Optional<OntologyAtom> bottomAtom = buildModule(new Signature(), rootAtom);
        if (bottomAtom.isPresent()) {
            for (AxiomWrapper q : bottomAtom.get().getModule()) {
                bottomAtom.get().addAxiom(q);
            }
        }
        // create atoms for all the axioms in the ontology
        for (AxiomWrapper p : o) {
            if (p.isUsed() && !p.getAtom().isPresent()) {
                createAtom(p, rootAtom);
            }
        }
        // restore tautologies in the ontology
        restoreTautologies();
        // clear the root atom
        rootAtom = null;
        // reduce graph
        aos.reduceGraph();
        return aos;
    }
}
