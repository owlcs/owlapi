package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDataSomeRestrictionTestCase extends AbstractOWLRestrictionWithFillerTestCase<OWLDataProperty, OWLDataRange> {


    protected OWLRestriction createRestriction(OWLDataProperty prop, OWLDataRange filler) throws Exception {
        return getFactory().getOWLDataSomeValuesFrom(prop, filler);
    }


    protected OWLDataProperty createProperty() throws OWLException {
        return createOWLDataProperty();
    }


    protected OWLDataRange createFiller() throws OWLException {
        return createOWLDatatype();
    }

}
