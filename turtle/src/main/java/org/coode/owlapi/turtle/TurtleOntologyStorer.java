package org.coode.owlapi.turtle;

import java.io.IOException;
import java.io.Writer;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Jan-2008<br><br>
 */
public class TurtleOntologyStorer extends AbstractOWLOntologyStorer {


    @Override
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLOntologyStorageException {
        try {
            TurtleRenderer ren = new TurtleRenderer(ontology, manager, writer, format);
            ren.render();
        }
        catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }


    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new TurtleOntologyFormat());
    }
}
