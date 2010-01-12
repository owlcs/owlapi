package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLHasValueRestriction;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
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
public abstract class OWLValueRestrictionImpl<P extends OWLPropertyExpression, V extends OWLObject> extends OWLRestrictionImpl<P> implements OWLHasValueRestriction<P, V> {

    private V value;


    protected OWLValueRestrictionImpl(OWLDataFactory dataFactory, P property, V value) {
        super(dataFactory, property);
        this.value = value;
    }


    public V getValue() {
        return value;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLHasValueRestriction)) {
                return false;
            }
            return ((OWLHasValueRestriction) obj).getValue().equals(value);
        }
        return false;
    }


    final protected int compareObjectOfSameType(OWLObject object) {
        OWLHasValueRestriction other = (OWLHasValueRestriction) object;
        int diff = getProperty().compareTo(other.getProperty());
        if(diff != 0) {
            return diff;
        }
        return value.compareTo(other.getValue());
    }
}
