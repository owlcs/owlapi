package org.semanticweb.owlapi.io;

import org.xml.sax.SAXException;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 * </p>
 * Many of the various OWL document formats are written in XML.  This exception wraps a {@link org.xml.sax.SAXException}
 * as an <code>OWLParserException</code>.  Parser implementers should use this class or subclasses of this class to
 * wrap <code>SAXException</code>s.
 */
public class OWLParserSAXException extends OWLParserException {

    public OWLParserSAXException(SAXException cause) {
        super(cause);
    }

    @Override
    public SAXException getCause() {
        return (SAXException) super.getCause();
    }
}
