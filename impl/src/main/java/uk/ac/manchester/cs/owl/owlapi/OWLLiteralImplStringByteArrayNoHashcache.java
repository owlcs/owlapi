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

import com.google.common.base.Optional;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * An OWLLiteral with xsd:string datatype and no language tag
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 26-Oct-2006
 */
public class OWLLiteralImplStringByteArrayNoHashcache implements OWLLiteral, CharSequence {


    private static final long serialVersionUID = 30406L;
    @Nonnull
    private static final OWLDatatype XSD_STRING = new OWL2DatatypeImpl(
            OWL2Datatype.XSD_STRING);
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    @Nonnull
    private final byte[] bytes;

    /**
     * @param literal the lexical form
     */
    public OWLLiteralImplStringByteArrayNoHashcache(@Nonnull String literal) {
        char[] chars = literal.toCharArray();
        bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c > 255) {
                throw new IllegalArgumentException("Attempt to store a character with codepoint > 255 as a byte: " +
                        literal);
            }
            bytes[i] = (byte) c;
        }
    }

    /**
     *
     * @param bytes  Array is shared, not copied;
     */
    public OWLLiteralImplStringByteArrayNoHashcache(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int length() {
        return bytes.length;
    }

    @Override
    public char charAt(int index) {
        return (char) (bytes[index] & 0xff);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new String(bytes, start, end - start, ISO_8859_1);
    }

    private static int index() {
        return OWLObjectTypeIndexProvider.DATA_TYPE_INDEX_BASE + 8;
    }

    @Override
    public String getLiteral() {
        return new String(bytes, ISO_8859_1);
    }

    @Override
    public boolean hasLang() {
        return false;
    }

    @Override
    public int parseInteger() throws NumberFormatException {
        int i = 0;
        for (byte b : bytes) {
            int c = b & 0xff;
            if (c < '0' || c > '9') {
                throw new NumberFormatException("bad decimal integer " + getLiteral());
            } else {
                i *= 10;
                i += c - '0';
            }
        }
        return i;
    }

    @Override
    public boolean isRDFPlainLiteral() {
        return false;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public boolean isFloat() {
        return false;
    }

    @Override
    public boolean parseBoolean() {
        throw new OWLRuntimeException(getClass().getName()
                + " does not have a boolean value");
    }

    @Override
    public double parseDouble() {
        throw new OWLRuntimeException(getClass().getName()
                + " does not have a double value");
    }

    @Override
    public float parseFloat() {
        throw new OWLRuntimeException(getClass().getName()
                + " does not have a float value");
    }

    @Override
    public String getLang() {
        return "";
    }

    @Override
    public boolean hasLang(String l) {
        return false;
    }

    @Override
    public OWLDatatype getDatatype() {
        return XSD_STRING;
    }

    @Override
    public int hashCode() {
        int h = 0;
        if (h == 0 && bytes.length > 0) {
            byte val[] = bytes;

            for (int i = 0; i < bytes.length; i++) {
                h = 31 * h + (val[i] & 0xff);
            }
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OWLLiteral)) {
            return false;
        }
        OWLLiteral other = (OWLLiteral) obj;
        String otherLiteralString = other.getLiteral();

        if (otherLiteralString.length() != this.length()) {
            return false;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (this.charAt(i) != otherLiteralString.charAt(i)) {
                return false;
            }
        }
        return getDatatype().equals(other.getDatatype())
                && getLang().equals(other.getLang());
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

    //XXX ToDo: Don't call getLiteral

    protected int compareObjectOfSameType(OWLObject object) {
        OWLLiteral other = (OWLLiteral) object;
        int diff = getLiteral().compareTo(other.getLiteral());
        if (diff != 0) {
            return diff;
        }
        diff = getDatatype().compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return getLang().compareTo(other.getLang());
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
    public Set<OWLEntity> getSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return CollectionFactory.emptySet();
    }

    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        return CollectionFactory.emptySet();
    }

    @Override
    public boolean isTopEntity() {
        return false;
    }

    @Override
    public boolean isBottomEntity() {
        return false;
    }

    @SuppressWarnings("null")
    @Override
    public int compareTo(OWLObject o) {
        int thisTypeIndex = index();
        int otherTypeIndex = 0;
        if (o instanceof OWLLiteralImplStringByteArrayNoHashcache) {
            otherTypeIndex = ((OWLLiteralImplStringByteArrayNoHashcache) o).index();
        } else {
            otherTypeIndex = OWLObjectImplWithoutEntityAndAnonCaching.OWLOBJECT_TYPEINDEX_PROVIDER
                    .getTypeIndex(o);
        }
        int diff = thisTypeIndex - otherTypeIndex;
        if (diff == 0) {
            // Objects are the same type
            return compareObjectOfSameType(o);
        } else {
            return diff;
        }
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return false;
    }

    @SuppressWarnings("null")
    @Override
    public Optional<IRI> asIRI() {
        return Optional.absent();
    }

    @SuppressWarnings("null")
    @Override
    public Optional<OWLAnonymousIndividual> asAnonymousIndividual() {
        return Optional.absent();
    }

    @SuppressWarnings("null")
    @Override
    public Optional<OWLLiteral> asLiteral() {
        return Optional.<OWLLiteral>of(this);
    }

    /**
     * package local getter.  Does <b>not</b> make defensive copy.
     * @return
     */
    @Nonnull
     byte[] getBytes() {
        return bytes;
    }
}
