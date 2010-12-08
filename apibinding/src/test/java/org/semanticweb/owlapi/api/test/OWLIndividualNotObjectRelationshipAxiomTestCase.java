package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLIndividualNotObjectRelationshipAxiomTestCase extends AbstractOWLIndividualRelationshipAxiomTestCase<OWLObjectProperty, OWLIndividual> {


    protected OWLObjectProperty createProperty() throws Exception {
        return createOWLObjectProperty();
    }


    protected OWLIndividual createObject() throws Exception {
        return createOWLIndividual();
    }


    protected OWLIndividualAxiom createAxiom(OWLIndividual subject, OWLObjectProperty property,
                                             OWLIndividual object) throws OWLException {
        return getFactory().getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object);
    }
}
