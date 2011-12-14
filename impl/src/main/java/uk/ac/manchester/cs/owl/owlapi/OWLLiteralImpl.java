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
package uk.ac.manchester.cs.owl.owlapi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br>
 * <br>
 */
public class OWLLiteralImpl extends OWLObjectImpl implements OWLLiteral {
	//	private static int compress = 0;
	//	private static int nocompress = 0;
	private static final class LiteralWrapper {
		String l;
		byte[] bytes;

		LiteralWrapper(String s) {
			if (s.length() > 160) {
				try {
					bytes = compress(s);
					l = null;
				} catch (IOException e) {
					// some problem happened - defaulting to no compression
					System.out.println("OWLLiteralImpl.LiteralWrapper.LiteralWrapper() "
							+ e.getMessage());
					l = s;
					bytes = null;
				}
			} else {
				//	nocompress++;
				bytes = null;
				l = s;
			}
			//			if((compress+nocompress)%1000==0) {
			//				System.out.println("OWLLiteralImpl.LiteralWrapper.LiteralWrapper() compressed: "+compress+"\tnot compressed: "+nocompress);
			//			}
		}

		String get() {
			if (l != null) {
				return l;
			}
			try {
				return decompress(bytes);
			} catch (IOException e) {
				// some problem has happened - cannot recover from this
				e.printStackTrace();
				return null;
			}
		}

//		byte[] compress(String s) throws IOException {
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			GZIPOutputStream zipout;
//			zipout = new GZIPOutputStream(out);
//			zipout.write(s.getBytes("UTF-8"));
//			zipout.finish();
//			zipout.flush();
//			//		compress++;
//			return out.toByteArray();
//		}
//
//		String decompress(byte[] result) throws IOException {
//			ByteArrayInputStream in = new ByteArrayInputStream(result);
//			GZIPInputStream zipin = new GZIPInputStream(in);
//			StringBuilder b = new StringBuilder();
//			int c = zipin.read();
//			while (c > -1) {
//				b.append((char) c);
//				c = zipin.read();
//			}
//			return b.toString();
//		}
		
		byte[] compress(String s) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream zipout;
			zipout = new GZIPOutputStream(out);
			Writer writer = new OutputStreamWriter(zipout, COMPRESSED_ENCODING);

			writer.write(s);
			writer.flush();
			zipout.finish();
			zipout.flush();
			// compress++;
			return out.toByteArray();
			}

			String decompress(byte[] result) throws IOException {
			ByteArrayInputStream in = new ByteArrayInputStream(result);
			GZIPInputStream zipin = new GZIPInputStream(in);
			Reader reader = new InputStreamReader(zipin, COMPRESSED_ENCODING);
			StringBuilder b = new StringBuilder();
			int c = reader.read();
			while (c > -1) {
			b.append((char) c);
			c = reader.read();
			}
			return b.toString();
			}



			private static final String COMPRESSED_ENCODING = "UTF-16";

		@Override
		public boolean equals(Object arg0) {
			// TODO Auto-generated method stub
			return super.equals(arg0);
		}
	}

	private final LiteralWrapper literal;
	private final OWLDatatype datatype;
	private final String lang;
	private final int hashcode;

	@SuppressWarnings("javadoc")
	public OWLLiteralImpl(OWLDataFactory dataFactory, String literal, OWLDatatype datatype) {
		super(dataFactory);
		this.literal = new LiteralWrapper(literal);
		this.datatype = datatype;
		this.lang = "";
		hashcode = getHashCode();
	}

	@SuppressWarnings("javadoc")
	public OWLLiteralImpl(OWLDataFactory dataFactory, String literal, String lang) {
		super(dataFactory);
		this.literal = new LiteralWrapper(literal);
		this.lang = lang;
		this.datatype = dataFactory.getRDFPlainLiteral();
		hashcode = getHashCode();
	}

	public String getLiteral() {
		return literal.get();
	}

	public boolean isRDFPlainLiteral() {
		return datatype.equals(getOWLDataFactory().getRDFPlainLiteral());
	}

	public boolean hasLang() {
		return !lang.equals("");
	}

	public boolean isInteger() {
		return datatype.equals(getOWLDataFactory().getIntegerOWLDatatype());
	}

	public int parseInteger() throws NumberFormatException {
		return Integer.parseInt(literal.get());
	}

	public boolean isBoolean() {
		return datatype.equals(getOWLDataFactory().getBooleanOWLDatatype());
	}

	public boolean parseBoolean() throws NumberFormatException {
		if (literal.get().equals("0")) {
			return false;
		}
		if (literal.get().equals("1")) {
			return true;
		}
		if (literal.get().equals("true")) {
			return true;
		}
		if (literal.get().equals("false")) {
			return false;
		}
		return false;
	}

	public boolean isDouble() {
		return datatype.equals(getOWLDataFactory().getDoubleOWLDatatype());
	}

	public double parseDouble() throws NumberFormatException {
		return Double.parseDouble(literal.get());
	}

	public boolean isFloat() {
		return datatype.equals(getOWLDataFactory().getFloatOWLDatatype());
	}

	public float parseFloat() throws NumberFormatException {
		return Float.parseFloat(literal.get());
	}

	public String getLang() {
		return lang;
	}

	public boolean hasLang(String l) {
		//XXX this was missing null checks: a null lang is still valid in the factory, where it becomes a ""
		if (l == null && lang == null) {
			return true;
		}
		if (l == null) {
			l = "";
		}
		return this.lang != null && this.lang.equalsIgnoreCase(l.trim());
	}

	public OWLDatatype getDatatype() {
		return datatype;
	}

	@Override
	public int hashCode() {
		return hashcode;
	}

	private int getHashCode() {
		int hashCode = 277;
		hashCode = hashCode * 37 + getDatatype().hashCode();
		hashCode = hashCode * 37;
		if (literal.l != null) {
			hashCode += literal.l.hashCode();
		} else {
			hashCode += Arrays.hashCode(literal.bytes);
		}
		if (hasLang()) {
			hashCode = hashCode * 37 + getLang().hashCode();
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (!(obj instanceof OWLLiteral)) {
				return false;
			}
			OWLLiteral other = (OWLLiteral) obj;
			return literal.get().equals(other.getLiteral())
					&& datatype.equals(other.getDatatype())
					&& lang.equals(other.getLang());
		}
		return false;
	}

	public void accept(OWLDataVisitor visitor) {
		visitor.visit(this);
	}

	public <O> O accept(OWLDataVisitorEx<O> visitor) {
		return visitor.visit(this);
	}

	public void accept(OWLAnnotationValueVisitor visitor) {
		visitor.visit(this);
	}

	public <O> O accept(OWLAnnotationValueVisitorEx<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	protected int compareObjectOfSameType(OWLObject object) {
		OWLLiteral other = (OWLLiteral) object;
		int diff = literal.get().compareTo(other.getLiteral());
		if (diff != 0) {
			return diff;
		}
		diff = datatype.compareTo(other.getDatatype());
		if (diff != 0) {
			return diff;
		}
		return lang.compareTo(other.getLang());
	}

	public void accept(OWLObjectVisitor visitor) {
		visitor.visit(this);
	}

	public <O> O accept(OWLObjectVisitorEx<O> visitor) {
		return visitor.visit(this);
	}
}
