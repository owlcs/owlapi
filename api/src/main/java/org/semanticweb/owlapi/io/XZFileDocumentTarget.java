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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.File;
import java.io.FileOutputStream;

import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.UnsupportedOptionsException;
import org.tukaani.xz.XZOutputStream;

/**
 * An ontology document target which can write to a XZ File. Notice that this works best when the
 * output stream is closed explicitly in the client code.
 *
 * @author ses
 * @since 4.0.2
 */
public class XZFileDocumentTarget extends OWLOntologyDocumentTargetBase {

    /**
     * Construct an XZ document target using the selected compression preset
     *
     * @param os target File
     * @param presetLevel LZMA2 Compression preset level
     * @throws UnsupportedOptionsException if the options selected are not acceptable
     */
    public XZFileDocumentTarget(File os, int presetLevel) throws UnsupportedOptionsException {
        this(os, new LZMA2Options(presetLevel));
    }

    /**
     * Construct an XZ document target
     *
     * @param file target File
     */
    public XZFileDocumentTarget(File file) {
        this(file, new LZMA2Options());
    }

    /**
     * @param os the actual file
     * @param filterOptions Settings for XZ compression
     */
    public XZFileDocumentTarget(File os, FilterOptions... filterOptions) {
        super(() -> new XZOutputStream(new FileOutputStream(checkNotNull(os, "os cannot be null")),
            filterOptions), null);
    }
}
