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
package org.semanticweb.owlapi.util;

import java.io.Serializable;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * adapter for axiom visitors
 * 
 * @author ignazio
 * @param <O>
 *        return type
 */
public class OWLAxiomVisitorExAdapter<O> implements OWLAxiomVisitorEx<O>,
        Serializable {

    private static final long serialVersionUID = 40000L;
    private O object;

    /** adapter with null default */
    public OWLAxiomVisitorExAdapter() {
        this(null);
    }

    /**
     * adapter with object as default value
     * 
     * @param object
     *        default return value
     */
    public OWLAxiomVisitorExAdapter(O object) {
        this.object = object;
    }

    /**
     * override to change default behaviour
     * 
     * @param axiom
     *        visited axiom
     * @return default return value;
     */
    protected O doDefault(@SuppressWarnings("unused") OWLAxiom axiom) {
        return object;
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLSubClassOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDeclarationAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLClassAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLSameIndividualAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        return doDefault(axiom);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLHasKeyAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        return doDefault(axiom);
    }

    @Override
    public O visit(@Nonnull SWRLRule rule) {
        return doDefault(rule);
    }
}
