package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplBoolean;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplDouble;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplFloat;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplInteger;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplNoCompression;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 26/11/2012
 */
@SuppressWarnings("javadoc")
public class HashCodeTestCase {

    @Test
    public void testSetContainsInt() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(
                OWL2Datatype.XSD_INTEGER.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("3", null,
                datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("3", null,
                datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplInteger(3, datatype);
        assertEquals(litNoComp, litIntImpl);
        Set<OWLLiteral> lncset = new HashSet<OWLLiteral>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }

    @Test
    public void testSetContainsDouble() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(
                OWL2Datatype.XSD_DOUBLE.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("3.0", null,
                datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("3.0", null,
                datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplDouble(3.0D, datatype);
        assertEquals(litNoComp, litIntImpl);
        Set<OWLLiteral> lncset = new HashSet<OWLLiteral>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }

    @Test
    public void testSetContainsFloat() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(
                OWL2Datatype.XSD_FLOAT.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("3.0", null,
                datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("3.0", null,
                datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplFloat(3.0F, datatype);
        assertEquals(litNoComp, litIntImpl);
        Set<OWLLiteral> lncset = new HashSet<OWLLiteral>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }

    @Test
    public void testSetContainsBoolean() {
        OWLDatatypeImpl datatype = new OWLDatatypeImpl(
                OWL2Datatype.XSD_BOOLEAN.getIRI());
        OWLLiteral litNoComp = new OWLLiteralImplNoCompression("true", null,
                datatype);
        OWLLiteral litNoComp2 = new OWLLiteralImplNoCompression("true", null,
                datatype);
        OWLLiteral litIntImpl = new OWLLiteralImplBoolean(true);
        assertEquals(litNoComp, litIntImpl);
        Set<OWLLiteral> lncset = new HashSet<OWLLiteral>();
        lncset.add(litNoComp);
        assertTrue(lncset.contains(litNoComp2));
        assertTrue(lncset.contains(litIntImpl));
    }
}
