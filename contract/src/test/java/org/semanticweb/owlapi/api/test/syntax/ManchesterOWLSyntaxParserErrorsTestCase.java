package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

/**
 * Some tests that ensure the correct token and token position are returned when
 * errors are encountered.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 01/04/2014
 */
@SuppressWarnings({ "javadoc", "null" })
@RunWith(MockitoJUnitRunner.class)
public class ManchesterOWLSyntaxParserErrorsTestCase {

    @Mock
    protected @Nonnull OWLEntityChecker entityChecker;
    private ParserWrapper parser;

    @Before
    public void setUp() {
        OWLDataFactory dataFactory = new OWLDataFactoryImpl();
        OWLClass cls = mock(OWLClass.class);
        when(cls.getIRI()).thenReturn(mock(IRI.class));
        when(entityChecker.getOWLClass("C")).thenReturn(cls);
        OWLClass clsC1 = mock(OWLClass.class);
        when(clsC1.getIRI()).thenReturn(mock(IRI.class));
        when(entityChecker.getOWLClass("C1")).thenReturn(clsC1);
        OWLObjectProperty oP = mock(OWLObjectProperty.class);
        when(oP.getIRI()).thenReturn(mock(IRI.class));
        when(oP.asOWLObjectProperty()).thenReturn(oP);
        when(entityChecker.getOWLObjectProperty("oP")).thenReturn(oP);
        when(entityChecker.getOWLDataProperty("dP")).thenReturn(mock(OWLDataProperty.class));
        when(entityChecker.getOWLAnnotationProperty("aP")).thenReturn(mock(OWLAnnotationProperty.class));
        when(entityChecker.getOWLAnnotationProperty("rdfs:comment")).thenReturn(dataFactory.getRDFSComment());
        OWLNamedIndividual ind = mock(OWLNamedIndividual.class);
        when(entityChecker.getOWLIndividual("ind")).thenReturn(ind);
        when(ind.asOWLNamedIndividual()).thenReturn(ind);
        parser = new ParserWrapper();
    }

    @Test
    public void unknownClassNameShouldCauseException() {
        checkForExceptionAt("Class: X", 7, "X");
    }

    @Test
    public void unknownObjectPropertyNameShouldCauseException() {
        checkForExceptionAt("ObjectProperty: P", 16, "P");
    }

    @Test
    public void unknownDataPropertyNameShouldCauseException() {
        checkForExceptionAt("DataProperty: D", 14, "D");
    }

    @Test
    public void unknownAnnotationPropertyNameShouldCauseException() {
        checkForExceptionAt("AnnotationProperty: A", 20, "A");
    }

    @Test
    public void unknownNamedIndividualShouldCauseException() {
        checkForExceptionAt("Individual: I", 12, "I");
    }

    @Test
    public void unknownDatatypeNameShouldCauseException() {
        checkForExceptionAt("Datatype: D", 10, "D");
    }

    @Test
    public void missingLiteralTypeShouldCauseException() {
        String input = "Class: C Annotations: rdfs:comment \"comment\"^^";
        checkForExceptionAtEOF(input);
    }

    @Test
    public void prematureEOFInDeclarationShouldCauseParseException() {
        checkForExceptionAtEOF("Class: ");
    }

    @Test
    public void prematureEOFAfterClassAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Annotations: ");
    }

    @Test
    public void prematureEOFAfterSubClassOfShouldCauseParseException() {
        String input = "Class: C SubClassOf: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    public void prematureEOFAfterEquivalentToShouldCauseParseException() {
        String input = "Class: C EquivalentTo: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    public void prematureEOFAfterDisjointWithShouldCauseParseException() {
        String input = "Class: C DisjointWith: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    public void prematureEOFAfterDisjointUnionOfShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointUnionOf: ");
    }

    @Test
    public void prematureEOFAfterHasKeyShouldCauseParseException() {
        String input = "Class: C HasKey: ";
        checkForExceptionAtEOF(input);
    }

    @Test
    public void prematureEOFAfterClassSubClassOfAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C SubClassOf: Annotations: ");
    }

    @Test
    public void prematureEOFAfterClassSubClassOfListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C SubClassOf: C1, ");
    }

    @Test
    public void prematureEOFAfterClassEquivalentToAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C EquivalentTo: Annotations: ");
    }

    @Test
    public void prematureEOFAfterClassEquivalentToListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C EquivalentTo: C1, ");
    }

    @Test
    public void prematureEOFAfterClassDisjointWithAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointWith: Annotations: ");
    }

    @Test
    public void prematureEOFAfterClassDisjointWithListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointWith: C1, ");
    }

    @Test
    public void prematureEOFAfterClassDisjointUnionOfAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointUnionOf: Annotations: ");
    }

    @Test
    public void prematureEOFAfterClassDisjointUnionOfListShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C DisjointUnionOf: C1, ");
    }

    @Test
    public void prematureEOFAfterClassHasKeyAxiomAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C HasKey: Annotations: ");
    }

    @Test
    public void prematureEOFAfterObjectPropertyShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: ");
    }

    @Test
    public void prematureEOFAfterObjectPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Annotations: ");
    }

    @Test
    public void prematureEOFAfterObjectPropertyDomainShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Domain: ");
    }

    @Test
    public void unrecognizedClassAfterObjectPropertyDomainShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP Domain: X", 27, "X");
    }

    @Test
    public void prematureEOFAfterObjectPropertyRangeShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Range: ");
    }

    @Test
    public void unrecognizedClassAfterObjectPropertyRangeShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP Range: X", 26, "X");
    }

    @Test
    public void prematureEOFAfterObjectPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP SubPropertyOf: ");
    }

    @Test
    public void unrecognizedPropertyAfterObjectPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP SubPropertyOf: Q", 34, "Q");
    }

    @Test
    public void prematureEOFAfterObjectPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP EquivalentTo: ");
    }

    @Test
    public void unrecognizedPropertyAfterObjectPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP EquivalentTo: Q", 33, "Q");
    }

    @Test
    public void prematureEOFAfterObjectPropertyDisjointWithShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP DisjointWith: ");
    }

    @Test
    public void unrecognizedPropertyAfterObjectPropertyDisjointWithToShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP DisjointWith: Q", 33, "Q");
    }

    @Test
    public void prematureEOFAfterObjectPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP Characteristics: ");
    }

    @Test
    public void unrecognizedCharacteristicAfterObjectPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP Characteristics: Q", 36, "Q");
    }

    @Test
    public void prematureEOFAfterObjectPropertyInverseOfShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP InverseOf: ");
    }

    @Test
    public void unrecognizedPropertyAfterObjectPropertyInverseOfShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP InverseOf: Q", 30, "Q");
    }

    @Test
    public void prematureEOFAfterObjectPropertySubPropertyChainShouldCauseParseException() {
        checkForExceptionAtEOF("ObjectProperty: oP SubPropertyChain: ");
    }

    @Test
    public void unrecognizedPropertyAfterObjectPropertySubPropertyChainOfShouldCauseParseException() {
        checkForExceptionAt("ObjectProperty: oP SubPropertyChain: Q", 37, "Q");
    }

    @Test
    public void prematureEOFAfterDataPropertyShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: ");
    }

    @Test
    public void prematureEOFAfterDataPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Annotations: ");
    }

    @Test
    public void unrecognisedAnnotationPropertyAfterDataPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Annotations: X", 30, "X");
    }

    @Test
    public void prematureEOFAfterDataPropertyDomainShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Domain: ");
    }

    @Test
    public void unrecognizedClassAfterDataPropertyDomainShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Domain: X", 25, "X");
    }

    @Test
    public void prematureEOFAfterDataPropertyRangeShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Range: ");
    }

    @Test
    public void unrecognizedClassAfterDataPropertyRangeShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Range: X", 24, "X");
    }

    @Test
    public void prematureEOFAfterDataPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP SubPropertyOf: ");
    }

    @Test
    public void unrecognizedPropertyAfterDataPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP SubPropertyOf: Q", 32, "Q");
    }

    @Test
    public void prematureEOFAfterDataPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP EquivalentTo: ");
    }

    @Test
    public void unrecognizedPropertyAfterDataPropertyEquivalentToShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP EquivalentTo: Q", 31, "Q");
    }

    @Test
    public void prematureEOFAfterDataPropertyDisjointWithShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP DisjointWith: ");
    }

    @Test
    public void unrecognizedPropertyAfterDataPropertyDisjointWithToShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP DisjointWith: Q", 31, "Q");
    }

    @Test
    public void prematureEOFAfterDataPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAtEOF("DataProperty: dP Characteristics: ");
    }

    @Test
    public void unrecognizedCharacteristicAfterDataPropertyCharacteristicsShouldCauseParseException() {
        checkForExceptionAt("DataProperty: dP Characteristics: Q", 34, "Q");
    }

    @Test
    public void prematureEOFAfterAnnotationPropertyShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: ");
    }

    @Test
    public void prematureEOFAfterAnnotationPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP Annotations: ");
    }

    @Test
    public void unrecognisedAnnotationPropertyAfterAnnotationPropertyAnnotationsShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP Annotations: X", 36, "X");
    }

    @Test
    public void prematureEOFAfterAnnotationPropertyDomainShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP Domain: ");
    }

    @Test
    public void unrecognizedClassAfterAnnotationPropertyDomainShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP Domain: X", 31, "X");
    }

    @Test
    public void prematureEOFAfterAnnotationPropertyRangeShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP Range: ");
    }

    @Test
    public void unrecognizedClassAfterAnnotationPropertyRangeShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP Range: X", 30, "X");
    }

    @Test
    public void prematureEOFAfterAnnotationPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAtEOF("AnnotationProperty: aP SubPropertyOf: ");
    }

    @Test
    public void unrecognizedPropertyAfterAnnotationPropertySubPropertyOfShouldCauseParseException() {
        checkForExceptionAt("AnnotationProperty: aP SubPropertyOf: Q", 38, "Q");
    }

    @Test
    public void prematureEOFAfterIndividualAnnotationsShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Annotations: ");
    }

    @Test
    public void unrecognizedAnnotationPropertyAfterIndividualAnnotationsShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Annotations: Q", 29, "Q");
    }

    @Test
    public void prematureEOFAfterIndividualTypesShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Types: ");
    }

    @Test
    public void unrecognizedClassAfterIndividualTypesShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Types: X", 23, "X");
    }

    @Test
    public void prematureEOFAfterIndividualFactsShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Facts: ");
    }

    @Test
    public void prematureEOFAfterIndividualFactsNotShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind Facts: not ");
    }

    @Test
    public void unrecognizedPropertyAfterIndividualFactsShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Facts: Q", 23, "Q");
    }

    @Test
    public void unrecognizedPropertyAfterIndividualFactsNotShouldCauseParseException() {
        checkForExceptionAt("Individual: ind Facts: not Q", 27, "Q");
    }

    @Test
    public void prematureEOFAfterIndividualSameAsShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind SameAs: ");
    }

    @Test
    public void unrecognizedIndividualAfterIndividualSameAsShouldCauseParseException() {
        checkForExceptionAt("Individual: ind SameAs: Q", 24, "Q");
    }

    @Test
    public void prematureEOFAfterIndividualDifferentFromShouldCauseParseException() {
        checkForExceptionAtEOF("Individual: ind DifferentFrom: ");
    }

    @Test
    public void unrecognizedIndividualAfterIndividualDifferentFromShouldCauseParseException() {
        checkForExceptionAt("Individual: ind DifferentFrom: Q", 31, "Q");
    }

    @Test
    public void unclosedLiteralShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Annotations: rdfs:comment \"XYZ");
    }

    @Test
    public void prematureEOFAfterRuleShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Rule: ");
    }

    @Test
    public void prematureEOFAfterRuleAtomShouldCauseParseException() {
        checkForExceptionAtEOF("Class: C Rule: oP(?x, ?y) ");
    }

    @Test
    public void unrecognisedPropertyAfterRuleShouldCauseParseException() {
        checkForExceptionAt("Class: C Rule: X(?x, ?y) ", 15, "X");
    }

    @Test
    public void unmarkedVariableInRuleAtomShouldCauseParseException() {
        checkForExceptionAt("Class: C Rule: oP(x, ?y)", 18, "x");
    }

    private void checkForExceptionAt(String input, int index, String currentToken) {
        try {
            parser.parse(input);
            fail();
        } catch (ParserException e) {
            assertEquals(index, e.getStartPos());
            assertEquals(currentToken, e.getCurrentToken());
            assertTrue(!e.getTokenSequence().isEmpty());
            assertEquals(currentToken, e.getTokenSequence().get(0));
        }
    }

    private void checkForExceptionAtEOF(String input) {
        checkForExceptionAt(input, input.length(), ManchesterOWLSyntaxTokenizer.EOF);
        String trimmedInput = input.trim();
        checkForExceptionAt(trimmedInput, trimmedInput.length(), ManchesterOWLSyntaxTokenizer.EOF);
    }

    private class ParserWrapper {

        ParserWrapper() {}

        public void parse(String input) {
            ManchesterOWLSyntaxParser actualParser = OWLManager.createManchesterParser();
            actualParser.setOWLEntityChecker(entityChecker);
            actualParser.setStringToParse(input);
            actualParser.parseFrames();
        }
    }
}
