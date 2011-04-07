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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Nov-2007<br>
 * <br>
 * 
 * A convenience class which will prepare an input source from a file.
 */
public class FileDocumentSource implements OWLOntologyDocumentSource {
	private File file;

	/**
	 * Constructs an ontology input source using the specified file.
	 * 
	 * @param file
	 *            The file from which a concrete representation of an ontology
	 *            will be obtained.
	 */
	public FileDocumentSource(File file) {
		this.file = file;
	}

	public IRI getDocumentIRI() {
		return IRI.create(file);
	}

	public boolean isInputStreamAvailable() {
		return true;
	}

	public InputStream getInputStream() {
		try {
			return new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new OWLOntologyInputSourceException(e);
		}
	}

	public boolean isReaderAvailable() {
		return true;
	}

	public Reader getReader() {
		try {
			return new InputStreamReader(getInputStream(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// it cannot not support UTF-8
			e.printStackTrace();
			throw new OWLOntologyInputSourceException(e);
		}
	}
}
