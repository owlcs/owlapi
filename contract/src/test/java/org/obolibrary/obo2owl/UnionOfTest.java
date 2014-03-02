package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class UnionOfTest extends OboFormatTestBasics {

    @Test
    public void testUnion() throws Exception {
        OWLOntology owlOnt = convertOBOFile("taxon_union_terms.obo");
        assertNotNull(owlOnt);
        OWLOntologyManager manager = owlOnt.getOWLOntologyManager();
        OWLDataFactory df = manager.getOWLDataFactory();
        IRI iri = IRI
                .create("http://purl.obolibrary.org/obo/NCBITaxon_Union_0000000");
        OWLClass cls = df.getOWLClass(iri);
        boolean ok = false;
        for (OWLEquivalentClassesAxiom ax : owlOnt
                .getEquivalentClassesAxioms(cls)) {
            for (OWLClassExpression ex : ax.getClassExpressions()) {
                if (ex instanceof OWLObjectUnionOf) {
                    ok = true;
                }
            }
        }
        assertTrue(ok);
    }
}
