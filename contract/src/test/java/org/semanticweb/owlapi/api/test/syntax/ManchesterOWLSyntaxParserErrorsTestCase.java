package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

/**
 * Some tests that ensure the correct token and token position are returned when errors are
 * encountered.
 *
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         01/04/2014
 */
class ManchesterOWLSyntaxParserErrorsTestCase extends TestBase {

    protected @Nonnull OWLEntityChecker entityChecker = mock(OWLEntityChecker.class);
    private ParserWrapper parser;

    @BeforeEach
    void setUp() {
        OWLClass cls = mock(OWLClass.class);
        when(entityChecker.getOWLClass("C")).thenReturn(cls);
        OWLClass clsC1 = mock(OWLClass.class);
        when(entityChecker.getOWLClass("C1")).thenReturn(clsC1);
        OWLObjectProperty oP = mock(OWLObjectProperty.class);
        when(oP.asOWLObjectProperty()).thenReturn(oP);
        when(entityChecker.getOWLObjectProperty("oP")).thenReturn(oP);
        when(entityChecker.getOWLDataProperty("dP")).thenReturn(mock(OWLDataProperty.class));
        when(entityChecker.getOWLAnnotationProperty("aP"))
            .thenReturn(mock(OWLAnnotationProperty.class));
        when(entityChecker.getOWLAnnotationProperty("rdfs:comment")).thenReturn(RDFSComment());
        OWLNamedIndividual ind = mock(OWLNamedIndividual.class);
        when(entityChecker.getOWLIndividual("ind")).thenReturn(ind);
        when(ind.asOWLNamedIndividual()).thenReturn(ind);
        parser = new ParserWrapper();
    }

    @Test
    void unknownClassNameShouldCauseException() {
        checkForExceptionAt("Class: X", 7, "X");
    }

    @Test
    void unknownObjectPropertyNameShouldCauseException() {
        checkForExceptionAt("ObjectProperty: P", 16, "P");
    }

    @Test
    void unknownDataPropertyNameShouldCauseException() {
        checkForExceptionAt("DataProperty: D", 14, "D");
    }

    @Test
    void unknownAnnotationPropertyNameShouldCauseException() {
        checkForExceptionAt("AnnotationProperty: A", 20, "A");
    }

    @Test
    void unknownNamedIndividualShouldCauseException() {
        checkForExceptionAt("Individual: I", 12, "I");
    }

    @Test
    void unknownDatatypeNameShouldCauseException() {
        checkForExceptionAt("Datatype: D", 10, "D");
    }

    @Test
    void missingLiteralTypeShouldCauseException() {
        String input = "Class: C Annotations: rdfs:comment \"comment\"^^";
        checkForExceptionAtEOF(input);
    }

    @Test
    void prematureEOFInDeclarationShouldCauseParseException() {
        checkForExceptionAtEOF("Class: ");
    }

    @Test
    void prematureEOFAfterClassAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Annotations: ");
    }

    @Test
    void prematureEOFAfterSubClassOfShouldCauseParseException() {
        String input = "Class: C SubClassOf: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    void prematureEOFAfterEquivalentToShouldCauseParseException() {
        String input = "Class: C EquivalentTo: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    void prematureEOFAfterDisjointWithShouldCauseParseException() {
        String input = "Class: C DisjointWith: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    void prematureEOFAfterDisjointUnionOfShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointUnionOf: ");
    }

    @Test
    void prematureEOFAfterHasKeyShouldCauseParseException() {
        String input = "Class: C HasKey: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    void prematureEOFAfterClassSubClassOfAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C SubClassOf: Annotations: ");
    }

    @Test
    void prematureEOFAfterClassSubClassOfListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C SubClassOf: C1, ");
    }

    @Test
    void prematureEOFAfterClassEquivalentToAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C EquivalentTo: Annotations: ");
    }

    @Test
    void prematureEOFAfterClassEquivalentToListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C EquivalentTo: C1, ");
    }

    @Test
    void prematureEOFAfterClassDisjointWithAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointWith: Annotations: ");
    }

    @Test
    void prematureEOFAfterClassDisjointWithListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointWith: C1, ");
    }

    @Test
    void prematureEOFAfterClassDisjointUnionOfAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointUnionOf: Annotations: ");
    }

    @Test
    void prematureEOFAfterClassDisjointUnionOfListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointUnionOf: C1, ");
    }

    @Test
    void prematureEOFAfterClassHasKeyAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C HasKey: Annotations: ");
    }

    @Test
    void prematureEOFAfterObjectPropertyShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: ");
    }

    @Test
    void prematureEOFAfterObjectPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Annotations: ");
    }

    @Test
    void prematureEOFAfterObjectPropertyDomainShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Domain: ");
    }

    @Test
    void unrecognizedClassAfterObjectPropertyDomainShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP Domain: X", 27, "X");
    }

    @Test
    void prematureEOFAfterObjectPropertyRangeShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Range: ");
    }

    @Test
    void unrecognizedClassAfterObjectPropertyRangeShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP Range: X", 26, "X");
    }

    @Test
    void prematureEOFAfterObjectPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP SubPropertyOf: ");
    }

    @Test
    void unrecognizedPropertyAfterObjectPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP SubPropertyOf: Q", 34, "Q");
    }

    @Test
    void prematureEOFAfterObjectPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP EquivalentTo: ");
    }

    @Test
    void unrecognizedPropertyAfterObjectPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP EquivalentTo: Q", 33, "Q");
    }

    @Test
    void prematureEOFAfterObjectPropertyDisjointWithShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP DisjointWith: ");
    }

    @Test
    void unrecognizedPropertyAfterObjectPropertyDisjointWithToShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP DisjointWith: Q", 33, "Q");
    }

    @Test
    void prematureEOFAfterObjectPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Characteristics: ");
    }

    @Test
    void unrecognizedCharacteristicAfterObjectPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP Characteristics: Q", 36, "Q");
    }

    @Test
    void prematureEOFAfterObjectPropertyInverseOfShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP InverseOf: ");
    }

    @Test
    void unrecognizedPropertyAfterObjectPropertyInverseOfShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP InverseOf: Q", 30, "Q");
    }

    @Test
    void prematureEOFAfterObjectPropertySubPropertyChainShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP SubPropertyChain: ");
    }

    @Test
    void unrecognizedPropertyAfterObjectPropertySubPropertyChainOfShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP SubPropertyChain: Q", 37, "Q");
    }

    @Test
    void prematureEOFAfterDataPropertyShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: ");
    }

    @Test
    void prematureEOFAfterDataPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Annotations: ");
    }

    @Test
    void unrecognisedAnnotationPropertyAfterDataPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Annotations: X", 30, "X");
    }

    @Test
    void prematureEOFAfterDataPropertyDomainShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Domain: ");
    }

    @Test
    void unrecognizedClassAfterDataPropertyDomainShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Domain: X", 25, "X");
    }

    @Test
    void prematureEOFAfterDataPropertyRangeShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Range: ");
    }

    @Test
    void unrecognizedClassAfterDataPropertyRangeShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Range: X", 24, "X");
    }

    @Test
    void prematureEOFAfterDataPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP SubPropertyOf: ");
    }

    @Test
    void unrecognizedPropertyAfterDataPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP SubPropertyOf: Q", 32, "Q");
    }

    @Test
    void prematureEOFAfterDataPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP EquivalentTo: ");
    }

    @Test
    void unrecognizedPropertyAfterDataPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP EquivalentTo: Q", 31, "Q");
    }

    @Test
    void prematureEOFAfterDataPropertyDisjointWithShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP DisjointWith: ");
    }

    @Test
    void unrecognizedPropertyAfterDataPropertyDisjointWithToShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP DisjointWith: Q", 31, "Q");
    }

    @Test
    void prematureEOFAfterDataPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Characteristics: ");
    }

    @Test
    void unrecognizedCharacteristicAfterDataPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Characteristics: Q", 34, "Q");
    }

    @Test
    void prematureEOFAfterAnnotationPropertyShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: ");
    }

    @Test
    void prematureEOFAfterAnnotationPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP Annotations: ");
    }

    @Test
    void unrecognisedAnnotationPropertyAfterAnnotationPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP Annotations: X", 36, "X");
    }

    @Test
    void prematureEOFAfterAnnotationPropertyDomainShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP Domain: ");
    }

    @Test
    void unrecognizedClassAfterAnnotationPropertyDomainShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP Domain: X", 31, "X");
    }

    @Test
    void prematureEOFAfterAnnotationPropertyRangeShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP Range: ");
    }

    @Test
    void unrecognizedClassAfterAnnotationPropertyRangeShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP Range: X", 30, "X");
    }

    @Test
    void prematureEOFAfterAnnotationPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP SubPropertyOf: ");
    }

    @Test
    void unrecognizedPropertyAfterAnnotationPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP SubPropertyOf: Q", 38, "Q");
    }

    @Test
    void prematureEOFAfterIndividualAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Annotations: ");
    }

    @Test
    void unrecognizedAnnotationPropertyAfterIndividualAnnotationsShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Annotations: Q", 29, "Q");
    }

    @Test
    void prematureEOFAfterIndividualTypesShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Types: ");
    }

    @Test
    void unrecognizedClassAfterIndividualTypesShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Types: X", 23, "X");
    }

    @Test
    void prematureEOFAfterIndividualFactsShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Facts: ");
    }

    @Test
    void prematureEOFAfterIndividualFactsNotShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Facts: not ");
    }

    @Test
    void unrecognizedPropertyAfterIndividualFactsShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Facts: Q", 23, "Q");
    }

    @Test
    void unrecognizedPropertyAfterIndividualFactsNotShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Facts: not Q", 27, "Q");
    }

    @Test
    void prematureEOFAfterIndividualSameAsShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind SameAs: ");
    }

    @Test
    void unrecognizedIndividualAfterIndividualSameAsShouldCauseParseException() {
        checkForExceptionAt("Individual: ind SameAs: Q", 24, "Q");
    }

    @Test
    void prematureEOFAfterIndividualDifferentFromShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind DifferentFrom: ");
    }

    @Test
    void unrecognizedIndividualAfterIndividualDifferentFromShouldCauseParseException() {
        checkForExceptionAt("Individual: ind DifferentFrom: Q", 31, "Q");
    }

    @Test
    void unclosedLiteralShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Annotations: rdfs:comment \"XYZ");
    }

    @Test
    void prematureEOFAfterRuleShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Rule: ");
    }

    @Test
    void prematureEOFAfterRuleAtomShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Rule: oP(?x, ?y) ");
    }

    @Test
    void unrecognisedPropertyAfterRuleShouldCauseParseException() {
        checkForExceptionAt("Class: C Rule: X(?x, ?y) ", 15, "X");
    }

    @Test
    void unmarkedVariableInRuleAtomShouldCauseParseException() {
        checkForExceptionAt("Class: C Rule: oP(x, ?y)", 18, "x");
    }

    private void checkForExceptionAt(String input, int index, String currentToken) {
        try {
            parser.parse(input);
            fail();
        } catch (ParserException ex) {
            assertEquals(index, ex.getStartPos());
            assertEquals(currentToken, ex.getCurrentToken());
            assertTrue(!ex.getTokenSequence().isEmpty());
            assertEquals(currentToken, ex.getTokenSequence().get(0));
        }
    }

    private void checkForExceptionAtEOF(String input) {
        checkForExceptionAt(input, input.length(), ManchesterOWLSyntaxTokenizer.EOFTOKEN);
        String trimmedInput = input.trim();
        checkForExceptionAt(trimmedInput, trimmedInput.length(),
            ManchesterOWLSyntaxTokenizer.EOFTOKEN);
    }

    private class ParserWrapper {

        ParserWrapper() {}

        void parse(String input) {
            ManchesterOWLSyntaxParser actualParser = OWLManager.createManchesterParser();
            actualParser.setOWLEntityChecker(entityChecker);
            actualParser.setStringToParse(input);
            actualParser.parseFrames();
        }
    }
}
