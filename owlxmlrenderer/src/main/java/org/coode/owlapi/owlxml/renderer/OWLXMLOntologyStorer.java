package org.coode.owlapi.owlxml.renderer;

import java.io.Writer;

import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Jan-2007<br><br>
 */
public class OWLXMLOntologyStorer extends AbstractOWLOntologyStorer {

    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new OWLXMLOntologyFormat());
    }


    @Override
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws
                                                                                                                            OWLOntologyStorageException {
        OWLXMLRenderer renderer = new OWLXMLRenderer(manager);
        renderer.render(ontology, writer, format);
    }
}
