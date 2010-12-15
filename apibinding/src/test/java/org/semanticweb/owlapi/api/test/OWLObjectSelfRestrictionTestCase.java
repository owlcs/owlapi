package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLObjectSelfRestrictionTestCase extends AbstractOWLDataFactoryTest {

    @Override
	public void testCreation() throws Exception {
        OWLObjectProperty prop = createOWLObjectProperty();
        OWLObjectHasSelf restA = getFactory().getOWLObjectHasSelf(prop);
        assertNotNull(restA);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLObjectProperty prop = createOWLObjectProperty();
        OWLObjectHasSelf restA = getFactory().getOWLObjectHasSelf(prop);
        OWLObjectHasSelf restB = getFactory().getOWLObjectHasSelf(prop);
        assertEquals(restA, restB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        OWLObjectHasSelf restA = getFactory().getOWLObjectHasSelf(createOWLObjectProperty());
        OWLObjectHasSelf restB = getFactory().getOWLObjectHasSelf(createOWLObjectProperty());
        assertNotEquals(restA, restB);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLObjectProperty prop = createOWLObjectProperty();
        OWLObjectHasSelf restA = getFactory().getOWLObjectHasSelf(prop);
        OWLObjectHasSelf restB = getFactory().getOWLObjectHasSelf(prop);
        assertEquals(restA.hashCode(), restB.hashCode());
    }
}
