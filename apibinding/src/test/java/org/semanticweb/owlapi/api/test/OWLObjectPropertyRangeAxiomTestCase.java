package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLObjectPropertyRangeAxiomTestCase extends AbstractOWLBinaryOperandAxiomTestCase<OWLObjectProperty, OWLClassExpression> {


    protected OWLObjectProperty createLeftOperand() throws Exception {
        return createOWLObjectProperty();
    }


    protected OWLClassExpression createRightOperand() throws Exception {
        return createOWLClass();
    }


    protected OWLAxiom createAxiom(OWLObjectProperty leftOperand, OWLClassExpression rightOperand) throws Exception {
        return getFactory().getOWLObjectPropertyRangeAxiom(leftOperand, rightOperand);
    }
}
