package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLObjectSubPropertyAxiomTestCase extends AbstractOWLBinaryOperandAxiomTestCase<OWLObjectProperty, OWLObjectProperty> {

    protected OWLObjectProperty createLeftOperand() throws Exception {
        return createOWLObjectProperty();
    }


    protected OWLObjectProperty createRightOperand() throws Exception {
        return createOWLObjectProperty();
    }


    protected OWLAxiom createAxiom(OWLObjectProperty leftOperand, OWLObjectProperty rightOperand) throws Exception {
        return getFactory().getOWLSubObjectPropertyOfAxiom(leftOperand, rightOperand);
    }
}
