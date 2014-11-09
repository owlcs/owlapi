package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class UnionOfTest extends OboFormatTestBasics {

    @Test
    public void testUnion() {
        OWLOntology owlOnt = convertOBOFile("taxon_union_terms.obo");
        assertNotNull(owlOnt);
        OWLClass cls = df.getOWLClass("http://purl.obolibrary.org/obo/",
                "NCBITaxon_Union_0000000");
        boolean ok = owlOnt.equivalentClassesAxioms(cls)
                .flatMap(ax -> ax.classExpressions())
                .anyMatch(ce -> ce instanceof OWLObjectUnionOf);
        assertTrue(ok);
    }
}
