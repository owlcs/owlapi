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
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Literals">Literal</a> in the OWL 2 Specification.  Literals can either be typed literals ({@link org.semanticweb.owlapi.model.OWLTypedLiteral)
 * or untyped literals ({@link org.semanticweb.owlapi.model.OWLStringLiteral).
 */
public interface OWLLiteral extends OWLObject, OWLAnnotationObject, OWLAnnotationValue, OWLPropertyAssertionObject {

    /**
     * Gets the lexical value of this literal
     *
     * @return gets the lexical value of this literal
     */
    String getLiteral();

    /**
     * Determines if this literal is an instance of {@link org.semanticweb.owlapi.model.OWLStringLiteral}
     * @return <code>true</code> if this <code>OWLLiteral</code> is an instance of
     * {@link org.semanticweb.owlapi.model.OWLStringLiteral}
     */
    boolean isOWLStringLiteral();

    /**
     * Gets the language tag of this literal (if it is an {@link org.semanticweb.owlapi.model.OWLStringLiteral}
     * and has a langauge tag.
     * @return
     * </p>
     * If this <code>OWLLiteral</code> is an {@link org.semanticweb.owlapi.model.OWLStringLiteral}:<br>
     * If this {@link org.semanticweb.owlapi.model.OWLStringLiteral} has a language tag the return value is the string
     * that represents this language tag.  If this {@link org.semanticweb.owlapi.model.OWLStringLiteral} does not have
     * a language tag then the return value is <code>null</code>.
     * </p>
     * If this <code>OWLLiteral</code> is an {@link org.semanticweb.owlapi.model.OWLTypedLiteral}:<br>
     * The return value is <code>null</code> (typed literals cannot have language tags).
     */
    String getLang();


    /**
     * Determines if this <code>OWLLiteral</code> is an <code>OWLStringLiteral</code> and if so whether it has a
     * specific language tag.
     * @param lang The specific lang to test for.
     * @return
     * </p>
     * If this <code>OWLLiteral</code> is an {@link org.semanticweb.owlapi.model.OWLStringLiteral}:<br>
     * If this {@link org.semanticweb.owlapi.model.OWLStringLiteral} has a language tag the return value is <code>true</code>
     * if the language tag is equal to <code>lang</code>.  If this {@link org.semanticweb.owlapi.model.OWLStringLiteral} does not have
     * a language tag then the return value is <code>false</code>.
     * </p>
     * If this <code>OWLLiteral</code> is an {@link org.semanticweb.owlapi.model.OWLTypedLiteral}:<br>
     * The return value is <code>false</code> (typed literals cannot have language tags).
     */
    boolean hasLang(String lang);


    /**
     * Determines if this literal is an {@link org.semanticweb.owlapi.model.OWLTypedLiteral} and is therefore
     * typed with an {@link org.semanticweb.owlapi.model.OWLDatatype}
     * @return <code>true</code> if this literal is an instance of {@link org.semanticweb.owlapi.model.OWLTypedLiteral}
     * and <code>false</code> if this is not the case.
     */
    boolean isOWLTypedLiteral();

    /**
     * Gets the <code>OWLDatatype</code> which types this literal if it is an {@link org.semanticweb.owlapi.model.OWLTypedLiteral}.
     *
     * @return
     * </p>
     * If this literal is an {@link org.semanticweb.owlapi.model.OWLTypedLiteral} then the return value is the
     * <code>OWLDatatype</code> that types this literal.
     * </p>
     * If this literal is an {@link org.semanticweb.owlapi.model.OWLStringLiteral} then the return value is <code>null</code>.
     * ({@link org.semanticweb.owlapi.model.OWLStringLiteral}s are not typed).
     */
    public OWLDatatype getDatatype();


    /**
     * If this literal is a typed (i.e. if the <code>isOWLTypedLiteral</code>
     * method returns <code>true</code> then this method obtains this literal as a typed
     * literal.  If <code>isOWLTypedLiteral</code> returns <code>false</code> then calling this method will
     * cause an <code>OWLRuntimeException</code> to be thrown.
     *
     * @return This literal as an 
     */
    OWLTypedLiteral asOWLTypedLiteral();


    /**
     * If this literal is an RDFTextLiteral (i.e. if the <code>isOWLTypedLiteral</code>
     * method returns <code>false</code> then this method obtains this literal as
     * an RDFTextLiteral.  If the <code>isOWLTypedLiteral</code> method returns <code>true</code>
     * because this method is a typed literal, then calling this method will cause
     * an <code>OWLRuntimeException</code> to be thrown.
     *
     * @return This literal as a more specific <code>OWLStringLiteral</code>
     */
    OWLStringLiteral asOWLStringLiteral();


    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);
}
