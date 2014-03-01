package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.profiles.CycleInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapi.profiles.EmptyOneOfAxiom;
import org.semanticweb.owlapi.profiles.InsufficientIndividuals;
import org.semanticweb.owlapi.profiles.InsufficientOperands;
import org.semanticweb.owlapi.profiles.InsufficientPropertyExpressions;
import org.semanticweb.owlapi.profiles.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapi.profiles.LexicalNotInLexicalSpace;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2DLProfileViolation;
import org.semanticweb.owlapi.profiles.OWL2DLProfileViolationVisitor;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfileViolation;
import org.semanticweb.owlapi.profiles.OWL2ELProfileViolationVisitor;
import org.semanticweb.owlapi.profiles.OWL2Profile;
import org.semanticweb.owlapi.profiles.OWL2ProfileReport;
import org.semanticweb.owlapi.profiles.OWL2ProfileViolation;
import org.semanticweb.owlapi.profiles.OWL2ProfileViolationVisitor;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2QLProfileViolation;
import org.semanticweb.owlapi.profiles.OWL2QLProfileViolationVisitor;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfileViolation;
import org.semanticweb.owlapi.profiles.OWL2RLProfileViolationVisitor;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.OntologyIRINotAbsolute;
import org.semanticweb.owlapi.profiles.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapi.profiles.UseOfAnonymousIndividual;
import org.semanticweb.owlapi.profiles.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.UseOfDataOneOfWithMultipleLiterals;
import org.semanticweb.owlapi.profiles.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapi.profiles.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.UseOfIllegalClassExpression;
import org.semanticweb.owlapi.profiles.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapi.profiles.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapi.profiles.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonEquivalentClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapi.profiles.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapi.profiles.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.profiles.UseOfObjectOneOfWithMultipleIndividuals;
import org.semanticweb.owlapi.profiles.UseOfObjectPropertyInverse;
import org.semanticweb.owlapi.profiles.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForClassIRI;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForDataPropertyIRI;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapi.profiles.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapi.profiles.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapi.profiles.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapi.profiles.UseOfUndeclaredClass;
import org.semanticweb.owlapi.profiles.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapi.profiles.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi.profiles.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapi.profiles.UseOfUnknownDatatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiProfilesTest {

    @Test
    public void shouldTestCycleInDatatypeDefinition() throws Exception {
        CycleInDatatypeDefinition testSubject0 = new CycleInDatatypeDefinition(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestDatatypeIRIAlsoUsedAsClassIRI() throws Exception {
        DatatypeIRIAlsoUsedAsClassIRI testSubject0 = new DatatypeIRIAlsoUsedAsClassIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class), IRI("urn:aFake"));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getIRI();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestEmptyOneOfAxiom() throws Exception {
        EmptyOneOfAxiom testSubject0 = new EmptyOneOfAxiom(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestInsufficientIndividuals() throws Exception {
        InsufficientIndividuals testSubject0 = new InsufficientIndividuals(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestInsufficientOperands() throws Exception {
        InsufficientOperands testSubject0 = new InsufficientOperands(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLObject.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestInsufficientPropertyExpressions() throws Exception {
        InsufficientPropertyExpressions testSubject0 = new InsufficientPropertyExpressions(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestLastPropertyInChainNotInImposedRange()
            throws Exception {
        LastPropertyInChainNotInImposedRange testSubject0 = new LastPropertyInChainNotInImposedRange(
                Utils.getMockOntology(),
                mock(OWLSubPropertyChainOfAxiom.class),
                mock(OWLObjectPropertyRangeAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        OWLSubPropertyChainOfAxiom result1 = testSubject0
                .getOWLSubPropertyChainOfAxiom();
        OWLObjectPropertyRangeAxiom result2 = testSubject0
                .getOWLObjectPropertyRangeAxiom();
        IRI result3 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result4 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result5 = testSubject0.getImportsClosure();
        OWLAxiom result6 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestLexicalNotInLexicalSpace() throws Exception {
        LexicalNotInLexicalSpace testSubject0 = new LexicalNotInLexicalSpace(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLLiteral.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        OWLLiteral result1 = testSubject0.getLiteral();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestOntologyIRINotAbsolute() throws Exception {
        OntologyIRINotAbsolute testSubject0 = new OntologyIRINotAbsolute(
                Utils.getMockOntology());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestOntologyVersionIRINotAbsolute() throws Exception {
        OntologyVersionIRINotAbsolute testSubject0 = new OntologyVersionIRINotAbsolute(
                Utils.getMockOntology());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestOWL2DLProfile() throws Exception {
        OWL2DLProfile testSubject0 = new OWL2DLProfile();
        String result0 = testSubject0.getName();
        OWLProfileReport result1 = testSubject0.checkOntology(Utils
                .getMockOntology());
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWL2DLProfileViolation() throws Exception {
        OWL2DLProfileViolation testSubject0 = mock(OWL2DLProfileViolation.class);
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
    }

    @Test
    public void shouldTestInterfaceOWL2DLProfileViolationVisitor()
            throws Exception {
        OWL2DLProfileViolationVisitor testSubject0 = mock(OWL2DLProfileViolationVisitor.class);
        testSubject0
                .visit(mock(UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom.class));
        testSubject0
                .visit(mock(UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom.class));
        testSubject0
                .visit(mock(UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom.class));
    }

    @Test
    public void shouldTestOWL2ELProfile() throws Exception {
        OWL2ELProfile testSubject0 = new OWL2ELProfile();
        String result0 = testSubject0.getName();
        OWLProfileReport result1 = testSubject0.checkOntology(Utils
                .getMockOntology());
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWL2ELProfileViolation() throws Exception {
        OWL2ELProfileViolation testSubject0 = mock(OWL2ELProfileViolation.class);
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
    }

    @Test
    public void shouldTestInterfaceOWL2ELProfileViolationVisitor()
            throws Exception {
        OWL2ELProfileViolationVisitor testSubject0 = mock(OWL2ELProfileViolationVisitor.class);
    }

    @Test
    public void shouldTestOWL2Profile() throws Exception {
        OWL2Profile testSubject0 = new OWL2Profile();
        String result0 = testSubject0.getName();
        OWLProfileReport result1 = testSubject0.checkOntology(Utils
                .getMockOntology());
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWL2ProfileReport() throws Exception {
        OWL2ProfileReport testSubject0 = new OWL2ProfileReport(
                mock(OWLProfile.class),
                Utils.mockSet(mock(OWLProfileViolation.class)),
                Utils.mockSet(Utils.mockObjectProperty()), Utils.mockSet(Utils
                        .mockObjectProperty()));
        String result0 = testSubject0.toString();
        Set<OWLObjectPropertyExpression> result1 = testSubject0
                .getNonSimpleRoles();
        Set<OWLObjectPropertyExpression> result2 = testSubject0
                .getSimpleRoles();
        boolean result3 = testSubject0.isInProfile();
        List<OWLProfileViolation> result4 = testSubject0.getViolations();
        OWLProfile result5 = testSubject0.getProfile();
    }

    @Test
    public void shouldTestInterfaceOWL2ProfileViolation() throws Exception {
        OWL2ProfileViolation testSubject0 = mock(OWL2ProfileViolation.class);
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
    }

    @Test
    public void shouldTestInterfaceOWL2ProfileViolationVisitor()
            throws Exception {
        OWL2ProfileViolationVisitor testSubject0 = mock(OWL2ProfileViolationVisitor.class);
    }

    public void shouldTestOWL2QLProfile() throws Exception {
        OWL2QLProfile testSubject0 = new OWL2QLProfile();
        String result0 = testSubject0.getName();
        OWLProfileReport result1 = testSubject0.checkOntology(Utils
                .getMockOntology());
        boolean result2 = testSubject0.isOWL2QLSuperClassExpression(Utils
                .mockAnonClass());
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWL2QLProfileViolation() throws Exception {
        OWL2QLProfileViolation testSubject0 = mock(OWL2QLProfileViolation.class);
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
    }

    @Test
    public void shouldTestInterfaceOWL2QLProfileViolationVisitor()
            throws Exception {
        OWL2QLProfileViolationVisitor testSubject0 = mock(OWL2QLProfileViolationVisitor.class);
    }

    public void shouldTestOWL2RLProfile() throws Exception {
        OWL2RLProfile testSubject0 = new OWL2RLProfile();
        String result0 = testSubject0.getName();
        OWLProfileReport result1 = testSubject0.checkOntology(Utils
                .getMockOntology());
        boolean result2 = testSubject0.isOWL2RLSuperClassExpression(Utils
                .mockAnonClass());
        boolean result3 = testSubject0.isOWL2RLEquivalentClassExpression(Utils
                .mockAnonClass());
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWL2RLProfileViolation() throws Exception {
        OWL2RLProfileViolation testSubject0 = mock(OWL2RLProfileViolation.class);
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
    }

    @Test
    public void shouldTestInterfaceOWL2RLProfileViolationVisitor()
            throws Exception {
        OWL2RLProfileViolationVisitor testSubject0 = mock(OWL2RLProfileViolationVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLProfile() throws Exception {
        OWLProfile testSubject0 = mock(OWLProfile.class);
        String result0 = testSubject0.getName();
        OWLProfileReport result1 = testSubject0.checkOntology(Utils
                .getMockOntology());
    }

    @Test
    public void shouldTestOWLProfileReport() throws Exception {
        OWLProfileReport testSubject0 = new OWLProfileReport(
                mock(OWLProfile.class),
                Utils.mockSet(mock(OWLProfileViolation.class)));
        String result0 = testSubject0.toString();
        boolean result1 = testSubject0.isInProfile();
        List<OWLProfileViolation> result2 = testSubject0.getViolations();
        OWLProfile result3 = testSubject0.getProfile();
    }

    @Test
    public void shouldTestOWLProfileViolation() throws Exception {
        OWLProfileViolation testSubject0 = new OWLProfileViolation(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        IRI result0 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result1 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result2 = testSubject0.getImportsClosure();
        OWLAxiom result3 = testSubject0.getAxiom();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestUseOfAnonymousIndividual() throws Exception {
        UseOfAnonymousIndividual testSubject0 = new UseOfAnonymousIndividual(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLAnonymousIndividual.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        OWLAnonymousIndividual result1 = testSubject0
                .getOWLAnonymousIndividual();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfBuiltInDatatypeInDatatypeDefinition()
            throws Exception {
        UseOfBuiltInDatatypeInDatatypeDefinition testSubject0 = new UseOfBuiltInDatatypeInDatatypeDefinition(
                Utils.getMockOntology(), mock(OWLDatatypeDefinitionAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfDataOneOfWithMultipleLiterals() throws Exception {
        UseOfDataOneOfWithMultipleLiterals testSubject0 = new UseOfDataOneOfWithMultipleLiterals(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDataOneOf.class));
        String result0 = testSubject0.toString();
        OWLDataOneOf result1 = testSubject0.getDataOneOf();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        OWLDataRange result2 = testSubject0.getOWLDataRange();
        IRI result3 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result4 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result5 = testSubject0.getImportsClosure();
        OWLAxiom result6 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfDefinedDatatypeInDatatypeRestriction()
            throws Exception {
        UseOfDefinedDatatypeInDatatypeRestriction testSubject0 = new UseOfDefinedDatatypeInDatatypeRestriction(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDatatypeRestriction.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        OWLDatatypeRestriction result1 = testSubject0
                .getOWLDatatypeRestriction();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfIllegalAxiom() throws Exception {
        UseOfIllegalAxiom testSubject0 = new UseOfIllegalAxiom(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    public void shouldTestUseOfIllegalClassExpression() throws Exception {
        UseOfIllegalClassExpression testSubject0 = new UseOfIllegalClassExpression(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                Utils.mockAnonClass());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        OWLClassExpression result1 = testSubject0.getOWLClassExpression();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfIllegalDataRange() throws Exception {
        UseOfIllegalDataRange testSubject0 = new UseOfIllegalDataRange(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDataRange.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        OWLDataRange result1 = testSubject0.getOWLDataRange();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfIllegalFacetRestriction() throws Exception {
        UseOfIllegalFacetRestriction testSubject0 = new UseOfIllegalFacetRestriction(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDatatypeRestriction.class), OWLFacet.MAX_INCLUSIVE);
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        OWLFacet result1 = testSubject0.getFacet();
        OWLDatatypeRestriction result2 = testSubject0.getDatatypeRestriction();
        IRI result3 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result4 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result5 = testSubject0.getImportsClosure();
        OWLAxiom result6 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfNonAbsoluteIRI() throws Exception {
        UseOfNonAbsoluteIRI testSubject0 = new UseOfNonAbsoluteIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class), IRI("test"));
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        IRI result0 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result1 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result2 = testSubject0.getImportsClosure();
        OWLAxiom result3 = testSubject0.getAxiom();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestUseOfNonAtomicClassExpression() throws Exception {
        UseOfNonAtomicClassExpression testSubject0 = new UseOfNonAtomicClassExpression(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                Utils.mockAnonClass());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        OWLClassExpression result1 = testSubject0.getOWLClassExpression();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfNonEquivalentClassExpression() throws Exception {
        UseOfNonEquivalentClassExpression testSubject0 = new UseOfNonEquivalentClassExpression(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                Utils.mockAnonClass());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        OWLClassExpression result1 = testSubject0.getOWLClassExpression();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    public void
            shouldTestUseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom()
                    throws Exception {
        UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom testSubject0 = new UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(
                Utils.getMockOntology(),
                mock(OWLAsymmetricObjectPropertyAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLAsymmetricObjectPropertyAxiom result1 = testSubject0.getAxiom();
        OWLAxiom result2 = testSubject0.getAxiom();
        IRI result3 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result4 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result5 = testSubject0.getImportsClosure();
    }

    public void shouldTestUseOfNonSimplePropertyInCardinalityRestriction()
            throws Exception {
        UseOfNonSimplePropertyInCardinalityRestriction testSubject0 = new UseOfNonSimplePropertyInCardinalityRestriction(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLObjectCardinalityRestriction.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLObjectCardinalityRestriction result1 = testSubject0
                .getOWLCardinalityRestriction();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    public void shouldTestUseOfNonSimplePropertyInDisjointPropertiesAxiom()
            throws Exception {
        UseOfNonSimplePropertyInDisjointPropertiesAxiom testSubject0 = new UseOfNonSimplePropertyInDisjointPropertiesAxiom(
                Utils.getMockOntology(),
                mock(OWLDisjointObjectPropertiesAxiom.class),
                Utils.mockObjectProperty());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLObjectPropertyExpression result1 = testSubject0
                .getOWLObjectProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    public void shouldTestUseOfNonSimplePropertyInFunctionalPropertyAxiom()
            throws Exception {
        UseOfNonSimplePropertyInFunctionalPropertyAxiom testSubject0 = new UseOfNonSimplePropertyInFunctionalPropertyAxiom(
                Utils.getMockOntology(),
                mock(OWLFunctionalObjectPropertyAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    public
            void
            shouldTestUseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom()
                    throws Exception {
        UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom testSubject0 = new UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom(
                Utils.getMockOntology(),
                mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    public void shouldTestUseOfNonSimplePropertyInIrreflexivePropertyAxiom()
            throws Exception {
        UseOfNonSimplePropertyInIrreflexivePropertyAxiom testSubject0 = new UseOfNonSimplePropertyInIrreflexivePropertyAxiom(
                Utils.getMockOntology(),
                mock(OWLIrreflexiveObjectPropertyAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfNonSimplePropertyInObjectHasSelf()
            throws Exception {
        UseOfNonSimplePropertyInObjectHasSelf testSubject0 = new UseOfNonSimplePropertyInObjectHasSelf(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLObjectHasSelf.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLObjectHasSelf result1 = testSubject0.getOWLObjectHasSelf();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfNonSubClassExpression() throws Exception {
        UseOfNonSubClassExpression testSubject0 = new UseOfNonSubClassExpression(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                Utils.mockAnonClass());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        OWLClassExpression result1 = testSubject0.getOWLClassExpression();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfNonSuperClassExpression() throws Exception {
        UseOfNonSuperClassExpression testSubject0 = new UseOfNonSuperClassExpression(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                Utils.mockAnonClass());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2QLProfileViolationVisitor.class));
        testSubject0.accept(mock(OWL2RLProfileViolationVisitor.class));
        OWLClassExpression result1 = testSubject0.getOWLClassExpression();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfObjectOneOfWithMultipleIndividuals()
            throws Exception {
        UseOfObjectOneOfWithMultipleIndividuals testSubject0 = new UseOfObjectOneOfWithMultipleIndividuals(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLObjectOneOf.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        OWLObjectOneOf result1 = testSubject0.getOWLObjectOneOf();
        OWLClassExpression result2 = testSubject0.getOWLClassExpression();
        IRI result3 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result4 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result5 = testSubject0.getImportsClosure();
        OWLAxiom result6 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfObjectPropertyInverse() throws Exception {
        UseOfObjectPropertyInverse testSubject0 = new UseOfObjectPropertyInverse(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                Utils.mockObjectProperty());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ELProfileViolationVisitor.class));
        OWLObjectPropertyExpression result1 = testSubject0
                .getOWLPropertyExpression();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfPropertyInChainCausesCycle() throws Exception {
        UseOfPropertyInChainCausesCycle testSubject0 = new UseOfPropertyInChainCausesCycle(
                Utils.getMockOntology(),
                mock(OWLSubPropertyChainOfAxiom.class),
                Utils.mockObjectProperty());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLObjectPropertyExpression result1 = testSubject0
                .getOWLObjectProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForAnnotationPropertyIRI()
            throws Exception {
        UseOfReservedVocabularyForAnnotationPropertyIRI testSubject0 = new UseOfReservedVocabularyForAnnotationPropertyIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLAnnotationProperty.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLAnnotationProperty result1 = testSubject0.getOWLAnnotationProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForClassIRI() throws Exception {
        UseOfReservedVocabularyForClassIRI testSubject0 = new UseOfReservedVocabularyForClassIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLClass.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLClass result1 = testSubject0.getOWLClass();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForDataPropertyIRI()
            throws Exception {
        UseOfReservedVocabularyForDataPropertyIRI testSubject0 = new UseOfReservedVocabularyForDataPropertyIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDataProperty.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLDataProperty result1 = testSubject0.getOWLDataProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForIndividualIRI()
            throws Exception {
        UseOfReservedVocabularyForIndividualIRI testSubject0 = new UseOfReservedVocabularyForIndividualIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLNamedIndividual.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLNamedIndividual result1 = testSubject0.getOWLNamedIndividual();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForObjectPropertyIRI()
            throws Exception {
        UseOfReservedVocabularyForObjectPropertyIRI testSubject0 = new UseOfReservedVocabularyForObjectPropertyIRI(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLObjectProperty.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLObjectProperty result1 = testSubject0.getOWLObjectProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForOntologyIRI()
            throws Exception {
        UseOfReservedVocabularyForOntologyIRI testSubject0 = new UseOfReservedVocabularyForOntologyIRI(
                Utils.getMockOntology());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfReservedVocabularyForVersionIRI()
            throws Exception {
        UseOfReservedVocabularyForVersionIRI testSubject0 = new UseOfReservedVocabularyForVersionIRI(
                Utils.getMockOntology());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom()
            throws Exception {
        UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom testSubject0 = new UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(
                Utils.getMockOntology(), mock(OWLSubDataPropertyOfAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        IRI result1 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result2 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result3 = testSubject0.getImportsClosure();
        OWLAxiom result4 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfUndeclaredAnnotationProperty() throws Exception {
        UseOfUndeclaredAnnotationProperty testSubject0 = new UseOfUndeclaredAnnotationProperty(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLAnnotation.class), mock(OWLAnnotationProperty.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLAnnotationProperty result1 = testSubject0.getOWLAnnotationProperty();
        OWLAnnotation result2 = testSubject0.getOWLAnnotation();
        IRI result3 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result4 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result5 = testSubject0.getImportsClosure();
        OWLAxiom result6 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfUndeclaredClass() throws Exception {
        UseOfUndeclaredClass testSubject0 = new UseOfUndeclaredClass(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLClass.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLClass result1 = testSubject0.getOWLClass();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfUndeclaredDataProperty() throws Exception {
        UseOfUndeclaredDataProperty testSubject0 = new UseOfUndeclaredDataProperty(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDataProperty.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLDataProperty result1 = testSubject0.getOWLDataProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfUndeclaredDatatype() throws Exception {
        UseOfUndeclaredDatatype testSubject0 = new UseOfUndeclaredDatatype(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDatatype.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        OWLDatatype result1 = testSubject0.getDatatype();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfUndeclaredObjectProperty() throws Exception {
        UseOfUndeclaredObjectProperty testSubject0 = new UseOfUndeclaredObjectProperty(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLObjectProperty.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2DLProfileViolationVisitor.class));
        OWLObjectProperty result1 = testSubject0.getOWLObjectProperty();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }

    @Test
    public void shouldTestUseOfUnknownDatatype() throws Exception {
        UseOfUnknownDatatype testSubject0 = new UseOfUnknownDatatype(
                Utils.getMockOntology(), mock(OWLAxiom.class),
                mock(OWLDatatype.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWL2ProfileViolationVisitor.class));
        OWLDatatype result1 = testSubject0.getDatatype();
        IRI result2 = testSubject0.getDocumentIRI(new OWLOntologyID());
        OWLOntologyID result3 = testSubject0.getOntologyID();
        Set<OWLOntologyID> result4 = testSubject0.getImportsClosure();
        OWLAxiom result5 = testSubject0.getAxiom();
    }
}
