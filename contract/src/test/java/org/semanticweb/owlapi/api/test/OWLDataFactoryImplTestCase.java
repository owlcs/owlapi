package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

/**
 * A test case to ensure that the reference implementation data factories do not
 * create duplicate objects for distinguished values (e.g. owl:Thing,
 * rdfs:Literal etc.)
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 24/10/2012
 */
@RunWith(value = Parameterized.class)
@SuppressWarnings("javadoc")
public class OWLDataFactoryImplTestCase {

    private final OWLDataFactoryImpl dataFactory;

    public OWLDataFactoryImplTestCase(OWLDataFactoryImpl dataFactory) {
        this.dataFactory = dataFactory;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        OWLDataFactoryImpl noCaching = new OWLDataFactoryImpl(false, false);
        OWLDataFactoryImpl withCaching = new OWLDataFactoryImpl(true, false);
        return Arrays.asList(new Object[] { noCaching },
                new Object[] { withCaching });
    }

    @Test
    public void getRDFPlainLiteral() {
        OWLDatatype datatypeCall1 = dataFactory.getRDFPlainLiteral();
        OWLDatatype datatypeCall2 = dataFactory.getRDFPlainLiteral();
        assertTrue(datatypeCall1 == datatypeCall2);
    }

    @Test
    public void getTopDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getTopDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getTopDatatype();
        assertTrue(datatypeCall1 == datatypeCall2);
    }

    @Test
    public void getBooleanDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getBooleanOWLDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getBooleanOWLDatatype();
        assertTrue(datatypeCall1 == datatypeCall2);
    }

    @Test
    public void getDoubleDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getDoubleOWLDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getDoubleOWLDatatype();
        assertTrue(datatypeCall1 == datatypeCall2);
    }

    @Test
    public void getFloatDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getFloatOWLDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getFloatOWLDatatype();
        assertTrue(datatypeCall1 == datatypeCall2);
    }

    @Test
    public void getRDFSLabel() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSLabel();
        OWLAnnotationProperty call2 = dataFactory.getRDFSLabel();
        assertTrue(call1 == call2);
    }

    @Test
    public void getRDFSComment() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSComment();
        OWLAnnotationProperty call2 = dataFactory.getRDFSComment();
        assertTrue(call1 == call2);
    }

    @Test
    public void getRDFSSeeAlso() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSSeeAlso();
        OWLAnnotationProperty call2 = dataFactory.getRDFSSeeAlso();
        assertTrue(call1 == call2);
    }

    @Test
    public void getRDFSIsDefinedBy() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSIsDefinedBy();
        OWLAnnotationProperty call2 = dataFactory.getRDFSIsDefinedBy();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLVersionInfo() {
        OWLAnnotationProperty call1 = dataFactory.getOWLVersionInfo();
        OWLAnnotationProperty call2 = dataFactory.getOWLVersionInfo();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLBackwardCompatibleWith() {
        OWLAnnotationProperty call1 = dataFactory
                .getOWLBackwardCompatibleWith();
        OWLAnnotationProperty call2 = dataFactory
                .getOWLBackwardCompatibleWith();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLIncompatibleWith() {
        OWLAnnotationProperty call1 = dataFactory.getOWLIncompatibleWith();
        OWLAnnotationProperty call2 = dataFactory.getOWLIncompatibleWith();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLDeprecated() {
        OWLAnnotationProperty call1 = dataFactory.getOWLDeprecated();
        OWLAnnotationProperty call2 = dataFactory.getOWLDeprecated();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLThing() {
        OWLClass call1 = dataFactory.getOWLThing();
        OWLClass call2 = dataFactory.getOWLThing();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLNothing() {
        OWLClass call1 = dataFactory.getOWLNothing();
        OWLClass call2 = dataFactory.getOWLNothing();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLTopObjectProperty() {
        OWLObjectProperty call1 = dataFactory.getOWLTopObjectProperty();
        OWLObjectProperty call2 = dataFactory.getOWLTopObjectProperty();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLBottomObjectProperty() {
        OWLObjectProperty call1 = dataFactory.getOWLBottomObjectProperty();
        OWLObjectProperty call2 = dataFactory.getOWLBottomObjectProperty();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLTopDataProperty() {
        OWLDataProperty call1 = dataFactory.getOWLTopDataProperty();
        OWLDataProperty call2 = dataFactory.getOWLTopDataProperty();
        assertTrue(call1 == call2);
    }

    @Test
    public void getOWLBottomDataProperty() {
        OWLDataProperty call1 = dataFactory.getOWLBottomDataProperty();
        OWLDataProperty call2 = dataFactory.getOWLBottomDataProperty();
        assertTrue(call1 == call2);
    }
}
