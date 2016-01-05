package org.semanticweb.owlapitools.decomposition;

import static org.semanticweb.owlapi.model.AxiomType.LOGICAL_AXIOMS_AND_DECLARATIONS_TYPES;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * A filter for axioms
 * 
 * @author ignazio
 */
public class AxiomSelector {

    /**
     * @param o
     *        the ontology to filter
     * @return list of declarations and logical axioms
     */
    public static List<OWLAxiom> selectAxioms(OWLOntology o) {
        return asList(
            LOGICAL_AXIOMS_AND_DECLARATIONS_TYPES.stream().flatMap(type -> o.axioms(type, Imports.INCLUDED)));
    }

    /**
     * @param o
     *        axioms to wrap
     * @return axioms wrapped as AxiomWrapper
     */
    public static List<AxiomWrapper> wrap(List<OWLAxiom> o) {
        List<AxiomWrapper> axioms = new ArrayList<>();
        for (OWLAxiom ax : o) {
            axioms.add(new AxiomWrapper(ax));
        }
        return axioms;
    }
}
