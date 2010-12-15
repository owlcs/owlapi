package org.semanticweb.owlapi.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 */
public abstract class AbstractOWLRenderer implements OWLRenderer {

    private OWLOntologyManager owlOntologyManager;


    protected AbstractOWLRenderer(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }


    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }


    protected OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }


    public void render(OWLOntology ontology, OutputStream os) throws OWLRendererException {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            render(ontology, writer);
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }


    /**
     * Renders the specified ontology using the specified writer.
     * @param writer The writer that should be used to write the ontology.
     * Note that this writer need not be wrapped with a <code>BufferedWriter</code>
     * because this is taken care of by this abstract implementation.
     */
    public abstract void render(OWLOntology ontology, Writer writer) throws OWLRendererException;
}
