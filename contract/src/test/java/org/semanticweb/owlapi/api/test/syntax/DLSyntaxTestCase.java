package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class DLSyntaxTestCase extends TestBase {

    @Test
    public void testCommasOnDisjointThree() {
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    public void testCommasOnDisjointTwo() {
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    public void testCommasOnDisjointFour() {
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLClass d = df.getOWLClass(IRI.create("urn:D"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c, d);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D", render);
    }

    @Test
    public void testCommasOnDisjointThreeOntologyHTML() throws Exception {
        OWLOntology o = m.createOntology(IRI.create("urn:test:onto"));
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals("<html>\n<body>\n<h1>Ontology: \nOntologyID(OntologyIRI(<urn:test:onto>) VersionIRI(<null>))</h1>\n<h2><a name=\"A\">urn:A</a></h2>\n" + 
            "<div class=\"entitybox\">\n<div class=\"axiombox\"> \n" + 
            "A &#8849; &#172; <a href=\"#B\">B</a>, A &#8849; &#172; <a href=\"#C\">C</a>, <a href=\"#B\">B</a> &#8849; &#172; <a href=\"#C\">C</a> </div>\n" + 
            "<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (0)</h3>\n</div>\n</div>\n" + 
            "<h2><a name=\"B\">urn:B</a></h2>\n<div class=\"entitybox\">\n<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (0)</h3>\n</div>\n</div>\n" + 
            "<h2><a name=\"C\">urn:C</a></h2>\n<div class=\"entitybox\">\n<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (0)</h3>\n</div>\n</div>\n" + 
            "<div>\n</div>\n</body>\n</html>\n", render);
    }

    @Test
    public void testCommasOnDisjointTwoOntologyHTML() throws Exception {
        OWLOntology o = m.createOntology(IRI.create("urn:test:onto"));
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals("<html>\n<body>\n<h1>Ontology: \nOntologyID(OntologyIRI(<urn:test:onto>) VersionIRI(<null>))</h1>\n<h2><a name=\"A\">urn:A</a></h2>\n" + 
            "<div class=\"entitybox\">\n<div class=\"axiombox\"> \nA &#8849; &#172; <a href=\"#B\">B</a> </div>\n" + 
            "<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (0)</h3>\n</div>\n</div>\n" + 
            "<h2><a name=\"B\">urn:B</a></h2>\n<div class=\"entitybox\">\n<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (0)</h3>\n</div>\n</div>\n" + 
            "<div>\n</div>\n</body>\n</html>\n", render);
    }

    @Test
    public void testCommasOnDisjointFourOntologyHTML() throws Exception {
        OWLOntology o = m.createOntology(IRI.create("urn:test:onto"));
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLClass d = df.getOWLClass(IRI.create("urn:D"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c, d);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals("<html>\n<body>\n<h1>Ontology: \nOntologyID(OntologyIRI(<urn:test:onto>) VersionIRI(<null>))</h1>\n<h2><a name=\"A\">urn:A</a></h2>\n" + 
            "<div class=\"entitybox\">\n<div class=\"axiombox\"> \nA &#8849; &#172; <a href=\"#B\">B</a>, A &#8849; &#172; <a href=\"#C\">C</a>, A &#8849; &#172; <a href=\"#D\">D</a>, <a href=\"#B\">B</a> &#8849; &#172; <a href=\"#C\">C</a>, <a href=\"#B\">B</a> &#8849; &#172; <a href=\"#D\">D</a>, <a href=\"#C\">C</a> &#8849; &#172; <a href=\"#D\">D</a> </div>\n" + 
            "<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n<h3>Usages (0)</h3>\n</div>\n</div>\n<h2><a name=\"B\">urn:B</a></h2>\n" + 
            "<div class=\"entitybox\">\n<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n" + 
            "<h3>Usages (0)</h3>\n</div>\n</div>\n<h2><a name=\"C\">urn:C</a></h2>\n" + 
            "<div class=\"entitybox\">\n<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n" + 
            "<h3>Usages (0)</h3>\n</div>\n</div>\n<h2><a name=\"D\">urn:D</a></h2>\n" + 
            "<div class=\"entitybox\">\n<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">\n" + 
            "<h3>Usages (0)</h3>\n</div>\n</div>\n<div>\n</div>\n</body>\n</html>\n", render);
    }

    @Test
    public void testCommasOnDisjointThreeOntology() throws Exception {
        OWLOntology o = m.createOntology();
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    public void testCommasOnDisjointTwoOntology() throws Exception {
        OWLOntology o = m.createOntology();
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    public void testCommasOnDisjointFourOntology() throws Exception {
        OWLOntology o = m.createOntology();
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLClass d = df.getOWLClass(IRI.create("urn:D"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c, d);
        m.addAxiom(o, ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D", render);
    }
}
