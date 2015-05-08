/**
 * 
 */
package org.semanticweb.owlapi.rio;

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
@SuppressWarnings({ "javadoc" })
public class RioUtilsTestCase {

    private static final ValueFactory VF = ValueFactoryImpl.getInstance();
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

    @Before
    public void setUp() {
        RDFResourceIRI testOwlApiSubjectUri1 = new RDFResourceIRI(IRI.create(
        "urn:test:subject:uri:1"));
        RDFResourceIRI testOwlApiPredicateUri1 = new RDFResourceIRI(IRI.create(
        "urn:test:predicate:uri:1"));
        RDFResourceIRI testOwlApiObjectUri1 = new RDFResourceIRI(IRI.create(
        "urn:test:object:uri:1"));
        RDFLiteral testOwlApiObjectPlainLiteral1 = new RDFLiteral(
        "Test literal", "", null);
        RDFLiteral testOwlApiObjectLangLiteral1 = new RDFLiteral("Test literal",
        "en", null);
        RDFLiteral testOwlApiObjectTypedLiteral1 = new RDFLiteral(
        "Test literal", null, IRI.create("urn:test:datatype:1"));
        RDFResourceBlankNode testOwlApiSubjectBNode1 = new RDFResourceBlankNode(
        IRI.create("subjectBnode1"));
        RDFResourceBlankNode testOwlApiObjectBNode1 = new RDFResourceBlankNode(
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
        URI testSesameSubjectUri1 = VF.createURI("urn:test:subject:uri:1");
        URI testSesamePredicateUri1 = VF.createURI("urn:test:predicate:uri:1");
        URI testSesameObjectUri1 = VF.createURI("urn:test:object:uri:1");
        Literal testSesameObjectPlainLiteral1 = VF.createLiteral(
        "Test literal");
        Literal testSesameObjectLangLiteral1 = VF.createLiteral("Test literal",
        "en");
        Literal testSesameObjectTypedLiteral1 = VF.createLiteral("Test literal",
        VF.createURI("urn:test:datatype:1"));
        BNode testSesameSubjectBNode1 = VF.createBNode("subjectBnode1");
        BNode testSesameObjectBNode1 = VF.createBNode("objectBnode1");
        testSesameTripleAllIRI = VF.createStatement(testSesameSubjectUri1,
        testSesamePredicateUri1, testSesameObjectUri1);
        testSesameTriplePlainLiteral = VF.createStatement(testSesameSubjectUri1,
        testSesamePredicateUri1, testSesameObjectPlainLiteral1);
        testSesameTripleLangLiteral = VF.createStatement(testSesameSubjectUri1,
        testSesamePredicateUri1, testSesameObjectLangLiteral1);
        testSesameTripleTypedLiteral = VF.createStatement(testSesameSubjectUri1,
        testSesamePredicateUri1, testSesameObjectTypedLiteral1);
        testSesameTripleSubjectBNode = VF.createStatement(
        testSesameSubjectBNode1, testSesamePredicateUri1, testSesameObjectUri1);
        testSesameTripleObjectBNode = VF.createStatement(testSesameSubjectUri1,
        testSesamePredicateUri1, testSesameObjectBNode1);
        testSesameTripleSubjectObjectBNode = VF.createStatement(
        testSesameSubjectBNode1, testSesamePredicateUri1,
        testSesameObjectBNode1);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTripleAllIRI() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTripleAllIRI);
        assertEquals(testSesameTripleAllIRI, tripleAsStatement);
    }

    @Test
    public void testTripleBNodeComparisonObject() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTripleObjectBNode);
        assertEquals(testSesameTripleObjectBNode, tripleAsStatement);
    }

    @Test
    public void testTripleBNodeComparisonSubject() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTripleSubjectBNode);
        assertEquals(testSesameTripleSubjectBNode, tripleAsStatement);
    }

    @Test
    public void testTripleBNodeComparisonSubjectAndObject() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTripleSubjectObjectBNode);
        assertEquals(testSesameTripleSubjectObjectBNode, tripleAsStatement);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTripleLangLiteral() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTripleLangLiteral);
        assertEquals(testSesameTripleLangLiteral, tripleAsStatement);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTriplePlainLiteral() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTriplePlainLiteral);
        assertEquals(testSesameTriplePlainLiteral, tripleAsStatement);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     */
    @Test
    public void testTripleTypedLiteral() {
        Statement tripleAsStatement = RioUtils.tripleAsStatement(
        testOwlApiTripleTypedLiteral);
        assertEquals(testSesameTripleTypedLiteral, tripleAsStatement);
    }
}
