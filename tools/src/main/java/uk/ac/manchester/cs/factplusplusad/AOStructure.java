package uk.ac.manchester.cs.factplusplusad;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapitools.decomposition.OntologyAtom;

/**
 * atomical ontology structure
 */
class AOStructure {

    List<OntologyAtom> atoms;

    /**
     * @return create a new atom and get a pointer to it
     */
    OntologyAtom newAtom() {
        OntologyAtom ret = new OntologyAtom();
        ret.setId(atoms.size());
        atoms.add(ret);
        return ret;
    }

    /**
     * reduce graph of the atoms in the structure
     */
    void reduceGraph() {
        Set<OntologyAtom> checked = new HashSet<>();
        atoms.forEach(p -> p.getAllDepAtoms(checked));
    }

    Stream<OntologyAtom> begin() {
        return atoms.stream();
    }

    /**
     * @param i index
     * @return RW atom by its index
     */
    OntologyAtom get(int i) {
        return atoms.get(i);
    }

    /**
     * @return size of the structure
     */
    int size() {
        return atoms.size();
    }
}
