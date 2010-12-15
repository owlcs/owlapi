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

/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
