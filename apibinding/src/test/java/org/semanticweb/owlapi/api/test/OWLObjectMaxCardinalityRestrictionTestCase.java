package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLObjectMaxCardinalityRestrictionTestCase extends AbstractOWLObjectCardinalityRestrictionTestCase {


    @Override
	protected OWLObjectCardinalityRestriction createRestriction(OWLObjectProperty prop, int cardinality) throws Exception {
        return getFactory().getOWLObjectMaxCardinality(cardinality, prop);
    }


    @Override
	protected OWLObjectCardinalityRestriction createRestriction(OWLObjectProperty prop, int cardinality,
                                                                OWLClassExpression classExpression) throws Exception {
        return getFactory().getOWLObjectMaxCardinality(cardinality, prop, classExpression);
    }
}
