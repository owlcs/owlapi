package org.coode.owl.rdf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-May-2007<br><br>
 */
public class TestEquivalentClasses extends AbstractRendererAndParserTestCase {

    @Override
	protected String getClassExpression() {
        return "Equivalent classes axioms test case";
    }


    @Override
	protected Set<OWLAxiom> getAxioms() {
        OWLClass clsA = getManager().getOWLDataFactory().getOWLClass(TestUtils.createIRI());
        OWLObjectProperty prop = getManager().getOWLDataFactory().getOWLObjectProperty(TestUtils.createIRI());
        OWLClassExpression descA = getManager().getOWLDataFactory().getOWLObjectSomeValuesFrom(prop,
                getManager().getOWLDataFactory().getOWLThing());
        Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>();
        classExpressions.add(clsA);
        classExpressions.add(descA);
        OWLAxiom ax = getManager().getOWLDataFactory().getOWLEquivalentClassesAxiom(classExpressions);
        return Collections.singleton(ax);
    }
}
