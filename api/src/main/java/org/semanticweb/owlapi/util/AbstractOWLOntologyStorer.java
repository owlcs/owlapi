package org.semanticweb.owlapi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLOntologyStorer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Dec-2007<br><br>
 */
public abstract class AbstractOWLOntologyStorer implements OWLOntologyStorer {


    public void storeOntology(OWLOntologyManager manager, OWLOntology ontology, IRI documentIRI, OWLOntologyFormat ontologyFormat) throws
            OWLOntologyStorageException {
        try {
            if (!documentIRI.isAbsolute()) {
                throw new OWLOntologyStorageException("Document IRI must be absolute: " + documentIRI);
            }


            File tempFile = File.createTempFile("owlapi", ".owl");
            FileOutputStream tempOutputStream=null;
            FileInputStream tempInputStream=null;
            InputStreamReader inputStreamReader=null;
            OutputStreamWriter outputStreamWriter=null;
            BufferedReader br=null;
            BufferedWriter w=null;
            try {
                tempOutputStream = new FileOutputStream(tempFile);
				BufferedWriter tempWriter = new BufferedWriter(new OutputStreamWriter(tempOutputStream, "UTF-8"));
                storeOntology(manager, ontology, tempWriter, ontologyFormat);
                tempWriter.flush();
                tempWriter.close();

                // Now copy across
                OutputStream os;
                if (documentIRI.getScheme().equals("file")) {
                    File file = new File(documentIRI.toURI());
                    // Ensure that the necessary directories exist.
                    file.getParentFile().mkdirs();
                    os = new FileOutputStream(file);
                } else {
                    URL url = documentIRI.toURI().toURL();
                    URLConnection conn = url.openConnection();
                    os = conn.getOutputStream();
                }



                tempInputStream = new FileInputStream(tempFile);
				inputStreamReader = new InputStreamReader(tempInputStream, "UTF-8");
				br = new BufferedReader(inputStreamReader);
                outputStreamWriter = new OutputStreamWriter(os, "UTF-8");
				w = new BufferedWriter(outputStreamWriter);
                String line;
                while((line = br.readLine()) != null) {
                    w.write(line);
                    w.write("\n");
                }
            }
            finally {
            	
                tempFile.delete();
                br.close();
                w.close();
                tempOutputStream.close();
                tempInputStream.close();
                inputStreamReader.close();
                outputStreamWriter.close();

            }

        }
        catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }


    public void storeOntology(OWLOntologyManager manager, OWLOntology ontology, OWLOntologyDocumentTarget target,
                              OWLOntologyFormat format) throws OWLOntologyStorageException {
        if (target.isWriterAvailable()) {
            try {
                Writer writer = target.getWriter();
                storeOntology(manager, ontology, writer, format);
                writer.close();
            }
            catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isOutputStreamAvailable()) {
        	BufferedWriter writer=null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(target.getOutputStream(), "UTF-8"));
                storeOntology(manager, ontology, writer, format);

            }
            catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
            finally {
				try {
					writer.close();
				} catch (IOException e) {
					// no operation
				}
            }

        } else if (target.isDocumentIRIAvailable()) {
            storeOntology(manager, ontology, target.getDocumentIRI(), format);
        } else {
            throw new OWLOntologyStorageException("Neither a Writer, OutputStream or Document IRI could be obtained to store the ontology");
        }
    }

    protected abstract void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLOntologyStorageException;
}
