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

import java.util.Locale;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * @author ignazio no cache used
 */
public class InternalsNoCache implements OWLDataFactoryInternals {
	private final OWLDataFactory factory;

	/**
	 * @param f
	 *            the factory to refer to
	 */
	public InternalsNoCache(OWLDataFactory f) {
		factory = f;
	}

	public void purge() {}

	public OWLClass getOWLClass(IRI iri) {
		return new OWLClassImpl(factory, iri);
	}

	public OWLObjectProperty getOWLObjectProperty(IRI iri) {
		return new OWLObjectPropertyImpl(factory, iri);
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		return new OWLDataPropertyImpl(factory, iri);
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		return new OWLNamedIndividualImpl(factory, iri);
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		return new OWLDatatypeImpl(factory, iri);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		return new OWLAnnotationPropertyImpl(factory, iri);
	}

	public OWLLiteral getOWLLiteral(float value) {
		return new OWLLiteralImplFloat(factory, value, getFloatOWLDatatype());
	}

	public OWLLiteral getOWLLiteral(String value) {
		return new OWLLiteralImpl(factory, value,
				getOWLDatatype(XSDVocabulary.STRING.getIRI()));
	}

	public OWLLiteral getOWLLiteral(String literal, String lang) {
		String normalisedLang;
		if (lang == null) {
			normalisedLang = "";
		} else {
			normalisedLang = lang.trim().toLowerCase(Locale.ENGLISH);
		}
		return new OWLLiteralImpl(factory, literal, normalisedLang);
	}

	public OWLLiteral getOWLLiteral(int value) {
		return new OWLLiteralImplInteger(factory, value, getIntegerOWLDatatype());
	}

	public OWLLiteral getOWLLiteral(double value) {
		return new OWLLiteralImplDouble(factory, value, getDoubleOWLDatatype());
	}

	public OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype) {
		OWLLiteral literal;
		if (datatype.isRDFPlainLiteral()) {
			int sep = lexicalValue.lastIndexOf('@');
			if (sep != -1) {
				String lex = lexicalValue.substring(0, sep);
				String lang = lexicalValue.substring(sep + 1);
				literal = new OWLLiteralImpl(factory, lex, lang);
			} else {
				literal = new OWLLiteralImpl(factory, lexicalValue, datatype);
			}
		} else {
			// check the four special cases
			try {
				if (datatype.isBoolean()) {
					lexicalValue = lexicalValue.trim();
					if (lexicalValue.equals("1")) {
						literal = factory.getOWLLiteral(true);
					} else if (lexicalValue.equals("0")) {
						literal = factory.getOWLLiteral(false);
					} else {
						literal = factory.getOWLLiteral(Boolean
								.parseBoolean(lexicalValue));
					}
				} else if (datatype.isFloat()) {
					float f;
					try {
						f = Float.parseFloat(lexicalValue);
						literal = getOWLLiteral(f);
					} catch (NumberFormatException e) {
						literal = new OWLLiteralImpl(factory, lexicalValue, datatype);
					}
				} else if (datatype.isDouble()) {
					literal = getOWLLiteral(Double.parseDouble(lexicalValue));
				} else if (datatype.isInteger()) {
					literal = getOWLLiteral(Integer.parseInt(lexicalValue));
				} else {
					literal = new OWLLiteralImpl(factory, lexicalValue, datatype);
				}
			} catch (NumberFormatException e) {
				// some literal is malformed, i.e., wrong format
				literal = new OWLLiteralImpl(factory, lexicalValue, datatype);
			}
		}
		return literal;
	}

	public OWLDatatype getTopDatatype() {
		return getOWLDatatype(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
	}

	public OWLDatatype getIntegerOWLDatatype() {
		return getOWLDatatype(XSDVocabulary.INTEGER.getIRI());
	}

	public OWLDatatype getFloatOWLDatatype() {
		return getOWLDatatype(XSDVocabulary.FLOAT.getIRI());
	}

	public OWLDatatype getDoubleOWLDatatype() {
		return getOWLDatatype(XSDVocabulary.DOUBLE.getIRI());
	}

	public OWLDatatype getBooleanOWLDatatype() {
		return getOWLDatatype(XSDVocabulary.BOOLEAN.getIRI());
	}

	public OWLDatatype getRDFPlainLiteral() {
		return getOWLDatatype(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
	}

}