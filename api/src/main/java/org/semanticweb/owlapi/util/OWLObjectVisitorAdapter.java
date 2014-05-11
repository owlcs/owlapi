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

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLObjectVisitorAdapter extends OWLAxiomVisitorAdapter implements
        OWLObjectVisitor {

    @Override
    public void visit(OWLOntology ontology) {
        handleDefault(ontology);
    }

    @Override
    public void visit(OWLClass ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        handleDefault(ce);
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLLiteral node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDatatype node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDataProperty property) {
        handleDefault(property);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        handleDefault(property);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        handleDefault(property);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        handleDefault(individual);
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        handleDefault(property);
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        handleDefault(individual);
    }

    @Override
    public void visit(IRI iri) {
        handleDefault(iri);
    }

    @Override
    public void visit(OWLAnnotation node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLVariable node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLClassAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        handleDefault(node);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        handleDefault(axiom);
    }
}
