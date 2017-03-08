package uk.ac.manchester.cs.factplusplusad;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static uk.ac.manchester.cs.owlapi.modularity.ModuleType.STAR;
import static uk.ac.manchester.cs.owlapi.modularity.ModuleType.TOP;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.atomicdecomposition.ModuleMethod;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;

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
    private Signature sig = new Signature();
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
     * true if no atoms are processed ATM
     */
    private boolean noAtomsProcessing = true;

    /**
     * init c'tor
     *
     * @param moduleMethod module method
     */
    Modularizer(ModuleMethod moduleMethod) {
        checker = LocalityChecker.createLocalityChecker(moduleMethod, sig);
        sigIndex = new SigIndex(checker);
    }
    // methods

    /**
     * update SIG wrt the axiom signature
     *
     * @param axiomSig signature to add
     */
    void addAxiomSig(Stream<OWLEntity> axiomSig) {
        axiomSig.filter(p -> !sig.contains(p)).forEach(p -> {
            // new one
            workQueue.push(p);
            sig.add(p);
        });
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
        addAxiomSig(axiom.signature());
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
     * add an axiom if it is non-local (or in noCheck is true)
     *
     * @param ax axiom to add
     * @param noCheck check or not
     */
    void addNonLocal(AxiomWrapper ax, boolean noCheck) {
        if (noCheck || isNonLocal(ax)) {
            addAxiomToModule(ax);
            if (noAtomsProcessing && ax.getAtom().isPresent()) {
                noAtomsProcessing = false;
                addNonLocal(ax.getAtom().get().getModule(), true);
                noAtomsProcessing = true;
            }
        }
    }

    /**
     * Add all the non-local axioms from given axiom-set AxSet.
     *
     * @param axSet axiom set
     * @param noCheck check or not
     */
    void addNonLocal(Collection<AxiomWrapper> axSet, boolean noCheck) {
        for (AxiomWrapper q : axSet) {
            if (!q.isInModule() && q.isInSearchSpace()) {
                addNonLocal(q, noCheck);
            }
        }
    }

    /**
     * build a module traversing axioms by a signature
     */
    private void extractModuleQueue() {
        // init queue with a sig
        add(workQueue, sig.getSignature());
        // add all the axioms that are non-local wrt given value of a
        // top-locality
        addNonLocal(sigIndex.getNonLocal(sig.topCLocal()), true);
        // main cycle
        while (!workQueue.isEmpty()) {
            // for all the axioms that contains entity in their signature
            Collection<AxiomWrapper> axioms = sigIndex.getAxioms(workQueue.pop());
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
        list.forEach(p -> {
            p.setInModule(false);
            if (p.isUsed()) {
                p.setInSearchSpace(true);
            }
        });
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
    void extract(Collection<AxiomWrapper> axioms, Signature signature, ModuleType type) {
        boolean topLocality = type == TOP;
        sig.setSignature(signature);
        sig.setLocality(topLocality);
        extractModule(axioms);
        if (type != STAR) {
            return;
        }
        // here there is a star: do the cycle until stabilization
        int size;
        List<AxiomWrapper> oldModule = new ArrayList<>();
        do {
            size = module.size();
            oldModule.clear();
            oldModule.addAll(module);
            topLocality = !topLocality;
            sig.setSignature(signature);
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
        boolean topLocality = type == TOP;
        sig.setSignature(ax.signature());
        sig.setLocality(topLocality);
        // axiom is a tautology if it is local wrt its own signature
        boolean toReturn = checker.local(ax);
        if (type != STAR || !toReturn) {
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
}
