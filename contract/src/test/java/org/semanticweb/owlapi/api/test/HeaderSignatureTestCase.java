package org.semanticweb.owlapi.api.test;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by @ssz on 29.08.2020.
 */
public class HeaderSignatureTestCase {

    @Test
    public void testContainsAnnotationPropertyIssue928() throws Exception {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLDataFactory df = m.getOWLDataFactory();

        OWLAnnotationProperty e1 = df.getRDFSComment();
        OWLAnnotationProperty e2 = df.getRDFSSeeAlso();
        OWLDatatype e3 = df.getIntegerOWLDatatype();
        OWLDatatype e4 = df.getStringOWLDatatype();

        OWLOntology o = m.createOntology(IRI.create("http://XXXX"));
        m.applyChange(new AddOntologyAnnotation(o, df.getOWLAnnotation(e1, df.getOWLLiteral("xxx", e4))));
        m.applyChange(new AddOntologyAnnotation(o, df.getOWLAnnotation(e2, df.getOWLLiteral("42", e3))));

        Assert.assertTrue(o.containsEntityInSignature(e1));
        Assert.assertTrue(o.containsEntityInSignature(e2));
        Assert.assertEquals(2, o.annotationPropertiesInSignature().peek(System.out::println).count());

        Assert.assertTrue(o.containsEntityInSignature(e3));
        Assert.assertTrue(o.containsEntityInSignature(e4));
    }

    @Test
    public void testContainsDatatypesInHeaderIssue965() throws Exception {
        String s = "@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
                "@prefix owl:   <http://www.w3.org/2002/07/owl#> .\n" +
                "@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                "<http://xxx>    a             owl:Ontology ;  " +
                "rdfs:comment  \"the_comment\" . ";
        StringDocumentSource src = new StringDocumentSource(s, "xx", new TurtleDocumentFormat(), null);

        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o = m.loadOntologyFromOntologyDocument(src);
        o.saveOntology(new TurtleDocumentFormat(), System.out);

        List<OWLAnnotation> header = o.annotations().collect(Collectors.toList());
        Assert.assertEquals(1, header.size());
        OWLAnnotation a = header.get(0);

        Assert.assertEquals(2, a.signature().count());
        Assert.assertEquals(1, a.annotationPropertiesInSignature().count());
        Assert.assertEquals(1, a.datatypesInSignature().count());

        Assert.assertEquals(2, o.signature().count());
        Assert.assertEquals(1, o.annotationPropertiesInSignature().count());
        Assert.assertEquals(1, o.datatypesInSignature().count());
    }
}
