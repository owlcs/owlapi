package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
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
 * Date: 11-Jan-2007<br><br>
 */
public class OWLFacetRestrictionImpl extends OWLObjectImpl implements OWLFacetRestriction {

    private OWLFacet facet;

    private OWLLiteral facetValue;


    public OWLFacetRestrictionImpl(OWLDataFactory dataFactory, OWLFacet facet,
                                   OWLLiteral facetValue) {
        super(dataFactory);
        this.facet = facet;
        this.facetValue = facetValue;
    }


    /**
     * Gets the restricting facet for this facet restriction
     */
    public OWLFacet getFacet() {
        return facet;
    }


    /**
     * Gets the corresponding facet value for this facet restriction
     */
    public OWLLiteral getFacetValue() {
        return facetValue;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    protected int compareObjectOfSameType(OWLObject object) {
        OWLFacetRestriction other = (OWLFacetRestriction) object;
        int diff = facet.compareTo(other.getFacet());
        if (diff != 0) {
            return diff;
        }
        return facetValue.compareTo(other.getFacetValue());
    }
}
