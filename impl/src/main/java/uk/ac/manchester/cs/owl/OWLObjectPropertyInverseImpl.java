package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;

import java.util.Set;
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
public class OWLObjectPropertyInverseImpl extends OWLObjectPropertyExpressionImpl implements OWLObjectPropertyInverse {

    private OWLObjectPropertyExpression inverseProperty;


    public OWLObjectPropertyInverseImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression inverseProperty) {
        super(dataFactory);
        this.inverseProperty = inverseProperty;
    }


    public OWLObjectPropertyExpression getInverse() {
        return inverseProperty;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLObjectPropertyInverse)) {
                return false;
            }
            return ((OWLObjectPropertyInverse) obj).getInverse().equals(inverseProperty);
        }
        return false;
    }


    protected Set<? extends OWLSubPropertyAxiom<OWLObjectPropertyExpression>> getSubPropertyAxiomsForRHS(OWLOntology ont) {
        return ont.getObjectSubPropertyAxiomsForRHS(this);
    }


    public void accept(OWLPropertyExpressionVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public boolean isAnonymous() {
        return true;
    }


    public OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Property is not a named property.  Check using the isAnonymous method before calling this method!");
    }


    protected int compareObjectOfSameType(OWLObject object) {
        return inverseProperty.compareTo(((OWLObjectPropertyInverse) object).getInverse());
    }
}
