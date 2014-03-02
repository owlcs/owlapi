/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.oboformat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** renderer for obo */
public class OBOFormatRenderer implements OWLRenderer {

    @Override
    public void render(OWLOntology ontology, OutputStream os)
            throws OWLOntologyStorageException {
        render(ontology, new OutputStreamWriter(os));
    }

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     * @throws OWLOntologyStorageException
     *         OWLOntologyStorageException
     */
    public void render(OWLOntology ontology, Writer writer)
            throws OWLOntologyStorageException {
        try {
            OWLAPIOwl2Obo translator = new OWLAPIOwl2Obo(
                    ontology.getOWLOntologyManager());
            OBODoc result = translator.convert(ontology);
            new OBOFormatWriter().write(result, new BufferedWriter(writer));
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
