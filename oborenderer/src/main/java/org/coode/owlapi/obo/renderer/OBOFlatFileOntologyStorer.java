package org.coode.owlapi.obo.renderer;

import java.io.Writer;

import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

/**
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 17, 2008<br><br>
 */
public class OBOFlatFileOntologyStorer extends AbstractOWLOntologyStorer {

    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new OBOOntologyFormat());
    }


    @Override  @SuppressWarnings("unused")
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws
            OWLOntologyStorageException {
        OBOFlatFileRenderer renderer = new OBOFlatFileRenderer(manager);
        renderer.render(ontology, writer);
    }
}
