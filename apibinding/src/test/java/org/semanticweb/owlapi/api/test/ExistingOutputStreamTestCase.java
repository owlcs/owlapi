package org.semanticweb.owlapi.api.test;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;

import java.io.*;

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
