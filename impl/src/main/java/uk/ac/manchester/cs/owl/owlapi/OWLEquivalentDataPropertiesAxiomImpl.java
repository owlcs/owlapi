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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLEquivalentDataPropertiesAxiomImpl extends OWLNaryPropertyAxiomImpl<OWLDataPropertyExpression>
        implements OWLEquivalentDataPropertiesAxiom {

    /**
     * @param properties
     *        properties
     * @param annotations
     *        annotations
     */
    public OWLEquivalentDataPropertiesAxiomImpl(Collection<? extends OWLDataPropertyExpression> properties,
            Collection<OWLAnnotation> annotations) {
        super(properties, annotations);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return new OWLEquivalentDataPropertiesAxiomImpl(properties, NO_ANNOTATIONS);
    }

    @Override
    public OWLEquivalentDataPropertiesAxiom getAnnotatedAxiom(Stream<OWLAnnotation> anns) {
        return new OWLEquivalentDataPropertiesAxiomImpl(properties, mergeAnnos(anns));
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom> asPairwiseAxioms() {
        Set<OWLEquivalentDataPropertiesAxiom> result = new HashSet<>();
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                result.add(new OWLEquivalentDataPropertiesAxiomImpl(
                        new HashSet<>(Arrays.asList(properties.get(i), properties.get(j))), NO_ANNOTATIONS));
            }
        }
        return result;
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom> splitToAnnotatedPairs() {
        if (properties.size() == 2) {
            return Collections.singleton(this);
        }
        Set<OWLEquivalentDataPropertiesAxiom> result = new HashSet<>();
        for (int i = 0; i < properties.size() - 1; i++) {
            OWLDataPropertyExpression indI = properties.get(i);
            OWLDataPropertyExpression indJ = properties.get(i + 1);
            result.add(new OWLEquivalentDataPropertiesAxiomImpl(new HashSet<>(Arrays.asList(indI, indJ)), annotations));
        }
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return obj instanceof OWLEquivalentDataPropertiesAxiom;
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.EQUIVALENT_DATA_PROPERTIES;
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom> asSubDataPropertyOfAxioms() {
        Set<OWLSubDataPropertyOfAxiom> result = new HashSet<>();
        for (int i = 0; i < properties.size(); i++) {
            for (int j = 0; j < properties.size(); j++) {
                if (i != j) {
                    result.add(new OWLSubDataPropertyOfAxiomImpl(properties.get(i), properties.get(j), NO_ANNOTATIONS));
                }
            }
        }
        return result;
    }
}
