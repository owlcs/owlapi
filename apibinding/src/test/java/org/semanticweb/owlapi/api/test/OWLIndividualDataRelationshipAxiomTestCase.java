package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLIndividualDataRelationshipAxiomTestCase extends AbstractOWLIndividualRelationshipAxiomTestCase<OWLDataProperty, OWLLiteral> {


    protected OWLDataProperty createProperty() throws Exception {
        return createOWLDataProperty();
    }


    protected OWLLiteral createObject() throws Exception {
        return createOWLLiteral();
    }


    protected OWLIndividualAxiom createAxiom(OWLIndividual subject, OWLDataProperty property, OWLLiteral object) throws OWLException {
        return getFactory().getOWLDataPropertyAssertionAxiom(property, subject, object);
    }
}
