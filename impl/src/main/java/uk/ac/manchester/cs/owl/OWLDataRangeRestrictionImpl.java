package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;

import java.util.Collections;
import java.util.HashSet;
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
public class OWLDataRangeRestrictionImpl extends OWLObjectImpl implements OWLDataRangeRestriction {

    private OWLDataRange dataRange;

    private Set<OWLDataRangeFacetRestriction> facetRestrictions;


    public OWLDataRangeRestrictionImpl(OWLDataFactory dataFactory, OWLDataRange dataRange,
                                       Set<OWLDataRangeFacetRestriction> facetRestrictions) {
        super(dataFactory);
        this.dataRange = dataRange;
        this.facetRestrictions = new HashSet<OWLDataRangeFacetRestriction>(facetRestrictions);
    }


    public boolean isDataType() {
        return false;
    }


    public boolean isTopDataType() {
        return false;
    }


    public OWLDataType asOWLDataType() {
        throw new OWLRuntimeException("Not a data type!");
    }


    public OWLDataRange getDataRange() {
        return dataRange;
    }


    /**
     * Gets the facet restrictions on this data range
     * @return A <code>Set</code> of facet restrictions that apply to
     *         this data range
     */
    public Set<OWLDataRangeFacetRestriction> getFacetRestrictions() {
        return Collections.unmodifiableSet(facetRestrictions);
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDataRangeRestriction)) {
                return false;
            }
            OWLDataRangeRestriction other = (OWLDataRangeRestriction) obj;
            return other.getDataRange().equals(dataRange) && other.getFacetRestrictions().equals(facetRestrictions);
        }
        return false;
    }


    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    protected int compareObjectOfSameType(OWLObject object) {
        OWLDataRangeRestriction other = (OWLDataRangeRestriction) object;
        int diff = dataRange.compareTo(other.getDataRange());
        if (diff != 0) {
            return diff;
        }
        return compareSets(facetRestrictions, other.getFacetRestrictions());
    }
}
