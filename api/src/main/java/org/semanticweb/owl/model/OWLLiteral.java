package org.semanticweb.owl.model;
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
 * Represents a literal (typed or untyped).
 */
public interface OWLLiteral extends OWLObject {

    /**
     * Gets the string that this literal represents.
     */
    String getString();


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
     */
    OWLTypedLiteral asOWLTypedLiteral();


    /**
     * If this constant is an untyped constant (i.e. if the <code>isTyped</code>
     * method returns <code>false</code> then this method obtains this constant as
     * an untyped constant.  If the <code>isTyped</code> method returns <code>true</code>
     * because this method is a typed constant, then calling this method will cause
     * an <code>OWLRuntimeException</code> to be thrown.
     */
    OWLUntypedLiteral asOWLUntypedConstant();


    void accept(OWLDataVisitor visitor);

    <O> O accept(OWLDataVisitorEx<O> visitor);
}
