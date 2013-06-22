package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertSame;

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

/** Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 24/10/2012 </p> A test case to ensure that the reference implementation
 * data factories do not create duplicate objects for distinguished values (e.g.
 * owl:Thing, rdfs:Literal etc.) */
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
        return Arrays.asList(new Object[] { noCaching }, new Object[] { withCaching });
    }

    @Test
    public void getRDFPlainLiteral() {
        OWLDatatype datatypeCall1 = dataFactory.getRDFPlainLiteral();
        OWLDatatype datatypeCall2 = dataFactory.getRDFPlainLiteral();
        assertSame(datatypeCall1, datatypeCall2);
    }

    @Test
    public void getTopDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getTopDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getTopDatatype();
        assertSame(datatypeCall1, datatypeCall2);
    }

    @Test
    public void getBooleanDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getBooleanOWLDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getBooleanOWLDatatype();
        assertSame(datatypeCall1, datatypeCall2);
    }

    @Test
    public void getDoubleDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getDoubleOWLDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getDoubleOWLDatatype();
        assertSame(datatypeCall1, datatypeCall2);
    }

    @Test
    public void getFloatDatatype() {
        OWLDatatype datatypeCall1 = dataFactory.getFloatOWLDatatype();
        OWLDatatype datatypeCall2 = dataFactory.getFloatOWLDatatype();
        assertSame(datatypeCall1, datatypeCall2);
    }

    @Test
    public void getRDFSLabel() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSLabel();
        OWLAnnotationProperty call2 = dataFactory.getRDFSLabel();
        assertSame(call1, call2);
    }

    @Test
    public void getRDFSComment() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSComment();
        OWLAnnotationProperty call2 = dataFactory.getRDFSComment();
        assertSame(call1, call2);
    }

    @Test
    public void getRDFSSeeAlso() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSSeeAlso();
        OWLAnnotationProperty call2 = dataFactory.getRDFSSeeAlso();
        assertSame(call1, call2);
    }

    @Test
    public void getRDFSIsDefinedBy() {
        OWLAnnotationProperty call1 = dataFactory.getRDFSIsDefinedBy();
        OWLAnnotationProperty call2 = dataFactory.getRDFSIsDefinedBy();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLVersionInfo() {
        OWLAnnotationProperty call1 = dataFactory.getOWLVersionInfo();
        OWLAnnotationProperty call2 = dataFactory.getOWLVersionInfo();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLBackwardCompatibleWith() {
        OWLAnnotationProperty call1 = dataFactory.getOWLBackwardCompatibleWith();
        OWLAnnotationProperty call2 = dataFactory.getOWLBackwardCompatibleWith();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLIncompatibleWith() {
        OWLAnnotationProperty call1 = dataFactory.getOWLIncompatibleWith();
        OWLAnnotationProperty call2 = dataFactory.getOWLIncompatibleWith();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLDeprecated() {
        OWLAnnotationProperty call1 = dataFactory.getOWLDeprecated();
        OWLAnnotationProperty call2 = dataFactory.getOWLDeprecated();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLThing() {
        OWLClass call1 = dataFactory.getOWLThing();
        OWLClass call2 = dataFactory.getOWLThing();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLNothing() {
        OWLClass call1 = dataFactory.getOWLNothing();
        OWLClass call2 = dataFactory.getOWLNothing();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLTopObjectProperty() {
        OWLObjectProperty call1 = dataFactory.getOWLTopObjectProperty();
        OWLObjectProperty call2 = dataFactory.getOWLTopObjectProperty();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLBottomObjectProperty() {
        OWLObjectProperty call1 = dataFactory.getOWLBottomObjectProperty();
        OWLObjectProperty call2 = dataFactory.getOWLBottomObjectProperty();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLTopDataProperty() {
        OWLDataProperty call1 = dataFactory.getOWLTopDataProperty();
        OWLDataProperty call2 = dataFactory.getOWLTopDataProperty();
        assertSame(call1, call2);
    }

    @Test
    public void getOWLBottomDataProperty() {
        OWLDataProperty call1 = dataFactory.getOWLBottomDataProperty();
        OWLDataProperty call2 = dataFactory.getOWLBottomDataProperty();
        assertSame(call1, call2);
    }
}
