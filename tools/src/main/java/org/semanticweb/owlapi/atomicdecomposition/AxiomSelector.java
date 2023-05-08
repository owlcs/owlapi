package org.semanticweb.owlapi.atomicdecomposition;

import static org.semanticweb.owlapi.model.AxiomType.logicalAxiomsAndDeclarationsTypes;

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
        Stream<AxiomType<?>> types = logicalAxiomsAndDeclarationsTypes().stream();
        if (excludeAssertions) {
            types = types.filter(x -> !AxiomType.aboxAxiomTypes().contains(x));
        }
        Stream<OWLAxiom> axioms = types.flatMap(type -> o.axioms(type, Imports.INCLUDED));
        return axioms.toList();
    }

    /**
     * @param o axioms to wrap
     * @return axioms wrapped as AxiomWrapper
     */
    public static List<AxiomWrapper> wrap(List<OWLAxiom> o) {
        return o.stream().map(AxiomWrapper::new).toList();
    }
}
