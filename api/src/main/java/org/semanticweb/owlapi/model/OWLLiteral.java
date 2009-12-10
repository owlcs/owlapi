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
     * Determines if this literal is typed (i.e. an instance
     * of <code>OWLTypedLiteral</code>)
     *
     * @return <code>true</code> if the literal is typed, otherwise false
     *         <code>false</code>
     */
    boolean isTyped();


    /**
     * If this literal is a typed (i.e. if the <code>isTyped</code>
     * method returns <code>true</code> then this method obtains this literal as a typed
     * literal.  If <code>isTyped</code> returns <code>false</code> then calling this method will
     * cause an <code>OWLRuntimeException</code> to be thrown.
     *
     * @return This literal as a typed literal
     */
    OWLTypedLiteral asOWLStringLiteral();


    /**
     * If this literal is an RDFTextLiteral (i.e. if the <code>isTyped</code>
     * method returns <code>false</code> then this method obtains this literal as
     * an RDFTextLiteral.  If the <code>isTyped</code> method returns <code>true</code>
     * because this method is a typed literal, then calling this method will cause
     * an <code>OWLRuntimeException</code> to be thrown.
     *
     * @return This literal as a more specific RDF Text Literal
     */
    OWLStringLiteral asStringLiteral();


    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);
}
