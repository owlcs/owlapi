package org.coode.owl.rdf;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Aug-2007<br><br>
 */
public class TestQCR extends AbstractRendererAndParserTestCase {


    @Override
	protected Set<OWLAxiom> getAxioms() {
        OWLClass clsA = getDataFactory().getOWLClass(TestUtils.createIRI());
        OWLClass clsB = getDataFactory().getOWLClass(TestUtils.createIRI());
        OWLClass clsC = getDataFactory().getOWLClass(TestUtils.createIRI());
        //Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(TestUtils.createIRI());
        OWLClassExpression filler = getDataFactory().getOWLObjectIntersectionOf(CollectionFactory.createSet(clsB, clsC));
        OWLCardinalityRestriction<?, ?, ?> restriction = getDataFactory().getOWLObjectMinCardinality(3, prop, filler);
        assertTrue(restriction.isQualified());
        OWLAxiom ax = getDataFactory().getOWLSubClassOfAxiom(clsA, restriction);
        return Collections.singleton(ax);
    }


    @Override
	protected String getClassExpression() {
        return "Qualified Cardinality";
    }
}
