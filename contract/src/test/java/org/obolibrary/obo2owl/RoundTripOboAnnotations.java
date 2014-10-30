package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
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
        AtomicBoolean hasAnnotation = new AtomicBoolean(false);
        OWLAnnotationProperty infIRI = df
                .getOWLAnnotationProperty(isInferredIRI);
        Set<OWLSubClassOfAxiom> axioms = owl.getAxioms(AxiomType.SUBCLASS_OF);
        for (OWLSubClassOfAxiom axiom : axioms) {
            OWLClassExpression superClassCE = axiom.getSuperClass();
            OWLClassExpression subClassCE = axiom.getSubClass();
            if (!superClassCE.isAnonymous() && !subClassCE.isAnonymous()) {
                OWLClass superClass = (OWLClass) superClassCE;
                OWLClass subClass = (OWLClass) subClassCE;
                if (superClass.getIRI().equals(t1)
                        && subClass.getIRI().equals(t3)) {
                    axiom.annotations(infIRI)
                            .map(a -> a.getValue())
                            .forEach(
                                    v -> {
                                        if (v instanceof OWLLiteral) {
                                            assertEquals("true",
                                                    ((OWLLiteral) v)
                                                            .getLiteral());
                                        } else {
                                            fail("The value is not the expected type, expected OWLiteral but was: "
                                                    + v.getClass().getName());
                                        }
                                        hasAnnotation.set(true);
                                    });
                }
            }
        }
        assertTrue(
                "The sub class reation between t3 and t1 should have an is_inferred=true annotation",
                hasAnnotation.get());
    }
}
