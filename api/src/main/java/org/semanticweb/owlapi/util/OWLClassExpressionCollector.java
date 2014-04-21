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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

import javax.annotation.Nonnull;

/**
 * Collects all of the nested class expression that are used in some OWLObject.
 * For example, given SubClassOf(ObjectUnionOf(D C) ObjectSomeValuesFrom(R F))
 * the collector could be used to obtain ObjectUnionOf(D C), D, C,
 * ObjectSomeValuesFrom(R F), F
 * 
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
public class OWLClassExpressionCollector implements
        OWLObjectVisitorEx<Set<OWLClassExpression>> {

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull IRI iri) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDatatype node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectProperty property) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLAnonymousIndividual individual) {
        return Collections.emptySet();
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLClassAtom node) {
        return node.getPredicate().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectInverseOf property) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLDataRangeAtom node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLAnnotation node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataOneOf node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataProperty property) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLObjectPropertyAtom node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataIntersectionOf node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLNamedIndividual individual) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataUnionOf node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSubClassOfAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.addAll(axiom.getSubClass().accept(this));
        result.addAll(axiom.getSuperClass().accept(this));
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLOntology ontology) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for (OWLAxiom ax : ontology.getLogicalAxioms()) {
            result.addAll(ax.accept(this));
        }
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDatatypeRestriction node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLBuiltInAtom node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLAnnotationProperty property) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLClass ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLVariable node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLLiteral node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectIntersectionOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            result.addAll(op.accept(this));
        }
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(
            @Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLIndividualArgument node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectUnionOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        for (OWLClassExpression op : ce.getOperands()) {
            result.addAll(op.accept(this));
        }
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLFacetRestriction node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLLiteralArgument node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectComplementOf ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getOperand().accept(this));
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLSameIndividualAtom node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectAllValuesFrom ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectHasValue ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectMinCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectExactCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(
            @Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectMaxCardinality ce) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(ce);
        result.addAll(ce.getFiller().accept(this));
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectHasSelf ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(
            @Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectOneOf ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataSomeValuesFrom ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataAllValuesFrom ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataHasValue ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataMinCardinality ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        return axiom.getRange().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataExactCardinality ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataMaxCardinality ce) {
        return Collections.<OWLClassExpression> singleton(ce);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        result.add(axiom.getOWLClass());
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDeclarationAxiom axiom) {
        return axiom.getEntity().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            result.addAll(ce.accept(this));
        }
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(
            @Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSameIndividualAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        return Collections.emptySet();
    }

    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLHasKeyAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLRule rule) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>();
        for (SWRLAtom atom : rule.getBody()) {
            result.addAll(atom.accept(this));
        }
        for (SWRLAtom atom : rule.getHead()) {
            result.addAll(atom.accept(this));
        }
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression>
            visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull OWLDataComplementOf node) {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> visit(@Nonnull SWRLDataPropertyAtom node) {
        return Collections.emptySet();
    }
}
