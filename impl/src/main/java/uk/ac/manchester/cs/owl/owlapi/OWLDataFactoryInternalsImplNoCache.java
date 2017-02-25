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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.FALSELITERAL;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.LANGSTRING;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.TRUELITERAL;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.XSDFLOAT;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.XSDINTEGER;
import static uk.ac.manchester.cs.owl.owlapi.InternalizedEntities.XSDSTRING;

import java.util.Locale;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * No cache used.
 * 
 * @author ignazio
 */
public class OWLDataFactoryInternalsImplNoCache implements OWLDataFactoryInternals {

    private final OWLLiteral negativeFloatZero = getBasicLiteral("-0.0", XSDFLOAT);
    private final boolean useCompression;

    /**
     * @param useCompression
     *        true if compression of literals should be used
     */
    public OWLDataFactoryInternalsImplNoCache(boolean useCompression) {
        this.useCompression = useCompression;
    }

    @Override
    public void purge() {
        // nothing to purge
    }

    @Override
    public OWLClass getOWLClass(IRI iri) {
        return new OWLClassImpl(iri);
    }

    @Override
    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return new OWLObjectPropertyImpl(iri);
    }

    @Override
    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return new OWLDataPropertyImpl(iri);
    }

    @Override
    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return new OWLNamedIndividualImpl(iri);
    }

    @Override
    public OWLDatatype getOWLDatatype(IRI iri) {
        return new OWLDatatypeImpl(iri);
    }

    @Override
    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return new OWLAnnotationPropertyImpl(iri);
    }

    @Override
    public OWLLiteral getOWLLiteral(float value) {
        return new OWLLiteralImplFloat(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String value) {
        if (useCompression) {
            return new OWLLiteralImpl(value, "", XSDSTRING);
        }
        return new OWLLiteralImplString(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String literal, @Nullable String lang) {
        String normalisedLang;
        if (lang == null) {
            normalisedLang = "";
        } else {
            normalisedLang = lang.trim().toLowerCase(Locale.ENGLISH);
        }
        if (normalisedLang.isEmpty()) {
            if (useCompression) {
                return new OWLLiteralImpl(literal, null, XSDSTRING);
            }
            return new OWLLiteralImplString(literal);
        } else {
            if (useCompression) {
                return new OWLLiteralImpl(literal, normalisedLang, null);
            }
            return new OWLLiteralImplPlain(literal, normalisedLang);
        }
    }

    @Override
    public OWLLiteral getOWLLiteral(int value) {
        return new OWLLiteralImplInteger(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(boolean value) {
        return value ? TRUELITERAL : FALSELITERAL;
    }

    @Override
    public OWLLiteral getOWLLiteral(double value) {
        return new OWLLiteralImplDouble(value);
    }

    @Override
    public OWLLiteral getOWLLiteral(String lexicalValue, OWLDatatype datatype) {
        if (datatype.isRDFPlainLiteral() || datatype.equals(LANGSTRING)) {
            int sep = lexicalValue.lastIndexOf('@');
            if (sep != -1) {
                String lex = lexicalValue.substring(0, sep);
                String lang = lexicalValue.substring(sep + 1);
                return verifyNotNull(getBasicLiteral(lex, lang, LANGSTRING));
            } else {
                return verifyNotNull(getBasicLiteral(lexicalValue, XSDSTRING));
            }
        }
        // check the special cases
        return verifyNotNull(parseSpecialCases(lexicalValue, datatype));
    }

    protected OWLLiteral parseSpecialCases(String lexicalValue, OWLDatatype datatype) {
        OWLLiteral literal;
        try {
            if (datatype.isString()) {
                literal = getOWLLiteral(lexicalValue);
            } else if (datatype.isBoolean()) {
                literal = getOWLLiteral(OWLLiteralImpl.asBoolean(lexicalValue.trim()));
            } else if (datatype.isFloat()) {
                literal = parseFloat(lexicalValue, datatype);
            } else if (datatype.isDouble()) {
                literal = getOWLLiteral(Double.parseDouble(lexicalValue));
            } else if (datatype.isInteger()) {
                literal = parseInteger(lexicalValue, datatype);
            } else {
                literal = getBasicLiteral(lexicalValue, datatype);
            }
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            // some literal is malformed, i.e., wrong format
            literal = getBasicLiteral(lexicalValue, datatype);
        }
        return literal;
    }

    protected OWLLiteral parseInteger(String lexicalValue, OWLDatatype datatype) {
        OWLLiteral literal;
        // again, some W3C tests require padding zeroes to make
        // literals different
        if (lexicalValue.trim().charAt(0) == '0') {
            literal = getBasicLiteral(lexicalValue, XSDINTEGER);
        } else {
            try {
                // this is fine for values that can be parsed as
                // ints - not all values are
                literal = getOWLLiteral(Integer.parseInt(lexicalValue));
            } catch (@SuppressWarnings("unused") NumberFormatException ex) {
                // try as a big decimal
                literal = getBasicLiteral(lexicalValue, datatype);
            }
        }
        return literal;
    }

    protected OWLLiteral parseFloat(String lexicalValue, OWLDatatype datatype) {
        if ("-0.0".equals(lexicalValue.trim())) {
            // according to some W3C test, this needs to be
            // different from 0.0; Java floats disagree
            return negativeFloatZero;
        }
        try {
            float f = Float.parseFloat(lexicalValue);
            return getOWLLiteral(f);
        } catch (@SuppressWarnings("unused") NumberFormatException e) {
            return getBasicLiteral(lexicalValue, datatype);
        }
    }

    protected OWLLiteral getBasicLiteral(String lexicalValue, OWLDatatype datatype) {
        return getBasicLiteral(lexicalValue, "", datatype);
    }

    protected OWLLiteral getBasicLiteral(String lexicalValue, String lang, @Nullable OWLDatatype datatype) {
        if (useCompression) {
            if (datatype == null || datatype.isRDFPlainLiteral() || datatype.equals(LANGSTRING)) {
                return new OWLLiteralImplPlain(lexicalValue, lang);
            }
            return new OWLLiteralImpl(lexicalValue, lang, datatype);
        }
        return new OWLLiteralImplNoCompression(lexicalValue, lang, datatype);
    }

    @Override
    public OWLAnnotation getOWLAnnotation(OWLAnnotationProperty property, OWLAnnotationValue value,
        Stream<OWLAnnotation> annotations) {
        return new OWLAnnotationImpl(property, value, annotations);
    }
}
