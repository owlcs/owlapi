package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.metrics.AxiomCountMetric;
import org.semanticweb.owlapi.metrics.AxiomTypeCountMetricFactory;
import org.semanticweb.owlapi.metrics.DLExpressivity;
import org.semanticweb.owlapi.metrics.DoubleValuedMetric;
import org.semanticweb.owlapi.metrics.GCICount;
import org.semanticweb.owlapi.metrics.HiddenGCICount;
import org.semanticweb.owlapi.metrics.ImportClosureSize;
import org.semanticweb.owlapi.metrics.IntegerValuedMetric;
import org.semanticweb.owlapi.metrics.LogicalAxiomCount;
import org.semanticweb.owlapi.metrics.MaximumNumberOfNamedSuperclasses;
import org.semanticweb.owlapi.metrics.NumberOfClassesWithMultipleInheritance;
import org.semanticweb.owlapi.metrics.OWLMetric;
import org.semanticweb.owlapi.metrics.OWLMetricManager;
import org.semanticweb.owlapi.metrics.ObjectCountMetric;
import org.semanticweb.owlapi.metrics.ReferencedClassCount;
import org.semanticweb.owlapi.metrics.ReferencedDataPropertyCount;
import org.semanticweb.owlapi.metrics.ReferencedIndividualCount;
import org.semanticweb.owlapi.metrics.ReferencedObjectPropertyCount;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractMetricsTest {
    @Test
    public void shouldTestAbstractOWLMetric() throws Exception {
        AbstractOWLMetric<Object> testSubject0 = new AbstractOWLMetric<Object>(
                Utils.getMockManager()) {
            @Override
            public String getName() {
                return null;
            }

            @Override
            protected Object recomputeMetric() {
                return null;
            }

            @Override
            protected boolean isMetricInvalidated(
                    List<? extends OWLOntologyChange> changes) {
                return false;
            }

            @Override
            protected void disposeMetric() {}
        };
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result2 = testSubject0.getOntologies();
        OWLOntology result3 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(OWLOntologyChange.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }



    @Test
    public void shouldTestAxiomCountMetric() throws Exception {
        AxiomCountMetric testSubject0 = new AxiomCountMetric(Utils.getMockManager()) {
            @Override
            protected String getObjectTypeName() {
                return null;
            }

            @Override
            protected Set<? extends OWLAxiom> getObjects(OWLOntology ont) {
                return null;
            }
        };
    }

    @Test
    public void shouldTestAxiomTypeCountMetricFactory() throws Exception {
        AxiomTypeCountMetricFactory testSubject0 = new AxiomTypeCountMetricFactory();
        Set<OWLMetric<?>> result0 = AxiomTypeCountMetricFactory.createMetrics(Utils
                .getMockManager());
        String result1 = testSubject0.toString();
    }


    @Test
    public void shouldTestDLExpressivity() throws Exception {
        DLExpressivity testSubject0 = new DLExpressivity(Utils.getMockManager());
    }

    @Test
    public void shouldTestDoubleValuedMetric() throws Exception {
        DoubleValuedMetric testSubject0 = new DoubleValuedMetric(Utils.getMockManager()) {
            @Override
            public String getName() {
                return null;
            }

            @Override
            protected Double recomputeMetric() {
                return null;
            }

            @Override
            protected boolean isMetricInvalidated(
                    List<? extends OWLOntologyChange> changes) {
                return false;
            }

            @Override
            protected void disposeMetric() {}
        };
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result2 = testSubject0.getOntologies();
        OWLOntology result3 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(OWLOntologyChange.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Test
    public void shouldTestGCICount() throws Exception {
        GCICount testSubject0 = new GCICount(Utils.getMockManager());
    }

    @Test
    public void shouldTestHiddenGCICount() throws Exception {
        HiddenGCICount testSubject0 = new HiddenGCICount(Utils.getMockManager());
    }

    @Test
    public void shouldTestImportClosureSize() throws Exception {
        ImportClosureSize testSubject0 = new ImportClosureSize(Utils.getMockManager());
    }

    @Test
    public void shouldTestIntegerValuedMetric() throws Exception {
        IntegerValuedMetric testSubject0 = new IntegerValuedMetric(Utils.getMockManager()) {
            @Override
            public String getName() {
                return null;
            }

            @Override
            protected Integer recomputeMetric() {
                return null;
            }

            @Override
            protected boolean isMetricInvalidated(
                    List<? extends OWLOntologyChange> changes) {
                return false;
            }

            @Override
            protected void disposeMetric() {}
        };
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result2 = testSubject0.getOntologies();
        OWLOntology result3 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(OWLOntologyChange.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Test
    public void shouldTestLogicalAxiomCount() throws Exception {
        LogicalAxiomCount testSubject0 = new LogicalAxiomCount(Utils.getMockManager());
    }

    @Test
    public void shouldTestMaximumNumberOfNamedSuperclasses() throws Exception {
        MaximumNumberOfNamedSuperclasses testSubject0 = new MaximumNumberOfNamedSuperclasses(
                Utils.getMockManager());
    }

    @Test
    public void shouldTestNumberOfClassesWithMultipleInheritance() throws Exception {
        NumberOfClassesWithMultipleInheritance testSubject0 = new NumberOfClassesWithMultipleInheritance(
                Factory.getManager());
    }

    @Test
    public void shouldTestObjectCountMetric() throws Exception {
        ObjectCountMetric<Object> testSubject0 = new ObjectCountMetric<Object>(
                Factory.getManager()) {
            @Override
            protected String getObjectTypeName() {
                return null;
            }

            @Override
            protected Set<Object> getObjects(OWLOntology ont) {
                return null;
            }
        };
    }

    @Test
    public void shouldTestInterfaceOWLMetric() throws Exception {
        OWLMetric<Object> testSubject0 = mock(OWLMetric.class);
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.getValue();
        testSubject0.dispose();
        OWLOntology result2 = testSubject0.getOntology();
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result3 = testSubject0.getManager();
        boolean result4 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Test
    public void shouldTestOWLMetricManager() throws Exception {
        OWLMetricManager testSubject0 = new OWLMetricManager(
                new ArrayList<OWLMetric<?>>());
        String result0 = testSubject0.toString();
        testSubject0.setOntology(Utils.getMockOntology());
        List<OWLMetric<?>> result1 = testSubject0.getMetrics();
    }

    @Test
    public void shouldTestReferencedClassCount() throws Exception {
        ReferencedClassCount testSubject0 = new ReferencedClassCount(
                Utils.getMockManager());
    }

    @Test
    public void shouldTestReferencedDataPropertyCount() throws Exception {
        ReferencedDataPropertyCount testSubject0 = new ReferencedDataPropertyCount(
                Utils.getMockManager());
    }

    @Test
    public void shouldTestReferencedIndividualCount() throws Exception {
        ReferencedIndividualCount testSubject0 = new ReferencedIndividualCount(
                Utils.getMockManager());
    }

    @Test
    public void shouldTestReferencedObjectPropertyCount() throws Exception {
        ReferencedObjectPropertyCount testSubject0 = new ReferencedObjectPropertyCount(
                Utils.getMockManager());
    }
}
