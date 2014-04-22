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

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
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
 * @since 2.2.0
 */
public class AxiomSubjectProvider implements OWLAxiomVisitor {

    private OWLObject subject;

    /**
     * @param axiom
     *        the axiom to visit
     * @return the subject
     */
    public OWLObject getSubject(@Nonnull OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        axiom.accept(this);
        return subject;
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        subject = axiom.getSubClass();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    private static OWLClassExpression selectClassExpression(
            Set<OWLClassExpression> descs) {
        for (OWLClassExpression desc : descs) {
            if (!desc.isAnonymous()) {
                return desc;
            }
        }
        return descs.iterator().next();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        subject = selectClassExpression(axiom.getClassExpressions());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        subject = axiom.getIndividuals().iterator().next();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        subject = axiom.getSubProperty();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        subject = axiom.getOWLClass();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        subject = axiom.getEntity();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        subject = axiom.getIndividual();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        subject = selectClassExpression(axiom.getClassExpressions());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        subject = axiom.getSubProperty();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        subject = axiom.getIndividuals().iterator().next();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        subject = axiom.getSuperProperty();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        subject = axiom.getFirstProperty();
    }

    @Override
    public void visit(SWRLRule rule) {
        subject = checkNotNull(rule, "rule cannot be null").getHead()
                .iterator().next();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        subject = axiom.getClassExpression();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        subject = axiom.getSubProperty();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        subject = axiom.getDataRange();
    }
}
