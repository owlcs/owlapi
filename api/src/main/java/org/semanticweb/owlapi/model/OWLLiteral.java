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
 */
public interface OWLLiteral extends OWLObject, OWLAnnotationObject, OWLAnnotationValue, OWLPropertyAssertionObject {

    /**
     * Determines if the datatype of this literal is rdf:PlainLiteral
     * @return <code>true</code> if the datatype of this literal is rdf:PlainLiteral, otherwise <code>false</code>.
     */
    boolean isRDFPlainLiteral();

    /**
     * Gets the lexical value of this literal
     *
     * @return gets the lexical value of this literal
     */
    String getLiteral();



    boolean isInteger();

    int parseInteger() throws NumberFormatException;

    boolean isBoolean();

    boolean parseBoolean() throws NumberFormatException;

    boolean isDouble();

    double parseDouble() throws NumberFormatException;
    
    boolean isFloat();

    float parseFloat() throws NumberFormatException;

    /**
     * Determines if this literal has a language tag.
     * @return <code>true</code> if this literal has a non-empty language tag, otherwise <code>false</code>
     */
    boolean hasLang();

    /**
     * Gets the language tag of this literal.
     * @return The language tag of this literal.  If the literal does not have a language tag, because it is
     * not of the type <code>rdf:PlainLiteral</code>, or because its language tag is empty, then the empty string
     * will be returned. (The <code>null</code> value is never returned).
     */
    String getLang();


    /**
     * Determines if this <code>OWLLiteral</code> has a particular language tag.
     * @param lang The specific lang to test for. The tag will be normalised - white space will be trimmed from
     * the end and it will be converted to lower case.
     * @return <code>true</code> if this literal has a language tag equal to <code>lang</code>, otherwise
     * <code>false</code>.
     */
    boolean hasLang(String lang);

    /**
     * Gets the <code>OWLDatatype</code> which types this literal.
     *
     * @return
     * The <code>OWLDatatype</code> that types this literal.  Note that for strings with language tag (previously
     * considered to be untyped literals) the datatype will be rdf:PlainLiteral.
     */
    public OWLDatatype getDatatype();

    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);
}
