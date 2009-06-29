package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Collection;
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
 * Date: 24-Mar-2009
 */
public class OWLDatatypeDefinitionAxiomImpl extends OWLAxiomImpl implements OWLDatatypeDefinitionAxiom {

    private OWLDatatype datatype;

    private OWLDataRange dataRange;


    public OWLDatatypeDefinitionAxiomImpl(OWLDataFactory dataFactory,
                                     OWLDatatype datatype,
                                     OWLDataRange dataRange, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.datatype = datatype;
        this.dataRange = dataRange;
    }

    public OWLAxiom getAxiomWithoutAnnotations() {
        if(!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(getDatatype(), getDataRange());
    }

    public OWLDatatypeDefinitionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDatatypeDefinitionAxiom(getDatatype(), getDataRange(), mergeAnnos(annotations));
    }

    public OWLDatatype getDatatype() {
        return datatype;
    }


    public OWLDataRange getDataRange() {
        return dataRange;
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isLogicalAxiom() {
        return true;
    }


    public AxiomType getAxiomType() {
        return AxiomType.DATATYPE_DEFINITION;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    protected int compareObjectOfSameType(OWLObject object) {
        OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) object;
        int diff = getDatatype().compareTo(other.getDatatype());
        if(diff != 0) {
            return diff;
        }
        return getDataRange().compareTo(other.getDataRange());
    }


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OWLDatatypeDefinitionAxiom)) {
            return false;
        }
        OWLDatatypeDefinitionAxiom other = (OWLDatatypeDefinitionAxiom) obj;
        return datatype.equals(other.getDatatype()) && dataRange.equals(other.getDataRange());
    }
}
