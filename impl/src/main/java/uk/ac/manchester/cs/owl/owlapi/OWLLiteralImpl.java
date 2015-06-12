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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import com.google.common.base.Optional;

/**
 * Implementation of {@link OWLLiteral} that uses compression of strings. See
 * also {@link OWLLiteralImplNoCompression}
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLLiteralImpl extends OWLObjectImplWithoutEntityAndAnonCaching implements OWLLiteral {

    private static final long serialVersionUID = 40000L;
    private static final int COMPRESSION_LIMIT = 160;
    private final LiteralWrapper literal;
    @Nonnull
    private static final OWLDatatype RDF_PLAIN_LITERAL = new OWL2DatatypeImpl(OWL2Datatype.RDF_PLAIN_LITERAL);
    @Nonnull
    private final OWLDatatype datatype;
    @Nonnull
    private final String language;
    private final int hashcode;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 8;
    }

    /**
     * @param literal
     *        the lexical form
     * @param lang
     *        the language; can be null or an empty string, in which case
     *        datatype can be any datatype but not null
     * @param datatype
     *        the datatype; if lang is null or the empty string, it can be null
     *        or it MUST be RDFPlainLiteral
     */
    public OWLLiteralImpl(@Nonnull String literal, @Nullable String lang, @Nullable OWLDatatype datatype) {
        this.literal = new LiteralWrapper(checkNotNull(literal, "literal cannot be null"));
        if (lang == null || lang.isEmpty()) {
            language = "";
            if (datatype == null) {
                this.datatype = RDF_PLAIN_LITERAL;
            } else {
                this.datatype = datatype;
            }
        } else {
            if (datatype != null && !datatype.isRDFPlainLiteral()) {
                // ERROR: attempting to build a literal with a language tag and
                // type different from plain literal
                throw new OWLRuntimeException("Error: cannot build a literal with type: " + datatype.getIRI()
                    + " and language: " + lang);
            }
            language = lang;
            this.datatype = RDF_PLAIN_LITERAL;
        }
        hashcode = getHashCode();
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        entities.add(datatype);
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {}

    @Override
    public String getLiteral() {
        return literal.get();
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return datatype.getIRI().equals(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI());
    }

    @Override
    public boolean hasLang() {
        return !language.isEmpty();
    }

    @Override
    public boolean isInteger() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_INTEGER.getIRI());
    }

    @Override
    public int parseInteger() {
        return Integer.parseInt(literal.get());
    }

    @Override
    public boolean isBoolean() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_BOOLEAN.getIRI());
    }

    @Override
    public boolean parseBoolean() {
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

    @Override
    public boolean isDouble() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_DOUBLE.getIRI());
    }

    @Override
    public double parseDouble() {
        return Double.parseDouble(literal.get());
    }

    @Override
    public boolean isFloat() {
        return datatype.getIRI().equals(OWL2Datatype.XSD_FLOAT.getIRI());
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
    public boolean hasLang(String lang) {
        if (lang == null) {
            return language.isEmpty();
        }
        return language.equalsIgnoreCase(lang.trim());
    }

    @Override
    public OWLDatatype getDatatype() {
        return datatype;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    private final int getHashCode() {
        int code = 277;
        code = code * 37 + getDatatype().hashCode();
        code *= 37;
        if (literal.l != null) {
            code += literal.l.hashCode();
        } else {
            code += Arrays.hashCode(literal.bytes);
        }
        if (hasLang()) {
            code = code * 37 + getLang().hashCode();
        }
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLLiteral)) {
            return false;
        }
        OWLLiteral other = (OWLLiteral) obj;
        return literal.get().equals(other.getLiteral()) && datatype.equals(other.getDatatype()) && language.equals(other
            .getLang());
    }

    @Override
    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(OWLAnnotationValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
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
        return language.compareTo(other.getLang());
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<IRI> asIRI() {
        return Optional.absent();
    }

    @Override
    public Optional<OWLAnonymousIndividual> asAnonymousIndividual() {
        return Optional.absent();
    }

    @Override
    public Optional<OWLLiteral> asLiteral() {
        return Optional.<OWLLiteral> of(this);
    }

    // Literal Wrapper
    private static class LiteralWrapper implements Serializable {

        private static final long serialVersionUID = 40000L;
        String l;
        byte[] bytes;

        LiteralWrapper(String s) {
            if (s.length() > COMPRESSION_LIMIT) {
                try {
                    bytes = compress(s);
                    l = null;
                } catch (IOException e) {
                    // some problem happened - defaulting to no compression
                    l = s;
                    bytes = null;
                }
            } else {
                bytes = null;
                l = s;
            }
        }

        @Nonnull
        String get() {
            if (l != null) {
                return verifyNotNull(l);
            }
            try {
                return decompress(bytes);
            } catch (IOException e) {
                // some problem has happened - cannot recover from this
                throw new OWLRuntimeException(e);
            }
        }

        @Nonnull
        static byte[] compress(String s) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream zipout = new GZIPOutputStream(out);
            Writer writer = new OutputStreamWriter(zipout, COMPRESSED_ENCODING);
            writer.write(s);
            writer.flush();
            zipout.finish();
            zipout.flush();
            return out.toByteArray();
        }

        @Nonnull
        static String decompress(byte[] result) throws IOException {
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
    }
}
