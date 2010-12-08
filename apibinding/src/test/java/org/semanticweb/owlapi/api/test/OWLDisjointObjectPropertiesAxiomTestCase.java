package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDisjointObjectPropertiesAxiomTestCase extends AbstractOWLNaryOperandsObjectTestCase<OWLObjectProperty> {


    protected OWLObject createObject(Set<OWLObjectProperty> operands) throws Exception {
        return getFactory().getOWLDisjointObjectPropertiesAxiom(operands);
    }


    protected OWLObjectProperty createOperand() throws Exception {
        return createOWLObjectProperty();
    }
}
