package org.semanticweb.owlapitools.decomposition;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * Wrapper around an OWLAxiom to hold attributes such as used, included in a module, included in
 * search space and atom which contains it.
 *
 * @author ignazio
 */
public class AxiomWrapper implements Serializable {

    private OWLAxiom axiom;
    private boolean used = true;
    private boolean searchspace;
    private boolean module;
    @Nullable
    private OntologyAtom atom;
    private int id;

    /**
     * @param axiom axiom to wrap
     */
    public AxiomWrapper(OWLAxiom axiom) {
        this.axiom = axiom;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id for the wrapper
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return signature of the axiom, or empty if no axiom is set
     */
    public Stream<OWLEntity> signature() {
        return axiom.signature();
    }

    /**
     * @return wrapped axiom
     */
    public OWLAxiom getAxiom() {
        return axiom;
    }

    /**
     * @return value for used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * @param b value for used
     */
    public void setUsed(boolean b) {
        used = b;
    }

    /**
     * @return true if in search space
     */
    public boolean isInSearchSpace() {
        return searchspace;
    }

    /**
     * @param b value for in search space
     */
    public void setInSearchSpace(boolean b) {
        searchspace = b;
    }

    /**
     * @return true if in module
     */
    public boolean isInModule() {
        return module;
    }

    /**
     * @param b value for in module
     */
    public void setInModule(boolean b) {
        module = b;
    }

    /**
     * @return the including atom
     */
    public Optional<OntologyAtom> getAtom() {
        return Optional.ofNullable(atom);
    }

    /**
     * @param atom atom including the axiom
     */
    public void setAtom(@Nullable OntologyAtom atom) {
        this.atom = atom;
    }
}
