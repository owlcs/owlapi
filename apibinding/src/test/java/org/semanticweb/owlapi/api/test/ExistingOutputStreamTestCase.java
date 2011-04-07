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
