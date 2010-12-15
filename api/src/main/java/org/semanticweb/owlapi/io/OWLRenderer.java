package org.semanticweb.owlapi.io;

import java.io.OutputStream;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 */
public interface OWLRenderer {

    
    void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) throws OWLException;

    /**
     * Renders the specified ontology to a concrete representation which should
     * be written to the specified output stream
     * @param ontology The ontology
     * @param os The OutputStream
     */
    void render(OWLOntology ontology, OutputStream os) throws OWLException;
}
