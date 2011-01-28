package org.coode.owl.rdf;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-May-2007<br><br>
 */
public class TestNegativeObjectPropertyAssertionAxiom extends AbstractRendererAndParserTestCase {

    @Override
	protected String getClassExpression() {
        return "Negative object property assertion test case";
    }


    @Override
	protected Set<OWLAxiom> getAxioms() {
        OWLIndividual subj = createIndividual();
        OWLObjectProperty prop = createObjectProperty();
        OWLIndividual obj = createIndividual();
        OWLAxiom ax = getDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(prop, subj, obj);
        return Collections.singleton(ax);
    }
}
