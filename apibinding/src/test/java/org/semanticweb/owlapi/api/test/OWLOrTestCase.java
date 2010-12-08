package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLOrTestCase extends AbstractOWLNaryOperandsObjectTestCase<OWLClassExpression> {


    protected OWLObject createObject(Set<OWLClassExpression> operands) throws Exception {
        return getFactory().getOWLObjectUnionOf(operands);
    }


    protected OWLClassExpression createOperand() throws Exception {
        return createOWLClass();
    }
}
