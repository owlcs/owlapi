/**
 * 
 */
package org.semanticweb.owlapi.rio.utils.test;

import org.junit.After;
import org.junit.Assert;
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
 * 
 */
public class RioUtilsTest
{
    private static ValueFactory vf = ValueFactoryImpl.getInstance();
    
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
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        this.testOwlApiSubjectUri1 = new RDFResourceIRI(IRI.create("urn:test:subject:uri:1"));
        this.testOwlApiPredicateUri1 = new RDFResourceIRI(IRI.create("urn:test:predicate:uri:1"));
        this.testOwlApiObjectUri1 = new RDFResourceIRI(IRI.create("urn:test:object:uri:1"));
        this.testOwlApiObjectPlainLiteral1 = new RDFLiteral("Test literal", "");
        this.testOwlApiObjectLangLiteral1 = new RDFLiteral("Test literal", "en");
        this.testOwlApiObjectTypedLiteral1 = new RDFLiteral("Test literal", IRI.create("urn:test:datatype:1"));
        this.testOwlApiSubjectBNode1 = new RDFResourceBlankNode(IRI.create("subjectBnode1"));
        this.testOwlApiObjectBNode1 = new RDFResourceBlankNode(IRI.create("objectBnode1"));
        
        this.testOwlApiTripleAllIRI =
                new RDFTriple(this.testOwlApiSubjectUri1, this.testOwlApiPredicateUri1, this.testOwlApiObjectUri1);
        this.testOwlApiTriplePlainLiteral =
                new RDFTriple(this.testOwlApiSubjectUri1, this.testOwlApiPredicateUri1,
                        this.testOwlApiObjectPlainLiteral1);
        this.testOwlApiTripleLangLiteral =
                new RDFTriple(this.testOwlApiSubjectUri1, this.testOwlApiPredicateUri1,
                        this.testOwlApiObjectLangLiteral1);
        this.testOwlApiTripleTypedLiteral =
                new RDFTriple(this.testOwlApiSubjectUri1, this.testOwlApiPredicateUri1,
                        this.testOwlApiObjectTypedLiteral1);
        this.testOwlApiTripleSubjectBNode =
                new RDFTriple(this.testOwlApiSubjectBNode1, this.testOwlApiPredicateUri1, this.testOwlApiObjectUri1);
        this.testOwlApiTripleObjectBNode =
                new RDFTriple(this.testOwlApiSubjectUri1, this.testOwlApiPredicateUri1, this.testOwlApiObjectBNode1);
        this.testOwlApiTripleSubjectObjectBNode =
                new RDFTriple(this.testOwlApiSubjectBNode1, this.testOwlApiPredicateUri1, this.testOwlApiObjectBNode1);
        
        this.testSesameSubjectUri1 = RioUtilsTest.vf.createURI("urn:test:subject:uri:1");
        this.testSesamePredicateUri1 = RioUtilsTest.vf.createURI("urn:test:predicate:uri:1");
        this.testSesameObjectUri1 = RioUtilsTest.vf.createURI("urn:test:object:uri:1");
        this.testSesameObjectPlainLiteral1 = RioUtilsTest.vf.createLiteral("Test literal");
        this.testSesameObjectLangLiteral1 = RioUtilsTest.vf.createLiteral("Test literal", "en");
        this.testSesameObjectTypedLiteral1 =
                RioUtilsTest.vf.createLiteral("Test literal", RioUtilsTest.vf.createURI("urn:test:datatype:1"));
        this.testSesameSubjectBNode1 = RioUtilsTest.vf.createBNode("subjectBnode1");
        this.testSesameObjectBNode1 = RioUtilsTest.vf.createBNode("objectBnode1");
        
        this.testSesameTripleAllIRI =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectUri1, this.testSesamePredicateUri1,
                        this.testSesameObjectUri1);
        this.testSesameTriplePlainLiteral =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectUri1, this.testSesamePredicateUri1,
                        this.testSesameObjectPlainLiteral1);
        this.testSesameTripleLangLiteral =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectUri1, this.testSesamePredicateUri1,
                        this.testSesameObjectLangLiteral1);
        this.testSesameTripleTypedLiteral =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectUri1, this.testSesamePredicateUri1,
                        this.testSesameObjectTypedLiteral1);
        this.testSesameTripleSubjectBNode =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectBNode1, this.testSesamePredicateUri1,
                        this.testSesameObjectUri1);
        this.testSesameTripleObjectBNode =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectUri1, this.testSesamePredicateUri1,
                        this.testSesameObjectBNode1);
        this.testSesameTripleSubjectObjectBNode =
                RioUtilsTest.vf.createStatement(this.testSesameSubjectBNode1, this.testSesamePredicateUri1,
                        this.testSesameObjectBNode1);
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }
    
    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     * .
     */
    @Test
    public void testTripleAllIRI()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTripleAllIRI);
        
        Assert.assertEquals(this.testSesameTripleAllIRI, tripleAsStatement);
    }
    
    @Test
    public void testTripleBNodeComparisonObject()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTripleObjectBNode);
        
        Assert.assertEquals(this.testSesameTripleObjectBNode, tripleAsStatement);
    }
    
    @Test
    public void testTripleBNodeComparisonSubject()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTripleSubjectBNode);
        
        Assert.assertEquals(this.testSesameTripleSubjectBNode, tripleAsStatement);
    }
    
    @Test
    public void testTripleBNodeComparisonSubjectAndObject()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTripleSubjectObjectBNode);
        
        Assert.assertEquals(this.testSesameTripleSubjectObjectBNode, tripleAsStatement);
    }
    
    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     * .
     */
    @Test
    public void testTripleLangLiteral()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTripleLangLiteral);
        
        Assert.assertEquals(this.testSesameTripleLangLiteral, tripleAsStatement);
    }
    
    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     * .
     */
    @Test
    public void testTriplePlainLiteral()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTriplePlainLiteral);
        
        Assert.assertEquals(this.testSesameTriplePlainLiteral, tripleAsStatement);
    }
    
    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.utils.RioUtils#tripleAsStatement(org.semanticweb.owlapi.io.RDFTriple)}
     * .
     */
    @Test
    public void testTripleTypedLiteral()
    {
        final Statement tripleAsStatement = RioUtils.tripleAsStatement(this.testOwlApiTripleTypedLiteral);
        
        Assert.assertEquals(this.testSesameTripleTypedLiteral, tripleAsStatement);
    }
}
