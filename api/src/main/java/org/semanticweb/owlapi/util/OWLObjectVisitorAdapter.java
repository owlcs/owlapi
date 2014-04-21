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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLObjectVisitorAdapter extends OWLAxiomVisitorAdapter implements
        OWLObjectVisitor {

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        handleDefault(ontology);
    }

    @Override
    public void visit(@Nonnull OWLClass desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLDataSomeValuesFrom desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf desc) {
        handleDefault(desc);
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        handleDefault(property);
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        handleDefault(property);
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        handleDefault(property);
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        handleDefault(individual);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        handleDefault(property);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual individual) {
        handleDefault(individual);
    }

    @Override
    public void visit(@Nonnull IRI iri) {
        handleDefault(iri);
    }

    @Override
    public void visit(@Nonnull OWLAnnotation node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        handleDefault(axiom);
    }
}
