package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 *
 * An exception to describe a problem in accessing an ontology.  Since there could
 * be any kind of implementation of <code>OWLOntology</code> (and other model interfaces),
 * some of which may use secondary storage, such as a database backend, there could
 * be problems with accessing ontology objects such as axioms.  In such situations the
 * implementation should wrap the implementation specific exceptions in this exception and
 * rethrow an instance of this exception.  Note that exceptions of this type are
 * unchecked (runtime) exceptions - this is because they represent potentially nasty situations
 * where client code calling methods such as getAxioms() probably doesn't know (or care) how to handle
 * situations where network/database connections fail.
 */
public class OWLOntologyResourceAccessException extends OWLRuntimeException {

    public OWLOntologyResourceAccessException(String message) {
        super(message);
    }


    public OWLOntologyResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }


    public OWLOntologyResourceAccessException(Throwable cause) {
        super(cause);
    }
}
