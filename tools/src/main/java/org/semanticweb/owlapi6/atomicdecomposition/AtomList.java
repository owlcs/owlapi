package org.semanticweb.owlapi6.atomicdecomposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * atomical ontology structure
 */
public class AtomList {

    /**
     * all the atoms
     */
    private final List<OntologyAtom> atoms = new ArrayList<>();

    /**
     * @return a new atom
     */
    public OntologyAtom newAtom() {
        OntologyAtom ret = new OntologyAtom();
        ret.setId(atoms.size());
        atoms.add(ret);
        return ret;
    }

    /**
     * reduce graph of the atoms in the structure
     */
    public void reduceGraph() {
        Set<OntologyAtom> checked = new HashSet<>();
        atoms.forEach(p -> p.getAllDepAtoms(checked));
    }

    Stream<OntologyAtom> begin() {
        return atoms.stream();
    }

    /**
     * @param index index of the atom to retrieve
     * @return atom with index
     */
    public OntologyAtom get(int index) {
        return atoms.get(index);
    }

    /**
     * @return size of the structure
     */
    public int size() {
        return atoms.size();
    }
}
