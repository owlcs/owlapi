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
public class OWLAndTestCase extends AbstractOWLNaryOperandsObjectTestCase<OWLClassExpression> {

    @Override
	protected OWLObject createObject(Set<OWLClassExpression> objects) throws Exception {
        return getFactory().getOWLObjectIntersectionOf(objects);
    }


    @Override
	protected OWLClassExpression createOperand() throws Exception {
        return createOWLClass();
    }
}
