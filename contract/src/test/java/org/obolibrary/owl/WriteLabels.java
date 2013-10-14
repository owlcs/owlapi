package org.obolibrary.owl;

import java.io.File;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Example for how-to use the LabelFunctionalSyntax to serialize an ontology. 
 */
public class WriteLabels {

    public static void main(OWLOntologyManager manager) throws Exception {
        manager.addOntologyStorer(new LabelFunctionalSyntaxOntologyStorer());
	    OWLOntology ontology = manager.loadOntology(IRI.create("http://purl.obolibrary.org/obo/tao.owl"));
	    OWLOntologyFormat format = new LabelFunctionalFormat();
	    manager.saveOntology(ontology, format, IRI.create(new File("out/tao-labels.ofn")));
	}

}
