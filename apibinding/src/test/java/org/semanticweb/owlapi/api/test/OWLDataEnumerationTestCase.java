package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDataEnumerationTestCase extends AbstractOWLNaryOperandsObjectTestCase<OWLLiteral> {


    @Override
	protected OWLObject createObject(Set<OWLLiteral> operands) throws Exception {
        return getFactory().getOWLDataOneOf(operands);
    }


    @Override
	protected OWLLiteral createOperand() throws Exception {
        return createOWLLiteral();
    }
}
