package org.obolibrary.macro;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

@SuppressWarnings("javadoc")
public class ExpandExpressionTest extends OboFormatTestBasics {

    @Test
    public void testExpand() {
        OWLOntology ontology = convert(parseOBOFile("no_overlap.obo"));
        MacroExpansionVisitor mev = new MacroExpansionVisitor(ontology);
        OWLOntology outputOntology = mev.expandAll();
        OWLClass cls = df.getOWLClass(IRI
                .create("http://purl.obolibrary.org/obo/TEST_2"));
        Set<OWLDisjointClassesAxiom> dcas = outputOntology
                .getDisjointClassesAxioms(cls);
        // System.out.println(dcas);
        assertEquals(1, dcas.size());
        cls = df.getOWLClass(IRI
                .create("http://purl.obolibrary.org/obo/TEST_3"));
        Set<OWLSubClassOfAxiom> scas = outputOntology
                .getSubClassAxiomsForSubClass(cls);
        // System.out.println(scas);
        assertEquals(1, scas.size());
        assertEquals(
                "[SubClassOf(<http://purl.obolibrary.org/obo/TEST_3> ObjectSomeValuesFrom(<http://purl.obolibrary.org/obo/BFO_0000051> ObjectIntersectionOf(<http://purl.obolibrary.org/obo/GO_0005886> ObjectSomeValuesFrom(<http://purl.obolibrary.org/obo/BFO_0000051> <http://purl.obolibrary.org/obo/TEST_4>))))]",
                scas.toString());
        cls = df.getOWLClass(IRI
                .create("http://purl.obolibrary.org/obo/TEST_4"));
        Set<OWLEquivalentClassesAxiom> ecas = outputOntology
                .getEquivalentClassesAxioms(cls);
        boolean ok = false;
        for (OWLEquivalentClassesAxiom eca : ecas) {
            for (OWLClassExpression x : eca.getClassExpressions()) {
                if (x instanceof OWLObjectIntersectionOf) {
                    for (OWLClassExpression y : ((OWLObjectIntersectionOf) x)
                            .getOperands()) {
                        if (y instanceof OWLObjectSomeValuesFrom) {
                            String pStr = ((OWLObjectSomeValuesFrom) y)
                                    .getProperty().toString();
                            // System.out.println("p=" + pStr);
                            assertEquals(
                                    "<http://purl.obolibrary.org/obo/BFO_0000051>",
                                    pStr);
                            ok = true;
                        }
                    }
                }
            }
        }
        assertTrue(ok);
        writeOWL(ontology);
    }
}
