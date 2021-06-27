/**
 *
 */
package org.semanticweb.owlapi.rio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.rio.utils.RioUtils;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class RioUtilsTestCase extends TestBase {

    private static final String TEST_LITERAL = "Test literal";
    private static final ValueFactory VF = SimpleValueFactory.getInstance();
    private RDFTriple testOwlApiTripleAllIRI;
    private RDFTriple testOwlApiTriplePlainLiteral;
    private RDFTriple testOwlApiTripleLangLiteral;
    private RDFTriple testOwlApiTripleTypedLiteral;
    private RDFTriple testOwlApiTripleSubjectBNode;
    private RDFTriple testOwlApiTripleObjectBNode;
    private RDFTriple testOwlApiTripleSubjectObjectBNode;
    private Statement testSesameTripleAllIRI;
    private Statement testSesameTriplePlainLiteral;
    private Statement testSesameTripleLangLiteral;
    private Statement testSesameTripleTypedLiteral;
    private Statement testSesameTripleSubjectBNode;
    private Statement testSesameTripleObjectBNode;
    private Statement testSesameTripleSubjectObjectBNode;

    @BeforeEach
    void setUp() {
        RDFResourceIRI testOwlApiSubjectUri1 =
            new RDFResourceIRI(iri("urn:test:subject:uri:1", ""));
        RDFResourceIRI testOwlApiPredicateUri1 =
            new RDFResourceIRI(iri("urn:test:predicate:uri:1", ""));
        RDFResourceIRI testOwlApiObjectUri1 = new RDFResourceIRI(iri("urn:test:object:uri:1", ""));
        RDFLiteral testOwlApiObjectPlainLiteral1 = new RDFLiteral(TEST_LITERAL, "", null);
        RDFLiteral testOwlApiObjectLangLiteral1 = new RDFLiteral(TEST_LITERAL, "en", null);
        RDFLiteral testOwlApiObjectTypedLiteral1 =
            new RDFLiteral(TEST_LITERAL, null, iri("urn:test:datatype:1", ""));
        RDFResourceBlankNode testOwlApiSubjectBNode1 =
            new RDFResourceBlankNode(iri("subjectBnode1", ""), true, false, false);
        RDFResourceBlankNode testOwlApiObjectBNode1 =
            new RDFResourceBlankNode(iri("objectBnode1", ""), true, false, false);
        testOwlApiTripleAllIRI =
            new RDFTriple(testOwlApiSubjectUri1, testOwlApiPredicateUri1, testOwlApiObjectUri1);
        testOwlApiTriplePlainLiteral = new RDFTriple(testOwlApiSubjectUri1, testOwlApiPredicateUri1,
            testOwlApiObjectPlainLiteral1);
        testOwlApiTripleLangLiteral = new RDFTriple(testOwlApiSubjectUri1, testOwlApiPredicateUri1,
            testOwlApiObjectLangLiteral1);
        testOwlApiTripleTypedLiteral = new RDFTriple(testOwlApiSubjectUri1, testOwlApiPredicateUri1,
            testOwlApiObjectTypedLiteral1);
        testOwlApiTripleSubjectBNode =
            new RDFTriple(testOwlApiSubjectBNode1, testOwlApiPredicateUri1, testOwlApiObjectUri1);
        testOwlApiTripleObjectBNode =
            new RDFTriple(testOwlApiSubjectUri1, testOwlApiPredicateUri1, testOwlApiObjectBNode1);
        testOwlApiTripleSubjectObjectBNode =
            new RDFTriple(testOwlApiSubjectBNode1, testOwlApiPredicateUri1, testOwlApiObjectBNode1);
        org.eclipse.rdf4j.model.IRI testSesameSubjectUri1 = VF.createIRI("urn:test:subject:uri:1");
        org.eclipse.rdf4j.model.IRI testSesamePredicateUri1 =
            VF.createIRI("urn:test:predicate:uri:1");
        org.eclipse.rdf4j.model.IRI testSesameObjectUri1 = VF.createIRI("urn:test:object:uri:1");
        Literal testSesameObjectPlainLiteral1 = VF.createLiteral(TEST_LITERAL);
        Literal testSesameObjectLangLiteral1 = VF.createLiteral(TEST_LITERAL, "en");
        Literal testSesameObjectTypedLiteral1 =
            VF.createLiteral(TEST_LITERAL, VF.createIRI("urn:test:datatype:1"));
        BNode testSesameSubjectBNode1 = VF.createBNode("subjectBnode1");
        BNode testSesameObjectBNode1 = VF.createBNode("objectBnode1");
        testSesameTripleAllIRI = VF.createStatement(testSesameSubjectUri1, testSesamePredicateUri1,
            testSesameObjectUri1);
        testSesameTriplePlainLiteral = VF.createStatement(testSesameSubjectUri1,
            testSesamePredicateUri1, testSesameObjectPlainLiteral1);
        testSesameTripleLangLiteral = VF.createStatement(testSesameSubjectUri1,
            testSesamePredicateUri1, testSesameObjectLangLiteral1);
        testSesameTripleTypedLiteral = VF.createStatement(testSesameSubjectUri1,
            testSesamePredicateUri1, testSesameObjectTypedLiteral1);
        testSesameTripleSubjectBNode = VF.createStatement(testSesameSubjectBNode1,
            testSesamePredicateUri1, testSesameObjectUri1);
        testSesameTripleObjectBNode = VF.createStatement(testSesameSubjectUri1,
            testSesamePredicateUri1, testSesameObjectBNode1);
        testSesameTripleSubjectObjectBNode = VF.createStatement(testSesameSubjectBNode1,
            testSesamePredicateUri1, testSesameObjectBNode1);
    }

    @Test
    void testTripleAllIRI() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(testOwlApiTripleAllIRI);
        assertEquals(testSesameTripleAllIRI, tripleAsStatement);
    }

    @Test
    void testTripleBNodeComparisonObject() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(testOwlApiTripleObjectBNode);
        assertEquals(testSesameTripleObjectBNode, tripleAsStatement);
    }

    @Test
    void testTripleBNodeComparisonSubject() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(testOwlApiTripleSubjectBNode);
        assertEquals(testSesameTripleSubjectBNode, tripleAsStatement);
    }

    @Test
    void testTripleBNodeComparisonSubjectAndObject() {
        Statement tripleAsStatement =
            RioUtils.tripleAsStatement(testOwlApiTripleSubjectObjectBNode);
        assertEquals(testSesameTripleSubjectObjectBNode, tripleAsStatement);
    }

    @Test
    void testTripleLangLiteral() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(testOwlApiTripleLangLiteral);
        assertEquals(testSesameTripleLangLiteral, tripleAsStatement);
    }

    @Test
    void testTriplePlainLiteral() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(testOwlApiTriplePlainLiteral);
        assertEquals(testSesameTriplePlainLiteral, tripleAsStatement);
    }

    @Test
    void testTripleTypedLiteral() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(testOwlApiTripleTypedLiteral);
        assertEquals(testSesameTripleTypedLiteral, tripleAsStatement);
    }
}
