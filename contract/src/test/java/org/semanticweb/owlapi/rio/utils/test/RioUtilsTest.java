/**
 * 
 */
package org.semanticweb.owlapi.rio.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.rio.utils.RioUtils;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings({ "javadoc", "null" })
public class RioUtilsTest {

    private static final ValueFactory vf = ValueFactoryImpl.getInstance();
    private RDFResourceIRI testOwlApiSubjectUri1;
    private RDFResourceIRI testOwlApiPredicateUri1;
    private RDFResourceIRI testOwlApiObjectUri1;
    private RDFLiteral testOwlApiObjectPlainLiteral1;
    private RDFLiteral testOwlApiObjectLangLiteral1;
    private RDFLiteral testOwlApiObjectTypedLiteral1;
    private RDFResourceBlankNode testOwlApiSubjectBNode1;
    private RDFResourceBlankNode testOwlApiObjectBNode1;
    private RDFTriple testOwlApiTripleAllIRI;
    private RDFTriple testOwlApiTriplePlainLiteral;
    private RDFTriple testOwlApiTripleLangLiteral;
    private RDFTriple testOwlApiTripleTypedLiteral;
    private RDFTriple testOwlApiTripleSubjectBNode;
    private RDFTriple testOwlApiTripleObjectBNode;
    private RDFTriple testOwlApiTripleSubjectObjectBNode;
    private URI testSesameSubjectUri1;
    private URI testSesamePredicateUri1;
    private URI testSesameObjectUri1;
    private Literal testSesameObjectPlainLiteral1;
    private Literal testSesameObjectLangLiteral1;
    private Literal testSesameObjectTypedLiteral1;
    private BNode testSesameSubjectBNode1;
    private BNode testSesameObjectBNode1;
    private Statement testSesameTripleAllIRI;
    private Statement testSesameTriplePlainLiteral;
    private Statement testSesameTripleLangLiteral;
    private Statement testSesameTripleTypedLiteral;
    private Statement testSesameTripleSubjectBNode;
    private Statement testSesameTripleObjectBNode;
    private Statement testSesameTripleSubjectObjectBNode;

    @Before
    public void setUp() {
        testOwlApiSubjectUri1 = new RDFResourceIRI(
                IRI.create("urn:test:subject:uri:1"));
        testOwlApiPredicateUri1 = new RDFResourceIRI(
                IRI.create("urn:test:predicate:uri:1"));
        testOwlApiObjectUri1 = new RDFResourceIRI(
                IRI.create("urn:test:object:uri:1"));
        testOwlApiObjectPlainLiteral1 = new RDFLiteral("Test literal", "", null);
        testOwlApiObjectLangLiteral1 = new RDFLiteral("Test literal", "en",
                null);
        testOwlApiObjectTypedLiteral1 = new RDFLiteral("Test literal", null,
                IRI.create("urn:test:datatype:1"));
        testOwlApiSubjectBNode1 = new RDFResourceBlankNode(
                IRI.create("subjectBnode1"));
        testOwlApiObjectBNode1 = new RDFResourceBlankNode(
                IRI.create("objectBnode1"));
        testOwlApiTripleAllIRI = new RDFTriple(testOwlApiSubjectUri1,
                testOwlApiPredicateUri1, testOwlApiObjectUri1);
        testOwlApiTriplePlainLiteral = new RDFTriple(testOwlApiSubjectUri1,
                testOwlApiPredicateUri1, testOwlApiObjectPlainLiteral1);
        testOwlApiTripleLangLiteral = new RDFTriple(testOwlApiSubjectUri1,
                testOwlApiPredicateUri1, testOwlApiObjectLangLiteral1);
        testOwlApiTripleTypedLiteral = new RDFTriple(testOwlApiSubjectUri1,
                testOwlApiPredicateUri1, testOwlApiObjectTypedLiteral1);
        testOwlApiTripleSubjectBNode = new RDFTriple(testOwlApiSubjectBNode1,
                testOwlApiPredicateUri1, testOwlApiObjectUri1);
        testOwlApiTripleObjectBNode = new RDFTriple(testOwlApiSubjectUri1,
                testOwlApiPredicateUri1, testOwlApiObjectBNode1);
        testOwlApiTripleSubjectObjectBNode = new RDFTriple(
                testOwlApiSubjectBNode1, testOwlApiPredicateUri1,
                testOwlApiObjectBNode1);
        testSesameSubjectUri1 = vf
                .createURI("urn:test:subject:uri:1");
        testSesamePredicateUri1 = vf
                .createURI("urn:test:predicate:uri:1");
        testSesameObjectUri1 = vf
                .createURI("urn:test:object:uri:1");
        testSesameObjectPlainLiteral1 = vf
                .createLiteral("Test literal");
        testSesameObjectLangLiteral1 = vf.createLiteral(
                "Test literal", "en");
        testSesameObjectTypedLiteral1 = vf.createLiteral(
                "Test literal",
                vf.createURI("urn:test:datatype:1"));
        testSesameSubjectBNode1 = vf.createBNode("subjectBnode1");
        testSesameObjectBNode1 = vf.createBNode("objectBnode1");
        testSesameTripleAllIRI = vf.createStatement(
                testSesameSubjectUri1, testSesamePredicateUri1,
                testSesameObjectUri1);
        testSesameTriplePlainLiteral = vf.createStatement(
                testSesameSubjectUri1, testSesamePredicateUri1,
                testSesameObjectPlainLiteral1);
        testSesameTripleLangLiteral = vf.createStatement(
                testSesameSubjectUri1, testSesamePredicateUri1,
                testSesameObjectLangLiteral1);
        testSesameTripleTypedLiteral = vf.createStatement(
                testSesameSubjectUri1, testSesamePredicateUri1,
                testSesameObjectTypedLiteral1);
        testSesameTripleSubjectBNode = vf.createStatement(
                testSesameSubjectBNode1, testSesamePredicateUri1,
                testSesameObjectUri1);
        testSesameTripleObjectBNode = vf.createStatement(
                testSesameSubjectUri1, testSesamePredicateUri1,
                testSesameObjectBNode1);
        testSesameTripleSubjectObjectBNode = vf.createStatement(
                testSesameSubjectBNode1, testSesamePredicateUri1,
                testSesameObjectBNode1);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTripleAllIRI() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTripleAllIRI);
        assertEquals(testSesameTripleAllIRI, tripleAsStatement);
    }

    @Test
    public void testTripleBNodeComparisonObject() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTripleObjectBNode);
        assertEquals(testSesameTripleObjectBNode, tripleAsStatement);
    }

    @Test
    public void testTripleBNodeComparisonSubject() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTripleSubjectBNode);
        assertEquals(testSesameTripleSubjectBNode, tripleAsStatement);
    }

    @Test
    public void testTripleBNodeComparisonSubjectAndObject() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTripleSubjectObjectBNode);
        assertEquals(testSesameTripleSubjectObjectBNode, tripleAsStatement);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTripleLangLiteral() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTripleLangLiteral);
        assertEquals(testSesameTripleLangLiteral, tripleAsStatement);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTriplePlainLiteral() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTriplePlainLiteral);
        assertEquals(testSesameTriplePlainLiteral, tripleAsStatement);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTripleTypedLiteral() {
        Statement tripleAsStatement = RioUtils
                .tripleAsStatement(testOwlApiTripleTypedLiteral);
        assertEquals(testSesameTripleTypedLiteral, tripleAsStatement);
    }
}
