package uk.ac.manchester.cs.owl.owlapi;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OWLAnnotationAssertionAxiomImplByteArrayStringValueTest {
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    private static final OWLAnnotationSubject SUBJECT = IRI.create("http://ex.org/#a");
    private static final OWLAnnotationPropertyImpl PROPERTY = new OWLAnnotationPropertyImpl(IRI.create("http://ex.org/#annoProp"));
    private static final Set<OWLAnnotation> ANNOTATIONS = Collections.emptySet();

    @Test
    public void testEquality() {
        String literal = "hello world";
        OWLAnnotationAssertionAxiomImpl original = getOwlAnnotationAssertionAxiom(literal);
        OWLAnnotationAssertionAxiomImplByteArrayStringValue byteArray = getOwlAnnotationAssertionAxiomImplByteArrayStringValue(literal);
        OWLAnnotationAssertionAxiomImplByteArrayStringValue byteArray2 = getOwlAnnotationAssertionAxiomImplByteArrayStringValue(literal);
        OWLAnnotationAssertionAxiomImplByteArrayStringValue byteArray3 = getOwlAnnotationAssertionAxiomImplByteArrayStringValue("goodbye world");
        assertTrue(original.equals(byteArray));
        assertTrue(byteArray.equals(original));
        assertTrue(byteArray.equals(byteArray2));
        assertFalse(byteArray.equals(byteArray3));


    }

    protected OWLAnnotationAssertionAxiomImplByteArrayStringValue getOwlAnnotationAssertionAxiomImplByteArrayStringValue(String literal) {
        return new OWLAnnotationAssertionAxiomImplByteArrayStringValue(SUBJECT, PROPERTY,literal.getBytes(ISO_8859_1), ANNOTATIONS);
    }

    protected OWLAnnotationAssertionAxiomImpl getOwlAnnotationAssertionAxiom(String literal) {
        OWLLiteralImplString value = new OWLLiteralImplString(literal);
        return new OWLAnnotationAssertionAxiomImpl(SUBJECT, PROPERTY,
                value, ANNOTATIONS);
    }

    @Test
    public void testCompare() {
        OWLAnnotationAssertionAxiomImplByteArrayStringValue a = getOwlAnnotationAssertionAxiomImplByteArrayStringValue("a");
        OWLAnnotationAssertionAxiomImplByteArrayStringValue b = getOwlAnnotationAssertionAxiomImplByteArrayStringValue("b");
        OWLAnnotationAssertionAxiomImplByteArrayStringValue aa = getOwlAnnotationAssertionAxiomImplByteArrayStringValue("aa");
        OWLAnnotationAssertionAxiomImpl ao = getOwlAnnotationAssertionAxiom("a");

        assertTrue(a.compareObjectOfSameType(a)==0);
        assertTrue(a.compareObjectOfSameType(ao)==0);
        assertTrue(ao.compareObjectOfSameType(a)==0);
        assertTrue(aa.compareObjectOfSameType(aa)==0);
        assertTrue(a.compareObjectOfSameType(b) <0);
        assertTrue(ao.compareObjectOfSameType(b) <0);
        assertTrue(a.compareObjectOfSameType(aa) <0);
        assertTrue(ao.compareObjectOfSameType(aa) <0);
        assertTrue(b.compareObjectOfSameType(a) >0);
        assertTrue(b.compareObjectOfSameType(ao) >0);
        assertTrue(b.compareObjectOfSameType(aa) >0);
    }

}