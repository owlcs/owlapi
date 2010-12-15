package org.coode.owlapi.rdf.rdfxml;

import java.io.IOException;
import java.io.Writer;

import org.coode.xml.IllegalElementNameException;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03-Jan-2007<br><br>
 */
public class RDFXMLOntologyStorer extends AbstractOWLOntologyStorer {

    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        if (ontologyFormat instanceof RDFXMLOntologyFormat) {
            return true;
        }
        return false;
    }


    @Override
	protected void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLOntologyStorageException {
        try {
            RDFXMLRenderer renderer = new RDFXMLRenderer(manager, ontology, writer, format);
            renderer.render();
        }
        catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
        catch (IllegalElementNameException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
