package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLUntypedConstantTestCase extends AbstractOWLDataFactoryTest {

    @Override
	public void testCreation() throws Exception {
        OWLLiteral conB = getFactory().getOWLLiteral("TEST", "LANG");
        assertNotNull(conB);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLLiteral conC = getFactory().getOWLLiteral("TEST", "LANG");
        OWLLiteral conD = getFactory().getOWLLiteral("TEST", "LANG");
        assertEquals(conC, conD);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        OWLLiteral conC = getFactory().getOWLLiteral("TEST", "LANG");
        OWLLiteral conD = getFactory().getOWLLiteral("TEST", "OTHER_LANG");
        assertNotEquals(conC, conD);
        OWLLiteral conE = getFactory().getOWLLiteral("TEST", "LANG");
        OWLLiteral conF = getFactory().getOWLLiteral("OTHER", "LANG");
        assertNotEquals(conE, conF);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLLiteral conA = getFactory().getOWLLiteral("TEST", "LANG");
        OWLLiteral conB = getFactory().getOWLLiteral("TEST", "LANG");
        assertEquals(conA.hashCode(), conB.hashCode());
    }
}
