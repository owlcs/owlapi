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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLSubPropertyChainAxiomImpl extends OWLPropertyAxiomImpl
    implements OWLSubPropertyChainOfAxiom {

    private final List<OWLObjectPropertyExpression> propertyChain;
    private final OWLObjectPropertyExpression superProperty;

    /**
     * @param propertyChain property chain
     * @param superProperty superproperty
     * @param annotations annotations
     */
    public OWLSubPropertyChainAxiomImpl(List<? extends OWLObjectPropertyExpression> propertyChain,
        OWLObjectPropertyExpression superProperty, Collection<OWLAnnotation> annotations) {
        super(annotations);
        this.propertyChain = new ArrayList<>(
            checkIterableNotNull(propertyChain, "propertyChain cannot be null", false));
        this.superProperty = checkNotNull(superProperty, "superProperty cannot be null");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> T getAnnotatedAxiom(Stream<OWLAnnotation> anns) {
        return (T) new OWLSubPropertyChainAxiomImpl(getPropertyChain(), getSuperProperty(),
            mergeAnnos(anns));
    }

    @Override
    @SuppressWarnings("unchecked")
    public OWLSubPropertyChainOfAxiom getAxiomWithoutAnnotations() {
        return !isAnnotated() ? this
            : new OWLSubPropertyChainAxiomImpl(getPropertyChain(), getSuperProperty(),
                NO_ANNOTATIONS);
    }

    @Override
    public List<OWLObjectPropertyExpression> getPropertyChain() {
        return new ArrayList<>(propertyChain);
    }

    @Override
    public OWLObjectPropertyExpression getSuperProperty() {
        return superProperty;
    }

    @Override
    public boolean isEncodingOfTransitiveProperty() {
        if (propertyChain.size() == 2) {
            return superProperty.equals(propertyChain.get(0))
                && superProperty.equals(propertyChain.get(1));
        } else {
            return false;
        }
    }
}
