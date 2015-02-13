/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRestriction;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLQuantifiedDataRestriction;

/** quantified data restriction */
public abstract class OWLQuantifiedDataRestrictionImpl extends
        OWLQuantifiedRestrictionImpl<OWLDataRange> implements
        OWLDataRestriction {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final OWLDataPropertyExpression property;

    /**
     * @param property
     *        property
     * @param filler
     *        filler
     */
    public OWLQuantifiedDataRestrictionImpl(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange filler) {
        super(filler);
        this.property = checkNotNull(property, "property cannot be null");
    }

    @Override
    public void addSignatureEntitiesToSet(Set<OWLEntity> entities) {
        addSignatureEntitiesToSetForValue(entities, getFiller());
        addSignatureEntitiesToSetForValue(entities, property);
    }

    @Override
    public void addAnonymousIndividualsToSet(Set<OWLAnonymousIndividual> anons) {
        addAnonymousIndividualsToSetForValue(anons, getFiller());
        addAnonymousIndividualsToSetForValue(anons, property);
        if (property instanceof HasIncrementalSignatureGenerationSupport) {
            HasIncrementalSignatureGenerationSupport property1 = (HasIncrementalSignatureGenerationSupport) property;
            property1.addAnonymousIndividualsToSet(anons);
        }
    }

    @Override
    public OWLDataPropertyExpression getProperty() {
        return property;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLQuantifiedDataRestriction other = (OWLQuantifiedDataRestriction) object;
        OWLDataPropertyExpression p1 = getProperty();
        OWLDataPropertyExpression p2 = other.getProperty();
        int diff = p1.compareTo(p2);
        if (diff != 0) {
            return diff;
        }
        return getFiller().compareTo(other.getFiller());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLDataRestriction)) {
            return false;
        }
        return getProperty().equals(((OWLDataRestriction) obj).getProperty());
    }
}
