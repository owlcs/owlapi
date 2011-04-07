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

package org.semanticweb.owlapi.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 * <br>
 * An <code>OWLOntologyDocumentTarget</code> that supports writing out to a <code>File</code>.
 * @since 3.2
 */
public class FileDocumentTarget implements OWLOntologyDocumentTarget {

    private File file;

    /**
     * Constructs the document target, with the target being the specified file.
     * @param file The file that is the target.
     */
    public FileDocumentTarget(File file) {
        this.file = file;
    }

    public boolean isWriterAvailable() {
        return true;
    }

    public Writer getWriter() throws IOException {
        return new BufferedWriter(new FileWriter(file));
    }

    public boolean isOutputStreamAvailable() {
        return true;
    }

    public OutputStream getOutputStream() throws IOException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    public boolean isDocumentIRIAvailable() {
        return true;
    }

    public IRI getDocumentIRI() {
        return IRI.create(file);
    }
}
