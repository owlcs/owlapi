package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
@SuppressWarnings("rawtypes")
public class OWLDataValueRestrictionTestCase extends AbstractOWLRestrictionWithFillerTestCase<OWLDataProperty, OWLLiteral> {

    @Override
	protected OWLRestriction createRestriction(OWLDataProperty prop, OWLLiteral filler) throws Exception {
        return getFactory().getOWLDataHasValue(prop, filler);
    }


    @Override
	protected OWLDataProperty createProperty() throws OWLException {
        return createOWLDataProperty();
    }


    @Override
	protected OWLLiteral createFiller() throws OWLException {
        return createOWLLiteral();
    }
}
