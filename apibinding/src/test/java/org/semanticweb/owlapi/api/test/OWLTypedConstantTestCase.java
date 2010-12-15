package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLTypedConstantTestCase extends AbstractOWLDataFactoryTest {


    @Override
	public void testCreation() throws Exception {
        OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
        OWLLiteral conA = getFactory().getOWLLiteral("3", dt);
        assertNotNull(conA);
    }


    @Override
	public void testEqualsPositive() throws Exception {
        OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
        OWLLiteral conA = getFactory().getOWLLiteral("3", dt);
        OWLLiteral conB = getFactory().getOWLLiteral("3", dt);
        assertEquals(conA, conB);
    }


    @Override
	public void testEqualsNegative() throws Exception {
        // Different datatypes - same literal
        OWLDatatype dtA = getFactory().getOWLDatatype(createIRI());
        OWLLiteral conA = getFactory().getOWLLiteral("3", dtA);
        OWLDatatype dtB = getFactory().getOWLDatatype(createIRI());
        OWLLiteral conB = getFactory().getOWLLiteral("3", dtB);
        assertNotEquals(conA, conB);
        // Different literals - same datatype
        OWLDatatype dtC = getFactory().getOWLDatatype(createIRI());
        OWLLiteral conC = getFactory().getOWLLiteral("3", dtC);
        OWLLiteral conD = getFactory().getOWLLiteral("4", dtC);
        assertNotEquals(conC, conD);
    }


    @Override
	public void testHashCode() throws Exception {
        OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
        OWLLiteral conA = getFactory().getOWLLiteral("3", dt);
        OWLLiteral conB = getFactory().getOWLLiteral("3", dt);
        assertEquals(conA.hashCode(), conB.hashCode());
    }
}
