package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParser;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxTokenizer;
import org.semanticweb.owlapi.manchestersyntax.parser.ParserException;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Some tests that ensure the correct token and token position are returned when errors are
 * encountered.
 *
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         01/04/2014
 */
class ManchesterOWLSyntaxParserErrorsTestCase extends TestBase {

    protected OWLEntityChecker entityChecker = mock(OWLEntityChecker.class);
    private ParserWrapper parser;

    @BeforeEach
    void setUp() {
        OWLClass cls = mock(OWLClass.class);
        when(cls.isOWLClass()).thenReturn(true);
        when(cls.asOWLClass()).thenReturn(cls);
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

    @ParameterizedTest
    @CsvSource({
        // unknownClassNameShouldCauseException
        "Class: X,7,X",
        // unknownObjectPropertyNameShouldCauseException
        "ObjectProperty: P,16,P",
        // unknownDataPropertyNameShouldCauseException
        "DataProperty: D,14,D",
        // unknownAnnotationPropertyNameShouldCauseException
        "AnnotationProperty: A,20,A",
        // unknownNamedIndividualShouldCauseException
        "Individual: I,12,I",
        // unknownDatatypeNameShouldCauseException
        "Datatype: D,10,D",
        // unrecognizedClassAfterObjectPropertyDomainShouldCauseParseException
        "ObjectProperty: oP Domain: X,27,X",
        // unrecognizedClassAfterObjectPropertyRangeShouldCauseParseException
        "ObjectProperty: oP Range: X,26,X",
        // unrecognizedPropertyAfterObjectPropertySubPropertyOfShouldCauseParseException
        "ObjectProperty: oP SubPropertyOf: Q,34,Q",
        // unrecognizedPropertyAfterObjectPropertyEquivalentToShouldCauseParseException
        "ObjectProperty: oP EquivalentTo: Q,33,Q",
        // unrecognizedPropertyAfterObjectPropertyDisjointWithToShouldCauseParseException
        "ObjectProperty: oP DisjointWith: Q,33,Q",
        // unrecognizedCharacteristicAfterObjectPropertyCharacteristicsShouldCauseParseException
        "ObjectProperty: oP Characteristics: Q,36,Q",
        // unrecognizedPropertyAfterObjectPropertyInverseOfShouldCauseParseException
        "ObjectProperty: oP InverseOf: Q,30,Q",
        // unrecognizedPropertyAfterObjectPropertySubPropertyChainOfShouldCauseParseException
        "ObjectProperty: oP SubPropertyChain: Q,37,Q",
        // unrecognisedAnnotationPropertyAfterDataPropertyAnnotationsShouldCauseParseException
        "DataProperty: dP Annotations: X,30,X",
        // unrecognizedClassAfterDataPropertyDomainShouldCauseParseException
        "DataProperty: dP Domain: X,25,X",
        // unrecognizedClassAfterDataPropertyRangeShouldCauseParseException
        "DataProperty: dP Range: X,24,X",
        // unrecognizedPropertyAfterDataPropertySubPropertyOfShouldCauseParseException
        "DataProperty: dP SubPropertyOf: Q,32,Q",
        // unrecognizedPropertyAfterDataPropertyEquivalentToShouldCauseParseException
        "DataProperty: dP EquivalentTo: Q,31,Q",
        // unrecognizedPropertyAfterDataPropertyDisjointWithToShouldCauseParseException
        "DataProperty: dP DisjointWith: Q,31,Q",
        // unrecognizedCharacteristicAfterDataPropertyCharacteristicsShouldCauseParseException
        "DataProperty: dP Characteristics: Q,34,Q",
        // unrecognisedAnnotationPropertyAfterAnnotationPropertyAnnotationsShouldCauseParseException
        "AnnotationProperty: aP Annotations: X,36,X",
        // unrecognizedClassAfterAnnotationPropertyDomainShouldCauseParseException
        "AnnotationProperty: aP Domain: X,31,X",
        // unrecognizedClassAfterAnnotationPropertyRangeShouldCauseParseException
        "AnnotationProperty: aP Range: X,30,X",
        // unrecognizedPropertyAfterAnnotationPropertySubPropertyOfShouldCauseParseException
        "AnnotationProperty: aP SubPropertyOf: Q,38,Q",
        // unrecognizedAnnotationPropertyAfterIndividualAnnotationsShouldCauseParseException
        "Individual: ind Annotations: Q,29,Q",
        // unrecognizedClassAfterIndividualTypesShouldCauseParseException
        "Individual: ind Types: X,23,X",
        // unrecognizedPropertyAfterIndividualFactsShouldCauseParseException
        "Individual: ind Facts: Q,23,Q",
        // unrecognizedPropertyAfterIndividualFactsNotShouldCauseParseException
        "Individual: ind Facts: not Q,27,Q",
        // unrecognizedIndividualAfterIndividualSameAsShouldCauseParseException
        "Individual: ind SameAs: Q,24,Q",
        // unrecognizedIndividualAfterIndividualDifferentFromShouldCauseParseException
        "Individual: ind DifferentFrom: Q,31,Q"})
    void checkForExceptionAt(String input, int index, String currentToken) {
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

    @ParameterizedTest
    @CsvSource(delimiter = '\t', value = {
        // unrecognisedPropertyAfterRuleShouldCauseParseException
        "Class: C Rule: X(?x, ?y) \t15\tX",
        // unmarkedVariableInRuleAtomShouldCauseParseException
        "Class: C Rule: oP(x, ?y)\t18\tx"})
    void checkForExceptionAt1(String input, int index, String currentToken) {
        checkForExceptionAt(input, index, currentToken);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        // missingLiteralTypeShouldCauseException
        "Class: C Annotations: rdfs:comment \"comment\"^^",
        // prematureEOFInDeclarationShouldCauseParseException
        "Class: ",
        // prematureEOFAfterClassAnnotationsShouldCauseParseException
        "Class: C Annotations: ",
        // prematureEOFAfterSubClassOfShouldCauseParseException
        "Class: C SubClassOf: ",
        // prematureEOFAfterEquivalentToShouldCauseParseException
        "Class: C EquivalentTo: ",
        // prematureEOFAfterDisjointWithShouldCauseParseException
        "Class: C DisjointWith: ",
        // prematureEOFAfterDisjointUnionOfShouldCauseParseException
        "Class: C DisjointUnionOf: ",
        // prematureEOFAfterHasKeyShouldCauseParseException
        "Class: C HasKey: ",
        // prematureEOFAfterClassSubClassOfAxiomAnnotationsShouldCauseParseException
        "Class: C SubClassOf: Annotations: ",
        // prematureEOFAfterClassSubClassOfListShouldCauseParseException
        "Class: C SubClassOf: C1, ",
        // prematureEOFAfterClassEquivalentToAxiomAnnotationsShouldCauseParseException
        "Class: C EquivalentTo: Annotations: ",
        // prematureEOFAfterClassEquivalentToListShouldCauseParseException
        "Class: C EquivalentTo: C1, ",
        // prematureEOFAfterClassDisjointWithAxiomAnnotationsShouldCauseParseException
        "Class: C DisjointWith: Annotations: ",
        // prematureEOFAfterClassDisjointWithListShouldCauseParseException
        "Class: C DisjointWith: C1, ",
        // prematureEOFAfterClassDisjointUnionOfAxiomAnnotationsShouldCauseParseException
        "Class: C DisjointUnionOf: Annotations: ",
        // prematureEOFAfterClassDisjointUnionOfListShouldCauseParseException
        "Class: C DisjointUnionOf: C1, ",
        // prematureEOFAfterClassHasKeyAxiomAnnotationsShouldCauseParseException
        "Class: C HasKey: Annotations: ",
        // prematureEOFAfterObjectPropertyShouldCauseParseException
        "ObjectProperty: ",
        // prematureEOFAfterObjectPropertyAnnotationsShouldCauseParseException
        "ObjectProperty: oP Annotations: ",
        // prematureEOFAfterObjectPropertyDomainShouldCauseParseException
        "ObjectProperty: oP Domain: ",
        // prematureEOFAfterObjectPropertyRangeShouldCauseParseException
        "ObjectProperty: oP Range: ",
        // prematureEOFAfterObjectPropertySubPropertyOfShouldCauseParseException
        "ObjectProperty: oP SubPropertyOf: ",
        // prematureEOFAfterObjectPropertyEquivalentToShouldCauseParseException
        "ObjectProperty: oP EquivalentTo: ",
        // prematureEOFAfterObjectPropertyDisjointWithShouldCauseParseException
        "ObjectProperty: oP DisjointWith: ",
        // prematureEOFAfterObjectPropertyCharacteristicsShouldCauseParseException
        "ObjectProperty: oP Characteristics: ",
        // prematureEOFAfterObjectPropertyInverseOfShouldCauseParseException
        "ObjectProperty: oP InverseOf: ",
        // prematureEOFAfterObjectPropertySubPropertyChainShouldCauseParseException
        "ObjectProperty: oP SubPropertyChain: ",
        // prematureEOFAfterDataPropertyShouldCauseParseException
        "DataProperty: ",
        // prematureEOFAfterDataPropertyAnnotationsShouldCauseParseException
        "DataProperty: dP Annotations: ",
        // prematureEOFAfterDataPropertyDomainShouldCauseParseException
        "DataProperty: dP Domain: ",
        // prematureEOFAfterDataPropertyRangeShouldCauseParseException
        "DataProperty: dP Range: ",
        // prematureEOFAfterDataPropertySubPropertyOfShouldCauseParseException
        "DataProperty: dP SubPropertyOf: ",
        // prematureEOFAfterDataPropertyEquivalentToShouldCauseParseException
        "DataProperty: dP EquivalentTo: ",
        // prematureEOFAfterDataPropertyDisjointWithShouldCauseParseException
        "DataProperty: dP DisjointWith: ",
        // prematureEOFAfterDataPropertyCharacteristicsShouldCauseParseException
        "DataProperty: dP Characteristics: ",
        // prematureEOFAfterAnnotationPropertyAnnotationsShouldCauseParseException
        "AnnotationProperty: aP Annotations: ",
        // prematureEOFAfterAnnotationPropertyDomainShouldCauseParseException
        "AnnotationProperty: aP Domain: ",
        // prematureEOFAfterAnnotationPropertyShouldCauseParseException
        "AnnotationProperty: ",
        // prematureEOFAfterAnnotationPropertyRangeShouldCauseParseException
        "AnnotationProperty: aP Range: ",
        // prematureEOFAfterAnnotationPropertySubPropertyOfShouldCauseParseException
        "AnnotationProperty: aP SubPropertyOf: ",
        // prematureEOFAfterIndividualAnnotationsShouldCauseParseException
        "Individual: ind Annotations: ",
        // prematureEOFAfterIndividualTypesShouldCauseParseException
        "Individual: ind Types: ",
        // prematureEOFAfterIndividualFactsShouldCauseParseException
        "Individual: ind Facts: ",
        // prematureEOFAfterIndividualFactsNotShouldCauseParseException
        "Individual: ind Facts: not ",
        // prematureEOFAfterIndividualSameAsShouldCauseParseException
        "Individual: ind SameAs: ",
        // prematureEOFAfterIndividualDifferentFromShouldCauseParseException
        "Individual: ind DifferentFrom: ",
        // unclosedLiteralShouldCauseParseException
        "Class: C Annotations: rdfs:comment \"XYZ",
        // prematureEOFAfterRuleShouldCauseParseException
        "Class: C Rule: ",
        // prematureEOFAfterRuleAtomShouldCauseParseException
        "Class: C Rule: oP(?x, ?y) "})
    void checkForExceptionAtEOF(String input) {
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
