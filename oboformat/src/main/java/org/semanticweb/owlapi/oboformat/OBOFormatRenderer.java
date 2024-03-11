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

import javax.annotation.Nonnull;

import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.obolibrary.oboformat.writer.OBOFormatWriter.NameProvider;
import org.obolibrary.oboformat.writer.OBOFormatWriter.OBODocNameProvider;
import org.obolibrary.oboformat.writer.OBOFormatWriter.OWLOntologyNameProvider;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** renderer for obo */
public class OBOFormatRenderer implements OWLRenderer {

    @Override
    public void render(@Nonnull OWLOntology ontology, @Nonnull OutputStream os)
        throws OWLOntologyStorageException {
        render(ontology, new OutputStreamWriter(os),
            ontology.getOWLOntologyManager().getOntologyFormat(ontology));
    }

    /**
     * @param ontology ontology
     * @param writer writer
     * @param format format to write out
     * @throws OWLOntologyStorageException OWLOntologyStorageException
     */
    public static void render(@Nonnull OWLOntology ontology, @Nonnull Writer writer,
        OWLDocumentFormat format) throws OWLOntologyStorageException {
        try {
            OWLAPIOwl2Obo translator = new OWLAPIOwl2Obo(ontology.getOWLOntologyManager());
            if (format.isPrefixOWLOntologyFormat()) {
                translator.setPrefixManager(format.asPrefixOWLOntologyFormat());
            }
            final OBODoc result = translator.convert(ontology);
            boolean hasImports = ontology.getImports().isEmpty() == false;
            NameProvider nameProvider;
            if (hasImports) {
                // if the ontology has imports
                // use it as secondary lookup for labels
                final NameProvider primary = new OBODocNameProvider(result);
                final NameProvider secondary =
                    new OWLOntologyNameProvider(ontology, primary.getDefaultOboNamespace(), result);
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
            oboFormatWriter.setCheckStructure(
                ((Boolean) format.getParameter(OBODocumentFormat.VALIDATION, Boolean.TRUE))
                    .booleanValue());
            oboFormatWriter.write(result, new BufferedWriter(writer), nameProvider);
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
