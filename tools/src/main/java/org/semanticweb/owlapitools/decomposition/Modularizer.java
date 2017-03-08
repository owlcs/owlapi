package org.semanticweb.owlapitools.decomposition;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * class to create modules of an ontology wrt module type
 */
public class Modularizer {

    /**
     * pointer to a sig index; if not NULL then use optimized algo
     */
    private final SigIndex sigIndex;
    /**
     * shared signature signature
     */
    private Signature sig;
    /**
     * internal syntactic locality checker
     */
    private LocalityChecker checker;
    /**
     * module as a list of axioms
     */
    private List<AxiomWrapper> module = new ArrayList<>();
    /**
     * queue of unprocessed entities
     */
    private Deque<OWLEntity> workQueue;
    /**
     * number of locality check calls
     */
    private long nChecks = 0;
    /**
     * number of non-local axioms
     */
    private long nNonLocal = 0;

    /**
     * @param c the clocality checker
     */
    public Modularizer(LocalityChecker c) {
        checker = c;
        sig = c.getSignature();
        sigIndex = new SigIndex(checker);
    }

    /**
     * @return the signature
     */
    public Signature getSignature() {
        return sig;
    }

    /**
     * update SIG wrt the axiom signature
     *
     * @param axiom axiom
     */
    private void addAxiomSig(AxiomWrapper axiom) {
        axiom.getAxiom().signature().filter(sig::add).forEach(workQueue::add);
    }

    /**
     * add an axiom to a module
     *
     * @param axiom axiom
     */
    private void addAxiomToModule(AxiomWrapper axiom) {
        axiom.setInModule(true);
        module.add(axiom);
        // update the signature
        addAxiomSig(axiom);
    }

    /**
     * @param ax axiom
     * @return true iff an AXiom is non-local
     */
    private boolean isNonLocal(AxiomWrapper ax) {
        boolean b = !checker.local(ax.getAxiom());
        ++nChecks;
        if (b) {
            ++nNonLocal;
        }
        return b;
    }

    /**
     * @param ax axiom add an axiom if it is non-local (or if noCheck is true)
     * @param noCheck true if locality check is not to be performed
     */
    private void addNonLocal(AxiomWrapper ax, boolean noCheck) {
        if (noCheck || isNonLocal(ax)) {
            addAxiomToModule(ax);
        }
    }

    /**
     * add all the non-local axioms from given axiom-set AxSet
     *
     * @param axSet collection of axioms
     * @param noCheck true if locality check is not to be performed
     */
    private void addNonLocal(Collection<AxiomWrapper> axSet, boolean noCheck) {
        for (AxiomWrapper q : axSet) {
            if (!q.isInModule() && q.isInSearchSpace()) {
                this.addNonLocal(q, noCheck);
            }
        }
    }

    /**
     * build a module traversing axioms by a signature
     */
    private void extractModuleQueue() {
        // init queue with a sig
        workQueue.addAll(sig.getSignature());
        // add all the axioms that are non-local wrt given value of a
        // top-locality
        addNonLocal(sigIndex.getNonLocal(sig.topCLocal()), true);
        // main cycle
        while (!workQueue.isEmpty()) {
            // for all the axioms that contains entity in their signature
            Collection<AxiomWrapper> axioms = sigIndex.getAxioms(workQueue.poll());
            addNonLocal(axioms, false);
        }
    }

    /**
     * extract module wrt presence of a sig index
     *
     * @param list axioms
     */
    private void extractModule(Collection<AxiomWrapper> list) {
        module.clear();
        // clear the module flag in the input
        list.forEach(p -> p.setInModule(false));
        list.stream().filter(p -> p.isUsed()).forEach(p -> p.setInSearchSpace(true));
        extractModuleQueue();
        list.forEach(p -> p.setInSearchSpace(false));
    }

    /**
     * allow the checker to preprocess an ontology if necessary
     *
     * @param axioms list of wrapped axioms
     */
    public void preprocessOntology(Collection<AxiomWrapper> axioms) {
        checker.preprocessOntology(axioms);
        sigIndex.clear();
        sigIndex.preprocessOntology(axioms);
        workQueue = new ArrayDeque<>(axioms.size());
        nChecks += 2 * axioms.size();
    }

    /**
     * extract module wrt SIGNATURE and TYPE from the set of axioms
     *
     * @param axioms axiom
     * @param signature signature
     * @param type type
     */
    public void extract(List<AxiomWrapper> axioms, Signature signature, ModuleType type) {
        boolean topLocality = type == ModuleType.TOP;
        sig = signature;
        checker.setSignatureValue(sig);
        sig.setLocality(topLocality);
        extractModule(axioms);
        if (type != ModuleType.STAR) {
            return;
        }
        // here there is a star: do the cycle until stabilization
        int size;
        do {
            size = module.size();
            List<AxiomWrapper> oldModule = new ArrayList<>(module);
            topLocality = !topLocality;
            sig = signature;
            sig.setLocality(topLocality);
            extractModule(oldModule);
        } while (size != module.size());
    }

    /**
     * @param ax axiom
     * @param type type
     * @return true iff the axiom AX is a tautology wrt given type
     */
    public boolean isTautology(OWLAxiom ax, ModuleType type) {
        boolean topLocality = type == ModuleType.TOP;
        sig = new Signature(ax.signature());
        sig.setLocality(topLocality);
        // axiom is a tautology if it is local wrt its own signature
        boolean toReturn = checker.local(ax);
        if (type != ModuleType.STAR || !toReturn) {
            return toReturn;
        }
        // here it is STAR case and AX is local wrt BOT
        sig.setLocality(!topLocality);
        return checker.local(ax);
    }

    /**
     * @return the Locality checker
     */
    public LocalityChecker getLocalityChecker() {
        return checker;
    }

    /**
     * @return the last computed module
     */
    public Collection<AxiomWrapper> getModule() {
        return module;
    }

    /**
     * @return number of checks made
     */
    long getNChecks() {
        return nChecks;
    }

    /**
     * @return number of axioms that were local
     */
    long getNNonLocal() {
        return nNonLocal;
    }

    /**
     * @param axiom axiom
     * @param signature signature
     * @param type type
     */
    public void extract(AxiomWrapper axiom, Signature signature, ModuleType type) {
        this.extract(Collections.singletonList(axiom), signature, type);
    }
}
