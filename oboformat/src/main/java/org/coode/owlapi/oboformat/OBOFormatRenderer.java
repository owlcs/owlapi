package org.coode.owlapi.oboformat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class OBOFormatRenderer implements OWLRenderer {
    private OWLOntologyManager manager;

    @Override
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager)
            throws OWLException {
        manager = owlOntologyManager;
    }

    @Override
    public void render(OWLOntology ontology, OutputStream os)
            throws OWLOntologyStorageException {
        render(ontology, new OutputStreamWriter(os));
    }

    public void render(OWLOntology ontology, Writer writer)
            throws OWLOntologyStorageException {
        try {
            OWLAPIOwl2Obo translator = new OWLAPIOwl2Obo(ontology.getOWLOntologyManager());
            OBODoc result = translator.convert(ontology);
            new OBOFormatWriter().write(result, new BufferedWriter(writer));
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
