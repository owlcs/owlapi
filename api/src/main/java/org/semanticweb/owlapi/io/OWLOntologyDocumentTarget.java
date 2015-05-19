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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Optional;

import org.semanticweb.owlapi.model.IRI;

/**
 * Specifies an interface that provides a pointer to an ontology document where
 * an ontology can be stored. <br>
 * Any client that writes an ontology to a "stream" will first try to obtain a
 * writer, followed by an OutputStream , followed by trying to open a stream
 * from a document IRI. <br>
 * A client that writes an ontology to a database or some similar storage will
 * simply try to use the {@link IRI} returned by {@link #getDocumentIRI()}.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public interface OWLOntologyDocumentTarget {

    /**
     * Gets a {@link java.io.Writer} that can be used to write an ontology to an
     * ontology document. If none is available, return Optional.absent. Do not
     * call multiple times for the same file: the output file will be opened for
     * write multiple times.
     * 
     * @return The writer
     */
    default Optional<Writer> getWriter() {
        return emptyOptional();
    }

    /**
     * Gets an {@link java.io.OutputStream} that can be used to write an
     * ontology to an ontology document. If none is available, return
     * Optional.absent. Do not call multiple times for the same file: the output
     * file will be opened for write multiple times.
     * 
     * @return The output stream
     */
    default Optional<OutputStream> getOutputStream() {
        return emptyOptional();
    }

    /**
     * Gets an IRI that points to an ontology document. If none is available,
     * return Optional.absent.
     * 
     * @return The IRI
     */
    default Optional<IRI> getDocumentIRI() {
        return emptyOptional();
    }
}
