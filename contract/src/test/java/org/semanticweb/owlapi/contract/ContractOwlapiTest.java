package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.AbstractCompositeOntologyChange;
import org.semanticweb.owlapi.AddClassExpressionClosureAxiom;
import org.semanticweb.owlapi.AmalgamateSubClassAxioms;
import org.semanticweb.owlapi.CoerceConstantsIntoDataPropertyRange;
import org.semanticweb.owlapi.ConvertEquivalentClassesToSuperClasses;
import org.semanticweb.owlapi.ConvertPropertyAssertionsToAnnotations;
import org.semanticweb.owlapi.ConvertSuperClassesToEquivalentClass;
import org.semanticweb.owlapi.CreateValuePartition;
import org.semanticweb.owlapi.MakeClassesMutuallyDisjoint;
import org.semanticweb.owlapi.MakePrimitiveSubClassesMutuallyDisjoint;
import org.semanticweb.owlapi.OWLCompositeOntologyChange;
import org.semanticweb.owlapi.RemoveAllDisjointAxioms;
import org.semanticweb.owlapi.ShortForm2AnnotationGenerator;
import org.semanticweb.owlapi.SplitSubClassAxioms;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.ShortFormProvider;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiTest {

    @Test
    public void shouldTestAbstractCompositeOntologyChange() throws Exception {
        AbstractCompositeOntologyChange testSubject0 = new AbstractCompositeOntologyChange(
                mock(OWLDataFactory.class)) {

            @Override
            public List<OWLOntologyChange> getChanges() {
                return null;
            }
        };
        String result0 = testSubject0.toString();
        List<OWLOntologyChange> result1 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestAddClassExpressionClosureAxiom() throws Exception {
        AddClassExpressionClosureAxiom testSubject0 = new AddClassExpressionClosureAxiom(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockObjectProperty(), Utils.mockSet(Utils
                        .getMockOntology()), Utils.getMockOntology());
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestAmalgamateSubClassAxioms() throws Exception {
        AmalgamateSubClassAxioms testSubject0 = new AmalgamateSubClassAxioms(
                Utils.mockSet(Utils.getMockOntology()),
                mock(OWLDataFactory.class));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestCoerceConstantsIntoDataPropertyRange()
            throws Exception {
        CoerceConstantsIntoDataPropertyRange testSubject0 = new CoerceConstantsIntoDataPropertyRange(
                mock(OWLDataFactory.class), Utils.mockSet(Utils
                        .getMockOntology()));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestConvertEquivalentClassesToSuperClasses()
            throws Exception {
        ConvertEquivalentClassesToSuperClasses testSubject0 = new ConvertEquivalentClassesToSuperClasses(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockSet(Utils.getMockOntology()),
                Utils.getMockOntology(), false);
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestConvertPropertyAssertionsToAnnotations()
            throws Exception {
        ConvertPropertyAssertionsToAnnotations testSubject0 = new ConvertPropertyAssertionsToAnnotations(
                mock(OWLDataFactory.class), Utils.mockSet(Utils
                        .getMockOntology()));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestConvertSuperClassesToEquivalentClass()
            throws Exception {
        ConvertSuperClassesToEquivalentClass testSubject0 = new ConvertSuperClassesToEquivalentClass(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockSet(Utils.getMockOntology()), Utils.getMockOntology());
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestCreateValuePartition() throws Exception {
        CreateValuePartition testSubject0 = new CreateValuePartition(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockSet(mock(OWLClass.class)),
                mock(OWLObjectProperty.class), Utils.getMockOntology());
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestMakeClassesMutuallyDisjoint() throws Exception {
        MakeClassesMutuallyDisjoint testSubject0 = new MakeClassesMutuallyDisjoint(
                mock(OWLDataFactory.class),
                Utils.mockSet(Utils.mockAnonClass()), false,
                Utils.getMockOntology());
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestMakePrimitiveSubClassesMutuallyDisjoint()
            throws Exception {
        MakePrimitiveSubClassesMutuallyDisjoint testSubject0 = new MakePrimitiveSubClassesMutuallyDisjoint(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockSet(Utils.getMockOntology()), Utils.getMockOntology());
        MakePrimitiveSubClassesMutuallyDisjoint testSubject1 = new MakePrimitiveSubClassesMutuallyDisjoint(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockSet(Utils.getMockOntology()),
                Utils.getMockOntology(), false);
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLCompositeOntologyChange()
            throws Exception {
        OWLCompositeOntologyChange testSubject0 = mock(OWLCompositeOntologyChange.class);
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestRemoveAllDisjointAxioms() throws Exception {
        RemoveAllDisjointAxioms testSubject0 = new RemoveAllDisjointAxioms(
                mock(OWLDataFactory.class), Utils.mockSet(Utils
                        .getMockOntology()));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    public void shouldTestShortForm2AnnotationGenerator() throws Exception {
        ShortForm2AnnotationGenerator testSubject0 = new ShortForm2AnnotationGenerator(
                Utils.getMockManager(), Utils.getMockOntology(),
                mock(ShortFormProvider.class), IRI("urn:aFake"), "");
        ShortForm2AnnotationGenerator testSubject1 = new ShortForm2AnnotationGenerator(
                Utils.getMockManager(), Utils.getMockOntology(),
                mock(ShortFormProvider.class), IRI("urn:aFake"));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestSplitSubClassAxioms() throws Exception {
        SplitSubClassAxioms testSubject0 = new SplitSubClassAxioms(
                Utils.mockSet(Utils.getMockOntology()),
                mock(OWLDataFactory.class));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }
}
