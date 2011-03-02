package org.semanticweb.owlapi.api.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/02/2011
 * <br>
 * API writers/storers/renderers should not close streams if they didn't open them.
 */
public class ExistingOutputStreamTestCase extends AbstractOWLAPITestCase {


    public void testOutputStreamRemainsOpen() throws Exception {
        try {
            OWLOntologyManager manager = getManager();
            OWLOntology ontology = manager.createOntology();
            saveOntology(ontology, new RDFXMLOntologyFormat());
            saveOntology(ontology, new OWLXMLOntologyFormat());
            saveOntology(ontology, new TurtleOntologyFormat());
            saveOntology(ontology, new OWLFunctionalSyntaxOntologyFormat());
            saveOntology(ontology, new ManchesterOWLSyntaxOntologyFormat());
        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void saveOntology(OWLOntology ontology, OWLOntologyFormat format) throws IOException, OWLOntologyStorageException {
        File file = File.createTempFile("ontology", ".owl");
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        manager.saveOntology(ontology, format, os);
        os.flush();
        OutputStreamWriter w = new OutputStreamWriter(os);
        w.write("<!-- Comment -->");
        w.flush();
        w.close();
    }
}
