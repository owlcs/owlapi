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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasAnonymousIndividuals;
import org.semanticweb.owlapi.model.HasSignature;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectImplWithoutEntityAndAnonCaching extends
        OWLObjectAbstractImpl implements
        HasIncrementalSignatureGenerationSupport {

    private static final long serialVersionUID = 40000L;

    @Nonnull
    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        Set<OWLAnonymousIndividual> result = new HashSet<>();
        addAnonymousIndividualsToSet(result);
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getSignature() {
        Set<OWLEntity> result = new HashSet<>();
        addSignatureEntitiesToSet(result);
        return result;
    }

    protected static void addEntitiesFromAnnotationsToSet(
            Collection<OWLAnnotation> annotations, Set<OWLEntity> entities) {
        for (OWLAnnotation annotation : annotations) {
            if (annotation instanceof OWLAnnotationImpl) {
                OWLAnnotationImpl owlAnnotation = (OWLAnnotationImpl) annotation;
                owlAnnotation.addSignatureEntitiesToSet(entities);
            } else {
                entities.addAll(annotation.getSignature());
            }
        }
    }

    protected static void addAnonymousIndividualsFromAnnotationsToSet(
            Collection<OWLAnnotation> annotations,
            Set<OWLAnonymousIndividual> anons) {
        for (OWLAnnotation annotation : annotations) {
            if (annotation instanceof OWLAnnotationImpl) {
                OWLAnnotationImpl owlAnnotation = (OWLAnnotationImpl) annotation;
                owlAnnotation.addAnonymousIndividualsToSet(anons);
            } else {
                anons.addAll(annotation.getAnonymousIndividuals());
            }
        }
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        Set<OWLAnnotationProperty> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLAnnotationProperty()) {
                result.add(ent.asOWLAnnotationProperty());
            }
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        OWLClassExpressionCollector collector = new OWLClassExpressionCollector();
        return accept(collector);
    }

    protected void addSignatureEntitiesToSetForValue(Set<OWLEntity> entities,
            HasSignature canHasSignature) {
        if (canHasSignature instanceof HasIncrementalSignatureGenerationSupport) {
            HasIncrementalSignatureGenerationSupport nonCachedSignatureImplSupport = (HasIncrementalSignatureGenerationSupport) canHasSignature;
            nonCachedSignatureImplSupport.addSignatureEntitiesToSet(entities);
        } else {
            entities.addAll(canHasSignature.getSignature());
        }
    }

    protected void addAnonymousIndividualsToSetForValue(
            Set<OWLAnonymousIndividual> anons,
            HasAnonymousIndividuals canHasAnons) {
        if (canHasAnons instanceof HasIncrementalSignatureGenerationSupport) {
            HasIncrementalSignatureGenerationSupport nonCachedSignatureImplSupport = (HasIncrementalSignatureGenerationSupport) canHasAnons;
            nonCachedSignatureImplSupport.addAnonymousIndividualsToSet(anons);
        } else {
            anons.addAll(canHasAnons.getAnonymousIndividuals());
        }
    }
}
