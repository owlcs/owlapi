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

import com.google.common.io.Closeables;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.annotation.Nonnull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.UnsupportedOptionsException;
import org.tukaani.xz.XZOutputStream;

/**
 * An ontology document target which can write to a XZ stream. Notice that
 * this works best when the output stream is closed explicitly in the client
 * code.
 * 
 * @author ignazio
 * @since 3.4.8
 */
public class XZStreamDocumentTarget implements OWLOntologyDocumentTarget,AutoCloseable {

    private final OutputStream outputStream;
    private XZOutputStream xzOutputStream;
    private FilterOptions filterOptions[];

    /**
     * @param os
     *        the actual output stream
     *        @param filterOptions XZ filter options to use.  If no options are supplied
     *                             use default LZMA2 Options.
     */
    public XZStreamDocumentTarget(OutputStream os,FilterOptions... filterOptions) {
        outputStream = os;
        if(filterOptions.length == 0) {
            filterOptions = new FilterOptions[] {new LZMA2Options()};
        }
        this.filterOptions = filterOptions;
    }

    /**
     *
     * @param os  output stream to wrap
     * @param presetLevel LZMA2 Preset Level to use
     * @throws UnsupportedOptionsException if an unsupported preset level is supplied
     */
    public XZStreamDocumentTarget(OutputStream os,int presetLevel) throws UnsupportedOptionsException {
        this(os,new LZMA2Options(presetLevel));
    }

    @Override
    public boolean isWriterAvailable() {
        return isOutputStreamAvailable();
    }

    @Nonnull
    @Override
    public Writer getWriter() {
        if (!isWriterAvailable()) {
            throw new UnsupportedOperationException(
                    "writer not available; check with isWriterAvailable() first.");
        }
        return new OutputStreamWriter(getOutputStream());
    }

    @Override
    public boolean isOutputStreamAvailable() {
        return true;
    }

    @Nonnull
    @Override
    public OutputStream getOutputStream() {
        if (xzOutputStream == null) {
            try {
                xzOutputStream = new XZOutputStream(outputStream,filterOptions);
            } catch (IOException e) {
                throw new OWLRuntimeException(e);
            }
        }
        return verifyNotNull(xzOutputStream);
    }

    @Override
    public boolean isDocumentIRIAvailable() {
        return false;
    }

    @Nonnull
    @Override
    public IRI getDocumentIRI() {
        throw new UnsupportedOperationException(
                "iri not available; check with isDocumentIRIAvailable() first");
    }

    @Override
    public void close() throws Exception {
        XZOutputStream xzOutputStream = this.xzOutputStream;
        this.xzOutputStream = null;
        Closeables.close(xzOutputStream,false);
    }
}
