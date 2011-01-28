package org.coode.owl.rdf;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Jul-2007<br><br>
 */
public class TestAnonymousType extends AbstractRendererAndParserTestCase {


    @Override
	protected Set<OWLAxiom> getAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClassExpression desc = getDataFactory().getOWLObjectComplementOf(getDataFactory().getOWLClass(TestUtils.createIRI()));
        OWLIndividual ind = getDataFactory().getOWLNamedIndividual(TestUtils.createIRI());
        axioms.add(getDataFactory().getOWLClassAssertionAxiom(desc, ind));
        return axioms;
    }


    @Override
	protected String getClassExpression() {
        return "Anonymous type test case";
    }
}
