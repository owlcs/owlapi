package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLIndividualTypeAxiomTestCase extends AbstractOWLBinaryOperandAxiomTestCase<OWLIndividual, OWLClassExpression> {


    protected OWLIndividual createLeftOperand() throws Exception {
        return createOWLIndividual();
    }


    protected OWLClassExpression createRightOperand() throws Exception {
        return createOWLClass();
    }


    protected OWLAxiom createAxiom(OWLIndividual leftOperand, OWLClassExpression rightOperand) throws Exception {
        return getFactory().getOWLClassAssertionAxiom(rightOperand, leftOperand);
    }
}
