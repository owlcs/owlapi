package uk.ac.manchester.cs.atomicdecomposition;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapitools.decomposition.AtomList;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/**
 * The atomic decomposition graph
 */
public interface AtomicDecomposition {

    /**
     * @param atom atom
     * @return dependencies for atom, including atom
     */
    Set<Atom> getDependencies(Atom atom);

    /**
     * @param atom atom
     * @return dependents for atom, including atom
     */
    Set<Atom> getDependents(Atom atom);

    /**
     * @return all atoms
     */
    Set<Atom> getAtoms();

    /**
     * @return all tautologies
     */
    Set<OWLAxiom> getTautologies();

    /**
     * @param axiom the axiom to search
     * @return Atom containing axiom
     */
    @Nullable
    Atom getAtomForAxiom(OWLAxiom axiom);

    /**
     * @return map between entities and atoms referencing them
     */
    Map<OWLEntity, Set<Atom>> getTermBasedIndex();

    /**
     * @param atom atom
     * @return true if atom is top atom
     */
    boolean isTopAtom(Atom atom);

    /**
     * @param atom atom
     * @return true if atom is bottom atom
     */
    boolean isBottomAtom(Atom atom);

    /**
     * @param atom atom
     * @return the connected component for the given atom
     */
    Set<Atom> getRelatedAtoms(Atom atom);

    /**
     * @return the set of top atoms
     */
    Set<Atom> getTopAtoms();

    /**
     * @return the set of bottom atoms
     */
    Set<Atom> getBottomAtoms();

    /**
     * @param atom atom
     * @return the set of axioms in the principal ideal for an atom
     */
    Set<OWLAxiom> getPrincipalIdeal(Atom atom);

    /**
     * @param atom atom
     * @return the signature for a principal ideal for an atom
     */
    Set<OWLEntity> getPrincipalIdealSignature(Atom atom);

    /**
     * @param atom atom
     * @param direct true if only direct dependencies should be returned
     * @return dependencies set for atom; it includes atom
     */
    Set<Atom> getDependencies(Atom atom, boolean direct);

    /**
     * @param atom atom
     * @param direct true if only direct dependents should be returned
     * @return dependents set for atom; it includes atom
     */
    Set<Atom> getDependents(Atom atom, boolean direct);

    /**
     * @param signature signature for the module to extract
     * @param useSemantics true if semantic extraction should be used
     * @param moduletype type of module
     * @return module stream
     */
    Stream<OWLAxiom> getModule(Stream<OWLEntity> signature, boolean useSemantics,
        ModuleType moduletype);

    /**
     * @return atom list
     */
    AtomList getAtomList();
}
