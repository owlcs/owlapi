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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ontology document target which can write to a GZIP stream. Notice that
 * this works best when the output stream is closed explicitly in the client
 * code.
 * 
 * @author ignazio
 * @since 3.4.8
 */
public class GZipStreamDocumentTarget implements OWLOntologyDocumentTarget {

    private static final Logger LOGGER = LoggerFactory
        .getLogger(GZipFileDocumentTarget.class);
    private final OutputStream outputStream;

    /**
     * @param os
     *        the actual file
     */
    public GZipStreamDocumentTarget(OutputStream os) {
        outputStream = os;
    }

    @Override
    public Optional<OutputStream> getOutputStream() {
        try {
            return optional(new GZIPOutputStream(outputStream));
        } catch (IOException e) {
            LOGGER.error("Fille cannot be found or opened", e);
            return emptyOptional();
        }
    }
}
