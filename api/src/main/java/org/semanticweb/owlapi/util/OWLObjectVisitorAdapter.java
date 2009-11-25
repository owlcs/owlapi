package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-Nov-2006<br><br>
 */
public class OWLObjectVisitorAdapter implements OWLObjectVisitor {

    protected void handleDefault(OWLObject owlObject) {

    }

    public void visit(OWLOntology ontology) {
        handleDefault(ontology);
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLClassAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDataPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDeclarationAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointClassesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLHasKeyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSameIndividualAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubClassOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(SWRLRule rule) {
        handleDefault(rule);
    }

    public void visit(OWLClass desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataAllValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataExactCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataMaxCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataMinCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataSomeValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLDataHasValue desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectAllValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectComplementOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectExactCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectHasSelf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectHasValue desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectIntersectionOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectMaxCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectMinCardinality desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectOneOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectSomeValuesFrom desc) {
        handleDefault(desc);
    }

    public void visit(OWLObjectUnionOf desc) {
        handleDefault(desc);
    }

    public void visit(OWLFacetRestriction node) {
        handleDefault(node);
    }

    public void visit(OWLStringLiteral node) {
        handleDefault(node);
    }

    public void visit(OWLTypedLiteral node) {
        handleDefault(node);
    }

    public void visit(OWLDataComplementOf node) {
        handleDefault(node);
    }

    public void visit(OWLDataIntersectionOf node) {
        handleDefault(node);
    }

    public void visit(OWLDataOneOf node) {
        handleDefault(node);
    }

    public void visit(OWLDatatype node) {
        handleDefault(node);
    }

    public void visit(OWLDatatypeRestriction node) {
        handleDefault(node);
    }

    public void visit(OWLDataUnionOf node) {
        handleDefault(node);
    }

    public void visit(OWLDataProperty property) {
        handleDefault(property);
    }

    public void visit(OWLObjectProperty property) {
        handleDefault(property);
    }

    public void visit(OWLObjectInverseOf property) {
        handleDefault(property);
    }

    public void visit(OWLNamedIndividual individual) {
        handleDefault(individual);
    }

    public void visit(OWLAnnotationProperty property) {
        handleDefault(property);
    }

    public void visit(OWLAnnotationAssertionAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        handleDefault(axiom);
    }

    public void visit(OWLAnonymousIndividual individual) {
        handleDefault(individual);
    }

    public void visit(IRI iri) {
        handleDefault(iri);
    }

    public void visit(OWLAnnotation node) {
        handleDefault(node);
    }

    public void visit(SWRLLiteralArgument node) {
        handleDefault(node);
    }

    public void visit(SWRLIndividualArgument node) {
        handleDefault(node);
    }

    public void visit(SWRLVariable node) {
        handleDefault(node);
    }

    public void visit(SWRLBuiltInAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLClassAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLDataRangeAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLDataPropertyAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLDifferentIndividualsAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLObjectPropertyAtom node) {
        handleDefault(node);
    }

    public void visit(SWRLSameIndividualAtom node) {
        handleDefault(node);
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        handleDefault(axiom);
    }
}
