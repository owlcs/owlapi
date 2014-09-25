package org.coode.owlapi.oboformat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.obolibrary.oboformat.writer.OBOFormatWriter.NameProvider;
import org.obolibrary.oboformat.writer.OBOFormatWriter.OBODocNameProvider;
import org.obolibrary.oboformat.writer.OBOFormatWriter.OWLOntologyNameProvider;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** renderer for obo */
public class OBOFormatRenderer implements OWLRenderer {

    @Override
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager)
            throws OWLException {}

    @Override
    public void render(OWLOntology ontology, OutputStream os)
            throws OWLOntologyStorageException {
        render(ontology, new OutputStreamWriter(os), ontology
                .getOWLOntologyManager().getOntologyFormat(ontology));
    }

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     * @throws OWLOntologyStorageException
     *         OWLOntologyStorageException
     */
    public static void render(OWLOntology ontology, Writer writer,
            OWLOntologyFormat format) throws OWLOntologyStorageException {
        try {
            OWLAPIOwl2Obo translator = new OWLAPIOwl2Obo(
                    ontology.getOWLOntologyManager());
            final OBODoc result = translator.convert(ontology);
            boolean hasImports = ontology.getImports().isEmpty() == false;
            NameProvider nameProvider;
            if (hasImports) {
                // if the ontology has imports
                // use it as secondary lookup for labels
                final NameProvider primary = new OBODocNameProvider(result);
                final NameProvider secondary = new OWLOntologyNameProvider(
                        ontology, primary.getDefaultOboNamespace());
                // combine primary and secondary name provider
                nameProvider = new NameProvider() {

                    @Override
                    public String getName(String id) {
                        String name = primary.getName(id);
                        if (name != null) {
                            return name;
                        }
                        return secondary.getName(id);
                    }

                    @Override
                    public String getDefaultOboNamespace() {
                        return primary.getDefaultOboNamespace();
                    }
                };
            } else {
                nameProvider = new OBODocNameProvider(result);
            }
            OBOFormatWriter oboFormatWriter = new OBOFormatWriter();
            oboFormatWriter.setCheckStructure((Boolean) format.getParameter(
                    OBOOntologyFormat.VALIDATION, Boolean.TRUE));
            oboFormatWriter.write(result, new BufferedWriter(writer),
                    nameProvider);
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
