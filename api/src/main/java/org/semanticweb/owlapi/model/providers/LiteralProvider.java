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
package org.semanticweb.owlapi.model.providers;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/** Provider for OWLLiteral construction. */
public interface LiteralProvider extends DatatypeProvider {

    // Literals
    /**
     * Gets an {@code OWLLiteral}, which has the specified lexical value, and is
     * typed with the specified datatype.
     * 
     * @param lexicalValue
     *        The lexical value.
     * @param datatype
     *        The datatype.
     * @return An OWLLiteral with the specified lexical value and specified
     *         datatype. If the datatype is {@code rdf:PlainLiteral}, and the
     *         lexical value contains a language tag then the language tag will
     *         be parsed out of the lexical value. For example,
     *         "abc@en"^^rdf:PlainLiteral would be parsed into a lexical value
     *         of "abc" and a language tag of "en".
     */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String lexicalValue,
            @Nonnull OWLDatatype datatype);

    /**
     * Gets an {@code OWLLiteral}, which has the specified lexical value, and is
     * typed with the specified datatype.
     * 
     * @param lexicalValue
     *        The lexical value.
     * @param datatype
     *        The datatype.
     * @return An OWLLiteral with the specified lexical value and specified
     *         datatype. If the datatype is {@code rdf:PlainLiteral}, and the
     *         lexical value contains a language tag then the language tag will
     *         be parsed out of the lexical value. For example,
     *         "abc@en"^^rdf:PlainLiteral would be parsed into a lexical value
     *         of "abc" and a language tag of "en".
     */
    @Nonnull
    default OWLLiteral getOWLLiteral(@Nonnull String lexicalValue,
            @Nonnull OWL2Datatype datatype) {
        checkNotNull(datatype, "datatype cannot be null");
        return getOWLLiteral(lexicalValue, getOWLDatatype(datatype));
    }

    /**
     * Convenience method that obtains a literal typed as an integer.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the integer, and whose data type is xsd:integer.
     */
    @Nonnull
    OWLLiteral getOWLLiteral(int value);

    /**
     * Convenience method that obtains a literal typed as a double.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the double, and whose data type is xsd:double.
     */
    @Nonnull
    OWLLiteral getOWLLiteral(double value);

    /**
     * Convenience method that obtains a literal typed as a boolean.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the boolean, and whose data type is xsd:boolean.
     */
    @Nonnull
    OWLLiteral getOWLLiteral(boolean value);

    /**
     * Convenience method that obtains a literal typed as a float.
     * 
     * @param value
     *        The value of the literal
     * @return An {@code OWLTypedConstant} whose literal is the lexical value of
     *         the float, and whose data type is xsd:float.
     */
    @Nonnull
    OWLLiteral getOWLLiteral(float value);

    /**
     * Gets a literal that has the specified lexical value, and has the datatype
     * xsd:string. The literal will not have a language tag.
     * 
     * @param value
     *        The lexical value of the literal.
     * @return A literal (without a language tag) that has a datatype of
     *         xsd:string.
     */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String value);

    /**
     * Gets an OWLLiteral with a language tag. The datatype of this literal will
     * have an IRI of rdf:PlainLiteral (
     * {@link org.semanticweb.owlapi.vocab.OWLRDFVocabulary#RDF_PLAIN_LITERAL}).
     * 
     * @param literal
     *        The string literal.
     * @param lang
     *        The language tag. The empty string may be specified to indicate an
     *        empty language tag. Leading and trailing white space will be
     *        removed from the tag and the tag will be normalised to LOWER CASE.
     *        If {@code lang} is {@code null} then {@code lang} will be
     *        converted to the empty string (for backwards compatibility). If
     *        not empty, the tag is formed according to <a
     *        href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">BCP47</a> but
     *        the OWL API will not check that the tag conforms to this
     *        specification - it is up to the caller to ensure this.
     * @return The OWLLiteral that represents the string literal with a
     *         (possibly empty) language tag.
     */
    @Nonnull
    OWLLiteral getOWLLiteral(@Nonnull String literal, @Nullable String lang);
}
