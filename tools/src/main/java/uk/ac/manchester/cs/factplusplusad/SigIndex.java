package uk.ac.manchester.cs.factplusplusad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapitools.decomposition.AxiomWrapper;

class SigIndex {

    /**
     * map between entities and axioms that contains them in their signature
     */
    private Map<OWLEntity, Collection<AxiomWrapper>> base = new HashMap<>();
    /**
     * locality checker
     */
    private LocalityChecker checker;
    /**
     * empty signature to test the non-locality
     */
    private Signature emptySig = new Signature();
    /**
     * sets of axioms non-local wrt the empty signature
     */
    private Collection<AxiomWrapper> topNonLocal = new ArrayList<>();
    private Collection<AxiomWrapper> bottomNonLocal = new ArrayList<>();
    /**
     * number of registered axioms
     */
    private int nRegistered = 0;
    /**
     * number of registered axioms
     */
    private int nUnregistered = 0;

    /**
     * @param c locality checker
     */
    public SigIndex(LocalityChecker c) {
        checker = c;
    }

    /**
     * add axiom AX to the non-local set with top-locality value TOP
     *
     * @param ax axiom
     * @param top top or bottom
     */
    private void checkNonLocal(AxiomWrapper ax, boolean top) {
        emptySig.setLocality(top);
        checker.setSignatureValue(emptySig);
        if (!checker.local(ax.getAxiom())) {
            if (top) {
                topNonLocal.add(ax);
            } else {
                bottomNonLocal.add(ax);
            }
        }
    }

    /**
     * clear internal structures
     */
    public void clear() {
        base.clear();
        topNonLocal.clear();
        bottomNonLocal.clear();
    }

    /**
     * given an entity, return a set of all axioms that contain this entity in a signature
     *
     * @param entity the entity
     * @return collection of axioms referring the entity
     */
    public Collection<AxiomWrapper> getAxioms(OWLEntity entity) {
        return base.get(entity);
    }

    /**
     * get the non-local axioms with top-locality value TOP
     *
     * @param top true if top locality should be used
     * @return collection of non local axioms
     */
    public Collection<AxiomWrapper> getNonLocal(boolean top) {
        return top ? topNonLocal : bottomNonLocal;
    }

    /**
     * @return number of ever processed axioms
     */
    public int nProcessedAx() {
        return nRegistered;
    }

    /**
     * @return number of currently registered axioms
     */
    int nRegisteredAx() {
        return nRegistered - nUnregistered;
    }

    /**
     * preprocess given set of axioms
     *
     * @param axioms the axioms to process
     */
    public void preprocessOntology(Collection<AxiomWrapper> axioms) {
        axioms.forEach(this::processAx);
    }

    /**
     * process an axiom wrt its Used status
     *
     * @param ax the axiom to process
     */
    public void processAx(AxiomWrapper ax) {
        if (ax.isUsed()) {
            registerAx(ax);
        } else {
            unregisterAx(ax);
        }
    }

    /**
     * register an axiom
     *
     * @param ax axiom
     */
    private void registerAx(AxiomWrapper ax) {
        ax.signature().forEach(a -> base.computeIfAbsent(a, x -> new HashSet<>()).add(ax));
        // check whether the axiom is non-local
        checkNonLocal(ax, false);
        checkNonLocal(ax, true);
        ++nRegistered;
    }

    /**
     * unregister an axiom AX
     *
     * @param ax axiom
     */
    private void unregisterAx(AxiomWrapper ax) {
        ax.signature().forEach(p -> base.getOrDefault(p, Collections.emptySet()).remove(ax));
        // remove from the non-locality
        topNonLocal.remove(ax);
        bottomNonLocal.remove(ax);
        ++nUnregistered;
    }
}
