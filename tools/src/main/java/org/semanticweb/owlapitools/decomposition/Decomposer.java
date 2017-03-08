package org.semanticweb.owlapitools.decomposition;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * atomical decomposer of the ontology
 */
public class Decomposer {

    /**
     * atomic structure to build
     */
    @Nullable
    private AtomList atomList = null;
    /**
     * modularizer to build modules
     */
    private Modularizer modularizer;
    /**
     * tautologies of the ontology
     */
    private List<AxiomWrapper> tautologies = new ArrayList<>();
    /**
     * fake atom that represents the whole ontology
     */
    @Nullable
    private OntologyAtom rootAtom = null;
    /**
     * module type for current AOS creation
     */
    private ModuleType type;
    private List<AxiomWrapper> axioms;

    /**
     * @param axioms axiom wrappers to decompose
     * @param c locality checker to use
     */
    public Decomposer(List<AxiomWrapper> axioms, LocalityChecker c) {
        this.axioms = axioms;
        modularizer = buildModulariser(axioms, c);
    }

    /**
     * @param axioms axioms
     * @param c locality checker
     * @return new modularizer
     */
    public static Modularizer buildModulariser(List<AxiomWrapper> axioms, LocalityChecker c) {
        Modularizer m = new Modularizer(c);
        m.preprocessOntology(axioms);
        return m;
    }

    /**
     * @return the modularizer for this decomposer
     */
    public Modularizer getModularizer() {
        return modularizer;
    }

    /**
     * restore all tautologies back
     */
    private void restoreTautologies() {
        for (AxiomWrapper p : tautologies) {
            p.setUsed(true);
        }
    }

    /**
     * remove tautologies (axioms that are always local) from the ontology temporarily
     */
    private void removeTautologies() {
        // we might use it for another decomposition
        tautologies.clear();
        for (AxiomWrapper p : axioms) {
            if (p.isUsed()) {
                // check whether an axiom is local wrt its own signature
                modularizer.extract(p, new Signature(p.getAxiom().signature()), type);
                if (modularizer.isTautology(p.getAxiom(), type)) {
                    tautologies.add(p);
                    p.setUsed(false);
                }
            }
        }
    }

    /**
     * @return all tautologies wrapped
     */
    public List<AxiomWrapper> getTautologies() {
        return tautologies;
    }

    /**
     * @param sig signature
     * @param parent parent atom
     * @return module for given axiom AX; use parent atom's module as a base for the module search
     */
    private Optional<OntologyAtom> buildModule(Signature sig, OntologyAtom parent) {
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
        assert atomList != null;
        OntologyAtom atom = atomList.newAtom();
        atom.setModule(module);
        return Optional.of(atom);
    }

    /**
     * @param ax axiom
     * @param parent parent atom
     * @return create atom for given axiom AX; use parent atom's module as a base for the module
     * search
     */
    private OntologyAtom createAtom(AxiomWrapper ax, OntologyAtom parent) {
        // check whether axiom already has an atom
        Optional<OntologyAtom> atom = ax.getAtom();
        if (atom.isPresent()) {
            return atom.get();
        }
        // build an atom: use a module to find atomic dependencies
        atom = buildModule(new Signature(ax.getAxiom().signature()), parent);
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
        /** do cycle via set to keep the order */
        for (AxiomWrapper q : atom.get().getModule()) {
            // #endif
            if (!q.equals(ax)) {
                atom.get().addDepAtom(createAtom(q, atom.get()));
            }
        }
        return atom.get();
    }

    /**
     * @return atom list class
     */
    public AtomList getAOS() {
        return verifyNotNull(atomList);
    }

    /**
     * @param t module type
     * @return the atomic structure for given module type T
     */
    public AtomList getAOS(ModuleType t) {
        // remember the type of the module
        type = t;
        // prepare a new AO structure
        atomList = new AtomList();
        // init semantic locality checker
        modularizer.preprocessOntology(axioms);
        // we don't need tautologies here
        removeTautologies();
        // init the root atom
        rootAtom = new OntologyAtom();
        rootAtom.setModule(new HashSet<>(axioms));
        // build the "bottom" atom for an empty signature
        assert rootAtom != null;
        Optional<OntologyAtom> bottomAtom = buildModule(new Signature(), rootAtom);
        if (bottomAtom.isPresent()) {
            bottomAtom.get().addAxioms(bottomAtom.get().getModule());
        }
        // create atoms for all the axioms in the ontology
        for (AxiomWrapper p : axioms) {
            if (p.isUsed() && !p.getAtom().isPresent()) {
                assert rootAtom != null;
                createAtom(p, rootAtom);
            }
        }
        // restore tautologies in the ontology
        restoreTautologies();
        rootAtom = null;
        // reduce graph
        assert atomList != null;
        atomList.reduceGraph();
        return verifyNotNull(atomList);
    }

    /**
     * @param signature the signature to use
     * @param moduletype the module type
     * @return a set of axioms that corresponds to the atom with the id INDEX
     */
    public Set<OWLAxiom> getNonLocal(Stream<OWLEntity> signature, ModuleType moduletype) {
        // init signature
        Signature sig = new Signature(signature);
        sig.setLocality(false);
        // do check
        modularizer.getLocalityChecker().setSignatureValue(sig);
        Set<OWLAxiom> result = new HashSet<>();
        for (AxiomWrapper p : axioms) {
            if (!modularizer.getLocalityChecker().local(p.getAxiom())) {
                result.add(p.getAxiom());
            }
        }
        return result;
    }

    /**
     * @param signature the signature to use
     * @param moduletype module type
     * @param useSemantics true if semantic locality should be used
     * @return a set of axioms that corresponds to the atom with the id INDEX
     */
    public Collection<AxiomWrapper> getModule(Stream<OWLEntity> signature, boolean useSemantics,
        ModuleType moduletype) {
        // init signature
        Signature sig = new Signature(signature);
        sig.setLocality(false);
        modularizer.extract(axioms, sig, moduletype);
        return modularizer.getModule();
    }
}
