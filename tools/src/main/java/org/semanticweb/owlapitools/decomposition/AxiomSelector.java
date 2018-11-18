package org.semanticweb.owlapitools.decomposition;

import static org.semanticweb.owlapi.model.AxiomType.LOGICAL_AXIOMS_AND_DECLARATIONS_TYPES;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * A filter for axioms
 *
 * @author ignazio
 */
public class AxiomSelector {

    private AxiomSelector() {}

    /**
     * @param o the ontology to filter
     * @return list of declarations and logical axioms
     */
    public static List<OWLAxiom> selectAxioms(OWLOntology o) {
        return selectAxioms(o, false);
    }

    /**
     * @param o the ontology to filter
     * @return list of declarations and logical axioms
     * @param excludeAssertions true if assertions should be excluded
     */
    public static List<OWLAxiom> selectAxioms(OWLOntology o, boolean excludeAssertions) {
        Stream<AxiomType<?>> types = LOGICAL_AXIOMS_AND_DECLARATIONS_TYPES.stream();
        if (excludeAssertions) {
            types = types.filter(x -> !AxiomType.ABoxAxiomTypes.contains(x));
        }
        return asList(types.flatMap(type -> o.axioms(type, Imports.INCLUDED)));
    }

    /**
     * @param o axioms to wrap
     * @return axioms wrapped as AxiomWrapper
     */
    public static List<AxiomWrapper> wrap(List<OWLAxiom> o) {
        return asList(o.stream().map(AxiomWrapper::new));
    }
}
