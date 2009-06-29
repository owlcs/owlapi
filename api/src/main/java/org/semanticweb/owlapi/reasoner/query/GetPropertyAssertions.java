package org.semanticweb.owlapi.reasoner.query;

import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.reasoner.UnsupportedQueryTypeException;

import java.util.Set;
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
 * Date: 18-Mar-2009
 */
public class GetPropertyAssertions implements StandardQuery<Set<OWLPropertyAssertionAxiom>> {

    private OWLIndividual subject;

    private OWLProperty property;

    private OWLPropertyAssertionObject object;


    /**
     * Constructs a property assertion query.  This is really just a simple conjunctive query that contains
     * one atom.  <code>null</code> values indicate must bind variables, where each <code>null</code> represents a
     * different variable.  For example, {@code <Matthew isRelatedTo null>} is a query for the property assertions
     * that have Matthew as a subject and isRelatedTo as a property (predicate) and any value for an object.
     * The query {@code <null isPartOf null>} is a query for all of the individuals that are part of something.
     * @param subject The subject of the query, or <code>null</code> to indicate a must bind variable
     * @param property The property of the query, or <code>null</code> to indicate a must bind variable
     * @param object The object of the query, or <code>null</code> to indicate a must bind variable
     */
    public GetPropertyAssertions(OWLIndividual subject,
                                 OWLProperty property,
                                 OWLPropertyAssertionObject object) {
        this.subject = subject;
        this.property = property;
        this.object = object;
    }


    /**
     * Gets the subject of the query.
     * @return The subject of the query, or <code>null</code> to indicate the subject is
     * a must bind variable
     */
    public OWLIndividual getSubject() {
        return subject;
    }


    /**
     * Gets the property of the query
     * @return The property of the query, or <code>null</code> to indicate the property is
     * a must bind variable
     */
    public OWLProperty getProperty() {
        return property;
    }


    /**
     * Gets the object of the query
     * @return The object of the query, or <code>null</code> to indicate a must bind variable
     */
    public OWLPropertyAssertionObject getObject() {
        return object;
    }


    public Set<OWLPropertyAssertionAxiom> accept(StandardQueryHandler handler) throws UnsupportedQueryTypeException, InterruptedException {
        return null;
    }

    
}
