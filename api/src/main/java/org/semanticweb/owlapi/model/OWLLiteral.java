package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Literals">Literal</a> in the OWL 2 Specification.
 * <p>
 * Each literal consists of a lexical form, which is a string, and a datatype.  A literal consisting of a lexical form
 * <code>"abc"</code> and a datatype identified by the IRI <code>datatypeIRI</code> is written as <code>"abc"^^datatypeIRI</code>.
 * <p>
 * Note that literals whose datatype is <code>rdf:PlainLiteral</code> can be abbreviated. For example,
 * literals of the form <code>"abc@"^^rdf:PlainLiteral</code> can be abbreviated in the functional-style syntax, and
 * other concrete syntaxes to "abc".  Literals of the form "abc@langTag"^^rdf:PlainLiteral where "langTag" is not empty
 * are abbreviated in functional-style syntax documents (and other concrete syntaxes) to "abc"@langTag whenever possible.
 */
public interface OWLLiteral extends OWLObject, OWLAnnotationObject, OWLAnnotationValue, OWLPropertyAssertionObject {

    /**
     * Determines if the datatype of this literal is <code>rdf:PlainLiteral</code>.  Note that literals that are
     * abbreviated in the functional syntax (and other concrete syntaxes) and are of the form <code>"abc"</code>
     * or <code>"abc"@langTag</code> will be of the type <code>rdf:PlainLiteral</code> after parsing.
     * @return <code>true</code> if the datatype of this literal is rdf:PlainLiteral, otherwise <code>false</code>.
     */
    boolean isRDFPlainLiteral();

    /**
     * Gets the lexical value of this literal.  Note that if the datatype is <code>rdf:PlainLiteral</code> then the
     * abbreviated lexical form will be returned.  That is, the language tag is not included.
     * @return The lexical value of this literal. If the datatype is <code>rdf:PlainLiteral</code> then the return
     *         values are as follows: If the literal is of the form <code>"abc@"^^rdf:PlainLiteral</code> then the return value
     *         will be "abc" (without the language tag included).  If the literal is of the form
     *         <code>"abc@langTag"^^rdf:PlainLiteral</code> then the return value will be "abc" (without the language tag included).
     */
    String getLiteral();

    /**
     * Gets the <code>OWLDatatype</code> which types this literal.
     * @return The <code>OWLDatatype</code> that types this literal.  Note that for strings with language tag (previously
     *         considered to be untyped literals) the datatype will be rdf:PlainLiteral.  The return value is
     *         never <code>null</code>.
     */
    OWLDatatype getDatatype();

    /**
     * Determines if this literal has a language tag.
     * @return <code>true</code> if this literal has a non-empty language tag, otherwise <code>false</code>
     */
    boolean hasLang();

    /**
     * Gets the language tag of this literal.
     * @return The language tag of this literal.  If the literal does not have a language tag, because it is
     *         not of the type <code>rdf:PlainLiteral</code>, or because its language tag is empty, then the empty string
     *         will be returned. (The <code>null</code> value is never returned).
     */
    String getLang();


    /**
     * Determines if this <code>OWLLiteral</code> has a particular language tag.
     * @param lang The specific lang to test for. The tag will be normalised - white space will be trimmed from
     * the end and it will be converted to lower case.
     * @return <code>true</code> if this literal has a language tag equal to <code>lang</code>, otherwise
     *         <code>false</code>.
     */
    boolean hasLang(String lang);


    /**
     * Determines if this literal is typed with a datatype that has an IRI that is
     * <code>"http://www.w3.org/2001/XMLSchema#"integer</code>.
     * @return <code>true</code> if this literal is typed with <code>"http://www.w3.org/2001/XMLSchema#"integer</code>,
     *         i.e. this literal represents an integer, otherwise <code>false</code>.
     */
    boolean isInteger();

    /**
     * Parses the lexical value of this literal into an integer.  The lexical value of this literal should be in
     * the lexical space of the integer datatype (<code>"http://www.w3.org/2001/XMLSchema#"integer</code>)
     * @return An integer value that is represented by this literal.
     * @throws NumberFormatException if the lexical form could not be parsed into an integer because it is not in
     *                               the lexical space of the integer datatype.
     */
    int parseInteger() throws NumberFormatException;

    /**
     * Determines if this literal is typed with a datatype that has an IRI that is
     * <code>"http://www.w3.org/2001/XMLSchema#"boolean</code>.
     * @return <code>true</code> if this literal is typed with <code>"http://www.w3.org/2001/XMLSchema#"boolean</code>,
     *         i.e. this literal represents a boolean, otherwise <code>false</code>.
     */
    boolean isBoolean();

    /**
     * Parses the lexical value of this literal into a boolean.  The lexical value of this literal should be in
     * the lexical space of the boolean datatype (<code>"http://www.w3.org/2001/XMLSchema#"boolean</code>).
     * @return A boolean value that is represented by this literal.
     * @throws NumberFormatException if the lexical form could not be parsed into a boolean because it is not in
     *                               the lexical space of the boolean datatype.
     */
    boolean parseBoolean() throws NumberFormatException;

    /**
     * Determines if this literal is typed with a datatype that has an IRI that is
     * <code>"http://www.w3.org/2001/XMLSchema#"double</code>.
     * @return <code>true</code> if this literal is typed with <code>"http://www.w3.org/2001/XMLSchema#"double</code>,
     *         i.e. this literal represents a double, otherwise <code>false</code>.
     */
    boolean isDouble();

    /**
     * Parses the lexical value of this literal into a double.  The lexical value of this literal should be in
     * the lexical space of the double datatype (<code>"http://www.w3.org/2001/XMLSchema#"double</code>).
     * @return A double value that is represented by this literal.
     * @throws NumberFormatException if the lexical form could not be parsed into a double because it is not in
     *                               the lexical space of the double datatype.
     */
    double parseDouble() throws NumberFormatException;

    /**
     * Determines if this literal is typed with a datatype that has an IRI that is
     * <code>"http://www.w3.org/2001/XMLSchema#"float</code>.
     * @return <code>true</code> if this literal is typed with <code>"http://www.w3.org/2001/XMLSchema#"float</code>,
     *         i.e. this literal represents a float, otherwise <code>false</code>.
     */
    boolean isFloat();

    /**
     * Parses the lexical value of this literal into a float.  The lexical value of this literal should be in
     * the lexical space of the float datatype (<code>"http://www.w3.org/2001/XMLSchema#"float</code>).
     * @return A float value that is represented by this literal.
     * @throws NumberFormatException if the lexical form could not be parsed into a float because it is not in
     *                               the lexical space of the float datatype.
     */
    float parseFloat() throws NumberFormatException;

    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);
}
