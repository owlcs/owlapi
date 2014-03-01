package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

@SuppressWarnings("javadoc")
public class RoundTripOboAnnotations extends RoundTripTest {

    @Test
    public void testIsInferredAnnotation() throws Exception {
        OBODoc input = parseOBOFile("is_inferred_annotation.obo");
        OWLOntology owl = convert(input);
        // check round trip
        OBODoc output = convert(owl);
        String outObo = renderOboToString(output);
        assertEquals(readResource("is_inferred_annotation.obo"), outObo);
        // check owl
        IRI t1 = IRI.create("http://purl.obolibrary.org/obo/TEST_0001");
        IRI t3 = IRI.create("http://purl.obolibrary.org/obo/TEST_0003");
        IRI isInferredIRI = IRI.create(Obo2OWLConstants.OIOVOCAB_IRI_PREFIX,
                "is_inferred");
        boolean hasAnnotation = false;
        Set<OWLSubClassOfAxiom> axioms = owl.getAxioms(AxiomType.SUBCLASS_OF);
        for (OWLSubClassOfAxiom axiom : axioms) {
            OWLClassExpression superClassCE = axiom.getSuperClass();
            OWLClassExpression subClassCE = axiom.getSubClass();
            if (!superClassCE.isAnonymous() && !subClassCE.isAnonymous()) {
                OWLClass superClass = (OWLClass) superClassCE;
                OWLClass subClass = (OWLClass) subClassCE;
                if (superClass.getIRI().equals(t1)
                        && subClass.getIRI().equals(t3)) {
                    Set<OWLAnnotation> annotations = axiom.getAnnotations();
                    for (OWLAnnotation owlAnnotation : annotations) {
                        OWLAnnotationProperty property = owlAnnotation
                                .getProperty();
                        if (property.getIRI().equals(isInferredIRI)) {
                            OWLAnnotationValue value = owlAnnotation.getValue();
                            if (value instanceof OWLLiteral) {
                                OWLLiteral literal = (OWLLiteral) value;
                                assertEquals("true", literal.getLiteral());
                            } else {
                                fail("The value is not the expected type, expected OWLiteral but was: "
                                        + value.getClass().getName());
                            }
                            hasAnnotation = true;
                        }
                    }
                }
            }
        }
        assertTrue(
                "The sub class reation between t3 and t1 should have an is_inferred=true annotation",
                hasAnnotation);
    }
}
