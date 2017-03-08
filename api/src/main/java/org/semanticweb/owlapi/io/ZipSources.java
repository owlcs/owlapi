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
import java.io.InputStream;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.semanticweb.owlapi.model.IRI;

/**
 * Utility class for handling zip files (local or remote).
 */
public class ZipSources {

    private static final Pattern ZIP_ENTRY_ONTOLOGY = Pattern.compile(".*owl|rdf|xml|mos");

    /**
     * @param is input stream to wrap if a zip file
     * @param name name of the file or the IRI
     * @return wrapped inputstream if necessary
     * @throws IOException if an exception happens accessing the zip stream
     */
    public static InputStream handleZips(InputStream is, IRI name) throws IOException {
        return handleZips(is, name.toString());
    }

    /**
     * @param is input stream to wrap if a zip file
     * @param name name of the file or the IRI
     * @return wrapped inputstream if necessary
     * @throws IOException if an exception happens accessing the zip stream
     */
    public static InputStream handleZips(InputStream is, String name) throws IOException {
        if (!name.toLowerCase(Locale.getDefault()).endsWith(".zip")) {
            return is;
        }
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry nextEntry = zis.getNextEntry();
        while (nextEntry != null) {
            if (couldBeOntology(nextEntry)) {
                return zis;
            }
            nextEntry = zis.getNextEntry();
        }
        return zis;
    }

    private static boolean couldBeOntology(ZipEntry zipEntry) {
        return ZIP_ENTRY_ONTOLOGY.matcher(zipEntry.getName()).matches();
    }
}
