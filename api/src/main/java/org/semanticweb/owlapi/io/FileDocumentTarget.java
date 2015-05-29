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
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.*;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@code OWLOntologyDocumentTarget} that supports writing out to a
 * {@code File}.
 * 
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2.0
 */
public class FileDocumentTarget implements OWLOntologyDocumentTarget {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDocumentTarget.class);
    private final @Nonnull File file;

    /**
     * Constructs the document target, with the target being the specified file.
     * 
     * @param file
     *        The file that is the target.
     */
    public FileDocumentTarget(File file) {
        this.file = checkNotNull(file, "file cannot be null");
    }

    @Override
    public Optional<Writer> getWriter() {
        try {
            return optional(new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            LOGGER.error("Writer cannot be created", e);
            return emptyOptional();
        }
    }

    @Override
    public Optional<OutputStream> getOutputStream() {
        try {
            return optional(new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            LOGGER.error("Input stream cannot be created", e);
            return emptyOptional();
        }
    }

    @Override
    public Optional<IRI> getDocumentIRI() {
        return optional(IRI.create(file));
    }
}
