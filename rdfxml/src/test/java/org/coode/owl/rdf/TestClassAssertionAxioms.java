package org.coode.owl.rdf;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-May-2007<br><br>
 */
public class TestClassAssertionAxioms extends AbstractRendererAndParserTestCase {

    @Override
	protected String getClassExpression() {
        return "Class assertion axioms test case";
    }


    @Override
	protected Set<OWLAxiom> getAxioms() {
        OWLIndividual ind = createIndividual();
        OWLClass cls = createClass();
        OWLAxiom ax = getDataFactory().getOWLClassAssertionAxiom(cls, ind);
        return Collections.singleton(ax);
    }
}
