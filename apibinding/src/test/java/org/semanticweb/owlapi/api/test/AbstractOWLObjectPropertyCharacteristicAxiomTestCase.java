package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public abstract class AbstractOWLObjectPropertyCharacteristicAxiomTestCase extends AbstractOWLPropertyCharacteristicTestCase<OWLObjectProperty> {


    @Override
	protected OWLObjectProperty createProperty() throws Exception {
        return createOWLObjectProperty();
    }
}
