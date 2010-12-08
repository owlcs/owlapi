package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLObjectSomeRestrictionTestCase extends AbstractOWLRestrictionWithFillerTestCase<OWLObjectProperty, OWLClassExpression> {


    protected OWLRestriction createRestriction(OWLObjectProperty prop, OWLClassExpression filler) throws Exception {
        return getFactory().getOWLObjectSomeValuesFrom(prop, filler);
    }


    protected OWLObjectProperty createProperty() throws Exception {
        return createOWLObjectProperty();
    }


    protected OWLClassExpression createFiller() throws Exception {
        return createOWLClass();
    }
}
