package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLDataMaxCardinalityTestCase extends AbstractOWLDataCardinalityRestrictionTestCase {


    protected OWLDataCardinalityRestriction createRestriction(OWLDataProperty prop, int cardinality) throws Exception {
        return getFactory().getOWLDataMaxCardinality(cardinality, prop);
    }


    protected OWLDataCardinalityRestriction createRestriction(OWLDataProperty prop, int cardinality, OWLDataRange dataRange) throws Exception {
        return getFactory().getOWLDataMaxCardinality(cardinality, prop, dataRange);
    }
}
