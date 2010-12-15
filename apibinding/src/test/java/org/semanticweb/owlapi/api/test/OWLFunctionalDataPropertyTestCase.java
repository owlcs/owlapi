package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLFunctionalDataPropertyTestCase extends AbstractOWLPropertyCharacteristicTestCase<OWLDataProperty> {


    @Override
	protected OWLDataProperty createProperty() throws Exception {
        return createOWLDataProperty();
    }


    @Override
	protected OWLPropertyAxiom createOWLPropertyAxiom(OWLDataProperty property) throws OWLException {
        return getFactory().getOWLFunctionalDataPropertyAxiom(property);
    }
}
