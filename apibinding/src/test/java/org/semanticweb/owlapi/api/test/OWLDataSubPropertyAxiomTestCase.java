package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDataSubPropertyAxiomTestCase extends AbstractOWLBinaryOperandAxiomTestCase<OWLDataProperty, OWLDataProperty> {


    protected OWLDataProperty createLeftOperand() throws Exception {
        return createOWLDataProperty();
    }


    protected OWLDataProperty createRightOperand() throws Exception {
        return createOWLDataProperty();
    }


    protected OWLAxiom createAxiom(OWLDataProperty leftOperand, OWLDataProperty rightOperand) throws Exception {
        return getFactory().getOWLSubDataPropertyOfAxiom(leftOperand, rightOperand);
    }
}
