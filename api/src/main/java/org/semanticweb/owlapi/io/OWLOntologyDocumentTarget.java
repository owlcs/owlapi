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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;

/**
 * Specifies an interface that provides a pointer to an ontology document where
 * an ontology can be stored. <br>
 * Any client that writes an ontology to a "stream" will first try to obtain a
 * writer (if {@link #isWriterAvailable()} returns true), followed by an
 * OutputStream (if {@link #isOutputStreamAvailable()} returns true), followed
 * by trying to open a stream from a document IRI (if isDocumentIRIAvailable
 * returns true). <br>
 * A client that writes an ontology to a database or some similar storage will
 * simply try to use the {@link IRI} returned by {@link #getDocumentIRI()}.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public interface OWLOntologyDocumentTarget {

    /**
     * Determines if this document target can be pointed to by a
     * {@link java.io.Writer}.
     * 
     * @return {@code true} if a {@link java.io.Writer} can be obtained from
     *         this document target.
     */
    boolean isWriterAvailable();

    /**
     * Gets a {@link java.io.Writer} that can be used to write an ontology to an
     * ontology document.
     * 
     * @return The writer
     * @throws IOException
     *         if there was a problem obtaining the writer
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *         if a writer is not available ({@link #isWriterAvailable()}
     *         returns {@code false}) and this method is called.
     */
    @Nonnull
    Writer getWriter() throws IOException;

    /**
     * Determines if this document target can be pointed to by an
     * {@link java.io.OutputStream}.
     * 
     * @return {@code true} if an {@link java.io.OutputStream} can be obtained
     *         from this document target.
     */
    boolean isOutputStreamAvailable();

    /**
     * Gets an {@link java.io.OutputStream} that can be used to write an
     * ontology to an ontology document.
     * 
     * @return The output stream
     * @throws IOException
     *         if there was a problem obtaining the output stream
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *         if an output stream is not available (
     *         {@link #isOutputStreamAvailable()} returns {@code false} ) and
     *         this method is called.
     */
    @Nonnull
    OutputStream getOutputStream() throws IOException;

    /**
     * Determines if an IRI that points to an ontology document is available.
     * The IRI encodes the exact details of how an ontology should be saved to a
     * document.
     * 
     * @return {@code true} if an IRI is available, otherwise {@code false}.
     */
    boolean isDocumentIRIAvailable();

    /**
     * Gets an IRI that points to an ontology document.
     * 
     * @return The IRI
     * @throws org.semanticweb.owlapi.model.OWLRuntimeException
     *         if an IRI is not available ({@link #isDocumentIRIAvailable()}
     *         returns {@code false}) and this method is called.
     */
    @Nonnull
    IRI getDocumentIRI();
}
