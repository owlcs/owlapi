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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLObject;
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

/**
 * Provides the object that is the subject of an axiom.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 5.0.0
 */
public class AxiomSubjectProviderEx implements OWLAxiomVisitorEx<OWLObject> {

    private static final AxiomSubjectProviderEx visitor = new AxiomSubjectProviderEx();

    /**
     * @param axiom
     *        the axiom to visit
     * @return the subject
     */
    public static OWLObject getSubject(@Nonnull OWLAxiom axiom) {
        return checkNotNull(axiom, "axiom cannot be null").accept(visitor);
    }

    @Override
    public OWLObject visit(OWLSubClassOfAxiom axiom) {
        return axiom.getSubClass();
    }

    @Override
    public OWLObject visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom.getSubject();
    }

    @Override
    public OWLObject visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    private static OWLClassExpression selectClassExpression(
            Stream<OWLClassExpression> descs) {
        return descs.sorted().findFirst().orElse(null);
    }

    @Override
    public OWLObject visit(OWLDisjointClassesAxiom axiom) {
        return selectClassExpression(axiom.classExpressions());
    }

    @Override
    public OWLObject visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return axiom.properties().iterator().next();
    }

    @Override
    public OWLObject visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom.getSubject();
    }

    @Override
    public OWLObject visit(OWLDifferentIndividualsAxiom axiom) {
        return axiom.individuals().iterator().next();
    }

    @Override
    public OWLObject visit(OWLDisjointDataPropertiesAxiom axiom) {
        return axiom.properties().iterator().next();
    }

    @Override
    public OWLObject visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return axiom.properties().iterator().next();
    }

    @Override
    public OWLObject visit(OWLObjectPropertyRangeAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLObjectPropertyAssertionAxiom axiom) {
        return axiom.getSubject();
    }

    @Override
    public OWLObject visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLSubObjectPropertyOfAxiom axiom) {
        return axiom.getSubProperty();
    }

    @Override
    public OWLObject visit(OWLDisjointUnionAxiom axiom) {
        return axiom.getOWLClass();
    }

    @Override
    public OWLObject visit(OWLDeclarationAxiom axiom) {
        return axiom.getEntity();
    }

    @Override
    public OWLObject visit(OWLAnnotationAssertionAxiom axiom) {
        return axiom.getSubject();
    }

    @Override
    public OWLObject visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLDataPropertyRangeAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLFunctionalDataPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return axiom.properties().iterator().next();
    }

    @Override
    public OWLObject visit(OWLClassAssertionAxiom axiom) {
        return axiom.getIndividual();
    }

    @Override
    public OWLObject visit(OWLEquivalentClassesAxiom axiom) {
        return selectClassExpression(axiom.classExpressions());
    }

    @Override
    public OWLObject visit(OWLDataPropertyAssertionAxiom axiom) {
        return axiom.getSubject();
    }

    @Override
    public OWLObject visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLSubDataPropertyOfAxiom axiom) {
        return axiom.getSubProperty();
    }

    @Override
    public OWLObject visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLSameIndividualAxiom axiom) {
        return axiom.individuals().iterator().next();
    }

    @Override
    public OWLObject visit(OWLSubPropertyChainOfAxiom axiom) {
        return axiom.getSuperProperty();
    }

    @Override
    public OWLObject visit(OWLInverseObjectPropertiesAxiom axiom) {
        return axiom.getFirstProperty();
    }

    @Override
    public OWLObject visit(SWRLRule rule) {
        return rule.head().iterator().next();
    }

    @Override
    public OWLObject visit(OWLHasKeyAxiom axiom) {
        return axiom.getClassExpression();
    }

    @Override
    public OWLObject visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return axiom.getProperty();
    }

    @Override
    public OWLObject visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return axiom.getSubProperty();
    }

    @Override
    public OWLObject visit(OWLDatatypeDefinitionAxiom axiom) {
        return axiom.getDataRange();
    }
}
