package org;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParserFactory;

class LoadRelaMathTestCase extends TestBase {

    @Test
    void should() {
        m.getOntologyParsers().set(new TurtleOntologyParserFactory());
        m.getIRIMappers().add(mapper());
        OWLOntology o = loadOntology(IRI.create("http://sweetontology.net/relaMath"), m);
        o.getImportsClosure().stream().forEach(x -> {
            String s = x.getAxioms().stream().filter(ax -> ax.toString().contains("Error1"))
                .map(Object::toString).collect(Collectors.joining("\t"));
            if (!s.isEmpty()) {
                System.out.println(x.getOntologyID() + " contains failure " + s);
            }
        });
        findMultipleDeclarations(o);
        findPunnings(o);
    }

    protected static void findMultipleDeclarations(OWLOntology o) {
        Map<IRI, List<OWLAxiom>> map = new HashMap<>();
        o.getAxioms(true).stream().filter(ax -> ax instanceof OWLDeclarationAxiom)
            .map(ax -> (OWLDeclarationAxiom) ax).forEach(
                ax -> map.computeIfAbsent(ax.getEntity().getIRI(), x -> new ArrayList<>()).add(ax));
        map.forEach((a, b) -> {
            if (b.size() > 1) {
                System.out.println("Declared multiple times with different type " + a);
            }
        });
    }

    protected static void findPunnings(OWLOntology o) {
        Map<IRI, List<OWLEntity>> map = new HashMap<>();
        o.getSignature(Imports.INCLUDED).stream()
            .forEach(ax -> map.computeIfAbsent(ax.getIRI(), x -> new ArrayList<>()).add(ax));
        map.forEach((a, b) -> {
            if (b.size() > 1) {
                System.out.println("Punned with different types " + a + " " + b.stream()
                    .map(x -> x.getEntityType().toString()).collect(Collectors.joining(", ")));
            }
        });
    }

    protected static OWLOntologyIRIMapper mapper() {
        Map<IRI, IRI> map = new HashMap<>();
        File sweet = new File(RESOURCES, "importscyclic");
        map.put(IRI.create("http://sweetontology.net/relaMath"),
            IRI.create(new File(sweet, "relaMath.ttl")));
        map.put(IRI.create("http://sweetontology.net/reprMath"),
            IRI.create(new File(sweet, "reprMath.ttl")));
        map.put(IRI.create("http://sweetontology.net/reprMathGraph"),
            IRI.create(new File(sweet, "reprMathGraph.ttl")));
        map.put(IRI.create("http://sweetontology.net/reprMathOperation"),
            IRI.create(new File(sweet, "reprMathOperation.ttl")));
        return ontologyIRI -> map.get(ontologyIRI);
    }
}
