package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class TypedLiteralsTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        addAxiomForLiteral(getFactory().getOWLLiteral(3), axioms);
        addAxiomForLiteral(getFactory().getOWLLiteral(33.3), axioms);
        addAxiomForLiteral(getFactory().getOWLLiteral(true), axioms);
        addAxiomForLiteral(getFactory().getOWLLiteral(33.3f), axioms);
        addAxiomForLiteral(getFactory().getOWLLiteral("33.3"), axioms);
        return axioms;
    }

    private void addAxiomForLiteral(OWLLiteral lit, Set<OWLAxiom> axioms) {
        OWLDataProperty prop = getOWLDataProperty("p");
        OWLNamedIndividual ind = getOWLIndividual("i");
        axioms.add(getFactory().getOWLDataPropertyAssertionAxiom(
                prop,
                ind,
                lit
        ));
    }
}
