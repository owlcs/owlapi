/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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


    public final void storeOntology(OWLOntologyManager manager, OWLOntology ontology, IRI documentIRI, OWLOntologyFormat ontologyFormat) throws
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

                if (br != null){
                    br.close();
                }
                if (w != null){
                    w.close();
                }
                if (tempOutputStream != null){
                    tempOutputStream.close();
                }
                if (tempInputStream != null){
                    tempInputStream.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
            }

        }
        catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }


    public final void storeOntology(OWLOntologyManager manager, OWLOntology ontology, OWLOntologyDocumentTarget target,
                                    OWLOntologyFormat format) throws OWLOntologyStorageException {
        if (target.isWriterAvailable()) {
            try {
                Writer writer = target.getWriter();
                storeOntology(manager, ontology, writer, format);
                writer.flush();
            }
            catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isOutputStreamAvailable()) {
            BufferedWriter writer=null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(target.getOutputStream(), "UTF-8"));
                storeOntology(manager, ontology, writer, format);
                writer.flush();

            }
            catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isDocumentIRIAvailable()) {
            storeOntology(manager, ontology, target.getDocumentIRI(), format);
        } else {
            throw new OWLOntologyStorageException("Neither a Writer, OutputStream or Document IRI could be obtained to store the ontology");
        }
    }

    protected abstract void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLOntologyStorageException;
}
