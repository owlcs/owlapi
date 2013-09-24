package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.ShortFormProvider;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiTest {
    @Test
    public void shouldTestAbstractCompositeOntologyChange() throws OWLException {
        AbstractCompositeOntologyChange testSubject0 = new AbstractCompositeOntologyChange(
                mock(OWLDataFactory.class)) {};

        List<OWLOntologyChange<?>> result1 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestAddClassExpressionClosureAxiom() throws OWLException {
        AddClassExpressionClosureAxiom testSubject0 = new AddClassExpressionClosureAxiom(
                mock(OWLDataFactory.class), mock(OWLClass.class),
                Utils.mockObjectProperty(), Utils.mockSet(Utils.getMockOntology()),
                Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestAmalgamateSubClassAxioms() throws OWLException {
        AmalgamateSubClassAxioms testSubject0 = new AmalgamateSubClassAxioms(
                mock(OWLDataFactory.class), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestCoerceConstantsIntoDataPropertyRange() throws OWLException {
        CoerceConstantsIntoDataPropertyRange testSubject0 = new CoerceConstantsIntoDataPropertyRange(
                mock(OWLDataFactory.class), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestConvertEquivalentClassesToSuperClasses() throws OWLException {
        ConvertEquivalentClassesToSuperClasses testSubject0 = new ConvertEquivalentClassesToSuperClasses(
                mock(OWLDataFactory.class), mock(OWLClass.class), Utils.mockSet(Utils
                        .getMockOntology()), Utils.getMockOntology(), false);
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestConvertPropertyAssertionsToAnnotations() throws OWLException {
        ConvertPropertyAssertionsToAnnotations testSubject0 = new ConvertPropertyAssertionsToAnnotations(
                Factory.getFactory(), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestConvertSuperClassesToEquivalentClass() throws OWLException {
        ConvertSuperClassesToEquivalentClass testSubject0 = new ConvertSuperClassesToEquivalentClass(
                Factory.getFactory(), Factory.getFactory().getOWLClass(
                        IRI.create("urn:test:class")), Utils.mockSet(Utils
                        .getMockOntology()), Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestCreateValuePartition() throws OWLException {
        CreateValuePartition testSubject0 = new CreateValuePartition(
                Factory.getFactory(), mock(OWLClass.class),
                Utils.mockSet(mock(OWLClass.class)), mock(OWLObjectProperty.class),
                Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestMakeClassesMutuallyDisjoint() throws OWLException {
        Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>(Arrays.asList(
                Factory.getFactory().getOWLClass(IRI.create("urn:test:c1")), Factory
                        .getFactory().getOWLClass(IRI.create("urn:test:c2"))));
        MakeClassesMutuallyDisjoint testSubject0 = new MakeClassesMutuallyDisjoint(
                Factory.getFactory(), classes, false, Utils.getMockOntology());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestMakePrimitiveSubClassesMutuallyDisjoint() throws OWLException {
        MakePrimitiveSubClassesMutuallyDisjoint testSubject0 = new MakePrimitiveSubClassesMutuallyDisjoint(
                Factory.getFactory(), Factory.getFactory().getOWLClass(
                        IRI.create("urn:test:c")), Utils.getMockOntology());
        MakePrimitiveSubClassesMutuallyDisjoint testSubject1 = new MakePrimitiveSubClassesMutuallyDisjoint(
                Factory.getFactory(), Factory.getFactory().getOWLClass(
                        IRI.create("urn:test:c")), Utils.getMockOntology(), false);
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestInterfaceOWLCompositeOntologyChange() throws OWLException {
        OWLCompositeOntologyChange testSubject0 = mock(OWLCompositeOntologyChange.class);
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestRemoveAllDisjointAxioms() throws OWLException {
        RemoveAllDisjointAxioms testSubject0 = new RemoveAllDisjointAxioms(
                mock(OWLDataFactory.class), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    public void shouldTestShortForm2AnnotationGenerator() throws OWLException {
        ShortForm2AnnotationGenerator testSubject0 = new ShortForm2AnnotationGenerator(
                mock(OWLDataFactory.class), Utils.getMockManager(),
                Utils.getMockOntology(), mock(ShortFormProvider.class), IRI("urn:aFake"),
                "");
        ShortForm2AnnotationGenerator testSubject1 = new ShortForm2AnnotationGenerator(
                mock(OWLDataFactory.class), Utils.getMockManager(),
                Utils.getMockOntology(), mock(ShortFormProvider.class), IRI("urn:aFake"));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }

    @Test
    public void shouldTestSplitSubClassAxioms() throws OWLException {
        SplitSubClassAxioms testSubject0 = new SplitSubClassAxioms(Utils.mockSet(Utils
                .getMockOntology()), mock(OWLDataFactory.class));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();

    }
}
