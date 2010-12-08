package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2008<br><br>
 */
public class NestedClassExpressionRoundTrippingTestCase extends AbstractRoundTrippingTest {


    protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("OntA");
        OWLObjectProperty prop = getOWLObjectProperty("propP");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(prop, getFactory().getOWLObjectSomeValuesFrom(prop, clsB));
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(clsA, desc);
        addAxiom(ont, ax);
        return ont;
    }
}
