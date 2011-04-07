/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDatatypeRestrictionImpl extends OWLObjectImpl implements OWLDatatypeRestriction {

    private OWLDatatype datatype;

    private Set<OWLFacetRestriction> facetRestrictions;


    public OWLDatatypeRestrictionImpl(OWLDataFactory dataFactory, OWLDatatype datatype, Set<OWLFacetRestriction> facetRestrictions) {
        super(dataFactory);
        this.datatype = datatype;
        this.facetRestrictions = new HashSet<OWLFacetRestriction>(facetRestrictions);
    }

    public DataRangeType getDataRangeType() {
        return DataRangeType.DATATYPE_RESTRICTION;
    }

    public boolean isDatatype() {
        return false;
    }


    public boolean isTopDatatype() {
        return false;
    }


    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
    }


    public OWLDatatype getDatatype() {
        return datatype;
    }


    /**
     * Gets the facet restrictions on this data range
     * @return A <code>Set</code> of facet restrictions that apply to
     *         this data range
     */
    public Set<OWLFacetRestriction> getFacetRestrictions() {
        return CollectionFactory.getCopyOnRequestSet(facetRestrictions);
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDatatypeRestriction)) {
                return false;
            }
            OWLDatatypeRestriction other = (OWLDatatypeRestriction) obj;
            return other.getDatatype().equals(datatype) && other.getFacetRestrictions().equals(facetRestrictions);
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

    public void accept(OWLDataRangeVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLDatatypeRestriction other = (OWLDatatypeRestriction) object;
        int diff = datatype.compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return compareSets(facetRestrictions, other.getFacetRestrictions());
    }
}
