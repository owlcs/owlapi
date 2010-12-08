package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2008<br><br>
 */
public class AnonymousRootRoundTrippingTestCase extends AbstractRoundTrippingTest {


    protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("OntA");
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        for (int i = 0; i < 10; i++) {
            inds.add(getOWLIndividual("Ind" + i));
        }
        addAxiom(ont, getFactory().getOWLDifferentIndividualsAxiom(inds));
        return ont;
    }
}
