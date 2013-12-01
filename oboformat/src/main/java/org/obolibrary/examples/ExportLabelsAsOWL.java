package org.obolibrary.examples;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.obolibrary.obo2owl.Obo2Owl;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Saves an OWL ontology consisting only of labels
 * 
 * argument 1: a file path to an input obo file
 * argument 2: a file path to an output owl file
 * 
 * Example:
 * <code>
 * java org.obolibrary.examples.ExportLabelsAsOWL human-phenotype-ontology.obo labels.owl
 * </code>
 *
 */
public class ExportLabelsAsOWL {
    public static void main(String[] args) throws Exception {
        // *** obo to owl ***
        // create a parser object and get an OBO Document (first arg)
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = p.parse(args[0]);
        // create a translator object and feed it the OBO Document
        // XXX a manager needs to be injected
        Obo2Owl bridge = new Obo2Owl(null);
        OWLOntology ontology = bridge.convert(obodoc);
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        // everything from here on uses only the OWLAPI
        // ** OWL ontology processing ***
        // prepare output file (2nd arg)
        OutputStream os = new FileOutputStream(args[1]);
        // we will be exporting only labels. Get the RDFS vocabulary IRI for
        // this
        IRI rdfsLabelIRI = OWLRDFVocabulary.RDFS_LABEL.getIRI();
        // create a new ontology o2. o2 will be a copy of o1, with a new IRI,
        // and will
        // include only label assertions from o
        OWLOntology ontology2 = manager.createOntology(IRI.create("http://example.org"));
        // iterate through all classes in the ontology adding class declarations
        // to o2, and label assertions
        for (OWLClass c : ontology.getClassesInSignature()) {
            manager.addAxiom(ontology2, manager.getOWLDataFactory()
                    .getOWLDeclarationAxiom(c));
            for (OWLAnnotationAssertionAxiom a : ontology.getAnnotationAssertionAxioms(c
                    .getIRI())) {
                // only add an annotation assertion if it is an rdfs:label
                // remove this condition to add ALL annotation assertions (e.g.
                // comments, definitions, synonyms)
                if (a.getProperty().getIRI().equals(rdfsLabelIRI)) {
                    manager.addAxiom(ontology2, a);
                }
            }
        }
        OWLOntologyFormat format = new RDFXMLOntologyFormat();
        manager.saveOntology(ontology2, format, os);
    }
}
