package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.metrics.AbstractOWLMetric;
import org.semanticweb.owlapi.metrics.AverageAssertedNamedSuperclassCount;
import org.semanticweb.owlapi.metrics.AxiomCount;
import org.semanticweb.owlapi.metrics.AxiomCountMetric;
import org.semanticweb.owlapi.metrics.AxiomTypeCountMetricFactory;
import org.semanticweb.owlapi.metrics.AxiomTypeMetric;
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
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractMetricsTest {
    @Test
    public void shouldTestAbstractOWLMetric() throws OWLException {
        AbstractOWLMetric<Object> testSubject0 = new AbstractOWLMetric<Object>(
                Utils.getMockOntology()) {
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
                    List<? extends OWLOntologyChange<?>> changes) {
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
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Ignore
    @Test
    public void shouldTestAverageAssertedNamedSuperclassCount() throws OWLException {
        AverageAssertedNamedSuperclassCount testSubject0 = new AverageAssertedNamedSuperclassCount(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Double result1 = testSubject0.recomputeMetric();
        Object result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestAxiomCount() throws OWLException {
        AxiomCount testSubject0 = new AxiomCount(Utils.getMockOntology());
        String result0 = testSubject0.getName();
        String result1 = testSubject0.toString();
        Object result2 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result3 = testSubject0.getOntologies();
        OWLOntology result4 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result5 = testSubject0.getManager();
        boolean result6 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestAxiomCountMetric() throws OWLException {
        AxiomCountMetric testSubject0 = new AxiomCountMetric(Utils.getMockOntology()) {
            @Override
            protected String getObjectTypeName() {
                return null;
            }

            @Override
            protected Set<? extends OWLAxiom> getObjects(OWLOntology ont) {
                return null;
            }
        };
        Set<? extends OWLAxiom> result0 = testSubject0.getAxioms();
        String result1 = testSubject0.getName();
        Object result2 = testSubject0.recomputeMetric();
        Integer result3 = testSubject0.recomputeMetric();
        String result4 = testSubject0.toString();
        Object result5 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result6 = testSubject0.getOntologies();
        OWLOntology result7 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result8 = testSubject0.getManager();
        boolean result9 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestAxiomTypeCountMetricFactory() throws OWLException {
        AxiomTypeCountMetricFactory testSubject0 = new AxiomTypeCountMetricFactory();
        Set<OWLMetric<?>> result0 = AxiomTypeCountMetricFactory.createMetrics(Utils
                .getMockOntology());
        String result1 = testSubject0.toString();
    }

    @Ignore
    @Test
    public void shouldTestAxiomTypeMetric() throws OWLException {
        AxiomTypeMetric testSubject0 = new AxiomTypeMetric(Utils.getMockOntology(),
                mock(AxiomType.class));
        AxiomType<?> result0 = testSubject0.getAxiomType();
        Set<? extends OWLAxiom> result1 = testSubject0.getAxioms();
        String result2 = testSubject0.getName();
        Object result3 = testSubject0.recomputeMetric();
        Integer result4 = testSubject0.recomputeMetric();
        String result5 = testSubject0.toString();
        Object result6 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result7 = testSubject0.getOntologies();
        OWLOntology result8 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result9 = testSubject0.getManager();
        boolean result10 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestDLExpressivity() throws OWLException {
        DLExpressivity testSubject0 = new DLExpressivity(Utils.getMockOntology());
        String result0 = testSubject0.getName();
        String result1 = testSubject0.recomputeMetric();
        Object result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Test
    public void shouldTestDoubleValuedMetric() throws OWLException {
        DoubleValuedMetric testSubject0 = new DoubleValuedMetric(Utils.getMockOntology()) {
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
                    List<? extends OWLOntologyChange<?>> changes) {
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
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Ignore
    @Test
    public void shouldTestGCICount() throws OWLException {
        GCICount testSubject0 = new GCICount(Utils.getMockOntology());
        Set<? extends OWLAxiom> result0 = testSubject0.getAxioms();
        String result1 = testSubject0.getName();
        Object result2 = testSubject0.recomputeMetric();
        Integer result3 = testSubject0.recomputeMetric();
        String result4 = testSubject0.toString();
        Object result5 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result6 = testSubject0.getOntologies();
        OWLOntology result7 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result8 = testSubject0.getManager();
        boolean result9 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestHiddenGCICount() throws OWLException {
        HiddenGCICount testSubject0 = new HiddenGCICount(Utils.getMockOntology());
        String result0 = testSubject0.getName();
        String result1 = testSubject0.toString();
        Object result2 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result3 = testSubject0.getOntologies();
        OWLOntology result4 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result5 = testSubject0.getManager();
        boolean result6 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestImportClosureSize() throws OWLException {
        ImportClosureSize testSubject0 = new ImportClosureSize(Utils.getMockOntology());
        String result0 = testSubject0.getName();
        String result1 = testSubject0.toString();
        Object result2 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result3 = testSubject0.getOntologies();
        OWLOntology result4 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result5 = testSubject0.getManager();
        boolean result6 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Test
    public void shouldTestIntegerValuedMetric() throws OWLException {
        IntegerValuedMetric testSubject0 = new IntegerValuedMetric(
                Utils.getMockOntology()) {
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
                    List<? extends OWLOntologyChange<?>> changes) {
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
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result4 = testSubject0.getManager();
        boolean result5 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
        String result6 = testSubject0.getName();
    }

    @Ignore
    @Test
    public void shouldTestLogicalAxiomCount() throws OWLException {
        LogicalAxiomCount testSubject0 = new LogicalAxiomCount(Utils.getMockOntology());
        Set<? extends OWLAxiom> result0 = testSubject0.getAxioms();
        String result1 = testSubject0.getName();
        Object result2 = testSubject0.recomputeMetric();
        Integer result3 = testSubject0.recomputeMetric();
        String result4 = testSubject0.toString();
        Object result5 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result6 = testSubject0.getOntologies();
        OWLOntology result7 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result8 = testSubject0.getManager();
        boolean result9 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestMaximumNumberOfNamedSuperclasses() throws OWLException {
        MaximumNumberOfNamedSuperclasses testSubject0 = new MaximumNumberOfNamedSuperclasses(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Integer result1 = testSubject0.recomputeMetric();
        Object result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestNumberOfClassesWithMultipleInheritance() throws OWLException {
        NumberOfClassesWithMultipleInheritance testSubject0 = new NumberOfClassesWithMultipleInheritance(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Integer result1 = testSubject0.recomputeMetric();
        Object result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestObjectCountMetric() throws OWLException {
        ObjectCountMetric<Object> testSubject0 = new ObjectCountMetric<Object>(Factory
                .getManager().createOntology()) {
            @Override
            protected String getObjectTypeName() {
                return null;
            }

            @Override
            protected Set<Object> getObjects(OWLOntology ont) {
                return null;
            }
        };
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.recomputeMetric();
        Integer result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Test
    public void shouldTestInterfaceOWLMetric() throws OWLException {
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
    public void shouldTestOWLMetricManager() throws OWLException {
        OWLMetricManager testSubject0 = new OWLMetricManager(
                new ArrayList<OWLMetric<?>>());
        String result0 = testSubject0.toString();
        testSubject0.setOntology(Utils.getMockOntology());
        List<OWLMetric<?>> result1 = testSubject0.getMetrics();
    }

    @Ignore
    @Test
    public void shouldTestReferencedClassCount() throws OWLException {
        ReferencedClassCount testSubject0 = new ReferencedClassCount(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.recomputeMetric();
        Integer result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestReferencedDataPropertyCount() throws OWLException {
        ReferencedDataPropertyCount testSubject0 = new ReferencedDataPropertyCount(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.recomputeMetric();
        Integer result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestReferencedIndividualCount() throws OWLException {
        ReferencedIndividualCount testSubject0 = new ReferencedIndividualCount(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.recomputeMetric();
        Integer result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }

    @Ignore
    @Test
    public void shouldTestReferencedObjectPropertyCount() throws OWLException {
        ReferencedObjectPropertyCount testSubject0 = new ReferencedObjectPropertyCount(
                Utils.getMockOntology());
        String result0 = testSubject0.getName();
        Object result1 = testSubject0.recomputeMetric();
        Integer result2 = testSubject0.recomputeMetric();
        String result3 = testSubject0.toString();
        Object result4 = testSubject0.getValue();
        testSubject0.dispose();
        Set<OWLOntology> result5 = testSubject0.getOntologies();
        OWLOntology result6 = testSubject0.getOntology();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.setOntology(Utils.getMockOntology());
        OWLOntologyManager result7 = testSubject0.getManager();
        boolean result8 = testSubject0.isImportsClosureUsed();
        testSubject0.setImportsClosureUsed(false);
    }
}
