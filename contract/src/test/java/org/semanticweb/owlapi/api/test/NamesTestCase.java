package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.coode.owl.krssparser.KRSSOWLParser;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxOWLParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyParser;
import org.coode.owlapi.obo.parser.OWLOBOParser;
import org.coode.owlapi.owlxmlparser.OWLXMLParser;
import org.coode.owlapi.rdfxml.parser.RDFXMLParser;
import org.junit.Test;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AddImportData;
import org.semanticweb.owlapi.change.AddOntologyAnnotationData;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.change.RemoveImportData;
import org.semanticweb.owlapi.change.RemoveOntologyAnnotationData;
import org.semanticweb.owlapi.change.SetOntologyIDData;
import org.semanticweb.owlapi.metrics.AverageAssertedNamedSuperclassCount;
import org.semanticweb.owlapi.metrics.AxiomCount;
import org.semanticweb.owlapi.metrics.HiddenGCICount;
import org.semanticweb.owlapi.metrics.ImportClosureSize;
import org.semanticweb.owlapi.metrics.MaximumNumberOfNamedSuperclasses;
import org.semanticweb.owlapi.metrics.NumberOfClassesWithMultipleInheritance;
import org.semanticweb.owlapi.metrics.UnsatisfiableClassCountMetric;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2Profile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParser;
import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OWLParser;

@SuppressWarnings("javadoc")
public class NamesTestCase {
    @Test
    public void shoudReturnRightName() {
        assertEquals("AddAxiomData", new AddAxiomData(mock(OWLAxiom.class)) {
            private static final long serialVersionUID = 6721581006563915342L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("AddImportData",
                new AddImportData(mock(OWLImportsDeclaration.class)) {
                    private static final long serialVersionUID = 2561394366351548647L;

                    @Override
                    public String getName() {
                        return super.getName();
                    }
                }.getName());
        assertEquals("AddOntologyAnnotationData", new AddOntologyAnnotationData(
                mock(OWLAnnotation.class)) {
            private static final long serialVersionUID = 7438496788137119984L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("OWLOntologyChangeRecord", new OWLOntologyChangeRecord<OWLAxiom>(
                mock(OWLOntologyID.class), mock(OWLOntologyChangeData.class)) {
            private static final long serialVersionUID = 1738715002719055965L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveAxiomData", new RemoveAxiomData(mock(OWLAxiom.class)) {
            private static final long serialVersionUID = 3509217375432737384L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveImportData", new RemoveImportData(
                mock(OWLImportsDeclaration.class)) {
            private static final long serialVersionUID = 2566579395436954130L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals("RemoveOntologyAnnotationData", new RemoveOntologyAnnotationData(
                mock(OWLAnnotation.class)) {
            private static final long serialVersionUID = 3019208333794944242L;

            @Override
            public String getName() {
                return super.getName();
            }
        }.getName());
        assertEquals(
                "SetOntologyIDData",
                new SetOntologyIDData(new OWLOntologyID(IRI.create("urn:test1"), IRI
                        .create("urn:test2"))) {
                    private static final long serialVersionUID = 7310142638187830473L;

                    @Override
                    public String getName() {
                        return super.getName();
                    }
                }.getName());
        assertEquals("OWL 2 DL", new OWL2DLProfile().getName());
        assertEquals("OWL 2 EL", new OWL2ELProfile().getName());
        assertEquals("OWL 2", new OWL2Profile().getName());
        assertEquals("OWL 2 QL", new OWL2QLProfile().getName());
        assertEquals("OWL 2 RL", new OWL2RLProfile().getName());
        assertEquals("KRSS2OWLParser", new KRSS2OWLParser().getName());
        assertEquals("KRSSOWLParser", new KRSSOWLParser().getName());
        assertEquals("OWLFunctionalSyntaxOWLParser",
                new OWLFunctionalSyntaxOWLParser().getName());
        assertEquals("ManchesterOWLSyntaxOntologyParser",
                new ManchesterOWLSyntaxOntologyParser().getName());
        assertEquals("OWLOBOParser", new OWLOBOParser().getName());
        assertEquals("OWLXMLParser", new OWLXMLParser().getName());
        assertEquals("RDFXMLParser", new RDFXMLParser().getName());
        assertEquals("TurtleOntologyParser", new TurtleOntologyParser().getName());
        assertEquals("Average number of named superclasses",
                new AverageAssertedNamedSuperclassCount(mock(OWLOntologyManager.class))
                        .getName());
        assertEquals("Axiom", new AxiomCount(mock(OWLOntologyManager.class)).getName());
        assertEquals("Hidden GCI Count", new HiddenGCICount(
                mock(OWLOntologyManager.class)).getName());
        assertEquals("Imports closure size", new ImportClosureSize(
                mock(OWLOntologyManager.class)).getName());
        assertEquals("Maximum number of asserted named superclasses",
                new MaximumNumberOfNamedSuperclasses(mock(OWLOntologyManager.class))
                        .getName());
        assertEquals(
                "Number of classes with asserted multiple inheritance",
                new NumberOfClassesWithMultipleInheritance(mock(OWLOntologyManager.class))
                        .getName());
        assertEquals("Unsatisfiable class count", new UnsatisfiableClassCountMetric(
                mock(OWLReasoner.class), mock(OWLOntologyManager.class)).getName());
    }
}
