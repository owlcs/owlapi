package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDataPropertyRangeAxiomTestCase extends AbstractOWLBinaryOperandAxiomTestCase<OWLDataProperty, OWLDataRange> {

    @Override
	protected OWLDataProperty createLeftOperand() throws Exception {
        return createOWLDataProperty();
    }


    @Override
	protected OWLDataRange createRightOperand() throws Exception {
        return createOWLDatatype();
    }


    @Override
	protected OWLAxiom createAxiom(OWLDataProperty leftOperand, OWLDataRange rightOperand) throws Exception {
        return getFactory().getOWLDataPropertyRangeAxiom(leftOperand, rightOperand);
    }
}
