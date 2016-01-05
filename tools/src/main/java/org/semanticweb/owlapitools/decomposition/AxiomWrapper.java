package org.semanticweb.owlapitools.decomposition;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Wrapper around an OWLAxiom to hold attributes such as used, included in a
 * module, included in search space and atom which contains it.
 * 
 * @author ignazio
 */
public class AxiomWrapper {

    private OWLAxiom axiom;
    private boolean used = true;
    private boolean searchspace;
    private boolean module;
    private @Nullable OntologyAtom atom;

    /**
     * @param axiom
     *        axiom to wrap
     */
    public AxiomWrapper(OWLAxiom axiom) {
        this.axiom = axiom;
    }

    /** @return wrapped axiom */
    public OWLAxiom getAxiom() {
        return axiom;
    }

    /**
     * @param b
     *        value for used
     */
    public void setUsed(boolean b) {
        used = b;
    }

    /** @return value for used */
    public boolean isUsed() {
        return used;
    }

    /**
     * @param b
     *        value for in search space
     */
    public void setInSearchSpace(boolean b) {
        searchspace = b;
    }

    /** @return true if in search space */
    public boolean isInSearchSpace() {
        return searchspace;
    }

    /**
     * @param b
     *        value for in module
     */
    public void setInModule(boolean b) {
        module = b;
    }

    /** @return true if in module */
    public boolean isInModule() {
        return module;
    }

    /**
     * @param atom
     *        atom including the axiom
     */
    public void setAtom(@Nullable OntologyAtom atom) {
        this.atom = atom;
    }

    /** @return the including atom */
    @Nullable
    public OntologyAtom getAtom() {
        return atom;
    }
}
