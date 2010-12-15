package org.coode.owl.rdf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-May-2007<br><br>
 */
public class TestDifferentIndividualsAxiom extends AbstractRendererAndParserTestCase {

    @Override
	protected String getClassExpression() {
        return "Different individuals axioms test case";
    }


    @Override
	protected Set<OWLAxiom> getAxioms() {
        Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();
        for (int i = 0; i < 5; i++) {
            individuals.add(createIndividual());
        }
        OWLAxiom ax = getDataFactory().getOWLDifferentIndividualsAxiom(individuals);
        return Collections.singleton(ax);
    }
}
