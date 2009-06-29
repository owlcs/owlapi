package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collection;
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
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLIndividualRelationshipAxiomImpl<P extends OWLPropertyExpression, O extends OWLPropertyAssertionObject> extends OWLLogicalAxiomImpl implements OWLPropertyAssertionAxiom<P, O> {

    private OWLIndividual subject;

    private P property;

    private O object;


    public OWLIndividualRelationshipAxiomImpl(OWLDataFactory dataFactory, OWLIndividual subject, P property, O object, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subject = subject;
        this.property = property;
        this.object = object;
    }


    public OWLIndividual getSubject() {
        return subject;
    }


    public P getProperty() {
        return property;
    }


    public O getObject() {
        return object;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLPropertyAssertionAxiom)) {
                return false;
            }
            OWLPropertyAssertionAxiom other = (OWLPropertyAssertionAxiom) obj;
            return other.getSubject().equals(subject) && other.getProperty().equals(property) && other.getObject().equals(
                    object);
        }
        return false;
    }


    final protected int compareObjectOfSameType(OWLObject object) {
        OWLPropertyAssertionAxiom other = (OWLPropertyAssertionAxiom) object;
        int diff = subject.compareTo(other.getSubject());
        if (diff != 0) {
            return diff;
        }
        diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return object.compareTo(other.getObject());
    }
}
