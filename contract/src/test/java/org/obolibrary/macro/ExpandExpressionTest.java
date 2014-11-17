package org.obolibrary.macro;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class ExpandExpressionTest extends OboFormatTestBasics {

    @Test
    public void testExpand() {
        OWLOntology ontology = convert(parseOBOFile("no_overlap.obo"));
        MacroExpansionVisitor mev = new MacroExpansionVisitor(ontology);
        OWLOntology outputOntology = mev.expandAll();
        OWLClass cls = df.getOWLClass("http://purl.obolibrary.org/obo/",
                "TEST_2");
        assertEquals(1, outputOntology.disjointClassesAxioms(cls).count());
        cls = df.getOWLClass("http://purl.obolibrary.org/obo/" + "TEST_3");
        assertEquals(1, outputOntology.subClassAxiomsForSubClass(cls).count());
        assertEquals(
                "SubClassOf(<http://purl.obolibrary.org/obo/TEST_3> ObjectSomeValuesFrom(<http://purl.obolibrary.org/obo/BFO_0000051> ObjectIntersectionOf(<http://purl.obolibrary.org/obo/GO_0005886> ObjectSomeValuesFrom(<http://purl.obolibrary.org/obo/BFO_0000051> <http://purl.obolibrary.org/obo/TEST_4>))))",
                outputOntology.subClassAxiomsForSubClass(cls).iterator().next()
                        .toString());
        cls = df.getOWLClass("http://purl.obolibrary.org/obo/", "TEST_4");
        Set<OWLEquivalentClassesAxiom> ecas = asSet(outputOntology
                .equivalentClassesAxioms(cls));
        AtomicBoolean ok = new AtomicBoolean(false);
        for (OWLEquivalentClassesAxiom eca : ecas) {
            eca.classExpressions()
                    .filter(ce -> ce instanceof OWLObjectIntersectionOf)
                    .flatMap(x -> ((OWLObjectIntersectionOf) x).operands())
                    .filter(y -> y instanceof OWLObjectSomeValuesFrom)
                    .map(y -> ((OWLObjectSomeValuesFrom) y).getProperty()
                            .toString())
                    .forEach(
                            pStr -> {
                                assertEquals(
                                        "<http://purl.obolibrary.org/obo/BFO_0000051>",
                                        pStr);
                                ok.set(true);
                            });
            assertTrue(ok.get());
            writeOWL(ontology);
        }
    }
}
