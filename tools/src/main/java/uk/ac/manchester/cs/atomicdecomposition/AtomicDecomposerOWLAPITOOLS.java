package uk.ac.manchester.cs.atomicdecomposition;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapitools.decomposition.*;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import uk.ac.manchester.cs.chainsaw.FastSet;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

/** atomc decomposition implementation */
public class AtomicDecomposerOWLAPITOOLS implements AtomicDecomposition {

    Set<OWLAxiom> globalAxioms;
    Set<OWLAxiom> tautologies;
    final Multimap<OWLEntity, Atom> termBasedIndex = LinkedHashMultimap
        .create();
    List<Atom> atoms;
    Map<Atom, Integer> atomIndex = new HashMap<>();
    IdentityMultiMap<Atom, Atom> dependents = new IdentityMultiMap<>();
    IdentityMultiMap<Atom, Atom> dependencies = new IdentityMultiMap<>();
    Decomposer decomposer;
    private final ModuleType type;

    Set<OWLAxiom> asSet(Collection<AxiomWrapper> c) {
        Set<OWLAxiom> toReturn = new HashSet<>();
        for (AxiomWrapper p : c) {
            toReturn.add(p.getAxiom());
        }
        return toReturn;
    }

    /**
     * @param o
     *        o
     */
    public AtomicDecomposerOWLAPITOOLS(OWLOntology o) {
        this(AxiomSelector.selectAxioms(o), ModuleType.BOT);
    }

    /**
     * @param o
     *        o
     * @param type
     *        type
     */
    public AtomicDecomposerOWLAPITOOLS(OWLOntology o, ModuleType type) {
        this(AxiomSelector.selectAxioms(o), type);
    }

    /**
     * @param axioms
     *        axioms
     * @param type
     *        type
     */
    public AtomicDecomposerOWLAPITOOLS(List<OWLAxiom> axioms, ModuleType type) {
        this.type = type;
        decomposer = new Decomposer(AxiomSelector.wrap(axioms),
            new SyntacticLocalityChecker());
        int size = decomposer.getAOS(this.type).size();
        atoms = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Atom atom = new Atom(asSet(decomposer.getAOS().get(i)
                .getAtomAxioms()));
            atoms.add(atom);
            atomIndex.put(atom, i);
            for (OWLEntity e : atom.getSignature()) {
                termBasedIndex.put(e, atom);
            }
        }
        for (int i = 0; i < size; i++) {
            Set<OntologyAtom> dependentIndexes = decomposer.getAOS().get(i)
                .getDependencies();
            for (OntologyAtom j : dependentIndexes) {
                dependencies.put(atoms.get(i), atoms.get(j.getId()));
                dependents.put(atoms.get(j.getId()), atoms.get(i));
            }
        }
    }

    int getModuleType() {
        return type.ordinal();
    }

    @Override
    public Set<Atom> getAtoms() {
        return new HashSet<>(atoms);
    }

    @Override
    public @Nullable Atom getAtomForAxiom(OWLAxiom axiom) {
        for (int i = 0; i < atoms.size(); i++) {
            if (atoms.get(i).contains(axiom)) {
                return atoms.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean isTopAtom(Atom atom) {
        return !dependents.containsKey(atom);
    }

    @Override
    public boolean isBottomAtom(Atom atom) {
        return !dependencies.containsKey(atom);
    }

    @Override
    public Set<OWLAxiom> getPrincipalIdeal(Atom atom) {
        return asSet(getAtomModule(atomIndex.get(atom)));
    }

    @Override
    public Set<OWLEntity> getPrincipalIdealSignature(Atom atom) {
        return Collections.emptySet();
    }

    @Override
    public Set<Atom> getDependencies(Atom atom) {
        return getDependencies(atom, false);
    }

    @Override
    public Set<Atom> getDependencies(Atom atom, boolean direct) {
        return explore(atom, direct, dependencies);
    }

    @Override
    public Set<Atom> getDependents(Atom atom) {
        return getDependents(atom, false);
    }

    @Override
    public Set<Atom> getDependents(Atom atom, boolean direct) {
        return explore(atom, direct, dependents);
    }

    Set<Atom> explore(Atom atom, boolean direct,
        IdentityMultiMap<Atom, Atom> multimap) {
        if (direct) {
            Set<Atom> hashSet = new HashSet<>(multimap.get(atom));
            for (Atom a : multimap.get(atom)) {
                hashSet.removeAll(multimap.get(a));
            }
            return hashSet;
        }
        Map<Atom, Atom> toReturn = new HashMap<>();
        toReturn.put(atom, atom);
        List<Atom> toDo = new ArrayList<>();
        toDo.add(atom);
        for (int i = 0; i < toDo.size(); i++) {
            final Atom key = toDo.get(i);
            if (key != null) {
                Collection<Atom> c = multimap.get(key);
                for (Atom a : c) {
                    if (toReturn.put(a, a) == null) {
                        toDo.add(a);
                    }
                }
            }
        }
        return toReturn.keySet();
    }

    @Override
    public Set<Atom> getRelatedAtoms(Atom atom) {
        Set<Atom> s = getDependencies(atom);
        s.addAll(getDependents(atom));
        return s;
    }

    @Override
    public Set<Atom> getTopAtoms() {
        Set<Atom> keys = getAtoms();
        keys.removeAll(dependencies.getAllValues());
        return keys;
    }

    Set<Atom> asSet(Iterable<Integer> keys) {
        Set<Atom> s = new HashSet<>();
        for (int i : keys) {
            s.add(atoms.get(i));
        }
        return s;
    }

    Set<Atom> asSet(FastSet keys) {
        Set<Atom> s = new HashSet<>();
        for (int i = 0; i < keys.size(); i++) {
            s.add(atoms.get(keys.get(i)));
        }
        return s;
    }

    @Override
    public Set<Atom> getBottomAtoms() {
        Set<Atom> keys = getAtoms();
        keys.removeAll(dependents.getAllValues());
        return keys;
    }

    Atom getAtomByID(Object id) {
        return atoms.get((Integer) id);
    }

    Set<OWLAxiom> getGlobalAxioms() {
        return globalAxioms;
    }

    void setGlobalAxioms(Set<OWLAxiom> globalAxioms) {
        this.globalAxioms = globalAxioms;
    }

    @Override
    public Set<OWLAxiom> getTautologies() {
        return asSet(decomposer.getTautologies());
    }

    @Override
    public Map<OWLEntity, Set<Atom>> getTermBasedIndex() {
        Map<OWLEntity, Set<Atom>> toReturn = new HashMap<>();
        for (OWLEntity e : termBasedIndex.keySet()) {
            toReturn.put(e, new HashSet<>(termBasedIndex.get(e)));
        }
        return toReturn;
    }

    /**
     * get a set of axioms that corresponds to the module of the atom with the
     * id INDEX
     * 
     * @param index
     *        index
     * @return module at index
     */
    Collection<AxiomWrapper> getAtomModule(int index) {
        return decomposer.getAOS().get(index).getModule();
    }

    Collection<AxiomWrapper> getModule(Stream<OWLEntity> signature,
        boolean useSemantics, ModuleType moduletype) {
        return decomposer.getModule(signature, useSemantics, moduletype);
    }
}
