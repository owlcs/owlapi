package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractOWLRestrictionTestCase<P extends OWLPropertyExpression> extends AbstractOWLDataFactoryTest {


    protected abstract OWLRestriction createRestriction(P prop) throws Exception;

    protected abstract P createProperty() throws Exception;

    public void testPropertyGetter() throws Exception {
        P prop = createProperty();
        OWLRestriction rest = createRestriction(prop);
        assertEquals(rest.getProperty(), prop);
    }

}
