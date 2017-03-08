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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.BufferByteArray;

/**
 * Implementation of {@link OWLLiteral} that uses compression of strings. See also
 * {@link OWLLiteralImplNoCompression}
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLLiteralImpl extends OWLObjectImpl implements OWLLiteral {

    private static final int COMPRESSION_LIMIT = 160;
    private final LiteralWrapper literal;
    private final OWLDatatype datatype;
    private final String language;

    /**
     * @param literal the lexical form
     * @param lang the language; can be null or an empty string, in which case datatype can be any
     *        datatype but not null
     * @param datatype the datatype; if lang is null or the empty string, it can be null or it MUST
     *        be RDFPlainLiteral
     */
    public OWLLiteralImpl(String literal, @Nullable String lang, @Nullable OWLDatatype datatype) {
        this.literal = new LiteralWrapper(checkNotNull(literal, "literal cannot be null"));
        if (lang == null || lang.isEmpty()) {
            language = "";
            if (datatype == null || datatype.equals(InternalizedEntities.PLAIN)
                            || datatype.equals(InternalizedEntities.XSDSTRING)) {
                this.datatype = InternalizedEntities.XSDSTRING;
            } else {
                this.datatype = datatype;
            }
        } else {
            if (datatype != null && !(datatype.equals(InternalizedEntities.LANGSTRING)
                            || datatype.equals(InternalizedEntities.PLAIN))) {
                // ERROR: attempting to build a literal with a language tag and
                // type different from plain literal or lang string
                throw new OWLRuntimeException("Error: cannot build a literal with type: "
                                + datatype.getIRI() + " and language: " + lang);
            }
            language = lang;
            this.datatype = InternalizedEntities.LANGSTRING;
        }
    }

    @Override
    public String getLiteral() {
        return literal.get();
    }

    @Override
    public boolean hasLang() {
        return !language.isEmpty();
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return getDatatype().isRDFPlainLiteral();
    }

    @Override
    public boolean isInteger() {
        return getDatatype().isInteger();
    }

    @Override
    public boolean isBoolean() {
        return getDatatype().isBoolean();
    }

    @Override
    public boolean isDouble() {
        return getDatatype().isDouble();
    }

    @Override
    public boolean isFloat() {
        return getDatatype().isFloat();
    }

    @Override
    public int parseInteger() {
        return Integer.parseInt(literal.get());
    }

    static boolean asBoolean(String s) {
        return Boolean.parseBoolean(s) || "1".equals(s.trim());
    }

    @Override
    public boolean parseBoolean() {
        return asBoolean(literal.get());
    }

    @Override
    public double parseDouble() {
        return Double.parseDouble(literal.get());
    }

    @Override
    public float parseFloat() {
        return Float.parseFloat(literal.get());
    }

    @Override
    public String getLang() {
        return language;
    }

    @Override
    public boolean hasLang(@Nullable String lang) {
        if (lang == null || lang.isEmpty()) {
            return language.isEmpty();
        }
        return language.equalsIgnoreCase(lang.trim());
    }

    @Override
    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
    protected int hashCode(OWLObject object) {
        return hash(object.hashIndex(),
                        Stream.of(getDatatype(), Integer.valueOf(specificHash()), getLang()));
    }

    private int specificHash() {
        if (literal.l != null) {
            return literal.l.hashCode();
        }
        return Arrays.hashCode(literal.bytes);
    }

    // Literal Wrapper
    private static class LiteralWrapper implements Serializable {

        private static final Charset COMPRESSED_ENCODING = StandardCharsets.UTF_16;
        @Nullable
        String l;
        @Nullable
        byte[] bytes;

        LiteralWrapper(String s) {
            if (s.length() > COMPRESSION_LIMIT) {
                try {
                    bytes = compress(s);
                    l = null;
                } catch (@SuppressWarnings("unused") OWLRuntimeException e) {
                    // some problem happened - defaulting to no compression
                    l = s;
                    bytes = null;
                }
            } else {
                bytes = null;
                l = s;
            }
        }

        String get() {
            if (l != null) {
                return verifyNotNull(l);
            }
            return decompress(verifyNotNull(bytes));
        }

        static byte[] compress(String s) {
            try (BufferByteArray out = new BufferByteArray(32);
                            GZIPOutputStream zipout = new GZIPOutputStream(out);
                            Writer writer = new OutputStreamWriter(zipout, COMPRESSED_ENCODING)) {
                writer.write(s);
                writer.flush();
                zipout.finish();
                zipout.flush();
                return out.toByteArray();
            } catch (IOException e) {
                throw new OWLRuntimeException(e);
            }
        }

        static String decompress(byte[] result) {
            try (ByteArrayInputStream in = new ByteArrayInputStream(result);
                            GZIPInputStream zipin = new GZIPInputStream(in);
                            Reader reader = new InputStreamReader(zipin, COMPRESSED_ENCODING)) {
                StringBuilder b = new StringBuilder();
                int c = reader.read();
                while (c > -1) {
                    b.append((char) c);
                    c = reader.read();
                }
                return b.toString();
            } catch (IOException e) {
                throw new OWLRuntimeException(e);
            }
        }
    }
}
