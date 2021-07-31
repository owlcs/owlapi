package org;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Check {
    public static void main(String[] args) throws Exception {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLOntology o = m.loadOntologyFromOntologyDocument(new FileDocumentSource(
            new File("/Users/ignazio/workspace/cell-ontology/1uberon.owl.txt"),
            new RDFXMLDocumentFormat()));
        // o.getAxioms().forEach(ax -> ax.getAnnotations().forEach(a -> v(a.getValue())));
        // obo(m, o);
        rdfxml(m, o, "2uberon.owl.txt");
        o = m.loadOntologyFromOntologyDocument(new FileDocumentSource(
            new File("/Users/ignazio/workspace/cell-ontology/1fbbt-full.owl.txt"),
            new RDFXMLDocumentFormat()));
        rdfxml(m, o, "2fbbt-full.owl.txt");
        // for (String s : documentTarget.toString().split("\n")) {
        // if (s.contains("xsd:string")) {
        // System.out.println("Check.main() " + s);
        // }
        // }
    }

    protected static void obo(OWLOntologyManager m, OWLOntology o)
        throws OWLOntologyStorageException, IOException, FileNotFoundException {
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        m.saveOntology(o, new OBODocumentFormat(), documentTarget);
        try (FileOutputStream out =
            new FileOutputStream(new File("/Users/ignazio/workspace/cell-ontology/test.obo"))) {
            out.write(documentTarget.toString().getBytes());
        }
    }

    protected static void rdfxml(OWLOntologyManager m, OWLOntology o, String f)
        throws OWLOntologyStorageException, IOException, FileNotFoundException {
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        m.saveOntology(o, new RDFXMLDocumentFormat(), documentTarget);
        try (FileOutputStream out =
            new FileOutputStream(new File("/Users/ignazio/workspace/cell-ontology/" + f))) {
            out.write(documentTarget.toString().getBytes());
        }
    }

    static void v(OWLAnnotationValue val) {
        if (val.isLiteral()) {
            OWLLiteral l = val.asLiteral().get();
            if (l.getLiteral().contains("GOC:tfm")) {
                System.out.println(
                    "Check.v() " + l.getLiteral() + " " + l.getDatatype().getIRI().getShortForm()
                        + " " + val.getClass().getSimpleName());
            }
        }
    }
}
