package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLObjectValueRestrictionTestCase extends AbstractOWLRestrictionWithFillerTestCase<OWLObjectProperty, OWLIndividual> {

    protected OWLRestriction createRestriction(OWLObjectProperty prop, OWLIndividual filler) throws Exception {
        return getFactory().getOWLObjectHasValue(prop, filler);
    }


    protected OWLObjectProperty createProperty() throws Exception {
        return createOWLObjectProperty();
    }


    protected OWLIndividual createFiller() throws Exception {
        return createOWLIndividual();
    }
}
